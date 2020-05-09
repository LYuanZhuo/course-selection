package com.lyuan.common.entity;

import lombok.Data;

/**
 * @author Zhuolinbo
 * @description 基础分页查询DTO，满足：分页查询，按单列排序
 * @date 2019/9/2 17:07
 */
@Data
public class BaseListDTO {

    private Integer currentPage = 1;

    private Integer pageSize = 20;

    private String orderBy = "id";

    private String ascOrDesc = "desc";

    private Integer offset = 0;

    public void setCurrentPage(Integer currentPage){
        this.currentPage = currentPage;
        this.offset = (this.currentPage-1)*this.pageSize;
    }

    public void setPageSize(Integer pageSize){
        this.pageSize = pageSize;
        this.offset = (this.currentPage-1)*this.pageSize;
    }
}
