package com.lyuan.applet.basecourse.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/24 16:12
 */
@Data
@Accessors(chain = true)
public class BasecourseByUserDTO {

    private Integer userId;

    private Integer status;

}
