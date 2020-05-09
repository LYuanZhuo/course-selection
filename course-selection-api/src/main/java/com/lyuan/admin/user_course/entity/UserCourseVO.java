package com.lyuan.admin.user_course.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/17 13:34
 */
@Data
public class UserCourseVO extends UserCourse{

    private String userName;

    private String avatarUrl;

    private String studentId;

    private Integer grade;

    private String college;

    private String statusInfo;

}
