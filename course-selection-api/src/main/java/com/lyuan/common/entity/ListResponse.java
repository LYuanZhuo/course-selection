package com.lyuan.common.entity;

import lombok.Data;

/**
 * @author Zhuolinbo
 * @description
 * @date 2019/9/2 17:16
 */
@Data
public class ListResponse extends BaseResponse {

    private long total;

    private long pageCount;
}
