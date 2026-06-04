package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of FOOTSTEP as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_ID, DAY, REGISTER_CHARA_ID
 *
 * [column]
 *     VILLAGE_ID, DAY, REGISTER_CHARA_ID, CHARA_ID, FOOTSTEP_ROOM_NUMBERS, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     VILLAGE_DAY
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     villageDay
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfFootstep {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<Footstep> _selectedList;
    protected BehaviorSelector _selector;
    protected FootstepBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfFootstep ready(List<Footstep> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected FootstepBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(FootstepBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfVillageDay _foreignVillageDayLoader;
    public LoaderOfVillageDay pulloutVillageDay() {
        if (_foreignVillageDayLoader == null)
        { _foreignVillageDayLoader = new LoaderOfVillageDay().ready(myBhv().pulloutVillageDay(_selectedList), _selector); }
        return _foreignVillageDayLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<Footstep> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
