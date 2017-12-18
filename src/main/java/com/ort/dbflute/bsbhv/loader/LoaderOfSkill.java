package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of SKILL as TABLE. <br>
 * <pre>
 * [primary key]
 *     SKILL_CODE
 *
 * [column]
 *     SKILL_CODE, SKILL_NAME, CAMP_CODE
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
 *     CAMP
 *
 * [referrer table]
 *     VILLAGE_PLAYER
 *
 * [foreign property]
 *     camp
 *
 * [referrer property]
 *     villagePlayerList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfSkill {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<Skill> _selectedList;
    protected BehaviorSelector _selector;
    protected SkillBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfSkill ready(List<Skill> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected SkillBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(SkillBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<VillagePlayer> _referrerVillagePlayer;

    /**
     * Load referrer of villagePlayerList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerList'.
     * <pre>
     * <span style="color: #0000C0">skillBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">skillList</span>, <span style="color: #553000">skillLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">skillLoader</span>.<span style="color: #CC4747">loadVillagePlayer</span>(<span style="color: #553000">playerCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">playerCB</span>.setupSelect...
     *         <span style="color: #553000">playerCB</span>.query().set...
     *         <span style="color: #553000">playerCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">playerLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    playerLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Skill skill : <span style="color: #553000">skillList</span>) {
     *     ... = skill.<span style="color: #CC4747">getVillagePlayerList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setSkillCode_InScope(pkList);
     * cb.query().addOrderBy_SkillCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayer> loadVillagePlayer(ReferrerConditionSetupper<VillagePlayerCB> refCBLambda) {
        myBhv().loadVillagePlayer(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayer = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayer().ready(_referrerVillagePlayer, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfCamp _foreignCampLoader;
    public LoaderOfCamp pulloutCamp() {
        if (_foreignCampLoader == null)
        { _foreignCampLoader = new LoaderOfCamp().ready(myBhv().pulloutCamp(_selectedList), _selector); }
        return _foreignCampLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<Skill> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
