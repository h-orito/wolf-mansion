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
    protected ConditionValue xgetCValueVilalgeId() { return _myCQ.xdfgetVilalgeId(); }
    public String keepVilalgeId_ExistsReferrer_MessageList(MessageCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVilalgeId_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepVilalgeId_NotExistsReferrer_MessageList(MessageCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVilalgeId_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepVilalgeId_SpecifyDerivedReferrer_MessageList(MessageCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVilalgeId_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepVilalgeId_QueryDerivedReferrer_MessageList(MessageCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVilalgeId_QueryDerivedReferrer_MessageListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVilalgeId_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepVilalgeId_QueryDerivedReferrer_VillagePlayerListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    protected ConditionValue xgetCValueVillageDisplayName() { return _myCQ.xdfgetVillageDisplayName(); }
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
