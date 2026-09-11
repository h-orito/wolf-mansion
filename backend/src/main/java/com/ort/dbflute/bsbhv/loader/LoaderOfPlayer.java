package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of PLAYER as TABLE.
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfPlayer {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<Player> _selectedList;
    protected BehaviorSelector _selector;
    protected PlayerBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfPlayer ready(List<Player> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected PlayerBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(PlayerBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<AnalyzerMemo> _referrerAnalyzerMemo;

    /**
     * Load referrer of analyzerMemoList by the set-upper of referrer. <br>
     * ANALYZER_MEMO by PLAYER_ID, named 'analyzerMemoList'.
     * <pre>
     * <span style="color: #0000C0">playerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">playerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadAnalyzerMemo</span>(<span style="color: #553000">memoCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">memoCB</span>.setupSelect...
     *         <span style="color: #553000">memoCB</span>.query().set...
     *         <span style="color: #553000">memoCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">memoLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    memoLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Player player : <span style="color: #553000">playerList</span>) {
     *     ... = player.<span style="color: #CC4747">getAnalyzerMemoList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setPlayerId_InScope(pkList);
     * cb.query().addOrderBy_PlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfAnalyzerMemo> loadAnalyzerMemo(ReferrerConditionSetupper<AnalyzerMemoCB> refCBLambda) {
        myBhv().loadAnalyzerMemo(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerAnalyzerMemo = refLs);
        return hd -> hd.handle(new LoaderOfAnalyzerMemo().ready(_referrerAnalyzerMemo, _selector));
    }

    protected List<Message> _referrerMessage;

    /**
     * Load referrer of messageList by the set-upper of referrer. <br>
     * MESSAGE by PLAYER_ID, named 'messageList'.
     * <pre>
     * <span style="color: #0000C0">playerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">playerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadMessage</span>(<span style="color: #553000">messageCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">messageCB</span>.setupSelect...
     *         <span style="color: #553000">messageCB</span>.query().set...
     *         <span style="color: #553000">messageCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">messageLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    messageLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Player player : <span style="color: #553000">playerList</span>) {
     *     ... = player.<span style="color: #CC4747">getMessageList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setPlayerId_InScope(pkList);
     * cb.query().addOrderBy_PlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessage> loadMessage(ReferrerConditionSetupper<MessageCB> refCBLambda) {
        myBhv().loadMessage(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessage = refLs);
        return hd -> hd.handle(new LoaderOfMessage().ready(_referrerMessage, _selector));
    }

    protected List<PlayerFavoriteChara> _referrerPlayerFavoriteChara;

    /**
     * Load referrer of playerFavoriteCharaList by the set-upper of referrer. <br>
     * PLAYER_FAVORITE_CHARA by PLAYER_ID, named 'playerFavoriteCharaList'.
     * <pre>
     * <span style="color: #0000C0">playerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">playerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadPlayerFavoriteChara</span>(<span style="color: #553000">charaCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">charaCB</span>.setupSelect...
     *         <span style="color: #553000">charaCB</span>.query().set...
     *         <span style="color: #553000">charaCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">charaLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    charaLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Player player : <span style="color: #553000">playerList</span>) {
     *     ... = player.<span style="color: #CC4747">getPlayerFavoriteCharaList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setPlayerId_InScope(pkList);
     * cb.query().addOrderBy_PlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfPlayerFavoriteChara> loadPlayerFavoriteChara(ReferrerConditionSetupper<PlayerFavoriteCharaCB> refCBLambda) {
        myBhv().loadPlayerFavoriteChara(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerPlayerFavoriteChara = refLs);
        return hd -> hd.handle(new LoaderOfPlayerFavoriteChara().ready(_referrerPlayerFavoriteChara, _selector));
    }

    protected List<RefreshToken> _referrerRefreshToken;

    /**
     * Load referrer of refreshTokenList by the set-upper of referrer. <br>
     * REFRESH_TOKEN by PLAYER_ID, named 'refreshTokenList'.
     * <pre>
     * <span style="color: #0000C0">playerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">playerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadRefreshToken</span>(<span style="color: #553000">tokenCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">tokenCB</span>.setupSelect...
     *         <span style="color: #553000">tokenCB</span>.query().set...
     *         <span style="color: #553000">tokenCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">tokenLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    tokenLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Player player : <span style="color: #553000">playerList</span>) {
     *     ... = player.<span style="color: #CC4747">getRefreshTokenList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setPlayerId_InScope(pkList);
     * cb.query().addOrderBy_PlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfRefreshToken> loadRefreshToken(ReferrerConditionSetupper<RefreshTokenCB> refCBLambda) {
        myBhv().loadRefreshToken(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerRefreshToken = refLs);
        return hd -> hd.handle(new LoaderOfRefreshToken().ready(_referrerRefreshToken, _selector));
    }

    protected List<VillagePlayer> _referrerVillagePlayer;

    /**
     * Load referrer of villagePlayerList by the set-upper of referrer. <br>
     * VILLAGE_PLAYER by PLAYER_ID, named 'villagePlayerList'.
     * <pre>
     * <span style="color: #0000C0">playerBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">playerList</span>, <span style="color: #553000">playerLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">playerLoader</span>.<span style="color: #CC4747">loadVillagePlayer</span>(<span style="color: #553000">playerCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">playerCB</span>.setupSelect...
     *         <span style="color: #553000">playerCB</span>.query().set...
     *         <span style="color: #553000">playerCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">playerLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    playerLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (Player player : <span style="color: #553000">playerList</span>) {
     *     ... = player.<span style="color: #CC4747">getVillagePlayerList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setPlayerId_InScope(pkList);
     * cb.query().addOrderBy_PlayerId_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfVillagePlayer> loadVillagePlayer(ReferrerConditionSetupper<VillagePlayerCB> refCBLambda) {
        myBhv().loadVillagePlayer(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerVillagePlayer = refLs);
        return hd -> hd.handle(new LoaderOfVillagePlayer().ready(_referrerVillagePlayer, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    protected LoaderOfAuthority _foreignAuthorityLoader;
    public LoaderOfAuthority pulloutAuthority() {
        if (_foreignAuthorityLoader == null)
        { _foreignAuthorityLoader = new LoaderOfAuthority().ready(myBhv().pulloutAuthority(_selectedList), _selector); }
        return _foreignAuthorityLoader;
    }

    protected LoaderOfPlayerDetail _foreignPlayerDetailAsOneLoader;
    public LoaderOfPlayerDetail pulloutPlayerDetailAsOne() {
        if (_foreignPlayerDetailAsOneLoader == null)
        { _foreignPlayerDetailAsOneLoader = new LoaderOfPlayerDetail().ready(myBhv().pulloutPlayerDetailAsOne(_selectedList), _selector); }
        return _foreignPlayerDetailAsOneLoader;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<Player> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
