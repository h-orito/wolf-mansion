package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of SKILL_ALLOCATION as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_ID, SKILL_CODE
 *
 * [column]
 *     VILLAGE_ID, SKILL_CODE, MIN_NUM, MAX_NUM, ALLOCATION, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     SKILL, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     skill, village
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfSkillAllocation {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<SkillAllocation> _selectedList;
    protected BehaviorSelector _selector;
    protected SkillAllocationBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfSkillAllocation ready(List<SkillAllocation> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected SkillAllocationBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(SkillAllocationBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfSkill _foreignSkillLoader;
    public LoaderOfSkill pulloutSkill() {
        if (_foreignSkillLoader == null)
        { _foreignSkillLoader = new LoaderOfSkill().ready(myBhv().pulloutSkill(_selectedList), _selector); }
        return _foreignSkillLoader;
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
    public List<SkillAllocation> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
