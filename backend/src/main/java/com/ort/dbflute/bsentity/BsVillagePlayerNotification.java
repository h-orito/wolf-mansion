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
 * The entity of VILLAGE_PLAYER_NOTIFICATION as TABLE. <br>
 * 村参加者通知
 * <pre>
 * [primary-key]
 *     VILLAGE_PLAYER_ID
 *
 * [column]
 *     VILLAGE_PLAYER_ID, DISCORD_WEBHOOK_URL, VILLAGE_START, VILLAGE_DAYCHANGE, VILLAGE_EPILOGUE, RECEIVE_SECRET_SAY, RECEIVE_ABILITY_SAY, RECEIVE_ANCHOR_SAY, KEYWORD, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VILLAGE_PLAYER
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     villagePlayer
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * String discordWebhookUrl = entity.getDiscordWebhookUrl();
 * Boolean villageStart = entity.getVillageStart();
 * Boolean villageDaychange = entity.getVillageDaychange();
 * Boolean villageEpilogue = entity.getVillageEpilogue();
 * Boolean receiveSecretSay = entity.getReceiveSecretSay();
 * Boolean receiveAbilitySay = entity.getReceiveAbilitySay();
 * Boolean receiveAnchorSay = entity.getReceiveAnchorSay();
 * String keyword = entity.getKeyword();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillagePlayerId(villagePlayerId);
 * entity.setDiscordWebhookUrl(discordWebhookUrl);
 * entity.setVillageStart(villageStart);
 * entity.setVillageDaychange(villageDaychange);
 * entity.setVillageEpilogue(villageEpilogue);
 * entity.setReceiveSecretSay(receiveSecretSay);
 * entity.setReceiveAbilitySay(receiveAbilitySay);
 * entity.setReceiveAnchorSay(receiveAnchorSay);
 * entity.setKeyword(keyword);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillagePlayerNotification extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_PLAYER_ID: {PK, NotNull, INT UNSIGNED(10), FK to village_player} */
    protected Integer _villagePlayerId;

    /** DISCORD_WEBHOOK_URL: {NotNull, VARCHAR(1000)} */
    protected String _discordWebhookUrl;

    /** VILLAGE_START: {NotNull, BIT} */
    protected Boolean _villageStart;

    /** VILLAGE_DAYCHANGE: {NotNull, BIT} */
    protected Boolean _villageDaychange;

    /** VILLAGE_EPILOGUE: {NotNull, BIT} */
    protected Boolean _villageEpilogue;

    /** RECEIVE_SECRET_SAY: {NotNull, BIT} */
    protected Boolean _receiveSecretSay;

    /** RECEIVE_ABILITY_SAY: {NotNull, BIT} */
    protected Boolean _receiveAbilitySay;

    /** RECEIVE_ANCHOR_SAY: {NotNull, BIT} */
    protected Boolean _receiveAnchorSay;

    /** KEYWORD: {VARCHAR(255)} */
    protected String _keyword;

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
        return "village_player_notification";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villagePlayerId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
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
        if (obj instanceof BsVillagePlayerNotification) {
            BsVillagePlayerNotification other = (BsVillagePlayerNotification)obj;
            if (!xSV(_villagePlayerId, other._villagePlayerId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villagePlayerId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
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
        sb.append(dm).append(xfND(_villagePlayerId));
        sb.append(dm).append(xfND(_discordWebhookUrl));
        sb.append(dm).append(xfND(_villageStart));
        sb.append(dm).append(xfND(_villageDaychange));
        sb.append(dm).append(xfND(_villageEpilogue));
        sb.append(dm).append(xfND(_receiveSecretSay));
        sb.append(dm).append(xfND(_receiveAbilitySay));
        sb.append(dm).append(xfND(_receiveAnchorSay));
        sb.append(dm).append(xfND(_keyword));
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
        if (_villagePlayer != null && _villagePlayer.isPresent())
        { sb.append(dm).append("villagePlayer"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillagePlayerNotification clone() {
        return (VillagePlayerNotification)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_PLAYER_ID: {PK, NotNull, INT UNSIGNED(10), FK to village_player} <br>
     * 村参加者ID
     * @return The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillagePlayerId() {
        checkSpecifiedProperty("villagePlayerId");
        return _villagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER_ID: {PK, NotNull, INT UNSIGNED(10), FK to village_player} <br>
     * 村参加者ID
     * @param villagePlayerId The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerId(Integer villagePlayerId) {
        registerModifiedProperty("villagePlayerId");
        _villagePlayerId = villagePlayerId;
    }

    /**
     * [get] DISCORD_WEBHOOK_URL: {NotNull, VARCHAR(1000)} <br>
     * DiscordWebhookUrl
     * @return The value of the column 'DISCORD_WEBHOOK_URL'. (basically NotNull if selected: for the constraint)
     */
    public String getDiscordWebhookUrl() {
        checkSpecifiedProperty("discordWebhookUrl");
        return convertEmptyToNull(_discordWebhookUrl);
    }

    /**
     * [set] DISCORD_WEBHOOK_URL: {NotNull, VARCHAR(1000)} <br>
     * DiscordWebhookUrl
     * @param discordWebhookUrl The value of the column 'DISCORD_WEBHOOK_URL'. (basically NotNull if update: for the constraint)
     */
    public void setDiscordWebhookUrl(String discordWebhookUrl) {
        registerModifiedProperty("discordWebhookUrl");
        _discordWebhookUrl = discordWebhookUrl;
    }

    /**
     * [get] VILLAGE_START: {NotNull, BIT} <br>
     * 村が開始した際通知する
     * @return The value of the column 'VILLAGE_START'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getVillageStart() {
        checkSpecifiedProperty("villageStart");
        return _villageStart;
    }

    /**
     * [set] VILLAGE_START: {NotNull, BIT} <br>
     * 村が開始した際通知する
     * @param villageStart The value of the column 'VILLAGE_START'. (basically NotNull if update: for the constraint)
     */
    public void setVillageStart(Boolean villageStart) {
        registerModifiedProperty("villageStart");
        _villageStart = villageStart;
    }

    /**
     * [get] VILLAGE_DAYCHANGE: {NotNull, BIT} <br>
     * 村の日付が更新された際通知する
     * @return The value of the column 'VILLAGE_DAYCHANGE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getVillageDaychange() {
        checkSpecifiedProperty("villageDaychange");
        return _villageDaychange;
    }

    /**
     * [set] VILLAGE_DAYCHANGE: {NotNull, BIT} <br>
     * 村の日付が更新された際通知する
     * @param villageDaychange The value of the column 'VILLAGE_DAYCHANGE'. (basically NotNull if update: for the constraint)
     */
    public void setVillageDaychange(Boolean villageDaychange) {
        registerModifiedProperty("villageDaychange");
        _villageDaychange = villageDaychange;
    }

    /**
     * [get] VILLAGE_EPILOGUE: {NotNull, BIT} <br>
     * 村がエピローグを迎えた際通知する
     * @return The value of the column 'VILLAGE_EPILOGUE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getVillageEpilogue() {
        checkSpecifiedProperty("villageEpilogue");
        return _villageEpilogue;
    }

    /**
     * [set] VILLAGE_EPILOGUE: {NotNull, BIT} <br>
     * 村がエピローグを迎えた際通知する
     * @param villageEpilogue The value of the column 'VILLAGE_EPILOGUE'. (basically NotNull if update: for the constraint)
     */
    public void setVillageEpilogue(Boolean villageEpilogue) {
        registerModifiedProperty("villageEpilogue");
        _villageEpilogue = villageEpilogue;
    }

    /**
     * [get] RECEIVE_SECRET_SAY: {NotNull, BIT} <br>
     * 秘話を受け取った際通知する
     * @return The value of the column 'RECEIVE_SECRET_SAY'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getReceiveSecretSay() {
        checkSpecifiedProperty("receiveSecretSay");
        return _receiveSecretSay;
    }

    /**
     * [set] RECEIVE_SECRET_SAY: {NotNull, BIT} <br>
     * 秘話を受け取った際通知する
     * @param receiveSecretSay The value of the column 'RECEIVE_SECRET_SAY'. (basically NotNull if update: for the constraint)
     */
    public void setReceiveSecretSay(Boolean receiveSecretSay) {
        registerModifiedProperty("receiveSecretSay");
        _receiveSecretSay = receiveSecretSay;
    }

    /**
     * [get] RECEIVE_ABILITY_SAY: {NotNull, BIT} <br>
     * 役職窓発言を受け取った際通知する
     * @return The value of the column 'RECEIVE_ABILITY_SAY'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getReceiveAbilitySay() {
        checkSpecifiedProperty("receiveAbilitySay");
        return _receiveAbilitySay;
    }

    /**
     * [set] RECEIVE_ABILITY_SAY: {NotNull, BIT} <br>
     * 役職窓発言を受け取った際通知する
     * @param receiveAbilitySay The value of the column 'RECEIVE_ABILITY_SAY'. (basically NotNull if update: for the constraint)
     */
    public void setReceiveAbilitySay(Boolean receiveAbilitySay) {
        registerModifiedProperty("receiveAbilitySay");
        _receiveAbilitySay = receiveAbilitySay;
    }

    /**
     * [get] RECEIVE_ANCHOR_SAY: {NotNull, BIT} <br>
     * アンカーで指定された際通知する
     * @return The value of the column 'RECEIVE_ANCHOR_SAY'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getReceiveAnchorSay() {
        checkSpecifiedProperty("receiveAnchorSay");
        return _receiveAnchorSay;
    }

    /**
     * [set] RECEIVE_ANCHOR_SAY: {NotNull, BIT} <br>
     * アンカーで指定された際通知する
     * @param receiveAnchorSay The value of the column 'RECEIVE_ANCHOR_SAY'. (basically NotNull if update: for the constraint)
     */
    public void setReceiveAnchorSay(Boolean receiveAnchorSay) {
        registerModifiedProperty("receiveAnchorSay");
        _receiveAnchorSay = receiveAnchorSay;
    }

    /**
     * [get] KEYWORD: {VARCHAR(255)} <br>
     * キーワードを含む際通知する
     * @return The value of the column 'KEYWORD'. (NullAllowed even if selected: for no constraint)
     */
    public String getKeyword() {
        checkSpecifiedProperty("keyword");
        return convertEmptyToNull(_keyword);
    }

    /**
     * [set] KEYWORD: {VARCHAR(255)} <br>
     * キーワードを含む際通知する
     * @param keyword The value of the column 'KEYWORD'. (NullAllowed: null update allowed for no constraint)
     */
    public void setKeyword(String keyword) {
        registerModifiedProperty("keyword");
        _keyword = keyword;
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
