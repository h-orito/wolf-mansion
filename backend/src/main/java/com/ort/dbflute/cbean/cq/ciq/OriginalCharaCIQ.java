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
 * The condition-query for in-line of original_chara.
 * @author DBFlute(AutoGenerator)
 */
public class OriginalCharaCIQ extends AbstractBsOriginalCharaCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected BsOriginalCharaCQ _myCQ;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public OriginalCharaCIQ(ConditionQuery referrerQuery, SqlClause sqlClause
                        , String aliasName, int nestLevel, BsOriginalCharaCQ myCQ) {
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
    protected ConditionValue xgetCValueOriginalCharaId() { return _myCQ.xdfgetOriginalCharaId(); }
    public String keepOriginalCharaId_ExistsReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq)
    { throwIICBOE("ExistsReferrer"); return null; }
    public String keepOriginalCharaId_NotExistsReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq)
    { throwIICBOE("NotExistsReferrer"); return null; }
    public String keepOriginalCharaId_SpecifyDerivedReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq)
    { throwIICBOE("(Specify)DerivedReferrer"); return null; }
    public String keepOriginalCharaId_QueryDerivedReferrer_OriginalCharaImageList(OriginalCharaImageCQ sq)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    public String keepOriginalCharaId_QueryDerivedReferrer_OriginalCharaImageListParameter(Object vl)
    { throwIICBOE("(Query)DerivedReferrer"); return null; }
    protected ConditionValue xgetCValueOriginalCharaName() { return _myCQ.xdfgetOriginalCharaName(); }
    protected ConditionValue xgetCValueOriginalCharaShortName() { return _myCQ.xdfgetOriginalCharaShortName(); }
    protected ConditionValue xgetCValueOriginalCharaGroupId() { return _myCQ.xdfgetOriginalCharaGroupId(); }
    protected ConditionValue xgetCValueDisplayWidth() { return _myCQ.xdfgetDisplayWidth(); }
    protected ConditionValue xgetCValueDisplayHeight() { return _myCQ.xdfgetDisplayHeight(); }
    protected ConditionValue xgetCValueRegisterDatetime() { return _myCQ.xdfgetRegisterDatetime(); }
    protected ConditionValue xgetCValueRegisterTrace() { return _myCQ.xdfgetRegisterTrace(); }
    protected ConditionValue xgetCValueUpdateDatetime() { return _myCQ.xdfgetUpdateDatetime(); }
    protected ConditionValue xgetCValueUpdateTrace() { return _myCQ.xdfgetUpdateTrace(); }
    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String pp) { return null; }
    public String keepScalarCondition(OriginalCharaCQ sq)
    { throwIICBOE("ScalarCondition"); return null; }
    public String keepSpecifyMyselfDerived(OriginalCharaCQ sq)
    { throwIICBOE("(Specify)MyselfDerived"); return null;}
    public String keepQueryMyselfDerived(OriginalCharaCQ sq)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepQueryMyselfDerivedParameter(Object vl)
    { throwIICBOE("(Query)MyselfDerived"); return null;}
    public String keepMyselfExists(OriginalCharaCQ sq)
    { throwIICBOE("MyselfExists"); return null;}

    protected void throwIICBOE(String name)
    { throw new IllegalConditionBeanOperationException(name + " at InlineView is unsupported."); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xinCB() { return OriginalCharaCB.class.getName(); }
    protected String xinCQ() { return OriginalCharaCQ.class.getName(); }
}
