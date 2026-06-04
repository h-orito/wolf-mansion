package com.ort.app.domain.model.auth

interface PlayerAuthRepository {
    /** ログイン ID (プレイヤー名) で認証用情報を取得。存在しなければ null。 */
    fun findByName(name: String): PlayerAuth?

    /** プレイヤー ID で認証用情報を取得 (refresh 時の access token 再発行用)。存在しなければ null。 */
    fun findById(playerId: Int): PlayerAuth?
}
