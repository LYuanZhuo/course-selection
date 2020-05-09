package com.lyuan.common.annotation;

import java.lang.annotation.*;

/**
 * @Description: 自定义拦截注解
 * @Author: Zhuo Linbo
 * @Date: 2020/3/20 12:48
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Interception {

    /**
     * @return false：不拦截  true：拦截
     */
    boolean value() default false;
}
