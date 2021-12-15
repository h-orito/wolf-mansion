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
 * The abstract condition-query of ABILITY.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsAbilityCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsAbilityCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "ABILITY";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     */
    public void setVillageId_IsNull() { regVillageId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     */
    public void setVillageId_IsNotNull() { regVillageId(CK_ISNN, DOBJ); }

    protected void regVillageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageId(), "VILLAGE_ID"); }
    protected abstract ConditionValue xgetCValueVillageId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_Equal(Integer day) {
        doSetDay_Equal(day);
    }

    protected void doSetDay_Equal(Integer day) {
        regDay(CK_EQ, day);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_NotEqual(Integer day) {
        doSetDay_NotEqual(day);
    }

    protected void doSetDay_NotEqual(Integer day) {
        regDay(CK_NES, day);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_GreaterThan(Integer day) {
        regDay(CK_GT, day);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_LessThan(Integer day) {
        regDay(CK_LT, day);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_GreaterEqual(Integer day) {
        regDay(CK_GE, day);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_LessEqual(Integer day) {
        regDay(CK_LE, day);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param minNumber The min number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDay_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDay_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param minNumber The min number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDay_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDay(), "DAY", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param dayList The collection of day as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDay_InScope(Collection<Integer> dayList) {
        doSetDay_InScope(dayList);
    }

    protected void doSetDay_InScope(Collection<Integer> dayList) {
        regINS(CK_INS, cTL(dayList), xgetCValueDay(), "DAY");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param dayList The collection of day as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDay_NotInScope(Collection<Integer> dayList) {
        doSetDay_NotInScope(dayList);
    }

    protected void doSetDay_NotInScope(Collection<Integer> dayList) {
        regINS(CK_NINS, cTL(dayList), xgetCValueDay(), "DAY");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     */
    public void setDay_IsNull() { regDay(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     */
    public void setDay_IsNotNull() { regDay(CK_ISNN, DOBJ); }

    protected void regDay(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDay(), "DAY"); }
    protected abstract ConditionValue xgetCValueDay();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_Equal(Integer charaId) {
        doSetCharaId_Equal(charaId);
    }

    protected void doSetCharaId_Equal(Integer charaId) {
        regCharaId(CK_EQ, charaId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_NotEqual(Integer charaId) {
        doSetCharaId_NotEqual(charaId);
    }

    protected void doSetCharaId_NotEqual(Integer charaId) {
        regCharaId(CK_NES, charaId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterThan(Integer charaId) {
        regCharaId(CK_GT, charaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessThan(Integer charaId) {
        regCharaId(CK_LT, charaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterEqual(Integer charaId) {
        regCharaId(CK_GE, charaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessEqual(Integer charaId) {
        regCharaId(CK_LE, charaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param minNumber The min number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setCharaId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setCharaId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param minNumber The min number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueCharaId(), "CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaIdList The collection of charaId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaId_InScope(Collection<Integer> charaIdList) {
        doSetCharaId_InScope(charaIdList);
    }

    protected void doSetCharaId_InScope(Collection<Integer> charaIdList) {
        regINS(CK_INS, cTL(charaIdList), xgetCValueCharaId(), "CHARA_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaIdList The collection of charaId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaId_NotInScope(Collection<Integer> charaIdList) {
        doSetCharaId_NotInScope(charaIdList);
    }

    protected void doSetCharaId_NotInScope(Collection<Integer> charaIdList) {
        regINS(CK_NINS, cTL(charaIdList), xgetCValueCharaId(), "CHARA_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     */
    public void setCharaId_IsNull() { regCharaId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * CHARA_ID: {PK, IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     */
    public void setCharaId_IsNotNull() { regCharaId(CK_ISNN, DOBJ); }

    protected void regCharaId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaId(), "CHARA_ID"); }
    protected abstract ConditionValue xgetCValueCharaId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaId The value of targetCharaId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setTargetCharaId_Equal(Integer targetCharaId) {
        doSetTargetCharaId_Equal(targetCharaId);
    }

    protected void doSetTargetCharaId_Equal(Integer targetCharaId) {
        regTargetCharaId(CK_EQ, targetCharaId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaId The value of targetCharaId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setTargetCharaId_NotEqual(Integer targetCharaId) {
        doSetTargetCharaId_NotEqual(targetCharaId);
    }

    protected void doSetTargetCharaId_NotEqual(Integer targetCharaId) {
        regTargetCharaId(CK_NES, targetCharaId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaId The value of targetCharaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setTargetCharaId_GreaterThan(Integer targetCharaId) {
        regTargetCharaId(CK_GT, targetCharaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaId The value of targetCharaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setTargetCharaId_LessThan(Integer targetCharaId) {
        regTargetCharaId(CK_LT, targetCharaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaId The value of targetCharaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setTargetCharaId_GreaterEqual(Integer targetCharaId) {
        regTargetCharaId(CK_GE, targetCharaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaId The value of targetCharaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setTargetCharaId_LessEqual(Integer targetCharaId) {
        regTargetCharaId(CK_LE, targetCharaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param minNumber The min number of targetCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of targetCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setTargetCharaId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setTargetCharaId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param minNumber The min number of targetCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of targetCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setTargetCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueTargetCharaId(), "TARGET_CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaIdList The collection of targetCharaId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetCharaId_InScope(Collection<Integer> targetCharaIdList) {
        doSetTargetCharaId_InScope(targetCharaIdList);
    }

    protected void doSetTargetCharaId_InScope(Collection<Integer> targetCharaIdList) {
        regINS(CK_INS, cTL(targetCharaIdList), xgetCValueTargetCharaId(), "TARGET_CHARA_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     * @param targetCharaIdList The collection of targetCharaId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetCharaId_NotInScope(Collection<Integer> targetCharaIdList) {
        doSetTargetCharaId_NotInScope(targetCharaIdList);
    }

    protected void doSetTargetCharaId_NotInScope(Collection<Integer> targetCharaIdList) {
        regINS(CK_NINS, cTL(targetCharaIdList), xgetCValueTargetCharaId(), "TARGET_CHARA_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     */
    public void setTargetCharaId_IsNull() { regTargetCharaId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TARGET_CHARA_ID: {IX, INT UNSIGNED(10), FK to CHARA}
     */
    public void setTargetCharaId_IsNotNull() { regTargetCharaId(CK_ISNN, DOBJ); }

    protected void regTargetCharaId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTargetCharaId(), "TARGET_CHARA_ID"); }
    protected abstract ConditionValue xgetCValueTargetCharaId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_Equal(String targetFootstep) {
        doSetTargetFootstep_Equal(fRES(targetFootstep));
    }

    protected void doSetTargetFootstep_Equal(String targetFootstep) {
        regTargetFootstep(CK_EQ, targetFootstep);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_NotEqual(String targetFootstep) {
        doSetTargetFootstep_NotEqual(fRES(targetFootstep));
    }

    protected void doSetTargetFootstep_NotEqual(String targetFootstep) {
        regTargetFootstep(CK_NES, targetFootstep);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_GreaterThan(String targetFootstep) {
        regTargetFootstep(CK_GT, fRES(targetFootstep));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_LessThan(String targetFootstep) {
        regTargetFootstep(CK_LT, fRES(targetFootstep));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_GreaterEqual(String targetFootstep) {
        regTargetFootstep(CK_GE, fRES(targetFootstep));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_LessEqual(String targetFootstep) {
        regTargetFootstep(CK_LE, fRES(targetFootstep));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstepList The collection of targetFootstep as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_InScope(Collection<String> targetFootstepList) {
        doSetTargetFootstep_InScope(targetFootstepList);
    }

    protected void doSetTargetFootstep_InScope(Collection<String> targetFootstepList) {
        regINS(CK_INS, cTL(targetFootstepList), xgetCValueTargetFootstep(), "TARGET_FOOTSTEP");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstepList The collection of targetFootstep as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setTargetFootstep_NotInScope(Collection<String> targetFootstepList) {
        doSetTargetFootstep_NotInScope(targetFootstepList);
    }

    protected void doSetTargetFootstep_NotInScope(Collection<String> targetFootstepList) {
        regINS(CK_NINS, cTL(targetFootstepList), xgetCValueTargetFootstep(), "TARGET_FOOTSTEP");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)} <br>
     * <pre>e.g. setTargetFootstep_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param targetFootstep The value of targetFootstep as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTargetFootstep_LikeSearch(String targetFootstep, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTargetFootstep_LikeSearch(targetFootstep, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)} <br>
     * <pre>e.g. setTargetFootstep_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param targetFootstep The value of targetFootstep as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setTargetFootstep_LikeSearch(String targetFootstep, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(targetFootstep), xgetCValueTargetFootstep(), "TARGET_FOOTSTEP", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setTargetFootstep_NotLikeSearch(String targetFootstep, ConditionOptionCall<LikeSearchOption> opLambda) {
        setTargetFootstep_NotLikeSearch(targetFootstep, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @param targetFootstep The value of targetFootstep as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setTargetFootstep_NotLikeSearch(String targetFootstep, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(targetFootstep), xgetCValueTargetFootstep(), "TARGET_FOOTSTEP", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     */
    public void setTargetFootstep_IsNull() { regTargetFootstep(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     */
    public void setTargetFootstep_IsNullOrEmpty() { regTargetFootstep(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     */
    public void setTargetFootstep_IsNotNull() { regTargetFootstep(CK_ISNN, DOBJ); }

    protected void regTargetFootstep(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueTargetFootstep(), "TARGET_FOOTSTEP"); }
    protected abstract ConditionValue xgetCValueTargetFootstep();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     * @param abilityTypeCode The value of abilityTypeCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_Equal(String abilityTypeCode) {
        doSetAbilityTypeCode_Equal(fRES(abilityTypeCode));
    }

    /**
     * Equal(=). As AbilityType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType cdef) {
        doSetAbilityTypeCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 襲撃 (ATTACK). And OnlyOnceRegistered. <br>
     * 襲撃
     */
    public void setAbilityTypeCode_Equal_襲撃() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.襲撃);
    }

    /**
     * Equal(=). As ババを渡す (BABAGIVE). And OnlyOnceRegistered. <br>
     * ババを渡す
     */
    public void setAbilityTypeCode_Equal_ババを渡す() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.ババを渡す);
    }

    /**
     * Equal(=). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setAbilityTypeCode_Equal_美人局() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.美人局);
    }

    /**
     * Equal(=). As 殴打 (BEAT). And OnlyOnceRegistered. <br>
     * 殴打
     */
    public void setAbilityTypeCode_Equal_殴打() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.殴打);
    }

    /**
     * Equal(=). As 爆弾設置 (BOMB). And OnlyOnceRegistered. <br>
     * 爆弾設置
     */
    public void setAbilityTypeCode_Equal_爆弾設置() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.爆弾設置);
    }

    /**
     * Equal(=). As 破局 (BREAKUP). And OnlyOnceRegistered. <br>
     * 破局
     */
    public void setAbilityTypeCode_Equal_破局() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.破局);
    }

    /**
     * Equal(=). As 誑かす (CHEAT). And OnlyOnceRegistered. <br>
     * 誑かす
     */
    public void setAbilityTypeCode_Equal_誑かす() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.誑かす);
    }

    /**
     * Equal(=). As 同棲 (COHABIT). And OnlyOnceRegistered. <br>
     * 同棲
     */
    public void setAbilityTypeCode_Equal_同棲() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.同棲);
    }

    /**
     * Equal(=). As 指揮 (COMMAND). And OnlyOnceRegistered. <br>
     * 指揮
     */
    public void setAbilityTypeCode_Equal_指揮() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.指揮);
    }

    /**
     * Equal(=). As 求愛 (COURT). And OnlyOnceRegistered. <br>
     * 求愛
     */
    public void setAbilityTypeCode_Equal_求愛() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.求愛);
    }

    /**
     * Equal(=). As 占い (DIVINE). And OnlyOnceRegistered. <br>
     * 占い
     */
    public void setAbilityTypeCode_Equal_占い() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.占い);
    }

    /**
     * Equal(=). As 強制転生 (FORCE_REINCARNATION). And OnlyOnceRegistered. <br>
     * 強制転生
     */
    public void setAbilityTypeCode_Equal_強制転生() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.強制転生);
    }

    /**
     * Equal(=). As フルーツバスケット (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * フルーツバスケット
     */
    public void setAbilityTypeCode_Equal_フルーツバスケット() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.フルーツバスケット);
    }

    /**
     * Equal(=). As 護衛 (GUARD). And OnlyOnceRegistered. <br>
     * 護衛
     */
    public void setAbilityTypeCode_Equal_護衛() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.護衛);
    }

    /**
     * Equal(=). As 隠蔽 (HIDE). And OnlyOnceRegistered. <br>
     * 隠蔽
     */
    public void setAbilityTypeCode_Equal_隠蔽() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.隠蔽);
    }

    /**
     * Equal(=). As 狩猟 (HUNTING). And OnlyOnceRegistered. <br>
     * 狩猟
     */
    public void setAbilityTypeCode_Equal_狩猟() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.狩猟);
    }

    /**
     * Equal(=). As 煽動 (INSTIGATE). And OnlyOnceRegistered. <br>
     * 煽動
     */
    public void setAbilityTypeCode_Equal_煽動() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.煽動);
    }

    /**
     * Equal(=). As 保険 (INSURANCE). And OnlyOnceRegistered. <br>
     * 保険
     */
    public void setAbilityTypeCode_Equal_保険() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.保険);
    }

    /**
     * Equal(=). As 捜査 (INVESTIGATE). And OnlyOnceRegistered. <br>
     * 捜査
     */
    public void setAbilityTypeCode_Equal_捜査() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.捜査);
    }

    /**
     * Equal(=). As 単独襲撃 (LONEATTACK). And OnlyOnceRegistered. <br>
     * 単独襲撃
     */
    public void setAbilityTypeCode_Equal_単独襲撃() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.単独襲撃);
    }

    /**
     * Equal(=). As 拡声 (LOUDSPEAK). And OnlyOnceRegistered. <br>
     * 拡声
     */
    public void setAbilityTypeCode_Equal_拡声() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.拡声);
    }

    /**
     * Equal(=). As 恋泥棒 (LOVESTEAL). And OnlyOnceRegistered. <br>
     * 恋泥棒
     */
    public void setAbilityTypeCode_Equal_恋泥棒() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.恋泥棒);
    }

    /**
     * Equal(=). As 死霊蘇生 (NECROMANCE). And OnlyOnceRegistered. <br>
     * 死霊蘇生
     */
    public void setAbilityTypeCode_Equal_死霊蘇生() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.死霊蘇生);
    }

    /**
     * Equal(=). As 虹塗り (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹塗り
     */
    public void setAbilityTypeCode_Equal_虹塗り() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.虹塗り);
    }

    /**
     * Equal(=). As 蘇生 (RESUSCITATE). And OnlyOnceRegistered. <br>
     * 蘇生
     */
    public void setAbilityTypeCode_Equal_蘇生() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.蘇生);
    }

    /**
     * Equal(=). As 誘惑 (SEDUCE). And OnlyOnceRegistered. <br>
     * 誘惑
     */
    public void setAbilityTypeCode_Equal_誘惑() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.誘惑);
    }

    /**
     * Equal(=). As 叫び (SHOUT). And OnlyOnceRegistered. <br>
     * 叫び
     */
    public void setAbilityTypeCode_Equal_叫び() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.叫び);
    }

    /**
     * Equal(=). As ストーキング (STALKING). And OnlyOnceRegistered. <br>
     * ストーキング
     */
    public void setAbilityTypeCode_Equal_ストーキング() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.ストーキング);
    }

    /**
     * Equal(=). As 翻訳 (TRANSLATE). And OnlyOnceRegistered. <br>
     * 翻訳
     */
    public void setAbilityTypeCode_Equal_翻訳() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.翻訳);
    }

    /**
     * Equal(=). As 罠設置 (TRAP). And OnlyOnceRegistered. <br>
     * 罠設置
     */
    public void setAbilityTypeCode_Equal_罠設置() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.罠設置);
    }

    /**
     * Equal(=). As 壁殴り (WALLPUNCH). And OnlyOnceRegistered. <br>
     * 壁殴り
     */
    public void setAbilityTypeCode_Equal_壁殴り() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.壁殴り);
    }

    /**
     * Equal(=). As 風来護衛 (WANDERERGUARD). And OnlyOnceRegistered. <br>
     * 風来護衛
     */
    public void setAbilityTypeCode_Equal_風来護衛() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.風来護衛);
    }

    /**
     * Equal(=). As 指差死 (YUBISASHI). And OnlyOnceRegistered. <br>
     * 指差死
     */
    public void setAbilityTypeCode_Equal_指差死() {
        setAbilityTypeCode_Equal_AsAbilityType(CDef.AbilityType.指差死);
    }

    protected void doSetAbilityTypeCode_Equal(String abilityTypeCode) {
        regAbilityTypeCode(CK_EQ, abilityTypeCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     * @param abilityTypeCode The value of abilityTypeCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_NotEqual(String abilityTypeCode) {
        doSetAbilityTypeCode_NotEqual(fRES(abilityTypeCode));
    }

    /**
     * NotEqual(&lt;&gt;). As AbilityType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType cdef) {
        doSetAbilityTypeCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 襲撃 (ATTACK). And OnlyOnceRegistered. <br>
     * 襲撃
     */
    public void setAbilityTypeCode_NotEqual_襲撃() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.襲撃);
    }

    /**
     * NotEqual(&lt;&gt;). As ババを渡す (BABAGIVE). And OnlyOnceRegistered. <br>
     * ババを渡す
     */
    public void setAbilityTypeCode_NotEqual_ババを渡す() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.ババを渡す);
    }

    /**
     * NotEqual(&lt;&gt;). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setAbilityTypeCode_NotEqual_美人局() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.美人局);
    }

    /**
     * NotEqual(&lt;&gt;). As 殴打 (BEAT). And OnlyOnceRegistered. <br>
     * 殴打
     */
    public void setAbilityTypeCode_NotEqual_殴打() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.殴打);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆弾設置 (BOMB). And OnlyOnceRegistered. <br>
     * 爆弾設置
     */
    public void setAbilityTypeCode_NotEqual_爆弾設置() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.爆弾設置);
    }

    /**
     * NotEqual(&lt;&gt;). As 破局 (BREAKUP). And OnlyOnceRegistered. <br>
     * 破局
     */
    public void setAbilityTypeCode_NotEqual_破局() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.破局);
    }

    /**
     * NotEqual(&lt;&gt;). As 誑かす (CHEAT). And OnlyOnceRegistered. <br>
     * 誑かす
     */
    public void setAbilityTypeCode_NotEqual_誑かす() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.誑かす);
    }

    /**
     * NotEqual(&lt;&gt;). As 同棲 (COHABIT). And OnlyOnceRegistered. <br>
     * 同棲
     */
    public void setAbilityTypeCode_NotEqual_同棲() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.同棲);
    }

    /**
     * NotEqual(&lt;&gt;). As 指揮 (COMMAND). And OnlyOnceRegistered. <br>
     * 指揮
     */
    public void setAbilityTypeCode_NotEqual_指揮() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.指揮);
    }

    /**
     * NotEqual(&lt;&gt;). As 求愛 (COURT). And OnlyOnceRegistered. <br>
     * 求愛
     */
    public void setAbilityTypeCode_NotEqual_求愛() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.求愛);
    }

    /**
     * NotEqual(&lt;&gt;). As 占い (DIVINE). And OnlyOnceRegistered. <br>
     * 占い
     */
    public void setAbilityTypeCode_NotEqual_占い() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.占い);
    }

    /**
     * NotEqual(&lt;&gt;). As 強制転生 (FORCE_REINCARNATION). And OnlyOnceRegistered. <br>
     * 強制転生
     */
    public void setAbilityTypeCode_NotEqual_強制転生() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.強制転生);
    }

    /**
     * NotEqual(&lt;&gt;). As フルーツバスケット (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * フルーツバスケット
     */
    public void setAbilityTypeCode_NotEqual_フルーツバスケット() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.フルーツバスケット);
    }

    /**
     * NotEqual(&lt;&gt;). As 護衛 (GUARD). And OnlyOnceRegistered. <br>
     * 護衛
     */
    public void setAbilityTypeCode_NotEqual_護衛() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.護衛);
    }

    /**
     * NotEqual(&lt;&gt;). As 隠蔽 (HIDE). And OnlyOnceRegistered. <br>
     * 隠蔽
     */
    public void setAbilityTypeCode_NotEqual_隠蔽() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.隠蔽);
    }

    /**
     * NotEqual(&lt;&gt;). As 狩猟 (HUNTING). And OnlyOnceRegistered. <br>
     * 狩猟
     */
    public void setAbilityTypeCode_NotEqual_狩猟() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.狩猟);
    }

    /**
     * NotEqual(&lt;&gt;). As 煽動 (INSTIGATE). And OnlyOnceRegistered. <br>
     * 煽動
     */
    public void setAbilityTypeCode_NotEqual_煽動() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.煽動);
    }

    /**
     * NotEqual(&lt;&gt;). As 保険 (INSURANCE). And OnlyOnceRegistered. <br>
     * 保険
     */
    public void setAbilityTypeCode_NotEqual_保険() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.保険);
    }

    /**
     * NotEqual(&lt;&gt;). As 捜査 (INVESTIGATE). And OnlyOnceRegistered. <br>
     * 捜査
     */
    public void setAbilityTypeCode_NotEqual_捜査() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.捜査);
    }

    /**
     * NotEqual(&lt;&gt;). As 単独襲撃 (LONEATTACK). And OnlyOnceRegistered. <br>
     * 単独襲撃
     */
    public void setAbilityTypeCode_NotEqual_単独襲撃() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.単独襲撃);
    }

    /**
     * NotEqual(&lt;&gt;). As 拡声 (LOUDSPEAK). And OnlyOnceRegistered. <br>
     * 拡声
     */
    public void setAbilityTypeCode_NotEqual_拡声() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.拡声);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋泥棒 (LOVESTEAL). And OnlyOnceRegistered. <br>
     * 恋泥棒
     */
    public void setAbilityTypeCode_NotEqual_恋泥棒() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.恋泥棒);
    }

    /**
     * NotEqual(&lt;&gt;). As 死霊蘇生 (NECROMANCE). And OnlyOnceRegistered. <br>
     * 死霊蘇生
     */
    public void setAbilityTypeCode_NotEqual_死霊蘇生() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.死霊蘇生);
    }

    /**
     * NotEqual(&lt;&gt;). As 虹塗り (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹塗り
     */
    public void setAbilityTypeCode_NotEqual_虹塗り() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.虹塗り);
    }

    /**
     * NotEqual(&lt;&gt;). As 蘇生 (RESUSCITATE). And OnlyOnceRegistered. <br>
     * 蘇生
     */
    public void setAbilityTypeCode_NotEqual_蘇生() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.蘇生);
    }

    /**
     * NotEqual(&lt;&gt;). As 誘惑 (SEDUCE). And OnlyOnceRegistered. <br>
     * 誘惑
     */
    public void setAbilityTypeCode_NotEqual_誘惑() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.誘惑);
    }

    /**
     * NotEqual(&lt;&gt;). As 叫び (SHOUT). And OnlyOnceRegistered. <br>
     * 叫び
     */
    public void setAbilityTypeCode_NotEqual_叫び() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.叫び);
    }

    /**
     * NotEqual(&lt;&gt;). As ストーキング (STALKING). And OnlyOnceRegistered. <br>
     * ストーキング
     */
    public void setAbilityTypeCode_NotEqual_ストーキング() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.ストーキング);
    }

    /**
     * NotEqual(&lt;&gt;). As 翻訳 (TRANSLATE). And OnlyOnceRegistered. <br>
     * 翻訳
     */
    public void setAbilityTypeCode_NotEqual_翻訳() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.翻訳);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠設置 (TRAP). And OnlyOnceRegistered. <br>
     * 罠設置
     */
    public void setAbilityTypeCode_NotEqual_罠設置() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.罠設置);
    }

    /**
     * NotEqual(&lt;&gt;). As 壁殴り (WALLPUNCH). And OnlyOnceRegistered. <br>
     * 壁殴り
     */
    public void setAbilityTypeCode_NotEqual_壁殴り() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.壁殴り);
    }

    /**
     * NotEqual(&lt;&gt;). As 風来護衛 (WANDERERGUARD). And OnlyOnceRegistered. <br>
     * 風来護衛
     */
    public void setAbilityTypeCode_NotEqual_風来護衛() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.風来護衛);
    }

    /**
     * NotEqual(&lt;&gt;). As 指差死 (YUBISASHI). And OnlyOnceRegistered. <br>
     * 指差死
     */
    public void setAbilityTypeCode_NotEqual_指差死() {
        setAbilityTypeCode_NotEqual_AsAbilityType(CDef.AbilityType.指差死);
    }

    protected void doSetAbilityTypeCode_NotEqual(String abilityTypeCode) {
        regAbilityTypeCode(CK_NES, abilityTypeCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     * @param abilityTypeCodeList The collection of abilityTypeCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_InScope(Collection<String> abilityTypeCodeList) {
        doSetAbilityTypeCode_InScope(abilityTypeCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As AbilityType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_InScope_AsAbilityType(Collection<CDef.AbilityType> cdefList) {
        doSetAbilityTypeCode_InScope(cTStrL(cdefList));
    }

    protected void doSetAbilityTypeCode_InScope(Collection<String> abilityTypeCodeList) {
        regINS(CK_INS, cTL(abilityTypeCodeList), xgetCValueAbilityTypeCode(), "ABILITY_TYPE_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     * @param abilityTypeCodeList The collection of abilityTypeCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setAbilityTypeCode_NotInScope(Collection<String> abilityTypeCodeList) {
        doSetAbilityTypeCode_NotInScope(abilityTypeCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As AbilityType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType} <br>
     * 能力種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setAbilityTypeCode_NotInScope_AsAbilityType(Collection<CDef.AbilityType> cdefList) {
        doSetAbilityTypeCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetAbilityTypeCode_NotInScope(Collection<String> abilityTypeCodeList) {
        regINS(CK_NINS, cTL(abilityTypeCodeList), xgetCValueAbilityTypeCode(), "ABILITY_TYPE_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     */
    public void setAbilityTypeCode_IsNull() { regAbilityTypeCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     */
    public void setAbilityTypeCode_IsNotNull() { regAbilityTypeCode(CK_ISNN, DOBJ); }

    protected void regAbilityTypeCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueAbilityTypeCode(), "ABILITY_TYPE_CODE"); }
    protected abstract ConditionValue xgetCValueAbilityTypeCode();

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
    public HpSLCFunction<AbilityCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, AbilityCB.class);
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
    public HpSLCFunction<AbilityCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, AbilityCB.class);
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
    public HpSLCFunction<AbilityCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, AbilityCB.class);
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
    public HpSLCFunction<AbilityCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, AbilityCB.class);
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
    public HpSLCFunction<AbilityCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, AbilityCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;AbilityCB&gt;() {
     *     public void query(AbilityCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<AbilityCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, AbilityCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(AbilityCQ sq);

    protected AbilityCB xcreateScalarConditionCB() {
        AbilityCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected AbilityCB xcreateScalarConditionPartitionByCB() {
        AbilityCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
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
    protected AbilityCB newMyCB() {
        return new AbilityCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return AbilityCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
