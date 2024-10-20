package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of VILLAGE_SETTINGS as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_ID
 *
 * [column]
 *     VILLAGE_ID, DUMMY_CHARA_ID, START_PERSON_MIN_NUM, PERSON_MAX_NUM, START_DATETIME, DAY_CHANGE_INTERVAL_SECONDS, IS_OPEN_VOTE, IS_POSSIBLE_SKILL_REQUEST, IS_AVAILABLE_SPECTATE, IS_AVAILABLE_SAME_WOLF_ATTACK, IS_OPEN_SKILL_IN_GRAVE, IS_VISIBLE_GRAVE_SPECTATE_MESSAGE, IS_AVAILABLE_SUDDONLY_DEATH, IS_AVAILABLE_COMMIT, IS_AVAILABLE_GUARD_SAME_TARGET, JOIN_PASSWORD, ORGANIZE, ALLOWED_SECRET_SAY_CODE, IS_AVAILABLE_ACTION, IS_RANDOM_ORGANIZE, IS_REINCARNATION_SKILL_ALL, IS_CREATOR_PRODUCER, ORIGINAL_CHARA_GROUP_ID, DAY1_DUMMY_MESSAGE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     ALLOWED_SECRET_SAY, ORIGINAL_CHARA_GROUP, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     allowedSecretSay, originalCharaGroup, village
 *
 * [referrer property]
 *     
 * </pre>
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
