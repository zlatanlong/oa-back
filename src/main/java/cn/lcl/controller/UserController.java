package cn.lcl.controller;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.dto.IdDTO;
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

    @PostMapping("/get")
    public Result getUser(@RequestBody IdDTO idDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.getUser(idDTO.getId()));
    }

    @PostMapping("/addUsers")
    public Result addUsers(@RequestBody List<User> users) {
        return userService.saveUsers(users);
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
        return ResultUtil.vaildFieldError(result, () -> userService.saveRole(userRole));
    }

    @PostMapping("/delRole")
    public Result delRole(@RequestBody @Valid SysUserRole userRole, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.deleteRole(userRole));
    }

    @PostMapping("/getRoles")
    public Result getRoles() {
        return ResultUtil.success(userService.listRoles());
    }

    @PostMapping("/getUsers")
    public Result getUsers(@RequestBody @Valid SearchPageDTO<User> searchPageDTO, BindingResult result) {
        return ResultUtil.vaildFieldError(result, () -> userService.listUsers(searchPageDTO));
    }


}
