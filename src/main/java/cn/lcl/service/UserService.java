package cn.lcl.service;

import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;

import java.util.List;

public interface UserService {
    Result addUser(List<User> users);

    User queryUserByNumber(String userNumber);

    Result login(User user);
}
