package com.lyuan.applet.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.applet.user.entity.User;
import com.lyuan.applet.user.entity.UserScoreDTO;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface AppletUserMapper extends BaseMapper<User> {

    int updateScore(UserScoreDTO userScoreDTO);
}
