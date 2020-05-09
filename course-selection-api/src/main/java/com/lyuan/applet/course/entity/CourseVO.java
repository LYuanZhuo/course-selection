package com.lyuan.applet.course.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/30 14:56
 */
@Data
public class CourseVO extends Course {

    private String teacherName;

    private String classroomName;

    private String courseName;

    private String courseDetail;

    private String courseTimeDetail;

}
