package cn.lcl.service;

import cn.lcl.pojo.result.Result;

import java.util.HashMap;

public interface AuthcService {
    Result loginViaPhone(HashMap<String,Object> map);
}
