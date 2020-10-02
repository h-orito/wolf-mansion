package com.ort.dbflute.bsbhv.loader;

import java.util.List;

import org.dbflute.bhv.*;
import org.dbflute.bhv.referrer.*;
import com.ort.dbflute.exbhv.*;
import com.ort.dbflute.exentity.*;
import com.ort.dbflute.cbean.*;

/**
 * The referrer loader of MESSAGE_TYPE as TABLE. <br>
 * <pre>
 * [primary key]
 *     MESSAGE_TYPE_CODE
 *
 * [column]
 *     MESSAGE_TYPE_CODE, MESSAGE_TYPE_NAME
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
 *     MESSAGE, MESSAGE_RESTRICTION, NORMAL_SAY_RESTRICTION, SKILL_SAY_RESTRICTION
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     messageList, messageRestrictionList, normalSayRestrictionList, skillSayRestrictionList
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public class LoaderOfMessageType {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected List<MessageType> _selectedList;
    protected BehaviorSelector _selector;
    protected MessageTypeBhv _myBhv; // lazy-loaded

    // ===================================================================================
    //                                                                   Ready for Loading
    //                                                                   =================
    public LoaderOfMessageType ready(List<MessageType> selectedList, BehaviorSelector selector)
    { _selectedList = selectedList; _selector = selector; return this; }

    protected MessageTypeBhv myBhv()
    { if (_myBhv != null) { return _myBhv; } else { _myBhv = _selector.select(MessageTypeBhv.class); return _myBhv; } }

    // ===================================================================================
    //                                                                       Load Referrer
    //                                                                       =============
    protected List<Message> _referrerMessage;

    /**
     * Load referrer of messageList by the set-upper of referrer. <br>
     * MESSAGE by MESSAGE_TYPE_CODE, named 'messageList'.
     * <pre>
     * <span style="color: #0000C0">messageTypeBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">messageTypeList</span>, <span style="color: #553000">typeLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">typeLoader</span>.<span style="color: #CC4747">loadMessage</span>(<span style="color: #553000">messageCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">messageCB</span>.setupSelect...
     *         <span style="color: #553000">messageCB</span>.query().set...
     *         <span style="color: #553000">messageCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">messageLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    messageLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (MessageType messageType : <span style="color: #553000">messageTypeList</span>) {
     *     ... = messageType.<span style="color: #CC4747">getMessageList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setMessageTypeCode_InScope(pkList);
     * cb.query().addOrderBy_MessageTypeCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessage> loadMessage(ReferrerConditionSetupper<MessageCB> refCBLambda) {
        myBhv().loadMessage(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessage = refLs);
        return hd -> hd.handle(new LoaderOfMessage().ready(_referrerMessage, _selector));
    }

    protected List<MessageRestriction> _referrerMessageRestriction;

    /**
     * Load referrer of messageRestrictionList by the set-upper of referrer. <br>
     * MESSAGE_RESTRICTION by MESSAGE_TYPE_CODE, named 'messageRestrictionList'.
     * <pre>
     * <span style="color: #0000C0">messageTypeBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">messageTypeList</span>, <span style="color: #553000">typeLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">typeLoader</span>.<span style="color: #CC4747">loadMessageRestriction</span>(<span style="color: #553000">restrictionCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">restrictionCB</span>.setupSelect...
     *         <span style="color: #553000">restrictionCB</span>.query().set...
     *         <span style="color: #553000">restrictionCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">restrictionLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    restrictionLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (MessageType messageType : <span style="color: #553000">messageTypeList</span>) {
     *     ... = messageType.<span style="color: #CC4747">getMessageRestrictionList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setMessageTypeCode_InScope(pkList);
     * cb.query().addOrderBy_MessageTypeCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfMessageRestriction> loadMessageRestriction(ReferrerConditionSetupper<MessageRestrictionCB> refCBLambda) {
        myBhv().loadMessageRestriction(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerMessageRestriction = refLs);
        return hd -> hd.handle(new LoaderOfMessageRestriction().ready(_referrerMessageRestriction, _selector));
    }

    protected List<NormalSayRestriction> _referrerNormalSayRestriction;

    /**
     * Load referrer of normalSayRestrictionList by the set-upper of referrer. <br>
     * NORMAL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'normalSayRestrictionList'.
     * <pre>
     * <span style="color: #0000C0">messageTypeBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">messageTypeList</span>, <span style="color: #553000">typeLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">typeLoader</span>.<span style="color: #CC4747">loadNormalSayRestriction</span>(<span style="color: #553000">restrictionCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">restrictionCB</span>.setupSelect...
     *         <span style="color: #553000">restrictionCB</span>.query().set...
     *         <span style="color: #553000">restrictionCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">restrictionLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    restrictionLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (MessageType messageType : <span style="color: #553000">messageTypeList</span>) {
     *     ... = messageType.<span style="color: #CC4747">getNormalSayRestrictionList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setMessageTypeCode_InScope(pkList);
     * cb.query().addOrderBy_MessageTypeCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfNormalSayRestriction> loadNormalSayRestriction(ReferrerConditionSetupper<NormalSayRestrictionCB> refCBLambda) {
        myBhv().loadNormalSayRestriction(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerNormalSayRestriction = refLs);
        return hd -> hd.handle(new LoaderOfNormalSayRestriction().ready(_referrerNormalSayRestriction, _selector));
    }

    protected List<SkillSayRestriction> _referrerSkillSayRestriction;

    /**
     * Load referrer of skillSayRestrictionList by the set-upper of referrer. <br>
     * SKILL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'skillSayRestrictionList'.
     * <pre>
     * <span style="color: #0000C0">messageTypeBhv</span>.<span style="color: #994747">load</span>(<span style="color: #553000">messageTypeList</span>, <span style="color: #553000">typeLoader</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">typeLoader</span>.<span style="color: #CC4747">loadSkillSayRestriction</span>(<span style="color: #553000">restrictionCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *         <span style="color: #553000">restrictionCB</span>.setupSelect...
     *         <span style="color: #553000">restrictionCB</span>.query().set...
     *         <span style="color: #553000">restrictionCB</span>.query().addOrderBy...
     *     }); <span style="color: #3F7E5E">// you can load nested referrer from here</span>
     *     <span style="color: #3F7E5E">//}).withNestedReferrer(<span style="color: #553000">restrictionLoader</span> -&gt; {</span>
     *     <span style="color: #3F7E5E">//    restrictionLoader.load...</span>
     *     <span style="color: #3F7E5E">//});</span>
     * });
     * for (MessageType messageType : <span style="color: #553000">messageTypeList</span>) {
     *     ... = messageType.<span style="color: #CC4747">getSkillSayRestrictionList()</span>;
     * }
     * </pre>
     * About internal policy, the value of primary key (and others too) is treated as case-insensitive. <br>
     * The condition-bean, which the set-upper provides, has settings before callback as follows:
     * <pre>
     * cb.query().setMessageTypeCode_InScope(pkList);
     * cb.query().addOrderBy_MessageTypeCode_Asc();
     * </pre>
     * @param refCBLambda The callback to set up referrer condition-bean for loading referrer. (NotNull)
     * @return The callback interface which you can load nested referrer by calling withNestedReferrer(). (NotNull)
     */
    public NestedReferrerLoaderGateway<LoaderOfSkillSayRestriction> loadSkillSayRestriction(ReferrerConditionSetupper<SkillSayRestrictionCB> refCBLambda) {
        myBhv().loadSkillSayRestriction(_selectedList, refCBLambda).withNestedReferrer(refLs -> _referrerSkillSayRestriction = refLs);
        return hd -> hd.handle(new LoaderOfSkillSayRestriction().ready(_referrerSkillSayRestriction, _selector));
    }

    // ===================================================================================
    //                                                                    Pull out Foreign
    //                                                                    ================
    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public List<MessageType> getSelectedList() { return _selectedList; }
    public BehaviorSelector getSelector() { return _selector; }
}
