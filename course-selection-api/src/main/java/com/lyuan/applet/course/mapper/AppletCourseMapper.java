package com.lyuan.applet.course.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.applet.course.entity.Course;
import com.lyuan.applet.course.entity.CourseUserCheckConflictDTO;
import com.lyuan.applet.course.entity.UserSelectInItemDTO;
import com.lyuan.applet.user_course.entity.UserCourseVO;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface AppletCourseMapper extends BaseMapper<Course> {

    List<Course> checkConflict(CourseUserCheckConflictDTO dto);

    int countUserSelectInItem(UserSelectInItemDTO userSelectInItemDTO);

}
