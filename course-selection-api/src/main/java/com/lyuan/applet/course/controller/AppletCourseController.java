package com.lyuan.applet.course.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.course.entity.CourseListDTO;
import com.lyuan.applet.course.service.IAppletCourseService;
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
 * @since 2019-11-19
 */
@RestController
@Slf4j
@RequestMapping("/applet/course")
public class AppletCourseController {
    @Autowired
    IAppletCourseService iAppletCourseService;

    @PostMapping("/list")
    public BaseResponse list(@RequestBody CourseListDTO listDTO) {
        log.info("小程序查询课程列表{}", JSONObject.toJSONString(listDTO));
        return iAppletCourseService.list(listDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("小程序查询课程{}", JSONObject.toJSONString(id));
        return iAppletCourseService.get(id);
    }

    @PostMapping("/listByItemId")
    public BaseResponse listByItemId(@RequestBody CourseListDTO listDTO,HttpServletRequest request) {
        log.info("小程序根据项目id查询课程列表{}", JSONObject.toJSONString(listDTO));
        return iAppletCourseService.listByItemId(listDTO,request);
    }

}
