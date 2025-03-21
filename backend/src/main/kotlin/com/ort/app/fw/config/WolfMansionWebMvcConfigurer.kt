package com.ort.app.fw.config

import com.ort.app.fw.interceptor.AccessContextInterceptor
import com.ort.app.fw.interceptor.UserInfoInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WolfMansionWebMvcConfigurer : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AccessContextInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**")
        registry.addInterceptor(UserInfoInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**")
    }
}