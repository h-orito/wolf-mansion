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
 * The base condition-query of message.
 * @author DBFlute(AutoGenerator)
 */
public class BsMessageCQ extends AbstractBsMessageCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected MessageCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsMessageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from message) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public MessageCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected MessageCIQ xcreateCIQ() {
        MessageCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected MessageCIQ xnewCIQ() {
        return new MessageCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join message on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public MessageCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        MessageCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _messageId;
    public ConditionValue xdfgetMessageId()
    { if (_messageId == null) { _messageId = nCV(); }
      return _messageId; }
    protected ConditionValue xgetCValueMessageId() { return xdfgetMessageId(); }

    /** 
     * Add order-by as ascend. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageId_Asc() { regOBA("MESSAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageId_Desc() { regOBD("MESSAGE_ID"); return this; }

    protected ConditionValue _villageId;
    public ConditionValue xdfgetVillageId()
    { if (_villageId == null) { _villageId = nCV(); }
      return _villageId; }
    protected ConditionValue xgetCValueVillageId() { return xdfgetVillageId(); }

    /** 
     * Add order-by as ascend. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_VillageId_Desc() { regOBD("VILLAGE_ID"); return this; }

    protected ConditionValue _villagePlayerId;
    public ConditionValue xdfgetVillagePlayerId()
    { if (_villagePlayerId == null) { _villagePlayerId = nCV(); }
      return _villagePlayerId; }
    protected ConditionValue xgetCValueVillagePlayerId() { return xdfgetVillagePlayerId(); }

    /** 
     * Add order-by as ascend. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_VillagePlayerId_Asc() { regOBA("VILLAGE_PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_VillagePlayerId_Desc() { regOBD("VILLAGE_PLAYER_ID"); return this; }

    protected ConditionValue _playerId;
    public ConditionValue xdfgetPlayerId()
    { if (_playerId == null) { _playerId = nCV(); }
      return _playerId; }
    protected ConditionValue xgetCValuePlayerId() { return xdfgetPlayerId(); }

    /** 
     * Add order-by as ascend. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to player}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_PlayerId_Asc() { regOBA("PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to player}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_PlayerId_Desc() { regOBD("PLAYER_ID"); return this; }

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
    public BsMessageCQ addOrderBy_ToVillagePlayerId_Asc() { regOBA("TO_VILLAGE_PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_ToVillagePlayerId_Desc() { regOBD("TO_VILLAGE_PLAYER_ID"); return this; }

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
    public BsMessageCQ addOrderBy_Day_Asc() { regOBA("DAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * DAY: {NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_Day_Desc() { regOBD("DAY"); return this; }

    protected ConditionValue _messageTypeCode;
    public ConditionValue xdfgetMessageTypeCode()
    { if (_messageTypeCode == null) { _messageTypeCode = nCV(); }
      return _messageTypeCode; }
    protected ConditionValue xgetCValueMessageTypeCode() { return xdfgetMessageTypeCode(); }

    /** 
     * Add order-by as ascend. <br>
     * MESSAGE_TYPE_CODE: {IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageTypeCode_Asc() { regOBA("MESSAGE_TYPE_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * MESSAGE_TYPE_CODE: {IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageTypeCode_Desc() { regOBD("MESSAGE_TYPE_CODE"); return this; }

    protected ConditionValue _messageNumber;
    public ConditionValue xdfgetMessageNumber()
    { if (_messageNumber == null) { _messageNumber = nCV(); }
      return _messageNumber; }
    protected ConditionValue xgetCValueMessageNumber() { return xdfgetMessageNumber(); }

    /** 
     * Add order-by as ascend. <br>
     * MESSAGE_NUMBER: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageNumber_Asc() { regOBA("MESSAGE_NUMBER"); return this; }

    /**
     * Add order-by as descend. <br>
     * MESSAGE_NUMBER: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageNumber_Desc() { regOBD("MESSAGE_NUMBER"); return this; }

    protected ConditionValue _messageContent;
    public ConditionValue xdfgetMessageContent()
    { if (_messageContent == null) { _messageContent = nCV(); }
      return _messageContent; }
    protected ConditionValue xgetCValueMessageContent() { return xdfgetMessageContent(); }

    /** 
     * Add order-by as ascend. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageContent_Asc() { regOBA("MESSAGE_CONTENT"); return this; }

    /**
     * Add order-by as descend. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageContent_Desc() { regOBD("MESSAGE_CONTENT"); return this; }

    protected ConditionValue _messageDatetime;
    public ConditionValue xdfgetMessageDatetime()
    { if (_messageDatetime == null) { _messageDatetime = nCV(); }
      return _messageDatetime; }
    protected ConditionValue xgetCValueMessageDatetime() { return xdfgetMessageDatetime(); }

    /** 
     * Add order-by as ascend. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageDatetime_Asc() { regOBA("MESSAGE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_MessageDatetime_Desc() { regOBD("MESSAGE_DATETIME"); return this; }

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
    public BsMessageCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsMessageCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsMessageCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsMessageCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsMessageCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsMessageCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsMessageCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        MessageCQ bq = (MessageCQ)bqs;
        MessageCQ uq = (MessageCQ)uqs;
        if (bq.hasConditionQueryMessageType()) {
            uq.queryMessageType().reflectRelationOnUnionQuery(bq.queryMessageType(), uq.queryMessageType());
        }
        if (bq.hasConditionQueryPlayer()) {
            uq.queryPlayer().reflectRelationOnUnionQuery(bq.queryPlayer(), uq.queryPlayer());
        }
        if (bq.hasConditionQueryVillagePlayerByToVillagePlayerId()) {
            uq.queryVillagePlayerByToVillagePlayerId().reflectRelationOnUnionQuery(bq.queryVillagePlayerByToVillagePlayerId(), uq.queryVillagePlayerByToVillagePlayerId());
        }
        if (bq.hasConditionQueryVillage()) {
            uq.queryVillage().reflectRelationOnUnionQuery(bq.queryVillage(), uq.queryVillage());
        }
        if (bq.hasConditionQueryVillagePlayerByVillagePlayerId()) {
            uq.queryVillagePlayerByVillagePlayerId().reflectRelationOnUnionQuery(bq.queryVillagePlayerByVillagePlayerId(), uq.queryVillagePlayerByVillagePlayerId());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'.
     * @return The instance of condition-query. (NotNull)
     */
    public MessageTypeCQ queryMessageType() {
        return xdfgetConditionQueryMessageType();
    }
    public MessageTypeCQ xdfgetConditionQueryMessageType() {
        String prop = "messageType";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryMessageType()); xsetupOuterJoinMessageType(); }
        return xgetQueRlMap(prop);
    }
    protected MessageTypeCQ xcreateQueryMessageType() {
        String nrp = xresolveNRP("message", "messageType"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new MessageTypeCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "messageType", nrp);
    }
    protected void xsetupOuterJoinMessageType() { xregOutJo("messageType"); }
    public boolean hasConditionQueryMessageType() { return xhasQueRlMap("messageType"); }

    /**
     * Get the condition-query for relation table. <br>
     * PLAYER by my PLAYER_ID, named 'player'.
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
        String nrp = xresolveNRP("message", "player"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new PlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "player", nrp);
    }
    protected void xsetupOuterJoinPlayer() { xregOutJo("player"); }
    public boolean hasConditionQueryPlayer() { return xhasQueRlMap("player"); }

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
        String nrp = xresolveNRP("message", "villagePlayerByToVillagePlayerId"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillagePlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villagePlayerByToVillagePlayerId", nrp);
    }
    protected void xsetupOuterJoinVillagePlayerByToVillagePlayerId() { xregOutJo("villagePlayerByToVillagePlayerId"); }
    public boolean hasConditionQueryVillagePlayerByToVillagePlayerId() { return xhasQueRlMap("villagePlayerByToVillagePlayerId"); }

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
        String nrp = xresolveNRP("message", "village"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillageCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "village", nrp);
    }
    protected void xsetupOuterJoinVillage() { xregOutJo("village"); }
    public boolean hasConditionQueryVillage() { return xhasQueRlMap("village"); }

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
        String nrp = xresolveNRP("message", "villagePlayerByVillagePlayerId"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillagePlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villagePlayerByVillagePlayerId", nrp);
    }
    protected void xsetupOuterJoinVillagePlayerByVillagePlayerId() { xregOutJo("villagePlayerByVillagePlayerId"); }
    public boolean hasConditionQueryVillagePlayerByVillagePlayerId() { return xhasQueRlMap("villagePlayerByVillagePlayerId"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, MessageCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(MessageCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, MessageCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(MessageCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, MessageCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(MessageCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, MessageCQ> _myselfExistsMap;
    public Map<String, MessageCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(MessageCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, MessageCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(MessageCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return MessageCB.class.getName(); }
    protected String xCQ() { return MessageCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
