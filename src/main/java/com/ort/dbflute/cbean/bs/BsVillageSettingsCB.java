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
import com.ort.dbflute.allcommon.DBFluteConfig;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.allcommon.ImplementedInvokerAssistant;
import com.ort.dbflute.allcommon.ImplementedSqlClauseCreator;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;
import com.ort.dbflute.cbean.nss.*;

/**
 * The base condition-bean of village_settings.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillageSettingsCB extends AbstractConditionBean {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillageSettingsCQ _conditionQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillageSettingsCB() {
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
        return "village_settings";
    }

    // ===================================================================================
    //                                                                 PrimaryKey Handling
    //                                                                 ===================
    /**
     * Accept the query condition of primary key as equal.
     * @param villageId : PK, NotNull, INT UNSIGNED(10), FK to village. (NotNull)
     * @return this. (NotNull)
     */
    public VillageSettingsCB acceptPK(Integer villageId) {
        assertObjectNotNull("villageId", villageId);
        BsVillageSettingsCB cb = this;
        cb.query().setVillageId_Equal(villageId);
        return (VillageSettingsCB)this;
    }

    public ConditionBean addOrderBy_PK_Asc() {
        query().addOrderBy_VillageId_Asc();
        return this;
    }

    public ConditionBean addOrderBy_PK_Desc() {
        query().addOrderBy_VillageId_Desc();
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
    public VillageSettingsCQ query() {
        assertQueryPurpose(); // assert only when user-public query
        return doGetConditionQuery();
    }

    public VillageSettingsCQ xdfgetConditionQuery() { // public for parameter comment and internal
        return doGetConditionQuery();
    }

    protected VillageSettingsCQ doGetConditionQuery() {
        if (_conditionQuery == null) {
            _conditionQuery = createLocalCQ();
        }
        return _conditionQuery;
    }

    protected VillageSettingsCQ createLocalCQ() {
        return xcreateCQ(null, getSqlClause(), getSqlClause().getBasePointAliasName(), 0);
    }

    protected VillageSettingsCQ xcreateCQ(ConditionQuery childQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        VillageSettingsCQ cq = xnewCQ(childQuery, sqlClause, aliasName, nestLevel);
        cq.xsetBaseCB(this);
        return cq;
    }

    protected VillageSettingsCQ xnewCQ(ConditionQuery childQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        return new VillageSettingsCQ(childQuery, sqlClause, aliasName, nestLevel);
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
    public void union(UnionQuery<VillageSettingsCB> unionCBLambda) {
        final VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForUnion(this); xsyncUQ(cb);
        try { lock(); unionCBLambda.query(cb); } finally { unlock(); } xsaveUCB(cb);
        final VillageSettingsCQ cq = cb.query(); query().xsetUnionQuery(cq);
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
    public void unionAll(UnionQuery<VillageSettingsCB> unionCBLambda) {
        final VillageSettingsCB cb = new VillageSettingsCB(); cb.xsetupForUnion(this); xsyncUQ(cb);
        try { lock(); unionCBLambda.query(cb); } finally { unlock(); } xsaveUCB(cb);
        final VillageSettingsCQ cq = cb.query(); query().xsetUnionAllQuery(cq);
    }

    // ===================================================================================
    //                                                                         SetupSelect
    //                                                                         ===========
    /**
     * Set up relation columns to select clause. <br>
     * ALLOWED_SECRET_SAY by my ALLOWED_SECRET_SAY_CODE, named 'allowedSecretSay'.
     * <pre>
     * <span style="color: #0000C0">villageSettingsBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_AllowedSecretSay()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">villageSettings</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">villageSettings</span>.<span style="color: #CC4747">getAllowedSecretSay()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     */
    public void setupSelect_AllowedSecretSay() {
        assertSetupSelectPurpose("allowedSecretSay");
        if (hasSpecifiedLocalColumn()) {
            specify().columnAllowedSecretSayCode();
        }
        doSetupSelect(() -> query().queryAllowedSecretSay());
    }

    /**
     * Set up relation columns to select clause. <br>
     * ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'.
     * <pre>
     * <span style="color: #0000C0">villageSettingsBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_OriginalCharaGroup()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">villageSettings</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">villageSettings</span>.<span style="color: #CC4747">getOriginalCharaGroup()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     */
    public void setupSelect_OriginalCharaGroup() {
        assertSetupSelectPurpose("originalCharaGroup");
        if (hasSpecifiedLocalColumn()) {
            specify().columnOriginalCharaGroupId();
        }
        doSetupSelect(() -> query().queryOriginalCharaGroup());
    }

    protected VillageNss _nssVillage;
    public VillageNss xdfgetNssVillage() {
        if (_nssVillage == null) { _nssVillage = new VillageNss(null); }
        return _nssVillage;
    }
    /**
     * Set up relation columns to select clause. <br>
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * <pre>
     * <span style="color: #0000C0">villageSettingsBhv</span>.selectEntity(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">setupSelect_Village()</span>; <span style="color: #3F7E5E">// ...().with[nested-relation]()</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * }).alwaysPresent(<span style="color: #553000">villageSettings</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">villageSettings</span>.<span style="color: #CC4747">getVillage()</span>; <span style="color: #3F7E5E">// you can get by using SetupSelect</span>
     * });
     * </pre>
     * @return The set-upper of nested relation. {setupSelect...().with[nested-relation]} (NotNull)
     */
    public VillageNss setupSelect_Village() {
        assertSetupSelectPurpose("village");
        doSetupSelect(() -> query().queryVillage());
        if (_nssVillage == null || !_nssVillage.hasConditionQuery())
        { _nssVillage = new VillageNss(query().queryVillage()); }
        return _nssVillage;
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

    public static class HpSpecification extends HpAbstractSpecification<VillageSettingsCQ> {
        protected AllowedSecretSayCB.HpSpecification _allowedSecretSay;
        protected OriginalCharaGroupCB.HpSpecification _originalCharaGroup;
        protected VillageCB.HpSpecification _village;
        public HpSpecification(ConditionBean baseCB, HpSpQyCall<VillageSettingsCQ> qyCall
                             , HpCBPurpose purpose, DBMetaProvider dbmetaProvider
                             , HpSDRFunctionFactory sdrFuncFactory)
        { super(baseCB, qyCall, purpose, dbmetaProvider, sdrFuncFactory); }
        /**
         * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnVillageId() { return doColumn("VILLAGE_ID"); }
        /**
         * DUMMY_CHARA_ID: {NotNull, INT UNSIGNED(10)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnDummyCharaId() { return doColumn("DUMMY_CHARA_ID"); }
        /**
         * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnStartPersonMinNum() { return doColumn("START_PERSON_MIN_NUM"); }
        /**
         * PERSON_MAX_NUM: {INT UNSIGNED(10)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnPersonMaxNum() { return doColumn("PERSON_MAX_NUM"); }
        /**
         * START_DATETIME: {DATETIME(19)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnStartDatetime() { return doColumn("START_DATETIME"); }
        /**
         * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnDayChangeIntervalSeconds() { return doColumn("DAY_CHANGE_INTERVAL_SECONDS"); }
        /**
         * IS_OPEN_VOTE: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsOpenVote() { return doColumn("IS_OPEN_VOTE"); }
        /**
         * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsPossibleSkillRequest() { return doColumn("IS_POSSIBLE_SKILL_REQUEST"); }
        /**
         * IS_AVAILABLE_SPECTATE: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsAvailableSpectate() { return doColumn("IS_AVAILABLE_SPECTATE"); }
        /**
         * IS_AVAILABLE_SAME_WOLF_ATTACK: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsAvailableSameWolfAttack() { return doColumn("IS_AVAILABLE_SAME_WOLF_ATTACK"); }
        /**
         * IS_OPEN_SKILL_IN_GRAVE: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsOpenSkillInGrave() { return doColumn("IS_OPEN_SKILL_IN_GRAVE"); }
        /**
         * IS_VISIBLE_GRAVE_SPECTATE_MESSAGE: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsVisibleGraveSpectateMessage() { return doColumn("IS_VISIBLE_GRAVE_SPECTATE_MESSAGE"); }
        /**
         * IS_AVAILABLE_SUDDONLY_DEATH: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsAvailableSuddonlyDeath() { return doColumn("IS_AVAILABLE_SUDDONLY_DEATH"); }
        /**
         * IS_AVAILABLE_COMMIT: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsAvailableCommit() { return doColumn("IS_AVAILABLE_COMMIT"); }
        /**
         * IS_AVAILABLE_GUARD_SAME_TARGET: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsAvailableGuardSameTarget() { return doColumn("IS_AVAILABLE_GUARD_SAME_TARGET"); }
        /**
         * JOIN_PASSWORD: {VARCHAR(12)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnJoinPassword() { return doColumn("JOIN_PASSWORD"); }
        /**
         * ORGANIZE: {NotNull, VARCHAR(10000)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnOrganize() { return doColumn("ORGANIZE"); }
        /**
         * ALLOWED_SECRET_SAY_CODE: {IX, NotNull, VARCHAR(20), FK to allowed_secret_say, classification=AllowedSecretSay}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnAllowedSecretSayCode() { return doColumn("ALLOWED_SECRET_SAY_CODE"); }
        /**
         * IS_AVAILABLE_ACTION: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsAvailableAction() { return doColumn("IS_AVAILABLE_ACTION"); }
        /**
         * IS_RANDOM_ORGANIZE: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsRandomOrganize() { return doColumn("IS_RANDOM_ORGANIZE"); }
        /**
         * IS_REINCARNATION_SKILL_ALL: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsReincarnationSkillAll() { return doColumn("IS_REINCARNATION_SKILL_ALL"); }
        /**
         * IS_CREATOR_PRODUCER: {NotNull, BIT, classification=Flg}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnIsCreatorProducer() { return doColumn("IS_CREATOR_PRODUCER"); }
        /**
         * ORIGINAL_CHARA_GROUP_ID: {IX, INT UNSIGNED(10), FK to original_chara_group}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnOriginalCharaGroupId() { return doColumn("ORIGINAL_CHARA_GROUP_ID"); }
        /**
         * DAY1_DUMMY_MESSAGE: {TEXT(65535)}
         * @return The information object of specified column. (NotNull)
         */
        public SpecifiedColumn columnDay1DummyMessage() { return doColumn("DAY1_DUMMY_MESSAGE"); }
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
            columnVillageId(); // PK
            if (qyCall().qy().hasConditionQueryAllowedSecretSay()
                    || qyCall().qy().xgetReferrerQuery() instanceof AllowedSecretSayCQ) {
                columnAllowedSecretSayCode(); // FK or one-to-one referrer
            }
            if (qyCall().qy().hasConditionQueryOriginalCharaGroup()
                    || qyCall().qy().xgetReferrerQuery() instanceof OriginalCharaGroupCQ) {
                columnOriginalCharaGroupId(); // FK or one-to-one referrer
            }
        }
        @Override
        protected String getTableDbName() { return "village_settings"; }
        /**
         * Prepare to specify functions about relation table. <br>
         * ALLOWED_SECRET_SAY by my ALLOWED_SECRET_SAY_CODE, named 'allowedSecretSay'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public AllowedSecretSayCB.HpSpecification specifyAllowedSecretSay() {
            assertRelation("allowedSecretSay");
            if (_allowedSecretSay == null) {
                _allowedSecretSay = new AllowedSecretSayCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryAllowedSecretSay()
                                    , () -> _qyCall.qy().queryAllowedSecretSay())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _allowedSecretSay.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryAllowedSecretSay()
                      , () -> xsyncQyCall().qy().queryAllowedSecretSay()));
                }
            }
            return _allowedSecretSay;
        }
        /**
         * Prepare to specify functions about relation table. <br>
         * ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public OriginalCharaGroupCB.HpSpecification specifyOriginalCharaGroup() {
            assertRelation("originalCharaGroup");
            if (_originalCharaGroup == null) {
                _originalCharaGroup = new OriginalCharaGroupCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryOriginalCharaGroup()
                                    , () -> _qyCall.qy().queryOriginalCharaGroup())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _originalCharaGroup.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryOriginalCharaGroup()
                      , () -> xsyncQyCall().qy().queryOriginalCharaGroup()));
                }
            }
            return _originalCharaGroup;
        }
        /**
         * Prepare to specify functions about relation table. <br>
         * VILLAGE by my VILLAGE_ID, named 'village'.
         * @return The instance for specification for relation table to specify. (NotNull)
         */
        public VillageCB.HpSpecification specifyVillage() {
            assertRelation("village");
            if (_village == null) {
                _village = new VillageCB.HpSpecification(_baseCB
                    , xcreateSpQyCall(() -> _qyCall.has() && _qyCall.qy().hasConditionQueryVillage()
                                    , () -> _qyCall.qy().queryVillage())
                    , _purpose, _dbmetaProvider, xgetSDRFnFc());
                if (xhasSyncQyCall()) { // inherits it
                    _village.xsetSyncQyCall(xcreateSpQyCall(
                        () -> xsyncQyCall().has() && xsyncQyCall().qy().hasConditionQueryVillage()
                      , () -> xsyncQyCall().qy().queryVillage()));
                }
            }
            return _village;
        }
        /**
         * Prepare for (Specify)MyselfDerived (SubQuery).
         * @return The object to set up a function for myself table. (NotNull)
         */
        public HpSDRFunction<VillageSettingsCB, VillageSettingsCQ> myselfDerived() {
            assertDerived("myselfDerived"); if (xhasSyncQyCall()) { xsyncQyCall().qy(); } // for sync (for example, this in ColumnQuery)
            return cHSDRF(_baseCB, _qyCall.qy(), (String fn, SubQuery<VillageSettingsCB> sq, VillageSettingsCQ cq, String al, DerivedReferrerOption op)
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
    public VillageSettingsCB dreamCruiseCB() {
        VillageSettingsCB cb = new VillageSettingsCB();
        cb.xsetupForDreamCruise((VillageSettingsCB) this);
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
    public HpColQyOperand<VillageSettingsCB> columnQuery(final SpecifyQuery<VillageSettingsCB> colCBLambda) {
        return xcreateColQyOperand((rightSp, operand) -> {
            return xcolqy(xcreateColumnQueryCB(), xcreateColumnQueryCB(), colCBLambda, rightSp, operand);
        });
    }

    protected VillageSettingsCB xcreateColumnQueryCB() {
        VillageSettingsCB cb = new VillageSettingsCB();
        cb.xsetupForColumnQuery((VillageSettingsCB)this);
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
    public void orScopeQuery(OrQuery<VillageSettingsCB> orCBLambda) {
        xorSQ((VillageSettingsCB)this, orCBLambda);
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
    public void orScopeQueryAndPart(AndQuery<VillageSettingsCB> andCBLambda) {
        xorSQAP((VillageSettingsCB)this, andCBLambda);
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
        final VillageSettingsCB cb;
        if (mainCB != null) {
            cb = (VillageSettingsCB)mainCB;
        } else {
            cb = new VillageSettingsCB();
        }
        specify().xsetSyncQyCall(xcreateSpQyCall(() -> true, () -> cb.query()));
    }

    // ===================================================================================
    //                                                                            Internal
    //                                                                            ========
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xgetConditionBeanClassNameInternally() { return VillageSettingsCB.class.getName(); }
    protected String xgetConditionQueryClassNameInternally() { return VillageSettingsCQ.class.getName(); }
    protected String xgetSubQueryClassNameInternally() { return SubQuery.class.getName(); }
    protected String xgetConditionOptionClassNameInternally() { return ConditionOption.class.getName(); }
}
