package cn.lcl.service.impl;

import cn.lcl.mapper.AuthorMapper;
import cn.lcl.mapper.PermissionMapper;
import cn.lcl.mapper.RolePermissionMapper;
import cn.lcl.pojo.Permission;
import cn.lcl.pojo.RolePermission;
import cn.lcl.pojo.UserRoleDept;
import cn.lcl.pojo.ov.RoleApiOV;
import cn.lcl.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorMapper authorMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;


    @Override
    public List<Long> findManagedUser(UserRoleDept userRoleDept) {
        return authorMapper.selectManagedUser(userRoleDept.getId());
    }

    @Override
    public LinkedHashMap<String, String> getRoleFilterMap() {
        Long roleId = 1242320794583883778L;
        List<RoleApiOV> rolePermission = authorMapper.getRolePermission(roleId);
        LinkedHashMap<String,String> map = new LinkedHashMap<>();

        for (RoleApiOV roleApiOV : rolePermission) {
            String roleString = "roles["+roleApiOV.getRole()+"]";
            map.put(roleApiOV.getApi(),roleString);
        }

        return map;
    }

    @Transactional
    @Override
    public boolean addPermissionWithRole(String api, String description) {
        Permission permission = new Permission();
        permission.setApi(api);
        permission.setName(description);
        permissionMapper.insert(permission);

        RolePermission rolePermission = new RolePermission();
        Long roleId = 1242320794583883778L;
        rolePermission.setPermId(permission.getId());
        rolePermission.setRoleId(roleId);
        rolePermissionMapper.insert(rolePermission);

        return true;
    }
}
