package com.ort.app.fw.interceptor

import com.ort.app.fw.util.WolfMansionUserInfoUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.dbflute.hook.AccessContext
import org.springframework.web.servlet.HandlerInterceptor
import java.time.LocalDateTime

class AccessContextInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (AccessContext.isExistAccessContextOnThread()) {
            // 既に設定されていたら何もしないで次へ
            // (二度呼び出しされたときのために念のため)
            return true
        }
        // [アクセス日時]
        val accessLocalDateTime = LocalDateTime.now()

        // [アクセスユーザ]
        val userInfo = WolfMansionUserInfoUtil.getUserInfo()
        val accessUser = userInfo?.username ?: "not login user"
        val context = AccessContext()
        context.accessLocalDateTime = accessLocalDateTime
        context.accessUser = "$accessUser: ${request.getIpAddress()}"
        AccessContext.setAccessContextOnThread(context)

        // Handlerメソッドを呼び出す場合はtrueを返却する
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        AccessContext.clearAccessContextOnThread()
    }
}

fun HttpServletRequest.getIpAddress(): String {
    val xForwardedFor = this.getHeader("X-Forwarded-For")
    return if (xForwardedFor.isNullOrEmpty()) this.remoteAddr
    else xForwardedFor
}

fun HttpServletRequest.getRefererQueryString(): String {
    return this.getHeader("Referer")?.substringAfter("?", "")?.ifBlank { null }?.let { "?$it" } ?: ""
}