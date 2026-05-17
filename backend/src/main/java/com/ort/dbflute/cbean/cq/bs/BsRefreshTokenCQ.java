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
 * The base condition-query of refresh_token.
 * @author DBFlute(AutoGenerator)
 */
public class BsRefreshTokenCQ extends AbstractBsRefreshTokenCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected RefreshTokenCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsRefreshTokenCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from refresh_token) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public RefreshTokenCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected RefreshTokenCIQ xcreateCIQ() {
        RefreshTokenCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected RefreshTokenCIQ xnewCIQ() {
        return new RefreshTokenCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join refresh_token on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public RefreshTokenCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        RefreshTokenCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _refreshTokenId;
    public ConditionValue xdfgetRefreshTokenId()
    { if (_refreshTokenId == null) { _refreshTokenId = nCV(); }
      return _refreshTokenId; }
    protected ConditionValue xgetCValueRefreshTokenId() { return xdfgetRefreshTokenId(); }

    /**
     * Add order-by as ascend. <br>
     * REFRESH_TOKEN_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_RefreshTokenId_Asc() { regOBA("REFRESH_TOKEN_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * REFRESH_TOKEN_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_RefreshTokenId_Desc() { regOBD("REFRESH_TOKEN_ID"); return this; }

    protected ConditionValue _playerId;
    public ConditionValue xdfgetPlayerId()
    { if (_playerId == null) { _playerId = nCV(); }
      return _playerId; }
    protected ConditionValue xgetCValuePlayerId() { return xdfgetPlayerId(); }

    /**
     * Add order-by as ascend. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_PlayerId_Asc() { regOBA("PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_PlayerId_Desc() { regOBD("PLAYER_ID"); return this; }

    protected ConditionValue _tokenHash;
    public ConditionValue xdfgetTokenHash()
    { if (_tokenHash == null) { _tokenHash = nCV(); }
      return _tokenHash; }
    protected ConditionValue xgetCValueTokenHash() { return xdfgetTokenHash(); }

    /**
     * Add order-by as ascend. <br>
     * TOKEN_HASH: {UQ, NotNull, CHAR(64)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_TokenHash_Asc() { regOBA("TOKEN_HASH"); return this; }

    /**
     * Add order-by as descend. <br>
     * TOKEN_HASH: {UQ, NotNull, CHAR(64)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_TokenHash_Desc() { regOBD("TOKEN_HASH"); return this; }

    protected ConditionValue _expiresAt;
    public ConditionValue xdfgetExpiresAt()
    { if (_expiresAt == null) { _expiresAt = nCV(); }
      return _expiresAt; }
    protected ConditionValue xgetCValueExpiresAt() { return xdfgetExpiresAt(); }

    /**
     * Add order-by as ascend. <br>
     * EXPIRES_AT: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_ExpiresAt_Asc() { regOBA("EXPIRES_AT"); return this; }

    /**
     * Add order-by as descend. <br>
     * EXPIRES_AT: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_ExpiresAt_Desc() { regOBD("EXPIRES_AT"); return this; }

    protected ConditionValue _revoked;
    public ConditionValue xdfgetRevoked()
    { if (_revoked == null) { _revoked = nCV(); }
      return _revoked; }
    protected ConditionValue xgetCValueRevoked() { return xdfgetRevoked(); }

    /**
     * Add order-by as ascend. <br>
     * REVOKED: {NotNull, BIT, default=[0]}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_Revoked_Asc() { regOBA("REVOKED"); return this; }

    /**
     * Add order-by as descend. <br>
     * REVOKED: {NotNull, BIT, default=[0]}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_Revoked_Desc() { regOBD("REVOKED"); return this; }

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
    public BsRefreshTokenCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsRefreshTokenCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsRefreshTokenCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsRefreshTokenCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsRefreshTokenCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsRefreshTokenCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsRefreshTokenCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        RefreshTokenCQ bq = (RefreshTokenCQ)bqs;
        RefreshTokenCQ uq = (RefreshTokenCQ)uqs;
        if (bq.hasConditionQueryPlayer()) {
            uq.queryPlayer().reflectRelationOnUnionQuery(bq.queryPlayer(), uq.queryPlayer());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
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
        String nrp = xresolveNRP("refresh_token", "player"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new PlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "player", nrp);
    }
    protected void xsetupOuterJoinPlayer() { xregOutJo("player"); }
    public boolean hasConditionQueryPlayer() { return xhasQueRlMap("player"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, RefreshTokenCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(RefreshTokenCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, RefreshTokenCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(RefreshTokenCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, RefreshTokenCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(RefreshTokenCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, RefreshTokenCQ> _myselfExistsMap;
    public Map<String, RefreshTokenCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(RefreshTokenCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, RefreshTokenCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(RefreshTokenCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return RefreshTokenCB.class.getName(); }
    protected String xCQ() { return RefreshTokenCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
