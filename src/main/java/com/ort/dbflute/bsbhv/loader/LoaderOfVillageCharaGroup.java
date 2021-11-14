package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_CHARA_GROUP as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_CHARA_GROUP_ID
 *
 * [column]
 *     VILLAGE_CHARA_GROUP_ID, VILLAGE_ID, CHARA_GROUP_ID, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_CHARA_GROUP_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     CHARA_GROUP, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     charaGroup, village
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillageCharaGroup {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillageCharaGroup> _selectedList;
    protected BehaviorSelector _selector;
    protected VillageCharaGroupBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillageCharaGroup ready(List<VillageCharaGroup> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillageCharaGroupBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillageCharaGroupBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfCharaGroup _foreignCharaGroupLoader;
    public LoaderOfCharaGroup pulloutCharaGroup() {
        if (_foreignCharaGroupLoader == null)
        { _foreignCharaGroupLoader = new LoaderOfCharaGroup().ready(myBhv().pulloutCharaGroup(_selectedList), _selector); }
        return _foreignCharaGroupLoader;
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
    public List<VillageCharaGroup> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
