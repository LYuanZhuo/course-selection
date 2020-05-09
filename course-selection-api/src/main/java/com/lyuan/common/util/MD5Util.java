package com.lyuan.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/2/5 14:47
 */
@Slf4j
public class MD5Util {

    /**
     * @Description: 字符串转md5
     * Params:
     * Return:
     * @Author Zhuo Linbo
     * @Date 2020/2/5 15:00
     */
    public static String encode(String password) {
        if (StringUtils.isEmpty(password)) {
            return "";
        }
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

}
