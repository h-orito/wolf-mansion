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
 * The entity of VILLAGE_PLAYER as TABLE. <br>
 * 村参加者
 * <pre>
 * [primary-key]
 *     VILLAGE_PLAYER_ID
 *
 * [column]
 *     VILLAGE_PLAYER_ID, VILLAGE_ID, PLAYER_ID, CHARA_ID, SKILL_CODE, REQUEST_SKILL_CODE, ROOM_NUMBER, IS_DEAD, IS_SPECTATOR, DEAD_REASON_CODE, DEAD_DAY, IS_GONE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     CHARA, DEAD_REASON, PLAYER, SKILL, VILLAGE
 *
 * [referrer table]
 *     MESSAGE
 *
 * [foreign property]
 *     chara, deadReason, player, skillByRequestSkillCode, skillBySkillCode, village
 *
 * [referrer property]
 *     messageList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * Integer villageId = entity.getVillageId();
 * Integer playerId = entity.getPlayerId();
 * Integer charaId = entity.getCharaId();
 * String skillCode = entity.getSkillCode();
 * String requestSkillCode = entity.getRequestSkillCode();
 * Integer roomNumber = entity.getRoomNumber();
 * Boolean isDead = entity.getIsDead();
 * Boolean isSpectator = entity.getIsSpectator();
 * String deadReasonCode = entity.getDeadReasonCode();
 * Integer deadDay = entity.getDeadDay();
 * Boolean isGone = entity.getIsGone();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillagePlayerId(villagePlayerId);
 * entity.setVillageId(villageId);
 * entity.setPlayerId(playerId);
 * entity.setCharaId(charaId);
 * entity.setSkillCode(skillCode);
 * entity.setRequestSkillCode(requestSkillCode);
 * entity.setRoomNumber(roomNumber);
 * entity.setIsDead(isDead);
 * entity.setIsSpectator(isSpectator);
 * entity.setDeadReasonCode(deadReasonCode);
 * entity.setDeadDay(deadDay);
 * entity.setIsGone(isGone);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillagePlayer extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _villagePlayerId;

    /** VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} */
    protected Integer _villageId;

    /** PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player} */
    protected Integer _playerId;

    /** CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara} */
    protected Integer _charaId;

    /** SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} */
    protected String _skillCode;

    /** REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} */
    protected String _requestSkillCode;

    /** ROOM_NUMBER: {INT UNSIGNED(10)} */
    protected Integer _roomNumber;

    /** IS_DEAD: {NotNull, BIT, classification=Flg} */
    protected Boolean _isDead;

    /** IS_SPECTATOR: {NotNull, BIT, classification=Flg} */
    protected Boolean _isSpectator;

    /** DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} */
    protected String _deadReasonCode;

    /** DEAD_DAY: {INT UNSIGNED(10)} */
    protected Integer _deadDay;

    /** IS_GONE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isGone;

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
        return "village_player";
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
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of skillCode as the classification of Skill. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Skill getSkillCodeAsSkill() {
        return CDef.Skill.codeOf(getSkillCode());
    }

    /**
     * Set the value of skillCode as the classification of Skill. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setSkillCodeAsSkill(CDef.Skill cdef) {
        setSkillCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of requestSkillCode as the classification of Skill. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Skill getRequestSkillCodeAsSkill() {
        return CDef.Skill.codeOf(getRequestSkillCode());
    }

    /**
     * Set the value of requestSkillCode as the classification of Skill. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setRequestSkillCodeAsSkill(CDef.Skill cdef) {
        setRequestSkillCode(cdef != null ? cdef.code() : null);
    }

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
     * Get the value of isSpectator as the classification of Flg. <br>
     * IS_SPECTATOR: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsSpectatorAsFlg() {
        return CDef.Flg.codeOf(getIsSpectator());
    }

    /**
     * Set the value of isSpectator as the classification of Flg. <br>
     * IS_SPECTATOR: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsSpectatorAsFlg(CDef.Flg cdef) {
        setIsSpectator(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of deadReasonCode as the classification of DeadReason. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.DeadReason getDeadReasonCodeAsDeadReason() {
        return CDef.DeadReason.codeOf(getDeadReasonCode());
    }

    /**
     * Set the value of deadReasonCode as the classification of DeadReason. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setDeadReasonCodeAsDeadReason(CDef.DeadReason cdef) {
        setDeadReasonCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of isGone as the classification of Flg. <br>
     * IS_GONE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsGoneAsFlg() {
        return CDef.Flg.codeOf(getIsGone());
    }

    /**
     * Set the value of isGone as the classification of Flg. <br>
     * IS_GONE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsGoneAsFlg(CDef.Flg cdef) {
        setIsGone(cdef != null ? toBoolean(cdef.code()) : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of skillCode as C国狂人 (CMADMAN). <br>
     * C国狂人
     */
    public void setSkillCode_C国狂人() {
        setSkillCodeAsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Set the value of skillCode as 魔神官 (EVILMEDIUM). <br>
     * 魔神官
     */
    public void setSkillCode_魔神官() {
        setSkillCodeAsSkill(CDef.Skill.魔神官);
    }

    /**
     * Set the value of skillCode as 妖狐 (FOX). <br>
     * 妖狐
     */
    public void setSkillCode_妖狐() {
        setSkillCodeAsSkill(CDef.Skill.妖狐);
    }

    /**
     * Set the value of skillCode as 導師 (GURU). <br>
     * 導師
     */
    public void setSkillCode_導師() {
        setSkillCodeAsSkill(CDef.Skill.導師);
    }

    /**
     * Set the value of skillCode as 狩人 (HUNTER). <br>
     * 狩人
     */
    public void setSkillCode_狩人() {
        setSkillCodeAsSkill(CDef.Skill.狩人);
    }

    /**
     * Set the value of skillCode as おまかせ (LEFTOVER). <br>
     * おまかせ
     */
    public void setSkillCode_おまかせ() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Set the value of skillCode as 狂人 (MADMAN). <br>
     * 狂人
     */
    public void setSkillCode_狂人() {
        setSkillCodeAsSkill(CDef.Skill.狂人);
    }

    /**
     * Set the value of skillCode as 共鳴者 (MASON). <br>
     * 共鳴者
     */
    public void setSkillCode_共鳴者() {
        setSkillCodeAsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Set the value of skillCode as 霊能者 (MEDIUM). <br>
     * 霊能者
     */
    public void setSkillCode_霊能者() {
        setSkillCodeAsSkill(CDef.Skill.霊能者);
    }

    /**
     * Set the value of skillCode as 占い師 (SEER). <br>
     * 占い師
     */
    public void setSkillCode_占い師() {
        setSkillCodeAsSkill(CDef.Skill.占い師);
    }

    /**
     * Set the value of skillCode as 村人 (VILLAGER). <br>
     * 村人
     */
    public void setSkillCode_村人() {
        setSkillCodeAsSkill(CDef.Skill.村人);
    }

    /**
     * Set the value of skillCode as 人狼 (WEREWOLF). <br>
     * 人狼
     */
    public void setSkillCode_人狼() {
        setSkillCodeAsSkill(CDef.Skill.人狼);
    }

    /**
     * Set the value of skillCode as 賢者 (WISE). <br>
     * 賢者
     */
    public void setSkillCode_賢者() {
        setSkillCodeAsSkill(CDef.Skill.賢者);
    }

    /**
     * Set the value of requestSkillCode as C国狂人 (CMADMAN). <br>
     * C国狂人
     */
    public void setRequestSkillCode_C国狂人() {
        setRequestSkillCodeAsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Set the value of requestSkillCode as 魔神官 (EVILMEDIUM). <br>
     * 魔神官
     */
    public void setRequestSkillCode_魔神官() {
        setRequestSkillCodeAsSkill(CDef.Skill.魔神官);
    }

    /**
     * Set the value of requestSkillCode as 妖狐 (FOX). <br>
     * 妖狐
     */
    public void setRequestSkillCode_妖狐() {
        setRequestSkillCodeAsSkill(CDef.Skill.妖狐);
    }

    /**
     * Set the value of requestSkillCode as 導師 (GURU). <br>
     * 導師
     */
    public void setRequestSkillCode_導師() {
        setRequestSkillCodeAsSkill(CDef.Skill.導師);
    }

    /**
     * Set the value of requestSkillCode as 狩人 (HUNTER). <br>
     * 狩人
     */
    public void setRequestSkillCode_狩人() {
        setRequestSkillCodeAsSkill(CDef.Skill.狩人);
    }

    /**
     * Set the value of requestSkillCode as おまかせ (LEFTOVER). <br>
     * おまかせ
     */
    public void setRequestSkillCode_おまかせ() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Set the value of requestSkillCode as 狂人 (MADMAN). <br>
     * 狂人
     */
    public void setRequestSkillCode_狂人() {
        setRequestSkillCodeAsSkill(CDef.Skill.狂人);
    }

    /**
     * Set the value of requestSkillCode as 共鳴者 (MASON). <br>
     * 共鳴者
     */
    public void setRequestSkillCode_共鳴者() {
        setRequestSkillCodeAsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Set the value of requestSkillCode as 霊能者 (MEDIUM). <br>
     * 霊能者
     */
    public void setRequestSkillCode_霊能者() {
        setRequestSkillCodeAsSkill(CDef.Skill.霊能者);
    }

    /**
     * Set the value of requestSkillCode as 占い師 (SEER). <br>
     * 占い師
     */
    public void setRequestSkillCode_占い師() {
        setRequestSkillCodeAsSkill(CDef.Skill.占い師);
    }

    /**
     * Set the value of requestSkillCode as 村人 (VILLAGER). <br>
     * 村人
     */
    public void setRequestSkillCode_村人() {
        setRequestSkillCodeAsSkill(CDef.Skill.村人);
    }

    /**
     * Set the value of requestSkillCode as 人狼 (WEREWOLF). <br>
     * 人狼
     */
    public void setRequestSkillCode_人狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.人狼);
    }

    /**
     * Set the value of requestSkillCode as 賢者 (WISE). <br>
     * 賢者
     */
    public void setRequestSkillCode_賢者() {
        setRequestSkillCodeAsSkill(CDef.Skill.賢者);
    }

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
     * Set the value of isSpectator as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsSpectator_True() {
        setIsSpectatorAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isSpectator as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsSpectator_False() {
        setIsSpectatorAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of deadReasonCode as 襲撃 (ATTACK). <br>
     * 襲撃
     */
    public void setDeadReasonCode_襲撃() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.襲撃);
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
     * Set the value of isGone as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsGone_True() {
        setIsGoneAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isGone as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsGone_False() {
        setIsGoneAsFlg(CDef.Flg.False);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of skillCode C国狂人? <br>
     * C国狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeC国狂人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.C国狂人) : false;
    }

    /**
     * Is the value of skillCode 魔神官? <br>
     * 魔神官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode魔神官() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.魔神官) : false;
    }

    /**
     * Is the value of skillCode 妖狐? <br>
     * 妖狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode妖狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妖狐) : false;
    }

    /**
     * Is the value of skillCode 導師? <br>
     * 導師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode導師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.導師) : false;
    }

    /**
     * Is the value of skillCode 狩人? <br>
     * 狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode狩人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狩人) : false;
    }

    /**
     * Is the value of skillCode おまかせ? <br>
     * おまかせ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ) : false;
    }

    /**
     * Is the value of skillCode 狂人? <br>
     * 狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode狂人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂人) : false;
    }

    /**
     * Is the value of skillCode 共鳴者? <br>
     * 共鳴者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode共鳴者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.共鳴者) : false;
    }

    /**
     * Is the value of skillCode 霊能者? <br>
     * 霊能者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode霊能者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.霊能者) : false;
    }

    /**
     * Is the value of skillCode 占い師? <br>
     * 占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode占い師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占い師) : false;
    }

    /**
     * Is the value of skillCode 村人? <br>
     * 村人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode村人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.村人) : false;
    }

    /**
     * Is the value of skillCode 人狼? <br>
     * 人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode人狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.人狼) : false;
    }

    /**
     * Is the value of skillCode 賢者? <br>
     * 賢者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode賢者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.賢者) : false;
    }

    /**
     * Is the value of requestSkillCode C国狂人? <br>
     * C国狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeC国狂人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.C国狂人) : false;
    }

    /**
     * Is the value of requestSkillCode 魔神官? <br>
     * 魔神官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode魔神官() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.魔神官) : false;
    }

    /**
     * Is the value of requestSkillCode 妖狐? <br>
     * 妖狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode妖狐() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妖狐) : false;
    }

    /**
     * Is the value of requestSkillCode 導師? <br>
     * 導師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode導師() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.導師) : false;
    }

    /**
     * Is the value of requestSkillCode 狩人? <br>
     * 狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode狩人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狩人) : false;
    }

    /**
     * Is the value of requestSkillCode おまかせ? <br>
     * おまかせ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ) : false;
    }

    /**
     * Is the value of requestSkillCode 狂人? <br>
     * 狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode狂人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂人) : false;
    }

    /**
     * Is the value of requestSkillCode 共鳴者? <br>
     * 共鳴者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode共鳴者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.共鳴者) : false;
    }

    /**
     * Is the value of requestSkillCode 霊能者? <br>
     * 霊能者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode霊能者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.霊能者) : false;
    }

    /**
     * Is the value of requestSkillCode 占い師? <br>
     * 占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode占い師() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占い師) : false;
    }

    /**
     * Is the value of requestSkillCode 村人? <br>
     * 村人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode村人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.村人) : false;
    }

    /**
     * Is the value of requestSkillCode 人狼? <br>
     * 人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode人狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.人狼) : false;
    }

    /**
     * Is the value of requestSkillCode 賢者? <br>
     * 賢者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode賢者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.賢者) : false;
    }

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
     * Is the value of isSpectator True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsSpectatorTrue() {
        CDef.Flg cdef = getIsSpectatorAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isSpectator False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsSpectatorFalse() {
        CDef.Flg cdef = getIsSpectatorAsFlg();
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
     * Is the value of isGone True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsGoneTrue() {
        CDef.Flg cdef = getIsGoneAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isGone False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsGoneFalse() {
        CDef.Flg cdef = getIsGoneAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
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

    /**
     * Get the value of the column 'isSpectator' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsSpectatorAlias() {
        CDef.Flg cdef = getIsSpectatorAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isGone' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsGoneAlias() {
        CDef.Flg cdef = getIsGoneAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CHARA by my CHARA_ID, named 'chara'. */
    protected OptionalEntity<Chara> _chara;

    /**
     * [get] CHARA by my CHARA_ID, named 'chara'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'chara'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Chara> getChara() {
        if (_chara == null) { _chara = OptionalEntity.relationEmpty(this, "chara"); }
        return _chara;
    }

    /**
     * [set] CHARA by my CHARA_ID, named 'chara'.
     * @param chara The entity of foreign property 'chara'. (NullAllowed)
     */
    public void setChara(OptionalEntity<Chara> chara) {
        _chara = chara;
    }

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

    /** SKILL by my REQUEST_SKILL_CODE, named 'skillByRequestSkillCode'. */
    protected OptionalEntity<Skill> _skillByRequestSkillCode;

    /**
     * [get] SKILL by my REQUEST_SKILL_CODE, named 'skillByRequestSkillCode'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'skillByRequestSkillCode'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Skill> getSkillByRequestSkillCode() {
        if (_skillByRequestSkillCode == null) { _skillByRequestSkillCode = OptionalEntity.relationEmpty(this, "skillByRequestSkillCode"); }
        return _skillByRequestSkillCode;
    }

    /**
     * [set] SKILL by my REQUEST_SKILL_CODE, named 'skillByRequestSkillCode'.
     * @param skillByRequestSkillCode The entity of foreign property 'skillByRequestSkillCode'. (NullAllowed)
     */
    public void setSkillByRequestSkillCode(OptionalEntity<Skill> skillByRequestSkillCode) {
        _skillByRequestSkillCode = skillByRequestSkillCode;
    }

    /** SKILL by my SKILL_CODE, named 'skillBySkillCode'. */
    protected OptionalEntity<Skill> _skillBySkillCode;

    /**
     * [get] SKILL by my SKILL_CODE, named 'skillBySkillCode'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'skillBySkillCode'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Skill> getSkillBySkillCode() {
        if (_skillBySkillCode == null) { _skillBySkillCode = OptionalEntity.relationEmpty(this, "skillBySkillCode"); }
        return _skillBySkillCode;
    }

    /**
     * [set] SKILL by my SKILL_CODE, named 'skillBySkillCode'.
     * @param skillBySkillCode The entity of foreign property 'skillBySkillCode'. (NullAllowed)
     */
    public void setSkillBySkillCode(OptionalEntity<Skill> skillBySkillCode) {
        _skillBySkillCode = skillBySkillCode;
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

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** MESSAGE by VILLAGE_PLAYER_ID, named 'messageList'. */
    protected List<Message> _messageList;

    /**
     * [get] MESSAGE by VILLAGE_PLAYER_ID, named 'messageList'.
     * @return The entity list of referrer property 'messageList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageList() {
        if (_messageList == null) { _messageList = newReferrerList(); }
        return _messageList;
    }

    /**
     * [set] MESSAGE by VILLAGE_PLAYER_ID, named 'messageList'.
     * @param messageList The entity list of referrer property 'messageList'. (NullAllowed)
     */
    public void setMessageList(List<Message> messageList) {
        _messageList = messageList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVillagePlayer) {
            BsVillagePlayer other = (BsVillagePlayer)obj;
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
        if (_chara != null && _chara.isPresent())
        { sb.append(li).append(xbRDS(_chara, "chara")); }
        if (_deadReason != null && _deadReason.isPresent())
        { sb.append(li).append(xbRDS(_deadReason, "deadReason")); }
        if (_player != null && _player.isPresent())
        { sb.append(li).append(xbRDS(_player, "player")); }
        if (_skillByRequestSkillCode != null && _skillByRequestSkillCode.isPresent())
        { sb.append(li).append(xbRDS(_skillByRequestSkillCode, "skillByRequestSkillCode")); }
        if (_skillBySkillCode != null && _skillBySkillCode.isPresent())
        { sb.append(li).append(xbRDS(_skillBySkillCode, "skillBySkillCode")); }
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
        if (_messageList != null) { for (Message et : _messageList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageList")); } } }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villagePlayerId));
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_playerId));
        sb.append(dm).append(xfND(_charaId));
        sb.append(dm).append(xfND(_skillCode));
        sb.append(dm).append(xfND(_requestSkillCode));
        sb.append(dm).append(xfND(_roomNumber));
        sb.append(dm).append(xfND(_isDead));
        sb.append(dm).append(xfND(_isSpectator));
        sb.append(dm).append(xfND(_deadReasonCode));
        sb.append(dm).append(xfND(_deadDay));
        sb.append(dm).append(xfND(_isGone));
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
        if (_chara != null && _chara.isPresent())
        { sb.append(dm).append("chara"); }
        if (_deadReason != null && _deadReason.isPresent())
        { sb.append(dm).append("deadReason"); }
        if (_player != null && _player.isPresent())
        { sb.append(dm).append("player"); }
        if (_skillByRequestSkillCode != null && _skillByRequestSkillCode.isPresent())
        { sb.append(dm).append("skillByRequestSkillCode"); }
        if (_skillBySkillCode != null && _skillBySkillCode.isPresent())
        { sb.append(dm).append("skillBySkillCode"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (_messageList != null && !_messageList.isEmpty())
        { sb.append(dm).append("messageList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillagePlayer clone() {
        return (VillagePlayer)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者ID
     * @return The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillagePlayerId() {
        checkSpecifiedProperty("villagePlayerId");
        return _villagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者ID
     * @param villagePlayerId The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerId(Integer villagePlayerId) {
        registerModifiedProperty("villagePlayerId");
        _villagePlayerId = villagePlayerId;
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
     * [get] PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @return The value of the column 'PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getPlayerId() {
        checkSpecifiedProperty("playerId");
        return _playerId;
    }

    /**
     * [set] PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @param playerId The value of the column 'PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPlayerId(Integer playerId) {
        registerModifiedProperty("playerId");
        _playerId = playerId;
    }

    /**
     * [get] CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara} <br>
     * キャラクターID
     * @return The value of the column 'CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaId() {
        checkSpecifiedProperty("charaId");
        return _charaId;
    }

    /**
     * [set] CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara} <br>
     * キャラクターID
     * @param charaId The value of the column 'CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaId(Integer charaId) {
        registerModifiedProperty("charaId");
        _charaId = charaId;
    }

    /**
     * [get] SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職コード
     * @return The value of the column 'SKILL_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getSkillCode() {
        checkSpecifiedProperty("skillCode");
        return convertEmptyToNull(_skillCode);
    }

    /**
     * [set] SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職コード
     * @param skillCode The value of the column 'SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setSkillCode(String skillCode) {
        checkClassificationCode("SKILL_CODE", CDef.DefMeta.Skill, skillCode);
        registerModifiedProperty("skillCode");
        _skillCode = skillCode;
    }

    /**
     * [get] REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 希望役職コード
     * @return The value of the column 'REQUEST_SKILL_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getRequestSkillCode() {
        checkSpecifiedProperty("requestSkillCode");
        return convertEmptyToNull(_requestSkillCode);
    }

    /**
     * [set] REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 希望役職コード
     * @param requestSkillCode The value of the column 'REQUEST_SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setRequestSkillCode(String requestSkillCode) {
        checkClassificationCode("REQUEST_SKILL_CODE", CDef.DefMeta.Skill, requestSkillCode);
        registerModifiedProperty("requestSkillCode");
        _requestSkillCode = requestSkillCode;
    }

    /**
     * [get] ROOM_NUMBER: {INT UNSIGNED(10)} <br>
     * 部屋番号
     * @return The value of the column 'ROOM_NUMBER'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getRoomNumber() {
        checkSpecifiedProperty("roomNumber");
        return _roomNumber;
    }

    /**
     * [set] ROOM_NUMBER: {INT UNSIGNED(10)} <br>
     * 部屋番号
     * @param roomNumber The value of the column 'ROOM_NUMBER'. (NullAllowed: null update allowed for no constraint)
     */
    public void setRoomNumber(Integer roomNumber) {
        registerModifiedProperty("roomNumber");
        _roomNumber = roomNumber;
    }

    /**
     * [get] IS_DEAD: {NotNull, BIT, classification=Flg} <br>
     * 死亡しているか
     * @return The value of the column 'IS_DEAD'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsDead() {
        checkSpecifiedProperty("isDead");
        return _isDead;
    }

    /**
     * [set] IS_DEAD: {NotNull, BIT, classification=Flg} <br>
     * 死亡しているか
     * @param isDead The value of the column 'IS_DEAD'. (basically NotNull if update: for the constraint)
     */
    public void setIsDead(Boolean isDead) {
        checkClassificationCode("IS_DEAD", CDef.DefMeta.Flg, isDead);
        registerModifiedProperty("isDead");
        _isDead = isDead;
    }

    /**
     * [get] IS_SPECTATOR: {NotNull, BIT, classification=Flg} <br>
     * 見学者か
     * @return The value of the column 'IS_SPECTATOR'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsSpectator() {
        checkSpecifiedProperty("isSpectator");
        return _isSpectator;
    }

    /**
     * [set] IS_SPECTATOR: {NotNull, BIT, classification=Flg} <br>
     * 見学者か
     * @param isSpectator The value of the column 'IS_SPECTATOR'. (basically NotNull if update: for the constraint)
     */
    public void setIsSpectator(Boolean isSpectator) {
        checkClassificationCode("IS_SPECTATOR", CDef.DefMeta.Flg, isSpectator);
        registerModifiedProperty("isSpectator");
        _isSpectator = isSpectator;
    }

    /**
     * [get] DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由コード
     * @return The value of the column 'DEAD_REASON_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getDeadReasonCode() {
        checkSpecifiedProperty("deadReasonCode");
        return convertEmptyToNull(_deadReasonCode);
    }

    /**
     * [set] DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由コード
     * @param deadReasonCode The value of the column 'DEAD_REASON_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setDeadReasonCode(String deadReasonCode) {
        checkClassificationCode("DEAD_REASON_CODE", CDef.DefMeta.DeadReason, deadReasonCode);
        registerModifiedProperty("deadReasonCode");
        _deadReasonCode = deadReasonCode;
    }

    /**
     * [get] DEAD_DAY: {INT UNSIGNED(10)} <br>
     * 何日目に死亡したか
     * @return The value of the column 'DEAD_DAY'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getDeadDay() {
        checkSpecifiedProperty("deadDay");
        return _deadDay;
    }

    /**
     * [set] DEAD_DAY: {INT UNSIGNED(10)} <br>
     * 何日目に死亡したか
     * @param deadDay The value of the column 'DEAD_DAY'. (NullAllowed: null update allowed for no constraint)
     */
    public void setDeadDay(Integer deadDay) {
        registerModifiedProperty("deadDay");
        _deadDay = deadDay;
    }

    /**
     * [get] IS_GONE: {NotNull, BIT, classification=Flg} <br>
     * 退村済みか
     * @return The value of the column 'IS_GONE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsGone() {
        checkSpecifiedProperty("isGone");
        return _isGone;
    }

    /**
     * [set] IS_GONE: {NotNull, BIT, classification=Flg} <br>
     * 退村済みか
     * @param isGone The value of the column 'IS_GONE'. (basically NotNull if update: for the constraint)
     */
    public void setIsGone(Boolean isGone) {
        checkClassificationCode("IS_GONE", CDef.DefMeta.Flg, isGone);
        registerModifiedProperty("isGone");
        _isGone = isGone;
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
     * @param skillCode The value of the column 'SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void mynativeMappingSkillCode(String skillCode) {
        setSkillCode(skillCode);
    }

    /**
     * For framework so basically DON'T use this method.
     * @param requestSkillCode The value of the column 'REQUEST_SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void mynativeMappingRequestSkillCode(String requestSkillCode) {
        setRequestSkillCode(requestSkillCode);
    }

    /**
     * For framework so basically DON'T use this method.
     * @param deadReasonCode The value of the column 'DEAD_REASON_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void mynativeMappingDeadReasonCode(String deadReasonCode) {
        setDeadReasonCode(deadReasonCode);
    }
}
