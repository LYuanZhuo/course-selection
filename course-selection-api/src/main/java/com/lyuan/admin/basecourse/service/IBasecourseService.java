package com.lyuan.admin.basecourse.service;

import com.lyuan.admin.basecourse.entity.Basecourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.basecourse.entity.BasecourseListDTO;
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
public interface IBasecourseService extends IService<Basecourse> {
    BaseResponse list(BasecourseListDTO listDTO);

    BaseResponse get(Integer id);

    BaseResponse delete(Integer id);

    BaseResponse add(Basecourse basecourse);

    BaseResponse update(Basecourse basecourse);
}
