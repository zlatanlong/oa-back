package cn.lcl.service;

import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysRolePermission;
import cn.lcl.pojo.result.Result;

public interface SysRoleService {

    Result addPermissionOnRole(SysRolePermission sysRolePermission);

    Result delPermissionOnRole(SysRolePermission sysRolePermission);

    Result addRole(SysRole role);

    Result getRoles();
}
