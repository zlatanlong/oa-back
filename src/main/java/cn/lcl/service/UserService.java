package cn.lcl.service;

import cn.lcl.pojo.dto.SearchPageDTO;
import cn.lcl.pojo.SysUserRole;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;

import java.util.List;

public interface UserService {
    Result saveUsers(List<User> users);

    Result getUser();

    Result getUser(Integer uid);

    User queryUserByNumber(String userNumber);

    Result login(User user);

    Result logout();

    Result saveRole(SysUserRole userRole);

    Result deleteRole(SysUserRole userRole);

    Result listRoles();

    Result listUsers(SearchPageDTO<User> SearchPageDTO);

}
