package com.lyuan.applet.user_course.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/30 14:12
 */
@Data
@AllArgsConstructor
public class ClassScheduleDTO {

    private Integer userId;

    private Integer year;

    private String quarter;

    private Integer week;

}
