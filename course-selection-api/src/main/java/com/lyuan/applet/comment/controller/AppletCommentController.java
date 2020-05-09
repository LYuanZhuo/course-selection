package com.lyuan.applet.comment.controller;


import com.alibaba.fastjson.JSONObject;
import com.lyuan.admin.comment.entity.CommentListDTO;
import com.lyuan.applet.comment.entity.Comment;
import com.lyuan.applet.comment.service.IAppletCommentService;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LYuan
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/applet/comment")
@Slf4j
public class AppletCommentController {

    @Autowired
    private IAppletCommentService iAppletCommentService;

    @PostMapping("/listByCourse")
    public BaseResponse listByCourse(@RequestBody CommentListDTO commentListDTO){
        log.info("小程序查询课程的评论{}", JSONObject.toJSONString(commentListDTO));
        return iAppletCommentService.listByCourse(commentListDTO);
    }

    @GetMapping("/listAllByUser")
    public BaseResponse listAllByUser(HttpServletRequest request){
        log.info("小程序用户查询自己所有评论{}");
        return iAppletCommentService.listAllByUser(request);
    }

    @PostMapping
    public BaseResponse add(@RequestBody Comment comment,HttpServletRequest request){
        log.info("小程序用户添加评论{}", JSONObject.toJSONString(comment));
        return iAppletCommentService.add(comment,request);
    }
}
