package cn.lcl.service;

import cn.lcl.pojo.Role;
import cn.lcl.pojo.result.Result;

public interface RoleService {
    Result<Role> add(Role role);
}
