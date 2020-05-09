package com.lyuan.applet.basecourse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.applet.basecourse.entity.Basecourse;
import com.lyuan.admin.basecourse.entity.BasecourseListDTO;
import com.lyuan.common.entity.BaseResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IAppletBasecourseService extends IService<Basecourse> {

    BaseResponse list(BasecourseListDTO listDTO);

}
