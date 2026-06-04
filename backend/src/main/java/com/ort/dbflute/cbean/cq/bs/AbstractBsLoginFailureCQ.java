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
 * The abstract condition-query of login_failure.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsLoginFailureCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsLoginFailureCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "login_failure";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureId The value of loginFailureId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setLoginFailureId_Equal(Integer loginFailureId) {
        doSetLoginFailureId_Equal(loginFailureId);
    }

    protected void doSetLoginFailureId_Equal(Integer loginFailureId) {
        regLoginFailureId(CK_EQ, loginFailureId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureId The value of loginFailureId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setLoginFailureId_NotEqual(Integer loginFailureId) {
        doSetLoginFailureId_NotEqual(loginFailureId);
    }

    protected void doSetLoginFailureId_NotEqual(Integer loginFailureId) {
        regLoginFailureId(CK_NES, loginFailureId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureId The value of loginFailureId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setLoginFailureId_GreaterThan(Integer loginFailureId) {
        regLoginFailureId(CK_GT, loginFailureId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureId The value of loginFailureId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setLoginFailureId_LessThan(Integer loginFailureId) {
        regLoginFailureId(CK_LT, loginFailureId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureId The value of loginFailureId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setLoginFailureId_GreaterEqual(Integer loginFailureId) {
        regLoginFailureId(CK_GE, loginFailureId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureId The value of loginFailureId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setLoginFailureId_LessEqual(Integer loginFailureId) {
        regLoginFailureId(CK_LE, loginFailureId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of loginFailureId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of loginFailureId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setLoginFailureId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setLoginFailureId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of loginFailureId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of loginFailureId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setLoginFailureId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueLoginFailureId(), "LOGIN_FAILURE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureIdList The collection of loginFailureId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginFailureId_InScope(Collection<Integer> loginFailureIdList) {
        doSetLoginFailureId_InScope(loginFailureIdList);
    }

    protected void doSetLoginFailureId_InScope(Collection<Integer> loginFailureIdList) {
        regINS(CK_INS, cTL(loginFailureIdList), xgetCValueLoginFailureId(), "LOGIN_FAILURE_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param loginFailureIdList The collection of loginFailureId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginFailureId_NotInScope(Collection<Integer> loginFailureIdList) {
        doSetLoginFailureId_NotInScope(loginFailureIdList);
    }

    protected void doSetLoginFailureId_NotInScope(Collection<Integer> loginFailureIdList) {
        regINS(CK_NINS, cTL(loginFailureIdList), xgetCValueLoginFailureId(), "LOGIN_FAILURE_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setLoginFailureId_IsNull() { regLoginFailureId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setLoginFailureId_IsNotNull() { regLoginFailureId(CK_ISNN, DOBJ); }

    protected void regLoginFailureId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueLoginFailureId(), "LOGIN_FAILURE_ID"); }
    protected abstract ConditionValue xgetCValueLoginFailureId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_Equal(String loginName) {
        doSetLoginName_Equal(fRES(loginName));
    }

    protected void doSetLoginName_Equal(String loginName) {
        regLoginName(CK_EQ, loginName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_NotEqual(String loginName) {
        doSetLoginName_NotEqual(fRES(loginName));
    }

    protected void doSetLoginName_NotEqual(String loginName) {
        regLoginName(CK_NES, loginName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_GreaterThan(String loginName) {
        regLoginName(CK_GT, fRES(loginName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_LessThan(String loginName) {
        regLoginName(CK_LT, fRES(loginName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_GreaterEqual(String loginName) {
        regLoginName(CK_GE, fRES(loginName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_LessEqual(String loginName) {
        regLoginName(CK_LE, fRES(loginName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginNameList The collection of loginName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_InScope(Collection<String> loginNameList) {
        doSetLoginName_InScope(loginNameList);
    }

    protected void doSetLoginName_InScope(Collection<String> loginNameList) {
        regINS(CK_INS, cTL(loginNameList), xgetCValueLoginName(), "LOGIN_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginNameList The collection of loginName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setLoginName_NotInScope(Collection<String> loginNameList) {
        doSetLoginName_NotInScope(loginNameList);
    }

    protected void doSetLoginName_NotInScope(Collection<String> loginNameList) {
        regINS(CK_NINS, cTL(loginNameList), xgetCValueLoginName(), "LOGIN_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)} <br>
     * <pre>e.g. setLoginName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param loginName The value of loginName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setLoginName_LikeSearch(String loginName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setLoginName_LikeSearch(loginName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)} <br>
     * <pre>e.g. setLoginName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param loginName The value of loginName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setLoginName_LikeSearch(String loginName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(loginName), xgetCValueLoginName(), "LOGIN_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setLoginName_NotLikeSearch(String loginName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setLoginName_NotLikeSearch(loginName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * LOGIN_NAME: {IX+, NotNull, VARCHAR(60)}
     * @param loginName The value of loginName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setLoginName_NotLikeSearch(String loginName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(loginName), xgetCValueLoginName(), "LOGIN_NAME", likeSearchOption);
    }

    protected void regLoginName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueLoginName(), "LOGIN_NAME"); }
    protected abstract ConditionValue xgetCValueLoginName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_Equal(String ipAddress) {
        doSetIpAddress_Equal(fRES(ipAddress));
    }

    protected void doSetIpAddress_Equal(String ipAddress) {
        regIpAddress(CK_EQ, ipAddress);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_NotEqual(String ipAddress) {
        doSetIpAddress_NotEqual(fRES(ipAddress));
    }

    protected void doSetIpAddress_NotEqual(String ipAddress) {
        regIpAddress(CK_NES, ipAddress);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_GreaterThan(String ipAddress) {
        regIpAddress(CK_GT, fRES(ipAddress));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_LessThan(String ipAddress) {
        regIpAddress(CK_LT, fRES(ipAddress));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_GreaterEqual(String ipAddress) {
        regIpAddress(CK_GE, fRES(ipAddress));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_LessEqual(String ipAddress) {
        regIpAddress(CK_LE, fRES(ipAddress));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddressList The collection of ipAddress as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_InScope(Collection<String> ipAddressList) {
        doSetIpAddress_InScope(ipAddressList);
    }

    protected void doSetIpAddress_InScope(Collection<String> ipAddressList) {
        regINS(CK_INS, cTL(ipAddressList), xgetCValueIpAddress(), "IP_ADDRESS");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddressList The collection of ipAddress as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setIpAddress_NotInScope(Collection<String> ipAddressList) {
        doSetIpAddress_NotInScope(ipAddressList);
    }

    protected void doSetIpAddress_NotInScope(Collection<String> ipAddressList) {
        regINS(CK_NINS, cTL(ipAddressList), xgetCValueIpAddress(), "IP_ADDRESS");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setIpAddress_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param ipAddress The value of ipAddress as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setIpAddress_LikeSearch(String ipAddress, ConditionOptionCall<LikeSearchOption> opLambda) {
        setIpAddress_LikeSearch(ipAddress, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setIpAddress_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param ipAddress The value of ipAddress as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setIpAddress_LikeSearch(String ipAddress, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(ipAddress), xgetCValueIpAddress(), "IP_ADDRESS", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setIpAddress_NotLikeSearch(String ipAddress, ConditionOptionCall<LikeSearchOption> opLambda) {
        setIpAddress_NotLikeSearch(ipAddress, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * IP_ADDRESS: {IX+, NotNull, VARCHAR(64)}
     * @param ipAddress The value of ipAddress as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setIpAddress_NotLikeSearch(String ipAddress, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(ipAddress), xgetCValueIpAddress(), "IP_ADDRESS", likeSearchOption);
    }

    protected void regIpAddress(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIpAddress(), "IP_ADDRESS"); }
    protected abstract ConditionValue xgetCValueIpAddress();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ATTEMPT_DATETIME: {NotNull, DATETIME(19)}
     * @param attemptDatetime The value of attemptDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setAttemptDatetime_Equal(java.time.LocalDateTime attemptDatetime) {
        regAttemptDatetime(CK_EQ,  attemptDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ATTEMPT_DATETIME: {NotNull, DATETIME(19)}
     * @param attemptDatetime The value of attemptDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setAttemptDatetime_GreaterThan(java.time.LocalDateTime attemptDatetime) {
        regAttemptDatetime(CK_GT,  attemptDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ATTEMPT_DATETIME: {NotNull, DATETIME(19)}
     * @param attemptDatetime The value of attemptDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setAttemptDatetime_LessThan(java.time.LocalDateTime attemptDatetime) {
        regAttemptDatetime(CK_LT,  attemptDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ATTEMPT_DATETIME: {NotNull, DATETIME(19)}
     * @param attemptDatetime The value of attemptDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setAttemptDatetime_GreaterEqual(java.time.LocalDateTime attemptDatetime) {
        regAttemptDatetime(CK_GE,  attemptDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ATTEMPT_DATETIME: {NotNull, DATETIME(19)}
     * @param attemptDatetime The value of attemptDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setAttemptDatetime_LessEqual(java.time.LocalDateTime attemptDatetime) {
        regAttemptDatetime(CK_LE, attemptDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ATTEMPT_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setAttemptDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of attemptDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of attemptDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setAttemptDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setAttemptDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ATTEMPT_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setAttemptDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of attemptDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of attemptDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setAttemptDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "ATTEMPT_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueAttemptDatetime(), nm, op);
    }

    protected void regAttemptDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueAttemptDatetime(), "ATTEMPT_DATETIME"); }
    protected abstract ConditionValue xgetCValueAttemptDatetime();

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
    public HpSLCFunction<LoginFailureCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, LoginFailureCB.class);
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
    public HpSLCFunction<LoginFailureCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, LoginFailureCB.class);
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
    public HpSLCFunction<LoginFailureCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, LoginFailureCB.class);
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
    public HpSLCFunction<LoginFailureCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, LoginFailureCB.class);
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
    public HpSLCFunction<LoginFailureCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, LoginFailureCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;LoginFailureCB&gt;() {
     *     public void query(LoginFailureCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<LoginFailureCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, LoginFailureCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        LoginFailureCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(LoginFailureCQ sq);

    protected LoginFailureCB xcreateScalarConditionCB() {
        LoginFailureCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected LoginFailureCB xcreateScalarConditionPartitionByCB() {
        LoginFailureCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<LoginFailureCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        LoginFailureCB cb = new LoginFailureCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "LOGIN_FAILURE_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(LoginFailureCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<LoginFailureCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(LoginFailureCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        LoginFailureCB cb = new LoginFailureCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "LOGIN_FAILURE_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(LoginFailureCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<LoginFailureCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        LoginFailureCB cb = new LoginFailureCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(LoginFailureCQ sq);

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
    protected LoginFailureCB newMyCB() {
        return new LoginFailureCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return LoginFailureCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
