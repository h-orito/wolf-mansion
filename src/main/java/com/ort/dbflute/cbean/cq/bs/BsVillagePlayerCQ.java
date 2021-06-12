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
 * The base condition-query of VILLAGE_PLAYER.
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
     * {select ... from ... left outer join (select * from VILLAGE_PLAYER) where FOO = [value] ...}
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
     * {select ... from ... left outer join VILLAGE_PLAYER on ... and FOO = [value] ...}
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

    public Map<String, CommitCQ> xdfgetVillagePlayerId_ExistsReferrer_CommitList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_CommitList"); }
    public String keepVillagePlayerId_ExistsReferrer_CommitList(CommitCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_CommitList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList", sq); }

    public Map<String, MessageSendtoCQ> xdfgetVillagePlayerId_ExistsReferrer_MessageSendtoList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_MessageSendtoList"); }
    public String keepVillagePlayerId_ExistsReferrer_MessageSendtoList(MessageSendtoCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_MessageSendtoList", sq); }

    public Map<String, VillagePlayerDeadHistoryCQ> xdfgetVillagePlayerId_ExistsReferrer_VillagePlayerDeadHistoryList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_VillagePlayerDeadHistoryList"); }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_VillagePlayerDeadHistoryList", sq); }

    public Map<String, VillagePlayerRoomHistoryCQ> xdfgetVillagePlayerId_ExistsReferrer_VillagePlayerRoomHistoryList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_VillagePlayerRoomHistoryList"); }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_VillagePlayerRoomHistoryList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_ExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_ExistsReferrer_VillagePlayerStatusByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_ExistsReferrer_VillagePlayerStatusByVillagePlayerIdList"); }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_ExistsReferrer_VillagePlayerStatusByVillagePlayerIdList", sq); }

    public Map<String, CommitCQ> xdfgetVillagePlayerId_NotExistsReferrer_CommitList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_CommitList"); }
    public String keepVillagePlayerId_NotExistsReferrer_CommitList(CommitCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_CommitList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList", sq); }

    public Map<String, MessageSendtoCQ> xdfgetVillagePlayerId_NotExistsReferrer_MessageSendtoList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_MessageSendtoList"); }
    public String keepVillagePlayerId_NotExistsReferrer_MessageSendtoList(MessageSendtoCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_MessageSendtoList", sq); }

    public Map<String, VillagePlayerDeadHistoryCQ> xdfgetVillagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList"); }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList", sq); }

    public Map<String, VillagePlayerRoomHistoryCQ> xdfgetVillagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList"); }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList"); }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList", sq); }

    public Map<String, CommitCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_CommitList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_CommitList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_CommitList(CommitCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_CommitList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList", sq); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList", sq); }

    public Map<String, MessageSendtoCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_MessageSendtoList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_MessageSendtoList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageSendtoList(MessageSendtoCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_MessageSendtoList", sq); }

    public Map<String, VillagePlayerDeadHistoryCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerDeadHistoryList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerDeadHistoryList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerDeadHistoryList", sq); }

    public Map<String, VillagePlayerRoomHistoryCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerRoomHistoryList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerRoomHistoryList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerRoomHistoryList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList", sq); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList"); }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList", sq); }

    public Map<String, CommitCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_CommitList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_CommitList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_CommitList(CommitCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_CommitList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_CommitListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_CommitList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_CommitListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_CommitList", pm); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList", pm); }

    public Map<String, MessageCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList", pm); }

    public Map<String, MessageSendtoCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageSendtoList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_MessageSendtoList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoList(MessageSendtoCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_MessageSendtoList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_MessageSendtoListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_MessageSendtoList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_MessageSendtoList", pm); }

    public Map<String, VillagePlayerDeadHistoryCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList", pm); }

    public Map<String, VillagePlayerRoomHistoryCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList", pm); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList", pm); }

    public Map<String, VillagePlayerStatusCQ> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList() { return xgetSQueMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq) { return xkeepSQue("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList", sq); }
    public Map<String, Object> xdfgetVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdListParameter() { return xgetSQuePmMap("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList"); }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdListParameter(Object pm) { return xkeepSQuePm("villagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList", pm); }

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
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_PlayerId_Asc() { regOBA("PLAYER_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
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
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CharaId_Asc() { regOBA("CHARA_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CharaId_Desc() { regOBD("CHARA_ID"); return this; }

    protected ConditionValue _skillCode;
    public ConditionValue xdfgetSkillCode()
    { if (_skillCode == null) { _skillCode = nCV(); }
      return _skillCode; }
    protected ConditionValue xgetCValueSkillCode() { return xdfgetSkillCode(); }

    /**
     * Add order-by as ascend. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_SkillCode_Asc() { regOBA("SKILL_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_SkillCode_Desc() { regOBD("SKILL_CODE"); return this; }

    protected ConditionValue _requestSkillCode;
    public ConditionValue xdfgetRequestSkillCode()
    { if (_requestSkillCode == null) { _requestSkillCode = nCV(); }
      return _requestSkillCode; }
    protected ConditionValue xgetCValueRequestSkillCode() { return xdfgetRequestSkillCode(); }

    /**
     * Add order-by as ascend. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_RequestSkillCode_Asc() { regOBA("REQUEST_SKILL_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_RequestSkillCode_Desc() { regOBD("REQUEST_SKILL_CODE"); return this; }

    protected ConditionValue _secondRequestSkillCode;
    public ConditionValue xdfgetSecondRequestSkillCode()
    { if (_secondRequestSkillCode == null) { _secondRequestSkillCode = nCV(); }
      return _secondRequestSkillCode; }
    protected ConditionValue xgetCValueSecondRequestSkillCode() { return xdfgetSecondRequestSkillCode(); }

    /**
     * Add order-by as ascend. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_SecondRequestSkillCode_Asc() { regOBA("SECOND_REQUEST_SKILL_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_SecondRequestSkillCode_Desc() { regOBD("SECOND_REQUEST_SKILL_CODE"); return this; }

    protected ConditionValue _roomNumber;
    public ConditionValue xdfgetRoomNumber()
    { if (_roomNumber == null) { _roomNumber = nCV(); }
      return _roomNumber; }
    protected ConditionValue xgetCValueRoomNumber() { return xdfgetRoomNumber(); }

    /**
     * Add order-by as ascend. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_RoomNumber_Asc() { regOBA("ROOM_NUMBER"); return this; }

    /**
     * Add order-by as descend. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_RoomNumber_Desc() { regOBD("ROOM_NUMBER"); return this; }

    protected ConditionValue _isDead;
    public ConditionValue xdfgetIsDead()
    { if (_isDead == null) { _isDead = nCV(); }
      return _isDead; }
    protected ConditionValue xgetCValueIsDead() { return xdfgetIsDead(); }

    /**
     * Add order-by as ascend. <br>
     * IS_DEAD: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsDead_Asc() { regOBA("IS_DEAD"); return this; }

    /**
     * Add order-by as descend. <br>
     * IS_DEAD: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsDead_Desc() { regOBD("IS_DEAD"); return this; }

    protected ConditionValue _isSpectator;
    public ConditionValue xdfgetIsSpectator()
    { if (_isSpectator == null) { _isSpectator = nCV(); }
      return _isSpectator; }
    protected ConditionValue xgetCValueIsSpectator() { return xdfgetIsSpectator(); }

    /**
     * Add order-by as ascend. <br>
     * IS_SPECTATOR: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsSpectator_Asc() { regOBA("IS_SPECTATOR"); return this; }

    /**
     * Add order-by as descend. <br>
     * IS_SPECTATOR: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsSpectator_Desc() { regOBD("IS_SPECTATOR"); return this; }

    protected ConditionValue _deadReasonCode;
    public ConditionValue xdfgetDeadReasonCode()
    { if (_deadReasonCode == null) { _deadReasonCode = nCV(); }
      return _deadReasonCode; }
    protected ConditionValue xgetCValueDeadReasonCode() { return xdfgetDeadReasonCode(); }

    /**
     * Add order-by as ascend. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_DeadReasonCode_Asc() { regOBA("DEAD_REASON_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_DeadReasonCode_Desc() { regOBD("DEAD_REASON_CODE"); return this; }

    protected ConditionValue _deadDay;
    public ConditionValue xdfgetDeadDay()
    { if (_deadDay == null) { _deadDay = nCV(); }
      return _deadDay; }
    protected ConditionValue xgetCValueDeadDay() { return xdfgetDeadDay(); }

    /**
     * Add order-by as ascend. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_DeadDay_Asc() { regOBA("DEAD_DAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_DeadDay_Desc() { regOBD("DEAD_DAY"); return this; }

    protected ConditionValue _isGone;
    public ConditionValue xdfgetIsGone()
    { if (_isGone == null) { _isGone = nCV(); }
      return _isGone; }
    protected ConditionValue xgetCValueIsGone() { return xdfgetIsGone(); }

    /**
     * Add order-by as ascend. <br>
     * IS_GONE: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsGone_Asc() { regOBA("IS_GONE"); return this; }

    /**
     * Add order-by as descend. <br>
     * IS_GONE: {NotNull, BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsGone_Desc() { regOBD("IS_GONE"); return this; }

    protected ConditionValue _lastAccessDatetime;
    public ConditionValue xdfgetLastAccessDatetime()
    { if (_lastAccessDatetime == null) { _lastAccessDatetime = nCV(); }
      return _lastAccessDatetime; }
    protected ConditionValue xgetCValueLastAccessDatetime() { return xdfgetLastAccessDatetime(); }

    /**
     * Add order-by as ascend. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_LastAccessDatetime_Asc() { regOBA("LAST_ACCESS_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_LastAccessDatetime_Desc() { regOBD("LAST_ACCESS_DATETIME"); return this; }

    protected ConditionValue _campCode;
    public ConditionValue xdfgetCampCode()
    { if (_campCode == null) { _campCode = nCV(); }
      return _campCode; }
    protected ConditionValue xgetCValueCampCode() { return xdfgetCampCode(); }

    /**
     * Add order-by as ascend. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CampCode_Asc() { regOBA("CAMP_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CampCode_Desc() { regOBD("CAMP_CODE"); return this; }

    protected ConditionValue _isWin;
    public ConditionValue xdfgetIsWin()
    { if (_isWin == null) { _isWin = nCV(); }
      return _isWin; }
    protected ConditionValue xgetCValueIsWin() { return xdfgetIsWin(); }

    /**
     * Add order-by as ascend. <br>
     * IS_WIN: {BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsWin_Asc() { regOBA("IS_WIN"); return this; }

    /**
     * Add order-by as descend. <br>
     * IS_WIN: {BIT, classification=Flg}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_IsWin_Desc() { regOBD("IS_WIN"); return this; }

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
    public BsVillagePlayerCQ addOrderBy_CharaName_Asc() { regOBA("CHARA_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CharaName_Desc() { regOBD("CHARA_NAME"); return this; }

    protected ConditionValue _charaShortName;
    public ConditionValue xdfgetCharaShortName()
    { if (_charaShortName == null) { _charaShortName = nCV(); }
      return _charaShortName; }
    protected ConditionValue xgetCValueCharaShortName() { return xdfgetCharaShortName(); }

    /**
     * Add order-by as ascend. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CharaShortName_Asc() { regOBA("CHARA_SHORT_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_CharaShortName_Desc() { regOBD("CHARA_SHORT_NAME"); return this; }

    protected ConditionValue _memo;
    public ConditionValue xdfgetMemo()
    { if (_memo == null) { _memo = nCV(); }
      return _memo; }
    protected ConditionValue xgetCValueMemo() { return xdfgetMemo(); }

    /**
     * Add order-by as ascend. <br>
     * MEMO: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_Memo_Asc() { regOBA("MEMO"); return this; }

    /**
     * Add order-by as descend. <br>
     * MEMO: {VARCHAR(20)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_Memo_Desc() { regOBD("MEMO"); return this; }

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
    public BsVillagePlayerCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

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
    public BsVillagePlayerCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

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
    public BsVillagePlayerCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

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
    public BsVillagePlayerCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillagePlayerCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

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
        if (bq.hasConditionQueryDeadReason()) {
            uq.queryDeadReason().reflectRelationOnUnionQuery(bq.queryDeadReason(), uq.queryDeadReason());
        }
        if (bq.hasConditionQueryPlayer()) {
            uq.queryPlayer().reflectRelationOnUnionQuery(bq.queryPlayer(), uq.queryPlayer());
        }
        if (bq.hasConditionQuerySkillByRequestSkillCode()) {
            uq.querySkillByRequestSkillCode().reflectRelationOnUnionQuery(bq.querySkillByRequestSkillCode(), uq.querySkillByRequestSkillCode());
        }
        if (bq.hasConditionQuerySkillBySecondRequestSkillCode()) {
            uq.querySkillBySecondRequestSkillCode().reflectRelationOnUnionQuery(bq.querySkillBySecondRequestSkillCode(), uq.querySkillBySecondRequestSkillCode());
        }
        if (bq.hasConditionQuerySkillBySkillCode()) {
            uq.querySkillBySkillCode().reflectRelationOnUnionQuery(bq.querySkillBySkillCode(), uq.querySkillBySkillCode());
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
     * CHARA by my CHARA_ID, named 'chara'.
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
        String nrp = xresolveNRP("VILLAGE_PLAYER", "chara"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new CharaCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "chara", nrp);
    }
    protected void xsetupOuterJoinChara() { xregOutJo("chara"); }
    public boolean hasConditionQueryChara() { return xhasQueRlMap("chara"); }

    /**
     * Get the condition-query for relation table. <br>
     * DEAD_REASON by my DEAD_REASON_CODE, named 'deadReason'.
     * @return The instance of condition-query. (NotNull)
     */
    public DeadReasonCQ queryDeadReason() {
        return xdfgetConditionQueryDeadReason();
    }
    public DeadReasonCQ xdfgetConditionQueryDeadReason() {
        String prop = "deadReason";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryDeadReason()); xsetupOuterJoinDeadReason(); }
        return xgetQueRlMap(prop);
    }
    protected DeadReasonCQ xcreateQueryDeadReason() {
        String nrp = xresolveNRP("VILLAGE_PLAYER", "deadReason"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new DeadReasonCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "deadReason", nrp);
    }
    protected void xsetupOuterJoinDeadReason() { xregOutJo("deadReason"); }
    public boolean hasConditionQueryDeadReason() { return xhasQueRlMap("deadReason"); }

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
        String nrp = xresolveNRP("VILLAGE_PLAYER", "player"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new PlayerCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "player", nrp);
    }
    protected void xsetupOuterJoinPlayer() { xregOutJo("player"); }
    public boolean hasConditionQueryPlayer() { return xhasQueRlMap("player"); }

    /**
     * Get the condition-query for relation table. <br>
     * SKILL by my REQUEST_SKILL_CODE, named 'skillByRequestSkillCode'.
     * @return The instance of condition-query. (NotNull)
     */
    public SkillCQ querySkillByRequestSkillCode() {
        return xdfgetConditionQuerySkillByRequestSkillCode();
    }
    public SkillCQ xdfgetConditionQuerySkillByRequestSkillCode() {
        String prop = "skillByRequestSkillCode";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQuerySkillByRequestSkillCode()); xsetupOuterJoinSkillByRequestSkillCode(); }
        return xgetQueRlMap(prop);
    }
    protected SkillCQ xcreateQuerySkillByRequestSkillCode() {
        String nrp = xresolveNRP("VILLAGE_PLAYER", "skillByRequestSkillCode"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new SkillCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "skillByRequestSkillCode", nrp);
    }
    protected void xsetupOuterJoinSkillByRequestSkillCode() { xregOutJo("skillByRequestSkillCode"); }
    public boolean hasConditionQuerySkillByRequestSkillCode() { return xhasQueRlMap("skillByRequestSkillCode"); }

    /**
     * Get the condition-query for relation table. <br>
     * SKILL by my SECOND_REQUEST_SKILL_CODE, named 'skillBySecondRequestSkillCode'.
     * @return The instance of condition-query. (NotNull)
     */
    public SkillCQ querySkillBySecondRequestSkillCode() {
        return xdfgetConditionQuerySkillBySecondRequestSkillCode();
    }
    public SkillCQ xdfgetConditionQuerySkillBySecondRequestSkillCode() {
        String prop = "skillBySecondRequestSkillCode";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQuerySkillBySecondRequestSkillCode()); xsetupOuterJoinSkillBySecondRequestSkillCode(); }
        return xgetQueRlMap(prop);
    }
    protected SkillCQ xcreateQuerySkillBySecondRequestSkillCode() {
        String nrp = xresolveNRP("VILLAGE_PLAYER", "skillBySecondRequestSkillCode"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new SkillCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "skillBySecondRequestSkillCode", nrp);
    }
    protected void xsetupOuterJoinSkillBySecondRequestSkillCode() { xregOutJo("skillBySecondRequestSkillCode"); }
    public boolean hasConditionQuerySkillBySecondRequestSkillCode() { return xhasQueRlMap("skillBySecondRequestSkillCode"); }

    /**
     * Get the condition-query for relation table. <br>
     * SKILL by my SKILL_CODE, named 'skillBySkillCode'.
     * @return The instance of condition-query. (NotNull)
     */
    public SkillCQ querySkillBySkillCode() {
        return xdfgetConditionQuerySkillBySkillCode();
    }
    public SkillCQ xdfgetConditionQuerySkillBySkillCode() {
        String prop = "skillBySkillCode";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQuerySkillBySkillCode()); xsetupOuterJoinSkillBySkillCode(); }
        return xgetQueRlMap(prop);
    }
    protected SkillCQ xcreateQuerySkillBySkillCode() {
        String nrp = xresolveNRP("VILLAGE_PLAYER", "skillBySkillCode"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new SkillCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "skillBySkillCode", nrp);
    }
    protected void xsetupOuterJoinSkillBySkillCode() { xregOutJo("skillBySkillCode"); }
    public boolean hasConditionQuerySkillBySkillCode() { return xhasQueRlMap("skillBySkillCode"); }

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
        String nrp = xresolveNRP("VILLAGE_PLAYER", "village"); String jan = xresolveJAN(nrp, xgetNNLvl());
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
