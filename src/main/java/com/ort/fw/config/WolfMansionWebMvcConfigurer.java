package com.ort.fw.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ort.fw.SetAccessContextInterceptor;
import com.ort.fw.SetUserInfoInterceptor;

public class WolfMansionWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SetAccessContextInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
        registry.addInterceptor(new SetUserInfoInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}
