package cn.lcl.service.impl;

import cn.lcl.config.wx.WxMappingJackson2HttpMessageConverter;
import cn.lcl.exception.MyException;
import cn.lcl.exception.enums.ResultEnum;
import cn.lcl.mapper.UserMapper;
import cn.lcl.pojo.Thing;
import cn.lcl.pojo.User;
import cn.lcl.pojo.result.Result;
import cn.lcl.pojo.wx.WxOpenidResult;
import cn.lcl.pojo.wx.WxTokenResule;
import cn.lcl.service.WxService;
import cn.lcl.util.AuthcUtil;
import cn.lcl.util.ResultUtil;
import cn.lcl.util.WxAccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Slf4j
public class WxServiceImpl implements WxService {
    @Value("${wx.code2token}")
    private String code2token;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Value("${wx.wx-token}")
    private String wxToken;
    @Value("${wx.template_id}")
    private String template_id;


    @Autowired
    private UserMapper userMapper;

    @Override
    public Result updateUserOpenid(String code) {
        User user = AuthcUtil.getUser();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());

        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("secret", secret);
        map.put("code", code);


        try {
            WxOpenidResult forObject = restTemplate.getForObject(code2token, WxOpenidResult.class, map);
            if (null == forObject || !forObject.valid()) {
                throw new MyException(ResultEnum.USER_SAVE_OPENID_FAILED_IN_WX);
            }
            user.setWxOpenId(forObject.getOpenid());
            int i = userMapper.updateById(user);
            if (i != 1) {
                throw new MyException(ResultEnum.USER_SAVE_OPENID_FAILED_IN_DATABASE);
            }
        } catch (RestClientException e) {
            throw new MyException(ResultEnum.USER_SAVE_OPENID_FAILED_IN_WX);
        }

        return ResultUtil.success(user);

    }

    @Override
    public void getWxToken() {
        RestTemplate restTemplate = new RestTemplate();

        HashMap<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("secret", secret);

        try {
            WxTokenResule forObject = restTemplate.getForObject(wxToken, WxTokenResule.class, map);
            if (null == forObject) {
                throw new MyException(ResultEnum.GET_WX_TOKEN_FAILED);
            } else if (!forObject.valid()) {
                log.error(forObject.getErrmsg());
                throw new MyException(ResultEnum.GET_WX_TOKEN_FAILED);
            }
            WxAccessToken.setAccessToken(forObject.getAccess_token());
        } catch (RestClientException e) {
            throw new MyException(ResultEnum.GET_WX_TOKEN_FAILED);
        }
    }

    @Override
    public Boolean sendThingNote(String openid, Thing thing) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("touser",openid);
        map.put("template_id",template_id);
        map.put("touser",openid);
        map.put("touser",openid);

        return null;
    }
}
