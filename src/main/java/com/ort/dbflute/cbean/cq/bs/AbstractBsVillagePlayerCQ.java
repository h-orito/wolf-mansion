package com.ort.dbflute.cbean.cq.bs;

import java.util.*;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.ckey.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.ordering.*;
import org.dbflute.cbean.scoping.*;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.dbmeta.DBMetaProvider;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The abstract condition-query of village_player.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVillagePlayerCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVillagePlayerCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    @Override
    protected DBMetaProvider xgetDBMetaProvider() {
        return DBMetaInstanceHandler.getProvider();
    }

    public String asTableDbName() {
        return "village_player";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerId The value of villagePlayerId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_Equal(Integer villagePlayerId) {
        doSetVillagePlayerId_Equal(villagePlayerId);
    }

    protected void doSetVillagePlayerId_Equal(Integer villagePlayerId) {
        regVillagePlayerId(CK_EQ, villagePlayerId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerId The value of villagePlayerId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_NotEqual(Integer villagePlayerId) {
        doSetVillagePlayerId_NotEqual(villagePlayerId);
    }

    protected void doSetVillagePlayerId_NotEqual(Integer villagePlayerId) {
        regVillagePlayerId(CK_NES, villagePlayerId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerId The value of villagePlayerId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_GreaterThan(Integer villagePlayerId) {
        regVillagePlayerId(CK_GT, villagePlayerId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerId The value of villagePlayerId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_LessThan(Integer villagePlayerId) {
        regVillagePlayerId(CK_LT, villagePlayerId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerId The value of villagePlayerId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_GreaterEqual(Integer villagePlayerId) {
        regVillagePlayerId(CK_GE, villagePlayerId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerId The value of villagePlayerId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_LessEqual(Integer villagePlayerId) {
        regVillagePlayerId(CK_LE, villagePlayerId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of villagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setVillagePlayerId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setVillagePlayerId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of villagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillagePlayerId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillagePlayerId(), "VILLAGE_PLAYER_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerIdList The collection of villagePlayerId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerId_InScope(Collection<Integer> villagePlayerIdList) {
        doSetVillagePlayerId_InScope(villagePlayerIdList);
    }

    protected void doSetVillagePlayerId_InScope(Collection<Integer> villagePlayerIdList) {
        regINS(CK_INS, cTL(villagePlayerIdList), xgetCValueVillagePlayerId(), "VILLAGE_PLAYER_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villagePlayerIdList The collection of villagePlayerId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerId_NotInScope(Collection<Integer> villagePlayerIdList) {
        doSetVillagePlayerId_NotInScope(villagePlayerIdList);
    }

    protected void doSetVillagePlayerId_NotInScope(Collection<Integer> villagePlayerIdList) {
        regINS(CK_NINS, cTL(villagePlayerIdList), xgetCValueVillagePlayerId(), "VILLAGE_PLAYER_ID");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from commit where ...)} <br>
     * commit by VILLAGE_PLAYER_ID, named 'commitAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsCommit</span>(commitCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     commitCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of CommitList for 'exists'. (NotNull)
     */
    public void existsCommit(SubQuery<CommitCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        CommitCB cb = new CommitCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_CommitList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "commitList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_CommitList(CommitCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select TO_VILLAGE_PLAYER_ID from message where ...)} <br>
     * message by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsMessageByToVillagePlayerId</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageByToVillagePlayerIdList for 'exists'. (NotNull)
     */
    public void existsMessageByToVillagePlayerId(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", pp, "messageByToVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from message where ...)} <br>
     * message by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsMessageByVillagePlayerId</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageByVillagePlayerIdList for 'exists'. (NotNull)
     */
    public void existsMessageByVillagePlayerId(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "messageByVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from message_sendto where ...)} <br>
     * message_sendto by VILLAGE_PLAYER_ID, named 'messageSendtoAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsMessageSendto</span>(sendtoCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     sendtoCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageSendtoList for 'exists'. (NotNull)
     */
    public void existsMessageSendto(SubQuery<MessageSendtoCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageSendtoCB cb = new MessageSendtoCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_MessageSendtoList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "messageSendtoList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_MessageSendtoList(MessageSendtoCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from village_player_access_info where ...)} <br>
     * village_player_access_info by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayerAccessInfo</span>(infoCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     infoCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerAccessInfoList for 'exists'. (NotNull)
     */
    public void existsVillagePlayerAccessInfo(SubQuery<VillagePlayerAccessInfoCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerAccessInfoCB cb = new VillagePlayerAccessInfoCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_VillagePlayerAccessInfoList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerAccessInfoList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from village_player_dead_history where ...)} <br>
     * village_player_dead_history by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayerDeadHistory</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerDeadHistoryList for 'exists'. (NotNull)
     */
    public void existsVillagePlayerDeadHistory(SubQuery<VillagePlayerDeadHistoryCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerDeadHistoryCB cb = new VillagePlayerDeadHistoryCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_VillagePlayerDeadHistoryList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerDeadHistoryList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from village_player_room_history where ...)} <br>
     * village_player_room_history by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayerRoomHistory</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerRoomHistoryList for 'exists'. (NotNull)
     */
    public void existsVillagePlayerRoomHistory(SubQuery<VillagePlayerRoomHistoryCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerRoomHistoryCB cb = new VillagePlayerRoomHistoryCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_VillagePlayerRoomHistoryList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerRoomHistoryList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from village_player_skill_history where ...)} <br>
     * village_player_skill_history by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayerSkillHistory</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerSkillHistoryList for 'exists'. (NotNull)
     */
    public void existsVillagePlayerSkillHistory(SubQuery<VillagePlayerSkillHistoryCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerSkillHistoryCB cb = new VillagePlayerSkillHistoryCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_VillagePlayerSkillHistoryList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerSkillHistoryList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select TO_VILLAGE_PLAYER_ID from village_player_status where ...)} <br>
     * village_player_status by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayerStatusByToVillagePlayerId</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerStatusByToVillagePlayerIdList for 'exists'. (NotNull)
     */
    public void existsVillagePlayerStatusByToVillagePlayerId(SubQuery<VillagePlayerStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", pp, "villagePlayerStatusByToVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_PLAYER_ID from village_player_status where ...)} <br>
     * village_player_status by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayerStatusByVillagePlayerId</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerStatusByVillagePlayerIdList for 'exists'. (NotNull)
     */
    public void existsVillagePlayerStatusByVillagePlayerId(SubQuery<VillagePlayerStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerStatusByVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_ExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from commit where ...)} <br>
     * commit by VILLAGE_PLAYER_ID, named 'commitAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsCommit</span>(commitCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     commitCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_CommitList for 'not exists'. (NotNull)
     */
    public void notExistsCommit(SubQuery<CommitCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        CommitCB cb = new CommitCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_CommitList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "commitList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_CommitList(CommitCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select TO_VILLAGE_PLAYER_ID from message where ...)} <br>
     * message by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsMessageByToVillagePlayerId</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList for 'not exists'. (NotNull)
     */
    public void notExistsMessageByToVillagePlayerId(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", pp, "messageByToVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_MessageByToVillagePlayerIdList(MessageCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from message where ...)} <br>
     * message by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsMessageByVillagePlayerId</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList for 'not exists'. (NotNull)
     */
    public void notExistsMessageByVillagePlayerId(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "messageByVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_MessageByVillagePlayerIdList(MessageCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from message_sendto where ...)} <br>
     * message_sendto by VILLAGE_PLAYER_ID, named 'messageSendtoAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsMessageSendto</span>(sendtoCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     sendtoCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_MessageSendtoList for 'not exists'. (NotNull)
     */
    public void notExistsMessageSendto(SubQuery<MessageSendtoCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageSendtoCB cb = new MessageSendtoCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_MessageSendtoList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "messageSendtoList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_MessageSendtoList(MessageSendtoCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from village_player_access_info where ...)} <br>
     * village_player_access_info by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayerAccessInfo</span>(infoCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     infoCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_VillagePlayerAccessInfoList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayerAccessInfo(SubQuery<VillagePlayerAccessInfoCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerAccessInfoCB cb = new VillagePlayerAccessInfoCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_VillagePlayerAccessInfoList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerAccessInfoList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from village_player_dead_history where ...)} <br>
     * village_player_dead_history by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayerDeadHistory</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayerDeadHistory(SubQuery<VillagePlayerDeadHistoryCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerDeadHistoryCB cb = new VillagePlayerDeadHistoryCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerDeadHistoryList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from village_player_room_history where ...)} <br>
     * village_player_room_history by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayerRoomHistory</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayerRoomHistory(SubQuery<VillagePlayerRoomHistoryCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerRoomHistoryCB cb = new VillagePlayerRoomHistoryCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerRoomHistoryList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from village_player_skill_history where ...)} <br>
     * village_player_skill_history by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayerSkillHistory</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_VillagePlayerSkillHistoryList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayerSkillHistory(SubQuery<VillagePlayerSkillHistoryCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerSkillHistoryCB cb = new VillagePlayerSkillHistoryCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_VillagePlayerSkillHistoryList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerSkillHistoryList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select TO_VILLAGE_PLAYER_ID from village_player_status where ...)} <br>
     * village_player_status by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayerStatusByToVillagePlayerId</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayerStatusByToVillagePlayerId(SubQuery<VillagePlayerStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", pp, "villagePlayerStatusByToVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_PLAYER_ID from village_player_status where ...)} <br>
     * village_player_status by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayerStatusByVillagePlayerId</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayerStatusByVillagePlayerId(SubQuery<VillagePlayerStatusCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerStatusByVillagePlayerIdList");
    }
    public abstract String keepVillagePlayerId_NotExistsReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq);

    public void xsderiveCommitList(String fn, SubQuery<CommitCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CommitCB cb = new CommitCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_CommitList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "commitList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_CommitList(CommitCQ sq);

    public void xsderiveMessageByToVillagePlayerIdList(String fn, SubQuery<MessageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", pp, "messageByToVillagePlayerIdList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq);

    public void xsderiveMessageByVillagePlayerIdList(String fn, SubQuery<MessageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "messageByVillagePlayerIdList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq);

    public void xsderiveMessageSendtoList(String fn, SubQuery<MessageSendtoCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageSendtoCB cb = new MessageSendtoCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_MessageSendtoList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "messageSendtoList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_MessageSendtoList(MessageSendtoCQ sq);

    public void xsderiveVillagePlayerAccessInfoList(String fn, SubQuery<VillagePlayerAccessInfoCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerAccessInfoCB cb = new VillagePlayerAccessInfoCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerAccessInfoList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerAccessInfoList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq);

    public void xsderiveVillagePlayerDeadHistoryList(String fn, SubQuery<VillagePlayerDeadHistoryCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerDeadHistoryCB cb = new VillagePlayerDeadHistoryCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerDeadHistoryList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerDeadHistoryList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq);

    public void xsderiveVillagePlayerRoomHistoryList(String fn, SubQuery<VillagePlayerRoomHistoryCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerRoomHistoryCB cb = new VillagePlayerRoomHistoryCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerRoomHistoryList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerRoomHistoryList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq);

    public void xsderiveVillagePlayerSkillHistoryList(String fn, SubQuery<VillagePlayerSkillHistoryCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerSkillHistoryCB cb = new VillagePlayerSkillHistoryCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerSkillHistoryList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerSkillHistoryList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq);

    public void xsderiveVillagePlayerStatusByToVillagePlayerIdList(String fn, SubQuery<VillagePlayerStatusCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", pp, "villagePlayerStatusByToVillagePlayerIdList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq);

    public void xsderiveVillagePlayerStatusByVillagePlayerIdList(String fn, SubQuery<VillagePlayerStatusCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", pp, "villagePlayerStatusByVillagePlayerIdList", al, op);
    }
    public abstract String keepVillagePlayerId_SpecifyDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from commit where ...)} <br>
     * commit by VILLAGE_PLAYER_ID, named 'commitAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedCommit()</span>.<span style="color: #CC4747">max</span>(commitCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     commitCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     commitCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<CommitCB> derivedCommit() {
        return xcreateQDRFunctionCommitList();
    }
    protected HpQDRFunction<CommitCB> xcreateQDRFunctionCommitList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveCommitList(fn, sq, rd, vl, op));
    }
    public void xqderiveCommitList(String fn, SubQuery<CommitCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CommitCB cb = new CommitCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_CommitList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_CommitListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "commitList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_CommitList(CommitCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_CommitListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from message where ...)} <br>
     * message by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedMessageByToVillagePlayerId()</span>.<span style="color: #CC4747">max</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     messageCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<MessageCB> derivedMessageByToVillagePlayerId() {
        return xcreateQDRFunctionMessageByToVillagePlayerIdList();
    }
    protected HpQDRFunction<MessageCB> xcreateQDRFunctionMessageByToVillagePlayerIdList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveMessageByToVillagePlayerIdList(fn, sq, rd, vl, op));
    }
    public void xqderiveMessageByToVillagePlayerIdList(String fn, SubQuery<MessageCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", sqpp, "messageByToVillagePlayerIdList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdList(MessageCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_MessageByToVillagePlayerIdListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from message where ...)} <br>
     * message by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedMessageByVillagePlayerId()</span>.<span style="color: #CC4747">max</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     messageCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<MessageCB> derivedMessageByVillagePlayerId() {
        return xcreateQDRFunctionMessageByVillagePlayerIdList();
    }
    protected HpQDRFunction<MessageCB> xcreateQDRFunctionMessageByVillagePlayerIdList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveMessageByVillagePlayerIdList(fn, sq, rd, vl, op));
    }
    public void xqderiveMessageByVillagePlayerIdList(String fn, SubQuery<MessageCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "messageByVillagePlayerIdList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdList(MessageCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_MessageByVillagePlayerIdListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from message_sendto where ...)} <br>
     * message_sendto by VILLAGE_PLAYER_ID, named 'messageSendtoAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedMessageSendto()</span>.<span style="color: #CC4747">max</span>(sendtoCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     sendtoCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     sendtoCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<MessageSendtoCB> derivedMessageSendto() {
        return xcreateQDRFunctionMessageSendtoList();
    }
    protected HpQDRFunction<MessageSendtoCB> xcreateQDRFunctionMessageSendtoList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveMessageSendtoList(fn, sq, rd, vl, op));
    }
    public void xqderiveMessageSendtoList(String fn, SubQuery<MessageSendtoCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageSendtoCB cb = new MessageSendtoCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "messageSendtoList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoList(MessageSendtoCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_MessageSendtoListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player_access_info where ...)} <br>
     * village_player_access_info by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayerAccessInfo()</span>.<span style="color: #CC4747">max</span>(infoCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     infoCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     infoCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerAccessInfoCB> derivedVillagePlayerAccessInfo() {
        return xcreateQDRFunctionVillagePlayerAccessInfoList();
    }
    protected HpQDRFunction<VillagePlayerAccessInfoCB> xcreateQDRFunctionVillagePlayerAccessInfoList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerAccessInfoList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerAccessInfoList(String fn, SubQuery<VillagePlayerAccessInfoCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerAccessInfoCB cb = new VillagePlayerAccessInfoCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerAccessInfoList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerAccessInfoListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "villagePlayerAccessInfoList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerAccessInfoList(VillagePlayerAccessInfoCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerAccessInfoListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player_dead_history where ...)} <br>
     * village_player_dead_history by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayerDeadHistory()</span>.<span style="color: #CC4747">max</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     historyCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerDeadHistoryCB> derivedVillagePlayerDeadHistory() {
        return xcreateQDRFunctionVillagePlayerDeadHistoryList();
    }
    protected HpQDRFunction<VillagePlayerDeadHistoryCB> xcreateQDRFunctionVillagePlayerDeadHistoryList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerDeadHistoryList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerDeadHistoryList(String fn, SubQuery<VillagePlayerDeadHistoryCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerDeadHistoryCB cb = new VillagePlayerDeadHistoryCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "villagePlayerDeadHistoryList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryList(VillagePlayerDeadHistoryCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerDeadHistoryListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player_room_history where ...)} <br>
     * village_player_room_history by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayerRoomHistory()</span>.<span style="color: #CC4747">max</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     historyCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerRoomHistoryCB> derivedVillagePlayerRoomHistory() {
        return xcreateQDRFunctionVillagePlayerRoomHistoryList();
    }
    protected HpQDRFunction<VillagePlayerRoomHistoryCB> xcreateQDRFunctionVillagePlayerRoomHistoryList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerRoomHistoryList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerRoomHistoryList(String fn, SubQuery<VillagePlayerRoomHistoryCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerRoomHistoryCB cb = new VillagePlayerRoomHistoryCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "villagePlayerRoomHistoryList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryList(VillagePlayerRoomHistoryCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerRoomHistoryListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player_skill_history where ...)} <br>
     * village_player_skill_history by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayerSkillHistory()</span>.<span style="color: #CC4747">max</span>(historyCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     historyCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     historyCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerSkillHistoryCB> derivedVillagePlayerSkillHistory() {
        return xcreateQDRFunctionVillagePlayerSkillHistoryList();
    }
    protected HpQDRFunction<VillagePlayerSkillHistoryCB> xcreateQDRFunctionVillagePlayerSkillHistoryList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerSkillHistoryList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerSkillHistoryList(String fn, SubQuery<VillagePlayerSkillHistoryCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerSkillHistoryCB cb = new VillagePlayerSkillHistoryCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerSkillHistoryList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerSkillHistoryListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "villagePlayerSkillHistoryList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerSkillHistoryList(VillagePlayerSkillHistoryCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerSkillHistoryListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player_status where ...)} <br>
     * village_player_status by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayerStatusByToVillagePlayerId()</span>.<span style="color: #CC4747">max</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     statusCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerStatusCB> derivedVillagePlayerStatusByToVillagePlayerId() {
        return xcreateQDRFunctionVillagePlayerStatusByToVillagePlayerIdList();
    }
    protected HpQDRFunction<VillagePlayerStatusCB> xcreateQDRFunctionVillagePlayerStatusByToVillagePlayerIdList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerStatusByToVillagePlayerIdList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerStatusByToVillagePlayerIdList(String fn, SubQuery<VillagePlayerStatusCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", sqpp, "villagePlayerStatusByToVillagePlayerIdList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdList(VillagePlayerStatusCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByToVillagePlayerIdListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from village_player_status where ...)} <br>
     * village_player_status by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayerStatusByVillagePlayerId()</span>.<span style="color: #CC4747">max</span>(statusCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     statusCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     statusCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerStatusCB> derivedVillagePlayerStatusByVillagePlayerId() {
        return xcreateQDRFunctionVillagePlayerStatusByVillagePlayerIdList();
    }
    protected HpQDRFunction<VillagePlayerStatusCB> xcreateQDRFunctionVillagePlayerStatusByVillagePlayerIdList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerStatusByVillagePlayerIdList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerStatusByVillagePlayerIdList(String fn, SubQuery<VillagePlayerStatusCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerStatusCB cb = new VillagePlayerStatusCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(cb.query()); String prpp = keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", sqpp, "villagePlayerStatusByVillagePlayerIdList", rd, vl, prpp, op);
    }
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdList(VillagePlayerStatusCQ sq);
    public abstract String keepVillagePlayerId_QueryDerivedReferrer_VillagePlayerStatusByVillagePlayerIdListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setVillagePlayerId_IsNull() { regVillagePlayerId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setVillagePlayerId_IsNotNull() { regVillagePlayerId(CK_ISNN, DOBJ); }

    protected void regVillagePlayerId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillagePlayerId(), "VILLAGE_PLAYER_ID"); }
    protected abstract ConditionValue xgetCValueVillagePlayerId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_Equal(Integer villageId) {
        doSetVillageId_Equal(villageId);
    }

    protected void doSetVillageId_Equal(Integer villageId) {
        regVillageId(CK_EQ, villageId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_NotEqual(Integer villageId) {
        doSetVillageId_NotEqual(villageId);
    }

    protected void doSetVillageId_NotEqual(Integer villageId) {
        regVillageId(CK_NES, villageId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setVillageId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageIdList The collection of villageId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageId_InScope(Collection<Integer> villageIdList) {
        doSetVillageId_InScope(villageIdList);
    }

    protected void doSetVillageId_InScope(Collection<Integer> villageIdList) {
        regINS(CK_INS, cTL(villageIdList), xgetCValueVillageId(), "VILLAGE_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village}
     * @param villageIdList The collection of villageId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageId_NotInScope(Collection<Integer> villageIdList) {
        doSetVillageId_NotInScope(villageIdList);
    }

    protected void doSetVillageId_NotInScope(Collection<Integer> villageIdList) {
        regINS(CK_NINS, cTL(villageIdList), xgetCValueVillageId(), "VILLAGE_ID");
    }

    protected void regVillageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageId(), "VILLAGE_ID"); }
    protected abstract ConditionValue xgetCValueVillageId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerId The value of playerId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_Equal(Integer playerId) {
        doSetPlayerId_Equal(playerId);
    }

    protected void doSetPlayerId_Equal(Integer playerId) {
        regPlayerId(CK_EQ, playerId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerId The value of playerId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_NotEqual(Integer playerId) {
        doSetPlayerId_NotEqual(playerId);
    }

    protected void doSetPlayerId_NotEqual(Integer playerId) {
        regPlayerId(CK_NES, playerId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerId The value of playerId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_GreaterThan(Integer playerId) {
        regPlayerId(CK_GT, playerId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerId The value of playerId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_LessThan(Integer playerId) {
        regPlayerId(CK_LT, playerId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerId The value of playerId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_GreaterEqual(Integer playerId) {
        regPlayerId(CK_GE, playerId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerId The value of playerId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_LessEqual(Integer playerId) {
        regPlayerId(CK_LE, playerId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param minNumber The min number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setPlayerId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setPlayerId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param minNumber The min number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setPlayerId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValuePlayerId(), "PLAYER_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerIdList The collection of playerId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setPlayerId_InScope(Collection<Integer> playerIdList) {
        doSetPlayerId_InScope(playerIdList);
    }

    protected void doSetPlayerId_InScope(Collection<Integer> playerIdList) {
        regINS(CK_INS, cTL(playerIdList), xgetCValuePlayerId(), "PLAYER_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player}
     * @param playerIdList The collection of playerId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setPlayerId_NotInScope(Collection<Integer> playerIdList) {
        doSetPlayerId_NotInScope(playerIdList);
    }

    protected void doSetPlayerId_NotInScope(Collection<Integer> playerIdList) {
        regINS(CK_NINS, cTL(playerIdList), xgetCValuePlayerId(), "PLAYER_ID");
    }

    protected void regPlayerId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValuePlayerId(), "PLAYER_ID"); }
    protected abstract ConditionValue xgetCValuePlayerId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_Equal(Integer charaId) {
        doSetCharaId_Equal(charaId);
    }

    protected void doSetCharaId_Equal(Integer charaId) {
        regCharaId(CK_EQ, charaId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_NotEqual(Integer charaId) {
        doSetCharaId_NotEqual(charaId);
    }

    protected void doSetCharaId_NotEqual(Integer charaId) {
        regCharaId(CK_NES, charaId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterThan(Integer charaId) {
        regCharaId(CK_GT, charaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessThan(Integer charaId) {
        regCharaId(CK_LT, charaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterEqual(Integer charaId) {
        regCharaId(CK_GE, charaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaId The value of charaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessEqual(Integer charaId) {
        regCharaId(CK_LE, charaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setCharaId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setCharaId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueCharaId(), "CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaIdList The collection of charaId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaId_InScope(Collection<Integer> charaIdList) {
        doSetCharaId_InScope(charaIdList);
    }

    protected void doSetCharaId_InScope(Collection<Integer> charaIdList) {
        regINS(CK_INS, cTL(charaIdList), xgetCValueCharaId(), "CHARA_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @param charaIdList The collection of charaId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaId_NotInScope(Collection<Integer> charaIdList) {
        doSetCharaId_NotInScope(charaIdList);
    }

    protected void doSetCharaId_NotInScope(Collection<Integer> charaIdList) {
        regINS(CK_NINS, cTL(charaIdList), xgetCValueCharaId(), "CHARA_ID");
    }

    protected void regCharaId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaId(), "CHARA_ID"); }
    protected abstract ConditionValue xgetCValueCharaId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCode The value of skillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_Equal(String skillCode) {
        doSetSkillCode_Equal(fRES(skillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setSkillCode_Equal_教唆者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * Equal(=). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSkillCode_Equal_絶対人狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Equal(=). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setSkillCode_Equal_占星術師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * Equal(=). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setSkillCode_Equal_ババ() {
        setSkillCode_Equal_AsSkill(CDef.Skill.ババ);
    }

    /**
     * Equal(=). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setSkillCode_Equal_美人局() {
        setSkillCode_Equal_AsSkill(CDef.Skill.美人局);
    }

    /**
     * Equal(=). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setSkillCode_Equal_パン屋() {
        setSkillCode_Equal_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * Equal(=). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setSkillCode_Equal_バールのようなもの() {
        setSkillCode_Equal_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Equal(=). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setSkillCode_Equal_黒箱者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Equal(=). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSkillCode_Equal_爆弾魔() {
        setSkillCode_Equal_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Equal(=). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSkillCode_Equal_誑狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * Equal(=). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setSkillCode_Equal_C国狂人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Equal(=). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setSkillCode_Equal_同棲者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * Equal(=). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setSkillCode_Equal_指揮官() {
        setSkillCode_Equal_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * Equal(=). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setSkillCode_Equal_検死官() {
        setSkillCode_Equal_AsSkill(CDef.Skill.検死官);
    }

    /**
     * Equal(=). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setSkillCode_Equal_求愛者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * Equal(=). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSkillCode_Equal_おまかせ愉快犯陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Equal(=). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setSkillCode_Equal_呪狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * Equal(=). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setSkillCode_Equal_探偵() {
        setSkillCode_Equal_AsSkill(CDef.Skill.探偵);
    }

    /**
     * Equal(=). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSkillCode_Equal_不止者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.不止者);
    }

    /**
     * Equal(=). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSkillCode_Equal_魔神官() {
        setSkillCode_Equal_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * Equal(=). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setSkillCode_Equal_執行人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.執行人);
    }

    /**
     * Equal(=). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setSkillCode_Equal_冤罪者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Equal(=). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setSkillCode_Equal_狂信者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * Equal(=). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setSkillCode_Equal_妄想癖() {
        setSkillCode_Equal_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Equal(=). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setSkillCode_Equal_花占い師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * Equal(=). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setSkillCode_Equal_おまかせ足音職() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Equal(=). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setSkillCode_Equal_妖狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * Equal(=). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setSkillCode_Equal_おまかせ妖狐陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Equal(=). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setSkillCode_Equal_おまかせ役職窓あり() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Equal(=). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setSkillCode_Equal_果実籠() {
        setSkillCode_Equal_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * Equal(=). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setSkillCode_Equal_歩狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * Equal(=). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setSkillCode_Equal_銀狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * Equal(=). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setSkillCode_Equal_ごん() {
        setSkillCode_Equal_AsSkill(CDef.Skill.ごん);
    }

    /**
     * Equal(=). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSkillCode_Equal_導師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.導師);
    }

    /**
     * Equal(=). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setSkillCode_Equal_堅狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.堅狼);
    }

    /**
     * Equal(=). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setSkillCode_Equal_申し子() {
        setSkillCode_Equal_AsSkill(CDef.Skill.申し子);
    }

    /**
     * Equal(=). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setSkillCode_Equal_仙狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * Equal(=). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSkillCode_Equal_飛狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * Equal(=). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setSkillCode_Equal_狩人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.狩人);
    }

    /**
     * Equal(=). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setSkillCode_Equal_背徳者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * Equal(=). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setSkillCode_Equal_稲荷() {
        setSkillCode_Equal_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * Equal(=). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSkillCode_Equal_煽動者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * Equal(=). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setSkillCode_Equal_保険屋() {
        setSkillCode_Equal_AsSkill(CDef.Skill.保険屋);
    }

    /**
     * Equal(=). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setSkillCode_Equal_絡新婦() {
        setSkillCode_Equal_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Equal(=). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setSkillCode_Equal_角狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.角狼);
    }

    /**
     * Equal(=). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setSkillCode_Equal_王狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.王狼);
    }

    /**
     * Equal(=). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setSkillCode_Equal_金狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.金狼);
    }

    /**
     * Equal(=). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setSkillCode_Equal_管狐() {
        setSkillCode_Equal_AsSkill(CDef.Skill.管狐);
    }

    /**
     * Equal(=). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setSkillCode_Equal_弁護士() {
        setSkillCode_Equal_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * Equal(=). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSkillCode_Equal_おまかせ() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Equal(=). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setSkillCode_Equal_聴狂人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Equal(=). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setSkillCode_Equal_黙狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.黙狼);
    }

    /**
     * Equal(=). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setSkillCode_Equal_一匹狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Equal(=). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setSkillCode_Equal_拡声者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * Equal(=). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setSkillCode_Equal_恋人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.恋人);
    }

    /**
     * Equal(=). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setSkillCode_Equal_おまかせ恋人陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Equal(=). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setSkillCode_Equal_強運者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.強運者);
    }

    /**
     * Equal(=). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setSkillCode_Equal_狂人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.狂人);
    }

    /**
     * Equal(=). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setSkillCode_Equal_共鳴者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Equal(=). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setSkillCode_Equal_マタギ() {
        setSkillCode_Equal_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * Equal(=). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setSkillCode_Equal_市長() {
        setSkillCode_Equal_AsSkill(CDef.Skill.市長);
    }

    /**
     * Equal(=). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSkillCode_Equal_霊能者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * Equal(=). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSkillCode_Equal_死霊術師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Equal(=). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_Equal_おまかせ役職窓なし() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Equal(=). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setSkillCode_Equal_おまかせ人外() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Equal(=). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setSkillCode_Equal_監視者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.監視者);
    }

    /**
     * Equal(=). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSkillCode_Equal_梟() {
        setSkillCode_Equal_AsSkill(CDef.Skill.梟);
    }

    /**
     * Equal(=). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setSkillCode_Equal_牧師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.牧師);
    }

    /**
     * Equal(=). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSkillCode_Equal_虹職人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * Equal(=). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSkillCode_Equal_転生者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.転生者);
    }

    /**
     * Equal(=). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setSkillCode_Equal_怨恨者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Equal(=). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSkillCode_Equal_蘇生者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Equal(=). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSkillCode_Equal_占い師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.占い師);
    }

    /**
     * Equal(=). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setSkillCode_Equal_破局者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.破局者);
    }

    /**
     * Equal(=). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setSkillCode_Equal_静狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.静狼);
    }

    /**
     * Equal(=). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setSkillCode_Equal_感覚者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * Equal(=). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSkillCode_Equal_夢遊病者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Equal(=). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setSkillCode_Equal_防音者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.防音者);
    }

    /**
     * Equal(=). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setSkillCode_Equal_ストーカー() {
        setSkillCode_Equal_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Equal(=). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setSkillCode_Equal_濁点者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * Equal(=). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setSkillCode_Equal_泥棒猫() {
        setSkillCode_Equal_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Equal(=). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setSkillCode_Equal_翻訳者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Equal(=). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSkillCode_Equal_罠師() {
        setSkillCode_Equal_AsSkill(CDef.Skill.罠師);
    }

    /**
     * Equal(=). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setSkillCode_Equal_トラック() {
        setSkillCode_Equal_AsSkill(CDef.Skill.トラック);
    }

    /**
     * Equal(=). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setSkillCode_Equal_村人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.村人);
    }

    /**
     * Equal(=). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setSkillCode_Equal_おまかせ村人陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Equal(=). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setSkillCode_Equal_壁殴り代行() {
        setSkillCode_Equal_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Equal(=). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setSkillCode_Equal_風来狩人() {
        setSkillCode_Equal_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Equal(=). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setSkillCode_Equal_人狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.人狼);
    }

    /**
     * Equal(=). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setSkillCode_Equal_おまかせ人狼陣営() {
        setSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Equal(=). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setSkillCode_Equal_当選者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.当選者);
    }

    /**
     * Equal(=). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setSkillCode_Equal_賢者() {
        setSkillCode_Equal_AsSkill(CDef.Skill.賢者);
    }

    /**
     * Equal(=). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setSkillCode_Equal_智狼() {
        setSkillCode_Equal_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetSkillCode_Equal(String skillCode) {
        regSkillCode(CK_EQ, skillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCode The value of skillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotEqual(String skillCode) {
        doSetSkillCode_NotEqual(fRES(skillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setSkillCode_NotEqual_教唆者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * NotEqual(&lt;&gt;). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSkillCode_NotEqual_絶対人狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setSkillCode_NotEqual_占星術師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * NotEqual(&lt;&gt;). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setSkillCode_NotEqual_ババ() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.ババ);
    }

    /**
     * NotEqual(&lt;&gt;). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setSkillCode_NotEqual_美人局() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.美人局);
    }

    /**
     * NotEqual(&lt;&gt;). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setSkillCode_NotEqual_パン屋() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * NotEqual(&lt;&gt;). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setSkillCode_NotEqual_バールのようなもの() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * NotEqual(&lt;&gt;). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setSkillCode_NotEqual_黒箱者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSkillCode_NotEqual_爆弾魔() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * NotEqual(&lt;&gt;). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSkillCode_NotEqual_誑狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * NotEqual(&lt;&gt;). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setSkillCode_NotEqual_C国狂人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setSkillCode_NotEqual_同棲者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * NotEqual(&lt;&gt;). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setSkillCode_NotEqual_指揮官() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * NotEqual(&lt;&gt;). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setSkillCode_NotEqual_検死官() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.検死官);
    }

    /**
     * NotEqual(&lt;&gt;). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setSkillCode_NotEqual_求愛者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSkillCode_NotEqual_おまかせ愉快犯陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setSkillCode_NotEqual_呪狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setSkillCode_NotEqual_探偵() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.探偵);
    }

    /**
     * NotEqual(&lt;&gt;). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSkillCode_NotEqual_不止者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.不止者);
    }

    /**
     * NotEqual(&lt;&gt;). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSkillCode_NotEqual_魔神官() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * NotEqual(&lt;&gt;). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setSkillCode_NotEqual_執行人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.執行人);
    }

    /**
     * NotEqual(&lt;&gt;). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setSkillCode_NotEqual_冤罪者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setSkillCode_NotEqual_狂信者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * NotEqual(&lt;&gt;). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setSkillCode_NotEqual_妄想癖() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * NotEqual(&lt;&gt;). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setSkillCode_NotEqual_花占い師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setSkillCode_NotEqual_おまかせ足音職() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * NotEqual(&lt;&gt;). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setSkillCode_NotEqual_妖狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setSkillCode_NotEqual_おまかせ妖狐陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setSkillCode_NotEqual_おまかせ役職窓あり() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * NotEqual(&lt;&gt;). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setSkillCode_NotEqual_果実籠() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * NotEqual(&lt;&gt;). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setSkillCode_NotEqual_歩狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setSkillCode_NotEqual_銀狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * NotEqual(&lt;&gt;). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setSkillCode_NotEqual_ごん() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.ごん);
    }

    /**
     * NotEqual(&lt;&gt;). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSkillCode_NotEqual_導師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.導師);
    }

    /**
     * NotEqual(&lt;&gt;). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setSkillCode_NotEqual_堅狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.堅狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setSkillCode_NotEqual_申し子() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.申し子);
    }

    /**
     * NotEqual(&lt;&gt;). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setSkillCode_NotEqual_仙狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSkillCode_NotEqual_飛狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setSkillCode_NotEqual_狩人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setSkillCode_NotEqual_背徳者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setSkillCode_NotEqual_稲荷() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * NotEqual(&lt;&gt;). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSkillCode_NotEqual_煽動者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * NotEqual(&lt;&gt;). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setSkillCode_NotEqual_保険屋() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.保険屋);
    }

    /**
     * NotEqual(&lt;&gt;). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setSkillCode_NotEqual_絡新婦() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * NotEqual(&lt;&gt;). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setSkillCode_NotEqual_角狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.角狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setSkillCode_NotEqual_王狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.王狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setSkillCode_NotEqual_金狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.金狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setSkillCode_NotEqual_管狐() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.管狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setSkillCode_NotEqual_弁護士() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSkillCode_NotEqual_おまかせ() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * NotEqual(&lt;&gt;). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setSkillCode_NotEqual_聴狂人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setSkillCode_NotEqual_黙狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.黙狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setSkillCode_NotEqual_一匹狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setSkillCode_NotEqual_拡声者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setSkillCode_NotEqual_恋人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.恋人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setSkillCode_NotEqual_おまかせ恋人陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setSkillCode_NotEqual_強運者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.強運者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setSkillCode_NotEqual_狂人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setSkillCode_NotEqual_共鳴者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * NotEqual(&lt;&gt;). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setSkillCode_NotEqual_マタギ() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * NotEqual(&lt;&gt;). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setSkillCode_NotEqual_市長() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.市長);
    }

    /**
     * NotEqual(&lt;&gt;). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSkillCode_NotEqual_霊能者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * NotEqual(&lt;&gt;). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSkillCode_NotEqual_死霊術師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSkillCode_NotEqual_おまかせ役職窓なし() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setSkillCode_NotEqual_おまかせ人外() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * NotEqual(&lt;&gt;). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setSkillCode_NotEqual_監視者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.監視者);
    }

    /**
     * NotEqual(&lt;&gt;). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSkillCode_NotEqual_梟() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.梟);
    }

    /**
     * NotEqual(&lt;&gt;). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setSkillCode_NotEqual_牧師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.牧師);
    }

    /**
     * NotEqual(&lt;&gt;). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSkillCode_NotEqual_虹職人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * NotEqual(&lt;&gt;). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSkillCode_NotEqual_転生者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.転生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setSkillCode_NotEqual_怨恨者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * NotEqual(&lt;&gt;). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSkillCode_NotEqual_蘇生者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSkillCode_NotEqual_占い師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setSkillCode_NotEqual_破局者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.破局者);
    }

    /**
     * NotEqual(&lt;&gt;). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setSkillCode_NotEqual_静狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.静狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setSkillCode_NotEqual_感覚者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * NotEqual(&lt;&gt;). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSkillCode_NotEqual_夢遊病者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * NotEqual(&lt;&gt;). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setSkillCode_NotEqual_防音者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.防音者);
    }

    /**
     * NotEqual(&lt;&gt;). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setSkillCode_NotEqual_ストーカー() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * NotEqual(&lt;&gt;). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setSkillCode_NotEqual_濁点者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * NotEqual(&lt;&gt;). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setSkillCode_NotEqual_泥棒猫() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * NotEqual(&lt;&gt;). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setSkillCode_NotEqual_翻訳者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSkillCode_NotEqual_罠師() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.罠師);
    }

    /**
     * NotEqual(&lt;&gt;). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setSkillCode_NotEqual_トラック() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.トラック);
    }

    /**
     * NotEqual(&lt;&gt;). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setSkillCode_NotEqual_村人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.村人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setSkillCode_NotEqual_おまかせ村人陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setSkillCode_NotEqual_壁殴り代行() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * NotEqual(&lt;&gt;). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setSkillCode_NotEqual_風来狩人() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setSkillCode_NotEqual_人狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setSkillCode_NotEqual_おまかせ人狼陣営() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setSkillCode_NotEqual_当選者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.当選者);
    }

    /**
     * NotEqual(&lt;&gt;). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setSkillCode_NotEqual_賢者() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.賢者);
    }

    /**
     * NotEqual(&lt;&gt;). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setSkillCode_NotEqual_智狼() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetSkillCode_NotEqual(String skillCode) {
        regSkillCode(CK_NES, skillCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCodeList The collection of skillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_InScope(Collection<String> skillCodeList) {
        doSetSkillCode_InScope(skillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_InScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSkillCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, C国狂人]
     */
    public void setSkillCode_InScope_AvailableWerewolfSay() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 聴狂人]
     */
    public void setSkillCode_InScope_ViewableWerewolfSay() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 管狐]
     */
    public void setSkillCode_InScope_HasDivineAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     */
    public void setSkillCode_InScope_HasSkillPsychicAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     */
    public void setSkillCode_InScope_HasAttackAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 背徳者]
     */
    public void setSkillCode_InScope_HasDisturbAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 爆弾魔]
     */
    public void setSkillCode_InScope_NoDeadByAttack() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     */
    public void setSkillCode_InScope_WolfCount() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfWolfCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 梟]
     */
    public void setSkillCode_InScope_NoCount() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfNoCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 狂信者, 煽動者]
     */
    public void setSkillCode_InScope_ViewableWolfCharaName() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     */
    public void setSkillCode_InScope_DivineResultWolf() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfDivineResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     */
    public void setSkillCode_InScope_PsychicResultWolf() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfPsychicResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     */
    public void setSkillCode_InScope_SomeoneSkill() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
    }

    protected void doSetSkillCode_InScope(Collection<String> skillCodeList) {
        regINS(CK_INS, cTL(skillCodeList), xgetCValueSkillCode(), "SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param skillCodeList The collection of skillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotInScope(Collection<String> skillCodeList) {
        doSetSkillCode_NotInScope(skillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_NotInScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSkillCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetSkillCode_NotInScope(Collection<String> skillCodeList) {
        regINS(CK_NINS, cTL(skillCodeList), xgetCValueSkillCode(), "SKILL_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSkillCode_IsNull() { regSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSkillCode_IsNullOrEmpty() { regSkillCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSkillCode_IsNotNull() { regSkillCode(CK_ISNN, DOBJ); }

    protected void regSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueSkillCode(), "SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueSkillCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param requestSkillCode The value of requestSkillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_Equal(String requestSkillCode) {
        doSetRequestSkillCode_Equal(fRES(requestSkillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setRequestSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetRequestSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setRequestSkillCode_Equal_教唆者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * Equal(=). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setRequestSkillCode_Equal_絶対人狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Equal(=). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setRequestSkillCode_Equal_占星術師() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * Equal(=). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setRequestSkillCode_Equal_ババ() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.ババ);
    }

    /**
     * Equal(=). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setRequestSkillCode_Equal_美人局() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.美人局);
    }

    /**
     * Equal(=). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setRequestSkillCode_Equal_パン屋() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * Equal(=). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setRequestSkillCode_Equal_バールのようなもの() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Equal(=). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setRequestSkillCode_Equal_黒箱者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Equal(=). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setRequestSkillCode_Equal_爆弾魔() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Equal(=). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setRequestSkillCode_Equal_誑狐() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * Equal(=). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setRequestSkillCode_Equal_C国狂人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Equal(=). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setRequestSkillCode_Equal_同棲者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * Equal(=). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setRequestSkillCode_Equal_指揮官() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * Equal(=). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setRequestSkillCode_Equal_検死官() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.検死官);
    }

    /**
     * Equal(=). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setRequestSkillCode_Equal_求愛者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * Equal(=). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setRequestSkillCode_Equal_おまかせ愉快犯陣営() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Equal(=). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setRequestSkillCode_Equal_呪狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * Equal(=). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setRequestSkillCode_Equal_探偵() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.探偵);
    }

    /**
     * Equal(=). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setRequestSkillCode_Equal_不止者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.不止者);
    }

    /**
     * Equal(=). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setRequestSkillCode_Equal_魔神官() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * Equal(=). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setRequestSkillCode_Equal_執行人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.執行人);
    }

    /**
     * Equal(=). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setRequestSkillCode_Equal_冤罪者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Equal(=). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setRequestSkillCode_Equal_狂信者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * Equal(=). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setRequestSkillCode_Equal_妄想癖() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Equal(=). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setRequestSkillCode_Equal_花占い師() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * Equal(=). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setRequestSkillCode_Equal_おまかせ足音職() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Equal(=). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setRequestSkillCode_Equal_妖狐() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * Equal(=). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setRequestSkillCode_Equal_おまかせ妖狐陣営() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Equal(=). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setRequestSkillCode_Equal_おまかせ役職窓あり() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Equal(=). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setRequestSkillCode_Equal_果実籠() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * Equal(=). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setRequestSkillCode_Equal_歩狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * Equal(=). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setRequestSkillCode_Equal_銀狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * Equal(=). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setRequestSkillCode_Equal_ごん() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.ごん);
    }

    /**
     * Equal(=). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setRequestSkillCode_Equal_導師() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.導師);
    }

    /**
     * Equal(=). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setRequestSkillCode_Equal_堅狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.堅狼);
    }

    /**
     * Equal(=). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setRequestSkillCode_Equal_申し子() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.申し子);
    }

    /**
     * Equal(=). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setRequestSkillCode_Equal_仙狐() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * Equal(=). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setRequestSkillCode_Equal_飛狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * Equal(=). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setRequestSkillCode_Equal_狩人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.狩人);
    }

    /**
     * Equal(=). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setRequestSkillCode_Equal_背徳者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * Equal(=). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setRequestSkillCode_Equal_稲荷() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * Equal(=). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setRequestSkillCode_Equal_煽動者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * Equal(=). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setRequestSkillCode_Equal_保険屋() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.保険屋);
    }

    /**
     * Equal(=). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setRequestSkillCode_Equal_絡新婦() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Equal(=). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setRequestSkillCode_Equal_角狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.角狼);
    }

    /**
     * Equal(=). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setRequestSkillCode_Equal_王狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.王狼);
    }

    /**
     * Equal(=). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setRequestSkillCode_Equal_金狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.金狼);
    }

    /**
     * Equal(=). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setRequestSkillCode_Equal_管狐() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.管狐);
    }

    /**
     * Equal(=). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setRequestSkillCode_Equal_弁護士() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * Equal(=). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setRequestSkillCode_Equal_おまかせ() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Equal(=). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setRequestSkillCode_Equal_聴狂人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Equal(=). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setRequestSkillCode_Equal_黙狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.黙狼);
    }

    /**
     * Equal(=). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setRequestSkillCode_Equal_一匹狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Equal(=). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setRequestSkillCode_Equal_拡声者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * Equal(=). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setRequestSkillCode_Equal_恋人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.恋人);
    }

    /**
     * Equal(=). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setRequestSkillCode_Equal_おまかせ恋人陣営() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Equal(=). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setRequestSkillCode_Equal_強運者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.強運者);
    }

    /**
     * Equal(=). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setRequestSkillCode_Equal_狂人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.狂人);
    }

    /**
     * Equal(=). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setRequestSkillCode_Equal_共鳴者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Equal(=). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setRequestSkillCode_Equal_マタギ() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * Equal(=). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setRequestSkillCode_Equal_市長() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.市長);
    }

    /**
     * Equal(=). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setRequestSkillCode_Equal_霊能者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * Equal(=). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setRequestSkillCode_Equal_死霊術師() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Equal(=). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setRequestSkillCode_Equal_おまかせ役職窓なし() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Equal(=). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setRequestSkillCode_Equal_おまかせ人外() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Equal(=). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setRequestSkillCode_Equal_監視者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.監視者);
    }

    /**
     * Equal(=). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setRequestSkillCode_Equal_梟() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.梟);
    }

    /**
     * Equal(=). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setRequestSkillCode_Equal_牧師() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.牧師);
    }

    /**
     * Equal(=). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setRequestSkillCode_Equal_虹職人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * Equal(=). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setRequestSkillCode_Equal_転生者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.転生者);
    }

    /**
     * Equal(=). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setRequestSkillCode_Equal_怨恨者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Equal(=). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setRequestSkillCode_Equal_蘇生者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Equal(=). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setRequestSkillCode_Equal_占い師() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.占い師);
    }

    /**
     * Equal(=). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setRequestSkillCode_Equal_破局者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.破局者);
    }

    /**
     * Equal(=). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setRequestSkillCode_Equal_静狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.静狼);
    }

    /**
     * Equal(=). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setRequestSkillCode_Equal_感覚者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * Equal(=). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setRequestSkillCode_Equal_夢遊病者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Equal(=). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setRequestSkillCode_Equal_防音者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.防音者);
    }

    /**
     * Equal(=). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setRequestSkillCode_Equal_ストーカー() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Equal(=). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setRequestSkillCode_Equal_濁点者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * Equal(=). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setRequestSkillCode_Equal_泥棒猫() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Equal(=). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setRequestSkillCode_Equal_翻訳者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Equal(=). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setRequestSkillCode_Equal_罠師() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.罠師);
    }

    /**
     * Equal(=). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setRequestSkillCode_Equal_トラック() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.トラック);
    }

    /**
     * Equal(=). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setRequestSkillCode_Equal_村人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.村人);
    }

    /**
     * Equal(=). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setRequestSkillCode_Equal_おまかせ村人陣営() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Equal(=). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setRequestSkillCode_Equal_壁殴り代行() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Equal(=). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setRequestSkillCode_Equal_風来狩人() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Equal(=). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setRequestSkillCode_Equal_人狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.人狼);
    }

    /**
     * Equal(=). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setRequestSkillCode_Equal_おまかせ人狼陣営() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Equal(=). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setRequestSkillCode_Equal_当選者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.当選者);
    }

    /**
     * Equal(=). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setRequestSkillCode_Equal_賢者() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.賢者);
    }

    /**
     * Equal(=). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setRequestSkillCode_Equal_智狼() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetRequestSkillCode_Equal(String requestSkillCode) {
        regRequestSkillCode(CK_EQ, requestSkillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param requestSkillCode The value of requestSkillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_NotEqual(String requestSkillCode) {
        doSetRequestSkillCode_NotEqual(fRES(requestSkillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setRequestSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetRequestSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setRequestSkillCode_NotEqual_教唆者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * NotEqual(&lt;&gt;). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setRequestSkillCode_NotEqual_絶対人狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setRequestSkillCode_NotEqual_占星術師() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * NotEqual(&lt;&gt;). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setRequestSkillCode_NotEqual_ババ() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.ババ);
    }

    /**
     * NotEqual(&lt;&gt;). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setRequestSkillCode_NotEqual_美人局() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.美人局);
    }

    /**
     * NotEqual(&lt;&gt;). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setRequestSkillCode_NotEqual_パン屋() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * NotEqual(&lt;&gt;). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setRequestSkillCode_NotEqual_バールのようなもの() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * NotEqual(&lt;&gt;). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setRequestSkillCode_NotEqual_黒箱者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setRequestSkillCode_NotEqual_爆弾魔() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * NotEqual(&lt;&gt;). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setRequestSkillCode_NotEqual_誑狐() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * NotEqual(&lt;&gt;). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setRequestSkillCode_NotEqual_C国狂人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setRequestSkillCode_NotEqual_同棲者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * NotEqual(&lt;&gt;). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setRequestSkillCode_NotEqual_指揮官() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * NotEqual(&lt;&gt;). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setRequestSkillCode_NotEqual_検死官() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.検死官);
    }

    /**
     * NotEqual(&lt;&gt;). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setRequestSkillCode_NotEqual_求愛者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setRequestSkillCode_NotEqual_おまかせ愉快犯陣営() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setRequestSkillCode_NotEqual_呪狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setRequestSkillCode_NotEqual_探偵() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.探偵);
    }

    /**
     * NotEqual(&lt;&gt;). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setRequestSkillCode_NotEqual_不止者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.不止者);
    }

    /**
     * NotEqual(&lt;&gt;). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setRequestSkillCode_NotEqual_魔神官() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * NotEqual(&lt;&gt;). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setRequestSkillCode_NotEqual_執行人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.執行人);
    }

    /**
     * NotEqual(&lt;&gt;). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setRequestSkillCode_NotEqual_冤罪者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setRequestSkillCode_NotEqual_狂信者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * NotEqual(&lt;&gt;). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setRequestSkillCode_NotEqual_妄想癖() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * NotEqual(&lt;&gt;). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setRequestSkillCode_NotEqual_花占い師() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setRequestSkillCode_NotEqual_おまかせ足音職() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * NotEqual(&lt;&gt;). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setRequestSkillCode_NotEqual_妖狐() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setRequestSkillCode_NotEqual_おまかせ妖狐陣営() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setRequestSkillCode_NotEqual_おまかせ役職窓あり() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * NotEqual(&lt;&gt;). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setRequestSkillCode_NotEqual_果実籠() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * NotEqual(&lt;&gt;). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setRequestSkillCode_NotEqual_歩狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setRequestSkillCode_NotEqual_銀狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * NotEqual(&lt;&gt;). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setRequestSkillCode_NotEqual_ごん() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.ごん);
    }

    /**
     * NotEqual(&lt;&gt;). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setRequestSkillCode_NotEqual_導師() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.導師);
    }

    /**
     * NotEqual(&lt;&gt;). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setRequestSkillCode_NotEqual_堅狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.堅狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setRequestSkillCode_NotEqual_申し子() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.申し子);
    }

    /**
     * NotEqual(&lt;&gt;). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setRequestSkillCode_NotEqual_仙狐() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setRequestSkillCode_NotEqual_飛狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setRequestSkillCode_NotEqual_狩人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setRequestSkillCode_NotEqual_背徳者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setRequestSkillCode_NotEqual_稲荷() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * NotEqual(&lt;&gt;). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setRequestSkillCode_NotEqual_煽動者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * NotEqual(&lt;&gt;). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setRequestSkillCode_NotEqual_保険屋() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.保険屋);
    }

    /**
     * NotEqual(&lt;&gt;). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setRequestSkillCode_NotEqual_絡新婦() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * NotEqual(&lt;&gt;). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setRequestSkillCode_NotEqual_角狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.角狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setRequestSkillCode_NotEqual_王狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.王狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setRequestSkillCode_NotEqual_金狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.金狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setRequestSkillCode_NotEqual_管狐() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.管狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setRequestSkillCode_NotEqual_弁護士() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setRequestSkillCode_NotEqual_おまかせ() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * NotEqual(&lt;&gt;). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setRequestSkillCode_NotEqual_聴狂人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setRequestSkillCode_NotEqual_黙狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.黙狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setRequestSkillCode_NotEqual_一匹狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setRequestSkillCode_NotEqual_拡声者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setRequestSkillCode_NotEqual_恋人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.恋人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setRequestSkillCode_NotEqual_おまかせ恋人陣営() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setRequestSkillCode_NotEqual_強運者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.強運者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setRequestSkillCode_NotEqual_狂人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setRequestSkillCode_NotEqual_共鳴者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * NotEqual(&lt;&gt;). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setRequestSkillCode_NotEqual_マタギ() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * NotEqual(&lt;&gt;). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setRequestSkillCode_NotEqual_市長() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.市長);
    }

    /**
     * NotEqual(&lt;&gt;). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setRequestSkillCode_NotEqual_霊能者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * NotEqual(&lt;&gt;). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setRequestSkillCode_NotEqual_死霊術師() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setRequestSkillCode_NotEqual_おまかせ役職窓なし() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setRequestSkillCode_NotEqual_おまかせ人外() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * NotEqual(&lt;&gt;). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setRequestSkillCode_NotEqual_監視者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.監視者);
    }

    /**
     * NotEqual(&lt;&gt;). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setRequestSkillCode_NotEqual_梟() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.梟);
    }

    /**
     * NotEqual(&lt;&gt;). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setRequestSkillCode_NotEqual_牧師() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.牧師);
    }

    /**
     * NotEqual(&lt;&gt;). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setRequestSkillCode_NotEqual_虹職人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * NotEqual(&lt;&gt;). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setRequestSkillCode_NotEqual_転生者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.転生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setRequestSkillCode_NotEqual_怨恨者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * NotEqual(&lt;&gt;). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setRequestSkillCode_NotEqual_蘇生者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setRequestSkillCode_NotEqual_占い師() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setRequestSkillCode_NotEqual_破局者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.破局者);
    }

    /**
     * NotEqual(&lt;&gt;). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setRequestSkillCode_NotEqual_静狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.静狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setRequestSkillCode_NotEqual_感覚者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * NotEqual(&lt;&gt;). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setRequestSkillCode_NotEqual_夢遊病者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * NotEqual(&lt;&gt;). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setRequestSkillCode_NotEqual_防音者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.防音者);
    }

    /**
     * NotEqual(&lt;&gt;). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setRequestSkillCode_NotEqual_ストーカー() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * NotEqual(&lt;&gt;). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setRequestSkillCode_NotEqual_濁点者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * NotEqual(&lt;&gt;). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setRequestSkillCode_NotEqual_泥棒猫() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * NotEqual(&lt;&gt;). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setRequestSkillCode_NotEqual_翻訳者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setRequestSkillCode_NotEqual_罠師() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.罠師);
    }

    /**
     * NotEqual(&lt;&gt;). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setRequestSkillCode_NotEqual_トラック() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.トラック);
    }

    /**
     * NotEqual(&lt;&gt;). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setRequestSkillCode_NotEqual_村人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.村人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setRequestSkillCode_NotEqual_おまかせ村人陣営() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setRequestSkillCode_NotEqual_壁殴り代行() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * NotEqual(&lt;&gt;). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setRequestSkillCode_NotEqual_風来狩人() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setRequestSkillCode_NotEqual_人狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setRequestSkillCode_NotEqual_おまかせ人狼陣営() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setRequestSkillCode_NotEqual_当選者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.当選者);
    }

    /**
     * NotEqual(&lt;&gt;). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setRequestSkillCode_NotEqual_賢者() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.賢者);
    }

    /**
     * NotEqual(&lt;&gt;). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setRequestSkillCode_NotEqual_智狼() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetRequestSkillCode_NotEqual(String requestSkillCode) {
        regRequestSkillCode(CK_NES, requestSkillCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param requestSkillCodeList The collection of requestSkillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_InScope(Collection<String> requestSkillCodeList) {
        doSetRequestSkillCode_InScope(requestSkillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRequestSkillCode_InScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetRequestSkillCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, C国狂人]
     */
    public void setRequestSkillCode_InScope_AvailableWerewolfSay() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 聴狂人]
     */
    public void setRequestSkillCode_InScope_ViewableWerewolfSay() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 管狐]
     */
    public void setRequestSkillCode_InScope_HasDivineAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     */
    public void setRequestSkillCode_InScope_HasSkillPsychicAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     */
    public void setRequestSkillCode_InScope_HasAttackAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 背徳者]
     */
    public void setRequestSkillCode_InScope_HasDisturbAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 爆弾魔]
     */
    public void setRequestSkillCode_InScope_NoDeadByAttack() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     */
    public void setRequestSkillCode_InScope_WolfCount() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfWolfCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 梟]
     */
    public void setRequestSkillCode_InScope_NoCount() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfNoCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 狂信者, 煽動者]
     */
    public void setRequestSkillCode_InScope_ViewableWolfCharaName() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     */
    public void setRequestSkillCode_InScope_DivineResultWolf() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfDivineResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     */
    public void setRequestSkillCode_InScope_PsychicResultWolf() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfPsychicResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     */
    public void setRequestSkillCode_InScope_SomeoneSkill() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
    }

    protected void doSetRequestSkillCode_InScope(Collection<String> requestSkillCodeList) {
        regINS(CK_INS, cTL(requestSkillCodeList), xgetCValueRequestSkillCode(), "REQUEST_SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param requestSkillCodeList The collection of requestSkillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_NotInScope(Collection<String> requestSkillCodeList) {
        doSetRequestSkillCode_NotInScope(requestSkillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRequestSkillCode_NotInScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetRequestSkillCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetRequestSkillCode_NotInScope(Collection<String> requestSkillCodeList) {
        regINS(CK_NINS, cTL(requestSkillCodeList), xgetCValueRequestSkillCode(), "REQUEST_SKILL_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setRequestSkillCode_IsNull() { regRequestSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setRequestSkillCode_IsNullOrEmpty() { regRequestSkillCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setRequestSkillCode_IsNotNull() { regRequestSkillCode(CK_ISNN, DOBJ); }

    protected void regRequestSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRequestSkillCode(), "REQUEST_SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueRequestSkillCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param secondRequestSkillCode The value of secondRequestSkillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_Equal(String secondRequestSkillCode) {
        doSetSecondRequestSkillCode_Equal(fRES(secondRequestSkillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetSecondRequestSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setSecondRequestSkillCode_Equal_教唆者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * Equal(=). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSecondRequestSkillCode_Equal_絶対人狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * Equal(=). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setSecondRequestSkillCode_Equal_占星術師() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * Equal(=). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setSecondRequestSkillCode_Equal_ババ() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.ババ);
    }

    /**
     * Equal(=). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setSecondRequestSkillCode_Equal_美人局() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.美人局);
    }

    /**
     * Equal(=). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setSecondRequestSkillCode_Equal_パン屋() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * Equal(=). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setSecondRequestSkillCode_Equal_バールのようなもの() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * Equal(=). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setSecondRequestSkillCode_Equal_黒箱者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * Equal(=). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSecondRequestSkillCode_Equal_爆弾魔() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * Equal(=). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSecondRequestSkillCode_Equal_誑狐() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * Equal(=). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setSecondRequestSkillCode_Equal_C国狂人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * Equal(=). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setSecondRequestSkillCode_Equal_同棲者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * Equal(=). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setSecondRequestSkillCode_Equal_指揮官() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * Equal(=). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setSecondRequestSkillCode_Equal_検死官() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.検死官);
    }

    /**
     * Equal(=). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setSecondRequestSkillCode_Equal_求愛者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * Equal(=). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ愉快犯陣営() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * Equal(=). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setSecondRequestSkillCode_Equal_呪狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * Equal(=). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setSecondRequestSkillCode_Equal_探偵() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.探偵);
    }

    /**
     * Equal(=). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSecondRequestSkillCode_Equal_不止者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.不止者);
    }

    /**
     * Equal(=). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSecondRequestSkillCode_Equal_魔神官() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * Equal(=). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setSecondRequestSkillCode_Equal_執行人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.執行人);
    }

    /**
     * Equal(=). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setSecondRequestSkillCode_Equal_冤罪者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * Equal(=). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setSecondRequestSkillCode_Equal_狂信者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * Equal(=). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setSecondRequestSkillCode_Equal_妄想癖() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * Equal(=). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setSecondRequestSkillCode_Equal_花占い師() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * Equal(=). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ足音職() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * Equal(=). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setSecondRequestSkillCode_Equal_妖狐() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * Equal(=). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ妖狐陣営() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * Equal(=). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ役職窓あり() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * Equal(=). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setSecondRequestSkillCode_Equal_果実籠() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * Equal(=). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setSecondRequestSkillCode_Equal_歩狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * Equal(=). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setSecondRequestSkillCode_Equal_銀狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * Equal(=). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setSecondRequestSkillCode_Equal_ごん() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.ごん);
    }

    /**
     * Equal(=). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSecondRequestSkillCode_Equal_導師() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.導師);
    }

    /**
     * Equal(=). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setSecondRequestSkillCode_Equal_堅狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.堅狼);
    }

    /**
     * Equal(=). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setSecondRequestSkillCode_Equal_申し子() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.申し子);
    }

    /**
     * Equal(=). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setSecondRequestSkillCode_Equal_仙狐() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * Equal(=). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSecondRequestSkillCode_Equal_飛狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * Equal(=). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setSecondRequestSkillCode_Equal_狩人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.狩人);
    }

    /**
     * Equal(=). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setSecondRequestSkillCode_Equal_背徳者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * Equal(=). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setSecondRequestSkillCode_Equal_稲荷() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * Equal(=). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSecondRequestSkillCode_Equal_煽動者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * Equal(=). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setSecondRequestSkillCode_Equal_保険屋() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.保険屋);
    }

    /**
     * Equal(=). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setSecondRequestSkillCode_Equal_絡新婦() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * Equal(=). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setSecondRequestSkillCode_Equal_角狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.角狼);
    }

    /**
     * Equal(=). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setSecondRequestSkillCode_Equal_王狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.王狼);
    }

    /**
     * Equal(=). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setSecondRequestSkillCode_Equal_金狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.金狼);
    }

    /**
     * Equal(=). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setSecondRequestSkillCode_Equal_管狐() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.管狐);
    }

    /**
     * Equal(=). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setSecondRequestSkillCode_Equal_弁護士() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * Equal(=). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSecondRequestSkillCode_Equal_おまかせ() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * Equal(=). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setSecondRequestSkillCode_Equal_聴狂人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * Equal(=). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setSecondRequestSkillCode_Equal_黙狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.黙狼);
    }

    /**
     * Equal(=). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setSecondRequestSkillCode_Equal_一匹狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * Equal(=). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setSecondRequestSkillCode_Equal_拡声者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * Equal(=). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setSecondRequestSkillCode_Equal_恋人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.恋人);
    }

    /**
     * Equal(=). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ恋人陣営() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * Equal(=). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setSecondRequestSkillCode_Equal_強運者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.強運者);
    }

    /**
     * Equal(=). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setSecondRequestSkillCode_Equal_狂人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.狂人);
    }

    /**
     * Equal(=). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setSecondRequestSkillCode_Equal_共鳴者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * Equal(=). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setSecondRequestSkillCode_Equal_マタギ() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * Equal(=). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setSecondRequestSkillCode_Equal_市長() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.市長);
    }

    /**
     * Equal(=). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSecondRequestSkillCode_Equal_霊能者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * Equal(=). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSecondRequestSkillCode_Equal_死霊術師() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * Equal(=). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ役職窓なし() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * Equal(=). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ人外() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * Equal(=). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setSecondRequestSkillCode_Equal_監視者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.監視者);
    }

    /**
     * Equal(=). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSecondRequestSkillCode_Equal_梟() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.梟);
    }

    /**
     * Equal(=). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setSecondRequestSkillCode_Equal_牧師() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.牧師);
    }

    /**
     * Equal(=). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSecondRequestSkillCode_Equal_虹職人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * Equal(=). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSecondRequestSkillCode_Equal_転生者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.転生者);
    }

    /**
     * Equal(=). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setSecondRequestSkillCode_Equal_怨恨者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * Equal(=). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSecondRequestSkillCode_Equal_蘇生者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * Equal(=). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSecondRequestSkillCode_Equal_占い師() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.占い師);
    }

    /**
     * Equal(=). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setSecondRequestSkillCode_Equal_破局者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.破局者);
    }

    /**
     * Equal(=). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setSecondRequestSkillCode_Equal_静狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.静狼);
    }

    /**
     * Equal(=). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setSecondRequestSkillCode_Equal_感覚者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * Equal(=). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSecondRequestSkillCode_Equal_夢遊病者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * Equal(=). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setSecondRequestSkillCode_Equal_防音者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.防音者);
    }

    /**
     * Equal(=). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setSecondRequestSkillCode_Equal_ストーカー() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * Equal(=). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setSecondRequestSkillCode_Equal_濁点者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * Equal(=). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setSecondRequestSkillCode_Equal_泥棒猫() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * Equal(=). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setSecondRequestSkillCode_Equal_翻訳者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * Equal(=). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSecondRequestSkillCode_Equal_罠師() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.罠師);
    }

    /**
     * Equal(=). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setSecondRequestSkillCode_Equal_トラック() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.トラック);
    }

    /**
     * Equal(=). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setSecondRequestSkillCode_Equal_村人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.村人);
    }

    /**
     * Equal(=). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ村人陣営() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * Equal(=). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setSecondRequestSkillCode_Equal_壁殴り代行() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * Equal(=). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setSecondRequestSkillCode_Equal_風来狩人() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * Equal(=). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setSecondRequestSkillCode_Equal_人狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.人狼);
    }

    /**
     * Equal(=). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setSecondRequestSkillCode_Equal_おまかせ人狼陣営() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * Equal(=). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setSecondRequestSkillCode_Equal_当選者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.当選者);
    }

    /**
     * Equal(=). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setSecondRequestSkillCode_Equal_賢者() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.賢者);
    }

    /**
     * Equal(=). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setSecondRequestSkillCode_Equal_智狼() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetSecondRequestSkillCode_Equal(String secondRequestSkillCode) {
        regSecondRequestSkillCode(CK_EQ, secondRequestSkillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param secondRequestSkillCode The value of secondRequestSkillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_NotEqual(String secondRequestSkillCode) {
        doSetSecondRequestSkillCode_NotEqual(fRES(secondRequestSkillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetSecondRequestSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 教唆者 (ABETTER). And OnlyOnceRegistered. <br>
     * 教唆者
     */
    public void setSecondRequestSkillCode_NotEqual_教唆者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.教唆者);
    }

    /**
     * NotEqual(&lt;&gt;). As 絶対人狼 (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * 絶対人狼
     */
    public void setSecondRequestSkillCode_NotEqual_絶対人狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.絶対人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 占星術師 (ASTROLOGER). And OnlyOnceRegistered. <br>
     * 占星術師
     */
    public void setSecondRequestSkillCode_NotEqual_占星術師() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.占星術師);
    }

    /**
     * NotEqual(&lt;&gt;). As ババ (BABA). And OnlyOnceRegistered. <br>
     * ババ
     */
    public void setSecondRequestSkillCode_NotEqual_ババ() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.ババ);
    }

    /**
     * NotEqual(&lt;&gt;). As 美人局 (BADGERGAME). And OnlyOnceRegistered. <br>
     * 美人局
     */
    public void setSecondRequestSkillCode_NotEqual_美人局() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.美人局);
    }

    /**
     * NotEqual(&lt;&gt;). As パン屋 (BAKERY). And OnlyOnceRegistered. <br>
     * パン屋
     */
    public void setSecondRequestSkillCode_NotEqual_パン屋() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.パン屋);
    }

    /**
     * NotEqual(&lt;&gt;). As バールのようなもの (BAR). And OnlyOnceRegistered. <br>
     * バールのようなもの
     */
    public void setSecondRequestSkillCode_NotEqual_バールのようなもの() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.バールのようなもの);
    }

    /**
     * NotEqual(&lt;&gt;). As 黒箱者 (BLACKBOX). And OnlyOnceRegistered. <br>
     * 黒箱者
     */
    public void setSecondRequestSkillCode_NotEqual_黒箱者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.黒箱者);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆弾魔 (BOMBER). And OnlyOnceRegistered. <br>
     * 爆弾魔
     */
    public void setSecondRequestSkillCode_NotEqual_爆弾魔() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.爆弾魔);
    }

    /**
     * NotEqual(&lt;&gt;). As 誑狐 (CHEATERFOX). And OnlyOnceRegistered. <br>
     * 誑狐
     */
    public void setSecondRequestSkillCode_NotEqual_誑狐() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.誑狐);
    }

    /**
     * NotEqual(&lt;&gt;). As C国狂人 (CMADMAN). And OnlyOnceRegistered. <br>
     * C国狂人
     */
    public void setSecondRequestSkillCode_NotEqual_C国狂人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.C国狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 同棲者 (COHABITER). And OnlyOnceRegistered. <br>
     * 同棲者
     */
    public void setSecondRequestSkillCode_NotEqual_同棲者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.同棲者);
    }

    /**
     * NotEqual(&lt;&gt;). As 指揮官 (COMMANDER). And OnlyOnceRegistered. <br>
     * 指揮官
     */
    public void setSecondRequestSkillCode_NotEqual_指揮官() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.指揮官);
    }

    /**
     * NotEqual(&lt;&gt;). As 検死官 (CORONER). And OnlyOnceRegistered. <br>
     * 検死官
     */
    public void setSecondRequestSkillCode_NotEqual_検死官() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.検死官);
    }

    /**
     * NotEqual(&lt;&gt;). As 求愛者 (COURTSHIP). And OnlyOnceRegistered. <br>
     * 求愛者
     */
    public void setSecondRequestSkillCode_NotEqual_求愛者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.求愛者);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ愉快犯陣営 (CRIMINALS). And OnlyOnceRegistered. <br>
     * おまかせ（愉快犯陣営）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ愉快犯陣営() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ愉快犯陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 呪狼 (CURSEWOLF). And OnlyOnceRegistered. <br>
     * 呪狼
     */
    public void setSecondRequestSkillCode_NotEqual_呪狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.呪狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 探偵 (DETECTIVE). And OnlyOnceRegistered. <br>
     * 探偵
     */
    public void setSecondRequestSkillCode_NotEqual_探偵() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.探偵);
    }

    /**
     * NotEqual(&lt;&gt;). As 不止者 (DYINGPOINTER). And OnlyOnceRegistered. <br>
     * 不止者
     */
    public void setSecondRequestSkillCode_NotEqual_不止者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.不止者);
    }

    /**
     * NotEqual(&lt;&gt;). As 魔神官 (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * 魔神官
     */
    public void setSecondRequestSkillCode_NotEqual_魔神官() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.魔神官);
    }

    /**
     * NotEqual(&lt;&gt;). As 執行人 (EXECUTIONER). And OnlyOnceRegistered. <br>
     * 執行人
     */
    public void setSecondRequestSkillCode_NotEqual_執行人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.執行人);
    }

    /**
     * NotEqual(&lt;&gt;). As 冤罪者 (FALSECHARGES). And OnlyOnceRegistered. <br>
     * 冤罪者
     */
    public void setSecondRequestSkillCode_NotEqual_冤罪者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.冤罪者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂信者 (FANATIC). And OnlyOnceRegistered. <br>
     * 狂信者
     */
    public void setSecondRequestSkillCode_NotEqual_狂信者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.狂信者);
    }

    /**
     * NotEqual(&lt;&gt;). As 妄想癖 (FANTASIST). And OnlyOnceRegistered. <br>
     * 妄想癖
     */
    public void setSecondRequestSkillCode_NotEqual_妄想癖() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.妄想癖);
    }

    /**
     * NotEqual(&lt;&gt;). As 花占い師 (FLOWERSEER). And OnlyOnceRegistered. <br>
     * 花占い師
     */
    public void setSecondRequestSkillCode_NotEqual_花占い師() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.花占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ足音職 (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * おまかせ（足音職）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ足音職() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ足音職);
    }

    /**
     * NotEqual(&lt;&gt;). As 妖狐 (FOX). And OnlyOnceRegistered. <br>
     * 妖狐
     */
    public void setSecondRequestSkillCode_NotEqual_妖狐() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.妖狐);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ妖狐陣営 (FOXS). And OnlyOnceRegistered. <br>
     * おまかせ（妖狐陣営）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ妖狐陣営() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ妖狐陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓あり (FRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓あり）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ役職窓あり() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓あり);
    }

    /**
     * NotEqual(&lt;&gt;). As 果実籠 (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * 果実籠
     */
    public void setSecondRequestSkillCode_NotEqual_果実籠() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.果実籠);
    }

    /**
     * NotEqual(&lt;&gt;). As 歩狼 (FUWOLF). And OnlyOnceRegistered. <br>
     * 歩狼
     */
    public void setSecondRequestSkillCode_NotEqual_歩狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.歩狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 銀狼 (GINWOLF). And OnlyOnceRegistered. <br>
     * 銀狼
     */
    public void setSecondRequestSkillCode_NotEqual_銀狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.銀狼);
    }

    /**
     * NotEqual(&lt;&gt;). As ごん (GONFOX). And OnlyOnceRegistered. <br>
     * ごん
     */
    public void setSecondRequestSkillCode_NotEqual_ごん() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.ごん);
    }

    /**
     * NotEqual(&lt;&gt;). As 導師 (GURU). And OnlyOnceRegistered. <br>
     * 導師
     */
    public void setSecondRequestSkillCode_NotEqual_導師() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.導師);
    }

    /**
     * NotEqual(&lt;&gt;). As 堅狼 (HARDWOLF). And OnlyOnceRegistered. <br>
     * 堅狼
     */
    public void setSecondRequestSkillCode_NotEqual_堅狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.堅狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 申し子 (HEAVENCHILD). And OnlyOnceRegistered. <br>
     * 申し子
     */
    public void setSecondRequestSkillCode_NotEqual_申し子() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.申し子);
    }

    /**
     * NotEqual(&lt;&gt;). As 仙狐 (HERMITFOX). And OnlyOnceRegistered. <br>
     * 仙狐
     */
    public void setSecondRequestSkillCode_NotEqual_仙狐() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.仙狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 飛狼 (HISHAWOLF). And OnlyOnceRegistered. <br>
     * 飛狼
     */
    public void setSecondRequestSkillCode_NotEqual_飛狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.飛狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 狩人 (HUNTER). And OnlyOnceRegistered. <br>
     * 狩人
     */
    public void setSecondRequestSkillCode_NotEqual_狩人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 背徳者 (IMMORAL). And OnlyOnceRegistered. <br>
     * 背徳者
     */
    public void setSecondRequestSkillCode_NotEqual_背徳者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.背徳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 稲荷 (INARI). And OnlyOnceRegistered. <br>
     * 稲荷
     */
    public void setSecondRequestSkillCode_NotEqual_稲荷() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.稲荷);
    }

    /**
     * NotEqual(&lt;&gt;). As 煽動者 (INSTIGATOR). And OnlyOnceRegistered. <br>
     * 煽動者
     */
    public void setSecondRequestSkillCode_NotEqual_煽動者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.煽動者);
    }

    /**
     * NotEqual(&lt;&gt;). As 保険屋 (INSURANCER). And OnlyOnceRegistered. <br>
     * 保険屋
     */
    public void setSecondRequestSkillCode_NotEqual_保険屋() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.保険屋);
    }

    /**
     * NotEqual(&lt;&gt;). As 絡新婦 (JOROGUMO). And OnlyOnceRegistered. <br>
     * 絡新婦
     */
    public void setSecondRequestSkillCode_NotEqual_絡新婦() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.絡新婦);
    }

    /**
     * NotEqual(&lt;&gt;). As 角狼 (KAKUWOLF). And OnlyOnceRegistered. <br>
     * 角狼
     */
    public void setSecondRequestSkillCode_NotEqual_角狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.角狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 王狼 (KINGWOLF). And OnlyOnceRegistered. <br>
     * 王狼
     */
    public void setSecondRequestSkillCode_NotEqual_王狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.王狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 金狼 (KINWOLF). And OnlyOnceRegistered. <br>
     * 金狼
     */
    public void setSecondRequestSkillCode_NotEqual_金狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.金狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 管狐 (KUDAFOX). And OnlyOnceRegistered. <br>
     * 管狐
     */
    public void setSecondRequestSkillCode_NotEqual_管狐() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.管狐);
    }

    /**
     * NotEqual(&lt;&gt;). As 弁護士 (LAWYER). And OnlyOnceRegistered. <br>
     * 弁護士
     */
    public void setSecondRequestSkillCode_NotEqual_弁護士() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.弁護士);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ (LEFTOVER). And OnlyOnceRegistered. <br>
     * おまかせ
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ);
    }

    /**
     * NotEqual(&lt;&gt;). As 聴狂人 (LISTENMADMAN). And OnlyOnceRegistered. <br>
     * 聴狂人
     */
    public void setSecondRequestSkillCode_NotEqual_聴狂人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.聴狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 黙狼 (LISTENWOLF). And OnlyOnceRegistered. <br>
     * 黙狼
     */
    public void setSecondRequestSkillCode_NotEqual_黙狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.黙狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 一匹狼 (LONEWOLF). And OnlyOnceRegistered. <br>
     * 一匹狼
     */
    public void setSecondRequestSkillCode_NotEqual_一匹狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.一匹狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 拡声者 (LOUDSPEAKER). And OnlyOnceRegistered. <br>
     * 拡声者
     */
    public void setSecondRequestSkillCode_NotEqual_拡声者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.拡声者);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋人 (LOVER). And OnlyOnceRegistered. <br>
     * 恋人
     */
    public void setSecondRequestSkillCode_NotEqual_恋人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.恋人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * おまかせ（恋人陣営）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ恋人陣営() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ恋人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 強運者 (LUCKYMAN). And OnlyOnceRegistered. <br>
     * 強運者
     */
    public void setSecondRequestSkillCode_NotEqual_強運者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.強運者);
    }

    /**
     * NotEqual(&lt;&gt;). As 狂人 (MADMAN). And OnlyOnceRegistered. <br>
     * 狂人
     */
    public void setSecondRequestSkillCode_NotEqual_狂人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.狂人);
    }

    /**
     * NotEqual(&lt;&gt;). As 共鳴者 (MASON). And OnlyOnceRegistered. <br>
     * 共鳴者
     */
    public void setSecondRequestSkillCode_NotEqual_共鳴者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.共鳴者);
    }

    /**
     * NotEqual(&lt;&gt;). As マタギ (MATAGI). And OnlyOnceRegistered. <br>
     * マタギ
     */
    public void setSecondRequestSkillCode_NotEqual_マタギ() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.マタギ);
    }

    /**
     * NotEqual(&lt;&gt;). As 市長 (MAYOR). And OnlyOnceRegistered. <br>
     * 市長
     */
    public void setSecondRequestSkillCode_NotEqual_市長() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.市長);
    }

    /**
     * NotEqual(&lt;&gt;). As 霊能者 (MEDIUM). And OnlyOnceRegistered. <br>
     * 霊能者
     */
    public void setSecondRequestSkillCode_NotEqual_霊能者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.霊能者);
    }

    /**
     * NotEqual(&lt;&gt;). As 死霊術師 (NECROMANCER). And OnlyOnceRegistered. <br>
     * 死霊術師
     */
    public void setSecondRequestSkillCode_NotEqual_死霊術師() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.死霊術師);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ役職窓なし (NOFRIENDS). And OnlyOnceRegistered. <br>
     * おまかせ（役職窓なし）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ役職窓なし() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ役職窓なし);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人外 (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（人外）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ人外() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人外);
    }

    /**
     * NotEqual(&lt;&gt;). As 監視者 (OBSERVER). And OnlyOnceRegistered. <br>
     * 監視者
     */
    public void setSecondRequestSkillCode_NotEqual_監視者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.監視者);
    }

    /**
     * NotEqual(&lt;&gt;). As 梟 (OWL). And OnlyOnceRegistered. <br>
     * 梟
     */
    public void setSecondRequestSkillCode_NotEqual_梟() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.梟);
    }

    /**
     * NotEqual(&lt;&gt;). As 牧師 (PASTOR). And OnlyOnceRegistered. <br>
     * 牧師
     */
    public void setSecondRequestSkillCode_NotEqual_牧師() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.牧師);
    }

    /**
     * NotEqual(&lt;&gt;). As 虹職人 (RAINBOW). And OnlyOnceRegistered. <br>
     * 虹職人
     */
    public void setSecondRequestSkillCode_NotEqual_虹職人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.虹職人);
    }

    /**
     * NotEqual(&lt;&gt;). As 転生者 (REINCARNATION). And OnlyOnceRegistered. <br>
     * 転生者
     */
    public void setSecondRequestSkillCode_NotEqual_転生者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.転生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 怨恨者 (RESENTER). And OnlyOnceRegistered. <br>
     * 怨恨者
     */
    public void setSecondRequestSkillCode_NotEqual_怨恨者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.怨恨者);
    }

    /**
     * NotEqual(&lt;&gt;). As 蘇生者 (RESUSCITATOR). And OnlyOnceRegistered. <br>
     * 蘇生者
     */
    public void setSecondRequestSkillCode_NotEqual_蘇生者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.蘇生者);
    }

    /**
     * NotEqual(&lt;&gt;). As 占い師 (SEER). And OnlyOnceRegistered. <br>
     * 占い師
     */
    public void setSecondRequestSkillCode_NotEqual_占い師() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.占い師);
    }

    /**
     * NotEqual(&lt;&gt;). As 破局者 (SEPARATOR). And OnlyOnceRegistered. <br>
     * 破局者
     */
    public void setSecondRequestSkillCode_NotEqual_破局者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.破局者);
    }

    /**
     * NotEqual(&lt;&gt;). As 静狼 (SILENTWOLF). And OnlyOnceRegistered. <br>
     * 静狼
     */
    public void setSecondRequestSkillCode_NotEqual_静狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.静狼);
    }

    /**
     * NotEqual(&lt;&gt;). As 感覚者 (SIXTHSENSOR). And OnlyOnceRegistered. <br>
     * 感覚者
     */
    public void setSecondRequestSkillCode_NotEqual_感覚者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.感覚者);
    }

    /**
     * NotEqual(&lt;&gt;). As 夢遊病者 (SLEEPWALKER). And OnlyOnceRegistered. <br>
     * 夢遊病者
     */
    public void setSecondRequestSkillCode_NotEqual_夢遊病者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.夢遊病者);
    }

    /**
     * NotEqual(&lt;&gt;). As 防音者 (SOUNDPROOFER). And OnlyOnceRegistered. <br>
     * 防音者
     */
    public void setSecondRequestSkillCode_NotEqual_防音者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.防音者);
    }

    /**
     * NotEqual(&lt;&gt;). As ストーカー (STALKER). And OnlyOnceRegistered. <br>
     * ストーカー
     */
    public void setSecondRequestSkillCode_NotEqual_ストーカー() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.ストーカー);
    }

    /**
     * NotEqual(&lt;&gt;). As 濁点者 (TATSUYA). And OnlyOnceRegistered. <br>
     * 濁点者
     */
    public void setSecondRequestSkillCode_NotEqual_濁点者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.濁点者);
    }

    /**
     * NotEqual(&lt;&gt;). As 泥棒猫 (THIEFCAT). And OnlyOnceRegistered. <br>
     * 泥棒猫
     */
    public void setSecondRequestSkillCode_NotEqual_泥棒猫() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.泥棒猫);
    }

    /**
     * NotEqual(&lt;&gt;). As 翻訳者 (TRANSLATOR). And OnlyOnceRegistered. <br>
     * 翻訳者
     */
    public void setSecondRequestSkillCode_NotEqual_翻訳者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.翻訳者);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠師 (TRAPPER). And OnlyOnceRegistered. <br>
     * 罠師
     */
    public void setSecondRequestSkillCode_NotEqual_罠師() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.罠師);
    }

    /**
     * NotEqual(&lt;&gt;). As トラック (TRUCK). And OnlyOnceRegistered. <br>
     * トラック
     */
    public void setSecondRequestSkillCode_NotEqual_トラック() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.トラック);
    }

    /**
     * NotEqual(&lt;&gt;). As 村人 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人
     */
    public void setSecondRequestSkillCode_NotEqual_村人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.村人);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ村人陣営 (VILLAGERS). And OnlyOnceRegistered. <br>
     * おまかせ（村人陣営）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ村人陣営() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ村人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 壁殴り代行 (WALLPUNCHER). And OnlyOnceRegistered. <br>
     * 壁殴り代行
     */
    public void setSecondRequestSkillCode_NotEqual_壁殴り代行() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.壁殴り代行);
    }

    /**
     * NotEqual(&lt;&gt;). As 風来狩人 (WANDERER). And OnlyOnceRegistered. <br>
     * 風来狩人
     */
    public void setSecondRequestSkillCode_NotEqual_風来狩人() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.風来狩人);
    }

    /**
     * NotEqual(&lt;&gt;). As 人狼 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼
     */
    public void setSecondRequestSkillCode_NotEqual_人狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.人狼);
    }

    /**
     * NotEqual(&lt;&gt;). As おまかせ人狼陣営 (WEREWOLFS). And OnlyOnceRegistered. <br>
     * おまかせ（人狼陣営）
     */
    public void setSecondRequestSkillCode_NotEqual_おまかせ人狼陣営() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.おまかせ人狼陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 当選者 (WINNER). And OnlyOnceRegistered. <br>
     * 当選者
     */
    public void setSecondRequestSkillCode_NotEqual_当選者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.当選者);
    }

    /**
     * NotEqual(&lt;&gt;). As 賢者 (WISE). And OnlyOnceRegistered. <br>
     * 賢者
     */
    public void setSecondRequestSkillCode_NotEqual_賢者() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.賢者);
    }

    /**
     * NotEqual(&lt;&gt;). As 智狼 (WISEWOLF). And OnlyOnceRegistered. <br>
     * 智狼
     */
    public void setSecondRequestSkillCode_NotEqual_智狼() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.智狼);
    }

    protected void doSetSecondRequestSkillCode_NotEqual(String secondRequestSkillCode) {
        regSecondRequestSkillCode(CK_NES, secondRequestSkillCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param secondRequestSkillCodeList The collection of secondRequestSkillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_InScope(Collection<String> secondRequestSkillCodeList) {
        doSetSecondRequestSkillCode_InScope(secondRequestSkillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSecondRequestSkillCode_InScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSecondRequestSkillCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁き可能 <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, C国狂人]
     */
    public void setSecondRequestSkillCode_InScope_AvailableWerewolfSay() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 囁きを見られる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 聴狂人]
     */
    public void setSecondRequestSkillCode_InScope_ViewableWerewolfSay() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い能力を持つ <br>
     * The group elements:[占い師, 賢者, 占星術師, 花占い師, 感覚者, 管狐]
     */
    public void setSecondRequestSkillCode_InScope_HasDivineAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 役職霊能能力を持つ <br>
     * The group elements:[導師, 魔神官, 稲荷]
     */
    public void setSecondRequestSkillCode_InScope_HasSkillPsychicAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃能力を持つ <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     */
    public void setSecondRequestSkillCode_InScope_HasAttackAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 徘徊能力を持つ <br>
     * The group elements:[C国狂人, 狂人, 狂信者, 魔神官, 聴狂人, 妖狐, 仙狐, 背徳者]
     */
    public void setSecondRequestSkillCode_InScope_HasDisturbAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 襲撃されても死なない <br>
     * The group elements:[壁殴り代行, 堅狼, 妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 爆弾魔]
     */
    public void setSecondRequestSkillCode_InScope_NoDeadByAttack() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人狼にカウントされる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼]
     */
    public void setSecondRequestSkillCode_InScope_WolfCount() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfWolfCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 勝敗判定時、人間にも人狼にもカウントされない <br>
     * The group elements:[妖狐, 誑狐, ごん, 仙狐, 管狐, 稲荷, 梟]
     */
    public void setSecondRequestSkillCode_InScope_NoCount() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfNoCount());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 人狼が誰かを知ることができる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, C国狂人, 狂信者, 煽動者]
     */
    public void setSecondRequestSkillCode_InScope_ViewableWolfCharaName() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 占い結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     */
    public void setSecondRequestSkillCode_InScope_DivineResultWolf() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfDivineResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * 霊能結果が人狼となる <br>
     * The group elements:[人狼, 呪狼, 智狼, 絶対人狼, 歩狼, 銀狼, 金狼, 飛狼, 角狼, 王狼, 静狼, 堅狼, 黙狼, 一匹狼]
     */
    public void setSecondRequestSkillCode_InScope_PsychicResultWolf() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfPsychicResultWolf());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 役職 <br>
     * おまかせ系 <br>
     * The group elements:[おまかせ, おまかせ村人陣営, おまかせ人狼陣営, おまかせ恋人陣営, おまかせ妖狐陣営, おまかせ愉快犯陣営, おまかせ足音職, おまかせ役職窓あり, おまかせ役職窓なし, おまかせ人外]
     */
    public void setSecondRequestSkillCode_InScope_SomeoneSkill() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
    }

    protected void doSetSecondRequestSkillCode_InScope(Collection<String> secondRequestSkillCodeList) {
        regINS(CK_INS, cTL(secondRequestSkillCodeList), xgetCValueSecondRequestSkillCode(), "SECOND_REQUEST_SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     * @param secondRequestSkillCodeList The collection of secondRequestSkillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_NotInScope(Collection<String> secondRequestSkillCodeList) {
        doSetSecondRequestSkillCode_NotInScope(secondRequestSkillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill} <br>
     * 役職
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSecondRequestSkillCode_NotInScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSecondRequestSkillCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetSecondRequestSkillCode_NotInScope(Collection<String> secondRequestSkillCodeList) {
        regINS(CK_NINS, cTL(secondRequestSkillCodeList), xgetCValueSecondRequestSkillCode(), "SECOND_REQUEST_SKILL_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSecondRequestSkillCode_IsNull() { regSecondRequestSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSecondRequestSkillCode_IsNullOrEmpty() { regSecondRequestSkillCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to skill, classification=Skill}
     */
    public void setSecondRequestSkillCode_IsNotNull() { regSecondRequestSkillCode(CK_ISNN, DOBJ); }

    protected void regSecondRequestSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueSecondRequestSkillCode(), "SECOND_REQUEST_SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueSecondRequestSkillCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumber The value of roomNumber as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomNumber_Equal(Integer roomNumber) {
        doSetRoomNumber_Equal(roomNumber);
    }

    protected void doSetRoomNumber_Equal(Integer roomNumber) {
        regRoomNumber(CK_EQ, roomNumber);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumber The value of roomNumber as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomNumber_NotEqual(Integer roomNumber) {
        doSetRoomNumber_NotEqual(roomNumber);
    }

    protected void doSetRoomNumber_NotEqual(Integer roomNumber) {
        regRoomNumber(CK_NES, roomNumber);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumber The value of roomNumber as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomNumber_GreaterThan(Integer roomNumber) {
        regRoomNumber(CK_GT, roomNumber);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumber The value of roomNumber as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomNumber_LessThan(Integer roomNumber) {
        regRoomNumber(CK_LT, roomNumber);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumber The value of roomNumber as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomNumber_GreaterEqual(Integer roomNumber) {
        regRoomNumber(CK_GE, roomNumber);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumber The value of roomNumber as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomNumber_LessEqual(Integer roomNumber) {
        regRoomNumber(CK_LE, roomNumber);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param minNumber The min number of roomNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of roomNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setRoomNumber_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setRoomNumber_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param minNumber The min number of roomNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of roomNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setRoomNumber_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueRoomNumber(), "ROOM_NUMBER", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumberList The collection of roomNumber as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRoomNumber_InScope(Collection<Integer> roomNumberList) {
        doSetRoomNumber_InScope(roomNumberList);
    }

    protected void doSetRoomNumber_InScope(Collection<Integer> roomNumberList) {
        regINS(CK_INS, cTL(roomNumberList), xgetCValueRoomNumber(), "ROOM_NUMBER");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @param roomNumberList The collection of roomNumber as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRoomNumber_NotInScope(Collection<Integer> roomNumberList) {
        doSetRoomNumber_NotInScope(roomNumberList);
    }

    protected void doSetRoomNumber_NotInScope(Collection<Integer> roomNumberList) {
        regINS(CK_NINS, cTL(roomNumberList), xgetCValueRoomNumber(), "ROOM_NUMBER");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     */
    public void setRoomNumber_IsNull() { regRoomNumber(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     */
    public void setRoomNumber_IsNotNull() { regRoomNumber(CK_ISNN, DOBJ); }

    protected void regRoomNumber(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRoomNumber(), "ROOM_NUMBER"); }
    protected abstract ConditionValue xgetCValueRoomNumber();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_DEAD: {NotNull, BIT, classification=Flg}
     * @param isDead The value of isDead as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsDead_Equal(Boolean isDead) {
        regIsDead(CK_EQ, isDead);
    }

    /**
     * Equal(=). As Flg. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IS_DEAD: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsDead_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsDead_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * はい: 有効を示す
     */
    public void setIsDead_Equal_True() {
        doSetIsDead_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * いいえ: 無効を示す
     */
    public void setIsDead_Equal_False() {
        doSetIsDead_Equal(Boolean.valueOf(CDef.Flg.False.code()));
    }

    protected void doSetIsDead_Equal(Boolean isDead) {
        regIsDead(CK_EQ, isDead);
    }

    protected void regIsDead(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsDead(), "IS_DEAD"); }
    protected abstract ConditionValue xgetCValueIsDead();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_SPECTATOR: {NotNull, BIT, classification=Flg}
     * @param isSpectator The value of isSpectator as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsSpectator_Equal(Boolean isSpectator) {
        regIsSpectator(CK_EQ, isSpectator);
    }

    /**
     * Equal(=). As Flg. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IS_SPECTATOR: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsSpectator_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsSpectator_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * はい: 有効を示す
     */
    public void setIsSpectator_Equal_True() {
        doSetIsSpectator_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * いいえ: 無効を示す
     */
    public void setIsSpectator_Equal_False() {
        doSetIsSpectator_Equal(Boolean.valueOf(CDef.Flg.False.code()));
    }

    protected void doSetIsSpectator_Equal(Boolean isSpectator) {
        regIsSpectator(CK_EQ, isSpectator);
    }

    protected void regIsSpectator(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsSpectator(), "IS_SPECTATOR"); }
    protected abstract ConditionValue xgetCValueIsSpectator();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     * @param deadReasonCode The value of deadReasonCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_Equal(String deadReasonCode) {
        doSetDeadReasonCode_Equal(fRES(deadReasonCode));
    }

    /**
     * Equal(=). As DeadReason. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason cdef) {
        doSetDeadReasonCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 襲撃 (ATTACK). And OnlyOnceRegistered. <br>
     * 襲撃
     */
    public void setDeadReasonCode_Equal_襲撃() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.襲撃);
    }

    /**
     * Equal(=). As 爆死 (BOMBED). And OnlyOnceRegistered. <br>
     * 爆死
     */
    public void setDeadReasonCode_Equal_爆死() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.爆死);
    }

    /**
     * Equal(=). As 呪殺 (DIVINED). And OnlyOnceRegistered. <br>
     * 呪殺
     */
    public void setDeadReasonCode_Equal_呪殺() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.呪殺);
    }

    /**
     * Equal(=). As 処刑 (EXECUTE). And OnlyOnceRegistered. <br>
     * 処刑
     */
    public void setDeadReasonCode_Equal_処刑() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.処刑);
    }

    /**
     * Equal(=). As 突然 (SUDDON). And OnlyOnceRegistered. <br>
     * 突然
     */
    public void setDeadReasonCode_Equal_突然() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.突然);
    }

    /**
     * Equal(=). As 後追 (SUICIDE). And OnlyOnceRegistered. <br>
     * 後追
     */
    public void setDeadReasonCode_Equal_後追() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.後追);
    }

    /**
     * Equal(=). As 罠死 (TRAPPED). And OnlyOnceRegistered. <br>
     * 罠死
     */
    public void setDeadReasonCode_Equal_罠死() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.罠死);
    }

    protected void doSetDeadReasonCode_Equal(String deadReasonCode) {
        regDeadReasonCode(CK_EQ, deadReasonCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     * @param deadReasonCode The value of deadReasonCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_NotEqual(String deadReasonCode) {
        doSetDeadReasonCode_NotEqual(fRES(deadReasonCode));
    }

    /**
     * NotEqual(&lt;&gt;). As DeadReason. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason cdef) {
        doSetDeadReasonCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 襲撃 (ATTACK). And OnlyOnceRegistered. <br>
     * 襲撃
     */
    public void setDeadReasonCode_NotEqual_襲撃() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.襲撃);
    }

    /**
     * NotEqual(&lt;&gt;). As 爆死 (BOMBED). And OnlyOnceRegistered. <br>
     * 爆死
     */
    public void setDeadReasonCode_NotEqual_爆死() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.爆死);
    }

    /**
     * NotEqual(&lt;&gt;). As 呪殺 (DIVINED). And OnlyOnceRegistered. <br>
     * 呪殺
     */
    public void setDeadReasonCode_NotEqual_呪殺() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.呪殺);
    }

    /**
     * NotEqual(&lt;&gt;). As 処刑 (EXECUTE). And OnlyOnceRegistered. <br>
     * 処刑
     */
    public void setDeadReasonCode_NotEqual_処刑() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.処刑);
    }

    /**
     * NotEqual(&lt;&gt;). As 突然 (SUDDON). And OnlyOnceRegistered. <br>
     * 突然
     */
    public void setDeadReasonCode_NotEqual_突然() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.突然);
    }

    /**
     * NotEqual(&lt;&gt;). As 後追 (SUICIDE). And OnlyOnceRegistered. <br>
     * 後追
     */
    public void setDeadReasonCode_NotEqual_後追() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.後追);
    }

    /**
     * NotEqual(&lt;&gt;). As 罠死 (TRAPPED). And OnlyOnceRegistered. <br>
     * 罠死
     */
    public void setDeadReasonCode_NotEqual_罠死() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.罠死);
    }

    protected void doSetDeadReasonCode_NotEqual(String deadReasonCode) {
        regDeadReasonCode(CK_NES, deadReasonCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     * @param deadReasonCodeList The collection of deadReasonCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_InScope(Collection<String> deadReasonCodeList) {
        doSetDeadReasonCode_InScope(deadReasonCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As DeadReason. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDeadReasonCode_InScope_AsDeadReason(Collection<CDef.DeadReason> cdefList) {
        doSetDeadReasonCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As DeadReason. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * 死亡理由 <br>
     * 無惨 <br>
     * The group elements:[襲撃, 呪殺, 罠死, 爆死]
     */
    public void setDeadReasonCode_InScope_Miserable() {
        setDeadReasonCode_InScope_AsDeadReason(CDef.DeadReason.listOfMiserable());
    }

    protected void doSetDeadReasonCode_InScope(Collection<String> deadReasonCodeList) {
        regINS(CK_INS, cTL(deadReasonCodeList), xgetCValueDeadReasonCode(), "DEAD_REASON_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     * @param deadReasonCodeList The collection of deadReasonCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_NotInScope(Collection<String> deadReasonCodeList) {
        doSetDeadReasonCode_NotInScope(deadReasonCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As DeadReason. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason} <br>
     * 死亡理由
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDeadReasonCode_NotInScope_AsDeadReason(Collection<CDef.DeadReason> cdefList) {
        doSetDeadReasonCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetDeadReasonCode_NotInScope(Collection<String> deadReasonCodeList) {
        regINS(CK_NINS, cTL(deadReasonCodeList), xgetCValueDeadReasonCode(), "DEAD_REASON_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     */
    public void setDeadReasonCode_IsNull() { regDeadReasonCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     */
    public void setDeadReasonCode_IsNullOrEmpty() { regDeadReasonCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     */
    public void setDeadReasonCode_IsNotNull() { regDeadReasonCode(CK_ISNN, DOBJ); }

    protected void regDeadReasonCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDeadReasonCode(), "DEAD_REASON_CODE"); }
    protected abstract ConditionValue xgetCValueDeadReasonCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDay The value of deadDay as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadDay_Equal(Integer deadDay) {
        doSetDeadDay_Equal(deadDay);
    }

    protected void doSetDeadDay_Equal(Integer deadDay) {
        regDeadDay(CK_EQ, deadDay);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDay The value of deadDay as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadDay_NotEqual(Integer deadDay) {
        doSetDeadDay_NotEqual(deadDay);
    }

    protected void doSetDeadDay_NotEqual(Integer deadDay) {
        regDeadDay(CK_NES, deadDay);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDay The value of deadDay as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadDay_GreaterThan(Integer deadDay) {
        regDeadDay(CK_GT, deadDay);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDay The value of deadDay as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadDay_LessThan(Integer deadDay) {
        regDeadDay(CK_LT, deadDay);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDay The value of deadDay as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadDay_GreaterEqual(Integer deadDay) {
        regDeadDay(CK_GE, deadDay);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDay The value of deadDay as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadDay_LessEqual(Integer deadDay) {
        regDeadDay(CK_LE, deadDay);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param minNumber The min number of deadDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of deadDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDeadDay_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDeadDay_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param minNumber The min number of deadDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of deadDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDeadDay_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDeadDay(), "DEAD_DAY", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDayList The collection of deadDay as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDeadDay_InScope(Collection<Integer> deadDayList) {
        doSetDeadDay_InScope(deadDayList);
    }

    protected void doSetDeadDay_InScope(Collection<Integer> deadDayList) {
        regINS(CK_INS, cTL(deadDayList), xgetCValueDeadDay(), "DEAD_DAY");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @param deadDayList The collection of deadDay as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDeadDay_NotInScope(Collection<Integer> deadDayList) {
        doSetDeadDay_NotInScope(deadDayList);
    }

    protected void doSetDeadDay_NotInScope(Collection<Integer> deadDayList) {
        regINS(CK_NINS, cTL(deadDayList), xgetCValueDeadDay(), "DEAD_DAY");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     */
    public void setDeadDay_IsNull() { regDeadDay(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * DEAD_DAY: {INT UNSIGNED(10)}
     */
    public void setDeadDay_IsNotNull() { regDeadDay(CK_ISNN, DOBJ); }

    protected void regDeadDay(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDeadDay(), "DEAD_DAY"); }
    protected abstract ConditionValue xgetCValueDeadDay();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_GONE: {NotNull, BIT, classification=Flg}
     * @param isGone The value of isGone as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsGone_Equal(Boolean isGone) {
        regIsGone(CK_EQ, isGone);
    }

    /**
     * Equal(=). As Flg. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IS_GONE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsGone_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsGone_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * はい: 有効を示す
     */
    public void setIsGone_Equal_True() {
        doSetIsGone_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * いいえ: 無効を示す
     */
    public void setIsGone_Equal_False() {
        doSetIsGone_Equal(Boolean.valueOf(CDef.Flg.False.code()));
    }

    protected void doSetIsGone_Equal(Boolean isGone) {
        regIsGone(CK_EQ, isGone);
    }

    protected void regIsGone(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsGone(), "IS_GONE"); }
    protected abstract ConditionValue xgetCValueIsGone();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @param lastAccessDatetime The value of lastAccessDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setLastAccessDatetime_Equal(java.time.LocalDateTime lastAccessDatetime) {
        regLastAccessDatetime(CK_EQ,  lastAccessDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @param lastAccessDatetime The value of lastAccessDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setLastAccessDatetime_GreaterThan(java.time.LocalDateTime lastAccessDatetime) {
        regLastAccessDatetime(CK_GT,  lastAccessDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @param lastAccessDatetime The value of lastAccessDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setLastAccessDatetime_LessThan(java.time.LocalDateTime lastAccessDatetime) {
        regLastAccessDatetime(CK_LT,  lastAccessDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @param lastAccessDatetime The value of lastAccessDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setLastAccessDatetime_GreaterEqual(java.time.LocalDateTime lastAccessDatetime) {
        regLastAccessDatetime(CK_GE,  lastAccessDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @param lastAccessDatetime The value of lastAccessDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setLastAccessDatetime_LessEqual(java.time.LocalDateTime lastAccessDatetime) {
        regLastAccessDatetime(CK_LE, lastAccessDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * <pre>e.g. setLastAccessDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of lastAccessDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of lastAccessDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setLastAccessDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setLastAccessDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * <pre>e.g. setLastAccessDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of lastAccessDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of lastAccessDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setLastAccessDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "LAST_ACCESS_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueLastAccessDatetime(), nm, op);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     */
    public void setLastAccessDatetime_IsNull() { regLastAccessDatetime(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     */
    public void setLastAccessDatetime_IsNotNull() { regLastAccessDatetime(CK_ISNN, DOBJ); }

    protected void regLastAccessDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueLastAccessDatetime(), "LAST_ACCESS_DATETIME"); }
    protected abstract ConditionValue xgetCValueLastAccessDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_Equal(String campCode) {
        doSetCampCode_Equal(fRES(campCode));
    }

    protected void doSetCampCode_Equal(String campCode) {
        regCampCode(CK_EQ, campCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_NotEqual(String campCode) {
        doSetCampCode_NotEqual(fRES(campCode));
    }

    protected void doSetCampCode_NotEqual(String campCode) {
        regCampCode(CK_NES, campCode);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_GreaterThan(String campCode) {
        regCampCode(CK_GT, fRES(campCode));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_LessThan(String campCode) {
        regCampCode(CK_LT, fRES(campCode));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_GreaterEqual(String campCode) {
        regCampCode(CK_GE, fRES(campCode));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_LessEqual(String campCode) {
        regCampCode(CK_LE, fRES(campCode));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCodeList The collection of campCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_InScope(Collection<String> campCodeList) {
        doSetCampCode_InScope(campCodeList);
    }

    protected void doSetCampCode_InScope(Collection<String> campCodeList) {
        regINS(CK_INS, cTL(campCodeList), xgetCValueCampCode(), "CAMP_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCodeList The collection of campCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCampCode_NotInScope(Collection<String> campCodeList) {
        doSetCampCode_NotInScope(campCodeList);
    }

    protected void doSetCampCode_NotInScope(Collection<String> campCodeList) {
        regINS(CK_NINS, cTL(campCodeList), xgetCValueCampCode(), "CAMP_CODE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)} <br>
     * <pre>e.g. setCampCode_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param campCode The value of campCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCampCode_LikeSearch(String campCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCampCode_LikeSearch(campCode, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)} <br>
     * <pre>e.g. setCampCode_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param campCode The value of campCode as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setCampCode_LikeSearch(String campCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(campCode), xgetCValueCampCode(), "CAMP_CODE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCampCode_NotLikeSearch(String campCode, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCampCode_NotLikeSearch(campCode, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     * @param campCode The value of campCode as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setCampCode_NotLikeSearch(String campCode, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(campCode), xgetCValueCampCode(), "CAMP_CODE", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     */
    public void setCampCode_IsNull() { regCampCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     */
    public void setCampCode_IsNullOrEmpty() { regCampCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * CAMP_CODE: {VARCHAR(20)}
     */
    public void setCampCode_IsNotNull() { regCampCode(CK_ISNN, DOBJ); }

    protected void regCampCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCampCode(), "CAMP_CODE"); }
    protected abstract ConditionValue xgetCValueCampCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_WIN: {BIT, classification=Flg}
     * @param isWin The value of isWin as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsWin_Equal(Boolean isWin) {
        regIsWin(CK_EQ, isWin);
    }

    /**
     * Equal(=). As Flg. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IS_WIN: {BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsWin_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsWin_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * はい: 有効を示す
     */
    public void setIsWin_Equal_True() {
        doSetIsWin_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * いいえ: 無効を示す
     */
    public void setIsWin_Equal_False() {
        doSetIsWin_Equal(Boolean.valueOf(CDef.Flg.False.code()));
    }

    protected void doSetIsWin_Equal(Boolean isWin) {
        regIsWin(CK_EQ, isWin);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * IS_WIN: {BIT, classification=Flg}
     */
    public void setIsWin_IsNull() { regIsWin(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * IS_WIN: {BIT, classification=Flg}
     */
    public void setIsWin_IsNotNull() { regIsWin(CK_ISNN, DOBJ); }

    protected void regIsWin(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsWin(), "IS_WIN"); }
    protected abstract ConditionValue xgetCValueIsWin();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_Equal(String charaName) {
        doSetCharaName_Equal(fRES(charaName));
    }

    protected void doSetCharaName_Equal(String charaName) {
        regCharaName(CK_EQ, charaName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_NotEqual(String charaName) {
        doSetCharaName_NotEqual(fRES(charaName));
    }

    protected void doSetCharaName_NotEqual(String charaName) {
        regCharaName(CK_NES, charaName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_GreaterThan(String charaName) {
        regCharaName(CK_GT, fRES(charaName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_LessThan(String charaName) {
        regCharaName(CK_LT, fRES(charaName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_GreaterEqual(String charaName) {
        regCharaName(CK_GE, fRES(charaName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_LessEqual(String charaName) {
        regCharaName(CK_LE, fRES(charaName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaNameList The collection of charaName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_InScope(Collection<String> charaNameList) {
        doSetCharaName_InScope(charaNameList);
    }

    protected void doSetCharaName_InScope(Collection<String> charaNameList) {
        regINS(CK_INS, cTL(charaNameList), xgetCValueCharaName(), "CHARA_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaNameList The collection of charaName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaName_NotInScope(Collection<String> charaNameList) {
        doSetCharaName_NotInScope(charaNameList);
    }

    protected void doSetCharaName_NotInScope(Collection<String> charaNameList) {
        regINS(CK_NINS, cTL(charaNameList), xgetCValueCharaName(), "CHARA_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setCharaName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param charaName The value of charaName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaName_LikeSearch(String charaName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaName_LikeSearch(charaName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setCharaName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param charaName The value of charaName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setCharaName_LikeSearch(String charaName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(charaName), xgetCValueCharaName(), "CHARA_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaName_NotLikeSearch(String charaName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaName_NotLikeSearch(charaName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @param charaName The value of charaName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setCharaName_NotLikeSearch(String charaName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(charaName), xgetCValueCharaName(), "CHARA_NAME", likeSearchOption);
    }

    protected void regCharaName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaName(), "CHARA_NAME"); }
    protected abstract ConditionValue xgetCValueCharaName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_Equal(String charaShortName) {
        doSetCharaShortName_Equal(fRES(charaShortName));
    }

    protected void doSetCharaShortName_Equal(String charaShortName) {
        regCharaShortName(CK_EQ, charaShortName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_NotEqual(String charaShortName) {
        doSetCharaShortName_NotEqual(fRES(charaShortName));
    }

    protected void doSetCharaShortName_NotEqual(String charaShortName) {
        regCharaShortName(CK_NES, charaShortName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_GreaterThan(String charaShortName) {
        regCharaShortName(CK_GT, fRES(charaShortName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_LessThan(String charaShortName) {
        regCharaShortName(CK_LT, fRES(charaShortName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_GreaterEqual(String charaShortName) {
        regCharaShortName(CK_GE, fRES(charaShortName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_LessEqual(String charaShortName) {
        regCharaShortName(CK_LE, fRES(charaShortName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortNameList The collection of charaShortName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_InScope(Collection<String> charaShortNameList) {
        doSetCharaShortName_InScope(charaShortNameList);
    }

    protected void doSetCharaShortName_InScope(Collection<String> charaShortNameList) {
        regINS(CK_INS, cTL(charaShortNameList), xgetCValueCharaShortName(), "CHARA_SHORT_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortNameList The collection of charaShortName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCharaShortName_NotInScope(Collection<String> charaShortNameList) {
        doSetCharaShortName_NotInScope(charaShortNameList);
    }

    protected void doSetCharaShortName_NotInScope(Collection<String> charaShortNameList) {
        regINS(CK_NINS, cTL(charaShortNameList), xgetCValueCharaShortName(), "CHARA_SHORT_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * <pre>e.g. setCharaShortName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param charaShortName The value of charaShortName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaShortName_LikeSearch(String charaShortName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaShortName_LikeSearch(charaShortName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * <pre>e.g. setCharaShortName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param charaShortName The value of charaShortName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setCharaShortName_LikeSearch(String charaShortName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(charaShortName), xgetCValueCharaShortName(), "CHARA_SHORT_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCharaShortName_NotLikeSearch(String charaShortName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCharaShortName_NotLikeSearch(charaShortName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @param charaShortName The value of charaShortName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setCharaShortName_NotLikeSearch(String charaShortName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(charaShortName), xgetCValueCharaShortName(), "CHARA_SHORT_NAME", likeSearchOption);
    }

    protected void regCharaShortName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCharaShortName(), "CHARA_SHORT_NAME"); }
    protected abstract ConditionValue xgetCValueCharaShortName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_Equal(String memo) {
        doSetMemo_Equal(fRES(memo));
    }

    protected void doSetMemo_Equal(String memo) {
        regMemo(CK_EQ, memo);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_NotEqual(String memo) {
        doSetMemo_NotEqual(fRES(memo));
    }

    protected void doSetMemo_NotEqual(String memo) {
        regMemo(CK_NES, memo);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_GreaterThan(String memo) {
        regMemo(CK_GT, fRES(memo));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_LessThan(String memo) {
        regMemo(CK_LT, fRES(memo));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_GreaterEqual(String memo) {
        regMemo(CK_GE, fRES(memo));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_LessEqual(String memo) {
        regMemo(CK_LE, fRES(memo));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memoList The collection of memo as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_InScope(Collection<String> memoList) {
        doSetMemo_InScope(memoList);
    }

    protected void doSetMemo_InScope(Collection<String> memoList) {
        regINS(CK_INS, cTL(memoList), xgetCValueMemo(), "MEMO");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memoList The collection of memo as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMemo_NotInScope(Collection<String> memoList) {
        doSetMemo_NotInScope(memoList);
    }

    protected void doSetMemo_NotInScope(Collection<String> memoList) {
        regINS(CK_NINS, cTL(memoList), xgetCValueMemo(), "MEMO");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MEMO: {VARCHAR(20)} <br>
     * <pre>e.g. setMemo_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param memo The value of memo as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setMemo_LikeSearch(String memo, ConditionOptionCall<LikeSearchOption> opLambda) {
        setMemo_LikeSearch(memo, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MEMO: {VARCHAR(20)} <br>
     * <pre>e.g. setMemo_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param memo The value of memo as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setMemo_LikeSearch(String memo, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(memo), xgetCValueMemo(), "MEMO", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setMemo_NotLikeSearch(String memo, ConditionOptionCall<LikeSearchOption> opLambda) {
        setMemo_NotLikeSearch(memo, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MEMO: {VARCHAR(20)}
     * @param memo The value of memo as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setMemo_NotLikeSearch(String memo, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(memo), xgetCValueMemo(), "MEMO", likeSearchOption);
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     */
    public void setMemo_IsNull() { regMemo(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     */
    public void setMemo_IsNullOrEmpty() { regMemo(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * MEMO: {VARCHAR(20)}
     */
    public void setMemo_IsNotNull() { regMemo(CK_ISNN, DOBJ); }

    protected void regMemo(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMemo(), "MEMO"); }
    protected abstract ConditionValue xgetCValueMemo();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_Equal(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_EQ,  registerDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GT,  registerDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessThan(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LT,  registerDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_GreaterEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_GE,  registerDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @param registerDatetime The value of registerDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRegisterDatetime_LessEqual(java.time.LocalDateTime registerDatetime) {
        regRegisterDatetime(CK_LE, registerDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setRegisterDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setRegisterDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of registerDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setRegisterDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "REGISTER_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueRegisterDatetime(), nm, op);
    }

    protected void regRegisterDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterDatetime(), "REGISTER_DATETIME"); }
    protected abstract ConditionValue xgetCValueRegisterDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_Equal(String registerTrace) {
        doSetRegisterTrace_Equal(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_Equal(String registerTrace) {
        regRegisterTrace(CK_EQ, registerTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotEqual(String registerTrace) {
        doSetRegisterTrace_NotEqual(fRES(registerTrace));
    }

    protected void doSetRegisterTrace_NotEqual(String registerTrace) {
        regRegisterTrace(CK_NES, registerTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterThan(String registerTrace) {
        regRegisterTrace(CK_GT, fRES(registerTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessThan(String registerTrace) {
        regRegisterTrace(CK_LT, fRES(registerTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_GreaterEqual(String registerTrace) {
        regRegisterTrace(CK_GE, fRES(registerTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_LessEqual(String registerTrace) {
        regRegisterTrace(CK_LE, fRES(registerTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_InScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_InScope(registerTraceList);
    }

    protected void doSetRegisterTrace_InScope(Collection<String> registerTraceList) {
        regINS(CK_INS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTraceList The collection of registerTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        doSetRegisterTrace_NotInScope(registerTraceList);
    }

    protected void doSetRegisterTrace_NotInScope(Collection<String> registerTraceList) {
        regINS(CK_NINS, cTL(registerTraceList), xgetCValueRegisterTrace(), "REGISTER_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_LikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_LikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setRegisterTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param registerTrace The value of registerTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setRegisterTrace_LikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setRegisterTrace_NotLikeSearch(String registerTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setRegisterTrace_NotLikeSearch(registerTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @param registerTrace The value of registerTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setRegisterTrace_NotLikeSearch(String registerTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(registerTrace), xgetCValueRegisterTrace(), "REGISTER_TRACE", likeSearchOption);
    }

    protected void regRegisterTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRegisterTrace(), "REGISTER_TRACE"); }
    protected abstract ConditionValue xgetCValueRegisterTrace();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_Equal(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_EQ,  updateDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GT,  updateDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessThan(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LT,  updateDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_GreaterEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_GE,  updateDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @param updateDatetime The value of updateDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setUpdateDatetime_LessEqual(java.time.LocalDateTime updateDatetime) {
        regUpdateDatetime(CK_LE, updateDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setUpdateDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setUpdateDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of updateDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setUpdateDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "UPDATE_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueUpdateDatetime(), nm, op);
    }

    protected void regUpdateDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateDatetime(), "UPDATE_DATETIME"); }
    protected abstract ConditionValue xgetCValueUpdateDatetime();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_Equal(String updateTrace) {
        doSetUpdateTrace_Equal(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_Equal(String updateTrace) {
        regUpdateTrace(CK_EQ, updateTrace);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotEqual(String updateTrace) {
        doSetUpdateTrace_NotEqual(fRES(updateTrace));
    }

    protected void doSetUpdateTrace_NotEqual(String updateTrace) {
        regUpdateTrace(CK_NES, updateTrace);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterThan(String updateTrace) {
        regUpdateTrace(CK_GT, fRES(updateTrace));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessThan(String updateTrace) {
        regUpdateTrace(CK_LT, fRES(updateTrace));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_GreaterEqual(String updateTrace) {
        regUpdateTrace(CK_GE, fRES(updateTrace));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_LessEqual(String updateTrace) {
        regUpdateTrace(CK_LE, fRES(updateTrace));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_InScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_InScope(updateTraceList);
    }

    protected void doSetUpdateTrace_InScope(Collection<String> updateTraceList) {
        regINS(CK_INS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTraceList The collection of updateTrace as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        doSetUpdateTrace_NotInScope(updateTraceList);
    }

    protected void doSetUpdateTrace_NotInScope(Collection<String> updateTraceList) {
        regINS(CK_NINS, cTL(updateTraceList), xgetCValueUpdateTrace(), "UPDATE_TRACE");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_LikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_LikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * <pre>e.g. setUpdateTrace_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param updateTrace The value of updateTrace as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setUpdateTrace_LikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setUpdateTrace_NotLikeSearch(String updateTrace, ConditionOptionCall<LikeSearchOption> opLambda) {
        setUpdateTrace_NotLikeSearch(updateTrace, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @param updateTrace The value of updateTrace as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setUpdateTrace_NotLikeSearch(String updateTrace, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(updateTrace), xgetCValueUpdateTrace(), "UPDATE_TRACE", likeSearchOption);
    }

    protected void regUpdateTrace(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueUpdateTrace(), "UPDATE_TRACE"); }
    protected abstract ConditionValue xgetCValueUpdateTrace();

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO = (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillagePlayerCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, VillagePlayerCB.class);
    }

    /**
     * Prepare ScalarCondition as equal. <br>
     * {where FOO &lt;&gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillagePlayerCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, VillagePlayerCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterThan. <br>
     * {where FOO &gt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillagePlayerCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, VillagePlayerCB.class);
    }

    /**
     * Prepare ScalarCondition as lessThan. <br>
     * {where FOO &lt; (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillagePlayerCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, VillagePlayerCB.class);
    }

    /**
     * Prepare ScalarCondition as greaterEqual. <br>
     * {where FOO &gt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().scalar_Equal().<span style="color: #CC4747">avg</span>(<span style="color: #553000">purchaseCB</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">purchaseCB</span>.specify().<span style="color: #CC4747">columnPurchasePrice</span>(); <span style="color: #3F7E5E">// *Point!</span>
     *     <span style="color: #553000">purchaseCB</span>.query().setPaymentCompleteFlg_Equal_True();
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillagePlayerCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, VillagePlayerCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;VillagePlayerCB&gt;() {
     *     public void query(VillagePlayerCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillagePlayerCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, VillagePlayerCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(VillagePlayerCQ sq);

    protected VillagePlayerCB xcreateScalarConditionCB() {
        VillagePlayerCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected VillagePlayerCB xcreateScalarConditionPartitionByCB() {
        VillagePlayerCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<VillagePlayerCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VILLAGE_PLAYER_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(VillagePlayerCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(VillagePlayerCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VILLAGE_PLAYER_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(VillagePlayerCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<VillagePlayerCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(VillagePlayerCQ sq);

    // ===================================================================================
    //                                                                        Manual Order
    //                                                                        ============
    /**
     * Order along manual ordering information.
     * <pre>
     * cb.query().addOrderBy_Birthdate_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when BIRTHDATE &gt;= '2000/01/01' then 0</span>
     * <span style="color: #3F7E5E">//     else 1</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     *
     * cb.query().addOrderBy_MemberStatusCode_Asc().<span style="color: #CC4747">withManualOrder</span>(<span style="color: #553000">op</span> <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Withdrawal);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Formalized);
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_Equal</span>(CDef.MemberStatus.Provisional);
     * });
     * <span style="color: #3F7E5E">// order by </span>
     * <span style="color: #3F7E5E">//   case</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'WDL' then 0</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'FML' then 1</span>
     * <span style="color: #3F7E5E">//     when MEMBER_STATUS_CODE = 'PRV' then 2</span>
     * <span style="color: #3F7E5E">//     else 3</span>
     * <span style="color: #3F7E5E">//   end asc, ...</span>
     * </pre>
     * <p>This function with Union is unsupported!</p>
     * <p>The order values are bound (treated as bind parameter).</p>
     * @param opLambda The callback for option of manual-order containing order values. (NotNull)
     */
    public void withManualOrder(ManualOrderOptionCall opLambda) { // is user public!
        xdoWithManualOrder(cMOO(opLambda));
    }

    // ===================================================================================
    //                                                                    Small Adjustment
    //                                                                    ================
    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    protected VillagePlayerCB newMyCB() {
        return new VillagePlayerCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return VillagePlayerCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
