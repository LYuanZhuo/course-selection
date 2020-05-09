package com.lyuan.admin.classroom.service;

import com.lyuan.admin.classroom.entity.Classroom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.classroom.entity.ClassroomListDTO;
import com.lyuan.admin.classroom.entity.ClassroomListEnableDTO;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IClassroomService extends IService<Classroom> {

    BaseResponse list(ClassroomListDTO request);

    BaseResponse get(Integer id);

    BaseResponse delete(Integer id);

    BaseResponse add(Classroom classroom);

    BaseResponse update(Classroom classroom);

    BaseResponse listEnable(ClassroomListEnableDTO classroomListEnableDTO);
}
