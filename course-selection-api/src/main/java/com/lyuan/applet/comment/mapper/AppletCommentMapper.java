package com.lyuan.applet.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.admin.comment.entity.CommentListVO;
import com.lyuan.applet.comment.entity.Comment;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface AppletCommentMapper extends BaseMapper<Comment> {

    List<CommentListVO> listAllByUser(Integer userId);

}
