package com.lyuan.admin.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyuan.admin.user.entity.User;
import com.lyuan.admin.user.entity.UserListDTO;
import com.lyuan.admin.user.mapper.UserMapper;
import com.lyuan.admin.user.entity.User;
import com.lyuan.admin.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListResponse;
import com.lyuan.common.util.ListQueryUtil;
import com.lyuan.common.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;
    
    @Override
    public ListResponse list(UserListDTO userListDTO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(userListDTO.getCollege())){
            wrapper.eq("college",userListDTO.getCollege());
        }
        if (userListDTO.getGrade()!=null&&userListDTO.getGrade()!=0){
            wrapper.eq("grade",userListDTO.getGrade());
        }
        if (!StringUtils.isEmpty(userListDTO.getName())){
            wrapper.like("name",userListDTO.getName());
        }
        ListQueryUtil.orderHandler(wrapper,userListDTO);
        Page page = new Page<>(userListDTO.getCurrentPage(), userListDTO.getPageSize());
        IPage iPage = userMapper.selectPage(page, wrapper);
        return ResponseEnum.SUCCESS.responseList(iPage.getRecords(),iPage.getPages(),iPage.getTotal());
    }

    @Override
    public BaseResponse get(Integer id) {
        User user = getById(id);
        if (user == null) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(user);
    }

    @Override
    public BaseResponse delete(Integer id) {
        if (!removeById(id)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse add(User user) {
        user.setPassword(MD5Util.encode(user.getStudentId()));
        if (!save(user)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse update(User user) {
        if (!updateById(user)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }
}
