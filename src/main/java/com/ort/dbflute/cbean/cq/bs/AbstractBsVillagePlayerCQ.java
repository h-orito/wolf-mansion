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
 * The abstract condition-query of VILLAGE_PLAYER.
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
        return "VILLAGE_PLAYER";
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
     * {exists (select VILLAGE_PLAYER_ID from COMMIT where ...)} <br>
     * COMMIT by VILLAGE_PLAYER_ID, named 'commitAsOne'.
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
     * {exists (select TO_VILLAGE_PLAYER_ID from MESSAGE where ...)} <br>
     * MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdAsOne'.
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
     * {exists (select VILLAGE_PLAYER_ID from MESSAGE where ...)} <br>
     * MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdAsOne'.
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
     * {exists (select VILLAGE_PLAYER_ID from MESSAGE_SENDTO where ...)} <br>
     * MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoAsOne'.
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
     * {exists (select VILLAGE_PLAYER_ID from VILLAGE_PLAYER_DEAD_HISTORY where ...)} <br>
     * VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryAsOne'.
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
     * {exists (select VILLAGE_PLAYER_ID from VILLAGE_PLAYER_ROOM_HISTORY where ...)} <br>
     * VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryAsOne'.
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
     * {exists (select TO_VILLAGE_PLAYER_ID from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdAsOne'.
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
     * {exists (select VILLAGE_PLAYER_ID from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdAsOne'.
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
     * {not exists (select VILLAGE_PLAYER_ID from COMMIT where ...)} <br>
     * COMMIT by VILLAGE_PLAYER_ID, named 'commitAsOne'.
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
     * {not exists (select TO_VILLAGE_PLAYER_ID from MESSAGE where ...)} <br>
     * MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdAsOne'.
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
     * {not exists (select VILLAGE_PLAYER_ID from MESSAGE where ...)} <br>
     * MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdAsOne'.
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
     * {not exists (select VILLAGE_PLAYER_ID from MESSAGE_SENDTO where ...)} <br>
     * MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoAsOne'.
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
     * {not exists (select VILLAGE_PLAYER_ID from VILLAGE_PLAYER_DEAD_HISTORY where ...)} <br>
     * VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryAsOne'.
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
     * {not exists (select VILLAGE_PLAYER_ID from VILLAGE_PLAYER_ROOM_HISTORY where ...)} <br>
     * VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryAsOne'.
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
     * {not exists (select TO_VILLAGE_PLAYER_ID from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdAsOne'.
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
     * {not exists (select VILLAGE_PLAYER_ID from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdAsOne'.
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
     * {FOO &lt;= (select max(BAR) from COMMIT where ...)} <br>
     * COMMIT by VILLAGE_PLAYER_ID, named 'commitAsOne'.
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
     * {FOO &lt;= (select max(BAR) from MESSAGE where ...)} <br>
     * MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdAsOne'.
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
     * {FOO &lt;= (select max(BAR) from MESSAGE where ...)} <br>
     * MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdAsOne'.
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
     * {FOO &lt;= (select max(BAR) from MESSAGE_SENDTO where ...)} <br>
     * MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoAsOne'.
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
     * {FOO &lt;= (select max(BAR) from VILLAGE_PLAYER_DEAD_HISTORY where ...)} <br>
     * VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryAsOne'.
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
     * {FOO &lt;= (select max(BAR) from VILLAGE_PLAYER_ROOM_HISTORY where ...)} <br>
     * VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryAsOne'.
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
     * {FOO &lt;= (select max(BAR) from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdAsOne'.
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
     * {FOO &lt;= (select max(BAR) from VILLAGE_PLAYER_STATUS where ...)} <br>
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdAsOne'.
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
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_GreaterThan(Integer playerId) {
        regPlayerId(CK_GT, playerId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_LessThan(Integer playerId) {
        regPlayerId(CK_LT, playerId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_GreaterEqual(Integer playerId) {
        regPlayerId(CK_GE, playerId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_LessEqual(Integer playerId) {
        regPlayerId(CK_LE, playerId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @param minNumber The min number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setPlayerId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValuePlayerId(), "PLAYER_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
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
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
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
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
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
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterThan(Integer charaId) {
        regCharaId(CK_GT, charaId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessThan(Integer charaId) {
        regCharaId(CK_LT, charaId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_GreaterEqual(Integer charaId) {
        regCharaId(CK_GE, charaId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param charaId The value of charaId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setCharaId_LessEqual(Integer charaId) {
        regCharaId(CK_LE, charaId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
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
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @param minNumber The min number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of charaId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setCharaId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueCharaId(), "CHARA_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
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
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
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
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCode The value of skillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_Equal(String skillCode) {
        doSetSkillCode_Equal(fRES(skillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As ???????????? (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSkillCode_Equal_????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ???????????? (ASTROLOGER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSkillCode_Equal_????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ????????? (BAKERY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (BOMBER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As C????????? (CMADMAN). And OnlyOnceRegistered. <br>
     * C?????????
     */
    public void setSkillCode_Equal_C?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.C?????????);
    }

    /**
     * Equal(=). As ????????? (COHABITER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (COMMANDER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (CORONER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (COURTSHIP). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (CURSEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (DETECTIVE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (FANATIC). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????????????????? (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * ???????????????????????????
     */
    public void setSkillCode_Equal_?????????????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????????????????);
    }

    /**
     * Equal(=). As ?????? (FOX). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ??????????????????????????? (FRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSkillCode_Equal_???????????????????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * Equal(=). As ????????? (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (GURU). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (HUNTER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (IMMORAL). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ???????????? (LEFTOVER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSkillCode_Equal_????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ?????? (LOVER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (LOVERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSkillCode_Equal_????????????????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ????????? (LUCKYMAN). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (MADMAN). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (MASON). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (MEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ??????????????????????????? (NOFRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSkillCode_Equal_???????????????????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * Equal(=). As ?????????????????? (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * ????????????????????????
     */
    public void setSkillCode_Equal_??????????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????????????????);
    }

    /**
     * Equal(=). As ??? (OWL). And OnlyOnceRegistered. <br>
     * ???
     */
    public void setSkillCode_Equal_???() {
        setSkillCode_Equal_AsSkill(CDef.Skill.???);
    }

    /**
     * Equal(=). As ????????? (SEER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_Equal_?????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ??????????????? (STALKER). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setSkillCode_Equal_???????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.???????????????);
    }

    /**
     * Equal(=). As ?????? (TRAPPER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (VILLAGER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (VILLAGERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSkillCode_Equal_????????????????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ?????? (WEREWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (WEREWOLFS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSkillCode_Equal_????????????????????????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ?????? (WISE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (WISEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_Equal_??????() {
        setSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    protected void doSetSkillCode_Equal(String skillCode) {
        regSkillCode(CK_EQ, skillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCode The value of skillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotEqual(String skillCode) {
        doSetSkillCode_NotEqual(fRES(skillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSkillCode_NotEqual_????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (ASTROLOGER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSkillCode_NotEqual_????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (BAKERY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (BOMBER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As C????????? (CMADMAN). And OnlyOnceRegistered. <br>
     * C?????????
     */
    public void setSkillCode_NotEqual_C?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.C?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COHABITER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COMMANDER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (CORONER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COURTSHIP). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (CURSEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (DETECTIVE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (FANATIC). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????????????????? (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * ???????????????????????????
     */
    public void setSkillCode_NotEqual_?????????????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (FOX). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????????????????? (FRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSkillCode_NotEqual_???????????????????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (GURU). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (HUNTER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (IMMORAL). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (LEFTOVER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSkillCode_NotEqual_????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (LOVER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (LOVERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSkillCode_NotEqual_????????????????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (LUCKYMAN). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (MADMAN). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (MASON). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (MEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????????????????? (NOFRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSkillCode_NotEqual_???????????????????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * ????????????????????????
     */
    public void setSkillCode_NotEqual_??????????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??? (OWL). And OnlyOnceRegistered. <br>
     * ???
     */
    public void setSkillCode_NotEqual_???() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.???);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (SEER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSkillCode_NotEqual_?????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????? (STALKER). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setSkillCode_NotEqual_???????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (TRAPPER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (VILLAGER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (VILLAGERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSkillCode_NotEqual_????????????????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WEREWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (WEREWOLFS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSkillCode_NotEqual_????????????????????????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WISE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WISEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSkillCode_NotEqual_??????() {
        setSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    protected void doSetSkillCode_NotEqual(String skillCode) {
        regSkillCode(CK_NES, skillCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCodeList The collection of skillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_InScope(Collection<String> skillCodeList) {
        doSetSkillCode_InScope(skillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSkillCode_InScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSkillCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ???????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????, C?????????]
     */
    public void setSkillCode_InScope_AvailableWerewolfSay() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[?????????, ??????, ????????????]
     */
    public void setSkillCode_InScope_HasDivineAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ??????????????????????????? <br>
     * The group elements:[??????, ?????????]
     */
    public void setSkillCode_InScope_HasSkillPsychicAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????]
     */
    public void setSkillCode_InScope_HasAttackAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[C?????????, ??????, ?????????, ?????????]
     */
    public void setSkillCode_InScope_HasMadmanAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasMadmanAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[C?????????, ??????, ?????????, ?????????, ??????, ?????????]
     */
    public void setSkillCode_InScope_HasDisturbAbility() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ?????????????????????????????? <br>
     * The group elements:[??????, ?????????]
     */
    public void setSkillCode_InScope_NoDeadByAttack() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ?????????????????????????????????????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????, C?????????, ?????????]
     */
    public void setSkillCode_InScope_ViewableWolfCharaName() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ??????????????? <br>
     * The group elements:[????????????, ????????????????????????, ????????????????????????, ????????????????????????, ?????????????????????, ???????????????????????????, ???????????????????????????, ??????????????????]
     */
    public void setSkillCode_InScope_SomeoneSkill() {
        setSkillCode_InScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
    }

    protected void doSetSkillCode_InScope(Collection<String> skillCodeList) {
        regINS(CK_INS, cTL(skillCodeList), xgetCValueSkillCode(), "SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param skillCodeList The collection of skillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSkillCode_NotInScope(Collection<String> skillCodeList) {
        doSetSkillCode_NotInScope(skillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
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
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setSkillCode_IsNull() { regSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setSkillCode_IsNullOrEmpty() { regSkillCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setSkillCode_IsNotNull() { regSkillCode(CK_ISNN, DOBJ); }

    protected void regSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueSkillCode(), "SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueSkillCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param requestSkillCode The value of requestSkillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_Equal(String requestSkillCode) {
        doSetRequestSkillCode_Equal(fRES(requestSkillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setRequestSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetRequestSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As ???????????? (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setRequestSkillCode_Equal_????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ???????????? (ASTROLOGER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setRequestSkillCode_Equal_????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ????????? (BAKERY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (BOMBER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As C????????? (CMADMAN). And OnlyOnceRegistered. <br>
     * C?????????
     */
    public void setRequestSkillCode_Equal_C?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.C?????????);
    }

    /**
     * Equal(=). As ????????? (COHABITER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (COMMANDER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (CORONER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (COURTSHIP). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (CURSEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (DETECTIVE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (FANATIC). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????????????????? (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * ???????????????????????????
     */
    public void setRequestSkillCode_Equal_?????????????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????????????????);
    }

    /**
     * Equal(=). As ?????? (FOX). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ??????????????????????????? (FRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setRequestSkillCode_Equal_???????????????????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * Equal(=). As ????????? (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (GURU). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (HUNTER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (IMMORAL). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ???????????? (LEFTOVER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setRequestSkillCode_Equal_????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ?????? (LOVER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (LOVERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setRequestSkillCode_Equal_????????????????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ????????? (LUCKYMAN). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (MADMAN). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (MASON). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (MEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ??????????????????????????? (NOFRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setRequestSkillCode_Equal_???????????????????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * Equal(=). As ?????????????????? (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * ????????????????????????
     */
    public void setRequestSkillCode_Equal_??????????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????????????????);
    }

    /**
     * Equal(=). As ??? (OWL). And OnlyOnceRegistered. <br>
     * ???
     */
    public void setRequestSkillCode_Equal_???() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.???);
    }

    /**
     * Equal(=). As ????????? (SEER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_Equal_?????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ??????????????? (STALKER). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setRequestSkillCode_Equal_???????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.???????????????);
    }

    /**
     * Equal(=). As ?????? (TRAPPER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (VILLAGER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (VILLAGERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setRequestSkillCode_Equal_????????????????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ?????? (WEREWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (WEREWOLFS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setRequestSkillCode_Equal_????????????????????????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ?????? (WISE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (WISEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_Equal_??????() {
        setRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    protected void doSetRequestSkillCode_Equal(String requestSkillCode) {
        regRequestSkillCode(CK_EQ, requestSkillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param requestSkillCode The value of requestSkillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_NotEqual(String requestSkillCode) {
        doSetRequestSkillCode_NotEqual(fRES(requestSkillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setRequestSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetRequestSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setRequestSkillCode_NotEqual_????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (ASTROLOGER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setRequestSkillCode_NotEqual_????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (BAKERY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (BOMBER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As C????????? (CMADMAN). And OnlyOnceRegistered. <br>
     * C?????????
     */
    public void setRequestSkillCode_NotEqual_C?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.C?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COHABITER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COMMANDER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (CORONER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COURTSHIP). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (CURSEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (DETECTIVE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (FANATIC). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????????????????? (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * ???????????????????????????
     */
    public void setRequestSkillCode_NotEqual_?????????????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (FOX). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????????????????? (FRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setRequestSkillCode_NotEqual_???????????????????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (GURU). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (HUNTER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (IMMORAL). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (LEFTOVER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setRequestSkillCode_NotEqual_????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (LOVER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (LOVERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setRequestSkillCode_NotEqual_????????????????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (LUCKYMAN). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (MADMAN). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (MASON). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (MEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????????????????? (NOFRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setRequestSkillCode_NotEqual_???????????????????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * ????????????????????????
     */
    public void setRequestSkillCode_NotEqual_??????????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??? (OWL). And OnlyOnceRegistered. <br>
     * ???
     */
    public void setRequestSkillCode_NotEqual_???() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (SEER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setRequestSkillCode_NotEqual_?????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????? (STALKER). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setRequestSkillCode_NotEqual_???????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (TRAPPER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (VILLAGER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (VILLAGERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setRequestSkillCode_NotEqual_????????????????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WEREWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (WEREWOLFS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setRequestSkillCode_NotEqual_????????????????????????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WISE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WISEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setRequestSkillCode_NotEqual_??????() {
        setRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    protected void doSetRequestSkillCode_NotEqual(String requestSkillCode) {
        regRequestSkillCode(CK_NES, requestSkillCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param requestSkillCodeList The collection of requestSkillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_InScope(Collection<String> requestSkillCodeList) {
        doSetRequestSkillCode_InScope(requestSkillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRequestSkillCode_InScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetRequestSkillCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ???????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????, C?????????]
     */
    public void setRequestSkillCode_InScope_AvailableWerewolfSay() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[?????????, ??????, ????????????]
     */
    public void setRequestSkillCode_InScope_HasDivineAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ??????????????????????????? <br>
     * The group elements:[??????, ?????????]
     */
    public void setRequestSkillCode_InScope_HasSkillPsychicAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????]
     */
    public void setRequestSkillCode_InScope_HasAttackAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[C?????????, ??????, ?????????, ?????????]
     */
    public void setRequestSkillCode_InScope_HasMadmanAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasMadmanAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[C?????????, ??????, ?????????, ?????????, ??????, ?????????]
     */
    public void setRequestSkillCode_InScope_HasDisturbAbility() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ?????????????????????????????? <br>
     * The group elements:[??????, ?????????]
     */
    public void setRequestSkillCode_InScope_NoDeadByAttack() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ?????????????????????????????????????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????, C?????????, ?????????]
     */
    public void setRequestSkillCode_InScope_ViewableWolfCharaName() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ??????????????? <br>
     * The group elements:[????????????, ????????????????????????, ????????????????????????, ????????????????????????, ?????????????????????, ???????????????????????????, ???????????????????????????, ??????????????????]
     */
    public void setRequestSkillCode_InScope_SomeoneSkill() {
        setRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
    }

    protected void doSetRequestSkillCode_InScope(Collection<String> requestSkillCodeList) {
        regINS(CK_INS, cTL(requestSkillCodeList), xgetCValueRequestSkillCode(), "REQUEST_SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param requestSkillCodeList The collection of requestSkillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setRequestSkillCode_NotInScope(Collection<String> requestSkillCodeList) {
        doSetRequestSkillCode_NotInScope(requestSkillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
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
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setRequestSkillCode_IsNull() { regRequestSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setRequestSkillCode_IsNullOrEmpty() { regRequestSkillCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setRequestSkillCode_IsNotNull() { regRequestSkillCode(CK_ISNN, DOBJ); }

    protected void regRequestSkillCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRequestSkillCode(), "REQUEST_SKILL_CODE"); }
    protected abstract ConditionValue xgetCValueRequestSkillCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param secondRequestSkillCode The value of secondRequestSkillCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_Equal(String secondRequestSkillCode) {
        doSetSecondRequestSkillCode_Equal(fRES(secondRequestSkillCode));
    }

    /**
     * Equal(=). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill cdef) {
        doSetSecondRequestSkillCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As ???????????? (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSecondRequestSkillCode_Equal_????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ???????????? (ASTROLOGER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSecondRequestSkillCode_Equal_????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ????????? (BAKERY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (BOMBER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As C????????? (CMADMAN). And OnlyOnceRegistered. <br>
     * C?????????
     */
    public void setSecondRequestSkillCode_Equal_C?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.C?????????);
    }

    /**
     * Equal(=). As ????????? (COHABITER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (COMMANDER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (CORONER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (COURTSHIP). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (CURSEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (DETECTIVE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (FANATIC). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????????????????? (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * ???????????????????????????
     */
    public void setSecondRequestSkillCode_Equal_?????????????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????????????????);
    }

    /**
     * Equal(=). As ?????? (FOX). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ??????????????????????????? (FRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSecondRequestSkillCode_Equal_???????????????????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * Equal(=). As ????????? (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (GURU). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (HUNTER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (IMMORAL). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ???????????? (LEFTOVER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSecondRequestSkillCode_Equal_????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????);
    }

    /**
     * Equal(=). As ?????? (LOVER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (LOVERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSecondRequestSkillCode_Equal_????????????????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ????????? (LUCKYMAN). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ?????? (MADMAN). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ????????? (MASON). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ????????? (MEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ??????????????????????????? (NOFRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSecondRequestSkillCode_Equal_???????????????????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * Equal(=). As ?????????????????? (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * ????????????????????????
     */
    public void setSecondRequestSkillCode_Equal_??????????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????????????????);
    }

    /**
     * Equal(=). As ??? (OWL). And OnlyOnceRegistered. <br>
     * ???
     */
    public void setSecondRequestSkillCode_Equal_???() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.???);
    }

    /**
     * Equal(=). As ????????? (SEER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_Equal_?????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.?????????);
    }

    /**
     * Equal(=). As ??????????????? (STALKER). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setSecondRequestSkillCode_Equal_???????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.???????????????);
    }

    /**
     * Equal(=). As ?????? (TRAPPER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (VILLAGER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (VILLAGERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSecondRequestSkillCode_Equal_????????????????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ?????? (WEREWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ???????????????????????? (WEREWOLFS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSecondRequestSkillCode_Equal_????????????????????????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * Equal(=). As ?????? (WISE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    /**
     * Equal(=). As ?????? (WISEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_Equal_??????() {
        setSecondRequestSkillCode_Equal_AsSkill(CDef.Skill.??????);
    }

    protected void doSetSecondRequestSkillCode_Equal(String secondRequestSkillCode) {
        regSecondRequestSkillCode(CK_EQ, secondRequestSkillCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param secondRequestSkillCode The value of secondRequestSkillCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_NotEqual(String secondRequestSkillCode) {
        doSetSecondRequestSkillCode_NotEqual(fRES(secondRequestSkillCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Skill. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill cdef) {
        doSetSecondRequestSkillCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (ABSOLUTEWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSecondRequestSkillCode_NotEqual_????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (ASTROLOGER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSecondRequestSkillCode_NotEqual_????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (BAKERY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (BOMBER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As C????????? (CMADMAN). And OnlyOnceRegistered. <br>
     * C?????????
     */
    public void setSecondRequestSkillCode_NotEqual_C?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.C?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COHABITER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COMMANDER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (CORONER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (COURTSHIP). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (CURSEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (DETECTIVE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (EVILMEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (FANATIC). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????????????????? (FOOTSTEPS). And OnlyOnceRegistered. <br>
     * ???????????????????????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (FOX). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????????????????? (FRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSecondRequestSkillCode_NotEqual_???????????????????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (FRUITSBASKET). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (GURU). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (HUNTER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (IMMORAL). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (LEFTOVER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setSecondRequestSkillCode_NotEqual_????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (LOVER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (LOVERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSecondRequestSkillCode_NotEqual_????????????????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (LUCKYMAN). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (MADMAN). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (MASON). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (MEDIUM). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????????????????? (NOFRIENDS). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setSecondRequestSkillCode_NotEqual_???????????????????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (NOVILLAGERS). And OnlyOnceRegistered. <br>
     * ????????????????????????
     */
    public void setSecondRequestSkillCode_NotEqual_??????????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??? (OWL). And OnlyOnceRegistered. <br>
     * ???
     */
    public void setSecondRequestSkillCode_NotEqual_???() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (SEER). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setSecondRequestSkillCode_NotEqual_?????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????? (STALKER). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setSecondRequestSkillCode_NotEqual_???????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.???????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (TRAPPER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (VILLAGER). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (VILLAGERS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSecondRequestSkillCode_NotEqual_????????????????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WEREWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????? (WEREWOLFS). And OnlyOnceRegistered. <br>
     * ??????????????????????????????
     */
    public void setSecondRequestSkillCode_NotEqual_????????????????????????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WISE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (WISEWOLF). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setSecondRequestSkillCode_NotEqual_??????() {
        setSecondRequestSkillCode_NotEqual_AsSkill(CDef.Skill.??????);
    }

    protected void doSetSecondRequestSkillCode_NotEqual(String secondRequestSkillCode) {
        regSecondRequestSkillCode(CK_NES, secondRequestSkillCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param secondRequestSkillCodeList The collection of secondRequestSkillCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_InScope(Collection<String> secondRequestSkillCodeList) {
        doSetSecondRequestSkillCode_InScope(secondRequestSkillCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setSecondRequestSkillCode_InScope_AsSkill(Collection<CDef.Skill> cdefList) {
        doSetSecondRequestSkillCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ???????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????, C?????????]
     */
    public void setSecondRequestSkillCode_InScope_AvailableWerewolfSay() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfAvailableWerewolfSay());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[?????????, ??????, ????????????]
     */
    public void setSecondRequestSkillCode_InScope_HasDivineAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDivineAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ??????????????????????????? <br>
     * The group elements:[??????, ?????????]
     */
    public void setSecondRequestSkillCode_InScope_HasSkillPsychicAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasSkillPsychicAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????]
     */
    public void setSecondRequestSkillCode_InScope_HasAttackAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasAttackAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[C?????????, ??????, ?????????, ?????????]
     */
    public void setSecondRequestSkillCode_InScope_HasMadmanAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasMadmanAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ????????????????????? <br>
     * The group elements:[C?????????, ??????, ?????????, ?????????, ??????, ?????????]
     */
    public void setSecondRequestSkillCode_InScope_HasDisturbAbility() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfHasDisturbAbility());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ?????????????????????????????? <br>
     * The group elements:[??????, ?????????]
     */
    public void setSecondRequestSkillCode_InScope_NoDeadByAttack() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfNoDeadByAttack());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ?????????????????????????????????????????? <br>
     * The group elements:[??????, ??????, ??????, ????????????, C?????????, ?????????]
     */
    public void setSecondRequestSkillCode_InScope_ViewableWolfCharaName() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfViewableWolfCharaName());
    }

    /**
     * InScope {in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ?????? <br>
     * ??????????????? <br>
     * The group elements:[????????????, ????????????????????????, ????????????????????????, ????????????????????????, ?????????????????????, ???????????????????????????, ???????????????????????????, ??????????????????]
     */
    public void setSecondRequestSkillCode_InScope_SomeoneSkill() {
        setSecondRequestSkillCode_InScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
    }

    protected void doSetSecondRequestSkillCode_InScope(Collection<String> secondRequestSkillCodeList) {
        regINS(CK_INS, cTL(secondRequestSkillCodeList), xgetCValueSecondRequestSkillCode(), "SECOND_REQUEST_SKILL_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @param secondRequestSkillCodeList The collection of secondRequestSkillCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setSecondRequestSkillCode_NotInScope(Collection<String> secondRequestSkillCodeList) {
        doSetSecondRequestSkillCode_NotInScope(secondRequestSkillCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Skill. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill} <br>
     * ??????
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
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setSecondRequestSkillCode_IsNull() { regSecondRequestSkillCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     */
    public void setSecondRequestSkillCode_IsNullOrEmpty() { regSecondRequestSkillCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
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
     * ??????????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsDead_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsDead_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * ??????: ???????????????
     */
    public void setIsDead_Equal_True() {
        doSetIsDead_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * ?????????: ???????????????
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
     * ??????????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsSpectator_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsSpectator_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * ??????: ???????????????
     */
    public void setIsSpectator_Equal_True() {
        doSetIsSpectator_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * ?????????: ???????????????
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
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     * @param deadReasonCode The value of deadReasonCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_Equal(String deadReasonCode) {
        doSetDeadReasonCode_Equal(fRES(deadReasonCode));
    }

    /**
     * Equal(=). As DeadReason. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * ????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason cdef) {
        doSetDeadReasonCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As ?????? (ATTACK). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_Equal_??????() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * Equal(=). As ?????? (BOMBED). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_Equal_??????() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * Equal(=). As ?????? (DIVINED). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_Equal_??????() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * Equal(=). As ?????? (EXECUTE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_Equal_??????() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * Equal(=). As ?????? (SUDDON). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_Equal_??????() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * Equal(=). As ?????? (SUICIDE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_Equal_??????() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * Equal(=). As ?????? (TRAPPED). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_Equal_??????() {
        setDeadReasonCode_Equal_AsDeadReason(CDef.DeadReason.??????);
    }

    protected void doSetDeadReasonCode_Equal(String deadReasonCode) {
        regDeadReasonCode(CK_EQ, deadReasonCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     * @param deadReasonCode The value of deadReasonCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_NotEqual(String deadReasonCode) {
        doSetDeadReasonCode_NotEqual(fRES(deadReasonCode));
    }

    /**
     * NotEqual(&lt;&gt;). As DeadReason. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * ????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason cdef) {
        doSetDeadReasonCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (ATTACK). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_NotEqual_??????() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (BOMBED). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_NotEqual_??????() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (DIVINED). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_NotEqual_??????() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (EXECUTE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_NotEqual_??????() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (SUDDON). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_NotEqual_??????() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (SUICIDE). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_NotEqual_??????() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (TRAPPED). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setDeadReasonCode_NotEqual_??????() {
        setDeadReasonCode_NotEqual_AsDeadReason(CDef.DeadReason.??????);
    }

    protected void doSetDeadReasonCode_NotEqual(String deadReasonCode) {
        regDeadReasonCode(CK_NES, deadReasonCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     * @param deadReasonCodeList The collection of deadReasonCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_InScope(Collection<String> deadReasonCodeList) {
        doSetDeadReasonCode_InScope(deadReasonCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As DeadReason. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * ????????????
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDeadReasonCode_InScope_AsDeadReason(Collection<CDef.DeadReason> cdefList) {
        doSetDeadReasonCode_InScope(cTStrL(cdefList));
    }

    /**
     * InScope {in ('a', 'b')}. As DeadReason. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * ???????????? <br>
     * ?????? <br>
     * The group elements:[??????, ??????, ??????, ??????]
     */
    public void setDeadReasonCode_InScope_Miserable() {
        setDeadReasonCode_InScope_AsDeadReason(CDef.DeadReason.listOfMiserable());
    }

    protected void doSetDeadReasonCode_InScope(Collection<String> deadReasonCodeList) {
        regINS(CK_INS, cTL(deadReasonCodeList), xgetCValueDeadReasonCode(), "DEAD_REASON_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     * @param deadReasonCodeList The collection of deadReasonCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setDeadReasonCode_NotInScope(Collection<String> deadReasonCodeList) {
        doSetDeadReasonCode_NotInScope(deadReasonCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As DeadReason. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason} <br>
     * ????????????
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
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     */
    public void setDeadReasonCode_IsNull() { regDeadReasonCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     */
    public void setDeadReasonCode_IsNullOrEmpty() { regDeadReasonCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
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
     * ??????????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsGone_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsGone_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * ??????: ???????????????
     */
    public void setIsGone_Equal_True() {
        doSetIsGone_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * ?????????: ???????????????
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
     * ??????????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsWin_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsWin_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * ??????: ???????????????
     */
    public void setIsWin_Equal_True() {
        doSetIsWin_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * ?????????: ???????????????
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
