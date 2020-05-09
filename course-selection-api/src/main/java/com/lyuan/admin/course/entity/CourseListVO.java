package com.lyuan.admin.course.entity;

import com.lyuan.admin.basecourse.entity.Basecourse;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2019/11/25 14:12
 */
@Data
@EqualsAndHashCode
public class CourseListVO extends Course{

    private String teacherName;

    private String classroomName;

    private String courseName;

    private String courseFullName;

    private String collegeName;

    private String itemName;

    private String itemEndTime;

    private String type;

    private Boolean selected;

    private String courseDetail;

    private String courseTimeDetail;

}
