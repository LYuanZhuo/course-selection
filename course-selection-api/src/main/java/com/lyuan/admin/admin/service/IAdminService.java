package com.lyuan.admin.admin.service;

import com.lyuan.admin.admin.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.common.entity.BaseResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IAdminService extends IService<Admin> {

    BaseResponse login(Admin admin);
}
