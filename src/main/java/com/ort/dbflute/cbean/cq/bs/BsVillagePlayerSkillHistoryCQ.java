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
 * The base condition-query of VILLAGE_PLAYER_SKILL_HISTORY.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillagePlayerSkillHistoryCQ extends AbstractBsVillagePlayerSkillHistoryCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillagePlayerSkillHistoryCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillagePlayerSkillHistoryCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from VILLAGE_PLAYER_SKILL_HISTORY) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillagePlayerSkillHistoryCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillagePlayerSkillHistoryCIQ xcreateCIQ() {
        VillagePlayerSkillHistoryCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillagePlayerSkillHistoryCIQ xnewCIQ() {
        return new VillagePlayerSkillHistoryCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join VILLAGE_PLAYER_SKILL_HISTORY on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillagePlayerSkillHistoryCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillagePlayerSkillHistoryCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villagePlayerSkillHistoryId;
    public ConditionValue xdfgetVillagePlayerSkillHistoryId()
    { if (_villagePlayerSkillHistoryId == null) { _villagePlayerSkillHistoryId = nCV(); }
      return _villagePlayerSkillHistoryId; }
    protected ConditionValue xgetCValueVillagePlayerSkillHistoryId() { return xdfgetVillagePlayerSkillHistoryId(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_SKILL_HISTORY_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_VillagePlayerSkillHistoryId_Asc() { regOBA("VILLAGE_PLAYER_SKILL_HISTORY_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_SKILL_HISTORY_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_VillagePlayerSkillHistoryId_Desc() { regOBD("VILLAGE_PLAYER_SKILL_HISTORY_ID"); return this; }

    protected ConditionValue _villagePlayerId;
    public ConditionValue xdfgetVillagePlayerId()
    { if (_villagePlayerId == null) { _villagePlayerId = nCV(); }
      return _villagePlayerId; }
    protected ConditionValue xgetCValueVillagePlayerId() { return xdfgetVillagePlayerId(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_VillagePlayerId_Asc() { regOBA("VILLAGE_PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_VillagePlayerId_Desc() { regOBD("VILLAGE_PLAYER_ID"); return this; }

    protected ConditionValue _day;
    public ConditionValue xdfgetDay()
    { if (_day == null) { _day = nCV(); }
      return _day; }
    protected ConditionValue xgetCValueDay() { return xdfgetDay(); }

    /**
     * Add order-by as ascend. <br>
     * DAY: {NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_Day_Asc() { regOBA("DAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * DAY: {NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_Day_Desc() { regOBD("DAY"); return this; }

    protected ConditionValue _skillCode;
    public ConditionValue xdfgetSkillCode()
    { if (_skillCode == null) { _skillCode = nCV(); }
      return _skillCode; }
    protected ConditionValue xgetCValueSkillCode() { return xdfgetSkillCode(); }

    /**
     * Add order-by as ascend. <br>
     * SKILL_CODE: {IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_SkillCode_Asc() { regOBA("SKILL_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * SKILL_CODE: {IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_SkillCode_Desc() { regOBD("SKILL_CODE"); return this; }

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
    public BsVillagePlayerSkillHistoryCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsVillagePlayerSkillHistoryCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsVillagePlayerSkillHistoryCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsVillagePlayerSkillHistoryCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerSkillHistoryCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsVillagePlayerSkillHistoryCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillagePlayerSkillHistoryCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillagePlayerSkillHistoryCQ bq = (VillagePlayerSkillHistoryCQ)bqs;
        VillagePlayerSkillHistoryCQ uq = (VillagePlayerSkillHistoryCQ)uqs;
        if (bq.hasConditionQuerySkill()) {
            uq.querySkill().reflectRelationOnUnionQuery(bq.querySkill(), uq.querySkill());
        }
        if (bq.hasConditionQueryVillagePlayer()) {
            uq.queryVillagePlayer().reflectRelationOnUnionQuery(bq.queryVillagePlayer(), uq.queryVillagePlayer());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * SKILL by my SKILL_CODE, named 'skill'.
     * @return The instance of condition-query. (NotNull)
     */
    public SkillCQ querySkill() {
        return xdfgetConditionQuerySkill();
    }
    public SkillCQ xdfgetConditionQuerySkill() {
        String prop = "skill";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQuerySkill()); xsetupOuterJoinSkill(); }
        return xgetQueRlMap(prop);
    }
    protected SkillCQ xcreateQuerySkill() {
        String nrp = xresolveNRP("VILLAGE_PLAYER_SKILL_HISTORY", "skill"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new SkillCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "skill", nrp);
    }
    protected void xsetupOuterJoinSkill() { xregOutJo("skill"); }
    public boolean hasConditionQuerySkill() { return xhasQueRlMap("skill"); }

    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayer'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillagePlayerCQ queryVillagePlayer() {
        return xdfgetConditionQueryVillagePlayer();
    }
    public VillagePlayerCQ xdfgetConditionQueryVillagePlayer() {
        String prop = "villagePlayer";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillagePlayer()); xsetupOuterJoinVillagePlayer(); }
        return xgetQueRlMap(prop);
    }
    protected VillagePlayerCQ xcreateQueryVillagePlayer() {
        String nrp = xresolveNRP("VILLAGE_PLAYER_SKILL_HISTORY", "villagePlayer"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillagePlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villagePlayer", nrp);
    }
    protected void xsetupOuterJoinVillagePlayer() { xregOutJo("villagePlayer"); }
    public boolean hasConditionQueryVillagePlayer() { return xhasQueRlMap("villagePlayer"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, VillagePlayerSkillHistoryCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillagePlayerSkillHistoryCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillagePlayerSkillHistoryCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillagePlayerSkillHistoryCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillagePlayerSkillHistoryCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillagePlayerSkillHistoryCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillagePlayerSkillHistoryCQ> _myselfExistsMap;
    public Map<String, VillagePlayerSkillHistoryCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillagePlayerSkillHistoryCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillagePlayerSkillHistoryCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillagePlayerSkillHistoryCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillagePlayerSkillHistoryCB.class.getName(); }
    protected String xCQ() { return VillagePlayerSkillHistoryCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
