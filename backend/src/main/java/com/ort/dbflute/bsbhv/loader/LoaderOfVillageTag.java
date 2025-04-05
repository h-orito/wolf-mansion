package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_TAG as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillageTag {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillageTag> _selectedList;
    protected BehaviorSelector _selector;
    protected VillageTagBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillageTag ready(List<VillageTag> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillageTagBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillageTagBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfVillage _foreignVillageLoader;
    public LoaderOfVillage pulloutVillage() {
        if (_foreignVillageLoader == null)
        { _foreignVillageLoader = new LoaderOfVillage().ready(myBhv().pulloutVillage(_selectedList), _selector); }
        return _foreignVillageLoader;
    }

    protected LoaderOfVillageTagItem _foreignVillageTagItemLoader;
    public LoaderOfVillageTagItem pulloutVillageTagItem() {
        if (_foreignVillageTagItemLoader == null)
        { _foreignVillageTagItemLoader = new LoaderOfVillageTagItem().ready(myBhv().pulloutVillageTagItem(_selectedList), _selector); }
        return _foreignVillageTagItemLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillageTag> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
