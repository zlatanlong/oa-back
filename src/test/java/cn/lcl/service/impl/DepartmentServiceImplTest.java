package cn.lcl.service.impl;

import cn.lcl.pojo.Department;
import cn.lcl.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentServiceImplTest {

    @Autowired
    DepartmentService departmentService;

    @Test
    public void add() {
        this.insert(11011601);
        this.insert(11011602);
        this.insert(11011603);

    }
    public void insert(Integer number) {
        Department department = new Department();
        department.setParentId(1242100166115225601L);
        department.setName("通信工程1601班");
        department.setNumber(number);

        Department addDepartment = departmentService.addDepartment(department);
        System.out.println(addDepartment);
    }

}