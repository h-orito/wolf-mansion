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
 * The DB meta of VILLAGE_PLAYER_STATUS. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerStatusDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillagePlayerStatusDbm _instance = new VillagePlayerStatusDbm();
    private VillagePlayerStatusDbm() {}
    public static VillagePlayerStatusDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getVillagePlayerStatusId(), (et, vl) -> ((VillagePlayerStatus)et).setVillagePlayerStatusId(cti(vl)), "villagePlayerStatusId");
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getVillagePlayerId(), (et, vl) -> ((VillagePlayerStatus)et).setVillagePlayerId(cti(vl)), "villagePlayerId");
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getToVillagePlayerId(), (et, vl) -> ((VillagePlayerStatus)et).setToVillagePlayerId(cti(vl)), "toVillagePlayerId");
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getVillagePlayerStatusCode(), (et, vl) -> {
            CDef.VillagePlayerStatusType cls = (CDef.VillagePlayerStatusType)gcls(et, columnVillagePlayerStatusCode(), vl);
            if (cls != null) {
                ((VillagePlayerStatus)et).setVillagePlayerStatusCodeAsVillagePlayerStatusType(cls);
            } else {
                ((VillagePlayerStatus)et).mynativeMappingVillagePlayerStatusCode((String)vl);
            }
        }, "villagePlayerStatusCode");
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getRegisterDatetime(), (et, vl) -> ((VillagePlayerStatus)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getRegisterTrace(), (et, vl) -> ((VillagePlayerStatus)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getUpdateDatetime(), (et, vl) -> ((VillagePlayerStatus)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerStatus)et).getUpdateTrace(), (et, vl) -> ((VillagePlayerStatus)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((VillagePlayerStatus)et).getVillagePlayerByToVillagePlayerId(), (et, vl) -> ((VillagePlayerStatus)et).setVillagePlayerByToVillagePlayerId((OptionalEntity<VillagePlayer>)vl), "villagePlayerByToVillagePlayerId");
        setupEfpg(_efpgMap, et -> ((VillagePlayerStatus)et).getVillagePlayerByVillagePlayerId(), (et, vl) -> ((VillagePlayerStatus)et).setVillagePlayerByVillagePlayerId((OptionalEntity<VillagePlayer>)vl), "villagePlayerByVillagePlayerId");
        setupEfpg(_efpgMap, et -> ((VillagePlayerStatus)et).getVillagePlayerStatusType(), (et, vl) -> ((VillagePlayerStatus)et).setVillagePlayerStatusType((OptionalEntity<VillagePlayerStatusType>)vl), "villagePlayerStatusType");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VILLAGE_PLAYER_STATUS";
    protected final String _tableDispName = "VILLAGE_PLAYER_STATUS";
    protected final String _tablePropertyName = "villagePlayerStatus";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_PLAYER_STATUS", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillagePlayerStatusId = cci("VILLAGE_PLAYER_STATUS_ID", "VILLAGE_PLAYER_STATUS_ID", null, null, Integer.class, "villagePlayerStatusId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillagePlayerId = cci("VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", null, null, Integer.class, "villagePlayerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villagePlayerByVillagePlayerId", null, null, false);
    protected final ColumnInfo _columnToVillagePlayerId = cci("TO_VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", null, null, Integer.class, "toVillagePlayerId", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villagePlayerByToVillagePlayerId", null, null, false);
    protected final ColumnInfo _columnVillagePlayerStatusCode = cci("VILLAGE_PLAYER_STATUS_CODE", "VILLAGE_PLAYER_STATUS_CODE", null, null, String.class, "villagePlayerStatusCode", null, false, false, true, "VARCHAR", 20, 0, null, null, false, null, null, "villagePlayerStatusType", null, CDef.DefMeta.VillagePlayerStatusType, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_PLAYER_STATUS_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerStatusId() { return _columnVillagePlayerStatusId; }
    /**
     * VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerId() { return _columnVillagePlayerId; }
    /**
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnToVillagePlayerId() { return _columnToVillagePlayerId; }
    /**
     * VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to VILLAGE_PLAYER_STATUS_TYPE, classification=VillagePlayerStatusType}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerStatusCode() { return _columnVillagePlayerStatusCode; }
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
        ls.add(columnVillagePlayerStatusId());
        ls.add(columnVillagePlayerId());
        ls.add(columnToVillagePlayerId());
        ls.add(columnVillagePlayerStatusCode());
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
    protected UniqueInfo cpui() { return hpcpui(columnVillagePlayerStatusId()); }
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
     * VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillagePlayerByToVillagePlayerId() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnToVillagePlayerId(), VillagePlayerDbm.getInstance().columnVillagePlayerId());
        return cfi("FK_VILLAGE_PLAYER_STATUS_TO_VILLAGE_PLAYER", "villagePlayerByToVillagePlayerId", this, VillagePlayerDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerStatusByToVillagePlayerIdList", false);
    }
    /**
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillagePlayerByVillagePlayerId() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerDbm.getInstance().columnVillagePlayerId());
        return cfi("FK_VILLAGE_PLAYER_STATUS_VILLAGE_PLAYER", "villagePlayerByVillagePlayerId", this, VillagePlayerDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerStatusByVillagePlayerIdList", false);
    }
    /**
     * VILLAGE_PLAYER_STATUS_TYPE by my VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusType'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillagePlayerStatusType() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerStatusCode(), VillagePlayerStatusTypeDbm.getInstance().columnVillagePlayerStatusTypeCode());
        return cfi("FK_VILLAGE_PLAYER_STATUS_VILLAGE_PLAYER_STATUS_TYPE", "villagePlayerStatusType", this, VillagePlayerStatusTypeDbm.getInstance(), mp, 2, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerStatusList", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------

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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillagePlayerStatus"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillagePlayerStatusCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillagePlayerStatusBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillagePlayerStatus> getEntityType() { return VillagePlayerStatus.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillagePlayerStatus newEntity() { return new VillagePlayerStatus(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillagePlayerStatus)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillagePlayerStatus)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
