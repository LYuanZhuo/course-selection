package com.lyuan.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.lyuan.common.define.CommonDefine;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhuolinbo
 * @description
 * @date 2019/9/1 18:31
 */
@Configuration
public class DataSourceConfig {

    /**
     * @description: 配置druid数据源
     * @author: zhuolinbo
     * @time: 2019/9/2 10:08
     * @param:
     * @return:
     */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    /**
     * @description: 配置一个管理后台的Servlet
     * @author: zhuolinbo
     * @time: 2019/9/2 10:08
     * @param:
     * @return:
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String,String> initParams = new HashMap<>(16);
        initParams.put("loginUsername", CommonDefine.DRUID_LOGIN_NAME);
        initParams.put("loginPassword", CommonDefine.DRUID_LOGIN_PASSWORD);
        //默认就是允许所有访问
        initParams.put("allow","");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * @description: web监控的filter
     * @author: zhuolinbo
     * @time: 2019/9/2 10:07
     * @param:
     * @return:
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
