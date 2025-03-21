package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;

/**
 * The referrer loader of MESSAGE_SENDTO as TABLE. <br>
 * <pre>
 * [primary key]
 *     MESSAGE_REPLYTO_ID
 *
 * [column]
 *     MESSAGE_REPLYTO_ID, MESSAGE_ID, VILLAGE_PLAYER_ID, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     MESSAGE_REPLYTO_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     MESSAGE, VILLAGE_PLAYER
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     message, villagePlayer
 *
 * [referrer property]
 *     
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfMessageSendto {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<MessageSendto> _selectedList;
    protected BehaviorSelector _selector;
    protected MessageSendtoBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfMessageSendto ready(List<MessageSendto> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected MessageSendtoBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(MessageSendtoBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfMessage _foreignMessageLoader;
    public LoaderOfMessage pulloutMessage() {
        if (_foreignMessageLoader == null)
        { _foreignMessageLoader = new LoaderOfMessage().ready(myBhv().pulloutMessage(_selectedList), _selector); }
        return _foreignMessageLoader;
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
    public List<MessageSendto> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
