package cn.lcl.service.impl;

import cn.lcl.mapper.DepartmentMapper;
import cn.lcl.mapper.RoleMapper;
import cn.lcl.mapper.UserMapper;
import cn.lcl.mapper.UserRoleDeptMapper;
import cn.lcl.pojo.Department;
import cn.lcl.pojo.Role;
import cn.lcl.pojo.User;
import cn.lcl.pojo.UserRoleDept;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.UserService;
import cn.lcl.util.ResultUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import org.apache.commons.beanutils.BeanUtils;
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
    public Result<User> addUser(Map<String, Object> map) {
        User user = new User();
        try {
            BeanUtils.copyProperties(user, map);
        } catch (Exception e) {
            // 反射异常
            return null;
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
    public Result<User> active(User user) {
        boolean update = new LambdaUpdateChainWrapper<User>(userMapper).eq(User::getId, user.getId())
                .set(User::getState, 1).update();
        if (update) {
            return ResultUtil.success(user);
        } else {
            return null;
        }
    }
}