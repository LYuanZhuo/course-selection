package com.lyuan.admin.classroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyuan.admin.classroom.entity.Classroom;
import com.lyuan.admin.classroom.entity.ClassroomListDTO;
import com.lyuan.admin.classroom.entity.ClassroomListEnableDTO;
import com.lyuan.admin.classroom.mapper.ClassroomMapper;
import com.lyuan.admin.classroom.service.IClassroomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListDTO;
import com.lyuan.common.util.ListQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements IClassroomService {

    @Resource
    ClassroomMapper classroomMapper;

    @Override
    public BaseResponse list(ClassroomListDTO listDTO) {
        QueryWrapper<Classroom> wrapper = new QueryWrapper<>();
        if (listDTO.getFloor()>0){
            wrapper.eq("floor",listDTO.getFloor());
        }
        if (!StringUtils.isEmpty(listDTO.getBuilding())) {
            wrapper.eq("building", listDTO.getBuilding());
        }
        ListQueryUtil.orderHandler(wrapper,listDTO);
        if (listDTO.getLeftSize()>0){
            wrapper.ge(true,"size",listDTO.getLeftSize());
        }
        if (listDTO.getRightSize()>0){
            wrapper.lt(true,"size",listDTO.getRightSize());
        }
        Page page = new Page<>(listDTO.getCurrentPage(), listDTO.getPageSize());
        IPage iPage = classroomMapper.selectPage(page, wrapper);
        return ResponseEnum.SUCCESS.responseList(iPage.getRecords(), iPage.getPages(), iPage.getTotal());
    }

    @Override
    public BaseResponse get(Integer id) {
        Classroom classroom = getById(id);
        if (classroom == null) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(classroom);
    }

    @Override
    public BaseResponse delete(Integer id) {
        if (!removeById(id)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse add(Classroom classroom) {
        if (!save(classroom)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse update(Classroom classroom) {
        if (!updateById(classroom)) {
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse listEnable(ClassroomListEnableDTO classroomListEnableDTO) {
        long total = classroomMapper.countListEnable(classroomListEnableDTO);
        long pageCount = (long)Math.ceil((double)total/classroomListEnableDTO.getPageSize());
        return ResponseEnum.SUCCESS.responseList(classroomMapper.listEnable(classroomListEnableDTO),pageCount,total);
    }
}
