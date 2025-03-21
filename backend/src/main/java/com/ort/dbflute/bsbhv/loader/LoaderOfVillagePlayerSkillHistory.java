package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_PLAYER_SKILL_HISTORY as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_PLAYER_SKILL_HISTORY_ID
 *
 * [column]
 *     VILLAGE_PLAYER_SKILL_HISTORY_ID, VILLAGE_PLAYER_ID, DAY, SKILL_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_SKILL_HISTORY_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     SKILL, VILLAGE_PLAYER
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     skill, villagePlayer
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillagePlayerSkillHistory {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillagePlayerSkillHistory> _selectedList;
    protected BehaviorSelector _selector;
    protected VillagePlayerSkillHistoryBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillagePlayerSkillHistory ready(List<VillagePlayerSkillHistory> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillagePlayerSkillHistoryBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillagePlayerSkillHistoryBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfSkill _foreignSkillLoader;
    public LoaderOfSkill pulloutSkill() {
        if (_foreignSkillLoader == null)
        { _foreignSkillLoader = new LoaderOfSkill().ready(myBhv().pulloutSkill(_selectedList), _selector); }
        return _foreignSkillLoader;
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
    public List<VillagePlayerSkillHistory> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
