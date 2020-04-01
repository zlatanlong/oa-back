package cn.lcl.service;

import cn.lcl.pojo.result.Result;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 这个接口是核心的权限控制业务
 */
public interface AuthorService {
    /**
     * 找一个用户管理的所有人的id集合
     * @param urdId 登录时候传递的
     * @return 用户管理的所有成员的id
     */
    List<Long> findManagedUser(Long urdId);


    /**
     * 获得 用户-api 的过滤map
     */
    LinkedHashMap<String,String> getRoleFilterMap();

    /**
     * 获得用户的角色
     * @param deptId 根据deptId
     * @return
     */
    String getRoleInDept(Long deptId);

    /**
     * 传递urd id
     * @return
     */
    Result authorByUserRoleDeptId(Long urdId);


    /**
     * 这是一个开发人员专用的
     */
    boolean addPermissionWithRole(String api, String description);
}
