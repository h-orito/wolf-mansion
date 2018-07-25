package com.ort.app.web.model.inner;

public class PlayerStatsDto {

    /** 参加数 */
    private Integer participateNum;

    /** 勝利数 */
    private Integer winNum;

    /** 勝率 */
    private Float winRate;

    public Integer getParticipateNum() {
        return participateNum;
    }

    public void setParticipateNum(Integer participateNum) {
        this.participateNum = participateNum;
    }

    public Integer getWinNum() {
        return winNum;
    }

    public void setWinNum(Integer winNum) {
        this.winNum = winNum;
    }

    public Float getWinRate() {
        return winRate;
    }

    public void setWinRate(Float winRate) {
        this.winRate = winRate;
    }
}
