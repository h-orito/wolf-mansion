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
 * The entity of ABILITY as TABLE. <br>
 * 能力行使
 * <pre>
 * [primary-key]
 *     VILLAGE_ID, DAY, CHARA_ID, ABILITY_TYPE_CODE
 *
 * [column]
 *     VILLAGE_ID, DAY, CHARA_ID, ATTACKER_CHARA_ID, TARGET_CHARA_ID, TARGET_FOOTSTEP, TARGET_SKILL_CODE, TARGET_CAMP_CODE, TARGET_ROOMS, ABILITY_TYPE_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     ABILITY_TYPE, VILLAGE_DAY
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     abilityType, villageDay
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villageId = entity.getVillageId();
 * Integer day = entity.getDay();
 * Integer charaId = entity.getCharaId();
 * Integer attackerCharaId = entity.getAttackerCharaId();
 * Integer targetCharaId = entity.getTargetCharaId();
 * String targetFootstep = entity.getTargetFootstep();
 * String targetSkillCode = entity.getTargetSkillCode();
 * String targetCampCode = entity.getTargetCampCode();
 * String targetRooms = entity.getTargetRooms();
 * String abilityTypeCode = entity.getAbilityTypeCode();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillageId(villageId);
 * entity.setDay(day);
 * entity.setCharaId(charaId);
 * entity.setAttackerCharaId(attackerCharaId);
 * entity.setTargetCharaId(targetCharaId);
 * entity.setTargetFootstep(targetFootstep);
 * entity.setTargetSkillCode(targetSkillCode);
 * entity.setTargetCampCode(targetCampCode);
 * entity.setTargetRooms(targetRooms);
 * entity.setAbilityTypeCode(abilityTypeCode);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsAbility extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} */
    protected Integer _villageId;

    /** DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} */
    protected Integer _day;

    /** CHARA_ID: {PK, NotNull, INT UNSIGNED(10)} */
    protected Integer _charaId;

    /** ATTACKER_CHARA_ID: {INT UNSIGNED(10)} */
    protected Integer _attackerCharaId;

    /** TARGET_CHARA_ID: {INT UNSIGNED(10)} */
    protected Integer _targetCharaId;

    /** TARGET_FOOTSTEP: {VARCHAR(1000)} */
    protected String _targetFootstep;

    /** TARGET_SKILL_CODE: {VARCHAR(20)} */
    protected String _targetSkillCode;

    /** TARGET_CAMP_CODE: {VARCHAR(20)} */
    protected String _targetCampCode;

    /** TARGET_ROOMS: {VARCHAR(1000)} */
    protected String _targetRooms;

    /** ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} */
    protected String _abilityTypeCode;

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
        return "ABILITY";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageId == null) { return false; }
        if (_day == null) { return false; }
        if (_charaId == null) { return false; }
        if (_abilityTypeCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of abilityTypeCode as the classification of AbilityType. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.AbilityType getAbilityTypeCodeAsAbilityType() {
        return CDef.AbilityType.codeOf(getAbilityTypeCode());
    }

    /**
     * Set the value of abilityTypeCode as the classification of AbilityType. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setAbilityTypeCodeAsAbilityType(CDef.AbilityType cdef) {
        setAbilityTypeCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of abilityTypeCode as 襲撃 (ATTACK). <br>
     * 襲撃
     */
    public void setAbilityTypeCode_襲撃() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.襲撃);
    }

    /**
     * Set the value of abilityTypeCode as 襲撃希望 (ATTACK_REQUEST). <br>
     * 襲撃希望
     */
    public void setAbilityTypeCode_襲撃希望() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.襲撃希望);
    }

    /**
     * Set the value of abilityTypeCode as ババを渡す (BABAGIVE). <br>
     * ババを渡す
     */
    public void setAbilityTypeCode_ババを渡す() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.ババを渡す);
    }

    /**
     * Set the value of abilityTypeCode as 美人局 (BADGERGAME). <br>
     * 美人局
     */
    public void setAbilityTypeCode_美人局() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.美人局);
    }

    /**
     * Set the value of abilityTypeCode as 殴打 (BEAT). <br>
     * 殴打
     */
    public void setAbilityTypeCode_殴打() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.殴打);
    }

    /**
     * Set the value of abilityTypeCode as 爆弾設置 (BOMB). <br>
     * 爆弾設置
     */
    public void setAbilityTypeCode_爆弾設置() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.爆弾設置);
    }

    /**
     * Set the value of abilityTypeCode as 破局 (BREAKUP). <br>
     * 破局
     */
    public void setAbilityTypeCode_破局() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.破局);
    }

    /**
     * Set the value of abilityTypeCode as 誑かす (CHEAT). <br>
     * 誑かす
     */
    public void setAbilityTypeCode_誑かす() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.誑かす);
    }

    /**
     * Set the value of abilityTypeCode as 浮気 (CHEATLOVE). <br>
     * 浮気
     */
    public void setAbilityTypeCode_浮気() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.浮気);
    }

    /**
     * Set the value of abilityTypeCode as 誰だ今の (CHIKUWA). <br>
     * 誰だ今の
     */
    public void setAbilityTypeCode_誰だ今の() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.誰だ今の);
    }

    /**
     * Set the value of abilityTypeCode as 曇天 (CLOUD). <br>
     * 曇天
     */
    public void setAbilityTypeCode_曇天() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.曇天);
    }

    /**
     * Set the value of abilityTypeCode as 同棲 (COHABIT). <br>
     * 同棲
     */
    public void setAbilityTypeCode_同棲() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.同棲);
    }

    /**
     * Set the value of abilityTypeCode as 指揮 (COMMAND). <br>
     * 指揮
     */
    public void setAbilityTypeCode_指揮() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.指揮);
    }

    /**
     * Set the value of abilityTypeCode as 反呪 (COUNTERCURSE). <br>
     * 反呪
     */
    public void setAbilityTypeCode_反呪() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.反呪);
    }

    /**
     * Set the value of abilityTypeCode as 求愛 (COURT). <br>
     * 求愛
     */
    public void setAbilityTypeCode_求愛() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.求愛);
    }

    /**
     * Set the value of abilityTypeCode as 呪縛 (CURSE). <br>
     * 呪縛
     */
    public void setAbilityTypeCode_呪縛() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.呪縛);
    }

    /**
     * Set the value of abilityTypeCode as 死者占い (DEADDIVINE). <br>
     * 死者占い
     */
    public void setAbilityTypeCode_死者占い() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.死者占い);
    }

    /**
     * Set the value of abilityTypeCode as 占い (DIVINE). <br>
     * 占い
     */
    public void setAbilityTypeCode_占い() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.占い);
    }

    /**
     * Set the value of abilityTypeCode as 道化 (DOUKE). <br>
     * 道化
     */
    public void setAbilityTypeCode_道化() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.道化);
    }

    /**
     * Set the value of abilityTypeCode as 強制転生 (FORCE_REINCARNATION). <br>
     * 強制転生
     */
    public void setAbilityTypeCode_強制転生() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.強制転生);
    }

    /**
     * Set the value of abilityTypeCode as フルーツバスケット (FRUITSBASKET). <br>
     * フルーツバスケット
     */
    public void setAbilityTypeCode_フルーツバスケット() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.フルーツバスケット);
    }

    /**
     * Set the value of abilityTypeCode as 念力付与 (GIVETELEKINESIS). <br>
     * 念力付与
     */
    public void setAbilityTypeCode_念力付与() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.念力付与);
    }

    /**
     * Set the value of abilityTypeCode as 護衛 (GUARD). <br>
     * 護衛
     */
    public void setAbilityTypeCode_護衛() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.護衛);
    }

    /**
     * Set the value of abilityTypeCode as 濡衣 (GUILTY). <br>
     * 濡衣
     */
    public void setAbilityTypeCode_濡衣() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.濡衣);
    }

    /**
     * Set the value of abilityTypeCode as 隠蔽 (HIDE). <br>
     * 隠蔽
     */
    public void setAbilityTypeCode_隠蔽() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.隠蔽);
    }

    /**
     * Set the value of abilityTypeCode as 冷やし中華 (HIYASICHUKA). <br>
     * 冷やし中華
     */
    public void setAbilityTypeCode_冷やし中華() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.冷やし中華);
    }

    /**
     * Set the value of abilityTypeCode as 狩猟 (HUNTING). <br>
     * 狩猟
     */
    public void setAbilityTypeCode_狩猟() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.狩猟);
    }

    /**
     * Set the value of abilityTypeCode as 教唆 (INCITE). <br>
     * 教唆
     */
    public void setAbilityTypeCode_教唆() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.教唆);
    }

    /**
     * Set the value of abilityTypeCode as 煽動 (INSTIGATE). <br>
     * 煽動
     */
    public void setAbilityTypeCode_煽動() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.煽動);
    }

    /**
     * Set the value of abilityTypeCode as 保険 (INSURANCE). <br>
     * 保険
     */
    public void setAbilityTypeCode_保険() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.保険);
    }

    /**
     * Set the value of abilityTypeCode as 捜査 (INVESTIGATE). <br>
     * 捜査
     */
    public void setAbilityTypeCode_捜査() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.捜査);
    }

    /**
     * Set the value of abilityTypeCode as 単独襲撃 (LONEATTACK). <br>
     * 単独襲撃
     */
    public void setAbilityTypeCode_単独襲撃() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.単独襲撃);
    }

    /**
     * Set the value of abilityTypeCode as 拡声 (LOUDSPEAK). <br>
     * 拡声
     */
    public void setAbilityTypeCode_拡声() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.拡声);
    }

    /**
     * Set the value of abilityTypeCode as 恋泥棒 (LOVESTEAL). <br>
     * 恋泥棒
     */
    public void setAbilityTypeCode_恋泥棒() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.恋泥棒);
    }

    /**
     * Set the value of abilityTypeCode as ナマ足 (NAMAASHI). <br>
     * ナマ足
     */
    public void setAbilityTypeCode_ナマ足() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.ナマ足);
    }

    /**
     * Set the value of abilityTypeCode as 死霊蘇生 (NECROMANCE). <br>
     * 死霊蘇生
     */
    public void setAbilityTypeCode_死霊蘇生() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.死霊蘇生);
    }

    /**
     * Set the value of abilityTypeCode as 全知 (OMNISCIENCE). <br>
     * 全知
     */
    public void setAbilityTypeCode_全知() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.全知);
    }

    /**
     * Set the value of abilityTypeCode as 降霊 (ONMYO_NECROMANCE). <br>
     * 降霊
     */
    public void setAbilityTypeCode_降霊() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.降霊);
    }

    /**
     * Set the value of abilityTypeCode as 説得 (PERSUADE). <br>
     * 説得
     */
    public void setAbilityTypeCode_説得() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.説得);
    }

    /**
     * Set the value of abilityTypeCode as 戦闘力発揮 (POWER). <br>
     * 戦闘力発揮
     */
    public void setAbilityTypeCode_戦闘力発揮() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.戦闘力発揮);
    }

    /**
     * Set the value of abilityTypeCode as 殺し屋化 (PRO). <br>
     * 殺し屋化
     */
    public void setAbilityTypeCode_殺し屋化() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.殺し屋化);
    }

    /**
     * Set the value of abilityTypeCode as 虹塗り (RAINBOW). <br>
     * 虹塗り
     */
    public void setAbilityTypeCode_虹塗り() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.虹塗り);
    }

    /**
     * Set the value of abilityTypeCode as 蘇生 (RESUSCITATE). <br>
     * 蘇生
     */
    public void setAbilityTypeCode_蘇生() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.蘇生);
    }

    /**
     * Set the value of abilityTypeCode as 革命 (REVOLUTION). <br>
     * 革命
     */
    public void setAbilityTypeCode_革命() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.革命);
    }

    /**
     * Set the value of abilityTypeCode as 暴走転生 (RUNAWAY). <br>
     * 暴走転生
     */
    public void setAbilityTypeCode_暴走転生() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.暴走転生);
    }

    /**
     * Set the value of abilityTypeCode as 世界を救う (SAVETHEWORLD). <br>
     * 世界を救う
     */
    public void setAbilityTypeCode_世界を救う() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.世界を救う);
    }

    /**
     * Set the value of abilityTypeCode as 誘惑 (SEDUCE). <br>
     * 誘惑
     */
    public void setAbilityTypeCode_誘惑() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.誘惑);
    }

    /**
     * Set the value of abilityTypeCode as 叫び (SHOUT). <br>
     * 叫び
     */
    public void setAbilityTypeCode_叫び() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.叫び);
    }

    /**
     * Set the value of abilityTypeCode as ストーキング (STALKING). <br>
     * ストーキング
     */
    public void setAbilityTypeCode_ストーキング() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.ストーキング);
    }

    /**
     * Set the value of abilityTypeCode as 翻訳 (TRANSLATE). <br>
     * 翻訳
     */
    public void setAbilityTypeCode_翻訳() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.翻訳);
    }

    /**
     * Set the value of abilityTypeCode as 人魚化 (TRANSMERMAID). <br>
     * 人魚化
     */
    public void setAbilityTypeCode_人魚化() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.人魚化);
    }

    /**
     * Set the value of abilityTypeCode as 罠設置 (TRAP). <br>
     * 罠設置
     */
    public void setAbilityTypeCode_罠設置() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.罠設置);
    }

    /**
     * Set the value of abilityTypeCode as 壁殴り (WALLPUNCH). <br>
     * 壁殴り
     */
    public void setAbilityTypeCode_壁殴り() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.壁殴り);
    }

    /**
     * Set the value of abilityTypeCode as 風来護衛 (WANDERERGUARD). <br>
     * 風来護衛
     */
    public void setAbilityTypeCode_風来護衛() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.風来護衛);
    }

    /**
     * Set the value of abilityTypeCode as 当選 (WIN). <br>
     * 当選
     */
    public void setAbilityTypeCode_当選() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.当選);
    }

    /**
     * Set the value of abilityTypeCode as 指差死 (YUBISASHI). <br>
     * 指差死
     */
    public void setAbilityTypeCode_指差死() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.指差死);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of abilityTypeCode 襲撃? <br>
     * 襲撃
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode襲撃() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.襲撃) : false;
    }

    /**
     * Is the value of abilityTypeCode 襲撃希望? <br>
     * 襲撃希望
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode襲撃希望() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.襲撃希望) : false;
    }

    /**
     * Is the value of abilityTypeCode ババを渡す? <br>
     * ババを渡す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCodeババを渡す() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.ババを渡す) : false;
    }

    /**
     * Is the value of abilityTypeCode 美人局? <br>
     * 美人局
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode美人局() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.美人局) : false;
    }

    /**
     * Is the value of abilityTypeCode 殴打? <br>
     * 殴打
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode殴打() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.殴打) : false;
    }

    /**
     * Is the value of abilityTypeCode 爆弾設置? <br>
     * 爆弾設置
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode爆弾設置() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.爆弾設置) : false;
    }

    /**
     * Is the value of abilityTypeCode 破局? <br>
     * 破局
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode破局() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.破局) : false;
    }

    /**
     * Is the value of abilityTypeCode 誑かす? <br>
     * 誑かす
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode誑かす() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.誑かす) : false;
    }

    /**
     * Is the value of abilityTypeCode 浮気? <br>
     * 浮気
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode浮気() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.浮気) : false;
    }

    /**
     * Is the value of abilityTypeCode 誰だ今の? <br>
     * 誰だ今の
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode誰だ今の() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.誰だ今の) : false;
    }

    /**
     * Is the value of abilityTypeCode 曇天? <br>
     * 曇天
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode曇天() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.曇天) : false;
    }

    /**
     * Is the value of abilityTypeCode 同棲? <br>
     * 同棲
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode同棲() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.同棲) : false;
    }

    /**
     * Is the value of abilityTypeCode 指揮? <br>
     * 指揮
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode指揮() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.指揮) : false;
    }

    /**
     * Is the value of abilityTypeCode 反呪? <br>
     * 反呪
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode反呪() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.反呪) : false;
    }

    /**
     * Is the value of abilityTypeCode 求愛? <br>
     * 求愛
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode求愛() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.求愛) : false;
    }

    /**
     * Is the value of abilityTypeCode 呪縛? <br>
     * 呪縛
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode呪縛() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.呪縛) : false;
    }

    /**
     * Is the value of abilityTypeCode 死者占い? <br>
     * 死者占い
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode死者占い() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.死者占い) : false;
    }

    /**
     * Is the value of abilityTypeCode 占い? <br>
     * 占い
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode占い() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.占い) : false;
    }

    /**
     * Is the value of abilityTypeCode 道化? <br>
     * 道化
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode道化() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.道化) : false;
    }

    /**
     * Is the value of abilityTypeCode 強制転生? <br>
     * 強制転生
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode強制転生() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.強制転生) : false;
    }

    /**
     * Is the value of abilityTypeCode フルーツバスケット? <br>
     * フルーツバスケット
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCodeフルーツバスケット() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.フルーツバスケット) : false;
    }

    /**
     * Is the value of abilityTypeCode 念力付与? <br>
     * 念力付与
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode念力付与() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.念力付与) : false;
    }

    /**
     * Is the value of abilityTypeCode 護衛? <br>
     * 護衛
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode護衛() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.護衛) : false;
    }

    /**
     * Is the value of abilityTypeCode 濡衣? <br>
     * 濡衣
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode濡衣() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.濡衣) : false;
    }

    /**
     * Is the value of abilityTypeCode 隠蔽? <br>
     * 隠蔽
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode隠蔽() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.隠蔽) : false;
    }

    /**
     * Is the value of abilityTypeCode 冷やし中華? <br>
     * 冷やし中華
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode冷やし中華() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.冷やし中華) : false;
    }

    /**
     * Is the value of abilityTypeCode 狩猟? <br>
     * 狩猟
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode狩猟() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.狩猟) : false;
    }

    /**
     * Is the value of abilityTypeCode 教唆? <br>
     * 教唆
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode教唆() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.教唆) : false;
    }

    /**
     * Is the value of abilityTypeCode 煽動? <br>
     * 煽動
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode煽動() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.煽動) : false;
    }

    /**
     * Is the value of abilityTypeCode 保険? <br>
     * 保険
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode保険() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.保険) : false;
    }

    /**
     * Is the value of abilityTypeCode 捜査? <br>
     * 捜査
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode捜査() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.捜査) : false;
    }

    /**
     * Is the value of abilityTypeCode 単独襲撃? <br>
     * 単独襲撃
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode単独襲撃() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.単独襲撃) : false;
    }

    /**
     * Is the value of abilityTypeCode 拡声? <br>
     * 拡声
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode拡声() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.拡声) : false;
    }

    /**
     * Is the value of abilityTypeCode 恋泥棒? <br>
     * 恋泥棒
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode恋泥棒() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.恋泥棒) : false;
    }

    /**
     * Is the value of abilityTypeCode ナマ足? <br>
     * ナマ足
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCodeナマ足() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.ナマ足) : false;
    }

    /**
     * Is the value of abilityTypeCode 死霊蘇生? <br>
     * 死霊蘇生
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode死霊蘇生() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.死霊蘇生) : false;
    }

    /**
     * Is the value of abilityTypeCode 全知? <br>
     * 全知
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode全知() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.全知) : false;
    }

    /**
     * Is the value of abilityTypeCode 降霊? <br>
     * 降霊
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode降霊() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.降霊) : false;
    }

    /**
     * Is the value of abilityTypeCode 説得? <br>
     * 説得
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode説得() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.説得) : false;
    }

    /**
     * Is the value of abilityTypeCode 戦闘力発揮? <br>
     * 戦闘力発揮
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode戦闘力発揮() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.戦闘力発揮) : false;
    }

    /**
     * Is the value of abilityTypeCode 殺し屋化? <br>
     * 殺し屋化
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode殺し屋化() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.殺し屋化) : false;
    }

    /**
     * Is the value of abilityTypeCode 虹塗り? <br>
     * 虹塗り
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode虹塗り() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.虹塗り) : false;
    }

    /**
     * Is the value of abilityTypeCode 蘇生? <br>
     * 蘇生
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode蘇生() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.蘇生) : false;
    }

    /**
     * Is the value of abilityTypeCode 革命? <br>
     * 革命
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode革命() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.革命) : false;
    }

    /**
     * Is the value of abilityTypeCode 暴走転生? <br>
     * 暴走転生
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode暴走転生() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.暴走転生) : false;
    }

    /**
     * Is the value of abilityTypeCode 世界を救う? <br>
     * 世界を救う
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode世界を救う() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.世界を救う) : false;
    }

    /**
     * Is the value of abilityTypeCode 誘惑? <br>
     * 誘惑
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode誘惑() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.誘惑) : false;
    }

    /**
     * Is the value of abilityTypeCode 叫び? <br>
     * 叫び
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode叫び() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.叫び) : false;
    }

    /**
     * Is the value of abilityTypeCode ストーキング? <br>
     * ストーキング
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCodeストーキング() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.ストーキング) : false;
    }

    /**
     * Is the value of abilityTypeCode 翻訳? <br>
     * 翻訳
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode翻訳() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.翻訳) : false;
    }

    /**
     * Is the value of abilityTypeCode 人魚化? <br>
     * 人魚化
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode人魚化() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.人魚化) : false;
    }

    /**
     * Is the value of abilityTypeCode 罠設置? <br>
     * 罠設置
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode罠設置() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.罠設置) : false;
    }

    /**
     * Is the value of abilityTypeCode 壁殴り? <br>
     * 壁殴り
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode壁殴り() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.壁殴り) : false;
    }

    /**
     * Is the value of abilityTypeCode 風来護衛? <br>
     * 風来護衛
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode風来護衛() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.風来護衛) : false;
    }

    /**
     * Is the value of abilityTypeCode 当選? <br>
     * 当選
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode当選() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.当選) : false;
    }

    /**
     * Is the value of abilityTypeCode 指差死? <br>
     * 指差死
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode指差死() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.指差死) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** ABILITY_TYPE by my ABILITY_TYPE_CODE, named 'abilityType'. */
    protected OptionalEntity<AbilityType> _abilityType;

    /**
     * [get] ABILITY_TYPE by my ABILITY_TYPE_CODE, named 'abilityType'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'abilityType'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<AbilityType> getAbilityType() {
        if (_abilityType == null) { _abilityType = OptionalEntity.relationEmpty(this, "abilityType"); }
        return _abilityType;
    }

    /**
     * [set] ABILITY_TYPE by my ABILITY_TYPE_CODE, named 'abilityType'.
     * @param abilityType The entity of foreign property 'abilityType'. (NullAllowed)
     */
    public void setAbilityType(OptionalEntity<AbilityType> abilityType) {
        _abilityType = abilityType;
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
        if (obj instanceof BsAbility) {
            BsAbility other = (BsAbility)obj;
            if (!xSV(_villageId, other._villageId)) { return false; }
            if (!xSV(_day, other._day)) { return false; }
            if (!xSV(_charaId, other._charaId)) { return false; }
            if (!xSV(_abilityTypeCode, other._abilityTypeCode)) { return false; }
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
        hs = xCH(hs, _day);
        hs = xCH(hs, _charaId);
        hs = xCH(hs, _abilityTypeCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_abilityType != null && _abilityType.isPresent())
        { sb.append(li).append(xbRDS(_abilityType, "abilityType")); }
        if (_villageDay != null && _villageDay.isPresent())
        { sb.append(li).append(xbRDS(_villageDay, "villageDay")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_day));
        sb.append(dm).append(xfND(_charaId));
        sb.append(dm).append(xfND(_attackerCharaId));
        sb.append(dm).append(xfND(_targetCharaId));
        sb.append(dm).append(xfND(_targetFootstep));
        sb.append(dm).append(xfND(_targetSkillCode));
        sb.append(dm).append(xfND(_targetCampCode));
        sb.append(dm).append(xfND(_targetRooms));
        sb.append(dm).append(xfND(_abilityTypeCode));
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
        if (_abilityType != null && _abilityType.isPresent())
        { sb.append(dm).append("abilityType"); }
        if (_villageDay != null && _villageDay.isPresent())
        { sb.append(dm).append("villageDay"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Ability clone() {
        return (Ability)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 何日目か
     * @return The value of the column 'DAY'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDay() {
        checkSpecifiedProperty("day");
        return _day;
    }

    /**
     * [set] DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY} <br>
     * 何日目か
     * @param day The value of the column 'DAY'. (basically NotNull if update: for the constraint)
     */
    public void setDay(Integer day) {
        registerModifiedProperty("day");
        _day = day;
    }

    /**
     * [get] CHARA_ID: {PK, NotNull, INT UNSIGNED(10)} <br>
     * キャラクターID
     * @return The value of the column 'CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaId() {
        checkSpecifiedProperty("charaId");
        return _charaId;
    }

    /**
     * [set] CHARA_ID: {PK, NotNull, INT UNSIGNED(10)} <br>
     * キャラクターID
     * @param charaId The value of the column 'CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaId(Integer charaId) {
        registerModifiedProperty("charaId");
        _charaId = charaId;
    }

    /**
     * [get] ATTACKER_CHARA_ID: {INT UNSIGNED(10)} <br>
     * 襲撃者キャラクターID
     * @return The value of the column 'ATTACKER_CHARA_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getAttackerCharaId() {
        checkSpecifiedProperty("attackerCharaId");
        return _attackerCharaId;
    }

    /**
     * [set] ATTACKER_CHARA_ID: {INT UNSIGNED(10)} <br>
     * 襲撃者キャラクターID
     * @param attackerCharaId The value of the column 'ATTACKER_CHARA_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setAttackerCharaId(Integer attackerCharaId) {
        registerModifiedProperty("attackerCharaId");
        _attackerCharaId = attackerCharaId;
    }

    /**
     * [get] TARGET_CHARA_ID: {INT UNSIGNED(10)} <br>
     * 行使対象キャラID
     * @return The value of the column 'TARGET_CHARA_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getTargetCharaId() {
        checkSpecifiedProperty("targetCharaId");
        return _targetCharaId;
    }

    /**
     * [set] TARGET_CHARA_ID: {INT UNSIGNED(10)} <br>
     * 行使対象キャラID
     * @param targetCharaId The value of the column 'TARGET_CHARA_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTargetCharaId(Integer targetCharaId) {
        registerModifiedProperty("targetCharaId");
        _targetCharaId = targetCharaId;
    }

    /**
     * [get] TARGET_FOOTSTEP: {VARCHAR(1000)} <br>
     * 行使対象の足音
     * @return The value of the column 'TARGET_FOOTSTEP'. (NullAllowed even if selected: for no constraint)
     */
    public String getTargetFootstep() {
        checkSpecifiedProperty("targetFootstep");
        return convertEmptyToNull(_targetFootstep);
    }

    /**
     * [set] TARGET_FOOTSTEP: {VARCHAR(1000)} <br>
     * 行使対象の足音
     * @param targetFootstep The value of the column 'TARGET_FOOTSTEP'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTargetFootstep(String targetFootstep) {
        registerModifiedProperty("targetFootstep");
        _targetFootstep = targetFootstep;
    }

    /**
     * [get] TARGET_SKILL_CODE: {VARCHAR(20)} <br>
     * 行使対象の役職コード
     * @return The value of the column 'TARGET_SKILL_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getTargetSkillCode() {
        checkSpecifiedProperty("targetSkillCode");
        return convertEmptyToNull(_targetSkillCode);
    }

    /**
     * [set] TARGET_SKILL_CODE: {VARCHAR(20)} <br>
     * 行使対象の役職コード
     * @param targetSkillCode The value of the column 'TARGET_SKILL_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTargetSkillCode(String targetSkillCode) {
        registerModifiedProperty("targetSkillCode");
        _targetSkillCode = targetSkillCode;
    }

    /**
     * [get] TARGET_CAMP_CODE: {VARCHAR(20)} <br>
     * 行使対象の陣営コード
     * @return The value of the column 'TARGET_CAMP_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getTargetCampCode() {
        checkSpecifiedProperty("targetCampCode");
        return convertEmptyToNull(_targetCampCode);
    }

    /**
     * [set] TARGET_CAMP_CODE: {VARCHAR(20)} <br>
     * 行使対象の陣営コード
     * @param targetCampCode The value of the column 'TARGET_CAMP_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTargetCampCode(String targetCampCode) {
        registerModifiedProperty("targetCampCode");
        _targetCampCode = targetCampCode;
    }

    /**
     * [get] TARGET_ROOMS: {VARCHAR(1000)} <br>
     * 行使対象の部屋
     * @return The value of the column 'TARGET_ROOMS'. (NullAllowed even if selected: for no constraint)
     */
    public String getTargetRooms() {
        checkSpecifiedProperty("targetRooms");
        return convertEmptyToNull(_targetRooms);
    }

    /**
     * [set] TARGET_ROOMS: {VARCHAR(1000)} <br>
     * 行使対象の部屋
     * @param targetRooms The value of the column 'TARGET_ROOMS'. (NullAllowed: null update allowed for no constraint)
     */
    public void setTargetRooms(String targetRooms) {
        registerModifiedProperty("targetRooms");
        _targetRooms = targetRooms;
    }

    /**
     * [get] ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別コード
     * @return The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getAbilityTypeCode() {
        checkSpecifiedProperty("abilityTypeCode");
        return convertEmptyToNull(_abilityTypeCode);
    }

    /**
     * [set] ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別コード
     * @param abilityTypeCode The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setAbilityTypeCode(String abilityTypeCode) {
        checkClassificationCode("ABILITY_TYPE_CODE", CDef.DefMeta.AbilityType, abilityTypeCode);
        registerModifiedProperty("abilityTypeCode");
        _abilityTypeCode = abilityTypeCode;
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
     * @param abilityTypeCode The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingAbilityTypeCode(String abilityTypeCode) {
        setAbilityTypeCode(abilityTypeCode);
    }
}
