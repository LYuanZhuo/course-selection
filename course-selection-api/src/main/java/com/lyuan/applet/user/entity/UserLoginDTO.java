package com.lyuan.applet.user.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/18 12:42
 */
@Data
public class UserLoginDTO {

    private String studentId;

    private String password;

    //获取openId所需
    private String code;

    private String avatarUrl;
}
