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
 * The base condition-query of VILLAGE_CHARA_GROUP.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillageCharaGroupCQ extends AbstractBsVillageCharaGroupCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillageCharaGroupCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillageCharaGroupCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from VILLAGE_CHARA_GROUP) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillageCharaGroupCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillageCharaGroupCIQ xcreateCIQ() {
        VillageCharaGroupCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillageCharaGroupCIQ xnewCIQ() {
        return new VillageCharaGroupCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join VILLAGE_CHARA_GROUP on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillageCharaGroupCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillageCharaGroupCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villageCharaGroupId;
    public ConditionValue xdfgetVillageCharaGroupId()
    { if (_villageCharaGroupId == null) { _villageCharaGroupId = nCV(); }
      return _villageCharaGroupId; }
    protected ConditionValue xgetCValueVillageCharaGroupId() { return xdfgetVillageCharaGroupId(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_VillageCharaGroupId_Asc() { regOBA("VILLAGE_CHARA_GROUP_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_VillageCharaGroupId_Desc() { regOBD("VILLAGE_CHARA_GROUP_ID"); return this; }

    protected ConditionValue _villageId;
    public ConditionValue xdfgetVillageId()
    { if (_villageId == null) { _villageId = nCV(); }
      return _villageId; }
    protected ConditionValue xgetCValueVillageId() { return xdfgetVillageId(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_VillageId_Desc() { regOBD("VILLAGE_ID"); return this; }

    protected ConditionValue _charaGroupId;
    public ConditionValue xdfgetCharaGroupId()
    { if (_charaGroupId == null) { _charaGroupId = nCV(); }
      return _charaGroupId; }
    protected ConditionValue xgetCValueCharaGroupId() { return xdfgetCharaGroupId(); }

    /**
     * Add order-by as ascend. <br>
     * CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA_GROUP}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_CharaGroupId_Asc() { regOBA("CHARA_GROUP_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA_GROUP}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_CharaGroupId_Desc() { regOBD("CHARA_GROUP_ID"); return this; }

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
    public BsVillageCharaGroupCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsVillageCharaGroupCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsVillageCharaGroupCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsVillageCharaGroupCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCharaGroupCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsVillageCharaGroupCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillageCharaGroupCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillageCharaGroupCQ bq = (VillageCharaGroupCQ)bqs;
        VillageCharaGroupCQ uq = (VillageCharaGroupCQ)uqs;
        if (bq.hasConditionQueryCharaGroup()) {
            uq.queryCharaGroup().reflectRelationOnUnionQuery(bq.queryCharaGroup(), uq.queryCharaGroup());
        }
        if (bq.hasConditionQueryVillage()) {
            uq.queryVillage().reflectRelationOnUnionQuery(bq.queryVillage(), uq.queryVillage());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'.
     * @return The instance of condition-query. (NotNull)
     */
    public CharaGroupCQ queryCharaGroup() {
        return xdfgetConditionQueryCharaGroup();
    }
    public CharaGroupCQ xdfgetConditionQueryCharaGroup() {
        String prop = "charaGroup";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryCharaGroup()); xsetupOuterJoinCharaGroup(); }
        return xgetQueRlMap(prop);
    }
    protected CharaGroupCQ xcreateQueryCharaGroup() {
        String nrp = xresolveNRP("VILLAGE_CHARA_GROUP", "charaGroup"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new CharaGroupCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "charaGroup", nrp);
    }
    protected void xsetupOuterJoinCharaGroup() { xregOutJo("charaGroup"); }
    public boolean hasConditionQueryCharaGroup() { return xhasQueRlMap("charaGroup"); }

    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillageCQ queryVillage() {
        return xdfgetConditionQueryVillage();
    }
    public VillageCQ xdfgetConditionQueryVillage() {
        String prop = "village";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillage()); xsetupOuterJoinVillage(); }
        return xgetQueRlMap(prop);
    }
    protected VillageCQ xcreateQueryVillage() {
        String nrp = xresolveNRP("VILLAGE_CHARA_GROUP", "village"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillageCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "village", nrp);
    }
    protected void xsetupOuterJoinVillage() { xregOutJo("village"); }
    public boolean hasConditionQueryVillage() { return xhasQueRlMap("village"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, VillageCharaGroupCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillageCharaGroupCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillageCharaGroupCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillageCharaGroupCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillageCharaGroupCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillageCharaGroupCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillageCharaGroupCQ> _myselfExistsMap;
    public Map<String, VillageCharaGroupCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillageCharaGroupCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillageCharaGroupCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillageCharaGroupCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillageCharaGroupCB.class.getName(); }
    protected String xCQ() { return VillageCharaGroupCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
