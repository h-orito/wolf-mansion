package com.ort.fw.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ort.fw.security.UserInfo;

public class WerewolfMansionUserInfoUtil {

    private WerewolfMansionUserInfoUtil() {
    }

    public static UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        if (authentication.getPrincipal() instanceof UserInfo) {
            return UserInfo.class.cast(authentication.getPrincipal());
        }
        return null;
    }
}
