package com.ort.dbflute.bsentity.dbmeta;

import java.util.List;
import java.util.Map;

import org.dbflute.Entity;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.exentity.*;

/**
 * The DB meta of VILLAGE. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillageDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillageDbm _instance = new VillageDbm();
    private VillageDbm() {}
    public static VillageDbm getInstance() { return _instance; }

    // ===================================================================================
    //                                                                       Current DBDef
    //                                                                       =============
    public String getProjectName() { return DBCurrent.getInstance().projectName(); }
    public String getProjectPrefix() { return DBCurrent.getInstance().projectPrefix(); }
    public String getGenerationGapBasePrefix() { return DBCurrent.getInstance().generationGapBasePrefix(); }
    public DBDef getCurrentDBDef() { return DBCurrent.getInstance().currentDBDef(); }

    // ===================================================================================
    //                                                                    Property Gateway
    //                                                                    ================
    // -----------------------------------------------------
    //                                       Column Property
    //                                       ---------------
    protected final Map<String, PropertyGateway> _epgMap = newHashMap();
    { xsetupEpg(); }
    protected void xsetupEpg() {
        setupEpg(_epgMap, et -> ((Village)et).getVillageId(), (et, vl) -> ((Village)et).setVillageId(cti(vl)), "villageId");
        setupEpg(_epgMap, et -> ((Village)et).getVillageDisplayName(), (et, vl) -> ((Village)et).setVillageDisplayName((String)vl), "villageDisplayName");
        setupEpg(_epgMap, et -> ((Village)et).getCreatePlayerName(), (et, vl) -> ((Village)et).setCreatePlayerName((String)vl), "createPlayerName");
        setupEpg(_epgMap, et -> ((Village)et).getVillageStatusCode(), (et, vl) -> {
            CDef.VillageStatus cls = (CDef.VillageStatus)gcls(et, columnVillageStatusCode(), vl);
            if (cls != null) {
                ((Village)et).setVillageStatusCodeAsVillageStatus(cls);
            } else {
                ((Village)et).mynativeMappingVillageStatusCode((String)vl);
            }
        }, "villageStatusCode");
        setupEpg(_epgMap, et -> ((Village)et).getRoomSizeWidth(), (et, vl) -> ((Village)et).setRoomSizeWidth(cti(vl)), "roomSizeWidth");
        setupEpg(_epgMap, et -> ((Village)et).getRoomSizeHeight(), (et, vl) -> ((Village)et).setRoomSizeHeight(cti(vl)), "roomSizeHeight");
        setupEpg(_epgMap, et -> ((Village)et).getEpilogueDay(), (et, vl) -> ((Village)et).setEpilogueDay(cti(vl)), "epilogueDay");
        setupEpg(_epgMap, et -> ((Village)et).getWinCampCode(), (et, vl) -> {
            CDef.Camp cls = (CDef.Camp)gcls(et, columnWinCampCode(), vl);
            if (cls != null) {
                ((Village)et).setWinCampCodeAsCamp(cls);
            } else {
                ((Village)et).mynativeMappingWinCampCode((String)vl);
            }
        }, "winCampCode");
        setupEpg(_epgMap, et -> ((Village)et).getRegisterDatetime(), (et, vl) -> ((Village)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((Village)et).getRegisterTrace(), (et, vl) -> ((Village)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((Village)et).getUpdateDatetime(), (et, vl) -> ((Village)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((Village)et).getUpdateTrace(), (et, vl) -> ((Village)et).setUpdateTrace((String)vl), "updateTrace");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    protected final Map<String, PropertyGateway> _efpgMap = newHashMap();
    { xsetupEfpg(); }
    @SuppressWarnings("unchecked")
    protected void xsetupEfpg() {
        setupEfpg(_efpgMap, et -> ((Village)et).getVillageStatus(), (et, vl) -> ((Village)et).setVillageStatus((OptionalEntity<VillageStatus>)vl), "villageStatus");
        setupEfpg(_efpgMap, et -> ((Village)et).getCamp(), (et, vl) -> ((Village)et).setCamp((OptionalEntity<Camp>)vl), "camp");
        setupEfpg(_efpgMap, et -> ((Village)et).getVillageSettingsAsOne(), (et, vl) -> ((Village)et).setVillageSettingsAsOne((OptionalEntity<VillageSettings>)vl), "villageSettingsAsOne");
        setupEfpg(_efpgMap, et -> ((Village)et).getWolfAllocationAsOne(), (et, vl) -> ((Village)et).setWolfAllocationAsOne((OptionalEntity<WolfAllocation>)vl), "wolfAllocationAsOne");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VILLAGE";
    protected final String _tableDispName = "VILLAGE";
    protected final String _tablePropertyName = "village";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, "campAllocationList,normalSayRestrictionList,skillAllocationList,skillSayRestrictionList,villageCharaGroupList,villageDayList,villagePlayerList,villageTagList", null, false);
    protected final ColumnInfo _columnVillageDisplayName = cci("VILLAGE_DISPLAY_NAME", "VILLAGE_DISPLAY_NAME", null, null, String.class, "villageDisplayName", null, false, false, true, "VARCHAR", 40, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnCreatePlayerName = cci("CREATE_PLAYER_NAME", "CREATE_PLAYER_NAME", null, null, String.class, "createPlayerName", null, false, false, true, "VARCHAR", 12, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillageStatusCode = cci("VILLAGE_STATUS_CODE", "VILLAGE_STATUS_CODE", null, null, String.class, "villageStatusCode", null, false, false, true, "VARCHAR", 20, 0, null, null, false, null, null, "villageStatus", null, CDef.DefMeta.VillageStatus, false);
    protected final ColumnInfo _columnRoomSizeWidth = cci("ROOM_SIZE_WIDTH", "ROOM_SIZE_WIDTH", null, null, Integer.class, "roomSizeWidth", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRoomSizeHeight = cci("ROOM_SIZE_HEIGHT", "ROOM_SIZE_HEIGHT", null, null, Integer.class, "roomSizeHeight", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnEpilogueDay = cci("EPILOGUE_DAY", "EPILOGUE_DAY", null, null, Integer.class, "epilogueDay", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnWinCampCode = cci("WIN_CAMP_CODE", "WIN_CAMP_CODE", null, null, String.class, "winCampCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "camp", null, CDef.DefMeta.Camp, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageDisplayName() { return _columnVillageDisplayName; }
    /**
     * CREATE_PLAYER_NAME: {NotNull, VARCHAR(12)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCreatePlayerName() { return _columnCreatePlayerName; }
    /**
     * VILLAGE_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_STATUS, classification=VillageStatus}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageStatusCode() { return _columnVillageStatusCode; }
    /**
     * ROOM_SIZE_WIDTH: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRoomSizeWidth() { return _columnRoomSizeWidth; }
    /**
     * ROOM_SIZE_HEIGHT: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRoomSizeHeight() { return _columnRoomSizeHeight; }
    /**
     * EPILOGUE_DAY: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnEpilogueDay() { return _columnEpilogueDay; }
    /**
     * WIN_CAMP_CODE: {IX, VARCHAR(20), FK to CAMP, classification=Camp}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnWinCampCode() { return _columnWinCampCode; }
    /**
     * REGISTER_DATETIME: {NotNull, DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRegisterDatetime() { return _columnRegisterDatetime; }
    /**
     * REGISTER_TRACE: {NotNull, VARCHAR(64)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRegisterTrace() { return _columnRegisterTrace; }
    /**
     * UPDATE_DATETIME: {NotNull, DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnUpdateDatetime() { return _columnUpdateDatetime; }
    /**
     * UPDATE_TRACE: {NotNull, VARCHAR(64)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnUpdateTrace() { return _columnUpdateTrace; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnVillageId());
        ls.add(columnVillageDisplayName());
        ls.add(columnCreatePlayerName());
        ls.add(columnVillageStatusCode());
        ls.add(columnRoomSizeWidth());
        ls.add(columnRoomSizeHeight());
        ls.add(columnEpilogueDay());
        ls.add(columnWinCampCode());
        ls.add(columnRegisterDatetime());
        ls.add(columnRegisterTrace());
        ls.add(columnUpdateDatetime());
        ls.add(columnUpdateTrace());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnVillageId()); }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // ===================================================================================
    //                                                                       Relation Info
    //                                                                       =============
    // cannot cache because it uses related DB meta instance while booting
    // (instead, cached by super's collection)
    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    /**
     * VILLAGE_STATUS by my VILLAGE_STATUS_CODE, named 'villageStatus'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillageStatus() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageStatusCode(), VillageStatusDbm.getInstance().columnVillageStatusCode());
        return cfi("FK_VILLAGE_VILLAGE_STATUS", "villageStatus", this, VillageStatusDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villageList", false);
    }
    /**
     * CAMP by my WIN_CAMP_CODE, named 'camp'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignCamp() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnWinCampCode(), CampDbm.getInstance().columnCampCode());
        return cfi("FK_VILLAGE_CAMP", "camp", this, CampDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villageList", false);
    }
    /**
     * VILLAGE_SETTINGS by VILLAGE_ID, named 'villageSettingsAsOne'.
     * @return The information object of foreign property(referrer-as-one). (NotNull)
     */
    public ForeignInfo foreignVillageSettingsAsOne() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageSettingsDbm.getInstance().columnVillageId());
        return cfi("FK_VILLAGE_SETTINGS_VILLAGE", "villageSettingsAsOne", this, VillageSettingsDbm.getInstance(), mp, 2, org.dbflute.optional.OptionalEntity.class, true, false, true, false, null, null, false, "village", false);
    }
    /**
     * WOLF_ALLOCATION by VILLAGE_ID, named 'wolfAllocationAsOne'.
     * @return The information object of foreign property(referrer-as-one). (NotNull)
     */
    public ForeignInfo foreignWolfAllocationAsOne() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), WolfAllocationDbm.getInstance().columnVillageId());
        return cfi("FK_WOLF_ALLOCATION_VILLAGE", "wolfAllocationAsOne", this, WolfAllocationDbm.getInstance(), mp, 3, org.dbflute.optional.OptionalEntity.class, true, false, true, false, null, null, false, "village", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * CAMP_ALLOCATION by VILLAGE_ID, named 'campAllocationList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerCampAllocationList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), CampAllocationDbm.getInstance().columnVillageId());
        return cri("FK_CAMP_ALLOCATION_VILLAGE", "campAllocationList", this, CampAllocationDbm.getInstance(), mp, false, "village");
    }
    /**
     * NORMAL_SAY_RESTRICTION by VILLAGE_ID, named 'normalSayRestrictionList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerNormalSayRestrictionList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), NormalSayRestrictionDbm.getInstance().columnVillageId());
        return cri("FK_NORMAL_SAY_RESTRICTION_VILLAGE", "normalSayRestrictionList", this, NormalSayRestrictionDbm.getInstance(), mp, false, "village");
    }
    /**
     * SKILL_ALLOCATION by VILLAGE_ID, named 'skillAllocationList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerSkillAllocationList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), SkillAllocationDbm.getInstance().columnVillageId());
        return cri("FK_SKILL_ALLOCATION_VILLAGE", "skillAllocationList", this, SkillAllocationDbm.getInstance(), mp, false, "village");
    }
    /**
     * SKILL_SAY_RESTRICTION by VILLAGE_ID, named 'skillSayRestrictionList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerSkillSayRestrictionList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), SkillSayRestrictionDbm.getInstance().columnVillageId());
        return cri("FK_SKILL_SAY_RESTRICTION_VILLAGE", "skillSayRestrictionList", this, SkillSayRestrictionDbm.getInstance(), mp, false, "village");
    }
    /**
     * VILLAGE_CHARA_GROUP by VILLAGE_ID, named 'villageCharaGroupList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillageCharaGroupList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageCharaGroupDbm.getInstance().columnVillageId());
        return cri("FK_VILLAGE_CHARA_GROUP_VILLAGE_ID", "villageCharaGroupList", this, VillageCharaGroupDbm.getInstance(), mp, false, "village");
    }
    /**
     * VILLAGE_DAY by VILLAGE_ID, named 'villageDayList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillageDayList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDayDbm.getInstance().columnVillageId());
        return cri("FK_VILLAGE_DAY_VILLAGE", "villageDayList", this, VillageDayDbm.getInstance(), mp, false, "village");
    }
    /**
     * VILLAGE_PLAYER by VILLAGE_ID, named 'villagePlayerList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillagePlayerDbm.getInstance().columnVillageId());
        return cri("FK_VILLAGE_PLAYER_VILLAGE", "villagePlayerList", this, VillagePlayerDbm.getInstance(), mp, false, "village");
    }
    /**
     * VILLAGE_TAG by VILLAGE_ID, named 'villageTagList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillageTagList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageTagDbm.getInstance().columnVillageId());
        return cri("FK_VILLAGE_TAG_VILLAGE", "villageTagList", this, VillageTagDbm.getInstance(), mp, false, "village");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============
    public boolean hasIdentity() { return true; }
    public boolean hasCommonColumn() { return true; }
    public List<ColumnInfo> getCommonColumnInfoList()
    { return newArrayList(columnRegisterDatetime(), columnRegisterTrace(), columnUpdateDatetime(), columnUpdateTrace()); }
    public List<ColumnInfo> getCommonColumnInfoBeforeInsertList()
    { return newArrayList(columnRegisterDatetime(), columnRegisterTrace(), columnUpdateDatetime(), columnUpdateTrace()); }
    public List<ColumnInfo> getCommonColumnInfoBeforeUpdateList()
    { return newArrayList(columnUpdateDatetime(), columnUpdateTrace()); }

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.Village"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillageCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillageBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<Village> getEntityType() { return Village.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public Village newEntity() { return new Village(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((Village)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((Village)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
