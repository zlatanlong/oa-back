package cn.lcl.service;

import cn.lcl.pojo.Thing;
import cn.lcl.pojo.result.Result;

public interface WxService {

    Result updateUserOpenid(String Code);

    void getWxToken();

    Boolean sendThingNote(String openid, Thing thing);

}
