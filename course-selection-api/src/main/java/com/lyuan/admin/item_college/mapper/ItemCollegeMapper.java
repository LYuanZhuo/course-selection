package com.lyuan.admin.item_college.mapper;

import com.lyuan.admin.item_college.entity.ItemCollege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.admin.item_college.entity.ItemCollegeUpdateDTO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
public interface ItemCollegeMapper extends BaseMapper<ItemCollege> {

    int updateItemCollege(ItemCollegeUpdateDTO itemCollegeUpdateDTO);
}
