package com.lyuan.admin.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyuan.admin.admin.entity.Admin;
import com.lyuan.admin.admin.mapper.AdminMapper;
import com.lyuan.admin.admin.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.JWTUtil;
import com.lyuan.common.util.MD5Util;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Resource
    AdminMapper adminMapper;

    @Override
    public BaseResponse login(Admin admin) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        String md5Password = MD5Util.encode(admin.getPassword());
        wrapper.eq("account",admin.getAccount()).eq("password",md5Password);
        Admin certainAdmin = adminMapper.selectOne(wrapper);
        if (certainAdmin==null){
            return ResponseEnum.FAIL_FIND_USER.response();
        }
        String token = JWTUtil.sign(certainAdmin.getId(),7);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",certainAdmin.getName());
        jsonObject.put("token",token);
        return ResponseEnum.SUCCESS.response(jsonObject);
    }
}
