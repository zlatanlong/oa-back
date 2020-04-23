package cn.lcl.controller;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.dto.IdDTO;
import cn.lcl.dto.ThingAddDTO;
import cn.lcl.dto.ThingReplyDTO;
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
    public Result readThing(@RequestBody @Valid IdDTO thingId, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.readThing(thingId));
    }

    @PostMapping("/joinedList")
    public Result joinedList(@RequestBody @Valid DataPageDTO<ThingReceiver> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.getJoinedThings(page));
    }

    @PostMapping("/createdList")
    public Result createdList(@RequestBody @Valid DataPageDTO<?> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.getCreatedThings(page));
    }

    /**
     *
     * @param page Page
     * @param result valid result
     * @return created thing and its receivers' page by search query.
     */
    @PostMapping("/created")
    public Result created(@RequestBody @Valid DataPageDTO<ThingReceiver> page, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.getCreatedThing(page));
    }

    /**
     * 事务接受者获取一个事务信息
     *
     * @param thingId
     * @return
     */
    @PostMapping("/get")
    public Result get(@RequestBody @Valid IdDTO thingId, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.getJoinedThing(thingId));
    }

    @PostMapping("/reply")
    public Result reply(@Valid ThingReplyDTO replyDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.replyThing(replyDTO));
    }

    @PostMapping("/reply/get")
    public Result replyGet(@RequestBody ThingReceiver thingReceiver) {
        return thingService.getRepliedThing(thingReceiver);
    }

    @PostMapping("/ifReplied")
    public Result ifReplied(@RequestBody @Valid IdDTO idDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.ifReplied(idDTO));
    }

    @PostMapping("/test")
    public void test(Date date) {
        System.out.println(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }
}
