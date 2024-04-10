package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of CAMP_ALLOCATION as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_ID, CAMP_CODE
 *
 * [column]
 *     VILLAGE_ID, CAMP_CODE, MIN_NUM, MAX_NUM, ALLOCATION, REINCARNATION_ALLOCATION, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     CAMP, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     camp, village
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfCampAllocation {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<CampAllocation> _selectedList;
    protected BehaviorSelector _selector;
    protected CampAllocationBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfCampAllocation ready(List<CampAllocation> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected CampAllocationBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(CampAllocationBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfCamp _foreignCampLoader;
    public LoaderOfCamp pulloutCamp() {
        if (_foreignCampLoader == null)
        { _foreignCampLoader = new LoaderOfCamp().ready(myBhv().pulloutCamp(_selectedList), _selector); }
        return _foreignCampLoader;
    }

    protected LoaderOfVillage _foreignVillageLoader;
    public LoaderOfVillage pulloutVillage() {
        if (_foreignVillageLoader == null)
        { _foreignVillageLoader = new LoaderOfVillage().ready(myBhv().pulloutVillage(_selectedList), _selector); }
        return _foreignVillageLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<CampAllocation> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
