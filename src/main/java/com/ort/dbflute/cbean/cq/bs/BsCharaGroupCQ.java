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
 * The base condition-query of CHARA_GROUP.
 * @author DBFlute(AutoGenerator)
 */
public class BsCharaGroupCQ extends AbstractBsCharaGroupCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected CharaGroupCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsCharaGroupCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from CHARA_GROUP) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public CharaGroupCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected CharaGroupCIQ xcreateCIQ() {
        CharaGroupCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected CharaGroupCIQ xnewCIQ() {
        return new CharaGroupCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join CHARA_GROUP on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public CharaGroupCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        CharaGroupCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _charaGroupId;
    public ConditionValue xdfgetCharaGroupId()
    { if (_charaGroupId == null) { _charaGroupId = nCV(); }
      return _charaGroupId; }
    protected ConditionValue xgetCValueCharaGroupId() { return xdfgetCharaGroupId(); }

    public Map<String, CharaCQ> xdfgetCharaGroupId_ExistsReferrer_CharaList() { return xgetSQueMap("charaGroupId_ExistsReferrer_CharaList"); }
    public String keepCharaGroupId_ExistsReferrer_CharaList(CharaCQ sq) { return xkeepSQue("charaGroupId_ExistsReferrer_CharaList", sq); }

    public Map<String, CharaCQ> xdfgetCharaGroupId_NotExistsReferrer_CharaList() { return xgetSQueMap("charaGroupId_NotExistsReferrer_CharaList"); }
    public String keepCharaGroupId_NotExistsReferrer_CharaList(CharaCQ sq) { return xkeepSQue("charaGroupId_NotExistsReferrer_CharaList", sq); }

    public Map<String, CharaCQ> xdfgetCharaGroupId_SpecifyDerivedReferrer_CharaList() { return xgetSQueMap("charaGroupId_SpecifyDerivedReferrer_CharaList"); }
    public String keepCharaGroupId_SpecifyDerivedReferrer_CharaList(CharaCQ sq) { return xkeepSQue("charaGroupId_SpecifyDerivedReferrer_CharaList", sq); }

    public Map<String, CharaCQ> xdfgetCharaGroupId_QueryDerivedReferrer_CharaList() { return xgetSQueMap("charaGroupId_QueryDerivedReferrer_CharaList"); }
    public String keepCharaGroupId_QueryDerivedReferrer_CharaList(CharaCQ sq) { return xkeepSQue("charaGroupId_QueryDerivedReferrer_CharaList", sq); }
    public Map<String, Object> xdfgetCharaGroupId_QueryDerivedReferrer_CharaListParameter() { return xgetSQuePmMap("charaGroupId_QueryDerivedReferrer_CharaList"); }
    public String keepCharaGroupId_QueryDerivedReferrer_CharaListParameter(Object pm) { return xkeepSQuePm("charaGroupId_QueryDerivedReferrer_CharaList", pm); }

    /** 
     * Add order-by as ascend. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsCharaGroupCQ addOrderBy_CharaGroupId_Asc() { regOBA("CHARA_GROUP_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsCharaGroupCQ addOrderBy_CharaGroupId_Desc() { regOBD("CHARA_GROUP_ID"); return this; }

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
    public BsCharaGroupCQ addOrderBy_CharaName_Asc() { regOBA("CHARA_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsCharaGroupCQ addOrderBy_CharaName_Desc() { regOBD("CHARA_NAME"); return this; }

    protected ConditionValue _designerId;
    public ConditionValue xdfgetDesignerId()
    { if (_designerId == null) { _designerId = nCV(); }
      return _designerId; }
    protected ConditionValue xgetCValueDesignerId() { return xdfgetDesignerId(); }

    /** 
     * Add order-by as ascend. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to DESIGNER}
     * @return this. (NotNull)
     */
    public BsCharaGroupCQ addOrderBy_DesignerId_Asc() { regOBA("DESIGNER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to DESIGNER}
     * @return this. (NotNull)
     */
    public BsCharaGroupCQ addOrderBy_DesignerId_Desc() { regOBD("DESIGNER_ID"); return this; }

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
    public BsCharaGroupCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsCharaGroupCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        CharaGroupCQ bq = (CharaGroupCQ)bqs;
        CharaGroupCQ uq = (CharaGroupCQ)uqs;
        if (bq.hasConditionQueryDesigner()) {
            uq.queryDesigner().reflectRelationOnUnionQuery(bq.queryDesigner(), uq.queryDesigner());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * DESIGNER by my DESIGNER_ID, named 'designer'.
     * @return The instance of condition-query. (NotNull)
     */
    public DesignerCQ queryDesigner() {
        return xdfgetConditionQueryDesigner();
    }
    public DesignerCQ xdfgetConditionQueryDesigner() {
        String prop = "designer";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryDesigner()); xsetupOuterJoinDesigner(); }
        return xgetQueRlMap(prop);
    }
    protected DesignerCQ xcreateQueryDesigner() {
        String nrp = xresolveNRP("CHARA_GROUP", "designer"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new DesignerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "designer", nrp);
    }
    protected void xsetupOuterJoinDesigner() { xregOutJo("designer"); }
    public boolean hasConditionQueryDesigner() { return xhasQueRlMap("designer"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, CharaGroupCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(CharaGroupCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, CharaGroupCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(CharaGroupCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, CharaGroupCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(CharaGroupCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, CharaGroupCQ> _myselfExistsMap;
    public Map<String, CharaGroupCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(CharaGroupCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, CharaGroupCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(CharaGroupCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return CharaGroupCB.class.getName(); }
    protected String xCQ() { return CharaGroupCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
