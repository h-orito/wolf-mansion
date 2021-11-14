package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import com.ort.dbflute.allcommon.EntityDefinedCommonColumn;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.*;

/**
 * The entity of MESSAGE as TABLE. <br>
 * メッセージ
 * <pre>
 * [primary-key]
 *     MESSAGE_ID
 *
 * [column]
 *     MESSAGE_ID, VILLAGE_ID, VILLAGE_PLAYER_ID, TO_VILLAGE_PLAYER_ID, PLAYER_ID, DAY, MESSAGE_TYPE_CODE, MESSAGE_NUMBER, MESSAGE_CONTENT, MESSAGE_DATETIME, IS_CONVERT_DISABLE, FACE_TYPE_CODE, CHARA_NAME, CHARA_SHORT_NAME, TO_CHARA_NAME, TO_CHARA_SHORT_NAME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     MESSAGE_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     FACE_TYPE, MESSAGE_TYPE, PLAYER, VILLAGE_PLAYER, VILLAGE_DAY
 *
 * [referrer table]
 *     MESSAGE_SENDTO
 *
 * [foreign property]
 *     faceType, messageType, player, villagePlayerByToVillagePlayerId, villageDay, villagePlayerByVillagePlayerId
 *
 * [referrer property]
 *     messageSendtoList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer messageId = entity.getMessageId();
 * Integer villageId = entity.getVillageId();
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * Integer toVillagePlayerId = entity.getToVillagePlayerId();
 * Integer playerId = entity.getPlayerId();
 * Integer day = entity.getDay();
 * String messageTypeCode = entity.getMessageTypeCode();
 * Integer messageNumber = entity.getMessageNumber();
 * String messageContent = entity.getMessageContent();
 * java.time.LocalDateTime messageDatetime = entity.getMessageDatetime();
 * Boolean isConvertDisable = entity.getIsConvertDisable();
 * String faceTypeCode = entity.getFaceTypeCode();
 * String charaName = entity.getCharaName();
 * String charaShortName = entity.getCharaShortName();
 * String toCharaName = entity.getToCharaName();
 * String toCharaShortName = entity.getToCharaShortName();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setMessageId(messageId);
 * entity.setVillageId(villageId);
 * entity.setVillagePlayerId(villagePlayerId);
 * entity.setToVillagePlayerId(toVillagePlayerId);
 * entity.setPlayerId(playerId);
 * entity.setDay(day);
 * entity.setMessageTypeCode(messageTypeCode);
 * entity.setMessageNumber(messageNumber);
 * entity.setMessageContent(messageContent);
 * entity.setMessageDatetime(messageDatetime);
 * entity.setIsConvertDisable(isConvertDisable);
 * entity.setFaceTypeCode(faceTypeCode);
 * entity.setCharaName(charaName);
 * entity.setCharaShortName(charaShortName);
 * entity.setToCharaName(toCharaName);
 * entity.setToCharaShortName(toCharaShortName);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsMessage extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _messageId;

    /** VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} */
    protected Integer _villageId;

    /** VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER} */
    protected Integer _villagePlayerId;

    /** TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER} */
    protected Integer _toVillagePlayerId;

    /** PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER} */
    protected Integer _playerId;

    /** DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} */
    protected Integer _day;

    /** MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} */
    protected String _messageTypeCode;

    /** MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)} */
    protected Integer _messageNumber;

    /** MESSAGE_CONTENT: {NotNull, TEXT(65535)} */
    protected String _messageContent;

    /** MESSAGE_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _messageDatetime;

    /** IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isConvertDisable;

    /** FACE_TYPE_CODE: {IX, VARCHAR(20), FK to FACE_TYPE, classification=FaceType} */
    protected String _faceTypeCode;

    /** CHARA_NAME: {VARCHAR(40)} */
    protected String _charaName;

    /** CHARA_SHORT_NAME: {CHAR(1)} */
    protected String _charaShortName;

    /** TO_CHARA_NAME: {VARCHAR(40)} */
    protected String _toCharaName;

    /** TO_CHARA_SHORT_NAME: {CHAR(1)} */
    protected String _toCharaShortName;

    /** REGISTER_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _registerDatetime;

    /** REGISTER_TRACE: {NotNull, VARCHAR(64)} */
    protected String _registerTrace;

    /** UPDATE_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _updateDatetime;

    /** UPDATE_TRACE: {NotNull, VARCHAR(64)} */
    protected String _updateTrace;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "MESSAGE";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_messageId == null) { return false; }
        return true;
    }

    /**
     * To be unique by the unique column. <br>
     * You can update the entity by the key when entity update (NOT batch update).
     * @param villageId : UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY. (NotNull)
     * @param messageTypeCode : +UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType. (NotNull)
     * @param messageNumber : +UQ, INT UNSIGNED(10). (NotNull)
     */
    public void uniqueBy(Integer villageId, CDef.MessageType messageTypeCode, Integer messageNumber) {
        __uniqueDrivenProperties.clear();
        __uniqueDrivenProperties.addPropertyName("villageId");
        __uniqueDrivenProperties.addPropertyName("messageTypeCode");
        __uniqueDrivenProperties.addPropertyName("messageNumber");
        setVillageId(villageId);setMessageTypeCodeAsMessageType(messageTypeCode);setMessageNumber(messageNumber);
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of messageTypeCode as the classification of MessageType. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.MessageType getMessageTypeCodeAsMessageType() {
        return CDef.MessageType.codeOf(getMessageTypeCode());
    }

    /**
     * Set the value of messageTypeCode as the classification of MessageType. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setMessageTypeCodeAsMessageType(CDef.MessageType cdef) {
        setMessageTypeCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of isConvertDisable as the classification of Flg. <br>
     * IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsConvertDisableAsFlg() {
        return CDef.Flg.codeOf(getIsConvertDisable());
    }

    /**
     * Set the value of isConvertDisable as the classification of Flg. <br>
     * IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsConvertDisableAsFlg(CDef.Flg cdef) {
        setIsConvertDisable(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of faceTypeCode as the classification of FaceType. <br>
     * FACE_TYPE_CODE: {IX, VARCHAR(20), FK to FACE_TYPE, classification=FaceType} <br>
     * 表情種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.FaceType getFaceTypeCodeAsFaceType() {
        return CDef.FaceType.codeOf(getFaceTypeCode());
    }

    /**
     * Set the value of faceTypeCode as the classification of FaceType. <br>
     * FACE_TYPE_CODE: {IX, VARCHAR(20), FK to FACE_TYPE, classification=FaceType} <br>
     * 表情種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setFaceTypeCodeAsFaceType(CDef.FaceType cdef) {
        setFaceTypeCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of messageTypeCode as アクション (ACTION). <br>
     * アクション
     */
    public void setMessageTypeCode_アクション() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.アクション);
    }

    /**
     * Set the value of messageTypeCode as 村建て発言 (CREATOR_SAY). <br>
     * 村建て発言
     */
    public void setMessageTypeCode_村建て発言() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.村建て発言);
    }

    /**
     * Set the value of messageTypeCode as 死者の呻き (GRAVE_SAY). <br>
     * 死者の呻き
     */
    public void setMessageTypeCode_死者の呻き() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.死者の呻き);
    }

    /**
     * Set the value of messageTypeCode as 恋人発言 (LOVERS_SAY). <br>
     * 恋人発言
     */
    public void setMessageTypeCode_恋人発言() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.恋人発言);
    }

    /**
     * Set the value of messageTypeCode as 共鳴発言 (MASON_SAY). <br>
     * 共鳴発言
     */
    public void setMessageTypeCode_共鳴発言() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.共鳴発言);
    }

    /**
     * Set the value of messageTypeCode as 独り言 (MONOLOGUE_SAY). <br>
     * 独り言
     */
    public void setMessageTypeCode_独り言() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.独り言);
    }

    /**
     * Set the value of messageTypeCode as 通常発言 (NORMAL_SAY). <br>
     * 通常発言
     */
    public void setMessageTypeCode_通常発言() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.通常発言);
    }

    /**
     * Set the value of messageTypeCode as 参加者一覧 (PARTICIPANTS). <br>
     * 参加者一覧
     */
    public void setMessageTypeCode_参加者一覧() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.参加者一覧);
    }

    /**
     * Set the value of messageTypeCode as 能力行使メッセージ (PRIVATE_ABILITY). <br>
     * 能力行使メッセージ
     */
    public void setMessageTypeCode_能力行使メッセージ() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.能力行使メッセージ);
    }

    /**
     * Set the value of messageTypeCode as 検死結果 (PRIVATE_CORONER). <br>
     * 検死結果
     */
    public void setMessageTypeCode_検死結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.検死結果);
    }

    /**
     * Set the value of messageTypeCode as 妖狐メッセージ (PRIVATE_FOX). <br>
     * 妖狐メッセージ
     */
    public void setMessageTypeCode_妖狐メッセージ() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.妖狐メッセージ);
    }

    /**
     * Set the value of messageTypeCode as 役職霊視結果 (PRIVATE_GURU). <br>
     * 役職霊視結果
     */
    public void setMessageTypeCode_役職霊視結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.役職霊視結果);
    }

    /**
     * Set the value of messageTypeCode as 足音調査結果 (PRIVATE_INVESTIGATE). <br>
     * 足音調査結果
     */
    public void setMessageTypeCode_足音調査結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.足音調査結果);
    }

    /**
     * Set the value of messageTypeCode as 恋人メッセージ (PRIVATE_LOVER). <br>
     * 恋人メッセージ
     */
    public void setMessageTypeCode_恋人メッセージ() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.恋人メッセージ);
    }

    /**
     * Set the value of messageTypeCode as 白黒霊視結果 (PRIVATE_PSYCHIC). <br>
     * 白黒霊視結果
     */
    public void setMessageTypeCode_白黒霊視結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.白黒霊視結果);
    }

    /**
     * Set the value of messageTypeCode as 白黒占い結果 (PRIVATE_SEER). <br>
     * 白黒占い結果
     */
    public void setMessageTypeCode_白黒占い結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.白黒占い結果);
    }

    /**
     * Set the value of messageTypeCode as 非公開システムメッセージ (PRIVATE_SYSTEM). <br>
     * 非公開システムメッセージ
     */
    public void setMessageTypeCode_非公開システムメッセージ() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.非公開システムメッセージ);
    }

    /**
     * Set the value of messageTypeCode as 襲撃結果 (PRIVATE_WEREWOLF). <br>
     * 襲撃結果
     */
    public void setMessageTypeCode_襲撃結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.襲撃結果);
    }

    /**
     * Set the value of messageTypeCode as 役職占い結果 (PRIVATE_WISE). <br>
     * 役職占い結果
     */
    public void setMessageTypeCode_役職占い結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.役職占い結果);
    }

    /**
     * Set the value of messageTypeCode as 公開システムメッセージ (PUBLIC_SYSTEM). <br>
     * 公開システムメッセージ
     */
    public void setMessageTypeCode_公開システムメッセージ() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.公開システムメッセージ);
    }

    /**
     * Set the value of messageTypeCode as 秘話 (SECRET_SAY). <br>
     * 秘話
     */
    public void setMessageTypeCode_秘話() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.秘話);
    }

    /**
     * Set the value of messageTypeCode as 見学発言 (SPECTATE_SAY). <br>
     * 見学発言
     */
    public void setMessageTypeCode_見学発言() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.見学発言);
    }

    /**
     * Set the value of messageTypeCode as 念話 (TELEPATHY). <br>
     * 念話
     */
    public void setMessageTypeCode_念話() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.念話);
    }

    /**
     * Set the value of messageTypeCode as 人狼の囁き (WEREWOLF_SAY). <br>
     * 人狼の囁き
     */
    public void setMessageTypeCode_人狼の囁き() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.人狼の囁き);
    }

    /**
     * Set the value of isConvertDisable as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsConvertDisable_True() {
        setIsConvertDisableAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isConvertDisable as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsConvertDisable_False() {
        setIsConvertDisableAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of faceTypeCode as 墓下 (GRAVE). <br>
     * 墓下
     */
    public void setFaceTypeCode_墓下() {
        setFaceTypeCodeAsFaceType(CDef.FaceType.墓下);
    }

    /**
     * Set the value of faceTypeCode as 共鳴 (MASON). <br>
     * 共鳴
     */
    public void setFaceTypeCode_共鳴() {
        setFaceTypeCodeAsFaceType(CDef.FaceType.共鳴);
    }

    /**
     * Set the value of faceTypeCode as 独り言 (MONOLOGUE). <br>
     * 独り言
     */
    public void setFaceTypeCode_独り言() {
        setFaceTypeCodeAsFaceType(CDef.FaceType.独り言);
    }

    /**
     * Set the value of faceTypeCode as 通常 (NORMAL). <br>
     * 通常
     */
    public void setFaceTypeCode_通常() {
        setFaceTypeCodeAsFaceType(CDef.FaceType.通常);
    }

    /**
     * Set the value of faceTypeCode as 秘話 (SECRET). <br>
     * 秘話
     */
    public void setFaceTypeCode_秘話() {
        setFaceTypeCodeAsFaceType(CDef.FaceType.秘話);
    }

    /**
     * Set the value of faceTypeCode as 囁き (WEREWOLF). <br>
     * 囁き
     */
    public void setFaceTypeCode_囁き() {
        setFaceTypeCodeAsFaceType(CDef.FaceType.囁き);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of messageTypeCode アクション? <br>
     * アクション
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCodeアクション() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.アクション) : false;
    }

    /**
     * Is the value of messageTypeCode 村建て発言? <br>
     * 村建て発言
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode村建て発言() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.村建て発言) : false;
    }

    /**
     * Is the value of messageTypeCode 死者の呻き? <br>
     * 死者の呻き
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode死者の呻き() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.死者の呻き) : false;
    }

    /**
     * Is the value of messageTypeCode 恋人発言? <br>
     * 恋人発言
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode恋人発言() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.恋人発言) : false;
    }

    /**
     * Is the value of messageTypeCode 共鳴発言? <br>
     * 共鳴発言
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode共鳴発言() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.共鳴発言) : false;
    }

    /**
     * Is the value of messageTypeCode 独り言? <br>
     * 独り言
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode独り言() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.独り言) : false;
    }

    /**
     * Is the value of messageTypeCode 通常発言? <br>
     * 通常発言
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode通常発言() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.通常発言) : false;
    }

    /**
     * Is the value of messageTypeCode 参加者一覧? <br>
     * 参加者一覧
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode参加者一覧() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.参加者一覧) : false;
    }

    /**
     * Is the value of messageTypeCode 能力行使メッセージ? <br>
     * 能力行使メッセージ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode能力行使メッセージ() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.能力行使メッセージ) : false;
    }

    /**
     * Is the value of messageTypeCode 検死結果? <br>
     * 検死結果
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode検死結果() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.検死結果) : false;
    }

    /**
     * Is the value of messageTypeCode 妖狐メッセージ? <br>
     * 妖狐メッセージ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode妖狐メッセージ() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.妖狐メッセージ) : false;
    }

    /**
     * Is the value of messageTypeCode 役職霊視結果? <br>
     * 役職霊視結果
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode役職霊視結果() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.役職霊視結果) : false;
    }

    /**
     * Is the value of messageTypeCode 足音調査結果? <br>
     * 足音調査結果
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode足音調査結果() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.足音調査結果) : false;
    }

    /**
     * Is the value of messageTypeCode 恋人メッセージ? <br>
     * 恋人メッセージ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode恋人メッセージ() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.恋人メッセージ) : false;
    }

    /**
     * Is the value of messageTypeCode 白黒霊視結果? <br>
     * 白黒霊視結果
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode白黒霊視結果() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.白黒霊視結果) : false;
    }

    /**
     * Is the value of messageTypeCode 白黒占い結果? <br>
     * 白黒占い結果
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode白黒占い結果() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.白黒占い結果) : false;
    }

    /**
     * Is the value of messageTypeCode 非公開システムメッセージ? <br>
     * 非公開システムメッセージ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode非公開システムメッセージ() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.非公開システムメッセージ) : false;
    }

    /**
     * Is the value of messageTypeCode 襲撃結果? <br>
     * 襲撃結果
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode襲撃結果() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.襲撃結果) : false;
    }

    /**
     * Is the value of messageTypeCode 役職占い結果? <br>
     * 役職占い結果
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode役職占い結果() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.役職占い結果) : false;
    }

    /**
     * Is the value of messageTypeCode 公開システムメッセージ? <br>
     * 公開システムメッセージ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode公開システムメッセージ() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.公開システムメッセージ) : false;
    }

    /**
     * Is the value of messageTypeCode 秘話? <br>
     * 秘話
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode秘話() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.秘話) : false;
    }

    /**
     * Is the value of messageTypeCode 見学発言? <br>
     * 見学発言
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode見学発言() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.見学発言) : false;
    }

    /**
     * Is the value of messageTypeCode 念話? <br>
     * 念話
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode念話() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.念話) : false;
    }

    /**
     * Is the value of messageTypeCode 人狼の囁き? <br>
     * 人狼の囁き
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode人狼の囁き() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.人狼の囁き) : false;
    }

    /**
     * Is the value of isConvertDisable True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsConvertDisableTrue() {
        CDef.Flg cdef = getIsConvertDisableAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isConvertDisable False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsConvertDisableFalse() {
        CDef.Flg cdef = getIsConvertDisableAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of faceTypeCode 墓下? <br>
     * 墓下
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isFaceTypeCode墓下() {
        CDef.FaceType cdef = getFaceTypeCodeAsFaceType();
        return cdef != null ? cdef.equals(CDef.FaceType.墓下) : false;
    }

    /**
     * Is the value of faceTypeCode 共鳴? <br>
     * 共鳴
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isFaceTypeCode共鳴() {
        CDef.FaceType cdef = getFaceTypeCodeAsFaceType();
        return cdef != null ? cdef.equals(CDef.FaceType.共鳴) : false;
    }

    /**
     * Is the value of faceTypeCode 独り言? <br>
     * 独り言
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isFaceTypeCode独り言() {
        CDef.FaceType cdef = getFaceTypeCodeAsFaceType();
        return cdef != null ? cdef.equals(CDef.FaceType.独り言) : false;
    }

    /**
     * Is the value of faceTypeCode 通常? <br>
     * 通常
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isFaceTypeCode通常() {
        CDef.FaceType cdef = getFaceTypeCodeAsFaceType();
        return cdef != null ? cdef.equals(CDef.FaceType.通常) : false;
    }

    /**
     * Is the value of faceTypeCode 秘話? <br>
     * 秘話
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isFaceTypeCode秘話() {
        CDef.FaceType cdef = getFaceTypeCodeAsFaceType();
        return cdef != null ? cdef.equals(CDef.FaceType.秘話) : false;
    }

    /**
     * Is the value of faceTypeCode 囁き? <br>
     * 囁き
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isFaceTypeCode囁き() {
        CDef.FaceType cdef = getFaceTypeCodeAsFaceType();
        return cdef != null ? cdef.equals(CDef.FaceType.囁き) : false;
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'isConvertDisable' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsConvertDisableAlias() {
        CDef.Flg cdef = getIsConvertDisableAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'. */
    protected OptionalEntity<FaceType> _faceType;

    /**
     * [get] FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'faceType'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<FaceType> getFaceType() {
        if (_faceType == null) { _faceType = OptionalEntity.relationEmpty(this, "faceType"); }
        return _faceType;
    }

    /**
     * [set] FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'.
     * @param faceType The entity of foreign property 'faceType'. (NullAllowed)
     */
    public void setFaceType(OptionalEntity<FaceType> faceType) {
        _faceType = faceType;
    }

    /** MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'. */
    protected OptionalEntity<MessageType> _messageType;

    /**
     * [get] MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'messageType'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<MessageType> getMessageType() {
        if (_messageType == null) { _messageType = OptionalEntity.relationEmpty(this, "messageType"); }
        return _messageType;
    }

    /**
     * [set] MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'.
     * @param messageType The entity of foreign property 'messageType'. (NullAllowed)
     */
    public void setMessageType(OptionalEntity<MessageType> messageType) {
        _messageType = messageType;
    }

    /** PLAYER by my PLAYER_ID, named 'player'. */
    protected OptionalEntity<Player> _player;

    /**
     * [get] PLAYER by my PLAYER_ID, named 'player'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'player'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Player> getPlayer() {
        if (_player == null) { _player = OptionalEntity.relationEmpty(this, "player"); }
        return _player;
    }

    /**
     * [set] PLAYER by my PLAYER_ID, named 'player'.
     * @param player The entity of foreign property 'player'. (NullAllowed)
     */
    public void setPlayer(OptionalEntity<Player> player) {
        _player = player;
    }

    /** VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'. */
    protected OptionalEntity<VillagePlayer> _villagePlayerByToVillagePlayerId;

    /**
     * [get] VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villagePlayerByToVillagePlayerId'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillagePlayer> getVillagePlayerByToVillagePlayerId() {
        if (_villagePlayerByToVillagePlayerId == null) { _villagePlayerByToVillagePlayerId = OptionalEntity.relationEmpty(this, "villagePlayerByToVillagePlayerId"); }
        return _villagePlayerByToVillagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * @param villagePlayerByToVillagePlayerId The entity of foreign property 'villagePlayerByToVillagePlayerId'. (NullAllowed)
     */
    public void setVillagePlayerByToVillagePlayerId(OptionalEntity<VillagePlayer> villagePlayerByToVillagePlayerId) {
        _villagePlayerByToVillagePlayerId = villagePlayerByToVillagePlayerId;
    }

    /** VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'. */
    protected OptionalEntity<VillageDay> _villageDay;

    /**
     * [get] VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villageDay'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillageDay> getVillageDay() {
        if (_villageDay == null) { _villageDay = OptionalEntity.relationEmpty(this, "villageDay"); }
        return _villageDay;
    }

    /**
     * [set] VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'.
     * @param villageDay The entity of foreign property 'villageDay'. (NullAllowed)
     */
    public void setVillageDay(OptionalEntity<VillageDay> villageDay) {
        _villageDay = villageDay;
    }

    /** VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'. */
    protected OptionalEntity<VillagePlayer> _villagePlayerByVillagePlayerId;

    /**
     * [get] VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villagePlayerByVillagePlayerId'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillagePlayer> getVillagePlayerByVillagePlayerId() {
        if (_villagePlayerByVillagePlayerId == null) { _villagePlayerByVillagePlayerId = OptionalEntity.relationEmpty(this, "villagePlayerByVillagePlayerId"); }
        return _villagePlayerByVillagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * @param villagePlayerByVillagePlayerId The entity of foreign property 'villagePlayerByVillagePlayerId'. (NullAllowed)
     */
    public void setVillagePlayerByVillagePlayerId(OptionalEntity<VillagePlayer> villagePlayerByVillagePlayerId) {
        _villagePlayerByVillagePlayerId = villagePlayerByVillagePlayerId;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** MESSAGE_SENDTO by MESSAGE_ID, named 'messageSendtoList'. */
    protected List<MessageSendto> _messageSendtoList;

    /**
     * [get] MESSAGE_SENDTO by MESSAGE_ID, named 'messageSendtoList'.
     * @return The entity list of referrer property 'messageSendtoList'. (NotNull: even if no loading, returns empty list)
     */
    public List<MessageSendto> getMessageSendtoList() {
        if (_messageSendtoList == null) { _messageSendtoList = newReferrerList(); }
        return _messageSendtoList;
    }

    /**
     * [set] MESSAGE_SENDTO by MESSAGE_ID, named 'messageSendtoList'.
     * @param messageSendtoList The entity list of referrer property 'messageSendtoList'. (NullAllowed)
     */
    public void setMessageSendtoList(List<MessageSendto> messageSendtoList) {
        _messageSendtoList = messageSendtoList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsMessage) {
            BsMessage other = (BsMessage)obj;
            if (!xSV(_messageId, other._messageId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _messageId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_faceType != null && _faceType.isPresent())
        { sb.append(li).append(xbRDS(_faceType, "faceType")); }
        if (_messageType != null && _messageType.isPresent())
        { sb.append(li).append(xbRDS(_messageType, "messageType")); }
        if (_player != null && _player.isPresent())
        { sb.append(li).append(xbRDS(_player, "player")); }
        if (_villagePlayerByToVillagePlayerId != null && _villagePlayerByToVillagePlayerId.isPresent())
        { sb.append(li).append(xbRDS(_villagePlayerByToVillagePlayerId, "villagePlayerByToVillagePlayerId")); }
        if (_villageDay != null && _villageDay.isPresent())
        { sb.append(li).append(xbRDS(_villageDay, "villageDay")); }
        if (_villagePlayerByVillagePlayerId != null && _villagePlayerByVillagePlayerId.isPresent())
        { sb.append(li).append(xbRDS(_villagePlayerByVillagePlayerId, "villagePlayerByVillagePlayerId")); }
        if (_messageSendtoList != null) { for (MessageSendto et : _messageSendtoList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageSendtoList")); } } }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_messageId));
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_villagePlayerId));
        sb.append(dm).append(xfND(_toVillagePlayerId));
        sb.append(dm).append(xfND(_playerId));
        sb.append(dm).append(xfND(_day));
        sb.append(dm).append(xfND(_messageTypeCode));
        sb.append(dm).append(xfND(_messageNumber));
        sb.append(dm).append(xfND(_messageContent));
        sb.append(dm).append(xfND(_messageDatetime));
        sb.append(dm).append(xfND(_isConvertDisable));
        sb.append(dm).append(xfND(_faceTypeCode));
        sb.append(dm).append(xfND(_charaName));
        sb.append(dm).append(xfND(_charaShortName));
        sb.append(dm).append(xfND(_toCharaName));
        sb.append(dm).append(xfND(_toCharaShortName));
        sb.append(dm).append(xfND(_registerDatetime));
        sb.append(dm).append(xfND(_registerTrace));
        sb.append(dm).append(xfND(_updateDatetime));
        sb.append(dm).append(xfND(_updateTrace));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_faceType != null && _faceType.isPresent())
        { sb.append(dm).append("faceType"); }
        if (_messageType != null && _messageType.isPresent())
        { sb.append(dm).append("messageType"); }
        if (_player != null && _player.isPresent())
        { sb.append(dm).append("player"); }
        if (_villagePlayerByToVillagePlayerId != null && _villagePlayerByToVillagePlayerId.isPresent())
        { sb.append(dm).append("villagePlayerByToVillagePlayerId"); }
        if (_villageDay != null && _villageDay.isPresent())
        { sb.append(dm).append("villageDay"); }
        if (_villagePlayerByVillagePlayerId != null && _villagePlayerByVillagePlayerId.isPresent())
        { sb.append(dm).append("villagePlayerByVillagePlayerId"); }
        if (_messageSendtoList != null && !_messageSendtoList.isEmpty())
        { sb.append(dm).append("messageSendtoList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Message clone() {
        return (Message)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * メッセージID
     * @return The value of the column 'MESSAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getMessageId() {
        checkSpecifiedProperty("messageId");
        return _messageId;
    }

    /**
     * [set] MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * メッセージID
     * @param messageId The value of the column 'MESSAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setMessageId(Integer messageId) {
        registerModifiedProperty("messageId");
        _messageId = messageId;
    }

    /**
     * [get] VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER} <br>
     * 村参加者ID
     * @return The value of the column 'VILLAGE_PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getVillagePlayerId() {
        checkSpecifiedProperty("villagePlayerId");
        return _villagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER} <br>
     * 村参加者ID
     * @param villagePlayerId The value of the column 'VILLAGE_PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setVillagePlayerId(Integer villagePlayerId) {
        registerModifiedProperty("villagePlayerId");
        _villagePlayerId = villagePlayerId;
    }

    /**
     * [get] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER} <br>
     * 秘話相手の村参加者ID
     * @return The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getToVillagePlayerId() {
        checkSpecifiedProperty("toVillagePlayerId");
        return _toVillagePlayerId;
    }

    /**
     * [set] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER} <br>
     * 秘話相手の村参加者ID
     * @param toVillagePlayerId The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setToVillagePlayerId(Integer toVillagePlayerId) {
        registerModifiedProperty("toVillagePlayerId");
        _toVillagePlayerId = toVillagePlayerId;
    }

    /**
     * [get] PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER} <br>
     * プレイヤーID
     * @return The value of the column 'PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getPlayerId() {
        checkSpecifiedProperty("playerId");
        return _playerId;
    }

    /**
     * [set] PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER} <br>
     * プレイヤーID
     * @param playerId The value of the column 'PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPlayerId(Integer playerId) {
        registerModifiedProperty("playerId");
        _playerId = playerId;
    }

    /**
     * [get] DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 何日目か
     * @return The value of the column 'DAY'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDay() {
        checkSpecifiedProperty("day");
        return _day;
    }

    /**
     * [set] DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 何日目か
     * @param day The value of the column 'DAY'. (basically NotNull if update: for the constraint)
     */
    public void setDay(Integer day) {
        registerModifiedProperty("day");
        _day = day;
    }

    /**
     * [get] MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別コード
     * @return The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageTypeCode() {
        checkSpecifiedProperty("messageTypeCode");
        return convertEmptyToNull(_messageTypeCode);
    }

    /**
     * [set] MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別コード
     * @param messageTypeCode The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setMessageTypeCode(String messageTypeCode) {
        checkClassificationCode("MESSAGE_TYPE_CODE", CDef.DefMeta.MessageType, messageTypeCode);
        registerModifiedProperty("messageTypeCode");
        _messageTypeCode = messageTypeCode;
    }

    /**
     * [get] MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)} <br>
     * メッセージ番号
     * @return The value of the column 'MESSAGE_NUMBER'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMessageNumber() {
        checkSpecifiedProperty("messageNumber");
        return _messageNumber;
    }

    /**
     * [set] MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)} <br>
     * メッセージ番号
     * @param messageNumber The value of the column 'MESSAGE_NUMBER'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMessageNumber(Integer messageNumber) {
        registerModifiedProperty("messageNumber");
        _messageNumber = messageNumber;
    }

    /**
     * [get] MESSAGE_CONTENT: {NotNull, TEXT(65535)} <br>
     * メッセージ内容
     * @return The value of the column 'MESSAGE_CONTENT'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageContent() {
        checkSpecifiedProperty("messageContent");
        return convertEmptyToNull(_messageContent);
    }

    /**
     * [set] MESSAGE_CONTENT: {NotNull, TEXT(65535)} <br>
     * メッセージ内容
     * @param messageContent The value of the column 'MESSAGE_CONTENT'. (basically NotNull if update: for the constraint)
     */
    public void setMessageContent(String messageContent) {
        registerModifiedProperty("messageContent");
        _messageContent = messageContent;
    }

    /**
     * [get] MESSAGE_DATETIME: {NotNull, DATETIME(19)} <br>
     * メッセージ日時
     * @return The value of the column 'MESSAGE_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getMessageDatetime() {
        checkSpecifiedProperty("messageDatetime");
        return _messageDatetime;
    }

    /**
     * [set] MESSAGE_DATETIME: {NotNull, DATETIME(19)} <br>
     * メッセージ日時
     * @param messageDatetime The value of the column 'MESSAGE_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setMessageDatetime(java.time.LocalDateTime messageDatetime) {
        registerModifiedProperty("messageDatetime");
        _messageDatetime = messageDatetime;
    }

    /**
     * [get] IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg} <br>
     * 変換無効か
     * @return The value of the column 'IS_CONVERT_DISABLE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsConvertDisable() {
        checkSpecifiedProperty("isConvertDisable");
        return _isConvertDisable;
    }

    /**
     * [set] IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg} <br>
     * 変換無効か
     * @param isConvertDisable The value of the column 'IS_CONVERT_DISABLE'. (basically NotNull if update: for the constraint)
     */
    public void setIsConvertDisable(Boolean isConvertDisable) {
        checkClassificationCode("IS_CONVERT_DISABLE", CDef.DefMeta.Flg, isConvertDisable);
        registerModifiedProperty("isConvertDisable");
        _isConvertDisable = isConvertDisable;
    }

    /**
     * [get] FACE_TYPE_CODE: {IX, VARCHAR(20), FK to FACE_TYPE, classification=FaceType} <br>
     * 表情種別コード
     * @return The value of the column 'FACE_TYPE_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getFaceTypeCode() {
        checkSpecifiedProperty("faceTypeCode");
        return convertEmptyToNull(_faceTypeCode);
    }

    /**
     * [set] FACE_TYPE_CODE: {IX, VARCHAR(20), FK to FACE_TYPE, classification=FaceType} <br>
     * 表情種別コード
     * @param faceTypeCode The value of the column 'FACE_TYPE_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setFaceTypeCode(String faceTypeCode) {
        checkClassificationCode("FACE_TYPE_CODE", CDef.DefMeta.FaceType, faceTypeCode);
        registerModifiedProperty("faceTypeCode");
        _faceTypeCode = faceTypeCode;
    }

    /**
     * [get] CHARA_NAME: {VARCHAR(40)} <br>
     * キャラクター名
     * @return The value of the column 'CHARA_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getCharaName() {
        checkSpecifiedProperty("charaName");
        return convertEmptyToNull(_charaName);
    }

    /**
     * [set] CHARA_NAME: {VARCHAR(40)} <br>
     * キャラクター名
     * @param charaName The value of the column 'CHARA_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setCharaName(String charaName) {
        registerModifiedProperty("charaName");
        _charaName = charaName;
    }

    /**
     * [get] CHARA_SHORT_NAME: {CHAR(1)} <br>
     * キャラクター略称
     * @return The value of the column 'CHARA_SHORT_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getCharaShortName() {
        checkSpecifiedProperty("charaShortName");
        return convertEmptyToNull(_charaShortName);
    }

    /**
     * [set] CHARA_SHORT_NAME: {CHAR(1)} <br>
     * キャラクター略称
     * @param charaShortName The value of the column 'CHARA_SHORT_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setCharaShortName(String charaShortName) {
        registerModifiedProperty("charaShortName");
        _charaShortName = charaShortName;
    }

    /**
     * [get] TO_CHARA_NAME: {VARCHAR(40)} <br>
     * 秘話相手のキャラクター名
     * @return The value of the column 'TO_CHARA_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getToCharaName() {
        checkSpecifiedProperty("toCharaName");
        return convertEmptyToNull(_toCharaName);
    }

    /**
     * [set] TO_CHARA_NAME: {VARCHAR(40)} <br>
     * 秘話相手のキャラクター名
     * @param toCharaName The value of the column 'TO_CHARA_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setToCharaName(String toCharaName) {
        registerModifiedProperty("toCharaName");
        _toCharaName = toCharaName;
    }

    /**
     * [get] TO_CHARA_SHORT_NAME: {CHAR(1)} <br>
     * 秘話相手のキャラクター略称
     * @return The value of the column 'TO_CHARA_SHORT_NAME'. (NullAllowed even if selected: for no constraint)
     */
    public String getToCharaShortName() {
        checkSpecifiedProperty("toCharaShortName");
        return convertEmptyToNull(_toCharaShortName);
    }

    /**
     * [set] TO_CHARA_SHORT_NAME: {CHAR(1)} <br>
     * 秘話相手のキャラクター略称
     * @param toCharaShortName The value of the column 'TO_CHARA_SHORT_NAME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setToCharaShortName(String toCharaShortName) {
        registerModifiedProperty("toCharaShortName");
        _toCharaShortName = toCharaShortName;
    }

    /**
     * [get] REGISTER_DATETIME: {NotNull, DATETIME(19)} <br>
     * 登録日時
     * @return The value of the column 'REGISTER_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return _registerDatetime;
    }

    /**
     * [set] REGISTER_DATETIME: {NotNull, DATETIME(19)} <br>
     * 登録日時
     * @param registerDatetime The value of the column 'REGISTER_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterDatetime(java.time.LocalDateTime registerDatetime) {
        registerModifiedProperty("registerDatetime");
        _registerDatetime = registerDatetime;
    }

    /**
     * [get] REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * 登録トレース
     * @return The value of the column 'REGISTER_TRACE'. (basically NotNull if selected: for the constraint)
     */
    public String getRegisterTrace() {
        checkSpecifiedProperty("registerTrace");
        return convertEmptyToNull(_registerTrace);
    }

    /**
     * [set] REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * 登録トレース
     * @param registerTrace The value of the column 'REGISTER_TRACE'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterTrace(String registerTrace) {
        registerModifiedProperty("registerTrace");
        _registerTrace = registerTrace;
    }

    /**
     * [get] UPDATE_DATETIME: {NotNull, DATETIME(19)} <br>
     * 更新日時
     * @return The value of the column 'UPDATE_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return _updateDatetime;
    }

    /**
     * [set] UPDATE_DATETIME: {NotNull, DATETIME(19)} <br>
     * 更新日時
     * @param updateDatetime The value of the column 'UPDATE_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateDatetime(java.time.LocalDateTime updateDatetime) {
        registerModifiedProperty("updateDatetime");
        _updateDatetime = updateDatetime;
    }

    /**
     * [get] UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * 更新トレース
     * @return The value of the column 'UPDATE_TRACE'. (basically NotNull if selected: for the constraint)
     */
    public String getUpdateTrace() {
        checkSpecifiedProperty("updateTrace");
        return convertEmptyToNull(_updateTrace);
    }

    /**
     * [set] UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * 更新トレース
     * @param updateTrace The value of the column 'UPDATE_TRACE'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateTrace(String updateTrace) {
        registerModifiedProperty("updateTrace");
        _updateTrace = updateTrace;
    }

    /**
     * For framework so basically DON'T use this method.
     * @param messageTypeCode The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingMessageTypeCode(String messageTypeCode) {
        setMessageTypeCode(messageTypeCode);
    }

    /**
     * For framework so basically DON'T use this method.
     * @param faceTypeCode The value of the column 'FACE_TYPE_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void mynativeMappingFaceTypeCode(String faceTypeCode) {
        setFaceTypeCode(faceTypeCode);
    }
}
