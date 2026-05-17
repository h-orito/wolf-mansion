package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of VILLAGE_PLAYER_STATUS_TYPE as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_PLAYER_STATUS_TYPE_CODE
 *
 * [column]
 *     VILLAGE_PLAYER_STATUS_TYPE_CODE, VILLAGE_PLAYER_STATUS_TYPE_NAME
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
 *     
 *
 * [referrer table]
 *     VILLAGE_PLAYER_STATUS
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     villagePlayerStatusList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillagePlayerStatusType {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillagePlayerStatusType> _selectedList;
    protected BehaviorSelector _selector;
    protected VillagePlayerStatusTypeBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillagePlayerStatusType ready(List<VillagePlayerStatusType> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillagePlayerStatusTypeBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillagePlayerStatusTypeBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<VillagePlayerStatus> _referrerVillagePlayerStatus;

    /**
     * Load referrer of villagePlayerStatusList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerStatusTypeBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerStatusTypeList</span>, <span style="color: #553000">typeLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">typeLoader</span>.<span style="color: #CC4747">loadVillagePlayerStatus</span>(<span style="color: #553000">statusCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">statusCB</span>.setupSelect...
     *         <span style="color: #553000">statusCB</span>.query().set...
     *         <span style="color: #553000">statusCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">statusLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    statusLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayerStatusType villagePlayerStatusType : <span style="color: #553000">villagePlayerStatusTypeList</span>) {
     *     ... = villagePlayerStatusType.<span style="color: #CC4747">getVillagePlayerStatusList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerStatusCode_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerStatusCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayerStatus> loadVillagePlayerStatus(ReferrerConditionSetupper<VillagePlayerStatusCB> refCBLambda) {
        myBhv().loadVillagePlayerStatus(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayerStatus = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayerStatus().ready(_referrerVillagePlayerStatus, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillagePlayerStatusType> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
