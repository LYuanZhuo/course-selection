package com.lyuan.admin.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyuan.admin.teacher.entity.TeacherListDTO;
import com.lyuan.admin.teacher.mapper.TeacherMapper;
import com.lyuan.admin.teacher.entity.Teacher;
import com.lyuan.admin.teacher.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListDTO;
import com.lyuan.common.util.FileUtil;
import com.lyuan.common.util.ListQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

    @Resource
    TeacherMapper teacherMapper;
    @Resource
    FileUtil fileUtil;

    @Override
    public BaseResponse list(TeacherListDTO listDTO) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(listDTO.getCollege())){
            wrapper.eq("college",listDTO.getCollege());
        }
        if (!StringUtils.isEmpty(listDTO.getName())){
            wrapper.like("name",listDTO.getName());
        }
        ListQueryUtil.orderHandler(wrapper,listDTO);
        Page page = new Page<>(listDTO.getCurrentPage(), listDTO.getPageSize());
        IPage iPage = teacherMapper.selectPage(page, wrapper);
        return ResponseEnum.SUCCESS.responseList(iPage.getRecords(),iPage.getPages(),iPage.getTotal());
    }

    @Override
    public BaseResponse delete(Integer id) {
        Teacher teacher = super.getById(id);
        if (!StringUtils.isEmpty(teacher.getHeadImage())){
            String fileName = teacher.getHeadImage().substring(teacher.getHeadImage().lastIndexOf("/"));
            fileUtil.delete(fileName);
        }
        if (!removeById(id)){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse add(MultipartFile file, Teacher teacher) {
        String imageUrl = fileUtil.saveImage(file);
        if (imageUrl!=null){
            teacher.setHeadImage(imageUrl);
        }
        if (!save(teacher)){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse update(MultipartFile file, Teacher teacher,String deleteOldImage) {
        if ("true".equals(deleteOldImage)||!StringUtils.isEmpty(file.getOriginalFilename())){
            if (!StringUtils.isEmpty(teacher.getHeadImage())){
                int index = teacher.getHeadImage().lastIndexOf("/");
                if (index>=0){
                    String fileName = teacher.getHeadImage().substring(index);
                    fileUtil.delete(fileName);
                }
            }
            teacher.setHeadImage("");
        }
        if (!StringUtils.isEmpty(file.getOriginalFilename())){
            String imageUrl = fileUtil.saveImage(file);
            teacher.setHeadImage(imageUrl);
        }
        if (!updateById(teacher)){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

    @Override
    public BaseResponse get(Integer id) {
        Teacher teacher = getById(id);
        if (teacher==null){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response(teacher);
    }
}
