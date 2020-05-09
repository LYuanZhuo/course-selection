package com.lyuan.applet.basecourse.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.basecourse.entity.BasecourseListDTO;
import com.lyuan.admin.basecourse.service.IBasecourseService;
import com.lyuan.applet.basecourse.service.IAppletBasecourseService;
import com.lyuan.common.annotation.Interception;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
@RequestMapping("/applet/basecourse")
public class AppletBasecourseController {

    @Autowired
    IAppletBasecourseService iAppletBasecourseService;

    @Autowired
    IBasecourseService iBasecourseService;

    @Interception
    @PostMapping("/list")
    public BaseResponse list(@RequestBody BasecourseListDTO listDTO) {
        log.info("查询基础课程列表{}", JSONObject.toJSONString(listDTO));
        return iAppletBasecourseService.list(listDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("查询基础课程{}", JSONObject.toJSONString(id));
        return iBasecourseService.get(id);
    }

}
