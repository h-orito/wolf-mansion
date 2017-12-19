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
 * The abstract condition-query of VILLAGE_SETTINGS.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVillageSettingsCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVillageSettingsCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "VILLAGE_SETTINGS";
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
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNum The value of startPersonMinNum as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_Equal(Integer startPersonMinNum) {
        doSetStartPersonMinNum_Equal(startPersonMinNum);
    }

    protected void doSetStartPersonMinNum_Equal(Integer startPersonMinNum) {
        regStartPersonMinNum(CK_EQ, startPersonMinNum);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNum The value of startPersonMinNum as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_NotEqual(Integer startPersonMinNum) {
        doSetStartPersonMinNum_NotEqual(startPersonMinNum);
    }

    protected void doSetStartPersonMinNum_NotEqual(Integer startPersonMinNum) {
        regStartPersonMinNum(CK_NES, startPersonMinNum);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNum The value of startPersonMinNum as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_GreaterThan(Integer startPersonMinNum) {
        regStartPersonMinNum(CK_GT, startPersonMinNum);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNum The value of startPersonMinNum as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_LessThan(Integer startPersonMinNum) {
        regStartPersonMinNum(CK_LT, startPersonMinNum);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNum The value of startPersonMinNum as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_GreaterEqual(Integer startPersonMinNum) {
        regStartPersonMinNum(CK_GE, startPersonMinNum);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNum The value of startPersonMinNum as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_LessEqual(Integer startPersonMinNum) {
        regStartPersonMinNum(CK_LE, startPersonMinNum);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param minNumber The min number of startPersonMinNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of startPersonMinNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setStartPersonMinNum_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setStartPersonMinNum_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param minNumber The min number of startPersonMinNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of startPersonMinNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setStartPersonMinNum_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueStartPersonMinNum(), "START_PERSON_MIN_NUM", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNumList The collection of startPersonMinNum as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_InScope(Collection<Integer> startPersonMinNumList) {
        doSetStartPersonMinNum_InScope(startPersonMinNumList);
    }

    protected void doSetStartPersonMinNum_InScope(Collection<Integer> startPersonMinNumList) {
        regINS(CK_INS, cTL(startPersonMinNumList), xgetCValueStartPersonMinNum(), "START_PERSON_MIN_NUM");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @param startPersonMinNumList The collection of startPersonMinNum as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setStartPersonMinNum_NotInScope(Collection<Integer> startPersonMinNumList) {
        doSetStartPersonMinNum_NotInScope(startPersonMinNumList);
    }

    protected void doSetStartPersonMinNum_NotInScope(Collection<Integer> startPersonMinNumList) {
        regINS(CK_NINS, cTL(startPersonMinNumList), xgetCValueStartPersonMinNum(), "START_PERSON_MIN_NUM");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     */
    public void setStartPersonMinNum_IsNull() { regStartPersonMinNum(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     */
    public void setStartPersonMinNum_IsNotNull() { regStartPersonMinNum(CK_ISNN, DOBJ); }

    protected void regStartPersonMinNum(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueStartPersonMinNum(), "START_PERSON_MIN_NUM"); }
    protected abstract ConditionValue xgetCValueStartPersonMinNum();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNum The value of personMaxNum as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setPersonMaxNum_Equal(Integer personMaxNum) {
        doSetPersonMaxNum_Equal(personMaxNum);
    }

    protected void doSetPersonMaxNum_Equal(Integer personMaxNum) {
        regPersonMaxNum(CK_EQ, personMaxNum);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNum The value of personMaxNum as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPersonMaxNum_NotEqual(Integer personMaxNum) {
        doSetPersonMaxNum_NotEqual(personMaxNum);
    }

    protected void doSetPersonMaxNum_NotEqual(Integer personMaxNum) {
        regPersonMaxNum(CK_NES, personMaxNum);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNum The value of personMaxNum as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPersonMaxNum_GreaterThan(Integer personMaxNum) {
        regPersonMaxNum(CK_GT, personMaxNum);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNum The value of personMaxNum as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPersonMaxNum_LessThan(Integer personMaxNum) {
        regPersonMaxNum(CK_LT, personMaxNum);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNum The value of personMaxNum as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPersonMaxNum_GreaterEqual(Integer personMaxNum) {
        regPersonMaxNum(CK_GE, personMaxNum);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNum The value of personMaxNum as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPersonMaxNum_LessEqual(Integer personMaxNum) {
        regPersonMaxNum(CK_LE, personMaxNum);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param minNumber The min number of personMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of personMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setPersonMaxNum_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setPersonMaxNum_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param minNumber The min number of personMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of personMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setPersonMaxNum_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValuePersonMaxNum(), "PERSON_MAX_NUM", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNumList The collection of personMaxNum as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setPersonMaxNum_InScope(Collection<Integer> personMaxNumList) {
        doSetPersonMaxNum_InScope(personMaxNumList);
    }

    protected void doSetPersonMaxNum_InScope(Collection<Integer> personMaxNumList) {
        regINS(CK_INS, cTL(personMaxNumList), xgetCValuePersonMaxNum(), "PERSON_MAX_NUM");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @param personMaxNumList The collection of personMaxNum as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setPersonMaxNum_NotInScope(Collection<Integer> personMaxNumList) {
        doSetPersonMaxNum_NotInScope(personMaxNumList);
    }

    protected void doSetPersonMaxNum_NotInScope(Collection<Integer> personMaxNumList) {
        regINS(CK_NINS, cTL(personMaxNumList), xgetCValuePersonMaxNum(), "PERSON_MAX_NUM");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     */
    public void setPersonMaxNum_IsNull() { regPersonMaxNum(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     */
    public void setPersonMaxNum_IsNotNull() { regPersonMaxNum(CK_ISNN, DOBJ); }

    protected void regPersonMaxNum(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValuePersonMaxNum(), "PERSON_MAX_NUM"); }
    protected abstract ConditionValue xgetCValuePersonMaxNum();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     * @param startDatetime The value of startDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartDatetime_Equal(java.time.LocalDateTime startDatetime) {
        regStartDatetime(CK_EQ,  startDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     * @param startDatetime The value of startDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartDatetime_GreaterThan(java.time.LocalDateTime startDatetime) {
        regStartDatetime(CK_GT,  startDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     * @param startDatetime The value of startDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartDatetime_LessThan(java.time.LocalDateTime startDatetime) {
        regStartDatetime(CK_LT,  startDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     * @param startDatetime The value of startDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartDatetime_GreaterEqual(java.time.LocalDateTime startDatetime) {
        regStartDatetime(CK_GE,  startDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     * @param startDatetime The value of startDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setStartDatetime_LessEqual(java.time.LocalDateTime startDatetime) {
        regStartDatetime(CK_LE, startDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     * <pre>e.g. setStartDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of startDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of startDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setStartDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setStartDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     * <pre>e.g. setStartDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of startDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of startDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setStartDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "START_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueStartDatetime(), nm, op);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     */
    public void setStartDatetime_IsNull() { regStartDatetime(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * START_DATETIME: {DATETIME(19)}
     */
    public void setStartDatetime_IsNotNull() { regStartDatetime(CK_ISNN, DOBJ); }

    protected void regStartDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueStartDatetime(), "START_DATETIME"); }
    protected abstract ConditionValue xgetCValueStartDatetime();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSeconds The value of dayChangeIntervalSeconds as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_Equal(Integer dayChangeIntervalSeconds) {
        doSetDayChangeIntervalSeconds_Equal(dayChangeIntervalSeconds);
    }

    protected void doSetDayChangeIntervalSeconds_Equal(Integer dayChangeIntervalSeconds) {
        regDayChangeIntervalSeconds(CK_EQ, dayChangeIntervalSeconds);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSeconds The value of dayChangeIntervalSeconds as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_NotEqual(Integer dayChangeIntervalSeconds) {
        doSetDayChangeIntervalSeconds_NotEqual(dayChangeIntervalSeconds);
    }

    protected void doSetDayChangeIntervalSeconds_NotEqual(Integer dayChangeIntervalSeconds) {
        regDayChangeIntervalSeconds(CK_NES, dayChangeIntervalSeconds);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSeconds The value of dayChangeIntervalSeconds as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_GreaterThan(Integer dayChangeIntervalSeconds) {
        regDayChangeIntervalSeconds(CK_GT, dayChangeIntervalSeconds);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSeconds The value of dayChangeIntervalSeconds as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_LessThan(Integer dayChangeIntervalSeconds) {
        regDayChangeIntervalSeconds(CK_LT, dayChangeIntervalSeconds);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSeconds The value of dayChangeIntervalSeconds as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_GreaterEqual(Integer dayChangeIntervalSeconds) {
        regDayChangeIntervalSeconds(CK_GE, dayChangeIntervalSeconds);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSeconds The value of dayChangeIntervalSeconds as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_LessEqual(Integer dayChangeIntervalSeconds) {
        regDayChangeIntervalSeconds(CK_LE, dayChangeIntervalSeconds);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of dayChangeIntervalSeconds. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of dayChangeIntervalSeconds. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDayChangeIntervalSeconds_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDayChangeIntervalSeconds_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of dayChangeIntervalSeconds. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of dayChangeIntervalSeconds. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDayChangeIntervalSeconds_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDayChangeIntervalSeconds(), "DAY_CHANGE_INTERVAL_SECONDS", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSecondsList The collection of dayChangeIntervalSeconds as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_InScope(Collection<Integer> dayChangeIntervalSecondsList) {
        doSetDayChangeIntervalSeconds_InScope(dayChangeIntervalSecondsList);
    }

    protected void doSetDayChangeIntervalSeconds_InScope(Collection<Integer> dayChangeIntervalSecondsList) {
        regINS(CK_INS, cTL(dayChangeIntervalSecondsList), xgetCValueDayChangeIntervalSeconds(), "DAY_CHANGE_INTERVAL_SECONDS");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @param dayChangeIntervalSecondsList The collection of dayChangeIntervalSeconds as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDayChangeIntervalSeconds_NotInScope(Collection<Integer> dayChangeIntervalSecondsList) {
        doSetDayChangeIntervalSeconds_NotInScope(dayChangeIntervalSecondsList);
    }

    protected void doSetDayChangeIntervalSeconds_NotInScope(Collection<Integer> dayChangeIntervalSecondsList) {
        regINS(CK_NINS, cTL(dayChangeIntervalSecondsList), xgetCValueDayChangeIntervalSeconds(), "DAY_CHANGE_INTERVAL_SECONDS");
    }

    protected void regDayChangeIntervalSeconds(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDayChangeIntervalSeconds(), "DAY_CHANGE_INTERVAL_SECONDS"); }
    protected abstract ConditionValue xgetCValueDayChangeIntervalSeconds();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_OPEN_VOTE: {NotNull, BIT}
     * @param isOpenVote The value of isOpenVote as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsOpenVote_Equal(Boolean isOpenVote) {
        regIsOpenVote(CK_EQ, isOpenVote);
    }

    protected void regIsOpenVote(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsOpenVote(), "IS_OPEN_VOTE"); }
    protected abstract ConditionValue xgetCValueIsOpenVote();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT}
     * @param isPossibleSkillRequest The value of isPossibleSkillRequest as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsPossibleSkillRequest_Equal(Boolean isPossibleSkillRequest) {
        regIsPossibleSkillRequest(CK_EQ, isPossibleSkillRequest);
    }

    protected void regIsPossibleSkillRequest(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsPossibleSkillRequest(), "IS_POSSIBLE_SKILL_REQUEST"); }
    protected abstract ConditionValue xgetCValueIsPossibleSkillRequest();

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
    public HpSLCFunction<VillageSettingsCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, VillageSettingsCB.class);
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
    public HpSLCFunction<VillageSettingsCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, VillageSettingsCB.class);
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
    public HpSLCFunction<VillageSettingsCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, VillageSettingsCB.class);
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
    public HpSLCFunction<VillageSettingsCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, VillageSettingsCB.class);
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
    public HpSLCFunction<VillageSettingsCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, VillageSettingsCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;VillageSettingsCB&gt;() {
     *     public void query(VillageSettingsCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillageSettingsCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, VillageSettingsCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageSettingsCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(VillageSettingsCQ sq);

    protected VillageSettingsCB xcreateScalarConditionCB() {
        VillageSettingsCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected VillageSettingsCB xcreateScalarConditionPartitionByCB() {
        VillageSettingsCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<VillageSettingsCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VILLAGE_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(VillageSettingsCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<VillageSettingsCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(VillageSettingsCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VILLAGE_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(VillageSettingsCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<VillageSettingsCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(VillageSettingsCQ sq);

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
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
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
    protected VillageSettingsCB newMyCB() {
        return new VillageSettingsCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return VillageSettingsCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
