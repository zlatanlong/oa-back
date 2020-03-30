package cn.lcl.controller;

import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.AuthcService;
import cn.lcl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AuthcService authcService;

    @PostMapping("/register")
    public Result add(@RequestBody HashMap<String, Object> map) {
        return userService.addUser(map);
    }

    @GetMapping
    public Result get() {
        return userService.getUser();
    }


    @PutMapping("/active")
    public Result active(@RequestBody User user) {
        return userService.active(user);
    }

    @PostMapping("/login/web")
    public Result loginWeb(@RequestBody HashMap<String, Object> map) {
        return authcService.loginViaPhone(map);
    }

}
