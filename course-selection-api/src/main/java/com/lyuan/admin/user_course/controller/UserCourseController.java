package com.lyuan.admin.user_course.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.user_course.entity.ListUsersByCourseDTO;
import com.lyuan.admin.user_course.service.IUserCourseService;
import com.lyuan.common.entity.ListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/user_course")
@Slf4j
public class UserCourseController {

    @Autowired
    private IUserCourseService iUserCourseService;

    @PostMapping("/listUsersByCourse")
    public ListResponse listUsersByCourse(@RequestBody ListUsersByCourseDTO listUsersByCourseDTO){
        log.info("根据课程Id获取用户",JSONObject.toJSONString(listUsersByCourseDTO));
        return iUserCourseService.listUsersByCourse(listUsersByCourseDTO);
    }
}
