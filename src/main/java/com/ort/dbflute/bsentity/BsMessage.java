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
 *     MESSAGE_ID, VILLAGE_ID, VILLAGE_PLAYER_ID, TO_VILLAGE_PLAYER_ID, PLAYER_ID, DAY, MESSAGE_TYPE_CODE, MESSAGE_NUMBER, MESSAGE_CONTENT, MESSAGE_DATETIME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     MESSAGE_TYPE, PLAYER, VILLAGE_PLAYER, VILLAGE_DAY
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     messageType, player, villagePlayerByToVillagePlayerId, villageDay, villagePlayerByVillagePlayerId
 *
 * [referrer property]
 *     
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

    /** VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to village_day} */
    protected Integer _villageId;

    /** VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} */
    protected Integer _villagePlayerId;

    /** TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} */
    protected Integer _toVillagePlayerId;

    /** PLAYER_ID: {IX, INT UNSIGNED(10), FK to player} */
    protected Integer _playerId;

    /** DAY: {NotNull, INT UNSIGNED(10), FK to village_day} */
    protected Integer _day;

    /** MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType} */
    protected String _messageTypeCode;

    /** MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)} */
    protected Integer _messageNumber;

    /** MESSAGE_CONTENT: {NotNull, VARCHAR(10000)} */
    protected String _messageContent;

    /** MESSAGE_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _messageDatetime;

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
        return "message";
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
     * @param villageId : UQ+, IX+, NotNull, INT UNSIGNED(10), FK to village_day. (NotNull)
     * @param messageTypeCode : +UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType. (NotNull)
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
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType} <br>
     * メッセージ種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.MessageType getMessageTypeCodeAsMessageType() {
        return CDef.MessageType.codeOf(getMessageTypeCode());
    }

    /**
     * Set the value of messageTypeCode as the classification of MessageType. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType} <br>
     * メッセージ種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setMessageTypeCodeAsMessageType(CDef.MessageType cdef) {
        setMessageTypeCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
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
     * Set the value of messageTypeCode as 役職霊視結果 (PRIVATE_GURU). <br>
     * 役職霊視結果
     */
    public void setMessageTypeCode_役職霊視結果() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.役職霊視結果);
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
     * Set the value of messageTypeCode as 人狼の囁き (WEREWOLF_SAY). <br>
     * 人狼の囁き
     */
    public void setMessageTypeCode_人狼の囁き() {
        setMessageTypeCodeAsMessageType(CDef.MessageType.人狼の囁き);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
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
     * Is the value of messageTypeCode 人狼の囁き? <br>
     * 人狼の囁き
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isMessageTypeCode人狼の囁き() {
        CDef.MessageType cdef = getMessageTypeCodeAsMessageType();
        return cdef != null ? cdef.equals(CDef.MessageType.人狼の囁き) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
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
     * [get] VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to village_day} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to village_day} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * 村参加者ID
     * @return The value of the column 'VILLAGE_PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getVillagePlayerId() {
        checkSpecifiedProperty("villagePlayerId");
        return _villagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * 村参加者ID
     * @param villagePlayerId The value of the column 'VILLAGE_PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setVillagePlayerId(Integer villagePlayerId) {
        registerModifiedProperty("villagePlayerId");
        _villagePlayerId = villagePlayerId;
    }

    /**
     * [get] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * 秘話相手の村参加者ID
     * @return The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getToVillagePlayerId() {
        checkSpecifiedProperty("toVillagePlayerId");
        return _toVillagePlayerId;
    }

    /**
     * [set] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * 秘話相手の村参加者ID
     * @param toVillagePlayerId The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setToVillagePlayerId(Integer toVillagePlayerId) {
        registerModifiedProperty("toVillagePlayerId");
        _toVillagePlayerId = toVillagePlayerId;
    }

    /**
     * [get] PLAYER_ID: {IX, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @return The value of the column 'PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getPlayerId() {
        checkSpecifiedProperty("playerId");
        return _playerId;
    }

    /**
     * [set] PLAYER_ID: {IX, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @param playerId The value of the column 'PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPlayerId(Integer playerId) {
        registerModifiedProperty("playerId");
        _playerId = playerId;
    }

    /**
     * [get] DAY: {NotNull, INT UNSIGNED(10), FK to village_day} <br>
     * 何日目か
     * @return The value of the column 'DAY'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDay() {
        checkSpecifiedProperty("day");
        return _day;
    }

    /**
     * [set] DAY: {NotNull, INT UNSIGNED(10), FK to village_day} <br>
     * 何日目か
     * @param day The value of the column 'DAY'. (basically NotNull if update: for the constraint)
     */
    public void setDay(Integer day) {
        registerModifiedProperty("day");
        _day = day;
    }

    /**
     * [get] MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType} <br>
     * メッセージ種別コード
     * @return The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageTypeCode() {
        checkSpecifiedProperty("messageTypeCode");
        return convertEmptyToNull(_messageTypeCode);
    }

    /**
     * [set] MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType} <br>
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
     * [get] MESSAGE_CONTENT: {NotNull, VARCHAR(10000)} <br>
     * メッセージ内容
     * @return The value of the column 'MESSAGE_CONTENT'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageContent() {
        checkSpecifiedProperty("messageContent");
        return convertEmptyToNull(_messageContent);
    }

    /**
     * [set] MESSAGE_CONTENT: {NotNull, VARCHAR(10000)} <br>
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
}
