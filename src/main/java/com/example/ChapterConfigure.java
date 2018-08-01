package com.example;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2018/8/1.
 */
// 配置静态文件访问地址 https://www.jianshu.com/p/c6ab1081fd5f
@Configuration
public class ChapterConfigure extends WebMvcConfigurerAdapter {

    public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceHandler("lin/resource/**").addResourceLocations("classpath:/static/");
    }
}
