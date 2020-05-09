package com.lyuan.admin.course.entity;

import com.lyuan.common.entity.BaseListDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2019/11/25 14:11
 */
@Data
@EqualsAndHashCode
public class CourseListDTO extends BaseListDTO {

    private String courseName;

    private String teacherName;

    private String collegeName;

    private String classroomName;

    private String type;

    private Integer year;

    private String quarter;

    private Integer itemId;
}
