package com.lyuan.applet.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.applet.user.entity.User;
import com.lyuan.applet.user.entity.UserLoginDTO;
import com.lyuan.applet.user.entity.UserUpdatePasswordDTO;
import com.lyuan.common.entity.BaseResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IAppletUserService extends IService<User> {

    BaseResponse login(UserLoginDTO userLoginDTO);

    BaseResponse getUser(HttpServletRequest request);

    BaseResponse updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO,HttpServletRequest request);
}
