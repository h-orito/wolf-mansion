package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of ORIGINAL_CHARA_IMAGE as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfOriginalCharaImage {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<OriginalCharaImage> _selectedList;
    protected BehaviorSelector _selector;
    protected OriginalCharaImageBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfOriginalCharaImage ready(List<OriginalCharaImage> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected OriginalCharaImageBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(OriginalCharaImageBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfOriginalChara _foreignOriginalCharaLoader;
    public LoaderOfOriginalChara pulloutOriginalChara() {
        if (_foreignOriginalCharaLoader == null)
        { _foreignOriginalCharaLoader = new LoaderOfOriginalChara().ready(myBhv().pulloutOriginalChara(_selectedList), _selector); }
        return _foreignOriginalCharaLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<OriginalCharaImage> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
