package com.lyuan.common.config;

import com.lyuan.common.interceptor.AdminInterceptor;
import com.lyuan.common.interceptor.AppletInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/2/5 12:42
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/**").excludePathPatterns("/applet/**","/static/**","/error");
        registry.addInterceptor(appletInterceptor()).addPathPatterns("/applet/**").excludePathPatterns("/static/**","/error");
    }

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor();
    }

    @Bean
    public AppletInterceptor appletInterceptor() {
        return new AppletInterceptor();
    }

    /**
     * @description 配置静态资源不拦截
     * (yml文件已经配置，此处可以省略)
     */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:F:/project_resource/course-selection/");
    }*/

}
