package com.lyuan.applet.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyuan.admin.comment.entity.CommentListDTO;
import com.lyuan.admin.comment.entity.CommentListVO;
import com.lyuan.admin.comment.mapper.CommentMapper;
import com.lyuan.admin.setting.entity.Setting;
import com.lyuan.admin.setting.mapper.SettingMapper;
import com.lyuan.applet.basecourse.entity.Basecourse;
import com.lyuan.applet.basecourse.entity.BasecourseByUserDTO;
import com.lyuan.applet.basecourse.mapper.AppletBasecourseMapper;
import com.lyuan.applet.comment.entity.Comment;
import com.lyuan.applet.comment.mapper.AppletCommentMapper;
import com.lyuan.applet.comment.service.IAppletCommentService;
import com.lyuan.applet.user.entity.UserScoreDTO;
import com.lyuan.applet.user.mapper.AppletUserMapper;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.define.UserScoreDefine;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.JWTUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
public class AppletCommentServiceImpl extends ServiceImpl<AppletCommentMapper, Comment> implements IAppletCommentService {

    @Resource
    private AppletCommentMapper appletCommentMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private SettingMapper settingMapper;
    @Resource
    private AppletBasecourseMapper appletBasecourseMapper;
    @Resource
    private AppletUserMapper appletUserMapper;

    @Override
    @Transactional
    public BaseResponse add(Comment comment, HttpServletRequest request) {
        //查询是否是评教时间
        Setting setting = settingMapper.selectById(1);
        Date now = new Date();
        if (!(now.after(setting.getCommentStartTime())&&now.before(setting.getCommentEndTime()))){
            return ResponseEnum.COMMENT_TIME_ERROR.response();
        }
        //查询该学生是否是此课程成功选课的学生
        int userId = JWTUtil.decode(request.getHeader("token"));
        List<Basecourse> list = appletBasecourseMapper.getBasecourseByUser(new BasecourseByUserDTO()
                .setUserId(userId).setStatus(1));
        boolean selected = false;
        for (Basecourse basecourse : list){
            if (basecourse.getId().equals(comment.getBasecourseId())){
                selected = true;
            }
        }
        if (!selected){
            return ResponseEnum.COMMENT_NOT_IN_COURSE_ERROR.response();
        }
        List<Comment> existCommentList = appletCommentMapper.selectList(new QueryWrapper<Comment>().eq("user_id",userId)
                .eq("basecourse_id",comment.getBasecourseId()));
        if (existCommentList.size()>0){
            return ResponseEnum.COMMENT_COMMENT_EXIST_ERROR.response();
        }
        //评教奖励积分
        appletUserMapper.updateScore(new UserScoreDTO().setUserId(userId).setScoreChange(UserScoreDefine.USER_COMMENT_SCORE));
        //更新课程评分
        appletBasecourseMapper.updateBasecourseScore(comment.getBasecourseId());
        comment.setTime(new Date());
        comment.setUserId(userId);
        if (!save(comment)){
            return ResponseEnum.FAIL.response();
        }
        return ResponseEnum.SUCCESS.response();
    }

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

    @Override
    public BaseResponse listAllByUser(HttpServletRequest request) {
        Integer userId = JWTUtil.decode(request.getHeader("token"));
        List<CommentListVO> listVOS = appletCommentMapper.listAllByUser(userId);
        listVOS.forEach(comment-> {
            comment.setCourseFullName(comment.getCourseName()+" ("+comment.getTeacherName()+")");
        });
        return ResponseEnum.SUCCESS.response(listVOS);
    }
}
