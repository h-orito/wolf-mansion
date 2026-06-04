package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of LOGIN_FAILURE as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfLoginFailure {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<LoginFailure> _selectedList;
    protected BehaviorSelector _selector;
    protected LoginFailureBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfLoginFailure ready(List<LoginFailure> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected LoginFailureBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(LoginFailureBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<LoginFailure> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
