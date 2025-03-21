package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of VILLAGE_TAG_ITEM as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_TAG_ITEM_CODE
 *
 * [column]
 *     VILLAGE_TAG_ITEM_CODE, VILLAGE_TAG_ITEM_NAME, DISP_ORDER
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
 *     
 *
 * [referrer table]
 *     VILLAGE_TAG
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     villageTagList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillageTagItem {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillageTagItem> _selectedList;
    protected BehaviorSelector _selector;
    protected VillageTagItemBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillageTagItem ready(List<VillageTagItem> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillageTagItemBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillageTagItemBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<VillageTag> _referrerVillageTag;

    /**
     * Load referrer of villageTagList by the set-upper of referrer. <br>
     * VILLAGE_TAG by VILLAGE_TAG_ITEM_CODE, named 'villageTagList'.
     * <pre>
     * <span style="color: #0000C0">villageTagItemBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villageTagItemList</span>, <span style="color: #553000">itemLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">itemLoader</span>.<span style="color: #CC4747">loadVillageTag</span>(<span style="color: #553000">tagCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">tagCB</span>.setupSelect...
     *         <span style="color: #553000">tagCB</span>.query().set...
     *         <span style="color: #553000">tagCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">tagLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    tagLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillageTagItem villageTagItem : <span style="color: #553000">villageTagItemList</span>) {
     *     ... = villageTagItem.<span style="color: #CC4747">getVillageTagList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillageTagItemCode_InScope(pkList);
     * cb.query().addOrderBy_VillageTagItemCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillageTag> loadVillageTag(ReferrerConditionSetupper<VillageTagCB> refCBLambda) {
        myBhv().loadVillageTag(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillageTag = refLs);
        return hd -> hd.handle(new LoaderOfVillageTag().ready(_referrerVillageTag, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillageTagItem> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
