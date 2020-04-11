package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.SysUserRoleMapper;
import cn.lcl.mapper.UserMapper;
import cn.lcl.pojo.SysPermission;
import cn.lcl.pojo.SysRole;
import cn.lcl.pojo.SysUserRole;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Transactional
    @Override
    public Result addUsers(List<User> users) {
        for (User user : users) {
            User eq = new LambdaQueryChainWrapper<User>(userMapper)
                    .eq(User::getNumber, user.getNumber()).one();
            if (eq != null) {
                throw new MyException(ResultEnum.USER_LIST_HAS_REPEAT);
            }
            user.setPassword(String.valueOf(user.getNumber()));
            int insert = userMapper.insert(user);
            if (insert != 1) {
                throw new MyException(ResultEnum.USER_INSERT_FAILED);
            }
        }
        return ResultUtil.success(users.size());
    }

    @Override
    public Result getUser() {
        return ResultUtil.success(AuthcUtil.getUser());
    }

    @Override
    public User queryUserByNumber(String userNumber) {
        User user = new LambdaQueryChainWrapper<User>(userMapper).eq(User::getNumber, userNumber).one();
        return user;
    }



    @Override
    public Result login(User user) {
        try {
            UsernamePasswordToken token =
                    new UsernamePasswordToken(String.valueOf(user.getNumber()), user.getPassword());

            Subject subject = SecurityUtils.getSubject();

            subject.login(token); // 执行登录的方法，如果没有异常说明没问题了

            User userAfterLogin = (User) subject.getPrincipal();
            getRolesAndPermissions(userAfterLogin);

            return ResultUtil.success(userAfterLogin);

        } catch (IncorrectCredentialsException e) {
            throw new MyException(ResultEnum.USER_PASSWORD_FAILED);
        } catch (UnknownAccountException e) {
            throw new MyException(ResultEnum.USER_NOT_FOUND);
        }
    }

    @Override
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResultUtil.success();
    }

    @Override
    public Result addRole(SysUserRole userRole) {
        sysUserRoleMapper.insert(userRole);

        return ResultUtil.success(userRole);
    }

    @Override
    public Result delRole(SysUserRole userRole) {
        LambdaQueryWrapper<SysUserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SysUserRole::getUserId, userRole.getUserId())
                .eq(SysUserRole::getRoleId, userRole.getRoleId());
        int delete = sysUserRoleMapper.delete(queryWrapper);

        if (delete != 1) {
            throw new MyException(ResultEnum.DELETE_USER_ROLE_FAILED);
        }

        return ResultUtil.success(delete);
    }

    @Override
    public Result getRoles() {
        User user = AuthcUtil.getUser();
        getRolesAndPermissions(user);
        return ResultUtil.success(user);
    }

    private void getRolesAndPermissions(User user) {
        List<SysRole> userRoleList = sysUserRoleMapper.getUserRoleList(user.getId());
        user.setRoleList(userRoleList);
        HashSet<SysPermission> sysPermissions = new HashSet<>();
        for (SysRole role : userRoleList) {
            sysPermissions.addAll(role.getPermissionList());
        }
        user.setPermissionSet(sysPermissions);
    }
}
