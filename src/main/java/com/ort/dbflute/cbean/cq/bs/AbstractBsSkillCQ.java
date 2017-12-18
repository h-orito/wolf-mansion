package com.ort.dbflute.cbean.cq.bs;

import java.util.*;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.ordering.*;
import org.dbflute.cbean.scoping.*;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.dbmeta.DBMetaProvider;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The abstract condition-query of skill.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsSkillCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsSkillCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    public String asTableDbName() {
        return "skill";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_Equal(String skillCode) {
        doSetSkillCode_Equal(fRES(skillCode));
    }

    protected void doSetSkillCode_Equal(String skillCode) {
        regSkillCode(CK_EQ, skillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_NotEqual(String skillCode) {
        doSetSkillCode_NotEqual(fRES(skillCode));
    }

    protected void doSetSkillCode_NotEqual(String skillCode) {
        regSkillCode(CK_NES, skillCode);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_GreaterThan(String skillCode) {
        regSkillCode(CK_GT, fRES(skillCode));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_LessThan(String skillCode) {
        regSkillCode(CK_LT, fRES(skillCode));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_GreaterEqual(String skillCode) {
        regSkillCode(CK_GE, fRES(skillCode));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_LessEqual(String skillCode) {
        regSkillCode(CK_LE, fRES(skillCode));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCodeList The collection of skillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_InScope(Collection<String> skillCodeList) {
        doSetSkillCode_InScope(skillCodeList);
    }

    protected void doSetSkillCode_InScope(Collection<String> skillCodeList) {
        regINS(CK_INS, cTL(skillCodeList), xgetCValueSkillCode(), "SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCodeList The collection of skillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_NotInScope(Collection<String> skillCodeList) {
        doSetSkillCode_NotInScope(skillCodeList);
    }

    protected void doSetSkillCode_NotInScope(Collection<String> skillCodeList) {
        regINS(CK_NINS, cTL(skillCodeList), xgetCValueSkillCode(), "SKILL_CODE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setSkillCode_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param skillCode The value of skillCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setSkillCode_LikeSearch(String skillCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setSkillCode_LikeSearch(skillCode, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setSkillCode_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param skillCode The value of skillCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setSkillCode_LikeSearch(String skillCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(skillCode), xgetCValueSkillCode(), "SKILL_CODE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setSkillCode_NotLikeSearch(String skillCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setSkillCode_NotLikeSearch(skillCode, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @param skillCode The value of skillCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setSkillCode_NotLikeSearch(String skillCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(skillCode), xgetCValueSkillCode(), "SKILL_CODE", likeSearchOption);
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select SKILL_CODE from village_player where ...)} <br>
     * village_player by SKILL_CODE, named 'villagePlayerAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayer</span>(playerCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     playerCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerList for 'exists'. (NotNull)
     */
    public void existsVillagePlayer(SubQuery<VillagePlayerCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepSkillCode_ExistsReferrer_VillagePlayerList(cb.query());
        registerExistsReferrer(cb.query(), "SKILL_CODE", "SKILL_CODE", pp, "villagePlayerList");
    }
    public abstract String keepSkillCode_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select SKILL_CODE from village_player where ...)} <br>
     * village_player by SKILL_CODE, named 'villagePlayerAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayer</span>(playerCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     playerCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of SkillCode_NotExistsReferrer_VillagePlayerList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayer(SubQuery<VillagePlayerCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepSkillCode_NotExistsReferrer_VillagePlayerList(cb.query());
        registerNotExistsReferrer(cb.query(), "SKILL_CODE", "SKILL_CODE", pp, "villagePlayerList");
    }
    public abstract String keepSkillCode_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq);

    public void xsderiveVillagePlayerList(String fn, SubQuery<VillagePlayerCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSkillCode_SpecifyDerivedReferrer_VillagePlayerList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "SKILL_CODE", "SKILL_CODE", pp, "villagePlayerList", al, op);
    }
    public abstract String keepSkillCode_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player where ...)} <br>
     * village_player by SKILL_CODE, named 'villagePlayerAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayer()</span>.<span style="color: #CC4747">max</span>(playerCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     playerCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     playerCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerCB> derivedVillagePlayer() {
        return xcreateQDRFunctionVillagePlayerList();
    }
    protected HpQDRFunction<VillagePlayerCB> xcreateQDRFunctionVillagePlayerList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerList(String fn, SubQuery<VillagePlayerCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepSkillCode_QueryDerivedReferrer_VillagePlayerList(cb.query()); String prpp = keepSkillCode_QueryDerivedReferrer_VillagePlayerListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "SKILL_CODE", "SKILL_CODE", sqpp, "villagePlayerList", rd, vl, prpp, op);
    }
    public abstract String keepSkillCode_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq);
    public abstract String keepSkillCode_QueryDerivedReferrer_VillagePlayerListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     */
    public void setSkillCode_IsNull() { regSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     */
    public void setSkillCode_IsNotNull() { regSkillCode(CK_ISNN, DOBJ); }

    protected void regSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueSkillCode(), "SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueSkillCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_Equal(String skillName) {
        doSetSkillName_Equal(fRES(skillName));
    }

    protected void doSetSkillName_Equal(String skillName) {
        regSkillName(CK_EQ, skillName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_NotEqual(String skillName) {
        doSetSkillName_NotEqual(fRES(skillName));
    }

    protected void doSetSkillName_NotEqual(String skillName) {
        regSkillName(CK_NES, skillName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_GreaterThan(String skillName) {
        regSkillName(CK_GT, fRES(skillName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_LessThan(String skillName) {
        regSkillName(CK_LT, fRES(skillName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_GreaterEqual(String skillName) {
        regSkillName(CK_GE, fRES(skillName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_LessEqual(String skillName) {
        regSkillName(CK_LE, fRES(skillName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillNameList The collection of skillName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_InScope(Collection<String> skillNameList) {
        doSetSkillName_InScope(skillNameList);
    }

    protected void doSetSkillName_InScope(Collection<String> skillNameList) {
        regINS(CK_INS, cTL(skillNameList), xgetCValueSkillName(), "SKILL_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillNameList The collection of skillName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillName_NotInScope(Collection<String> skillNameList) {
        doSetSkillName_NotInScope(skillNameList);
    }

    protected void doSetSkillName_NotInScope(Collection<String> skillNameList) {
        regINS(CK_NINS, cTL(skillNameList), xgetCValueSkillName(), "SKILL_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setSkillName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param skillName The value of skillName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setSkillName_LikeSearch(String skillName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setSkillName_LikeSearch(skillName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setSkillName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param skillName The value of skillName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setSkillName_LikeSearch(String skillName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(skillName), xgetCValueSkillName(), "SKILL_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setSkillName_NotLikeSearch(String skillName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setSkillName_NotLikeSearch(skillName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @param skillName The value of skillName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setSkillName_NotLikeSearch(String skillName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(skillName), xgetCValueSkillName(), "SKILL_NAME", likeSearchOption);
    }

    protected void regSkillName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueSkillName(), "SKILL_NAME"); }
    protected abstract ConditionValue xgetCValueSkillName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_Equal(String campCode) {
        doSetCampCode_Equal(fRES(campCode));
    }

    protected void doSetCampCode_Equal(String campCode) {
        regCampCode(CK_EQ, campCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_NotEqual(String campCode) {
        doSetCampCode_NotEqual(fRES(campCode));
    }

    protected void doSetCampCode_NotEqual(String campCode) {
        regCampCode(CK_NES, campCode);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_GreaterThan(String campCode) {
        regCampCode(CK_GT, fRES(campCode));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_LessThan(String campCode) {
        regCampCode(CK_LT, fRES(campCode));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_GreaterEqual(String campCode) {
        regCampCode(CK_GE, fRES(campCode));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_LessEqual(String campCode) {
        regCampCode(CK_LE, fRES(campCode));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCodeList The collection of campCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_InScope(Collection<String> campCodeList) {
        doSetCampCode_InScope(campCodeList);
    }

    protected void doSetCampCode_InScope(Collection<String> campCodeList) {
        regINS(CK_INS, cTL(campCodeList), xgetCValueCampCode(), "CAMP_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCodeList The collection of campCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_NotInScope(Collection<String> campCodeList) {
        doSetCampCode_NotInScope(campCodeList);
    }

    protected void doSetCampCode_NotInScope(Collection<String> campCodeList) {
        regINS(CK_NINS, cTL(campCodeList), xgetCValueCampCode(), "CAMP_CODE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp} <br>
     * <pre>e.g. setCampCode_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param campCode The value of campCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCampCode_LikeSearch(String campCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCampCode_LikeSearch(campCode, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp} <br>
     * <pre>e.g. setCampCode_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param campCode The value of campCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setCampCode_LikeSearch(String campCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(campCode), xgetCValueCampCode(), "CAMP_CODE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCampCode_NotLikeSearch(String campCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCampCode_NotLikeSearch(campCode, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @param campCode The value of campCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setCampCode_NotLikeSearch(String campCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(campCode), xgetCValueCampCode(), "CAMP_CODE", likeSearchOption);
    }

    protected void regCampCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCampCode(), "CAMP_CODE"); }
    protected abstract ConditionValue xgetCValueCampCode();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO = (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre> 
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, SkillCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO &lt;&gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre> 
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, SkillCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br>
     * {where FOO &gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre> 
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, SkillCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br>
     * {where FOO &lt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre> 
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, SkillCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br>
     * {where FOO &gt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre> 
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, SkillCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;SkillCB&gt;() {
     *     public void query(SkillCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, SkillCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(SkillCQ sq);

    protected SkillCB xcreateScalarConditionCB() {
        SkillCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected SkillCB xcreateScalarConditionPartitionByCB() {
        SkillCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<SkillCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillCB cb = new SkillCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "SKILL_CODE";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(SkillCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<SkillCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(SkillCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillCB cb = new SkillCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "SKILL_CODE";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(SkillCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<SkillCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        SkillCB cb = new SkillCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(SkillCQ sq);

    // ===================================================================================
    //                                                                        Manual Order
    //                                                                        ============
    /**
     * Order along manual ordering information.
     * <pre>
     * cb.query().addOrderBy_Birthdate_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when BIRTHDATE &gt;= '2000/01/01' then 0</span>
     * <span style="color: #3F7E5E">//     else 1</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     *
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Withdrawal);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Formalized);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Provisional);
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'WDL' then 0</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'FML' then 1</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'PRV' then 2</span>
     * <span style="color: #3F7E5E">//     else 3</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     * </pre>
     * <p>This function with Union is unsupported!</p>
     * <p>The order values are bound (treated as bind parameter).</p>
     * @param opLambda The callback for option of manual-order containing order values. (NotNull)
     */
    public void withManualOrder(ManualOrderOptionCall opLambda) { // is user public!
        xdoWithManualOrder(cMOO(opLambda));
    }

    // ===================================================================================
    //                                                                    Small Adjustment
    //                                                                    ================
    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    protected SkillCB newMyCB() {
        return new SkillCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return SkillCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
