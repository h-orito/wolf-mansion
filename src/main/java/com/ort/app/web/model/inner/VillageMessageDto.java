package com.ort.app.web.model.inner;

import java.time.LocalDateTime;

import com.ort.dbflute.allcommon.CDef;

public class VillageMessageDto {

    /** プレイヤー名 */
    private String playerName;

    /** キャラクター名 */
    private String characterName;

    /** キャラクター画像URL */
    private String characterImageUrl;

    /** メッセージ種別 */
    private CDef.MessageType messageType;

    /** メッセージ番号 */
    private Integer messageNumber;

    /** メッセージ内容 */
    private String messageContent;

    /** メッセージ日時 */
    private LocalDateTime messageDatetime;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterImageUrl() {
        return characterImageUrl;
    }

    public void setCharacterImageUrl(String characterImageUrl) {
        this.characterImageUrl = characterImageUrl;
    }

    public CDef.MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(CDef.MessageType messageType) {
        this.messageType = messageType;
    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    public void setMessageNumber(Integer messageNumber) {
        this.messageNumber = messageNumber;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getMessageDatetime() {
        return messageDatetime;
    }

    public void setMessageDatetime(LocalDateTime messageDatetime) {
        this.messageDatetime = messageDatetime;
    }

}
