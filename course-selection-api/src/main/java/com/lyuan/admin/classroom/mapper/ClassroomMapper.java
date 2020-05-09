package com.lyuan.admin.classroom.mapper;

import com.lyuan.admin.classroom.entity.Classroom;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.admin.classroom.entity.ClassroomListEnableDTO;
import com.lyuan.admin.course.entity.Course;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface ClassroomMapper extends BaseMapper<Classroom> {

    List<Classroom> listEnable(ClassroomListEnableDTO classroomListEnableDTO);

    Long countListEnable(ClassroomListEnableDTO classroomListEnableDTO);

    List<Course> find(Course course);
}
