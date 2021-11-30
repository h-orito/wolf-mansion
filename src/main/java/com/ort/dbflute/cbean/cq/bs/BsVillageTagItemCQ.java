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
 * The base condition-query of VILLAGE_TAG_ITEM.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillageTagItemCQ extends AbstractBsVillageTagItemCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillageTagItemCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillageTagItemCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from VILLAGE_TAG_ITEM) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillageTagItemCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillageTagItemCIQ xcreateCIQ() {
        VillageTagItemCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillageTagItemCIQ xnewCIQ() {
        return new VillageTagItemCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join VILLAGE_TAG_ITEM on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillageTagItemCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillageTagItemCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villageTagItemCode;
    public ConditionValue xdfgetVillageTagItemCode()
    { if (_villageTagItemCode == null) { _villageTagItemCode = nCV(); }
      return _villageTagItemCode; }
    protected ConditionValue xgetCValueVillageTagItemCode() { return xdfgetVillageTagItemCode(); }

    public Map<String, VillageTagCQ> xdfgetVillageTagItemCode_ExistsReferrer_VillageTagList() { return xgetSQueMap("villageTagItemCode_ExistsReferrer_VillageTagList"); }
    public String keepVillageTagItemCode_ExistsReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageTagItemCode_ExistsReferrer_VillageTagList", sq); }

    public Map<String, VillageTagCQ> xdfgetVillageTagItemCode_NotExistsReferrer_VillageTagList() { return xgetSQueMap("villageTagItemCode_NotExistsReferrer_VillageTagList"); }
    public String keepVillageTagItemCode_NotExistsReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageTagItemCode_NotExistsReferrer_VillageTagList", sq); }

    public Map<String, VillageTagCQ> xdfgetVillageTagItemCode_SpecifyDerivedReferrer_VillageTagList() { return xgetSQueMap("villageTagItemCode_SpecifyDerivedReferrer_VillageTagList"); }
    public String keepVillageTagItemCode_SpecifyDerivedReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageTagItemCode_SpecifyDerivedReferrer_VillageTagList", sq); }

    public Map<String, VillageTagCQ> xdfgetVillageTagItemCode_QueryDerivedReferrer_VillageTagList() { return xgetSQueMap("villageTagItemCode_QueryDerivedReferrer_VillageTagList"); }
    public String keepVillageTagItemCode_QueryDerivedReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageTagItemCode_QueryDerivedReferrer_VillageTagList", sq); }
    public Map<String, Object> xdfgetVillageTagItemCode_QueryDerivedReferrer_VillageTagListParameter() { return xgetSQuePmMap("villageTagItemCode_QueryDerivedReferrer_VillageTagList"); }
    public String keepVillageTagItemCode_QueryDerivedReferrer_VillageTagListParameter(Object pm) { return xkeepSQuePm("villageTagItemCode_QueryDerivedReferrer_VillageTagList", pm); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     * @return this. (NotNull)
     */
    public BsVillageTagItemCQ addOrderBy_VillageTagItemCode_Asc() { regOBA("VILLAGE_TAG_ITEM_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     * @return this. (NotNull)
     */
    public BsVillageTagItemCQ addOrderBy_VillageTagItemCode_Desc() { regOBD("VILLAGE_TAG_ITEM_CODE"); return this; }

    protected ConditionValue _villageTagItemName;
    public ConditionValue xdfgetVillageTagItemName()
    { if (_villageTagItemName == null) { _villageTagItemName = nCV(); }
      return _villageTagItemName; }
    protected ConditionValue xgetCValueVillageTagItemName() { return xdfgetVillageTagItemName(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @return this. (NotNull)
     */
    public BsVillageTagItemCQ addOrderBy_VillageTagItemName_Asc() { regOBA("VILLAGE_TAG_ITEM_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @return this. (NotNull)
     */
    public BsVillageTagItemCQ addOrderBy_VillageTagItemName_Desc() { regOBD("VILLAGE_TAG_ITEM_NAME"); return this; }

    protected ConditionValue _dispOrder;
    public ConditionValue xdfgetDispOrder()
    { if (_dispOrder == null) { _dispOrder = nCV(); }
      return _dispOrder; }
    protected ConditionValue xgetCValueDispOrder() { return xdfgetDispOrder(); }

    /**
     * Add order-by as ascend. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageTagItemCQ addOrderBy_DispOrder_Asc() { regOBA("DISP_ORDER"); return this; }

    /**
     * Add order-by as descend. <br>
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageTagItemCQ addOrderBy_DispOrder_Desc() { regOBD("DISP_ORDER"); return this; }

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
    public BsVillageTagItemCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsVillageTagItemCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, VillageTagItemCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillageTagItemCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillageTagItemCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillageTagItemCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillageTagItemCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillageTagItemCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillageTagItemCQ> _myselfExistsMap;
    public Map<String, VillageTagItemCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillageTagItemCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillageTagItemCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillageTagItemCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillageTagItemCB.class.getName(); }
    protected String xCQ() { return VillageTagItemCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
