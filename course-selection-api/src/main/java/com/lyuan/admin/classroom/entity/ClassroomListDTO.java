package com.lyuan.admin.classroom.entity;

import com.lyuan.common.entity.BaseListDTO;
import com.lyuan.common.entity.ListDTO;
import lombok.Data;

/**
 * <p>
 *       筛选教室座位大小
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Data
public class ClassroomListDTO extends BaseListDTO {

    private String building;

    private Integer floor;

    private Integer leftSize;

    private Integer rightSize;
}
