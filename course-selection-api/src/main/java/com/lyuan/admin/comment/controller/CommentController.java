package com.lyuan.admin.comment.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.comment.entity.CommentListDTO;
import com.lyuan.admin.comment.service.ICommentService;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService iCommentService;

    @PostMapping("/listByCourse")
    public BaseResponse listByCourse(@RequestBody CommentListDTO commentListDTO){
        log.info("查询课程的评论{}", JSONObject.toJSONString(commentListDTO));
        return iCommentService.listByCourse(commentListDTO);
    }
}
