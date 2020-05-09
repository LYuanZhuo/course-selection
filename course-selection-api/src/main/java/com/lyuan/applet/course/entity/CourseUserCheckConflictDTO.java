package com.lyuan.applet.course.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/21 14:58
 */
@Data
public class CourseUserCheckConflictDTO extends Course {

    private Integer userId;
}
