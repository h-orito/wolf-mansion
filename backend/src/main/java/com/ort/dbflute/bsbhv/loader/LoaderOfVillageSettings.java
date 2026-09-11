package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_SETTINGS as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillageSettings {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillageSettings> _selectedList;
    protected BehaviorSelector _selector;
    protected VillageSettingsBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillageSettings ready(List<VillageSettings> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillageSettingsBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillageSettingsBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfAllowedSecretSay _foreignAllowedSecretSayLoader;
    public LoaderOfAllowedSecretSay pulloutAllowedSecretSay() {
        if (_foreignAllowedSecretSayLoader == null)
        { _foreignAllowedSecretSayLoader = new LoaderOfAllowedSecretSay().ready(myBhv().pulloutAllowedSecretSay(_selectedList), _selector); }
        return _foreignAllowedSecretSayLoader;
    }

    protected LoaderOfOriginalCharaGroup _foreignOriginalCharaGroupLoader;
    public LoaderOfOriginalCharaGroup pulloutOriginalCharaGroup() {
        if (_foreignOriginalCharaGroupLoader == null)
        { _foreignOriginalCharaGroupLoader = new LoaderOfOriginalCharaGroup().ready(myBhv().pulloutOriginalCharaGroup(_selectedList), _selector); }
        return _foreignOriginalCharaGroupLoader;
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
    public List<VillageSettings> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
