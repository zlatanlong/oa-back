package cn.lcl.service.impl;

import cn.lcl.mapper.UserMapper;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserIService;
import cn.lcl.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public Result addUser(List<User> users) {
        for (User user : users) {
            user.setPassword(String.valueOf(user.getNumber()));
            int insert = userMapper.insert(user);
            if (insert!=1) {
                // 插入失败
                return null;
            }
        }
        return null;
    }
}
