package com.lyuan.applet.user_course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.applet.course.entity.BasecourseAndUserDTO;
import com.lyuan.applet.course.entity.Course;
import com.lyuan.applet.course.entity.CourseVO;
import com.lyuan.applet.course.entity.UserSelectInItemDTO;
import com.lyuan.applet.user_course.entity.*;

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
public interface AppletUserCourseMapper extends BaseMapper<UserCourse> {

    List<UserCourseVO> listAllByItemAndUser(UserSelectInItemDTO userSelectInItemDTO);

    List<UserCourseVO> listAllByUser(UserCourseByUserDTO userCourseByUserDTO);

    List<CourseVO > getClassSchedule(ClassScheduleDTO classScheduleDTO);

    List<CourseVO> listByBasecourseAndUser(BasecourseAndUserDTO  basecourseAndUserDTO);

}
