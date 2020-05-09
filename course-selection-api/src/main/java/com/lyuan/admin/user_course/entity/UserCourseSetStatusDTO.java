package com.lyuan.admin.user_course.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/29 12:49
 */
@Data
@Accessors(chain = true)
public class UserCourseSetStatusDTO {

    private Integer courseId;

    private Integer count;
}
