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
 * The entity of SKILL_ALLOCATION as TABLE. <br>
 * 役職配分
 * <pre>
 * [primary-key]
 *     VILLAGE_ID, SKILL_CODE
 *
 * [column]
 *     VILLAGE_ID, SKILL_CODE, MIN_NUM, MAX_NUM, ALLOCATION, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     SKILL, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     skill, village
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villageId = entity.getVillageId();
 * String skillCode = entity.getSkillCode();
 * Integer minNum = entity.getMinNum();
 * Integer maxNum = entity.getMaxNum();
 * Integer allocation = entity.getAllocation();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillageId(villageId);
 * entity.setSkillCode(skillCode);
 * entity.setMinNum(minNum);
 * entity.setMaxNum(maxNum);
 * entity.setAllocation(allocation);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsSkillAllocation extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

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

    /** SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} */
    protected String _skillCode;

    /** MIN_NUM: {NotNull, INT UNSIGNED(10)} */
    protected Integer _minNum;

    /** MAX_NUM: {INT UNSIGNED(10)} */
    protected Integer _maxNum;

    /** ALLOCATION: {NotNull, INT UNSIGNED(10)} */
    protected Integer _allocation;

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
        return "SKILL_ALLOCATION";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageId == null) { return false; }
        if (_skillCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of skillCode as the classification of Skill. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Skill getSkillCodeAsSkill() {
        return CDef.Skill.codeOf(getSkillCode());
    }

    /**
     * Set the value of skillCode as the classification of Skill. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setSkillCodeAsSkill(CDef.Skill cdef) {
        setSkillCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
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
     * Set the value of skillCode as 魔神官 (EVILMEDIUM). <br>
     * 魔神官
     */
    public void setSkillCode_魔神官() {
        setSkillCodeAsSkill(CDef.Skill.魔神官);
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
     * Set the value of skillCode as 導師 (GURU). <br>
     * 導師
     */
    public void setSkillCode_導師() {
        setSkillCodeAsSkill(CDef.Skill.導師);
    }

    /**
     * Set the value of skillCode as 申し子 (HEAVENCHILD). <br>
     * 申し子
     */
    public void setSkillCode_申し子() {
        setSkillCodeAsSkill(CDef.Skill.申し子);
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
     * Set the value of skillCode as 絡新婦 (JOROGUMO). <br>
     * 絡新婦
     */
    public void setSkillCode_絡新婦() {
        setSkillCodeAsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Set the value of skillCode as おまかせ (LEFTOVER). <br>
     * おまかせ
     */
    public void setSkillCode_おまかせ() {
        setSkillCodeAsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Set the value of skillCode as 一匹狼 (LONEWOLF). <br>
     * 一匹狼
     */
    public void setSkillCode_一匹狼() {
        setSkillCodeAsSkill(CDef.Skill.一匹狼);
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
     * Set the value of skillCode as 霊能者 (MEDIUM). <br>
     * 霊能者
     */
    public void setSkillCode_霊能者() {
        setSkillCodeAsSkill(CDef.Skill.霊能者);
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
     * Set the value of skillCode as 梟 (OWL). <br>
     * 梟
     */
    public void setSkillCode_梟() {
        setSkillCodeAsSkill(CDef.Skill.梟);
    }

    /**
     * Set the value of skillCode as 転生者 (REINCARNATION). <br>
     * 転生者
     */
    public void setSkillCode_転生者() {
        setSkillCodeAsSkill(CDef.Skill.転生者);
    }

    /**
     * Set the value of skillCode as 占い師 (SEER). <br>
     * 占い師
     */
    public void setSkillCode_占い師() {
        setSkillCodeAsSkill(CDef.Skill.占い師);
    }

    /**
     * Set the value of skillCode as 夢遊病者 (SLEEPWALKER). <br>
     * 夢遊病者
     */
    public void setSkillCode_夢遊病者() {
        setSkillCodeAsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Set the value of skillCode as ストーカー (STALKER). <br>
     * ストーカー
     */
    public void setSkillCode_ストーカー() {
        setSkillCodeAsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Set the value of skillCode as 罠師 (TRAPPER). <br>
     * 罠師
     */
    public void setSkillCode_罠師() {
        setSkillCodeAsSkill(CDef.Skill.罠師);
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

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
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
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, C国狂人]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_AvailableWerewolfSay() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isAvailableWerewolfSay();
    }

    /**
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasDivineAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasDivineAbility();
    }

    /**
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasSkillPsychicAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasSkillPsychicAbility();
    }

    /**
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasAttackAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasAttackAbility();
    }

    /**
     * 狂人能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasMadmanAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasMadmanAbility();
    }

    /**
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 妖狐, 背徳者]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_HasDisturbAbility() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isHasDisturbAbility();
    }

    /**
     * 襲撃されても死なない <br>
     * The group elements:[妖狐, 誑狐, 爆弾魔]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_NoDeadByAttack() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isNoDeadByAttack();
    }

    /**
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_WolfCount() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isWolfCount();
    }

    /**
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, 梟]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_NoCount() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isNoCount();
    }

    /**
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, C国狂人, 狂信者]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_ViewableWolfCharaName() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isViewableWolfCharaName();
    }

    /**
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 一匹狼]
     * @return The determination, true or false.
     */
    public boolean isSkillCode_DivineResultWolf() {
        CDef.Skill cdef = getSkillCodeAsSkill();
        return cdef != null && cdef.isDivineResultWolf();
    }

    /**
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 一匹狼]
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

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** SKILL by my SKILL_CODE, named 'skill'. */
    protected OptionalEntity<Skill> _skill;

    /**
     * [get] SKILL by my SKILL_CODE, named 'skill'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'skill'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Skill> getSkill() {
        if (_skill == null) { _skill = OptionalEntity.relationEmpty(this, "skill"); }
        return _skill;
    }

    /**
     * [set] SKILL by my SKILL_CODE, named 'skill'.
     * @param skill The entity of foreign property 'skill'. (NullAllowed)
     */
    public void setSkill(OptionalEntity<Skill> skill) {
        _skill = skill;
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
        if (obj instanceof BsSkillAllocation) {
            BsSkillAllocation other = (BsSkillAllocation)obj;
            if (!xSV(_villageId, other._villageId)) { return false; }
            if (!xSV(_skillCode, other._skillCode)) { return false; }
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
        hs = xCH(hs, _skillCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_skill != null && _skill.isPresent())
        { sb.append(li).append(xbRDS(_skill, "skill")); }
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
        sb.append(dm).append(xfND(_skillCode));
        sb.append(dm).append(xfND(_minNum));
        sb.append(dm).append(xfND(_maxNum));
        sb.append(dm).append(xfND(_allocation));
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
        if (_skill != null && _skill.isPresent())
        { sb.append(dm).append("skill"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public SkillAllocation clone() {
        return (SkillAllocation)super.clone();
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
     * [get] SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職コード
     * @return The value of the column 'SKILL_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getSkillCode() {
        checkSpecifiedProperty("skillCode");
        return convertEmptyToNull(_skillCode);
    }

    /**
     * [set] SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職コード
     * @param skillCode The value of the column 'SKILL_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setSkillCode(String skillCode) {
        checkClassificationCode("SKILL_CODE", CDef.DefMeta.Skill, skillCode);
        registerModifiedProperty("skillCode");
        _skillCode = skillCode;
    }

    /**
     * [get] MIN_NUM: {NotNull, INT UNSIGNED(10)} <br>
     * 最小人数
     * @return The value of the column 'MIN_NUM'. (basically NotNull if selected: for the constraint)
     */
    public Integer getMinNum() {
        checkSpecifiedProperty("minNum");
        return _minNum;
    }

    /**
     * [set] MIN_NUM: {NotNull, INT UNSIGNED(10)} <br>
     * 最小人数
     * @param minNum The value of the column 'MIN_NUM'. (basically NotNull if update: for the constraint)
     */
    public void setMinNum(Integer minNum) {
        registerModifiedProperty("minNum");
        _minNum = minNum;
    }

    /**
     * [get] MAX_NUM: {INT UNSIGNED(10)} <br>
     * 最大人数
     * @return The value of the column 'MAX_NUM'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMaxNum() {
        checkSpecifiedProperty("maxNum");
        return _maxNum;
    }

    /**
     * [set] MAX_NUM: {INT UNSIGNED(10)} <br>
     * 最大人数
     * @param maxNum The value of the column 'MAX_NUM'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMaxNum(Integer maxNum) {
        registerModifiedProperty("maxNum");
        _maxNum = maxNum;
    }

    /**
     * [get] ALLOCATION: {NotNull, INT UNSIGNED(10)} <br>
     * 配分
     * @return The value of the column 'ALLOCATION'. (basically NotNull if selected: for the constraint)
     */
    public Integer getAllocation() {
        checkSpecifiedProperty("allocation");
        return _allocation;
    }

    /**
     * [set] ALLOCATION: {NotNull, INT UNSIGNED(10)} <br>
     * 配分
     * @param allocation The value of the column 'ALLOCATION'. (basically NotNull if update: for the constraint)
     */
    public void setAllocation(Integer allocation) {
        registerModifiedProperty("allocation");
        _allocation = allocation;
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
     * @param skillCode The value of the column 'SKILL_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingSkillCode(String skillCode) {
        setSkillCode(skillCode);
    }
}
