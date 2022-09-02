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
 *     VILLAGE_PLAYER_ID, VILLAGE_ID, PLAYER_ID, CHARA_ID, SKILL_CODE, REQUEST_SKILL_CODE, SECOND_REQUEST_SKILL_CODE, ROOM_NUMBER, IS_DEAD, IS_SPECTATOR, DEAD_REASON_CODE, DEAD_DAY, IS_GONE, LAST_ACCESS_DATETIME, CAMP_CODE, IS_WIN, CHARA_NAME, CHARA_SHORT_NAME, MEMO, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     DEAD_REASON, PLAYER, SKILL, VILLAGE
 *
 * [referrer table]
 *     COMMIT, MESSAGE, MESSAGE_SENDTO, VILLAGE_PLAYER_ACCESS_INFO, VILLAGE_PLAYER_DEAD_HISTORY, VILLAGE_PLAYER_ROOM_HISTORY, VILLAGE_PLAYER_SKILL_HISTORY, VILLAGE_PLAYER_STATUS
 *
 * [foreign property]
 *     deadReason, player, skillByRequestSkillCode, skillBySecondRequestSkillCode, skillBySkillCode, village
 *
 * [referrer property]
 *     commitList, messageByToVillagePlayerIdList, messageByVillagePlayerIdList, messageSendtoList, villagePlayerAccessInfoList, villagePlayerDeadHistoryList, villagePlayerRoomHistoryList, villagePlayerSkillHistoryList, villagePlayerStatusByToVillagePlayerIdList, villagePlayerStatusByVillagePlayerIdList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * Integer villageId = entity.getVillageId();
 * Integer playerId = entity.getPlayerId();
 * Integer charaId = entity.getCharaId();
 * String skillCode = entity.getSkillCode();
 * String requestSkillCode = entity.getRequestSkillCode();
 * String secondRequestSkillCode = entity.getSecondRequestSkillCode();
 * Integer roomNumber = entity.getRoomNumber();
 * Boolean isDead = entity.getIsDead();
 * Boolean isSpectator = entity.getIsSpectator();
 * String deadReasonCode = entity.getDeadReasonCode();
 * Integer deadDay = entity.getDeadDay();
 * Boolean isGone = entity.getIsGone();
 * java.time.LocalDateTime lastAccessDatetime = entity.getLastAccessDatetime();
 * String campCode = entity.getCampCode();
 * Boolean isWin = entity.getIsWin();
 * String charaName = entity.getCharaName();
 * String charaShortName = entity.getCharaShortName();
 * String memo = entity.getMemo();
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
 * entity.setSecondRequestSkillCode(secondRequestSkillCode);
 * entity.setRoomNumber(roomNumber);
 * entity.setIsDead(isDead);
 * entity.setIsSpectator(isSpectator);
 * entity.setDeadReasonCode(deadReasonCode);
 * entity.setDeadDay(deadDay);
 * entity.setIsGone(isGone);
 * entity.setLastAccessDatetime(lastAccessDatetime);
 * entity.setCampCode(campCode);
 * entity.setIsWin(isWin);
 * entity.setCharaName(charaName);
 * entity.setCharaShortName(charaShortName);
 * entity.setMemo(memo);
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

    /** VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE} */
    protected Integer _villageId;

    /** PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER} */
    protected Integer _playerId;

    /** CHARA_ID: {NotNull, INT UNSIGNED(10)} */
    protected Integer _charaId;

    /** SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} */
    protected String _skillCode;

    /** REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} */
    protected String _requestSkillCode;

    /** SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} */
    protected String _secondRequestSkillCode;

    /** ROOM_NUMBER: {INT UNSIGNED(10)} */
    protected Integer _roomNumber;

    /** IS_DEAD: {NotNull, BIT, classification=Flg} */
    protected Boolean _isDead;

    /** IS_SPECTATOR: {NotNull, BIT, classification=Flg} */
    protected Boolean _isSpectator;

    /** DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} */
    protected String _deadReasonCode;

    /** DEAD_DAY: {INT UNSIGNED(10)} */
    protected Integer _deadDay;

    /** IS_GONE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isGone;

    /** LAST_ACCESS_DATETIME: {DATETIME(19)} */
    protected java.time.LocalDateTime _lastAccessDatetime;

    /** CAMP_CODE: {VARCHAR(20)} */
    protected String _campCode;

    /** IS_WIN: {BIT, classification=Flg} */
    protected Boolean _isWin;

    /** CHARA_NAME: {NotNull, VARCHAR(40)} */
    protected String _charaName;

    /** CHARA_SHORT_NAME: {NotNull, CHAR(1)} */
    protected String _charaShortName;

    /** MEMO: {VARCHAR(20)} */
    protected String _memo;

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
        return "VILLAGE_PLAYER";
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
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Skill getSkillCodeAsSkill() {
        return CDef.Skill.codeOf(getSkillCode());
    }

    /**
     * Set the value of skillCode as the classification of Skill. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setSkillCodeAsSkill(CDef.Skill cdef) {
        setSkillCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of requestSkillCode as the classification of Skill. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Skill getRequestSkillCodeAsSkill() {
        return CDef.Skill.codeOf(getRequestSkillCode());
    }

    /**
     * Set the value of requestSkillCode as the classification of Skill. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setRequestSkillCodeAsSkill(CDef.Skill cdef) {
        setRequestSkillCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of secondRequestSkillCode as the classification of Skill. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Skill getSecondRequestSkillCodeAsSkill() {
        return CDef.Skill.codeOf(getSecondRequestSkillCode());
    }

    /**
     * Set the value of secondRequestSkillCode as the classification of Skill. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setSecondRequestSkillCodeAsSkill(CDef.Skill cdef) {
        setSecondRequestSkillCode(cdef != null ? cdef.code() : null);
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

    /**
     * Get the value of isWin as the classification of Flg. <br>
     * IS_WIN: {BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsWinAsFlg() {
        return CDef.Flg.codeOf(getIsWin());
    }

    /**
     * Set the value of isWin as the classification of Flg. <br>
     * IS_WIN: {BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsWinAsFlg(CDef.Flg cdef) {
        setIsWin(cdef != null ? toBoolean(cdef.code()) : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of skillCode as 教唆者 (ABETTER). <br>
     * 教唆者
     */
    public void setSkillCode_教唆者() {
        setSkillCodeAsSkill(CDef.Skill.教唆者);
    }

    /**
     * Set the value of skillCode as 絶対人狼 (ABSOLUTEWOLF). <br>
     * 絶対人狼
     */
    public void setSkillCode_絶対人狼() {
        setSkillCodeAsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Set the value of skillCode as 占星術師 (ASTROLOGER). <br>
     * 占星術師
     */
    public void setSkillCode_占星術師() {
        setSkillCodeAsSkill(CDef.Skill.占星術師);
    }

    /**
     * Set the value of skillCode as ババ (BABA). <br>
     * ババ
     */
    public void setSkillCode_ババ() {
        setSkillCodeAsSkill(CDef.Skill.ババ);
    }

    /**
     * Set the value of skillCode as 美人局 (BADGERGAME). <br>
     * 美人局
     */
    public void setSkillCode_美人局() {
        setSkillCodeAsSkill(CDef.Skill.美人局);
    }

    /**
     * Set the value of skillCode as パン屋 (BAKERY). <br>
     * パン屋
     */
    public void setSkillCode_パン屋() {
        setSkillCodeAsSkill(CDef.Skill.パン屋);
    }

    /**
     * Set the value of skillCode as バールのようなもの (BAR). <br>
     * バールのようなもの
     */
    public void setSkillCode_バールのようなもの() {
        setSkillCodeAsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Set the value of skillCode as 黒箱者 (BLACKBOX). <br>
     * 黒箱者
     */
    public void setSkillCode_黒箱者() {
        setSkillCodeAsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Set the value of skillCode as 爆弾魔 (BOMBER). <br>
     * 爆弾魔
     */
    public void setSkillCode_爆弾魔() {
        setSkillCodeAsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Set the value of skillCode as 誑狐 (CHEATERFOX). <br>
     * 誑狐
     */
    public void setSkillCode_誑狐() {
        setSkillCodeAsSkill(CDef.Skill.誑狐);
    }

    /**
     * Set the value of skillCode as C国狂人 (CMADMAN). <br>
     * C国狂人
     */
    public void setSkillCode_C国狂人() {
        setSkillCodeAsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Set the value of skillCode as 同棲者 (COHABITER). <br>
     * 同棲者
     */
    public void setSkillCode_同棲者() {
        setSkillCodeAsSkill(CDef.Skill.同棲者);
    }

    /**
     * Set the value of skillCode as 指揮官 (COMMANDER). <br>
     * 指揮官
     */
    public void setSkillCode_指揮官() {
        setSkillCodeAsSkill(CDef.Skill.指揮官);
    }

    /**
     * Set the value of skillCode as 検死官 (CORONER). <br>
     * 検死官
     */
    public void setSkillCode_検死官() {
        setSkillCodeAsSkill(CDef.Skill.検死官);
    }

    /**
     * Set the value of skillCode as 求愛者 (COURTSHIP). <br>
     * 求愛者
     */
    public void setSkillCode_求愛者() {
        setSkillCodeAsSkill(CDef.Skill.求愛者);
    }

    /**
     * Set the value of skillCode as おまかせ愉快犯陣営 (CRIMINALS). <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSkillCode_おまかせ愉快犯陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Set the value of skillCode as 呪狼 (CURSEWOLF). <br>
     * 呪狼
     */
    public void setSkillCode_呪狼() {
        setSkillCodeAsSkill(CDef.Skill.呪狼);
    }

    /**
     * Set the value of skillCode as 探偵 (DETECTIVE). <br>
     * 探偵
     */
    public void setSkillCode_探偵() {
        setSkillCodeAsSkill(CDef.Skill.探偵);
    }

    /**
     * Set the value of skillCode as 箪笥 (DRAWERS). <br>
     * 箪笥
     */
    public void setSkillCode_箪笥() {
        setSkillCodeAsSkill(CDef.Skill.箪笥);
    }

    /**
     * Set the value of skillCode as 不止者 (DYINGPOINTER). <br>
     * 不止者
     */
    public void setSkillCode_不止者() {
        setSkillCodeAsSkill(CDef.Skill.不止者);
    }

    /**
     * Set the value of skillCode as 闇探偵 (EVILDETECTIVE). <br>
     * 闇探偵
     */
    public void setSkillCode_闇探偵() {
        setSkillCodeAsSkill(CDef.Skill.闇探偵);
    }

    /**
     * Set the value of skillCode as 魔神官 (EVILMEDIUM). <br>
     * 魔神官
     */
    public void setSkillCode_魔神官() {
        setSkillCodeAsSkill(CDef.Skill.魔神官);
    }

    /**
     * Set the value of skillCode as 執行人 (EXECUTIONER). <br>
     * 執行人
     */
    public void setSkillCode_執行人() {
        setSkillCodeAsSkill(CDef.Skill.執行人);
    }

    /**
     * Set the value of skillCode as 冤罪者 (FALSECHARGES). <br>
     * 冤罪者
     */
    public void setSkillCode_冤罪者() {
        setSkillCodeAsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Set the value of skillCode as 狂信者 (FANATIC). <br>
     * 狂信者
     */
    public void setSkillCode_狂信者() {
        setSkillCodeAsSkill(CDef.Skill.狂信者);
    }

    /**
     * Set the value of skillCode as 妄想癖 (FANTASIST). <br>
     * 妄想癖
     */
    public void setSkillCode_妄想癖() {
        setSkillCodeAsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Set the value of skillCode as 花占い師 (FLOWERSEER). <br>
     * 花占い師
     */
    public void setSkillCode_花占い師() {
        setSkillCodeAsSkill(CDef.Skill.花占い師);
    }

    /**
     * Set the value of skillCode as おまかせ足音職 (FOOTSTEPS). <br>
     * おまかせ（足音職）
     */
    public void setSkillCode_おまかせ足音職() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Set the value of skillCode as 妖狐 (FOX). <br>
     * 妖狐
     */
    public void setSkillCode_妖狐() {
        setSkillCodeAsSkill(CDef.Skill.妖狐);
    }

    /**
     * Set the value of skillCode as おまかせ妖狐陣営 (FOXS). <br>
     * おまかせ（妖狐陣営）
     */
    public void setSkillCode_おまかせ妖狐陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Set the value of skillCode as おまかせ役職窓あり (FRIENDS). <br>
     * おまかせ（役職窓あり）
     */
    public void setSkillCode_おまかせ役職窓あり() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Set the value of skillCode as 果実籠 (FRUITSBASKET). <br>
     * 果実籠
     */
    public void setSkillCode_果実籠() {
        setSkillCodeAsSkill(CDef.Skill.果実籠);
    }

    /**
     * Set the value of skillCode as 歩狼 (FUWOLF). <br>
     * 歩狼
     */
    public void setSkillCode_歩狼() {
        setSkillCodeAsSkill(CDef.Skill.歩狼);
    }

    /**
     * Set the value of skillCode as 銀狼 (GINWOLF). <br>
     * 銀狼
     */
    public void setSkillCode_銀狼() {
        setSkillCodeAsSkill(CDef.Skill.銀狼);
    }

    /**
     * Set the value of skillCode as ごん (GONFOX). <br>
     * ごん
     */
    public void setSkillCode_ごん() {
        setSkillCodeAsSkill(CDef.Skill.ごん);
    }

    /**
     * Set the value of skillCode as 導師 (GURU). <br>
     * 導師
     */
    public void setSkillCode_導師() {
        setSkillCodeAsSkill(CDef.Skill.導師);
    }

    /**
     * Set the value of skillCode as 堅狼 (HARDWOLF). <br>
     * 堅狼
     */
    public void setSkillCode_堅狼() {
        setSkillCodeAsSkill(CDef.Skill.堅狼);
    }

    /**
     * Set the value of skillCode as 申し子 (HEAVENCHILD). <br>
     * 申し子
     */
    public void setSkillCode_申し子() {
        setSkillCodeAsSkill(CDef.Skill.申し子);
    }

    /**
     * Set the value of skillCode as 仙狐 (HERMITFOX). <br>
     * 仙狐
     */
    public void setSkillCode_仙狐() {
        setSkillCodeAsSkill(CDef.Skill.仙狐);
    }

    /**
     * Set the value of skillCode as 飛狼 (HISHAWOLF). <br>
     * 飛狼
     */
    public void setSkillCode_飛狼() {
        setSkillCodeAsSkill(CDef.Skill.飛狼);
    }

    /**
     * Set the value of skillCode as 狩人 (HUNTER). <br>
     * 狩人
     */
    public void setSkillCode_狩人() {
        setSkillCodeAsSkill(CDef.Skill.狩人);
    }

    /**
     * Set the value of skillCode as 背徳者 (IMMORAL). <br>
     * 背徳者
     */
    public void setSkillCode_背徳者() {
        setSkillCodeAsSkill(CDef.Skill.背徳者);
    }

    /**
     * Set the value of skillCode as 稲荷 (INARI). <br>
     * 稲荷
     */
    public void setSkillCode_稲荷() {
        setSkillCodeAsSkill(CDef.Skill.稲荷);
    }

    /**
     * Set the value of skillCode as 煽動者 (INSTIGATOR). <br>
     * 煽動者
     */
    public void setSkillCode_煽動者() {
        setSkillCodeAsSkill(CDef.Skill.煽動者);
    }

    /**
     * Set the value of skillCode as 保険屋 (INSURANCER). <br>
     * 保険屋
     */
    public void setSkillCode_保険屋() {
        setSkillCodeAsSkill(CDef.Skill.保険屋);
    }

    /**
     * Set the value of skillCode as 絡新婦 (JOROGUMO). <br>
     * 絡新婦
     */
    public void setSkillCode_絡新婦() {
        setSkillCodeAsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Set the value of skillCode as 角狼 (KAKUWOLF). <br>
     * 角狼
     */
    public void setSkillCode_角狼() {
        setSkillCodeAsSkill(CDef.Skill.角狼);
    }

    /**
     * Set the value of skillCode as 王狼 (KINGWOLF). <br>
     * 王狼
     */
    public void setSkillCode_王狼() {
        setSkillCodeAsSkill(CDef.Skill.王狼);
    }

    /**
     * Set the value of skillCode as 金狼 (KINWOLF). <br>
     * 金狼
     */
    public void setSkillCode_金狼() {
        setSkillCodeAsSkill(CDef.Skill.金狼);
    }

    /**
     * Set the value of skillCode as 管狐 (KUDAFOX). <br>
     * 管狐
     */
    public void setSkillCode_管狐() {
        setSkillCodeAsSkill(CDef.Skill.管狐);
    }

    /**
     * Set the value of skillCode as 弁護士 (LAWYER). <br>
     * 弁護士
     */
    public void setSkillCode_弁護士() {
        setSkillCodeAsSkill(CDef.Skill.弁護士);
    }

    /**
     * Set the value of skillCode as おまかせ (LEFTOVER). <br>
     * おまかせ
     */
    public void setSkillCode_おまかせ() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Set the value of skillCode as 聴狂人 (LISTENMADMAN). <br>
     * 聴狂人
     */
    public void setSkillCode_聴狂人() {
        setSkillCodeAsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Set the value of skillCode as 黙狼 (LISTENWOLF). <br>
     * 黙狼
     */
    public void setSkillCode_黙狼() {
        setSkillCodeAsSkill(CDef.Skill.黙狼);
    }

    /**
     * Set the value of skillCode as 一匹狼 (LONEWOLF). <br>
     * 一匹狼
     */
    public void setSkillCode_一匹狼() {
        setSkillCodeAsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Set the value of skillCode as 拡声者 (LOUDSPEAKER). <br>
     * 拡声者
     */
    public void setSkillCode_拡声者() {
        setSkillCodeAsSkill(CDef.Skill.拡声者);
    }

    /**
     * Set the value of skillCode as 恋人 (LOVER). <br>
     * 恋人
     */
    public void setSkillCode_恋人() {
        setSkillCodeAsSkill(CDef.Skill.恋人);
    }

    /**
     * Set the value of skillCode as おまかせ恋人陣営 (LOVERS). <br>
     * おまかせ（恋人陣営）
     */
    public void setSkillCode_おまかせ恋人陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Set the value of skillCode as 強運者 (LUCKYMAN). <br>
     * 強運者
     */
    public void setSkillCode_強運者() {
        setSkillCodeAsSkill(CDef.Skill.強運者);
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
     * Set the value of skillCode as マタギ (MATAGI). <br>
     * マタギ
     */
    public void setSkillCode_マタギ() {
        setSkillCodeAsSkill(CDef.Skill.マタギ);
    }

    /**
     * Set the value of skillCode as 市長 (MAYOR). <br>
     * 市長
     */
    public void setSkillCode_市長() {
        setSkillCodeAsSkill(CDef.Skill.市長);
    }

    /**
     * Set the value of skillCode as 霊能者 (MEDIUM). <br>
     * 霊能者
     */
    public void setSkillCode_霊能者() {
        setSkillCodeAsSkill(CDef.Skill.霊能者);
    }

    /**
     * Set the value of skillCode as 耳年増 (MIMIDOSHIMA). <br>
     * 耳年増
     */
    public void setSkillCode_耳年増() {
        setSkillCodeAsSkill(CDef.Skill.耳年増);
    }

    /**
     * Set the value of skillCode as 死霊術師 (NECROMANCER). <br>
     * 死霊術師
     */
    public void setSkillCode_死霊術師() {
        setSkillCodeAsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Set the value of skillCode as 夜狐 (NIGHTFOX). <br>
     * 夜狐
     */
    public void setSkillCode_夜狐() {
        setSkillCodeAsSkill(CDef.Skill.夜狐);
    }

    /**
     * Set the value of skillCode as おまかせ役職窓なし (NOFRIENDS). <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_おまかせ役職窓なし() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Set the value of skillCode as おまかせ人外 (NOVILLAGERS). <br>
     * おまかせ（人外）
     */
    public void setSkillCode_おまかせ人外() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Set the value of skillCode as 監視者 (OBSERVER). <br>
     * 監視者
     */
    public void setSkillCode_監視者() {
        setSkillCodeAsSkill(CDef.Skill.監視者);
    }

    /**
     * Set the value of skillCode as 全知者 (OMNISCIENCE). <br>
     * 全知者
     */
    public void setSkillCode_全知者() {
        setSkillCodeAsSkill(CDef.Skill.全知者);
    }

    /**
     * Set the value of skillCode as 梟 (OWL). <br>
     * 梟
     */
    public void setSkillCode_梟() {
        setSkillCodeAsSkill(CDef.Skill.梟);
    }

    /**
     * Set the value of skillCode as 牧師 (PASTOR). <br>
     * 牧師
     */
    public void setSkillCode_牧師() {
        setSkillCodeAsSkill(CDef.Skill.牧師);
    }

    /**
     * Set the value of skillCode as 画鋲 (PUSHPIN). <br>
     * 画鋲
     */
    public void setSkillCode_画鋲() {
        setSkillCodeAsSkill(CDef.Skill.画鋲);
    }

    /**
     * Set the value of skillCode as 虹職人 (RAINBOW). <br>
     * 虹職人
     */
    public void setSkillCode_虹職人() {
        setSkillCodeAsSkill(CDef.Skill.虹職人);
    }

    /**
     * Set the value of skillCode as 転生者 (REINCARNATION). <br>
     * 転生者
     */
    public void setSkillCode_転生者() {
        setSkillCodeAsSkill(CDef.Skill.転生者);
    }

    /**
     * Set the value of skillCode as 怨恨者 (RESENTER). <br>
     * 怨恨者
     */
    public void setSkillCode_怨恨者() {
        setSkillCodeAsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Set the value of skillCode as 蘇生者 (RESUSCITATOR). <br>
     * 蘇生者
     */
    public void setSkillCode_蘇生者() {
        setSkillCodeAsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Set the value of skillCode as 占い師 (SEER). <br>
     * 占い師
     */
    public void setSkillCode_占い師() {
        setSkillCodeAsSkill(CDef.Skill.占い師);
    }

    /**
     * Set the value of skillCode as 破局者 (SEPARATOR). <br>
     * 破局者
     */
    public void setSkillCode_破局者() {
        setSkillCodeAsSkill(CDef.Skill.破局者);
    }

    /**
     * Set the value of skillCode as 静狼 (SILENTWOLF). <br>
     * 静狼
     */
    public void setSkillCode_静狼() {
        setSkillCodeAsSkill(CDef.Skill.静狼);
    }

    /**
     * Set the value of skillCode as 感覚者 (SIXTHSENSOR). <br>
     * 感覚者
     */
    public void setSkillCode_感覚者() {
        setSkillCodeAsSkill(CDef.Skill.感覚者);
    }

    /**
     * Set the value of skillCode as 夢遊病者 (SLEEPWALKER). <br>
     * 夢遊病者
     */
    public void setSkillCode_夢遊病者() {
        setSkillCodeAsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Set the value of skillCode as 防音者 (SOUNDPROOFER). <br>
     * 防音者
     */
    public void setSkillCode_防音者() {
        setSkillCodeAsSkill(CDef.Skill.防音者);
    }

    /**
     * Set the value of skillCode as ストーカー (STALKER). <br>
     * ストーカー
     */
    public void setSkillCode_ストーカー() {
        setSkillCodeAsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Set the value of skillCode as 濁点者 (TATSUYA). <br>
     * 濁点者
     */
    public void setSkillCode_濁点者() {
        setSkillCodeAsSkill(CDef.Skill.濁点者);
    }

    /**
     * Set the value of skillCode as 泥棒猫 (THIEFCAT). <br>
     * 泥棒猫
     */
    public void setSkillCode_泥棒猫() {
        setSkillCodeAsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Set the value of skillCode as 翻訳者 (TRANSLATOR). <br>
     * 翻訳者
     */
    public void setSkillCode_翻訳者() {
        setSkillCodeAsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Set the value of skillCode as 罠師 (TRAPPER). <br>
     * 罠師
     */
    public void setSkillCode_罠師() {
        setSkillCodeAsSkill(CDef.Skill.罠師);
    }

    /**
     * Set the value of skillCode as 騙狐 (TRICKFOX). <br>
     * 騙狐
     */
    public void setSkillCode_騙狐() {
        setSkillCodeAsSkill(CDef.Skill.騙狐);
    }

    /**
     * Set the value of skillCode as トラック (TRUCK). <br>
     * トラック
     */
    public void setSkillCode_トラック() {
        setSkillCodeAsSkill(CDef.Skill.トラック);
    }

    /**
     * Set the value of skillCode as 村人 (VILLAGER). <br>
     * 村人
     */
    public void setSkillCode_村人() {
        setSkillCodeAsSkill(CDef.Skill.村人);
    }

    /**
     * Set the value of skillCode as おまかせ村人陣営 (VILLAGERS). <br>
     * おまかせ（村人陣営）
     */
    public void setSkillCode_おまかせ村人陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Set the value of skillCode as 壁殴り代行 (WALLPUNCHER). <br>
     * 壁殴り代行
     */
    public void setSkillCode_壁殴り代行() {
        setSkillCodeAsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Set the value of skillCode as 風来狩人 (WANDERER). <br>
     * 風来狩人
     */
    public void setSkillCode_風来狩人() {
        setSkillCodeAsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Set the value of skillCode as 人狼 (WEREWOLF). <br>
     * 人狼
     */
    public void setSkillCode_人狼() {
        setSkillCodeAsSkill(CDef.Skill.人狼);
    }

    /**
     * Set the value of skillCode as おまかせ人狼陣営 (WEREWOLFS). <br>
     * おまかせ（人狼陣営）
     */
    public void setSkillCode_おまかせ人狼陣営() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Set the value of skillCode as 当選者 (WINNER). <br>
     * 当選者
     */
    public void setSkillCode_当選者() {
        setSkillCodeAsSkill(CDef.Skill.当選者);
    }

    /**
     * Set the value of skillCode as 賢者 (WISE). <br>
     * 賢者
     */
    public void setSkillCode_賢者() {
        setSkillCodeAsSkill(CDef.Skill.賢者);
    }

    /**
     * Set the value of skillCode as 智狼 (WISEWOLF). <br>
     * 智狼
     */
    public void setSkillCode_智狼() {
        setSkillCodeAsSkill(CDef.Skill.智狼);
    }

    /**
     * Set the value of requestSkillCode as 教唆者 (ABETTER). <br>
     * 教唆者
     */
    public void setRequestSkillCode_教唆者() {
        setRequestSkillCodeAsSkill(CDef.Skill.教唆者);
    }

    /**
     * Set the value of requestSkillCode as 絶対人狼 (ABSOLUTEWOLF). <br>
     * 絶対人狼
     */
    public void setRequestSkillCode_絶対人狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Set the value of requestSkillCode as 占星術師 (ASTROLOGER). <br>
     * 占星術師
     */
    public void setRequestSkillCode_占星術師() {
        setRequestSkillCodeAsSkill(CDef.Skill.占星術師);
    }

    /**
     * Set the value of requestSkillCode as ババ (BABA). <br>
     * ババ
     */
    public void setRequestSkillCode_ババ() {
        setRequestSkillCodeAsSkill(CDef.Skill.ババ);
    }

    /**
     * Set the value of requestSkillCode as 美人局 (BADGERGAME). <br>
     * 美人局
     */
    public void setRequestSkillCode_美人局() {
        setRequestSkillCodeAsSkill(CDef.Skill.美人局);
    }

    /**
     * Set the value of requestSkillCode as パン屋 (BAKERY). <br>
     * パン屋
     */
    public void setRequestSkillCode_パン屋() {
        setRequestSkillCodeAsSkill(CDef.Skill.パン屋);
    }

    /**
     * Set the value of requestSkillCode as バールのようなもの (BAR). <br>
     * バールのようなもの
     */
    public void setRequestSkillCode_バールのようなもの() {
        setRequestSkillCodeAsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Set the value of requestSkillCode as 黒箱者 (BLACKBOX). <br>
     * 黒箱者
     */
    public void setRequestSkillCode_黒箱者() {
        setRequestSkillCodeAsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Set the value of requestSkillCode as 爆弾魔 (BOMBER). <br>
     * 爆弾魔
     */
    public void setRequestSkillCode_爆弾魔() {
        setRequestSkillCodeAsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Set the value of requestSkillCode as 誑狐 (CHEATERFOX). <br>
     * 誑狐
     */
    public void setRequestSkillCode_誑狐() {
        setRequestSkillCodeAsSkill(CDef.Skill.誑狐);
    }

    /**
     * Set the value of requestSkillCode as C国狂人 (CMADMAN). <br>
     * C国狂人
     */
    public void setRequestSkillCode_C国狂人() {
        setRequestSkillCodeAsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Set the value of requestSkillCode as 同棲者 (COHABITER). <br>
     * 同棲者
     */
    public void setRequestSkillCode_同棲者() {
        setRequestSkillCodeAsSkill(CDef.Skill.同棲者);
    }

    /**
     * Set the value of requestSkillCode as 指揮官 (COMMANDER). <br>
     * 指揮官
     */
    public void setRequestSkillCode_指揮官() {
        setRequestSkillCodeAsSkill(CDef.Skill.指揮官);
    }

    /**
     * Set the value of requestSkillCode as 検死官 (CORONER). <br>
     * 検死官
     */
    public void setRequestSkillCode_検死官() {
        setRequestSkillCodeAsSkill(CDef.Skill.検死官);
    }

    /**
     * Set the value of requestSkillCode as 求愛者 (COURTSHIP). <br>
     * 求愛者
     */
    public void setRequestSkillCode_求愛者() {
        setRequestSkillCodeAsSkill(CDef.Skill.求愛者);
    }

    /**
     * Set the value of requestSkillCode as おまかせ愉快犯陣営 (CRIMINALS). <br>
     * おまかせ（愉快犯陣営）
     */
    public void setRequestSkillCode_おまかせ愉快犯陣営() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Set the value of requestSkillCode as 呪狼 (CURSEWOLF). <br>
     * 呪狼
     */
    public void setRequestSkillCode_呪狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.呪狼);
    }

    /**
     * Set the value of requestSkillCode as 探偵 (DETECTIVE). <br>
     * 探偵
     */
    public void setRequestSkillCode_探偵() {
        setRequestSkillCodeAsSkill(CDef.Skill.探偵);
    }

    /**
     * Set the value of requestSkillCode as 箪笥 (DRAWERS). <br>
     * 箪笥
     */
    public void setRequestSkillCode_箪笥() {
        setRequestSkillCodeAsSkill(CDef.Skill.箪笥);
    }

    /**
     * Set the value of requestSkillCode as 不止者 (DYINGPOINTER). <br>
     * 不止者
     */
    public void setRequestSkillCode_不止者() {
        setRequestSkillCodeAsSkill(CDef.Skill.不止者);
    }

    /**
     * Set the value of requestSkillCode as 闇探偵 (EVILDETECTIVE). <br>
     * 闇探偵
     */
    public void setRequestSkillCode_闇探偵() {
        setRequestSkillCodeAsSkill(CDef.Skill.闇探偵);
    }

    /**
     * Set the value of requestSkillCode as 魔神官 (EVILMEDIUM). <br>
     * 魔神官
     */
    public void setRequestSkillCode_魔神官() {
        setRequestSkillCodeAsSkill(CDef.Skill.魔神官);
    }

    /**
     * Set the value of requestSkillCode as 執行人 (EXECUTIONER). <br>
     * 執行人
     */
    public void setRequestSkillCode_執行人() {
        setRequestSkillCodeAsSkill(CDef.Skill.執行人);
    }

    /**
     * Set the value of requestSkillCode as 冤罪者 (FALSECHARGES). <br>
     * 冤罪者
     */
    public void setRequestSkillCode_冤罪者() {
        setRequestSkillCodeAsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Set the value of requestSkillCode as 狂信者 (FANATIC). <br>
     * 狂信者
     */
    public void setRequestSkillCode_狂信者() {
        setRequestSkillCodeAsSkill(CDef.Skill.狂信者);
    }

    /**
     * Set the value of requestSkillCode as 妄想癖 (FANTASIST). <br>
     * 妄想癖
     */
    public void setRequestSkillCode_妄想癖() {
        setRequestSkillCodeAsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Set the value of requestSkillCode as 花占い師 (FLOWERSEER). <br>
     * 花占い師
     */
    public void setRequestSkillCode_花占い師() {
        setRequestSkillCodeAsSkill(CDef.Skill.花占い師);
    }

    /**
     * Set the value of requestSkillCode as おまかせ足音職 (FOOTSTEPS). <br>
     * おまかせ（足音職）
     */
    public void setRequestSkillCode_おまかせ足音職() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Set the value of requestSkillCode as 妖狐 (FOX). <br>
     * 妖狐
     */
    public void setRequestSkillCode_妖狐() {
        setRequestSkillCodeAsSkill(CDef.Skill.妖狐);
    }

    /**
     * Set the value of requestSkillCode as おまかせ妖狐陣営 (FOXS). <br>
     * おまかせ（妖狐陣営）
     */
    public void setRequestSkillCode_おまかせ妖狐陣営() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Set the value of requestSkillCode as おまかせ役職窓あり (FRIENDS). <br>
     * おまかせ（役職窓あり）
     */
    public void setRequestSkillCode_おまかせ役職窓あり() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Set the value of requestSkillCode as 果実籠 (FRUITSBASKET). <br>
     * 果実籠
     */
    public void setRequestSkillCode_果実籠() {
        setRequestSkillCodeAsSkill(CDef.Skill.果実籠);
    }

    /**
     * Set the value of requestSkillCode as 歩狼 (FUWOLF). <br>
     * 歩狼
     */
    public void setRequestSkillCode_歩狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.歩狼);
    }

    /**
     * Set the value of requestSkillCode as 銀狼 (GINWOLF). <br>
     * 銀狼
     */
    public void setRequestSkillCode_銀狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.銀狼);
    }

    /**
     * Set the value of requestSkillCode as ごん (GONFOX). <br>
     * ごん
     */
    public void setRequestSkillCode_ごん() {
        setRequestSkillCodeAsSkill(CDef.Skill.ごん);
    }

    /**
     * Set the value of requestSkillCode as 導師 (GURU). <br>
     * 導師
     */
    public void setRequestSkillCode_導師() {
        setRequestSkillCodeAsSkill(CDef.Skill.導師);
    }

    /**
     * Set the value of requestSkillCode as 堅狼 (HARDWOLF). <br>
     * 堅狼
     */
    public void setRequestSkillCode_堅狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.堅狼);
    }

    /**
     * Set the value of requestSkillCode as 申し子 (HEAVENCHILD). <br>
     * 申し子
     */
    public void setRequestSkillCode_申し子() {
        setRequestSkillCodeAsSkill(CDef.Skill.申し子);
    }

    /**
     * Set the value of requestSkillCode as 仙狐 (HERMITFOX). <br>
     * 仙狐
     */
    public void setRequestSkillCode_仙狐() {
        setRequestSkillCodeAsSkill(CDef.Skill.仙狐);
    }

    /**
     * Set the value of requestSkillCode as 飛狼 (HISHAWOLF). <br>
     * 飛狼
     */
    public void setRequestSkillCode_飛狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.飛狼);
    }

    /**
     * Set the value of requestSkillCode as 狩人 (HUNTER). <br>
     * 狩人
     */
    public void setRequestSkillCode_狩人() {
        setRequestSkillCodeAsSkill(CDef.Skill.狩人);
    }

    /**
     * Set the value of requestSkillCode as 背徳者 (IMMORAL). <br>
     * 背徳者
     */
    public void setRequestSkillCode_背徳者() {
        setRequestSkillCodeAsSkill(CDef.Skill.背徳者);
    }

    /**
     * Set the value of requestSkillCode as 稲荷 (INARI). <br>
     * 稲荷
     */
    public void setRequestSkillCode_稲荷() {
        setRequestSkillCodeAsSkill(CDef.Skill.稲荷);
    }

    /**
     * Set the value of requestSkillCode as 煽動者 (INSTIGATOR). <br>
     * 煽動者
     */
    public void setRequestSkillCode_煽動者() {
        setRequestSkillCodeAsSkill(CDef.Skill.煽動者);
    }

    /**
     * Set the value of requestSkillCode as 保険屋 (INSURANCER). <br>
     * 保険屋
     */
    public void setRequestSkillCode_保険屋() {
        setRequestSkillCodeAsSkill(CDef.Skill.保険屋);
    }

    /**
     * Set the value of requestSkillCode as 絡新婦 (JOROGUMO). <br>
     * 絡新婦
     */
    public void setRequestSkillCode_絡新婦() {
        setRequestSkillCodeAsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Set the value of requestSkillCode as 角狼 (KAKUWOLF). <br>
     * 角狼
     */
    public void setRequestSkillCode_角狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.角狼);
    }

    /**
     * Set the value of requestSkillCode as 王狼 (KINGWOLF). <br>
     * 王狼
     */
    public void setRequestSkillCode_王狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.王狼);
    }

    /**
     * Set the value of requestSkillCode as 金狼 (KINWOLF). <br>
     * 金狼
     */
    public void setRequestSkillCode_金狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.金狼);
    }

    /**
     * Set the value of requestSkillCode as 管狐 (KUDAFOX). <br>
     * 管狐
     */
    public void setRequestSkillCode_管狐() {
        setRequestSkillCodeAsSkill(CDef.Skill.管狐);
    }

    /**
     * Set the value of requestSkillCode as 弁護士 (LAWYER). <br>
     * 弁護士
     */
    public void setRequestSkillCode_弁護士() {
        setRequestSkillCodeAsSkill(CDef.Skill.弁護士);
    }

    /**
     * Set the value of requestSkillCode as おまかせ (LEFTOVER). <br>
     * おまかせ
     */
    public void setRequestSkillCode_おまかせ() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Set the value of requestSkillCode as 聴狂人 (LISTENMADMAN). <br>
     * 聴狂人
     */
    public void setRequestSkillCode_聴狂人() {
        setRequestSkillCodeAsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Set the value of requestSkillCode as 黙狼 (LISTENWOLF). <br>
     * 黙狼
     */
    public void setRequestSkillCode_黙狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.黙狼);
    }

    /**
     * Set the value of requestSkillCode as 一匹狼 (LONEWOLF). <br>
     * 一匹狼
     */
    public void setRequestSkillCode_一匹狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Set the value of requestSkillCode as 拡声者 (LOUDSPEAKER). <br>
     * 拡声者
     */
    public void setRequestSkillCode_拡声者() {
        setRequestSkillCodeAsSkill(CDef.Skill.拡声者);
    }

    /**
     * Set the value of requestSkillCode as 恋人 (LOVER). <br>
     * 恋人
     */
    public void setRequestSkillCode_恋人() {
        setRequestSkillCodeAsSkill(CDef.Skill.恋人);
    }

    /**
     * Set the value of requestSkillCode as おまかせ恋人陣営 (LOVERS). <br>
     * おまかせ（恋人陣営）
     */
    public void setRequestSkillCode_おまかせ恋人陣営() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Set the value of requestSkillCode as 強運者 (LUCKYMAN). <br>
     * 強運者
     */
    public void setRequestSkillCode_強運者() {
        setRequestSkillCodeAsSkill(CDef.Skill.強運者);
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
     * Set the value of requestSkillCode as マタギ (MATAGI). <br>
     * マタギ
     */
    public void setRequestSkillCode_マタギ() {
        setRequestSkillCodeAsSkill(CDef.Skill.マタギ);
    }

    /**
     * Set the value of requestSkillCode as 市長 (MAYOR). <br>
     * 市長
     */
    public void setRequestSkillCode_市長() {
        setRequestSkillCodeAsSkill(CDef.Skill.市長);
    }

    /**
     * Set the value of requestSkillCode as 霊能者 (MEDIUM). <br>
     * 霊能者
     */
    public void setRequestSkillCode_霊能者() {
        setRequestSkillCodeAsSkill(CDef.Skill.霊能者);
    }

    /**
     * Set the value of requestSkillCode as 耳年増 (MIMIDOSHIMA). <br>
     * 耳年増
     */
    public void setRequestSkillCode_耳年増() {
        setRequestSkillCodeAsSkill(CDef.Skill.耳年増);
    }

    /**
     * Set the value of requestSkillCode as 死霊術師 (NECROMANCER). <br>
     * 死霊術師
     */
    public void setRequestSkillCode_死霊術師() {
        setRequestSkillCodeAsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Set the value of requestSkillCode as 夜狐 (NIGHTFOX). <br>
     * 夜狐
     */
    public void setRequestSkillCode_夜狐() {
        setRequestSkillCodeAsSkill(CDef.Skill.夜狐);
    }

    /**
     * Set the value of requestSkillCode as おまかせ役職窓なし (NOFRIENDS). <br>
     * おまかせ（役職窓なし）
     */
    public void setRequestSkillCode_おまかせ役職窓なし() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Set the value of requestSkillCode as おまかせ人外 (NOVILLAGERS). <br>
     * おまかせ（人外）
     */
    public void setRequestSkillCode_おまかせ人外() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Set the value of requestSkillCode as 監視者 (OBSERVER). <br>
     * 監視者
     */
    public void setRequestSkillCode_監視者() {
        setRequestSkillCodeAsSkill(CDef.Skill.監視者);
    }

    /**
     * Set the value of requestSkillCode as 全知者 (OMNISCIENCE). <br>
     * 全知者
     */
    public void setRequestSkillCode_全知者() {
        setRequestSkillCodeAsSkill(CDef.Skill.全知者);
    }

    /**
     * Set the value of requestSkillCode as 梟 (OWL). <br>
     * 梟
     */
    public void setRequestSkillCode_梟() {
        setRequestSkillCodeAsSkill(CDef.Skill.梟);
    }

    /**
     * Set the value of requestSkillCode as 牧師 (PASTOR). <br>
     * 牧師
     */
    public void setRequestSkillCode_牧師() {
        setRequestSkillCodeAsSkill(CDef.Skill.牧師);
    }

    /**
     * Set the value of requestSkillCode as 画鋲 (PUSHPIN). <br>
     * 画鋲
     */
    public void setRequestSkillCode_画鋲() {
        setRequestSkillCodeAsSkill(CDef.Skill.画鋲);
    }

    /**
     * Set the value of requestSkillCode as 虹職人 (RAINBOW). <br>
     * 虹職人
     */
    public void setRequestSkillCode_虹職人() {
        setRequestSkillCodeAsSkill(CDef.Skill.虹職人);
    }

    /**
     * Set the value of requestSkillCode as 転生者 (REINCARNATION). <br>
     * 転生者
     */
    public void setRequestSkillCode_転生者() {
        setRequestSkillCodeAsSkill(CDef.Skill.転生者);
    }

    /**
     * Set the value of requestSkillCode as 怨恨者 (RESENTER). <br>
     * 怨恨者
     */
    public void setRequestSkillCode_怨恨者() {
        setRequestSkillCodeAsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Set the value of requestSkillCode as 蘇生者 (RESUSCITATOR). <br>
     * 蘇生者
     */
    public void setRequestSkillCode_蘇生者() {
        setRequestSkillCodeAsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Set the value of requestSkillCode as 占い師 (SEER). <br>
     * 占い師
     */
    public void setRequestSkillCode_占い師() {
        setRequestSkillCodeAsSkill(CDef.Skill.占い師);
    }

    /**
     * Set the value of requestSkillCode as 破局者 (SEPARATOR). <br>
     * 破局者
     */
    public void setRequestSkillCode_破局者() {
        setRequestSkillCodeAsSkill(CDef.Skill.破局者);
    }

    /**
     * Set the value of requestSkillCode as 静狼 (SILENTWOLF). <br>
     * 静狼
     */
    public void setRequestSkillCode_静狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.静狼);
    }

    /**
     * Set the value of requestSkillCode as 感覚者 (SIXTHSENSOR). <br>
     * 感覚者
     */
    public void setRequestSkillCode_感覚者() {
        setRequestSkillCodeAsSkill(CDef.Skill.感覚者);
    }

    /**
     * Set the value of requestSkillCode as 夢遊病者 (SLEEPWALKER). <br>
     * 夢遊病者
     */
    public void setRequestSkillCode_夢遊病者() {
        setRequestSkillCodeAsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Set the value of requestSkillCode as 防音者 (SOUNDPROOFER). <br>
     * 防音者
     */
    public void setRequestSkillCode_防音者() {
        setRequestSkillCodeAsSkill(CDef.Skill.防音者);
    }

    /**
     * Set the value of requestSkillCode as ストーカー (STALKER). <br>
     * ストーカー
     */
    public void setRequestSkillCode_ストーカー() {
        setRequestSkillCodeAsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Set the value of requestSkillCode as 濁点者 (TATSUYA). <br>
     * 濁点者
     */
    public void setRequestSkillCode_濁点者() {
        setRequestSkillCodeAsSkill(CDef.Skill.濁点者);
    }

    /**
     * Set the value of requestSkillCode as 泥棒猫 (THIEFCAT). <br>
     * 泥棒猫
     */
    public void setRequestSkillCode_泥棒猫() {
        setRequestSkillCodeAsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Set the value of requestSkillCode as 翻訳者 (TRANSLATOR). <br>
     * 翻訳者
     */
    public void setRequestSkillCode_翻訳者() {
        setRequestSkillCodeAsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Set the value of requestSkillCode as 罠師 (TRAPPER). <br>
     * 罠師
     */
    public void setRequestSkillCode_罠師() {
        setRequestSkillCodeAsSkill(CDef.Skill.罠師);
    }

    /**
     * Set the value of requestSkillCode as 騙狐 (TRICKFOX). <br>
     * 騙狐
     */
    public void setRequestSkillCode_騙狐() {
        setRequestSkillCodeAsSkill(CDef.Skill.騙狐);
    }

    /**
     * Set the value of requestSkillCode as トラック (TRUCK). <br>
     * トラック
     */
    public void setRequestSkillCode_トラック() {
        setRequestSkillCodeAsSkill(CDef.Skill.トラック);
    }

    /**
     * Set the value of requestSkillCode as 村人 (VILLAGER). <br>
     * 村人
     */
    public void setRequestSkillCode_村人() {
        setRequestSkillCodeAsSkill(CDef.Skill.村人);
    }

    /**
     * Set the value of requestSkillCode as おまかせ村人陣営 (VILLAGERS). <br>
     * おまかせ（村人陣営）
     */
    public void setRequestSkillCode_おまかせ村人陣営() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Set the value of requestSkillCode as 壁殴り代行 (WALLPUNCHER). <br>
     * 壁殴り代行
     */
    public void setRequestSkillCode_壁殴り代行() {
        setRequestSkillCodeAsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Set the value of requestSkillCode as 風来狩人 (WANDERER). <br>
     * 風来狩人
     */
    public void setRequestSkillCode_風来狩人() {
        setRequestSkillCodeAsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Set the value of requestSkillCode as 人狼 (WEREWOLF). <br>
     * 人狼
     */
    public void setRequestSkillCode_人狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.人狼);
    }

    /**
     * Set the value of requestSkillCode as おまかせ人狼陣営 (WEREWOLFS). <br>
     * おまかせ（人狼陣営）
     */
    public void setRequestSkillCode_おまかせ人狼陣営() {
        setRequestSkillCodeAsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Set the value of requestSkillCode as 当選者 (WINNER). <br>
     * 当選者
     */
    public void setRequestSkillCode_当選者() {
        setRequestSkillCodeAsSkill(CDef.Skill.当選者);
    }

    /**
     * Set the value of requestSkillCode as 賢者 (WISE). <br>
     * 賢者
     */
    public void setRequestSkillCode_賢者() {
        setRequestSkillCodeAsSkill(CDef.Skill.賢者);
    }

    /**
     * Set the value of requestSkillCode as 智狼 (WISEWOLF). <br>
     * 智狼
     */
    public void setRequestSkillCode_智狼() {
        setRequestSkillCodeAsSkill(CDef.Skill.智狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 教唆者 (ABETTER). <br>
     * 教唆者
     */
    public void setSecondRequestSkillCode_教唆者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.教唆者);
    }

    /**
     * Set the value of secondRequestSkillCode as 絶対人狼 (ABSOLUTEWOLF). <br>
     * 絶対人狼
     */
    public void setSecondRequestSkillCode_絶対人狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 占星術師 (ASTROLOGER). <br>
     * 占星術師
     */
    public void setSecondRequestSkillCode_占星術師() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.占星術師);
    }

    /**
     * Set the value of secondRequestSkillCode as ババ (BABA). <br>
     * ババ
     */
    public void setSecondRequestSkillCode_ババ() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.ババ);
    }

    /**
     * Set the value of secondRequestSkillCode as 美人局 (BADGERGAME). <br>
     * 美人局
     */
    public void setSecondRequestSkillCode_美人局() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.美人局);
    }

    /**
     * Set the value of secondRequestSkillCode as パン屋 (BAKERY). <br>
     * パン屋
     */
    public void setSecondRequestSkillCode_パン屋() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.パン屋);
    }

    /**
     * Set the value of secondRequestSkillCode as バールのようなもの (BAR). <br>
     * バールのようなもの
     */
    public void setSecondRequestSkillCode_バールのようなもの() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Set the value of secondRequestSkillCode as 黒箱者 (BLACKBOX). <br>
     * 黒箱者
     */
    public void setSecondRequestSkillCode_黒箱者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Set the value of secondRequestSkillCode as 爆弾魔 (BOMBER). <br>
     * 爆弾魔
     */
    public void setSecondRequestSkillCode_爆弾魔() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Set the value of secondRequestSkillCode as 誑狐 (CHEATERFOX). <br>
     * 誑狐
     */
    public void setSecondRequestSkillCode_誑狐() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.誑狐);
    }

    /**
     * Set the value of secondRequestSkillCode as C国狂人 (CMADMAN). <br>
     * C国狂人
     */
    public void setSecondRequestSkillCode_C国狂人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Set the value of secondRequestSkillCode as 同棲者 (COHABITER). <br>
     * 同棲者
     */
    public void setSecondRequestSkillCode_同棲者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.同棲者);
    }

    /**
     * Set the value of secondRequestSkillCode as 指揮官 (COMMANDER). <br>
     * 指揮官
     */
    public void setSecondRequestSkillCode_指揮官() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.指揮官);
    }

    /**
     * Set the value of secondRequestSkillCode as 検死官 (CORONER). <br>
     * 検死官
     */
    public void setSecondRequestSkillCode_検死官() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.検死官);
    }

    /**
     * Set the value of secondRequestSkillCode as 求愛者 (COURTSHIP). <br>
     * 求愛者
     */
    public void setSecondRequestSkillCode_求愛者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.求愛者);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ愉快犯陣営 (CRIMINALS). <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSecondRequestSkillCode_おまかせ愉快犯陣営() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Set the value of secondRequestSkillCode as 呪狼 (CURSEWOLF). <br>
     * 呪狼
     */
    public void setSecondRequestSkillCode_呪狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.呪狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 探偵 (DETECTIVE). <br>
     * 探偵
     */
    public void setSecondRequestSkillCode_探偵() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.探偵);
    }

    /**
     * Set the value of secondRequestSkillCode as 箪笥 (DRAWERS). <br>
     * 箪笥
     */
    public void setSecondRequestSkillCode_箪笥() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.箪笥);
    }

    /**
     * Set the value of secondRequestSkillCode as 不止者 (DYINGPOINTER). <br>
     * 不止者
     */
    public void setSecondRequestSkillCode_不止者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.不止者);
    }

    /**
     * Set the value of secondRequestSkillCode as 闇探偵 (EVILDETECTIVE). <br>
     * 闇探偵
     */
    public void setSecondRequestSkillCode_闇探偵() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.闇探偵);
    }

    /**
     * Set the value of secondRequestSkillCode as 魔神官 (EVILMEDIUM). <br>
     * 魔神官
     */
    public void setSecondRequestSkillCode_魔神官() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.魔神官);
    }

    /**
     * Set the value of secondRequestSkillCode as 執行人 (EXECUTIONER). <br>
     * 執行人
     */
    public void setSecondRequestSkillCode_執行人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.執行人);
    }

    /**
     * Set the value of secondRequestSkillCode as 冤罪者 (FALSECHARGES). <br>
     * 冤罪者
     */
    public void setSecondRequestSkillCode_冤罪者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Set the value of secondRequestSkillCode as 狂信者 (FANATIC). <br>
     * 狂信者
     */
    public void setSecondRequestSkillCode_狂信者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.狂信者);
    }

    /**
     * Set the value of secondRequestSkillCode as 妄想癖 (FANTASIST). <br>
     * 妄想癖
     */
    public void setSecondRequestSkillCode_妄想癖() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Set the value of secondRequestSkillCode as 花占い師 (FLOWERSEER). <br>
     * 花占い師
     */
    public void setSecondRequestSkillCode_花占い師() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.花占い師);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ足音職 (FOOTSTEPS). <br>
     * おまかせ（足音職）
     */
    public void setSecondRequestSkillCode_おまかせ足音職() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Set the value of secondRequestSkillCode as 妖狐 (FOX). <br>
     * 妖狐
     */
    public void setSecondRequestSkillCode_妖狐() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.妖狐);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ妖狐陣営 (FOXS). <br>
     * おまかせ（妖狐陣営）
     */
    public void setSecondRequestSkillCode_おまかせ妖狐陣営() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ役職窓あり (FRIENDS). <br>
     * おまかせ（役職窓あり）
     */
    public void setSecondRequestSkillCode_おまかせ役職窓あり() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Set the value of secondRequestSkillCode as 果実籠 (FRUITSBASKET). <br>
     * 果実籠
     */
    public void setSecondRequestSkillCode_果実籠() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.果実籠);
    }

    /**
     * Set the value of secondRequestSkillCode as 歩狼 (FUWOLF). <br>
     * 歩狼
     */
    public void setSecondRequestSkillCode_歩狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.歩狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 銀狼 (GINWOLF). <br>
     * 銀狼
     */
    public void setSecondRequestSkillCode_銀狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.銀狼);
    }

    /**
     * Set the value of secondRequestSkillCode as ごん (GONFOX). <br>
     * ごん
     */
    public void setSecondRequestSkillCode_ごん() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.ごん);
    }

    /**
     * Set the value of secondRequestSkillCode as 導師 (GURU). <br>
     * 導師
     */
    public void setSecondRequestSkillCode_導師() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.導師);
    }

    /**
     * Set the value of secondRequestSkillCode as 堅狼 (HARDWOLF). <br>
     * 堅狼
     */
    public void setSecondRequestSkillCode_堅狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.堅狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 申し子 (HEAVENCHILD). <br>
     * 申し子
     */
    public void setSecondRequestSkillCode_申し子() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.申し子);
    }

    /**
     * Set the value of secondRequestSkillCode as 仙狐 (HERMITFOX). <br>
     * 仙狐
     */
    public void setSecondRequestSkillCode_仙狐() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.仙狐);
    }

    /**
     * Set the value of secondRequestSkillCode as 飛狼 (HISHAWOLF). <br>
     * 飛狼
     */
    public void setSecondRequestSkillCode_飛狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.飛狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 狩人 (HUNTER). <br>
     * 狩人
     */
    public void setSecondRequestSkillCode_狩人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.狩人);
    }

    /**
     * Set the value of secondRequestSkillCode as 背徳者 (IMMORAL). <br>
     * 背徳者
     */
    public void setSecondRequestSkillCode_背徳者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.背徳者);
    }

    /**
     * Set the value of secondRequestSkillCode as 稲荷 (INARI). <br>
     * 稲荷
     */
    public void setSecondRequestSkillCode_稲荷() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.稲荷);
    }

    /**
     * Set the value of secondRequestSkillCode as 煽動者 (INSTIGATOR). <br>
     * 煽動者
     */
    public void setSecondRequestSkillCode_煽動者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.煽動者);
    }

    /**
     * Set the value of secondRequestSkillCode as 保険屋 (INSURANCER). <br>
     * 保険屋
     */
    public void setSecondRequestSkillCode_保険屋() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.保険屋);
    }

    /**
     * Set the value of secondRequestSkillCode as 絡新婦 (JOROGUMO). <br>
     * 絡新婦
     */
    public void setSecondRequestSkillCode_絡新婦() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Set the value of secondRequestSkillCode as 角狼 (KAKUWOLF). <br>
     * 角狼
     */
    public void setSecondRequestSkillCode_角狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.角狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 王狼 (KINGWOLF). <br>
     * 王狼
     */
    public void setSecondRequestSkillCode_王狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.王狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 金狼 (KINWOLF). <br>
     * 金狼
     */
    public void setSecondRequestSkillCode_金狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.金狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 管狐 (KUDAFOX). <br>
     * 管狐
     */
    public void setSecondRequestSkillCode_管狐() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.管狐);
    }

    /**
     * Set the value of secondRequestSkillCode as 弁護士 (LAWYER). <br>
     * 弁護士
     */
    public void setSecondRequestSkillCode_弁護士() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.弁護士);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ (LEFTOVER). <br>
     * おまかせ
     */
    public void setSecondRequestSkillCode_おまかせ() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Set the value of secondRequestSkillCode as 聴狂人 (LISTENMADMAN). <br>
     * 聴狂人
     */
    public void setSecondRequestSkillCode_聴狂人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Set the value of secondRequestSkillCode as 黙狼 (LISTENWOLF). <br>
     * 黙狼
     */
    public void setSecondRequestSkillCode_黙狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.黙狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 一匹狼 (LONEWOLF). <br>
     * 一匹狼
     */
    public void setSecondRequestSkillCode_一匹狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 拡声者 (LOUDSPEAKER). <br>
     * 拡声者
     */
    public void setSecondRequestSkillCode_拡声者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.拡声者);
    }

    /**
     * Set the value of secondRequestSkillCode as 恋人 (LOVER). <br>
     * 恋人
     */
    public void setSecondRequestSkillCode_恋人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.恋人);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ恋人陣営 (LOVERS). <br>
     * おまかせ（恋人陣営）
     */
    public void setSecondRequestSkillCode_おまかせ恋人陣営() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Set the value of secondRequestSkillCode as 強運者 (LUCKYMAN). <br>
     * 強運者
     */
    public void setSecondRequestSkillCode_強運者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.強運者);
    }

    /**
     * Set the value of secondRequestSkillCode as 狂人 (MADMAN). <br>
     * 狂人
     */
    public void setSecondRequestSkillCode_狂人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.狂人);
    }

    /**
     * Set the value of secondRequestSkillCode as 共鳴者 (MASON). <br>
     * 共鳴者
     */
    public void setSecondRequestSkillCode_共鳴者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Set the value of secondRequestSkillCode as マタギ (MATAGI). <br>
     * マタギ
     */
    public void setSecondRequestSkillCode_マタギ() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.マタギ);
    }

    /**
     * Set the value of secondRequestSkillCode as 市長 (MAYOR). <br>
     * 市長
     */
    public void setSecondRequestSkillCode_市長() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.市長);
    }

    /**
     * Set the value of secondRequestSkillCode as 霊能者 (MEDIUM). <br>
     * 霊能者
     */
    public void setSecondRequestSkillCode_霊能者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.霊能者);
    }

    /**
     * Set the value of secondRequestSkillCode as 耳年増 (MIMIDOSHIMA). <br>
     * 耳年増
     */
    public void setSecondRequestSkillCode_耳年増() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.耳年増);
    }

    /**
     * Set the value of secondRequestSkillCode as 死霊術師 (NECROMANCER). <br>
     * 死霊術師
     */
    public void setSecondRequestSkillCode_死霊術師() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Set the value of secondRequestSkillCode as 夜狐 (NIGHTFOX). <br>
     * 夜狐
     */
    public void setSecondRequestSkillCode_夜狐() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.夜狐);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ役職窓なし (NOFRIENDS). <br>
     * おまかせ（役職窓なし）
     */
    public void setSecondRequestSkillCode_おまかせ役職窓なし() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ人外 (NOVILLAGERS). <br>
     * おまかせ（人外）
     */
    public void setSecondRequestSkillCode_おまかせ人外() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Set the value of secondRequestSkillCode as 監視者 (OBSERVER). <br>
     * 監視者
     */
    public void setSecondRequestSkillCode_監視者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.監視者);
    }

    /**
     * Set the value of secondRequestSkillCode as 全知者 (OMNISCIENCE). <br>
     * 全知者
     */
    public void setSecondRequestSkillCode_全知者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.全知者);
    }

    /**
     * Set the value of secondRequestSkillCode as 梟 (OWL). <br>
     * 梟
     */
    public void setSecondRequestSkillCode_梟() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.梟);
    }

    /**
     * Set the value of secondRequestSkillCode as 牧師 (PASTOR). <br>
     * 牧師
     */
    public void setSecondRequestSkillCode_牧師() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.牧師);
    }

    /**
     * Set the value of secondRequestSkillCode as 画鋲 (PUSHPIN). <br>
     * 画鋲
     */
    public void setSecondRequestSkillCode_画鋲() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.画鋲);
    }

    /**
     * Set the value of secondRequestSkillCode as 虹職人 (RAINBOW). <br>
     * 虹職人
     */
    public void setSecondRequestSkillCode_虹職人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.虹職人);
    }

    /**
     * Set the value of secondRequestSkillCode as 転生者 (REINCARNATION). <br>
     * 転生者
     */
    public void setSecondRequestSkillCode_転生者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.転生者);
    }

    /**
     * Set the value of secondRequestSkillCode as 怨恨者 (RESENTER). <br>
     * 怨恨者
     */
    public void setSecondRequestSkillCode_怨恨者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Set the value of secondRequestSkillCode as 蘇生者 (RESUSCITATOR). <br>
     * 蘇生者
     */
    public void setSecondRequestSkillCode_蘇生者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Set the value of secondRequestSkillCode as 占い師 (SEER). <br>
     * 占い師
     */
    public void setSecondRequestSkillCode_占い師() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.占い師);
    }

    /**
     * Set the value of secondRequestSkillCode as 破局者 (SEPARATOR). <br>
     * 破局者
     */
    public void setSecondRequestSkillCode_破局者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.破局者);
    }

    /**
     * Set the value of secondRequestSkillCode as 静狼 (SILENTWOLF). <br>
     * 静狼
     */
    public void setSecondRequestSkillCode_静狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.静狼);
    }

    /**
     * Set the value of secondRequestSkillCode as 感覚者 (SIXTHSENSOR). <br>
     * 感覚者
     */
    public void setSecondRequestSkillCode_感覚者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.感覚者);
    }

    /**
     * Set the value of secondRequestSkillCode as 夢遊病者 (SLEEPWALKER). <br>
     * 夢遊病者
     */
    public void setSecondRequestSkillCode_夢遊病者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Set the value of secondRequestSkillCode as 防音者 (SOUNDPROOFER). <br>
     * 防音者
     */
    public void setSecondRequestSkillCode_防音者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.防音者);
    }

    /**
     * Set the value of secondRequestSkillCode as ストーカー (STALKER). <br>
     * ストーカー
     */
    public void setSecondRequestSkillCode_ストーカー() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Set the value of secondRequestSkillCode as 濁点者 (TATSUYA). <br>
     * 濁点者
     */
    public void setSecondRequestSkillCode_濁点者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.濁点者);
    }

    /**
     * Set the value of secondRequestSkillCode as 泥棒猫 (THIEFCAT). <br>
     * 泥棒猫
     */
    public void setSecondRequestSkillCode_泥棒猫() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Set the value of secondRequestSkillCode as 翻訳者 (TRANSLATOR). <br>
     * 翻訳者
     */
    public void setSecondRequestSkillCode_翻訳者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Set the value of secondRequestSkillCode as 罠師 (TRAPPER). <br>
     * 罠師
     */
    public void setSecondRequestSkillCode_罠師() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.罠師);
    }

    /**
     * Set the value of secondRequestSkillCode as 騙狐 (TRICKFOX). <br>
     * 騙狐
     */
    public void setSecondRequestSkillCode_騙狐() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.騙狐);
    }

    /**
     * Set the value of secondRequestSkillCode as トラック (TRUCK). <br>
     * トラック
     */
    public void setSecondRequestSkillCode_トラック() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.トラック);
    }

    /**
     * Set the value of secondRequestSkillCode as 村人 (VILLAGER). <br>
     * 村人
     */
    public void setSecondRequestSkillCode_村人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.村人);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ村人陣営 (VILLAGERS). <br>
     * おまかせ（村人陣営）
     */
    public void setSecondRequestSkillCode_おまかせ村人陣営() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Set the value of secondRequestSkillCode as 壁殴り代行 (WALLPUNCHER). <br>
     * 壁殴り代行
     */
    public void setSecondRequestSkillCode_壁殴り代行() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Set the value of secondRequestSkillCode as 風来狩人 (WANDERER). <br>
     * 風来狩人
     */
    public void setSecondRequestSkillCode_風来狩人() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Set the value of secondRequestSkillCode as 人狼 (WEREWOLF). <br>
     * 人狼
     */
    public void setSecondRequestSkillCode_人狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.人狼);
    }

    /**
     * Set the value of secondRequestSkillCode as おまかせ人狼陣営 (WEREWOLFS). <br>
     * おまかせ（人狼陣営）
     */
    public void setSecondRequestSkillCode_おまかせ人狼陣営() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Set the value of secondRequestSkillCode as 当選者 (WINNER). <br>
     * 当選者
     */
    public void setSecondRequestSkillCode_当選者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.当選者);
    }

    /**
     * Set the value of secondRequestSkillCode as 賢者 (WISE). <br>
     * 賢者
     */
    public void setSecondRequestSkillCode_賢者() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.賢者);
    }

    /**
     * Set the value of secondRequestSkillCode as 智狼 (WISEWOLF). <br>
     * 智狼
     */
    public void setSecondRequestSkillCode_智狼() {
        setSecondRequestSkillCodeAsSkill(CDef.Skill.智狼);
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

    /**
     * Set the value of deadReasonCode as 雑魚 (ZAKO). <br>
     * 雑魚
     */
    public void setDeadReasonCode_雑魚() {
        setDeadReasonCodeAsDeadReason(CDef.DeadReason.雑魚);
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

    /**
     * Set the value of isWin as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsWin_True() {
        setIsWinAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isWin as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsWin_False() {
        setIsWinAsFlg(CDef.Flg.False);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of skillCode 教唆者? <br>
     * 教唆者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode教唆者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.教唆者) : false;
    }

    /**
     * Is the value of skillCode 絶対人狼? <br>
     * 絶対人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode絶対人狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絶対人狼) : false;
    }

    /**
     * Is the value of skillCode 占星術師? <br>
     * 占星術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode占星術師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占星術師) : false;
    }

    /**
     * Is the value of skillCode ババ? <br>
     * ババ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeババ() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ババ) : false;
    }

    /**
     * Is the value of skillCode 美人局? <br>
     * 美人局
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode美人局() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.美人局) : false;
    }

    /**
     * Is the value of skillCode パン屋? <br>
     * パン屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeパン屋() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.パン屋) : false;
    }

    /**
     * Is the value of skillCode バールのようなもの? <br>
     * バールのようなもの
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeバールのようなもの() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.バールのようなもの) : false;
    }

    /**
     * Is the value of skillCode 黒箱者? <br>
     * 黒箱者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode黒箱者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黒箱者) : false;
    }

    /**
     * Is the value of skillCode 爆弾魔? <br>
     * 爆弾魔
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode爆弾魔() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.爆弾魔) : false;
    }

    /**
     * Is the value of skillCode 誑狐? <br>
     * 誑狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode誑狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.誑狐) : false;
    }

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
     * Is the value of skillCode 同棲者? <br>
     * 同棲者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode同棲者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.同棲者) : false;
    }

    /**
     * Is the value of skillCode 指揮官? <br>
     * 指揮官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode指揮官() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.指揮官) : false;
    }

    /**
     * Is the value of skillCode 検死官? <br>
     * 検死官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode検死官() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.検死官) : false;
    }

    /**
     * Is the value of skillCode 求愛者? <br>
     * 求愛者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode求愛者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.求愛者) : false;
    }

    /**
     * Is the value of skillCode おまかせ愉快犯陣営? <br>
     * おまかせ（愉快犯陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ愉快犯陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ愉快犯陣営) : false;
    }

    /**
     * Is the value of skillCode 呪狼? <br>
     * 呪狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode呪狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.呪狼) : false;
    }

    /**
     * Is the value of skillCode 探偵? <br>
     * 探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode探偵() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.探偵) : false;
    }

    /**
     * Is the value of skillCode 箪笥? <br>
     * 箪笥
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode箪笥() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.箪笥) : false;
    }

    /**
     * Is the value of skillCode 不止者? <br>
     * 不止者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode不止者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.不止者) : false;
    }

    /**
     * Is the value of skillCode 闇探偵? <br>
     * 闇探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode闇探偵() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.闇探偵) : false;
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
     * Is the value of skillCode 執行人? <br>
     * 執行人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode執行人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.執行人) : false;
    }

    /**
     * Is the value of skillCode 冤罪者? <br>
     * 冤罪者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode冤罪者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.冤罪者) : false;
    }

    /**
     * Is the value of skillCode 狂信者? <br>
     * 狂信者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode狂信者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂信者) : false;
    }

    /**
     * Is the value of skillCode 妄想癖? <br>
     * 妄想癖
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode妄想癖() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妄想癖) : false;
    }

    /**
     * Is the value of skillCode 花占い師? <br>
     * 花占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode花占い師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.花占い師) : false;
    }

    /**
     * Is the value of skillCode おまかせ足音職? <br>
     * おまかせ（足音職）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ足音職() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ足音職) : false;
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
     * Is the value of skillCode おまかせ妖狐陣営? <br>
     * おまかせ（妖狐陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ妖狐陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ妖狐陣営) : false;
    }

    /**
     * Is the value of skillCode おまかせ役職窓あり? <br>
     * おまかせ（役職窓あり）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ役職窓あり() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓あり) : false;
    }

    /**
     * Is the value of skillCode 果実籠? <br>
     * 果実籠
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode果実籠() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.果実籠) : false;
    }

    /**
     * Is the value of skillCode 歩狼? <br>
     * 歩狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode歩狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.歩狼) : false;
    }

    /**
     * Is the value of skillCode 銀狼? <br>
     * 銀狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode銀狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.銀狼) : false;
    }

    /**
     * Is the value of skillCode ごん? <br>
     * ごん
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeごん() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ごん) : false;
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
     * Is the value of skillCode 堅狼? <br>
     * 堅狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode堅狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.堅狼) : false;
    }

    /**
     * Is the value of skillCode 申し子? <br>
     * 申し子
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode申し子() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.申し子) : false;
    }

    /**
     * Is the value of skillCode 仙狐? <br>
     * 仙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode仙狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.仙狐) : false;
    }

    /**
     * Is the value of skillCode 飛狼? <br>
     * 飛狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode飛狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.飛狼) : false;
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
     * Is the value of skillCode 背徳者? <br>
     * 背徳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode背徳者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.背徳者) : false;
    }

    /**
     * Is the value of skillCode 稲荷? <br>
     * 稲荷
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode稲荷() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.稲荷) : false;
    }

    /**
     * Is the value of skillCode 煽動者? <br>
     * 煽動者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode煽動者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.煽動者) : false;
    }

    /**
     * Is the value of skillCode 保険屋? <br>
     * 保険屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode保険屋() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.保険屋) : false;
    }

    /**
     * Is the value of skillCode 絡新婦? <br>
     * 絡新婦
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode絡新婦() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絡新婦) : false;
    }

    /**
     * Is the value of skillCode 角狼? <br>
     * 角狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode角狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.角狼) : false;
    }

    /**
     * Is the value of skillCode 王狼? <br>
     * 王狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode王狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.王狼) : false;
    }

    /**
     * Is the value of skillCode 金狼? <br>
     * 金狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode金狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.金狼) : false;
    }

    /**
     * Is the value of skillCode 管狐? <br>
     * 管狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode管狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.管狐) : false;
    }

    /**
     * Is the value of skillCode 弁護士? <br>
     * 弁護士
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode弁護士() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.弁護士) : false;
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
     * Is the value of skillCode 聴狂人? <br>
     * 聴狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode聴狂人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.聴狂人) : false;
    }

    /**
     * Is the value of skillCode 黙狼? <br>
     * 黙狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode黙狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黙狼) : false;
    }

    /**
     * Is the value of skillCode 一匹狼? <br>
     * 一匹狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode一匹狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.一匹狼) : false;
    }

    /**
     * Is the value of skillCode 拡声者? <br>
     * 拡声者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode拡声者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.拡声者) : false;
    }

    /**
     * Is the value of skillCode 恋人? <br>
     * 恋人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode恋人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.恋人) : false;
    }

    /**
     * Is the value of skillCode おまかせ恋人陣営? <br>
     * おまかせ（恋人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ恋人陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ恋人陣営) : false;
    }

    /**
     * Is the value of skillCode 強運者? <br>
     * 強運者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode強運者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.強運者) : false;
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
     * Is the value of skillCode マタギ? <br>
     * マタギ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeマタギ() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.マタギ) : false;
    }

    /**
     * Is the value of skillCode 市長? <br>
     * 市長
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode市長() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.市長) : false;
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
     * Is the value of skillCode 耳年増? <br>
     * 耳年増
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode耳年増() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.耳年増) : false;
    }

    /**
     * Is the value of skillCode 死霊術師? <br>
     * 死霊術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode死霊術師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.死霊術師) : false;
    }

    /**
     * Is the value of skillCode 夜狐? <br>
     * 夜狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode夜狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夜狐) : false;
    }

    /**
     * Is the value of skillCode おまかせ役職窓なし? <br>
     * おまかせ（役職窓なし）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ役職窓なし() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓なし) : false;
    }

    /**
     * Is the value of skillCode おまかせ人外? <br>
     * おまかせ（人外）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ人外() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人外) : false;
    }

    /**
     * Is the value of skillCode 監視者? <br>
     * 監視者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode監視者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.監視者) : false;
    }

    /**
     * Is the value of skillCode 全知者? <br>
     * 全知者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode全知者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.全知者) : false;
    }

    /**
     * Is the value of skillCode 梟? <br>
     * 梟
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode梟() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.梟) : false;
    }

    /**
     * Is the value of skillCode 牧師? <br>
     * 牧師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode牧師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.牧師) : false;
    }

    /**
     * Is the value of skillCode 画鋲? <br>
     * 画鋲
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode画鋲() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.画鋲) : false;
    }

    /**
     * Is the value of skillCode 虹職人? <br>
     * 虹職人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode虹職人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.虹職人) : false;
    }

    /**
     * Is the value of skillCode 転生者? <br>
     * 転生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode転生者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.転生者) : false;
    }

    /**
     * Is the value of skillCode 怨恨者? <br>
     * 怨恨者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode怨恨者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.怨恨者) : false;
    }

    /**
     * Is the value of skillCode 蘇生者? <br>
     * 蘇生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode蘇生者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.蘇生者) : false;
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
     * Is the value of skillCode 破局者? <br>
     * 破局者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode破局者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.破局者) : false;
    }

    /**
     * Is the value of skillCode 静狼? <br>
     * 静狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode静狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.静狼) : false;
    }

    /**
     * Is the value of skillCode 感覚者? <br>
     * 感覚者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode感覚者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.感覚者) : false;
    }

    /**
     * Is the value of skillCode 夢遊病者? <br>
     * 夢遊病者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode夢遊病者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夢遊病者) : false;
    }

    /**
     * Is the value of skillCode 防音者? <br>
     * 防音者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode防音者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.防音者) : false;
    }

    /**
     * Is the value of skillCode ストーカー? <br>
     * ストーカー
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeストーカー() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ストーカー) : false;
    }

    /**
     * Is the value of skillCode 濁点者? <br>
     * 濁点者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode濁点者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.濁点者) : false;
    }

    /**
     * Is the value of skillCode 泥棒猫? <br>
     * 泥棒猫
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode泥棒猫() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.泥棒猫) : false;
    }

    /**
     * Is the value of skillCode 翻訳者? <br>
     * 翻訳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode翻訳者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.翻訳者) : false;
    }

    /**
     * Is the value of skillCode 罠師? <br>
     * 罠師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode罠師() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.罠師) : false;
    }

    /**
     * Is the value of skillCode 騙狐? <br>
     * 騙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode騙狐() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.騙狐) : false;
    }

    /**
     * Is the value of skillCode トラック? <br>
     * トラック
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeトラック() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.トラック) : false;
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
     * Is the value of skillCode おまかせ村人陣営? <br>
     * おまかせ（村人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ村人陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ村人陣営) : false;
    }

    /**
     * Is the value of skillCode 壁殴り代行? <br>
     * 壁殴り代行
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode壁殴り代行() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.壁殴り代行) : false;
    }

    /**
     * Is the value of skillCode 風来狩人? <br>
     * 風来狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode風来狩人() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.風来狩人) : false;
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
     * Is the value of skillCode おまかせ人狼陣営? <br>
     * おまかせ（人狼陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCodeおまかせ人狼陣営() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人狼陣営) : false;
    }

    /**
     * Is the value of skillCode 当選者? <br>
     * 当選者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode当選者() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.当選者) : false;
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
     * Is the value of skillCode 智狼? <br>
     * 智狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSkillCode智狼() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.智狼) : false;
    }

    /**
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, C国狂人]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_AvailableWerewolfSay() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isAvailableWerewolfSay();
    }

    /**
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 聴狂人]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_ViewableWerewolfSay() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWerewolfSay();
    }

    /**
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 管狐]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasDivineAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasDivineAbility();
    }

    /**
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasSkillPsychicAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasSkillPsychicAbility();
    }

    /**
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasAttackAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasAttackAbility();
    }

    /**
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasDisturbAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasDisturbAbility();
    }

    /**
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 爆弾魔]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_NoDeadByAttack() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isNoDeadByAttack();
    }

    /**
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_WolfCount() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isWolfCount();
    }

    /**
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 梟]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_NoCount() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isNoCount();
    }

    /**
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 狂信者, 煽動者]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_ViewableWolfCharaName() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWolfCharaName();
    }

    /**
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_DivineResultWolf() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isDivineResultWolf();
    }

    /**
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_PsychicResultWolf() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isPsychicResultWolf();
    }

    /**
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_SomeoneSkill() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isSomeoneSkill();
    }

    /**
     * Is the value of requestSkillCode 教唆者? <br>
     * 教唆者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode教唆者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.教唆者) : false;
    }

    /**
     * Is the value of requestSkillCode 絶対人狼? <br>
     * 絶対人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode絶対人狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絶対人狼) : false;
    }

    /**
     * Is the value of requestSkillCode 占星術師? <br>
     * 占星術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode占星術師() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占星術師) : false;
    }

    /**
     * Is the value of requestSkillCode ババ? <br>
     * ババ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeババ() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ババ) : false;
    }

    /**
     * Is the value of requestSkillCode 美人局? <br>
     * 美人局
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode美人局() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.美人局) : false;
    }

    /**
     * Is the value of requestSkillCode パン屋? <br>
     * パン屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeパン屋() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.パン屋) : false;
    }

    /**
     * Is the value of requestSkillCode バールのようなもの? <br>
     * バールのようなもの
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeバールのようなもの() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.バールのようなもの) : false;
    }

    /**
     * Is the value of requestSkillCode 黒箱者? <br>
     * 黒箱者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode黒箱者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黒箱者) : false;
    }

    /**
     * Is the value of requestSkillCode 爆弾魔? <br>
     * 爆弾魔
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode爆弾魔() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.爆弾魔) : false;
    }

    /**
     * Is the value of requestSkillCode 誑狐? <br>
     * 誑狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode誑狐() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.誑狐) : false;
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
     * Is the value of requestSkillCode 同棲者? <br>
     * 同棲者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode同棲者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.同棲者) : false;
    }

    /**
     * Is the value of requestSkillCode 指揮官? <br>
     * 指揮官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode指揮官() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.指揮官) : false;
    }

    /**
     * Is the value of requestSkillCode 検死官? <br>
     * 検死官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode検死官() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.検死官) : false;
    }

    /**
     * Is the value of requestSkillCode 求愛者? <br>
     * 求愛者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode求愛者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.求愛者) : false;
    }

    /**
     * Is the value of requestSkillCode おまかせ愉快犯陣営? <br>
     * おまかせ（愉快犯陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ愉快犯陣営() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ愉快犯陣営) : false;
    }

    /**
     * Is the value of requestSkillCode 呪狼? <br>
     * 呪狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode呪狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.呪狼) : false;
    }

    /**
     * Is the value of requestSkillCode 探偵? <br>
     * 探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode探偵() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.探偵) : false;
    }

    /**
     * Is the value of requestSkillCode 箪笥? <br>
     * 箪笥
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode箪笥() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.箪笥) : false;
    }

    /**
     * Is the value of requestSkillCode 不止者? <br>
     * 不止者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode不止者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.不止者) : false;
    }

    /**
     * Is the value of requestSkillCode 闇探偵? <br>
     * 闇探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode闇探偵() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.闇探偵) : false;
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
     * Is the value of requestSkillCode 執行人? <br>
     * 執行人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode執行人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.執行人) : false;
    }

    /**
     * Is the value of requestSkillCode 冤罪者? <br>
     * 冤罪者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode冤罪者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.冤罪者) : false;
    }

    /**
     * Is the value of requestSkillCode 狂信者? <br>
     * 狂信者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode狂信者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂信者) : false;
    }

    /**
     * Is the value of requestSkillCode 妄想癖? <br>
     * 妄想癖
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode妄想癖() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妄想癖) : false;
    }

    /**
     * Is the value of requestSkillCode 花占い師? <br>
     * 花占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode花占い師() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.花占い師) : false;
    }

    /**
     * Is the value of requestSkillCode おまかせ足音職? <br>
     * おまかせ（足音職）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ足音職() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ足音職) : false;
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
     * Is the value of requestSkillCode おまかせ妖狐陣営? <br>
     * おまかせ（妖狐陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ妖狐陣営() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ妖狐陣営) : false;
    }

    /**
     * Is the value of requestSkillCode おまかせ役職窓あり? <br>
     * おまかせ（役職窓あり）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ役職窓あり() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓あり) : false;
    }

    /**
     * Is the value of requestSkillCode 果実籠? <br>
     * 果実籠
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode果実籠() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.果実籠) : false;
    }

    /**
     * Is the value of requestSkillCode 歩狼? <br>
     * 歩狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode歩狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.歩狼) : false;
    }

    /**
     * Is the value of requestSkillCode 銀狼? <br>
     * 銀狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode銀狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.銀狼) : false;
    }

    /**
     * Is the value of requestSkillCode ごん? <br>
     * ごん
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeごん() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ごん) : false;
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
     * Is the value of requestSkillCode 堅狼? <br>
     * 堅狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode堅狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.堅狼) : false;
    }

    /**
     * Is the value of requestSkillCode 申し子? <br>
     * 申し子
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode申し子() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.申し子) : false;
    }

    /**
     * Is the value of requestSkillCode 仙狐? <br>
     * 仙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode仙狐() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.仙狐) : false;
    }

    /**
     * Is the value of requestSkillCode 飛狼? <br>
     * 飛狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode飛狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.飛狼) : false;
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
     * Is the value of requestSkillCode 背徳者? <br>
     * 背徳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode背徳者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.背徳者) : false;
    }

    /**
     * Is the value of requestSkillCode 稲荷? <br>
     * 稲荷
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode稲荷() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.稲荷) : false;
    }

    /**
     * Is the value of requestSkillCode 煽動者? <br>
     * 煽動者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode煽動者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.煽動者) : false;
    }

    /**
     * Is the value of requestSkillCode 保険屋? <br>
     * 保険屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode保険屋() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.保険屋) : false;
    }

    /**
     * Is the value of requestSkillCode 絡新婦? <br>
     * 絡新婦
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode絡新婦() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絡新婦) : false;
    }

    /**
     * Is the value of requestSkillCode 角狼? <br>
     * 角狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode角狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.角狼) : false;
    }

    /**
     * Is the value of requestSkillCode 王狼? <br>
     * 王狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode王狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.王狼) : false;
    }

    /**
     * Is the value of requestSkillCode 金狼? <br>
     * 金狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode金狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.金狼) : false;
    }

    /**
     * Is the value of requestSkillCode 管狐? <br>
     * 管狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode管狐() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.管狐) : false;
    }

    /**
     * Is the value of requestSkillCode 弁護士? <br>
     * 弁護士
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode弁護士() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.弁護士) : false;
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
     * Is the value of requestSkillCode 聴狂人? <br>
     * 聴狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode聴狂人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.聴狂人) : false;
    }

    /**
     * Is the value of requestSkillCode 黙狼? <br>
     * 黙狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode黙狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黙狼) : false;
    }

    /**
     * Is the value of requestSkillCode 一匹狼? <br>
     * 一匹狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode一匹狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.一匹狼) : false;
    }

    /**
     * Is the value of requestSkillCode 拡声者? <br>
     * 拡声者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode拡声者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.拡声者) : false;
    }

    /**
     * Is the value of requestSkillCode 恋人? <br>
     * 恋人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode恋人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.恋人) : false;
    }

    /**
     * Is the value of requestSkillCode おまかせ恋人陣営? <br>
     * おまかせ（恋人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ恋人陣営() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ恋人陣営) : false;
    }

    /**
     * Is the value of requestSkillCode 強運者? <br>
     * 強運者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode強運者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.強運者) : false;
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
     * Is the value of requestSkillCode マタギ? <br>
     * マタギ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeマタギ() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.マタギ) : false;
    }

    /**
     * Is the value of requestSkillCode 市長? <br>
     * 市長
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode市長() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.市長) : false;
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
     * Is the value of requestSkillCode 耳年増? <br>
     * 耳年増
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode耳年増() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.耳年増) : false;
    }

    /**
     * Is the value of requestSkillCode 死霊術師? <br>
     * 死霊術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode死霊術師() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.死霊術師) : false;
    }

    /**
     * Is the value of requestSkillCode 夜狐? <br>
     * 夜狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode夜狐() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夜狐) : false;
    }

    /**
     * Is the value of requestSkillCode おまかせ役職窓なし? <br>
     * おまかせ（役職窓なし）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ役職窓なし() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓なし) : false;
    }

    /**
     * Is the value of requestSkillCode おまかせ人外? <br>
     * おまかせ（人外）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ人外() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人外) : false;
    }

    /**
     * Is the value of requestSkillCode 監視者? <br>
     * 監視者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode監視者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.監視者) : false;
    }

    /**
     * Is the value of requestSkillCode 全知者? <br>
     * 全知者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode全知者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.全知者) : false;
    }

    /**
     * Is the value of requestSkillCode 梟? <br>
     * 梟
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode梟() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.梟) : false;
    }

    /**
     * Is the value of requestSkillCode 牧師? <br>
     * 牧師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode牧師() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.牧師) : false;
    }

    /**
     * Is the value of requestSkillCode 画鋲? <br>
     * 画鋲
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode画鋲() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.画鋲) : false;
    }

    /**
     * Is the value of requestSkillCode 虹職人? <br>
     * 虹職人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode虹職人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.虹職人) : false;
    }

    /**
     * Is the value of requestSkillCode 転生者? <br>
     * 転生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode転生者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.転生者) : false;
    }

    /**
     * Is the value of requestSkillCode 怨恨者? <br>
     * 怨恨者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode怨恨者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.怨恨者) : false;
    }

    /**
     * Is the value of requestSkillCode 蘇生者? <br>
     * 蘇生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode蘇生者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.蘇生者) : false;
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
     * Is the value of requestSkillCode 破局者? <br>
     * 破局者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode破局者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.破局者) : false;
    }

    /**
     * Is the value of requestSkillCode 静狼? <br>
     * 静狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode静狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.静狼) : false;
    }

    /**
     * Is the value of requestSkillCode 感覚者? <br>
     * 感覚者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode感覚者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.感覚者) : false;
    }

    /**
     * Is the value of requestSkillCode 夢遊病者? <br>
     * 夢遊病者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode夢遊病者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夢遊病者) : false;
    }

    /**
     * Is the value of requestSkillCode 防音者? <br>
     * 防音者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode防音者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.防音者) : false;
    }

    /**
     * Is the value of requestSkillCode ストーカー? <br>
     * ストーカー
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeストーカー() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ストーカー) : false;
    }

    /**
     * Is the value of requestSkillCode 濁点者? <br>
     * 濁点者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode濁点者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.濁点者) : false;
    }

    /**
     * Is the value of requestSkillCode 泥棒猫? <br>
     * 泥棒猫
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode泥棒猫() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.泥棒猫) : false;
    }

    /**
     * Is the value of requestSkillCode 翻訳者? <br>
     * 翻訳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode翻訳者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.翻訳者) : false;
    }

    /**
     * Is the value of requestSkillCode 罠師? <br>
     * 罠師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode罠師() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.罠師) : false;
    }

    /**
     * Is the value of requestSkillCode 騙狐? <br>
     * 騙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode騙狐() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.騙狐) : false;
    }

    /**
     * Is the value of requestSkillCode トラック? <br>
     * トラック
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeトラック() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.トラック) : false;
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
     * Is the value of requestSkillCode おまかせ村人陣営? <br>
     * おまかせ（村人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ村人陣営() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ村人陣営) : false;
    }

    /**
     * Is the value of requestSkillCode 壁殴り代行? <br>
     * 壁殴り代行
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode壁殴り代行() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.壁殴り代行) : false;
    }

    /**
     * Is the value of requestSkillCode 風来狩人? <br>
     * 風来狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode風来狩人() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.風来狩人) : false;
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
     * Is the value of requestSkillCode おまかせ人狼陣営? <br>
     * おまかせ（人狼陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCodeおまかせ人狼陣営() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人狼陣営) : false;
    }

    /**
     * Is the value of requestSkillCode 当選者? <br>
     * 当選者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode当選者() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.当選者) : false;
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
     * Is the value of requestSkillCode 智狼? <br>
     * 智狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode智狼() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.智狼) : false;
    }

    /**
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, C国狂人]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_AvailableWerewolfSay() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isAvailableWerewolfSay();
    }

    /**
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 聴狂人]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_ViewableWerewolfSay() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWerewolfSay();
    }

    /**
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 管狐]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_HasDivineAbility() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasDivineAbility();
    }

    /**
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_HasSkillPsychicAbility() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasSkillPsychicAbility();
    }

    /**
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_HasAttackAbility() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasAttackAbility();
    }

    /**
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_HasDisturbAbility() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasDisturbAbility();
    }

    /**
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 爆弾魔]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_NoDeadByAttack() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isNoDeadByAttack();
    }

    /**
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_WolfCount() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isWolfCount();
    }

    /**
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 梟]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_NoCount() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isNoCount();
    }

    /**
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 狂信者, 煽動者]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_ViewableWolfCharaName() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWolfCharaName();
    }

    /**
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_DivineResultWolf() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isDivineResultWolf();
    }

    /**
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_PsychicResultWolf() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isPsychicResultWolf();
    }

    /**
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     * @return The determination, true or false.
     */
    public boolean isRequestSkillCode_SomeoneSkill() {
        CDef.Skill cdef = getRequestSkillCodeAsSkill();
        return cdef != null && cdef.isSomeoneSkill();
    }

    /**
     * Is the value of secondRequestSkillCode 教唆者? <br>
     * 教唆者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode教唆者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.教唆者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 絶対人狼? <br>
     * 絶対人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode絶対人狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絶対人狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 占星術師? <br>
     * 占星術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode占星術師() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占星術師) : false;
    }

    /**
     * Is the value of secondRequestSkillCode ババ? <br>
     * ババ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeババ() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ババ) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 美人局? <br>
     * 美人局
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode美人局() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.美人局) : false;
    }

    /**
     * Is the value of secondRequestSkillCode パン屋? <br>
     * パン屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeパン屋() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.パン屋) : false;
    }

    /**
     * Is the value of secondRequestSkillCode バールのようなもの? <br>
     * バールのようなもの
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeバールのようなもの() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.バールのようなもの) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 黒箱者? <br>
     * 黒箱者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode黒箱者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黒箱者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 爆弾魔? <br>
     * 爆弾魔
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode爆弾魔() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.爆弾魔) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 誑狐? <br>
     * 誑狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode誑狐() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.誑狐) : false;
    }

    /**
     * Is the value of secondRequestSkillCode C国狂人? <br>
     * C国狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeC国狂人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.C国狂人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 同棲者? <br>
     * 同棲者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode同棲者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.同棲者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 指揮官? <br>
     * 指揮官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode指揮官() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.指揮官) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 検死官? <br>
     * 検死官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode検死官() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.検死官) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 求愛者? <br>
     * 求愛者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode求愛者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.求愛者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ愉快犯陣営? <br>
     * おまかせ（愉快犯陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ愉快犯陣営() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ愉快犯陣営) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 呪狼? <br>
     * 呪狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode呪狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.呪狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 探偵? <br>
     * 探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode探偵() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.探偵) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 箪笥? <br>
     * 箪笥
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode箪笥() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.箪笥) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 不止者? <br>
     * 不止者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode不止者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.不止者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 闇探偵? <br>
     * 闇探偵
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode闇探偵() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.闇探偵) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 魔神官? <br>
     * 魔神官
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode魔神官() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.魔神官) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 執行人? <br>
     * 執行人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode執行人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.執行人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 冤罪者? <br>
     * 冤罪者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode冤罪者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.冤罪者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 狂信者? <br>
     * 狂信者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode狂信者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂信者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 妄想癖? <br>
     * 妄想癖
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode妄想癖() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妄想癖) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 花占い師? <br>
     * 花占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode花占い師() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.花占い師) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ足音職? <br>
     * おまかせ（足音職）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ足音職() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ足音職) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 妖狐? <br>
     * 妖狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode妖狐() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.妖狐) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ妖狐陣営? <br>
     * おまかせ（妖狐陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ妖狐陣営() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ妖狐陣営) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ役職窓あり? <br>
     * おまかせ（役職窓あり）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ役職窓あり() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓あり) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 果実籠? <br>
     * 果実籠
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode果実籠() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.果実籠) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 歩狼? <br>
     * 歩狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode歩狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.歩狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 銀狼? <br>
     * 銀狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode銀狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.銀狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode ごん? <br>
     * ごん
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeごん() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ごん) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 導師? <br>
     * 導師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode導師() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.導師) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 堅狼? <br>
     * 堅狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode堅狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.堅狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 申し子? <br>
     * 申し子
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode申し子() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.申し子) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 仙狐? <br>
     * 仙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode仙狐() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.仙狐) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 飛狼? <br>
     * 飛狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode飛狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.飛狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 狩人? <br>
     * 狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode狩人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狩人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 背徳者? <br>
     * 背徳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode背徳者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.背徳者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 稲荷? <br>
     * 稲荷
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode稲荷() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.稲荷) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 煽動者? <br>
     * 煽動者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode煽動者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.煽動者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 保険屋? <br>
     * 保険屋
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode保険屋() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.保険屋) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 絡新婦? <br>
     * 絡新婦
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode絡新婦() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.絡新婦) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 角狼? <br>
     * 角狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode角狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.角狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 王狼? <br>
     * 王狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode王狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.王狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 金狼? <br>
     * 金狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode金狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.金狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 管狐? <br>
     * 管狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode管狐() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.管狐) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 弁護士? <br>
     * 弁護士
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode弁護士() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.弁護士) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ? <br>
     * おまかせ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 聴狂人? <br>
     * 聴狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode聴狂人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.聴狂人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 黙狼? <br>
     * 黙狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode黙狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.黙狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 一匹狼? <br>
     * 一匹狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode一匹狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.一匹狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 拡声者? <br>
     * 拡声者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode拡声者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.拡声者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 恋人? <br>
     * 恋人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode恋人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.恋人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ恋人陣営? <br>
     * おまかせ（恋人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ恋人陣営() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ恋人陣営) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 強運者? <br>
     * 強運者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode強運者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.強運者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 狂人? <br>
     * 狂人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode狂人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.狂人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 共鳴者? <br>
     * 共鳴者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode共鳴者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.共鳴者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode マタギ? <br>
     * マタギ
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeマタギ() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.マタギ) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 市長? <br>
     * 市長
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode市長() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.市長) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 霊能者? <br>
     * 霊能者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode霊能者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.霊能者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 耳年増? <br>
     * 耳年増
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode耳年増() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.耳年増) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 死霊術師? <br>
     * 死霊術師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode死霊術師() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.死霊術師) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 夜狐? <br>
     * 夜狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode夜狐() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夜狐) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ役職窓なし? <br>
     * おまかせ（役職窓なし）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ役職窓なし() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ役職窓なし) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ人外? <br>
     * おまかせ（人外）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ人外() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人外) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 監視者? <br>
     * 監視者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode監視者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.監視者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 全知者? <br>
     * 全知者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode全知者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.全知者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 梟? <br>
     * 梟
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode梟() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.梟) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 牧師? <br>
     * 牧師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode牧師() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.牧師) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 画鋲? <br>
     * 画鋲
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode画鋲() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.画鋲) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 虹職人? <br>
     * 虹職人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode虹職人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.虹職人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 転生者? <br>
     * 転生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode転生者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.転生者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 怨恨者? <br>
     * 怨恨者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode怨恨者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.怨恨者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 蘇生者? <br>
     * 蘇生者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode蘇生者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.蘇生者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 占い師? <br>
     * 占い師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode占い師() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.占い師) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 破局者? <br>
     * 破局者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode破局者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.破局者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 静狼? <br>
     * 静狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode静狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.静狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 感覚者? <br>
     * 感覚者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode感覚者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.感覚者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 夢遊病者? <br>
     * 夢遊病者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode夢遊病者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.夢遊病者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 防音者? <br>
     * 防音者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode防音者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.防音者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode ストーカー? <br>
     * ストーカー
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeストーカー() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.ストーカー) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 濁点者? <br>
     * 濁点者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode濁点者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.濁点者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 泥棒猫? <br>
     * 泥棒猫
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode泥棒猫() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.泥棒猫) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 翻訳者? <br>
     * 翻訳者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode翻訳者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.翻訳者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 罠師? <br>
     * 罠師
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode罠師() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.罠師) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 騙狐? <br>
     * 騙狐
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode騙狐() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.騙狐) : false;
    }

    /**
     * Is the value of secondRequestSkillCode トラック? <br>
     * トラック
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeトラック() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.トラック) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 村人? <br>
     * 村人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode村人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.村人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ村人陣営? <br>
     * おまかせ（村人陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ村人陣営() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ村人陣営) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 壁殴り代行? <br>
     * 壁殴り代行
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode壁殴り代行() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.壁殴り代行) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 風来狩人? <br>
     * 風来狩人
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode風来狩人() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.風来狩人) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 人狼? <br>
     * 人狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode人狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.人狼) : false;
    }

    /**
     * Is the value of secondRequestSkillCode おまかせ人狼陣営? <br>
     * おまかせ（人狼陣営）
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCodeおまかせ人狼陣営() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.おまかせ人狼陣営) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 当選者? <br>
     * 当選者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode当選者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.当選者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 賢者? <br>
     * 賢者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode賢者() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.賢者) : false;
    }

    /**
     * Is the value of secondRequestSkillCode 智狼? <br>
     * 智狼
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode智狼() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null ? cdef.equals(CDef.Skill.智狼) : false;
    }

    /**
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, C国狂人]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_AvailableWerewolfSay() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isAvailableWerewolfSay();
    }

    /**
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 聴狂人]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_ViewableWerewolfSay() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWerewolfSay();
    }

    /**
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 管狐]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_HasDivineAbility() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasDivineAbility();
    }

    /**
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_HasSkillPsychicAbility() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasSkillPsychicAbility();
    }

    /**
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_HasAttackAbility() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasAttackAbility();
    }

    /**
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_HasDisturbAbility() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isHasDisturbAbility();
    }

    /**
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 爆弾魔]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_NoDeadByAttack() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isNoDeadByAttack();
    }

    /**
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_WolfCount() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isWolfCount();
    }

    /**
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 梟]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_NoCount() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isNoCount();
    }

    /**
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 狂信者, 煽動者]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_ViewableWolfCharaName() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWolfCharaName();
    }

    /**
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_DivineResultWolf() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isDivineResultWolf();
    }

    /**
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_PsychicResultWolf() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isPsychicResultWolf();
    }

    /**
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     * @return The determination, true or false.
     */
    public boolean isSecondRequestSkillCode_SomeoneSkill() {
        CDef.Skill cdef = getSecondRequestSkillCodeAsSkill();
        return cdef != null && cdef.isSomeoneSkill();
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
     * Is the value of deadReasonCode 雑魚? <br>
     * 雑魚
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode雑魚() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null ? cdef.equals(CDef.DeadReason.雑魚) : false;
    }

    /**
     * 無惨 <br>
     * The group elements:[襲撃, 呪殺, 罠死, 爆死, 雑魚]
     * @return The determination, true or false.
     */
    public boolean isDeadReasonCode_Miserable() {
        CDef.DeadReason cdef = getDeadReasonCodeAsDeadReason();
        return cdef != null && cdef.isMiserable();
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

    /**
     * Is the value of isWin True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsWinTrue() {
        CDef.Flg cdef = getIsWinAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isWin False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsWinFalse() {
        CDef.Flg cdef = getIsWinAsFlg();
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

    /**
     * Get the value of the column 'isWin' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsWinAlias() {
        CDef.Flg cdef = getIsWinAsFlg();
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

    /** SKILL by my SECOND_REQUEST_SKILL_CODE, named 'skillBySecondRequestSkillCode'. */
    protected OptionalEntity<Skill> _skillBySecondRequestSkillCode;

    /**
     * [get] SKILL by my SECOND_REQUEST_SKILL_CODE, named 'skillBySecondRequestSkillCode'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'skillBySecondRequestSkillCode'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Skill> getSkillBySecondRequestSkillCode() {
        if (_skillBySecondRequestSkillCode == null) { _skillBySecondRequestSkillCode = OptionalEntity.relationEmpty(this, "skillBySecondRequestSkillCode"); }
        return _skillBySecondRequestSkillCode;
    }

    /**
     * [set] SKILL by my SECOND_REQUEST_SKILL_CODE, named 'skillBySecondRequestSkillCode'.
     * @param skillBySecondRequestSkillCode The entity of foreign property 'skillBySecondRequestSkillCode'. (NullAllowed)
     */
    public void setSkillBySecondRequestSkillCode(OptionalEntity<Skill> skillBySecondRequestSkillCode) {
        _skillBySecondRequestSkillCode = skillBySecondRequestSkillCode;
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
    /** COMMIT by VILLAGE_PLAYER_ID, named 'commitList'. */
    protected List<Commit> _commitList;

    /**
     * [get] COMMIT by VILLAGE_PLAYER_ID, named 'commitList'.
     * @return The entity list of referrer property 'commitList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Commit> getCommitList() {
        if (_commitList == null) { _commitList = newReferrerList(); }
        return _commitList;
    }

    /**
     * [set] COMMIT by VILLAGE_PLAYER_ID, named 'commitList'.
     * @param commitList The entity list of referrer property 'commitList'. (NullAllowed)
     */
    public void setCommitList(List<Commit> commitList) {
        _commitList = commitList;
    }

    /** MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'. */
    protected List<Message> _messageByToVillagePlayerIdList;

    /**
     * [get] MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'.
     * @return The entity list of referrer property 'messageByToVillagePlayerIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageByToVillagePlayerIdList() {
        if (_messageByToVillagePlayerIdList == null) { _messageByToVillagePlayerIdList = newReferrerList(); }
        return _messageByToVillagePlayerIdList;
    }

    /**
     * [set] MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'.
     * @param messageByToVillagePlayerIdList The entity list of referrer property 'messageByToVillagePlayerIdList'. (NullAllowed)
     */
    public void setMessageByToVillagePlayerIdList(List<Message> messageByToVillagePlayerIdList) {
        _messageByToVillagePlayerIdList = messageByToVillagePlayerIdList;
    }

    /** MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'. */
    protected List<Message> _messageByVillagePlayerIdList;

    /**
     * [get] MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'.
     * @return The entity list of referrer property 'messageByVillagePlayerIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageByVillagePlayerIdList() {
        if (_messageByVillagePlayerIdList == null) { _messageByVillagePlayerIdList = newReferrerList(); }
        return _messageByVillagePlayerIdList;
    }

    /**
     * [set] MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'.
     * @param messageByVillagePlayerIdList The entity list of referrer property 'messageByVillagePlayerIdList'. (NullAllowed)
     */
    public void setMessageByVillagePlayerIdList(List<Message> messageByVillagePlayerIdList) {
        _messageByVillagePlayerIdList = messageByVillagePlayerIdList;
    }

    /** MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoList'. */
    protected List<MessageSendto> _messageSendtoList;

    /**
     * [get] MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoList'.
     * @return The entity list of referrer property 'messageSendtoList'. (NotNull: even if no loading, returns empty list)
     */
    public List<MessageSendto> getMessageSendtoList() {
        if (_messageSendtoList == null) { _messageSendtoList = newReferrerList(); }
        return _messageSendtoList;
    }

    /**
     * [set] MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoList'.
     * @param messageSendtoList The entity list of referrer property 'messageSendtoList'. (NullAllowed)
     */
    public void setMessageSendtoList(List<MessageSendto> messageSendtoList) {
        _messageSendtoList = messageSendtoList;
    }

    /** VILLAGE_PLAYER_ACCESS_INFO by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoList'. */
    protected List<VillagePlayerAccessInfo> _villagePlayerAccessInfoList;

    /**
     * [get] VILLAGE_PLAYER_ACCESS_INFO by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoList'.
     * @return The entity list of referrer property 'villagePlayerAccessInfoList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerAccessInfo> getVillagePlayerAccessInfoList() {
        if (_villagePlayerAccessInfoList == null) { _villagePlayerAccessInfoList = newReferrerList(); }
        return _villagePlayerAccessInfoList;
    }

    /**
     * [set] VILLAGE_PLAYER_ACCESS_INFO by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoList'.
     * @param villagePlayerAccessInfoList The entity list of referrer property 'villagePlayerAccessInfoList'. (NullAllowed)
     */
    public void setVillagePlayerAccessInfoList(List<VillagePlayerAccessInfo> villagePlayerAccessInfoList) {
        _villagePlayerAccessInfoList = villagePlayerAccessInfoList;
    }

    /** VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryList'. */
    protected List<VillagePlayerDeadHistory> _villagePlayerDeadHistoryList;

    /**
     * [get] VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryList'.
     * @return The entity list of referrer property 'villagePlayerDeadHistoryList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerDeadHistory> getVillagePlayerDeadHistoryList() {
        if (_villagePlayerDeadHistoryList == null) { _villagePlayerDeadHistoryList = newReferrerList(); }
        return _villagePlayerDeadHistoryList;
    }

    /**
     * [set] VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryList'.
     * @param villagePlayerDeadHistoryList The entity list of referrer property 'villagePlayerDeadHistoryList'. (NullAllowed)
     */
    public void setVillagePlayerDeadHistoryList(List<VillagePlayerDeadHistory> villagePlayerDeadHistoryList) {
        _villagePlayerDeadHistoryList = villagePlayerDeadHistoryList;
    }

    /** VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryList'. */
    protected List<VillagePlayerRoomHistory> _villagePlayerRoomHistoryList;

    /**
     * [get] VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryList'.
     * @return The entity list of referrer property 'villagePlayerRoomHistoryList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerRoomHistory> getVillagePlayerRoomHistoryList() {
        if (_villagePlayerRoomHistoryList == null) { _villagePlayerRoomHistoryList = newReferrerList(); }
        return _villagePlayerRoomHistoryList;
    }

    /**
     * [set] VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryList'.
     * @param villagePlayerRoomHistoryList The entity list of referrer property 'villagePlayerRoomHistoryList'. (NullAllowed)
     */
    public void setVillagePlayerRoomHistoryList(List<VillagePlayerRoomHistory> villagePlayerRoomHistoryList) {
        _villagePlayerRoomHistoryList = villagePlayerRoomHistoryList;
    }

    /** VILLAGE_PLAYER_SKILL_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryList'. */
    protected List<VillagePlayerSkillHistory> _villagePlayerSkillHistoryList;

    /**
     * [get] VILLAGE_PLAYER_SKILL_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryList'.
     * @return The entity list of referrer property 'villagePlayerSkillHistoryList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerSkillHistory> getVillagePlayerSkillHistoryList() {
        if (_villagePlayerSkillHistoryList == null) { _villagePlayerSkillHistoryList = newReferrerList(); }
        return _villagePlayerSkillHistoryList;
    }

    /**
     * [set] VILLAGE_PLAYER_SKILL_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryList'.
     * @param villagePlayerSkillHistoryList The entity list of referrer property 'villagePlayerSkillHistoryList'. (NullAllowed)
     */
    public void setVillagePlayerSkillHistoryList(List<VillagePlayerSkillHistory> villagePlayerSkillHistoryList) {
        _villagePlayerSkillHistoryList = villagePlayerSkillHistoryList;
    }

    /** VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdList'. */
    protected List<VillagePlayerStatus> _villagePlayerStatusByToVillagePlayerIdList;

    /**
     * [get] VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdList'.
     * @return The entity list of referrer property 'villagePlayerStatusByToVillagePlayerIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerStatus> getVillagePlayerStatusByToVillagePlayerIdList() {
        if (_villagePlayerStatusByToVillagePlayerIdList == null) { _villagePlayerStatusByToVillagePlayerIdList = newReferrerList(); }
        return _villagePlayerStatusByToVillagePlayerIdList;
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdList'.
     * @param villagePlayerStatusByToVillagePlayerIdList The entity list of referrer property 'villagePlayerStatusByToVillagePlayerIdList'. (NullAllowed)
     */
    public void setVillagePlayerStatusByToVillagePlayerIdList(List<VillagePlayerStatus> villagePlayerStatusByToVillagePlayerIdList) {
        _villagePlayerStatusByToVillagePlayerIdList = villagePlayerStatusByToVillagePlayerIdList;
    }

    /** VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdList'. */
    protected List<VillagePlayerStatus> _villagePlayerStatusByVillagePlayerIdList;

    /**
     * [get] VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdList'.
     * @return The entity list of referrer property 'villagePlayerStatusByVillagePlayerIdList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerStatus> getVillagePlayerStatusByVillagePlayerIdList() {
        if (_villagePlayerStatusByVillagePlayerIdList == null) { _villagePlayerStatusByVillagePlayerIdList = newReferrerList(); }
        return _villagePlayerStatusByVillagePlayerIdList;
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdList'.
     * @param villagePlayerStatusByVillagePlayerIdList The entity list of referrer property 'villagePlayerStatusByVillagePlayerIdList'. (NullAllowed)
     */
    public void setVillagePlayerStatusByVillagePlayerIdList(List<VillagePlayerStatus> villagePlayerStatusByVillagePlayerIdList) {
        _villagePlayerStatusByVillagePlayerIdList = villagePlayerStatusByVillagePlayerIdList;
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
        if (_deadReason != null && _deadReason.isPresent())
        { sb.append(li).append(xbRDS(_deadReason, "deadReason")); }
        if (_player != null && _player.isPresent())
        { sb.append(li).append(xbRDS(_player, "player")); }
        if (_skillByRequestSkillCode != null && _skillByRequestSkillCode.isPresent())
        { sb.append(li).append(xbRDS(_skillByRequestSkillCode, "skillByRequestSkillCode")); }
        if (_skillBySecondRequestSkillCode != null && _skillBySecondRequestSkillCode.isPresent())
        { sb.append(li).append(xbRDS(_skillBySecondRequestSkillCode, "skillBySecondRequestSkillCode")); }
        if (_skillBySkillCode != null && _skillBySkillCode.isPresent())
        { sb.append(li).append(xbRDS(_skillBySkillCode, "skillBySkillCode")); }
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
        if (_commitList != null) { for (Commit et : _commitList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "commitList")); } } }
        if (_messageByToVillagePlayerIdList != null) { for (Message et : _messageByToVillagePlayerIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageByToVillagePlayerIdList")); } } }
        if (_messageByVillagePlayerIdList != null) { for (Message et : _messageByVillagePlayerIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageByVillagePlayerIdList")); } } }
        if (_messageSendtoList != null) { for (MessageSendto et : _messageSendtoList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageSendtoList")); } } }
        if (_villagePlayerAccessInfoList != null) { for (VillagePlayerAccessInfo et : _villagePlayerAccessInfoList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerAccessInfoList")); } } }
        if (_villagePlayerDeadHistoryList != null) { for (VillagePlayerDeadHistory et : _villagePlayerDeadHistoryList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerDeadHistoryList")); } } }
        if (_villagePlayerRoomHistoryList != null) { for (VillagePlayerRoomHistory et : _villagePlayerRoomHistoryList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerRoomHistoryList")); } } }
        if (_villagePlayerSkillHistoryList != null) { for (VillagePlayerSkillHistory et : _villagePlayerSkillHistoryList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerSkillHistoryList")); } } }
        if (_villagePlayerStatusByToVillagePlayerIdList != null) { for (VillagePlayerStatus et : _villagePlayerStatusByToVillagePlayerIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerStatusByToVillagePlayerIdList")); } } }
        if (_villagePlayerStatusByVillagePlayerIdList != null) { for (VillagePlayerStatus et : _villagePlayerStatusByVillagePlayerIdList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerStatusByVillagePlayerIdList")); } } }
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
        sb.append(dm).append(xfND(_secondRequestSkillCode));
        sb.append(dm).append(xfND(_roomNumber));
        sb.append(dm).append(xfND(_isDead));
        sb.append(dm).append(xfND(_isSpectator));
        sb.append(dm).append(xfND(_deadReasonCode));
        sb.append(dm).append(xfND(_deadDay));
        sb.append(dm).append(xfND(_isGone));
        sb.append(dm).append(xfND(_lastAccessDatetime));
        sb.append(dm).append(xfND(_campCode));
        sb.append(dm).append(xfND(_isWin));
        sb.append(dm).append(xfND(_charaName));
        sb.append(dm).append(xfND(_charaShortName));
        sb.append(dm).append(xfND(_memo));
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
        if (_player != null && _player.isPresent())
        { sb.append(dm).append("player"); }
        if (_skillByRequestSkillCode != null && _skillByRequestSkillCode.isPresent())
        { sb.append(dm).append("skillByRequestSkillCode"); }
        if (_skillBySecondRequestSkillCode != null && _skillBySecondRequestSkillCode.isPresent())
        { sb.append(dm).append("skillBySecondRequestSkillCode"); }
        if (_skillBySkillCode != null && _skillBySkillCode.isPresent())
        { sb.append(dm).append("skillBySkillCode"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (_commitList != null && !_commitList.isEmpty())
        { sb.append(dm).append("commitList"); }
        if (_messageByToVillagePlayerIdList != null && !_messageByToVillagePlayerIdList.isEmpty())
        { sb.append(dm).append("messageByToVillagePlayerIdList"); }
        if (_messageByVillagePlayerIdList != null && !_messageByVillagePlayerIdList.isEmpty())
        { sb.append(dm).append("messageByVillagePlayerIdList"); }
        if (_messageSendtoList != null && !_messageSendtoList.isEmpty())
        { sb.append(dm).append("messageSendtoList"); }
        if (_villagePlayerAccessInfoList != null && !_villagePlayerAccessInfoList.isEmpty())
        { sb.append(dm).append("villagePlayerAccessInfoList"); }
        if (_villagePlayerDeadHistoryList != null && !_villagePlayerDeadHistoryList.isEmpty())
        { sb.append(dm).append("villagePlayerDeadHistoryList"); }
        if (_villagePlayerRoomHistoryList != null && !_villagePlayerRoomHistoryList.isEmpty())
        { sb.append(dm).append("villagePlayerRoomHistoryList"); }
        if (_villagePlayerSkillHistoryList != null && !_villagePlayerSkillHistoryList.isEmpty())
        { sb.append(dm).append("villagePlayerSkillHistoryList"); }
        if (_villagePlayerStatusByToVillagePlayerIdList != null && !_villagePlayerStatusByToVillagePlayerIdList.isEmpty())
        { sb.append(dm).append("villagePlayerStatusByToVillagePlayerIdList"); }
        if (_villagePlayerStatusByVillagePlayerIdList != null && !_villagePlayerStatusByVillagePlayerIdList.isEmpty())
        { sb.append(dm).append("villagePlayerStatusByVillagePlayerIdList"); }
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
     * [get] VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER} <br>
     * プレイヤーID
     * @return The value of the column 'PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getPlayerId() {
        checkSpecifiedProperty("playerId");
        return _playerId;
    }

    /**
     * [set] PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER} <br>
     * プレイヤーID
     * @param playerId The value of the column 'PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPlayerId(Integer playerId) {
        registerModifiedProperty("playerId");
        _playerId = playerId;
    }

    /**
     * [get] CHARA_ID: {NotNull, INT UNSIGNED(10)} <br>
     * キャラクターID
     * @return The value of the column 'CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaId() {
        checkSpecifiedProperty("charaId");
        return _charaId;
    }

    /**
     * [set] CHARA_ID: {NotNull, INT UNSIGNED(10)} <br>
     * キャラクターID
     * @param charaId The value of the column 'CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaId(Integer charaId) {
        registerModifiedProperty("charaId");
        _charaId = charaId;
    }

    /**
     * [get] SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職コード
     * @return The value of the column 'SKILL_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getSkillCode() {
        checkSpecifiedProperty("skillCode");
        return convertEmptyToNull(_skillCode);
    }

    /**
     * [set] SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職コード
     * @param skillCode The value of the column 'SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setSkillCode(String skillCode) {
        checkClassificationCode("SKILL_CODE", CDef.DefMeta.Skill, skillCode);
        registerModifiedProperty("skillCode");
        _skillCode = skillCode;
    }

    /**
     * [get] REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 希望役職コード
     * @return The value of the column 'REQUEST_SKILL_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getRequestSkillCode() {
        checkSpecifiedProperty("requestSkillCode");
        return convertEmptyToNull(_requestSkillCode);
    }

    /**
     * [set] REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 希望役職コード
     * @param requestSkillCode The value of the column 'REQUEST_SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setRequestSkillCode(String requestSkillCode) {
        checkClassificationCode("REQUEST_SKILL_CODE", CDef.DefMeta.Skill, requestSkillCode);
        registerModifiedProperty("requestSkillCode");
        _requestSkillCode = requestSkillCode;
    }

    /**
     * [get] SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 第二希望役職コード
     * @return The value of the column 'SECOND_REQUEST_SKILL_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getSecondRequestSkillCode() {
        checkSpecifiedProperty("secondRequestSkillCode");
        return convertEmptyToNull(_secondRequestSkillCode);
    }

    /**
     * [set] SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 第二希望役職コード
     * @param secondRequestSkillCode The value of the column 'SECOND_REQUEST_SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    protected void setSecondRequestSkillCode(String secondRequestSkillCode) {
        checkClassificationCode("SECOND_REQUEST_SKILL_CODE", CDef.DefMeta.Skill, secondRequestSkillCode);
        registerModifiedProperty("secondRequestSkillCode");
        _secondRequestSkillCode = secondRequestSkillCode;
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
     * [get] LAST_ACCESS_DATETIME: {DATETIME(19)} <br>
     * 最終接続日時
     * @return The value of the column 'LAST_ACCESS_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getLastAccessDatetime() {
        checkSpecifiedProperty("lastAccessDatetime");
        return _lastAccessDatetime;
    }

    /**
     * [set] LAST_ACCESS_DATETIME: {DATETIME(19)} <br>
     * 最終接続日時
     * @param lastAccessDatetime The value of the column 'LAST_ACCESS_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setLastAccessDatetime(java.time.LocalDateTime lastAccessDatetime) {
        registerModifiedProperty("lastAccessDatetime");
        _lastAccessDatetime = lastAccessDatetime;
    }

    /**
     * [get] CAMP_CODE: {VARCHAR(20)} <br>
     * 勝敗判定陣営コード
     * @return The value of the column 'CAMP_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getCampCode() {
        checkSpecifiedProperty("campCode");
        return convertEmptyToNull(_campCode);
    }

    /**
     * [set] CAMP_CODE: {VARCHAR(20)} <br>
     * 勝敗判定陣営コード
     * @param campCode The value of the column 'CAMP_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setCampCode(String campCode) {
        registerModifiedProperty("campCode");
        _campCode = campCode;
    }

    /**
     * [get] IS_WIN: {BIT, classification=Flg} <br>
     * 勝利したか
     * @return The value of the column 'IS_WIN'. (NullAllowed even if selected: for no constraint)
     */
    public Boolean getIsWin() {
        checkSpecifiedProperty("isWin");
        return _isWin;
    }

    /**
     * [set] IS_WIN: {BIT, classification=Flg} <br>
     * 勝利したか
     * @param isWin The value of the column 'IS_WIN'. (NullAllowed: null update allowed for no constraint)
     */
    public void setIsWin(Boolean isWin) {
        checkClassificationCode("IS_WIN", CDef.DefMeta.Flg, isWin);
        registerModifiedProperty("isWin");
        _isWin = isWin;
    }

    /**
     * [get] CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクター名
     * @return The value of the column 'CHARA_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getCharaName() {
        checkSpecifiedProperty("charaName");
        return convertEmptyToNull(_charaName);
    }

    /**
     * [set] CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクター名
     * @param charaName The value of the column 'CHARA_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setCharaName(String charaName) {
        registerModifiedProperty("charaName");
        _charaName = charaName;
    }

    /**
     * [get] CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * キャラクター略称
     * @return The value of the column 'CHARA_SHORT_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getCharaShortName() {
        checkSpecifiedProperty("charaShortName");
        return convertEmptyToNull(_charaShortName);
    }

    /**
     * [set] CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * キャラクター略称
     * @param charaShortName The value of the column 'CHARA_SHORT_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setCharaShortName(String charaShortName) {
        registerModifiedProperty("charaShortName");
        _charaShortName = charaShortName;
    }

    /**
     * [get] MEMO: {VARCHAR(20)} <br>
     * メモ
     * @return The value of the column 'MEMO'. (NullAllowed even if selected: for no constraint)
     */
    public String getMemo() {
        checkSpecifiedProperty("memo");
        return convertEmptyToNull(_memo);
    }

    /**
     * [set] MEMO: {VARCHAR(20)} <br>
     * メモ
     * @param memo The value of the column 'MEMO'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMemo(String memo) {
        registerModifiedProperty("memo");
        _memo = memo;
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
     * @param secondRequestSkillCode The value of the column 'SECOND_REQUEST_SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void mynativeMappingSecondRequestSkillCode(String secondRequestSkillCode) {
        setSecondRequestSkillCode(secondRequestSkillCode);
    }

    /**
     * For framework so basically DON'T use this method.
     * @param deadReasonCode The value of the column 'DEAD_REASON_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void mynativeMappingDeadReasonCode(String deadReasonCode) {
        setDeadReasonCode(deadReasonCode);
    }
}
