package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_PLAYER_ROOM_HISTORY as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_PLAYER_ROOM_HISTORY_ID
 *
 * [column]
 *     VILLAGE_PLAYER_ROOM_HISTORY_ID, VILLAGE_PLAYER_ID, DAY, ROOM_NUMBER, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_ROOM_HISTORY_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VILLAGE_PLAYER
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     villagePlayer
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillagePlayerRoomHistory {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillagePlayerRoomHistory> _selectedList;
    protected BehaviorSelector _selector;
    protected VillagePlayerRoomHistoryBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillagePlayerRoomHistory ready(List<VillagePlayerRoomHistory> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillagePlayerRoomHistoryBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillagePlayerRoomHistoryBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfVillagePlayer _foreignVillagePlayerLoader;
    public LoaderOfVillagePlayer pulloutVillagePlayer() {
        if (_foreignVillagePlayerLoader == null)
        { _foreignVillagePlayerLoader = new LoaderOfVillagePlayer().ready(myBhv().pulloutVillagePlayer(_selectedList), _selector); }
        return _foreignVillagePlayerLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillagePlayerRoomHistory> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
