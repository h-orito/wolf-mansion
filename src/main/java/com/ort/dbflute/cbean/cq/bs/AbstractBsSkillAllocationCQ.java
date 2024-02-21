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
 * The abstract condition-query of skill_allocation.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsSkillAllocationCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsSkillAllocationCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "skill_allocation";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     */
    public void setVillageId_IsNull() { regVillageId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     */
    public void setVillageId_IsNotNull() { regVillageId(CK_ISNN, DOBJ); }

    protected void regVillageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageId(), "VILLAGE_ID"); }
    protected abstract ConditionValue xgetCValueVillageId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCode The value of skillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_Equal(String skillCode) {
        doSetSkillCode_Equal(fRES(skillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setSkillCode_Equal_教唆者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * Equal(=). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSkillCode_Equal_絶対人狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Equal(=). As 餡麺麭者 (ANPANMAN). And OnlyOnceRegistered. <br>
     * 餡麺麭者
     */
    public void setSkillCode_Equal_餡麺麭者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.餡麺麭者);
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
     * Equal(=). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setSkillCode_Equal_バールのようなもの() {
        setSkillCode_Equal_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Equal(=). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setSkillCode_Equal_黒箱者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Equal(=). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSkillCode_Equal_爆弾魔() {
        setSkillCode_Equal_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Equal(=). As 組長 (BOSS). And OnlyOnceRegistered. <br>
     * 組長
     */
    public void setSkillCode_Equal_組長() {
        setSkillCode_Equal_AsSkill(CDef.Skill.組長);
    }

    /**
     * Equal(=). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSkillCode_Equal_誑狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * Equal(=). As 浮気者 (CHEATLOVER). And OnlyOnceRegistered. <br>
     * 浮気者
     */
    public void setSkillCode_Equal_浮気者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.浮気者);
    }

    /**
     * Equal(=). As 曇天者 (CLOUDY). And OnlyOnceRegistered. <br>
     * 曇天者
     */
    public void setSkillCode_Equal_曇天者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.曇天者);
    }

    /**
     * Equal(=). As 道化師 (CLOWN). And OnlyOnceRegistered. <br>
     * 道化師
     */
    public void setSkillCode_Equal_道化師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.道化師);
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
     * Equal(=). As 反呪者 (CURSECOUNTER). And OnlyOnceRegistered. <br>
     * 反呪者
     */
    public void setSkillCode_Equal_反呪者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.反呪者);
    }

    /**
     * Equal(=). As 呪縛者 (CURSER). And OnlyOnceRegistered. <br>
     * 呪縛者
     */
    public void setSkillCode_Equal_呪縛者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.呪縛者);
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
     * Equal(=). As 興信者 (DETECTSEER). And OnlyOnceRegistered. <br>
     * 興信者
     */
    public void setSkillCode_Equal_興信者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.興信者);
    }

    /**
     * Equal(=). As 剖狼 (DISSECTWOLF). And OnlyOnceRegistered. <br>
     * 剖狼
     */
    public void setSkillCode_Equal_剖狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.剖狼);
    }

    /**
     * Equal(=). As 箪笥 (DRAWERS). And OnlyOnceRegistered. <br>
     * 箪笥
     */
    public void setSkillCode_Equal_箪笥() {
        setSkillCode_Equal_AsSkill(CDef.Skill.箪笥);
    }

    /**
     * Equal(=). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSkillCode_Equal_不止者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.不止者);
    }

    /**
     * Equal(=). As 帝狼 (EMPERORWOLF). And OnlyOnceRegistered. <br>
     * 帝狼
     */
    public void setSkillCode_Equal_帝狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.帝狼);
    }

    /**
     * Equal(=). As 闇パン屋 (EVILBAKERY). And OnlyOnceRegistered. <br>
     * 闇パン屋
     */
    public void setSkillCode_Equal_闇パン屋() {
        setSkillCode_Equal_AsSkill(CDef.Skill.闇パン屋);
    }

    /**
     * Equal(=). As 闇探偵 (EVILDETECTIVE). And OnlyOnceRegistered. <br>
     * 闇探偵
     */
    public void setSkillCode_Equal_闇探偵() {
        setSkillCode_Equal_AsSkill(CDef.Skill.闇探偵);
    }

    /**
     * Equal(=). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSkillCode_Equal_魔神官() {
        setSkillCode_Equal_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * Equal(=). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setSkillCode_Equal_執行人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.執行人);
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
     * Equal(=). As 冷凍者 (FREEZER). And OnlyOnceRegistered. <br>
     * 冷凍者
     */
    public void setSkillCode_Equal_冷凍者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.冷凍者);
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
     * Equal(=). As 濡衣者 (GUILTER). And OnlyOnceRegistered. <br>
     * 濡衣者
     */
    public void setSkillCode_Equal_濡衣者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.濡衣者);
    }

    /**
     * Equal(=). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSkillCode_Equal_導師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.導師);
    }

    /**
     * Equal(=). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setSkillCode_Equal_堅狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.堅狼);
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
     * Equal(=). As 勇者 (HERO). And OnlyOnceRegistered. <br>
     * 勇者
     */
    public void setSkillCode_Equal_勇者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.勇者);
    }

    /**
     * Equal(=). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSkillCode_Equal_飛狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * Equal(=). As 冷やし中華 (HIYASICHUKA). And OnlyOnceRegistered. <br>
     * 冷やし中華
     */
    public void setSkillCode_Equal_冷やし中華() {
        setSkillCode_Equal_AsSkill(CDef.Skill.冷やし中華);
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
     * Equal(=). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setSkillCode_Equal_稲荷() {
        setSkillCode_Equal_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * Equal(=). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSkillCode_Equal_煽動者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * Equal(=). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setSkillCode_Equal_保険屋() {
        setSkillCode_Equal_AsSkill(CDef.Skill.保険屋);
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
     * Equal(=). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setSkillCode_Equal_管狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.管狐);
    }

    /**
     * Equal(=). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setSkillCode_Equal_弁護士() {
        setSkillCode_Equal_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * Equal(=). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSkillCode_Equal_おまかせ() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Equal(=). As 伝説の殺し屋 (LEGENDASSASSIN). And OnlyOnceRegistered. <br>
     * 伝説の殺し屋
     */
    public void setSkillCode_Equal_伝説の殺し屋() {
        setSkillCode_Equal_AsSkill(CDef.Skill.伝説の殺し屋);
    }

    /**
     * Equal(=). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setSkillCode_Equal_聴狂人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Equal(=). As 共有者 (LISTENMASON). And OnlyOnceRegistered. <br>
     * 共有者
     */
    public void setSkillCode_Equal_共有者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.共有者);
    }

    /**
     * Equal(=). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setSkillCode_Equal_黙狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.黙狼);
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
     * Equal(=). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setSkillCode_Equal_マタギ() {
        setSkillCode_Equal_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * Equal(=). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setSkillCode_Equal_市長() {
        setSkillCode_Equal_AsSkill(CDef.Skill.市長);
    }

    /**
     * Equal(=). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSkillCode_Equal_霊能者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * Equal(=). As 魅惑の人魚 (MERMAID). And OnlyOnceRegistered. <br>
     * 魅惑の人魚
     */
    public void setSkillCode_Equal_魅惑の人魚() {
        setSkillCode_Equal_AsSkill(CDef.Skill.魅惑の人魚);
    }

    /**
     * Equal(=). As 耳年増 (MIMIDOSHIMA). And OnlyOnceRegistered. <br>
     * 耳年増
     */
    public void setSkillCode_Equal_耳年増() {
        setSkillCode_Equal_AsSkill(CDef.Skill.耳年増);
    }

    /**
     * Equal(=). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSkillCode_Equal_死霊術師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Equal(=). As 夜狐 (NIGHTFOX). And OnlyOnceRegistered. <br>
     * 夜狐
     */
    public void setSkillCode_Equal_夜狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.夜狐);
    }

    /**
     * Equal(=). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_Equal_おまかせ役職窓なし() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Equal(=). As リア充 (NORMIE). And OnlyOnceRegistered. <br>
     * リア充
     */
    public void setSkillCode_Equal_リア充() {
        setSkillCode_Equal_AsSkill(CDef.Skill.リア充);
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
     * Equal(=). As 全知者 (OMNISCIENCE). And OnlyOnceRegistered. <br>
     * 全知者
     */
    public void setSkillCode_Equal_全知者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.全知者);
    }

    /**
     * Equal(=). As 陰陽師 (ONMYOJI). And OnlyOnceRegistered. <br>
     * 陰陽師
     */
    public void setSkillCode_Equal_陰陽師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.陰陽師);
    }

    /**
     * Equal(=). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSkillCode_Equal_梟() {
        setSkillCode_Equal_AsSkill(CDef.Skill.梟);
    }

    /**
     * Equal(=). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setSkillCode_Equal_牧師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.牧師);
    }

    /**
     * Equal(=). As 海王者 (POSEIDON). And OnlyOnceRegistered. <br>
     * 海王者
     */
    public void setSkillCode_Equal_海王者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.海王者);
    }

    /**
     * Equal(=). As 画鋲 (PUSHPIN). And OnlyOnceRegistered. <br>
     * 画鋲
     */
    public void setSkillCode_Equal_画鋲() {
        setSkillCode_Equal_AsSkill(CDef.Skill.画鋲);
    }

    /**
     * Equal(=). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSkillCode_Equal_虹職人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * Equal(=). As 暴狼 (RAMPAGEWOLF). And OnlyOnceRegistered. <br>
     * 暴狼
     */
    public void setSkillCode_Equal_暴狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.暴狼);
    }

    /**
     * Equal(=). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSkillCode_Equal_転生者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.転生者);
    }

    /**
     * Equal(=). As 覚者 (REMEMBERSEER). And OnlyOnceRegistered. <br>
     * 覚者
     */
    public void setSkillCode_Equal_覚者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.覚者);
    }

    /**
     * Equal(=). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setSkillCode_Equal_怨恨者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Equal(=). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSkillCode_Equal_蘇生者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Equal(=). As 革命者 (REVOLUTIONARY). And OnlyOnceRegistered. <br>
     * 革命者
     */
    public void setSkillCode_Equal_革命者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.革命者);
    }

    /**
     * Equal(=). As 王族 (ROYALTY). And OnlyOnceRegistered. <br>
     * 王族
     */
    public void setSkillCode_Equal_王族() {
        setSkillCode_Equal_AsSkill(CDef.Skill.王族);
    }

    /**
     * Equal(=). As 暴走トラック (RUNAWAYTRUCK). And OnlyOnceRegistered. <br>
     * 暴走トラック
     */
    public void setSkillCode_Equal_暴走トラック() {
        setSkillCode_Equal_AsSkill(CDef.Skill.暴走トラック);
    }

    /**
     * Equal(=). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSkillCode_Equal_占い師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.占い師);
    }

    /**
     * Equal(=). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setSkillCode_Equal_破局者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.破局者);
    }

    /**
     * Equal(=). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setSkillCode_Equal_静狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.静狼);
    }

    /**
     * Equal(=). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setSkillCode_Equal_感覚者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * Equal(=). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSkillCode_Equal_夢遊病者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Equal(=). As 臭狼 (SMELLWOLF). And OnlyOnceRegistered. <br>
     * 臭狼
     */
    public void setSkillCode_Equal_臭狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.臭狼);
    }

    /**
     * Equal(=). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setSkillCode_Equal_防音者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.防音者);
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
     * Equal(=). As 念狐 (TELEFOX). And OnlyOnceRegistered. <br>
     * 念狐
     */
    public void setSkillCode_Equal_念狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.念狐);
    }

    /**
     * Equal(=). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setSkillCode_Equal_泥棒猫() {
        setSkillCode_Equal_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Equal(=). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setSkillCode_Equal_翻訳者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Equal(=). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSkillCode_Equal_罠師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.罠師);
    }

    /**
     * Equal(=). As 騙狐 (TRICKFOX). And OnlyOnceRegistered. <br>
     * 騙狐
     */
    public void setSkillCode_Equal_騙狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.騙狐);
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
     * Equal(=). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setSkillCode_Equal_当選者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.当選者);
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
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCode The value of skillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotEqual(String skillCode) {
        doSetSkillCode_NotEqual(fRES(skillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setSkillCode_NotEqual_教唆者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * NotEqual(&lt;&gt;). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSkillCode_NotEqual_絶対人狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 餡麺麭者 (ANPANMAN). And OnlyOnceRegistered. <br>
     * 餡麺麭者
     */
    public void setSkillCode_NotEqual_餡麺麭者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.餡麺麭者);
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
     * NotEqual(&lt;&gt;). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setSkillCode_NotEqual_バールのようなもの() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * NotEqual(&lt;&gt;). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setSkillCode_NotEqual_黒箱者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSkillCode_NotEqual_爆弾魔() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * NotEqual(&lt;&gt;). As 組長 (BOSS). And OnlyOnceRegistered. <br>
     * 組長
     */
    public void setSkillCode_NotEqual_組長() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.組長);
    }

    /**
     * NotEqual(&lt;&gt;). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSkillCode_NotEqual_誑狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 浮気者 (CHEATLOVER). And OnlyOnceRegistered. <br>
     * 浮気者
     */
    public void setSkillCode_NotEqual_浮気者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.浮気者);
    }

    /**
     * NotEqual(&lt;&gt;). As 曇天者 (CLOUDY). And OnlyOnceRegistered. <br>
     * 曇天者
     */
    public void setSkillCode_NotEqual_曇天者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.曇天者);
    }

    /**
     * NotEqual(&lt;&gt;). As 道化師 (CLOWN). And OnlyOnceRegistered. <br>
     * 道化師
     */
    public void setSkillCode_NotEqual_道化師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.道化師);
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
     * NotEqual(&lt;&gt;). As 反呪者 (CURSECOUNTER). And OnlyOnceRegistered. <br>
     * 反呪者
     */
    public void setSkillCode_NotEqual_反呪者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.反呪者);
    }

    /**
     * NotEqual(&lt;&gt;). As 呪縛者 (CURSER). And OnlyOnceRegistered. <br>
     * 呪縛者
     */
    public void setSkillCode_NotEqual_呪縛者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.呪縛者);
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
     * NotEqual(&lt;&gt;). As 興信者 (DETECTSEER). And OnlyOnceRegistered. <br>
     * 興信者
     */
    public void setSkillCode_NotEqual_興信者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.興信者);
    }

    /**
     * NotEqual(&lt;&gt;). As 剖狼 (DISSECTWOLF). And OnlyOnceRegistered. <br>
     * 剖狼
     */
    public void setSkillCode_NotEqual_剖狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.剖狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 箪笥 (DRAWERS). And OnlyOnceRegistered. <br>
     * 箪笥
     */
    public void setSkillCode_NotEqual_箪笥() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.箪笥);
    }

    /**
     * NotEqual(&lt;&gt;). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSkillCode_NotEqual_不止者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.不止者);
    }

    /**
     * NotEqual(&lt;&gt;). As 帝狼 (EMPERORWOLF). And OnlyOnceRegistered. <br>
     * 帝狼
     */
    public void setSkillCode_NotEqual_帝狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.帝狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 闇パン屋 (EVILBAKERY). And OnlyOnceRegistered. <br>
     * 闇パン屋
     */
    public void setSkillCode_NotEqual_闇パン屋() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.闇パン屋);
    }

    /**
     * NotEqual(&lt;&gt;). As 闇探偵 (EVILDETECTIVE). And OnlyOnceRegistered. <br>
     * 闇探偵
     */
    public void setSkillCode_NotEqual_闇探偵() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.闇探偵);
    }

    /**
     * NotEqual(&lt;&gt;). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSkillCode_NotEqual_魔神官() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * NotEqual(&lt;&gt;). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setSkillCode_NotEqual_執行人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.執行人);
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
     * NotEqual(&lt;&gt;). As 冷凍者 (FREEZER). And OnlyOnceRegistered. <br>
     * 冷凍者
     */
    public void setSkillCode_NotEqual_冷凍者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.冷凍者);
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
     * NotEqual(&lt;&gt;). As 濡衣者 (GUILTER). And OnlyOnceRegistered. <br>
     * 濡衣者
     */
    public void setSkillCode_NotEqual_濡衣者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.濡衣者);
    }

    /**
     * NotEqual(&lt;&gt;). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSkillCode_NotEqual_導師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.導師);
    }

    /**
     * NotEqual(&lt;&gt;). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setSkillCode_NotEqual_堅狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.堅狼);
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
     * NotEqual(&lt;&gt;). As 勇者 (HERO). And OnlyOnceRegistered. <br>
     * 勇者
     */
    public void setSkillCode_NotEqual_勇者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.勇者);
    }

    /**
     * NotEqual(&lt;&gt;). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSkillCode_NotEqual_飛狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 冷やし中華 (HIYASICHUKA). And OnlyOnceRegistered. <br>
     * 冷やし中華
     */
    public void setSkillCode_NotEqual_冷やし中華() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.冷やし中華);
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
     * NotEqual(&lt;&gt;). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setSkillCode_NotEqual_稲荷() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * NotEqual(&lt;&gt;). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSkillCode_NotEqual_煽動者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * NotEqual(&lt;&gt;). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setSkillCode_NotEqual_保険屋() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.保険屋);
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
     * NotEqual(&lt;&gt;). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setSkillCode_NotEqual_管狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.管狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setSkillCode_NotEqual_弁護士() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSkillCode_NotEqual_おまかせ() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * NotEqual(&lt;&gt;). As 伝説の殺し屋 (LEGENDASSASSIN). And OnlyOnceRegistered. <br>
     * 伝説の殺し屋
     */
    public void setSkillCode_NotEqual_伝説の殺し屋() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.伝説の殺し屋);
    }

    /**
     * NotEqual(&lt;&gt;). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setSkillCode_NotEqual_聴狂人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 共有者 (LISTENMASON). And OnlyOnceRegistered. <br>
     * 共有者
     */
    public void setSkillCode_NotEqual_共有者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.共有者);
    }

    /**
     * NotEqual(&lt;&gt;). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setSkillCode_NotEqual_黙狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.黙狼);
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
     * NotEqual(&lt;&gt;). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setSkillCode_NotEqual_マタギ() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * NotEqual(&lt;&gt;). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setSkillCode_NotEqual_市長() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.市長);
    }

    /**
     * NotEqual(&lt;&gt;). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSkillCode_NotEqual_霊能者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * NotEqual(&lt;&gt;). As 魅惑の人魚 (MERMAID). And OnlyOnceRegistered. <br>
     * 魅惑の人魚
     */
    public void setSkillCode_NotEqual_魅惑の人魚() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.魅惑の人魚);
    }

    /**
     * NotEqual(&lt;&gt;). As 耳年増 (MIMIDOSHIMA). And OnlyOnceRegistered. <br>
     * 耳年増
     */
    public void setSkillCode_NotEqual_耳年増() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.耳年増);
    }

    /**
     * NotEqual(&lt;&gt;). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSkillCode_NotEqual_死霊術師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * NotEqual(&lt;&gt;). As 夜狐 (NIGHTFOX). And OnlyOnceRegistered. <br>
     * 夜狐
     */
    public void setSkillCode_NotEqual_夜狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.夜狐);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_NotEqual_おまかせ役職窓なし() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * NotEqual(&lt;&gt;). As リア充 (NORMIE). And OnlyOnceRegistered. <br>
     * リア充
     */
    public void setSkillCode_NotEqual_リア充() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.リア充);
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
     * NotEqual(&lt;&gt;). As 全知者 (OMNISCIENCE). And OnlyOnceRegistered. <br>
     * 全知者
     */
    public void setSkillCode_NotEqual_全知者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.全知者);
    }

    /**
     * NotEqual(&lt;&gt;). As 陰陽師 (ONMYOJI). And OnlyOnceRegistered. <br>
     * 陰陽師
     */
    public void setSkillCode_NotEqual_陰陽師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.陰陽師);
    }

    /**
     * NotEqual(&lt;&gt;). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSkillCode_NotEqual_梟() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.梟);
    }

    /**
     * NotEqual(&lt;&gt;). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setSkillCode_NotEqual_牧師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.牧師);
    }

    /**
     * NotEqual(&lt;&gt;). As 海王者 (POSEIDON). And OnlyOnceRegistered. <br>
     * 海王者
     */
    public void setSkillCode_NotEqual_海王者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.海王者);
    }

    /**
     * NotEqual(&lt;&gt;). As 画鋲 (PUSHPIN). And OnlyOnceRegistered. <br>
     * 画鋲
     */
    public void setSkillCode_NotEqual_画鋲() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.画鋲);
    }

    /**
     * NotEqual(&lt;&gt;). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSkillCode_NotEqual_虹職人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * NotEqual(&lt;&gt;). As 暴狼 (RAMPAGEWOLF). And OnlyOnceRegistered. <br>
     * 暴狼
     */
    public void setSkillCode_NotEqual_暴狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.暴狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSkillCode_NotEqual_転生者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.転生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 覚者 (REMEMBERSEER). And OnlyOnceRegistered. <br>
     * 覚者
     */
    public void setSkillCode_NotEqual_覚者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.覚者);
    }

    /**
     * NotEqual(&lt;&gt;). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setSkillCode_NotEqual_怨恨者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * NotEqual(&lt;&gt;). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSkillCode_NotEqual_蘇生者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 革命者 (REVOLUTIONARY). And OnlyOnceRegistered. <br>
     * 革命者
     */
    public void setSkillCode_NotEqual_革命者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.革命者);
    }

    /**
     * NotEqual(&lt;&gt;). As 王族 (ROYALTY). And OnlyOnceRegistered. <br>
     * 王族
     */
    public void setSkillCode_NotEqual_王族() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.王族);
    }

    /**
     * NotEqual(&lt;&gt;). As 暴走トラック (RUNAWAYTRUCK). And OnlyOnceRegistered. <br>
     * 暴走トラック
     */
    public void setSkillCode_NotEqual_暴走トラック() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.暴走トラック);
    }

    /**
     * NotEqual(&lt;&gt;). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSkillCode_NotEqual_占い師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setSkillCode_NotEqual_破局者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.破局者);
    }

    /**
     * NotEqual(&lt;&gt;). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setSkillCode_NotEqual_静狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.静狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setSkillCode_NotEqual_感覚者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * NotEqual(&lt;&gt;). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSkillCode_NotEqual_夢遊病者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * NotEqual(&lt;&gt;). As 臭狼 (SMELLWOLF). And OnlyOnceRegistered. <br>
     * 臭狼
     */
    public void setSkillCode_NotEqual_臭狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.臭狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setSkillCode_NotEqual_防音者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.防音者);
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
     * NotEqual(&lt;&gt;). As 念狐 (TELEFOX). And OnlyOnceRegistered. <br>
     * 念狐
     */
    public void setSkillCode_NotEqual_念狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.念狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setSkillCode_NotEqual_泥棒猫() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * NotEqual(&lt;&gt;). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setSkillCode_NotEqual_翻訳者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSkillCode_NotEqual_罠師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.罠師);
    }

    /**
     * NotEqual(&lt;&gt;). As 騙狐 (TRICKFOX). And OnlyOnceRegistered. <br>
     * 騙狐
     */
    public void setSkillCode_NotEqual_騙狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.騙狐);
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
     * NotEqual(&lt;&gt;). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setSkillCode_NotEqual_当選者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.当選者);
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
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCodeList The collection of skillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_InScope(Collection<String> skillCodeList) {
        doSetSkillCode_InScope(skillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill} <br>
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
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 臭狼, 帝狼, 剖狼, C国狂人]
     */
    public void setSkillCode_InScope_AvailableWerewolfSay() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, C国狂人, 聴狂人]
     */
    public void setSkillCode_InScope_ViewableWerewolfSay() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 興信者, 管狐]
     */
    public void setSkillCode_InScope_HasDivineAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     */
    public void setSkillCode_InScope_HasSkillPsychicAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼]
     */
    public void setSkillCode_InScope_HasAttackAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 夜狐, 背徳者]
     */
    public void setSkillCode_InScope_HasDisturbAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 爆弾魔]
     */
    public void setSkillCode_InScope_NoDeadByAttack() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, 暴狼]
     */
    public void setSkillCode_InScope_WolfCount() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfWolfCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 騙狐, 夜狐, 念狐, 梟]
     */
    public void setSkillCode_InScope_NoCount() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfNoCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, C国狂人, 狂信者, 煽動者]
     */
    public void setSkillCode_InScope_ViewableWolfCharaName() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, 暴狼, 一匹狼]
     */
    public void setSkillCode_InScope_DivineResultWolf() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfDivineResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 臭狼, 帝狼, 剖狼, 暴狼, 一匹狼]
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
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCodeList The collection of skillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotInScope(Collection<String> skillCodeList) {
        doSetSkillCode_NotInScope(skillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill} <br>
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
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSkillCode_IsNull() { regSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSkillCode_IsNotNull() { regSkillCode(CK_ISNN, DOBJ); }

    protected void regSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueSkillCode(), "SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueSkillCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNum The value of minNum as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMinNum_Equal(Integer minNum) {
        doSetMinNum_Equal(minNum);
    }

    protected void doSetMinNum_Equal(Integer minNum) {
        regMinNum(CK_EQ, minNum);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNum The value of minNum as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMinNum_NotEqual(Integer minNum) {
        doSetMinNum_NotEqual(minNum);
    }

    protected void doSetMinNum_NotEqual(Integer minNum) {
        regMinNum(CK_NES, minNum);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNum The value of minNum as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMinNum_GreaterThan(Integer minNum) {
        regMinNum(CK_GT, minNum);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNum The value of minNum as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMinNum_LessThan(Integer minNum) {
        regMinNum(CK_LT, minNum);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNum The value of minNum as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMinNum_GreaterEqual(Integer minNum) {
        regMinNum(CK_GE, minNum);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNum The value of minNum as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMinNum_LessEqual(Integer minNum) {
        regMinNum(CK_LE, minNum);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of minNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of minNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMinNum_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMinNum_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of minNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of minNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMinNum_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMinNum(), "MIN_NUM", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumList The collection of minNum as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMinNum_InScope(Collection<Integer> minNumList) {
        doSetMinNum_InScope(minNumList);
    }

    protected void doSetMinNum_InScope(Collection<Integer> minNumList) {
        regINS(CK_INS, cTL(minNumList), xgetCValueMinNum(), "MIN_NUM");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumList The collection of minNum as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMinNum_NotInScope(Collection<Integer> minNumList) {
        doSetMinNum_NotInScope(minNumList);
    }

    protected void doSetMinNum_NotInScope(Collection<Integer> minNumList) {
        regINS(CK_NINS, cTL(minNumList), xgetCValueMinNum(), "MIN_NUM");
    }

    protected void regMinNum(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMinNum(), "MIN_NUM"); }
    protected abstract ConditionValue xgetCValueMinNum();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNum The value of maxNum as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMaxNum_Equal(Integer maxNum) {
        doSetMaxNum_Equal(maxNum);
    }

    protected void doSetMaxNum_Equal(Integer maxNum) {
        regMaxNum(CK_EQ, maxNum);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNum The value of maxNum as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMaxNum_NotEqual(Integer maxNum) {
        doSetMaxNum_NotEqual(maxNum);
    }

    protected void doSetMaxNum_NotEqual(Integer maxNum) {
        regMaxNum(CK_NES, maxNum);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNum The value of maxNum as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMaxNum_GreaterThan(Integer maxNum) {
        regMaxNum(CK_GT, maxNum);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNum The value of maxNum as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMaxNum_LessThan(Integer maxNum) {
        regMaxNum(CK_LT, maxNum);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNum The value of maxNum as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMaxNum_GreaterEqual(Integer maxNum) {
        regMaxNum(CK_GE, maxNum);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNum The value of maxNum as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMaxNum_LessEqual(Integer maxNum) {
        regMaxNum(CK_LE, maxNum);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param minNumber The min number of maxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of maxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMaxNum_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMaxNum_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param minNumber The min number of maxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of maxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMaxNum_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMaxNum(), "MAX_NUM", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNumList The collection of maxNum as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMaxNum_InScope(Collection<Integer> maxNumList) {
        doSetMaxNum_InScope(maxNumList);
    }

    protected void doSetMaxNum_InScope(Collection<Integer> maxNumList) {
        regINS(CK_INS, cTL(maxNumList), xgetCValueMaxNum(), "MAX_NUM");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     * @param maxNumList The collection of maxNum as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMaxNum_NotInScope(Collection<Integer> maxNumList) {
        doSetMaxNum_NotInScope(maxNumList);
    }

    protected void doSetMaxNum_NotInScope(Collection<Integer> maxNumList) {
        regINS(CK_NINS, cTL(maxNumList), xgetCValueMaxNum(), "MAX_NUM");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     */
    public void setMaxNum_IsNull() { regMaxNum(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * MAX_NUM: {INT UNSIGNED(10)}
     */
    public void setMaxNum_IsNotNull() { regMaxNum(CK_ISNN, DOBJ); }

    protected void regMaxNum(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMaxNum(), "MAX_NUM"); }
    protected abstract ConditionValue xgetCValueMaxNum();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocation The value of allocation as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setAllocation_Equal(Integer allocation) {
        doSetAllocation_Equal(allocation);
    }

    protected void doSetAllocation_Equal(Integer allocation) {
        regAllocation(CK_EQ, allocation);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocation The value of allocation as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setAllocation_NotEqual(Integer allocation) {
        doSetAllocation_NotEqual(allocation);
    }

    protected void doSetAllocation_NotEqual(Integer allocation) {
        regAllocation(CK_NES, allocation);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocation The value of allocation as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setAllocation_GreaterThan(Integer allocation) {
        regAllocation(CK_GT, allocation);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocation The value of allocation as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setAllocation_LessThan(Integer allocation) {
        regAllocation(CK_LT, allocation);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocation The value of allocation as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setAllocation_GreaterEqual(Integer allocation) {
        regAllocation(CK_GE, allocation);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocation The value of allocation as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setAllocation_LessEqual(Integer allocation) {
        regAllocation(CK_LE, allocation);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of allocation. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of allocation. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setAllocation_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setAllocation_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of allocation. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of allocation. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setAllocation_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueAllocation(), "ALLOCATION", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocationList The collection of allocation as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAllocation_InScope(Collection<Integer> allocationList) {
        doSetAllocation_InScope(allocationList);
    }

    protected void doSetAllocation_InScope(Collection<Integer> allocationList) {
        regINS(CK_INS, cTL(allocationList), xgetCValueAllocation(), "ALLOCATION");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @param allocationList The collection of allocation as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAllocation_NotInScope(Collection<Integer> allocationList) {
        doSetAllocation_NotInScope(allocationList);
    }

    protected void doSetAllocation_NotInScope(Collection<Integer> allocationList) {
        regINS(CK_NINS, cTL(allocationList), xgetCValueAllocation(), "ALLOCATION");
    }

    protected void regAllocation(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueAllocation(), "ALLOCATION"); }
    protected abstract ConditionValue xgetCValueAllocation();

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
    public HpSLCFunction<SkillAllocationCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, SkillAllocationCB.class);
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
    public HpSLCFunction<SkillAllocationCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, SkillAllocationCB.class);
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
    public HpSLCFunction<SkillAllocationCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, SkillAllocationCB.class);
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
    public HpSLCFunction<SkillAllocationCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, SkillAllocationCB.class);
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
    public HpSLCFunction<SkillAllocationCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, SkillAllocationCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;SkillAllocationCB&gt;() {
     *     public void query(SkillAllocationCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillAllocationCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, SkillAllocationCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillAllocationCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(SkillAllocationCQ sq);

    protected SkillAllocationCB xcreateScalarConditionCB() {
        SkillAllocationCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected SkillAllocationCB xcreateScalarConditionPartitionByCB() {
        SkillAllocationCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
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
    protected SkillAllocationCB newMyCB() {
        return new SkillAllocationCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return SkillAllocationCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
