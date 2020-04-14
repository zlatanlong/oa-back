package cn.lcl.service;

import cn.lcl.dto.DataPageDTO;
import cn.lcl.pojo.SysUserRole;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;

import java.util.List;

public interface UserService {
    Result addUsers(List<User> users);

    Result getUser();

    User queryUserByNumber(String userNumber);

    Result login(User user);

    Result logout();

    Result addRole(SysUserRole userRole);

    Result delRole(SysUserRole userRole);

    Result getRoles();

    Result getUsers(DataPageDTO<User> DataPageDTO);

}
