package com.lyuan.applet.user.controller;


import com.lyuan.applet.user.entity.User;
import com.lyuan.applet.user.entity.UserLoginDTO;
import com.lyuan.applet.user.entity.UserUpdatePasswordDTO;
import com.lyuan.applet.user.service.IAppletUserService;
import com.lyuan.common.annotation.Interception;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Http2;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/applet/user")
@Slf4j
public class AppletUserController {

    @Autowired
    private IAppletUserService iAppletUserService;

    @Interception
    @PostMapping("/login")
    public BaseResponse login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("小程序用户登录{}",userLoginDTO);
        return iAppletUserService.login(userLoginDTO);
    }

    @GetMapping
    public BaseResponse getUser(HttpServletRequest request){
        log.info("获取用户信息 request");
        return iAppletUserService.getUser(request);
    }

    @PostMapping("/updatePassword")
    public BaseResponse updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO,HttpServletRequest request){
        log.info("小程序用户修改密码");
        return iAppletUserService.updatePassword(userUpdatePasswordDTO,request);
    }
}
