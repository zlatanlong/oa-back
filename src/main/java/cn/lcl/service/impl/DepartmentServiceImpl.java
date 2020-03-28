package cn.lcl.service.impl;

import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.DepartmentMapper;
import cn.lcl.pojo.Department;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.DepartmentService;
import cn.lcl.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * 需要部门名 上级部门 部门编号 即可
     *
     * @param department
     * @return
     */
    @Override
    public Result addDepartment(Department department) {
        // 先判断number是否重复
        HashMap<String, Object> map = new HashMap<>();
        map.put("number", department.getNumber());
        if (departmentMapper.selectByMap(map).size()!=0) {
            throw new MyException(ResultEnum.DEPARTMENT_REPEAT);
        }

        Long parentId = department.getParentId();
        Department parentDept = departmentMapper.selectById(parentId);
        if (parentDept == null) {
            throw new MyException(ResultEnum.DEPARTMENT_NO_PARENT);
        } else if (parentDept.getParentPath() == null) {
            // 它的父还没有上一辈，此时吧它的父加入path即可
            department.setParentPath(parentId.toString());
        } else {
            // 它的父还有上一辈
            String grandParentPath = parentDept.getParentPath();
            department.setParentPath(grandParentPath + "," + parentId.toString());
        }


        department.setLevel(parentDept.getLevel() + 1);
        departmentMapper.insert(department);
        return ResultUtil.success(department);

    }
}
