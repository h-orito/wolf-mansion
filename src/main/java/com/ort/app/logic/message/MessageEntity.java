/**
 * 
 */
package com.ort.app.logic.message;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.VillagePlayer;

public class MessageEntity {
    public Integer villageId;
    public Integer day;
    public CDef.MessageType messageType;
    public String content;
    public VillagePlayer villagePlayer;
    public VillagePlayer targetVillagePlayer;
    public Boolean isConvertDisable;
    public CDef.FaceType faceType;

    public static class Builder {
        private Integer villageId;
        private Integer day;
        private CDef.MessageType messageType;
        private String content;
        private VillagePlayer villagePlayer;
        private VillagePlayer targetVillagePlayer;
        private Boolean isConvertDisable;
        private CDef.FaceType faceType;

        public Builder(Integer villageId, Integer day) {
            this.villageId = villageId;
            this.day = day;
        }

        public Builder messageType(CDef.MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder villagePlayer(VillagePlayer vp) {
            this.villagePlayer = vp;
            return this;
        }

        public Builder targetVillagePlayer(VillagePlayer vp) {
            this.targetVillagePlayer = vp;
            return this;
        }

        public Builder isConvertDisable(Boolean isConvertDisable) {
            this.isConvertDisable = isConvertDisable;
            return this;
        }

        public Builder faceType(CDef.FaceType faceType) {
            this.faceType = faceType;
            return this;
        }

        public MessageEntity build() {
            if (this.villageId == null || this.day == null || this.messageType == null || this.content == null
                    || this.isConvertDisable == null) {
                throw new IllegalStateException("引数不足");
            }
            return new MessageEntity(this);
        }
    }

    public static Builder publicSystemBuilder(Integer villageId, Integer day) {
        Builder builder = new Builder(villageId, day);
        return builder.messageType(CDef.MessageType.公開システムメッセージ).isConvertDisable(true);
    }

    public static Builder privateSystemBuilder(Integer villageId, Integer day) {
        Builder builder = new Builder(villageId, day);
        return builder.messageType(CDef.MessageType.非公開システムメッセージ).isConvertDisable(true);
    }

    public static Builder systemBuilder(Integer villageId, Integer day) {
        Builder builder = new Builder(villageId, day);
        return builder.isConvertDisable(true);
    }

    public MessageEntity replaceContent(String content) {
        this.content = content;
        return this;
    }

    private MessageEntity(Builder builder) {
        this.villageId = builder.villageId;
        this.day = builder.day;
        this.messageType = builder.messageType;
        this.content = builder.content;
        this.villagePlayer = builder.villagePlayer;
        this.targetVillagePlayer = builder.targetVillagePlayer;
        this.isConvertDisable = builder.isConvertDisable;
        this.faceType = builder.faceType;
    }
}