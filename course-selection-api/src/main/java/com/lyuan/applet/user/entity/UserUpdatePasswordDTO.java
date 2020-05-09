package com.lyuan.applet.user.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/4/2 18:45
 */
@Data
public class UserUpdatePasswordDTO {

    private String oldPassword;

    private String firstNewPassword;

    private String secondNewPassword;
}
