package com.ort.dbflute.cbean.cq.bs;

import java.util.Map;

import org.dbflute.cbean.*;
import org.dbflute.cbean.chelper.*;
import org.dbflute.cbean.coption.*;
import org.dbflute.cbean.cvalue.ConditionValue;
import org.dbflute.cbean.sqlclause.SqlClause;
import org.dbflute.exception.IllegalConditionBeanOperationException;
import com.ort.dbflute.cbean.cq.ciq.*;
import com.ort.dbflute.cbean.*;
import com.ort.dbflute.cbean.cq.*;

/**
 * The base condition-query of VILLAGE.
 * @author DBFlute(AutoGenerator)
 */
public class BsVillageCQ extends AbstractBsVillageCQ {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected VillageCIQ _inlineQuery;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BsVillageCQ(ConditionQuery referrerQuery, SqlClause sqlClause, String aliasName, int nestLevel) {
        super(referrerQuery, sqlClause, aliasName, nestLevel);
    }

    // ===================================================================================
    //                                                                 InlineView/OrClause
    //                                                                 ===================
    /**
     * Prepare InlineView query. <br>
     * {select ... from ... left outer join (select * from VILLAGE) where FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">inline()</span>.setFoo...;
     * </pre>
     * @return The condition-query for InlineView query. (NotNull)
     */
    public VillageCIQ inline() {
        if (_inlineQuery == null) { _inlineQuery = xcreateCIQ(); }
        _inlineQuery.xsetOnClause(false); return _inlineQuery;
    }

    protected VillageCIQ xcreateCIQ() {
        VillageCIQ ciq = xnewCIQ();
        ciq.xsetBaseCB(_baseCB);
        return ciq;
    }

    protected VillageCIQ xnewCIQ() {
        return new VillageCIQ(xgetReferrerQuery(), xgetSqlClause(), xgetAliasName(), xgetNestLevel(), this);
    }

    /**
     * Prepare OnClause query. <br>
     * {select ... from ... left outer join VILLAGE on ... and FOO = [value] ...}
     * <pre>
     * cb.query().queryMemberStatus().<span style="color: #CC4747">on()</span>.setFoo...;
     * </pre>
     * @return The condition-query for OnClause query. (NotNull)
     * @throws IllegalConditionBeanOperationException When this condition-query is base query.
     */
    public VillageCIQ on() {
        if (isBaseQuery()) { throw new IllegalConditionBeanOperationException("OnClause for local table is unavailable!"); }
        VillageCIQ inlineQuery = inline(); inlineQuery.xsetOnClause(true); return inlineQuery;
    }

    // ===================================================================================
    //                                                                               Query
    //                                                                               =====
    protected ConditionValue _villageId;
    public ConditionValue xdfgetVillageId()
    { if (_villageId == null) { _villageId = nCV(); }
      return _villageId; }
    protected ConditionValue xgetCValueVillageId() { return xdfgetVillageId(); }

    public Map<String, CampAllocationCQ> xdfgetVillageId_ExistsReferrer_CampAllocationList() { return xgetSQueMap("villageId_ExistsReferrer_CampAllocationList"); }
    public String keepVillageId_ExistsReferrer_CampAllocationList(CampAllocationCQ sq) { return xkeepSQue("villageId_ExistsReferrer_CampAllocationList", sq); }

