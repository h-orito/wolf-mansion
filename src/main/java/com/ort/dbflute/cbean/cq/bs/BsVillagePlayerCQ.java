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
 * The base condition-query of village_player.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillagePlayerCQ extends AbstractBsVillagePlayerCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillagePlayerCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillagePlayerCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from village_player) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillagePlayerCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillagePlayerCIQ xcreateCIQ() {
        VillagePlayerCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillagePlayerCIQ xnewCIQ() {
        return new VillagePlayerCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join village_player on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillagePlayerCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillagePlayerCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villagePlayerId;
    public ConditionValue xdfgetVillagePlayerId()
    { if (_villagePlayerId == null) { _villagePlayerId = nCV(); }
      return _villagePlayerId; }
    protected ConditionValue xgetCValueVillagePlayerId() { return xdfgetVillagePlayerId(); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList", pm); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList", pm); }

    /** 
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_VillagePlayerId_Asc() { regOBA("VILLAGE_PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_VillagePlayerId_Desc() { regOBD("VILLAGE_PLAYER_ID"); return this; }

    protected ConditionValue _villageId;
    public ConditionValue xdfgetVillageId()
    { if (_villageId == null) { _villageId = nCV(); }
      return _villageId; }
    protected ConditionValue xgetCValueVillageId() { return xdfgetVillageId(); }

    /** 
     * Add order-by as ascend. <br>
     * VILLAGE_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to village}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to village}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_VillageId_Desc() { regOBD("VILLAGE_ID"); return this; }

    protected ConditionValue _playerId;
    public ConditionValue xdfgetPlayerId()
    { if (_playerId == null) { _playerId = nCV(); }
      return _playerId; }
    protected ConditionValue xgetCValuePlayerId() { return xdfgetPlayerId(); }

    /** 
     * Add order-by as ascend. <br>
     * PLAYER_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_PlayerId_Asc() { regOBA("PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * PLAYER_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_PlayerId_Desc() { regOBD("PLAYER_ID"); return this; }

    protected ConditionValue _charaId;
    public ConditionValue xdfgetCharaId()
    { if (_charaId == null) { _charaId = nCV(); }
      return _charaId; }
    protected ConditionValue xgetCValueCharaId() { return xdfgetCharaId(); }

    /** 
     * Add order-by as ascend. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CharaId_Asc() { regOBA("CHARA_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CharaId_Desc() { regOBD("CHARA_ID"); return this; }

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
    public BsVillagePlayerCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillagePlayerCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillagePlayerCQ bq = (VillagePlayerCQ)bqs;
        VillagePlayerCQ uq = (VillagePlayerCQ)uqs;
        if (bq.hasConditionQueryChara()) {
            uq.queryChara().reflectRelationOnUnionQuery(bq.queryChara(), uq.queryChara());
        }
        if (bq.hasConditionQueryPlayer()) {
            uq.queryPlayer().reflectRelationOnUnionQuery(bq.queryPlayer(), uq.queryPlayer());
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
     * chara by my CHARA_ID, named 'chara'.
     * @return The instance of condition-query. (NotNull)
     */
    public CharaCQ queryChara() {
        return xdfgetConditionQueryChara();
    }
    public CharaCQ xdfgetConditionQueryChara() {
        String prop = "chara";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryChara()); xsetupOuterJoinChara(); }
        return xgetQueRlMap(prop);
    }
    protected CharaCQ xcreateQueryChara() {
        String nrp = xresolveNRP("village_player", "chara"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new CharaCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "chara", nrp);
    }
    protected void xsetupOuterJoinChara() { xregOutJo("chara"); }
    public boolean hasConditionQueryChara() { return xhasQueRlMap("chara"); }

    /**
     * Get the condition-query for relation table. <br>
     * player by my PLAYER_ID, named 'player'.
     * @return The instance of condition-query. (NotNull)
     */
    public PlayerCQ queryPlayer() {
        return xdfgetConditionQueryPlayer();
    }
    public PlayerCQ xdfgetConditionQueryPlayer() {
        String prop = "player";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryPlayer()); xsetupOuterJoinPlayer(); }
        return xgetQueRlMap(prop);
    }
    protected PlayerCQ xcreateQueryPlayer() {
        String nrp = xresolveNRP("village_player", "player"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new PlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "player", nrp);
    }
    protected void xsetupOuterJoinPlayer() { xregOutJo("player"); }
    public boolean hasConditionQueryPlayer() { return xhasQueRlMap("player"); }

    /**
     * Get the condition-query for relation table. <br>
     * village by my VILLAGE_ID, named 'village'.
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
        String nrp = xresolveNRP("village_player", "village"); String jan = xresolveJAN(nrp, xgetNNLvl());
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
    public Map<String, VillagePlayerCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillagePlayerCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillagePlayerCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillagePlayerCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillagePlayerCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillagePlayerCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillagePlayerCQ> _myselfExistsMap;
    public Map<String, VillagePlayerCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillagePlayerCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillagePlayerCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillagePlayerCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillagePlayerCB.class.getName(); }
    protected String xCQ() { return VillagePlayerCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
