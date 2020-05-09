package com.lyuan.admin.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyuan.admin.basecourse.entity.Basecourse;
import com.lyuan.admin.basecourse.mapper.BasecourseMapper;
import com.lyuan.admin.classroom.entity.Classroom;
import com.lyuan.admin.classroom.mapper.ClassroomMapper;
import com.lyuan.admin.course.entity.AddCourseToItemDTO;
import com.lyuan.admin.course.entity.Course;
import com.lyuan.admin.course.entity.CourseListDTO;
import com.lyuan.admin.course.entity.CourseListVO;
import com.lyuan.admin.course.mapper.CourseMapper;
import com.lyuan.admin.course.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.teacher.entity.Teacher;
import com.lyuan.admin.teacher.mapper.TeacherMapper;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.InfoConvertUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Resource
    CourseMapper courseMapper;
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    BasecourseMapper basecourseMapper;
    @Resource
    ClassroomMapper classroomMapper;

    @Override
    public BaseResponse list(CourseListDTO listDTO) {
        long total = courseMapper.count(listDTO);
        List<CourseListVO> list = courseMapper.list(listDTO);
        list.forEach(course->course.setCourseTimeDetail(InfoConvertUtil.getCourseTimeDetail(course)));
        Long pageCount = (long)Math.ceil((double)total/listDTO.getPageSize());
        if (list!=null){
            return ResponseEnum.SUCCESS.responseList(list,pageCount,total);
        }
        return ResponseEnum.FAIL.response();
    }

    @Override
    public BaseResponse get(Integer id) {
        CourseListVO course = courseMapper.getById(id);
        course.setCourseTimeDetail(InfoConvertUtil.getCourseTimeDetail(course));
        if (course == null) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(course);
    }

    @Override
    public BaseResponse delete(Integer id) {
        if (!removeById(id)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse add(Course course) {
        if (course.getEndWeek()<course.getStartWeek()){
            return ResponseEnum.WEEK_ERROR.response();
        }
        List<Course> list = list(new QueryWrapper<Course>()
                .eq("year",course.getYear()).eq("quarter",course.getQuarter()).eq("basecourse_id",course.getBasecourseId()));
        if (list.size()>0){
            return ResponseEnum.COURSE_EXIST.response();
        }
        List<Course> courses = classroomMapper.find(course);
        if (!CollectionUtils.isEmpty(courses)){
            return ResponseEnum.CLASSROOM_ILLEGAL.response();
        }
        if (!save(course)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse update(Course course) {
        if (!updateById(course)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse listByItemId(CourseListDTO listDTO) {
        long total = courseMapper.countByItemId(listDTO);
        List<CourseListVO> list = courseMapper.listByItemId(listDTO);
        Long pageCount = (long)Math.ceil((double)total/listDTO.getPageSize());
        if (list!=null){
            return ResponseEnum.SUCCESS.responseList(list,pageCount,total);
        }
        return ResponseEnum.FAIL.response();
    }

    @Override
    public BaseResponse listEnableByItemId(CourseListDTO listDTO) {
        long total = courseMapper.countEnableByItemId(listDTO);
        List<CourseListVO> list = courseMapper.listEnableByItemId(listDTO);
        Long pageCount = (long)Math.ceil((double)total/listDTO.getPageSize());
        if (list!=null){
            return ResponseEnum.SUCCESS.responseList(list,pageCount,total);
        }
        return ResponseEnum.FAIL.response();
    }

    @Override
    public BaseResponse batchAddCourseToItem(AddCourseToItemDTO addCourseToItemDTO) {
        courseMapper.batchAddCourseToItem(addCourseToItemDTO);
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse removeCourseFromItem(Integer id) {
        UpdateWrapper<Course> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("item_id",null);
        if (update(wrapper)){
            return ResponseEnum.SUCCESS.response();
        }
        return ResponseEnum.FAIL.response();
    }
}
