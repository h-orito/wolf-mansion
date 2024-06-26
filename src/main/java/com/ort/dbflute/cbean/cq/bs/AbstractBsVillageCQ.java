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
 * The abstract condition-query of VILLAGE.
 * @author DBFlute(AutoGenerator)
 */
public abstract class AbstractBsVillageCQ extends AbstractConditionQuery {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public AbstractBsVillageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
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
        return "VILLAGE";
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villageId The value of villageId as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterThan(Integer villageId) {
        regVillageId(CK_GT, villageId);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villageId The value of villageId as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessThan(Integer villageId) {
        regVillageId(CK_LT, villageId);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villageId The value of villageId as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_GreaterEqual(Integer villageId) {
        regVillageId(CK_GE, villageId);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villageId The value of villageId as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageId_LessEqual(Integer villageId) {
        regVillageId(CK_LE, villageId);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param minNumber The min number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of villageId. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setVillageId_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueVillageId(), "VILLAGE_ID", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
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
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @param villageIdList The collection of villageId as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageId_NotInScope(Collection<Integer> villageIdList) {
        doSetVillageId_NotInScope(villageIdList);
    }

    protected void doSetVillageId_NotInScope(Collection<Integer> villageIdList) {
        regINS(CK_NINS, cTL(villageIdList), xgetCValueVillageId(), "VILLAGE_ID");
    }

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from CAMP_ALLOCATION where ...)} <br>
     * CAMP_ALLOCATION by VILLAGE_ID, named 'campAllocationAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsCampAllocation</span>(allocationCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     allocationCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of CampAllocationList for 'exists'. (NotNull)
     */
    public void existsCampAllocation(SubQuery<CampAllocationCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        CampAllocationCB cb = new CampAllocationCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_CampAllocationList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "campAllocationList");
    }
    public abstract String keepVillageId_ExistsReferrer_CampAllocationList(CampAllocationCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from NORMAL_SAY_RESTRICTION where ...)} <br>
     * NORMAL_SAY_RESTRICTION by VILLAGE_ID, named 'normalSayRestrictionAsOne'.
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
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_NormalSayRestrictionList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "normalSayRestrictionList");
    }
    public abstract String keepVillageId_ExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from SKILL_ALLOCATION where ...)} <br>
     * SKILL_ALLOCATION by VILLAGE_ID, named 'skillAllocationAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsSkillAllocation</span>(allocationCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     allocationCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of SkillAllocationList for 'exists'. (NotNull)
     */
    public void existsSkillAllocation(SubQuery<SkillAllocationCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        SkillAllocationCB cb = new SkillAllocationCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_SkillAllocationList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "skillAllocationList");
    }
    public abstract String keepVillageId_ExistsReferrer_SkillAllocationList(SkillAllocationCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from SKILL_SAY_RESTRICTION where ...)} <br>
     * SKILL_SAY_RESTRICTION by VILLAGE_ID, named 'skillSayRestrictionAsOne'.
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
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_SkillSayRestrictionList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "skillSayRestrictionList");
    }
    public abstract String keepVillageId_ExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from VILLAGE_CHARA_GROUP where ...)} <br>
     * VILLAGE_CHARA_GROUP by VILLAGE_ID, named 'villageCharaGroupAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillageCharaGroup</span>(groupCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     groupCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageCharaGroupList for 'exists'. (NotNull)
     */
    public void existsVillageCharaGroup(SubQuery<VillageCharaGroupCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageCharaGroupCB cb = new VillageCharaGroupCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_VillageCharaGroupList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageCharaGroupList");
    }
    public abstract String keepVillageId_ExistsReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from VILLAGE_DAY where ...)} <br>
     * VILLAGE_DAY by VILLAGE_ID, named 'villageDayAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillageDay</span>(dayCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     dayCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageDayList for 'exists'. (NotNull)
     */
    public void existsVillageDay(SubQuery<VillageDayCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageDayCB cb = new VillageDayCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_VillageDayList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageDayList");
    }
    public abstract String keepVillageId_ExistsReferrer_VillageDayList(VillageDayCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from VILLAGE_PLAYER where ...)} <br>
     * VILLAGE_PLAYER by VILLAGE_ID, named 'villagePlayerAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillagePlayer</span>(playerCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     playerCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillagePlayerList for 'exists'. (NotNull)
     */
    public void existsVillagePlayer(SubQuery<VillagePlayerCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_VillagePlayerList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villagePlayerList");
    }
    public abstract String keepVillageId_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq);

    /**
     * Set up ExistsReferrer (correlated sub-query). <br>
     * {exists (select VILLAGE_ID from VILLAGE_TAG where ...)} <br>
     * VILLAGE_TAG by VILLAGE_ID, named 'villageTagAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">existsVillageTag</span>(tagCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     tagCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageTagList for 'exists'. (NotNull)
     */
    public void existsVillageTag(SubQuery<VillageTagCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_ExistsReferrer_VillageTagList(cb.query());
        registerExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageTagList");
    }
    public abstract String keepVillageId_ExistsReferrer_VillageTagList(VillageTagCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from CAMP_ALLOCATION where ...)} <br>
     * CAMP_ALLOCATION by VILLAGE_ID, named 'campAllocationAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsCampAllocation</span>(allocationCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     allocationCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_CampAllocationList for 'not exists'. (NotNull)
     */
    public void notExistsCampAllocation(SubQuery<CampAllocationCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        CampAllocationCB cb = new CampAllocationCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_CampAllocationList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "campAllocationList");
    }
    public abstract String keepVillageId_NotExistsReferrer_CampAllocationList(CampAllocationCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from NORMAL_SAY_RESTRICTION where ...)} <br>
     * NORMAL_SAY_RESTRICTION by VILLAGE_ID, named 'normalSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsNormalSayRestriction</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_NormalSayRestrictionList for 'not exists'. (NotNull)
     */
    public void notExistsNormalSayRestriction(SubQuery<NormalSayRestrictionCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        NormalSayRestrictionCB cb = new NormalSayRestrictionCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_NormalSayRestrictionList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "normalSayRestrictionList");
    }
    public abstract String keepVillageId_NotExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from SKILL_ALLOCATION where ...)} <br>
     * SKILL_ALLOCATION by VILLAGE_ID, named 'skillAllocationAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsSkillAllocation</span>(allocationCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     allocationCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_SkillAllocationList for 'not exists'. (NotNull)
     */
    public void notExistsSkillAllocation(SubQuery<SkillAllocationCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        SkillAllocationCB cb = new SkillAllocationCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_SkillAllocationList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "skillAllocationList");
    }
    public abstract String keepVillageId_NotExistsReferrer_SkillAllocationList(SkillAllocationCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from SKILL_SAY_RESTRICTION where ...)} <br>
     * SKILL_SAY_RESTRICTION by VILLAGE_ID, named 'skillSayRestrictionAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsSkillSayRestriction</span>(restrictionCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     restrictionCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_SkillSayRestrictionList for 'not exists'. (NotNull)
     */
    public void notExistsSkillSayRestriction(SubQuery<SkillSayRestrictionCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        SkillSayRestrictionCB cb = new SkillSayRestrictionCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_SkillSayRestrictionList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "skillSayRestrictionList");
    }
    public abstract String keepVillageId_NotExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from VILLAGE_CHARA_GROUP where ...)} <br>
     * VILLAGE_CHARA_GROUP by VILLAGE_ID, named 'villageCharaGroupAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillageCharaGroup</span>(groupCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     groupCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_VillageCharaGroupList for 'not exists'. (NotNull)
     */
    public void notExistsVillageCharaGroup(SubQuery<VillageCharaGroupCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageCharaGroupCB cb = new VillageCharaGroupCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_VillageCharaGroupList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageCharaGroupList");
    }
    public abstract String keepVillageId_NotExistsReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from VILLAGE_DAY where ...)} <br>
     * VILLAGE_DAY by VILLAGE_ID, named 'villageDayAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillageDay</span>(dayCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     dayCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_VillageDayList for 'not exists'. (NotNull)
     */
    public void notExistsVillageDay(SubQuery<VillageDayCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageDayCB cb = new VillageDayCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_VillageDayList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageDayList");
    }
    public abstract String keepVillageId_NotExistsReferrer_VillageDayList(VillageDayCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from VILLAGE_PLAYER where ...)} <br>
     * VILLAGE_PLAYER by VILLAGE_ID, named 'villagePlayerAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillagePlayer</span>(playerCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     playerCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_VillagePlayerList for 'not exists'. (NotNull)
     */
    public void notExistsVillagePlayer(SubQuery<VillagePlayerCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_VillagePlayerList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villagePlayerList");
    }
    public abstract String keepVillageId_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq);

    /**
     * Set up NotExistsReferrer (correlated sub-query). <br>
     * {not exists (select VILLAGE_ID from VILLAGE_TAG where ...)} <br>
     * VILLAGE_TAG by VILLAGE_ID, named 'villageTagAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">notExistsVillageTag</span>(tagCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     tagCB.query().set...
     * });
     * </pre>
     * @param subCBLambda The callback for sub-query of VillageId_NotExistsReferrer_VillageTagList for 'not exists'. (NotNull)
     */
    public void notExistsVillageTag(SubQuery<VillageTagCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForExistsReferrer(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepVillageId_NotExistsReferrer_VillageTagList(cb.query());
        registerNotExistsReferrer(cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageTagList");
    }
    public abstract String keepVillageId_NotExistsReferrer_VillageTagList(VillageTagCQ sq);

    public void xsderiveCampAllocationList(String fn, SubQuery<CampAllocationCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CampAllocationCB cb = new CampAllocationCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_CampAllocationList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "campAllocationList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_CampAllocationList(CampAllocationCQ sq);

    public void xsderiveNormalSayRestrictionList(String fn, SubQuery<NormalSayRestrictionCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        NormalSayRestrictionCB cb = new NormalSayRestrictionCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_NormalSayRestrictionList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "normalSayRestrictionList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);

    public void xsderiveSkillAllocationList(String fn, SubQuery<SkillAllocationCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillAllocationCB cb = new SkillAllocationCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_SkillAllocationList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "skillAllocationList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_SkillAllocationList(SkillAllocationCQ sq);

    public void xsderiveSkillSayRestrictionList(String fn, SubQuery<SkillSayRestrictionCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillSayRestrictionCB cb = new SkillSayRestrictionCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_SkillSayRestrictionList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "skillSayRestrictionList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);

    public void xsderiveVillageCharaGroupList(String fn, SubQuery<VillageCharaGroupCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCharaGroupCB cb = new VillageCharaGroupCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_VillageCharaGroupList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageCharaGroupList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq);

    public void xsderiveVillageDayList(String fn, SubQuery<VillageDayCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageDayCB cb = new VillageDayCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_VillageDayList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageDayList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_VillageDayList(VillageDayCQ sq);

    public void xsderiveVillagePlayerList(String fn, SubQuery<VillagePlayerCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_VillagePlayerList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villagePlayerList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq);

    public void xsderiveVillageTagList(String fn, SubQuery<VillageTagCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepVillageId_SpecifyDerivedReferrer_VillageTagList(cb.query());
        registerSpecifyDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", pp, "villageTagList", al, op);
    }
    public abstract String keepVillageId_SpecifyDerivedReferrer_VillageTagList(VillageTagCQ sq);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from CAMP_ALLOCATION where ...)} <br>
     * CAMP_ALLOCATION by VILLAGE_ID, named 'campAllocationAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedCampAllocation()</span>.<span style="color: #CC4747">max</span>(allocationCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     allocationCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     allocationCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<CampAllocationCB> derivedCampAllocation() {
        return xcreateQDRFunctionCampAllocationList();
    }
    protected HpQDRFunction<CampAllocationCB> xcreateQDRFunctionCampAllocationList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveCampAllocationList(fn, sq, rd, vl, op));
    }
    public void xqderiveCampAllocationList(String fn, SubQuery<CampAllocationCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        CampAllocationCB cb = new CampAllocationCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_CampAllocationList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_CampAllocationListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "campAllocationList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_CampAllocationList(CampAllocationCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_CampAllocationListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from NORMAL_SAY_RESTRICTION where ...)} <br>
     * NORMAL_SAY_RESTRICTION by VILLAGE_ID, named 'normalSayRestrictionAsOne'.
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
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_NormalSayRestrictionList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_NormalSayRestrictionListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "normalSayRestrictionList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_NormalSayRestrictionListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from SKILL_ALLOCATION where ...)} <br>
     * SKILL_ALLOCATION by VILLAGE_ID, named 'skillAllocationAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedSkillAllocation()</span>.<span style="color: #CC4747">max</span>(allocationCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     allocationCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     allocationCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<SkillAllocationCB> derivedSkillAllocation() {
        return xcreateQDRFunctionSkillAllocationList();
    }
    protected HpQDRFunction<SkillAllocationCB> xcreateQDRFunctionSkillAllocationList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveSkillAllocationList(fn, sq, rd, vl, op));
    }
    public void xqderiveSkillAllocationList(String fn, SubQuery<SkillAllocationCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        SkillAllocationCB cb = new SkillAllocationCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_SkillAllocationList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_SkillAllocationListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "skillAllocationList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_SkillAllocationList(SkillAllocationCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_SkillAllocationListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from SKILL_SAY_RESTRICTION where ...)} <br>
     * SKILL_SAY_RESTRICTION by VILLAGE_ID, named 'skillSayRestrictionAsOne'.
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
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_SkillSayRestrictionList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_SkillSayRestrictionListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "skillSayRestrictionList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_SkillSayRestrictionListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from VILLAGE_CHARA_GROUP where ...)} <br>
     * VILLAGE_CHARA_GROUP by VILLAGE_ID, named 'villageCharaGroupAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillageCharaGroup()</span>.<span style="color: #CC4747">max</span>(groupCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     groupCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     groupCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillageCharaGroupCB> derivedVillageCharaGroup() {
        return xcreateQDRFunctionVillageCharaGroupList();
    }
    protected HpQDRFunction<VillageCharaGroupCB> xcreateQDRFunctionVillageCharaGroupList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillageCharaGroupList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillageCharaGroupList(String fn, SubQuery<VillageCharaGroupCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCharaGroupCB cb = new VillageCharaGroupCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_VillageCharaGroupList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_VillageCharaGroupListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "villageCharaGroupList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_VillageCharaGroupListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from VILLAGE_DAY where ...)} <br>
     * VILLAGE_DAY by VILLAGE_ID, named 'villageDayAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillageDay()</span>.<span style="color: #CC4747">max</span>(dayCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     dayCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     dayCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillageDayCB> derivedVillageDay() {
        return xcreateQDRFunctionVillageDayList();
    }
    protected HpQDRFunction<VillageDayCB> xcreateQDRFunctionVillageDayList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillageDayList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillageDayList(String fn, SubQuery<VillageDayCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageDayCB cb = new VillageDayCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_VillageDayList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_VillageDayListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "villageDayList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_VillageDayList(VillageDayCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_VillageDayListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from VILLAGE_PLAYER where ...)} <br>
     * VILLAGE_PLAYER by VILLAGE_ID, named 'villagePlayerAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillagePlayer()</span>.<span style="color: #CC4747">max</span>(playerCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     playerCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     playerCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillagePlayerCB> derivedVillagePlayer() {
        return xcreateQDRFunctionVillagePlayerList();
    }
    protected HpQDRFunction<VillagePlayerCB> xcreateQDRFunctionVillagePlayerList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillagePlayerList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillagePlayerList(String fn, SubQuery<VillagePlayerCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillagePlayerCB cb = new VillagePlayerCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_VillagePlayerList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_VillagePlayerListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "villagePlayerList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_VillagePlayerListParameter(Object vl);

    /**
     * Prepare for (Query)DerivedReferrer (correlated sub-query). <br>
     * {FOO &lt;= (select max(BAR) from VILLAGE_TAG where ...)} <br>
     * VILLAGE_TAG by VILLAGE_ID, named 'villageTagAsOne'.
     * <pre>
     * cb.query().<span style="color: #CC4747">derivedVillageTag()</span>.<span style="color: #CC4747">max</span>(tagCB <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> {
     *     tagCB.specify().<span style="color: #CC4747">columnFoo...</span> <span style="color: #3F7E5E">// derived column by function</span>
     *     tagCB.query().setBar... <span style="color: #3F7E5E">// referrer condition</span>
     * }).<span style="color: #CC4747">greaterEqual</span>(123); <span style="color: #3F7E5E">// condition to derived column</span>
     * </pre>
     * @return The object to set up a function for referrer table. (NotNull)
     */
    public HpQDRFunction<VillageTagCB> derivedVillageTag() {
        return xcreateQDRFunctionVillageTagList();
    }
    protected HpQDRFunction<VillageTagCB> xcreateQDRFunctionVillageTagList() {
        return xcQDRFunc((fn, sq, rd, vl, op) -> xqderiveVillageTagList(fn, sq, rd, vl, op));
    }
    public void xqderiveVillageTagList(String fn, SubQuery<VillageTagCB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageTagCB cb = new VillageTagCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String sqpp = keepVillageId_QueryDerivedReferrer_VillageTagList(cb.query()); String prpp = keepVillageId_QueryDerivedReferrer_VillageTagListParameter(vl);
        registerQueryDerivedReferrer(fn, cb.query(), "VILLAGE_ID", "VILLAGE_ID", sqpp, "villageTagList", rd, vl, prpp, op);
    }
    public abstract String keepVillageId_QueryDerivedReferrer_VillageTagList(VillageTagCQ sq);
    public abstract String keepVillageId_QueryDerivedReferrer_VillageTagListParameter(Object vl);

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setVillageId_IsNull() { regVillageId(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     */
    public void setVillageId_IsNotNull() { regVillageId(CK_ISNN, DOBJ); }

    protected void regVillageId(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageId(), "VILLAGE_ID"); }
    protected abstract ConditionValue xgetCValueVillageId();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_Equal(String villageDisplayName) {
        doSetVillageDisplayName_Equal(fRES(villageDisplayName));
    }

    protected void doSetVillageDisplayName_Equal(String villageDisplayName) {
        regVillageDisplayName(CK_EQ, villageDisplayName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_NotEqual(String villageDisplayName) {
        doSetVillageDisplayName_NotEqual(fRES(villageDisplayName));
    }

    protected void doSetVillageDisplayName_NotEqual(String villageDisplayName) {
        regVillageDisplayName(CK_NES, villageDisplayName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_GreaterThan(String villageDisplayName) {
        regVillageDisplayName(CK_GT, fRES(villageDisplayName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_LessThan(String villageDisplayName) {
        regVillageDisplayName(CK_LT, fRES(villageDisplayName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_GreaterEqual(String villageDisplayName) {
        regVillageDisplayName(CK_GE, fRES(villageDisplayName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_LessEqual(String villageDisplayName) {
        regVillageDisplayName(CK_LE, fRES(villageDisplayName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayNameList The collection of villageDisplayName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_InScope(Collection<String> villageDisplayNameList) {
        doSetVillageDisplayName_InScope(villageDisplayNameList);
    }

    protected void doSetVillageDisplayName_InScope(Collection<String> villageDisplayNameList) {
        regINS(CK_INS, cTL(villageDisplayNameList), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayNameList The collection of villageDisplayName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageDisplayName_NotInScope(Collection<String> villageDisplayNameList) {
        doSetVillageDisplayName_NotInScope(villageDisplayNameList);
    }

    protected void doSetVillageDisplayName_NotInScope(Collection<String> villageDisplayNameList) {
        regINS(CK_NINS, cTL(villageDisplayNameList), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setVillageDisplayName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param villageDisplayName The value of villageDisplayName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillageDisplayName_LikeSearch(String villageDisplayName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillageDisplayName_LikeSearch(villageDisplayName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)} <br>
     * <pre>e.g. setVillageDisplayName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param villageDisplayName The value of villageDisplayName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setVillageDisplayName_LikeSearch(String villageDisplayName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(villageDisplayName), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setVillageDisplayName_NotLikeSearch(String villageDisplayName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setVillageDisplayName_NotLikeSearch(villageDisplayName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @param villageDisplayName The value of villageDisplayName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setVillageDisplayName_NotLikeSearch(String villageDisplayName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(villageDisplayName), xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME", likeSearchOption);
    }

    protected void regVillageDisplayName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageDisplayName(), "VILLAGE_DISPLAY_NAME"); }
    protected abstract ConditionValue xgetCValueVillageDisplayName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_Equal(String createPlayerName) {
        doSetCreatePlayerName_Equal(fRES(createPlayerName));
    }

    protected void doSetCreatePlayerName_Equal(String createPlayerName) {
        regCreatePlayerName(CK_EQ, createPlayerName);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_NotEqual(String createPlayerName) {
        doSetCreatePlayerName_NotEqual(fRES(createPlayerName));
    }

    protected void doSetCreatePlayerName_NotEqual(String createPlayerName) {
        regCreatePlayerName(CK_NES, createPlayerName);
    }

    /**
     * GreaterThan(&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as greaterThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_GreaterThan(String createPlayerName) {
        regCreatePlayerName(CK_GT, fRES(createPlayerName));
    }

    /**
     * LessThan(&lt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as lessThan. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_LessThan(String createPlayerName) {
        regCreatePlayerName(CK_LT, fRES(createPlayerName));
    }

    /**
     * GreaterEqual(&gt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as greaterEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_GreaterEqual(String createPlayerName) {
        regCreatePlayerName(CK_GE, fRES(createPlayerName));
    }

    /**
     * LessEqual(&lt;=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as lessEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_LessEqual(String createPlayerName) {
        regCreatePlayerName(CK_LE, fRES(createPlayerName));
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerNameList The collection of createPlayerName as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_InScope(Collection<String> createPlayerNameList) {
        doSetCreatePlayerName_InScope(createPlayerNameList);
    }

    protected void doSetCreatePlayerName_InScope(Collection<String> createPlayerNameList) {
        regINS(CK_INS, cTL(createPlayerNameList), xgetCValueCreatePlayerName(), "CREATE_PLAYER_NAME");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerNameList The collection of createPlayerName as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setCreatePlayerName_NotInScope(Collection<String> createPlayerNameList) {
        doSetCreatePlayerName_NotInScope(createPlayerNameList);
    }

    protected void doSetCreatePlayerName_NotInScope(Collection<String> createPlayerNameList) {
        regINS(CK_NINS, cTL(createPlayerNameList), xgetCValueCreatePlayerName(), "CREATE_PLAYER_NAME");
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)} <br>
     * <pre>e.g. setCreatePlayerName_LikeSearch("xxx", op <span style="color: #90226C; font-weight: bold"><span style="font-size: 120%">-</span>&gt;</span> op.<span style="color: #CC4747">likeContain()</span>);</pre>
     * @param createPlayerName The value of createPlayerName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCreatePlayerName_LikeSearch(String createPlayerName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCreatePlayerName_LikeSearch(createPlayerName, xcLSOP(opLambda));
    }

    /**
     * LikeSearch with various options. (versatile) {like '%xxx%' escape ...}. And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)} <br>
     * <pre>e.g. setCreatePlayerName_LikeSearch("xxx", new <span style="color: #CC4747">LikeSearchOption</span>().likeContain());</pre>
     * @param createPlayerName The value of createPlayerName as likeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of like-search. (NotNull)
     */
    protected void setCreatePlayerName_LikeSearch(String createPlayerName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_LS, fRES(createPlayerName), xgetCValueCreatePlayerName(), "CREATE_PLAYER_NAME", likeSearchOption);
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param opLambda The callback for option of like-search. (NotNull)
     */
    public void setCreatePlayerName_NotLikeSearch(String createPlayerName, ConditionOptionCall<LikeSearchOption> opLambda) {
        setCreatePlayerName_NotLikeSearch(createPlayerName, xcLSOP(opLambda));
    }

    /**
     * NotLikeSearch with various options. (versatile) {not like 'xxx%' escape ...} <br>
     * And NullOrEmptyIgnored, SeveralRegistered. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @param createPlayerName The value of createPlayerName as notLikeSearch. (basically NotNull, NotEmpty: error as default, or no condition as option)
     * @param likeSearchOption The option of not-like-search. (NotNull)
     */
    protected void setCreatePlayerName_NotLikeSearch(String createPlayerName, LikeSearchOption likeSearchOption) {
        regLSQ(CK_NLS, fRES(createPlayerName), xgetCValueCreatePlayerName(), "CREATE_PLAYER_NAME", likeSearchOption);
    }

    protected void regCreatePlayerName(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueCreatePlayerName(), "CREATE_PLAYER_NAME"); }
    protected abstract ConditionValue xgetCValueCreatePlayerName();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus}
     * @param villageStatusCode The value of villageStatusCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageStatusCode_Equal(String villageStatusCode) {
        doSetVillageStatusCode_Equal(fRES(villageStatusCode));
    }

    /**
     * Equal(=). As VillageStatus. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus} <br>
     * 村ステータス
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageStatusCode_Equal_AsVillageStatus(CDef.VillageStatus cdef) {
        doSetVillageStatusCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 廃村 (CANCEL). And OnlyOnceRegistered. <br>
     * 廃村
     */
    public void setVillageStatusCode_Equal_廃村() {
        setVillageStatusCode_Equal_AsVillageStatus(CDef.VillageStatus.廃村);
    }

    /**
     * Equal(=). As 終了 (COMPLETED). And OnlyOnceRegistered. <br>
     * 終了
     */
    public void setVillageStatusCode_Equal_終了() {
        setVillageStatusCode_Equal_AsVillageStatus(CDef.VillageStatus.終了);
    }

    /**
     * Equal(=). As エピローグ (EPILOGUE). And OnlyOnceRegistered. <br>
     * エピローグ
     */
    public void setVillageStatusCode_Equal_エピローグ() {
        setVillageStatusCode_Equal_AsVillageStatus(CDef.VillageStatus.エピローグ);
    }

    /**
     * Equal(=). As 募集中 (IN_PREPARATION). And OnlyOnceRegistered. <br>
     * 募集中
     */
    public void setVillageStatusCode_Equal_募集中() {
        setVillageStatusCode_Equal_AsVillageStatus(CDef.VillageStatus.募集中);
    }

    /**
     * Equal(=). As 進行中 (IN_PROGRESS). And OnlyOnceRegistered. <br>
     * 進行中
     */
    public void setVillageStatusCode_Equal_進行中() {
        setVillageStatusCode_Equal_AsVillageStatus(CDef.VillageStatus.進行中);
    }

    /**
     * Equal(=). As 開始待ち (WAITING). And OnlyOnceRegistered. <br>
     * 開始待ち
     */
    public void setVillageStatusCode_Equal_開始待ち() {
        setVillageStatusCode_Equal_AsVillageStatus(CDef.VillageStatus.開始待ち);
    }

    protected void doSetVillageStatusCode_Equal(String villageStatusCode) {
        regVillageStatusCode(CK_EQ, villageStatusCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus}
     * @param villageStatusCode The value of villageStatusCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageStatusCode_NotEqual(String villageStatusCode) {
        doSetVillageStatusCode_NotEqual(fRES(villageStatusCode));
    }

    /**
     * NotEqual(&lt;&gt;). As VillageStatus. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus} <br>
     * 村ステータス
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setVillageStatusCode_NotEqual_AsVillageStatus(CDef.VillageStatus cdef) {
        doSetVillageStatusCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 廃村 (CANCEL). And OnlyOnceRegistered. <br>
     * 廃村
     */
    public void setVillageStatusCode_NotEqual_廃村() {
        setVillageStatusCode_NotEqual_AsVillageStatus(CDef.VillageStatus.廃村);
    }

    /**
     * NotEqual(&lt;&gt;). As 終了 (COMPLETED). And OnlyOnceRegistered. <br>
     * 終了
     */
    public void setVillageStatusCode_NotEqual_終了() {
        setVillageStatusCode_NotEqual_AsVillageStatus(CDef.VillageStatus.終了);
    }

    /**
     * NotEqual(&lt;&gt;). As エピローグ (EPILOGUE). And OnlyOnceRegistered. <br>
     * エピローグ
     */
    public void setVillageStatusCode_NotEqual_エピローグ() {
        setVillageStatusCode_NotEqual_AsVillageStatus(CDef.VillageStatus.エピローグ);
    }

    /**
     * NotEqual(&lt;&gt;). As 募集中 (IN_PREPARATION). And OnlyOnceRegistered. <br>
     * 募集中
     */
    public void setVillageStatusCode_NotEqual_募集中() {
        setVillageStatusCode_NotEqual_AsVillageStatus(CDef.VillageStatus.募集中);
    }

    /**
     * NotEqual(&lt;&gt;). As 進行中 (IN_PROGRESS). And OnlyOnceRegistered. <br>
     * 進行中
     */
    public void setVillageStatusCode_NotEqual_進行中() {
        setVillageStatusCode_NotEqual_AsVillageStatus(CDef.VillageStatus.進行中);
    }

    /**
     * NotEqual(&lt;&gt;). As 開始待ち (WAITING). And OnlyOnceRegistered. <br>
     * 開始待ち
     */
    public void setVillageStatusCode_NotEqual_開始待ち() {
        setVillageStatusCode_NotEqual_AsVillageStatus(CDef.VillageStatus.開始待ち);
    }

    protected void doSetVillageStatusCode_NotEqual(String villageStatusCode) {
        regVillageStatusCode(CK_NES, villageStatusCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus}
     * @param villageStatusCodeList The collection of villageStatusCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageStatusCode_InScope(Collection<String> villageStatusCodeList) {
        doSetVillageStatusCode_InScope(villageStatusCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As VillageStatus. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus} <br>
     * 村ステータス
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageStatusCode_InScope_AsVillageStatus(Collection<CDef.VillageStatus> cdefList) {
        doSetVillageStatusCode_InScope(cTStrL(cdefList));
    }

    protected void doSetVillageStatusCode_InScope(Collection<String> villageStatusCodeList) {
        regINS(CK_INS, cTL(villageStatusCodeList), xgetCValueVillageStatusCode(), "VILLAGE_STATUS_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus}
     * @param villageStatusCodeList The collection of villageStatusCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setVillageStatusCode_NotInScope(Collection<String> villageStatusCodeList) {
        doSetVillageStatusCode_NotInScope(villageStatusCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As VillageStatus. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus} <br>
     * 村ステータス
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setVillageStatusCode_NotInScope_AsVillageStatus(Collection<CDef.VillageStatus> cdefList) {
        doSetVillageStatusCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetVillageStatusCode_NotInScope(Collection<String> villageStatusCodeList) {
        regINS(CK_NINS, cTL(villageStatusCodeList), xgetCValueVillageStatusCode(), "VILLAGE_STATUS_CODE");
    }

    protected void regVillageStatusCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueVillageStatusCode(), "VILLAGE_STATUS_CODE"); }
    protected abstract ConditionValue xgetCValueVillageStatusCode();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidth The value of roomSizeWidth as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_Equal(Integer roomSizeWidth) {
        doSetRoomSizeWidth_Equal(roomSizeWidth);
    }

    protected void doSetRoomSizeWidth_Equal(Integer roomSizeWidth) {
        regRoomSizeWidth(CK_EQ, roomSizeWidth);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidth The value of roomSizeWidth as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_NotEqual(Integer roomSizeWidth) {
        doSetRoomSizeWidth_NotEqual(roomSizeWidth);
    }

    protected void doSetRoomSizeWidth_NotEqual(Integer roomSizeWidth) {
        regRoomSizeWidth(CK_NES, roomSizeWidth);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidth The value of roomSizeWidth as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_GreaterThan(Integer roomSizeWidth) {
        regRoomSizeWidth(CK_GT, roomSizeWidth);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidth The value of roomSizeWidth as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_LessThan(Integer roomSizeWidth) {
        regRoomSizeWidth(CK_LT, roomSizeWidth);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidth The value of roomSizeWidth as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_GreaterEqual(Integer roomSizeWidth) {
        regRoomSizeWidth(CK_GE, roomSizeWidth);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidth The value of roomSizeWidth as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_LessEqual(Integer roomSizeWidth) {
        regRoomSizeWidth(CK_LE, roomSizeWidth);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param minNumber The min number of roomSizeWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of roomSizeWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setRoomSizeWidth_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setRoomSizeWidth_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param minNumber The min number of roomSizeWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of roomSizeWidth. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setRoomSizeWidth_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueRoomSizeWidth(), "ROOM_SIZE_WIDTH", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidthList The collection of roomSizeWidth as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_InScope(Collection<Integer> roomSizeWidthList) {
        doSetRoomSizeWidth_InScope(roomSizeWidthList);
    }

    protected void doSetRoomSizeWidth_InScope(Collection<Integer> roomSizeWidthList) {
        regINS(CK_INS, cTL(roomSizeWidthList), xgetCValueRoomSizeWidth(), "ROOM_SIZE_WIDTH");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @param roomSizeWidthList The collection of roomSizeWidth as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRoomSizeWidth_NotInScope(Collection<Integer> roomSizeWidthList) {
        doSetRoomSizeWidth_NotInScope(roomSizeWidthList);
    }

    protected void doSetRoomSizeWidth_NotInScope(Collection<Integer> roomSizeWidthList) {
        regINS(CK_NINS, cTL(roomSizeWidthList), xgetCValueRoomSizeWidth(), "ROOM_SIZE_WIDTH");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     */
    public void setRoomSizeWidth_IsNull() { regRoomSizeWidth(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     */
    public void setRoomSizeWidth_IsNotNull() { regRoomSizeWidth(CK_ISNN, DOBJ); }

    protected void regRoomSizeWidth(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRoomSizeWidth(), "ROOM_SIZE_WIDTH"); }
    protected abstract ConditionValue xgetCValueRoomSizeWidth();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeight The value of roomSizeHeight as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_Equal(Integer roomSizeHeight) {
        doSetRoomSizeHeight_Equal(roomSizeHeight);
    }

    protected void doSetRoomSizeHeight_Equal(Integer roomSizeHeight) {
        regRoomSizeHeight(CK_EQ, roomSizeHeight);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeight The value of roomSizeHeight as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_NotEqual(Integer roomSizeHeight) {
        doSetRoomSizeHeight_NotEqual(roomSizeHeight);
    }

    protected void doSetRoomSizeHeight_NotEqual(Integer roomSizeHeight) {
        regRoomSizeHeight(CK_NES, roomSizeHeight);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeight The value of roomSizeHeight as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_GreaterThan(Integer roomSizeHeight) {
        regRoomSizeHeight(CK_GT, roomSizeHeight);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeight The value of roomSizeHeight as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_LessThan(Integer roomSizeHeight) {
        regRoomSizeHeight(CK_LT, roomSizeHeight);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeight The value of roomSizeHeight as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_GreaterEqual(Integer roomSizeHeight) {
        regRoomSizeHeight(CK_GE, roomSizeHeight);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeight The value of roomSizeHeight as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_LessEqual(Integer roomSizeHeight) {
        regRoomSizeHeight(CK_LE, roomSizeHeight);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param minNumber The min number of roomSizeHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of roomSizeHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setRoomSizeHeight_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setRoomSizeHeight_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param minNumber The min number of roomSizeHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of roomSizeHeight. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setRoomSizeHeight_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueRoomSizeHeight(), "ROOM_SIZE_HEIGHT", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeightList The collection of roomSizeHeight as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_InScope(Collection<Integer> roomSizeHeightList) {
        doSetRoomSizeHeight_InScope(roomSizeHeightList);
    }

    protected void doSetRoomSizeHeight_InScope(Collection<Integer> roomSizeHeightList) {
        regINS(CK_INS, cTL(roomSizeHeightList), xgetCValueRoomSizeHeight(), "ROOM_SIZE_HEIGHT");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @param roomSizeHeightList The collection of roomSizeHeight as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setRoomSizeHeight_NotInScope(Collection<Integer> roomSizeHeightList) {
        doSetRoomSizeHeight_NotInScope(roomSizeHeightList);
    }

    protected void doSetRoomSizeHeight_NotInScope(Collection<Integer> roomSizeHeightList) {
        regINS(CK_NINS, cTL(roomSizeHeightList), xgetCValueRoomSizeHeight(), "ROOM_SIZE_HEIGHT");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     */
    public void setRoomSizeHeight_IsNull() { regRoomSizeHeight(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     */
    public void setRoomSizeHeight_IsNotNull() { regRoomSizeHeight(CK_ISNN, DOBJ); }

    protected void regRoomSizeHeight(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueRoomSizeHeight(), "ROOM_SIZE_HEIGHT"); }
    protected abstract ConditionValue xgetCValueRoomSizeHeight();

    /**
     * Equal(=). And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDay The value of epilogueDay as equal. (basically NotNull: error as default, or no condition as option)
     */
    public void setEpilogueDay_Equal(Integer epilogueDay) {
        doSetEpilogueDay_Equal(epilogueDay);
    }

    protected void doSetEpilogueDay_Equal(Integer epilogueDay) {
        regEpilogueDay(CK_EQ, epilogueDay);
    }

    /**
     * NotEqual(&lt;&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDay The value of epilogueDay as notEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setEpilogueDay_NotEqual(Integer epilogueDay) {
        doSetEpilogueDay_NotEqual(epilogueDay);
    }

    protected void doSetEpilogueDay_NotEqual(Integer epilogueDay) {
        regEpilogueDay(CK_NES, epilogueDay);
    }

    /**
     * GreaterThan(&gt;). And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDay The value of epilogueDay as greaterThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setEpilogueDay_GreaterThan(Integer epilogueDay) {
        regEpilogueDay(CK_GT, epilogueDay);
    }

    /**
     * LessThan(&lt;). And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDay The value of epilogueDay as lessThan. (basically NotNull: error as default, or no condition as option)
     */
    public void setEpilogueDay_LessThan(Integer epilogueDay) {
        regEpilogueDay(CK_LT, epilogueDay);
    }

    /**
     * GreaterEqual(&gt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDay The value of epilogueDay as greaterEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setEpilogueDay_GreaterEqual(Integer epilogueDay) {
        regEpilogueDay(CK_GE, epilogueDay);
    }

    /**
     * LessEqual(&lt;=). And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDay The value of epilogueDay as lessEqual. (basically NotNull: error as default, or no condition as option)
     */
    public void setEpilogueDay_LessEqual(Integer epilogueDay) {
        regEpilogueDay(CK_LE, epilogueDay);
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param minNumber The min number of epilogueDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of epilogueDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param opLambda The callback for option of range-of. (NotNull)
     */
    public void setEpilogueDay_RangeOf(Integer minNumber, Integer maxNumber, ConditionOptionCall<RangeOfOption> opLambda) {
        setEpilogueDay_RangeOf(minNumber, maxNumber, xcROOP(opLambda));
    }

    /**
     * RangeOf with various options. (versatile) <br>
     * {(default) minNumber &lt;= column &lt;= maxNumber} <br>
     * And NullIgnored, OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param minNumber The min number of epilogueDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param maxNumber The max number of epilogueDay. (basically NotNull: if op.allowOneSide(), null allowed)
     * @param rangeOfOption The option of range-of. (NotNull)
     */
    protected void setEpilogueDay_RangeOf(Integer minNumber, Integer maxNumber, RangeOfOption rangeOfOption) {
        regROO(minNumber, maxNumber, xgetCValueEpilogueDay(), "EPILOGUE_DAY", rangeOfOption);
    }

    /**
     * InScope {in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDayList The collection of epilogueDay as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setEpilogueDay_InScope(Collection<Integer> epilogueDayList) {
        doSetEpilogueDay_InScope(epilogueDayList);
    }

    protected void doSetEpilogueDay_InScope(Collection<Integer> epilogueDayList) {
        regINS(CK_INS, cTL(epilogueDayList), xgetCValueEpilogueDay(), "EPILOGUE_DAY");
    }

    /**
     * NotInScope {not in (1, 2)}. And NullIgnored, NullElementIgnored, SeveralRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @param epilogueDayList The collection of epilogueDay as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setEpilogueDay_NotInScope(Collection<Integer> epilogueDayList) {
        doSetEpilogueDay_NotInScope(epilogueDayList);
    }

    protected void doSetEpilogueDay_NotInScope(Collection<Integer> epilogueDayList) {
        regINS(CK_NINS, cTL(epilogueDayList), xgetCValueEpilogueDay(), "EPILOGUE_DAY");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     */
    public void setEpilogueDay_IsNull() { regEpilogueDay(CK_ISN, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     */
    public void setEpilogueDay_IsNotNull() { regEpilogueDay(CK_ISNN, DOBJ); }

    protected void regEpilogueDay(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueEpilogueDay(), "EPILOGUE_DAY"); }
    protected abstract ConditionValue xgetCValueEpilogueDay();

    /**
     * Equal(=). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     * @param winCampCode The value of winCampCode as equal. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setWinCampCode_Equal(String winCampCode) {
        doSetWinCampCode_Equal(fRES(winCampCode));
    }

    /**
     * Equal(=). As Camp. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp} <br>
     * 陣営
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setWinCampCode_Equal_AsCamp(CDef.Camp cdef) {
        doSetWinCampCode_Equal(cdef != null ? cdef.code() : null);
    }

    /**
     * Equal(=). As 愉快犯陣営 (CRIMINAL). And OnlyOnceRegistered. <br>
     * 愉快犯陣営
     */
    public void setWinCampCode_Equal_愉快犯陣営() {
        setWinCampCode_Equal_AsCamp(CDef.Camp.愉快犯陣営);
    }

    /**
     * Equal(=). As 狐陣営 (FOX). And OnlyOnceRegistered. <br>
     * 狐陣営
     */
    public void setWinCampCode_Equal_狐陣営() {
        setWinCampCode_Equal_AsCamp(CDef.Camp.狐陣営);
    }

    /**
     * Equal(=). As 恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * 恋人陣営
     */
    public void setWinCampCode_Equal_恋人陣営() {
        setWinCampCode_Equal_AsCamp(CDef.Camp.恋人陣営);
    }

    /**
     * Equal(=). As 村人陣営 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人陣営
     */
    public void setWinCampCode_Equal_村人陣営() {
        setWinCampCode_Equal_AsCamp(CDef.Camp.村人陣営);
    }

    /**
     * Equal(=). As 人狼陣営 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼陣営
     */
    public void setWinCampCode_Equal_人狼陣営() {
        setWinCampCode_Equal_AsCamp(CDef.Camp.人狼陣営);
    }

    protected void doSetWinCampCode_Equal(String winCampCode) {
        regWinCampCode(CK_EQ, winCampCode);
    }

    /**
     * NotEqual(&lt;&gt;). And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     * @param winCampCode The value of winCampCode as notEqual. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setWinCampCode_NotEqual(String winCampCode) {
        doSetWinCampCode_NotEqual(fRES(winCampCode));
    }

    /**
     * NotEqual(&lt;&gt;). As Camp. And NullOrEmptyIgnored, OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp} <br>
     * 陣営
     * @param cdef The instance of classification definition (as ENUM type). (basically NotNull: error as default, or no condition as option)
     */
    public void setWinCampCode_NotEqual_AsCamp(CDef.Camp cdef) {
        doSetWinCampCode_NotEqual(cdef != null ? cdef.code() : null);
    }

    /**
     * NotEqual(&lt;&gt;). As 愉快犯陣営 (CRIMINAL). And OnlyOnceRegistered. <br>
     * 愉快犯陣営
     */
    public void setWinCampCode_NotEqual_愉快犯陣営() {
        setWinCampCode_NotEqual_AsCamp(CDef.Camp.愉快犯陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 狐陣営 (FOX). And OnlyOnceRegistered. <br>
     * 狐陣営
     */
    public void setWinCampCode_NotEqual_狐陣営() {
        setWinCampCode_NotEqual_AsCamp(CDef.Camp.狐陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 恋人陣営 (LOVERS). And OnlyOnceRegistered. <br>
     * 恋人陣営
     */
    public void setWinCampCode_NotEqual_恋人陣営() {
        setWinCampCode_NotEqual_AsCamp(CDef.Camp.恋人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 村人陣営 (VILLAGER). And OnlyOnceRegistered. <br>
     * 村人陣営
     */
    public void setWinCampCode_NotEqual_村人陣営() {
        setWinCampCode_NotEqual_AsCamp(CDef.Camp.村人陣営);
    }

    /**
     * NotEqual(&lt;&gt;). As 人狼陣営 (WEREWOLF). And OnlyOnceRegistered. <br>
     * 人狼陣営
     */
    public void setWinCampCode_NotEqual_人狼陣営() {
        setWinCampCode_NotEqual_AsCamp(CDef.Camp.人狼陣営);
    }

    protected void doSetWinCampCode_NotEqual(String winCampCode) {
        regWinCampCode(CK_NES, winCampCode);
    }

    /**
     * InScope {in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     * @param winCampCodeList The collection of winCampCode as inScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setWinCampCode_InScope(Collection<String> winCampCodeList) {
        doSetWinCampCode_InScope(winCampCodeList);
    }

    /**
     * InScope {in ('a', 'b')}. As Camp. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp} <br>
     * 陣営
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_InScope_AsCamp(Collection<CDef.Camp> cdefList) {
        doSetWinCampCode_InScope(cTStrL(cdefList));
    }

    protected void doSetWinCampCode_InScope(Collection<String> winCampCodeList) {
        regINS(CK_INS, cTL(winCampCodeList), xgetCValueWinCampCode(), "WIN_CAMP_CODE");
    }

    /**
     * NotInScope {not in ('a', 'b')}. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     * @param winCampCodeList The collection of winCampCode as notInScope. (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    protected void setWinCampCode_NotInScope(Collection<String> winCampCodeList) {
        doSetWinCampCode_NotInScope(winCampCodeList);
    }

    /**
     * NotInScope {not in ('a', 'b')}. As Camp. And NullOrEmptyIgnored, NullOrEmptyElementIgnored, SeveralRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp} <br>
     * 陣営
     * @param cdefList The list of classification definition (as ENUM type). (basically NotNull, NotEmpty: error as default, or no condition as option)
     */
    public void setWinCampCode_NotInScope_AsCamp(Collection<CDef.Camp> cdefList) {
        doSetWinCampCode_NotInScope(cTStrL(cdefList));
    }

    protected void doSetWinCampCode_NotInScope(Collection<String> winCampCodeList) {
        regINS(CK_NINS, cTL(winCampCodeList), xgetCValueWinCampCode(), "WIN_CAMP_CODE");
    }

    /**
     * IsNull {is null}. And OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     */
    public void setWinCampCode_IsNull() { regWinCampCode(CK_ISN, DOBJ); }

    /**
     * IsNullOrEmpty {is null or empty}. And OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     */
    public void setWinCampCode_IsNullOrEmpty() { regWinCampCode(CK_ISNOE, DOBJ); }

    /**
     * IsNotNull {is not null}. And OnlyOnceRegistered. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     */
    public void setWinCampCode_IsNotNull() { regWinCampCode(CK_ISNN, DOBJ); }

    protected void regWinCampCode(ConditionKey ky, Object vl) { regQ(ky, vl, xgetCValueWinCampCode(), "WIN_CAMP_CODE"); }
    protected abstract ConditionValue xgetCValueWinCampCode();

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
    public HpSLCFunction<VillageCB> scalar_Equal() {
        return xcreateSLCFunction(CK_EQ, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_NotEqual() {
        return xcreateSLCFunction(CK_NES, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_GreaterThan() {
        return xcreateSLCFunction(CK_GT, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_LessThan() {
        return xcreateSLCFunction(CK_LT, VillageCB.class);
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
    public HpSLCFunction<VillageCB> scalar_GreaterEqual() {
        return xcreateSLCFunction(CK_GE, VillageCB.class);
    }

    /**
     * Prepare ScalarCondition as lessEqual. <br>
     * {where FOO &lt;= (select max(BAR) from ...)}
     * <pre>
     * cb.query().<span style="color: #CC4747">scalar_LessEqual()</span>.max(new SubQuery&lt;VillageCB&gt;() {
     *     public void query(VillageCB subCB) {
     *         subCB.specify().setFoo... <span style="color: #3F7E5E">// derived column for function</span>
     *         subCB.query().setBar...
     *     }
     * });
     * </pre>
     * @return The object to set up a function. (NotNull)
     */
    public HpSLCFunction<VillageCB> scalar_LessEqual() {
        return xcreateSLCFunction(CK_LE, VillageCB.class);
    }

    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xscalarCondition(String fn, SubQuery<CB> sq, String rd, HpSLCCustomized<CB> cs, ScalarConditionOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCB cb = xcreateScalarConditionCB(); sq.query((CB)cb);
        String pp = keepScalarCondition(cb.query()); // for saving query-value
        cs.setPartitionByCBean((CB)xcreateScalarConditionPartitionByCB()); // for using partition-by
        registerScalarCondition(fn, cb.query(), pp, rd, cs, op);
    }
    public abstract String keepScalarCondition(VillageCQ sq);

    protected VillageCB xcreateScalarConditionCB() {
        VillageCB cb = newMyCB(); cb.xsetupForScalarCondition(this); return cb;
    }

    protected VillageCB xcreateScalarConditionPartitionByCB() {
        VillageCB cb = newMyCB(); cb.xsetupForScalarConditionPartitionBy(this); return cb;
    }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public void xsmyselfDerive(String fn, SubQuery<VillageCB> sq, String al, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCB cb = new VillageCB(); cb.xsetupForDerivedReferrer(this);
        lockCall(() -> sq.query(cb)); String pp = keepSpecifyMyselfDerived(cb.query()); String pk = "VILLAGE_ID";
        registerSpecifyMyselfDerived(fn, cb.query(), pk, pk, pp, "myselfDerived", al, op);
    }
    public abstract String keepSpecifyMyselfDerived(VillageCQ sq);

    /**
     * Prepare for (Query)MyselfDerived (correlated sub-query).
     * @return The object to set up a function for myself table. (NotNull)
     */
    public HpQDRFunction<VillageCB> myselfDerived() {
        return xcreateQDRFunctionMyselfDerived(VillageCB.class);
    }
    @SuppressWarnings("unchecked")
    protected <CB extends ConditionBean> void xqderiveMyselfDerived(String fn, SubQuery<CB> sq, String rd, Object vl, DerivedReferrerOption op) {
        assertObjectNotNull("subQuery", sq);
        VillageCB cb = new VillageCB(); cb.xsetupForDerivedReferrer(this); sq.query((CB)cb);
        String pk = "VILLAGE_ID";
        String sqpp = keepQueryMyselfDerived(cb.query()); // for saving query-value.
        String prpp = keepQueryMyselfDerivedParameter(vl);
        registerQueryMyselfDerived(fn, cb.query(), pk, pk, sqpp, "myselfDerived", rd, vl, prpp, op);
    }
    public abstract String keepQueryMyselfDerived(VillageCQ sq);
    public abstract String keepQueryMyselfDerivedParameter(Object vl);

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    /**
     * Prepare for MyselfExists (correlated sub-query).
     * @param subCBLambda The implementation of sub-query. (NotNull)
     */
    public void myselfExists(SubQuery<VillageCB> subCBLambda) {
        assertObjectNotNull("subCBLambda", subCBLambda);
        VillageCB cb = new VillageCB(); cb.xsetupForMyselfExists(this);
        lockCall(() -> subCBLambda.query(cb)); String pp = keepMyselfExists(cb.query());
        registerMyselfExists(cb.query(), pp);
    }
    public abstract String keepMyselfExists(VillageCQ sq);

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
    protected VillageCB newMyCB() {
        return new VillageCB();
    }
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xabUDT() { return Date.class.getName(); }
    protected String xabCQ() { return VillageCQ.class.getName(); }
    protected String xabLSO() { return LikeSearchOption.class.getName(); }
    protected String xabSLCS() { return HpSLCSetupper.class.getName(); }
    protected String xabSCP() { return SubQuery.class.getName(); }
}
