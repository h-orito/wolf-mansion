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
 * The abstract condition-query of MESSAGE_TYPE.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsMessageTypeCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsMessageTypeCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "MESSAGE_TYPE";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_Equal(String messageTypeCode) {
        doSetMessageTypeCode_Equal(fRES(messageTypeCode));
    }

    /**
     * Equal(=). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType} <br>
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
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     * @param messageTypeCode The value of messageTypeCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotEqual(String messageTypeCode) {
        doSetMessageTypeCode_NotEqual(fRES(messageTypeCode));
    }

    /**
     * NotEqual(&lt;&gt;). As MessageType. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType} <br>
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
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_InScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_InScope(messageTypeCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType} <br>
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
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     * @param messageTypeCodeList The collection of messageTypeCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setMessageTypeCode_NotInScope(Collection<String> messageTypeCodeList) {
        doSetMessageTypeCode_NotInScope(messageTypeCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As MessageType. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType} <br>
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
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select MESSAGE_TYPE_CODE from MESSAGE where ...)} <br>
     * MESSAGE by MESSAGE_TYPE_CODE, named 'messageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsMessage</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageList for 'exists'. (NotNull)
     */
    public void existsMessage(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMessageTypeCode_ExistsReferrer_MessageList(cb.query());
        registerExistsReferrer(cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "messageList");
    }
    public abstract String keepMessageTypeCode_ExistsReferrer_MessageList(MessageCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select MESSAGE_TYPE_CODE from NORMAL_SAY_RESTRICTION where ...)} <br>
     * NORMAL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'normalSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsNormalSayRestriction</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of NormalSayRestrictionList for 'exists'. (NotNull)
     */
    public void existsNormalSayRestriction(SubQuery<NormalSayRestrictionCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        NormalSayRestrictionCB cb = new NormalSayRestrictionCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMessageTypeCode_ExistsReferrer_NormalSayRestrictionList(cb.query());
        registerExistsReferrer(cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "normalSayRestrictionList");
    }
    public abstract String keepMessageTypeCode_ExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select MESSAGE_TYPE_CODE from SKILL_SAY_RESTRICTION where ...)} <br>
     * SKILL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'skillSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsSkillSayRestriction</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of SkillSayRestrictionList for 'exists'. (NotNull)
     */
    public void existsSkillSayRestriction(SubQuery<SkillSayRestrictionCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        SkillSayRestrictionCB cb = new SkillSayRestrictionCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMessageTypeCode_ExistsReferrer_SkillSayRestrictionList(cb.query());
        registerExistsReferrer(cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "skillSayRestrictionList");
    }
    public abstract String keepMessageTypeCode_ExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select MESSAGE_TYPE_CODE from MESSAGE where ...)} <br>
     * MESSAGE by MESSAGE_TYPE_CODE, named 'messageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsMessage</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageTypeCode_NotExistsReferrer_MessageList for 'not exists'. (NotNull)
     */
    public void notExistsMessage(SubQuery<MessageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageCB cb = new MessageCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMessageTypeCode_NotExistsReferrer_MessageList(cb.query());
        registerNotExistsReferrer(cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "messageList");
    }
    public abstract String keepMessageTypeCode_NotExistsReferrer_MessageList(MessageCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select MESSAGE_TYPE_CODE from NORMAL_SAY_RESTRICTION where ...)} <br>
     * NORMAL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'normalSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsNormalSayRestriction</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageTypeCode_NotExistsReferrer_NormalSayRestrictionList for 'not exists'. (NotNull)
     */
    public void notExistsNormalSayRestriction(SubQuery<NormalSayRestrictionCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        NormalSayRestrictionCB cb = new NormalSayRestrictionCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMessageTypeCode_NotExistsReferrer_NormalSayRestrictionList(cb.query());
        registerNotExistsReferrer(cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "normalSayRestrictionList");
    }
    public abstract String keepMessageTypeCode_NotExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select MESSAGE_TYPE_CODE from SKILL_SAY_RESTRICTION where ...)} <br>
     * SKILL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'skillSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsSkillSayRestriction</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of MessageTypeCode_NotExistsReferrer_SkillSayRestrictionList for 'not exists'. (NotNull)
     */
    public void notExistsSkillSayRestriction(SubQuery<SkillSayRestrictionCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        SkillSayRestrictionCB cb = new SkillSayRestrictionCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMessageTypeCode_NotExistsReferrer_SkillSayRestrictionList(cb.query());
        registerNotExistsReferrer(cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "skillSayRestrictionList");
    }
    public abstract String keepMessageTypeCode_NotExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);

    public void xsderiveMessageList(String fn, SubQuery<MessageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepMessageTypeCode_SpecifyDerivedReferrer_MessageList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "messageList", al, op);
    }
    public abstract String keepMessageTypeCode_SpecifyDerivedReferrer_MessageList(MessageCQ sq);

    public void xsderiveNormalSayRestrictionList(String fn, SubQuery<NormalSayRestrictionCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        NormalSayRestrictionCB cb = new NormalSayRestrictionCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepMessageTypeCode_SpecifyDerivedReferrer_NormalSayRestrictionList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "normalSayRestrictionList", al, op);
    }
    public abstract String keepMessageTypeCode_SpecifyDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);

    public void xsderiveSkillSayRestrictionList(String fn, SubQuery<SkillSayRestrictionCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillSayRestrictionCB cb = new SkillSayRestrictionCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepMessageTypeCode_SpecifyDerivedReferrer_SkillSayRestrictionList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", pp, "skillSayRestrictionList", al, op);
    }
    public abstract String keepMessageTypeCode_SpecifyDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from MESSAGE where ...)} <br>
     * MESSAGE by MESSAGE_TYPE_CODE, named 'messageAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedMessage()</span>.<span style="color: #CC4747">max</span>(messageCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     messageCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     messageCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<MessageCB> derivedMessage() {
        return xcreateQDRFunctionMessageList();
    }
    protected HpQDRFunction<MessageCB> xcreateQDRFunctionMessageList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveMessageList(fn, sq, rd, vl, op));
    }
    public void xqderiveMessageList(String fn, SubQuery<MessageCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageCB cb = new MessageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepMessageTypeCode_QueryDerivedReferrer_MessageList(cb.query()); String prpp = keepMessageTypeCode_QueryDerivedReferrer_MessageListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", sqpp, "messageList", rd, vl, prpp, op);
    }
    public abstract String keepMessageTypeCode_QueryDerivedReferrer_MessageList(MessageCQ sq);
    public abstract String keepMessageTypeCode_QueryDerivedReferrer_MessageListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from NORMAL_SAY_RESTRICTION where ...)} <br>
     * NORMAL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'normalSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedNormalSayRestriction()</span>.<span style="color: #CC4747">max</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     restrictionCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<NormalSayRestrictionCB> derivedNormalSayRestriction() {
        return xcreateQDRFunctionNormalSayRestrictionList();
    }
    protected HpQDRFunction<NormalSayRestrictionCB> xcreateQDRFunctionNormalSayRestrictionList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveNormalSayRestrictionList(fn, sq, rd, vl, op));
    }
    public void xqderiveNormalSayRestrictionList(String fn, SubQuery<NormalSayRestrictionCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        NormalSayRestrictionCB cb = new NormalSayRestrictionCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList(cb.query()); String prpp = keepMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", sqpp, "normalSayRestrictionList", rd, vl, prpp, op);
    }
    public abstract String keepMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);
    public abstract String keepMessageTypeCode_QueryDerivedReferrer_NormalSayRestrictionListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from SKILL_SAY_RESTRICTION where ...)} <br>
     * SKILL_SAY_RESTRICTION by MESSAGE_TYPE_CODE, named 'skillSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedSkillSayRestriction()</span>.<span style="color: #CC4747">max</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     restrictionCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<SkillSayRestrictionCB> derivedSkillSayRestriction() {
        return xcreateQDRFunctionSkillSayRestrictionList();
    }
    protected HpQDRFunction<SkillSayRestrictionCB> xcreateQDRFunctionSkillSayRestrictionList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveSkillSayRestrictionList(fn, sq, rd, vl, op));
    }
    public void xqderiveSkillSayRestrictionList(String fn, SubQuery<SkillSayRestrictionCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillSayRestrictionCB cb = new SkillSayRestrictionCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList(cb.query()); String prpp = keepMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", sqpp, "skillSayRestrictionList", rd, vl, prpp, op);
    }
    public abstract String keepMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);
    public abstract String keepMessageTypeCode_QueryDerivedReferrer_SkillSayRestrictionListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     */
    public void setMessageTypeCode_IsNull() { regMessageTypeCode(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=MessageType}
     */
    public void setMessageTypeCode_IsNotNull() { regMessageTypeCode(CK_ISNN, DOBJ); }

    protected void regMessageTypeCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageTypeCode(), "MESSAGE_TYPE_CODE"); }
    protected abstract ConditionValue xgetCValueMessageTypeCode();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_Equal(String messageTypeName) {
        doSetMessageTypeName_Equal(fRES(messageTypeName));
    }

    protected void doSetMessageTypeName_Equal(String messageTypeName) {
        regMessageTypeName(CK_EQ, messageTypeName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_NotEqual(String messageTypeName) {
        doSetMessageTypeName_NotEqual(fRES(messageTypeName));
    }

    protected void doSetMessageTypeName_NotEqual(String messageTypeName) {
        regMessageTypeName(CK_NES, messageTypeName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_GreaterThan(String messageTypeName) {
        regMessageTypeName(CK_GT, fRES(messageTypeName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_LessThan(String messageTypeName) {
        regMessageTypeName(CK_LT, fRES(messageTypeName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_GreaterEqual(String messageTypeName) {
        regMessageTypeName(CK_GE, fRES(messageTypeName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_LessEqual(String messageTypeName) {
        regMessageTypeName(CK_LE, fRES(messageTypeName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeNameList The collection of messageTypeName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_InScope(Collection<String> messageTypeNameList) {
        doSetMessageTypeName_InScope(messageTypeNameList);
    }

    protected void doSetMessageTypeName_InScope(Collection<String> messageTypeNameList) {
        regINS(CK_INS, cTL(messageTypeNameList), xgetCValueMessageTypeName(), "MESSAGE_TYPE_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeNameList The collection of messageTypeName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setMessageTypeName_NotInScope(Collection<String> messageTypeNameList) {
        doSetMessageTypeName_NotInScope(messageTypeNameList);
    }

    protected void doSetMessageTypeName_NotInScope(Collection<String> messageTypeNameList) {
        regINS(CK_NINS, cTL(messageTypeNameList), xgetCValueMessageTypeName(), "MESSAGE_TYPE_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setMessageTypeName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param messageTypeName The value of messageTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setMessageTypeName_LikeSearch(String messageTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setMessageTypeName_LikeSearch(messageTypeName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * <pre>e.g. setMessageTypeName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param messageTypeName The value of messageTypeName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setMessageTypeName_LikeSearch(String messageTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(messageTypeName), xgetCValueMessageTypeName(), "MESSAGE_TYPE_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setMessageTypeName_NotLikeSearch(String messageTypeName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setMessageTypeName_NotLikeSearch(messageTypeName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * MESSAGE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @param messageTypeName The value of messageTypeName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setMessageTypeName_NotLikeSearch(String messageTypeName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(messageTypeName), xgetCValueMessageTypeName(), "MESSAGE_TYPE_NAME", likeSearchOption);
    }

    protected void regMessageTypeName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueMessageTypeName(), "MESSAGE_TYPE_NAME"); }
    protected abstract ConditionValue xgetCValueMessageTypeName();

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
    public HpSLCFunction<MessageTypeCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, MessageTypeCB.class);
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
    public HpSLCFunction<MessageTypeCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, MessageTypeCB.class);
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
    public HpSLCFunction<MessageTypeCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, MessageTypeCB.class);
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
    public HpSLCFunction<MessageTypeCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, MessageTypeCB.class);
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
    public HpSLCFunction<MessageTypeCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, MessageTypeCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;MessageTypeCB&gt;() {
     *     public void query(MessageTypeCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<MessageTypeCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, MessageTypeCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageTypeCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(MessageTypeCQ sq);

    protected MessageTypeCB xcreateScalarConditionCB() {
        MessageTypeCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected MessageTypeCB xcreateScalarConditionPartitionByCB() {
        MessageTypeCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<MessageTypeCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageTypeCB cb = new MessageTypeCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "MESSAGE_TYPE_CODE";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(MessageTypeCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<MessageTypeCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(MessageTypeCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        MessageTypeCB cb = new MessageTypeCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "MESSAGE_TYPE_CODE";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(MessageTypeCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<MessageTypeCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        MessageTypeCB cb = new MessageTypeCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(MessageTypeCQ sq);

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
    protected MessageTypeCB newMyCB() {
        return new MessageTypeCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return MessageTypeCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
