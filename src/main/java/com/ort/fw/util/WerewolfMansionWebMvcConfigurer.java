package com.ort.fw.util;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ort.fw.SetAccessContextInterceptor;

public class WerewolfMansionWebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SetAccessContextInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}
