package cn.lcl.controller;

import cn.lcl.dto.ThingDTO;
import cn.lcl.pojo.Thing;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.ThingService;
import cn.lcl.util.FileUtil;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/thing")
public class ThingController {

    @Autowired
    ThingService thingService;


    @PostMapping
    public Result addThing(@RequestBody @Valid ThingDTO thing, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> thingService.addThing(thing));
    }

    @PostMapping("/read")
    public Result readThing(@RequestBody Thing thing) {
        return thingService.readThing(thing);
    }

    @PostMapping("/test")
    public void test(List<MultipartFile> files){
        for (MultipartFile file : files) {
            System.out.println(FileUtil.upload(file));
        }
    }

    @PostMapping("/test2")
    public void test2(Integer[] nums){
        Arrays.stream(nums).forEach(System.out::println);
    }
}
