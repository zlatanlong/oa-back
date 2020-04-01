package cn.lcl.service.impl;

import cn.lcl.service.AuthzService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthzServiceImplTest {

    @Autowired
    AuthzService greatRoleService;

    @Test
    public void testFind() {

    }
}