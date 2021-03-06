package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of SKILL_SAY_RESTRICTION as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_ID, MESSAGE_TYPE_CODE
 *
 * [column]
 *     VILLAGE_ID, MESSAGE_TYPE_CODE, MESSAGE_MAX_NUM, MESSAGE_MAX_LENGTH, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     MESSAGE_TYPE, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     messageType, village
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfSkillSayRestriction {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<SkillSayRestriction> _selectedList;
    protected BehaviorSelector _selector;
    protected SkillSayRestrictionBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfSkillSayRestriction ready(List<SkillSayRestriction> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected SkillSayRestrictionBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(SkillSayRestrictionBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfMessageType _foreignMessageTypeLoader;
    public LoaderOfMessageType pulloutMessageType() {
        if (_foreignMessageTypeLoader == null)
        { _foreignMessageTypeLoader = new LoaderOfMessageType().ready(myBhv().pulloutMessageType(_selectedList), _selector); }
        return _foreignMessageTypeLoader;
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
    public List<SkillSayRestriction> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
