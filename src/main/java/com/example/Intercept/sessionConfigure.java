package com.example.Intercept;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/8/1.
 */
//注册拦截器
@Configuration
public class sessionConfigure extends WebMvcConfigurerAdapter {
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(new Intercept()).addPathPatterns("/**");
    }
}
