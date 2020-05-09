package com.lyuan.admin.basecourse.entity;

import com.lyuan.common.entity.BaseListDTO;
import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2019/11/23 12:14
 */
@Data
public class BasecourseListDTO extends BaseListDTO {

    private String name;

    private String teacherName;

    private String college;

    private String type;

}
