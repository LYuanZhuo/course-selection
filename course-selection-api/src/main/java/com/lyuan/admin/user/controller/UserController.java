package com.lyuan.admin.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.user.entity.User;
import com.lyuan.admin.user.entity.UserListDTO;
import com.lyuan.admin.user.service.IUserService;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    
    @Autowired
    private IUserService iUserService;
    
    @PostMapping("/list")
    public ListResponse list(@RequestBody UserListDTO userListDTO){
        log.info("获取用户列表{}", JSONObject.toJSONString(userListDTO));
        return iUserService.list(userListDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("id获取用户{}", JSONObject.toJSONString(id));
        return iUserService.get(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        log.info("删除用户{}", JSONObject.toJSONString(id));
        return iUserService.delete(id);
    }

    @PostMapping("/")
    public BaseResponse add(@RequestBody User user) {
        log.info("增添用户{}", JSONObject.toJSONString(user));
        return iUserService.add(user);
    }

    @PutMapping("/")
    public BaseResponse update(@RequestBody User user) {
        log.info("修改用户{}", JSONObject.toJSONString(user));
        return iUserService.update(user);
    }

}
