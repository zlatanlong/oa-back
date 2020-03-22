package cn.lcl.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DepartmentMapperTest {
    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    public void insertDept() {
        Department department = new Department();
        department.setName("中国石油大学华东");
        department.setNumber(1);
        department.setLevel(0);
        int insert = departmentMapper.insert(department);
        System.out.println("影响个数"+insert);
    }

    @Test
    public void selectDept() {
        Department department = departmentMapper.selectById(1241602676962193410L);
        System.out.println(department);
    }
}