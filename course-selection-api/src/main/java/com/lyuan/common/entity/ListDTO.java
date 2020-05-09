package com.lyuan.common.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Zhuolinbo
 * @description 通用条件分页查询DTO，满足：多列like搜索，多列equal筛选，分页，单列排序
 * @date 2019/9/2 17:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListDTO extends BaseListDTO{

    private JSONObject searches = null;

    private JSONObject conditions = null;
}
