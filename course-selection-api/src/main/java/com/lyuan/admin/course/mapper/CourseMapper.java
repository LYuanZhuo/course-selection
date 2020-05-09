package com.lyuan.admin.course.mapper;

import com.lyuan.admin.course.entity.AddCourseToItemDTO;
import com.lyuan.admin.course.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.admin.course.entity.CourseListDTO;
import com.lyuan.admin.course.entity.CourseListVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface CourseMapper extends BaseMapper<Course> {

    CourseListVO getById(Integer id);

    List<CourseListVO> list(CourseListDTO courseListDTO);

    Long count(CourseListDTO courseListDTO);

    long countByItemId(CourseListDTO listDTO);

    List<CourseListVO> listByItemId(CourseListDTO listDTO);

    List<CourseListVO> listEnableByItemId(CourseListDTO listDTO);

    Long countEnableByItemId(CourseListDTO listDTO);

    int batchAddCourseToItem(AddCourseToItemDTO addCourseToItemDTO);
}
