package cn.lcl.controller;

import cn.lcl.pojo.Department;
import cn.lcl.pojo.result.Result;
import cn.lcl.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @PostMapping
    public Result add(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

}
