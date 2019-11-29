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

    /** メッセージ表示横幅px */
    private Integer width;

    /** メッセージ表示縦幅px */
    private Integer height;

    /** 秘話相手キャラクター名 */
    private String targetCharacterName;

    /** 変換無効か */
    private Boolean isConvertDisable;

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

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    /** css指定用 */
    public String getMinHeightCss() {
        return "min-height: " + height + "px;";
    }

    public String getTargetCharacterName() {
        return targetCharacterName;
    }

    public void setTargetCharacterName(String targetCharacterName) {
        this.targetCharacterName = targetCharacterName;
    }

    public Boolean getIsConvertDisable() {
        return isConvertDisable;
    }

    public void setIsConvertDisable(Boolean isConvertDisable) {
        this.isConvertDisable = isConvertDisable;
    }
}
