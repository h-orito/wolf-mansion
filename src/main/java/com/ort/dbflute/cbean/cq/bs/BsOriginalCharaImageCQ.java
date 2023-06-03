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
 * The base condition-query of original_chara_image.
 * @author DBFlute(AutoGenerator)
 */
public class BsOriginalCharaImageCQ extends AbstractBsOriginalCharaImageCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected OriginalCharaImageCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsOriginalCharaImageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from original_chara_image) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public OriginalCharaImageCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected OriginalCharaImageCIQ xcreateCIQ() {
        OriginalCharaImageCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected OriginalCharaImageCIQ xnewCIQ() {
        return new OriginalCharaImageCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join original_chara_image on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public OriginalCharaImageCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        OriginalCharaImageCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _originalCharaImageId;
    public ConditionValue xdfgetOriginalCharaImageId()
    { if (_originalCharaImageId == null) { _originalCharaImageId = nCV(); }
      return _originalCharaImageId; }
    protected ConditionValue xgetCValueOriginalCharaImageId() { return xdfgetOriginalCharaImageId(); }

    /**
     * Add order-by as ascend. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_OriginalCharaImageId_Asc() { regOBA("ORIGINAL_CHARA_IMAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_OriginalCharaImageId_Desc() { regOBD("ORIGINAL_CHARA_IMAGE_ID"); return this; }

    protected ConditionValue _originalCharaId;
    public ConditionValue xdfgetOriginalCharaId()
    { if (_originalCharaId == null) { _originalCharaId = nCV(); }
      return _originalCharaId; }
    protected ConditionValue xgetCValueOriginalCharaId() { return xdfgetOriginalCharaId(); }

    /**
     * Add order-by as ascend. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_OriginalCharaId_Asc() { regOBA("ORIGINAL_CHARA_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_OriginalCharaId_Desc() { regOBD("ORIGINAL_CHARA_ID"); return this; }

    protected ConditionValue _faceTypeName;
    public ConditionValue xdfgetFaceTypeName()
    { if (_faceTypeName == null) { _faceTypeName = nCV(); }
      return _faceTypeName; }
    protected ConditionValue xgetCValueFaceTypeName() { return xdfgetFaceTypeName(); }

    /**
     * Add order-by as ascend. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_FaceTypeName_Asc() { regOBA("FACE_TYPE_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_FaceTypeName_Desc() { regOBD("FACE_TYPE_NAME"); return this; }

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
    public BsOriginalCharaImageCQ addOrderBy_CharaImgUrl_Asc() { regOBA("CHARA_IMG_URL"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_CharaImgUrl_Desc() { regOBD("CHARA_IMG_URL"); return this; }

    protected ConditionValue _isDisplay;
    public ConditionValue xdfgetIsDisplay()
    { if (_isDisplay == null) { _isDisplay = nCV(); }
      return _isDisplay; }
    protected ConditionValue xgetCValueIsDisplay() { return xdfgetIsDisplay(); }

    /**
     * Add order-by as ascend. <br>
     * IS_DISPLAY: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_IsDisplay_Asc() { regOBA("IS_DISPLAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * IS_DISPLAY: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_IsDisplay_Desc() { regOBD("IS_DISPLAY"); return this; }

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
    public BsOriginalCharaImageCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsOriginalCharaImageCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsOriginalCharaImageCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsOriginalCharaImageCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsOriginalCharaImageCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsOriginalCharaImageCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsOriginalCharaImageCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        OriginalCharaImageCQ bq = (OriginalCharaImageCQ)bqs;
        OriginalCharaImageCQ uq = (OriginalCharaImageCQ)uqs;
        if (bq.hasConditionQueryOriginalChara()) {
            uq.queryOriginalChara().reflectRelationOnUnionQuery(bq.queryOriginalChara(), uq.queryOriginalChara());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * ORIGINAL_CHARA by my ORIGINAL_CHARA_ID, named 'originalChara'.
     * @return The instance of condition-query. (NotNull)
     */
    public OriginalCharaCQ queryOriginalChara() {
        return xdfgetConditionQueryOriginalChara();
    }
    public OriginalCharaCQ xdfgetConditionQueryOriginalChara() {
        String prop = "originalChara";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryOriginalChara()); xsetupOuterJoinOriginalChara(); }
        return xgetQueRlMap(prop);
    }
    protected OriginalCharaCQ xcreateQueryOriginalChara() {
        String nrp = xresolveNRP("original_chara_image", "originalChara"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new OriginalCharaCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "originalChara", nrp);
    }
    protected void xsetupOuterJoinOriginalChara() { xregOutJo("originalChara"); }
    public boolean hasConditionQueryOriginalChara() { return xhasQueRlMap("originalChara"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, OriginalCharaImageCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(OriginalCharaImageCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, OriginalCharaImageCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(OriginalCharaImageCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, OriginalCharaImageCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(OriginalCharaImageCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, OriginalCharaImageCQ> _myselfExistsMap;
    public Map<String, OriginalCharaImageCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(OriginalCharaImageCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, OriginalCharaImageCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(OriginalCharaImageCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return OriginalCharaImageCB.class.getName(); }
    protected String xCQ() { return OriginalCharaImageCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
