package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of VILLAGE_PLAYER as TABLE. <br>
 * <pre>
 * [primary key]
 *     VILLAGE_PLAYER_ID
 *
 * [column]
 *     VILLAGE_PLAYER_ID, VILLAGE_ID, PLAYER_ID, CHARA_ID, SKILL_CODE, REQUEST_SKILL_CODE, SECOND_REQUEST_SKILL_CODE, ROOM_NUMBER, IS_DEAD, IS_SPECTATOR, DEAD_REASON_CODE, DEAD_DAY, IS_GONE, LAST_ACCESS_DATETIME, CAMP_CODE, IS_WIN, CHARA_NAME, CHARA_SHORT_NAME, MEMO, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     DEAD_REASON, PLAYER, SKILL, VILLAGE, VILLAGE_PLAYER_NOTIFICATION(AsOne)
 *
 * [referrer table]
 *     COMMIT, MESSAGE, MESSAGE_SENDTO, VILLAGE_PLAYER_ACCESS_INFO, VILLAGE_PLAYER_DEAD_HISTORY, VILLAGE_PLAYER_ROOM_HISTORY, VILLAGE_PLAYER_SKILL_HISTORY, VILLAGE_PLAYER_STATUS, VILLAGE_PLAYER_NOTIFICATION
 *
 * [foreign property]
 *     deadReason, player, skillByRequestSkillCode, skillBySecondRequestSkillCode, skillBySkillCode, village, villagePlayerNotificationAsOne
 *
 * [referrer property]
 *     commitList, messageByToVillagePlayerIdList, messageByVillagePlayerIdList, messageSendtoList, villagePlayerAccessInfoList, villagePlayerDeadHistoryList, villagePlayerRoomHistoryList, villagePlayerSkillHistoryList, villagePlayerStatusByToVillagePlayerIdList, villagePlayerStatusByVillagePlayerIdList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfVillagePlayer {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<VillagePlayer> _selectedList;
    protected BehaviorSelector _selector;
    protected VillagePlayerBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfVillagePlayer ready(List<VillagePlayer> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected VillagePlayerBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(VillagePlayerBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<Commit> _referrerCommit;

    /**
     * Load referrer of commitList by the set-upper of referrer. <br>
     * COMMIT by VILLAGE_PLAYER_ID, named 'commitList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadCommit</span>(<span style="color: #553000">commitCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">commitCB</span>.setupSelect...
     *         <span style="color: #553000">commitCB</span>.query().set...
     *         <span style="color: #553000">commitCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">commitLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    commitLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getCommitList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfCommit> loadCommit(ReferrerConditionSetupper<CommitCB> refCBLambda) {
        myBhv().loadCommit(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerCommit = refLs);
        return hd -> hd.handle(new LoaderOfCommit().ready(_referrerCommit, _selector));
    }

    protected List<Message> _referrerMessageByToVillagePlayerId;

    /**
     * Load referrer of messageByToVillagePlayerIdList by the set-upper of referrer. <br>
     * MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadMessageByToVillagePlayerId</span>(<span style="color: #553000">messageCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">messageCB</span>.setupSelect...
     *         <span style="color: #553000">messageCB</span>.query().set...
     *         <span style="color: #553000">messageCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">messageLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    messageLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getMessageByToVillagePlayerIdList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setToVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_ToVillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessage> loadMessageByToVillagePlayerId(ReferrerConditionSetupper<MessageCB> refCBLambda) {
        myBhv().loadMessageByToVillagePlayerId(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessageByToVillagePlayerId = refLs);
        return hd -> hd.handle(new LoaderOfMessage().ready(_referrerMessageByToVillagePlayerId, _selector));
    }

    protected List<Message> _referrerMessageByVillagePlayerId;

    /**
     * Load referrer of messageByVillagePlayerIdList by the set-upper of referrer. <br>
     * MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadMessageByVillagePlayerId</span>(<span style="color: #553000">messageCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">messageCB</span>.setupSelect...
     *         <span style="color: #553000">messageCB</span>.query().set...
     *         <span style="color: #553000">messageCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">messageLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    messageLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getMessageByVillagePlayerIdList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessage> loadMessageByVillagePlayerId(ReferrerConditionSetupper<MessageCB> refCBLambda) {
        myBhv().loadMessageByVillagePlayerId(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessageByVillagePlayerId = refLs);
        return hd -> hd.handle(new LoaderOfMessage().ready(_referrerMessageByVillagePlayerId, _selector));
    }

    protected List<MessageSendto> _referrerMessageSendto;

    /**
     * Load referrer of messageSendtoList by the set-upper of referrer. <br>
     * MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadMessageSendto</span>(<span style="color: #553000">sendtoCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">sendtoCB</span>.setupSelect...
     *         <span style="color: #553000">sendtoCB</span>.query().set...
     *         <span style="color: #553000">sendtoCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">sendtoLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    sendtoLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getMessageSendtoList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessageSendto> loadMessageSendto(ReferrerConditionSetupper<MessageSendtoCB> refCBLambda) {
        myBhv().loadMessageSendto(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessageSendto = refLs);
        return hd -> hd.handle(new LoaderOfMessageSendto().ready(_referrerMessageSendto, _selector));
    }

    protected List<VillagePlayerAccessInfo> _referrerVillagePlayerAccessInfo;

    /**
     * Load referrer of villagePlayerAccessInfoList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER_ACCESS_INFO by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadVillagePlayerAccessInfo</span>(<span style="color: #553000">infoCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">infoCB</span>.setupSelect...
     *         <span style="color: #553000">infoCB</span>.query().set...
     *         <span style="color: #553000">infoCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">infoLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    infoLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getVillagePlayerAccessInfoList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayerAccessInfo> loadVillagePlayerAccessInfo(ReferrerConditionSetupper<VillagePlayerAccessInfoCB> refCBLambda) {
        myBhv().loadVillagePlayerAccessInfo(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayerAccessInfo = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayerAccessInfo().ready(_referrerVillagePlayerAccessInfo, _selector));
    }

    protected List<VillagePlayerDeadHistory> _referrerVillagePlayerDeadHistory;

    /**
     * Load referrer of villagePlayerDeadHistoryList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadVillagePlayerDeadHistory</span>(<span style="color: #553000">historyCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">historyCB</span>.setupSelect...
     *         <span style="color: #553000">historyCB</span>.query().set...
     *         <span style="color: #553000">historyCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">historyLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    historyLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getVillagePlayerDeadHistoryList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayerDeadHistory> loadVillagePlayerDeadHistory(ReferrerConditionSetupper<VillagePlayerDeadHistoryCB> refCBLambda) {
        myBhv().loadVillagePlayerDeadHistory(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayerDeadHistory = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayerDeadHistory().ready(_referrerVillagePlayerDeadHistory, _selector));
    }

    protected List<VillagePlayerRoomHistory> _referrerVillagePlayerRoomHistory;

    /**
     * Load referrer of villagePlayerRoomHistoryList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadVillagePlayerRoomHistory</span>(<span style="color: #553000">historyCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">historyCB</span>.setupSelect...
     *         <span style="color: #553000">historyCB</span>.query().set...
     *         <span style="color: #553000">historyCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">historyLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    historyLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getVillagePlayerRoomHistoryList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayerRoomHistory> loadVillagePlayerRoomHistory(ReferrerConditionSetupper<VillagePlayerRoomHistoryCB> refCBLambda) {
        myBhv().loadVillagePlayerRoomHistory(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayerRoomHistory = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayerRoomHistory().ready(_referrerVillagePlayerRoomHistory, _selector));
    }

    protected List<VillagePlayerSkillHistory> _referrerVillagePlayerSkillHistory;

    /**
     * Load referrer of villagePlayerSkillHistoryList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER_SKILL_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadVillagePlayerSkillHistory</span>(<span style="color: #553000">historyCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">historyCB</span>.setupSelect...
     *         <span style="color: #553000">historyCB</span>.query().set...
     *         <span style="color: #553000">historyCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">historyLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    historyLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getVillagePlayerSkillHistoryList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayerSkillHistory> loadVillagePlayerSkillHistory(ReferrerConditionSetupper<VillagePlayerSkillHistoryCB> refCBLambda) {
        myBhv().loadVillagePlayerSkillHistory(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayerSkillHistory = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayerSkillHistory().ready(_referrerVillagePlayerSkillHistory, _selector));
    }

    protected List<VillagePlayerStatus> _referrerVillagePlayerStatusByToVillagePlayerId;

    /**
     * Load referrer of villagePlayerStatusByToVillagePlayerIdList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadVillagePlayerStatusByToVillagePlayerId</span>(<span style="color: #553000">statusCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">statusCB</span>.setupSelect...
     *         <span style="color: #553000">statusCB</span>.query().set...
     *         <span style="color: #553000">statusCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">statusLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    statusLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getVillagePlayerStatusByToVillagePlayerIdList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setToVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_ToVillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayerStatus> loadVillagePlayerStatusByToVillagePlayerId(ReferrerConditionSetupper<VillagePlayerStatusCB> refCBLambda) {
        myBhv().loadVillagePlayerStatusByToVillagePlayerId(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayerStatusByToVillagePlayerId = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayerStatus().ready(_referrerVillagePlayerStatusByToVillagePlayerId, _selector));
    }

    protected List<VillagePlayerStatus> _referrerVillagePlayerStatusByVillagePlayerId;

    /**
     * Load referrer of villagePlayerStatusByVillagePlayerIdList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdList'.
     * <pre>
     * <span style="color: #0000C0">villagePlayerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">villagePlayerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadVillagePlayerStatusByVillagePlayerId</span>(<span style="color: #553000">statusCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">statusCB</span>.setupSelect...
     *         <span style="color: #553000">statusCB</span>.query().set...
     *         <span style="color: #553000">statusCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">statusLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    statusLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (VillagePlayer villagePlayer : <span style="color: #553000">villagePlayerList</span>) {
     *     ... = villagePlayer.<span style="color: #CC4747">getVillagePlayerStatusByVillagePlayerIdList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setVillagePlayerId_InScope(pkList);
     * cb.query().addOrderBy_VillagePlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayerStatus> loadVillagePlayerStatusByVillagePlayerId(ReferrerConditionSetupper<VillagePlayerStatusCB> refCBLambda) {
        myBhv().loadVillagePlayerStatusByVillagePlayerId(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayerStatusByVillagePlayerId = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayerStatus().ready(_referrerVillagePlayerStatusByVillagePlayerId, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfDeadReason _foreignDeadReasonLoader;
    public LoaderOfDeadReason pulloutDeadReason() {
        if (_foreignDeadReasonLoader == null)
        { _foreignDeadReasonLoader = new LoaderOfDeadReason().ready(myBhv().pulloutDeadReason(_selectedList), _selector); }
        return _foreignDeadReasonLoader;
    }

    protected LoaderOfPlayer _foreignPlayerLoader;
    public LoaderOfPlayer pulloutPlayer() {
        if (_foreignPlayerLoader == null)
        { _foreignPlayerLoader = new LoaderOfPlayer().ready(myBhv().pulloutPlayer(_selectedList), _selector); }
        return _foreignPlayerLoader;
    }

    protected LoaderOfSkill _foreignSkillByRequestSkillCodeLoader;
    public LoaderOfSkill pulloutSkillByRequestSkillCode() {
        if (_foreignSkillByRequestSkillCodeLoader == null)
        { _foreignSkillByRequestSkillCodeLoader = new LoaderOfSkill().ready(myBhv().pulloutSkillByRequestSkillCode(_selectedList), _selector); }
        return _foreignSkillByRequestSkillCodeLoader;
    }

    protected LoaderOfSkill _foreignSkillBySecondRequestSkillCodeLoader;
    public LoaderOfSkill pulloutSkillBySecondRequestSkillCode() {
        if (_foreignSkillBySecondRequestSkillCodeLoader == null)
        { _foreignSkillBySecondRequestSkillCodeLoader = new LoaderOfSkill().ready(myBhv().pulloutSkillBySecondRequestSkillCode(_selectedList), _selector); }
        return _foreignSkillBySecondRequestSkillCodeLoader;
    }

    protected LoaderOfSkill _foreignSkillBySkillCodeLoader;
    public LoaderOfSkill pulloutSkillBySkillCode() {
        if (_foreignSkillBySkillCodeLoader == null)
        { _foreignSkillBySkillCodeLoader = new LoaderOfSkill().ready(myBhv().pulloutSkillBySkillCode(_selectedList), _selector); }
        return _foreignSkillBySkillCodeLoader;
    }

    protected LoaderOfVillage _foreignVillageLoader;
    public LoaderOfVillage pulloutVillage() {
        if (_foreignVillageLoader == null)
        { _foreignVillageLoader = new LoaderOfVillage().ready(myBhv().pulloutVillage(_selectedList), _selector); }
        return _foreignVillageLoader;
    }

    protected LoaderOfVillagePlayerNotification _foreignVillagePlayerNotificationAsOneLoader;
    public LoaderOfVillagePlayerNotification pulloutVillagePlayerNotificationAsOne() {
        if (_foreignVillagePlayerNotificationAsOneLoader == null)
        { _foreignVillagePlayerNotificationAsOneLoader = new LoaderOfVillagePlayerNotification().ready(myBhv().pulloutVillagePlayerNotificationAsOne(_selectedList), _selector); }
        return _foreignVillagePlayerNotificationAsOneLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<VillagePlayer> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
