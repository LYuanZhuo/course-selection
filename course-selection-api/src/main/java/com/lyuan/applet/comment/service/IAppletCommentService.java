package com.lyuan.applet.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyuan.admin.comment.entity.CommentListDTO;
import com.lyuan.applet.comment.entity.Comment;
import com.lyuan.common.entity.BaseResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
public interface IAppletCommentService extends IService<Comment> {

    BaseResponse add(Comment comment, HttpServletRequest request);

    BaseResponse listAllByUser(HttpServletRequest request);

    BaseResponse listByCourse(CommentListDTO commentListDTO);
}
