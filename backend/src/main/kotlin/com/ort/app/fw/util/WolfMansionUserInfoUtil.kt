package com.ort.app.fw.util

import com.ort.app.fw.security.JwtTokenService
import com.ort.app.fw.security.UserInfo
import com.ort.dbflute.allcommon.CDef
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

object WolfMansionUserInfoUtil {

    fun getUserInfo(): UserInfo? {
        val authentication = SecurityContextHolder.getContext().authentication ?: return null
        val principal = authentication.principal
        return when (principal) {
            is UserInfo -> principal
            is Jwt -> fromJwt(principal)
            else -> null
        }
    }

    private fun fromJwt(jwt: Jwt): UserInfo? {
        val userName = jwt.subject ?: return null
        val authorityCode = jwt.getClaimAsString(JwtTokenService.CLAIM_AUTHORITY)
        val authority = authorityCode?.let { CDef.Authority.codeOf(it) } ?: CDef.Authority.プレイヤー
        return UserInfo().apply {
            setUsername(userName)
            setPassword("") // JWT 認証では DB のパスワードは不要
            setAuthority(authority)
        }
    }
}
