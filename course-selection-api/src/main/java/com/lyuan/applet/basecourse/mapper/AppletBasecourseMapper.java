package com.lyuan.applet.basecourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.applet.basecourse.entity.Basecourse;
import com.lyuan.applet.basecourse.entity.BasecourseByUserDTO;
import com.lyuan.applet.basecourse.entity.BasecourseListDTO;
import com.lyuan.applet.basecourse.entity.BasecourseListVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface AppletBasecourseMapper extends BaseMapper<Basecourse> {

    List<Basecourse> getBasecourseByUser(BasecourseByUserDTO basecourseByUserDTO);

    boolean updateBasecourseScore(Integer id);

}
