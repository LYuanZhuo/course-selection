package com.lyuan.applet.user_course.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.applet.user_course.entity.UserCourse;
import com.lyuan.applet.user_course.service.IAppletUserCourseService;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
@RestController
@RequestMapping("/applet/user_course")
@Slf4j
public class AppletUserCourseController {

    @Autowired
    private IAppletUserCourseService iAppletUserCourseService;

    @PostMapping("/select")
    public BaseResponse select(@RequestBody UserCourse userCourse, HttpServletRequest request){
        log.info("小程序用户选课{}",JSONObject.toJSONString(userCourse));
        return iAppletUserCourseService.select(userCourse,request);
    }

    @PostMapping("/cancelSelect")
    public BaseResponse cancelSelect(@RequestBody UserCourse userCourse, HttpServletRequest request){
        log.info("小程序用户退选课{}",JSONObject.toJSONString(userCourse));
        return iAppletUserCourseService.cancelSelect(userCourse,request);
    }

    @GetMapping("/listAllByItemAndUser")
    public BaseResponse listAllByItemAndUser(Integer itemId, HttpServletRequest request){
        log.info("小程序获取用户某项目所有选课{}",JSONObject.toJSONString(itemId));
        return iAppletUserCourseService.listAllByItemAndUser(itemId,request);
    }

    @GetMapping("/listAllByUser")
    public BaseResponse listAllByUser(Integer status,Integer limit,HttpServletRequest request){
        log.info("小程序获取用户的所有选课{}");
        return iAppletUserCourseService.listAllByUser(status,limit,request);
    }

    @GetMapping("/getClassSchedule")
    public BaseResponse getClassSchedule(Integer year,String quarter,Integer week,HttpServletRequest request){
        log.info("小程序获取用户的课程表{}");
        return iAppletUserCourseService.getClassSchedule(year,quarter,week,request);
    }

}
