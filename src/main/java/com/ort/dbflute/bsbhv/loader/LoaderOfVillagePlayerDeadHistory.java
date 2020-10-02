package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_PLAYER_DEAD_HISTORY as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_PLAYER_DEAD_HISTORY_ID
 *
 * [column]
 *     VILLAGE_PLAYER_DEAD_HISTORY_ID, VILLAGE_PLAYER_ID, DAY, IS_DEAD, DEAD_REASON_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_DEAD_HISTORY_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     DEAD_REASON, VILLAGE_PLAYER
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     deadReason, villagePlayer
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillagePlayerDeadHistory {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillagePlayerDeadHistory> _selectedList;
    protected BehaviorSelector _selector;
    protected VillagePlayerDeadHistoryBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillagePlayerDeadHistory ready(List<VillagePlayerDeadHistory> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillagePlayerDeadHistoryBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillagePlayerDeadHistoryBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfDeadReason _foreignDeadReasonLoader;
    public LoaderOfDeadReason pulloutDeadReason() {
        if (_foreignDeadReasonLoader == null)
        { _foreignDeadReasonLoader = new LoaderOfDeadReason().ready(myBhv().pulloutDeadReason(_selectedList), _selector); }
        return _foreignDeadReasonLoader;
    }

    protected LoaderOfVillagePlayer _foreignVillagePlayerLoader;
    public LoaderOfVillagePlayer pulloutVillagePlayer() {
        if (_foreignVillagePlayerLoader == null)
        { _foreignVillagePlayerLoader = new LoaderOfVillagePlayer().ready(myBhv().pulloutVillagePlayer(_selectedList), _selector); }
        return _foreignVillagePlayerLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillagePlayerDeadHistory> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
