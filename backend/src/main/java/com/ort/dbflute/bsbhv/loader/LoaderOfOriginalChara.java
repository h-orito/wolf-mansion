package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of ORIGINAL_CHARA as TABLE. <br>
 * <pre>
 * [primary key]
 *     ORIGINAL_CHARA_ID
 *
 * [column]
 *     ORIGINAL_CHARA_ID, ORIGINAL_CHARA_NAME, ORIGINAL_CHARA_SHORT_NAME, ORIGINAL_CHARA_GROUP_ID, DISPLAY_WIDTH, DISPLAY_HEIGHT, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     ORIGINAL_CHARA_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     ORIGINAL_CHARA_GROUP
 *
 * [referrer table]
 *     ORIGINAL_CHARA_IMAGE
 *
 * [foreign property]
 *     originalCharaGroup
 *
 * [referrer property]
 *     originalCharaImageList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfOriginalChara {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<OriginalChara> _selectedList;
    protected BehaviorSelector _selector;
    protected OriginalCharaBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfOriginalChara ready(List<OriginalChara> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected OriginalCharaBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(OriginalCharaBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<OriginalCharaImage> _referrerOriginalCharaImage;

    /**
     * Load referrer of originalCharaImageList by the set-upper of referrer. <br>
     * ORIGINAL_CHARA_IMAGE by ORIGINAL_CHARA_ID, named 'originalCharaImageList'.
     * <pre>
     * <span style="color: #0000C0">originalCharaBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">originalCharaList</span>, <span style="color: #553000">charaLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">charaLoader</span>.<span style="color: #CC4747">loadOriginalCharaImage</span>(<span style="color: #553000">imageCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">imageCB</span>.setupSelect...
     *         <span style="color: #553000">imageCB</span>.query().set...
     *         <span style="color: #553000">imageCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">imageLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    imageLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (OriginalChara originalChara : <span style="color: #553000">originalCharaList</span>) {
     *     ... = originalChara.<span style="color: #CC4747">getOriginalCharaImageList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setOriginalCharaId_InScope(pkList);
     * cb.query().addOrderBy_OriginalCharaId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfOriginalCharaImage> loadOriginalCharaImage(ReferrerConditionSetupper<OriginalCharaImageCB> refCBLambda) {
        myBhv().loadOriginalCharaImage(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerOriginalCharaImage = refLs);
        return hd -> hd.handle(new LoaderOfOriginalCharaImage().ready(_referrerOriginalCharaImage, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfOriginalCharaGroup _foreignOriginalCharaGroupLoader;
    public LoaderOfOriginalCharaGroup pulloutOriginalCharaGroup() {
        if (_foreignOriginalCharaGroupLoader == null)
        { _foreignOriginalCharaGroupLoader = new LoaderOfOriginalCharaGroup().ready(myBhv().pulloutOriginalCharaGroup(_selectedList), _selector); }
        return _foreignOriginalCharaGroupLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<OriginalChara> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
