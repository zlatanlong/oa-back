package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.AuthorMapper;
import cn.lcl.mapper.PermissionMapper;
import cn.lcl.mapper.RolePermissionMapper;
import cn.lcl.mapper.UserRoleDeptMapper;
import cn.lcl.pojo.Permission;
import cn.lcl.pojo.RolePermission;
import cn.lcl.pojo.User;
import cn.lcl.pojo.UserRoleDept;
import cn.lcl.pojo.ov.RoleApiOV;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.AuthorService;
import cn.lcl.util.ResultUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorMapper authorMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Autowired
    UserRoleDeptMapper userRoleDeptMapper;


    @Override
    public List<Long> findManagedUser(Long urdId) {
        return authorMapper.selectManagedUser(urdId);
    }

    @Override
    public LinkedHashMap<String, String> getRoleFilterMap() {
        List<RoleApiOV> rolePermission = authorMapper.getRolePermission();
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        for (RoleApiOV roleApiOV : rolePermission) {
            String roleString = "roles[" + roleApiOV.getRole() + "]";
            map.put(roleApiOV.getApi(), roleString);
        }

        return map;
    }

    @Override
    public String getRoleInDept(Long deptId) {
        return authorMapper.getRoleInDept(deptId);
    }

    @Override
    public Result authorByUserRoleDeptId(Long urdId) {
        Subject subject = SecurityUtils.getSubject();
        // 检查是否登录
        if (!subject.isAuthenticated()) {
            throw new MyException(ResultEnum.NOT_AUTHENTICATION);

        }
        User user = (User) subject.getPrincipal();

        // 检查此urdId是否绑定此user
        UserRoleDept userRoleDept = userRoleDeptMapper.selectById(urdId);
        if (userRoleDept == null) {
            throw new MyException(ResultEnum.NOT_FOUND_THIS_URD);
        }

        if (userRoleDept.getUserId().equals(user.getId())) {
            // 若是这个用户的权限，绑定urdId
            subject.getSession().setAttribute("urdId", urdId);
            subject.getSession().setAttribute("managedIdList",this.findManagedUser(urdId));
            return ResultUtil.success();
        } else {
            throw new MyException(ResultEnum.NOT_HAVE_THIS_URD);
        }

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
