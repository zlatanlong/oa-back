package cn.lcl.service.impl;

import cn.lcl.mapper.GreatRoleMapper;
import cn.lcl.pojo.UserRoleDept;
import cn.lcl.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    GreatRoleMapper greatRoleMapper;

    @Override
    public List<Long> findManagedUser(UserRoleDept userRoleDept) {
        return greatRoleMapper.selectManagedUser(userRoleDept.getId());
    }
}
