package com.lyuan.applet.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.basecourse.entity.Basecourse;
import com.lyuan.admin.basecourse.mapper.BasecourseMapper;
import com.lyuan.admin.classroom.entity.Classroom;
import com.lyuan.admin.classroom.mapper.ClassroomMapper;
import com.lyuan.admin.course.mapper.CourseMapper;
import com.lyuan.admin.teacher.entity.Teacher;
import com.lyuan.admin.teacher.mapper.TeacherMapper;
import com.lyuan.admin.user_course.mapper.UserCourseMapper;
import com.lyuan.applet.course.entity.Course;
import com.lyuan.admin.course.entity.CourseListDTO;
import com.lyuan.admin.course.entity.CourseListVO;
import com.lyuan.applet.course.entity.UserSelectInItemDTO;
import com.lyuan.applet.course.mapper.AppletCourseMapper;
import com.lyuan.applet.course.service.IAppletCourseService;
import com.lyuan.applet.user.mapper.AppletUserMapper;
import com.lyuan.applet.user_course.entity.UserCourse;
import com.lyuan.applet.user_course.mapper.AppletUserCourseMapper;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.InfoConvertUtil;
import com.lyuan.common.util.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class AppletCourseServiceImpl extends ServiceImpl<AppletCourseMapper, Course> implements IAppletCourseService {
    @Resource
    CourseMapper courseMapper;
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    BasecourseMapper basecourseMapper;
    @Resource
    ClassroomMapper classroomMapper;
    @Resource
    AppletUserCourseMapper appletUserCourseMapper;
    @Resource
    AppletCourseMapper appletCourseMapper;

    @Override
    public BaseResponse list(CourseListDTO listDTO) {
        long total = courseMapper.count(listDTO);
        List<CourseListVO> list = courseMapper.list(listDTO);
        Long pageCount = (long)Math.ceil((double)total/listDTO.getPageSize());
        if (list!=null){
            return ResponseEnum.SUCCESS.responseList(list,pageCount,total);
        }
        return ResponseEnum.FAIL.response();
    }

    @Override
    public BaseResponse get(Integer id) {
        Course course = getById(id);
        Basecourse basecourse = basecourseMapper.selectById(course.getBasecourseId());
        Teacher teacher = teacherMapper.selectById(basecourse.getTeacherId());
        Classroom classroom = classroomMapper.selectById(course.getClassroomId());
        CourseListVO courseListVO = new CourseListVO();
        BeanUtils.copyProperties(course,courseListVO);
        courseListVO.setCourseName(basecourse!=null?basecourse.getName():null);
        courseListVO.setTeacherName(teacher!=null?teacher.getName():null);
        courseListVO.setClassroomName(classroom!=null?classroom.getName():null);
        if (course == null) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(courseListVO);
    }

    @Override
    public BaseResponse listByItemId(CourseListDTO listDTO,HttpServletRequest request) {
        Integer userId = JWTUtil.decode(request.getHeader("token"));
        List<UserCourse> list = appletUserCourseMapper.selectList(
                new QueryWrapper<UserCourse>().eq("user_id",userId));
        Set<Integer> set = new HashSet<>();
        list.forEach(item-> set.add(item.getCourseId()));
        long total = courseMapper.countByItemId(listDTO);
        List<CourseListVO> courseList = courseMapper.listByItemId(listDTO);
        courseList.forEach(course->{
            course.setSelected(set.contains(course.getId()));
            course.setCourseDetail(InfoConvertUtil.getCourseDetail(course));
            course.setCourseTimeDetail(InfoConvertUtil.getCourseTimeDetail(course));
            course.setCourseFullName(course.getCourseName()+" ("+course.getTeacherName()+")");
        });
        Long pageCount = (long)Math.ceil((double)total/listDTO.getPageSize());
        if (courseList!=null){
            return ResponseEnum.SUCCESS.responseList(courseList,pageCount,total);
        }
        return ResponseEnum.FAIL.response();
    }

}
