package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of WOLF_ALLOCATION as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfWolfAllocation {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<WolfAllocation> _selectedList;
    protected BehaviorSelector _selector;
    protected WolfAllocationBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfWolfAllocation ready(List<WolfAllocation> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected WolfAllocationBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(WolfAllocationBhv.class); return _myBhv; } }

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
    public List<WolfAllocation> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
