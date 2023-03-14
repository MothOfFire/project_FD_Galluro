package com.cxs.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//处理跨域问题
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                .allowCredentials(true)//是否发送cookie（允许发送cookie证书）
                .allowedOriginPatterns("*")//允许放行所有的原始域
                .allowedMethods(new String[]{"GET","POST","PUT","DELETE"})//允许放行的方法
                .allowedHeaders("*")//允许放行所有的Header属性
                .exposedHeaders("*");

    }
}
