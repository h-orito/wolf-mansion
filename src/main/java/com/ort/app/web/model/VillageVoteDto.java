package com.ort.app.web.model;

import java.util.List;

public class VillageVoteDto {

    /** 参加者ごとの投票リスト */
    private List<VillageMemberVoteDto> voteList;

    /** 投票した回数の最大 */
    private Integer maxVoteCount;

    public List<VillageMemberVoteDto> getVoteList() {
        return voteList;
    }

    public void setVoteList(List<VillageMemberVoteDto> voteList) {
        this.voteList = voteList;
    }

    public Integer getMaxVoteCount() {
        return maxVoteCount;
    }

    public void setMaxVoteCount(Integer maxVoteCount) {
        this.maxVoteCount = maxVoteCount;
    }
}
