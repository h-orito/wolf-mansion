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
 * The base condition-query of VILLAGE_SETTINGS.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillageSettingsCQ extends AbstractBsVillageSettingsCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillageSettingsCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillageSettingsCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from VILLAGE_SETTINGS) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillageSettingsCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillageSettingsCIQ xcreateCIQ() {
        VillageSettingsCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillageSettingsCIQ xnewCIQ() {
        return new VillageSettingsCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join VILLAGE_SETTINGS on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillageSettingsCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillageSettingsCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villageId;
    public ConditionValue xdfgetVillageId()
    { if (_villageId == null) { _villageId = nCV(); }
      return _villageId; }
    protected ConditionValue xgetCValueVillageId() { return xdfgetVillageId(); }

    /** 
     * Add order-by as ascend. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_VillageId_Desc() { regOBD("VILLAGE_ID"); return this; }

    protected ConditionValue _startPersonMinNum;
    public ConditionValue xdfgetStartPersonMinNum()
    { if (_startPersonMinNum == null) { _startPersonMinNum = nCV(); }
      return _startPersonMinNum; }
    protected ConditionValue xgetCValueStartPersonMinNum() { return xdfgetStartPersonMinNum(); }

    /** 
     * Add order-by as ascend. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_StartPersonMinNum_Asc() { regOBA("START_PERSON_MIN_NUM"); return this; }

    /**
     * Add order-by as descend. <br>
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_StartPersonMinNum_Desc() { regOBD("START_PERSON_MIN_NUM"); return this; }

    protected ConditionValue _personMaxNum;
    public ConditionValue xdfgetPersonMaxNum()
    { if (_personMaxNum == null) { _personMaxNum = nCV(); }
      return _personMaxNum; }
    protected ConditionValue xgetCValuePersonMaxNum() { return xdfgetPersonMaxNum(); }

    /** 
     * Add order-by as ascend. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_PersonMaxNum_Asc() { regOBA("PERSON_MAX_NUM"); return this; }

    /**
     * Add order-by as descend. <br>
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_PersonMaxNum_Desc() { regOBD("PERSON_MAX_NUM"); return this; }

    protected ConditionValue _startDatetime;
    public ConditionValue xdfgetStartDatetime()
    { if (_startDatetime == null) { _startDatetime = nCV(); }
      return _startDatetime; }
    protected ConditionValue xgetCValueStartDatetime() { return xdfgetStartDatetime(); }

    /** 
     * Add order-by as ascend. <br>
     * START_DATETIME: {DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_StartDatetime_Asc() { regOBA("START_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * START_DATETIME: {DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_StartDatetime_Desc() { regOBD("START_DATETIME"); return this; }

    protected ConditionValue _dayChangeIntervalSeconds;
    public ConditionValue xdfgetDayChangeIntervalSeconds()
    { if (_dayChangeIntervalSeconds == null) { _dayChangeIntervalSeconds = nCV(); }
      return _dayChangeIntervalSeconds; }
    protected ConditionValue xgetCValueDayChangeIntervalSeconds() { return xdfgetDayChangeIntervalSeconds(); }

    /** 
     * Add order-by as ascend. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_DayChangeIntervalSeconds_Asc() { regOBA("DAY_CHANGE_INTERVAL_SECONDS"); return this; }

    /**
     * Add order-by as descend. <br>
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_DayChangeIntervalSeconds_Desc() { regOBD("DAY_CHANGE_INTERVAL_SECONDS"); return this; }

    protected ConditionValue _isOpenVote;
    public ConditionValue xdfgetIsOpenVote()
    { if (_isOpenVote == null) { _isOpenVote = nCV(); }
      return _isOpenVote; }
    protected ConditionValue xgetCValueIsOpenVote() { return xdfgetIsOpenVote(); }

    /** 
     * Add order-by as ascend. <br>
     * IS_OPEN_VOTE: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_IsOpenVote_Asc() { regOBA("IS_OPEN_VOTE"); return this; }

    /**
     * Add order-by as descend. <br>
     * IS_OPEN_VOTE: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_IsOpenVote_Desc() { regOBD("IS_OPEN_VOTE"); return this; }

    protected ConditionValue _isPossibleSkillRequest;
    public ConditionValue xdfgetIsPossibleSkillRequest()
    { if (_isPossibleSkillRequest == null) { _isPossibleSkillRequest = nCV(); }
      return _isPossibleSkillRequest; }
    protected ConditionValue xgetCValueIsPossibleSkillRequest() { return xdfgetIsPossibleSkillRequest(); }

    /** 
     * Add order-by as ascend. <br>
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_IsPossibleSkillRequest_Asc() { regOBA("IS_POSSIBLE_SKILL_REQUEST"); return this; }

    /**
     * Add order-by as descend. <br>
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillageSettingsCQ addOrderBy_IsPossibleSkillRequest_Desc() { regOBD("IS_POSSIBLE_SKILL_REQUEST"); return this; }

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
    public BsVillageSettingsCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillageSettingsCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillageSettingsCQ bq = (VillageSettingsCQ)bqs;
        VillageSettingsCQ uq = (VillageSettingsCQ)uqs;
        if (bq.hasConditionQueryVillage()) {
            uq.queryVillage().reflectRelationOnUnionQuery(bq.queryVillage(), uq.queryVillage());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
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
        String nrp = xresolveNRP("VILLAGE_SETTINGS", "village"); String jan = xresolveJAN(nrp, xgetNNLvl());
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
    public Map<String, VillageSettingsCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillageSettingsCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillageSettingsCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillageSettingsCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillageSettingsCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillageSettingsCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillageSettingsCQ> _myselfExistsMap;
    public Map<String, VillageSettingsCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillageSettingsCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillageSettingsCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillageSettingsCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillageSettingsCB.class.getName(); }
    protected String xCQ() { return VillageSettingsCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
