package com.lyuan.admin.admin.controller;


import com.lyuan.admin.admin.entity.Admin;
import com.lyuan.admin.admin.service.IAdminService;
import com.lyuan.common.annotation.Interception;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService iAdminService;

    @Interception
    @PostMapping("/login")
    public BaseResponse login(@RequestBody Admin admin){
        log.info("管理员登录{}",admin);
        return iAdminService.login(admin);
    }

}
