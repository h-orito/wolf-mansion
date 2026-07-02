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
 * The condition-query for in-line of VILLAGE_PLAYER_STATUS_TYPE.
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerStatusTypeCIQ extends AbstractBsVillagePlayerStatusTypeCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsVillagePlayerStatusTypeCQ _myCQ;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public VillagePlayerStatusTypeCIQ(ConditionQuery referrerQuery, SqlClause sqlClause
                        , String aliasName, int nestLevel, BsVillagePlayerStatusTypeCQ myCQ) {
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
    protected ConditionValue xgetCValueVillagePlayerStatusTypeCode() { return _myCQ.xdfgetVillagePlayerStatusTypeCode(); }
    public String keepVillagePlayerStatusTypeCode_ExistsReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVillagePlayerStatusTypeCode_NotExistsReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVillagePlayerStatusTypeCode_SpecifyDerivedReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusList(VillagePlayerStatusCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVillagePlayerStatusTypeCode_QueryDerivedReferrer_VillagePlayerStatusListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    protected ConditionValue xgetCValueVillagePlayerStatusTypeName() { return _myCQ.xdfgetVillagePlayerStatusTypeName(); }
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String pp) { return null; }
    public String keepScalarCondition(VillagePlayerStatusTypeCQ sq)
    { throwIICBOE("ScalarCondition"); return null; }
    public String keepSpecifyMyselfDerived(VillagePlayerStatusTypeCQ sq)
    { throwIICBOE("(Specify)MyselfDerived"); return null;}
    public String keepQueryMyselfDerived(VillagePlayerStatusTypeCQ sq)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepQueryMyselfDerivedParameter(Object vl)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepMyselfExists(VillagePlayerStatusTypeCQ sq)
    { throwIICBOE("MyselfExists"); return null;}

    protected void throwIICBOE(String name)
    { throw new IllegalConditionBeanOperationException(name + " at InlineView is unsupported."); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xinCB() { return VillagePlayerStatusTypeCB.class.getName(); }
    protected String xinCQ() { return VillagePlayerStatusTypeCQ.class.getName(); }
}
