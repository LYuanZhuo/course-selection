package com.lyuan.common.entity;

import lombok.Data;

/**
 * @author Zhuolinbo
 * @description 基础响应封装实体
 * @date 2019/9/2 16:57
 */
@Data

public class BaseResponse {

    private int code;

    private String info;

    private Object data;
}
