package com.lyuan.applet.user.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/19 14:15
 */
@Data
public class UserVO {

    private String name;

    private String account;

    private String college;

    private Integer score;

    private Integer grade;

    private String studentId;

    private String avatarUrl;
}
