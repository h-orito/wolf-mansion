package com.ort.dbflute.cbean.bs;

import org.dbflute.cbean.AbstractConditionBean;
import org.dbflute.cbean.ConditionBean;
import org.dbflute.cbean.ConditionQuery;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.dream.*;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.cbean.sqlclause.SqlClauseCreator;
import org.dbflute.cbean.scoping.*;
import org.dbflute.dbmeta.DBMetaProvider;
import org.dbflute.twowaysql.factory.SqlAnalyzerFactory;
import org.dbflute.twowaysql.style.BoundDateDisplayTimeZoneProvider;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.DBFluteConfig;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.allcommon.ImplementedInvokerAssistant;
import com.ort.dbflute.allcommon.ImplementedSqlClauseCreator;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;
import com.ort.dbflute.cbean.nss.*;

/**
 * The base condition-bean of message.
 * @author DBFlute(AutoGenerator)
 */
public class BsMessageCB extends AbstractConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected MessageCQ _conditionQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsMessageCB() {
        if (DBFluteConfig.getInstance().isPagingCountLater()) {
            enablePagingCountLater();
        }
        if (DBFluteConfig.getInstance().isPagingCountLeastJoin()) {
            enablePagingCountLeastJoin();
        }
        if (DBFluteConfig.getInstance().isNonSpecifiedColumnAccessAllowed()) {
            enableNonSpecifiedColumnAccess();
        }
        if (DBFluteConfig.getInstance().isSpecifyColumnRequired()) {
            enableSpecifyColumnRequired();
        }
        xsetSpecifyColumnRequiredExceptDeterminer(DBFluteConfig.getInstance().getSpecifyColumnRequiredExceptDeterminer());
        if (DBFluteConfig.getInstance().isSpecifyColumnRequiredWarningOnly()) {
            xenableSpecifyColumnRequiredWarningOnly();
        }
        if (DBFluteConfig.getInstance().isQueryUpdateCountPreCheck()) {
            enableQueryUpdateCountPreCheck();
        }
    }

    // ===================================================================================
    //                                                                           SqlClause
    //                                                                           =========
    @Override
    protected SqlClause createSqlClause() {
        SqlClauseCreator creator = DBFluteConfig.getInstance().getSqlClauseCreator();
        if (creator != null) {
            return creator.createSqlClause(this);
        }
        return new ImplementedSqlClauseCreator().createSqlClause(this); // as default
    }

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    protected DBMetaProvider getDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider(); // as default
    }

    public String asTableDbName() {
        return "message";
    }

    // ===================================================================================
    //                                                                 PrimaryKey Handling
    //                                                                 ===================
    /**
     * Accept the query condition of primary key as equal.
     * @param messageId : PK, ID, NotNull, INT UNSIGNED(10). (NotNull)
     * @return this. (NotNull)
     */
    public MessageCB acceptPK(Integer messageId) {
        assertObjectNotNull("messageId", messageId);
        BsMessageCB cb = this;
        cb.query().setMessageId_Equal(messageId);
        return (MessageCB)this;
    }

    /**
     * Accept the query condition of unique key as equal.
     * @param villageId : UQ+, IX+, NotNull, INT UNSIGNED(10), FK to village_day. (NotNull)
     * @param messageTypeCode : +UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType. (NotNull)
     * @param messageNumber : +UQ, INT UNSIGNED(10). (NotNull)
     * @return this. (NotNull)
     */
    public MessageCB acceptUniqueOf(Integer villageId, CDef.MessageType messageTypeCode, Integer messageNumber) {
        assertObjectNotNull("villageId", villageId);assertObjectNotNull("messageTypeCode", messageTypeCode);assertObjectNotNull("messageNumber", messageNumber);
        BsMessageCB cb = this;
        cb.query().setVillageId_Equal(villageId);cb.query().setMessageTypeCode_Equal_AsMessageType(messageTypeCode);cb.query().setMessageNumber_Equal(messageNumber);
        return (MessageCB)this;
    }

    public ConditionBean addOrderBy_PK_Asc() {
        query().addOrderBy_MessageId_Asc();
        return this;
    }

    public ConditionBean addOrderBy_PK_Desc() {
        query().addOrderBy_MessageId_Desc();
        return this;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Prepare for various queries. <br>
     * Examples of main functions are following:
     * <pre>
     * <span style="color: #3F7E5E">// Basic Queries</span>
     * cb.query().setMemberId_Equal(value);        <span style="color: #3F7E5E">// =</span>
     * cb.query().setMemberId_NotEqual(value);     <span style="color: #3F7E5E">// !=</span>
     * cb.query().setMemberId_GreaterThan(value);  <span style="color: #3F7E5E">// &gt;</span>
     * cb.query().setMemberId_LessThan(value);     <span style="color: #3F7E5E">// &lt;</span>
     * cb.query().setMemberId_GreaterEqual(value); <span style="color: #3F7E5E">// &gt;=</span>
     * cb.query().setMemberId_LessEqual(value);    <span style="color: #3F7E5E">// &lt;=</span>
     * cb.query().setMemberName_InScope(valueList);    <span style="color: #3F7E5E">// in ('a', 'b')</span>
     * cb.query().setMemberName_NotInScope(valueList); <span style="color: #3F7E5E">// not in ('a', 'b')</span>
     * <span style="color: #3F7E5E">// LikeSearch with various options: (versatile)</span>
     * <span style="color: #3F7E5E">// {like ... [options]}</span>
     * cb.query().setMemberName_LikeSearch(value, option);
     * cb.query().setMemberName_NotLikeSearch(value, option); <span style="color: #3F7E5E">// not like ...</span>
     * <span style="color: #3F7E5E">// FromTo with various options: (versatile)</span>
     * <span style="color: #3F7E5E">// {(default) fromDatetime &lt;= BIRTHDATE &lt;= toDatetime}</span>
     * cb.query().setBirthdate_FromTo(fromDatetime, toDatetime, option);
     * <span style="color: #3F7E5E">// DateFromTo: (Date means yyyy/MM/dd)</span>
     * <span style="color: #3F7E5E">// {fromDate &lt;= BIRTHDATE &lt; toDate + 1 day}</span>
     * cb.query().setBirthdate_IsNull();    <span style="color: #3F7E5E">// is null</span>
     * cb.query().setBirthdate_IsNotNull(); <span style="color: #3F7E5E">// is not null</span>
     *
     * <span style="color: #3F7E5E">// ExistsReferrer: (correlated sub-query)</span>
     * <span style="color: #3F7E5E">// {where exists (select PURCHASE_ID from PURCHASE where ...)}</span>
     * cb.query().existsPurchase(purchaseCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     purchaseCB.query().set... <span style="color: #3F7E5E">// referrer sub-query condition</span>
     * });
     * cb.query().notExistsPurchase...
     *
     * <span style="color: #3F7E5E">// (Query)DerivedReferrer: (correlated sub-query)</span>
     * cb.query().derivedPurchaseList().max(purchaseCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     purchaseCB.specify().columnPurchasePrice(); <span style="color: #3F7E5E">// derived column for function</span>
     *     purchaseCB.query().set... <span style="color: #3F7E5E">// referrer sub-query condition</span>
     * }).greaterEqual(value);
     *
     * <span style="color: #3F7E5E">// ScalarCondition: (self-table sub-query)</span>
     * cb.query().scalar_Equal().max(scalarCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     scalarCB.specify().columnBirthdate(); <span style="color: #3F7E5E">// derived column for function</span>
     *     scalarCB.query().set... <span style="color: #3F7E5E">// scalar sub-query condition</span>
     * });
     *
     * <span style="color: #3F7E5E">// OrderBy</span>
     * cb.query().addOrderBy_MemberName_Asc();
     * cb.query().addOrderBy_MemberName_Desc().withManualOrder(option);
     * cb.query().addOrderBy_MemberName_Desc().withNullsFirst();
     * cb.query().addOrderBy_MemberName_Desc().withNullsLast();
     * cb.query().addSpecifiedDerivedOrderBy_Desc(aliasName);
     *
     * <span style="color: #3F7E5E">// Query(Relation)</span>
     * cb.query().queryMemberStatus()...;
     * cb.query().queryMemberAddressAsValid(targetDate)...;
     * </pre>
     * @return The instance of condition-query for base-point table to set up query. (NotNull)
     */
    public MessageCQ query() {
        assertQueryPurpose(); // assert only when user-public query
        return doGetConditionQuery();
    }

    public MessageCQ xdfgetConditionQuery() { // public for parameter comment and internal
        return doGetConditionQuery();
    }

    protected MessageCQ doGetConditionQuery() {
        if (_conditionQuery == null) {
            _conditionQuery = createLocalCQ();
        }
        return _conditionQuery;
    }

    protected MessageCQ createLocalCQ() {
        return xcreateCQ(null, getSqlClause(), getSqlClause().getBasePointAliasName(), 0);
    }

    protected MessageCQ xcreateCQ(ConditionQuery childQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        MessageCQ cq = xnewCQ(childQuery, sqlClause, aliasName, nestLevel);
        cq.xsetBaseCB(this);
        return cq;
    }

    protected MessageCQ xnewCQ(ConditionQuery childQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        return new MessageCQ(childQuery, sqlClause, aliasName, nestLevel);
    }

    /**
     * {@inheritDoc}
     */
    public ConditionQuery localCQ() {
        return doGetConditionQuery();
    }

    // ===================================================================================
    //                                                                               Union
    //                                                                               =====
    /**
     * Set up 'union' for base-point table. <br>
     * You don't need to call SetupSelect in union-query,
     * because it inherits calls before. (Don't call SetupSelect after here)
     * <pre>
     * cb.query().<span style="color: #CC4747">union</span>(<span style="color: #553000">unionCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">unionCB</span>.query().set...
     * });
     * </pre>
     * @param unionCBLambda The callback for query of 'union'. (NotNull)
     */
    public void union(UnionQuery<MessageCB> unionCBLambda) {
        final MessageCB cb = new MessageCB(); cb.xsetupForUnion(this); xsyncUQ(cb);
        try { lock(); unionCBLambda.query(cb); } finally { unlock(); } xsaveUCB(cb);
        final MessageCQ cq = cb.query(); query().xsetUnionQuery(cq);
    }

    /**
     * Set up 'union all' for base-point table. <br>
     * You don't need to call SetupSelect in union-query,
     * because it inherits calls before. (Don't call SetupSelect after here)
     * <pre>
     * cb.query().<span style="color: #CC4747">unionAll</span>(<span style="color: #553000">unionCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">unionCB</span>.query().set...
     * });
     * </pre>
     * @param unionCBLambda The callback for query of 'union all'. (NotNull)
     */
    public void unionAll(UnionQuery<MessageCB> unionCBLambda) {
        final MessageCB cb = new MessageCB(); cb.xsetupForUnion(this); xsyncUQ(cb);
        try { lock(); unionCBLambda.query(cb); } finally { unlock(); } xsaveUCB(cb);
        final MessageCQ cq = cb.query(); query().xsetUnionAllQuery(cq);
    }

    // ===================================================================================
    //                                                                         SetupSelect
    //                                                                         ===========
    /**
     * Set up relation columns to select clause. <br>
     * FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'.
     * <pre>
     * <span style="color: #0000C0">messageBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_FaceType()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">message</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">message</span>.<span style="color: #CC4747">getFaceType()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     */
    public void setupSelect_FaceType() {
        assertSetupSelectPurpose("faceType");
        if (hasSpecifiedLocalColumn()) {
            specify().columnFaceTypeCode();
        }
        doSetupSelect(() -> query().queryFaceType());
    }

    /**
     * Set up relation columns to select clause. <br>
     * MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'.
     * <pre>
     * <span style="color: #0000C0">messageBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_MessageType()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">message</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">message</span>.<span style="color: #CC4747">getMessageType()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     */
    public void setupSelect_MessageType() {
        assertSetupSelectPurpose("messageType");
        if (hasSpecifiedLocalColumn()) {
            specify().columnMessageTypeCode();
        }
        doSetupSelect(() -> query().queryMessageType());
    }

    protected PlayerNss _nssPlayer;
    public PlayerNss xdfgetNssPlayer() {
        if (_nssPlayer == null) { _nssPlayer = new PlayerNss(null); }
        return _nssPlayer;
    }
    /**
     * Set up relation columns to select clause. <br>
     * PLAYER by my PLAYER_ID, named 'player'.
     * <pre>
     * <span style="color: #0000C0">messageBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_Player()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">message</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">message</span>.<span style="color: #CC4747">getPlayer()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     * @return The set-upper of nested relation. {setupSelect...().with[nested-relation]} (NotNull)
     */
    public PlayerNss setupSelect_Player() {
        assertSetupSelectPurpose("player");
        if (hasSpecifiedLocalColumn()) {
            specify().columnPlayerId();
        }
        doSetupSelect(() -> query().queryPlayer());
        if (_nssPlayer == null || !_nssPlayer.hasConditionQuery())
        { _nssPlayer = new PlayerNss(query().queryPlayer()); }
        return _nssPlayer;
    }

    protected VillagePlayerNss _nssVillagePlayerByToVillagePlayerId;
    public VillagePlayerNss xdfgetNssVillagePlayerByToVillagePlayerId() {
        if (_nssVillagePlayerByToVillagePlayerId == null) { _nssVillagePlayerByToVillagePlayerId = new VillagePlayerNss(null); }
        return _nssVillagePlayerByToVillagePlayerId;
    }
    /**
     * Set up relation columns to select clause. <br>
     * VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * <pre>
     * <span style="color: #0000C0">messageBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_VillagePlayerByToVillagePlayerId()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">message</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">message</span>.<span style="color: #CC4747">getVillagePlayerByToVillagePlayerId()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     * @return The set-upper of nested relation. {setupSelect...().with[nested-relation]} (NotNull)
     */
    public VillagePlayerNss setupSelect_VillagePlayerByToVillagePlayerId() {
        assertSetupSelectPurpose("villagePlayerByToVillagePlayerId");
        if (hasSpecifiedLocalColumn()) {
            specify().columnToVillagePlayerId();
        }
        doSetupSelect(() -> query().queryVillagePlayerByToVillagePlayerId());
        if (_nssVillagePlayerByToVillagePlayerId == null || !_nssVillagePlayerByToVillagePlayerId.hasConditionQuery())
        { _nssVillagePlayerByToVillagePlayerId = new VillagePlayerNss(query().queryVillagePlayerByToVillagePlayerId()); }
        return _nssVillagePlayerByToVillagePlayerId;
    }

    protected VillageDayNss _nssVillageDay;
    public VillageDayNss xdfgetNssVillageDay() {
        if (_nssVillageDay == null) { _nssVillageDay = new VillageDayNss(null); }
        return _nssVillageDay;
    }
    /**
     * Set up relation columns to select clause. <br>
     * VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'.
     * <pre>
     * <span style="color: #0000C0">messageBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_VillageDay()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">message</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">message</span>.<span style="color: #CC4747">getVillageDay()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     * @return The set-upper of nested relation. {setupSelect...().with[nested-relation]} (NotNull)
     */
    public VillageDayNss setupSelect_VillageDay() {
        assertSetupSelectPurpose("villageDay");
        if (hasSpecifiedLocalColumn()) {
            specify().columnVillageId();
            specify().columnDay();
        }
        doSetupSelect(() -> query().queryVillageDay());
        if (_nssVillageDay == null || !_nssVillageDay.hasConditionQuery())
        { _nssVillageDay = new VillageDayNss(query().queryVillageDay()); }
        return _nssVillageDay;
    }

    protected VillagePlayerNss _nssVillagePlayerByVillagePlayerId;
    public VillagePlayerNss xdfgetNssVillagePlayerByVillagePlayerId() {
        if (_nssVillagePlayerByVillagePlayerId == null) { _nssVillagePlayerByVillagePlayerId = new VillagePlayerNss(null); }
        return _nssVillagePlayerByVillagePlayerId;
    }
    /**
     * Set up relation columns to select clause. <br>
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * <pre>
     * <span style="color: #0000C0">messageBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_VillagePlayerByVillagePlayerId()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">message</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">message</span>.<span style="color: #CC4747">getVillagePlayerByVillagePlayerId()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     * @return The set-upper of nested relation. {setupSelect...().with[nested-relation]} (NotNull)
     */
    public VillagePlayerNss setupSelect_VillagePlayerByVillagePlayerId() {
        assertSetupSelectPurpose("villagePlayerByVillagePlayerId");
        if (hasSpecifiedLocalColumn()) {
            specify().columnVillagePlayerId();
        }
        doSetupSelect(() -> query().queryVillagePlayerByVillagePlayerId());
        if (_nssVillagePlayerByVillagePlayerId == null || !_nssVillagePlayerByVillagePlayerId.hasConditionQuery())
        { _nssVillagePlayerByVillagePlayerId = new VillagePlayerNss(query().queryVillagePlayerByVillagePlayerId()); }
        return _nssVillagePlayerByVillagePlayerId;
    }

    // [DBFlute-0.7.4]
    // ===================================================================================
    //                                                                             Specify
    //                                                                             =======
    protected HpSpecification _specification;

    /**
     * Prepare for SpecifyColumn, (Specify)DerivedReferrer. <br>
     * This method should be called after SetupSelect.
     * <pre>
     * <span style="color: #0000C0">memberBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.setupSelect_MemberStatus(); <span style="color: #3F7E5E">// should be called before specify()</span>
     *     <span style="color: #553000">cb</span>.specify().columnMemberName();
     *     <span style="color: #553000">cb</span>.specify().specifyMemberStatus().columnMemberStatusName();
     *     <span style="color: #553000">cb</span>.specify().derivedPurchaseList().max(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">purchaseCB</span>.specify().columnPurchaseDatetime();
     *         <span style="color: #553000">purchaseCB</span>.query().set...
     *     }, aliasName);
     * }).alwaysPresent(<span style="color: #553000">member</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ...
     * });
     * </pre>
     * @return The instance of specification. (NotNull)
     */
    public HpSpecification specify() {
        assertSpecifyPurpose();
        if (_specification == null) { _specification = new HpSpecification(this
            , xcreateSpQyCall(() -> true, () -> xdfgetConditionQuery())
            , _purpose, getDBMetaProvider(), xcSDRFnFc()); }
        return _specification;
    }

    public HpColumnSpHandler localSp() {
        return specify();
    }

    public boolean hasSpecifiedLocalColumn() {
        return _specification != null && _specification.hasSpecifiedColumn();
    }

    public static class HpSpecification extends HpAbstractSpecification<MessageCQ> {
        protected FaceTypeCB.HpSpecification _faceType;
        protected MessageTypeCB.HpSpecification _messageType;
        protected PlayerCB.HpSpecification _player;
        protected VillagePlayerCB.HpSpecification _villagePlayerByToVillagePlayerId;
        protected VillageDayCB.HpSpecification _villageDay;
        protected VillagePlayerCB.HpSpecification _villagePlayerByVillagePlayerId;
        public HpSpecification(ConditionBean baseCB, HpSpQyCall<MessageCQ> qyCall
                             , HpCBPurpose purpose, DBMetaProvider dbmetaProvider
                             , HpSDRFunctionFactory sdrFuncFactory)
        { super(baseCB, qyCall, purpose, dbmetaProvider, sdrFuncFactory); }
        /**
         * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnMessageId() { return doColumn("MESSAGE_ID"); }
        /**
         * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to village_day}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnVillageId() { return doColumn("VILLAGE_ID"); }
        /**
         * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnVillagePlayerId() { return doColumn("VILLAGE_PLAYER_ID"); }
        /**
         * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnToVillagePlayerId() { return doColumn("TO_VILLAGE_PLAYER_ID"); }
        /**
         * PLAYER_ID: {IX, INT UNSIGNED(10), FK to player}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnPlayerId() { return doColumn("PLAYER_ID"); }
        /**
         * DAY: {NotNull, INT UNSIGNED(10), FK to village_day}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnDay() { return doColumn("DAY"); }
        /**
         * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnMessageTypeCode() { return doColumn("MESSAGE_TYPE_CODE"); }
        /**
         * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnMessageNumber() { return doColumn("MESSAGE_NUMBER"); }
        /**
         * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnMessageContent() { return doColumn("MESSAGE_CONTENT"); }
        /**
         * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnMessageDatetime() { return doColumn("MESSAGE_DATETIME"); }
        /**
         * IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsConvertDisable() { return doColumn("IS_CONVERT_DISABLE"); }
        /**
         * FACE_TYPE_CODE: {IX, VARCHAR(20), FK to face_type, classification=FaceType}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnFaceTypeCode() { return doColumn("FACE_TYPE_CODE"); }
        /**
         * REGISTER_DATETIME: {NotNull, DATETIME(19)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnRegisterDatetime() { return doColumn("REGISTER_DATETIME"); }
        /**
         * REGISTER_TRACE: {NotNull, VARCHAR(64)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnRegisterTrace() { return doColumn("REGISTER_TRACE"); }
        /**
         * UPDATE_DATETIME: {NotNull, DATETIME(19)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnUpdateDatetime() { return doColumn("UPDATE_DATETIME"); }
        /**
         * UPDATE_TRACE: {NotNull, VARCHAR(64)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnUpdateTrace() { return doColumn("UPDATE_TRACE"); }
        public void everyColumn() { doEveryColumn(); }
        public void exceptRecordMetaColumn() { doExceptRecordMetaColumn(); }
        @Override
        protected void doSpecifyRequiredColumn() {
            columnMessageId(); // PK
            if (qyCall().qy().hasConditionQueryFaceType()
                    || qyCall().qy().xgetReferrerQuery() instanceof FaceTypeCQ) {
                columnFaceTypeCode(); // FK or one-to-one referrer
            }
            if (qyCall().qy().hasConditionQueryMessageType()
                    || qyCall().qy().xgetReferrerQuery() instanceof MessageTypeCQ) {
                columnMessageTypeCode(); // FK or one-to-one referrer
            }
            if (qyCall().qy().hasConditionQueryPlayer()
                    || qyCall().qy().xgetReferrerQuery() instanceof PlayerCQ) {
                columnPlayerId(); // FK or one-to-one referrer
            }
            if (qyCall().qy().hasConditionQueryVillagePlayerByToVillagePlayerId()
                    || qyCall().qy().xgetReferrerQuery() instanceof VillagePlayerCQ) {
                columnToVillagePlayerId(); // FK or one-to-one referrer
            }
            if (qyCall().qy().hasConditionQueryVillageDay()
                    || qyCall().qy().xgetReferrerQuery() instanceof VillageDayCQ) {
                columnVillageId(); // FK or one-to-one referrer
                columnDay(); // FK or one-to-one referrer
            }
            if (qyCall().qy().hasConditionQueryVillagePlayerByVillagePlayerId()
                    || qyCall().qy().xgetReferrerQuery() instanceof VillagePlayerCQ) {
                columnVillagePlayerId(); // FK or one-to-one referrer
            }
        }
        @Override
        protected String getTableDbName() { return "message"; }
        /**
         * Prepare to specify functions about relation table. <br>
         * FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public FaceTypeCB.HpSpecification specifyFaceType() {
            assertRelation("faceType");
            if (_faceType == null) {
                _faceType = new FaceTypeCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryFaceType()
                                    , () -> _qyCall.qy().queryFaceType())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _faceType.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryFaceType()
                      , () -> xsyncQyCall().qy().queryFaceType()));
                }
            }
            return _faceType;
        }
        /**
         * Prepare to specify functions about relation table. <br>
         * MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public MessageTypeCB.HpSpecification specifyMessageType() {
            assertRelation("messageType");
            if (_messageType == null) {
                _messageType = new MessageTypeCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryMessageType()
                                    , () -> _qyCall.qy().queryMessageType())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _messageType.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryMessageType()
                      , () -> xsyncQyCall().qy().queryMessageType()));
                }
            }
            return _messageType;
        }
        /**
         * Prepare to specify functions about relation table. <br>
         * PLAYER by my PLAYER_ID, named 'player'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public PlayerCB.HpSpecification specifyPlayer() {
            assertRelation("player");
            if (_player == null) {
                _player = new PlayerCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryPlayer()
                                    , () -> _qyCall.qy().queryPlayer())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _player.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryPlayer()
                      , () -> xsyncQyCall().qy().queryPlayer()));
                }
            }
            return _player;
        }
        /**
         * Prepare to specify functions about relation table. <br>
         * VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public VillagePlayerCB.HpSpecification specifyVillagePlayerByToVillagePlayerId() {
            assertRelation("villagePlayerByToVillagePlayerId");
            if (_villagePlayerByToVillagePlayerId == null) {
                _villagePlayerByToVillagePlayerId = new VillagePlayerCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryVillagePlayerByToVillagePlayerId()
                                    , () -> _qyCall.qy().queryVillagePlayerByToVillagePlayerId())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _villagePlayerByToVillagePlayerId.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryVillagePlayerByToVillagePlayerId()
                      , () -> xsyncQyCall().qy().queryVillagePlayerByToVillagePlayerId()));
                }
            }
            return _villagePlayerByToVillagePlayerId;
        }
        /**
         * Prepare to specify functions about relation table. <br>
         * VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public VillageDayCB.HpSpecification specifyVillageDay() {
            assertRelation("villageDay");
            if (_villageDay == null) {
                _villageDay = new VillageDayCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryVillageDay()
                                    , () -> _qyCall.qy().queryVillageDay())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _villageDay.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryVillageDay()
                      , () -> xsyncQyCall().qy().queryVillageDay()));
                }
            }
            return _villageDay;
        }
        /**
         * Prepare to specify functions about relation table. <br>
         * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public VillagePlayerCB.HpSpecification specifyVillagePlayerByVillagePlayerId() {
            assertRelation("villagePlayerByVillagePlayerId");
            if (_villagePlayerByVillagePlayerId == null) {
                _villagePlayerByVillagePlayerId = new VillagePlayerCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryVillagePlayerByVillagePlayerId()
                                    , () -> _qyCall.qy().queryVillagePlayerByVillagePlayerId())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _villagePlayerByVillagePlayerId.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryVillagePlayerByVillagePlayerId()
                      , () -> xsyncQyCall().qy().queryVillagePlayerByVillagePlayerId()));
                }
            }
            return _villagePlayerByVillagePlayerId;
        }
        /**
         * Prepare for (Specify)MyselfDerived (SubQuery).
         * @return The object to set up a function for myself table. (NotNull)
         */
        public HpSDRFunction<MessageCB, MessageCQ> myselfDerived() {
            assertDerived("myselfDerived"); if (xhasSyncQyCall()) { xsyncQyCall().qy(); } // for sync (for example, this in ColumnQuery)
            return cHSDRF(_baseCB, _qyCall.qy(), (String fn, SubQuery<MessageCB> sq, MessageCQ cq, String al, DerivedReferrerOption op)
                    -> cq.xsmyselfDerive(fn, sq, al, op), _dbmetaProvider);
        }
    }

    // ===================================================================================
    //                                                                        Dream Cruise
    //                                                                        ============
    /**
     * Welcome to the Dream Cruise for condition-bean deep world. <br>
     * This is very specialty so you can get the frontier spirit. Bon voyage!
     * @return The condition-bean for dream cruise, which is linked to main condition-bean.
     */
    public MessageCB dreamCruiseCB() {
        MessageCB cb = new MessageCB();
        cb.xsetupForDreamCruise((MessageCB) this);
        return cb;
    }

    protected ConditionBean xdoCreateDreamCruiseCB() {
        return dreamCruiseCB();
    }

    // [DBFlute-0.9.5.3]
    // ===================================================================================
    //                                                                        Column Query
    //                                                                        ============
    /**
     * Set up column-query. {column1 = column2}
     * <pre>
     * <span style="color: #3F7E5E">// where FOO &lt; BAR</span>
     * cb.<span style="color: #CC4747">columnQuery</span>(<span style="color: #553000">colCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">colCB</span>.specify().<span style="color: #CC4747">columnFoo()</span>; <span style="color: #3F7E5E">// left column</span>
     * }).lessThan(<span style="color: #553000">colCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">colCB</span>.specify().<span style="color: #CC4747">columnBar()</span>; <span style="color: #3F7E5E">// right column</span>
     * }); <span style="color: #3F7E5E">// you can calculate for right column like '}).plus(3);'</span>
     * </pre>
     * @param colCBLambda The callback for specify-query of left column. (NotNull)
     * @return The object for setting up operand and right column. (NotNull)
     */
    public HpColQyOperand<MessageCB> columnQuery(final SpecifyQuery<MessageCB> colCBLambda) {
        return xcreateColQyOperand((rightSp, operand) -> {
            return xcolqy(xcreateColumnQueryCB(), xcreateColumnQueryCB(), colCBLambda, rightSp, operand);
        });
    }

    protected MessageCB xcreateColumnQueryCB() {
        MessageCB cb = new MessageCB();
        cb.xsetupForColumnQuery((MessageCB)this);
        return cb;
    }

    // [DBFlute-0.9.6.3]
    // ===================================================================================
    //                                                                       OrScope Query
    //                                                                       =============
    /**
     * Set up the query for or-scope. <br>
     * (Same-column-and-same-condition-key conditions are allowed in or-scope)
     * <pre>
     * <span style="color: #3F7E5E">// where (FOO = '...' or BAR = '...')</span>
     * cb.<span style="color: #CC4747">orScopeQuery</span>(<span style="color: #553000">orCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">orCB</span>.query().setFoo...
     *     <span style="color: #553000">orCB</span>.query().setBar...
     * });
     * </pre>
     * @param orCBLambda The callback for query of or-condition. (NotNull)
     */
    public void orScopeQuery(OrQuery<MessageCB> orCBLambda) {
        xorSQ((MessageCB)this, orCBLambda);
    }

    /**
     * Set up the and-part of or-scope. <br>
     * (However nested or-scope query and as-or-split of like-search in and-part are unsupported)
     * <pre>
     * <span style="color: #3F7E5E">// where (FOO = '...' or (BAR = '...' and QUX = '...'))</span>
     * cb.<span style="color: #994747">orScopeQuery</span>(<span style="color: #553000">orCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">orCB</span>.query().setFoo...
     *     <span style="color: #553000">orCB</span>.<span style="color: #CC4747">orScopeQueryAndPart</span>(<span style="color: #553000">andCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">andCB</span>.query().setBar...
     *         <span style="color: #553000">andCB</span>.query().setQux...
     *     });
     * });
     * </pre>
     * @param andCBLambda The callback for query of and-condition. (NotNull)
     */
    public void orScopeQueryAndPart(AndQuery<MessageCB> andCBLambda) {
        xorSQAP((MessageCB)this, andCBLambda);
    }

    // ===================================================================================
    //                                                                          DisplaySQL
    //                                                                          ==========
    @Override
    protected SqlAnalyzerFactory getSqlAnalyzerFactory()
    { return new ImplementedInvokerAssistant().assistSqlAnalyzerFactory(); }
    @Override
    protected String getConfiguredLogDatePattern() { return DBFluteConfig.getInstance().getLogDatePattern(); }
    @Override
    protected String getConfiguredLogTimestampPattern() { return DBFluteConfig.getInstance().getLogTimestampPattern(); }
    @Override
    protected String getConfiguredLogTimePattern() { return DBFluteConfig.getInstance().getLogTimePattern(); }
    @Override
    protected BoundDateDisplayTimeZoneProvider getConfiguredLogTimeZoneProvider() { return DBFluteConfig.getInstance().getLogTimeZoneProvider(); }

    // ===================================================================================
    //                                                                       Meta Handling
    //                                                                       =============
    public boolean hasUnionQueryOrUnionAllQuery() {
        return query().hasUnionQueryOrUnionAllQuery();
    }

    // ===================================================================================
    //                                                                        Purpose Type
    //                                                                        ============
    @Override
    protected void xprepareSyncQyCall(ConditionBean mainCB) {
        final MessageCB cb;
        if (mainCB != null) {
            cb = (MessageCB)mainCB;
        } else {
            cb = new MessageCB();
        }
        specify().xsetSyncQyCall(xcreateSpQyCall(() -> true, () -> cb.query()));
    }

    // ===================================================================================
    //                                                                            Internal
    //                                                                            ========
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xgetConditionBeanClassNameInternally() { return MessageCB.class.getName(); }
    protected String xgetConditionQueryClassNameInternally() { return MessageCQ.class.getName(); }
    protected String xgetSubQueryClassNameInternally() { return SubQuery.class.getName(); }
    protected String xgetConditionOptionClassNameInternally() { return ConditionOption.class.getName(); }
}
