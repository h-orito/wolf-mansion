package com.ort.dbflute.cbean.cq.bs;

import java.util.Map;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.scoping.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import com.ort.dbflute.cbean.cq.ciq.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The base condition-query of village_day.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillageDayCQ extends AbstractBsVillageDayCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillageDayCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillageDayCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from village_day) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillageDayCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillageDayCIQ xcreateCIQ() {
        VillageDayCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillageDayCIQ xnewCIQ() {
        return new VillageDayCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join village_day on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillageDayCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillageDayCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_VillageId_Desc() { regOBD("VILLAGE_ID"); return this; }

    protected ConditionValue _day;
    public ConditionValue xdfgetDay()
    { if (_day == null) { _day = nCV(); }
      return _day; }
    protected ConditionValue xgetCValueDay() { return xdfgetDay(); }

    /** 
     * Add order-by as ascend. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_Day_Asc() { regOBA("DAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_Day_Desc() { regOBD("DAY"); return this; }

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
    public BsVillageDayCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsVillageDayCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsVillageDayCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsVillageDayCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageDayCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsVillageDayCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillageDayCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillageDayCQ bq = (VillageDayCQ)bqs;
        VillageDayCQ uq = (VillageDayCQ)uqs;
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
        String nrp = xresolveNRP("village_day", "village"); String jan = xresolveJAN(nrp, xgetNNLvl());
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
    public Map<String, VillageDayCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillageDayCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                      ExistsReferrer for Compound PK
    //                                                      ==============================
    /**
     * Set up ExistsReferrer (correlated sub-query by compound key). <br>
     * {exists (select ... from ability where ...)}
     * @param subQuery The sub-query of AbilityList for 'exists'. (NotNull)
     */
    public void existsAbility(SubQuery<AbilityCB> subQuery) {
        assertObjectNotNull("subQuery<AbilityCB>", subQuery);
        AbilityCB cb = new AbilityCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_ExistsReferrer_AbilityList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "abilityList");
    }
    public Map<String, AbilityCQ> xdfgetTwoOrMorePk_ExistsReferrer_AbilityList() { return xgetSQueMap("twoOrMorePk_ExistsReferrer_AbilityList"); }
    public String keepTwoOrMorePk_ExistsReferrer_AbilityList(AbilityCQ sq) { return xkeepSQue("twoOrMorePk_ExistsReferrer_AbilityList", sq); }

    /**
     * Set up ExistsReferrer (correlated sub-query by compound key). <br>
     * {exists (select ... from footstep where ...)}
     * @param subQuery The sub-query of FootstepList for 'exists'. (NotNull)
     */
    public void existsFootstep(SubQuery<FootstepCB> subQuery) {
        assertObjectNotNull("subQuery<FootstepCB>", subQuery);
        FootstepCB cb = new FootstepCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_ExistsReferrer_FootstepList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "footstepList");
    }
    public Map<String, FootstepCQ> xdfgetTwoOrMorePk_ExistsReferrer_FootstepList() { return xgetSQueMap("twoOrMorePk_ExistsReferrer_FootstepList"); }
    public String keepTwoOrMorePk_ExistsReferrer_FootstepList(FootstepCQ sq) { return xkeepSQue("twoOrMorePk_ExistsReferrer_FootstepList", sq); }

    /**
     * Set up ExistsReferrer (correlated sub-query by compound key). <br>
     * {exists (select ... from message where ...)}
     * @param subQuery The sub-query of MessageList for 'exists'. (NotNull)
     */
    public void existsMessage(SubQuery<MessageCB> subQuery) {
        assertObjectNotNull("subQuery<MessageCB>", subQuery);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_ExistsReferrer_MessageList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "messageList");
    }
    public Map<String, MessageCQ> xdfgetTwoOrMorePk_ExistsReferrer_MessageList() { return xgetSQueMap("twoOrMorePk_ExistsReferrer_MessageList"); }
    public String keepTwoOrMorePk_ExistsReferrer_MessageList(MessageCQ sq) { return xkeepSQue("twoOrMorePk_ExistsReferrer_MessageList", sq); }

    /**
     * Set up ExistsReferrer (correlated sub-query by compound key). <br>
     * {exists (select ... from vote where ...)}
     * @param subQuery The sub-query of VoteList for 'exists'. (NotNull)
     */
    public void existsVote(SubQuery<VoteCB> subQuery) {
        assertObjectNotNull("subQuery<VoteCB>", subQuery);
        VoteCB cb = new VoteCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_ExistsReferrer_VoteList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "voteList");
    }
    public Map<String, VoteCQ> xdfgetTwoOrMorePk_ExistsReferrer_VoteList() { return xgetSQueMap("twoOrMorePk_ExistsReferrer_VoteList"); }
    public String keepTwoOrMorePk_ExistsReferrer_VoteList(VoteCQ sq) { return xkeepSQue("twoOrMorePk_ExistsReferrer_VoteList", sq); }

    /**
     * Set up NotExistsReferrer (correlated sub-query by compound key). <br>
     * {not exists (select ... from ability where ...)}
     * @param subQuery The sub-query of AbilityList for 'not exists'. (NotNull)
     */
    public void notExistsAbility(SubQuery<AbilityCB> subQuery) {
        assertObjectNotNull("subQuery<AbilityCB>", subQuery);
        AbilityCB cb = new AbilityCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_NotExistsReferrer_AbilityList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "abilityList");
    }
    public Map<String, AbilityCQ> xdfgetTwoOrMorePk_NotExistsReferrer_AbilityList() { return xgetSQueMap("twoOrMorePk_NotExistsReferrer_AbilityList"); }
    public String keepTwoOrMorePk_NotExistsReferrer_AbilityList(AbilityCQ sq) { return xkeepSQue("twoOrMorePk_NotExistsReferrer_AbilityList", sq); }

    /**
     * Set up NotExistsReferrer (correlated sub-query by compound key). <br>
     * {not exists (select ... from footstep where ...)}
     * @param subQuery The sub-query of FootstepList for 'not exists'. (NotNull)
     */
    public void notExistsFootstep(SubQuery<FootstepCB> subQuery) {
        assertObjectNotNull("subQuery<FootstepCB>", subQuery);
        FootstepCB cb = new FootstepCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_NotExistsReferrer_FootstepList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "footstepList");
    }
    public Map<String, FootstepCQ> xdfgetTwoOrMorePk_NotExistsReferrer_FootstepList() { return xgetSQueMap("twoOrMorePk_NotExistsReferrer_FootstepList"); }
    public String keepTwoOrMorePk_NotExistsReferrer_FootstepList(FootstepCQ sq) { return xkeepSQue("twoOrMorePk_NotExistsReferrer_FootstepList", sq); }

    /**
     * Set up NotExistsReferrer (correlated sub-query by compound key). <br>
     * {not exists (select ... from message where ...)}
     * @param subQuery The sub-query of MessageList for 'not exists'. (NotNull)
     */
    public void notExistsMessage(SubQuery<MessageCB> subQuery) {
        assertObjectNotNull("subQuery<MessageCB>", subQuery);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_NotExistsReferrer_MessageList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "messageList");
    }
    public Map<String, MessageCQ> xdfgetTwoOrMorePk_NotExistsReferrer_MessageList() { return xgetSQueMap("twoOrMorePk_NotExistsReferrer_MessageList"); }
    public String keepTwoOrMorePk_NotExistsReferrer_MessageList(MessageCQ sq) { return xkeepSQue("twoOrMorePk_NotExistsReferrer_MessageList", sq); }

    /**
     * Set up NotExistsReferrer (correlated sub-query by compound key). <br>
     * {not exists (select ... from vote where ...)}
     * @param subQuery The sub-query of VoteList for 'not exists'. (NotNull)
     */
    public void notExistsVote(SubQuery<VoteCB> subQuery) {
        assertObjectNotNull("subQuery<VoteCB>", subQuery);
        VoteCB cb = new VoteCB(); cb.xsetupForExistsReferrer(this);
        try { lock(); subQuery.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_NotExistsReferrer_VoteList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "voteList");
    }
    public Map<String, VoteCQ> xdfgetTwoOrMorePk_NotExistsReferrer_VoteList() { return xgetSQueMap("twoOrMorePk_NotExistsReferrer_VoteList"); }
    public String keepTwoOrMorePk_NotExistsReferrer_VoteList(VoteCQ sq) { return xkeepSQue("twoOrMorePk_NotExistsReferrer_VoteList", sq); }

    // ===================================================================================
    //                                            (Specify)DerivedReferrer for Compound PK
    //                                            ========================================
    public void xsderiveAbilityList(String fn, SubQuery<AbilityCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityCB cb = new AbilityCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_SpecifyDerivedReferrer_AbilityList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "abilityList", al, op);
    }
    public Map<String, AbilityCQ> xdfgetTwoOrMorePk_SpecifyDerivedReferrer_AbilityList() { return xgetSQueMap("twoOrMorePk_SpecifyDerivedReferrer_AbilityList"); }
    public String keepTwoOrMorePk_SpecifyDerivedReferrer_AbilityList(AbilityCQ sq) { return xkeepSQue("twoOrMorePk_SpecifyDerivedReferrer_AbilityList", sq); }

    public void xsderiveFootstepList(String fn, SubQuery<FootstepCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        FootstepCB cb = new FootstepCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_SpecifyDerivedReferrer_FootstepList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "footstepList", al, op);
    }
    public Map<String, FootstepCQ> xdfgetTwoOrMorePk_SpecifyDerivedReferrer_FootstepList() { return xgetSQueMap("twoOrMorePk_SpecifyDerivedReferrer_FootstepList"); }
    public String keepTwoOrMorePk_SpecifyDerivedReferrer_FootstepList(FootstepCQ sq) { return xkeepSQue("twoOrMorePk_SpecifyDerivedReferrer_FootstepList", sq); }

    public void xsderiveMessageList(String fn, SubQuery<MessageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_SpecifyDerivedReferrer_MessageList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "messageList", al, op);
    }
    public Map<String, MessageCQ> xdfgetTwoOrMorePk_SpecifyDerivedReferrer_MessageList() { return xgetSQueMap("twoOrMorePk_SpecifyDerivedReferrer_MessageList"); }
    public String keepTwoOrMorePk_SpecifyDerivedReferrer_MessageList(MessageCQ sq) { return xkeepSQue("twoOrMorePk_SpecifyDerivedReferrer_MessageList", sq); }

    public void xsderiveVoteList(String fn, SubQuery<VoteCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VoteCB cb = new VoteCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String pp = keepTwoOrMorePk_SpecifyDerivedReferrer_VoteList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", pp, "voteList", al, op);
    }
    public Map<String, VoteCQ> xdfgetTwoOrMorePk_SpecifyDerivedReferrer_VoteList() { return xgetSQueMap("twoOrMorePk_SpecifyDerivedReferrer_VoteList"); }
    public String keepTwoOrMorePk_SpecifyDerivedReferrer_VoteList(VoteCQ sq) { return xkeepSQue("twoOrMorePk_SpecifyDerivedReferrer_VoteList", sq); }

    // ===================================================================================
    //                                              (Query)DerivedReferrer for Compound PK
    //                                              ======================================
    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from ability where ...)} <br>
     * ability by VILLAGE_ID, DAY, named 'abilityAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedAbility()</span>.<span style="color: #CC4747">max</span>(new SubQuery&lt;AbilityCB&gt;() {
     *     public void query(AbilityCB subCB) {
     *         subCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *         subCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     *     }
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<AbilityCB> derivedAbility() {
        return xcreateQDRFunctionAbilityList();
    }
    protected HpQDRFunction<AbilityCB> xcreateQDRFunctionAbilityList() {
        return xcQDRFunc(new HpQDRSetupper<AbilityCB>() {
            public void setup(String fn, SubQuery<AbilityCB> sq, String rd, Object vl, DerivedReferrerOption op) {
                xqderiveAbilityList(fn, sq, rd, vl, op);
            }
        });
    }
    public void xqderiveAbilityList(String fn, SubQuery<AbilityCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        AbilityCB cb = new AbilityCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String sqpp = keepTwoOrMorePk_QueryDerivedReferrer_AbilityList(cb.query()); String prpp = keepTwoOrMorePk_QueryDerivedReferrer_AbilityListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", sqpp, "abilityList", rd, vl, prpp, op);
    }
    public Map<String, AbilityCQ> xdfgetTwoOrMorePk_QueryDerivedReferrer_AbilityList() { return xgetSQueMap("twoOrMorePk_QueryDerivedReferrer_AbilityList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_AbilityList(AbilityCQ sq) { return xkeepSQue("twoOrMorePk_QueryDerivedReferrer_AbilityList", sq); }
    public Map<String, Object> xdfgetTwoOrMorePk_QueryDerivedReferrer_AbilityListParameter() { return xgetSQuePmMap("twoOrMorePk_QueryDerivedReferrer_AbilityList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_AbilityListParameter(Object pm) { return xkeepSQuePm("twoOrMorePk_QueryDerivedReferrer_AbilityList", pm); }

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from footstep where ...)} <br>
     * footstep by VILLAGE_ID, DAY, named 'footstepAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedFootstep()</span>.<span style="color: #CC4747">max</span>(new SubQuery&lt;FootstepCB&gt;() {
     *     public void query(FootstepCB subCB) {
     *         subCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *         subCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     *     }
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<FootstepCB> derivedFootstep() {
        return xcreateQDRFunctionFootstepList();
    }
    protected HpQDRFunction<FootstepCB> xcreateQDRFunctionFootstepList() {
        return xcQDRFunc(new HpQDRSetupper<FootstepCB>() {
            public void setup(String fn, SubQuery<FootstepCB> sq, String rd, Object vl, DerivedReferrerOption op) {
                xqderiveFootstepList(fn, sq, rd, vl, op);
            }
        });
    }
    public void xqderiveFootstepList(String fn, SubQuery<FootstepCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        FootstepCB cb = new FootstepCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String sqpp = keepTwoOrMorePk_QueryDerivedReferrer_FootstepList(cb.query()); String prpp = keepTwoOrMorePk_QueryDerivedReferrer_FootstepListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", sqpp, "footstepList", rd, vl, prpp, op);
    }
    public Map<String, FootstepCQ> xdfgetTwoOrMorePk_QueryDerivedReferrer_FootstepList() { return xgetSQueMap("twoOrMorePk_QueryDerivedReferrer_FootstepList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_FootstepList(FootstepCQ sq) { return xkeepSQue("twoOrMorePk_QueryDerivedReferrer_FootstepList", sq); }
    public Map<String, Object> xdfgetTwoOrMorePk_QueryDerivedReferrer_FootstepListParameter() { return xgetSQuePmMap("twoOrMorePk_QueryDerivedReferrer_FootstepList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_FootstepListParameter(Object pm) { return xkeepSQuePm("twoOrMorePk_QueryDerivedReferrer_FootstepList", pm); }

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from message where ...)} <br>
     * message by VILLAGE_ID, DAY, named 'messageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedMessage()</span>.<span style="color: #CC4747">max</span>(new SubQuery&lt;MessageCB&gt;() {
     *     public void query(MessageCB subCB) {
     *         subCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *         subCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     *     }
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<MessageCB> derivedMessage() {
        return xcreateQDRFunctionMessageList();
    }
    protected HpQDRFunction<MessageCB> xcreateQDRFunctionMessageList() {
        return xcQDRFunc(new HpQDRSetupper<MessageCB>() {
            public void setup(String fn, SubQuery<MessageCB> sq, String rd, Object vl, DerivedReferrerOption op) {
                xqderiveMessageList(fn, sq, rd, vl, op);
            }
        });
    }
    public void xqderiveMessageList(String fn, SubQuery<MessageCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String sqpp = keepTwoOrMorePk_QueryDerivedReferrer_MessageList(cb.query()); String prpp = keepTwoOrMorePk_QueryDerivedReferrer_MessageListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", sqpp, "messageList", rd, vl, prpp, op);
    }
    public Map<String, MessageCQ> xdfgetTwoOrMorePk_QueryDerivedReferrer_MessageList() { return xgetSQueMap("twoOrMorePk_QueryDerivedReferrer_MessageList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_MessageList(MessageCQ sq) { return xkeepSQue("twoOrMorePk_QueryDerivedReferrer_MessageList", sq); }
    public Map<String, Object> xdfgetTwoOrMorePk_QueryDerivedReferrer_MessageListParameter() { return xgetSQuePmMap("twoOrMorePk_QueryDerivedReferrer_MessageList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_MessageListParameter(Object pm) { return xkeepSQuePm("twoOrMorePk_QueryDerivedReferrer_MessageList", pm); }

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from vote where ...)} <br>
     * vote by VILLAGE_ID, DAY, named 'voteAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVote()</span>.<span style="color: #CC4747">max</span>(new SubQuery&lt;VoteCB&gt;() {
     *     public void query(VoteCB subCB) {
     *         subCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *         subCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     *     }
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VoteCB> derivedVote() {
        return xcreateQDRFunctionVoteList();
    }
    protected HpQDRFunction<VoteCB> xcreateQDRFunctionVoteList() {
        return xcQDRFunc(new HpQDRSetupper<VoteCB>() {
            public void setup(String fn, SubQuery<VoteCB> sq, String rd, Object vl, DerivedReferrerOption op) {
                xqderiveVoteList(fn, sq, rd, vl, op);
            }
        });
    }
    public void xqderiveVoteList(String fn, SubQuery<VoteCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VoteCB cb = new VoteCB(); cb.xsetupForDerivedReferrer(this);
        try { lock(); sq.query(cb); } finally { unlock(); }
        String sqpp = keepTwoOrMorePk_QueryDerivedReferrer_VoteList(cb.query()); String prpp = keepTwoOrMorePk_QueryDerivedReferrer_VoteListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID, DAY", "VILLAGE_ID, DAY", sqpp, "voteList", rd, vl, prpp, op);
    }
    public Map<String, VoteCQ> xdfgetTwoOrMorePk_QueryDerivedReferrer_VoteList() { return xgetSQueMap("twoOrMorePk_QueryDerivedReferrer_VoteList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_VoteList(VoteCQ sq) { return xkeepSQue("twoOrMorePk_QueryDerivedReferrer_VoteList", sq); }
    public Map<String, Object> xdfgetTwoOrMorePk_QueryDerivedReferrer_VoteListParameter() { return xgetSQuePmMap("twoOrMorePk_QueryDerivedReferrer_VoteList"); }
    public String keepTwoOrMorePk_QueryDerivedReferrer_VoteListParameter(Object pm) { return xkeepSQuePm("twoOrMorePk_QueryDerivedReferrer_VoteList", pm); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillageDayCB.class.getName(); }
    protected String xCQ() { return VillageDayCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
