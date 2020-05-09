package com.lyuan.admin.user_course.entity;

import com.lyuan.common.entity.BaseListDTO;
import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/28 12:55
 */
@Data
public class ListUsersByCourseDTO extends BaseListDTO {

    private Integer courseId;

    private Integer status;
}
