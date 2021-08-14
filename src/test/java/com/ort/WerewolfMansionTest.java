package com.ort;

import java.time.LocalDateTime;

import org.dbflute.hook.AccessContext;
import org.junit.Before;

import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WolfMansionDateUtil;
import com.ort.fw.util.WolfMansionUserInfoUtil;

public class WerewolfMansionTest {

    @Before
    public void setUp() {
        // set access context
        setAccessContext();
    }

    private void setAccessContext() {
        if (AccessContext.isExistAccessContextOnThread()) {
            return;
        }
        // [アクセス日時]
        LocalDateTime accessLocalDateTime = WolfMansionDateUtil.currentLocalDateTime();

        // [アクセスユーザ]
        UserInfo userInfo = WolfMansionUserInfoUtil.getUserInfo();
        String accessUser = userInfo == null ? "not login user" : userInfo.getUsername();

        AccessContext context = new AccessContext();
        context.setAccessLocalDateTime(accessLocalDateTime);
        context.setAccessUser(accessUser);
        AccessContext.setAccessContextOnThread(context);
    }
}
