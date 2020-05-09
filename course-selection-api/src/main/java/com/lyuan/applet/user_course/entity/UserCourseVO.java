package com.lyuan.applet.user_course.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/17 13:34
 */
@Data
public class UserCourseVO extends UserCourse {

    private String userName;

    private String courseName;

    private String courseFullName;

    private String courseType;

    private String teacherName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private Long remainTime;

    private String selectResult;

    private Integer basecourseId;

    private Integer itemId;
}
