package com.ort.app.web.model.inner.village;

import java.util.List;

import com.ort.app.web.model.OptionDto;

public class VillageVoteFormDto {

    /** 投票対象リスト */
    private List<OptionDto> voteTargetList;

    public List<OptionDto> getVoteTargetList() {
        return voteTargetList;
    }

    public void setVoteTargetList(List<OptionDto> voteTargetList) {
        this.voteTargetList = voteTargetList;
    }
}
