package com.lyuan.admin.item.service;

import com.lyuan.admin.item.entity.Item;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.item.entity.ItemListDTO;
import com.lyuan.common.entity.BaseResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IItemService extends IService<Item> {

    BaseResponse list(ItemListDTO listDTO);

    BaseResponse get(Integer id);

    BaseResponse delete(Integer id);

    BaseResponse add(Item item);

    BaseResponse update(Item item);

    BaseResponse enable(Integer id);


    /**
     * 设置选课项目的所有课程的选课结果
     * @param id
     */
    void setSelectResult(Integer id);
}
