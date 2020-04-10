package cn.lcl.controller;

import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping("/login")
    public Result login(@RequestBody @Valid User user, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.login(user));
    }
}
