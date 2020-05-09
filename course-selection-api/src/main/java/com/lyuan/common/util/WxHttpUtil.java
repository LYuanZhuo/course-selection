package com.lyuan.common.util;

import com.alibaba.fastjson.JSONObject;
import com.lyuan.common.define.WxDefine;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/18 13:55
 */
public class WxHttpUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    public static String getOpenid(String code) {
        String uri = WxDefine.GET_OPENID
                .replace("APPID",WxDefine.APP_ID)
                .replace("SECRET",WxDefine.APP_SECRET)
                .replace("JSCODE",code);
        String res = restTemplate.getForObject(uri,String.class);
        JSONObject jsonObject = JSONObject.parseObject(res);
        return jsonObject.getString("openid");
    }
}
