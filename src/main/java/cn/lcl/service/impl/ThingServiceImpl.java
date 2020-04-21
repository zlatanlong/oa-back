package cn.lcl.service.impl;

import cn.lcl.dto.ThingDTO;
import cn.lcl.mapper.ThingMapper;
import cn.lcl.mapper.ThingReceiverMapper;
import cn.lcl.mapper.ThingSendFileMapper;
import cn.lcl.mapper.ThingTagMapper;
import cn.lcl.pojo.*;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.ThingService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.FileUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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


    @Transactional
    @Override
    public Result addThing(ThingDTO thing) {
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
                ThingSendFile thingSendFile = new ThingSendFile();
                thingSendFile.setThingId(thing.getId());
                thingSendFile.setOriginName(file.getOriginalFilename());
                thingSendFile.setFileIp(FileUtil.upload(file,String.valueOf(thing.getId())));
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
    public Result getThing(Thing thing) {
        return null;
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
    public Result replyThing(Thing thing) {
        return null;
    }
}
