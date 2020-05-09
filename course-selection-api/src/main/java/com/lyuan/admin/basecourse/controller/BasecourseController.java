package com.lyuan.admin.basecourse.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.basecourse.entity.Basecourse;
import com.lyuan.admin.basecourse.entity.BasecourseListDTO;
import com.lyuan.admin.basecourse.service.IBasecourseService;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/basecourse")
public class BasecourseController {

    @Autowired
    IBasecourseService iBasecourseService;

    @PostMapping("/list")
    public BaseResponse list(@RequestBody BasecourseListDTO listDTO) {
        log.info("查询基础课程列表{}", JSONObject.toJSONString(listDTO));
        return iBasecourseService.list(listDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("查询基础课程{}", JSONObject.toJSONString(id));
        return iBasecourseService.get(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        log.info("删除基础课程{}", JSONObject.toJSONString(id));
        return iBasecourseService.delete(id);
    }

    @PostMapping("/")
    public BaseResponse add(@RequestBody Basecourse basecourse) {
        log.info("增添基础课程{}", JSONObject.toJSONString(basecourse));
        return iBasecourseService.add(basecourse);
    }

    @PutMapping("/")
    public BaseResponse update(@RequestBody Basecourse basecourse) {
        log.info("修改基础课程{}", JSONObject.toJSONString(basecourse));
        return iBasecourseService.update(basecourse);
    }

}
