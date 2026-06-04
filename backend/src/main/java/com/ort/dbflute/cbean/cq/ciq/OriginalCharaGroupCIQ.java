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
 * The condition-query for in-line of original_chara_group.
 * @author DBFlute(AutoGenerator)
 */
public class OriginalCharaGroupCIQ extends AbstractBsOriginalCharaGroupCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsOriginalCharaGroupCQ _myCQ;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public OriginalCharaGroupCIQ(ConditionQuery referrerQuery, SqlClause sqlClause
                        , String aliasName, int nestLevel, BsOriginalCharaGroupCQ myCQ) {
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
    protected ConditionValue xgetCValueOriginalCharaGroupId() { return _myCQ.xdfgetOriginalCharaGroupId(); }
    public String keepOriginalCharaGroupId_ExistsReferrer_OriginalCharaList(OriginalCharaCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepOriginalCharaGroupId_ExistsReferrer_VillageSettingsList(VillageSettingsCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepOriginalCharaGroupId_NotExistsReferrer_OriginalCharaList(OriginalCharaCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepOriginalCharaGroupId_NotExistsReferrer_VillageSettingsList(VillageSettingsCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepOriginalCharaGroupId_SpecifyDerivedReferrer_OriginalCharaList(OriginalCharaCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepOriginalCharaGroupId_SpecifyDerivedReferrer_VillageSettingsList(VillageSettingsCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaList(OriginalCharaCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_OriginalCharaListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsList(VillageSettingsCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepOriginalCharaGroupId_QueryDerivedReferrer_VillageSettingsListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    protected ConditionValue xgetCValueOriginalCharaGroupName() { return _myCQ.xdfgetOriginalCharaGroupName(); }
    protected ConditionValue xgetCValueRegisterDatetime() { return _myCQ.xdfgetRegisterDatetime(); }
    protected ConditionValue xgetCValueRegisterTrace() { return _myCQ.xdfgetRegisterTrace(); }
    protected ConditionValue xgetCValueUpdateDatetime() { return _myCQ.xdfgetUpdateDatetime(); }
    protected ConditionValue xgetCValueUpdateTrace() { return _myCQ.xdfgetUpdateTrace(); }
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String pp) { return null; }
    public String keepScalarCondition(OriginalCharaGroupCQ sq)
    { throwIICBOE("ScalarCondition"); return null; }
    public String keepSpecifyMyselfDerived(OriginalCharaGroupCQ sq)
    { throwIICBOE("(Specify)MyselfDerived"); return null;}
    public String keepQueryMyselfDerived(OriginalCharaGroupCQ sq)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepQueryMyselfDerivedParameter(Object vl)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepMyselfExists(OriginalCharaGroupCQ sq)
    { throwIICBOE("MyselfExists"); return null;}

    protected void throwIICBOE(String name)
    { throw new IllegalConditionBeanOperationException(name + " at InlineView is unsupported."); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xinCB() { return OriginalCharaGroupCB.class.getName(); }
    protected String xinCQ() { return OriginalCharaGroupCQ.class.getName(); }
}
