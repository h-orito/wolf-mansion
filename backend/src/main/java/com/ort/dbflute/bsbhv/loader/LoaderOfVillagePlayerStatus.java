package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_PLAYER_STATUS as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_PLAYER_STATUS_ID
 *
 * [column]
 *     VILLAGE_PLAYER_STATUS_ID, VILLAGE_PLAYER_ID, TO_VILLAGE_PLAYER_ID, VILLAGE_PLAYER_STATUS_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_STATUS_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VILLAGE_PLAYER, VILLAGE_PLAYER_STATUS_TYPE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     villagePlayerByToVillagePlayerId, villagePlayerByVillagePlayerId, villagePlayerStatusType
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillagePlayerStatus {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillagePlayerStatus> _selectedList;
    protected BehaviorSelector _selector;
    protected VillagePlayerStatusBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillagePlayerStatus ready(List<VillagePlayerStatus> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillagePlayerStatusBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillagePlayerStatusBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfVillagePlayer _foreignVillagePlayerByToVillagePlayerIdLoader;
    public LoaderOfVillagePlayer pulloutVillagePlayerByToVillagePlayerId() {
        if (_foreignVillagePlayerByToVillagePlayerIdLoader == null)
        { _foreignVillagePlayerByToVillagePlayerIdLoader = new LoaderOfVillagePlayer().ready(myBhv().pulloutVillagePlayerByToVillagePlayerId(_selectedList), _selector); }
        return _foreignVillagePlayerByToVillagePlayerIdLoader;
    }

    protected LoaderOfVillagePlayer _foreignVillagePlayerByVillagePlayerIdLoader;
    public LoaderOfVillagePlayer pulloutVillagePlayerByVillagePlayerId() {
        if (_foreignVillagePlayerByVillagePlayerIdLoader == null)
        { _foreignVillagePlayerByVillagePlayerIdLoader = new LoaderOfVillagePlayer().ready(myBhv().pulloutVillagePlayerByVillagePlayerId(_selectedList), _selector); }
        return _foreignVillagePlayerByVillagePlayerIdLoader;
    }

    protected LoaderOfVillagePlayerStatusType _foreignVillagePlayerStatusTypeLoader;
    public LoaderOfVillagePlayerStatusType pulloutVillagePlayerStatusType() {
        if (_foreignVillagePlayerStatusTypeLoader == null)
        { _foreignVillagePlayerStatusTypeLoader = new LoaderOfVillagePlayerStatusType().ready(myBhv().pulloutVillagePlayerStatusType(_selectedList), _selector); }
        return _foreignVillagePlayerStatusTypeLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillagePlayerStatus> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
