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
 * The abstract condition-query of VILLAGE_PLAYER_STATUS_TYPE.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVillagePlayerStatusTypeCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVillagePlayerStatusTypeCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "VILLAGE_PLAYER_STATUS_TYPE";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     * @param villagePlayerStatusTypeCode The value of villagePlayerStatusTypeCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillagePlayerStatusTypeCode_Equal(String villagePlayerStatusTypeCode) {
        doSetVillagePlayerStatusTypeCode_Equal(fRES(villagePlayerStatusTypeCode));
    }

    /**
     * Equal(=). As VillagePlayerStatusType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeCode_Equal_AsVillagePlayerStatusType(CDef.VillagePlayerStatusType cdef) {
        doSetVillagePlayerStatusTypeCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 後追い (FOLLOWING_SUICIDE). And OnlyOnceRegistered. <br>
     * 後追い
     */
    public void setVillagePlayerStatusTypeCode_Equal_後追い() {
        setVillagePlayerStatusTypeCode_Equal_AsVillagePlayerStatusType(CDef.VillagePlayerStatusType.後追い);
    }

    protected void doSetVillagePlayerStatusTypeCode_Equal(String villagePlayerStatusTypeCode) {
        regVillagePlayerStatusTypeCode(CK_EQ, villagePlayerStatusTypeCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     * @param villagePlayerStatusTypeCode The value of villagePlayerStatusTypeCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillagePlayerStatusTypeCode_NotEqual(String villagePlayerStatusTypeCode) {
        doSetVillagePlayerStatusTypeCode_NotEqual(fRES(villagePlayerStatusTypeCode));
    }

    /**
     * NotEqual(&lt;&gt;). As VillagePlayerStatusType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeCode_NotEqual_AsVillagePlayerStatusType(CDef.VillagePlayerStatusType cdef) {
        doSetVillagePlayerStatusTypeCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 後追い (FOLLOWING_SUICIDE). And OnlyOnceRegistered. <br>
     * 後追い
     */
    public void setVillagePlayerStatusTypeCode_NotEqual_後追い() {
        setVillagePlayerStatusTypeCode_NotEqual_AsVillagePlayerStatusType(CDef.VillagePlayerStatusType.後追い);
    }

    protected void doSetVillagePlayerStatusTypeCode_NotEqual(String villagePlayerStatusTypeCode) {
        regVillagePlayerStatusTypeCode(CK_NES, villagePlayerStatusTypeCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     * @param villagePlayerStatusTypeCodeList The collection of villagePlayerStatusTypeCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillagePlayerStatusTypeCode_InScope(Collection<String> villagePlayerStatusTypeCodeList) {
        doSetVillagePlayerStatusTypeCode_InScope(villagePlayerStatusTypeCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As VillagePlayerStatusType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeCode_InScope_AsVillagePlayerStatusType(Collection<CDef.VillagePlayerStatusType> cdefList) {
        doSetVillagePlayerStatusTypeCode_InScope(cTStrL(cdefList));
    }

    protected void doSetVillagePlayerStatusTypeCode_InScope(Collection<String> villagePlayerStatusTypeCodeList) {
        regINS(CK_INS, cTL(villagePlayerStatusTypeCodeList), xgetCValueVillagePlayerStatusTypeCode(), "VILLAGE_PLAYER_STATUS_TYPE_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     * @param villagePlayerStatusTypeCodeList The collection of villagePlayerStatusTypeCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillagePlayerStatusTypeCode_NotInScope(Collection<String> villagePlayerStatusTypeCodeList) {
        doSetVillagePlayerStatusTypeCode_NotInScope(villagePlayerStatusTypeCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As VillagePlayerStatusType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeCode_NotInScope_AsVillagePlayerStatusType(Collection<CDef.VillagePlayerStatusType> cdefList) {
        doSetVillagePlayerStatusTypeCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetVillagePlayerStatusTypeCode_NotInScope(Collection<String> villagePlayerStatusTypeCodeList) {
        regINS(CK_NINS, cTL(villagePlayerStatusTypeCodeList), xgetCValueVillagePlayerStatusTypeCode(), "VILLAGE_PLAYER_STATUS_TYPE_CODE");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_STATUS_CODE from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayerStatus</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerStatusList for 'exists'. (NotNull)
     */
    public void existsVillagePlayerStatus(SubQuery<VillagePlayerStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerStatusTypeCode_ExistsReferrer_VillagePlayerStatusList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_STATUS_TYPE_CODE", "VILLAGE_PLAYER_STATUS_CODE", pp, "villagePlayerStatusList");
    }
    public abstract String keepVillagePlayerStatusTypeCode_ExistsReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_STATUS_CODE from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayerStatus</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayerStatus(SubQuery<VillagePlayerStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_STATUS_TYPE_CODE", "VILLAGE_PLAYER_STATUS_CODE", pp, "villagePlayerStatusList");
    }
    public abstract String keepVillagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq);

    public void xsderiveVillagePlayerStatusList(String fn, SubQuery<VillagePlayerStatusCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerStatusTypeCode_SpecifyDerivedReferrer_VillagePlayerStatusList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_STATUS_TYPE_CODE", "VILLAGE_PLAYER_STATUS_CODE", pp, "villagePlayerStatusList", al, op);
    }
    public abstract String keepVillagePlayerStatusTypeCode_SpecifyDerivedReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayerStatus()</span>.<span style="color: #CC4747">max</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     statusCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerStatusCB> derivedVillagePlayerStatus() {
        return xcreateQDRFunctionVillagePlayerStatusList();
    }
    protected HpQDRFunction<VillagePlayerStatusCB> xcreateQDRFunctionVillagePlayerStatusList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerStatusList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerStatusList(String fn, SubQuery<VillagePlayerStatusCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList(cb.query()); String prpp = keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_STATUS_TYPE_CODE", "VILLAGE_PLAYER_STATUS_CODE", sqpp, "villagePlayerStatusList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq);
    public abstract String keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     */
    public void setVillagePlayerStatusTypeCode_IsNull() { regVillagePlayerStatusTypeCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     */
    public void setVillagePlayerStatusTypeCode_IsNotNull() { regVillagePlayerStatusTypeCode(CK_ISNN, DOBJ); }

    protected void regVillagePlayerStatusTypeCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillagePlayerStatusTypeCode(), "VILLAGE_PLAYER_STATUS_TYPE_CODE"); }
    protected abstract ConditionValue xgetCValueVillagePlayerStatusTypeCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_Equal(String villagePlayerStatusTypeName) {
        doSetVillagePlayerStatusTypeName_Equal(fRES(villagePlayerStatusTypeName));
    }

    protected void doSetVillagePlayerStatusTypeName_Equal(String villagePlayerStatusTypeName) {
        regVillagePlayerStatusTypeName(CK_EQ, villagePlayerStatusTypeName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_NotEqual(String villagePlayerStatusTypeName) {
        doSetVillagePlayerStatusTypeName_NotEqual(fRES(villagePlayerStatusTypeName));
    }

    protected void doSetVillagePlayerStatusTypeName_NotEqual(String villagePlayerStatusTypeName) {
        regVillagePlayerStatusTypeName(CK_NES, villagePlayerStatusTypeName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_GreaterThan(String villagePlayerStatusTypeName) {
        regVillagePlayerStatusTypeName(CK_GT, fRES(villagePlayerStatusTypeName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_LessThan(String villagePlayerStatusTypeName) {
        regVillagePlayerStatusTypeName(CK_LT, fRES(villagePlayerStatusTypeName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_GreaterEqual(String villagePlayerStatusTypeName) {
        regVillagePlayerStatusTypeName(CK_GE, fRES(villagePlayerStatusTypeName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_LessEqual(String villagePlayerStatusTypeName) {
        regVillagePlayerStatusTypeName(CK_LE, fRES(villagePlayerStatusTypeName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeNameList The collection of villagePlayerStatusTypeName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_InScope(Collection<String> villagePlayerStatusTypeNameList) {
        doSetVillagePlayerStatusTypeName_InScope(villagePlayerStatusTypeNameList);
    }

    protected void doSetVillagePlayerStatusTypeName_InScope(Collection<String> villagePlayerStatusTypeNameList) {
        regINS(CK_INS, cTL(villagePlayerStatusTypeNameList), xgetCValueVillagePlayerStatusTypeName(), "VILLAGE_PLAYER_STATUS_TYPE_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeNameList The collection of villagePlayerStatusTypeName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerStatusTypeName_NotInScope(Collection<String> villagePlayerStatusTypeNameList) {
        doSetVillagePlayerStatusTypeName_NotInScope(villagePlayerStatusTypeNameList);
    }

    protected void doSetVillagePlayerStatusTypeName_NotInScope(Collection<String> villagePlayerStatusTypeNameList) {
        regINS(CK_NINS, cTL(villagePlayerStatusTypeNameList), xgetCValueVillagePlayerStatusTypeName(), "VILLAGE_PLAYER_STATUS_TYPE_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setVillagePlayerStatusTypeName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillagePlayerStatusTypeName_LikeSearch(String villagePlayerStatusTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillagePlayerStatusTypeName_LikeSearch(villagePlayerStatusTypeName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setVillagePlayerStatusTypeName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setVillagePlayerStatusTypeName_LikeSearch(String villagePlayerStatusTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(villagePlayerStatusTypeName), xgetCValueVillagePlayerStatusTypeName(), "VILLAGE_PLAYER_STATUS_TYPE_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillagePlayerStatusTypeName_NotLikeSearch(String villagePlayerStatusTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillagePlayerStatusTypeName_NotLikeSearch(villagePlayerStatusTypeName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param villagePlayerStatusTypeName The value of villagePlayerStatusTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setVillagePlayerStatusTypeName_NotLikeSearch(String villagePlayerStatusTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(villagePlayerStatusTypeName), xgetCValueVillagePlayerStatusTypeName(), "VILLAGE_PLAYER_STATUS_TYPE_NAME", likeSearchOption);
    }

    protected void regVillagePlayerStatusTypeName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillagePlayerStatusTypeName(), "VILLAGE_PLAYER_STATUS_TYPE_NAME"); }
    protected abstract ConditionValue xgetCValueVillagePlayerStatusTypeName();

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
    public HpSLCFunction<VillagePlayerStatusTypeCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, VillagePlayerStatusTypeCB.class);
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
    public HpSLCFunction<VillagePlayerStatusTypeCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, VillagePlayerStatusTypeCB.class);
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
    public HpSLCFunction<VillagePlayerStatusTypeCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, VillagePlayerStatusTypeCB.class);
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
    public HpSLCFunction<VillagePlayerStatusTypeCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, VillagePlayerStatusTypeCB.class);
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
    public HpSLCFunction<VillagePlayerStatusTypeCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, VillagePlayerStatusTypeCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;VillagePlayerStatusTypeCB&gt;() {
     *     public void query(VillagePlayerStatusTypeCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillagePlayerStatusTypeCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, VillagePlayerStatusTypeCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusTypeCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(VillagePlayerStatusTypeCQ sq);

    protected VillagePlayerStatusTypeCB xcreateScalarConditionCB() {
        VillagePlayerStatusTypeCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected VillagePlayerStatusTypeCB xcreateScalarConditionPartitionByCB() {
        VillagePlayerStatusTypeCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<VillagePlayerStatusTypeCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusTypeCB cb = new VillagePlayerStatusTypeCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VILLAGE_PLAYER_STATUS_TYPE_CODE";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(VillagePlayerStatusTypeCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerStatusTypeCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(VillagePlayerStatusTypeCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusTypeCB cb = new VillagePlayerStatusTypeCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VILLAGE_PLAYER_STATUS_TYPE_CODE";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(VillagePlayerStatusTypeCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<VillagePlayerStatusTypeCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerStatusTypeCB cb = new VillagePlayerStatusTypeCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(VillagePlayerStatusTypeCQ sq);

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
    protected VillagePlayerStatusTypeCB newMyCB() {
        return new VillagePlayerStatusTypeCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return VillagePlayerStatusTypeCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
