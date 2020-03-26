package cn.lcl.service.impl;

import cn.lcl.mapper.DepartmentMapper;
import cn.lcl.mapper.GreatRoleMapper;
import cn.lcl.mapper.UserMapper;
import cn.lcl.mapper.UserRoleDeptMapper;
import cn.lcl.pojo.UserRoleDept;
import cn.lcl.service.GreatRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GreatRoleServiceImpl implements GreatRoleService {

    @Autowired
    GreatRoleMapper greatRoleMapper;

    @Override
    public List<Long> findManagedUser(UserRoleDept userRoleDept) {
        List<Long> longs = greatRoleMapper.selectManagedUser(userRoleDept.getId());
        return longs;
    }
}
