package com.ort.dbflute.cbean.cq.bs;

import java.util.Map;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import com.ort.dbflute.cbean.cq.ciq.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The base condition-query of original_chara_group.
 * @author DBFlute(AutoGenerator)
 */
public class BsOriginalCharaGroupCQ extends AbstractBsOriginalCharaGroupCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected OriginalCharaGroupCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsOriginalCharaGroupCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from original_chara_group) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public OriginalCharaGroupCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected OriginalCharaGroupCIQ xcreateCIQ() {
        OriginalCharaGroupCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected OriginalCharaGroupCIQ xnewCIQ() {
        return new OriginalCharaGroupCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join original_chara_group on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public OriginalCharaGroupCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        OriginalCharaGroupCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _originalCharaGroupId;
    public ConditionValue xdfgetOriginalCharaGroupId()
    { if (_originalCharaGroupId == null) { _originalCharaGroupId = nCV(); }
      return _originalCharaGroupId; }
    protected ConditionValue xgetCValueOriginalCharaGroupId() { return xdfgetOriginalCharaGroupId(); }

    public Map<String, OriginalCharaCQ> xdfgetOriginalCharaGroupId_ExistsReferrer_OriginalCharaList() { return xgetSQueMap("originalCharaGroupId_ExistsReferrer_OriginalCharaList"); }
    public String keepOriginalCharaGroupId_ExistsReferrer_OriginalCharaList(OriginalCharaCQ sq) { return xkeepSQue("originalCharaGroupId_ExistsReferrer_OriginalCharaList", sq); }

    public Map<String, VillageSettingsCQ> xdfgetOriginalCharaGroupId_ExistsReferrer_VillageSettingsList() { return xgetSQueMap("originalCharaGroupId_ExistsReferrer_VillageSettingsList"); }
    public String keepOriginalCharaGroupId_ExistsReferrer_VillageSettingsList(VillageSettingsCQ sq) { return xkeepSQue("originalCharaGroupId_ExistsReferrer_VillageSettingsList", sq); }

    public Map<String, OriginalCharaCQ> xdfgetOriginalCharaGroupId_NotExistsReferrer_OriginalCharaList() { return xgetSQueMap("originalCharaGroupId_NotExistsReferrer_OriginalCharaList"); }
    public String keepOriginalCharaGroupId_NotExistsReferrer_OriginalCharaList(OriginalCharaCQ sq) { return xkeepSQue("originalCharaGroupId_NotExistsReferrer_OriginalCharaList", sq); }

    public Map<String, VillageSettingsCQ> xdfgetOriginalCharaGroupId_NotExistsReferrer_VillageSettingsList() { return xgetSQueMap("originalCharaGroupId_NotExistsReferrer_VillageSettingsList"); }
    public String keepOriginalCharaGroupId_NotExistsReferrer_VillageSettingsList(VillageSettingsCQ sq) { return xkeepSQue("originalCharaGroupId_NotExistsReferrer_VillageSettingsList", sq); }

    public Map<String, OriginalCharaCQ> xdfgetOriginalCharaGroupId_SpecifyDerivedReferrer_OriginalCharaList() { return xgetSQueMap("originalCharaGroupId_SpecifyDerivedReferrer_OriginalCharaList"); }
    public String keepOriginalCharaGroupId_SpecifyDerivedReferrer_OriginalCharaList(OriginalCharaCQ sq) { return xkeepSQue("originalCharaGroupId_SpecifyDerivedReferrer_OriginalCharaList", sq); }

    public Map<String, VillageSettingsCQ> xdfgetOriginalCharaGroupId_SpecifyDerivedReferrer_VillageSettingsList() { return xgetSQueMap("originalCharaGroupId_SpecifyDerivedReferrer_VillageSettingsList"); }
    public String keepOriginalCharaGroupId_SpecifyDerivedReferrer_VillageSettingsList(VillageSettingsCQ sq) { return xkeepSQue("originalCharaGroupId_SpecifyDerivedReferrer_VillageSettingsList", sq); }

    public Map<String, OriginalCharaCQ> xdfgetOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaList() { return xgetSQueMap("originalCharaGroupId_QueryDerivedReferrer_OriginalCharaList"); }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaList(OriginalCharaCQ sq) { return xkeepSQue("originalCharaGroupId_QueryDerivedReferrer_OriginalCharaList", sq); }
    public Map<String, Object> xdfgetOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaListParameter() { return xgetSQuePmMap("originalCharaGroupId_QueryDerivedReferrer_OriginalCharaList"); }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaListParameter(Object pm) { return xkeepSQuePm("originalCharaGroupId_QueryDerivedReferrer_OriginalCharaList", pm); }

    public Map<String, VillageSettingsCQ> xdfgetOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsList() { return xgetSQueMap("originalCharaGroupId_QueryDerivedReferrer_VillageSettingsList"); }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsList(VillageSettingsCQ sq) { return xkeepSQue("originalCharaGroupId_QueryDerivedReferrer_VillageSettingsList", sq); }
    public Map<String, Object> xdfgetOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsListParameter() { return xgetSQuePmMap("originalCharaGroupId_QueryDerivedReferrer_VillageSettingsList"); }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsListParameter(Object pm) { return xkeepSQuePm("originalCharaGroupId_QueryDerivedReferrer_VillageSettingsList", pm); }

    /**
     * Add order-by as ascend. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_OriginalCharaGroupId_Asc() { regOBA("ORIGINAL_CHARA_GROUP_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_OriginalCharaGroupId_Desc() { regOBD("ORIGINAL_CHARA_GROUP_ID"); return this; }

    protected ConditionValue _originalCharaGroupName;
    public ConditionValue xdfgetOriginalCharaGroupName()
    { if (_originalCharaGroupName == null) { _originalCharaGroupName = nCV(); }
      return _originalCharaGroupName; }
    protected ConditionValue xgetCValueOriginalCharaGroupName() { return xdfgetOriginalCharaGroupName(); }

    /**
     * Add order-by as ascend. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_OriginalCharaGroupName_Asc() { regOBA("ORIGINAL_CHARA_GROUP_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_OriginalCharaGroupName_Desc() { regOBD("ORIGINAL_CHARA_GROUP_NAME"); return this; }

    protected ConditionValue _registerDatetime;
    public ConditionValue xdfgetRegisterDatetime()
    { if (_registerDatetime == null) { _registerDatetime = nCV(); }
      return _registerDatetime; }
    protected ConditionValue xgetCValueRegisterDatetime() { return xdfgetRegisterDatetime(); }

    /**
     * Add order-by as ascend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

    protected ConditionValue _registerTrace;
    public ConditionValue xdfgetRegisterTrace()
    { if (_registerTrace == null) { _registerTrace = nCV(); }
      return _registerTrace; }
    protected ConditionValue xgetCValueRegisterTrace() { return xdfgetRegisterTrace(); }

    /**
     * Add order-by as ascend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

    protected ConditionValue _updateDatetime;
    public ConditionValue xdfgetUpdateDatetime()
    { if (_updateDatetime == null) { _updateDatetime = nCV(); }
      return _updateDatetime; }
    protected ConditionValue xgetCValueUpdateDatetime() { return xdfgetUpdateDatetime(); }

    /**
     * Add order-by as ascend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

    protected ConditionValue _updateTrace;
    public ConditionValue xdfgetUpdateTrace()
    { if (_updateTrace == null) { _updateTrace = nCV(); }
      return _updateTrace; }
    protected ConditionValue xgetCValueUpdateTrace() { return xdfgetUpdateTrace(); }

    /**
     * Add order-by as ascend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

    // ===================================================================================
    //                                                             SpecifiedDerivedOrderBy
    //                                                             =======================
    /**
     * Add order-by for specified derived column as ascend.
     * <pre>
     * cb.specify().derivedPurchaseList().max(new SubQuery&lt;PurchaseCB&gt;() {
     *     public void query(PurchaseCB subCB) {
     *         subCB.specify().columnPurchaseDatetime();
     *     }
     * }, <span style="color: #CC4747">aliasName</span>);
     * <span style="color: #3F7E5E">// order by [alias-name] asc</span>
     * cb.<span style="color: #CC4747">addSpecifiedDerivedOrderBy_Asc</span>(<span style="color: #CC4747">aliasName</span>);
     * </pre>
     * @param aliasName The alias name specified at (Specify)DerivedReferrer. (NotNull)
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

    /**
     * Add order-by for specified derived column as descend.
     * <pre>
     * cb.specify().derivedPurchaseList().max(new SubQuery&lt;PurchaseCB&gt;() {
     *     public void query(PurchaseCB subCB) {
     *         subCB.specify().columnPurchaseDatetime();
     *     }
     * }, <span style="color: #CC4747">aliasName</span>);
     * <span style="color: #3F7E5E">// order by [alias-name] desc</span>
     * cb.<span style="color: #CC4747">addSpecifiedDerivedOrderBy_Desc</span>(<span style="color: #CC4747">aliasName</span>);
     * </pre>
     * @param aliasName The alias name specified at (Specify)DerivedReferrer. (NotNull)
     * @return this. (NotNull)
     */
    public BsOriginalCharaGroupCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, OriginalCharaGroupCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(OriginalCharaGroupCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, OriginalCharaGroupCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(OriginalCharaGroupCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, OriginalCharaGroupCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(OriginalCharaGroupCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, OriginalCharaGroupCQ> _myselfExistsMap;
    public Map<String, OriginalCharaGroupCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(OriginalCharaGroupCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, OriginalCharaGroupCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(OriginalCharaGroupCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return OriginalCharaGroupCB.class.getName(); }
    protected String xCQ() { return OriginalCharaGroupCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
