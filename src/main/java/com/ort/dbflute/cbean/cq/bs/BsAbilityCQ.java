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
 * The base condition-query of ABILITY.
 * @author DBFlute(AutoGenerator)
 */
public class BsAbilityCQ extends AbstractBsAbilityCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected AbilityCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsAbilityCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from ABILITY) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public AbilityCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected AbilityCIQ xcreateCIQ() {
        AbilityCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected AbilityCIQ xnewCIQ() {
        return new AbilityCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join ABILITY on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public AbilityCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        AbilityCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_VillageId_Desc() { regOBD("VILLAGE_ID"); return this; }

    protected ConditionValue _day;
    public ConditionValue xdfgetDay()
    { if (_day == null) { _day = nCV(); }
      return _day; }
    protected ConditionValue xgetCValueDay() { return xdfgetDay(); }

    /**
     * Add order-by as ascend. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_Day_Asc() { regOBA("DAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * DAY: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_Day_Desc() { regOBD("DAY"); return this; }

    protected ConditionValue _charaId;
    public ConditionValue xdfgetCharaId()
    { if (_charaId == null) { _charaId = nCV(); }
      return _charaId; }
    protected ConditionValue xgetCValueCharaId() { return xdfgetCharaId(); }

    /**
     * Add order-by as ascend. <br>
     * CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_CharaId_Asc() { regOBA("CHARA_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_ID: {PK, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_CharaId_Desc() { regOBD("CHARA_ID"); return this; }

    protected ConditionValue _attackerCharaId;
    public ConditionValue xdfgetAttackerCharaId()
    { if (_attackerCharaId == null) { _attackerCharaId = nCV(); }
      return _attackerCharaId; }
    protected ConditionValue xgetCValueAttackerCharaId() { return xdfgetAttackerCharaId(); }

    /**
     * Add order-by as ascend. <br>
     * ATTACKER_CHARA_ID: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_AttackerCharaId_Asc() { regOBA("ATTACKER_CHARA_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * ATTACKER_CHARA_ID: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_AttackerCharaId_Desc() { regOBD("ATTACKER_CHARA_ID"); return this; }

    protected ConditionValue _targetCharaId;
    public ConditionValue xdfgetTargetCharaId()
    { if (_targetCharaId == null) { _targetCharaId = nCV(); }
      return _targetCharaId; }
    protected ConditionValue xgetCValueTargetCharaId() { return xdfgetTargetCharaId(); }

    /**
     * Add order-by as ascend. <br>
     * TARGET_CHARA_ID: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetCharaId_Asc() { regOBA("TARGET_CHARA_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * TARGET_CHARA_ID: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetCharaId_Desc() { regOBD("TARGET_CHARA_ID"); return this; }

    protected ConditionValue _targetFootstep;
    public ConditionValue xdfgetTargetFootstep()
    { if (_targetFootstep == null) { _targetFootstep = nCV(); }
      return _targetFootstep; }
    protected ConditionValue xgetCValueTargetFootstep() { return xdfgetTargetFootstep(); }

    /**
     * Add order-by as ascend. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetFootstep_Asc() { regOBA("TARGET_FOOTSTEP"); return this; }

    /**
     * Add order-by as descend. <br>
     * TARGET_FOOTSTEP: {VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetFootstep_Desc() { regOBD("TARGET_FOOTSTEP"); return this; }

    protected ConditionValue _targetSkillCode;
    public ConditionValue xdfgetTargetSkillCode()
    { if (_targetSkillCode == null) { _targetSkillCode = nCV(); }
      return _targetSkillCode; }
    protected ConditionValue xgetCValueTargetSkillCode() { return xdfgetTargetSkillCode(); }

    /**
     * Add order-by as ascend. <br>
     * TARGET_SKILL_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetSkillCode_Asc() { regOBA("TARGET_SKILL_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * TARGET_SKILL_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetSkillCode_Desc() { regOBD("TARGET_SKILL_CODE"); return this; }

    protected ConditionValue _targetCampCode;
    public ConditionValue xdfgetTargetCampCode()
    { if (_targetCampCode == null) { _targetCampCode = nCV(); }
      return _targetCampCode; }
    protected ConditionValue xgetCValueTargetCampCode() { return xdfgetTargetCampCode(); }

    /**
     * Add order-by as ascend. <br>
     * TARGET_CAMP_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetCampCode_Asc() { regOBA("TARGET_CAMP_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * TARGET_CAMP_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetCampCode_Desc() { regOBD("TARGET_CAMP_CODE"); return this; }

    protected ConditionValue _targetRooms;
    public ConditionValue xdfgetTargetRooms()
    { if (_targetRooms == null) { _targetRooms = nCV(); }
      return _targetRooms; }
    protected ConditionValue xgetCValueTargetRooms() { return xdfgetTargetRooms(); }

    /**
     * Add order-by as ascend. <br>
     * TARGET_ROOMS: {VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetRooms_Asc() { regOBA("TARGET_ROOMS"); return this; }

    /**
     * Add order-by as descend. <br>
     * TARGET_ROOMS: {VARCHAR(1000)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_TargetRooms_Desc() { regOBD("TARGET_ROOMS"); return this; }

    protected ConditionValue _abilityTypeCode;
    public ConditionValue xdfgetAbilityTypeCode()
    { if (_abilityTypeCode == null) { _abilityTypeCode = nCV(); }
      return _abilityTypeCode; }
    protected ConditionValue xgetCValueAbilityTypeCode() { return xdfgetAbilityTypeCode(); }

    /**
     * Add order-by as ascend. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_AbilityTypeCode_Asc() { regOBA("ABILITY_TYPE_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * ABILITY_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to ABILITY_TYPE, classification=AbilityType}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_AbilityTypeCode_Desc() { regOBD("ABILITY_TYPE_CODE"); return this; }

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
    public BsAbilityCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsAbilityCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsAbilityCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsAbilityCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsAbilityCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
    public BsAbilityCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

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
    public BsAbilityCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        AbilityCQ bq = (AbilityCQ)bqs;
        AbilityCQ uq = (AbilityCQ)uqs;
        if (bq.hasConditionQueryAbilityType()) {
            uq.queryAbilityType().reflectRelationOnUnionQuery(bq.queryAbilityType(), uq.queryAbilityType());
        }
        if (bq.hasConditionQueryVillageDay()) {
            uq.queryVillageDay().reflectRelationOnUnionQuery(bq.queryVillageDay(), uq.queryVillageDay());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * ABILITY_TYPE by my ABILITY_TYPE_CODE, named 'abilityType'.
     * @return The instance of condition-query. (NotNull)
     */
    public AbilityTypeCQ queryAbilityType() {
        return xdfgetConditionQueryAbilityType();
    }
    public AbilityTypeCQ xdfgetConditionQueryAbilityType() {
        String prop = "abilityType";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryAbilityType()); xsetupOuterJoinAbilityType(); }
        return xgetQueRlMap(prop);
    }
    protected AbilityTypeCQ xcreateQueryAbilityType() {
        String nrp = xresolveNRP("ABILITY", "abilityType"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new AbilityTypeCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "abilityType", nrp);
    }
    protected void xsetupOuterJoinAbilityType() { xregOutJo("abilityType"); }
    public boolean hasConditionQueryAbilityType() { return xhasQueRlMap("abilityType"); }

    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillageDayCQ queryVillageDay() {
        return xdfgetConditionQueryVillageDay();
    }
    public VillageDayCQ xdfgetConditionQueryVillageDay() {
        String prop = "villageDay";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillageDay()); xsetupOuterJoinVillageDay(); }
        return xgetQueRlMap(prop);
    }
    protected VillageDayCQ xcreateQueryVillageDay() {
        String nrp = xresolveNRP("ABILITY", "villageDay"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillageDayCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villageDay", nrp);
    }
    protected void xsetupOuterJoinVillageDay() { xregOutJo("villageDay"); }
    public boolean hasConditionQueryVillageDay() { return xhasQueRlMap("villageDay"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, AbilityCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(AbilityCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return AbilityCB.class.getName(); }
    protected String xCQ() { return AbilityCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
