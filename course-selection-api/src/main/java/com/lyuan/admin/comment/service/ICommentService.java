package com.lyuan.admin.comment.service;

import com.lyuan.admin.comment.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.comment.entity.CommentListDTO;
import com.lyuan.common.entity.BaseResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface ICommentService extends IService<Comment> {

    BaseResponse listByCourse(CommentListDTO commentListDTO);
}
