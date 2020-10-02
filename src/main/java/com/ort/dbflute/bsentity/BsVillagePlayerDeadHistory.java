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
 * The entity of VILLAGE_PLAYER_DEAD_HISTORY as TABLE. <br>
 * 村参加者生死履歴
 * <pre>
 * [primary-key]
 *     VILLAGE_PLAYER_DEAD_HISTORY_ID
 *
 * [column]
 *     VILLAGE_PLAYER_DEAD_HISTORY_ID, VILLAGE_PLAYER_ID, DAY, IS_DEAD, DEAD_REASON_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_DEAD_HISTORY_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     DEAD_REASON, VILLAGE_PLAYER
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     deadReason, villagePlayer
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villagePlayerDeadHistoryId = entity.getVillagePlayerDeadHistoryId();
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * Integer day = entity.getDay();
 * Boolean isDead = entity.getIsDead();
 * String deadReasonCode = entity.getDeadReasonCode();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillagePlayerDeadHistoryId(villagePlayerDeadHistoryId);
 * entity.setVillagePlayerId(villagePlayerId);
 * entity.setDay(day);
 * entity.setIsDead(isDead);
 * entity.setDeadReasonCode(deadReasonCode);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillagePlayerDeadHistory extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_PLAYER_DEAD_HISTORY_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _villagePlayerDeadHistoryId;

    /** VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER} */
    protected Integer _villagePlayerId;

    /** DAY: {NotNull, INT UNSIGNED(10)} */
    protected Integer _day;

    /** IS_DEAD: {NotNull, BIT, classification=Flg} */
    protected Boolean _isDead;

    /** DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} */
    protected String _deadReasonCode;

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
        return "VILLAGE_PLAYER_DEAD_HISTORY";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villagePlayerDeadHistoryId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of isDead as the classification of Flg. <br>
     * IS_DEAD: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsDeadAsFlg() {
        return CDef.Flg.codeOf(getIsDead());
    }

    /**
     * Set the value of isDead as the classification of Flg. <br>
     * IS_DEAD: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsDeadAsFlg(CDef.Flg cdef) {
        setIsDead(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of deadReasonCode as the classification of DeadReason. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * 死亡理由
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.DeadReason getDeadReasonCodeAsDeadReason() {
        return CDef.DeadReason.codeOf(getDeadReasonCode());
    }

    /**
     * Set the value of deadReasonCode as the classification of DeadReason. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * 死亡理由
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setDeadReasonCodeAsDeadReason(CDef.DeadReason cdef) {
        setDeadReasonCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of isDead as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsDead_True() {
        setIsDeadAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isDead as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsDead_False() {
        setIsDeadAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of deadReasonCode as 襲撃 (ATTACK). <br>
     * 襲撃
     */
    public void setDeadReasonCode_襲撃() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.襲撃);
    }

    /**
     * Set the value of deadReasonCode as 爆死 (BOMBED). <br>
     * 爆死
     */
    public void setDeadReasonCode_爆死() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.爆死);
    }

    /**
     * Set the value of deadReasonCode as 呪殺 (DIVINED). <br>
     * 呪殺
     */
    public void setDeadReasonCode_呪殺() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.呪殺);
    }

    /**
     * Set the value of deadReasonCode as 処刑 (EXECUTE). <br>
     * 処刑
     */
    public void setDeadReasonCode_処刑() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.処刑);
    }

    /**
     * Set the value of deadReasonCode as 突然 (SUDDON). <br>
     * 突然
     */
    public void setDeadReasonCode_突然() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.突然);
    }

    /**
     * Set the value of deadReasonCode as 後追 (SUICIDE). <br>
     * 後追
     */
    public void setDeadReasonCode_後追() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.後追);
    }

    /**
     * Set the value of deadReasonCode as 罠死 (TRAPPED). <br>
     * 罠死
     */
    public void setDeadReasonCode_罠死() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.罠死);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of isDead True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsDeadTrue() {
        CDef.Flg cdef = getIsDeadAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isDead False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsDeadFalse() {
        CDef.Flg cdef = getIsDeadAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of deadReasonCode 襲撃? <br>
     * 襲撃
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode襲撃() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.襲撃) : false;
    }

    /**
     * Is the value of deadReasonCode 爆死? <br>
     * 爆死
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode爆死() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.爆死) : false;
    }

    /**
     * Is the value of deadReasonCode 呪殺? <br>
     * 呪殺
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode呪殺() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.呪殺) : false;
    }

    /**
     * Is the value of deadReasonCode 処刑? <br>
     * 処刑
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode処刑() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.処刑) : false;
    }

    /**
     * Is the value of deadReasonCode 突然? <br>
     * 突然
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode突然() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.突然) : false;
    }

    /**
     * Is the value of deadReasonCode 後追? <br>
     * 後追
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode後追() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.後追) : false;
    }

    /**
     * Is the value of deadReasonCode 罠死? <br>
     * 罠死
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode罠死() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.罠死) : false;
    }

    /**
     * 無惨 <br>
     * The group elements:[襲撃, 呪殺, 罠死, 爆死]
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode_Miserable() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null && cdef.isMiserable();
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'isDead' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsDeadAlias() {
        CDef.Flg cdef = getIsDeadAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** DEAD_REASON by my DEAD_REASON_CODE, named 'deadReason'. */
    protected OptionalEntity<DeadReason> _deadReason;

    /**
     * [get] DEAD_REASON by my DEAD_REASON_CODE, named 'deadReason'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'deadReason'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<DeadReason> getDeadReason() {
        if (_deadReason == null) { _deadReason = OptionalEntity.relationEmpty(this, "deadReason"); }
        return _deadReason;
    }

    /**
     * [set] DEAD_REASON by my DEAD_REASON_CODE, named 'deadReason'.
     * @param deadReason The entity of foreign property 'deadReason'. (NullAllowed)
     */
    public void setDeadReason(OptionalEntity<DeadReason> deadReason) {
        _deadReason = deadReason;
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
        if (obj instanceof BsVillagePlayerDeadHistory) {
            BsVillagePlayerDeadHistory other = (BsVillagePlayerDeadHistory)obj;
            if (!xSV(_villagePlayerDeadHistoryId, other._villagePlayerDeadHistoryId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villagePlayerDeadHistoryId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_deadReason != null && _deadReason.isPresent())
        { sb.append(li).append(xbRDS(_deadReason, "deadReason")); }
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
        sb.append(dm).append(xfND(_villagePlayerDeadHistoryId));
        sb.append(dm).append(xfND(_villagePlayerId));
        sb.append(dm).append(xfND(_day));
        sb.append(dm).append(xfND(_isDead));
        sb.append(dm).append(xfND(_deadReasonCode));
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
        if (_deadReason != null && _deadReason.isPresent())
        { sb.append(dm).append("deadReason"); }
        if (_villagePlayer != null && _villagePlayer.isPresent())
        { sb.append(dm).append("villagePlayer"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillagePlayerDeadHistory clone() {
        return (VillagePlayerDeadHistory)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_PLAYER_DEAD_HISTORY_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者生死履歴ID
     * @return The value of the column 'VILLAGE_PLAYER_DEAD_HISTORY_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillagePlayerDeadHistoryId() {
        checkSpecifiedProperty("villagePlayerDeadHistoryId");
        return _villagePlayerDeadHistoryId;
    }

    /**
     * [set] VILLAGE_PLAYER_DEAD_HISTORY_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者生死履歴ID
     * @param villagePlayerDeadHistoryId The value of the column 'VILLAGE_PLAYER_DEAD_HISTORY_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerDeadHistoryId(Integer villagePlayerDeadHistoryId) {
        registerModifiedProperty("villagePlayerDeadHistoryId");
        _villagePlayerDeadHistoryId = villagePlayerDeadHistoryId;
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
     * [get] IS_DEAD: {NotNull, BIT, classification=Flg} <br>
     * 死亡か : falseの場合は復活
     * @return The value of the column 'IS_DEAD'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsDead() {
        checkSpecifiedProperty("isDead");
        return _isDead;
    }

    /**
     * [set] IS_DEAD: {NotNull, BIT, classification=Flg} <br>
     * 死亡か : falseの場合は復活
     * @param isDead The value of the column 'IS_DEAD'. (basically NotNull if update: for the constraint)
     */
    public void setIsDead(Boolean isDead) {
        checkClassificationCode("IS_DEAD", CDef.DefMeta.Flg, isDead);
        registerModifiedProperty("isDead");
        _isDead = isDead;
    }

    /**
     * [get] DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * 死亡理由コード
     * @return The value of the column 'DEAD_REASON_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getDeadReasonCode() {
        checkSpecifiedProperty("deadReasonCode");
        return convertEmptyToNull(_deadReasonCode);
    }

    /**
     * [set] DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * 死亡理由コード
     * @param deadReasonCode The value of the column 'DEAD_REASON_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setDeadReasonCode(String deadReasonCode) {
        checkClassificationCode("DEAD_REASON_CODE", CDef.DefMeta.DeadReason, deadReasonCode);
        registerModifiedProperty("deadReasonCode");
        _deadReasonCode = deadReasonCode;
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
     * @param deadReasonCode The value of the column 'DEAD_REASON_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void mynativeMappingDeadReasonCode(String deadReasonCode) {
        setDeadReasonCode(deadReasonCode);
    }
}
