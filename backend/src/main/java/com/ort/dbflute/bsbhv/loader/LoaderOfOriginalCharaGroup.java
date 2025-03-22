package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of ORIGINAL_CHARA_GROUP as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfOriginalCharaGroup {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<OriginalCharaGroup> _selectedList;
    protected BehaviorSelector _selector;
    protected OriginalCharaGroupBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfOriginalCharaGroup ready(List<OriginalCharaGroup> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected OriginalCharaGroupBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(OriginalCharaGroupBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<OriginalChara> _referrerOriginalChara;

    /**
     * Load referrer of originalCharaList by the set-upper of referrer. <br>
     * ORIGINAL_CHARA by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaList'.
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">originalCharaGroupList</span>, <span style="color: #553000">groupLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">groupLoader</span>.<span style="color: #CC4747">loadOriginalChara</span>(<span style="color: #553000">charaCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">charaCB</span>.setupSelect...
     *         <span style="color: #553000">charaCB</span>.query().set...
     *         <span style="color: #553000">charaCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">charaLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    charaLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (OriginalCharaGroup originalCharaGroup : <span style="color: #553000">originalCharaGroupList</span>) {
     *     ... = originalCharaGroup.<span style="color: #CC4747">getOriginalCharaList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setOriginalCharaGroupId_InScope(pkList);
     * cb.query().addOrderBy_OriginalCharaGroupId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfOriginalChara> loadOriginalChara(ReferrerConditionSetupper<OriginalCharaCB> refCBLambda) {
        myBhv().loadOriginalChara(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerOriginalChara = refLs);
        return hd -> hd.handle(new LoaderOfOriginalChara().ready(_referrerOriginalChara, _selector));
    }

    protected List<VillageSettings> _referrerVillageSettings;

    /**
     * Load referrer of villageSettingsList by the set-upper of referrer. <br>
     * VILLAGE_SETTINGS by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsList'.
     * <pre>
     * <span style="color: #0000C0">originalCharaGroupBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">originalCharaGroupList</span>, <span style="color: #553000">groupLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">groupLoader</span>.<span style="color: #CC4747">loadVillageSettings</span>(<span style="color: #553000">settingsCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">settingsCB</span>.setupSelect...
     *         <span style="color: #553000">settingsCB</span>.query().set...
     *         <span style="color: #553000">settingsCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">settingsLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    settingsLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (OriginalCharaGroup originalCharaGroup : <span style="color: #553000">originalCharaGroupList</span>) {
     *     ... = originalCharaGroup.<span style="color: #CC4747">getVillageSettingsList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setOriginalCharaGroupId_InScope(pkList);
     * cb.query().addOrderBy_OriginalCharaGroupId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillageSettings> loadVillageSettings(ReferrerConditionSetupper<VillageSettingsCB> refCBLambda) {
        myBhv().loadVillageSettings(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillageSettings = refLs);
        return hd -> hd.handle(new LoaderOfVillageSettings().ready(_referrerVillageSettings, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<OriginalCharaGroup> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
