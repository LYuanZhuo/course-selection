package com.lyuan.admin.user_course.mapper;

import com.lyuan.admin.user_course.entity.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
public interface UserCourseMapper extends BaseMapper<UserCourse> {

    List<UserCourseVO> listUsersByCourse(ListUsersByCourseDTO listUsersByCourseDTO);

    Integer countUsersByCourse(ListUsersByCourseDTO listUsersByCourseDTO);

    int setSelectSuccessStatusByCourse(UserCourseSetStatusDTO  userCourseSetStatusDTO);

    int setSelectFailStatusByCourse(Integer courseId);

    int returnScoreToFailSelectUser(Integer courseId);
}
