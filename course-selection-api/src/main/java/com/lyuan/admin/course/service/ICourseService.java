package com.lyuan.admin.course.service;

import com.lyuan.admin.course.entity.AddCourseToItemDTO;
import com.lyuan.admin.course.entity.Course;
import com.lyuan.admin.course.entity.CourseListDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.common.entity.BaseResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface ICourseService extends IService<Course> {
    
    BaseResponse list(CourseListDTO listDTO);

    BaseResponse get(Integer id);

    BaseResponse delete(Integer id);

    BaseResponse add(Course course);

    BaseResponse update(Course course);

    BaseResponse listByItemId(CourseListDTO listDTO);

    BaseResponse listEnableByItemId(CourseListDTO listDTO);

    BaseResponse batchAddCourseToItem(AddCourseToItemDTO addCourseToItemDTO);

    BaseResponse removeCourseFromItem(Integer id);
}
