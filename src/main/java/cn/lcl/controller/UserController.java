package cn.lcl.controller;

import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public Result add(@RequestBody Map<String,Object> map) {
        return userService.addUser(map);
    }

    @PutMapping("/active")
    public Result active(@RequestBody User user) {
        return userService.active(user);
    }

}
