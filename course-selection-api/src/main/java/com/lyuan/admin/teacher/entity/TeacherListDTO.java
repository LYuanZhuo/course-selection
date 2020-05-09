package com.lyuan.admin.teacher.entity;

import com.lyuan.common.entity.BaseListDTO;
import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/16 16:39
 */
@Data
public class TeacherListDTO extends BaseListDTO {

    private String college;

    private String name;
}
