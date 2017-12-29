package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of VILLAGE_DAY as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_ID, DAY
 *
 * [column]
 *     VILLAGE_ID, DAY, DAYCHANGE_DATETIME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VILLAGE
 *
 * [referrer table]
 *     ABILITY, FOOTSTEP, MESSAGE, VOTE
 *
 * [foreign property]
 *     village
 *
 * [referrer property]
 *     abilityList, footstepList, messageList, voteList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillageDay {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillageDay> _selectedList;
    protected BehaviorSelector _selector;
    protected VillageDayBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillageDay ready(List<VillageDay> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillageDayBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillageDayBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<Ability> _referrerAbility;

    /**
     * Load referrer of abilityList by the set-upper of referrer. <br>
     * ABILITY by VILLAGE_ID, DAY, named 'abilityList'.
     * <pre>
     * <span style="color: #0000C0">villageDayBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villageDayList</span>, <span style="color: #553000">dayLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">dayLoader</span>.<span style="color: #CC4747">loadAbility</span>(<span style="color: #553000">abilityCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">abilityCB</span>.setupSelect...
     *         <span style="color: #553000">abilityCB</span>.query().set...
     *         <span style="color: #553000">abilityCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">abilityLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    abilityLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillageDay villageDay : <span style="color: #553000">villageDayList</span>) {
     *     ... = villageDay.<span style="color: #CC4747">getAbilityList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().set[ForeignKey]_InScope(pkList);
     * cb.query().addOrderBy_[ForeignKey]_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfAbility> loadAbility(ReferrerConditionSetupper<AbilityCB> refCBLambda) {
        myBhv().loadAbility(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerAbility = refLs);
        return hd -> hd.handle(new LoaderOfAbility().ready(_referrerAbility, _selector));
    }

    protected List<Footstep> _referrerFootstep;

    /**
     * Load referrer of footstepList by the set-upper of referrer. <br>
     * FOOTSTEP by VILLAGE_ID, DAY, named 'footstepList'.
     * <pre>
     * <span style="color: #0000C0">villageDayBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villageDayList</span>, <span style="color: #553000">dayLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">dayLoader</span>.<span style="color: #CC4747">loadFootstep</span>(<span style="color: #553000">footstepCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">footstepCB</span>.setupSelect...
     *         <span style="color: #553000">footstepCB</span>.query().set...
     *         <span style="color: #553000">footstepCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">footstepLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    footstepLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillageDay villageDay : <span style="color: #553000">villageDayList</span>) {
     *     ... = villageDay.<span style="color: #CC4747">getFootstepList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().set[ForeignKey]_InScope(pkList);
     * cb.query().addOrderBy_[ForeignKey]_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfFootstep> loadFootstep(ReferrerConditionSetupper<FootstepCB> refCBLambda) {
        myBhv().loadFootstep(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerFootstep = refLs);
        return hd -> hd.handle(new LoaderOfFootstep().ready(_referrerFootstep, _selector));
    }

    protected List<Message> _referrerMessage;

    /**
     * Load referrer of messageList by the set-upper of referrer. <br>
     * MESSAGE by VILLAGE_ID, DAY, named 'messageList'.
     * <pre>
     * <span style="color: #0000C0">villageDayBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villageDayList</span>, <span style="color: #553000">dayLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">dayLoader</span>.<span style="color: #CC4747">loadMessage</span>(<span style="color: #553000">messageCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">messageCB</span>.setupSelect...
     *         <span style="color: #553000">messageCB</span>.query().set...
     *         <span style="color: #553000">messageCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">messageLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    messageLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillageDay villageDay : <span style="color: #553000">villageDayList</span>) {
     *     ... = villageDay.<span style="color: #CC4747">getMessageList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().set[ForeignKey]_InScope(pkList);
     * cb.query().addOrderBy_[ForeignKey]_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessage> loadMessage(ReferrerConditionSetupper<MessageCB> refCBLambda) {
        myBhv().loadMessage(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessage = refLs);
        return hd -> hd.handle(new LoaderOfMessage().ready(_referrerMessage, _selector));
    }

    protected List<Vote> _referrerVote;

    /**
     * Load referrer of voteList by the set-upper of referrer. <br>
     * VOTE by VILLAGE_ID, DAY, named 'voteList'.
     * <pre>
     * <span style="color: #0000C0">villageDayBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villageDayList</span>, <span style="color: #553000">dayLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">dayLoader</span>.<span style="color: #CC4747">loadVote</span>(<span style="color: #553000">voteCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">voteCB</span>.setupSelect...
     *         <span style="color: #553000">voteCB</span>.query().set...
     *         <span style="color: #553000">voteCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">voteLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    voteLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillageDay villageDay : <span style="color: #553000">villageDayList</span>) {
     *     ... = villageDay.<span style="color: #CC4747">getVoteList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().set[ForeignKey]_InScope(pkList);
     * cb.query().addOrderBy_[ForeignKey]_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVote> loadVote(ReferrerConditionSetupper<VoteCB> refCBLambda) {
        myBhv().loadVote(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVote = refLs);
        return hd -> hd.handle(new LoaderOfVote().ready(_referrerVote, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfVillage _foreignVillageLoader;
    public LoaderOfVillage pulloutVillage() {
        if (_foreignVillageLoader == null)
        { _foreignVillageLoader = new LoaderOfVillage().ready(myBhv().pulloutVillage(_selectedList), _selector); }
        return _foreignVillageLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillageDay> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
