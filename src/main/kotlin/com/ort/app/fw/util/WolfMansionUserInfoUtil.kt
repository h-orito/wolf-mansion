package com.ort.app.fw.util

import com.ort.app.fw.security.UserInfo
import org.springframework.security.core.context.SecurityContextHolder

object WolfMansionUserInfoUtil {

    fun getUserInfo(): UserInfo? {
        val authentication = SecurityContextHolder.getContext().authentication ?: return null
        return if (authentication.principal is UserInfo) {
            authentication.principal as UserInfo
        } else null
    }
}