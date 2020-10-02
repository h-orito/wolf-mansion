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
 * The DB meta of VILLAGE_PLAYER_ROOM_HISTORY. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerRoomHistoryDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillagePlayerRoomHistoryDbm _instance = new VillagePlayerRoomHistoryDbm();
    private VillagePlayerRoomHistoryDbm() {}
    public static VillagePlayerRoomHistoryDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getVillagePlayerRoomHistoryId(), (et, vl) -> ((VillagePlayerRoomHistory)et).setVillagePlayerRoomHistoryId(cti(vl)), "villagePlayerRoomHistoryId");
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getVillagePlayerId(), (et, vl) -> ((VillagePlayerRoomHistory)et).setVillagePlayerId(cti(vl)), "villagePlayerId");
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getDay(), (et, vl) -> ((VillagePlayerRoomHistory)et).setDay(cti(vl)), "day");
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getRoomNumber(), (et, vl) -> ((VillagePlayerRoomHistory)et).setRoomNumber(cti(vl)), "roomNumber");
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getRegisterDatetime(), (et, vl) -> ((VillagePlayerRoomHistory)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getRegisterTrace(), (et, vl) -> ((VillagePlayerRoomHistory)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getUpdateDatetime(), (et, vl) -> ((VillagePlayerRoomHistory)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerRoomHistory)et).getUpdateTrace(), (et, vl) -> ((VillagePlayerRoomHistory)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((VillagePlayerRoomHistory)et).getVillagePlayer(), (et, vl) -> ((VillagePlayerRoomHistory)et).setVillagePlayer((OptionalEntity<VillagePlayer>)vl), "villagePlayer");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VILLAGE_PLAYER_ROOM_HISTORY";
    protected final String _tableDispName = "VILLAGE_PLAYER_ROOM_HISTORY";
    protected final String _tablePropertyName = "villagePlayerRoomHistory";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_PLAYER_ROOM_HISTORY", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillagePlayerRoomHistoryId = cci("VILLAGE_PLAYER_ROOM_HISTORY_ID", "VILLAGE_PLAYER_ROOM_HISTORY_ID", null, null, Integer.class, "villagePlayerRoomHistoryId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillagePlayerId = cci("VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", null, null, Integer.class, "villagePlayerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villagePlayer", null, null, false);
    protected final ColumnInfo _columnDay = cci("DAY", "DAY", null, null, Integer.class, "day", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRoomNumber = cci("ROOM_NUMBER", "ROOM_NUMBER", null, null, Integer.class, "roomNumber", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_PLAYER_ROOM_HISTORY_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerRoomHistoryId() { return _columnVillagePlayerRoomHistoryId; }
    /**
     * VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE_PLAYER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerId() { return _columnVillagePlayerId; }
    /**
     * DAY: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDay() { return _columnDay; }
    /**
     * ROOM_NUMBER: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRoomNumber() { return _columnRoomNumber; }
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
        ls.add(columnVillagePlayerRoomHistoryId());
        ls.add(columnVillagePlayerId());
        ls.add(columnDay());
        ls.add(columnRoomNumber());
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
    protected UniqueInfo cpui() { return hpcpui(columnVillagePlayerRoomHistoryId()); }
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
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayer'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillagePlayer() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerDbm.getInstance().columnVillagePlayerId());
        return cfi("FK_VILLAGE_PLAYER_ROOM_HISTORY_VILLAGE_PLAYER", "villagePlayer", this, VillagePlayerDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerRoomHistoryList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillagePlayerRoomHistory"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillagePlayerRoomHistoryCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillagePlayerRoomHistoryBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillagePlayerRoomHistory> getEntityType() { return VillagePlayerRoomHistory.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillagePlayerRoomHistory newEntity() { return new VillagePlayerRoomHistory(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillagePlayerRoomHistory)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillagePlayerRoomHistory)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
