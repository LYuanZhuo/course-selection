package com.lyuan.admin.teacher.service;

import com.lyuan.admin.teacher.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.teacher.entity.TeacherListDTO;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface ITeacherService extends IService<Teacher> {

    BaseResponse list(TeacherListDTO teacherListDTO);

    BaseResponse delete(Integer id);

    BaseResponse add(MultipartFile file, Teacher teacher);

    BaseResponse update(MultipartFile file, Teacher teacher,String deleteOldImage);

    BaseResponse get(Integer id);
}
