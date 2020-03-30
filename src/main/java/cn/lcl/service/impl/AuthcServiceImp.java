package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.AuthcService;
import cn.lcl.service.UserService;
import cn.lcl.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthcServiceImp implements AuthcService {

    @Autowired
    UserService userService;


    @Override
    public Result loginViaPhone(HashMap<String, Object> map) {
        try {
            String phone = (String) map.get("phone");
            String password = (String) map.get("password");
            UsernamePasswordToken token = new UsernamePasswordToken(phone, password);

            Subject subject = SecurityUtils.getSubject();

            subject.login(token); // 执行登录的方法，如果没有异常说明没问题了
            User user = (User) subject.getPrincipal();
            if (user.getState()!=1) {
                subject.logout();
                throw new MyException((ResultEnum.USER_NOT_ACTIVE));
            }
            return ResultUtil.success(subject.getPrincipal());


        } catch (IncorrectCredentialsException e) {
            throw new MyException(ResultEnum.USER_PASSWORD_FAILED);
        } catch (UnknownAccountException e) {
            throw new MyException(ResultEnum.USER_NOT_FOUND);
        } catch (ClassCastException e) {
            throw new MyException(ResultEnum.USER_INFO_NOT_INTEGRITY);
        }
    }
}
