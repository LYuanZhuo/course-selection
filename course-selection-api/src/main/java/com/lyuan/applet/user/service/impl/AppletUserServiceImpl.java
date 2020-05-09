package com.lyuan.applet.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.applet.user.entity.*;
import com.lyuan.applet.user.mapper.AppletUserMapper;
import com.lyuan.applet.user.service.IAppletUserService;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.JWTUtil;
import com.lyuan.common.util.MD5Util;
import com.lyuan.common.util.WxHttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class AppletUserServiceImpl extends ServiceImpl<AppletUserMapper, User> implements IAppletUserService {

    @Override
    public BaseResponse login(UserLoginDTO userLoginDTO) {
        String password = MD5Util.encode(userLoginDTO.getPassword());
        User user = super.getOne(new QueryWrapper<User>().
                eq("student_id", userLoginDTO.getStudentId()).
                eq("password", password));
        if (user == null) {
            return ResponseEnum.FAIL_FIND_USER.response();
        }
        String openId = WxHttpUtil.getOpenid(userLoginDTO.getCode());
        if (!StringUtils.isEmpty(openId) && !openId.equals(user.getOpenId())) {
            update(new UpdateWrapper<User>().eq("id", user.getId()).set("open_id", openId));
        }
        if (!StringUtils.isEmpty(userLoginDTO.getAvatarUrl())&&!userLoginDTO.getAvatarUrl().equals(user.getAvatarUrl())){
            update(new UpdateWrapper<User>().eq("id", user.getId()).set("avatar_url", userLoginDTO.getAvatarUrl()));
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUser(new UserVO());
        BeanUtils.copyProperties(user, userLoginVO.getUser());
        userLoginVO.setToken(JWTUtil.sign(user.getId(),14));
        return ResponseEnum.SUCCESS.response(userLoginVO);
    }

    @Override
    public BaseResponse getUser(HttpServletRequest request) {
        User user = getById(JWTUtil.decode(request.getHeader("token")));
        if (user==null){
            return ResponseEnum.FAIL.response();
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return ResponseEnum.SUCCESS.response(userVO);
    }

    @Override
    public BaseResponse updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO, HttpServletRequest request) {
        if (!userUpdatePasswordDTO.getFirstNewPassword().equals(userUpdatePasswordDTO.getSecondNewPassword())){
            return ResponseEnum.PASSWORD_NOT_SAME.response();
        }
        Integer userId = JWTUtil.decode(request.getHeader("token"));
        User user = getOne(new QueryWrapper<User>().eq("id",userId).
                eq("password",MD5Util.encode(userUpdatePasswordDTO.getOldPassword())));
        if (user==null){
            return ResponseEnum.PASSWORD_ERROR.response();
        }
        User updateUser = new User();
        updateUser.setPassword(MD5Util.encode(userUpdatePasswordDTO.getSecondNewPassword()));
        updateUser.setId(userId);
        if (!updateById(updateUser)){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }
}
