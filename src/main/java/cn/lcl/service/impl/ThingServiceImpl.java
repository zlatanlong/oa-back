package cn.lcl.service.impl;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.dto.IdDTO;
import cn.lcl.dto.ThingAddDTO;
import cn.lcl.dto.ThingReplyDTO;
import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.*;
import cn.lcl.pojo.*;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.ThingService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.FileUtil;
import cn.lcl.util.ResultUtil;
import cn.lcl.vo.Thing4ReplyVO;
import cn.lcl.vo.ThingCreatedListOneVO;
import cn.lcl.vo.ThingCreatedVO;
import cn.lcl.vo.ThingRepliedVO;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ThingServiceImpl implements ThingService {

    @Autowired
    private ThingMapper thingMapper;
    @Autowired
    private ThingTagMapper thingTagMapper;
    @Autowired
    private ThingReceiverMapper thingReceiverMapper;
    @Autowired
    private ThingSendFileMapper thingSendFileMapper;
    @Autowired
    private ThingReplyFileMapper thingReplyFileMapper;


    @Transactional
    @Override
    public Result addThing(ThingAddDTO thing) {
        // 1.插入事务
        thingMapper.insert(thing);
        // 2.插入 事务-标签对应关系
        ThingTag thingTag = new ThingTag();
        thingTag.setTagId(thing.getTagId()); // danger
        thingTag.setThingId(thing.getId());
        thingTagMapper.insert(thingTag);
        // 3.插入 事务-用户对应关系
        for (Integer receiverId : thing.getReceiverIds()) {
            // danger 判断receiver为空？
            ThingReceiver thingReceiver = new ThingReceiver();
            thingReceiver.setHasRead("0");
            thingReceiver.setHasFinish("0");
            thingReceiver.setThingId(thing.getId());
            thingReceiver.setUserId(receiverId);
            thingReceiverMapper.insert(thingReceiver);
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

        return ResultUtil.success();
    }

    @Override
    public Result getCreatedThings(DataPageDTO<?> page) {
        User user = AuthcUtil.getUser();
        Page<ThingCreatedListOneVO> paramPage = page.getParamPage();
        Page<ThingCreatedListOneVO> things = thingMapper.getCreatedThingsByUserId(paramPage, user.getId());
        return ResultUtil.success(things);
    }

    @Override
    public Result getCreatedThing(DataPageDTO<ThingReceiver> page) {
        ThingReceiver tr = page.getData();
        Thing thing = thingMapper.selectById(tr.getThingId());
        if (thing == null) {
            throw new MyException(ResultEnum.THING_NOT_FOUND);
        }
        // 1. valid the thing not null.
        // 2. get vo from mapper and set its thing field.
        ThingCreatedVO thingCreatedVO = thingMapper.getCreatedThingAboutReceiverNum(thing.getId());
        thingCreatedVO.setThing(thing);
        // 3. get the receivers page.
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
        Thing4ReplyVO thing4ReplyVO = new Thing4ReplyVO();
        thing4ReplyVO.setThing(thing);
        // 2. if thing has files, get the files.
        if ("1".equals(thing.getHasSendFile())) {
            thing4ReplyVO.setFiles(new LambdaQueryChainWrapper<>(thingSendFileMapper)
                    .eq(ThingSendFile::getThingId, thing.getId())
                    .list());
        }
        // 3. get questions
        return ResultUtil.success(thing4ReplyVO);
    }

    @Override
    public Result getRepliedThing(ThingReceiver thingReceiver) {
        // 1. test if reply
        thingReceiver = thingReceiverMapper.
                selectThingReceiverAndUserRealNamePage(thingReceiver.getThingId(), thingReceiver.getUserId());
        if (thingReceiver == null) {
            throw new MyException(ResultEnum.THING_AND_RECEIVER_NOT_FOUND);
        }
        // 2. get the content in ThingReceiver
        ThingRepliedVO thingRepliedVO = new ThingRepliedVO();
        thingRepliedVO.setThingReceiver(thingReceiver);
        // 3. get the reply files
        Thing thing = thingMapper.selectById(thingReceiver.getThingId());
        if ("1".equals(thing.getNeedFileReply())) {
            List<ThingReplyFile> list = new LambdaQueryChainWrapper<>(thingReplyFileMapper)
                    .eq(ThingReplyFile::getThingId, thingReceiver.getThingId())
                    .eq(ThingReplyFile::getUserId, thingReceiver.getUserId())
                    .list();
            thingRepliedVO.setFiles(list);
        }
        // 4. get the answer
        return ResultUtil.success(thingRepliedVO);
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
    public Result getJoinedThings(DataPageDTO<ThingReceiver> page) {
        User user = AuthcUtil.getUser();
        Page<ThingReceiver> paramPage = page.getParamPage();
        return ResultUtil.success(thingReceiverMapper.selectThingReceiversByReceiverId(
                paramPage, user.getId(), page.getData()));
    }

    @Transactional
    @Override
    public Result replyThing(ThingReplyDTO reply) {
        Thing thing = thingMapper.selectById(reply.getThingId());
        ThingReceiver thingReceiver = checkThingAndGetThingReceiver(thing);
        if ("1".equals(thingReceiver.getHasFinish())) {
            throw new MyException(ResultEnum.THING_HAS_REPLYED);
        }
        // reply must be read
        thingReceiver.setHasRead("1");

        // 1. the note of this reply
        if (reply.getContent() != null) {
            thingReceiver.setContent(reply.getContent());
        }
        // 2.如果需要回执文件
        if ("1".equals(thing.getNeedFileReply())) {
            for (MultipartFile file : reply.getFiles()) {
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
        // 3.如果需要回执answer
        // 4.成功后设为已回执
        thingReceiver.setHasFinish("1");
        thingReceiverMapper.updateById(thingReceiver);
        return ResultUtil.success();
    }

    @Override
    public Result ifReplied(IdDTO idDTO) {
        User user = AuthcUtil.getUser();
        ThingReceiver one = new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, idDTO.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
        return ResultUtil.success("1".equals(one.getHasFinish()));
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
}