    public Map<String, NormalSayRestrictionCQ> xdfgetVillageId_ExistsReferrer_NormalSayRestrictionList() { return xgetSQueMap("villageId_ExistsReferrer_NormalSayRestrictionList"); }
    public String keepVillageId_ExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("villageId_ExistsReferrer_NormalSayRestrictionList", sq); }

    public Map<String, SkillAllocationCQ> xdfgetVillageId_ExistsReferrer_SkillAllocationList() { return xgetSQueMap("villageId_ExistsReferrer_SkillAllocationList"); }
    public String keepVillageId_ExistsReferrer_SkillAllocationList(SkillAllocationCQ sq) { return xkeepSQue("villageId_ExistsReferrer_SkillAllocationList", sq); }

    public Map<String, SkillSayRestrictionCQ> xdfgetVillageId_ExistsReferrer_SkillSayRestrictionList() { return xgetSQueMap("villageId_ExistsReferrer_SkillSayRestrictionList"); }
    public String keepVillageId_ExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("villageId_ExistsReferrer_SkillSayRestrictionList", sq); }

    public Map<String, VillageCharaGroupCQ> xdfgetVillageId_ExistsReferrer_VillageCharaGroupList() { return xgetSQueMap("villageId_ExistsReferrer_VillageCharaGroupList"); }
    public String keepVillageId_ExistsReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq) { return xkeepSQue("villageId_ExistsReferrer_VillageCharaGroupList", sq); }

    public Map<String, VillageDayCQ> xdfgetVillageId_ExistsReferrer_VillageDayList() { return xgetSQueMap("villageId_ExistsReferrer_VillageDayList"); }
    public String keepVillageId_ExistsReferrer_VillageDayList(VillageDayCQ sq) { return xkeepSQue("villageId_ExistsReferrer_VillageDayList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetVillageId_ExistsReferrer_VillagePlayerList() { return xgetSQueMap("villageId_ExistsReferrer_VillagePlayerList"); }
    public String keepVillageId_ExistsReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("villageId_ExistsReferrer_VillagePlayerList", sq); }

    public Map<String, VillageTagCQ> xdfgetVillageId_ExistsReferrer_VillageTagList() { return xgetSQueMap("villageId_ExistsReferrer_VillageTagList"); }
    public String keepVillageId_ExistsReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageId_ExistsReferrer_VillageTagList", sq); }

    public Map<String, CampAllocationCQ> xdfgetVillageId_NotExistsReferrer_CampAllocationList() { return xgetSQueMap("villageId_NotExistsReferrer_CampAllocationList"); }
    public String keepVillageId_NotExistsReferrer_CampAllocationList(CampAllocationCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_CampAllocationList", sq); }

    public Map<String, NormalSayRestrictionCQ> xdfgetVillageId_NotExistsReferrer_NormalSayRestrictionList() { return xgetSQueMap("villageId_NotExistsReferrer_NormalSayRestrictionList"); }
    public String keepVillageId_NotExistsReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_NormalSayRestrictionList", sq); }

    public Map<String, SkillAllocationCQ> xdfgetVillageId_NotExistsReferrer_SkillAllocationList() { return xgetSQueMap("villageId_NotExistsReferrer_SkillAllocationList"); }
    public String keepVillageId_NotExistsReferrer_SkillAllocationList(SkillAllocationCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_SkillAllocationList", sq); }

    public Map<String, SkillSayRestrictionCQ> xdfgetVillageId_NotExistsReferrer_SkillSayRestrictionList() { return xgetSQueMap("villageId_NotExistsReferrer_SkillSayRestrictionList"); }
    public String keepVillageId_NotExistsReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_SkillSayRestrictionList", sq); }

    public Map<String, VillageCharaGroupCQ> xdfgetVillageId_NotExistsReferrer_VillageCharaGroupList() { return xgetSQueMap("villageId_NotExistsReferrer_VillageCharaGroupList"); }
    public String keepVillageId_NotExistsReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_VillageCharaGroupList", sq); }

    public Map<String, VillageDayCQ> xdfgetVillageId_NotExistsReferrer_VillageDayList() { return xgetSQueMap("villageId_NotExistsReferrer_VillageDayList"); }
    public String keepVillageId_NotExistsReferrer_VillageDayList(VillageDayCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_VillageDayList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetVillageId_NotExistsReferrer_VillagePlayerList() { return xgetSQueMap("villageId_NotExistsReferrer_VillagePlayerList"); }
    public String keepVillageId_NotExistsReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_VillagePlayerList", sq); }

    public Map<String, VillageTagCQ> xdfgetVillageId_NotExistsReferrer_VillageTagList() { return xgetSQueMap("villageId_NotExistsReferrer_VillageTagList"); }
    public String keepVillageId_NotExistsReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageId_NotExistsReferrer_VillageTagList", sq); }

    public Map<String, CampAllocationCQ> xdfgetVillageId_SpecifyDerivedReferrer_CampAllocationList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_CampAllocationList"); }
    public String keepVillageId_SpecifyDerivedReferrer_CampAllocationList(CampAllocationCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_CampAllocationList", sq); }

    public Map<String, NormalSayRestrictionCQ> xdfgetVillageId_SpecifyDerivedReferrer_NormalSayRestrictionList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_NormalSayRestrictionList"); }
    public String keepVillageId_SpecifyDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_NormalSayRestrictionList", sq); }

    public Map<String, SkillAllocationCQ> xdfgetVillageId_SpecifyDerivedReferrer_SkillAllocationList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_SkillAllocationList"); }
    public String keepVillageId_SpecifyDerivedReferrer_SkillAllocationList(SkillAllocationCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_SkillAllocationList", sq); }

    public Map<String, SkillSayRestrictionCQ> xdfgetVillageId_SpecifyDerivedReferrer_SkillSayRestrictionList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_SkillSayRestrictionList"); }
    public String keepVillageId_SpecifyDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_SkillSayRestrictionList", sq); }

    public Map<String, VillageCharaGroupCQ> xdfgetVillageId_SpecifyDerivedReferrer_VillageCharaGroupList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_VillageCharaGroupList"); }
    public String keepVillageId_SpecifyDerivedReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_VillageCharaGroupList", sq); }

    public Map<String, VillageDayCQ> xdfgetVillageId_SpecifyDerivedReferrer_VillageDayList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_VillageDayList"); }
    public String keepVillageId_SpecifyDerivedReferrer_VillageDayList(VillageDayCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_VillageDayList", sq); }

    public Map<String, VillagePlayerCQ> xdfgetVillageId_SpecifyDerivedReferrer_VillagePlayerList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_VillagePlayerList"); }
    public String keepVillageId_SpecifyDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_VillagePlayerList", sq); }

    public Map<String, VillageTagCQ> xdfgetVillageId_SpecifyDerivedReferrer_VillageTagList() { return xgetSQueMap("villageId_SpecifyDerivedReferrer_VillageTagList"); }
    public String keepVillageId_SpecifyDerivedReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageId_SpecifyDerivedReferrer_VillageTagList", sq); }

    public Map<String, CampAllocationCQ> xdfgetVillageId_QueryDerivedReferrer_CampAllocationList() { return xgetSQueMap("villageId_QueryDerivedReferrer_CampAllocationList"); }
    public String keepVillageId_QueryDerivedReferrer_CampAllocationList(CampAllocationCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_CampAllocationList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_CampAllocationListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_CampAllocationList"); }
    public String keepVillageId_QueryDerivedReferrer_CampAllocationListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_CampAllocationList", pm); }

    public Map<String, NormalSayRestrictionCQ> xdfgetVillageId_QueryDerivedReferrer_NormalSayRestrictionList() { return xgetSQueMap("villageId_QueryDerivedReferrer_NormalSayRestrictionList"); }
    public String keepVillageId_QueryDerivedReferrer_NormalSayRestrictionList(NormalSayRestrictionCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_NormalSayRestrictionList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_NormalSayRestrictionListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_NormalSayRestrictionList"); }
    public String keepVillageId_QueryDerivedReferrer_NormalSayRestrictionListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_NormalSayRestrictionList", pm); }

    public Map<String, SkillAllocationCQ> xdfgetVillageId_QueryDerivedReferrer_SkillAllocationList() { return xgetSQueMap("villageId_QueryDerivedReferrer_SkillAllocationList"); }
    public String keepVillageId_QueryDerivedReferrer_SkillAllocationList(SkillAllocationCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_SkillAllocationList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_SkillAllocationListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_SkillAllocationList"); }
    public String keepVillageId_QueryDerivedReferrer_SkillAllocationListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_SkillAllocationList", pm); }

    public Map<String, SkillSayRestrictionCQ> xdfgetVillageId_QueryDerivedReferrer_SkillSayRestrictionList() { return xgetSQueMap("villageId_QueryDerivedReferrer_SkillSayRestrictionList"); }
    public String keepVillageId_QueryDerivedReferrer_SkillSayRestrictionList(SkillSayRestrictionCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_SkillSayRestrictionList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_SkillSayRestrictionListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_SkillSayRestrictionList"); }
    public String keepVillageId_QueryDerivedReferrer_SkillSayRestrictionListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_SkillSayRestrictionList", pm); }

    public Map<String, VillageCharaGroupCQ> xdfgetVillageId_QueryDerivedReferrer_VillageCharaGroupList() { return xgetSQueMap("villageId_QueryDerivedReferrer_VillageCharaGroupList"); }
    public String keepVillageId_QueryDerivedReferrer_VillageCharaGroupList(VillageCharaGroupCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_VillageCharaGroupList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_VillageCharaGroupListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_VillageCharaGroupList"); }
    public String keepVillageId_QueryDerivedReferrer_VillageCharaGroupListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_VillageCharaGroupList", pm); }

    public Map<String, VillageDayCQ> xdfgetVillageId_QueryDerivedReferrer_VillageDayList() { return xgetSQueMap("villageId_QueryDerivedReferrer_VillageDayList"); }
    public String keepVillageId_QueryDerivedReferrer_VillageDayList(VillageDayCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_VillageDayList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_VillageDayListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_VillageDayList"); }
    public String keepVillageId_QueryDerivedReferrer_VillageDayListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_VillageDayList", pm); }

    public Map<String, VillagePlayerCQ> xdfgetVillageId_QueryDerivedReferrer_VillagePlayerList() { return xgetSQueMap("villageId_QueryDerivedReferrer_VillagePlayerList"); }
    public String keepVillageId_QueryDerivedReferrer_VillagePlayerList(VillagePlayerCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_VillagePlayerList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_VillagePlayerListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_VillagePlayerList"); }
    public String keepVillageId_QueryDerivedReferrer_VillagePlayerListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_VillagePlayerList", pm); }

    public Map<String, VillageTagCQ> xdfgetVillageId_QueryDerivedReferrer_VillageTagList() { return xgetSQueMap("villageId_QueryDerivedReferrer_VillageTagList"); }
    public String keepVillageId_QueryDerivedReferrer_VillageTagList(VillageTagCQ sq) { return xkeepSQue("villageId_QueryDerivedReferrer_VillageTagList", sq); }
    public Map<String, Object> xdfgetVillageId_QueryDerivedReferrer_VillageTagListParameter() { return xgetSQuePmMap("villageId_QueryDerivedReferrer_VillageTagList"); }
    public String keepVillageId_QueryDerivedReferrer_VillageTagListParameter(Object pm) { return xkeepSQuePm("villageId_QueryDerivedReferrer_VillageTagList", pm); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageId_Asc() { regOBA("VILLAGE_ID"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageId_Desc() { regOBD("VILLAGE_ID"); return this; }

    protected ConditionValue _villageDisplayName;
    public ConditionValue xdfgetVillageDisplayName()
    { if (_villageDisplayName == null) { _villageDisplayName = nCV(); }
      return _villageDisplayName; }
    protected ConditionValue xgetCValueVillageDisplayName() { return xdfgetVillageDisplayName(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageDisplayName_Asc() { regOBA("VILLAGE_DISPLAY_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageDisplayName_Desc() { regOBD("VILLAGE_DISPLAY_NAME"); return this; }

    protected ConditionValue _createPlayerName;
    public ConditionValue xdfgetCreatePlayerName()
    { if (_createPlayerName == null) { _createPlayerName = nCV(); }
      return _createPlayerName; }
    protected ConditionValue xgetCValueCreatePlayerName() { return xdfgetCreatePlayerName(); }

    /**
     * Add order-by as ascend. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_CreatePlayerName_Asc() { regOBA("CREATE_PLAYER_NAME"); return this; }

    /**
     * Add order-by as descend. <br>
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_CreatePlayerName_Desc() { regOBD("CREATE_PLAYER_NAME"); return this; }

    protected ConditionValue _villageStatusCode;
    public ConditionValue xdfgetVillageStatusCode()
    { if (_villageStatusCode == null) { _villageStatusCode = nCV(); }
      return _villageStatusCode; }
    protected ConditionValue xgetCValueVillageStatusCode() { return xdfgetVillageStatusCode(); }

    /**
     * Add order-by as ascend. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageStatusCode_Asc() { regOBA("VILLAGE_STATUS_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_VillageStatusCode_Desc() { regOBD("VILLAGE_STATUS_CODE"); return this; }

    protected ConditionValue _roomSizeWidth;
    public ConditionValue xdfgetRoomSizeWidth()
    { if (_roomSizeWidth == null) { _roomSizeWidth = nCV(); }
      return _roomSizeWidth; }
    protected ConditionValue xgetCValueRoomSizeWidth() { return xdfgetRoomSizeWidth(); }

    /**
     * Add order-by as ascend. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RoomSizeWidth_Asc() { regOBA("ROOM_SIZE_WIDTH"); return this; }

    /**
     * Add order-by as descend. <br>
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RoomSizeWidth_Desc() { regOBD("ROOM_SIZE_WIDTH"); return this; }

    protected ConditionValue _roomSizeHeight;
    public ConditionValue xdfgetRoomSizeHeight()
    { if (_roomSizeHeight == null) { _roomSizeHeight = nCV(); }
      return _roomSizeHeight; }
    protected ConditionValue xgetCValueRoomSizeHeight() { return xdfgetRoomSizeHeight(); }

    /**
     * Add order-by as ascend. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RoomSizeHeight_Asc() { regOBA("ROOM_SIZE_HEIGHT"); return this; }

    /**
     * Add order-by as descend. <br>
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RoomSizeHeight_Desc() { regOBD("ROOM_SIZE_HEIGHT"); return this; }

    protected ConditionValue _epilogueDay;
    public ConditionValue xdfgetEpilogueDay()
    { if (_epilogueDay == null) { _epilogueDay = nCV(); }
      return _epilogueDay; }
    protected ConditionValue xgetCValueEpilogueDay() { return xdfgetEpilogueDay(); }

    /**
     * Add order-by as ascend. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_EpilogueDay_Asc() { regOBA("EPILOGUE_DAY"); return this; }

    /**
     * Add order-by as descend. <br>
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_EpilogueDay_Desc() { regOBD("EPILOGUE_DAY"); return this; }

    protected ConditionValue _winCampCode;
    public ConditionValue xdfgetWinCampCode()
    { if (_winCampCode == null) { _winCampCode = nCV(); }
      return _winCampCode; }
    protected ConditionValue xgetCValueWinCampCode() { return xdfgetWinCampCode(); }

    /**
     * Add order-by as ascend. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_WinCampCode_Asc() { regOBA("WIN_CAMP_CODE"); return this; }

    /**
     * Add order-by as descend. <br>
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_WinCampCode_Desc() { regOBD("WIN_CAMP_CODE"); return this; }

    protected ConditionValue _registerDatetime;
    public ConditionValue xdfgetRegisterDatetime()
    { if (_registerDatetime == null) { _registerDatetime = nCV(); }
      return _registerDatetime; }
    protected ConditionValue xgetCValueRegisterDatetime() { return xdfgetRegisterDatetime(); }

    /**
     * Add order-by as ascend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RegisterDatetime_Asc() { regOBA("REGISTER_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RegisterDatetime_Desc() { regOBD("REGISTER_DATETIME"); return this; }

    protected ConditionValue _registerTrace;
    public ConditionValue xdfgetRegisterTrace()
    { if (_registerTrace == null) { _registerTrace = nCV(); }
      return _registerTrace; }
    protected ConditionValue xgetCValueRegisterTrace() { return xdfgetRegisterTrace(); }

    /**
     * Add order-by as ascend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RegisterTrace_Asc() { regOBA("REGISTER_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_RegisterTrace_Desc() { regOBD("REGISTER_TRACE"); return this; }

    protected ConditionValue _updateDatetime;
    public ConditionValue xdfgetUpdateDatetime()
    { if (_updateDatetime == null) { _updateDatetime = nCV(); }
      return _updateDatetime; }
    protected ConditionValue xgetCValueUpdateDatetime() { return xdfgetUpdateDatetime(); }

    /**
     * Add order-by as ascend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_UpdateDatetime_Asc() { regOBA("UPDATE_DATETIME"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_UpdateDatetime_Desc() { regOBD("UPDATE_DATETIME"); return this; }

    protected ConditionValue _updateTrace;
    public ConditionValue xdfgetUpdateTrace()
    { if (_updateTrace == null) { _updateTrace = nCV(); }
      return _updateTrace; }
    protected ConditionValue xgetCValueUpdateTrace() { return xdfgetUpdateTrace(); }

    /**
     * Add order-by as ascend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_UpdateTrace_Asc() { regOBA("UPDATE_TRACE"); return this; }

    /**
     * Add order-by as descend. <br>
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return this. (NotNull)
     */
    public BsVillageCQ addOrderBy_UpdateTrace_Desc() { regOBD("UPDATE_TRACE"); return this; }

    // ===================================================================================
    //                                                             SpecifiedDerivedOrderBy
    //                                                             =======================
    /**
     * Add order-by for specified derived column as ascend.
     * <pre>
     * cb.specify().derivedPurchaseList().max(new SubQuery&lt;PurchaseCB&gt;() {
     *     public void query(PurchaseCB subCB) {
     *         subCB.specify().columnPurchaseDatetime();
     *     }
     * }, <span style="color: #CC4747">aliasName</span>);
     * <span style="color: #3F7E5E">// order by [alias-name] asc</span>
     * cb.<span style="color: #CC4747">addSpecifiedDerivedOrderBy_Asc</span>(<span style="color: #CC4747">aliasName</span>);
     * </pre>
     * @param aliasName The alias name specified at (Specify)DerivedReferrer. (NotNull)
     * @return this. (NotNull)
     */
    public BsVillageCQ addSpecifiedDerivedOrderBy_Asc(String aliasName) { registerSpecifiedDerivedOrderBy_Asc(aliasName); return this; }

    /**
     * Add order-by for specified derived column as descend.
     * <pre>
     * cb.specify().derivedPurchaseList().max(new SubQuery&lt;PurchaseCB&gt;() {
     *     public void query(PurchaseCB subCB) {
     *         subCB.specify().columnPurchaseDatetime();
     *     }
     * }, <span style="color: #CC4747">aliasName</span>);
     * <span style="color: #3F7E5E">// order by [alias-name] desc</span>
     * cb.<span style="color: #CC4747">addSpecifiedDerivedOrderBy_Desc</span>(<span style="color: #CC4747">aliasName</span>);
     * </pre>
     * @param aliasName The alias name specified at (Specify)DerivedReferrer. (NotNull)
     * @return this. (NotNull)
     */
    public BsVillageCQ addSpecifiedDerivedOrderBy_Desc(String aliasName) { registerSpecifiedDerivedOrderBy_Desc(aliasName); return this; }

    // ===================================================================================
    //                                                                         Union Query
    //                                                                         ===========
    public void reflectRelationOnUnionQuery(ConditionQuery bqs, ConditionQuery uqs) {
        VillageCQ bq = (VillageCQ)bqs;
        VillageCQ uq = (VillageCQ)uqs;
        if (bq.hasConditionQueryVillageStatus()) {
            uq.queryVillageStatus().reflectRelationOnUnionQuery(bq.queryVillageStatus(), uq.queryVillageStatus());
        }
        if (bq.hasConditionQueryCamp()) {
            uq.queryCamp().reflectRelationOnUnionQuery(bq.queryCamp(), uq.queryCamp());
        }
        if (bq.hasConditionQueryVillageSettingsAsOne()) {
            uq.queryVillageSettingsAsOne().reflectRelationOnUnionQuery(bq.queryVillageSettingsAsOne(), uq.queryVillageSettingsAsOne());
        }
        if (bq.hasConditionQueryWolfAllocationAsOne()) {
            uq.queryWolfAllocationAsOne().reflectRelationOnUnionQuery(bq.queryWolfAllocationAsOne(), uq.queryWolfAllocationAsOne());
        }
    }

    // ===================================================================================
    //                                                                       Foreign Query
    //                                                                       =============
    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE_STATUS by my VILLAGE_STATUS_CODE, named 'villageStatus'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillageStatusCQ queryVillageStatus() {
        return xdfgetConditionQueryVillageStatus();
    }
    public VillageStatusCQ xdfgetConditionQueryVillageStatus() {
        String prop = "villageStatus";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillageStatus()); xsetupOuterJoinVillageStatus(); }
        return xgetQueRlMap(prop);
    }
    protected VillageStatusCQ xcreateQueryVillageStatus() {
        String nrp = xresolveNRP("VILLAGE", "villageStatus"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillageStatusCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villageStatus", nrp);
    }
    protected void xsetupOuterJoinVillageStatus() { xregOutJo("villageStatus"); }
    public boolean hasConditionQueryVillageStatus() { return xhasQueRlMap("villageStatus"); }

    /**
     * Get the condition-query for relation table. <br>
     * CAMP by my WIN_CAMP_CODE, named 'camp'.
     * @return The instance of condition-query. (NotNull)
     */
    public CampCQ queryCamp() {
        return xdfgetConditionQueryCamp();
    }
    public CampCQ xdfgetConditionQueryCamp() {
        String prop = "camp";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryCamp()); xsetupOuterJoinCamp(); }
        return xgetQueRlMap(prop);
    }
    protected CampCQ xcreateQueryCamp() {
        String nrp = xresolveNRP("VILLAGE", "camp"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new CampCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "camp", nrp);
    }
    protected void xsetupOuterJoinCamp() { xregOutJo("camp"); }
    public boolean hasConditionQueryCamp() { return xhasQueRlMap("camp"); }

    /**
     * Get the condition-query for relation table. <br>
     * VILLAGE_SETTINGS by VILLAGE_ID, named 'villageSettingsAsOne'.
     * @return The instance of condition-query. (NotNull)
     */
    public VillageSettingsCQ queryVillageSettingsAsOne() { return xdfgetConditionQueryVillageSettingsAsOne(); }
    public VillageSettingsCQ xdfgetConditionQueryVillageSettingsAsOne() {
        String prop = "villageSettingsAsOne";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryVillageSettingsAsOne()); xsetupOuterJoinVillageSettingsAsOne(); }
        return xgetQueRlMap(prop);
    }
    protected VillageSettingsCQ xcreateQueryVillageSettingsAsOne() {
        String nrp = xresolveNRP("VILLAGE", "villageSettingsAsOne"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new VillageSettingsCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "villageSettingsAsOne", nrp);
    }
    protected void xsetupOuterJoinVillageSettingsAsOne() { xregOutJo("villageSettingsAsOne"); }
    public boolean hasConditionQueryVillageSettingsAsOne() { return xhasQueRlMap("villageSettingsAsOne"); }

    /**
     * Get the condition-query for relation table. <br>
     * WOLF_ALLOCATION by VILLAGE_ID, named 'wolfAllocationAsOne'.
     * @return The instance of condition-query. (NotNull)
     */
    public WolfAllocationCQ queryWolfAllocationAsOne() { return xdfgetConditionQueryWolfAllocationAsOne(); }
    public WolfAllocationCQ xdfgetConditionQueryWolfAllocationAsOne() {
        String prop = "wolfAllocationAsOne";
        if (!xhasQueRlMap(prop)) { xregQueRl(prop, xcreateQueryWolfAllocationAsOne()); xsetupOuterJoinWolfAllocationAsOne(); }
        return xgetQueRlMap(prop);
    }
    protected WolfAllocationCQ xcreateQueryWolfAllocationAsOne() {
        String nrp = xresolveNRP("VILLAGE", "wolfAllocationAsOne"); String jan = xresolveJAN(nrp, xgetNNLvl());
        return xinitRelCQ(new WolfAllocationCQ(this, xgetSqlClause(), jan, xgetNNLvl()), _baseCB, "wolfAllocationAsOne", nrp);
    }
    protected void xsetupOuterJoinWolfAllocationAsOne() { xregOutJo("wolfAllocationAsOne"); }
    public boolean hasConditionQueryWolfAllocationAsOne() { return xhasQueRlMap("wolfAllocationAsOne"); }

    protected Map<String, Object> xfindFixedConditionDynamicParameterMap(String property) {
        return null;
    }

    // ===================================================================================
    //                                                                     ScalarCondition
    //                                                                     ===============
    public Map<String, VillageCQ> xdfgetScalarCondition() { return xgetSQueMap("scalarCondition"); }
    public String keepScalarCondition(VillageCQ sq) { return xkeepSQue("scalarCondition", sq); }

    // ===================================================================================
    //                                                                       MyselfDerived
    //                                                                       =============
    public Map<String, VillageCQ> xdfgetSpecifyMyselfDerived() { return xgetSQueMap("specifyMyselfDerived"); }
    public String keepSpecifyMyselfDerived(VillageCQ sq) { return xkeepSQue("specifyMyselfDerived", sq); }

    public Map<String, VillageCQ> xdfgetQueryMyselfDerived() { return xgetSQueMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerived(VillageCQ sq) { return xkeepSQue("queryMyselfDerived", sq); }
    public Map<String, Object> xdfgetQueryMyselfDerivedParameter() { return xgetSQuePmMap("queryMyselfDerived"); }
    public String keepQueryMyselfDerivedParameter(Object pm) { return xkeepSQuePm("queryMyselfDerived", pm); }

    // ===================================================================================
    //                                                                        MyselfExists
    //                                                                        ============
    protected Map<String, VillageCQ> _myselfExistsMap;
    public Map<String, VillageCQ> xdfgetMyselfExists() { return xgetSQueMap("myselfExists"); }
    public String keepMyselfExists(VillageCQ sq) { return xkeepSQue("myselfExists", sq); }

    // ===================================================================================
    //                                                                       MyselfInScope
    //                                                                       =============
    public Map<String, VillageCQ> xdfgetMyselfInScope() { return xgetSQueMap("myselfInScope"); }
    public String keepMyselfInScope(VillageCQ sq) { return xkeepSQue("myselfInScope", sq); }

    // ===================================================================================
    //                                                                       Very Internal
    //                                                                       =============
    // very internal (for suppressing warn about 'Not Use Import')
    protected String xCB() { return VillageCB.class.getName(); }
    protected String xCQ() { return VillageCQ.class.getName(); }
    protected String xCHp() { return HpQDRFunction.class.getName(); }
    protected String xCOp() { return ConditionOption.class.getName(); }
    protected String xMap() { return Map.class.getName(); }
}
