package com.ort.app.fw.interceptor

import com.ort.app.fw.security.UserInfo
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

class UserInfoInterceptor : HandlerInterceptor {

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