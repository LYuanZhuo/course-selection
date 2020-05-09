package com.lyuan.common.define.enumDefine;

import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.entity.ListResponse;

/**
 * @author Zhuolinbo
 * @description
 * @date 2019/9/2 16:36
 */
public enum ResponseEnum {

    ERROR(-1,"系统异常！"),
    SUCCESS(0,"请求成功"),
    FAIL(-1,"请求失败"),

    FAIL_FIND_USER(100,"账号或密码错误！"),
    TOKEN_INVALID(101,"token不合法！"),
    TOKEN_EXPIRE(102,"登录超时，请重新登录！"),

    //course
    WEEK_ERROR(200,"结束周必须大于起始周"),
    CLASSROOM_ILLEGAL(201,"教室已被抢占"),
    COURSE_EXIST(202,"该学期已存在该课程"),

    //classroom
    CLASSROOM_NOT_EXIST(300,"教室不存在"),

    //baseCourse
    BASECOURSE_NOT_EXIST(400,"基础课程不存在"),

    //item
    ITEM_NOT_EXIST(500,"选课项目不存在"),
    ITEM_ENABLE_FAIL(502,"启用失败"),
    ITEM_TIME_ERROR(503,"选课结束时间必须大于开始时间"),
    ITEM_ENTER_ERROR(504,"你无权进入此选课"),

    //course_user
    SELECT_CONFIG(600,"选课冲突"),
    SELECT_MAX(601,"选课数量已达上限"),
    SELECT_TIME_ERROR(602,"现在不是选课时间"),
    HAD_SELECTED_ERROR(603,"你已经修过此课程"),

    //user
    USER_SCORE_ERROR(700,"积分不足"),
    PASSWORD_NOT_SAME(701,"两次输入密码不一致！"),
    PASSWORD_ERROR(702,"原密码验证失败！"),

    //comment
    COMMENT_TIME_ERROR(800,"现在不是评教时间"),
    COMMENT_NOT_IN_COURSE_ERROR(801,"您不是此课程学生，无权评教"),
    COMMENT_COMMENT_EXIST_ERROR(801,"不能重复评教")


    ;
    ResponseEnum(Integer code,String info) {
        this.code = code;
        this.info = info;
    }
    //响应码
    private Integer code;
    //响应信息
    private String info;

    /**
     * @description: 基础响应(带数据)
     * @author: zhuolinbo
     * @time: 2019/9/2 17:19
     * @param:
     * @return:
     */
    public BaseResponse response(Object data){
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setInfo(info);
        response.setData(data);
        return response;
    }

    /**
     * @description: 基础响应(不带数据）
     * @author: zhuolinbo
     * @time: 2019/9/2 17:20
     * @param:
     * @return:
     */
    public BaseResponse response(){
        BaseResponse response = new BaseResponse();
        response.setCode(code);
        response.setInfo(info);
        return response;
    }

    /**
     * @description: 响应列表查询
     * @author: zhuolinbo
     * @time: 2019/9/2 17:19
     * @param:data:数据 totalPage：总页数 totalNum：总数
     * @return:
     */
    public ListResponse responseList(Object data, long pageCount, long total){
        ListResponse response = new ListResponse();
        response.setCode(code);
        response.setInfo(info);
        response.setData(data);
        response.setPageCount(pageCount);
        response.setTotal(total);
        return response;
    }
}
