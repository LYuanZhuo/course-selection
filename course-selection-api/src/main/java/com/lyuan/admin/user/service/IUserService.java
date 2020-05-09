package com.lyuan.admin.user.service;

import com.lyuan.admin.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.user.entity.UserListDTO;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IUserService extends IService<User> {

    ListResponse list(UserListDTO userListDTO);

    BaseResponse delete(Integer id);

    BaseResponse add(User user);

    BaseResponse update(User user);

    BaseResponse get(Integer id);
}
