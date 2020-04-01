package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.DepartmentMapper;
import cn.lcl.mapper.RoleMapper;
import cn.lcl.mapper.UserMapper;
import cn.lcl.mapper.UserRoleDeptMapper;
import cn.lcl.pojo.Role;
import cn.lcl.pojo.User;
import cn.lcl.pojo.UserRoleDept;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import cn.lcl.util.AuthzUntil;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleDeptMapper userRoleDeptMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    DepartmentMapper departmentMapper;

    @Transactional
    @Override
    public Result addUser(Map<String, Object> map) {
        User user = new User();
        try {
            BeanUtils.copyProperties(user, map);
        } catch (Exception e) {
            // 反射异常
            throw new MyException(ResultEnum.USER_INFO_NOT_INTEGRITY);
        }
        // 从部门获取number
        Long deptId = (Long) map.get("deptId");
        Integer occupation = (Integer) map.get("occupation");
        Long maxNumber = userMapper.selectMaxNumber(deptId, occupation);
        user.setNumber(maxNumber + 1);

        user.setState((byte) 0);
        userMapper.insert(user);

        // 默认添加一个成员的身份
        Role role = new LambdaQueryChainWrapper<>(roleMapper).eq(Role::getName, "成员").one();

        UserRoleDept userRoleDept = new UserRoleDept();
        userRoleDept.setUserId(user.getId());
        userRoleDept.setDeptId(deptId);
        userRoleDept.setRoleId(role.getId());
        userRoleDept.setState((byte) 0);

        userRoleDeptMapper.insert(userRoleDept);
        return ResultUtil.success(user);
    }

    @Override
    public Result getUser() {
        Subject subject = SecurityUtils.getSubject();

        User user = (User) subject.getPrincipal();

        // 用户可能会修改user所以不用subject中的数据
        return ResultUtil.success(userMapper.selectById(user.getId()));
    }

    @Override
    public Result active(User user) {

        AuthzUntil.authzManageUser(user.getId());

        User updateUser = new User();
        updateUser.setState((byte) 1);
        updateUser.setId(user.getId());
        int i = userMapper.updateById(updateUser);

        if (i == 1) {
            return ResultUtil.success(Boolean.TRUE);
        } else {
            throw new MyException(ResultEnum.USER_ACTIVE_FAIL);
        }
    }

    @Override
    public User queryUserByPhone(String phone) {
        return new LambdaQueryChainWrapper<User>(userMapper).eq(User::getPhone, phone).one();
    }
}
