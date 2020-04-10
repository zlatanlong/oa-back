package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.UserMapper;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Transactional
    @Override
    public Result addUser(List<User> users) {
        for (User user : users) {
            User eq = new LambdaQueryChainWrapper<User>(userMapper)
                    .eq(User::getNumber, user.getNumber()).one();
            if (eq != null) {
                throw new MyException(ResultEnum.USER_LIST_HAS_REPEAT);
            }
            user.setPassword(String.valueOf(user.getNumber()));
            int insert = userMapper.insert(user);
            if (insert != 1) {
                throw new MyException(ResultEnum.USER_INSERT_FAILED);
            }
        }
        return ResultUtil.success(users.size());
    }

    @Override
    public User queryUserByNumber(String userNumber) {
        return new LambdaQueryChainWrapper<User>(userMapper).eq(User::getNumber, userNumber).one();
    }

    @Override
    public Result login(User user) {
        try {
            UsernamePasswordToken token =
                    new UsernamePasswordToken(String.valueOf(user.getNumber()), user.getPassword());

            Subject subject = SecurityUtils.getSubject();

            subject.login(token); // 执行登录的方法，如果没有异常说明没问题了

            User userAfterLogin = (User) subject.getPrincipal();

            return ResultUtil.success(userAfterLogin);

        } catch (IncorrectCredentialsException e) {
            throw new MyException(ResultEnum.USER_PASSWORD_FAILED);
        } catch (UnknownAccountException e) {
            throw new MyException(ResultEnum.USER_NOT_FOUND);
        }
    }
}
