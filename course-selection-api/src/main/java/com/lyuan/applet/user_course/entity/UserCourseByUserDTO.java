package com.lyuan.applet.user_course.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/27 22:29
 */
@Data
@Accessors(chain = true)
public class UserCourseByUserDTO {

    private Integer userId;

    private Integer status;

    private Integer limit;
}
