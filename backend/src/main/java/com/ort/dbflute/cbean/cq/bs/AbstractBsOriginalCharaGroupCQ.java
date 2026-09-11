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
 * The abstract condition-query of original_chara_group.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsOriginalCharaGroupCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsOriginalCharaGroupCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "original_chara_group";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaGroupId The value of originalCharaGroupId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_GreaterThan(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_GT, originalCharaGroupId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaGroupId The value of originalCharaGroupId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_LessThan(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_LT, originalCharaGroupId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaGroupId The value of originalCharaGroupId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_GreaterEqual(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_GE, originalCharaGroupId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaGroupId The value of originalCharaGroupId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_LessEqual(Integer originalCharaGroupId) {
        regOriginalCharaGroupId(CK_LE, originalCharaGroupId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of originalCharaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of originalCharaGroupId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setOriginalCharaGroupId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueOriginalCharaGroupId(), "ORIGINAL_CHARA_GROUP_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param originalCharaGroupIdList The collection of originalCharaGroupId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupId_NotInScope(Collection<Integer> originalCharaGroupIdList) {
        doSetOriginalCharaGroupId_NotInScope(originalCharaGroupIdList);
    }

    protected void doSetOriginalCharaGroupId_NotInScope(Collection<Integer> originalCharaGroupIdList) {
        regINS(CK_NINS, cTL(originalCharaGroupIdList), xgetCValueOriginalCharaGroupId(), "ORIGINAL_CHARA_GROUP_ID");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select ORIGINAL_CHARA_GROUP_ID from original_chara where ...)} <br>
     * original_chara by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsOriginalChara</span>(charaCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     charaCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of OriginalCharaList for 'exists'. (NotNull)
     */
    public void existsOriginalChara(SubQuery<OriginalCharaCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        OriginalCharaCB cb = new OriginalCharaCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepOriginalCharaGroupId_ExistsReferrer_OriginalCharaList(cb.query());
        registerExistsReferrer(cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", pp, "originalCharaList");
    }
    public abstract String keepOriginalCharaGroupId_ExistsReferrer_OriginalCharaList(OriginalCharaCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select ORIGINAL_CHARA_GROUP_ID from village_settings where ...)} <br>
     * village_settings by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillageSettings</span>(settingsCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     settingsCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageSettingsList for 'exists'. (NotNull)
     */
    public void existsVillageSettings(SubQuery<VillageSettingsCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepOriginalCharaGroupId_ExistsReferrer_VillageSettingsList(cb.query());
        registerExistsReferrer(cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", pp, "villageSettingsList");
    }
    public abstract String keepOriginalCharaGroupId_ExistsReferrer_VillageSettingsList(VillageSettingsCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select ORIGINAL_CHARA_GROUP_ID from original_chara where ...)} <br>
     * original_chara by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsOriginalChara</span>(charaCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     charaCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of OriginalCharaGroupId_NotExistsReferrer_OriginalCharaList for 'not exists'. (NotNull)
     */
    public void notExistsOriginalChara(SubQuery<OriginalCharaCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        OriginalCharaCB cb = new OriginalCharaCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepOriginalCharaGroupId_NotExistsReferrer_OriginalCharaList(cb.query());
        registerNotExistsReferrer(cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", pp, "originalCharaList");
    }
    public abstract String keepOriginalCharaGroupId_NotExistsReferrer_OriginalCharaList(OriginalCharaCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select ORIGINAL_CHARA_GROUP_ID from village_settings where ...)} <br>
     * village_settings by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillageSettings</span>(settingsCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     settingsCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of OriginalCharaGroupId_NotExistsReferrer_VillageSettingsList for 'not exists'. (NotNull)
     */
    public void notExistsVillageSettings(SubQuery<VillageSettingsCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepOriginalCharaGroupId_NotExistsReferrer_VillageSettingsList(cb.query());
        registerNotExistsReferrer(cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", pp, "villageSettingsList");
    }
    public abstract String keepOriginalCharaGroupId_NotExistsReferrer_VillageSettingsList(VillageSettingsCQ sq);

    public void xsderiveOriginalCharaList(String fn, SubQuery<OriginalCharaCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaCB cb = new OriginalCharaCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepOriginalCharaGroupId_SpecifyDerivedReferrer_OriginalCharaList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", pp, "originalCharaList", al, op);
    }
    public abstract String keepOriginalCharaGroupId_SpecifyDerivedReferrer_OriginalCharaList(OriginalCharaCQ sq);

    public void xsderiveVillageSettingsList(String fn, SubQuery<VillageSettingsCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepOriginalCharaGroupId_SpecifyDerivedReferrer_VillageSettingsList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", pp, "villageSettingsList", al, op);
    }
    public abstract String keepOriginalCharaGroupId_SpecifyDerivedReferrer_VillageSettingsList(VillageSettingsCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from original_chara where ...)} <br>
     * original_chara by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedOriginalChara()</span>.<span style="color: #CC4747">max</span>(charaCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     charaCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     charaCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<OriginalCharaCB> derivedOriginalChara() {
        return xcreateQDRFunctionOriginalCharaList();
    }
    protected HpQDRFunction<OriginalCharaCB> xcreateQDRFunctionOriginalCharaList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveOriginalCharaList(fn, sq, rd, vl, op));
    }
    public void xqderiveOriginalCharaList(String fn, SubQuery<OriginalCharaCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaCB cb = new OriginalCharaCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaList(cb.query()); String prpp = keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", sqpp, "originalCharaList", rd, vl, prpp, op);
    }
    public abstract String keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaList(OriginalCharaCQ sq);
    public abstract String keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_settings where ...)} <br>
     * village_settings by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillageSettings()</span>.<span style="color: #CC4747">max</span>(settingsCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     settingsCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     settingsCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillageSettingsCB> derivedVillageSettings() {
        return xcreateQDRFunctionVillageSettingsList();
    }
    protected HpQDRFunction<VillageSettingsCB> xcreateQDRFunctionVillageSettingsList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillageSettingsList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillageSettingsList(String fn, SubQuery<VillageSettingsCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsList(cb.query()); String prpp = keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", sqpp, "villageSettingsList", rd, vl, prpp, op);
    }
    public abstract String keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsList(VillageSettingsCQ sq);
    public abstract String keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setOriginalCharaGroupId_IsNull() { regOriginalCharaGroupId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setOriginalCharaGroupId_IsNotNull() { regOriginalCharaGroupId(CK_ISNN, DOBJ); }

    protected void regOriginalCharaGroupId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaGroupId(), "ORIGINAL_CHARA_GROUP_ID"); }
    protected abstract ConditionValue xgetCValueOriginalCharaGroupId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_Equal(String originalCharaGroupName) {
        doSetOriginalCharaGroupName_Equal(fRES(originalCharaGroupName));
    }

    protected void doSetOriginalCharaGroupName_Equal(String originalCharaGroupName) {
        regOriginalCharaGroupName(CK_EQ, originalCharaGroupName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_NotEqual(String originalCharaGroupName) {
        doSetOriginalCharaGroupName_NotEqual(fRES(originalCharaGroupName));
    }

    protected void doSetOriginalCharaGroupName_NotEqual(String originalCharaGroupName) {
        regOriginalCharaGroupName(CK_NES, originalCharaGroupName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_GreaterThan(String originalCharaGroupName) {
        regOriginalCharaGroupName(CK_GT, fRES(originalCharaGroupName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_LessThan(String originalCharaGroupName) {
        regOriginalCharaGroupName(CK_LT, fRES(originalCharaGroupName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_GreaterEqual(String originalCharaGroupName) {
        regOriginalCharaGroupName(CK_GE, fRES(originalCharaGroupName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_LessEqual(String originalCharaGroupName) {
        regOriginalCharaGroupName(CK_LE, fRES(originalCharaGroupName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupNameList The collection of originalCharaGroupName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_InScope(Collection<String> originalCharaGroupNameList) {
        doSetOriginalCharaGroupName_InScope(originalCharaGroupNameList);
    }

    protected void doSetOriginalCharaGroupName_InScope(Collection<String> originalCharaGroupNameList) {
        regINS(CK_INS, cTL(originalCharaGroupNameList), xgetCValueOriginalCharaGroupName(), "ORIGINAL_CHARA_GROUP_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupNameList The collection of originalCharaGroupName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setOriginalCharaGroupName_NotInScope(Collection<String> originalCharaGroupNameList) {
        doSetOriginalCharaGroupName_NotInScope(originalCharaGroupNameList);
    }

    protected void doSetOriginalCharaGroupName_NotInScope(Collection<String> originalCharaGroupNameList) {
        regINS(CK_NINS, cTL(originalCharaGroupNameList), xgetCValueOriginalCharaGroupName(), "ORIGINAL_CHARA_GROUP_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setOriginalCharaGroupName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param originalCharaGroupName The value of originalCharaGroupName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setOriginalCharaGroupName_LikeSearch(String originalCharaGroupName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setOriginalCharaGroupName_LikeSearch(originalCharaGroupName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setOriginalCharaGroupName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param originalCharaGroupName The value of originalCharaGroupName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setOriginalCharaGroupName_LikeSearch(String originalCharaGroupName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(originalCharaGroupName), xgetCValueOriginalCharaGroupName(), "ORIGINAL_CHARA_GROUP_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setOriginalCharaGroupName_NotLikeSearch(String originalCharaGroupName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setOriginalCharaGroupName_NotLikeSearch(originalCharaGroupName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @param originalCharaGroupName The value of originalCharaGroupName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setOriginalCharaGroupName_NotLikeSearch(String originalCharaGroupName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(originalCharaGroupName), xgetCValueOriginalCharaGroupName(), "ORIGINAL_CHARA_GROUP_NAME", likeSearchOption);
    }

    protected void regOriginalCharaGroupName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueOriginalCharaGroupName(), "ORIGINAL_CHARA_GROUP_NAME"); }
    protected abstract ConditionValue xgetCValueOriginalCharaGroupName();

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
    public HpSLCFunction<OriginalCharaGroupCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, OriginalCharaGroupCB.class);
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
    public HpSLCFunction<OriginalCharaGroupCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, OriginalCharaGroupCB.class);
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
    public HpSLCFunction<OriginalCharaGroupCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, OriginalCharaGroupCB.class);
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
    public HpSLCFunction<OriginalCharaGroupCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, OriginalCharaGroupCB.class);
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
    public HpSLCFunction<OriginalCharaGroupCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, OriginalCharaGroupCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;OriginalCharaGroupCB&gt;() {
     *     public void query(OriginalCharaGroupCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<OriginalCharaGroupCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, OriginalCharaGroupCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaGroupCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(OriginalCharaGroupCQ sq);

    protected OriginalCharaGroupCB xcreateScalarConditionCB() {
        OriginalCharaGroupCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected OriginalCharaGroupCB xcreateScalarConditionPartitionByCB() {
        OriginalCharaGroupCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<OriginalCharaGroupCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaGroupCB cb = new OriginalCharaGroupCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "ORIGINAL_CHARA_GROUP_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(OriginalCharaGroupCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<OriginalCharaGroupCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(OriginalCharaGroupCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        OriginalCharaGroupCB cb = new OriginalCharaGroupCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "ORIGINAL_CHARA_GROUP_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(OriginalCharaGroupCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<OriginalCharaGroupCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        OriginalCharaGroupCB cb = new OriginalCharaGroupCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(OriginalCharaGroupCQ sq);

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
    protected OriginalCharaGroupCB newMyCB() {
        return new OriginalCharaGroupCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return OriginalCharaGroupCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
