package com.ort.app.fw.exception

/**
 * レート制限超過 (ログイン試行過多 / 連続登録防止 等)。REST API では 429 にマップする。
 * 認証失敗と同様、ユーザー存在の有無は区別しない文言にすること。
 */
class WolfMansionTooManyRequestsException(
    message: String,
) : RuntimeException(message)
