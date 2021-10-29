package com.ort.app.fw.interceptor

import com.ort.app.fw.security.UserInfo
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
        if (authentication.principal is UserInfo) {
            val user = authentication.principal as UserInfo
            modelAndView?.addObject("user", user)
        }
    }
}