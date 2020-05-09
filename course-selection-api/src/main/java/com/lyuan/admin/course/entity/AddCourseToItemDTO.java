package com.lyuan.admin.course.entity;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author LYuan
 * @since 2020-03-15
 */
@Data
public class AddCourseToItemDTO {

    private Integer itemId;

    private Integer[] courseIds;

}
