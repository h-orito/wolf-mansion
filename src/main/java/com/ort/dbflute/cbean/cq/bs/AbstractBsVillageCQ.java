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
 * The abstract condition-query of village.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVillageCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVillageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "village";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeId The value of vilalgeId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setVilalgeId_Equal(Integer vilalgeId) {
        doSetVilalgeId_Equal(vilalgeId);
    }

    protected void doSetVilalgeId_Equal(Integer vilalgeId) {
        regVilalgeId(CK_EQ, vilalgeId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeId The value of vilalgeId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVilalgeId_NotEqual(Integer vilalgeId) {
        doSetVilalgeId_NotEqual(vilalgeId);
    }

    protected void doSetVilalgeId_NotEqual(Integer vilalgeId) {
        regVilalgeId(CK_NES, vilalgeId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeId The value of vilalgeId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVilalgeId_GreaterThan(Integer vilalgeId) {
        regVilalgeId(CK_GT, vilalgeId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeId The value of vilalgeId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVilalgeId_LessThan(Integer vilalgeId) {
        regVilalgeId(CK_LT, vilalgeId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeId The value of vilalgeId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVilalgeId_GreaterEqual(Integer vilalgeId) {
        regVilalgeId(CK_GE, vilalgeId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeId The value of vilalgeId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVilalgeId_LessEqual(Integer vilalgeId) {
        regVilalgeId(CK_LE, vilalgeId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of vilalgeId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of vilalgeId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setVilalgeId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setVilalgeId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of vilalgeId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of vilalgeId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVilalgeId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVilalgeId(), "VILALGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeIdList The collection of vilalgeId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVilalgeId_InScope(Collection<Integer> vilalgeIdList) {
        doSetVilalgeId_InScope(vilalgeIdList);
    }

    protected void doSetVilalgeId_InScope(Collection<Integer> vilalgeIdList) {
        regINS(CK_INS, cTL(vilalgeIdList), xgetCValueVilalgeId(), "VILALGE_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param vilalgeIdList The collection of vilalgeId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVilalgeId_NotInScope(Collection<Integer> vilalgeIdList) {
        doSetVilalgeId_NotInScope(vilalgeIdList);
    }

    protected void doSetVilalgeId_NotInScope(Collection<Integer> vilalgeIdList) {
        regINS(CK_NINS, cTL(vilalgeIdList), xgetCValueVilalgeId(), "VILALGE_ID");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from message where ...)} <br>
     * message by VILLAGE_ID, named 'messageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsMessage</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageList for 'exists'. (NotNull)
     */
    public void existsMessage(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVilalgeId_ExistsReferrer_MessageList(cb.query());
        registerExistsReferrer(cb.query(), "VILALGE_ID", "VILLAGE_ID", pp, "messageList");
    }
    public abstract String keepVilalgeId_ExistsReferrer_MessageList(MessageCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from village_player where ...)} <br>
     * village_player by VILLAGE_ID, named 'villagePlayerAsOne'.
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
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVilalgeId_ExistsReferrer_VillagePlayerList(cb.query());
        registerExistsReferrer(cb.query(), "VILALGE_ID", "VILLAGE_ID", pp, "villagePlayerList");
    }
    public abstract String keepVilalgeId_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from message where ...)} <br>
     * message by VILLAGE_ID, named 'messageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsMessage</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VilalgeId_NotExistsReferrer_MessageList for 'not exists'. (NotNull)
     */
    public void notExistsMessage(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVilalgeId_NotExistsReferrer_MessageList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILALGE_ID", "VILLAGE_ID", pp, "messageList");
    }
    public abstract String keepVilalgeId_NotExistsReferrer_MessageList(MessageCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from village_player where ...)} <br>
     * village_player by VILLAGE_ID, named 'villagePlayerAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayer</span>(playerCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     playerCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VilalgeId_NotExistsReferrer_VillagePlayerList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayer(SubQuery<VillagePlayerCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVilalgeId_NotExistsReferrer_VillagePlayerList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILALGE_ID", "VILLAGE_ID", pp, "villagePlayerList");
    }
    public abstract String keepVilalgeId_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq);

    public void xsderiveMessageList(String fn, SubQuery<MessageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVilalgeId_SpecifyDerivedReferrer_MessageList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILALGE_ID", "VILLAGE_ID", pp, "messageList", al, op);
    }
    public abstract String keepVilalgeId_SpecifyDerivedReferrer_MessageList(MessageCQ sq);

    public void xsderiveVillagePlayerList(String fn, SubQuery<VillagePlayerCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVilalgeId_SpecifyDerivedReferrer_VillagePlayerList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILALGE_ID", "VILLAGE_ID", pp, "villagePlayerList", al, op);
    }
    public abstract String keepVilalgeId_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from message where ...)} <br>
     * message by VILLAGE_ID, named 'messageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedMessage()</span>.<span style="color: #CC4747">max</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     messageCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<MessageCB> derivedMessage() {
        return xcreateQDRFunctionMessageList();
    }
    protected HpQDRFunction<MessageCB> xcreateQDRFunctionMessageList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveMessageList(fn, sq, rd, vl, op));
    }
    public void xqderiveMessageList(String fn, SubQuery<MessageCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVilalgeId_QueryDerivedReferrer_MessageList(cb.query()); String prpp = keepVilalgeId_QueryDerivedReferrer_MessageListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILALGE_ID", "VILLAGE_ID", sqpp, "messageList", rd, vl, prpp, op);
    }
    public abstract String keepVilalgeId_QueryDerivedReferrer_MessageList(MessageCQ sq);
    public abstract String keepVilalgeId_QueryDerivedReferrer_MessageListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player where ...)} <br>
     * village_player by VILLAGE_ID, named 'villagePlayerAsOne'.
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
        lockCall(() -> sq.query(cb)); String sqpp = keepVilalgeId_QueryDerivedReferrer_VillagePlayerList(cb.query()); String prpp = keepVilalgeId_QueryDerivedReferrer_VillagePlayerListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILALGE_ID", "VILLAGE_ID", sqpp, "villagePlayerList", rd, vl, prpp, op);
    }
    public abstract String keepVilalgeId_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq);
    public abstract String keepVilalgeId_QueryDerivedReferrer_VillagePlayerListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setVilalgeId_IsNull() { regVilalgeId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setVilalgeId_IsNotNull() { regVilalgeId(CK_ISNN, DOBJ); }

    protected void regVilalgeId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVilalgeId(), "VILALGE_ID"); }
    protected abstract ConditionValue xgetCValueVilalgeId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_Equal(String villageDisplayName) {
        doSetVillageDisplayName_Equal(fRES(villageDisplayName));
    }

    protected void doSetVillageDisplayName_Equal(String villageDisplayName) {
        regVillageDisplayName(CK_EQ, villageDisplayName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_NotEqual(String villageDisplayName) {
        doSetVillageDisplayName_NotEqual(fRES(villageDisplayName));
    }

    protected void doSetVillageDisplayName_NotEqual(String villageDisplayName) {
        regVillageDisplayName(CK_NES, villageDisplayName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_GreaterThan(String villageDisplayName) {
        regVillageDisplayName(CK_GT, fRES(villageDisplayName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_LessThan(String villageDisplayName) {
        regVillageDisplayName(CK_LT, fRES(villageDisplayName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_GreaterEqual(String villageDisplayName) {
        regVillageDisplayName(CK_GE, fRES(villageDisplayName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_LessEqual(String villageDisplayName) {
        regVillageDisplayName(CK_LE, fRES(villageDisplayName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayNameList The collection of villageDisplayName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_InScope(Collection<String> villageDisplayNameList) {
        doSetVillageDisplayName_InScope(villageDisplayNameList);
    }

    protected void doSetVillageDisplayName_InScope(Collection<String> villageDisplayNameList) {
        regINS(CK_INS, cTL(villageDisplayNameList), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayNameList The collection of villageDisplayName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_NotInScope(Collection<String> villageDisplayNameList) {
        doSetVillageDisplayName_NotInScope(villageDisplayNameList);
    }

    protected void doSetVillageDisplayName_NotInScope(Collection<String> villageDisplayNameList) {
        regINS(CK_NINS, cTL(villageDisplayNameList), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setVillageDisplayName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param villageDisplayName The value of villageDisplayName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillageDisplayName_LikeSearch(String villageDisplayName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillageDisplayName_LikeSearch(villageDisplayName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setVillageDisplayName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param villageDisplayName The value of villageDisplayName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setVillageDisplayName_LikeSearch(String villageDisplayName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(villageDisplayName), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillageDisplayName_NotLikeSearch(String villageDisplayName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillageDisplayName_NotLikeSearch(villageDisplayName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setVillageDisplayName_NotLikeSearch(String villageDisplayName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(villageDisplayName), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME", likeSearchOption);
    }

    protected void regVillageDisplayName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME"); }
    protected abstract ConditionValue xgetCValueVillageDisplayName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_Equal(String winCampCode) {
        doSetWinCampCode_Equal(fRES(winCampCode));
    }

    protected void doSetWinCampCode_Equal(String winCampCode) {
        regWinCampCode(CK_EQ, winCampCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_NotEqual(String winCampCode) {
        doSetWinCampCode_NotEqual(fRES(winCampCode));
    }

    protected void doSetWinCampCode_NotEqual(String winCampCode) {
        regWinCampCode(CK_NES, winCampCode);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_GreaterThan(String winCampCode) {
        regWinCampCode(CK_GT, fRES(winCampCode));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_LessThan(String winCampCode) {
        regWinCampCode(CK_LT, fRES(winCampCode));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_GreaterEqual(String winCampCode) {
        regWinCampCode(CK_GE, fRES(winCampCode));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_LessEqual(String winCampCode) {
        regWinCampCode(CK_LE, fRES(winCampCode));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCodeList The collection of winCampCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_InScope(Collection<String> winCampCodeList) {
        doSetWinCampCode_InScope(winCampCodeList);
    }

    protected void doSetWinCampCode_InScope(Collection<String> winCampCodeList) {
        regINS(CK_INS, cTL(winCampCodeList), xgetCValueWinCampCode(), "WIN_CAMP_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCodeList The collection of winCampCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_NotInScope(Collection<String> winCampCodeList) {
        doSetWinCampCode_NotInScope(winCampCodeList);
    }

    protected void doSetWinCampCode_NotInScope(Collection<String> winCampCodeList) {
        regINS(CK_NINS, cTL(winCampCodeList), xgetCValueWinCampCode(), "WIN_CAMP_CODE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)} <br>
     * <pre>e.g. setWinCampCode_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param winCampCode The value of winCampCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setWinCampCode_LikeSearch(String winCampCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setWinCampCode_LikeSearch(winCampCode, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)} <br>
     * <pre>e.g. setWinCampCode_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param winCampCode The value of winCampCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setWinCampCode_LikeSearch(String winCampCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(winCampCode), xgetCValueWinCampCode(), "WIN_CAMP_CODE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setWinCampCode_NotLikeSearch(String winCampCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setWinCampCode_NotLikeSearch(winCampCode, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     * @param winCampCode The value of winCampCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setWinCampCode_NotLikeSearch(String winCampCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(winCampCode), xgetCValueWinCampCode(), "WIN_CAMP_CODE", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     */
    public void setWinCampCode_IsNull() { regWinCampCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     */
    public void setWinCampCode_IsNullOrEmpty() { regWinCampCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {VARCHAR(20)}
     */
    public void setWinCampCode_IsNotNull() { regWinCampCode(CK_ISNN, DOBJ); }

    protected void regWinCampCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueWinCampCode(), "WIN_CAMP_CODE"); }
    protected abstract ConditionValue xgetCValueWinCampCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_Equal(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_EQ,  registerDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GT,  registerDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LT,  registerDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GE,  registerDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LE, registerDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setRegisterDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "REGISTER_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueRegisterDatetime(), nm, op);
    }

    protected void regRegisterDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterDatetime(), "REGISTER_DATETIME"); }
    protected abstract ConditionValue xgetCValueRegisterDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_Equal(String registerTrace) {
        doSetRegisterTrace_Equal(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_Equal(String registerTrace) {
        regRegisterTrace(CK_EQ, registerTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotEqual(String registerTrace) {
        doSetRegisterTrace_NotEqual(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_NotEqual(String registerTrace) {
        regRegisterTrace(CK_NES, registerTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterThan(String registerTrace) {
        regRegisterTrace(CK_GT, fRES(registerTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessThan(String registerTrace) {
        regRegisterTrace(CK_LT, fRES(registerTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterEqual(String registerTrace) {
        regRegisterTrace(CK_GE, fRES(registerTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessEqual(String registerTrace) {
        regRegisterTrace(CK_LE, fRES(registerTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_InScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_InScope(registerTraceList);
    }

    protected void doSetRegisterTrace_InScope(Collection<String> registerTraceList) {
        regINS(CK_INS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_NotInScope(registerTraceList);
    }

    protected void doSetRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        regINS(CK_NINS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_LikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_LikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setRegisterTrace_LikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_NotLikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_NotLikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setRegisterTrace_NotLikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    protected void regRegisterTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterTrace(), "REGISTER_TRACE"); }
    protected abstract ConditionValue xgetCValueRegisterTrace();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_Equal(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_EQ,  updateDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GT,  updateDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LT,  updateDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GE,  updateDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LE, updateDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setUpdateDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "UPDATE_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueUpdateDatetime(), nm, op);
    }

    protected void regUpdateDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateDatetime(), "UPDATE_DATETIME"); }
    protected abstract ConditionValue xgetCValueUpdateDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_Equal(String updateTrace) {
        doSetUpdateTrace_Equal(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_Equal(String updateTrace) {
        regUpdateTrace(CK_EQ, updateTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotEqual(String updateTrace) {
        doSetUpdateTrace_NotEqual(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_NotEqual(String updateTrace) {
        regUpdateTrace(CK_NES, updateTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterThan(String updateTrace) {
        regUpdateTrace(CK_GT, fRES(updateTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessThan(String updateTrace) {
        regUpdateTrace(CK_LT, fRES(updateTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterEqual(String updateTrace) {
        regUpdateTrace(CK_GE, fRES(updateTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessEqual(String updateTrace) {
        regUpdateTrace(CK_LE, fRES(updateTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_InScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_InScope(updateTraceList);
    }

    protected void doSetUpdateTrace_InScope(Collection<String> updateTraceList) {
        regINS(CK_INS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_NotInScope(updateTraceList);
    }

    protected void doSetUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        regINS(CK_NINS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_LikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_LikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setUpdateTrace_LikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_NotLikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_NotLikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setUpdateTrace_NotLikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    protected void regUpdateTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateTrace(), "UPDATE_TRACE"); }
    protected abstract ConditionValue xgetCValueUpdateTrace();

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
    public HpSLCFunction<VillageCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, VillageCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;VillageCB&gt;() {
     *     public void query(VillageCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillageCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, VillageCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(VillageCQ sq);

    protected VillageCB xcreateScalarConditionCB() {
        VillageCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected VillageCB xcreateScalarConditionPartitionByCB() {
        VillageCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<VillageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCB cb = new VillageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VILALGE_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(VillageCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<VillageCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(VillageCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCB cb = new VillageCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VILALGE_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(VillageCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<VillageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageCB cb = new VillageCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(VillageCQ sq);

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
    protected VillageCB newMyCB() {
        return new VillageCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return VillageCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
