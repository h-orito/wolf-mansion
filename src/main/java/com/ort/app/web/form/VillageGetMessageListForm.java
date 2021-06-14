package com.ort.app.web.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class VillageGetMessageListForm implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 村ID */
    @NotNull
    private Integer villageId;

    /** 何日目か */
    private Integer day;

    /** 1ページあたりの表示発言数 */
    private Integer pageSize;

    /** 何ページ目か */
    private Integer pageNum;

    /** 自分宛に絞るか */
    private Boolean onlyToMe;

    public Integer getVillageId() {
        return villageId;
    }

    public void setVillageId(Integer villageId) {
        this.villageId = villageId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Boolean getOnlyToMe() {
        return onlyToMe;
    }

    public void setOnlyToMe(Boolean onlyToMe) {
        this.onlyToMe = onlyToMe;
    }
}
