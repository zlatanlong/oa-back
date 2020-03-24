package cn.lcl.service;

import cn.lcl.pojo.User;

import java.util.Map;

public interface UserService {
    User addUser(Map<String, Object> map);

    User active(User user);
}
