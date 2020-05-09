package com.lyuan.admin.user.entity;

import com.lyuan.common.entity.BaseListDTO;
import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/27 13:28
 */
@Data
public class UserListDTO extends BaseListDTO {

    private String name;

    private String college;

    private Integer grade;
}
