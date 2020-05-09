package com.lyuan.admin.course.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.course.entity.AddCourseToItemDTO;
import com.lyuan.admin.course.entity.Course;
import com.lyuan.admin.course.entity.CourseListDTO;
import com.lyuan.admin.course.service.ICourseService;
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
@RequestMapping("/course")
public class CourseController {
    @Autowired
    ICourseService iCourseService;

    @PostMapping("/list")
    public BaseResponse list(@RequestBody CourseListDTO listDTO) {
        log.info("查询课程列表{}", JSONObject.toJSONString(listDTO));
        return iCourseService.list(listDTO);
    }

    @GetMapping("/{id}")
    public BaseResponse get(@PathVariable Integer id) {
        log.info("查询课程{}", JSONObject.toJSONString(id));
        return iCourseService.get(id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse delete(@PathVariable Integer id) {
        log.info("删除课程{}", JSONObject.toJSONString(id));
        return iCourseService.delete(id);
    }

    @PostMapping("/")
    public BaseResponse add(@RequestBody Course course) {
        log.info("增添课程{}", JSONObject.toJSONString(course));
        return iCourseService.add(course);
    }

    @PutMapping("/")
    public BaseResponse update(@RequestBody Course course) {
        log.info("修改课程{}", JSONObject.toJSONString(course));
        return iCourseService.update(course);
    }

    @PostMapping("/listByItemId")
    public BaseResponse listByItemId(@RequestBody CourseListDTO listDTO) {
        log.info("根据项目id查询课程列表{}", JSONObject.toJSONString(listDTO));
        return iCourseService.listByItemId(listDTO);
    }

    @PostMapping("/listEnableByItemId")
    public BaseResponse listEnableByItemId(@RequestBody CourseListDTO listDTO) {
        log.info("查询可选课程列表{}", JSONObject.toJSONString(listDTO));
        return iCourseService.listEnableByItemId(listDTO);
    }

    @PostMapping("/batchAddCourseToItem")
    public BaseResponse batchAddCourseToItem(@RequestBody AddCourseToItemDTO addCourseToItemDTO) {
        log.info("批量设置课程的项目关联{}", JSONObject.toJSONString(addCourseToItemDTO));
        return iCourseService.batchAddCourseToItem(addCourseToItemDTO);
    }

    @PutMapping("/removeCourseFromItem/{id}")
    public BaseResponse removeCourseFromItem(@PathVariable Integer id) {
        log.info("移除课程的项目关联{}", JSONObject.toJSONString(id));
        return iCourseService.removeCourseFromItem(id);
    }
}
