package cn.lcl.config.shrio;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroConfig {

    // 3. ShiroFilterFactoryBean
    // 请求在这里拦截
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 关联Manager
        factoryBean.setSecurityManager(defaultWebSecurityManager);
        /*
         * anon: 所有人
         * authc: 必须认证
         * user: 记住我
         * perms: 拥有对某个资源的权限
         * roles: 拥有某个角色
         */
        HashMap<String, String> filterMap = new HashMap<>();
        filterMap.put("/user/login", "anon");
        filterMap.put("/user", "authc");
        filterMap.put("/user/active", "authc");
//        filterMap.put("/user/add", "perms[user:add]");
//        filterMap.put("/user/update", "authc");
//        filterMap.put("/user/update", "perms[user:update]");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        // 这里应该返回前端
//        factoryBean.setLoginUrl("/login");
//        factoryBean.setUnauthorizedUrl("/noauth");

        return factoryBean;
    }


    // 2. DafaultWebSecurityManger
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联userRealm
        securityManager.setRealm(userRealm);

        return securityManager;
    }

    // 1. create realm object
    @Bean(name = "userRealm")
    public UserRealm userRealm() {
        return new UserRealm();
    }
}
