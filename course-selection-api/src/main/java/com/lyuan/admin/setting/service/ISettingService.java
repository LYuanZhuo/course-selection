package com.lyuan.admin.setting.service;

import com.lyuan.admin.setting.entity.Setting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.common.entity.BaseResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-24
 */
public interface ISettingService extends IService<Setting> {

    BaseResponse getSetting();

    BaseResponse updateSetting(Setting setting);
}
