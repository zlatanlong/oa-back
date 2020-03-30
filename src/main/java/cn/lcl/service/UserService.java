package cn.lcl.service;

import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;

import java.util.Map;

public interface UserService {
    Result addUser(Map<String, Object> map);

    Result getUser();

    Result active(User user);

    User queryUserByPhone(String phone);


}
