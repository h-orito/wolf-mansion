package com.ort.app.fw.exception

/**
 * 認証失敗 (資格情報誤り / refresh token 無効 等)。REST API では 401 にマップする。
 * ユーザー存在の有無は区別しない文言にすること (列挙を助長しない)。
 */
class WolfMansionAuthException(
    message: String,
) : RuntimeException(message)
