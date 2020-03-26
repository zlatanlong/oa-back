package cn.lcl.service.impl;

import cn.lcl.pojo.UserRoleDept;
import cn.lcl.service.GreatRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GreatRoleServiceImplTest {

    @Autowired
    GreatRoleService greatRoleService;

    @Test
    public void testFind() {
        UserRoleDept urd = new UserRoleDept();
        urd.setId(1242317046222438401L);

        List<Long> managedUser = greatRoleService.findManagedUser(urd);

        boolean ifContains = managedUser.contains(1242368386592149506L);
        System.out.println(ifContains);

    }
}