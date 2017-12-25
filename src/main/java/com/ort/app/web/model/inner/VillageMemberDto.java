package com.ort.app.web.model.inner;

import java.util.List;

public class VillageMemberDto {

    /** 状態 e.g. 襲撃 */
    private String status;

    /** キャラリスト */
    private List<VillageMemberDetailDto> statusMemberList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<VillageMemberDetailDto> getStatusMemberList() {
        return statusMemberList;
    }

    public void setStatusMemberList(List<VillageMemberDetailDto> statusMemberList) {
        this.statusMemberList = statusMemberList;
    }

}
