package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.SysPermissionMapper;
import cn.lcl.mapper.SysRoleMapper;
import cn.lcl.mapper.SysRolePermissionMapper;
import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysRolePermission;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.SysRoleService;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Transactional
    @Override
    public Result addPermissionOnRole(SysRolePermission sysRolePermission) {
        SysPermission permission = sysPermissionMapper.selectById(sysRolePermission.getPermissionId());
        SysRole role = sysRoleMapper.selectById(sysRolePermission.getRoleId());
        if (permission == null) {
            throw new MyException(ResultEnum.PERMISSION_NOT_FOUND);
        } else if (role == null) {
            throw new MyException(ResultEnum.ROLE_NOT_FOUND);
        }
        SysRolePermission one = new LambdaQueryChainWrapper<>(sysRolePermissionMapper)
                .eq(SysRolePermission::getRoleId, sysRolePermission.getRoleId())
                .eq(SysRolePermission::getPermissionId, sysRolePermission.getPermissionId())
                .one();
        if (one != null) {
            throw new MyException(ResultEnum.ROLE_ALREADLY_HAS_THIS_PERMISSION);
        }

        sysRolePermissionMapper.insert(sysRolePermission);

        return ResultUtil.success(sysRolePermission);
    }

    @Override
    public Result delPermissionOnRole(SysRolePermission sysRolePermission) {
        return null;
    }

    @Override
    public Result addRole(SysRole role) {
        int insert = sysRoleMapper.insert(role);
        return ResultUtil.success(role);
    }


    @Override
    public Result getRoles() {
        return ResultUtil.success(new LambdaQueryChainWrapper<>(sysRoleMapper).list());
    }
}
