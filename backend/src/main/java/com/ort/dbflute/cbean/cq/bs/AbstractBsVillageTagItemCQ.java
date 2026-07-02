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
 * The abstract condition-query of village_tag_item.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVillageTagItemCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVillageTagItemCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "village_tag_item";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     * @param villageTagItemCode The value of villageTagItemCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageTagItemCode_Equal(String villageTagItemCode) {
        doSetVillageTagItemCode_Equal(fRES(villageTagItemCode));
    }

    /**
     * Equal(=). As VillageTagItem. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageTagItemCode_Equal_AsVillageTagItem(CDef.VillageTagItem cdef) {
        doSetVillageTagItemCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 誰歓 (ANYONE_WELCOME). And OnlyOnceRegistered. <br>
     * 誰歓
     */
    public void setVillageTagItemCode_Equal_誰歓() {
        setVillageTagItemCode_Equal_AsVillageTagItem(CDef.VillageTagItem.誰歓);
    }

    /**
     * Equal(=). As R15 (R15). And OnlyOnceRegistered. <br>
     * R15
     */
    public void setVillageTagItemCode_Equal_R15() {
        setVillageTagItemCode_Equal_AsVillageTagItem(CDef.VillageTagItem.R15);
    }

    /**
     * Equal(=). As R18 (R18). And OnlyOnceRegistered. <br>
     * R18
     */
    public void setVillageTagItemCode_Equal_R18() {
        setVillageTagItemCode_Equal_AsVillageTagItem(CDef.VillageTagItem.R18);
    }

    /**
     * Equal(=). As 身内 (RELATIVES_ONLY). And OnlyOnceRegistered. <br>
     * 身内
     */
    public void setVillageTagItemCode_Equal_身内() {
        setVillageTagItemCode_Equal_AsVillageTagItem(CDef.VillageTagItem.身内);
    }

    protected void doSetVillageTagItemCode_Equal(String villageTagItemCode) {
        regVillageTagItemCode(CK_EQ, villageTagItemCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     * @param villageTagItemCode The value of villageTagItemCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageTagItemCode_NotEqual(String villageTagItemCode) {
        doSetVillageTagItemCode_NotEqual(fRES(villageTagItemCode));
    }

    /**
     * NotEqual(&lt;&gt;). As VillageTagItem. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageTagItemCode_NotEqual_AsVillageTagItem(CDef.VillageTagItem cdef) {
        doSetVillageTagItemCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 誰歓 (ANYONE_WELCOME). And OnlyOnceRegistered. <br>
     * 誰歓
     */
    public void setVillageTagItemCode_NotEqual_誰歓() {
        setVillageTagItemCode_NotEqual_AsVillageTagItem(CDef.VillageTagItem.誰歓);
    }

    /**
     * NotEqual(&lt;&gt;). As R15 (R15). And OnlyOnceRegistered. <br>
     * R15
     */
    public void setVillageTagItemCode_NotEqual_R15() {
        setVillageTagItemCode_NotEqual_AsVillageTagItem(CDef.VillageTagItem.R15);
    }

    /**
     * NotEqual(&lt;&gt;). As R18 (R18). And OnlyOnceRegistered. <br>
     * R18
     */
    public void setVillageTagItemCode_NotEqual_R18() {
        setVillageTagItemCode_NotEqual_AsVillageTagItem(CDef.VillageTagItem.R18);
    }

    /**
     * NotEqual(&lt;&gt;). As 身内 (RELATIVES_ONLY). And OnlyOnceRegistered. <br>
     * 身内
     */
    public void setVillageTagItemCode_NotEqual_身内() {
        setVillageTagItemCode_NotEqual_AsVillageTagItem(CDef.VillageTagItem.身内);
    }

    protected void doSetVillageTagItemCode_NotEqual(String villageTagItemCode) {
        regVillageTagItemCode(CK_NES, villageTagItemCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     * @param villageTagItemCodeList The collection of villageTagItemCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageTagItemCode_InScope(Collection<String> villageTagItemCodeList) {
        doSetVillageTagItemCode_InScope(villageTagItemCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As VillageTagItem. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemCode_InScope_AsVillageTagItem(Collection<CDef.VillageTagItem> cdefList) {
        doSetVillageTagItemCode_InScope(cTStrL(cdefList));
    }

    protected void doSetVillageTagItemCode_InScope(Collection<String> villageTagItemCodeList) {
        regINS(CK_INS, cTL(villageTagItemCodeList), xgetCValueVillageTagItemCode(), "VILLAGE_TAG_ITEM_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     * @param villageTagItemCodeList The collection of villageTagItemCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageTagItemCode_NotInScope(Collection<String> villageTagItemCodeList) {
        doSetVillageTagItemCode_NotInScope(villageTagItemCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As VillageTagItem. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemCode_NotInScope_AsVillageTagItem(Collection<CDef.VillageTagItem> cdefList) {
        doSetVillageTagItemCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetVillageTagItemCode_NotInScope(Collection<String> villageTagItemCodeList) {
        regINS(CK_NINS, cTL(villageTagItemCodeList), xgetCValueVillageTagItemCode(), "VILLAGE_TAG_ITEM_CODE");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_TAG_ITEM_CODE from village_tag where ...)} <br>
     * village_tag by VILLAGE_TAG_ITEM_CODE, named 'villageTagAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillageTag</span>(tagCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     tagCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageTagList for 'exists'. (NotNull)
     */
    public void existsVillageTag(SubQuery<VillageTagCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageTagItemCode_ExistsReferrer_VillageTagList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_TAG_ITEM_CODE", "VILLAGE_TAG_ITEM_CODE", pp, "villageTagList");
    }
    public abstract String keepVillageTagItemCode_ExistsReferrer_VillageTagList(VillageTagCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_TAG_ITEM_CODE from village_tag where ...)} <br>
     * village_tag by VILLAGE_TAG_ITEM_CODE, named 'villageTagAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillageTag</span>(tagCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     tagCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageTagItemCode_NotExistsReferrer_VillageTagList for 'not exists'. (NotNull)
     */
    public void notExistsVillageTag(SubQuery<VillageTagCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageTagItemCode_NotExistsReferrer_VillageTagList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_TAG_ITEM_CODE", "VILLAGE_TAG_ITEM_CODE", pp, "villageTagList");
    }
    public abstract String keepVillageTagItemCode_NotExistsReferrer_VillageTagList(VillageTagCQ sq);

    public void xsderiveVillageTagList(String fn, SubQuery<VillageTagCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageTagItemCode_SpecifyDerivedReferrer_VillageTagList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_TAG_ITEM_CODE", "VILLAGE_TAG_ITEM_CODE", pp, "villageTagList", al, op);
    }
    public abstract String keepVillageTagItemCode_SpecifyDerivedReferrer_VillageTagList(VillageTagCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_tag where ...)} <br>
     * village_tag by VILLAGE_TAG_ITEM_CODE, named 'villageTagAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillageTag()</span>.<span style="color: #CC4747">max</span>(tagCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     tagCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     tagCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillageTagCB> derivedVillageTag() {
        return xcreateQDRFunctionVillageTagList();
    }
    protected HpQDRFunction<VillageTagCB> xcreateQDRFunctionVillageTagList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillageTagList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillageTagList(String fn, SubQuery<VillageTagCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageTagItemCode_QueryDerivedReferrer_VillageTagList(cb.query()); String prpp = keepVillageTagItemCode_QueryDerivedReferrer_VillageTagListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_TAG_ITEM_CODE", "VILLAGE_TAG_ITEM_CODE", sqpp, "villageTagList", rd, vl, prpp, op);
    }
    public abstract String keepVillageTagItemCode_QueryDerivedReferrer_VillageTagList(VillageTagCQ sq);
    public abstract String keepVillageTagItemCode_QueryDerivedReferrer_VillageTagListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     */
    public void setVillageTagItemCode_IsNull() { regVillageTagItemCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     */
    public void setVillageTagItemCode_IsNotNull() { regVillageTagItemCode(CK_ISNN, DOBJ); }

    protected void regVillageTagItemCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageTagItemCode(), "VILLAGE_TAG_ITEM_CODE"); }
    protected abstract ConditionValue xgetCValueVillageTagItemCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_Equal(String villageTagItemName) {
        doSetVillageTagItemName_Equal(fRES(villageTagItemName));
    }

    protected void doSetVillageTagItemName_Equal(String villageTagItemName) {
        regVillageTagItemName(CK_EQ, villageTagItemName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_NotEqual(String villageTagItemName) {
        doSetVillageTagItemName_NotEqual(fRES(villageTagItemName));
    }

    protected void doSetVillageTagItemName_NotEqual(String villageTagItemName) {
        regVillageTagItemName(CK_NES, villageTagItemName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_GreaterThan(String villageTagItemName) {
        regVillageTagItemName(CK_GT, fRES(villageTagItemName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_LessThan(String villageTagItemName) {
        regVillageTagItemName(CK_LT, fRES(villageTagItemName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_GreaterEqual(String villageTagItemName) {
        regVillageTagItemName(CK_GE, fRES(villageTagItemName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_LessEqual(String villageTagItemName) {
        regVillageTagItemName(CK_LE, fRES(villageTagItemName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemNameList The collection of villageTagItemName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_InScope(Collection<String> villageTagItemNameList) {
        doSetVillageTagItemName_InScope(villageTagItemNameList);
    }

    protected void doSetVillageTagItemName_InScope(Collection<String> villageTagItemNameList) {
        regINS(CK_INS, cTL(villageTagItemNameList), xgetCValueVillageTagItemName(), "VILLAGE_TAG_ITEM_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemNameList The collection of villageTagItemName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageTagItemName_NotInScope(Collection<String> villageTagItemNameList) {
        doSetVillageTagItemName_NotInScope(villageTagItemNameList);
    }

    protected void doSetVillageTagItemName_NotInScope(Collection<String> villageTagItemNameList) {
        regINS(CK_NINS, cTL(villageTagItemNameList), xgetCValueVillageTagItemName(), "VILLAGE_TAG_ITEM_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)} <br>
     * <pre>e.g. setVillageTagItemName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param villageTagItemName The value of villageTagItemName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillageTagItemName_LikeSearch(String villageTagItemName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillageTagItemName_LikeSearch(villageTagItemName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)} <br>
     * <pre>e.g. setVillageTagItemName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param villageTagItemName The value of villageTagItemName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setVillageTagItemName_LikeSearch(String villageTagItemName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(villageTagItemName), xgetCValueVillageTagItemName(), "VILLAGE_TAG_ITEM_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillageTagItemName_NotLikeSearch(String villageTagItemName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillageTagItemName_NotLikeSearch(villageTagItemName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @param villageTagItemName The value of villageTagItemName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setVillageTagItemName_NotLikeSearch(String villageTagItemName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(villageTagItemName), xgetCValueVillageTagItemName(), "VILLAGE_TAG_ITEM_NAME", likeSearchOption);
    }

    protected void regVillageTagItemName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageTagItemName(), "VILLAGE_TAG_ITEM_NAME"); }
    protected abstract ConditionValue xgetCValueVillageTagItemName();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrder The value of dispOrder as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDispOrder_Equal(Integer dispOrder) {
        doSetDispOrder_Equal(dispOrder);
    }

    protected void doSetDispOrder_Equal(Integer dispOrder) {
        regDispOrder(CK_EQ, dispOrder);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrder The value of dispOrder as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDispOrder_NotEqual(Integer dispOrder) {
        doSetDispOrder_NotEqual(dispOrder);
    }

    protected void doSetDispOrder_NotEqual(Integer dispOrder) {
        regDispOrder(CK_NES, dispOrder);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrder The value of dispOrder as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDispOrder_GreaterThan(Integer dispOrder) {
        regDispOrder(CK_GT, dispOrder);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrder The value of dispOrder as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDispOrder_LessThan(Integer dispOrder) {
        regDispOrder(CK_LT, dispOrder);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrder The value of dispOrder as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDispOrder_GreaterEqual(Integer dispOrder) {
        regDispOrder(CK_GE, dispOrder);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrder The value of dispOrder as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDispOrder_LessEqual(Integer dispOrder) {
        regDispOrder(CK_LE, dispOrder);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of dispOrder. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of dispOrder. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDispOrder_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDispOrder_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of dispOrder. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of dispOrder. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDispOrder_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDispOrder(), "DISP_ORDER", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrderList The collection of dispOrder as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDispOrder_InScope(Collection<Integer> dispOrderList) {
        doSetDispOrder_InScope(dispOrderList);
    }

    protected void doSetDispOrder_InScope(Collection<Integer> dispOrderList) {
        regINS(CK_INS, cTL(dispOrderList), xgetCValueDispOrder(), "DISP_ORDER");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @param dispOrderList The collection of dispOrder as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDispOrder_NotInScope(Collection<Integer> dispOrderList) {
        doSetDispOrder_NotInScope(dispOrderList);
    }

    protected void doSetDispOrder_NotInScope(Collection<Integer> dispOrderList) {
        regINS(CK_NINS, cTL(dispOrderList), xgetCValueDispOrder(), "DISP_ORDER");
    }

    protected void regDispOrder(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDispOrder(), "DISP_ORDER"); }
    protected abstract ConditionValue xgetCValueDispOrder();

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
    public HpSLCFunction<VillageTagItemCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, VillageTagItemCB.class);
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
    public HpSLCFunction<VillageTagItemCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, VillageTagItemCB.class);
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
    public HpSLCFunction<VillageTagItemCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, VillageTagItemCB.class);
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
    public HpSLCFunction<VillageTagItemCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, VillageTagItemCB.class);
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
    public HpSLCFunction<VillageTagItemCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, VillageTagItemCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;VillageTagItemCB&gt;() {
     *     public void query(VillageTagItemCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillageTagItemCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, VillageTagItemCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageTagItemCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(VillageTagItemCQ sq);

    protected VillageTagItemCB xcreateScalarConditionCB() {
        VillageTagItemCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected VillageTagItemCB xcreateScalarConditionPartitionByCB() {
        VillageTagItemCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<VillageTagItemCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageTagItemCB cb = new VillageTagItemCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VILLAGE_TAG_ITEM_CODE";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(VillageTagItemCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<VillageTagItemCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(VillageTagItemCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageTagItemCB cb = new VillageTagItemCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VILLAGE_TAG_ITEM_CODE";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(VillageTagItemCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<VillageTagItemCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageTagItemCB cb = new VillageTagItemCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(VillageTagItemCQ sq);

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
    protected VillageTagItemCB newMyCB() {
        return new VillageTagItemCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return VillageTagItemCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
