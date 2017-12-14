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
 * The base condition-query of chara.
 * @author DBFlute(AutoGenerator)
 */
public class BsCharaCQ extends AbstractBsCharaCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected CharaCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsCharaCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from chara) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public CharaCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected CharaCIQ xcreateCIQ() {
        CharaCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected CharaCIQ xnewCIQ() {
        return new CharaCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join chara on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public CharaCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        CharaCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _charaId;
    public ConditionValue xdfgetCharaId()
    { if (_charaId == null) { _charaId = nCV(); }
      return _charaId; }
    protected ConditionValue xgetCValueCharaId() { return xdfgetCharaId(); }

    public Map<String, VillagePlayerCQ> xdfgetCharaId_ExistsReferrer_VillagePlayerList() { return xgetSQueMap("charaId_ExistsReferrer_VillagePlayerList"); }
    public String keepCharaId_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("charaId_ExistsReferrer_VillagePlayerList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetCharaId_NotExistsReferrer_VillagePlayerList() { return xgetSQueMap("charaId_NotExistsReferrer_VillagePlayerList"); }
    public String keepCharaId_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("charaId_NotExistsReferrer_VillagePlayerList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetCharaId_SpecifyDerivedReferrer_VillagePlayerList() { return xgetSQueMap("charaId_SpecifyDerivedReferrer_VillagePlayerList"); }
    public String keepCharaId_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("charaId_SpecifyDerivedReferrer_VillagePlayerList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetCharaId_QueryDerivedReferrer_VillagePlayerList() { return xgetSQueMap("charaId_QueryDerivedReferrer_VillagePlayerList"); }
    public String keepCharaId_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("charaId_QueryDerivedReferrer_VillagePlayerList", sq); }
    public Map<String, Object> xdfgetCharaId_QueryDerivedReferrer_VillagePlayerListParameter() { return xgetSQuePmMap("charaId_QueryDerivedReferrer_VillagePlayerList"); }
    public String keepCharaId_QueryDerivedReferrer_VillagePlayerListParameter(Object pm) { return xkeepSQuePm("charaId_QueryDerivedReferrer_VillagePlayerList", pm); }

    /** 
     * Add order-by as ascend. <br>
     * CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaId_Asc() { regOBA("CHARA_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaId_Desc() { regOBD("CHARA_ID"); return this; }

    protected ConditionValue _charaName;
    public ConditionValue xdfgetCharaName()
    { if (_charaName == null) { _charaName = nCV(); }
      return _charaName; }
    protected ConditionValue xgetCValueCharaName() { return xdfgetCharaName(); }

    /** 
     * Add order-by as ascend. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaName_Asc() { regOBA("CHARA_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaName_Desc() { regOBD("CHARA_NAME"); return this; }

    protected ConditionValue _charaGroupId;
    public ConditionValue xdfgetCharaGroupId()
    { if (_charaGroupId == null) { _charaGroupId = nCV(); }
      return _charaGroupId; }
    protected ConditionValue xgetCValueCharaGroupId() { return xdfgetCharaGroupId(); }

    /** 
     * Add order-by as ascend. <br>
     * CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaGroupId_Asc() { regOBA("CHARA_GROUP_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaGroupId_Desc() { regOBD("CHARA_GROUP_ID"); return this; }

    protected ConditionValue _charaImgUrl;
    public ConditionValue xdfgetCharaImgUrl()
    { if (_charaImgUrl == null) { _charaImgUrl = nCV(); }
      return _charaImgUrl; }
    protected ConditionValue xgetCValueCharaImgUrl() { return xdfgetCharaImgUrl(); }

    /** 
     * Add order-by as ascend. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaImgUrl_Asc() { regOBA("CHARA_IMG_URL"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @return this. (NotNull)
     */
    public BsCharaCQ addOrderBy_CharaImgUrl_Desc() { regOBD("CHARA_IMG_URL"); return this; }

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
    public BsCharaCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsCharaCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        CharaCQ bq = (CharaCQ)bqs;
        CharaCQ uq = (CharaCQ)uqs;
        if (bq.hasConditionQueryCharaGroup()) {
            uq.queryCharaGroup().reflectRelationOnUnionQuery(bq.queryCharaGroup(), uq.queryCharaGroup());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * chara_group by my CHARA_GROUP_ID, named 'charaGroup'.
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
        String nrp = xresolveNRP("chara", "charaGroup"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new CharaGroupCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "charaGroup", nrp);
    }
    protected void xsetupOuterJoinCharaGroup() { xregOutJo("charaGroup"); }
    public boolean hasConditionQueryCharaGroup() { return xhasQueRlMap("charaGroup"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, CharaCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(CharaCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, CharaCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(CharaCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, CharaCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(CharaCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, CharaCQ> _myselfExistsMap;
    public Map<String, CharaCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(CharaCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, CharaCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(CharaCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return CharaCB.class.getName(); }
    protected String xCQ() { return CharaCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
