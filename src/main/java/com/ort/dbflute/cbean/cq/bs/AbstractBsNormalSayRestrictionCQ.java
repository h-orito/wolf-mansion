package com.ort.dbflute.cbean.cq.bs;

import java.util.*;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.ordering.*;
import org.dbflute.cbean.scoping.*;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.dbmeta.DBMetaProvider;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The abstract condition-query of NORMAL_SAY_RESTRICTION.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsNormalSayRestrictionCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsNormalSayRestrictionCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    public String asTableDbName() {
        return "NORMAL_SAY_RESTRICTION";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_Equal(Integer villageId) {
        doSetVillageId_Equal(villageId);
    }

    protected void doSetVillageId_Equal(Integer villageId) {
        regVillageId(CK_EQ, villageId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_NotEqual(Integer villageId) {
        doSetVillageId_NotEqual(villageId);
    }

    protected void doSetVillageId_NotEqual(Integer villageId) {
        regVillageId(CK_NES, villageId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setVillageId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageIdList The collection of villageId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageId_InScope(Collection<Integer> villageIdList) {
        doSetVillageId_InScope(villageIdList);
    }

    protected void doSetVillageId_InScope(Collection<Integer> villageIdList) {
        regINS(CK_INS, cTL(villageIdList), xgetCValueVillageId(), "VILLAGE_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageIdList The collection of villageId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageId_NotInScope(Collection<Integer> villageIdList) {
        doSetVillageId_NotInScope(villageIdList);
    }

    protected void doSetVillageId_NotInScope(Collection<Integer> villageIdList) {
        regINS(CK_NINS, cTL(villageIdList), xgetCValueVillageId(), "VILLAGE_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     */
    public void setVillageId_IsNull() { regVillageId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     */
    public void setVillageId_IsNotNull() { regVillageId(CK_ISNN, DOBJ); }

    protected void regVillageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageId(), "VILLAGE_ID"); }
    protected abstract ConditionValue xgetCValueVillageId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCode The value of skillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_Equal(String skillCode) {
        doSetSkillCode_Equal(fRES(skillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSkillCode_Equal_絶対人狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Equal(=). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setSkillCode_Equal_占星術師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * Equal(=). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setSkillCode_Equal_ババ() {
        setSkillCode_Equal_AsSkill(CDef.Skill.ババ);
    }

    /**
     * Equal(=). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setSkillCode_Equal_美人局() {
        setSkillCode_Equal_AsSkill(CDef.Skill.美人局);
    }

    /**
     * Equal(=). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setSkillCode_Equal_パン屋() {
        setSkillCode_Equal_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * Equal(=). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSkillCode_Equal_爆弾魔() {
        setSkillCode_Equal_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Equal(=). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSkillCode_Equal_誑狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * Equal(=). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setSkillCode_Equal_C国狂人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Equal(=). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setSkillCode_Equal_同棲者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * Equal(=). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setSkillCode_Equal_指揮官() {
        setSkillCode_Equal_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * Equal(=). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setSkillCode_Equal_検死官() {
        setSkillCode_Equal_AsSkill(CDef.Skill.検死官);
    }

    /**
     * Equal(=). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setSkillCode_Equal_求愛者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * Equal(=). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSkillCode_Equal_おまかせ愉快犯陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Equal(=). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setSkillCode_Equal_呪狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * Equal(=). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setSkillCode_Equal_探偵() {
        setSkillCode_Equal_AsSkill(CDef.Skill.探偵);
    }

    /**
     * Equal(=). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSkillCode_Equal_不止者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.不止者);
    }

    /**
     * Equal(=). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSkillCode_Equal_魔神官() {
        setSkillCode_Equal_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * Equal(=). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setSkillCode_Equal_冤罪者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Equal(=). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setSkillCode_Equal_狂信者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * Equal(=). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setSkillCode_Equal_妄想癖() {
        setSkillCode_Equal_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Equal(=). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setSkillCode_Equal_花占い師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * Equal(=). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setSkillCode_Equal_おまかせ足音職() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Equal(=). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setSkillCode_Equal_妖狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * Equal(=). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setSkillCode_Equal_おまかせ妖狐陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Equal(=). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setSkillCode_Equal_おまかせ役職窓あり() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Equal(=). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setSkillCode_Equal_果実籠() {
        setSkillCode_Equal_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * Equal(=). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setSkillCode_Equal_歩狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * Equal(=). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setSkillCode_Equal_銀狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * Equal(=). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setSkillCode_Equal_ごん() {
        setSkillCode_Equal_AsSkill(CDef.Skill.ごん);
    }

    /**
     * Equal(=). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSkillCode_Equal_導師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.導師);
    }

    /**
     * Equal(=). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setSkillCode_Equal_申し子() {
        setSkillCode_Equal_AsSkill(CDef.Skill.申し子);
    }

    /**
     * Equal(=). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setSkillCode_Equal_仙狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * Equal(=). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSkillCode_Equal_飛狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * Equal(=). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setSkillCode_Equal_狩人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.狩人);
    }

    /**
     * Equal(=). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setSkillCode_Equal_背徳者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * Equal(=). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSkillCode_Equal_煽動者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * Equal(=). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setSkillCode_Equal_絡新婦() {
        setSkillCode_Equal_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Equal(=). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setSkillCode_Equal_角狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.角狼);
    }

    /**
     * Equal(=). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setSkillCode_Equal_王狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.王狼);
    }

    /**
     * Equal(=). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setSkillCode_Equal_金狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.金狼);
    }

    /**
     * Equal(=). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSkillCode_Equal_おまかせ() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Equal(=). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setSkillCode_Equal_一匹狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Equal(=). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setSkillCode_Equal_拡声者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * Equal(=). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setSkillCode_Equal_恋人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.恋人);
    }

    /**
     * Equal(=). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setSkillCode_Equal_おまかせ恋人陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Equal(=). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setSkillCode_Equal_強運者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.強運者);
    }

    /**
     * Equal(=). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setSkillCode_Equal_狂人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.狂人);
    }

    /**
     * Equal(=). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setSkillCode_Equal_共鳴者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Equal(=). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSkillCode_Equal_霊能者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * Equal(=). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSkillCode_Equal_死霊術師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Equal(=). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_Equal_おまかせ役職窓なし() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Equal(=). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setSkillCode_Equal_おまかせ人外() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Equal(=). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setSkillCode_Equal_監視者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.監視者);
    }

    /**
     * Equal(=). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSkillCode_Equal_梟() {
        setSkillCode_Equal_AsSkill(CDef.Skill.梟);
    }

    /**
     * Equal(=). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSkillCode_Equal_虹職人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * Equal(=). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSkillCode_Equal_転生者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.転生者);
    }

    /**
     * Equal(=). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSkillCode_Equal_蘇生者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Equal(=). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSkillCode_Equal_占い師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.占い師);
    }

    /**
     * Equal(=). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSkillCode_Equal_夢遊病者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Equal(=). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setSkillCode_Equal_ストーカー() {
        setSkillCode_Equal_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Equal(=). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setSkillCode_Equal_濁点者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * Equal(=). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSkillCode_Equal_罠師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.罠師);
    }

    /**
     * Equal(=). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setSkillCode_Equal_トラック() {
        setSkillCode_Equal_AsSkill(CDef.Skill.トラック);
    }

    /**
     * Equal(=). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setSkillCode_Equal_村人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.村人);
    }

    /**
     * Equal(=). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setSkillCode_Equal_おまかせ村人陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Equal(=). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setSkillCode_Equal_壁殴り代行() {
        setSkillCode_Equal_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Equal(=). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setSkillCode_Equal_風来狩人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Equal(=). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setSkillCode_Equal_人狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.人狼);
    }

    /**
     * Equal(=). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setSkillCode_Equal_おまかせ人狼陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Equal(=). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setSkillCode_Equal_賢者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.賢者);
    }

    /**
     * Equal(=). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setSkillCode_Equal_智狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetSkillCode_Equal(String skillCode) {
        regSkillCode(CK_EQ, skillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCode The value of skillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotEqual(String skillCode) {
        doSetSkillCode_NotEqual(fRES(skillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSkillCode_NotEqual_絶対人狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setSkillCode_NotEqual_占星術師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * NotEqual(&lt;&gt;). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setSkillCode_NotEqual_ババ() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.ババ);
    }

    /**
     * NotEqual(&lt;&gt;). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setSkillCode_NotEqual_美人局() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.美人局);
    }

    /**
     * NotEqual(&lt;&gt;). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setSkillCode_NotEqual_パン屋() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSkillCode_NotEqual_爆弾魔() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * NotEqual(&lt;&gt;). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSkillCode_NotEqual_誑狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * NotEqual(&lt;&gt;). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setSkillCode_NotEqual_C国狂人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setSkillCode_NotEqual_同棲者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * NotEqual(&lt;&gt;). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setSkillCode_NotEqual_指揮官() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * NotEqual(&lt;&gt;). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setSkillCode_NotEqual_検死官() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.検死官);
    }

    /**
     * NotEqual(&lt;&gt;). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setSkillCode_NotEqual_求愛者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSkillCode_NotEqual_おまかせ愉快犯陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setSkillCode_NotEqual_呪狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setSkillCode_NotEqual_探偵() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.探偵);
    }

    /**
     * NotEqual(&lt;&gt;). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSkillCode_NotEqual_不止者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.不止者);
    }

    /**
     * NotEqual(&lt;&gt;). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSkillCode_NotEqual_魔神官() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * NotEqual(&lt;&gt;). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setSkillCode_NotEqual_冤罪者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setSkillCode_NotEqual_狂信者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * NotEqual(&lt;&gt;). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setSkillCode_NotEqual_妄想癖() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * NotEqual(&lt;&gt;). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setSkillCode_NotEqual_花占い師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setSkillCode_NotEqual_おまかせ足音職() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * NotEqual(&lt;&gt;). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setSkillCode_NotEqual_妖狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setSkillCode_NotEqual_おまかせ妖狐陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setSkillCode_NotEqual_おまかせ役職窓あり() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * NotEqual(&lt;&gt;). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setSkillCode_NotEqual_果実籠() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * NotEqual(&lt;&gt;). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setSkillCode_NotEqual_歩狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setSkillCode_NotEqual_銀狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * NotEqual(&lt;&gt;). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setSkillCode_NotEqual_ごん() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.ごん);
    }

    /**
     * NotEqual(&lt;&gt;). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSkillCode_NotEqual_導師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.導師);
    }

    /**
     * NotEqual(&lt;&gt;). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setSkillCode_NotEqual_申し子() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.申し子);
    }

    /**
     * NotEqual(&lt;&gt;). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setSkillCode_NotEqual_仙狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSkillCode_NotEqual_飛狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setSkillCode_NotEqual_狩人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setSkillCode_NotEqual_背徳者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSkillCode_NotEqual_煽動者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * NotEqual(&lt;&gt;). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setSkillCode_NotEqual_絡新婦() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * NotEqual(&lt;&gt;). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setSkillCode_NotEqual_角狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.角狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setSkillCode_NotEqual_王狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.王狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setSkillCode_NotEqual_金狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.金狼);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSkillCode_NotEqual_おまかせ() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * NotEqual(&lt;&gt;). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setSkillCode_NotEqual_一匹狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setSkillCode_NotEqual_拡声者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setSkillCode_NotEqual_恋人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.恋人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setSkillCode_NotEqual_おまかせ恋人陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setSkillCode_NotEqual_強運者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.強運者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setSkillCode_NotEqual_狂人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setSkillCode_NotEqual_共鳴者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * NotEqual(&lt;&gt;). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSkillCode_NotEqual_霊能者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * NotEqual(&lt;&gt;). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSkillCode_NotEqual_死霊術師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_NotEqual_おまかせ役職窓なし() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setSkillCode_NotEqual_おまかせ人外() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * NotEqual(&lt;&gt;). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setSkillCode_NotEqual_監視者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.監視者);
    }

    /**
     * NotEqual(&lt;&gt;). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSkillCode_NotEqual_梟() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.梟);
    }

    /**
     * NotEqual(&lt;&gt;). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSkillCode_NotEqual_虹職人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * NotEqual(&lt;&gt;). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSkillCode_NotEqual_転生者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.転生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSkillCode_NotEqual_蘇生者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSkillCode_NotEqual_占い師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSkillCode_NotEqual_夢遊病者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * NotEqual(&lt;&gt;). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setSkillCode_NotEqual_ストーカー() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * NotEqual(&lt;&gt;). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setSkillCode_NotEqual_濁点者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSkillCode_NotEqual_罠師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.罠師);
    }

    /**
     * NotEqual(&lt;&gt;). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setSkillCode_NotEqual_トラック() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.トラック);
    }

    /**
     * NotEqual(&lt;&gt;). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setSkillCode_NotEqual_村人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.村人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setSkillCode_NotEqual_おまかせ村人陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setSkillCode_NotEqual_壁殴り代行() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * NotEqual(&lt;&gt;). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setSkillCode_NotEqual_風来狩人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setSkillCode_NotEqual_人狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setSkillCode_NotEqual_おまかせ人狼陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setSkillCode_NotEqual_賢者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.賢者);
    }

    /**
     * NotEqual(&lt;&gt;). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setSkillCode_NotEqual_智狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetSkillCode_NotEqual(String skillCode) {
        regSkillCode(CK_NES, skillCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCodeList The collection of skillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_InScope(Collection<String> skillCodeList) {
        doSetSkillCode_InScope(skillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_InScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSkillCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, C国狂人]
     */
    public void setSkillCode_InScope_AvailableWerewolfSay() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師]
     */
    public void setSkillCode_InScope_HasDivineAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官]
     */
    public void setSkillCode_InScope_HasSkillPsychicAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼]
     */
    public void setSkillCode_InScope_HasAttackAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 妖狐, 仙狐, 背徳者]
     */
    public void setSkillCode_InScope_HasDisturbAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 妖狐, 誑狐, ごん, 爆弾魔]
     */
    public void setSkillCode_InScope_NoDeadByAttack() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼]
     */
    public void setSkillCode_InScope_WolfCount() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfWolfCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 梟]
     */
    public void setSkillCode_InScope_NoCount() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfNoCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, C国狂人, 狂信者, 煽動者]
     */
    public void setSkillCode_InScope_ViewableWolfCharaName() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 一匹狼]
     */
    public void setSkillCode_InScope_DivineResultWolf() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfDivineResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 一匹狼]
     */
    public void setSkillCode_InScope_PsychicResultWolf() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfPsychicResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     */
    public void setSkillCode_InScope_SomeoneSkill() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
    }

    protected void doSetSkillCode_InScope(Collection<String> skillCodeList) {
        regINS(CK_INS, cTL(skillCodeList), xgetCValueSkillCode(), "SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCodeList The collection of skillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotInScope(Collection<String> skillCodeList) {
        doSetSkillCode_NotInScope(skillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_NotInScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSkillCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetSkillCode_NotInScope(Collection<String> skillCodeList) {
        regINS(CK_NINS, cTL(skillCodeList), xgetCValueSkillCode(), "SKILL_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setSkillCode_IsNull() { regSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setSkillCode_IsNotNull() { regSkillCode(CK_ISNN, DOBJ); }

    protected void regSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueSkillCode(), "SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueSkillCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_Equal(String messageTypeCode) {
        doSetMessageTypeCode_Equal(fRES(messageTypeCode));
    }

    /**
     * Equal(=). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageTypeCode_Equal_AsMessageType(CDef.MessageType cdef) {
        doSetMessageTypeCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As アクション (ACTION). And OnlyOnceRegistered. <br>
     * アクション
     */
    public void setMessageTypeCode_Equal_アクション() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.アクション);
    }

    /**
     * Equal(=). As 村建て発言 (CREATOR_SAY). And OnlyOnceRegistered. <br>
     * 村建て発言
     */
    public void setMessageTypeCode_Equal_村建て発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.村建て発言);
    }

    /**
     * Equal(=). As 死者の呻き (GRAVE_SAY). And OnlyOnceRegistered. <br>
     * 死者の呻き
     */
    public void setMessageTypeCode_Equal_死者の呻き() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.死者の呻き);
    }

    /**
     * Equal(=). As 恋人発言 (LOVERS_SAY). And OnlyOnceRegistered. <br>
     * 恋人発言
     */
    public void setMessageTypeCode_Equal_恋人発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.恋人発言);
    }

    /**
     * Equal(=). As 共鳴発言 (MASON_SAY). And OnlyOnceRegistered. <br>
     * 共鳴発言
     */
    public void setMessageTypeCode_Equal_共鳴発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.共鳴発言);
    }

    /**
     * Equal(=). As 独り言 (MONOLOGUE_SAY). And OnlyOnceRegistered. <br>
     * 独り言
     */
    public void setMessageTypeCode_Equal_独り言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.独り言);
    }

    /**
     * Equal(=). As 通常発言 (NORMAL_SAY). And OnlyOnceRegistered. <br>
     * 通常発言
     */
    public void setMessageTypeCode_Equal_通常発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.通常発言);
    }

    /**
     * Equal(=). As 参加者一覧 (PARTICIPANTS). And OnlyOnceRegistered. <br>
     * 参加者一覧
     */
    public void setMessageTypeCode_Equal_参加者一覧() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.参加者一覧);
    }

    /**
     * Equal(=). As 検死結果 (PRIVATE_CORONER). And OnlyOnceRegistered. <br>
     * 検死結果
     */
    public void setMessageTypeCode_Equal_検死結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.検死結果);
    }

    /**
     * Equal(=). As 妖狐メッセージ (PRIVATE_FOX). And OnlyOnceRegistered. <br>
     * 妖狐メッセージ
     */
    public void setMessageTypeCode_Equal_妖狐メッセージ() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.妖狐メッセージ);
    }

    /**
     * Equal(=). As 役職霊視結果 (PRIVATE_GURU). And OnlyOnceRegistered. <br>
     * 役職霊視結果
     */
    public void setMessageTypeCode_Equal_役職霊視結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.役職霊視結果);
    }

    /**
     * Equal(=). As 足音調査結果 (PRIVATE_INVESTIGATE). And OnlyOnceRegistered. <br>
     * 足音調査結果
     */
    public void setMessageTypeCode_Equal_足音調査結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.足音調査結果);
    }

    /**
     * Equal(=). As 恋人メッセージ (PRIVATE_LOVER). And OnlyOnceRegistered. <br>
     * 恋人メッセージ
     */
    public void setMessageTypeCode_Equal_恋人メッセージ() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.恋人メッセージ);
    }

    /**
     * Equal(=). As 白黒霊視結果 (PRIVATE_PSYCHIC). And OnlyOnceRegistered. <br>
     * 白黒霊視結果
     */
    public void setMessageTypeCode_Equal_白黒霊視結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.白黒霊視結果);
    }

    /**
     * Equal(=). As 白黒占い結果 (PRIVATE_SEER). And OnlyOnceRegistered. <br>
     * 白黒占い結果
     */
    public void setMessageTypeCode_Equal_白黒占い結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.白黒占い結果);
    }

    /**
     * Equal(=). As 非公開システムメッセージ (PRIVATE_SYSTEM). And OnlyOnceRegistered. <br>
     * 非公開システムメッセージ
     */
    public void setMessageTypeCode_Equal_非公開システムメッセージ() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.非公開システムメッセージ);
    }

    /**
     * Equal(=). As 襲撃結果 (PRIVATE_WEREWOLF). And OnlyOnceRegistered. <br>
     * 襲撃結果
     */
    public void setMessageTypeCode_Equal_襲撃結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.襲撃結果);
    }

    /**
     * Equal(=). As 役職占い結果 (PRIVATE_WISE). And OnlyOnceRegistered. <br>
     * 役職占い結果
     */
    public void setMessageTypeCode_Equal_役職占い結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.役職占い結果);
    }

    /**
     * Equal(=). As 公開システムメッセージ (PUBLIC_SYSTEM). And OnlyOnceRegistered. <br>
     * 公開システムメッセージ
     */
    public void setMessageTypeCode_Equal_公開システムメッセージ() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.公開システムメッセージ);
    }

    /**
     * Equal(=). As 秘話 (SECRET_SAY). And OnlyOnceRegistered. <br>
     * 秘話
     */
    public void setMessageTypeCode_Equal_秘話() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.秘話);
    }

    /**
     * Equal(=). As 見学発言 (SPECTATE_SAY). And OnlyOnceRegistered. <br>
     * 見学発言
     */
    public void setMessageTypeCode_Equal_見学発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.見学発言);
    }

    /**
     * Equal(=). As 念話 (TELEPATHY). And OnlyOnceRegistered. <br>
     * 念話
     */
    public void setMessageTypeCode_Equal_念話() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.念話);
    }

    /**
     * Equal(=). As 人狼の囁き (WEREWOLF_SAY). And OnlyOnceRegistered. <br>
     * 人狼の囁き
     */
    public void setMessageTypeCode_Equal_人狼の囁き() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.人狼の囁き);
    }

    protected void doSetMessageTypeCode_Equal(String messageTypeCode) {
        regMessageTypeCode(CK_EQ, messageTypeCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotEqual(String messageTypeCode) {
        doSetMessageTypeCode_NotEqual(fRES(messageTypeCode));
    }

    /**
     * NotEqual(&lt;&gt;). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType cdef) {
        doSetMessageTypeCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As アクション (ACTION). And OnlyOnceRegistered. <br>
     * アクション
     */
    public void setMessageTypeCode_NotEqual_アクション() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.アクション);
    }

    /**
     * NotEqual(&lt;&gt;). As 村建て発言 (CREATOR_SAY). And OnlyOnceRegistered. <br>
     * 村建て発言
     */
    public void setMessageTypeCode_NotEqual_村建て発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.村建て発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 死者の呻き (GRAVE_SAY). And OnlyOnceRegistered. <br>
     * 死者の呻き
     */
    public void setMessageTypeCode_NotEqual_死者の呻き() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.死者の呻き);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋人発言 (LOVERS_SAY). And OnlyOnceRegistered. <br>
     * 恋人発言
     */
    public void setMessageTypeCode_NotEqual_恋人発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.恋人発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 共鳴発言 (MASON_SAY). And OnlyOnceRegistered. <br>
     * 共鳴発言
     */
    public void setMessageTypeCode_NotEqual_共鳴発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.共鳴発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 独り言 (MONOLOGUE_SAY). And OnlyOnceRegistered. <br>
     * 独り言
     */
    public void setMessageTypeCode_NotEqual_独り言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.独り言);
    }

    /**
     * NotEqual(&lt;&gt;). As 通常発言 (NORMAL_SAY). And OnlyOnceRegistered. <br>
     * 通常発言
     */
    public void setMessageTypeCode_NotEqual_通常発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.通常発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 参加者一覧 (PARTICIPANTS). And OnlyOnceRegistered. <br>
     * 参加者一覧
     */
    public void setMessageTypeCode_NotEqual_参加者一覧() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.参加者一覧);
    }

    /**
     * NotEqual(&lt;&gt;). As 検死結果 (PRIVATE_CORONER). And OnlyOnceRegistered. <br>
     * 検死結果
     */
    public void setMessageTypeCode_NotEqual_検死結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.検死結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 妖狐メッセージ (PRIVATE_FOX). And OnlyOnceRegistered. <br>
     * 妖狐メッセージ
     */
    public void setMessageTypeCode_NotEqual_妖狐メッセージ() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.妖狐メッセージ);
    }

    /**
     * NotEqual(&lt;&gt;). As 役職霊視結果 (PRIVATE_GURU). And OnlyOnceRegistered. <br>
     * 役職霊視結果
     */
    public void setMessageTypeCode_NotEqual_役職霊視結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.役職霊視結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 足音調査結果 (PRIVATE_INVESTIGATE). And OnlyOnceRegistered. <br>
     * 足音調査結果
     */
    public void setMessageTypeCode_NotEqual_足音調査結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.足音調査結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋人メッセージ (PRIVATE_LOVER). And OnlyOnceRegistered. <br>
     * 恋人メッセージ
     */
    public void setMessageTypeCode_NotEqual_恋人メッセージ() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.恋人メッセージ);
    }

    /**
     * NotEqual(&lt;&gt;). As 白黒霊視結果 (PRIVATE_PSYCHIC). And OnlyOnceRegistered. <br>
     * 白黒霊視結果
     */
    public void setMessageTypeCode_NotEqual_白黒霊視結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.白黒霊視結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 白黒占い結果 (PRIVATE_SEER). And OnlyOnceRegistered. <br>
     * 白黒占い結果
     */
    public void setMessageTypeCode_NotEqual_白黒占い結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.白黒占い結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 非公開システムメッセージ (PRIVATE_SYSTEM). And OnlyOnceRegistered. <br>
     * 非公開システムメッセージ
     */
    public void setMessageTypeCode_NotEqual_非公開システムメッセージ() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.非公開システムメッセージ);
    }

    /**
     * NotEqual(&lt;&gt;). As 襲撃結果 (PRIVATE_WEREWOLF). And OnlyOnceRegistered. <br>
     * 襲撃結果
     */
    public void setMessageTypeCode_NotEqual_襲撃結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.襲撃結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 役職占い結果 (PRIVATE_WISE). And OnlyOnceRegistered. <br>
     * 役職占い結果
     */
    public void setMessageTypeCode_NotEqual_役職占い結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.役職占い結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 公開システムメッセージ (PUBLIC_SYSTEM). And OnlyOnceRegistered. <br>
     * 公開システムメッセージ
     */
    public void setMessageTypeCode_NotEqual_公開システムメッセージ() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.公開システムメッセージ);
    }

    /**
     * NotEqual(&lt;&gt;). As 秘話 (SECRET_SAY). And OnlyOnceRegistered. <br>
     * 秘話
     */
    public void setMessageTypeCode_NotEqual_秘話() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.秘話);
    }

    /**
     * NotEqual(&lt;&gt;). As 見学発言 (SPECTATE_SAY). And OnlyOnceRegistered. <br>
     * 見学発言
     */
    public void setMessageTypeCode_NotEqual_見学発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.見学発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 念話 (TELEPATHY). And OnlyOnceRegistered. <br>
     * 念話
     */
    public void setMessageTypeCode_NotEqual_念話() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.念話);
    }

    /**
     * NotEqual(&lt;&gt;). As 人狼の囁き (WEREWOLF_SAY). And OnlyOnceRegistered. <br>
     * 人狼の囁き
     */
    public void setMessageTypeCode_NotEqual_人狼の囁き() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.人狼の囁き);
    }

    protected void doSetMessageTypeCode_NotEqual(String messageTypeCode) {
        regMessageTypeCode(CK_NES, messageTypeCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_InScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_InScope(messageTypeCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeCode_InScope_AsMessageType(Collection<CDef.MessageType> cdefList) {
        doSetMessageTypeCode_InScope(cTStrL(cdefList));
    }

    protected void doSetMessageTypeCode_InScope(Collection<String> messageTypeCodeList) {
        regINS(CK_INS, cTL(messageTypeCodeList), xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotInScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_NotInScope(messageTypeCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeCode_NotInScope_AsMessageType(Collection<CDef.MessageType> cdefList) {
        doSetMessageTypeCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetMessageTypeCode_NotInScope(Collection<String> messageTypeCodeList) {
        regINS(CK_NINS, cTL(messageTypeCodeList), xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     */
    public void setMessageTypeCode_IsNull() { regMessageTypeCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     */
    public void setMessageTypeCode_IsNotNull() { regMessageTypeCode(CK_ISNN, DOBJ); }

    protected void regMessageTypeCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE"); }
    protected abstract ConditionValue xgetCValueMessageTypeCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_Equal(Integer messageMaxNum) {
        doSetMessageMaxNum_Equal(messageMaxNum);
    }

    protected void doSetMessageMaxNum_Equal(Integer messageMaxNum) {
        regMessageMaxNum(CK_EQ, messageMaxNum);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_NotEqual(Integer messageMaxNum) {
        doSetMessageMaxNum_NotEqual(messageMaxNum);
    }

    protected void doSetMessageMaxNum_NotEqual(Integer messageMaxNum) {
        regMessageMaxNum(CK_NES, messageMaxNum);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_GreaterThan(Integer messageMaxNum) {
        regMessageMaxNum(CK_GT, messageMaxNum);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_LessThan(Integer messageMaxNum) {
        regMessageMaxNum(CK_LT, messageMaxNum);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_GreaterEqual(Integer messageMaxNum) {
        regMessageMaxNum(CK_GE, messageMaxNum);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_LessEqual(Integer messageMaxNum) {
        regMessageMaxNum(CK_LE, messageMaxNum);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMessageMaxNum_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMessageMaxNum_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMessageMaxNum_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNumList The collection of messageMaxNum as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxNum_InScope(Collection<Integer> messageMaxNumList) {
        doSetMessageMaxNum_InScope(messageMaxNumList);
    }

    protected void doSetMessageMaxNum_InScope(Collection<Integer> messageMaxNumList) {
        regINS(CK_INS, cTL(messageMaxNumList), xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNumList The collection of messageMaxNum as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxNum_NotInScope(Collection<Integer> messageMaxNumList) {
        doSetMessageMaxNum_NotInScope(messageMaxNumList);
    }

    protected void doSetMessageMaxNum_NotInScope(Collection<Integer> messageMaxNumList) {
        regINS(CK_NINS, cTL(messageMaxNumList), xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM");
    }

    protected void regMessageMaxNum(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM"); }
    protected abstract ConditionValue xgetCValueMessageMaxNum();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_Equal(Integer messageMaxLength) {
        doSetMessageMaxLength_Equal(messageMaxLength);
    }

    protected void doSetMessageMaxLength_Equal(Integer messageMaxLength) {
        regMessageMaxLength(CK_EQ, messageMaxLength);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_NotEqual(Integer messageMaxLength) {
        doSetMessageMaxLength_NotEqual(messageMaxLength);
    }

    protected void doSetMessageMaxLength_NotEqual(Integer messageMaxLength) {
        regMessageMaxLength(CK_NES, messageMaxLength);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_GreaterThan(Integer messageMaxLength) {
        regMessageMaxLength(CK_GT, messageMaxLength);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_LessThan(Integer messageMaxLength) {
        regMessageMaxLength(CK_LT, messageMaxLength);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_GreaterEqual(Integer messageMaxLength) {
        regMessageMaxLength(CK_GE, messageMaxLength);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_LessEqual(Integer messageMaxLength) {
        regMessageMaxLength(CK_LE, messageMaxLength);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMessageMaxLength_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMessageMaxLength_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMessageMaxLength_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLengthList The collection of messageMaxLength as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxLength_InScope(Collection<Integer> messageMaxLengthList) {
        doSetMessageMaxLength_InScope(messageMaxLengthList);
    }

    protected void doSetMessageMaxLength_InScope(Collection<Integer> messageMaxLengthList) {
        regINS(CK_INS, cTL(messageMaxLengthList), xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLengthList The collection of messageMaxLength as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxLength_NotInScope(Collection<Integer> messageMaxLengthList) {
        doSetMessageMaxLength_NotInScope(messageMaxLengthList);
    }

    protected void doSetMessageMaxLength_NotInScope(Collection<Integer> messageMaxLengthList) {
        regINS(CK_NINS, cTL(messageMaxLengthList), xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH");
    }

    protected void regMessageMaxLength(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH"); }
    protected abstract ConditionValue xgetCValueMessageMaxLength();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_Equal(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_EQ,  registerDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GT,  registerDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LT,  registerDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GE,  registerDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LE, registerDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setRegisterDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "REGISTER_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueRegisterDatetime(), nm, op);
    }

    protected void regRegisterDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterDatetime(), "REGISTER_DATETIME"); }
    protected abstract ConditionValue xgetCValueRegisterDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_Equal(String registerTrace) {
        doSetRegisterTrace_Equal(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_Equal(String registerTrace) {
        regRegisterTrace(CK_EQ, registerTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotEqual(String registerTrace) {
        doSetRegisterTrace_NotEqual(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_NotEqual(String registerTrace) {
        regRegisterTrace(CK_NES, registerTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterThan(String registerTrace) {
        regRegisterTrace(CK_GT, fRES(registerTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessThan(String registerTrace) {
        regRegisterTrace(CK_LT, fRES(registerTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterEqual(String registerTrace) {
        regRegisterTrace(CK_GE, fRES(registerTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessEqual(String registerTrace) {
        regRegisterTrace(CK_LE, fRES(registerTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_InScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_InScope(registerTraceList);
    }

    protected void doSetRegisterTrace_InScope(Collection<String> registerTraceList) {
        regINS(CK_INS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_NotInScope(registerTraceList);
    }

    protected void doSetRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        regINS(CK_NINS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_LikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_LikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setRegisterTrace_LikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_NotLikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_NotLikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setRegisterTrace_NotLikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    protected void regRegisterTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterTrace(), "REGISTER_TRACE"); }
    protected abstract ConditionValue xgetCValueRegisterTrace();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_Equal(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_EQ,  updateDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GT,  updateDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LT,  updateDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GE,  updateDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LE, updateDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setUpdateDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "UPDATE_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueUpdateDatetime(), nm, op);
    }

    protected void regUpdateDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateDatetime(), "UPDATE_DATETIME"); }
    protected abstract ConditionValue xgetCValueUpdateDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_Equal(String updateTrace) {
        doSetUpdateTrace_Equal(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_Equal(String updateTrace) {
        regUpdateTrace(CK_EQ, updateTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotEqual(String updateTrace) {
        doSetUpdateTrace_NotEqual(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_NotEqual(String updateTrace) {
        regUpdateTrace(CK_NES, updateTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterThan(String updateTrace) {
        regUpdateTrace(CK_GT, fRES(updateTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessThan(String updateTrace) {
        regUpdateTrace(CK_LT, fRES(updateTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterEqual(String updateTrace) {
        regUpdateTrace(CK_GE, fRES(updateTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessEqual(String updateTrace) {
        regUpdateTrace(CK_LE, fRES(updateTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_InScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_InScope(updateTraceList);
    }

    protected void doSetUpdateTrace_InScope(Collection<String> updateTraceList) {
        regINS(CK_INS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_NotInScope(updateTraceList);
    }

    protected void doSetUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        regINS(CK_NINS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_LikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_LikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setUpdateTrace_LikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_NotLikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_NotLikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setUpdateTrace_NotLikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    protected void regUpdateTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateTrace(), "UPDATE_TRACE"); }
    protected abstract ConditionValue xgetCValueUpdateTrace();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO = (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<NormalSayRestrictionCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, NormalSayRestrictionCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO &lt;&gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<NormalSayRestrictionCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, NormalSayRestrictionCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br>
     * {where FOO &gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<NormalSayRestrictionCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, NormalSayRestrictionCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br>
     * {where FOO &lt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<NormalSayRestrictionCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, NormalSayRestrictionCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br>
     * {where FOO &gt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<NormalSayRestrictionCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, NormalSayRestrictionCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;NormalSayRestrictionCB&gt;() {
     *     public void query(NormalSayRestrictionCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<NormalSayRestrictionCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, NormalSayRestrictionCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        NormalSayRestrictionCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(NormalSayRestrictionCQ sq);

    protected NormalSayRestrictionCB xcreateScalarConditionCB() {
        NormalSayRestrictionCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected NormalSayRestrictionCB xcreateScalarConditionPartitionByCB() {
        NormalSayRestrictionCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                        Manual Order
    //                                                                        ============
    /**
     * Order along manual ordering information.
     * <pre>
     * cb.query().addOrderBy_Birthdate_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when BIRTHDATE &gt;= '2000/01/01' then 0</span>
     * <span style="color: #3F7E5E">//     else 1</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     *
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Withdrawal);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Formalized);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Provisional);
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'WDL' then 0</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'FML' then 1</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'PRV' then 2</span>
     * <span style="color: #3F7E5E">//     else 3</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     * </pre>
     * <p>This function with Union is unsupported!</p>
     * <p>The order values are bound (treated as bind parameter).</p>
     * @param opLambda The callback for option of manual-order containing order values. (NotNull)
     */
    public void withManualOrder(ManualOrderOptionCall opLambda) { // is user public!
        xdoWithManualOrder(cMOO(opLambda));
    }

    // ===================================================================================
    //                                                                    Small Adjustment
    //                                                                    ================
    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    protected NormalSayRestrictionCB newMyCB() {
        return new NormalSayRestrictionCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return NormalSayRestrictionCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
