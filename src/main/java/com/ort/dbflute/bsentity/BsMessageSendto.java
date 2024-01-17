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
 * The entity of MESSAGE_SENDTO as TABLE. <br>
 * メッセージ送信先
 * <pre>
 * [primary-key]
 *     MESSAGE_REPLYTO_ID
 *
 * [column]
 *     MESSAGE_REPLYTO_ID, MESSAGE_ID, VILLAGE_PLAYER_ID, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     MESSAGE_REPLYTO_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     MESSAGE, VILLAGE_PLAYER
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     message, villagePlayer
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer messageReplytoId = entity.getMessageReplytoId();
 * Integer messageId = entity.getMessageId();
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setMessageReplytoId(messageReplytoId);
 * entity.setMessageId(messageId);
 * entity.setVillagePlayerId(villagePlayerId);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsMessageSendto extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** MESSAGE_REPLYTO_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _messageReplytoId;

    /** MESSAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to MESSAGE} */
    protected Integer _messageId;

    /** VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER} */
    protected Integer _villagePlayerId;

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
        return "MESSAGE_SENDTO";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_messageReplytoId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** MESSAGE by my MESSAGE_ID, named 'message'. */
    protected OptionalEntity<Message> _message;

    /**
     * [get] MESSAGE by my MESSAGE_ID, named 'message'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'message'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Message> getMessage() {
        if (_message == null) { _message = OptionalEntity.relationEmpty(this, "message"); }
        return _message;
    }

    /**
     * [set] MESSAGE by my MESSAGE_ID, named 'message'.
     * @param message The entity of foreign property 'message'. (NullAllowed)
     */
    public void setMessage(OptionalEntity<Message> message) {
        _message = message;
    }

    /** VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayer'. */
    protected OptionalEntity<VillagePlayer> _villagePlayer;

    /**
     * [get] VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayer'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villagePlayer'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillagePlayer> getVillagePlayer() {
        if (_villagePlayer == null) { _villagePlayer = OptionalEntity.relationEmpty(this, "villagePlayer"); }
        return _villagePlayer;
    }

    /**
     * [set] VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayer'.
     * @param villagePlayer The entity of foreign property 'villagePlayer'. (NullAllowed)
     */
    public void setVillagePlayer(OptionalEntity<VillagePlayer> villagePlayer) {
        _villagePlayer = villagePlayer;
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
        if (obj instanceof BsMessageSendto) {
            BsMessageSendto other = (BsMessageSendto)obj;
            if (!xSV(_messageReplytoId, other._messageReplytoId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _messageReplytoId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_message != null && _message.isPresent())
        { sb.append(li).append(xbRDS(_message, "message")); }
        if (_villagePlayer != null && _villagePlayer.isPresent())
        { sb.append(li).append(xbRDS(_villagePlayer, "villagePlayer")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_messageReplytoId));
        sb.append(dm).append(xfND(_messageId));
        sb.append(dm).append(xfND(_villagePlayerId));
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
        if (_message != null && _message.isPresent())
        { sb.append(dm).append("message"); }
        if (_villagePlayer != null && _villagePlayer.isPresent())
        { sb.append(dm).append("villagePlayer"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public MessageSendto clone() {
        return (MessageSendto)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] MESSAGE_REPLYTO_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * メッセージ送信先ID
     * @return The value of the column 'MESSAGE_REPLYTO_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getMessageReplytoId() {
        checkSpecifiedProperty("messageReplytoId");
        return _messageReplytoId;
    }

    /**
     * [set] MESSAGE_REPLYTO_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * メッセージ送信先ID
     * @param messageReplytoId The value of the column 'MESSAGE_REPLYTO_ID'. (basically NotNull if update: for the constraint)
     */
    public void setMessageReplytoId(Integer messageReplytoId) {
        registerModifiedProperty("messageReplytoId");
        _messageReplytoId = messageReplytoId;
    }

    /**
     * [get] MESSAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to MESSAGE} <br>
     * メッセージID
     * @return The value of the column 'MESSAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getMessageId() {
        checkSpecifiedProperty("messageId");
        return _messageId;
    }

    /**
     * [set] MESSAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to MESSAGE} <br>
     * メッセージID
     * @param messageId The value of the column 'MESSAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setMessageId(Integer messageId) {
        registerModifiedProperty("messageId");
        _messageId = messageId;
    }

    /**
     * [get] VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER} <br>
     * 村参加者ID
     * @return The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillagePlayerId() {
        checkSpecifiedProperty("villagePlayerId");
        return _villagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER} <br>
     * 村参加者ID
     * @param villagePlayerId The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerId(Integer villagePlayerId) {
        registerModifiedProperty("villagePlayerId");
        _villagePlayerId = villagePlayerId;
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
