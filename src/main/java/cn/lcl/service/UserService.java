package cn.lcl.service;

import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;

import java.util.Map;

public interface UserService {
    Result<User> addUser(Map<String, Object> map);

    Result<User> active(User user);

}
