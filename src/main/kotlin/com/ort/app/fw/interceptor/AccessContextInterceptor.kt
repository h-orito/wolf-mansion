package com.ort.app.fw.interceptor

import com.ort.fw.util.WolfMansionDateUtil
import com.ort.fw.util.WolfMansionUserInfoUtil
import org.dbflute.hook.AccessContext
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AccessContextInterceptor : HandlerInterceptorAdapter() {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (AccessContext.isExistAccessContextOnThread()) {
            // 既に設定されていたら何もしないで次へ
            // (二度呼び出しされたときのために念のため)
            return true
        }
        // [アクセス日時]
        val accessLocalDateTime = WolfMansionDateUtil.currentLocalDateTime()

        // [アクセスユーザ]
        val userInfo = WolfMansionUserInfoUtil.getUserInfo()
        val accessUser = if (userInfo == null) "not login user" else userInfo.username
        val context = AccessContext()
        context.accessLocalDateTime = accessLocalDateTime
        context.accessUser = accessUser
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