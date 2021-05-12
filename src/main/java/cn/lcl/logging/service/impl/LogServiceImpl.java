package cn.lcl.logging.service.impl;

import cn.hutool.json.JSONUtil;
import cn.lcl.logging.entity.Log;
import cn.lcl.logging.mapper.LogMapper;
import cn.lcl.logging.service.LogService;
import cn.lcl.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zlatanlong
 * @Date: 2021/4/13 18:33
 */
@Service
public class LogServiceImpl implements LogService {


    private final LogMapper logMapper;

    public LogServiceImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    @Override
    public void save(Log log, String username, String browser, String ip, ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        cn.lcl.logging.annotation.Log aopLog = method.getAnnotation(cn.lcl.logging.annotation.Log.class);

        String methodName = signature.getName() + "()";
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(getParameter(method, joinPoint.getArgs()));
        log.setBrowser(browser);
        logMapper.insert(log);
    }


    /**
     * 根据方法和传入的参数获取请求参数，
     * 有些方法参数并不是请求发生的参数，
     * 这里只将方法参数数组有RequestBody或RequestParam的检出
     *c
     * @param method 方法
     * @param args 切点参数
     * @return 变量Json串
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        // 方法参数数组
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

}
