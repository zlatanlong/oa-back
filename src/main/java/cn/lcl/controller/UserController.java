package cn.lcl.controller;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.pojo.SysUserRole;
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

    @PostMapping
    public Result getUser() {
        return userService.getUser();
    }

    @PostMapping("/addUsers")
    public Result addUsers(@RequestBody List<User> users) {
        return userService.addUsers(users);
    }

    @PostMapping("/login")
    public Result login(@RequestBody @Valid User user, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.login(user));
    }

    @PostMapping("/logout")
    public Result logout() {
        return userService.logout();
    }

    @PostMapping("/addRole")
    public Result addRole(@RequestBody @Valid SysUserRole userRole, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.addRole(userRole));
    }

    @PostMapping("/delRole")
    public Result delRole(@RequestBody @Valid SysUserRole userRole, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.delRole(userRole));
    }

    @PostMapping("/getRoles")
    public Result getRoles() {
        return ResultUtil.success(userService.getRoles());
    }

    @PostMapping("/getUsers")
    public Result getUsers(@RequestBody @Valid DataPageDTO<User> dataPageDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.getUsers(dataPageDTO));
    }


}
