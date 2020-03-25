package cn.lcl.service.impl;

import cn.lcl.mapper.RoleMapper;
import cn.lcl.pojo.Role;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.RoleService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public Result<Role> add(Role role) {
        roleMapper.insert(role);
        return ResultUtil.success(role);
    }
}
