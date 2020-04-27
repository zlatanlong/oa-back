package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.*;
import cn.lcl.pojo.*;
import cn.lcl.pojo.dto.*;
import cn.lcl.pojo.result.Result;
import cn.lcl.pojo.vo.ThingCreatedListOneVO;
import cn.lcl.pojo.vo.ThingCreatedVO;
import cn.lcl.pojo.vo.ThingFInishedVO;
import cn.lcl.pojo.vo.ThingJoinedVO;
import cn.lcl.service.QuestionService;
import cn.lcl.service.ThingService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.FileUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ThingServiceImpl implements ThingService {

    private final ThingMapper thingMapper;
    private final ThingTagMapper thingTagMapper;
    private final ThingReceiverMapper thingReceiverMapper;
    private final ThingSendFileMapper thingSendFileMapper;
    private final ThingReplyFileMapper thingReplyFileMapper;
    private final QuestionService questionService;
    private final TeamMemberMapper teamMemberMapper;

    public ThingServiceImpl(ThingMapper thingMapper, ThingTagMapper thingTagMapper,
                            ThingReceiverMapper thingReceiverMapper, ThingSendFileMapper thingSendFileMapper,
                            ThingReplyFileMapper thingReplyFileMapper, QuestionService questionService, TeamMemberMapper teamMemberMapper) {
        this.thingMapper = thingMapper;
        this.thingTagMapper = thingTagMapper;
        this.thingReceiverMapper = thingReceiverMapper;
        this.thingSendFileMapper = thingSendFileMapper;
        this.thingReplyFileMapper = thingReplyFileMapper;
        this.questionService = questionService;
        this.teamMemberMapper = teamMemberMapper;
    }


    @Transactional
    @Override
    public Result saveThing(ThingAddDTO thing) {
        // 1.插入事务
        thingMapper.insert(thing);
        // 2.插入 事务-标签对应关系
        ThingTag thingTag = new ThingTag();
        thingTag.setTagId(thing.getTagId()); // danger
        thingTag.setThingId(thing.getId());
        thingTagMapper.insert(thingTag);
        // 3.插入 事务-用户对应关系
        if (thing.getUserTeam()) {
            if (thing.getTeamId() == null) {
                throw new MyException(ResultEnum.THING_TEAM_ID_NOT_NULL);
            }
            LambdaQueryWrapper<TeamMember> query = Wrappers.lambdaQuery();
            query.select(TeamMember::getUserId).eq(TeamMember::getTeamId, thing.getTeamId());
            List<TeamMember> teamMembers = teamMemberMapper.selectList(query);
            for (TeamMember teamMember : teamMembers) {
                // danger 判断receiver为空？
                ThingReceiver thingReceiver = new ThingReceiver();
                thingReceiver.setHasRead("0");
                thingReceiver.setHasFinished("0");
                thingReceiver.setThingId(thing.getId());
                thingReceiver.setUserId(teamMember.getUserId());
                thingReceiverMapper.insert(thingReceiver);
            }
        } else {
            if (thing.getReceiverIds() == null || thing.getReceiverIds().length == 0) {
                throw new MyException(ResultEnum.THING_RECEIVERIDS_NOT_NULL);
            }
            for (Integer receiverId : thing.getReceiverIds()) {
                // danger 判断receiver为空？
                ThingReceiver thingReceiver = new ThingReceiver();
                thingReceiver.setHasRead("0");
                thingReceiver.setHasFinished("0");
                thingReceiver.setThingId(thing.getId());
                thingReceiver.setUserId(receiverId);
                thingReceiverMapper.insert(thingReceiver);
            }
        }
        // 4.插入 事务-文件对应关系
        if ("1".equals(thing.getHasSendFile())) {
            for (MultipartFile file : thing.getFiles()) {
                if ("".equals(file.getOriginalFilename())) {
                    continue;
                }
                ThingSendFile thingSendFile = new ThingSendFile();
                thingSendFile.setThingId(thing.getId());
                thingSendFile.setOriginName(file.getOriginalFilename());
                try {
                    thingSendFile.setFileUrl(FileUtil.upload(file, String.valueOf(thing.getId())));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyException(ResultEnum.FILE_UPLOAD_FAILED);
                }
                thingSendFileMapper.insert(thingSendFile);
            }
        }
        // 5.插入 事务-问题对应关系(单独业务)
        // 如果不需要完成，则不需要此关系。
        if (!"1".equals(thing.getNeedFinish())) {
            return ResultUtil.success();
        }
        if ("1".equals(thing.getNeedAnswer())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(thing.getQuestionsJSON());
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        ThingQuestion thingQuestion = mapper.readValue(node.toString(), ThingQuestion.class);
                        thingQuestion.setThingId(thing.getId());
                        Boolean aBoolean = questionService.saveQuestion(thingQuestion);
                        if (!aBoolean) {
                            throw new MyException(ResultEnum.THING_QUESTION_INSERT_FAILED);
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new MyException(ResultEnum.THING_QUESTION_INSERT_FAILED);
            }
        }

        return ResultUtil.success();
    }

    @Override
    public Result listCreatedThings(SearchPageDTO<?> page) {
        User user = AuthcUtil.getUser();
        Page<ThingCreatedListOneVO> paramPage = page.getParamPage();
        Page<ThingCreatedListOneVO> things = thingMapper.getCreatedThingsByUserId(paramPage, user.getId());
        return ResultUtil.success(things);
    }

    @Override
    public Result getCreatedThing(SearchPageDTO<ThingReceiver> page) {
        ThingReceiver tr = page.getData();
        Thing thing = thingMapper.getThingById(tr.getThingId());
        if (thing == null) {
            throw new MyException(ResultEnum.THING_NOT_FOUND);
        }
        // 1. valid the thing not null.
        // 2. get vo from mapper and set its thing field.
        ThingCreatedVO thingCreatedVO = thingMapper.getCreatedThingAboutReceiverNum(thing.getId());
        thingCreatedVO.setThing(thing);
        // 3. get questions
        if ("1".equals(thing.getNeedAnswer())) {
            thingCreatedVO.setQuestions(questionService.listQuestions(thing.getId()));
        }
        // 4. get the receivers page.
        Page<ThingReceiver> paramPage = page.getParamPage();
        thingCreatedVO.setThingReceiversPage(
                thingReceiverMapper.selectThingReceiversAndUserRealNamePageByThingId(paramPage, tr));

        return ResultUtil.success(thingCreatedVO);
    }

    @Override
    public Result getJoinedThing(IdDTO thingId) {
        Thing thing = thingMapper.getThingById(thingId.getId());
        ThingReceiver thingReceiver = checkThingAndGetThingReceiver(thing);
        thingReceiver.setHasRead("1");
        thingReceiverMapper.updateById(thingReceiver);
        // 1. get the thing entity.
        ThingJoinedVO thingJoinedVO = new ThingJoinedVO();
        thingJoinedVO.setThing(thing);
        // 2. if thing has files, get the files.
        if ("1".equals(thing.getHasSendFile())) {
            thingJoinedVO.setFiles(new LambdaQueryChainWrapper<>(thingSendFileMapper)
                    .eq(ThingSendFile::getThingId, thing.getId())
                    .list());
        }
        // 3. get questions
        if ("1".equals(thing.getNeedAnswer())) {
            thingJoinedVO.setQuestions(questionService.listQuestions(thingId.getId()));
        }

        return ResultUtil.success(thingJoinedVO);
    }

    @Override
    public Result readThing(IdDTO thingId) {
        User user = AuthcUtil.getUser();
        ThingReceiver one = new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, thingId.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
        one.setHasRead("1");
        thingReceiverMapper.updateById(one);
        return ResultUtil.success(one);
    }

    @Override
    public Result listJoinedThings(SearchPageDTO<ThingReceiver> page) {
        User user = AuthcUtil.getUser();
        Page<ThingReceiver> paramPage = page.getParamPage();
        return ResultUtil.success(thingReceiverMapper.selectThingReceiversByReceiverId(
                paramPage, user.getId(), page.getData()));
    }

    @Transactional
    @Override
    public Result finishThing(ThingFinishDTO finishDTO) {
        Thing thing = thingMapper.selectById(finishDTO.getThingId());
        ThingReceiver thingReceiver = checkThingAndGetThingReceiver(thing);
        if ("1".equals(thingReceiver.getHasFinished())) {
            throw new MyException(ResultEnum.THING_HAS_FINISHED);
        }
        if (!"1".equals(thing.getNeedFinish())) {
            throw new MyException(ResultEnum.THING_NOT_HAVE_TO_FINISH);
        }
        // finish must be read
        thingReceiver.setHasRead("1");

        // 1. the note of this reply
        if (finishDTO.getContent() != null) {
            thingReceiver.setContent(finishDTO.getContent());
        }
        // 2.如果需要回执文件
        if ("1".equals(thing.getNeedFileReply())) {
            finishFiles(finishDTO, thing);
        }
        // 3.如果需要回执answer
        if ("1".equals(thing.getNeedAnswer())) {
            finishAnswers(finishDTO, thing);
        }
        // 4.成功后设为已完成
        thingReceiver.setHasFinished("1");
        thingReceiverMapper.updateById(thingReceiver);
        return ResultUtil.success();
    }

    @Override
    public Result getFinishedThing(ThingReceiver thingReceiver) {
        // 1. test if exist.
        thingReceiver = thingReceiverMapper.
                selectThingReceiverAndUserRealNamePage(thingReceiver.getThingId(), thingReceiver.getUserId());
        if (thingReceiver == null) {
            throw new MyException(ResultEnum.THING_AND_RECEIVER_NOT_FOUND);
        }
        if (!"1".equals(thingReceiver.getHasFinished())) {
            throw new MyException(ResultEnum.THING_NOT_FINISHED);
        }

        // 2. get the content in ThingReceiver
        ThingFInishedVO thingFinishedVO = new ThingFInishedVO();
        thingFinishedVO.setThingReceiver(thingReceiver);

        // 3. get the reply files
        Thing thing = thingMapper.selectById(thingReceiver.getThingId());
        if ("1".equals(thing.getNeedFileReply())) {
            List<ThingReplyFile> list = new LambdaQueryChainWrapper<>(thingReplyFileMapper)
                    .eq(ThingReplyFile::getThingId, thingReceiver.getThingId())
                    .eq(ThingReplyFile::getUserId, thingReceiver.getUserId())
                    .list();
            thingFinishedVO.setFiles(list);
        }

        // 4. get the answer
        if ("1".equals(thing.getNeedAnswer())) {
            thingFinishedVO.setQuestions(questionService.listQuestionsWithAnswers(thing.getId()));
        }
        return ResultUtil.success(thingFinishedVO);
    }

    @Override
    public Result ifFinished(IdDTO idDTO) {
        User user = AuthcUtil.getUser();
        ThingReceiver one = new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, idDTO.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
        return ResultUtil.success("1".equals(one.getHasFinished()));
    }

    private ThingReceiver checkThingAndGetThingReceiver(Thing thing) {
        if (thing == null) {
            throw new MyException(ResultEnum.THING_NOT_FOUND);
        }
        User user = AuthcUtil.getUser();
        return new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, thing.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
    }

    private void finishAnswers(ThingFinishDTO finishDTO, Thing thing) {
        if ("1".equals(thing.getNeedAnswer())) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode jsonNode = mapper.readTree(finishDTO.getAnswersJSON());
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        QuestionAnswer answer = mapper.readValue(node.toString(), QuestionAnswer.class);
                        Boolean aBoolean = questionService.saveQuestionAnswer(answer);
                        if (!aBoolean) {
                            throw new MyException(ResultEnum.ANSWER_INSERT_FAILED);
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new MyException(ResultEnum.ANSWER_INSERT_FAILED);
            }
        }
    }

    private void finishFiles(ThingFinishDTO finishDTO, Thing thing) {
        if ("1".equals(thing.getNeedFileReply())) {
            for (MultipartFile file : finishDTO.getFiles()) {
                if ("".equals(file.getOriginalFilename())) {
                    continue;
                }
                ThingReplyFile thingReplyFile = new ThingReplyFile();
                thingReplyFile.setThingId(thing.getId());
                thingReplyFile.setOriginName(file.getOriginalFilename());
                User user = AuthcUtil.getUser();
                try {
                    thingReplyFile.setFileUrl(FileUtil.upload(file, thing.getId() + "/" + user.getId()));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new MyException(ResultEnum.FILE_UPLOAD_FAILED);
                }
                thingReplyFile.setUserId(user.getId());
                thingReplyFileMapper.insert(thingReplyFile);
            }
        }
    }
}
