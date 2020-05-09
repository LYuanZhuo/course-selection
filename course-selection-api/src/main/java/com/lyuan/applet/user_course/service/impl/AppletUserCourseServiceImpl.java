package com.lyuan.applet.user_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.basecourse.entity.Basecourse;
import com.lyuan.admin.basecourse.mapper.BasecourseMapper;
import com.lyuan.applet.course.entity.*;
import com.lyuan.applet.course.mapper.AppletCourseMapper;
import com.lyuan.applet.item.entity.Item;
import com.lyuan.applet.item.mapper.AppletItemMapper;
import com.lyuan.applet.user.entity.User;
import com.lyuan.applet.user.entity.UserScoreDTO;
import com.lyuan.applet.user.mapper.AppletUserMapper;
import com.lyuan.applet.user_course.entity.*;
import com.lyuan.applet.user_course.mapper.AppletUserCourseMapper;
import com.lyuan.applet.user_course.service.IAppletUserCourseService;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.InfoConvertUtil;
import com.lyuan.common.util.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
@Service
public class AppletUserCourseServiceImpl extends ServiceImpl<AppletUserCourseMapper, UserCourse> implements IAppletUserCourseService {

    @Resource
    private AppletCourseMapper appletCourseMapper;
    @Resource
    private BasecourseMapper basecourseMapper;
    @Resource
    private AppletItemMapper appletItemMapper;
    @Resource
    private AppletUserMapper appletUserMapper;
    @Resource
    private AppletUserCourseMapper appletUserCourseMapper;

    @Override
    @Transactional
    public BaseResponse select(UserCourse userCourse, HttpServletRequest request) {
        Course course = appletCourseMapper.selectById(userCourse.getCourseId());
        Item item = appletItemMapper.selectById(course.getItemId());
        //查询是否在选课时间内
        Date now = new Date();
        if (!(now.after(item.getStartTime())&&now.before(item.getEndTime()))){
            return ResponseEnum.SELECT_TIME_ERROR.response();
        }
        //获取用户id
        Integer userId = JWTUtil.decode(request.getHeader("token"));
        //查询此课程是否会和其他课程冲突
        CourseUserCheckConflictDTO dto = new CourseUserCheckConflictDTO();
        dto.setUserId(userId);
        BeanUtils.copyProperties(course,dto);
        List<Course> list = appletCourseMapper.checkConflict(dto);
        //有冲突
        if (list.size()>0) {
            Course conflictCourse = list.get(0);
            //查询封装冲突课程详情
            Basecourse basecourse = basecourseMapper.selectById(conflictCourse.getBasecourseId());
            ConflictCourseVO conflictCourseVO = new ConflictCourseVO();
            conflictCourseVO.setCourseName(basecourse.getName());
            conflictCourseVO.setCourseDetail(InfoConvertUtil.getCourseTimeDetail(conflictCourse));
            return ResponseEnum.SELECT_CONFIG.response(conflictCourseVO);
        }
        //查询是否超过最大选课量
        int userSelectCount = appletCourseMapper.countUserSelectInItem(new UserSelectInItemDTO(userId,item.getId()));
        if (userSelectCount>=item.getMaxSelectNum()){
            return ResponseEnum.SELECT_MAX.response(item.getMaxSelectNum());
        }
        //查询积分是否足够
        User user = appletUserMapper.selectById(userId);
        if (userCourse.getScore()>user.getScore()){
            return ResponseEnum.USER_SCORE_ERROR.response();
        }
        //查询是否已经修过此课程
        List<CourseVO> selectedList = appletUserCourseMapper.listByBasecourseAndUser(new BasecourseAndUserDTO()
                .setUserId(userId)
                .setBasecourseId(course.getBasecourseId()));
        if (selectedList.size()>0){
            return ResponseEnum.HAD_SELECTED_ERROR.response(selectedList.get(0));
        }
        //更新积分
        if (userCourse.getScore()>0){
            appletUserMapper.updateScore(new UserScoreDTO().setUserId(userId).setScoreChange(-userCourse.getScore()));
        }
       //添加选课记录
        userCourse.setUserId(userId);
        userCourse.setTime(new Date());
        if (!save(userCourse)){
            return ResponseEnum.FAIL.response();
        }
        //更新选课人数
        int selectCount = appletUserCourseMapper.selectCount(new QueryWrapper<UserCourse>().eq("course_id",userCourse.getCourseId()));
        int num = appletCourseMapper.updateById(new Course().setId(userCourse.getCourseId()).setSelectCount(selectCount));
        if (num<=0){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    @Transactional
    public BaseResponse cancelSelect(UserCourse userCourse, HttpServletRequest request) {
        Course course = appletCourseMapper.selectById(userCourse.getCourseId());
        Item item = appletItemMapper.selectById(course.getItemId());
        Date now = new Date();
        if (!(now.after(item.getStartTime())&&now.before(item.getEndTime()))){
            return ResponseEnum.SELECT_TIME_ERROR.response();
        }
        //获取用户id
        Integer userId = JWTUtil.decode(request.getHeader("token"));
        QueryWrapper<UserCourse> wrapper = new QueryWrapper<UserCourse>().eq("user_id",userId).eq("course_id",userCourse.getCourseId());
        UserCourse gotUserCourse = appletUserCourseMapper.selectOne(wrapper);
        appletUserCourseMapper.delete(wrapper);
        //更新积分
        appletUserMapper.updateScore(new UserScoreDTO().setUserId(userId).setScoreChange(gotUserCourse.getScore()));
        //更新选课人数
        int selectCount = appletUserCourseMapper.selectCount(new QueryWrapper<UserCourse>().eq("course_id",userCourse.getCourseId()));
        appletCourseMapper.updateById(new Course().setId(userCourse.getCourseId()).setSelectCount(selectCount));
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse listAllByItemAndUser(Integer itemId, HttpServletRequest request) {
        int userId = JWTUtil.decode(request.getHeader("token"));
        List<UserCourseVO> list =
                appletUserCourseMapper.listAllByItemAndUser(new UserSelectInItemDTO(userId,itemId));
        list.forEach(course->course.setCourseFullName(course.getCourseName()+" ("+course.getTeacherName()+")"));
        if (list==null){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(list);
    }

    @Override
    public BaseResponse listAllByUser(Integer status, Integer limit, HttpServletRequest request) {
        int userId = JWTUtil.decode(request.getHeader("token"));
        List<UserCourseVO> list =
                appletUserCourseMapper.listAllByUser(new UserCourseByUserDTO().setLimit(limit).setStatus(status).setUserId(userId));
        long now = (new Date()).getTime();
        list.forEach(course->{
            course.setCourseFullName(course.getCourseName()+" ("+course.getTeacherName()+")");
            course.setRemainTime(course.getEndTime().getTime()-now);
            course.setSelectResult(InfoConvertUtil.selectStatusConvert(course.getStatus()));
        });
        if (list==null){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(list);
    }

    @Override
    public BaseResponse getClassSchedule(Integer year, String quarter,Integer week,HttpServletRequest request) {
        Integer userId = JWTUtil.decode(request.getHeader("token"));
        List<CourseVO> list = appletUserCourseMapper.getClassSchedule(new ClassScheduleDTO(userId,year,quarter,week));
        Course[][] schedule = new Course[6][7];
        list.forEach(course -> schedule[course.getClassIndex()-1][course.getWeekDay()-1] = course);
        return ResponseEnum.SUCCESS.response(schedule);
    }

}
