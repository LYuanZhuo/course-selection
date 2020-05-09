package com.lyuan.applet.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.applet.item.entity.Item;
import com.lyuan.applet.item.entity.ItemListDTO;
import com.lyuan.common.entity.BaseResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IAppletItemService extends IService<Item> {

    BaseResponse list(ItemListDTO listDTO);

    BaseResponse get(Integer id);

    BaseResponse enterItem(Integer id, HttpServletRequest request);
}
