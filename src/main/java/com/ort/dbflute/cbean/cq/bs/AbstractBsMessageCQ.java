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
 * The abstract condition-query of MESSAGE.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsMessageCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsMessageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "MESSAGE";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageId The value of messageId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageId_Equal(Integer messageId) {
        doSetMessageId_Equal(messageId);
    }

    protected void doSetMessageId_Equal(Integer messageId) {
        regMessageId(CK_EQ, messageId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageId The value of messageId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageId_NotEqual(Integer messageId) {
        doSetMessageId_NotEqual(messageId);
    }

    protected void doSetMessageId_NotEqual(Integer messageId) {
        regMessageId(CK_NES, messageId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageId The value of messageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageId_GreaterThan(Integer messageId) {
        regMessageId(CK_GT, messageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageId The value of messageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageId_LessThan(Integer messageId) {
        regMessageId(CK_LT, messageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageId The value of messageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageId_GreaterEqual(Integer messageId) {
        regMessageId(CK_GE, messageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageId The value of messageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageId_LessEqual(Integer messageId) {
        regMessageId(CK_LE, messageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMessageId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMessageId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMessageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMessageId(), "MESSAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageIdList The collection of messageId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageId_InScope(Collection<Integer> messageIdList) {
        doSetMessageId_InScope(messageIdList);
    }

    protected void doSetMessageId_InScope(Collection<Integer> messageIdList) {
        regINS(CK_INS, cTL(messageIdList), xgetCValueMessageId(), "MESSAGE_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param messageIdList The collection of messageId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageId_NotInScope(Collection<Integer> messageIdList) {
        doSetMessageId_NotInScope(messageIdList);
    }

    protected void doSetMessageId_NotInScope(Collection<Integer> messageIdList) {
        regINS(CK_NINS, cTL(messageIdList), xgetCValueMessageId(), "MESSAGE_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setMessageId_IsNull() { regMessageId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setMessageId_IsNotNull() { regMessageId(CK_ISNN, DOBJ); }

    protected void regMessageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageId(), "MESSAGE_ID"); }
    protected abstract ConditionValue xgetCValueMessageId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
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
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
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
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
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
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param villagePlayerId The value of villagePlayerId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_GreaterThan(Integer villagePlayerId) {
        regVillagePlayerId(CK_GT, villagePlayerId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param villagePlayerId The value of villagePlayerId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_LessThan(Integer villagePlayerId) {
        regVillagePlayerId(CK_LT, villagePlayerId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param villagePlayerId The value of villagePlayerId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_GreaterEqual(Integer villagePlayerId) {
        regVillagePlayerId(CK_GE, villagePlayerId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param villagePlayerId The value of villagePlayerId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillagePlayerId_LessEqual(Integer villagePlayerId) {
        regVillagePlayerId(CK_LE, villagePlayerId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
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
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param minNumber The min number of villagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillagePlayerId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillagePlayerId(), "VILLAGE_PLAYER_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
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
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param villagePlayerIdList The collection of villagePlayerId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillagePlayerId_NotInScope(Collection<Integer> villagePlayerIdList) {
        doSetVillagePlayerId_NotInScope(villagePlayerIdList);
    }

    protected void doSetVillagePlayerId_NotInScope(Collection<Integer> villagePlayerIdList) {
        regINS(CK_NINS, cTL(villagePlayerIdList), xgetCValueVillagePlayerId(), "VILLAGE_PLAYER_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     */
    public void setVillagePlayerId_IsNull() { regVillagePlayerId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     */
    public void setVillagePlayerId_IsNotNull() { regVillagePlayerId(CK_ISNN, DOBJ); }

    protected void regVillagePlayerId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillagePlayerId(), "VILLAGE_PLAYER_ID"); }
    protected abstract ConditionValue xgetCValueVillagePlayerId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerId The value of toVillagePlayerId as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_Equal(Integer toVillagePlayerId) {
        doSetToVillagePlayerId_Equal(toVillagePlayerId);
    }

    protected void doSetToVillagePlayerId_Equal(Integer toVillagePlayerId) {
        regToVillagePlayerId(CK_EQ, toVillagePlayerId);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerId The value of toVillagePlayerId as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_NotEqual(Integer toVillagePlayerId) {
        doSetToVillagePlayerId_NotEqual(toVillagePlayerId);
    }

    protected void doSetToVillagePlayerId_NotEqual(Integer toVillagePlayerId) {
        regToVillagePlayerId(CK_NES, toVillagePlayerId);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerId The value of toVillagePlayerId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_GreaterThan(Integer toVillagePlayerId) {
        regToVillagePlayerId(CK_GT, toVillagePlayerId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerId The value of toVillagePlayerId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_LessThan(Integer toVillagePlayerId) {
        regToVillagePlayerId(CK_LT, toVillagePlayerId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerId The value of toVillagePlayerId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_GreaterEqual(Integer toVillagePlayerId) {
        regToVillagePlayerId(CK_GE, toVillagePlayerId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerId The value of toVillagePlayerId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_LessEqual(Integer toVillagePlayerId) {
        regToVillagePlayerId(CK_LE, toVillagePlayerId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param minNumber The min number of toVillagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of toVillagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setToVillagePlayerId_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setToVillagePlayerId_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param minNumber The min number of toVillagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of toVillagePlayerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setToVillagePlayerId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueToVillagePlayerId(), "TO_VILLAGE_PLAYER_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerIdList The collection of toVillagePlayerId as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_InScope(Collection<Integer> toVillagePlayerIdList) {
        doSetToVillagePlayerId_InScope(toVillagePlayerIdList);
    }

    protected void doSetToVillagePlayerId_InScope(Collection<Integer> toVillagePlayerIdList) {
        regINS(CK_INS, cTL(toVillagePlayerIdList), xgetCValueToVillagePlayerId(), "TO_VILLAGE_PLAYER_ID");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @param toVillagePlayerIdList The collection of toVillagePlayerId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setToVillagePlayerId_NotInScope(Collection<Integer> toVillagePlayerIdList) {
        doSetToVillagePlayerId_NotInScope(toVillagePlayerIdList);
    }

    protected void doSetToVillagePlayerId_NotInScope(Collection<Integer> toVillagePlayerIdList) {
        regINS(CK_NINS, cTL(toVillagePlayerIdList), xgetCValueToVillagePlayerId(), "TO_VILLAGE_PLAYER_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     */
    public void setToVillagePlayerId_IsNull() { regToVillagePlayerId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     */
    public void setToVillagePlayerId_IsNotNull() { regToVillagePlayerId(CK_ISNN, DOBJ); }

    protected void regToVillagePlayerId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueToVillagePlayerId(), "TO_VILLAGE_PLAYER_ID"); }
    protected abstract ConditionValue xgetCValueToVillagePlayerId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_GreaterThan(Integer playerId) {
        regPlayerId(CK_GT, playerId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_LessThan(Integer playerId) {
        regPlayerId(CK_LT, playerId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_GreaterEqual(Integer playerId) {
        regPlayerId(CK_GE, playerId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     * @param playerId The value of playerId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setPlayerId_LessEqual(Integer playerId) {
        regPlayerId(CK_LE, playerId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     * @param minNumber The min number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of playerId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setPlayerId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValuePlayerId(), "PLAYER_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
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
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     * @param playerIdList The collection of playerId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setPlayerId_NotInScope(Collection<Integer> playerIdList) {
        doSetPlayerId_NotInScope(playerIdList);
    }

    protected void doSetPlayerId_NotInScope(Collection<Integer> playerIdList) {
        regINS(CK_NINS, cTL(playerIdList), xgetCValuePlayerId(), "PLAYER_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     */
    public void setPlayerId_IsNull() { regPlayerId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to PLAYER}
     */
    public void setPlayerId_IsNotNull() { regPlayerId(CK_ISNN, DOBJ); }

    protected void regPlayerId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValuePlayerId(), "PLAYER_ID"); }
    protected abstract ConditionValue xgetCValuePlayerId();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_Equal(Integer day) {
        doSetDay_Equal(day);
    }

    protected void doSetDay_Equal(Integer day) {
        regDay(CK_EQ, day);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_NotEqual(Integer day) {
        doSetDay_NotEqual(day);
    }

    protected void doSetDay_NotEqual(Integer day) {
        regDay(CK_NES, day);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_GreaterThan(Integer day) {
        regDay(CK_GT, day);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_LessThan(Integer day) {
        regDay(CK_LT, day);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_GreaterEqual(Integer day) {
        regDay(CK_GE, day);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param day The value of day as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setDay_LessEqual(Integer day) {
        regDay(CK_LE, day);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param minNumber The min number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setDay_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setDay_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param minNumber The min number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of day. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setDay_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueDay(), "DAY", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param dayList The collection of day as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDay_InScope(Collection<Integer> dayList) {
        doSetDay_InScope(dayList);
    }

    protected void doSetDay_InScope(Collection<Integer> dayList) {
        regINS(CK_INS, cTL(dayList), xgetCValueDay(), "DAY");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * DAY: {NotNull, INT UNSIGNED(10), FK to VILLAGE_DAY}
     * @param dayList The collection of day as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setDay_NotInScope(Collection<Integer> dayList) {
        doSetDay_NotInScope(dayList);
    }

    protected void doSetDay_NotInScope(Collection<Integer> dayList) {
        regINS(CK_NINS, cTL(dayList), xgetCValueDay(), "DAY");
    }

    protected void regDay(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueDay(), "DAY"); }
    protected abstract ConditionValue xgetCValueDay();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_Equal(String messageTypeCode) {
        doSetMessageTypeCode_Equal(fRES(messageTypeCode));
    }

    /**
     * Equal(=). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageTypeCode_Equal_AsMessageType(CDef.MessageType cdef) {
        doSetMessageTypeCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 村建て発言 (CREATOR_SAY). And OnlyOnceRegistered. <br>
     * 村建て発言
     */
    public void setMessageTypeCode_Equal_村建て発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.村建て発言);
    }

    /**
     * Equal(=). As 死者の呻き (GRAVE_SAY). And OnlyOnceRegistered. <br>
     * 死者の呻き
     */
    public void setMessageTypeCode_Equal_死者の呻き() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.死者の呻き);
    }

    /**
     * Equal(=). As 共鳴発言 (MASON_SAY). And OnlyOnceRegistered. <br>
     * 共鳴発言
     */
    public void setMessageTypeCode_Equal_共鳴発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.共鳴発言);
    }

    /**
     * Equal(=). As 独り言 (MONOLOGUE_SAY). And OnlyOnceRegistered. <br>
     * 独り言
     */
    public void setMessageTypeCode_Equal_独り言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.独り言);
    }

    /**
     * Equal(=). As 通常発言 (NORMAL_SAY). And OnlyOnceRegistered. <br>
     * 通常発言
     */
    public void setMessageTypeCode_Equal_通常発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.通常発言);
    }

    /**
     * Equal(=). As 役職霊視結果 (PRIVATE_GURU). And OnlyOnceRegistered. <br>
     * 役職霊視結果
     */
    public void setMessageTypeCode_Equal_役職霊視結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.役職霊視結果);
    }

    /**
     * Equal(=). As 白黒霊視結果 (PRIVATE_PSYCHIC). And OnlyOnceRegistered. <br>
     * 白黒霊視結果
     */
    public void setMessageTypeCode_Equal_白黒霊視結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.白黒霊視結果);
    }

    /**
     * Equal(=). As 白黒占い結果 (PRIVATE_SEER). And OnlyOnceRegistered. <br>
     * 白黒占い結果
     */
    public void setMessageTypeCode_Equal_白黒占い結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.白黒占い結果);
    }

    /**
     * Equal(=). As 非公開システムメッセージ (PRIVATE_SYSTEM). And OnlyOnceRegistered. <br>
     * 非公開システムメッセージ
     */
    public void setMessageTypeCode_Equal_非公開システムメッセージ() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.非公開システムメッセージ);
    }

    /**
     * Equal(=). As 役職占い結果 (PRIVATE_WISE). And OnlyOnceRegistered. <br>
     * 役職占い結果
     */
    public void setMessageTypeCode_Equal_役職占い結果() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.役職占い結果);
    }

    /**
     * Equal(=). As 公開システムメッセージ (PUBLIC_SYSTEM). And OnlyOnceRegistered. <br>
     * 公開システムメッセージ
     */
    public void setMessageTypeCode_Equal_公開システムメッセージ() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.公開システムメッセージ);
    }

    /**
     * Equal(=). As 秘話 (SECRET_SAY). And OnlyOnceRegistered. <br>
     * 秘話
     */
    public void setMessageTypeCode_Equal_秘話() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.秘話);
    }

    /**
     * Equal(=). As 見学発言 (SPECTATE_SAY). And OnlyOnceRegistered. <br>
     * 見学発言
     */
    public void setMessageTypeCode_Equal_見学発言() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.見学発言);
    }

    /**
     * Equal(=). As 人狼の囁き (WEREWOLF_SAY). And OnlyOnceRegistered. <br>
     * 人狼の囁き
     */
    public void setMessageTypeCode_Equal_人狼の囁き() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.人狼の囁き);
    }

    protected void doSetMessageTypeCode_Equal(String messageTypeCode) {
        regMessageTypeCode(CK_EQ, messageTypeCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotEqual(String messageTypeCode) {
        doSetMessageTypeCode_NotEqual(fRES(messageTypeCode));
    }

    /**
     * NotEqual(&lt;&gt;). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType cdef) {
        doSetMessageTypeCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 村建て発言 (CREATOR_SAY). And OnlyOnceRegistered. <br>
     * 村建て発言
     */
    public void setMessageTypeCode_NotEqual_村建て発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.村建て発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 死者の呻き (GRAVE_SAY). And OnlyOnceRegistered. <br>
     * 死者の呻き
     */
    public void setMessageTypeCode_NotEqual_死者の呻き() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.死者の呻き);
    }

    /**
     * NotEqual(&lt;&gt;). As 共鳴発言 (MASON_SAY). And OnlyOnceRegistered. <br>
     * 共鳴発言
     */
    public void setMessageTypeCode_NotEqual_共鳴発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.共鳴発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 独り言 (MONOLOGUE_SAY). And OnlyOnceRegistered. <br>
     * 独り言
     */
    public void setMessageTypeCode_NotEqual_独り言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.独り言);
    }

    /**
     * NotEqual(&lt;&gt;). As 通常発言 (NORMAL_SAY). And OnlyOnceRegistered. <br>
     * 通常発言
     */
    public void setMessageTypeCode_NotEqual_通常発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.通常発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 役職霊視結果 (PRIVATE_GURU). And OnlyOnceRegistered. <br>
     * 役職霊視結果
     */
    public void setMessageTypeCode_NotEqual_役職霊視結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.役職霊視結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 白黒霊視結果 (PRIVATE_PSYCHIC). And OnlyOnceRegistered. <br>
     * 白黒霊視結果
     */
    public void setMessageTypeCode_NotEqual_白黒霊視結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.白黒霊視結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 白黒占い結果 (PRIVATE_SEER). And OnlyOnceRegistered. <br>
     * 白黒占い結果
     */
    public void setMessageTypeCode_NotEqual_白黒占い結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.白黒占い結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 非公開システムメッセージ (PRIVATE_SYSTEM). And OnlyOnceRegistered. <br>
     * 非公開システムメッセージ
     */
    public void setMessageTypeCode_NotEqual_非公開システムメッセージ() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.非公開システムメッセージ);
    }

    /**
     * NotEqual(&lt;&gt;). As 役職占い結果 (PRIVATE_WISE). And OnlyOnceRegistered. <br>
     * 役職占い結果
     */
    public void setMessageTypeCode_NotEqual_役職占い結果() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.役職占い結果);
    }

    /**
     * NotEqual(&lt;&gt;). As 公開システムメッセージ (PUBLIC_SYSTEM). And OnlyOnceRegistered. <br>
     * 公開システムメッセージ
     */
    public void setMessageTypeCode_NotEqual_公開システムメッセージ() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.公開システムメッセージ);
    }

    /**
     * NotEqual(&lt;&gt;). As 秘話 (SECRET_SAY). And OnlyOnceRegistered. <br>
     * 秘話
     */
    public void setMessageTypeCode_NotEqual_秘話() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.秘話);
    }

    /**
     * NotEqual(&lt;&gt;). As 見学発言 (SPECTATE_SAY). And OnlyOnceRegistered. <br>
     * 見学発言
     */
    public void setMessageTypeCode_NotEqual_見学発言() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.見学発言);
    }

    /**
     * NotEqual(&lt;&gt;). As 人狼の囁き (WEREWOLF_SAY). And OnlyOnceRegistered. <br>
     * 人狼の囁き
     */
    public void setMessageTypeCode_NotEqual_人狼の囁き() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.人狼の囁き);
    }

    protected void doSetMessageTypeCode_NotEqual(String messageTypeCode) {
        regMessageTypeCode(CK_NES, messageTypeCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_InScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_InScope(messageTypeCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeCode_InScope_AsMessageType(Collection<CDef.MessageType> cdefList) {
        doSetMessageTypeCode_InScope(cTStrL(cdefList));
    }

    protected void doSetMessageTypeCode_InScope(Collection<String> messageTypeCodeList) {
        regINS(CK_INS, cTL(messageTypeCodeList), xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotInScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_NotInScope(messageTypeCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * メッセージ種別
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeCode_NotInScope_AsMessageType(Collection<CDef.MessageType> cdefList) {
        doSetMessageTypeCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetMessageTypeCode_NotInScope(Collection<String> messageTypeCodeList) {
        regINS(CK_NINS, cTL(messageTypeCodeList), xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE");
    }

    protected void regMessageTypeCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE"); }
    protected abstract ConditionValue xgetCValueMessageTypeCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumber The value of messageNumber as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageNumber_Equal(Integer messageNumber) {
        doSetMessageNumber_Equal(messageNumber);
    }

    protected void doSetMessageNumber_Equal(Integer messageNumber) {
        regMessageNumber(CK_EQ, messageNumber);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumber The value of messageNumber as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageNumber_NotEqual(Integer messageNumber) {
        doSetMessageNumber_NotEqual(messageNumber);
    }

    protected void doSetMessageNumber_NotEqual(Integer messageNumber) {
        regMessageNumber(CK_NES, messageNumber);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumber The value of messageNumber as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageNumber_GreaterThan(Integer messageNumber) {
        regMessageNumber(CK_GT, messageNumber);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumber The value of messageNumber as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageNumber_LessThan(Integer messageNumber) {
        regMessageNumber(CK_LT, messageNumber);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumber The value of messageNumber as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageNumber_GreaterEqual(Integer messageNumber) {
        regMessageNumber(CK_GE, messageNumber);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumber The value of messageNumber as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageNumber_LessEqual(Integer messageNumber) {
        regMessageNumber(CK_LE, messageNumber);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param minNumber The min number of messageNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMessageNumber_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMessageNumber_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param minNumber The min number of messageNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageNumber. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMessageNumber_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMessageNumber(), "MESSAGE_NUMBER", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumberList The collection of messageNumber as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageNumber_InScope(Collection<Integer> messageNumberList) {
        doSetMessageNumber_InScope(messageNumberList);
    }

    protected void doSetMessageNumber_InScope(Collection<Integer> messageNumberList) {
        regINS(CK_INS, cTL(messageNumberList), xgetCValueMessageNumber(), "MESSAGE_NUMBER");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @param messageNumberList The collection of messageNumber as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageNumber_NotInScope(Collection<Integer> messageNumberList) {
        doSetMessageNumber_NotInScope(messageNumberList);
    }

    protected void doSetMessageNumber_NotInScope(Collection<Integer> messageNumberList) {
        regINS(CK_NINS, cTL(messageNumberList), xgetCValueMessageNumber(), "MESSAGE_NUMBER");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     */
    public void setMessageNumber_IsNull() { regMessageNumber(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     */
    public void setMessageNumber_IsNotNull() { regMessageNumber(CK_ISNN, DOBJ); }

    protected void regMessageNumber(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageNumber(), "MESSAGE_NUMBER"); }
    protected abstract ConditionValue xgetCValueMessageNumber();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_Equal(String messageContent) {
        doSetMessageContent_Equal(fRES(messageContent));
    }

    protected void doSetMessageContent_Equal(String messageContent) {
        regMessageContent(CK_EQ, messageContent);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_NotEqual(String messageContent) {
        doSetMessageContent_NotEqual(fRES(messageContent));
    }

    protected void doSetMessageContent_NotEqual(String messageContent) {
        regMessageContent(CK_NES, messageContent);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_GreaterThan(String messageContent) {
        regMessageContent(CK_GT, fRES(messageContent));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_LessThan(String messageContent) {
        regMessageContent(CK_LT, fRES(messageContent));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_GreaterEqual(String messageContent) {
        regMessageContent(CK_GE, fRES(messageContent));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_LessEqual(String messageContent) {
        regMessageContent(CK_LE, fRES(messageContent));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContentList The collection of messageContent as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_InScope(Collection<String> messageContentList) {
        doSetMessageContent_InScope(messageContentList);
    }

    protected void doSetMessageContent_InScope(Collection<String> messageContentList) {
        regINS(CK_INS, cTL(messageContentList), xgetCValueMessageContent(), "MESSAGE_CONTENT");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContentList The collection of messageContent as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageContent_NotInScope(Collection<String> messageContentList) {
        doSetMessageContent_NotInScope(messageContentList);
    }

    protected void doSetMessageContent_NotInScope(Collection<String> messageContentList) {
        regINS(CK_NINS, cTL(messageContentList), xgetCValueMessageContent(), "MESSAGE_CONTENT");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)} <br>
     * <pre>e.g. setMessageContent_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param messageContent The value of messageContent as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setMessageContent_LikeSearch(String messageContent, ConditionOptionCall<LikeSearchOption> opLambda) {
        setMessageContent_LikeSearch(messageContent, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)} <br>
     * <pre>e.g. setMessageContent_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param messageContent The value of messageContent as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setMessageContent_LikeSearch(String messageContent, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(messageContent), xgetCValueMessageContent(), "MESSAGE_CONTENT", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setMessageContent_NotLikeSearch(String messageContent, ConditionOptionCall<LikeSearchOption> opLambda) {
        setMessageContent_NotLikeSearch(messageContent, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @param messageContent The value of messageContent as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setMessageContent_NotLikeSearch(String messageContent, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(messageContent), xgetCValueMessageContent(), "MESSAGE_CONTENT", likeSearchOption);
    }

    protected void regMessageContent(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageContent(), "MESSAGE_CONTENT"); }
    protected abstract ConditionValue xgetCValueMessageContent();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @param messageDatetime The value of messageDatetime as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageDatetime_Equal(java.time.LocalDateTime messageDatetime) {
        regMessageDatetime(CK_EQ,  messageDatetime);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @param messageDatetime The value of messageDatetime as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageDatetime_GreaterThan(java.time.LocalDateTime messageDatetime) {
        regMessageDatetime(CK_GT,  messageDatetime);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @param messageDatetime The value of messageDatetime as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageDatetime_LessThan(java.time.LocalDateTime messageDatetime) {
        regMessageDatetime(CK_LT,  messageDatetime);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @param messageDatetime The value of messageDatetime as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageDatetime_GreaterEqual(java.time.LocalDateTime messageDatetime) {
        regMessageDatetime(CK_GE,  messageDatetime);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @param messageDatetime The value of messageDatetime as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageDatetime_LessEqual(java.time.LocalDateTime messageDatetime) {
        regMessageDatetime(CK_LE, messageDatetime);
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setMessageDatetime_FromTo(fromDate, toDate, op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">compareAsDate()</span>);</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of messageDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of messageDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of from-to. (NotNull)
     */
    public void setMessageDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, ConditionOptionCall<FromToOption> opLambda) {
        setMessageDatetime_FromTo(fromDatetime, toDatetime, xcFTOP(opLambda));
    }

    /**
     * FromTo with various options. (versatile) {(default) fromDatetime &lt;= column &lt;= toDatetime} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * <pre>e.g. setMessageDatetime_FromTo(fromDate, toDate, new <span style="color: #CC4747">FromToOption</span>().compareAsDate());</pre>
     * @param fromDatetime The from-datetime(yyyy/MM/dd HH:mm:ss.SSS) of messageDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param toDatetime The to-datetime(yyyy/MM/dd HH:mm:ss.SSS) of messageDatetime. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param fromToOption The option of from-to. (NotNull)
     */
    protected void setMessageDatetime_FromTo(java.time.LocalDateTime fromDatetime, java.time.LocalDateTime toDatetime, FromToOption fromToOption) {
        String nm = "MESSAGE_DATETIME"; FromToOption op = fromToOption;
        regFTQ(xfFTHD(fromDatetime, nm, op), xfFTHD(toDatetime, nm, op), xgetCValueMessageDatetime(), nm, op);
    }

    protected void regMessageDatetime(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageDatetime(), "MESSAGE_DATETIME"); }
    protected abstract ConditionValue xgetCValueMessageDatetime();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg}
     * @param isConvertDisable The value of isConvertDisable as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setIsConvertDisable_Equal(Boolean isConvertDisable) {
        regIsConvertDisable(CK_EQ, isConvertDisable);
    }

    /**
     * Equal(=). As Flg. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setIsConvertDisable_Equal_AsFlg(CDef.Flg cdef) {
        doSetIsConvertDisable_Equal(cdef != null ? Boolean.valueOf(cdef.code()) : null);
    }

    /**
     * Equal(=). As True. And OnlyOnceRegistered. <br>
     * はい: 有効を示す
     */
    public void setIsConvertDisable_Equal_True() {
        doSetIsConvertDisable_Equal(Boolean.valueOf(CDef.Flg.True.code()));
    }

    /**
     * Equal(=). As False. And OnlyOnceRegistered. <br>
     * いいえ: 無効を示す
     */
    public void setIsConvertDisable_Equal_False() {
        doSetIsConvertDisable_Equal(Boolean.valueOf(CDef.Flg.False.code()));
    }

    protected void doSetIsConvertDisable_Equal(Boolean isConvertDisable) {
        regIsConvertDisable(CK_EQ, isConvertDisable);
    }

    protected void regIsConvertDisable(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueIsConvertDisable(), "IS_CONVERT_DISABLE"); }
    protected abstract ConditionValue xgetCValueIsConvertDisable();

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
    public HpSLCFunction<MessageCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, MessageCB.class);
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
    public HpSLCFunction<MessageCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, MessageCB.class);
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
    public HpSLCFunction<MessageCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, MessageCB.class);
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
    public HpSLCFunction<MessageCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, MessageCB.class);
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
    public HpSLCFunction<MessageCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, MessageCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;MessageCB&gt;() {
     *     public void query(MessageCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<MessageCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, MessageCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(MessageCQ sq);

    protected MessageCB xcreateScalarConditionCB() {
        MessageCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected MessageCB xcreateScalarConditionPartitionByCB() {
        MessageCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<MessageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "MESSAGE_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(MessageCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<MessageCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(MessageCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "MESSAGE_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(MessageCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(MessageCQ sq);

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
     *     <span style="color: #553000">op</span>.<span style="color: #CC4747">when_GreaterEqual</span>(priorityDate); <span style="color: #3F7E5E">// e.g. 2000/01/01</span>
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
    protected MessageCB newMyCB() {
        return new MessageCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return MessageCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
