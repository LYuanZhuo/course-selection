package com.lyuan.admin.comment.mapper;

import com.lyuan.admin.comment.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyuan.admin.comment.entity.CommentListDTO;
import com.lyuan.admin.comment.entity.CommentListVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface CommentMapper extends BaseMapper<Comment> {

    List<CommentListVO> listByCourse(CommentListDTO commentListDTO);

    int countByCourse(CommentListDTO commentListDTO);

}
