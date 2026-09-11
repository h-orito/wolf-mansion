package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of PLAYER_FAVORITE_CHARA as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfPlayerFavoriteChara {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<PlayerFavoriteChara> _selectedList;
    protected BehaviorSelector _selector;
    protected PlayerFavoriteCharaBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfPlayerFavoriteChara ready(List<PlayerFavoriteChara> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected PlayerFavoriteCharaBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(PlayerFavoriteCharaBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfChara _foreignCharaLoader;
    public LoaderOfChara pulloutChara() {
        if (_foreignCharaLoader == null)
        { _foreignCharaLoader = new LoaderOfChara().ready(myBhv().pulloutChara(_selectedList), _selector); }
        return _foreignCharaLoader;
    }

    protected LoaderOfPlayer _foreignPlayerLoader;
    public LoaderOfPlayer pulloutPlayer() {
        if (_foreignPlayerLoader == null)
        { _foreignPlayerLoader = new LoaderOfPlayer().ready(myBhv().pulloutPlayer(_selectedList), _selector); }
        return _foreignPlayerLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<PlayerFavoriteChara> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
