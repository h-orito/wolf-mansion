package com.ort.app.web.model.inner;

import java.time.LocalDateTime;

public class VillageMemberDetailDto {

    /** キャラ名 */
    private String charaName;

    /** 死亡日時 */
    private String deadDay;

    /** 最終接続日時 */
    private LocalDateTime lastAccessDatetime;

    public String getCharaName() {
        return charaName;
    }

    public void setCharaName(String charaName) {
        this.charaName = charaName;
    }

    public String getDeadDay() {
        return deadDay;
    }

    public void setDeadDay(String deadDay) {
        this.deadDay = deadDay;
    }

    public LocalDateTime getLastAccessDatetime() {
        return lastAccessDatetime;
    }

    public void setLastAccessDatetime(LocalDateTime lastAccessDatetime) {
        this.lastAccessDatetime = lastAccessDatetime;
    }
}
