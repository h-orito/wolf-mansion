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
 * The base condition-query of message_type.
 * @author DBFlute(AutoGenerator)
 */
public class BsMessageTypeCQ extends AbstractBsMessageTypeCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected MessageTypeCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsMessageTypeCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from message_type) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public MessageTypeCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected MessageTypeCIQ xcreateCIQ() {
        MessageTypeCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected MessageTypeCIQ xnewCIQ() {
        return new MessageTypeCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join message_type on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public MessageTypeCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        MessageTypeCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _messageTypeCode;
    public ConditionValue xdfgetMessageTypeCode()
    { if (_messageTypeCode == null) { _messageTypeCode = nCV(); }
      return _messageTypeCode; }
    protected ConditionValue xgetCValueMessageTypeCode() { return xdfgetMessageTypeCode(); }

    public Map<String, MessageCQ> xdfgetMessageTypeCode_ExistsReferrer_MessageList() { return xgetSQueMap("messageTypeCode_ExistsReferrer_MessageList"); }
    public String keepMessageTypeCode_ExistsReferrer_MessageList(MessageCQ sq) { return xkeepSQue("messageTypeCode_ExistsReferrer_MessageList", sq); }

    public Map<String, NormalSayRestrictionCQ> xdfgetMessageTypeCode_ExistsReferrer_NormalSayRestrictionList() { return xgetSQueMap("messageTypeCode_ExistsReferrer_NormalSayRestrictionList"); }
    public String keepMessageTypeCode_ExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_ExistsReferrer_NormalSayRestrictionList", sq); }

    public Map<String, SkillSayRestrictionCQ> xdfgetMessageTypeCode_ExistsReferrer_SkillSayRestrictionList() { return xgetSQueMap("messageTypeCode_ExistsReferrer_SkillSayRestrictionList"); }
    public String keepMessageTypeCode_ExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_ExistsReferrer_SkillSayRestrictionList", sq); }

    public Map<String, MessageCQ> xdfgetMessageTypeCode_NotExistsReferrer_MessageList() { return xgetSQueMap("messageTypeCode_NotExistsReferrer_MessageList"); }
    public String keepMessageTypeCode_NotExistsReferrer_MessageList(MessageCQ sq) { return xkeepSQue("messageTypeCode_NotExistsReferrer_MessageList", sq); }

    public Map<String, NormalSayRestrictionCQ> xdfgetMessageTypeCode_NotExistsReferrer_NormalSayRestrictionList() { return xgetSQueMap("messageTypeCode_NotExistsReferrer_NormalSayRestrictionList"); }
    public String keepMessageTypeCode_NotExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_NotExistsReferrer_NormalSayRestrictionList", sq); }

    public Map<String, SkillSayRestrictionCQ> xdfgetMessageTypeCode_NotExistsReferrer_SkillSayRestrictionList() { return xgetSQueMap("messageTypeCode_NotExistsReferrer_SkillSayRestrictionList"); }
    public String keepMessageTypeCode_NotExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_NotExistsReferrer_SkillSayRestrictionList", sq); }

    public Map<String, MessageCQ> xdfgetMessageTypeCode_SpecifyDerivedReferrer_MessageList() { return xgetSQueMap("messageTypeCode_SpecifyDerivedReferrer_MessageList"); }
    public String keepMessageTypeCode_SpecifyDerivedReferrer_MessageList(MessageCQ sq) { return xkeepSQue("messageTypeCode_SpecifyDerivedReferrer_MessageList", sq); }

    public Map<String, NormalSayRestrictionCQ> xdfgetMessageTypeCode_SpecifyDerivedReferrer_NormalSayRestrictionList() { return xgetSQueMap("messageTypeCode_SpecifyDerivedReferrer_NormalSayRestrictionList"); }
    public String keepMessageTypeCode_SpecifyDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_SpecifyDerivedReferrer_NormalSayRestrictionList", sq); }

    public Map<String, SkillSayRestrictionCQ> xdfgetMessageTypeCode_SpecifyDerivedReferrer_SkillSayRestrictionList() { return xgetSQueMap("messageTypeCode_SpecifyDerivedReferrer_SkillSayRestrictionList"); }
    public String keepMessageTypeCode_SpecifyDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_SpecifyDerivedReferrer_SkillSayRestrictionList", sq); }

    public Map<String, MessageCQ> xdfgetMessageTypeCode_QueryDerivedReferrer_MessageList() { return xgetSQueMap("messageTypeCode_QueryDerivedReferrer_MessageList"); }
    public String keepMessageTypeCode_QueryDerivedReferrer_MessageList(MessageCQ sq) { return xkeepSQue("messageTypeCode_QueryDerivedReferrer_MessageList", sq); }
    public Map<String, Object> xdfgetMessageTypeCode_QueryDerivedReferrer_MessageListParameter() { return xgetSQuePmMap("messageTypeCode_QueryDerivedReferrer_MessageList"); }
    public String keepMessageTypeCode_QueryDerivedReferrer_MessageListParameter(Object pm) { return xkeepSQuePm("messageTypeCode_QueryDerivedReferrer_MessageList", pm); }

    public Map<String, NormalSayRestrictionCQ> xdfgetMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList() { return xgetSQueMap("messageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList"); }
    public String keepMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList", sq); }
    public Map<String, Object> xdfgetMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionListParameter() { return xgetSQuePmMap("messageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList"); }
    public String keepMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionListParameter(Object pm) { return xkeepSQuePm("messageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList", pm); }

    public Map<String, SkillSayRestrictionCQ> xdfgetMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList() { return xgetSQueMap("messageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList"); }
    public String keepMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("messageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList", sq); }
    public Map<String, Object> xdfgetMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionListParameter() { return xgetSQuePmMap("messageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList"); }
    public String keepMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionListParameter(Object pm) { return xkeepSQuePm("messageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList", pm); }

    /**
     * Add order-by as ascend. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     * @return this. (NotNull)
     */
    public BsMessageTypeCQ addOrderBy_MessageTypeCode_Asc() { regOBA("MESSAGE_TYPE_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     * @return this. (NotNull)
     */
    public BsMessageTypeCQ addOrderBy_MessageTypeCode_Desc() { regOBD("MESSAGE_TYPE_CODE"); return this; }

    protected ConditionValue _messageTypeName;
    public ConditionValue xdfgetMessageTypeName()
    { if (_messageTypeName == null) { _messageTypeName = nCV(); }
      return _messageTypeName; }
    protected ConditionValue xgetCValueMessageTypeName() { return xdfgetMessageTypeName(); }

    /**
     * Add order-by as ascend. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsMessageTypeCQ addOrderBy_MessageTypeName_Asc() { regOBA("MESSAGE_TYPE_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsMessageTypeCQ addOrderBy_MessageTypeName_Desc() { regOBD("MESSAGE_TYPE_NAME"); return this; }

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
    public BsMessageTypeCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsMessageTypeCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

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
    public Map<String, MessageTypeCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(MessageTypeCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, MessageTypeCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(MessageTypeCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, MessageTypeCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(MessageTypeCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, MessageTypeCQ> _myselfExistsMap;
    public Map<String, MessageTypeCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(MessageTypeCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, MessageTypeCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(MessageTypeCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return MessageTypeCB.class.getName(); }
    protected String xCQ() { return MessageTypeCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
