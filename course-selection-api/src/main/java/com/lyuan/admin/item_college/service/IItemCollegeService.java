package com.lyuan.admin.item_college.service;

import com.lyuan.admin.item_college.entity.ItemCollege;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.item_college.entity.ItemCollegeUpdateDTO;
import com.lyuan.common.entity.BaseResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
public interface IItemCollegeService extends IService<ItemCollege> {

    BaseResponse updateItemCollege(ItemCollegeUpdateDTO itemCollegeUpdateDTO);

    BaseResponse listByItemId(Integer itemId);
}
