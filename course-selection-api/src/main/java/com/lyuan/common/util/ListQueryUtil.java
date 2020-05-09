package com.lyuan.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyuan.common.entity.BaseListDTO;
import com.lyuan.common.entity.ListDTO;
import org.apache.commons.lang3.StringUtils;

public class ListQueryUtil {

    //条件参数封装
    public static void queryHandler(QueryWrapper wrapper,ListDTO listDTO) {
        if (listDTO.getConditions() != null && listDTO.getConditions().size() > 0) {
            listDTO.getConditions().forEach((key, val) -> wrapper.eq(true,key, val));
        }
        if (listDTO.getSearches() != null && listDTO.getSearches().size() > 0) {
            listDTO.getSearches().forEach((key, val) -> wrapper.like(true,key, val));
        }
    }

    //排序封装
    public static void orderHandler(QueryWrapper wrapper,BaseListDTO listDTO) {
        if (!StringUtils.isEmpty(listDTO.getAscOrDesc()) && !StringUtils.isEmpty(listDTO.getOrderBy())) {
            wrapper.orderBy(true, "asc".equals(listDTO.getAscOrDesc()), listDTO.getOrderBy());
        }
    }
}
