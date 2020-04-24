package cn.lcl.service;

import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysRolePermission;
import cn.lcl.pojo.result.Result;

public interface SysRoleService {

    Result savePermissionOnRole(SysRolePermission sysRolePermission);

    Result removePermissionOnRole(SysRolePermission sysRolePermission);

    Result saveRole(SysRole role);

    Result listRoles();

    Result getRole(SysRole sysRole);
}
