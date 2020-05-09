package com.lyuan.admin.setting.controller;


import com.lyuan.admin.setting.entity.Setting;
import com.lyuan.admin.setting.service.ISettingService;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2020-03-24
 */
@RestController
@RequestMapping("/setting")
@Slf4j
public class SettingController {

    @Autowired
    private ISettingService iSettingService;

    @GetMapping
    public BaseResponse getSetting(){
        log.info("获取设置");
        return iSettingService.getSetting();
    }

    @PutMapping
    public BaseResponse updateSetting(@RequestBody Setting setting){
        log.info("更新设置");
        return iSettingService.updateSetting(setting);
    }
}
