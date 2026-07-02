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
 * The base condition-query of village_player_status_type.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillagePlayerStatusTypeCQ extends AbstractBsVillagePlayerStatusTypeCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillagePlayerStatusTypeCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillagePlayerStatusTypeCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from village_player_status_type) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillagePlayerStatusTypeCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillagePlayerStatusTypeCIQ xcreateCIQ() {
        VillagePlayerStatusTypeCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillagePlayerStatusTypeCIQ xnewCIQ() {
        return new VillagePlayerStatusTypeCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join village_player_status_type on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillagePlayerStatusTypeCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillagePlayerStatusTypeCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villagePlayerStatusTypeCode;
    public ConditionValue xdfgetVillagePlayerStatusTypeCode()
    { if (_villagePlayerStatusTypeCode == null) { _villagePlayerStatusTypeCode = nCV(); }
      return _villagePlayerStatusTypeCode; }
    protected ConditionValue xgetCValueVillagePlayerStatusTypeCode() { return xdfgetVillagePlayerStatusTypeCode(); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerStatusTypeCode_ExistsReferrer_VillagePlayerStatusList() { return xgetSQueMap("villagePlayerStatusTypeCode_ExistsReferrer_VillagePlayerStatusList"); }
    public String keepVillagePlayerStatusTypeCode_ExistsReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerStatusTypeCode_ExistsReferrer_VillagePlayerStatusList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList() { return xgetSQueMap("villagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList"); }
    public String keepVillagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerStatusTypeCode_SpecifyDerivedReferrer_VillagePlayerStatusList() { return xgetSQueMap("villagePlayerStatusTypeCode_SpecifyDerivedReferrer_VillagePlayerStatusList"); }
    public String keepVillagePlayerStatusTypeCode_SpecifyDerivedReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerStatusTypeCode_SpecifyDerivedReferrer_VillagePlayerStatusList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList() { return xgetSQueMap("villagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList"); }
    public String keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList", sq); }
    public Map<String, Object> xdfgetVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusListParameter() { return xgetSQuePmMap("villagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList"); }
    public String keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusListParameter(Object pm) { return xkeepSQuePm("villagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList", pm); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusTypeCQ addOrderBy_VillagePlayerStatusTypeCode_Asc() { regOBA("VILLAGE_PLAYER_STATUS_TYPE_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusTypeCQ addOrderBy_VillagePlayerStatusTypeCode_Desc() { regOBD("VILLAGE_PLAYER_STATUS_TYPE_CODE"); return this; }

    protected ConditionValue _villagePlayerStatusTypeName;
    public ConditionValue xdfgetVillagePlayerStatusTypeName()
    { if (_villagePlayerStatusTypeName == null) { _villagePlayerStatusTypeName = nCV(); }
      return _villagePlayerStatusTypeName; }
    protected ConditionValue xgetCValueVillagePlayerStatusTypeName() { return xdfgetVillagePlayerStatusTypeName(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusTypeCQ addOrderBy_VillagePlayerStatusTypeName_Asc() { regOBA("VILLAGE_PLAYER_STATUS_TYPE_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusTypeCQ addOrderBy_VillagePlayerStatusTypeName_Desc() { regOBD("VILLAGE_PLAYER_STATUS_TYPE_NAME"); return this; }

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
    public BsVillagePlayerStatusTypeCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillagePlayerStatusTypeCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

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
    public Map<String, VillagePlayerStatusTypeCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillagePlayerStatusTypeCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillagePlayerStatusTypeCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillagePlayerStatusTypeCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillagePlayerStatusTypeCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillagePlayerStatusTypeCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillagePlayerStatusTypeCQ> _myselfExistsMap;
    public Map<String, VillagePlayerStatusTypeCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillagePlayerStatusTypeCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillagePlayerStatusTypeCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillagePlayerStatusTypeCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillagePlayerStatusTypeCB.class.getName(); }
    protected String xCQ() { return VillagePlayerStatusTypeCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
