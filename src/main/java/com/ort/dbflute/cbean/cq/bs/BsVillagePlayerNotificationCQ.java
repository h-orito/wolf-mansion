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
 * The base condition-query of village_player_notification.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillagePlayerNotificationCQ extends AbstractBsVillagePlayerNotificationCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillagePlayerNotificationCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillagePlayerNotificationCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from village_player_notification) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillagePlayerNotificationCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillagePlayerNotificationCIQ xcreateCIQ() {
        VillagePlayerNotificationCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillagePlayerNotificationCIQ xnewCIQ() {
        return new VillagePlayerNotificationCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join village_player_notification on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillagePlayerNotificationCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillagePlayerNotificationCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villagePlayerId;
    public ConditionValue xdfgetVillagePlayerId()
    { if (_villagePlayerId == null) { _villagePlayerId = nCV(); }
      return _villagePlayerId; }
    protected ConditionValue xgetCValueVillagePlayerId() { return xdfgetVillagePlayerId(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_ID: {PK, NotNull, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillagePlayerId_Asc() { regOBA("VILLAGE_PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_ID: {PK, NotNull, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillagePlayerId_Desc() { regOBD("VILLAGE_PLAYER_ID"); return this; }

    protected ConditionValue _discordWebhookUrl;
    public ConditionValue xdfgetDiscordWebhookUrl()
    { if (_discordWebhookUrl == null) { _discordWebhookUrl = nCV(); }
      return _discordWebhookUrl; }
    protected ConditionValue xgetCValueDiscordWebhookUrl() { return xdfgetDiscordWebhookUrl(); }

    /**
     * Add order-by as ascend. <br>
     * DISCORD_WEBHOOK_URL: {NotNull, VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_DiscordWebhookUrl_Asc() { regOBA("DISCORD_WEBHOOK_URL"); return this; }

    /**
     * Add order-by as descend. <br>
     * DISCORD_WEBHOOK_URL: {NotNull, VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_DiscordWebhookUrl_Desc() { regOBD("DISCORD_WEBHOOK_URL"); return this; }

    protected ConditionValue _villageStart;
    public ConditionValue xdfgetVillageStart()
    { if (_villageStart == null) { _villageStart = nCV(); }
      return _villageStart; }
    protected ConditionValue xgetCValueVillageStart() { return xdfgetVillageStart(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_START: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillageStart_Asc() { regOBA("VILLAGE_START"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_START: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillageStart_Desc() { regOBD("VILLAGE_START"); return this; }

    protected ConditionValue _villageDaychange;
    public ConditionValue xdfgetVillageDaychange()
    { if (_villageDaychange == null) { _villageDaychange = nCV(); }
      return _villageDaychange; }
    protected ConditionValue xgetCValueVillageDaychange() { return xdfgetVillageDaychange(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_DAYCHANGE: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillageDaychange_Asc() { regOBA("VILLAGE_DAYCHANGE"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_DAYCHANGE: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillageDaychange_Desc() { regOBD("VILLAGE_DAYCHANGE"); return this; }

    protected ConditionValue _villageEpilogue;
    public ConditionValue xdfgetVillageEpilogue()
    { if (_villageEpilogue == null) { _villageEpilogue = nCV(); }
      return _villageEpilogue; }
    protected ConditionValue xgetCValueVillageEpilogue() { return xdfgetVillageEpilogue(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_EPILOGUE: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillageEpilogue_Asc() { regOBA("VILLAGE_EPILOGUE"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_EPILOGUE: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_VillageEpilogue_Desc() { regOBD("VILLAGE_EPILOGUE"); return this; }

    protected ConditionValue _receiveSecretSay;
    public ConditionValue xdfgetReceiveSecretSay()
    { if (_receiveSecretSay == null) { _receiveSecretSay = nCV(); }
      return _receiveSecretSay; }
    protected ConditionValue xgetCValueReceiveSecretSay() { return xdfgetReceiveSecretSay(); }

    /**
     * Add order-by as ascend. <br>
     * RECEIVE_SECRET_SAY: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_ReceiveSecretSay_Asc() { regOBA("RECEIVE_SECRET_SAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * RECEIVE_SECRET_SAY: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_ReceiveSecretSay_Desc() { regOBD("RECEIVE_SECRET_SAY"); return this; }

    protected ConditionValue _receiveAbilitySay;
    public ConditionValue xdfgetReceiveAbilitySay()
    { if (_receiveAbilitySay == null) { _receiveAbilitySay = nCV(); }
      return _receiveAbilitySay; }
    protected ConditionValue xgetCValueReceiveAbilitySay() { return xdfgetReceiveAbilitySay(); }

    /**
     * Add order-by as ascend. <br>
     * RECEIVE_ABILITY_SAY: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_ReceiveAbilitySay_Asc() { regOBA("RECEIVE_ABILITY_SAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * RECEIVE_ABILITY_SAY: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_ReceiveAbilitySay_Desc() { regOBD("RECEIVE_ABILITY_SAY"); return this; }

    protected ConditionValue _receiveAnchorSay;
    public ConditionValue xdfgetReceiveAnchorSay()
    { if (_receiveAnchorSay == null) { _receiveAnchorSay = nCV(); }
      return _receiveAnchorSay; }
    protected ConditionValue xgetCValueReceiveAnchorSay() { return xdfgetReceiveAnchorSay(); }

    /**
     * Add order-by as ascend. <br>
     * RECEIVE_ANCHOR_SAY: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_ReceiveAnchorSay_Asc() { regOBA("RECEIVE_ANCHOR_SAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * RECEIVE_ANCHOR_SAY: {NotNull, BIT}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_ReceiveAnchorSay_Desc() { regOBD("RECEIVE_ANCHOR_SAY"); return this; }

    protected ConditionValue _keyword;
    public ConditionValue xdfgetKeyword()
    { if (_keyword == null) { _keyword = nCV(); }
      return _keyword; }
    protected ConditionValue xgetCValueKeyword() { return xdfgetKeyword(); }

    /**
     * Add order-by as ascend. <br>
     * KEYWORD: {VARCHAR(255)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_Keyword_Asc() { regOBA("KEYWORD"); return this; }

    /**
     * Add order-by as descend. <br>
     * KEYWORD: {VARCHAR(255)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_Keyword_Desc() { regOBD("KEYWORD"); return this; }

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
    public BsVillagePlayerNotificationCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsVillagePlayerNotificationCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsVillagePlayerNotificationCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsVillagePlayerNotificationCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerNotificationCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsVillagePlayerNotificationCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillagePlayerNotificationCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillagePlayerNotificationCQ bq = (VillagePlayerNotificationCQ)bqs;
        VillagePlayerNotificationCQ uq = (VillagePlayerNotificationCQ)uqs;
        if (bq.hasConditionQueryVillagePlayer()) {
            uq.queryVillagePlayer().reflectRelationOnUnionQuery(bq.queryVillagePlayer(), uq.queryVillagePlayer());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
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
        String nrp = xresolveNRP("village_player_notification", "villagePlayer"); String jan = xresolveJAN(nrp, xgetNNLvl());
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
    public Map<String, VillagePlayerNotificationCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillagePlayerNotificationCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillagePlayerNotificationCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillagePlayerNotificationCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillagePlayerNotificationCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillagePlayerNotificationCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillagePlayerNotificationCQ> _myselfExistsMap;
    public Map<String, VillagePlayerNotificationCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillagePlayerNotificationCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillagePlayerNotificationCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillagePlayerNotificationCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillagePlayerNotificationCB.class.getName(); }
    protected String xCQ() { return VillagePlayerNotificationCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
