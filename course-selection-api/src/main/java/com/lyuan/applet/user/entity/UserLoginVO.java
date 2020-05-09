package com.lyuan.applet.user.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/18 14:23
 */
@Data
public class UserLoginVO {

    private UserVO user;

    private String token;
}
