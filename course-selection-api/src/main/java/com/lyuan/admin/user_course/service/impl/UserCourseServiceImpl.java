package com.lyuan.admin.user_course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyuan.admin.user_course.entity.*;
import com.lyuan.admin.user_course.mapper.UserCourseMapper;
import com.lyuan.admin.user_course.service.IUserCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.ListResponse;
import com.lyuan.common.util.InfoConvertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2020-03-14
 */
@Service
public class UserCourseServiceImpl extends ServiceImpl<UserCourseMapper, UserCourse> implements IUserCourseService {

    @Resource
    UserCourseMapper userCourseMapper;

    @Override
    public ListResponse listUsersByCourse(ListUsersByCourseDTO listUsersByCourseDTO) {
        List<UserCourseVO> list = userCourseMapper.listUsersByCourse(listUsersByCourseDTO);
        list.forEach(userCourse->userCourse.setStatusInfo(InfoConvertUtil.selectStatusConvert(userCourse.getStatus())));
        Integer total = userCourseMapper.countUsersByCourse(listUsersByCourseDTO);
        int pageCount = (int)Math.ceil((double)total/listUsersByCourseDTO.getPageSize());
        return ResponseEnum.SUCCESS.responseList(list,pageCount,total);
    }

    @Override
    public void setSelectStatusByCourse(Integer courseId) {
        int count = userCourseMapper.selectCount(new QueryWrapper<UserCourse>().eq("course_id",courseId));
        //设置成功选课状态
        userCourseMapper.setSelectSuccessStatusByCourse(
                new UserCourseSetStatusDTO().setCourseId(courseId).setCount(count));
        //设置失败选课状态
        userCourseMapper.setSelectFailStatusByCourse(courseId);
        //返还失败选课的学生的积分
        userCourseMapper.returnScoreToFailSelectUser(courseId);
    }
}
