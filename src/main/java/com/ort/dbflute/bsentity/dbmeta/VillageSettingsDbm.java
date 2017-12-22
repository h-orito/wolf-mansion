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
 * The DB meta of village_settings. (Singleton)
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
        setupEpg(_epgMap, et -> ((VillageSettings)et).getIsOpenVote(), (et, vl) -> {
            ((VillageSettings)et).setIsOpenVote((Boolean)vl);
        }, "isOpenVote");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getIsPossibleSkillRequest(), (et, vl) -> {
            ((VillageSettings)et).setIsPossibleSkillRequest((Boolean)vl);
        }, "isPossibleSkillRequest");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getCharacterGroupId(), (et, vl) -> ((VillageSettings)et).setCharacterGroupId(cti(vl)), "characterGroupId");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getRegisterDatetime(), (et, vl) -> ((VillageSettings)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getRegisterTrace(), (et, vl) -> ((VillageSettings)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getUpdateDatetime(), (et, vl) -> ((VillageSettings)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((VillageSettings)et).getUpdateTrace(), (et, vl) -> ((VillageSettings)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((VillageSettings)et).getCharaGroup(), (et, vl) -> ((VillageSettings)et).setCharaGroup((OptionalEntity<CharaGroup>)vl), "charaGroup");
        setupEfpg(_efpgMap, et -> ((VillageSettings)et).getVillage(), (et, vl) -> ((VillageSettings)et).setVillage((OptionalEntity<Village>)vl), "village");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "village_settings";
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
    protected final ColumnInfo _columnIsOpenVote = cci("IS_OPEN_VOTE", "IS_OPEN_VOTE", null, null, Boolean.class, "isOpenVote", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnIsPossibleSkillRequest = cci("IS_POSSIBLE_SKILL_REQUEST", "IS_POSSIBLE_SKILL_REQUEST", null, null, Boolean.class, "isPossibleSkillRequest", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnCharacterGroupId = cci("CHARACTER_GROUP_ID", "CHARACTER_GROUP_ID", null, null, Integer.class, "characterGroupId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "charaGroup", null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village}
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
     * IS_OPEN_VOTE: {NotNull, BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsOpenVote() { return _columnIsOpenVote; }
    /**
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsPossibleSkillRequest() { return _columnIsPossibleSkillRequest; }
    /**
     * CHARACTER_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharacterGroupId() { return _columnCharacterGroupId; }
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
        ls.add(columnStartPersonMinNum());
        ls.add(columnPersonMaxNum());
        ls.add(columnStartDatetime());
        ls.add(columnDayChangeIntervalSeconds());
        ls.add(columnIsOpenVote());
        ls.add(columnIsPossibleSkillRequest());
        ls.add(columnCharacterGroupId());
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
     * CHARA_GROUP by my CHARACTER_GROUP_ID, named 'charaGroup'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignCharaGroup() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnCharacterGroupId(), CharaGroupDbm.getInstance().columnCharaGroupId());
        return cfi("FK_VILLAGE_SETTINGS_CHARA_GROUP", "charaGroup", this, CharaGroupDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villageSettingsList", false);
    }
    /**
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillage() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDbm.getInstance().columnVillageId());
        return cfi("FK_VILLAGE_SETTINGS_VILLAGE", "village", this, VillageDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, true, false, false, false, null, null, false, "villageSettingsAsOne", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============
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
