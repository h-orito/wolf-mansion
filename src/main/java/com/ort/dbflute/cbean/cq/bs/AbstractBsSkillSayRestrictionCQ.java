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
 * The abstract condition-query of SKILL_SAY_RESTRICTION.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsSkillSayRestrictionCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsSkillSayRestrictionCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "SKILL_SAY_RESTRICTION";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
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
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @param villageIdList The collection of villageId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageId_NotInScope(Collection<Integer> villageIdList) {
        doSetVillageId_NotInScope(villageIdList);
    }

    protected void doSetVillageId_NotInScope(Collection<Integer> villageIdList) {
        regINS(CK_NINS, cTL(villageIdList), xgetCValueVillageId(), "VILLAGE_ID");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     */
    public void setVillageId_IsNull() { regVillageId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     */
    public void setVillageId_IsNotNull() { regVillageId(CK_ISNN, DOBJ); }

    protected void regVillageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageId(), "VILLAGE_ID"); }
    protected abstract ConditionValue xgetCValueVillageId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_Equal(String messageTypeCode) {
        doSetMessageTypeCode_Equal(fRES(messageTypeCode));
    }

    /**
     * Equal(=). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * ?????????????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageTypeCode_Equal_AsMessageType(CDef.MessageType cdef) {
        doSetMessageTypeCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As ??????????????? (ACTION). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_Equal_???????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.???????????????);
    }

    /**
     * Equal(=). As ??????????????? (CREATOR_SAY). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_Equal_???????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.???????????????);
    }

    /**
     * Equal(=). As ??????????????? (GRAVE_SAY). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_Equal_???????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.???????????????);
    }

    /**
     * Equal(=). As ???????????? (LOVERS_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_Equal_????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * Equal(=). As ???????????? (MASON_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_Equal_????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * Equal(=). As ????????? (MONOLOGUE_SAY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setMessageTypeCode_Equal_?????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.?????????);
    }

    /**
     * Equal(=). As ???????????? (NORMAL_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_Equal_????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * Equal(=). As ???????????? (PRIVATE_CORONER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_Equal_????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * Equal(=). As ?????????????????? (PRIVATE_GURU). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_Equal_??????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * Equal(=). As ?????????????????? (PRIVATE_INVESTIGATE). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_Equal_??????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * Equal(=). As ????????????????????? (PRIVATE_LOVER). And OnlyOnceRegistered. <br>
     * ?????????????????????
     */
    public void setMessageTypeCode_Equal_?????????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.?????????????????????);
    }

    /**
     * Equal(=). As ?????????????????? (PRIVATE_PSYCHIC). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_Equal_??????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * Equal(=). As ?????????????????? (PRIVATE_SEER). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_Equal_??????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * Equal(=). As ???????????????????????????????????? (PRIVATE_SYSTEM). And OnlyOnceRegistered. <br>
     * ????????????????????????????????????
     */
    public void setMessageTypeCode_Equal_????????????????????????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.????????????????????????????????????);
    }

    /**
     * Equal(=). As ???????????? (PRIVATE_WEREWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_Equal_????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * Equal(=). As ?????????????????? (PRIVATE_WISE). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_Equal_??????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * Equal(=). As ????????????????????????????????? (PUBLIC_SYSTEM). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setMessageTypeCode_Equal_?????????????????????????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.?????????????????????????????????);
    }

    /**
     * Equal(=). As ?????? (SECRET_SAY). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setMessageTypeCode_Equal_??????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.??????);
    }

    /**
     * Equal(=). As ???????????? (SPECTATE_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_Equal_????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * Equal(=). As ??????????????? (WEREWOLF_SAY). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_Equal_???????????????() {
        setMessageTypeCode_Equal_AsMessageType(CDef.MessageType.???????????????);
    }

    protected void doSetMessageTypeCode_Equal(String messageTypeCode) {
        regMessageTypeCode(CK_EQ, messageTypeCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotEqual(String messageTypeCode) {
        doSetMessageTypeCode_NotEqual(fRES(messageTypeCode));
    }

    /**
     * NotEqual(&lt;&gt;). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * ?????????????????????
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType cdef) {
        doSetMessageTypeCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????? (ACTION). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_NotEqual_???????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.???????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????? (CREATOR_SAY). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_NotEqual_???????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.???????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????? (GRAVE_SAY). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_NotEqual_???????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.???????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (LOVERS_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_NotEqual_????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (MASON_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_NotEqual_????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????? (MONOLOGUE_SAY). And OnlyOnceRegistered. <br>
     * ?????????
     */
    public void setMessageTypeCode_NotEqual_?????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.?????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (NORMAL_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_NotEqual_????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (PRIVATE_CORONER). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_NotEqual_????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (PRIVATE_GURU). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_NotEqual_??????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (PRIVATE_INVESTIGATE). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_NotEqual_??????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????????????????? (PRIVATE_LOVER). And OnlyOnceRegistered. <br>
     * ?????????????????????
     */
    public void setMessageTypeCode_NotEqual_?????????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.?????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (PRIVATE_PSYCHIC). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_NotEqual_??????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (PRIVATE_SEER). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_NotEqual_??????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????????????????????????????? (PRIVATE_SYSTEM). And OnlyOnceRegistered. <br>
     * ????????????????????????????????????
     */
    public void setMessageTypeCode_NotEqual_????????????????????????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.????????????????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (PRIVATE_WEREWOLF). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_NotEqual_????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????????????????? (PRIVATE_WISE). And OnlyOnceRegistered. <br>
     * ??????????????????
     */
    public void setMessageTypeCode_NotEqual_??????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.??????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ????????????????????????????????? (PUBLIC_SYSTEM). And OnlyOnceRegistered. <br>
     * ?????????????????????????????????
     */
    public void setMessageTypeCode_NotEqual_?????????????????????????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.?????????????????????????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ?????? (SECRET_SAY). And OnlyOnceRegistered. <br>
     * ??????
     */
    public void setMessageTypeCode_NotEqual_??????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.??????);
    }

    /**
     * NotEqual(&lt;&gt;). As ???????????? (SPECTATE_SAY). And OnlyOnceRegistered. <br>
     * ????????????
     */
    public void setMessageTypeCode_NotEqual_????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.????????????);
    }

    /**
     * NotEqual(&lt;&gt;). As ??????????????? (WEREWOLF_SAY). And OnlyOnceRegistered. <br>
     * ???????????????
     */
    public void setMessageTypeCode_NotEqual_???????????????() {
        setMessageTypeCode_NotEqual_AsMessageType(CDef.MessageType.???????????????);
    }

    protected void doSetMessageTypeCode_NotEqual(String messageTypeCode) {
        regMessageTypeCode(CK_NES, messageTypeCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_InScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_InScope(messageTypeCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * ?????????????????????
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
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotInScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_NotInScope(messageTypeCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType} <br>
     * ?????????????????????
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeCode_NotInScope_AsMessageType(Collection<CDef.MessageType> cdefList) {
        doSetMessageTypeCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetMessageTypeCode_NotInScope(Collection<String> messageTypeCodeList) {
        regINS(CK_NINS, cTL(messageTypeCodeList), xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     */
    public void setMessageTypeCode_IsNull() { regMessageTypeCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to MESSAGE_TYPE, classification=MessageType}
     */
    public void setMessageTypeCode_IsNotNull() { regMessageTypeCode(CK_ISNN, DOBJ); }

    protected void regMessageTypeCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE"); }
    protected abstract ConditionValue xgetCValueMessageTypeCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_Equal(Integer messageMaxNum) {
        doSetMessageMaxNum_Equal(messageMaxNum);
    }

    protected void doSetMessageMaxNum_Equal(Integer messageMaxNum) {
        regMessageMaxNum(CK_EQ, messageMaxNum);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_NotEqual(Integer messageMaxNum) {
        doSetMessageMaxNum_NotEqual(messageMaxNum);
    }

    protected void doSetMessageMaxNum_NotEqual(Integer messageMaxNum) {
        regMessageMaxNum(CK_NES, messageMaxNum);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_GreaterThan(Integer messageMaxNum) {
        regMessageMaxNum(CK_GT, messageMaxNum);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_LessThan(Integer messageMaxNum) {
        regMessageMaxNum(CK_LT, messageMaxNum);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_GreaterEqual(Integer messageMaxNum) {
        regMessageMaxNum(CK_GE, messageMaxNum);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNum The value of messageMaxNum as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxNum_LessEqual(Integer messageMaxNum) {
        regMessageMaxNum(CK_LE, messageMaxNum);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMessageMaxNum_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMessageMaxNum_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxNum. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMessageMaxNum_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNumList The collection of messageMaxNum as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxNum_InScope(Collection<Integer> messageMaxNumList) {
        doSetMessageMaxNum_InScope(messageMaxNumList);
    }

    protected void doSetMessageMaxNum_InScope(Collection<Integer> messageMaxNumList) {
        regINS(CK_INS, cTL(messageMaxNumList), xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_NUM: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxNumList The collection of messageMaxNum as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxNum_NotInScope(Collection<Integer> messageMaxNumList) {
        doSetMessageMaxNum_NotInScope(messageMaxNumList);
    }

    protected void doSetMessageMaxNum_NotInScope(Collection<Integer> messageMaxNumList) {
        regINS(CK_NINS, cTL(messageMaxNumList), xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM");
    }

    protected void regMessageMaxNum(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageMaxNum(), "MESSAGE_MAX_NUM"); }
    protected abstract ConditionValue xgetCValueMessageMaxNum();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_Equal(Integer messageMaxLength) {
        doSetMessageMaxLength_Equal(messageMaxLength);
    }

    protected void doSetMessageMaxLength_Equal(Integer messageMaxLength) {
        regMessageMaxLength(CK_EQ, messageMaxLength);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_NotEqual(Integer messageMaxLength) {
        doSetMessageMaxLength_NotEqual(messageMaxLength);
    }

    protected void doSetMessageMaxLength_NotEqual(Integer messageMaxLength) {
        regMessageMaxLength(CK_NES, messageMaxLength);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_GreaterThan(Integer messageMaxLength) {
        regMessageMaxLength(CK_GT, messageMaxLength);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_LessThan(Integer messageMaxLength) {
        regMessageMaxLength(CK_LT, messageMaxLength);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_GreaterEqual(Integer messageMaxLength) {
        regMessageMaxLength(CK_GE, messageMaxLength);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLength The value of messageMaxLength as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setMessageMaxLength_LessEqual(Integer messageMaxLength) {
        regMessageMaxLength(CK_LE, messageMaxLength);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setMessageMaxLength_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setMessageMaxLength_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of messageMaxLength. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setMessageMaxLength_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLengthList The collection of messageMaxLength as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxLength_InScope(Collection<Integer> messageMaxLengthList) {
        doSetMessageMaxLength_InScope(messageMaxLengthList);
    }

    protected void doSetMessageMaxLength_InScope(Collection<Integer> messageMaxLengthList) {
        regINS(CK_INS, cTL(messageMaxLengthList), xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * MESSAGE_MAX_LENGTH: {NotNull, INT UNSIGNED(10)}
     * @param messageMaxLengthList The collection of messageMaxLength as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageMaxLength_NotInScope(Collection<Integer> messageMaxLengthList) {
        doSetMessageMaxLength_NotInScope(messageMaxLengthList);
    }

    protected void doSetMessageMaxLength_NotInScope(Collection<Integer> messageMaxLengthList) {
        regINS(CK_NINS, cTL(messageMaxLengthList), xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH");
    }

    protected void regMessageMaxLength(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageMaxLength(), "MESSAGE_MAX_LENGTH"); }
    protected abstract ConditionValue xgetCValueMessageMaxLength();

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
    public HpSLCFunction<SkillSayRestrictionCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, SkillSayRestrictionCB.class);
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
    public HpSLCFunction<SkillSayRestrictionCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, SkillSayRestrictionCB.class);
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
    public HpSLCFunction<SkillSayRestrictionCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, SkillSayRestrictionCB.class);
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
    public HpSLCFunction<SkillSayRestrictionCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, SkillSayRestrictionCB.class);
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
    public HpSLCFunction<SkillSayRestrictionCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, SkillSayRestrictionCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;SkillSayRestrictionCB&gt;() {
     *     public void query(SkillSayRestrictionCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<SkillSayRestrictionCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, SkillSayRestrictionCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillSayRestrictionCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(SkillSayRestrictionCQ sq);

    protected SkillSayRestrictionCB xcreateScalarConditionCB() {
        SkillSayRestrictionCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected SkillSayRestrictionCB xcreateScalarConditionPartitionByCB() {
        SkillSayRestrictionCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

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
    protected SkillSayRestrictionCB newMyCB() {
        return new SkillSayRestrictionCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return SkillSayRestrictionCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
