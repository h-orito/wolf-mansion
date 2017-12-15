package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of MESSAGE as TABLE. <br>
 * <pre>
 * [primary key]
 *     MESSAGE_ID
 *
 * [column]
 *     MESSAGE_ID, VILLAGE_ID, VILLAGE_PLAYER_ID, PLAYER_ID, TO_VILLAGE_PLAYER_ID, DAY, MESSAGE_TYPE_CODE, MESSAGE_NUMBER, MESSAGE_CONTENT, MESSAGE_DATETIME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     MESSAGE_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     MESSAGE_TYPE, PLAYER, VILLAGE_PLAYER, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     messageType, player, villagePlayerByToVillagePlayerId, village, villagePlayerByVillagePlayerId
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfMessage {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<Message> _selectedList;
    protected BehaviorSelector _selector;
    protected MessageBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfMessage ready(List<Message> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected MessageBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(MessageBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfMessageType _foreignMessageTypeLoader;
    public LoaderOfMessageType pulloutMessageType() {
        if (_foreignMessageTypeLoader == null)
        { _foreignMessageTypeLoader = new LoaderOfMessageType().ready(myBhv().pulloutMessageType(_selectedList), _selector); }
        return _foreignMessageTypeLoader;
    }

    protected LoaderOfPlayer _foreignPlayerLoader;
    public LoaderOfPlayer pulloutPlayer() {
        if (_foreignPlayerLoader == null)
        { _foreignPlayerLoader = new LoaderOfPlayer().ready(myBhv().pulloutPlayer(_selectedList), _selector); }
        return _foreignPlayerLoader;
    }

    protected LoaderOfVillagePlayer _foreignVillagePlayerByToVillagePlayerIdLoader;
    public LoaderOfVillagePlayer pulloutVillagePlayerByToVillagePlayerId() {
        if (_foreignVillagePlayerByToVillagePlayerIdLoader == null)
        { _foreignVillagePlayerByToVillagePlayerIdLoader = new LoaderOfVillagePlayer().ready(myBhv().pulloutVillagePlayerByToVillagePlayerId(_selectedList), _selector); }
        return _foreignVillagePlayerByToVillagePlayerIdLoader;
    }

    protected LoaderOfVillage _foreignVillageLoader;
    public LoaderOfVillage pulloutVillage() {
        if (_foreignVillageLoader == null)
        { _foreignVillageLoader = new LoaderOfVillage().ready(myBhv().pulloutVillage(_selectedList), _selector); }
        return _foreignVillageLoader;
    }

    protected LoaderOfVillagePlayer _foreignVillagePlayerByVillagePlayerIdLoader;
    public LoaderOfVillagePlayer pulloutVillagePlayerByVillagePlayerId() {
        if (_foreignVillagePlayerByVillagePlayerIdLoader == null)
        { _foreignVillagePlayerByVillagePlayerIdLoader = new LoaderOfVillagePlayer().ready(myBhv().pulloutVillagePlayerByVillagePlayerId(_selectedList), _selector); }
        return _foreignVillagePlayerByVillagePlayerIdLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<Message> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
