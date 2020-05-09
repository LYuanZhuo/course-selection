package com.lyuan.admin.item.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lyuan.common.entity.BaseListDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2019/11/25 12:09
 */
@Data
@EqualsAndHashCode
public class ItemListDTO extends BaseListDTO {

    private String name;

    private Integer year;

    private String quarter;
}
