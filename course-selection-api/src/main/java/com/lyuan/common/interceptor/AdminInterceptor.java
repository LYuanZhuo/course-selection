package com.lyuan.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lyuan.common.annotation.Interception;
import com.lyuan.common.define.enumDefine.ResponseEnum;
import com.lyuan.common.entity.BaseResponse;
import com.lyuan.common.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/2/5 12:35
 */
@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("管理端拦截器拦截url："+request.getRequestURL());
        /**
         * handler保存了本次请求的controller也就是接口方法的一些信息，如类，方法，参数等
         * 如果是一次静态资源的请求则该handler不应该是HandlerMethod的实现类
         * 判断是否是一个正常的接口，如果是则进行鉴权操作，不是则直接放行
         */
        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Interception interception = handlerMethod.getMethodAnnotation(Interception.class);
        //检查拦截注解
        if (interception!=null&&!interception.value()){
            return true;
        }
        String token = request.getHeader("token");
        //检查token是否合法
        if (StringUtils.isEmpty(token)||JWTUtil.decode(token)==null){
            BaseResponse baseResponse = ResponseEnum.TOKEN_INVALID.response();
            setResponse(response,baseResponse);
            return false;
        }
        //检查token是否过期
        if (JWTUtil.checkExpire(token)){
            BaseResponse baseResponse = ResponseEnum.TOKEN_EXPIRE.response();
            setResponse(response,baseResponse);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * @Description: 装载response
     * Params:
     * Return:
     * @Author Zhuo Linbo
     * @Date 2020/2/6 15:05
     */
    private void setResponse(HttpServletResponse response,Object object) throws IOException {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(jsonObject);
    }

}
