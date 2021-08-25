package com.ort.app.fw.interceptor

import com.ort.fw.security.UserInfo
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserInfoInterceptor : HandlerInterceptorAdapter() {

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
        val authentication = SecurityContextHolder.getContext().authentication ?: return
        // TODO UserInfoをkotlinにできたら変更する
        if (authentication.principal is UserInfo) {
            val user = UserInfo::class.java.cast(authentication.principal)
            modelAndView?.addObject("user", user)
        }
    }
}