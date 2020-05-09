package com.lyuan.admin.setting.service.impl;

import com.lyuan.admin.setting.entity.Setting;
import com.lyuan.admin.setting.mapper.SettingMapper;
import com.lyuan.admin.setting.service.ISettingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-24
 */
@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements ISettingService {

    @Override
    public BaseResponse getSetting() {
        Setting setting = getById(1);
        if (setting==null){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(setting);
    }

    @Override
    public BaseResponse updateSetting(Setting setting) {
        setting.setId(1);
        boolean success = updateById(setting);
        if (!success){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(setting);
    }
}
