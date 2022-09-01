package com.ort.dbflute.bsbhv;

import java.util.List;

import org.dbflute.*;
import org.dbflute.bhv.*;
import org.dbflute.bhv.core.BehaviorCommandInvoker;
import org.dbflute.bhv.readable.*;
import org.dbflute.bhv.writable.*;
import org.dbflute.bhv.referrer.*;
import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.HpSLSFunction;
import org.dbflute.cbean.result.*;
import org.dbflute.exception.*;
import org.dbflute.hook.CommonColumnAutoSetupper;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.outsidesql.executor.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.bsbhv.loader.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.bsentity.dbmeta.*;
import com.ort.dbflute.cbean.*;

/**
 * The behavior of ORIGINAL_CHARA_GROUP as TABLE. <br>
 * <pre>
 * [primary key]
 *     ORIGINAL_CHARA_GROUP_ID
 *
 * [column]
 *     ORIGINAL_CHARA_GROUP_ID, ORIGINAL_CHARA_GROUP_NAME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     ORIGINAL_CHARA_GROUP_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     
 *
 * [referrer table]
 *     ORIGINAL_CHARA, VILLAGE_SETTINGS
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     originalCharaList, villageSettingsList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsOriginalCharaGroupBhv extends AbstractBehaviorWritable<OriginalCharaGroup, OriginalCharaGroupCB> {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /*df:beginQueryPath*/
    /*df:endQueryPath*/

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public OriginalCharaGroupDbm asDBMeta() { return OriginalCharaGroupDbm.getInstance(); }
    /** {@inheritDoc} */
    public String asTableDbName() { return "ORIGINAL_CHARA_GROUP"; }

    // ===================================================================================
    //                                                                        New Instance
    //                                                                        ============
    /** {@inheritDoc} */
    public OriginalCharaGroupCB newConditionBean() { return new OriginalCharaGroupCB(); }

    // ===================================================================================
    //                                                                        Count Select
    //                                                                        ============
    /**
     * Select the count of uniquely-selected records by the condition-bean. {IgnorePagingCondition, IgnoreSpecifyColumn}<br>
     * SpecifyColumn is ignored but you can use it only to remove text type column for union's distinct.
     * <pre>
     * <span style="color: #70226C">int</span> count = <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectCount</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @return The count for the condition. (NotMinus)
     */
    public int selectCount(CBCall<OriginalCharaGroupCB> cbLambda) {
        return facadeSelectCount(createCB(cbLambda));
    }

    // ===================================================================================
    //                                                                       Entity Select
    //                                                                       =============
    /**
     * Select the entity by the condition-bean. <br>
     * It returns not-null optional entity, so you should ... <br>
     * <span style="color: #AD4747; font-size: 120%">If the data is always present as your business rule, alwaysPresent().</span> <br>
     * <span style="color: #AD4747; font-size: 120%">If it might be no data, isPresent() and orElse(), ...</span>
     * <pre>
     * <span style="color: #3F7E5E">// if the data always exists as your business rule</span>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectEntity</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * }).<span style="color: #CC4747">alwaysPresent</span>(<span style="color: #553000">originalCharaGroup</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// called if present, or exception</span>
     *     ... = <span style="color: #553000">originalCharaGroup</span>.get...
     * });
     *
     * <span style="color: #3F7E5E">// if it might be no data, ...</span>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectEntity</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * }).<span style="color: #CC4747">ifPresent</span>(<span style="color: #553000">originalCharaGroup</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// called if present</span>
     *     ... = <span style="color: #553000">originalCharaGroup</span>.get...
     * }).<span style="color: #994747">orElse</span>(() <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// called if not present</span>
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @return The optional entity selected by the condition. (NotNull: if no data, empty entity)
     * @throws EntityAlreadyDeletedException When get(), required() of return value is called and the value is null, which means entity has already been deleted (not found).
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public OptionalEntity<OriginalCharaGroup> selectEntity(CBCall<OriginalCharaGroupCB> cbLambda) {
        return facadeSelectEntity(createCB(cbLambda));
    }

    protected OptionalEntity<OriginalCharaGroup> facadeSelectEntity(OriginalCharaGroupCB cb) {
        return doSelectOptionalEntity(cb, typeOfSelectedEntity());
    }

    protected <ENTITY extends OriginalCharaGroup> OptionalEntity<ENTITY> doSelectOptionalEntity(OriginalCharaGroupCB cb, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectEntity(cb, tp), cb);
    }

    protected Entity doReadEntity(ConditionBean cb) { return facadeSelectEntity(downcast(cb)).orElse(null); }

    /**
     * Select the entity by the condition-bean with deleted check. <br>
     * <span style="color: #AD4747; font-size: 120%">If the data is always present as your business rule, this method is good.</span>
     * <pre>
     * OriginalCharaGroup <span style="color: #553000">originalCharaGroup</span> = <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectEntityWithDeletedCheck</span>(cb <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> cb.acceptPK(1));
     * ... = <span style="color: #553000">originalCharaGroup</span>.get...(); <span style="color: #3F7E5E">// the entity always be not null</span>
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @return The entity selected by the condition. (NotNull: if no data, throws exception)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public OriginalCharaGroup selectEntityWithDeletedCheck(CBCall<OriginalCharaGroupCB> cbLambda) {
        return facadeSelectEntityWithDeletedCheck(createCB(cbLambda));
    }

    /**
     * Select the entity by the primary-key value.
     * @param originalCharaGroupId : PK, ID, NotNull, INT UNSIGNED(10). (NotNull)
     * @return The optional entity selected by the PK. (NotNull: if no data, empty entity)
     * @throws EntityAlreadyDeletedException When get(), required() of return value is called and the value is null, which means entity has already been deleted (not found).
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws SelectEntityConditionNotFoundException When the condition for selecting an entity is not found.
     */
    public OptionalEntity<OriginalCharaGroup> selectByPK(Integer originalCharaGroupId) {
        return facadeSelectByPK(originalCharaGroupId);
    }

    protected OptionalEntity<OriginalCharaGroup> facadeSelectByPK(Integer originalCharaGroupId) {
        return doSelectOptionalByPK(originalCharaGroupId, typeOfSelectedEntity());
    }

    protected <ENTITY extends OriginalCharaGroup> ENTITY doSelectByPK(Integer originalCharaGroupId, Class<? extends ENTITY> tp) {
        return doSelectEntity(xprepareCBAsPK(originalCharaGroupId), tp);
    }

    protected <ENTITY extends OriginalCharaGroup> OptionalEntity<ENTITY> doSelectOptionalByPK(Integer originalCharaGroupId, Class<? extends ENTITY> tp) {
        return createOptionalEntity(doSelectByPK(originalCharaGroupId, tp), originalCharaGroupId);
    }

    protected OriginalCharaGroupCB xprepareCBAsPK(Integer originalCharaGroupId) {
        assertObjectNotNull("originalCharaGroupId", originalCharaGroupId);
        return newConditionBean().acceptPK(originalCharaGroupId);
    }

    // ===================================================================================
    //                                                                         List Select
    //                                                                         ===========
    /**
     * Select the list as result bean.
     * <pre>
     * ListResultBean&lt;OriginalCharaGroup&gt; <span style="color: #553000">originalCharaGroupList</span> = <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectList</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...;
     *     <span style="color: #553000">cb</span>.query().addOrderBy...;
     * });
     * <span style="color: #70226C">for</span> (OriginalCharaGroup <span style="color: #553000">originalCharaGroup</span> : <span style="color: #553000">originalCharaGroupList</span>) {
     *     ... = <span style="color: #553000">originalCharaGroup</span>.get...;
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @return The result bean of selected list. (NotNull: if no data, returns empty list)
     * @throws DangerousResultSizeException When the result size is over the specified safety size.
     */
    public ListResultBean<OriginalCharaGroup> selectList(CBCall<OriginalCharaGroupCB> cbLambda) {
        return facadeSelectList(createCB(cbLambda));
    }

    @Override
    protected boolean isEntityDerivedMappable() { return true; }

    // ===================================================================================
    //                                                                         Page Select
    //                                                                         ===========
    /**
     * Select the page as result bean. <br>
     * (both count-select and paging-select are executed)
     * <pre>
     * PagingResultBean&lt;OriginalCharaGroup&gt; <span style="color: #553000">page</span> = <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectPage</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     *     <span style="color: #553000">cb</span>.query().addOrderBy...
     *     <span style="color: #553000">cb</span>.<span style="color: #CC4747">paging</span>(20, 3); <span style="color: #3F7E5E">// 20 records per a page and current page number is 3</span>
     * });
     * <span style="color: #70226C">int</span> allRecordCount = <span style="color: #553000">page</span>.getAllRecordCount();
     * <span style="color: #70226C">int</span> allPageCount = <span style="color: #553000">page</span>.getAllPageCount();
     * <span style="color: #70226C">boolean</span> isExistPrePage = <span style="color: #553000">page</span>.isExistPrePage();
     * <span style="color: #70226C">boolean</span> isExistNextPage = <span style="color: #553000">page</span>.isExistNextPage();
     * ...
     * <span style="color: #70226C">for</span> (OriginalCharaGroup originalCharaGroup : <span style="color: #553000">page</span>) {
     *     ... = originalCharaGroup.get...;
     * }
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @return The result bean of selected page. (NotNull: if no data, returns bean as empty list)
     * @throws DangerousResultSizeException When the result size is over the specified safety size.
     */
    public PagingResultBean<OriginalCharaGroup> selectPage(CBCall<OriginalCharaGroupCB> cbLambda) {
        return facadeSelectPage(createCB(cbLambda));
    }

    // ===================================================================================
    //                                                                       Cursor Select
    //                                                                       =============
    /**
     * Select the cursor by the condition-bean.
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectCursor</span>(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * }, <span style="color: #553000">member</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     ... = <span style="color: #553000">member</span>.getMemberName();
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @param entityLambda The handler of entity row of OriginalCharaGroup. (NotNull)
     */
    public void selectCursor(CBCall<OriginalCharaGroupCB> cbLambda, EntityRowHandler<OriginalCharaGroup> entityLambda) {
        facadeSelectCursor(createCB(cbLambda), entityLambda);
    }

    // ===================================================================================
    //                                                                       Scalar Select
    //                                                                       =============
    /**
     * Select the scalar value derived by a function from uniquely-selected records. <br>
     * You should call a function method after this method called like as follows:
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">selectScalar</span>(Date.class).max(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.specify().<span style="color: #CC4747">column...</span>; <span style="color: #3F7E5E">// required for the function</span>
     *     <span style="color: #553000">cb</span>.query().set...
     * });
     * </pre>
     * @param <RESULT> The type of result.
     * @param resultType The type of result. (NotNull)
     * @return The scalar function object to specify function for scalar value. (NotNull)
     */
    public <RESULT> HpSLSFunction<OriginalCharaGroupCB, RESULT> selectScalar(Class<RESULT> resultType) {
        return facadeScalarSelect(resultType);
    }

    // ===================================================================================
    //                                                                            Sequence
    //                                                                            ========
    @Override
    protected Number doReadNextVal() {
        String msg = "This table is NOT related to sequence: " + asTableDbName();
        throw new UnsupportedOperationException(msg);
    }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    /**
     * Load referrer for the list by the referrer loader.
     * <pre>
     * List&lt;Member&gt; <span style="color: #553000">memberList</span> = <span style="color: #0000C0">memberBhv</span>.selectList(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().set...
     * });
     * memberBhv.<span style="color: #CC4747">load</span>(<span style="color: #553000">memberList</span>, <span style="color: #553000">memberLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">memberLoader</span>.<span style="color: #CC4747">loadPurchase</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">purchaseCB</span>.setupSelect...
     *         <span style="color: #553000">purchaseCB</span>.query().set...
     *         <span style="color: #553000">purchaseCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can also load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(purchaseLoader -&gt; {</span>
     *     <span style="color: #3F7E5E">//    purchaseLoader.loadPurchasePayment(...);</span>
     *     <span style="color: #3F7E5E">//});</span>
     *
     *     <span style="color: #3F7E5E">// you can also pull out foreign table and load its referrer</span>
     *     <span style="color: #3F7E5E">// (setupSelect of the foreign table should be called)</span>
     *     <span style="color: #3F7E5E">//memberLoader.pulloutMemberStatus().loadMemberLogin(...)</span>
     * });
     * <span style="color: #70226C">for</span> (Member member : <span style="color: #553000">memberList</span>) {
     *     List&lt;Purchase&gt; purchaseList = member.<span style="color: #CC4747">getPurchaseList()</span>;
     *     <span style="color: #70226C">for</span> (Purchase purchase : purchaseList) {
     *         ...
     *     }
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has order by FK before callback.
     * @param originalCharaGroupList The entity list of originalCharaGroup. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(List<OriginalCharaGroup> originalCharaGroupList, ReferrerLoaderHandler<LoaderOfOriginalCharaGroup> loaderLambda) {
        xassLRArg(originalCharaGroupList, loaderLambda);
        loaderLambda.handle(new LoaderOfOriginalCharaGroup().ready(originalCharaGroupList, _behaviorSelector));
    }

    /**
     * Load referrer for the entity by the referrer loader.
     * <pre>
     * Member <span style="color: #553000">member</span> = <span style="color: #0000C0">memberBhv</span>.selectEntityWithDeletedCheck(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> <span style="color: #553000">cb</span>.acceptPK(1));
     * <span style="color: #0000C0">memberBhv</span>.<span style="color: #CC4747">load</span>(<span style="color: #553000">member</span>, <span style="color: #553000">memberLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">memberLoader</span>.<span style="color: #CC4747">loadPurchase</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">purchaseCB</span>.setupSelect...
     *         <span style="color: #553000">purchaseCB</span>.query().set...
     *         <span style="color: #553000">purchaseCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can also load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(purchaseLoader -&gt; {</span>
     *     <span style="color: #3F7E5E">//    purchaseLoader.loadPurchasePayment(...);</span>
     *     <span style="color: #3F7E5E">//});</span>
     *
     *     <span style="color: #3F7E5E">// you can also pull out foreign table and load its referrer</span>
     *     <span style="color: #3F7E5E">// (setupSelect of the foreign table should be called)</span>
     *     <span style="color: #3F7E5E">//memberLoader.pulloutMemberStatus().loadMemberLogin(...)</span>
     * });
     * List&lt;Purchase&gt; purchaseList = <span style="color: #553000">member</span>.<span style="color: #CC4747">getPurchaseList()</span>;
     * <span style="color: #70226C">for</span> (Purchase purchase : purchaseList) {
     *     ...
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has order by FK before callback.
     * @param originalCharaGroup The entity of originalCharaGroup. (NotNull)
     * @param loaderLambda The callback to handle the referrer loader for actually loading referrer. (NotNull)
     */
    public void load(OriginalCharaGroup originalCharaGroup, ReferrerLoaderHandler<LoaderOfOriginalCharaGroup> loaderLambda) {
        xassLRArg(originalCharaGroup, loaderLambda);
        loaderLambda.handle(new LoaderOfOriginalCharaGroup().ready(xnewLRAryLs(originalCharaGroup), _behaviorSelector));
    }

    /**
     * Load referrer of originalCharaList by the set-upper of referrer. <br>
     * ORIGINAL_CHARA by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaList'.
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">loadOriginalChara</span>(<span style="color: #553000">originalCharaGroupList</span>, <span style="color: #553000">charaCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">charaCB</span>.setupSelect...
     *     <span style="color: #553000">charaCB</span>.query().set...
     *     <span style="color: #553000">charaCB</span>.query().addOrderBy...
     * }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     * <span style="color: #3F7E5E">//}).withNestedReferrer(referrerList -&gt; {</span>
     * <span style="color: #3F7E5E">//    ...</span>
     * <span style="color: #3F7E5E">//});</span>
     * <span style="color: #70226C">for</span> (OriginalCharaGroup originalCharaGroup : <span style="color: #553000">originalCharaGroupList</span>) {
     *     ... = originalCharaGroup.<span style="color: #CC4747">getOriginalCharaList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setOriginalCharaGroupId_InScope(pkList);
     * cb.query().addOrderBy_OriginalCharaGroupId_Asc();
     * </pre>
     * @param originalCharaGroupList The entity list of originalCharaGroup. (NotNull)
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerListGateway<OriginalChara> loadOriginalChara(List<OriginalCharaGroup> originalCharaGroupList, ReferrerConditionSetupper<OriginalCharaCB> refCBLambda) {
        xassLRArg(originalCharaGroupList, refCBLambda);
        return doLoadOriginalChara(originalCharaGroupList, new LoadReferrerOption<OriginalCharaCB, OriginalChara>().xinit(refCBLambda));
    }

    /**
     * Load referrer of originalCharaList by the set-upper of referrer. <br>
     * ORIGINAL_CHARA by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaList'.
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">loadOriginalChara</span>(<span style="color: #553000">originalCharaGroup</span>, <span style="color: #553000">charaCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">charaCB</span>.setupSelect...
     *     <span style="color: #553000">charaCB</span>.query().set...
     *     <span style="color: #553000">charaCB</span>.query().addOrderBy...
     * }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     * <span style="color: #3F7E5E">//}).withNestedReferrer(referrerList -&gt; {</span>
     * <span style="color: #3F7E5E">//    ...</span>
     * <span style="color: #3F7E5E">//});</span>
     * ... = <span style="color: #553000">originalCharaGroup</span>.<span style="color: #CC4747">getOriginalCharaList()</span>;
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setOriginalCharaGroupId_InScope(pkList);
     * cb.query().addOrderBy_OriginalCharaGroupId_Asc();
     * </pre>
     * @param originalCharaGroup The entity of originalCharaGroup. (NotNull)
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerListGateway<OriginalChara> loadOriginalChara(OriginalCharaGroup originalCharaGroup, ReferrerConditionSetupper<OriginalCharaCB> refCBLambda) {
        xassLRArg(originalCharaGroup, refCBLambda);
        return doLoadOriginalChara(xnewLRLs(originalCharaGroup), new LoadReferrerOption<OriginalCharaCB, OriginalChara>().xinit(refCBLambda));
    }

    protected NestedReferrerListGateway<OriginalChara> doLoadOriginalChara(List<OriginalCharaGroup> originalCharaGroupList, LoadReferrerOption<OriginalCharaCB, OriginalChara> option) {
        return helpLoadReferrerInternally(originalCharaGroupList, option, "originalCharaList");
    }

    /**
     * Load referrer of villageSettingsList by the set-upper of referrer. <br>
     * VILLAGE_SETTINGS by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsList'.
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">loadVillageSettings</span>(<span style="color: #553000">originalCharaGroupList</span>, <span style="color: #553000">settingsCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">settingsCB</span>.setupSelect...
     *     <span style="color: #553000">settingsCB</span>.query().set...
     *     <span style="color: #553000">settingsCB</span>.query().addOrderBy...
     * }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     * <span style="color: #3F7E5E">//}).withNestedReferrer(referrerList -&gt; {</span>
     * <span style="color: #3F7E5E">//    ...</span>
     * <span style="color: #3F7E5E">//});</span>
     * <span style="color: #70226C">for</span> (OriginalCharaGroup originalCharaGroup : <span style="color: #553000">originalCharaGroupList</span>) {
     *     ... = originalCharaGroup.<span style="color: #CC4747">getVillageSettingsList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setOriginalCharaGroupId_InScope(pkList);
     * cb.query().addOrderBy_OriginalCharaGroupId_Asc();
     * </pre>
     * @param originalCharaGroupList The entity list of originalCharaGroup. (NotNull)
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerListGateway<VillageSettings> loadVillageSettings(List<OriginalCharaGroup> originalCharaGroupList, ReferrerConditionSetupper<VillageSettingsCB> refCBLambda) {
        xassLRArg(originalCharaGroupList, refCBLambda);
        return doLoadVillageSettings(originalCharaGroupList, new LoadReferrerOption<VillageSettingsCB, VillageSettings>().xinit(refCBLambda));
    }

    /**
     * Load referrer of villageSettingsList by the set-upper of referrer. <br>
     * VILLAGE_SETTINGS by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsList'.
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">loadVillageSettings</span>(<span style="color: #553000">originalCharaGroup</span>, <span style="color: #553000">settingsCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">settingsCB</span>.setupSelect...
     *     <span style="color: #553000">settingsCB</span>.query().set...
     *     <span style="color: #553000">settingsCB</span>.query().addOrderBy...
     * }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     * <span style="color: #3F7E5E">//}).withNestedReferrer(referrerList -&gt; {</span>
     * <span style="color: #3F7E5E">//    ...</span>
     * <span style="color: #3F7E5E">//});</span>
     * ... = <span style="color: #553000">originalCharaGroup</span>.<span style="color: #CC4747">getVillageSettingsList()</span>;
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setOriginalCharaGroupId_InScope(pkList);
     * cb.query().addOrderBy_OriginalCharaGroupId_Asc();
     * </pre>
     * @param originalCharaGroup The entity of originalCharaGroup. (NotNull)
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerListGateway<VillageSettings> loadVillageSettings(OriginalCharaGroup originalCharaGroup, ReferrerConditionSetupper<VillageSettingsCB> refCBLambda) {
        xassLRArg(originalCharaGroup, refCBLambda);
        return doLoadVillageSettings(xnewLRLs(originalCharaGroup), new LoadReferrerOption<VillageSettingsCB, VillageSettings>().xinit(refCBLambda));
    }

    protected NestedReferrerListGateway<VillageSettings> doLoadVillageSettings(List<OriginalCharaGroup> originalCharaGroupList, LoadReferrerOption<VillageSettingsCB, VillageSettings> option) {
        return helpLoadReferrerInternally(originalCharaGroupList, option, "villageSettingsList");
    }

    // ===================================================================================
    //                                                                   Pull out Relation
    //                                                                   =================
    // ===================================================================================
    //                                                                      Extract Column
    //                                                                      ==============
    /**
     * Extract the value list of (single) primary key originalCharaGroupId.
     * @param originalCharaGroupList The list of originalCharaGroup. (NotNull, EmptyAllowed)
     * @return The list of the column value. (NotNull, EmptyAllowed, NotNullElement)
     */
    public List<Integer> extractOriginalCharaGroupIdList(List<OriginalCharaGroup> originalCharaGroupList)
    { return helpExtractListInternally(originalCharaGroupList, "originalCharaGroupId"); }

    // ===================================================================================
    //                                                                       Entity Update
    //                                                                       =============
    /**
     * Insert the entity modified-only. (DefaultConstraintsEnabled)
     * <pre>
     * OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * originalCharaGroup.setFoo...(value);
     * originalCharaGroup.setBar...(value);
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.set...;</span>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">insert</span>(originalCharaGroup);
     * ... = originalCharaGroup.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * <p>While, when the entity is created by select, all columns are registered.</p>
     * @param originalCharaGroup The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insert(OriginalCharaGroup originalCharaGroup) {
        doInsert(originalCharaGroup, null);
    }

    /**
     * Update the entity modified-only. (ZeroUpdateException, NonExclusiveControl) <br>
     * By PK as default, and also you can update by unique keys using entity's uniqueOf().
     * <pre>
     * OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     * originalCharaGroup.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * originalCharaGroup.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.set...;</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * originalCharaGroup.<span style="color: #CC4747">setVersionNo</span>(value);
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">update</span>(originalCharaGroup);
     * </pre>
     * @param originalCharaGroup The entity of update. (NotNull, PrimaryKeyNotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void update(OriginalCharaGroup originalCharaGroup) {
        doUpdate(originalCharaGroup, null);
    }

    /**
     * Insert or update the entity modified-only. (DefaultConstraintsEnabled, NonExclusiveControl) <br>
     * if (the entity has no PK) { insert() } else { update(), but no data, insert() } <br>
     * <p><span style="color: #994747; font-size: 120%">Also you can update by unique keys using entity's uniqueOf().</span></p>
     * @param originalCharaGroup The entity of insert or update. (NotNull, ...depends on insert or update)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void insertOrUpdate(OriginalCharaGroup originalCharaGroup) {
        doInsertOrUpdate(originalCharaGroup, null, null);
    }

    /**
     * Delete the entity. (ZeroUpdateException, NonExclusiveControl) <br>
     * By PK as default, and also you can delete by unique keys using entity's uniqueOf().
     * <pre>
     * OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     * originalCharaGroup.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * originalCharaGroup.<span style="color: #CC4747">setVersionNo</span>(value);
     * <span style="color: #70226C">try</span> {
     *     <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">delete</span>(originalCharaGroup);
     * } <span style="color: #70226C">catch</span> (EntityAlreadyUpdatedException e) { <span style="color: #3F7E5E">// if concurrent update</span>
     *     ...
     * }
     * </pre>
     * @param originalCharaGroup The entity of delete. (NotNull, PrimaryKeyNotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     */
    public void delete(OriginalCharaGroup originalCharaGroup) {
        doDelete(originalCharaGroup, null);
    }

    // ===================================================================================
    //                                                                        Batch Update
    //                                                                        ============
    /**
     * Batch-insert the entity list modified-only of same-set columns. (DefaultConstraintsEnabled) <br>
     * This method uses executeBatch() of java.sql.PreparedStatement. <br>
     * <p><span style="color: #CC4747; font-size: 120%">The columns of least common multiple are registered like this:</span></p>
     * <pre>
     * <span style="color: #70226C">for</span> (... : ...) {
     *     OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     *     originalCharaGroup.setFooName("foo");
     *     <span style="color: #70226C">if</span> (...) {
     *         originalCharaGroup.setFooPrice(123);
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are registered</span>
     *     <span style="color: #3F7E5E">// FOO_PRICE not-called in any entities are registered as null without default value</span>
     *     <span style="color: #3F7E5E">// columns not-called in all entities are registered as null or default value</span>
     *     originalCharaGroupList.add(originalCharaGroup);
     * }
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">batchInsert</span>(originalCharaGroupList);
     * </pre>
     * <p>While, when the entities are created by select, all columns are registered.</p>
     * <p>And if the table has an identity, entities after the process don't have incremented values.
     * (When you use the (normal) insert(), you can get the incremented value from your entity)</p>
     * @param originalCharaGroupList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNullAllowed: when auto-increment)
     * @return The array of inserted count. (NotNull, EmptyAllowed)
     */
    public int[] batchInsert(List<OriginalCharaGroup> originalCharaGroupList) {
        return doBatchInsert(originalCharaGroupList, null);
    }

    /**
     * Batch-update the entity list modified-only of same-set columns. (NonExclusiveControl) <br>
     * This method uses executeBatch() of java.sql.PreparedStatement. <br>
     * <span style="color: #CC4747; font-size: 120%">You should specify same-set columns to all entities like this:</span>
     * <pre>
     * for (... : ...) {
     *     OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     *     originalCharaGroup.setFooName("foo");
     *     <span style="color: #70226C">if</span> (...) {
     *         originalCharaGroup.setFooPrice(123);
     *     } <span style="color: #70226C">else</span> {
     *         originalCharaGroup.setFooPrice(null); <span style="color: #3F7E5E">// updated as null</span>
     *         <span style="color: #3F7E5E">//originalCharaGroup.setFooDate(...); // *not allowed, fragmented</span>
     *     }
     *     <span style="color: #3F7E5E">// FOO_NAME and FOO_PRICE (and record meta columns) are updated</span>
     *     <span style="color: #3F7E5E">// (others are not updated: their values are kept)</span>
     *     originalCharaGroupList.add(originalCharaGroup);
     * }
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">batchUpdate</span>(originalCharaGroupList);
     * </pre>
     * @param originalCharaGroupList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchUpdate(List<OriginalCharaGroup> originalCharaGroupList) {
        return doBatchUpdate(originalCharaGroupList, null);
    }

    /**
     * Batch-delete the entity list. (NonExclusiveControl) <br>
     * This method uses executeBatch() of java.sql.PreparedStatement.
     * @param originalCharaGroupList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     */
    public int[] batchDelete(List<OriginalCharaGroup> originalCharaGroupList) {
        return doBatchDelete(originalCharaGroupList, null);
    }

    // ===================================================================================
    //                                                                        Query Update
    //                                                                        ============
    /**
     * Insert the several entities by query (modified-only for fixed value).
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">queryInsert</span>(new QueryInsertSetupper&lt;OriginalCharaGroup, OriginalCharaGroupCB&gt;() {
     *     public ConditionBean setup(OriginalCharaGroup entity, OriginalCharaGroupCB intoCB) {
     *         FooCB cb = FooCB();
     *         cb.setupSelect_Bar();
     *
     *         <span style="color: #3F7E5E">// mapping</span>
     *         intoCB.specify().columnMyName().mappedFrom(cb.specify().columnFooName());
     *         intoCB.specify().columnMyCount().mappedFrom(cb.specify().columnFooCount());
     *         intoCB.specify().columnMyDate().mappedFrom(cb.specify().specifyBar().columnBarDate());
     *         entity.setMyFixedValue("foo"); <span style="color: #3F7E5E">// fixed value</span>
     *         <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     *         <span style="color: #3F7E5E">//entity.setRegisterUser(value);</span>
     *         <span style="color: #3F7E5E">//entity.set...;</span>
     *         <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     *         <span style="color: #3F7E5E">//entity.setVersionNo(value);</span>
     *
     *         return cb;
     *     }
     * });
     * </pre>
     * @param manyArgLambda The callback to set up query-insert. (NotNull)
     * @return The inserted count.
     */
    public int queryInsert(QueryInsertSetupper<OriginalCharaGroup, OriginalCharaGroupCB> manyArgLambda) {
        return doQueryInsert(manyArgLambda, null);
    }

    /**
     * Update the several entities by query non-strictly modified-only. (NonExclusiveControl)
     * <pre>
     * OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.setPK...(value);</span>
     * originalCharaGroup.setFoo...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set values of common columns</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.setRegisterUser(value);</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.set...;</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.setVersionNo(value);</span>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">queryUpdate</span>(originalCharaGroup, <span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().setFoo...
     * });
     * </pre>
     * @param originalCharaGroup The entity that contains update values. (NotNull, PrimaryKeyNullAllowed)
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @return The updated count.
     * @throws NonQueryUpdateNotAllowedException When the query has no condition.
     */
    public int queryUpdate(OriginalCharaGroup originalCharaGroup, CBCall<OriginalCharaGroupCB> cbLambda) {
        return doQueryUpdate(originalCharaGroup, createCB(cbLambda), null);
    }

    /**
     * Delete the several entities by query. (NonExclusiveControl)
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">queryDelete</span>(originalCharaGroup, <span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().setFoo...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @return The deleted count.
     * @throws NonQueryDeleteNotAllowedException When the query has no condition.
     */
    public int queryDelete(CBCall<OriginalCharaGroupCB> cbLambda) {
        return doQueryDelete(createCB(cbLambda), null);
    }

    // ===================================================================================
    //                                                                      Varying Update
    //                                                                      ==============
    // -----------------------------------------------------
    //                                         Entity Update
    //                                         -------------
    /**
     * Insert the entity with varying requests. <br>
     * For example, disableCommonColumnAutoSetup(), disablePrimaryKeyIdentity(). <br>
     * Other specifications are same as insert(entity).
     * <pre>
     * OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     * <span style="color: #3F7E5E">// if auto-increment, you don't need to set the PK value</span>
     * originalCharaGroup.setFoo...(value);
     * originalCharaGroup.setBar...(value);
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">varyingInsert</span>(originalCharaGroup, <span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #3F7E5E">// you can insert by your values for common columns</span>
     *     <span style="color: #553000">op</span>.disableCommonColumnAutoSetup();
     * });
     * ... = originalCharaGroup.getPK...(); <span style="color: #3F7E5E">// if auto-increment, you can get the value after</span>
     * </pre>
     * @param originalCharaGroup The entity of insert. (NotNull, PrimaryKeyNullAllowed: when auto-increment)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsert(OriginalCharaGroup originalCharaGroup, WritableOptionCall<OriginalCharaGroupCB, InsertOption<OriginalCharaGroupCB>> opLambda) {
        doInsert(originalCharaGroup, createInsertOption(opLambda));
    }

    /**
     * Update the entity with varying requests modified-only. (ZeroUpdateException, NonExclusiveControl) <br>
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification), disableCommonColumnAutoSetup(). <br>
     * Other specifications are same as update(entity).
     * <pre>
     * OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     * originalCharaGroup.setPK...(value); <span style="color: #3F7E5E">// required</span>
     * originalCharaGroup.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// if exclusive control, the value of concurrency column is required</span>
     * originalCharaGroup.<span style="color: #CC4747">setVersionNo</span>(value);
     * <span style="color: #3F7E5E">// you can update by self calculation values</span>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">varyingUpdate</span>(originalCharaGroup, <span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.self(<span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">cb</span>.specify().<span style="color: #CC4747">columnXxxCount()</span>;
     *     }).plus(1); <span style="color: #3F7E5E">// XXX_COUNT = XXX_COUNT + 1</span>
     * });
     * </pre>
     * @param originalCharaGroup The entity of update. (NotNull, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingUpdate(OriginalCharaGroup originalCharaGroup, WritableOptionCall<OriginalCharaGroupCB, UpdateOption<OriginalCharaGroupCB>> opLambda) {
        doUpdate(originalCharaGroup, createUpdateOption(opLambda));
    }

    /**
     * Insert or update the entity with varying requests. (ExclusiveControl: when update) <br>
     * Other specifications are same as insertOrUpdate(entity).
     * @param originalCharaGroup The entity of insert or update. (NotNull)
     * @param insertOpLambda The callback for option of insert for varying requests. (NotNull)
     * @param updateOpLambda The callback for option of update for varying requests. (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     * @throws EntityAlreadyExistsException When the entity already exists. (unique constraint violation)
     */
    public void varyingInsertOrUpdate(OriginalCharaGroup originalCharaGroup, WritableOptionCall<OriginalCharaGroupCB, InsertOption<OriginalCharaGroupCB>> insertOpLambda, WritableOptionCall<OriginalCharaGroupCB, UpdateOption<OriginalCharaGroupCB>> updateOpLambda) {
        doInsertOrUpdate(originalCharaGroup, createInsertOption(insertOpLambda), createUpdateOption(updateOpLambda));
    }

    /**
     * Delete the entity with varying requests. (ZeroUpdateException, NonExclusiveControl) <br>
     * Now a valid option does not exist. <br>
     * Other specifications are same as delete(entity).
     * @param originalCharaGroup The entity of delete. (NotNull, PrimaryKeyNotNull, ConcurrencyColumnNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @throws EntityAlreadyDeletedException When the entity has already been deleted. (not found)
     * @throws EntityDuplicatedException When the entity has been duplicated.
     */
    public void varyingDelete(OriginalCharaGroup originalCharaGroup, WritableOptionCall<OriginalCharaGroupCB, DeleteOption<OriginalCharaGroupCB>> opLambda) {
        doDelete(originalCharaGroup, createDeleteOption(opLambda));
    }

    // -----------------------------------------------------
    //                                          Batch Update
    //                                          ------------
    /**
     * Batch-insert the list with varying requests. <br>
     * For example, disableCommonColumnAutoSetup()
     * , disablePrimaryKeyIdentity(), limitBatchInsertLogging(). <br>
     * Other specifications are same as batchInsert(entityList).
     * @param originalCharaGroupList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchInsert(List<OriginalCharaGroup> originalCharaGroupList, WritableOptionCall<OriginalCharaGroupCB, InsertOption<OriginalCharaGroupCB>> opLambda) {
        return doBatchInsert(originalCharaGroupList, createInsertOption(opLambda));
    }

    /**
     * Batch-update the list with varying requests. <br>
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), limitBatchUpdateLogging(). <br>
     * Other specifications are same as batchUpdate(entityList).
     * @param originalCharaGroupList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The array of updated count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchUpdate(List<OriginalCharaGroup> originalCharaGroupList, WritableOptionCall<OriginalCharaGroupCB, UpdateOption<OriginalCharaGroupCB>> opLambda) {
        return doBatchUpdate(originalCharaGroupList, createUpdateOption(opLambda));
    }

    /**
     * Batch-delete the list with varying requests. <br>
     * For example, limitBatchDeleteLogging(). <br>
     * Other specifications are same as batchDelete(entityList).
     * @param originalCharaGroupList The list of the entity. (NotNull, EmptyAllowed, PrimaryKeyNotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The array of deleted count. (NotNull, EmptyAllowed)
     */
    public int[] varyingBatchDelete(List<OriginalCharaGroup> originalCharaGroupList, WritableOptionCall<OriginalCharaGroupCB, DeleteOption<OriginalCharaGroupCB>> opLambda) {
        return doBatchDelete(originalCharaGroupList, createDeleteOption(opLambda));
    }

    // -----------------------------------------------------
    //                                          Query Update
    //                                          ------------
    /**
     * Insert the several entities by query with varying requests (modified-only for fixed value). <br>
     * For example, disableCommonColumnAutoSetup(), disablePrimaryKeyIdentity(). <br>
     * Other specifications are same as queryInsert(entity, setupper).
     * @param manyArgLambda The set-upper of query-insert. (NotNull)
     * @param opLambda The callback for option of insert for varying requests. (NotNull)
     * @return The inserted count.
     */
    public int varyingQueryInsert(QueryInsertSetupper<OriginalCharaGroup, OriginalCharaGroupCB> manyArgLambda, WritableOptionCall<OriginalCharaGroupCB, InsertOption<OriginalCharaGroupCB>> opLambda) {
        return doQueryInsert(manyArgLambda, createInsertOption(opLambda));
    }

    /**
     * Update the several entities by query with varying requests non-strictly modified-only. {NonExclusiveControl} <br>
     * For example, self(selfCalculationSpecification), specify(updateColumnSpecification)
     * , disableCommonColumnAutoSetup(), allowNonQueryUpdate(). <br>
     * Other specifications are same as queryUpdate(entity, cb).
     * <pre>
     * <span style="color: #3F7E5E">// ex) you can update by self calculation values</span>
     * OriginalCharaGroup originalCharaGroup = <span style="color: #70226C">new</span> OriginalCharaGroup();
     * <span style="color: #3F7E5E">// you don't need to set PK value</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.setPK...(value);</span>
     * originalCharaGroup.setOther...(value); <span style="color: #3F7E5E">// you should set only modified columns</span>
     * <span style="color: #3F7E5E">// you don't need to set a value of concurrency column</span>
     * <span style="color: #3F7E5E">// (auto-increment for version number is valid though non-exclusive control)</span>
     * <span style="color: #3F7E5E">//originalCharaGroup.setVersionNo(value);</span>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">varyingQueryUpdate</span>(originalCharaGroup, <span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().setFoo...
     * }, <span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.self(<span style="color: #553000">colCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">colCB</span>.specify().<span style="color: #CC4747">columnFooCount()</span>;
     *     }).plus(1); <span style="color: #3F7E5E">// FOO_COUNT = FOO_COUNT + 1</span>
     * });
     * </pre>
     * @param originalCharaGroup The entity that contains update values. (NotNull) {PrimaryKeyNotRequired}
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @param opLambda The callback for option of update for varying requests. (NotNull)
     * @return The updated count.
     * @throws NonQueryUpdateNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryUpdate(OriginalCharaGroup originalCharaGroup, CBCall<OriginalCharaGroupCB> cbLambda, WritableOptionCall<OriginalCharaGroupCB, UpdateOption<OriginalCharaGroupCB>> opLambda) {
        return doQueryUpdate(originalCharaGroup, createCB(cbLambda), createUpdateOption(opLambda));
    }

    /**
     * Delete the several entities by query with varying requests non-strictly. <br>
     * For example, allowNonQueryDelete(). <br>
     * Other specifications are same as queryDelete(cb).
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #CC4747">queryDelete</span>(originalCharaGroup, <span style="color: #553000">cb</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">cb</span>.query().setFoo...
     * }, <span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>...
     * });
     * </pre>
     * @param cbLambda The callback for condition-bean of OriginalCharaGroup. (NotNull)
     * @param opLambda The callback for option of delete for varying requests. (NotNull)
     * @return The deleted count.
     * @throws NonQueryDeleteNotAllowedException When the query has no condition (if not allowed).
     */
    public int varyingQueryDelete(CBCall<OriginalCharaGroupCB> cbLambda, WritableOptionCall<OriginalCharaGroupCB, DeleteOption<OriginalCharaGroupCB>> opLambda) {
        return doQueryDelete(createCB(cbLambda), createDeleteOption(opLambda));
    }

    // ===================================================================================
    //                                                                          OutsideSql
    //                                                                          ==========
    /**
     * Prepare the all facade executor of outside-SQL to execute it.
     * <pre>
     * <span style="color: #3F7E5E">// main style</span>
     * originalCharaGroupBhv.outideSql().selectEntity(pmb); <span style="color: #3F7E5E">// optional</span>
     * originalCharaGroupBhv.outideSql().selectList(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * originalCharaGroupBhv.outideSql().selectPage(pmb); <span style="color: #3F7E5E">// PagingResultBean</span>
     * originalCharaGroupBhv.outideSql().selectPagedListOnly(pmb); <span style="color: #3F7E5E">// ListResultBean</span>
     * originalCharaGroupBhv.outideSql().selectCursor(pmb, handler); <span style="color: #3F7E5E">// (by handler)</span>
     * originalCharaGroupBhv.outideSql().execute(pmb); <span style="color: #3F7E5E">// int (updated count)</span>
     * originalCharaGroupBhv.outideSql().call(pmb); <span style="color: #3F7E5E">// void (pmb has OUT parameters)</span>
     *
     * <span style="color: #3F7E5E">// traditional style</span>
     * originalCharaGroupBhv.outideSql().traditionalStyle().selectEntity(path, pmb, entityType);
     * originalCharaGroupBhv.outideSql().traditionalStyle().selectList(path, pmb, entityType);
     * originalCharaGroupBhv.outideSql().traditionalStyle().selectPage(path, pmb, entityType);
     * originalCharaGroupBhv.outideSql().traditionalStyle().selectPagedListOnly(path, pmb, entityType);
     * originalCharaGroupBhv.outideSql().traditionalStyle().selectCursor(path, pmb, handler);
     * originalCharaGroupBhv.outideSql().traditionalStyle().execute(path, pmb);
     *
     * <span style="color: #3F7E5E">// options</span>
     * originalCharaGroupBhv.outideSql().removeBlockComment().selectList()
     * originalCharaGroupBhv.outideSql().removeLineComment().selectList()
     * originalCharaGroupBhv.outideSql().formatSql().selectList()
     * </pre>
     * <p>The invoker of behavior command should be not null when you call this method.</p>
     * @return The new-created all facade executor of outside-SQL. (NotNull)
     */
    public OutsideSqlAllFacadeExecutor<OriginalCharaGroupBhv> outsideSql() {
        return doOutsideSql();
    }

    // ===================================================================================
    //                                                                         Type Helper
    //                                                                         ===========
    protected Class<? extends OriginalCharaGroup> typeOfSelectedEntity() { return OriginalCharaGroup.class; }
    protected Class<OriginalCharaGroup> typeOfHandlingEntity() { return OriginalCharaGroup.class; }
    protected Class<OriginalCharaGroupCB> typeOfHandlingConditionBean() { return OriginalCharaGroupCB.class; }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    @Override
    @javax.annotation.Resource(name="behaviorCommandInvoker")
    public void setBehaviorCommandInvoker(BehaviorCommandInvoker behaviorCommandInvoker) {
        super.setBehaviorCommandInvoker(behaviorCommandInvoker);
    }

    @Override
    @javax.annotation.Resource(name="behaviorSelector")
    public void setBehaviorSelector(BehaviorSelector behaviorSelector) {
        super.setBehaviorSelector(behaviorSelector);
    }

    @Override
    @javax.annotation.Resource(name="commonColumnAutoSetupper")
    public void setCommonColumnAutoSetupper(CommonColumnAutoSetupper commonColumnAutoSetupper) {
        super.setCommonColumnAutoSetupper(commonColumnAutoSetupper);
    }
}
