package com.ort.app.web.model;

import java.io.Serializable;
import java.util.List;

import com.ort.app.web.model.inner.VillageMessageDto;

public class VillageMessageListResultContent implements Serializable {

    private static final long serialVersionUID = 1L;

    /** メッセージリスト */
    private List<VillageMessageDto> messageList;

    /** 村状態メッセージ */
    private String villageStatusMessage;

    /** コミット状態メッセージ */
    private String commitStatusMessage;

    /** 突然死候補メッセージ */
    private String suddonlyDeathMessage;

    /** 最新日付 */
    private Integer latestDay;

    /** 総ページ数 */
    private Integer allPageCount;

    /** 前のページがあるか */
    private Boolean isExistPrePage;

    /** 次のページがあるか */
    private Boolean isExistNextPage;

    /** 現在のページ番号 */
    private Integer currentPageNum;

    /** 表示するページ番号リスト */
    private List<Integer> pageNumList;

    public List<VillageMessageDto> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<VillageMessageDto> messageList) {
        this.messageList = messageList;
    }

    public String getVillageStatusMessage() {
        return villageStatusMessage;
    }

    public void setVillageStatusMessage(String villageStatusMessage) {
        this.villageStatusMessage = villageStatusMessage;
    }

    public String getCommitStatusMessage() {
        return commitStatusMessage;
    }

    public void setCommitStatusMessage(String commitStatusMessage) {
        this.commitStatusMessage = commitStatusMessage;
    }

    public String getSuddonlyDeathMessage() {
        return suddonlyDeathMessage;
    }

    public void setSuddonlyDeathMessage(String suddonlyDeathMessage) {
        this.suddonlyDeathMessage = suddonlyDeathMessage;
    }

    public Integer getLatestDay() {
        return latestDay;
    }

    public void setLatestDay(Integer latestDay) {
        this.latestDay = latestDay;
    }

    public Integer getAllPageCount() {
        return allPageCount;
    }

    public void setAllPageCount(Integer allPageCount) {
        this.allPageCount = allPageCount;
    }

    public Boolean getIsExistPrePage() {
        return isExistPrePage;
    }

    public void setIsExistPrePage(Boolean isExistPrePage) {
        this.isExistPrePage = isExistPrePage;
    }

    public Boolean getIsExistNextPage() {
        return isExistNextPage;
    }

    public void setIsExistNextPage(Boolean isExistNextPage) {
        this.isExistNextPage = isExistNextPage;
    }

    public Integer getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(Integer currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public List<Integer> getPageNumList() {
        return pageNumList;
    }

    public void setPageNumList(List<Integer> pageNumList) {
        this.pageNumList = pageNumList;
    }
}
