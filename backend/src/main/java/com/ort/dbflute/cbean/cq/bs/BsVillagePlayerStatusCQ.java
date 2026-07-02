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
 * The base condition-query of village_player_status.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillagePlayerStatusCQ extends AbstractBsVillagePlayerStatusCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillagePlayerStatusCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillagePlayerStatusCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from village_player_status) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillagePlayerStatusCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillagePlayerStatusCIQ xcreateCIQ() {
        VillagePlayerStatusCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillagePlayerStatusCIQ xnewCIQ() {
        return new VillagePlayerStatusCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join village_player_status on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillagePlayerStatusCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillagePlayerStatusCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villagePlayerStatusId;
    public ConditionValue xdfgetVillagePlayerStatusId()
    { if (_villagePlayerStatusId == null) { _villagePlayerStatusId = nCV(); }
      return _villagePlayerStatusId; }
    protected ConditionValue xgetCValueVillagePlayerStatusId() { return xdfgetVillagePlayerStatusId(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_STATUS_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_VillagePlayerStatusId_Asc() { regOBA("VILLAGE_PLAYER_STATUS_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_STATUS_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_VillagePlayerStatusId_Desc() { regOBD("VILLAGE_PLAYER_STATUS_ID"); return this; }

    protected ConditionValue _villagePlayerId;
    public ConditionValue xdfgetVillagePlayerId()
    { if (_villagePlayerId == null) { _villagePlayerId = nCV(); }
      return _villagePlayerId; }
    protected ConditionValue xgetCValueVillagePlayerId() { return xdfgetVillagePlayerId(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_VillagePlayerId_Asc() { regOBA("VILLAGE_PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_VillagePlayerId_Desc() { regOBD("VILLAGE_PLAYER_ID"); return this; }

    protected ConditionValue _toVillagePlayerId;
    public ConditionValue xdfgetToVillagePlayerId()
    { if (_toVillagePlayerId == null) { _toVillagePlayerId = nCV(); }
      return _toVillagePlayerId; }
    protected ConditionValue xgetCValueToVillagePlayerId() { return xdfgetToVillagePlayerId(); }

    /**
     * Add order-by as ascend. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_ToVillagePlayerId_Asc() { regOBA("TO_VILLAGE_PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_ToVillagePlayerId_Desc() { regOBD("TO_VILLAGE_PLAYER_ID"); return this; }

    protected ConditionValue _villagePlayerStatusCode;
    public ConditionValue xdfgetVillagePlayerStatusCode()
    { if (_villagePlayerStatusCode == null) { _villagePlayerStatusCode = nCV(); }
      return _villagePlayerStatusCode; }
    protected ConditionValue xgetCValueVillagePlayerStatusCode() { return xdfgetVillagePlayerStatusCode(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to village_player_status_type, classification=VillagePlayerStatusType}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_VillagePlayerStatusCode_Asc() { regOBA("VILLAGE_PLAYER_STATUS_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to village_player_status_type, classification=VillagePlayerStatusType}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_VillagePlayerStatusCode_Desc() { regOBD("VILLAGE_PLAYER_STATUS_CODE"); return this; }

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
    public BsVillagePlayerStatusCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsVillagePlayerStatusCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsVillagePlayerStatusCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsVillagePlayerStatusCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerStatusCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsVillagePlayerStatusCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillagePlayerStatusCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillagePlayerStatusCQ bq = (VillagePlayerStatusCQ)bqs;
        VillagePlayerStatusCQ uq = (VillagePlayerStatusCQ)uqs;
        if (bq.hasConditionQueryVillagePlayerByToVillagePlayerId()) {
            uq.queryVillagePlayerByToVillagePlayerId().reflectRelationOnUnionQuery(bq.queryVillagePlayerByToVillagePlayerId(), uq.queryVillagePlayerByToVillagePlayerId());
        }
        if (bq.hasConditionQueryVillagePlayerByVillagePlayerId()) {
            uq.queryVillagePlayerByVillagePlayerId().reflectRelationOnUnionQuery(bq.queryVillagePlayerByVillagePlayerId(), uq.queryVillagePlayerByVillagePlayerId());
        }
        if (bq.hasConditionQueryVillagePlayerStatusType()) {
            uq.queryVillagePlayerStatusType().reflectRelationOnUnionQuery(bq.queryVillagePlayerStatusType(), uq.queryVillagePlayerStatusType());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillagePlayerCQ queryVillagePlayerByToVillagePlayerId() {
        return xdfgetConditionQueryVillagePlayerByToVillagePlayerId();
    }
    public VillagePlayerCQ xdfgetConditionQueryVillagePlayerByToVillagePlayerId() {
        String prop = "villagePlayerByToVillagePlayerId";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillagePlayerByToVillagePlayerId()); xsetupOuterJoinVillagePlayerByToVillagePlayerId(); }
        return xgetQueRlMap(prop);
    }
    protected VillagePlayerCQ xcreateQueryVillagePlayerByToVillagePlayerId() {
        String nrp = xresolveNRP("village_player_status", "villagePlayerByToVillagePlayerId"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillagePlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villagePlayerByToVillagePlayerId", nrp);
    }
    protected void xsetupOuterJoinVillagePlayerByToVillagePlayerId() { xregOutJo("villagePlayerByToVillagePlayerId"); }
    public boolean hasConditionQueryVillagePlayerByToVillagePlayerId() { return xhasQueRlMap("villagePlayerByToVillagePlayerId"); }

    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillagePlayerCQ queryVillagePlayerByVillagePlayerId() {
        return xdfgetConditionQueryVillagePlayerByVillagePlayerId();
    }
    public VillagePlayerCQ xdfgetConditionQueryVillagePlayerByVillagePlayerId() {
        String prop = "villagePlayerByVillagePlayerId";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillagePlayerByVillagePlayerId()); xsetupOuterJoinVillagePlayerByVillagePlayerId(); }
        return xgetQueRlMap(prop);
    }
    protected VillagePlayerCQ xcreateQueryVillagePlayerByVillagePlayerId() {
        String nrp = xresolveNRP("village_player_status", "villagePlayerByVillagePlayerId"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillagePlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villagePlayerByVillagePlayerId", nrp);
    }
    protected void xsetupOuterJoinVillagePlayerByVillagePlayerId() { xregOutJo("villagePlayerByVillagePlayerId"); }
    public boolean hasConditionQueryVillagePlayerByVillagePlayerId() { return xhasQueRlMap("villagePlayerByVillagePlayerId"); }

    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE_PLAYER_STATUS_TYPE by my VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusType'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillagePlayerStatusTypeCQ queryVillagePlayerStatusType() {
        return xdfgetConditionQueryVillagePlayerStatusType();
    }
    public VillagePlayerStatusTypeCQ xdfgetConditionQueryVillagePlayerStatusType() {
        String prop = "villagePlayerStatusType";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillagePlayerStatusType()); xsetupOuterJoinVillagePlayerStatusType(); }
        return xgetQueRlMap(prop);
    }
    protected VillagePlayerStatusTypeCQ xcreateQueryVillagePlayerStatusType() {
        String nrp = xresolveNRP("village_player_status", "villagePlayerStatusType"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillagePlayerStatusTypeCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villagePlayerStatusType", nrp);
    }
    protected void xsetupOuterJoinVillagePlayerStatusType() { xregOutJo("villagePlayerStatusType"); }
    public boolean hasConditionQueryVillagePlayerStatusType() { return xhasQueRlMap("villagePlayerStatusType"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, VillagePlayerStatusCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillagePlayerStatusCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillagePlayerStatusCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillagePlayerStatusCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillagePlayerStatusCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillagePlayerStatusCQ> _myselfExistsMap;
    public Map<String, VillagePlayerStatusCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillagePlayerStatusCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillagePlayerStatusCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillagePlayerStatusCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillagePlayerStatusCB.class.getName(); }
    protected String xCQ() { return VillagePlayerStatusCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
