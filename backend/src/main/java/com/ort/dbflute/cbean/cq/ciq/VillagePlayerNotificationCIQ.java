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
 * The condition-query for in-line of VILLAGE_PLAYER_NOTIFICATION.
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerNotificationCIQ extends AbstractBsVillagePlayerNotificationCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsVillagePlayerNotificationCQ _myCQ;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public VillagePlayerNotificationCIQ(ConditionQuery referrerQuery, SqlClause sqlClause
                        , String aliasName, int nestLevel, BsVillagePlayerNotificationCQ myCQ) {
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
    protected ConditionValue xgetCValueDiscordWebhookUrl() { return _myCQ.xdfgetDiscordWebhookUrl(); }
    protected ConditionValue xgetCValueVillageStart() { return _myCQ.xdfgetVillageStart(); }
    protected ConditionValue xgetCValueVillageDaychange() { return _myCQ.xdfgetVillageDaychange(); }
    protected ConditionValue xgetCValueVillageEpilogue() { return _myCQ.xdfgetVillageEpilogue(); }
    protected ConditionValue xgetCValueReceiveSecretSay() { return _myCQ.xdfgetReceiveSecretSay(); }
    protected ConditionValue xgetCValueReceiveAbilitySay() { return _myCQ.xdfgetReceiveAbilitySay(); }
    protected ConditionValue xgetCValueReceiveAnchorSay() { return _myCQ.xdfgetReceiveAnchorSay(); }
    protected ConditionValue xgetCValueKeyword() { return _myCQ.xdfgetKeyword(); }
    protected ConditionValue xgetCValueRegisterDatetime() { return _myCQ.xdfgetRegisterDatetime(); }
    protected ConditionValue xgetCValueRegisterTrace() { return _myCQ.xdfgetRegisterTrace(); }
    protected ConditionValue xgetCValueUpdateDatetime() { return _myCQ.xdfgetUpdateDatetime(); }
    protected ConditionValue xgetCValueUpdateTrace() { return _myCQ.xdfgetUpdateTrace(); }
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String pp) { return null; }
    public String keepScalarCondition(VillagePlayerNotificationCQ sq)
    { throwIICBOE("ScalarCondition"); return null; }
    public String keepSpecifyMyselfDerived(VillagePlayerNotificationCQ sq)
    { throwIICBOE("(Specify)MyselfDerived"); return null;}
    public String keepQueryMyselfDerived(VillagePlayerNotificationCQ sq)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepQueryMyselfDerivedParameter(Object vl)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepMyselfExists(VillagePlayerNotificationCQ sq)
    { throwIICBOE("MyselfExists"); return null;}

    protected void throwIICBOE(String name)
    { throw new IllegalConditionBeanOperationException(name + " at InlineView is unsupported."); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xinCB() { return VillagePlayerNotificationCB.class.getName(); }
    protected String xinCQ() { return VillagePlayerNotificationCQ.class.getName(); }
}
