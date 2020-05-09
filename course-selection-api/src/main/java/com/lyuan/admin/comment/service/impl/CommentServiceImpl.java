package com.lyuan.admin.comment.service.impl;

import com.lyuan.admin.comment.entity.CommentListDTO;
import com.lyuan.admin.comment.entity.CommentListVO;
import com.lyuan.admin.comment.mapper.CommentMapper;
import com.lyuan.admin.comment.entity.Comment;
import com.lyuan.admin.comment.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public BaseResponse listByCourse(CommentListDTO commentListDTO) {
        List<CommentListVO> listVOS = commentMapper.listByCourse(commentListDTO);
        listVOS.forEach(comment->{
            if (comment.getAnonymous()){
                comment.setUserName("匿名");
                comment.setUserAvatarUrl(null);
            }
        });
        int total = commentMapper.countByCourse(commentListDTO);
        int pageCount = (int)Math.ceil((double)total/commentListDTO.getPageSize());
        return ResponseEnum.SUCCESS.responseList(listVOS,pageCount,total);
    }
}
