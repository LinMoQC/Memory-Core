package com.linmoblog.server.Config;

import com.linmoblog.server.Interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    @Autowired
    private Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/protected/**")
                .excludePathPatterns("/api/login", "/upload/**");  // 除了login接口以及静态图片路径
    }

    /**
     * 图片虚拟地址映射
     * @param registry
     * 设置该映射之后，外网只能访问本地的upload文件内部的资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + System.getProperty("user.dir")+"\\src\\main\\resources\\static\\upload\\");
    }
}
