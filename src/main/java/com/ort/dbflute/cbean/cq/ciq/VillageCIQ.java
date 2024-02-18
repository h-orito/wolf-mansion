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
 * The condition-query for in-line of village.
 * @author DBFlute(AutoGenerator)
 */
public class VillageCIQ extends AbstractBsVillageCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsVillageCQ _myCQ;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public VillageCIQ(ConditionQuery referrerQuery, SqlClause sqlClause
                        , String aliasName, int nestLevel, BsVillageCQ myCQ) {
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
    protected ConditionValue xgetCValueVillageId() { return _myCQ.xdfgetVillageId(); }
    public String keepVillageId_ExistsReferrer_CampAllocationList(CampAllocationCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_ExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_ExistsReferrer_SkillAllocationList(SkillAllocationCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_ExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_ExistsReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_ExistsReferrer_VillageDayList(VillageDayCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_ExistsReferrer_VillageTagList(VillageTagCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_CampAllocationList(CampAllocationCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_SkillAllocationList(SkillAllocationCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_VillageDayList(VillageDayCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_NotExistsReferrer_VillageTagList(VillageTagCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_CampAllocationList(CampAllocationCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_SkillAllocationList(SkillAllocationCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_VillageDayList(VillageDayCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_SpecifyDerivedReferrer_VillageTagList(VillageTagCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_CampAllocationList(CampAllocationCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_CampAllocationListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_NormalSayRestrictionListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_SkillAllocationList(SkillAllocationCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_SkillAllocationListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_SkillSayRestrictionListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillageCharaGroupListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillageDayList(VillageDayCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillageDayListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillagePlayerListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillageTagList(VillageTagCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillageId_QueryDerivedReferrer_VillageTagListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    protected ConditionValue xgetCValueVillageDisplayName() { return _myCQ.xdfgetVillageDisplayName(); }
    protected ConditionValue xgetCValueCreatePlayerName() { return _myCQ.xdfgetCreatePlayerName(); }
    protected ConditionValue xgetCValueVillageStatusCode() { return _myCQ.xdfgetVillageStatusCode(); }
    protected ConditionValue xgetCValueRoomSizeWidth() { return _myCQ.xdfgetRoomSizeWidth(); }
    protected ConditionValue xgetCValueRoomSizeHeight() { return _myCQ.xdfgetRoomSizeHeight(); }
    protected ConditionValue xgetCValueEpilogueDay() { return _myCQ.xdfgetEpilogueDay(); }
    protected ConditionValue xgetCValueWinCampCode() { return _myCQ.xdfgetWinCampCode(); }
    protected ConditionValue xgetCValueRegisterDatetime() { return _myCQ.xdfgetRegisterDatetime(); }
    protected ConditionValue xgetCValueRegisterTrace() { return _myCQ.xdfgetRegisterTrace(); }
    protected ConditionValue xgetCValueUpdateDatetime() { return _myCQ.xdfgetUpdateDatetime(); }
    protected ConditionValue xgetCValueUpdateTrace() { return _myCQ.xdfgetUpdateTrace(); }
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String pp) { return null; }
    public String keepScalarCondition(VillageCQ sq)
    { throwIICBOE("ScalarCondition"); return null; }
    public String keepSpecifyMyselfDerived(VillageCQ sq)
    { throwIICBOE("(Specify)MyselfDerived"); return null;}
    public String keepQueryMyselfDerived(VillageCQ sq)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepQueryMyselfDerivedParameter(Object vl)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepMyselfExists(VillageCQ sq)
    { throwIICBOE("MyselfExists"); return null;}

    protected void throwIICBOE(String name)
    { throw new IllegalConditionBeanOperationException(name + " at InlineView is unsupported."); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xinCB() { return VillageCB.class.getName(); }
    protected String xinCQ() { return VillageCQ.class.getName(); }
}
