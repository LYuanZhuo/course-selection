package com.lyuan.admin.basecourse.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.basecourse.entity.Basecourse;
import com.lyuan.admin.basecourse.entity.BasecourseListDTO;
import com.lyuan.admin.basecourse.entity.BasecourseListVO;
import com.lyuan.admin.basecourse.mapper.BasecourseMapper;
import com.lyuan.admin.basecourse.service.IBasecourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.teacher.entity.Teacher;
import com.lyuan.admin.teacher.mapper.TeacherMapper;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class BasecourseServiceImpl extends ServiceImpl<BasecourseMapper, Basecourse> implements IBasecourseService {
    @Resource
    BasecourseMapper basecourseMapper;
    @Resource
    TeacherMapper teacherMapper;

    @Override
    public BaseResponse list(BasecourseListDTO listDTO) {
        Long total = basecourseMapper.count(listDTO);
        List<BasecourseListVO> list = basecourseMapper.list(listDTO);
        Long pageCount = total/listDTO.getPageSize()+1;
        if (list!=null&&total!=null){
            return ResponseEnum.SUCCESS.responseList(list,pageCount,total);
        }
        return ResponseEnum.FAIL.response();
    }

    @Override
    public BaseResponse get(Integer id) {
        Basecourse basecourse = getById(id);
        Teacher teacher = teacherMapper.selectById(basecourse.getTeacherId());
        BasecourseListVO basecourseListVO = JSONObject.parseObject(JSONObject.toJSONString(basecourse), BasecourseListVO.class);
        if (teacher!=null){
            basecourseListVO.setTeacherName(teacher.getName());
            basecourseListVO.setTeacherHeadImage(teacher.getHeadImage());
            basecourseListVO.setTeacherCollege(teacher.getCollege());
        }
        if (basecourse == null) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(basecourseListVO);
    }

    @Override
    public BaseResponse delete(Integer id) {
        if (!removeById(id)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse add(Basecourse basecourse) {
        if (!save(basecourse)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse update(Basecourse basecourse) {
        if (!updateById(basecourse)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }
}
