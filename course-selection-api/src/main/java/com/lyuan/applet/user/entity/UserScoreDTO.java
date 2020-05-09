package com.lyuan.applet.user.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/23 12:51
 */
@Data
@Accessors(chain = true)
public class UserScoreDTO {

    private Integer userId;

    private Integer scoreChange;
}
