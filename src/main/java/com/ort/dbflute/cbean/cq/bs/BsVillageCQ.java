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
 * The base condition-query of village.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillageCQ extends AbstractBsVillageCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillageCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from village) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillageCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillageCIQ xcreateCIQ() {
        VillageCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillageCIQ xnewCIQ() {
        return new VillageCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join village on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillageCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillageCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _vilalgeId;
    public ConditionValue xdfgetVilalgeId()
    { if (_vilalgeId == null) { _vilalgeId = nCV(); }
      return _vilalgeId; }
    protected ConditionValue xgetCValueVilalgeId() { return xdfgetVilalgeId(); }

    public Map<String, MessageCQ> xdfgetVilalgeId_ExistsReferrer_MessageList() { return xgetSQueMap("vilalgeId_ExistsReferrer_MessageList"); }
    public String keepVilalgeId_ExistsReferrer_MessageList(MessageCQ sq) { return xkeepSQue("vilalgeId_ExistsReferrer_MessageList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetVilalgeId_ExistsReferrer_VillagePlayerList() { return xgetSQueMap("vilalgeId_ExistsReferrer_VillagePlayerList"); }
    public String keepVilalgeId_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("vilalgeId_ExistsReferrer_VillagePlayerList", sq); }

    public Map<String, MessageCQ> xdfgetVilalgeId_NotExistsReferrer_MessageList() { return xgetSQueMap("vilalgeId_NotExistsReferrer_MessageList"); }
    public String keepVilalgeId_NotExistsReferrer_MessageList(MessageCQ sq) { return xkeepSQue("vilalgeId_NotExistsReferrer_MessageList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetVilalgeId_NotExistsReferrer_VillagePlayerList() { return xgetSQueMap("vilalgeId_NotExistsReferrer_VillagePlayerList"); }
    public String keepVilalgeId_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("vilalgeId_NotExistsReferrer_VillagePlayerList", sq); }

    public Map<String, MessageCQ> xdfgetVilalgeId_SpecifyDerivedReferrer_MessageList() { return xgetSQueMap("vilalgeId_SpecifyDerivedReferrer_MessageList"); }
    public String keepVilalgeId_SpecifyDerivedReferrer_MessageList(MessageCQ sq) { return xkeepSQue("vilalgeId_SpecifyDerivedReferrer_MessageList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetVilalgeId_SpecifyDerivedReferrer_VillagePlayerList() { return xgetSQueMap("vilalgeId_SpecifyDerivedReferrer_VillagePlayerList"); }
    public String keepVilalgeId_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("vilalgeId_SpecifyDerivedReferrer_VillagePlayerList", sq); }

    public Map<String, MessageCQ> xdfgetVilalgeId_QueryDerivedReferrer_MessageList() { return xgetSQueMap("vilalgeId_QueryDerivedReferrer_MessageList"); }
    public String keepVilalgeId_QueryDerivedReferrer_MessageList(MessageCQ sq) { return xkeepSQue("vilalgeId_QueryDerivedReferrer_MessageList", sq); }
    public Map<String, Object> xdfgetVilalgeId_QueryDerivedReferrer_MessageListParameter() { return xgetSQuePmMap("vilalgeId_QueryDerivedReferrer_MessageList"); }
    public String keepVilalgeId_QueryDerivedReferrer_MessageListParameter(Object pm) { return xkeepSQuePm("vilalgeId_QueryDerivedReferrer_MessageList", pm); }

    public Map<String, VillagePlayerCQ> xdfgetVilalgeId_QueryDerivedReferrer_VillagePlayerList() { return xgetSQueMap("vilalgeId_QueryDerivedReferrer_VillagePlayerList"); }
    public String keepVilalgeId_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("vilalgeId_QueryDerivedReferrer_VillagePlayerList", sq); }
    public Map<String, Object> xdfgetVilalgeId_QueryDerivedReferrer_VillagePlayerListParameter() { return xgetSQuePmMap("vilalgeId_QueryDerivedReferrer_VillagePlayerList"); }
    public String keepVilalgeId_QueryDerivedReferrer_VillagePlayerListParameter(Object pm) { return xkeepSQuePm("vilalgeId_QueryDerivedReferrer_VillagePlayerList", pm); }

    /** 
     * Add order-by as ascend. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VilalgeId_Asc() { regOBA("VILALGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VilalgeId_Desc() { regOBD("VILALGE_ID"); return this; }

    protected ConditionValue _villageDisplayName;
    public ConditionValue xdfgetVillageDisplayName()
    { if (_villageDisplayName == null) { _villageDisplayName = nCV(); }
      return _villageDisplayName; }
    protected ConditionValue xgetCValueVillageDisplayName() { return xdfgetVillageDisplayName(); }

    /** 
     * Add order-by as ascend. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageDisplayName_Asc() { regOBA("VILLAGE_DISPLAY_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageDisplayName_Desc() { regOBD("VILLAGE_DISPLAY_NAME"); return this; }

    protected ConditionValue _winCampCode;
    public ConditionValue xdfgetWinCampCode()
    { if (_winCampCode == null) { _winCampCode = nCV(); }
      return _winCampCode; }
    protected ConditionValue xgetCValueWinCampCode() { return xdfgetWinCampCode(); }

    /** 
     * Add order-by as ascend. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_WinCampCode_Asc() { regOBA("WIN_CAMP_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_WinCampCode_Desc() { regOBD("WIN_CAMP_CODE"); return this; }

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
    public BsVillageCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsVillageCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsVillageCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsVillageCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsVillageCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillageCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillageCQ bq = (VillageCQ)bqs;
        VillageCQ uq = (VillageCQ)uqs;
        if (bq.hasConditionQueryVillageSettingsAsOne()) {
            uq.queryVillageSettingsAsOne().reflectRelationOnUnionQuery(bq.queryVillageSettingsAsOne(), uq.queryVillageSettingsAsOne());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * village_settings by VILLAGE_ID, named 'villageSettingsAsOne'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillageSettingsCQ queryVillageSettingsAsOne() { return xdfgetConditionQueryVillageSettingsAsOne(); }
    public VillageSettingsCQ xdfgetConditionQueryVillageSettingsAsOne() {
        String prop = "villageSettingsAsOne";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillageSettingsAsOne()); xsetupOuterJoinVillageSettingsAsOne(); }
        return xgetQueRlMap(prop);
    }
    protected VillageSettingsCQ xcreateQueryVillageSettingsAsOne() {
        String nrp = xresolveNRP("village", "villageSettingsAsOne"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillageSettingsCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villageSettingsAsOne", nrp);
    }
    protected void xsetupOuterJoinVillageSettingsAsOne() { xregOutJo("villageSettingsAsOne"); }
    public boolean hasConditionQueryVillageSettingsAsOne() { return xhasQueRlMap("villageSettingsAsOne"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, VillageCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillageCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillageCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillageCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillageCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillageCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillageCQ> _myselfExistsMap;
    public Map<String, VillageCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillageCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillageCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillageCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillageCB.class.getName(); }
    protected String xCQ() { return VillageCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
