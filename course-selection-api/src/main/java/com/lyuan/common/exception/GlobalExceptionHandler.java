package com.lyuan.common.exception;

import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description: 全局异常处理
 * @Author: Zhuo Linbo
 * @Date: 2020/3/28 21:22
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public BaseResponse exceptionHandler(Exception e){
        log.error("未知异常！  :  ",e);
        return ResponseEnum.ERROR.response();
    }
}
