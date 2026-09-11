package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_PLAYER_NOTIFICATION as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillagePlayerNotification {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillagePlayerNotification> _selectedList;
    protected BehaviorSelector _selector;
    protected VillagePlayerNotificationBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillagePlayerNotification ready(List<VillagePlayerNotification> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillagePlayerNotificationBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillagePlayerNotificationBhv.class); return _myBhv; } }

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
    public List<VillagePlayerNotification> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
