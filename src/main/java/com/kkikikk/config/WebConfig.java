package com.kkikikk.config;

import com.kkikikk.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于配置WebMvc的拦截器等
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 注入登录拦截器，用于拦截未登录的用户请求
     */
    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 添加拦截器配置，指定哪些路径不被拦截
     *
     * @param registry 拦截器注册对象，用于注册拦截器及其排除路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录和注册接口不拦截，允许用户访问这些特定的路径
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");
    }

}
