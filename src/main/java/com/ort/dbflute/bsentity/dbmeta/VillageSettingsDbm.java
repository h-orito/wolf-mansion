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
 * The DB meta of VILLAGE_SETTINGS. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillageSettingsDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillageSettingsDbm _instance = new VillageSettingsDbm();
    private VillageSettingsDbm() {}
    public static VillageSettingsDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillageSettings)et).getVillageId(), (et, vl) -> ((VillageSettings)et).setVillageId(cti(vl)), "villageId");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getStartPersonMinNum(), (et, vl) -> ((VillageSettings)et).setStartPersonMinNum(cti(vl)), "startPersonMinNum");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getPersonMaxNum(), (et, vl) -> ((VillageSettings)et).setPersonMaxNum(cti(vl)), "personMaxNum");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getStartDatetime(), (et, vl) -> ((VillageSettings)et).setStartDatetime(ctldt(vl)), "startDatetime");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getDayChangeIntervalSeconds(), (et, vl) -> ((VillageSettings)et).setDayChangeIntervalSeconds(cti(vl)), "dayChangeIntervalSeconds");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getIsOpenVote(), (et, vl) -> ((VillageSettings)et).setIsOpenVote((Boolean)vl), "isOpenVote");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getIsPossibleSkillRequest(), (et, vl) -> ((VillageSettings)et).setIsPossibleSkillRequest((Boolean)vl), "isPossibleSkillRequest");
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
        setupEfpg(_efpgMap, et -> ((VillageSettings)et).getVillage(), (et, vl) -> ((VillageSettings)et).setVillage((OptionalEntity<Village>)vl), "village");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VILLAGE_SETTINGS";
    protected final String _tableDispName = "VILLAGE_SETTINGS";
    protected final String _tablePropertyName = "villageSettings";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_SETTINGS", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, true, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "village", null, null, false);
    protected final ColumnInfo _columnStartPersonMinNum = cci("START_PERSON_MIN_NUM", "START_PERSON_MIN_NUM", null, null, Integer.class, "startPersonMinNum", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnPersonMaxNum = cci("PERSON_MAX_NUM", "PERSON_MAX_NUM", null, null, Integer.class, "personMaxNum", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnStartDatetime = cci("START_DATETIME", "START_DATETIME", null, null, java.time.LocalDateTime.class, "startDatetime", null, false, false, false, "DATETIME", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnDayChangeIntervalSeconds = cci("DAY_CHANGE_INTERVAL_SECONDS", "DAY_CHANGE_INTERVAL_SECONDS", null, null, Integer.class, "dayChangeIntervalSeconds", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIsOpenVote = cci("IS_OPEN_VOTE", "IS_OPEN_VOTE", null, null, Boolean.class, "isOpenVote", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIsPossibleSkillRequest = cci("IS_POSSIBLE_SKILL_REQUEST", "IS_POSSIBLE_SKILL_REQUEST", null, null, Boolean.class, "isPossibleSkillRequest", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);

    /**
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * START_PERSON_MIN_NUM: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnStartPersonMinNum() { return _columnStartPersonMinNum; }
    /**
     * PERSON_MAX_NUM: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPersonMaxNum() { return _columnPersonMaxNum; }
    /**
     * START_DATETIME: {DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnStartDatetime() { return _columnStartDatetime; }
    /**
     * DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDayChangeIntervalSeconds() { return _columnDayChangeIntervalSeconds; }
    /**
     * IS_OPEN_VOTE: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsOpenVote() { return _columnIsOpenVote; }
    /**
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsPossibleSkillRequest() { return _columnIsPossibleSkillRequest; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnVillageId());
        ls.add(columnStartPersonMinNum());
        ls.add(columnPersonMaxNum());
        ls.add(columnStartDatetime());
        ls.add(columnDayChangeIntervalSeconds());
        ls.add(columnIsOpenVote());
        ls.add(columnIsPossibleSkillRequest());
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
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillage() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDbm.getInstance().columnVilalgeId());
        return cfi("FK_VILLAGE_SETTINGS_VILLAGE", "village", this, VillageDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, true, false, false, false, null, null, false, "villageSettingsAsOne", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillageSettings"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillageSettingsCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillageSettingsBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillageSettings> getEntityType() { return VillageSettings.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillageSettings newEntity() { return new VillageSettings(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillageSettings)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillageSettings)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
