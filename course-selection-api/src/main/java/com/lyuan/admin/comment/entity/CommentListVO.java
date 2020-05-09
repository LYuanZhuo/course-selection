package com.lyuan.admin.comment.entity;

import com.lyuan.applet.comment.entity.Comment;
import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/24 15:05
 */
@Data
public class CommentListVO extends Comment {

    private String userName;

    private String userAvatarUrl;

    private String courseName;

    private String courseFullName;

    private String teacherName;

}
