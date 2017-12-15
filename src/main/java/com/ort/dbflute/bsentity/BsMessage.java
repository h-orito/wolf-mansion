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
import com.ort.dbflute.exentity.*;

/**
 * The entity of MESSAGE as TABLE. <br>
 * メッセージ
 * <pre>
 * [primary-key]
 *     MESSAGE_ID
 *
 * [column]
 *     MESSAGE_ID, VILLAGE_ID, VILLAGE_PLAYER_ID, PLAYER_ID, TO_VILLAGE_PLAYER_ID, DAY, MESSAGE_TYPE_CODE, MESSAGE_NUMBER, MESSAGE_CONTENT, MESSAGE_DATETIME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     MESSAGE_TYPE, PLAYER, VILLAGE_PLAYER, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     messageType, player, villagePlayerByToVillagePlayerId, village, villagePlayerByVillagePlayerId
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer messageId = entity.getMessageId();
 * Integer villageId = entity.getVillageId();
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * Integer playerId = entity.getPlayerId();
 * Integer toVillagePlayerId = entity.getToVillagePlayerId();
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
 * entity.setPlayerId(playerId);
 * entity.setToVillagePlayerId(toVillagePlayerId);
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

    /** VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} */
    protected Integer _villageId;

    /** VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} */
    protected Integer _villagePlayerId;

    /** PLAYER_ID: {IX, INT UNSIGNED(10), FK to player} */
    protected Integer _playerId;

    /** TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} */
    protected Integer _toVillagePlayerId;

    /** DAY: {NotNull, INT UNSIGNED(10)} */
    protected Integer _day;

    /** MESSAGE_TYPE_CODE: {IX, NotNull, VARCHAR(10), FK to message_type} */
    protected String _messageTypeCode;

    /** MESSAGE_NUMBER: {INT UNSIGNED(10)} */
    protected Integer _messageNumber;

    /** MESSAGE_CONTENT: {NotNull, VARCHAR(1000)} */
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

    /** VILLAGE by my VILLAGE_ID, named 'village'. */
    protected OptionalEntity<Village> _village;

    /**
     * [get] VILLAGE by my VILLAGE_ID, named 'village'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'village'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Village> getVillage() {
        if (_village == null) { _village = OptionalEntity.relationEmpty(this, "village"); }
        return _village;
    }

    /**
     * [set] VILLAGE by my VILLAGE_ID, named 'village'.
     * @param village The entity of foreign property 'village'. (NullAllowed)
     */
    public void setVillage(OptionalEntity<Village> village) {
        _village = village;
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
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
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
        sb.append(dm).append(xfND(_playerId));
        sb.append(dm).append(xfND(_toVillagePlayerId));
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
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
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
     * [get] VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} <br>
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
     * [get] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * どの村参加者に
     * @return The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getToVillagePlayerId() {
        checkSpecifiedProperty("toVillagePlayerId");
        return _toVillagePlayerId;
    }

    /**
     * [set] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * どの村参加者に
     * @param toVillagePlayerId The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setToVillagePlayerId(Integer toVillagePlayerId) {
        registerModifiedProperty("toVillagePlayerId");
        _toVillagePlayerId = toVillagePlayerId;
    }

    /**
     * [get] DAY: {NotNull, INT UNSIGNED(10)} <br>
     * 何日目か
     * @return The value of the column 'DAY'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDay() {
        checkSpecifiedProperty("day");
        return _day;
    }

    /**
     * [set] DAY: {NotNull, INT UNSIGNED(10)} <br>
     * 何日目か
     * @param day The value of the column 'DAY'. (basically NotNull if update: for the constraint)
     */
    public void setDay(Integer day) {
        registerModifiedProperty("day");
        _day = day;
    }

    /**
     * [get] MESSAGE_TYPE_CODE: {IX, NotNull, VARCHAR(10), FK to message_type} <br>
     * メッセージ種別コード
     * @return The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageTypeCode() {
        checkSpecifiedProperty("messageTypeCode");
        return convertEmptyToNull(_messageTypeCode);
    }

    /**
     * [set] MESSAGE_TYPE_CODE: {IX, NotNull, VARCHAR(10), FK to message_type} <br>
     * メッセージ種別コード
     * @param messageTypeCode The value of the column 'MESSAGE_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setMessageTypeCode(String messageTypeCode) {
        registerModifiedProperty("messageTypeCode");
        _messageTypeCode = messageTypeCode;
    }

    /**
     * [get] MESSAGE_NUMBER: {INT UNSIGNED(10)} <br>
     * メッセージ番号
     * @return The value of the column 'MESSAGE_NUMBER'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMessageNumber() {
        checkSpecifiedProperty("messageNumber");
        return _messageNumber;
    }

    /**
     * [set] MESSAGE_NUMBER: {INT UNSIGNED(10)} <br>
     * メッセージ番号
     * @param messageNumber The value of the column 'MESSAGE_NUMBER'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMessageNumber(Integer messageNumber) {
        registerModifiedProperty("messageNumber");
        _messageNumber = messageNumber;
    }

    /**
     * [get] MESSAGE_CONTENT: {NotNull, VARCHAR(1000)} <br>
     * メッセージ内容
     * @return The value of the column 'MESSAGE_CONTENT'. (basically NotNull if selected: for the constraint)
     */
    public String getMessageContent() {
        checkSpecifiedProperty("messageContent");
        return convertEmptyToNull(_messageContent);
    }

    /**
     * [set] MESSAGE_CONTENT: {NotNull, VARCHAR(1000)} <br>
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
}
