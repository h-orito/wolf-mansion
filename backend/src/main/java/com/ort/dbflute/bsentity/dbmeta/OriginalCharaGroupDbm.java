package com.ort.dbflute.bsentity.dbmeta;

import java.util.List;
import java.util.Map;

import org.dbflute.Entity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.exentity.*;

/**
 * The DB meta of original_chara_group. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class OriginalCharaGroupDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final OriginalCharaGroupDbm _instance = new OriginalCharaGroupDbm();
    private OriginalCharaGroupDbm() {}
    public static OriginalCharaGroupDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((OriginalCharaGroup)et).getOriginalCharaGroupId(), (et, vl) -> ((OriginalCharaGroup)et).setOriginalCharaGroupId(cti(vl)), "originalCharaGroupId");
        setupEpg(_epgMap, et -> ((OriginalCharaGroup)et).getOriginalCharaGroupName(), (et, vl) -> ((OriginalCharaGroup)et).setOriginalCharaGroupName((String)vl), "originalCharaGroupName");
        setupEpg(_epgMap, et -> ((OriginalCharaGroup)et).getRegisterDatetime(), (et, vl) -> ((OriginalCharaGroup)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((OriginalCharaGroup)et).getRegisterTrace(), (et, vl) -> ((OriginalCharaGroup)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((OriginalCharaGroup)et).getUpdateDatetime(), (et, vl) -> ((OriginalCharaGroup)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((OriginalCharaGroup)et).getUpdateTrace(), (et, vl) -> ((OriginalCharaGroup)et).setUpdateTrace((String)vl), "updateTrace");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "original_chara_group";
    protected final String _tableDispName = "ORIGINAL_CHARA_GROUP";
    protected final String _tablePropertyName = "originalCharaGroup";
    protected final TableSqlName _tableSqlName = new TableSqlName("ORIGINAL_CHARA_GROUP", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnOriginalCharaGroupId = cci("ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", null, null, Integer.class, "originalCharaGroupId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, "originalCharaList,villageSettingsList", null, false);
    protected final ColumnInfo _columnOriginalCharaGroupName = cci("ORIGINAL_CHARA_GROUP_NAME", "ORIGINAL_CHARA_GROUP_NAME", null, null, String.class, "originalCharaGroupName", null, false, false, true, "VARCHAR", 40, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaGroupId() { return _columnOriginalCharaGroupId; }
    /**
     * ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaGroupName() { return _columnOriginalCharaGroupName; }
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
        ls.add(columnOriginalCharaGroupId());
        ls.add(columnOriginalCharaGroupName());
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
    protected UniqueInfo cpui() { return hpcpui(columnOriginalCharaGroupId()); }
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

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * ORIGINAL_CHARA by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerOriginalCharaList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnOriginalCharaGroupId(), OriginalCharaDbm.getInstance().columnOriginalCharaGroupId());
        return cri("FK_ORIGINAL_CHARA_ORIGINAL_CHARA_GROUP", "originalCharaList", this, OriginalCharaDbm.getInstance(), mp, false, "originalCharaGroup");
    }
    /**
     * VILLAGE_SETTINGS by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillageSettingsList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnOriginalCharaGroupId(), VillageSettingsDbm.getInstance().columnOriginalCharaGroupId());
        return cri("FK_VILLAGE_SETTINGS_ORIGINAL_CHARA_GROUP_ID", "villageSettingsList", this, VillageSettingsDbm.getInstance(), mp, false, "originalCharaGroup");
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.OriginalCharaGroup"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.OriginalCharaGroupCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.OriginalCharaGroupBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<OriginalCharaGroup> getEntityType() { return OriginalCharaGroup.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public OriginalCharaGroup newEntity() { return new OriginalCharaGroup(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((OriginalCharaGroup)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((OriginalCharaGroup)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
