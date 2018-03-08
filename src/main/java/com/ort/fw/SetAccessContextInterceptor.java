package com.ort.fw;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dbflute.hook.AccessContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionDateUtil;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

public class SetAccessContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (AccessContext.isExistAccessContextOnThread()) {
            // 既に設定されていたら何もしないで次へ
            // (二度呼び出しされたときのために念のため)
            return true;
        }
        // [アクセス日時]
        LocalDateTime accessLocalDateTime = WerewolfMansionDateUtil.currentLocalDateTime();

        // [アクセスユーザ]
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        String accessUser = userInfo == null ? "not login user" : userInfo.getUsername();

        AccessContext context = new AccessContext();
        context.setAccessLocalDateTime(accessLocalDateTime);
        context.setAccessUser(accessUser);
        AccessContext.setAccessContextOnThread(context);

        // Handlerメソッドを呼び出す場合はtrueを返却する
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AccessContext.clearAccessContextOnThread();
    }
}
