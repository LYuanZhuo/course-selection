package com.lyuan.admin.classroom.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/5 15:49
 */
@Data
public class ClassroomListEnableDTO extends ClassroomListDTO {

    private Integer year;

    private String quarter;

    private Integer startWeek;

    private Integer endWeek;

    private Integer weekDay;

    private Integer classIndex;
}
