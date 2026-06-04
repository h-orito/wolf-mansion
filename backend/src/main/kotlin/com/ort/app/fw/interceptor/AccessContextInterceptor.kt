package com.ort.app.fw.interceptor

import com.ort.app.fw.util.WolfMansionUserInfoUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.dbflute.hook.AccessContext
import org.springframework.web.servlet.HandlerInterceptor
import java.time.LocalDateTime

class AccessContextInterceptor : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
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
        ex: Exception?,
    ) {
        AccessContext.clearAccessContextOnThread()
    }
}

/**
 * クライアントの接続元 IP を返す。Cloudflare 配下のため `CF-Connecting-IP` を最優先する。
 * - `CF-Connecting-IP`: Cloudflare が必ず単一の接続元 IP で上書きするヘッダ (詐称不可)
 * - フォールバックは `X-Forwarded-For` の **先頭ホップ** (最左 = オリジナルクライアント)。
 *   生の全文字列を返すと攻撃者が偽プレフィックスを毎回変えて IP キーを変動させられるため先頭のみ採用する
 * - いずれも無ければ `remoteAddr`
 *
 * 前提: origin が Cloudflare 経由のリクエストのみ受ける構成であること (直アクセス可だと CF ヘッダも詐称可能)。
 */
fun HttpServletRequest.getIpAddress(): String {
    // Cloudflare が必ず単一の接続元 IP で上書きする CF-Connecting-IP を最優先する
    val cfConnectingIp = this.getHeader("CF-Connecting-IP")
    if (!cfConnectingIp.isNullOrBlank()) return cfConnectingIp.trim()
    // フォールバック: X-Forwarded-For の先頭ホップ (最左 = オリジナルクライアント)。生の全文字列は採らない
    val forwardedFor = this.getHeader("X-Forwarded-For")
    if (!forwardedFor.isNullOrBlank()) return forwardedFor.substringBefore(",").trim()
    return this.remoteAddr
}

fun HttpServletRequest.getRefererQueryString(): String =
    this
        .getHeader("Referer")
        ?.substringAfter("?", "")
        ?.ifBlank {
            null
        }?.let { "?$it" } ?: ""
