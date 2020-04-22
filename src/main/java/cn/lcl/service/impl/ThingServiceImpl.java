package cn.lcl.service.impl;

import cn.lcl.dto.DataPageDTO;
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
import cn.lcl.vo.ThingReplyVO;
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
    @Autowired
    private UserMapper userMapper;


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
            thingReceiver.setHasReader("0");
            thingReceiver.setHasFinish("0");
            thingReceiver.setThingId(thing.getId());
            thingReceiver.setUserId(receiverId);
            thingReceiverMapper.insert(thingReceiver);
        }
        // 4.插入 事务-文件对应关系
        if (thing.getFiles() != null && thing.getFiles().size() != 0) {
            for (MultipartFile file : thing.getFiles()) {
                if ("".equals(file.getOriginalFilename())) {
                    continue;
                }
                ThingSendFile thingSendFile = new ThingSendFile();
                thingSendFile.setThingId(thing.getId());
                thingSendFile.setOriginName(file.getOriginalFilename());
                try {
                    thingSendFile.setFileIp(FileUtil.upload(file, String.valueOf(thing.getId())));
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
    public Result getCreatedThing(Thing thing) {
        return null;
    }

    @Override
    public Result getThing4Reply(Thing thing) {
        thing = thingMapper.selectById(thing.getId());
        ThingReceiver thingReceiver = checkThingAndGetThingReceiver(thing);
        // 1. if user has replied, return a exception.
        if ("1".equals(thingReceiver.getHasFinish())) {
            throw new MyException(ResultEnum.THING_HAS_REPLYED);
        }
        // 2. get the thing entity.
        Thing4ReplyVO thing4ReplyVO = new Thing4ReplyVO();
        thing4ReplyVO.setThing(thing);
        // 3. get the sender name of this thing.
        User user = userMapper.selectById(thing.getCreatorId());
        thing4ReplyVO.setSenderName(user.getRealName());
        // 4. if has files to read, get the file.
        if ("1".equals(thing.getHasSendFile())) {
            thing4ReplyVO.setFiles(new LambdaQueryChainWrapper<>(thingSendFileMapper)
                    .eq(ThingSendFile::getThingId, thing.getId())
                    .list());
        }

        return ResultUtil.success(thing4ReplyVO);
    }

    @Override
    public Result getRepliedThing(ThingReceiver thingReceiver) {
        // 1. test if reply
        ThingReceiver receiver = new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, thingReceiver.getThingId())
                .eq(ThingReceiver::getUserId, thingReceiver.getUserId())
                .one();
        if (receiver==null) {
            throw new MyException(ResultEnum.THING_AND_RECEIVER_NOT_FOUND);
        }
        // 2. get the content in ThingReceiver
        ThingReplyVO thingReplyVO = new ThingReplyVO();
        thingReplyVO.setThingReceiver(receiver);
        // 3. get the reply files
        Thing thing = thingMapper.selectById(thingReceiver.getThingId());
        if ("1".equals(thing.getNeedFileReply())) {
            List<ThingReplyFile> list = new LambdaQueryChainWrapper<>(thingReplyFileMapper)
                    .eq(ThingReplyFile::getThingId, thingReceiver.getThingId())
                    .eq(ThingReplyFile::getUserId, thingReceiver.getUserId())
                    .list();
            thingReplyVO.setFiles(list);
        }
        // 4. get the answer
        return ResultUtil.success(thingReplyVO);
    }


    @Override
    public Result readThing(Thing thing) {
        User user = AuthcUtil.getUser();
        ThingReceiver one = new LambdaQueryChainWrapper<>(thingReceiverMapper)
                .eq(ThingReceiver::getThingId, thing.getId())
                .eq(ThingReceiver::getUserId, user.getId())
                .one();
        one.setHasReader("1");
        thingReceiverMapper.updateById(one);
        return ResultUtil.success(one);
    }

    @Override
    public Result getJoinedList(DataPageDTO<?> page) {
        User user = AuthcUtil.getUser();
        return ResultUtil.success(thingReceiverMapper.getThingReceiversByUserId(
                new Page<>(page.getPageCurrent(), page.getPageSize()), user.getId()));
    }

    @Transactional
    @Override
    public Result replyThing(ThingReplyDTO reply) {
        Thing thing = thingMapper.selectById(reply.getThingId());

        ThingReceiver thingReceiver = checkThingAndGetThingReceiver(thing);
        // reply must be read
        thingReceiver.setHasReader("1");
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
                    thingReplyFile.setFileIp(FileUtil.upload(file, thing.getId() + "/" + user.getId()));
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
