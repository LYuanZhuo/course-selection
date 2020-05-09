package com.lyuan.admin.classroom.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.classroom.entity.Classroom;
import com.lyuan.admin.classroom.entity.ClassroomListDTO;
import com.lyuan.admin.classroom.entity.ClassroomListEnableDTO;
import com.lyuan.admin.classroom.service.IClassroomService;
import com.lyuan.common.entity.BaseResponse;
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
@RequestMapping("/classroom")
public class ClassroomController {
    @Autowired
    IClassroomService iClassroomService;

    @PostMapping("/list")
    public BaseResponse list(@RequestBody ClassroomListDTO classroomListDTO) {
        log.info("查询教室列表{}", JSONObject.toJSONString(classroomListDTO));
        return iClassroomService.list(classroomListDTO);
    }

    @PostMapping("/listEnable")
    public BaseResponse listEnable(@RequestBody ClassroomListEnableDTO classroomListEnableDTO) {
        log.info("查询可选教室列表{}", JSONObject.toJSONString(classroomListEnableDTO));
        return iClassroomService.listEnable(classroomListEnableDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("查询教室{}", JSONObject.toJSONString(id));
        return iClassroomService.get(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        log.info("删除教室{}", JSONObject.toJSONString(id));
        return iClassroomService.delete(id);
    }

    @PostMapping("/")
    public BaseResponse add(@RequestBody Classroom classroom) {
        log.info("增添教室{}", JSONObject.toJSONString(classroom));
        return iClassroomService.add(classroom);
    }

    @PutMapping("/")
    public BaseResponse update(@RequestBody Classroom classroom) {
        log.info("修改教室{}", JSONObject.toJSONString(classroom));
        return iClassroomService.update(classroom);
    }
}
