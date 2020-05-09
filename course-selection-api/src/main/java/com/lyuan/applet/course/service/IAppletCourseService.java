package com.lyuan.applet.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.applet.course.entity.Course;
import com.lyuan.admin.course.entity.CourseListDTO;
import com.lyuan.common.entity.BaseResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IAppletCourseService extends IService<Course> {
    
    BaseResponse list(CourseListDTO listDTO);

    BaseResponse get(Integer id);

    BaseResponse listByItemId(CourseListDTO listDTO,HttpServletRequest request);

}
