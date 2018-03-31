package com.ort.app.web.model.inner;

import java.time.LocalDateTime;

public class VillageMessageDto {

    /** プレイヤー名 */
    private String playerName;

    /** キャラクター名 */
    private String characterName;

    /** キャラクターID */
    private Integer characterId;

    /** キャラクター画像URL */
    private String characterImageUrl;

    /** メッセージ種別 */
    private String messageType;

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

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public String getCharacterImageUrl() {
        return characterImageUrl;
    }

    public void setCharacterImageUrl(String characterImageUrl) {
        this.characterImageUrl = characterImageUrl;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
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
