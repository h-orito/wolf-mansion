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
 * The abstract condition-query of chara_group.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsCharaGroupCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsCharaGroupCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "chara_group";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupId The value of charaGroupId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaGroupId_Equal(Integer charaGroupId) {
        doSetCharaGroupId_Equal(charaGroupId);
    }

    protected void doSetCharaGroupId_Equal(Integer charaGroupId) {
        regCharaGroupId(CK_EQ, charaGroupId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupId The value of charaGroupId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaGroupId_NotEqual(Integer charaGroupId) {
        doSetCharaGroupId_NotEqual(charaGroupId);
    }

    protected void doSetCharaGroupId_NotEqual(Integer charaGroupId) {
        regCharaGroupId(CK_NES, charaGroupId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupId The value of charaGroupId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaGroupId_GreaterThan(Integer charaGroupId) {
        regCharaGroupId(CK_GT, charaGroupId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupId The value of charaGroupId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaGroupId_LessThan(Integer charaGroupId) {
        regCharaGroupId(CK_LT, charaGroupId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupId The value of charaGroupId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaGroupId_GreaterEqual(Integer charaGroupId) {
        regCharaGroupId(CK_GE, charaGroupId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupId The value of charaGroupId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaGroupId_LessEqual(Integer charaGroupId) {
        regCharaGroupId(CK_LE, charaGroupId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of charaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setCharaGroupId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setCharaGroupId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of charaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setCharaGroupId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueCharaGroupId(), "CHARA_GROUP_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupIdList The collection of charaGroupId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaGroupId_InScope(Collection<Integer> charaGroupIdList) {
        doSetCharaGroupId_InScope(charaGroupIdList);
    }

    protected void doSetCharaGroupId_InScope(Collection<Integer> charaGroupIdList) {
        regINS(CK_INS, cTL(charaGroupIdList), xgetCValueCharaGroupId(), "CHARA_GROUP_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param charaGroupIdList The collection of charaGroupId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaGroupId_NotInScope(Collection<Integer> charaGroupIdList) {
        doSetCharaGroupId_NotInScope(charaGroupIdList);
    }

    protected void doSetCharaGroupId_NotInScope(Collection<Integer> charaGroupIdList) {
        regINS(CK_NINS, cTL(charaGroupIdList), xgetCValueCharaGroupId(), "CHARA_GROUP_ID");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select CHARA_GROUP_ID from chara where ...)} <br>
     * chara by CHARA_GROUP_ID, named 'charaAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsChara</span>(charaCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     charaCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of CharaList for 'exists'. (NotNull)
     */
    public void existsChara(SubQuery<CharaCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        CharaCB cb = new CharaCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepCharaGroupId_ExistsReferrer_CharaList(cb.query());
        registerExistsReferrer(cb.query(), "CHARA_GROUP_ID", "CHARA_GROUP_ID", pp, "charaList");
    }
    public abstract String keepCharaGroupId_ExistsReferrer_CharaList(CharaCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select CHARA_GROUP_ID from chara where ...)} <br>
     * chara by CHARA_GROUP_ID, named 'charaAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsChara</span>(charaCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     charaCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of CharaGroupId_NotExistsReferrer_CharaList for 'not exists'. (NotNull)
     */
    public void notExistsChara(SubQuery<CharaCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        CharaCB cb = new CharaCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepCharaGroupId_NotExistsReferrer_CharaList(cb.query());
        registerNotExistsReferrer(cb.query(), "CHARA_GROUP_ID", "CHARA_GROUP_ID", pp, "charaList");
    }
    public abstract String keepCharaGroupId_NotExistsReferrer_CharaList(CharaCQ sq);

    public void xsderiveCharaList(String fn, SubQuery<CharaCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CharaCB cb = new CharaCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepCharaGroupId_SpecifyDerivedReferrer_CharaList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "CHARA_GROUP_ID", "CHARA_GROUP_ID", pp, "charaList", al, op);
    }
    public abstract String keepCharaGroupId_SpecifyDerivedReferrer_CharaList(CharaCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from chara where ...)} <br>
     * chara by CHARA_GROUP_ID, named 'charaAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedChara()</span>.<span style="color: #CC4747">max</span>(charaCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     charaCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     charaCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<CharaCB> derivedChara() {
        return xcreateQDRFunctionCharaList();
    }
    protected HpQDRFunction<CharaCB> xcreateQDRFunctionCharaList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveCharaList(fn, sq, rd, vl, op));
    }
    public void xqderiveCharaList(String fn, SubQuery<CharaCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CharaCB cb = new CharaCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepCharaGroupId_QueryDerivedReferrer_CharaList(cb.query()); String prpp = keepCharaGroupId_QueryDerivedReferrer_CharaListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "CHARA_GROUP_ID", "CHARA_GROUP_ID", sqpp, "charaList", rd, vl, prpp, op);
    }
    public abstract String keepCharaGroupId_QueryDerivedReferrer_CharaList(CharaCQ sq);
    public abstract String keepCharaGroupId_QueryDerivedReferrer_CharaListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setCharaGroupId_IsNull() { regCharaGroupId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setCharaGroupId_IsNotNull() { regCharaGroupId(CK_ISNN, DOBJ); }

    protected void regCharaGroupId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaGroupId(), "CHARA_GROUP_ID"); }
    protected abstract ConditionValue xgetCValueCharaGroupId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_Equal(String charaName) {
        doSetCharaName_Equal(fRES(charaName));
    }

    protected void doSetCharaName_Equal(String charaName) {
        regCharaName(CK_EQ, charaName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_NotEqual(String charaName) {
        doSetCharaName_NotEqual(fRES(charaName));
    }

    protected void doSetCharaName_NotEqual(String charaName) {
        regCharaName(CK_NES, charaName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_GreaterThan(String charaName) {
        regCharaName(CK_GT, fRES(charaName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_LessThan(String charaName) {
        regCharaName(CK_LT, fRES(charaName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_GreaterEqual(String charaName) {
        regCharaName(CK_GE, fRES(charaName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_LessEqual(String charaName) {
        regCharaName(CK_LE, fRES(charaName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaNameList The collection of charaName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_InScope(Collection<String> charaNameList) {
        doSetCharaName_InScope(charaNameList);
    }

    protected void doSetCharaName_InScope(Collection<String> charaNameList) {
        regINS(CK_INS, cTL(charaNameList), xgetCValueCharaName(), "CHARA_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaNameList The collection of charaName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_NotInScope(Collection<String> charaNameList) {
        doSetCharaName_NotInScope(charaNameList);
    }

    protected void doSetCharaName_NotInScope(Collection<String> charaNameList) {
        regINS(CK_NINS, cTL(charaNameList), xgetCValueCharaName(), "CHARA_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setCharaName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param charaName The value of charaName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaName_LikeSearch(String charaName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaName_LikeSearch(charaName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setCharaName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param charaName The value of charaName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setCharaName_LikeSearch(String charaName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(charaName), xgetCValueCharaName(), "CHARA_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaName_NotLikeSearch(String charaName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaName_NotLikeSearch(charaName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setCharaName_NotLikeSearch(String charaName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(charaName), xgetCValueCharaName(), "CHARA_NAME", likeSearchOption);
    }

    protected void regCharaName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaName(), "CHARA_NAME"); }
    protected abstract ConditionValue xgetCValueCharaName();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerId The value of designerId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDesignerId_Equal(Integer designerId) {
        doSetDesignerId_Equal(designerId);
    }

    protected void doSetDesignerId_Equal(Integer designerId) {
        regDesignerId(CK_EQ, designerId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerId The value of designerId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDesignerId_NotEqual(Integer designerId) {
        doSetDesignerId_NotEqual(designerId);
    }

    protected void doSetDesignerId_NotEqual(Integer designerId) {
        regDesignerId(CK_NES, designerId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerId The value of designerId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDesignerId_GreaterThan(Integer designerId) {
        regDesignerId(CK_GT, designerId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerId The value of designerId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDesignerId_LessThan(Integer designerId) {
        regDesignerId(CK_LT, designerId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerId The value of designerId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDesignerId_GreaterEqual(Integer designerId) {
        regDesignerId(CK_GE, designerId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerId The value of designerId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDesignerId_LessEqual(Integer designerId) {
        regDesignerId(CK_LE, designerId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param minNumber The min number of designerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of designerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDesignerId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDesignerId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param minNumber The min number of designerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of designerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDesignerId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDesignerId(), "DESIGNER_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerIdList The collection of designerId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDesignerId_InScope(Collection<Integer> designerIdList) {
        doSetDesignerId_InScope(designerIdList);
    }

    protected void doSetDesignerId_InScope(Collection<Integer> designerIdList) {
        regINS(CK_INS, cTL(designerIdList), xgetCValueDesignerId(), "DESIGNER_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer}
     * @param designerIdList The collection of designerId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDesignerId_NotInScope(Collection<Integer> designerIdList) {
        doSetDesignerId_NotInScope(designerIdList);
    }

    protected void doSetDesignerId_NotInScope(Collection<Integer> designerIdList) {
        regINS(CK_NINS, cTL(designerIdList), xgetCValueDesignerId(), "DESIGNER_ID");
    }

    protected void regDesignerId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDesignerId(), "DESIGNER_ID"); }
    protected abstract ConditionValue xgetCValueDesignerId();

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
    public HpSLCFunction<CharaGroupCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, CharaGroupCB.class);
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
    public HpSLCFunction<CharaGroupCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, CharaGroupCB.class);
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
    public HpSLCFunction<CharaGroupCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, CharaGroupCB.class);
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
    public HpSLCFunction<CharaGroupCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, CharaGroupCB.class);
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
    public HpSLCFunction<CharaGroupCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, CharaGroupCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;CharaGroupCB&gt;() {
     *     public void query(CharaGroupCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<CharaGroupCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, CharaGroupCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        CharaGroupCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(CharaGroupCQ sq);

    protected CharaGroupCB xcreateScalarConditionCB() {
        CharaGroupCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected CharaGroupCB xcreateScalarConditionPartitionByCB() {
        CharaGroupCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<CharaGroupCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CharaGroupCB cb = new CharaGroupCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "CHARA_GROUP_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(CharaGroupCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<CharaGroupCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(CharaGroupCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CharaGroupCB cb = new CharaGroupCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "CHARA_GROUP_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(CharaGroupCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<CharaGroupCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        CharaGroupCB cb = new CharaGroupCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(CharaGroupCQ sq);

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
    protected CharaGroupCB newMyCB() {
        return new CharaGroupCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return CharaGroupCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
