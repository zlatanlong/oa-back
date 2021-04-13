package cn.lcl.logging.service;

import cn.lcl.logging.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

/**
 * @Author: zlatanlong
 * @Date: 2021/4/13 16:54
 */
public interface LogService {

    /**
     *
     * @param log log类型
     * @param username 用户名
     * @param browser 浏览器信息
     * @param ip ip信息
     * @param joinPoint 切点
     */
    void save(Log log, String username, String browser, String ip, ProceedingJoinPoint joinPoint);
}
