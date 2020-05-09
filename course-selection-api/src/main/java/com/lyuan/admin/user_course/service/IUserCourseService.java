package com.lyuan.admin.user_course.service;

import com.lyuan.admin.user_course.entity.ListUsersByCourseDTO;
import com.lyuan.admin.user_course.entity.UserCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.common.entity.ListResponse;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
public interface IUserCourseService extends IService<UserCourse> {

    ListResponse listUsersByCourse(ListUsersByCourseDTO listUsersByCourseDTO);


    /**设置
     * @param courseId 课程id
     * @return
     */
    void setSelectStatusByCourse(Integer courseId);

}
