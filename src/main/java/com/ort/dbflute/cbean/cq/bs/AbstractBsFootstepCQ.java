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
 * The abstract condition-query of FOOTSTEP.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsFootstepCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsFootstepCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "FOOTSTEP";
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
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaId The value of registerCharaId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterCharaId_Equal(Integer registerCharaId) {
        doSetRegisterCharaId_Equal(registerCharaId);
    }

    protected void doSetRegisterCharaId_Equal(Integer registerCharaId) {
        regRegisterCharaId(CK_EQ, registerCharaId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaId The value of registerCharaId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterCharaId_NotEqual(Integer registerCharaId) {
        doSetRegisterCharaId_NotEqual(registerCharaId);
    }

    protected void doSetRegisterCharaId_NotEqual(Integer registerCharaId) {
        regRegisterCharaId(CK_NES, registerCharaId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaId The value of registerCharaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterCharaId_GreaterThan(Integer registerCharaId) {
        regRegisterCharaId(CK_GT, registerCharaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaId The value of registerCharaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterCharaId_LessThan(Integer registerCharaId) {
        regRegisterCharaId(CK_LT, registerCharaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaId The value of registerCharaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterCharaId_GreaterEqual(Integer registerCharaId) {
        regRegisterCharaId(CK_GE, registerCharaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaId The value of registerCharaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterCharaId_LessEqual(Integer registerCharaId) {
        regRegisterCharaId(CK_LE, registerCharaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of registerCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of registerCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setRegisterCharaId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setRegisterCharaId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of registerCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of registerCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setRegisterCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueRegisterCharaId(), "REGISTER_CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaIdList The collection of registerCharaId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterCharaId_InScope(Collection<Integer> registerCharaIdList) {
        doSetRegisterCharaId_InScope(registerCharaIdList);
    }

    protected void doSetRegisterCharaId_InScope(Collection<Integer> registerCharaIdList) {
        regINS(CK_INS, cTL(registerCharaIdList), xgetCValueRegisterCharaId(), "REGISTER_CHARA_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @param registerCharaIdList The collection of registerCharaId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterCharaId_NotInScope(Collection<Integer> registerCharaIdList) {
        doSetRegisterCharaId_NotInScope(registerCharaIdList);
    }

    protected void doSetRegisterCharaId_NotInScope(Collection<Integer> registerCharaIdList) {
        regINS(CK_NINS, cTL(registerCharaIdList), xgetCValueRegisterCharaId(), "REGISTER_CHARA_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     */
    public void setRegisterCharaId_IsNull() { regRegisterCharaId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * REGISTER_CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     */
    public void setRegisterCharaId_IsNotNull() { regRegisterCharaId(CK_ISNN, DOBJ); }

    protected void regRegisterCharaId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterCharaId(), "REGISTER_CHARA_ID"); }
    protected abstract ConditionValue xgetCValueRegisterCharaId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
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
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
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
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterThan(Integer charaId) {
        regCharaId(CK_GT, charaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessThan(Integer charaId) {
        regCharaId(CK_LT, charaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterEqual(Integer charaId) {
        regCharaId(CK_GE, charaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessEqual(Integer charaId) {
        regCharaId(CK_LE, charaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
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
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueCharaId(), "CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
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
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaIdList The collection of charaId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaId_NotInScope(Collection<Integer> charaIdList) {
        doSetCharaId_NotInScope(charaIdList);
    }

    protected void doSetCharaId_NotInScope(Collection<Integer> charaIdList) {
        regINS(CK_NINS, cTL(charaIdList), xgetCValueCharaId(), "CHARA_ID");
    }

    protected void regCharaId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaId(), "CHARA_ID"); }
    protected abstract ConditionValue xgetCValueCharaId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_Equal(String footstepRoomNumbers) {
        doSetFootstepRoomNumbers_Equal(fRES(footstepRoomNumbers));
    }

    protected void doSetFootstepRoomNumbers_Equal(String footstepRoomNumbers) {
        regFootstepRoomNumbers(CK_EQ, footstepRoomNumbers);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_NotEqual(String footstepRoomNumbers) {
        doSetFootstepRoomNumbers_NotEqual(fRES(footstepRoomNumbers));
    }

    protected void doSetFootstepRoomNumbers_NotEqual(String footstepRoomNumbers) {
        regFootstepRoomNumbers(CK_NES, footstepRoomNumbers);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_GreaterThan(String footstepRoomNumbers) {
        regFootstepRoomNumbers(CK_GT, fRES(footstepRoomNumbers));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_LessThan(String footstepRoomNumbers) {
        regFootstepRoomNumbers(CK_LT, fRES(footstepRoomNumbers));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_GreaterEqual(String footstepRoomNumbers) {
        regFootstepRoomNumbers(CK_GE, fRES(footstepRoomNumbers));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_LessEqual(String footstepRoomNumbers) {
        regFootstepRoomNumbers(CK_LE, fRES(footstepRoomNumbers));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbersList The collection of footstepRoomNumbers as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_InScope(Collection<String> footstepRoomNumbersList) {
        doSetFootstepRoomNumbers_InScope(footstepRoomNumbersList);
    }

    protected void doSetFootstepRoomNumbers_InScope(Collection<String> footstepRoomNumbersList) {
        regINS(CK_INS, cTL(footstepRoomNumbersList), xgetCValueFootstepRoomNumbers(), "FOOTSTEP_ROOM_NUMBERS");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbersList The collection of footstepRoomNumbers as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setFootstepRoomNumbers_NotInScope(Collection<String> footstepRoomNumbersList) {
        doSetFootstepRoomNumbers_NotInScope(footstepRoomNumbersList);
    }

    protected void doSetFootstepRoomNumbers_NotInScope(Collection<String> footstepRoomNumbersList) {
        regINS(CK_NINS, cTL(footstepRoomNumbersList), xgetCValueFootstepRoomNumbers(), "FOOTSTEP_ROOM_NUMBERS");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)} <br>
     * <pre>e.g. setFootstepRoomNumbers_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param footstepRoomNumbers The value of footstepRoomNumbers as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setFootstepRoomNumbers_LikeSearch(String footstepRoomNumbers, ConditionOptionCall<LikeSearchOption> opLambda) {
        setFootstepRoomNumbers_LikeSearch(footstepRoomNumbers, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)} <br>
     * <pre>e.g. setFootstepRoomNumbers_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param footstepRoomNumbers The value of footstepRoomNumbers as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setFootstepRoomNumbers_LikeSearch(String footstepRoomNumbers, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(footstepRoomNumbers), xgetCValueFootstepRoomNumbers(), "FOOTSTEP_ROOM_NUMBERS", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setFootstepRoomNumbers_NotLikeSearch(String footstepRoomNumbers, ConditionOptionCall<LikeSearchOption> opLambda) {
        setFootstepRoomNumbers_NotLikeSearch(footstepRoomNumbers, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     * @param footstepRoomNumbers The value of footstepRoomNumbers as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setFootstepRoomNumbers_NotLikeSearch(String footstepRoomNumbers, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(footstepRoomNumbers), xgetCValueFootstepRoomNumbers(), "FOOTSTEP_ROOM_NUMBERS", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     */
    public void setFootstepRoomNumbers_IsNull() { regFootstepRoomNumbers(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     */
    public void setFootstepRoomNumbers_IsNullOrEmpty() { regFootstepRoomNumbers(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * FOOTSTEP_ROOM_NUMBERS: {VARCHAR(1000)}
     */
    public void setFootstepRoomNumbers_IsNotNull() { regFootstepRoomNumbers(CK_ISNN, DOBJ); }

    protected void regFootstepRoomNumbers(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueFootstepRoomNumbers(), "FOOTSTEP_ROOM_NUMBERS"); }
    protected abstract ConditionValue xgetCValueFootstepRoomNumbers();

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
    public HpSLCFunction<FootstepCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, FootstepCB.class);
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
    public HpSLCFunction<FootstepCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, FootstepCB.class);
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
    public HpSLCFunction<FootstepCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, FootstepCB.class);
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
    public HpSLCFunction<FootstepCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, FootstepCB.class);
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
    public HpSLCFunction<FootstepCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, FootstepCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;FootstepCB&gt;() {
     *     public void query(FootstepCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<FootstepCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, FootstepCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        FootstepCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(FootstepCQ sq);

    protected FootstepCB xcreateScalarConditionCB() {
        FootstepCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected FootstepCB xcreateScalarConditionPartitionByCB() {
        FootstepCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
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
    protected FootstepCB newMyCB() {
        return new FootstepCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return FootstepCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
