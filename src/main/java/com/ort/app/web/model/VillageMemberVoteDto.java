package com.ort.app.web.model;

import java.util.List;

public class VillageMemberVoteDto {

    /** キャラ省略名 */
    private String charaName;

    /** 投票リスト */
    private List<String> voteTargetList;

    public String getCharaName() {
        return charaName;
    }

    public void setCharaName(String charaName) {
        this.charaName = charaName;
    }

    public List<String> getVoteTargetList() {
        return voteTargetList;
    }

    public void setVoteTargetList(List<String> voteTargetList) {
        this.voteTargetList = voteTargetList;
    }
}
