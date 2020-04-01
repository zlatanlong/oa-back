package cn.lcl.config.shrio;

import cn.lcl.service.AuthzService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    AuthzService authzService;

    // 3. ShiroFilterFactoryBean
    // 请求在这里拦截
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        // 关联Manager
        shiroFilter.setSecurityManager(defaultWebSecurityManager);

        /*
         * anon: 所有人
         * authc: 必须认证
         * user: 记住我
         * perms[]: 拥有所有指定的权限
         * roles[]: 拥有所有指定角色
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/*", "authc");
        // api - 角色
        filterChainDefinitionMap.putAll(authzService.getRoleFilterMap());

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);

        // 均用自定义filter接管
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("authc", new CORSAuthenticationFilter());
        filterMap.put("roles", new MyRolesAuthorizationFilter());
        shiroFilter.setFilters(filterMap);

        return shiroFilter;
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
