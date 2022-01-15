package com.xdxct.config.webmvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 姜瑜欣
 * @description: 跨域配置
 * @date 2022/1/1410:38
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${web.load-path}")
    private String loadPath;

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600)
                .allowCredentials(true);
    }

    /**
     * 拦截请求，定位到资源路径
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations(loadPath);
    }
}
