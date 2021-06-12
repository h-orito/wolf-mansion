package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of MESSAGE as TABLE. <br>
 * <pre>
 * [primary key]
 *     MESSAGE_ID
 *
 * [column]
 *     MESSAGE_ID, VILLAGE_ID, VILLAGE_PLAYER_ID, TO_VILLAGE_PLAYER_ID, PLAYER_ID, DAY, MESSAGE_TYPE_CODE, MESSAGE_NUMBER, MESSAGE_CONTENT, MESSAGE_DATETIME, IS_CONVERT_DISABLE, FACE_TYPE_CODE, CHARA_NAME, CHARA_SHORT_NAME, TO_CHARA_NAME, TO_CHARA_SHORT_NAME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     FACE_TYPE, MESSAGE_TYPE, PLAYER, VILLAGE_PLAYER, VILLAGE_DAY
 *
 * [referrer table]
 *     MESSAGE_SENDTO
 *
 * [foreign property]
 *     faceType, messageType, player, villagePlayerByToVillagePlayerId, villageDay, villagePlayerByVillagePlayerId
 *
 * [referrer property]
 *     messageSendtoList
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
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<MessageSendto> _referrerMessageSendto;

    /**
     * Load referrer of messageSendtoList by the set-upper of referrer. <br>
     * MESSAGE_SENDTO by MESSAGE_ID, named 'messageSendtoList'.
     * <pre>
     * <span style="color: #0000C0">messageBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">messageList</span>, <span style="color: #553000">messageLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">messageLoader</span>.<span style="color: #CC4747">loadMessageSendto</span>(<span style="color: #553000">sendtoCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">sendtoCB</span>.setupSelect...
     *         <span style="color: #553000">sendtoCB</span>.query().set...
     *         <span style="color: #553000">sendtoCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">sendtoLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    sendtoLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Message message : <span style="color: #553000">messageList</span>) {
     *     ... = message.<span style="color: #CC4747">getMessageSendtoList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setMessageId_InScope(pkList);
     * cb.query().addOrderBy_MessageId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessageSendto> loadMessageSendto(ReferrerConditionSetupper<MessageSendtoCB> refCBLambda) {
        myBhv().loadMessageSendto(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessageSendto = refLs);
        return hd -> hd.handle(new LoaderOfMessageSendto().ready(_referrerMessageSendto, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfFaceType _foreignFaceTypeLoader;
    public LoaderOfFaceType pulloutFaceType() {
        if (_foreignFaceTypeLoader == null)
        { _foreignFaceTypeLoader = new LoaderOfFaceType().ready(myBhv().pulloutFaceType(_selectedList), _selector); }
        return _foreignFaceTypeLoader;
    }

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

    protected LoaderOfVillageDay _foreignVillageDayLoader;
    public LoaderOfVillageDay pulloutVillageDay() {
        if (_foreignVillageDayLoader == null)
        { _foreignVillageDayLoader = new LoaderOfVillageDay().ready(myBhv().pulloutVillageDay(_selectedList), _selector); }
        return _foreignVillageDayLoader;
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
