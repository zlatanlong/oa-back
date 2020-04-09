package cn.lcl.controller;

import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUsers")
    public Result addUsers(@RequestBody List<User> users) {
        return userService.addUser(users);
    }

    @GetMapping("/test")
    public String test(){
        return "hello";
    }
}
