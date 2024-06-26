package com.ort.dbflute.cbean.cq.ciq;

import java.util.Map;
import org.dbflute.cbean.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.ConditionOption;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.bs.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The condition-query for in-line of VILLAGE_PLAYER.
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerCIQ extends AbstractBsVillagePlayerCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsVillagePlayerCQ _myCQ;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public VillagePlayerCIQ(ConditionQuery referrerQuery, SqlClause sqlClause
                        , String aliasName, int nestLevel, BsVillagePlayerCQ myCQ) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
        _myCQ = myCQ;
        _foreignPropertyName = _myCQ.xgetForeignPropertyName(); // accept foreign property name
        _relationPath = _myCQ.xgetRelationPath(); // accept relation path
        _inline = true;
    }

    // ===================================================================================
    //                                                             Override about Register
    //                                                             =======================
    protected void reflectRelationOnUnionQuery(ConditionQuery bq, ConditionQuery uq)
    { throw new IllegalConditionBeanOperationException("InlineView cannot use Union: " + bq + " : " + uq); }

    @Override
    protected void setupConditionValueAndRegisterWhereClause(ConditionKey k, Object v, ConditionValue cv, String col)
    { regIQ(k, v, cv, col); }

    @Override
    protected void setupConditionValueAndRegisterWhereClause(ConditionKey k, Object v, ConditionValue cv, String col, ConditionOption op)
    { regIQ(k, v, cv, col, op); }

    @Override
    protected void registerWhereClause(String wc)
    { registerInlineWhereClause(wc); }

    @Override
    protected boolean isInScopeRelationSuppressLocalAliasName() {
        if (_onClause) { throw new IllegalConditionBeanOperationException("InScopeRelation on OnClause is unsupported."); }
        return true;
    }

    // ===================================================================================
    //                                                                Override about Query
    //                                                                ====================
    protected ConditionValue xgetCValueVillagePlayerId() { return _myCQ.xdfgetVillagePlayerId(); }
    public String keepVillagePlayerId_ExistsReferrer_CommitList(CommitCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_MessageSendtoList(MessageSendtoCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_CommitList(CommitCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_MessageSendtoList(MessageSendtoCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_CommitList(CommitCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_MessageSendtoList(MessageSendtoCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_CommitList(CommitCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_CommitListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoList(MessageSendtoCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerAccessInfoListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerSkillHistoryListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    protected ConditionValue xgetCValueVillageId() { return _myCQ.xdfgetVillageId(); }
    protected ConditionValue xgetCValuePlayerId() { return _myCQ.xdfgetPlayerId(); }
    protected ConditionValue xgetCValueCharaId() { return _myCQ.xdfgetCharaId(); }
    protected ConditionValue xgetCValueSkillCode() { return _myCQ.xdfgetSkillCode(); }
    protected ConditionValue xgetCValueRequestSkillCode() { return _myCQ.xdfgetRequestSkillCode(); }
    protected ConditionValue xgetCValueSecondRequestSkillCode() { return _myCQ.xdfgetSecondRequestSkillCode(); }
    protected ConditionValue xgetCValueRoomNumber() { return _myCQ.xdfgetRoomNumber(); }
    protected ConditionValue xgetCValueIsDead() { return _myCQ.xdfgetIsDead(); }
    protected ConditionValue xgetCValueIsSpectator() { return _myCQ.xdfgetIsSpectator(); }
    protected ConditionValue xgetCValueDeadReasonCode() { return _myCQ.xdfgetDeadReasonCode(); }
    protected ConditionValue xgetCValueDeadDay() { return _myCQ.xdfgetDeadDay(); }
    protected ConditionValue xgetCValueIsGone() { return _myCQ.xdfgetIsGone(); }
    protected ConditionValue xgetCValueLastAccessDatetime() { return _myCQ.xdfgetLastAccessDatetime(); }
    protected ConditionValue xgetCValueCampCode() { return _myCQ.xdfgetCampCode(); }
    protected ConditionValue xgetCValueIsWin() { return _myCQ.xdfgetIsWin(); }
    protected ConditionValue xgetCValueCharaName() { return _myCQ.xdfgetCharaName(); }
    protected ConditionValue xgetCValueCharaShortName() { return _myCQ.xdfgetCharaShortName(); }
    protected ConditionValue xgetCValueMemo() { return _myCQ.xdfgetMemo(); }
    protected ConditionValue xgetCValueRegisterDatetime() { return _myCQ.xdfgetRegisterDatetime(); }
    protected ConditionValue xgetCValueRegisterTrace() { return _myCQ.xdfgetRegisterTrace(); }
    protected ConditionValue xgetCValueUpdateDatetime() { return _myCQ.xdfgetUpdateDatetime(); }
    protected ConditionValue xgetCValueUpdateTrace() { return _myCQ.xdfgetUpdateTrace(); }
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String pp) { return null; }
    public String keepScalarCondition(VillagePlayerCQ sq)
    { throwIICBOE("ScalarCondition"); return null; }
    public String keepSpecifyMyselfDerived(VillagePlayerCQ sq)
    { throwIICBOE("(Specify)MyselfDerived"); return null;}
    public String keepQueryMyselfDerived(VillagePlayerCQ sq)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepQueryMyselfDerivedParameter(Object vl)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepMyselfExists(VillagePlayerCQ sq)
    { throwIICBOE("MyselfExists"); return null;}

    protected void throwIICBOE(String name)
    { throw new IllegalConditionBeanOperationException(name + " at InlineView is unsupported."); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xinCB() { return VillagePlayerCB.class.getName(); }
    protected String xinCQ() { return VillagePlayerCQ.class.getName(); }
}
