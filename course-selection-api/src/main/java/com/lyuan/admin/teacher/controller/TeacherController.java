package com.lyuan.admin.teacher.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.teacher.entity.Teacher;
import com.lyuan.admin.teacher.entity.TeacherListDTO;
import com.lyuan.admin.teacher.service.ITeacherService;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

    @Autowired
    ITeacherService iTeacherService;

    @PostMapping("/list")
    public BaseResponse list(@RequestBody TeacherListDTO teacherListDTO) {
        log.info("查询老师列表{}", JSONObject.toJSONString(teacherListDTO));
        return iTeacherService.list(teacherListDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("查询老师{}", JSONObject.toJSONString(id));
        return iTeacherService.get(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        log.info("删除老师{}", JSONObject.toJSONString(id));
        return iTeacherService.delete(id);
    }

    @PostMapping("/")
    public BaseResponse add(Teacher teacher, @RequestParam("file") MultipartFile file) {
        log.info("增添老师{}", JSONObject.toJSONString(teacher));
        return iTeacherService.add(file, teacher);
    }

    @PutMapping("/")
    public BaseResponse update(Teacher teacher, @RequestParam("file") MultipartFile file, String deleteOldImage) {
        log.info("修改老师{}", JSONObject.toJSONString(teacher));
        return iTeacherService.update(file, teacher, deleteOldImage);
    }

}
