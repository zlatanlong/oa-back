package cn.lcl.mapper;

import cn.lcl.pojo.Department;
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
        department.setParentId(1242085090150871041L);
        department.setName("软件工程");
        department.setNumber(1202L);
        department.setLevel(2);
        int insert = departmentMapper.insert(department);
        System.out.println("影响个数"+insert);
    }

    @Test
    public void selectDept() {
        Department department = departmentMapper.selectById(123L);
        System.out.println(department);
    }
}