package cn.lcl.service;

import cn.lcl.pojo.UserRoleDept;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 这个接口是核心的权限控制业务
 */
public interface AuthorService {
    /**
     * 找一个用户管理的所有人的id集合
     * @param userRoleDept
     * @return
     */
    List<Long> findManagedUser(UserRoleDept userRoleDept);


    LinkedHashMap<String,String> getRoleFilterMap();


    /**
     * 这是一个开发人员专用的
     * @param api
     * @param description
     * @return
     */
    boolean addPermissionWithRole(String api, String description);
}
