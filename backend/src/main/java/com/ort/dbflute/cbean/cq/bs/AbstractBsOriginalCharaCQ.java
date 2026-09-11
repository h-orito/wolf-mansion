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
 * The abstract condition-query of original_chara.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsOriginalCharaCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsOriginalCharaCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "original_chara";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaId The value of originalCharaId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_Equal(Integer originalCharaId) {
        doSetOriginalCharaId_Equal(originalCharaId);
    }

    protected void doSetOriginalCharaId_Equal(Integer originalCharaId) {
        regOriginalCharaId(CK_EQ, originalCharaId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaId The value of originalCharaId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_NotEqual(Integer originalCharaId) {
        doSetOriginalCharaId_NotEqual(originalCharaId);
    }

    protected void doSetOriginalCharaId_NotEqual(Integer originalCharaId) {
        regOriginalCharaId(CK_NES, originalCharaId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaId The value of originalCharaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_GreaterThan(Integer originalCharaId) {
        regOriginalCharaId(CK_GT, originalCharaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaId The value of originalCharaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_LessThan(Integer originalCharaId) {
        regOriginalCharaId(CK_LT, originalCharaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaId The value of originalCharaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_GreaterEqual(Integer originalCharaId) {
        regOriginalCharaId(CK_GE, originalCharaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaId The value of originalCharaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaId_LessEqual(Integer originalCharaId) {
        regOriginalCharaId(CK_LE, originalCharaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setOriginalCharaId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setOriginalCharaId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setOriginalCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaIdList The collection of originalCharaId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaId_InScope(Collection<Integer> originalCharaIdList) {
        doSetOriginalCharaId_InScope(originalCharaIdList);
    }

    protected void doSetOriginalCharaId_InScope(Collection<Integer> originalCharaIdList) {
        regINS(CK_INS, cTL(originalCharaIdList), xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaIdList The collection of originalCharaId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaId_NotInScope(Collection<Integer> originalCharaIdList) {
        doSetOriginalCharaId_NotInScope(originalCharaIdList);
    }

    protected void doSetOriginalCharaId_NotInScope(Collection<Integer> originalCharaIdList) {
        regINS(CK_NINS, cTL(originalCharaIdList), xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select ORIGINAL_CHARA_ID from original_chara_image where ...)} <br>
     * original_chara_image by ORIGINAL_CHARA_ID, named 'originalCharaImageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsOriginalCharaImage</span>(imageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     imageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of OriginalCharaImageList for 'exists'. (NotNull)
     */
    public void existsOriginalCharaImage(SubQuery<OriginalCharaImageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        OriginalCharaImageCB cb = new OriginalCharaImageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepOriginalCharaId_ExistsReferrer_OriginalCharaImageList(cb.query());
        registerExistsReferrer(cb.query(), "ORIGINAL_CHARA_ID", "ORIGINAL_CHARA_ID", pp, "originalCharaImageList");
    }
    public abstract String keepOriginalCharaId_ExistsReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select ORIGINAL_CHARA_ID from original_chara_image where ...)} <br>
     * original_chara_image by ORIGINAL_CHARA_ID, named 'originalCharaImageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsOriginalCharaImage</span>(imageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     imageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of OriginalCharaId_NotExistsReferrer_OriginalCharaImageList for 'not exists'. (NotNull)
     */
    public void notExistsOriginalCharaImage(SubQuery<OriginalCharaImageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        OriginalCharaImageCB cb = new OriginalCharaImageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepOriginalCharaId_NotExistsReferrer_OriginalCharaImageList(cb.query());
        registerNotExistsReferrer(cb.query(), "ORIGINAL_CHARA_ID", "ORIGINAL_CHARA_ID", pp, "originalCharaImageList");
    }
    public abstract String keepOriginalCharaId_NotExistsReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq);

    public void xsderiveOriginalCharaImageList(String fn, SubQuery<OriginalCharaImageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaImageCB cb = new OriginalCharaImageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepOriginalCharaId_SpecifyDerivedReferrer_OriginalCharaImageList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "ORIGINAL_CHARA_ID", "ORIGINAL_CHARA_ID", pp, "originalCharaImageList", al, op);
    }
    public abstract String keepOriginalCharaId_SpecifyDerivedReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from original_chara_image where ...)} <br>
     * original_chara_image by ORIGINAL_CHARA_ID, named 'originalCharaImageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedOriginalCharaImage()</span>.<span style="color: #CC4747">max</span>(imageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     imageCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     imageCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<OriginalCharaImageCB> derivedOriginalCharaImage() {
        return xcreateQDRFunctionOriginalCharaImageList();
    }
    protected HpQDRFunction<OriginalCharaImageCB> xcreateQDRFunctionOriginalCharaImageList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveOriginalCharaImageList(fn, sq, rd, vl, op));
    }
    public void xqderiveOriginalCharaImageList(String fn, SubQuery<OriginalCharaImageCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaImageCB cb = new OriginalCharaImageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepOriginalCharaId_QueryDerivedReferrer_OriginalCharaImageList(cb.query()); String prpp = keepOriginalCharaId_QueryDerivedReferrer_OriginalCharaImageListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "ORIGINAL_CHARA_ID", "ORIGINAL_CHARA_ID", sqpp, "originalCharaImageList", rd, vl, prpp, op);
    }
    public abstract String keepOriginalCharaId_QueryDerivedReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq);
    public abstract String keepOriginalCharaId_QueryDerivedReferrer_OriginalCharaImageListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setOriginalCharaId_IsNull() { regOriginalCharaId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setOriginalCharaId_IsNotNull() { regOriginalCharaId(CK_ISNN, DOBJ); }

    protected void regOriginalCharaId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaId(), "ORIGINAL_CHARA_ID"); }
    protected abstract ConditionValue xgetCValueOriginalCharaId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_Equal(String originalCharaName) {
        doSetOriginalCharaName_Equal(fRES(originalCharaName));
    }

    protected void doSetOriginalCharaName_Equal(String originalCharaName) {
        regOriginalCharaName(CK_EQ, originalCharaName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_NotEqual(String originalCharaName) {
        doSetOriginalCharaName_NotEqual(fRES(originalCharaName));
    }

    protected void doSetOriginalCharaName_NotEqual(String originalCharaName) {
        regOriginalCharaName(CK_NES, originalCharaName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_GreaterThan(String originalCharaName) {
        regOriginalCharaName(CK_GT, fRES(originalCharaName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_LessThan(String originalCharaName) {
        regOriginalCharaName(CK_LT, fRES(originalCharaName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_GreaterEqual(String originalCharaName) {
        regOriginalCharaName(CK_GE, fRES(originalCharaName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_LessEqual(String originalCharaName) {
        regOriginalCharaName(CK_LE, fRES(originalCharaName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaNameList The collection of originalCharaName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_InScope(Collection<String> originalCharaNameList) {
        doSetOriginalCharaName_InScope(originalCharaNameList);
    }

    protected void doSetOriginalCharaName_InScope(Collection<String> originalCharaNameList) {
        regINS(CK_INS, cTL(originalCharaNameList), xgetCValueOriginalCharaName(), "ORIGINAL_CHARA_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaNameList The collection of originalCharaName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaName_NotInScope(Collection<String> originalCharaNameList) {
        doSetOriginalCharaName_NotInScope(originalCharaNameList);
    }

    protected void doSetOriginalCharaName_NotInScope(Collection<String> originalCharaNameList) {
        regINS(CK_NINS, cTL(originalCharaNameList), xgetCValueOriginalCharaName(), "ORIGINAL_CHARA_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setOriginalCharaName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param originalCharaName The value of originalCharaName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setOriginalCharaName_LikeSearch(String originalCharaName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setOriginalCharaName_LikeSearch(originalCharaName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setOriginalCharaName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param originalCharaName The value of originalCharaName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setOriginalCharaName_LikeSearch(String originalCharaName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(originalCharaName), xgetCValueOriginalCharaName(), "ORIGINAL_CHARA_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setOriginalCharaName_NotLikeSearch(String originalCharaName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setOriginalCharaName_NotLikeSearch(originalCharaName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaName The value of originalCharaName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setOriginalCharaName_NotLikeSearch(String originalCharaName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(originalCharaName), xgetCValueOriginalCharaName(), "ORIGINAL_CHARA_NAME", likeSearchOption);
    }

    protected void regOriginalCharaName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaName(), "ORIGINAL_CHARA_NAME"); }
    protected abstract ConditionValue xgetCValueOriginalCharaName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_Equal(String originalCharaShortName) {
        doSetOriginalCharaShortName_Equal(fRES(originalCharaShortName));
    }

    protected void doSetOriginalCharaShortName_Equal(String originalCharaShortName) {
        regOriginalCharaShortName(CK_EQ, originalCharaShortName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_NotEqual(String originalCharaShortName) {
        doSetOriginalCharaShortName_NotEqual(fRES(originalCharaShortName));
    }

    protected void doSetOriginalCharaShortName_NotEqual(String originalCharaShortName) {
        regOriginalCharaShortName(CK_NES, originalCharaShortName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_GreaterThan(String originalCharaShortName) {
        regOriginalCharaShortName(CK_GT, fRES(originalCharaShortName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_LessThan(String originalCharaShortName) {
        regOriginalCharaShortName(CK_LT, fRES(originalCharaShortName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_GreaterEqual(String originalCharaShortName) {
        regOriginalCharaShortName(CK_GE, fRES(originalCharaShortName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_LessEqual(String originalCharaShortName) {
        regOriginalCharaShortName(CK_LE, fRES(originalCharaShortName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortNameList The collection of originalCharaShortName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_InScope(Collection<String> originalCharaShortNameList) {
        doSetOriginalCharaShortName_InScope(originalCharaShortNameList);
    }

    protected void doSetOriginalCharaShortName_InScope(Collection<String> originalCharaShortNameList) {
        regINS(CK_INS, cTL(originalCharaShortNameList), xgetCValueOriginalCharaShortName(), "ORIGINAL_CHARA_SHORT_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortNameList The collection of originalCharaShortName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaShortName_NotInScope(Collection<String> originalCharaShortNameList) {
        doSetOriginalCharaShortName_NotInScope(originalCharaShortNameList);
    }

    protected void doSetOriginalCharaShortName_NotInScope(Collection<String> originalCharaShortNameList) {
        regINS(CK_NINS, cTL(originalCharaShortNameList), xgetCValueOriginalCharaShortName(), "ORIGINAL_CHARA_SHORT_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * <pre>e.g. setOriginalCharaShortName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param originalCharaShortName The value of originalCharaShortName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setOriginalCharaShortName_LikeSearch(String originalCharaShortName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setOriginalCharaShortName_LikeSearch(originalCharaShortName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * <pre>e.g. setOriginalCharaShortName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param originalCharaShortName The value of originalCharaShortName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setOriginalCharaShortName_LikeSearch(String originalCharaShortName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(originalCharaShortName), xgetCValueOriginalCharaShortName(), "ORIGINAL_CHARA_SHORT_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setOriginalCharaShortName_NotLikeSearch(String originalCharaShortName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setOriginalCharaShortName_NotLikeSearch(originalCharaShortName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param originalCharaShortName The value of originalCharaShortName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setOriginalCharaShortName_NotLikeSearch(String originalCharaShortName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(originalCharaShortName), xgetCValueOriginalCharaShortName(), "ORIGINAL_CHARA_SHORT_NAME", likeSearchOption);
    }

    protected void regOriginalCharaShortName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaShortName(), "ORIGINAL_CHARA_SHORT_NAME"); }
    protected abstract ConditionValue xgetCValueOriginalCharaShortName();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupId The value of originalCharaGroupId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_Equal(Integer originalCharaGroupId) {
        doSetOriginalCharaGroupId_Equal(originalCharaGroupId);
    }

    protected void doSetOriginalCharaGroupId_Equal(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_EQ, originalCharaGroupId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupId The value of originalCharaGroupId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_NotEqual(Integer originalCharaGroupId) {
        doSetOriginalCharaGroupId_NotEqual(originalCharaGroupId);
    }

    protected void doSetOriginalCharaGroupId_NotEqual(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_NES, originalCharaGroupId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupId The value of originalCharaGroupId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_GreaterThan(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_GT, originalCharaGroupId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupId The value of originalCharaGroupId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_LessThan(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_LT, originalCharaGroupId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupId The value of originalCharaGroupId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_GreaterEqual(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_GE, originalCharaGroupId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupId The value of originalCharaGroupId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_LessEqual(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_LE, originalCharaGroupId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param minNumber The min number of originalCharaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setOriginalCharaGroupId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setOriginalCharaGroupId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param minNumber The min number of originalCharaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setOriginalCharaGroupId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueOriginalCharaGroupId(), "ORIGINAL_CHARA_GROUP_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupIdList The collection of originalCharaGroupId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_InScope(Collection<Integer> originalCharaGroupIdList) {
        doSetOriginalCharaGroupId_InScope(originalCharaGroupIdList);
    }

    protected void doSetOriginalCharaGroupId_InScope(Collection<Integer> originalCharaGroupIdList) {
        regINS(CK_INS, cTL(originalCharaGroupIdList), xgetCValueOriginalCharaGroupId(), "ORIGINAL_CHARA_GROUP_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group}
     * @param originalCharaGroupIdList The collection of originalCharaGroupId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_NotInScope(Collection<Integer> originalCharaGroupIdList) {
        doSetOriginalCharaGroupId_NotInScope(originalCharaGroupIdList);
    }

    protected void doSetOriginalCharaGroupId_NotInScope(Collection<Integer> originalCharaGroupIdList) {
        regINS(CK_NINS, cTL(originalCharaGroupIdList), xgetCValueOriginalCharaGroupId(), "ORIGINAL_CHARA_GROUP_ID");
    }

    protected void regOriginalCharaGroupId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaGroupId(), "ORIGINAL_CHARA_GROUP_ID"); }
    protected abstract ConditionValue xgetCValueOriginalCharaGroupId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidth The value of displayWidth as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayWidth_Equal(Integer displayWidth) {
        doSetDisplayWidth_Equal(displayWidth);
    }

    protected void doSetDisplayWidth_Equal(Integer displayWidth) {
        regDisplayWidth(CK_EQ, displayWidth);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidth The value of displayWidth as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayWidth_NotEqual(Integer displayWidth) {
        doSetDisplayWidth_NotEqual(displayWidth);
    }

    protected void doSetDisplayWidth_NotEqual(Integer displayWidth) {
        regDisplayWidth(CK_NES, displayWidth);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidth The value of displayWidth as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayWidth_GreaterThan(Integer displayWidth) {
        regDisplayWidth(CK_GT, displayWidth);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidth The value of displayWidth as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayWidth_LessThan(Integer displayWidth) {
        regDisplayWidth(CK_LT, displayWidth);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidth The value of displayWidth as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayWidth_GreaterEqual(Integer displayWidth) {
        regDisplayWidth(CK_GE, displayWidth);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidth The value of displayWidth as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayWidth_LessEqual(Integer displayWidth) {
        regDisplayWidth(CK_LE, displayWidth);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of displayWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of displayWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDisplayWidth_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDisplayWidth_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of displayWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of displayWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDisplayWidth_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDisplayWidth(), "DISPLAY_WIDTH", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidthList The collection of displayWidth as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDisplayWidth_InScope(Collection<Integer> displayWidthList) {
        doSetDisplayWidth_InScope(displayWidthList);
    }

    protected void doSetDisplayWidth_InScope(Collection<Integer> displayWidthList) {
        regINS(CK_INS, cTL(displayWidthList), xgetCValueDisplayWidth(), "DISPLAY_WIDTH");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @param displayWidthList The collection of displayWidth as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDisplayWidth_NotInScope(Collection<Integer> displayWidthList) {
        doSetDisplayWidth_NotInScope(displayWidthList);
    }

    protected void doSetDisplayWidth_NotInScope(Collection<Integer> displayWidthList) {
        regINS(CK_NINS, cTL(displayWidthList), xgetCValueDisplayWidth(), "DISPLAY_WIDTH");
    }

    protected void regDisplayWidth(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDisplayWidth(), "DISPLAY_WIDTH"); }
    protected abstract ConditionValue xgetCValueDisplayWidth();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeight The value of displayHeight as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayHeight_Equal(Integer displayHeight) {
        doSetDisplayHeight_Equal(displayHeight);
    }

    protected void doSetDisplayHeight_Equal(Integer displayHeight) {
        regDisplayHeight(CK_EQ, displayHeight);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeight The value of displayHeight as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayHeight_NotEqual(Integer displayHeight) {
        doSetDisplayHeight_NotEqual(displayHeight);
    }

    protected void doSetDisplayHeight_NotEqual(Integer displayHeight) {
        regDisplayHeight(CK_NES, displayHeight);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeight The value of displayHeight as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayHeight_GreaterThan(Integer displayHeight) {
        regDisplayHeight(CK_GT, displayHeight);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeight The value of displayHeight as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayHeight_LessThan(Integer displayHeight) {
        regDisplayHeight(CK_LT, displayHeight);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeight The value of displayHeight as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayHeight_GreaterEqual(Integer displayHeight) {
        regDisplayHeight(CK_GE, displayHeight);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeight The value of displayHeight as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDisplayHeight_LessEqual(Integer displayHeight) {
        regDisplayHeight(CK_LE, displayHeight);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of displayHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of displayHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDisplayHeight_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDisplayHeight_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of displayHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of displayHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDisplayHeight_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDisplayHeight(), "DISPLAY_HEIGHT", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeightList The collection of displayHeight as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDisplayHeight_InScope(Collection<Integer> displayHeightList) {
        doSetDisplayHeight_InScope(displayHeightList);
    }

    protected void doSetDisplayHeight_InScope(Collection<Integer> displayHeightList) {
        regINS(CK_INS, cTL(displayHeightList), xgetCValueDisplayHeight(), "DISPLAY_HEIGHT");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @param displayHeightList The collection of displayHeight as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDisplayHeight_NotInScope(Collection<Integer> displayHeightList) {
        doSetDisplayHeight_NotInScope(displayHeightList);
    }

    protected void doSetDisplayHeight_NotInScope(Collection<Integer> displayHeightList) {
        regINS(CK_NINS, cTL(displayHeightList), xgetCValueDisplayHeight(), "DISPLAY_HEIGHT");
    }

    protected void regDisplayHeight(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDisplayHeight(), "DISPLAY_HEIGHT"); }
    protected abstract ConditionValue xgetCValueDisplayHeight();

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
    public HpSLCFunction<OriginalCharaCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, OriginalCharaCB.class);
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
    public HpSLCFunction<OriginalCharaCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, OriginalCharaCB.class);
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
    public HpSLCFunction<OriginalCharaCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, OriginalCharaCB.class);
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
    public HpSLCFunction<OriginalCharaCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, OriginalCharaCB.class);
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
    public HpSLCFunction<OriginalCharaCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, OriginalCharaCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;OriginalCharaCB&gt;() {
     *     public void query(OriginalCharaCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, OriginalCharaCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(OriginalCharaCQ sq);

    protected OriginalCharaCB xcreateScalarConditionCB() {
        OriginalCharaCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected OriginalCharaCB xcreateScalarConditionPartitionByCB() {
        OriginalCharaCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<OriginalCharaCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaCB cb = new OriginalCharaCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "ORIGINAL_CHARA_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(OriginalCharaCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<OriginalCharaCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(OriginalCharaCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaCB cb = new OriginalCharaCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "ORIGINAL_CHARA_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(OriginalCharaCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<OriginalCharaCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        OriginalCharaCB cb = new OriginalCharaCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(OriginalCharaCQ sq);

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
    protected OriginalCharaCB newMyCB() {
        return new OriginalCharaCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return OriginalCharaCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
