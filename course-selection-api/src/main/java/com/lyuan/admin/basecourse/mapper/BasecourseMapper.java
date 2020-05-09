package com.lyuan.admin.basecourse.mapper;

import com.lyuan.admin.basecourse.entity.Basecourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.admin.basecourse.entity.BasecourseListDTO;
import com.lyuan.admin.basecourse.entity.BasecourseListVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface BasecourseMapper extends BaseMapper<Basecourse> {

    List<BasecourseListVO> list(BasecourseListDTO listDTO);

    Long count(BasecourseListDTO listDTO);
}
