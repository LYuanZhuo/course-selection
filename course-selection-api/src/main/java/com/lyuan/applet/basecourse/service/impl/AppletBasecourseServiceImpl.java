package com.lyuan.applet.basecourse.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.basecourse.mapper.BasecourseMapper;
import com.lyuan.admin.basecourse.service.IBasecourseService;
import com.lyuan.admin.teacher.entity.Teacher;
import com.lyuan.admin.teacher.mapper.TeacherMapper;
import com.lyuan.applet.basecourse.entity.Basecourse;
import com.lyuan.admin.basecourse.entity.BasecourseListDTO;
import com.lyuan.admin.basecourse.entity.BasecourseListVO;
import com.lyuan.applet.basecourse.mapper.AppletBasecourseMapper;
import com.lyuan.applet.basecourse.service.IAppletBasecourseService;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
public class AppletBasecourseServiceImpl extends ServiceImpl<AppletBasecourseMapper, Basecourse> implements IAppletBasecourseService {
    @Resource
    BasecourseMapper basecourseMapper;

    @Override
    public BaseResponse list(BasecourseListDTO listDTO) {
        Long total;
        List<BasecourseListVO> list;
        if(StringUtils.isEmpty(listDTO.getName())){
            total = basecourseMapper.count(listDTO);
            list = basecourseMapper.list(listDTO);
        }else {
            //搜索直接搜全部
            int courseCount = count();
            listDTO.setPageSize(courseCount);
            total = basecourseMapper.count(listDTO);
            list = basecourseMapper.list(listDTO);
            //name作为关键词筛选老师
            listDTO.setTeacherName(listDTO.getName());
            listDTO.setName("");
            List<BasecourseListVO> listVOS = basecourseMapper.list(listDTO);
            for (BasecourseListVO basecourseListVO:listVOS){
                if (!list.contains(basecourseListVO)){
                    list.add(basecourseListVO);
                    total++;
                }
            }
        }
        int pageCount = (int)Math.ceil((double)total/listDTO.getPageSize());
        if (list!=null&&total!=null){
            return ResponseEnum.SUCCESS.responseList(list,pageCount,total);
        }
        return ResponseEnum.FAIL.response();
    }

}
