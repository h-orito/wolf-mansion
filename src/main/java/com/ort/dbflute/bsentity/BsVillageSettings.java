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
 * The entity of VILLAGE_SETTINGS as TABLE. <br>
 * 村設定
 * <pre>
 * [primary-key]
 *     VILLAGE_ID
 *
 * [column]
 *     VILLAGE_ID, DUMMY_CHARA_ID, START_PERSON_MIN_NUM, PERSON_MAX_NUM, START_DATETIME, DAY_CHANGE_INTERVAL_SECONDS, IS_OPEN_VOTE, IS_POSSIBLE_SKILL_REQUEST, IS_AVAILABLE_SPECTATE, IS_AVAILABLE_SAME_WOLF_ATTACK, IS_OPEN_SKILL_IN_GRAVE, IS_VISIBLE_GRAVE_SPECTATE_MESSAGE, IS_AVAILABLE_SUDDONLY_DEATH, IS_AVAILABLE_COMMIT, IS_AVAILABLE_GUARD_SAME_TARGET, JOIN_PASSWORD, ORGANIZE, ALLOWED_SECRET_SAY_CODE, IS_AVAILABLE_ACTION, IS_RANDOM_ORGANIZE, IS_REINCARNATION_SKILL_ALL, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     ALLOWED_SECRET_SAY, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     allowedSecretSay, village
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villageId = entity.getVillageId();
 * Integer dummyCharaId = entity.getDummyCharaId();
 * Integer startPersonMinNum = entity.getStartPersonMinNum();
 * Integer personMaxNum = entity.getPersonMaxNum();
 * java.time.LocalDateTime startDatetime = entity.getStartDatetime();
 * Integer dayChangeIntervalSeconds = entity.getDayChangeIntervalSeconds();
 * Boolean isOpenVote = entity.getIsOpenVote();
 * Boolean isPossibleSkillRequest = entity.getIsPossibleSkillRequest();
 * Boolean isAvailableSpectate = entity.getIsAvailableSpectate();
 * Boolean isAvailableSameWolfAttack = entity.getIsAvailableSameWolfAttack();
 * Boolean isOpenSkillInGrave = entity.getIsOpenSkillInGrave();
 * Boolean isVisibleGraveSpectateMessage = entity.getIsVisibleGraveSpectateMessage();
 * Boolean isAvailableSuddonlyDeath = entity.getIsAvailableSuddonlyDeath();
 * Boolean isAvailableCommit = entity.getIsAvailableCommit();
 * Boolean isAvailableGuardSameTarget = entity.getIsAvailableGuardSameTarget();
 * String joinPassword = entity.getJoinPassword();
 * String organize = entity.getOrganize();
 * String allowedSecretSayCode = entity.getAllowedSecretSayCode();
 * Boolean isAvailableAction = entity.getIsAvailableAction();
 * Boolean isRandomOrganize = entity.getIsRandomOrganize();
 * Boolean isReincarnationSkillAll = entity.getIsReincarnationSkillAll();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillageId(villageId);
 * entity.setDummyCharaId(dummyCharaId);
 * entity.setStartPersonMinNum(startPersonMinNum);
 * entity.setPersonMaxNum(personMaxNum);
 * entity.setStartDatetime(startDatetime);
 * entity.setDayChangeIntervalSeconds(dayChangeIntervalSeconds);
 * entity.setIsOpenVote(isOpenVote);
 * entity.setIsPossibleSkillRequest(isPossibleSkillRequest);
 * entity.setIsAvailableSpectate(isAvailableSpectate);
 * entity.setIsAvailableSameWolfAttack(isAvailableSameWolfAttack);
 * entity.setIsOpenSkillInGrave(isOpenSkillInGrave);
 * entity.setIsVisibleGraveSpectateMessage(isVisibleGraveSpectateMessage);
 * entity.setIsAvailableSuddonlyDeath(isAvailableSuddonlyDeath);
 * entity.setIsAvailableCommit(isAvailableCommit);
 * entity.setIsAvailableGuardSameTarget(isAvailableGuardSameTarget);
 * entity.setJoinPassword(joinPassword);
 * entity.setOrganize(organize);
 * entity.setAllowedSecretSayCode(allowedSecretSayCode);
 * entity.setIsAvailableAction(isAvailableAction);
 * entity.setIsRandomOrganize(isRandomOrganize);
 * entity.setIsReincarnationSkillAll(isReincarnationSkillAll);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillageSettings extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE} */
    protected Integer _villageId;

    /** DUMMY_CHARA_ID: {NotNull, INT UNSIGNED(10)} */
    protected Integer _dummyCharaId;

    /** START_PERSON_MIN_NUM: {INT UNSIGNED(10)} */
    protected Integer _startPersonMinNum;

    /** PERSON_MAX_NUM: {INT UNSIGNED(10)} */
    protected Integer _personMaxNum;

    /** START_DATETIME: {DATETIME(19)} */
    protected java.time.LocalDateTime _startDatetime;

    /** DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)} */
    protected Integer _dayChangeIntervalSeconds;

    /** IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isOpenVote;

    /** IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} */
    protected Boolean _isPossibleSkillRequest;

    /** IS_AVAILABLE_SPECTATE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isAvailableSpectate;

    /** IS_AVAILABLE_SAME_WOLF_ATTACK: {NotNull, BIT, classification=Flg} */
    protected Boolean _isAvailableSameWolfAttack;

    /** IS_OPEN_SKILL_IN_GRAVE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isOpenSkillInGrave;

    /** IS_VISIBLE_GRAVE_SPECTATE_MESSAGE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isVisibleGraveSpectateMessage;

    /** IS_AVAILABLE_SUDDONLY_DEATH: {NotNull, BIT, classification=Flg} */
    protected Boolean _isAvailableSuddonlyDeath;

    /** IS_AVAILABLE_COMMIT: {NotNull, BIT, classification=Flg} */
    protected Boolean _isAvailableCommit;

    /** IS_AVAILABLE_GUARD_SAME_TARGET: {NotNull, BIT, classification=Flg} */
    protected Boolean _isAvailableGuardSameTarget;

    /** JOIN_PASSWORD: {VARCHAR(12)} */
    protected String _joinPassword;

    /** ORGANIZE: {NotNull, VARCHAR(10000)} */
    protected String _organize;

    /** ALLOWED_SECRET_SAY_CODE: {IX, NotNull, VARCHAR(20), FK to ALLOWED_SECRET_SAY, classification=AllowedSecretSay} */
    protected String _allowedSecretSayCode;

    /** IS_AVAILABLE_ACTION: {NotNull, BIT, classification=Flg} */
    protected Boolean _isAvailableAction;

    /** IS_RANDOM_ORGANIZE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isRandomOrganize;

    /** IS_REINCARNATION_SKILL_ALL: {NotNull, BIT, classification=Flg} */
    protected Boolean _isReincarnationSkillAll;

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
        return "VILLAGE_SETTINGS";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of isOpenVote as the classification of Flg. <br>
     * IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsOpenVoteAsFlg() {
        return CDef.Flg.codeOf(getIsOpenVote());
    }

    /**
     * Set the value of isOpenVote as the classification of Flg. <br>
     * IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsOpenVoteAsFlg(CDef.Flg cdef) {
        setIsOpenVote(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isPossibleSkillRequest as the classification of Flg. <br>
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsPossibleSkillRequestAsFlg() {
        return CDef.Flg.codeOf(getIsPossibleSkillRequest());
    }

    /**
     * Set the value of isPossibleSkillRequest as the classification of Flg. <br>
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsPossibleSkillRequestAsFlg(CDef.Flg cdef) {
        setIsPossibleSkillRequest(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isAvailableSpectate as the classification of Flg. <br>
     * IS_AVAILABLE_SPECTATE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsAvailableSpectateAsFlg() {
        return CDef.Flg.codeOf(getIsAvailableSpectate());
    }

    /**
     * Set the value of isAvailableSpectate as the classification of Flg. <br>
     * IS_AVAILABLE_SPECTATE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsAvailableSpectateAsFlg(CDef.Flg cdef) {
        setIsAvailableSpectate(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isAvailableSameWolfAttack as the classification of Flg. <br>
     * IS_AVAILABLE_SAME_WOLF_ATTACK: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsAvailableSameWolfAttackAsFlg() {
        return CDef.Flg.codeOf(getIsAvailableSameWolfAttack());
    }

    /**
     * Set the value of isAvailableSameWolfAttack as the classification of Flg. <br>
     * IS_AVAILABLE_SAME_WOLF_ATTACK: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsAvailableSameWolfAttackAsFlg(CDef.Flg cdef) {
        setIsAvailableSameWolfAttack(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isOpenSkillInGrave as the classification of Flg. <br>
     * IS_OPEN_SKILL_IN_GRAVE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsOpenSkillInGraveAsFlg() {
        return CDef.Flg.codeOf(getIsOpenSkillInGrave());
    }

    /**
     * Set the value of isOpenSkillInGrave as the classification of Flg. <br>
     * IS_OPEN_SKILL_IN_GRAVE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsOpenSkillInGraveAsFlg(CDef.Flg cdef) {
        setIsOpenSkillInGrave(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isVisibleGraveSpectateMessage as the classification of Flg. <br>
     * IS_VISIBLE_GRAVE_SPECTATE_MESSAGE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsVisibleGraveSpectateMessageAsFlg() {
        return CDef.Flg.codeOf(getIsVisibleGraveSpectateMessage());
    }

    /**
     * Set the value of isVisibleGraveSpectateMessage as the classification of Flg. <br>
     * IS_VISIBLE_GRAVE_SPECTATE_MESSAGE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsVisibleGraveSpectateMessageAsFlg(CDef.Flg cdef) {
        setIsVisibleGraveSpectateMessage(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isAvailableSuddonlyDeath as the classification of Flg. <br>
     * IS_AVAILABLE_SUDDONLY_DEATH: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsAvailableSuddonlyDeathAsFlg() {
        return CDef.Flg.codeOf(getIsAvailableSuddonlyDeath());
    }

    /**
     * Set the value of isAvailableSuddonlyDeath as the classification of Flg. <br>
     * IS_AVAILABLE_SUDDONLY_DEATH: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsAvailableSuddonlyDeathAsFlg(CDef.Flg cdef) {
        setIsAvailableSuddonlyDeath(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isAvailableCommit as the classification of Flg. <br>
     * IS_AVAILABLE_COMMIT: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsAvailableCommitAsFlg() {
        return CDef.Flg.codeOf(getIsAvailableCommit());
    }

    /**
     * Set the value of isAvailableCommit as the classification of Flg. <br>
     * IS_AVAILABLE_COMMIT: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsAvailableCommitAsFlg(CDef.Flg cdef) {
        setIsAvailableCommit(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isAvailableGuardSameTarget as the classification of Flg. <br>
     * IS_AVAILABLE_GUARD_SAME_TARGET: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsAvailableGuardSameTargetAsFlg() {
        return CDef.Flg.codeOf(getIsAvailableGuardSameTarget());
    }

    /**
     * Set the value of isAvailableGuardSameTarget as the classification of Flg. <br>
     * IS_AVAILABLE_GUARD_SAME_TARGET: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsAvailableGuardSameTargetAsFlg(CDef.Flg cdef) {
        setIsAvailableGuardSameTarget(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of allowedSecretSayCode as the classification of AllowedSecretSay. <br>
     * ALLOWED_SECRET_SAY_CODE: {IX, NotNull, VARCHAR(20), FK to ALLOWED_SECRET_SAY, classification=AllowedSecretSay} <br>
     * 秘話可能範囲
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.AllowedSecretSay getAllowedSecretSayCodeAsAllowedSecretSay() {
        return CDef.AllowedSecretSay.codeOf(getAllowedSecretSayCode());
    }

    /**
     * Set the value of allowedSecretSayCode as the classification of AllowedSecretSay. <br>
     * ALLOWED_SECRET_SAY_CODE: {IX, NotNull, VARCHAR(20), FK to ALLOWED_SECRET_SAY, classification=AllowedSecretSay} <br>
     * 秘話可能範囲
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay cdef) {
        setAllowedSecretSayCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of isAvailableAction as the classification of Flg. <br>
     * IS_AVAILABLE_ACTION: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsAvailableActionAsFlg() {
        return CDef.Flg.codeOf(getIsAvailableAction());
    }

    /**
     * Set the value of isAvailableAction as the classification of Flg. <br>
     * IS_AVAILABLE_ACTION: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsAvailableActionAsFlg(CDef.Flg cdef) {
        setIsAvailableAction(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isRandomOrganize as the classification of Flg. <br>
     * IS_RANDOM_ORGANIZE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsRandomOrganizeAsFlg() {
        return CDef.Flg.codeOf(getIsRandomOrganize());
    }

    /**
     * Set the value of isRandomOrganize as the classification of Flg. <br>
     * IS_RANDOM_ORGANIZE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsRandomOrganizeAsFlg(CDef.Flg cdef) {
        setIsRandomOrganize(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isReincarnationSkillAll as the classification of Flg. <br>
     * IS_REINCARNATION_SKILL_ALL: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsReincarnationSkillAllAsFlg() {
        return CDef.Flg.codeOf(getIsReincarnationSkillAll());
    }

    /**
     * Set the value of isReincarnationSkillAll as the classification of Flg. <br>
     * IS_REINCARNATION_SKILL_ALL: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsReincarnationSkillAllAsFlg(CDef.Flg cdef) {
        setIsReincarnationSkillAll(cdef != null ? toBoolean(cdef.code()) : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of isOpenVote as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsOpenVote_True() {
        setIsOpenVoteAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isOpenVote as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsOpenVote_False() {
        setIsOpenVoteAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isPossibleSkillRequest as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsPossibleSkillRequest_True() {
        setIsPossibleSkillRequestAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isPossibleSkillRequest as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsPossibleSkillRequest_False() {
        setIsPossibleSkillRequestAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isAvailableSpectate as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsAvailableSpectate_True() {
        setIsAvailableSpectateAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isAvailableSpectate as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsAvailableSpectate_False() {
        setIsAvailableSpectateAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isAvailableSameWolfAttack as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsAvailableSameWolfAttack_True() {
        setIsAvailableSameWolfAttackAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isAvailableSameWolfAttack as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsAvailableSameWolfAttack_False() {
        setIsAvailableSameWolfAttackAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isOpenSkillInGrave as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsOpenSkillInGrave_True() {
        setIsOpenSkillInGraveAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isOpenSkillInGrave as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsOpenSkillInGrave_False() {
        setIsOpenSkillInGraveAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isVisibleGraveSpectateMessage as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsVisibleGraveSpectateMessage_True() {
        setIsVisibleGraveSpectateMessageAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isVisibleGraveSpectateMessage as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsVisibleGraveSpectateMessage_False() {
        setIsVisibleGraveSpectateMessageAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isAvailableSuddonlyDeath as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsAvailableSuddonlyDeath_True() {
        setIsAvailableSuddonlyDeathAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isAvailableSuddonlyDeath as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsAvailableSuddonlyDeath_False() {
        setIsAvailableSuddonlyDeathAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isAvailableCommit as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsAvailableCommit_True() {
        setIsAvailableCommitAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isAvailableCommit as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsAvailableCommit_False() {
        setIsAvailableCommitAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isAvailableGuardSameTarget as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsAvailableGuardSameTarget_True() {
        setIsAvailableGuardSameTargetAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isAvailableGuardSameTarget as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsAvailableGuardSameTarget_False() {
        setIsAvailableGuardSameTargetAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of allowedSecretSayCode as 全員 (EVERYTHING). <br>
     * 全員
     */
    public void setAllowedSecretSayCode_全員() {
        setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay.全員);
    }

    /**
     * Set the value of allowedSecretSayCode as なし (NOTHING). <br>
     * なし
     */
    public void setAllowedSecretSayCode_なし() {
        setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay.なし);
    }

    /**
     * Set the value of allowedSecretSayCode as 村建てとのみ (ONLY_CREATOR). <br>
     * 村建てとのみ
     */
    public void setAllowedSecretSayCode_村建てとのみ() {
        setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay.村建てとのみ);
    }

    /**
     * Set the value of isAvailableAction as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsAvailableAction_True() {
        setIsAvailableActionAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isAvailableAction as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsAvailableAction_False() {
        setIsAvailableActionAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isRandomOrganize as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsRandomOrganize_True() {
        setIsRandomOrganizeAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isRandomOrganize as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsRandomOrganize_False() {
        setIsRandomOrganizeAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isReincarnationSkillAll as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsReincarnationSkillAll_True() {
        setIsReincarnationSkillAllAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isReincarnationSkillAll as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsReincarnationSkillAll_False() {
        setIsReincarnationSkillAllAsFlg(CDef.Flg.False);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of isOpenVote True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsOpenVoteTrue() {
        CDef.Flg cdef = getIsOpenVoteAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isOpenVote False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsOpenVoteFalse() {
        CDef.Flg cdef = getIsOpenVoteAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isPossibleSkillRequest True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsPossibleSkillRequestTrue() {
        CDef.Flg cdef = getIsPossibleSkillRequestAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isPossibleSkillRequest False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsPossibleSkillRequestFalse() {
        CDef.Flg cdef = getIsPossibleSkillRequestAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isAvailableSpectate True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableSpectateTrue() {
        CDef.Flg cdef = getIsAvailableSpectateAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isAvailableSpectate False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableSpectateFalse() {
        CDef.Flg cdef = getIsAvailableSpectateAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isAvailableSameWolfAttack True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableSameWolfAttackTrue() {
        CDef.Flg cdef = getIsAvailableSameWolfAttackAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isAvailableSameWolfAttack False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableSameWolfAttackFalse() {
        CDef.Flg cdef = getIsAvailableSameWolfAttackAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isOpenSkillInGrave True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsOpenSkillInGraveTrue() {
        CDef.Flg cdef = getIsOpenSkillInGraveAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isOpenSkillInGrave False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsOpenSkillInGraveFalse() {
        CDef.Flg cdef = getIsOpenSkillInGraveAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isVisibleGraveSpectateMessage True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsVisibleGraveSpectateMessageTrue() {
        CDef.Flg cdef = getIsVisibleGraveSpectateMessageAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isVisibleGraveSpectateMessage False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsVisibleGraveSpectateMessageFalse() {
        CDef.Flg cdef = getIsVisibleGraveSpectateMessageAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isAvailableSuddonlyDeath True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableSuddonlyDeathTrue() {
        CDef.Flg cdef = getIsAvailableSuddonlyDeathAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isAvailableSuddonlyDeath False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableSuddonlyDeathFalse() {
        CDef.Flg cdef = getIsAvailableSuddonlyDeathAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isAvailableCommit True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableCommitTrue() {
        CDef.Flg cdef = getIsAvailableCommitAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isAvailableCommit False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableCommitFalse() {
        CDef.Flg cdef = getIsAvailableCommitAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isAvailableGuardSameTarget True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableGuardSameTargetTrue() {
        CDef.Flg cdef = getIsAvailableGuardSameTargetAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isAvailableGuardSameTarget False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableGuardSameTargetFalse() {
        CDef.Flg cdef = getIsAvailableGuardSameTargetAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of allowedSecretSayCode 全員? <br>
     * 全員
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAllowedSecretSayCode全員() {
        CDef.AllowedSecretSay cdef = getAllowedSecretSayCodeAsAllowedSecretSay();
        return cdef != null ? cdef.equals(CDef.AllowedSecretSay.全員) : false;
    }

    /**
     * Is the value of allowedSecretSayCode なし? <br>
     * なし
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAllowedSecretSayCodeなし() {
        CDef.AllowedSecretSay cdef = getAllowedSecretSayCodeAsAllowedSecretSay();
        return cdef != null ? cdef.equals(CDef.AllowedSecretSay.なし) : false;
    }

    /**
     * Is the value of allowedSecretSayCode 村建てとのみ? <br>
     * 村建てとのみ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAllowedSecretSayCode村建てとのみ() {
        CDef.AllowedSecretSay cdef = getAllowedSecretSayCodeAsAllowedSecretSay();
        return cdef != null ? cdef.equals(CDef.AllowedSecretSay.村建てとのみ) : false;
    }

    /**
     * Is the value of isAvailableAction True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableActionTrue() {
        CDef.Flg cdef = getIsAvailableActionAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isAvailableAction False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableActionFalse() {
        CDef.Flg cdef = getIsAvailableActionAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isRandomOrganize True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsRandomOrganizeTrue() {
        CDef.Flg cdef = getIsRandomOrganizeAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isRandomOrganize False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsRandomOrganizeFalse() {
        CDef.Flg cdef = getIsRandomOrganizeAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isReincarnationSkillAll True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsReincarnationSkillAllTrue() {
        CDef.Flg cdef = getIsReincarnationSkillAllAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isReincarnationSkillAll False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsReincarnationSkillAllFalse() {
        CDef.Flg cdef = getIsReincarnationSkillAllAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'isOpenVote' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsOpenVoteAlias() {
        CDef.Flg cdef = getIsOpenVoteAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isPossibleSkillRequest' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsPossibleSkillRequestAlias() {
        CDef.Flg cdef = getIsPossibleSkillRequestAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isAvailableSpectate' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsAvailableSpectateAlias() {
        CDef.Flg cdef = getIsAvailableSpectateAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isAvailableSameWolfAttack' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsAvailableSameWolfAttackAlias() {
        CDef.Flg cdef = getIsAvailableSameWolfAttackAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isOpenSkillInGrave' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsOpenSkillInGraveAlias() {
        CDef.Flg cdef = getIsOpenSkillInGraveAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isVisibleGraveSpectateMessage' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsVisibleGraveSpectateMessageAlias() {
        CDef.Flg cdef = getIsVisibleGraveSpectateMessageAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isAvailableSuddonlyDeath' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsAvailableSuddonlyDeathAlias() {
        CDef.Flg cdef = getIsAvailableSuddonlyDeathAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isAvailableCommit' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsAvailableCommitAlias() {
        CDef.Flg cdef = getIsAvailableCommitAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isAvailableGuardSameTarget' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsAvailableGuardSameTargetAlias() {
        CDef.Flg cdef = getIsAvailableGuardSameTargetAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isAvailableAction' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsAvailableActionAlias() {
        CDef.Flg cdef = getIsAvailableActionAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isRandomOrganize' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsRandomOrganizeAlias() {
        CDef.Flg cdef = getIsRandomOrganizeAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isReincarnationSkillAll' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsReincarnationSkillAllAlias() {
        CDef.Flg cdef = getIsReincarnationSkillAllAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** ALLOWED_SECRET_SAY by my ALLOWED_SECRET_SAY_CODE, named 'allowedSecretSay'. */
    protected OptionalEntity<AllowedSecretSay> _allowedSecretSay;

    /**
     * [get] ALLOWED_SECRET_SAY by my ALLOWED_SECRET_SAY_CODE, named 'allowedSecretSay'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'allowedSecretSay'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<AllowedSecretSay> getAllowedSecretSay() {
        if (_allowedSecretSay == null) { _allowedSecretSay = OptionalEntity.relationEmpty(this, "allowedSecretSay"); }
        return _allowedSecretSay;
    }

    /**
     * [set] ALLOWED_SECRET_SAY by my ALLOWED_SECRET_SAY_CODE, named 'allowedSecretSay'.
     * @param allowedSecretSay The entity of foreign property 'allowedSecretSay'. (NullAllowed)
     */
    public void setAllowedSecretSay(OptionalEntity<AllowedSecretSay> allowedSecretSay) {
        _allowedSecretSay = allowedSecretSay;
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
    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVillageSettings) {
            BsVillageSettings other = (BsVillageSettings)obj;
            if (!xSV(_villageId, other._villageId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villageId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_allowedSecretSay != null && _allowedSecretSay.isPresent())
        { sb.append(li).append(xbRDS(_allowedSecretSay, "allowedSecretSay")); }
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_dummyCharaId));
        sb.append(dm).append(xfND(_startPersonMinNum));
        sb.append(dm).append(xfND(_personMaxNum));
        sb.append(dm).append(xfND(_startDatetime));
        sb.append(dm).append(xfND(_dayChangeIntervalSeconds));
        sb.append(dm).append(xfND(_isOpenVote));
        sb.append(dm).append(xfND(_isPossibleSkillRequest));
        sb.append(dm).append(xfND(_isAvailableSpectate));
        sb.append(dm).append(xfND(_isAvailableSameWolfAttack));
        sb.append(dm).append(xfND(_isOpenSkillInGrave));
        sb.append(dm).append(xfND(_isVisibleGraveSpectateMessage));
        sb.append(dm).append(xfND(_isAvailableSuddonlyDeath));
        sb.append(dm).append(xfND(_isAvailableCommit));
        sb.append(dm).append(xfND(_isAvailableGuardSameTarget));
        sb.append(dm).append(xfND(_joinPassword));
        sb.append(dm).append(xfND(_organize));
        sb.append(dm).append(xfND(_allowedSecretSayCode));
        sb.append(dm).append(xfND(_isAvailableAction));
        sb.append(dm).append(xfND(_isRandomOrganize));
        sb.append(dm).append(xfND(_isReincarnationSkillAll));
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
        if (_allowedSecretSay != null && _allowedSecretSay.isPresent())
        { sb.append(dm).append("allowedSecretSay"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillageSettings clone() {
        return (VillageSettings)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] DUMMY_CHARA_ID: {NotNull, INT UNSIGNED(10)} <br>
     * ダミーキャラID
     * @return The value of the column 'DUMMY_CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDummyCharaId() {
        checkSpecifiedProperty("dummyCharaId");
        return _dummyCharaId;
    }

    /**
     * [set] DUMMY_CHARA_ID: {NotNull, INT UNSIGNED(10)} <br>
     * ダミーキャラID
     * @param dummyCharaId The value of the column 'DUMMY_CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setDummyCharaId(Integer dummyCharaId) {
        registerModifiedProperty("dummyCharaId");
        _dummyCharaId = dummyCharaId;
    }

    /**
     * [get] START_PERSON_MIN_NUM: {INT UNSIGNED(10)} <br>
     * 最低開始人数
     * @return The value of the column 'START_PERSON_MIN_NUM'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getStartPersonMinNum() {
        checkSpecifiedProperty("startPersonMinNum");
        return _startPersonMinNum;
    }

    /**
     * [set] START_PERSON_MIN_NUM: {INT UNSIGNED(10)} <br>
     * 最低開始人数
     * @param startPersonMinNum The value of the column 'START_PERSON_MIN_NUM'. (NullAllowed: null update allowed for no constraint)
     */
    public void setStartPersonMinNum(Integer startPersonMinNum) {
        registerModifiedProperty("startPersonMinNum");
        _startPersonMinNum = startPersonMinNum;
    }

    /**
     * [get] PERSON_MAX_NUM: {INT UNSIGNED(10)} <br>
     * 定員
     * @return The value of the column 'PERSON_MAX_NUM'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getPersonMaxNum() {
        checkSpecifiedProperty("personMaxNum");
        return _personMaxNum;
    }

    /**
     * [set] PERSON_MAX_NUM: {INT UNSIGNED(10)} <br>
     * 定員
     * @param personMaxNum The value of the column 'PERSON_MAX_NUM'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPersonMaxNum(Integer personMaxNum) {
        registerModifiedProperty("personMaxNum");
        _personMaxNum = personMaxNum;
    }

    /**
     * [get] START_DATETIME: {DATETIME(19)} <br>
     * 開始日時
     * @return The value of the column 'START_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getStartDatetime() {
        checkSpecifiedProperty("startDatetime");
        return _startDatetime;
    }

    /**
     * [set] START_DATETIME: {DATETIME(19)} <br>
     * 開始日時
     * @param startDatetime The value of the column 'START_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setStartDatetime(java.time.LocalDateTime startDatetime) {
        registerModifiedProperty("startDatetime");
        _startDatetime = startDatetime;
    }

    /**
     * [get] DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)} <br>
     * 更新間隔（秒）
     * @return The value of the column 'DAY_CHANGE_INTERVAL_SECONDS'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDayChangeIntervalSeconds() {
        checkSpecifiedProperty("dayChangeIntervalSeconds");
        return _dayChangeIntervalSeconds;
    }

    /**
     * [set] DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)} <br>
     * 更新間隔（秒）
     * @param dayChangeIntervalSeconds The value of the column 'DAY_CHANGE_INTERVAL_SECONDS'. (basically NotNull if update: for the constraint)
     */
    public void setDayChangeIntervalSeconds(Integer dayChangeIntervalSeconds) {
        registerModifiedProperty("dayChangeIntervalSeconds");
        _dayChangeIntervalSeconds = dayChangeIntervalSeconds;
    }

    /**
     * [get] IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * 記名投票か
     * @return The value of the column 'IS_OPEN_VOTE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsOpenVote() {
        checkSpecifiedProperty("isOpenVote");
        return _isOpenVote;
    }

    /**
     * [set] IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * 記名投票か
     * @param isOpenVote The value of the column 'IS_OPEN_VOTE'. (basically NotNull if update: for the constraint)
     */
    public void setIsOpenVote(Boolean isOpenVote) {
        checkClassificationCode("IS_OPEN_VOTE", CDef.DefMeta.Flg, isOpenVote);
        registerModifiedProperty("isOpenVote");
        _isOpenVote = isOpenVote;
    }

    /**
     * [get] IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * 役職希望有効か
     * @return The value of the column 'IS_POSSIBLE_SKILL_REQUEST'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsPossibleSkillRequest() {
        checkSpecifiedProperty("isPossibleSkillRequest");
        return _isPossibleSkillRequest;
    }

    /**
     * [set] IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * 役職希望有効か
     * @param isPossibleSkillRequest The value of the column 'IS_POSSIBLE_SKILL_REQUEST'. (basically NotNull if update: for the constraint)
     */
    public void setIsPossibleSkillRequest(Boolean isPossibleSkillRequest) {
        checkClassificationCode("IS_POSSIBLE_SKILL_REQUEST", CDef.DefMeta.Flg, isPossibleSkillRequest);
        registerModifiedProperty("isPossibleSkillRequest");
        _isPossibleSkillRequest = isPossibleSkillRequest;
    }

    /**
     * [get] IS_AVAILABLE_SPECTATE: {NotNull, BIT, classification=Flg} <br>
     * 見学可能か
     * @return The value of the column 'IS_AVAILABLE_SPECTATE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsAvailableSpectate() {
        checkSpecifiedProperty("isAvailableSpectate");
        return _isAvailableSpectate;
    }

    /**
     * [set] IS_AVAILABLE_SPECTATE: {NotNull, BIT, classification=Flg} <br>
     * 見学可能か
     * @param isAvailableSpectate The value of the column 'IS_AVAILABLE_SPECTATE'. (basically NotNull if update: for the constraint)
     */
    public void setIsAvailableSpectate(Boolean isAvailableSpectate) {
        checkClassificationCode("IS_AVAILABLE_SPECTATE", CDef.DefMeta.Flg, isAvailableSpectate);
        registerModifiedProperty("isAvailableSpectate");
        _isAvailableSpectate = isAvailableSpectate;
    }

    /**
     * [get] IS_AVAILABLE_SAME_WOLF_ATTACK: {NotNull, BIT, classification=Flg} <br>
     * 連続襲撃可能か
     * @return The value of the column 'IS_AVAILABLE_SAME_WOLF_ATTACK'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsAvailableSameWolfAttack() {
        checkSpecifiedProperty("isAvailableSameWolfAttack");
        return _isAvailableSameWolfAttack;
    }

    /**
     * [set] IS_AVAILABLE_SAME_WOLF_ATTACK: {NotNull, BIT, classification=Flg} <br>
     * 連続襲撃可能か
     * @param isAvailableSameWolfAttack The value of the column 'IS_AVAILABLE_SAME_WOLF_ATTACK'. (basically NotNull if update: for the constraint)
     */
    public void setIsAvailableSameWolfAttack(Boolean isAvailableSameWolfAttack) {
        checkClassificationCode("IS_AVAILABLE_SAME_WOLF_ATTACK", CDef.DefMeta.Flg, isAvailableSameWolfAttack);
        registerModifiedProperty("isAvailableSameWolfAttack");
        _isAvailableSameWolfAttack = isAvailableSameWolfAttack;
    }

    /**
     * [get] IS_OPEN_SKILL_IN_GRAVE: {NotNull, BIT, classification=Flg} <br>
     * 墓下役職公開ありか
     * @return The value of the column 'IS_OPEN_SKILL_IN_GRAVE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsOpenSkillInGrave() {
        checkSpecifiedProperty("isOpenSkillInGrave");
        return _isOpenSkillInGrave;
    }

    /**
     * [set] IS_OPEN_SKILL_IN_GRAVE: {NotNull, BIT, classification=Flg} <br>
     * 墓下役職公開ありか
     * @param isOpenSkillInGrave The value of the column 'IS_OPEN_SKILL_IN_GRAVE'. (basically NotNull if update: for the constraint)
     */
    public void setIsOpenSkillInGrave(Boolean isOpenSkillInGrave) {
        checkClassificationCode("IS_OPEN_SKILL_IN_GRAVE", CDef.DefMeta.Flg, isOpenSkillInGrave);
        registerModifiedProperty("isOpenSkillInGrave");
        _isOpenSkillInGrave = isOpenSkillInGrave;
    }

    /**
     * [get] IS_VISIBLE_GRAVE_SPECTATE_MESSAGE: {NotNull, BIT, classification=Flg} <br>
     * 墓下見学発言を生存者が見られるか
     * @return The value of the column 'IS_VISIBLE_GRAVE_SPECTATE_MESSAGE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsVisibleGraveSpectateMessage() {
        checkSpecifiedProperty("isVisibleGraveSpectateMessage");
        return _isVisibleGraveSpectateMessage;
    }

    /**
     * [set] IS_VISIBLE_GRAVE_SPECTATE_MESSAGE: {NotNull, BIT, classification=Flg} <br>
     * 墓下見学発言を生存者が見られるか
     * @param isVisibleGraveSpectateMessage The value of the column 'IS_VISIBLE_GRAVE_SPECTATE_MESSAGE'. (basically NotNull if update: for the constraint)
     */
    public void setIsVisibleGraveSpectateMessage(Boolean isVisibleGraveSpectateMessage) {
        checkClassificationCode("IS_VISIBLE_GRAVE_SPECTATE_MESSAGE", CDef.DefMeta.Flg, isVisibleGraveSpectateMessage);
        registerModifiedProperty("isVisibleGraveSpectateMessage");
        _isVisibleGraveSpectateMessage = isVisibleGraveSpectateMessage;
    }

    /**
     * [get] IS_AVAILABLE_SUDDONLY_DEATH: {NotNull, BIT, classification=Flg} <br>
     * 突然死ありか
     * @return The value of the column 'IS_AVAILABLE_SUDDONLY_DEATH'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsAvailableSuddonlyDeath() {
        checkSpecifiedProperty("isAvailableSuddonlyDeath");
        return _isAvailableSuddonlyDeath;
    }

    /**
     * [set] IS_AVAILABLE_SUDDONLY_DEATH: {NotNull, BIT, classification=Flg} <br>
     * 突然死ありか
     * @param isAvailableSuddonlyDeath The value of the column 'IS_AVAILABLE_SUDDONLY_DEATH'. (basically NotNull if update: for the constraint)
     */
    public void setIsAvailableSuddonlyDeath(Boolean isAvailableSuddonlyDeath) {
        checkClassificationCode("IS_AVAILABLE_SUDDONLY_DEATH", CDef.DefMeta.Flg, isAvailableSuddonlyDeath);
        registerModifiedProperty("isAvailableSuddonlyDeath");
        _isAvailableSuddonlyDeath = isAvailableSuddonlyDeath;
    }

    /**
     * [get] IS_AVAILABLE_COMMIT: {NotNull, BIT, classification=Flg} <br>
     * コミット可能か
     * @return The value of the column 'IS_AVAILABLE_COMMIT'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsAvailableCommit() {
        checkSpecifiedProperty("isAvailableCommit");
        return _isAvailableCommit;
    }

    /**
     * [set] IS_AVAILABLE_COMMIT: {NotNull, BIT, classification=Flg} <br>
     * コミット可能か
     * @param isAvailableCommit The value of the column 'IS_AVAILABLE_COMMIT'. (basically NotNull if update: for the constraint)
     */
    public void setIsAvailableCommit(Boolean isAvailableCommit) {
        checkClassificationCode("IS_AVAILABLE_COMMIT", CDef.DefMeta.Flg, isAvailableCommit);
        registerModifiedProperty("isAvailableCommit");
        _isAvailableCommit = isAvailableCommit;
    }

    /**
     * [get] IS_AVAILABLE_GUARD_SAME_TARGET: {NotNull, BIT, classification=Flg} <br>
     * 連続護衛可能か
     * @return The value of the column 'IS_AVAILABLE_GUARD_SAME_TARGET'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsAvailableGuardSameTarget() {
        checkSpecifiedProperty("isAvailableGuardSameTarget");
        return _isAvailableGuardSameTarget;
    }

    /**
     * [set] IS_AVAILABLE_GUARD_SAME_TARGET: {NotNull, BIT, classification=Flg} <br>
     * 連続護衛可能か
     * @param isAvailableGuardSameTarget The value of the column 'IS_AVAILABLE_GUARD_SAME_TARGET'. (basically NotNull if update: for the constraint)
     */
    public void setIsAvailableGuardSameTarget(Boolean isAvailableGuardSameTarget) {
        checkClassificationCode("IS_AVAILABLE_GUARD_SAME_TARGET", CDef.DefMeta.Flg, isAvailableGuardSameTarget);
        registerModifiedProperty("isAvailableGuardSameTarget");
        _isAvailableGuardSameTarget = isAvailableGuardSameTarget;
    }

    /**
     * [get] JOIN_PASSWORD: {VARCHAR(12)} <br>
     * 入村パスワード
     * @return The value of the column 'JOIN_PASSWORD'. (NullAllowed even if selected: for no constraint)
     */
    public String getJoinPassword() {
        checkSpecifiedProperty("joinPassword");
        return convertEmptyToNull(_joinPassword);
    }

    /**
     * [set] JOIN_PASSWORD: {VARCHAR(12)} <br>
     * 入村パスワード
     * @param joinPassword The value of the column 'JOIN_PASSWORD'. (NullAllowed: null update allowed for no constraint)
     */
    public void setJoinPassword(String joinPassword) {
        registerModifiedProperty("joinPassword");
        _joinPassword = joinPassword;
    }

    /**
     * [get] ORGANIZE: {NotNull, VARCHAR(10000)} <br>
     * 構成
     * @return The value of the column 'ORGANIZE'. (basically NotNull if selected: for the constraint)
     */
    public String getOrganize() {
        checkSpecifiedProperty("organize");
        return convertEmptyToNull(_organize);
    }

    /**
     * [set] ORGANIZE: {NotNull, VARCHAR(10000)} <br>
     * 構成
     * @param organize The value of the column 'ORGANIZE'. (basically NotNull if update: for the constraint)
     */
    public void setOrganize(String organize) {
        registerModifiedProperty("organize");
        _organize = organize;
    }

    /**
     * [get] ALLOWED_SECRET_SAY_CODE: {IX, NotNull, VARCHAR(20), FK to ALLOWED_SECRET_SAY, classification=AllowedSecretSay} <br>
     * 秘話可能な範囲コード
     * @return The value of the column 'ALLOWED_SECRET_SAY_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getAllowedSecretSayCode() {
        checkSpecifiedProperty("allowedSecretSayCode");
        return convertEmptyToNull(_allowedSecretSayCode);
    }

    /**
     * [set] ALLOWED_SECRET_SAY_CODE: {IX, NotNull, VARCHAR(20), FK to ALLOWED_SECRET_SAY, classification=AllowedSecretSay} <br>
     * 秘話可能な範囲コード
     * @param allowedSecretSayCode The value of the column 'ALLOWED_SECRET_SAY_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setAllowedSecretSayCode(String allowedSecretSayCode) {
        checkClassificationCode("ALLOWED_SECRET_SAY_CODE", CDef.DefMeta.AllowedSecretSay, allowedSecretSayCode);
        registerModifiedProperty("allowedSecretSayCode");
        _allowedSecretSayCode = allowedSecretSayCode;
    }

    /**
     * [get] IS_AVAILABLE_ACTION: {NotNull, BIT, classification=Flg} <br>
     * アクション可能か
     * @return The value of the column 'IS_AVAILABLE_ACTION'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsAvailableAction() {
        checkSpecifiedProperty("isAvailableAction");
        return _isAvailableAction;
    }

    /**
     * [set] IS_AVAILABLE_ACTION: {NotNull, BIT, classification=Flg} <br>
     * アクション可能か
     * @param isAvailableAction The value of the column 'IS_AVAILABLE_ACTION'. (basically NotNull if update: for the constraint)
     */
    public void setIsAvailableAction(Boolean isAvailableAction) {
        checkClassificationCode("IS_AVAILABLE_ACTION", CDef.DefMeta.Flg, isAvailableAction);
        registerModifiedProperty("isAvailableAction");
        _isAvailableAction = isAvailableAction;
    }

    /**
     * [get] IS_RANDOM_ORGANIZE: {NotNull, BIT, classification=Flg} <br>
     * 闇鍋構成か
     * @return The value of the column 'IS_RANDOM_ORGANIZE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsRandomOrganize() {
        checkSpecifiedProperty("isRandomOrganize");
        return _isRandomOrganize;
    }

    /**
     * [set] IS_RANDOM_ORGANIZE: {NotNull, BIT, classification=Flg} <br>
     * 闇鍋構成か
     * @param isRandomOrganize The value of the column 'IS_RANDOM_ORGANIZE'. (basically NotNull if update: for the constraint)
     */
    public void setIsRandomOrganize(Boolean isRandomOrganize) {
        checkClassificationCode("IS_RANDOM_ORGANIZE", CDef.DefMeta.Flg, isRandomOrganize);
        registerModifiedProperty("isRandomOrganize");
        _isRandomOrganize = isRandomOrganize;
    }

    /**
     * [get] IS_REINCARNATION_SKILL_ALL: {NotNull, BIT, classification=Flg} <br>
     * 全ての役職に転生可能か
     * @return The value of the column 'IS_REINCARNATION_SKILL_ALL'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsReincarnationSkillAll() {
        checkSpecifiedProperty("isReincarnationSkillAll");
        return _isReincarnationSkillAll;
    }

    /**
     * [set] IS_REINCARNATION_SKILL_ALL: {NotNull, BIT, classification=Flg} <br>
     * 全ての役職に転生可能か
     * @param isReincarnationSkillAll The value of the column 'IS_REINCARNATION_SKILL_ALL'. (basically NotNull if update: for the constraint)
     */
    public void setIsReincarnationSkillAll(Boolean isReincarnationSkillAll) {
        checkClassificationCode("IS_REINCARNATION_SKILL_ALL", CDef.DefMeta.Flg, isReincarnationSkillAll);
        registerModifiedProperty("isReincarnationSkillAll");
        _isReincarnationSkillAll = isReincarnationSkillAll;
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
     * @param allowedSecretSayCode The value of the column 'ALLOWED_SECRET_SAY_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingAllowedSecretSayCode(String allowedSecretSayCode) {
        setAllowedSecretSayCode(allowedSecretSayCode);
    }
}
