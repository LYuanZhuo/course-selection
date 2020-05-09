package com.lyuan.applet.user_course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.applet.user_course.entity.UserCourse;
import com.lyuan.common.entity.BaseResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
public interface IAppletUserCourseService extends IService<UserCourse> {

    BaseResponse select(UserCourse userCourse, HttpServletRequest request);

    BaseResponse cancelSelect(UserCourse userCourse, HttpServletRequest request);

    BaseResponse listAllByItemAndUser(Integer itemId, HttpServletRequest request);

    BaseResponse listAllByUser(Integer status, Integer limit, HttpServletRequest request);

    BaseResponse getClassSchedule(Integer year, String quarter, Integer week,HttpServletRequest request);

}
