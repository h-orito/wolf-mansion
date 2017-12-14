package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of chara as TABLE. <br>
 * <pre>
 * [primary key]
 *     CHARA_ID
 *
 * [column]
 *     CHARA_ID, CHARA_NAME, CHARA_GROUP_ID, CHARA_IMG_URL
 *
 * [sequence]
 *     
 *
 * [identity]
 *     CHARA_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     chara_group
 *
 * [referrer table]
 *     village_player
 *
 * [foreign property]
 *     charaGroup
 *
 * [referrer property]
 *     villagePlayerList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfChara {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<Chara> _selectedList;
    protected BehaviorSelector _selector;
    protected CharaBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfChara ready(List<Chara> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected CharaBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(CharaBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<VillagePlayer> _referrerVillagePlayer;

    /**
     * Load referrer of villagePlayerList by the set-upper of referrer. <br>
     * village_player by CHARA_ID, named 'villagePlayerList'.
     * <pre>
     * <span style="color: #0000C0">charaBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">charaList</span>, <span style="color: #553000">charaLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">charaLoader</span>.<span style="color: #CC4747">loadVillagePlayer</span>(<span style="color: #553000">playerCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">playerCB</span>.setupSelect...
     *         <span style="color: #553000">playerCB</span>.query().set...
     *         <span style="color: #553000">playerCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">playerLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    playerLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Chara chara : <span style="color: #553000">charaList</span>) {
     *     ... = chara.<span style="color: #CC4747">getVillagePlayerList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setCharaId_InScope(pkList);
     * cb.query().addOrderBy_CharaId_Asc();
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
    protected LoaderOfCharaGroup _foreignCharaGroupLoader;
    public LoaderOfCharaGroup pulloutCharaGroup() {
        if (_foreignCharaGroupLoader == null)
        { _foreignCharaGroupLoader = new LoaderOfCharaGroup().ready(myBhv().pulloutCharaGroup(_selectedList), _selector); }
        return _foreignCharaGroupLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<Chara> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
