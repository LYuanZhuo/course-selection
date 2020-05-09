package com.lyuan.applet.course.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/4/2 14:35
 */
@Data
@Accessors(chain = true)
public class BasecourseAndUserDTO {

    private Integer userId;

    private Integer basecourseId;
}
