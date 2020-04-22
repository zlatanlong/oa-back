package cn.lcl.controller;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.dto.ThingAddDTO;
import cn.lcl.dto.ThingReplyDTO;
import cn.lcl.pojo.Thing;
import cn.lcl.pojo.ThingReceiver;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.ThingService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@RestController
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    ThingService thingService;


    @PostMapping
    public Result addThing(@Valid ThingAddDTO thing, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.addThing(thing));
    }

    @PostMapping("/read")
    public Result readThing(@RequestBody Thing thing) {
        return thingService.readThing(thing);
    }

    @PostMapping("/joinedList")
    public Result joinedList(@RequestBody @Valid DataPageDTO<?> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.getJoinedList(page));
    }

    /**
     * 事务接受者获取一个事务信息
     *
     * @param thing
     * @return
     */
    @PostMapping("/get")
    public Result get(@RequestBody Thing thing) {
        return thingService.getThing4Reply(thing);
    }

    @PostMapping("/reply")
    public Result reply(@Valid ThingReplyDTO replyDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.replyThing(replyDTO));
    }

    @PostMapping("/reply/get")
    public Result replyGet(@RequestBody ThingReceiver thingReceiver) {
        return thingService.getRepliedThing(thingReceiver);
    }

    @PostMapping("/test")
    public void test(Date date){
        System.out.println(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }
}
