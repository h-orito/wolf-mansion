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
 * The DB meta of village_player_dead_history. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerDeadHistoryDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillagePlayerDeadHistoryDbm _instance = new VillagePlayerDeadHistoryDbm();
    private VillagePlayerDeadHistoryDbm() {}
    public static VillagePlayerDeadHistoryDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getVillagePlayerDeadHistoryId(), (et, vl) -> ((VillagePlayerDeadHistory)et).setVillagePlayerDeadHistoryId(cti(vl)), "villagePlayerDeadHistoryId");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getVillagePlayerId(), (et, vl) -> ((VillagePlayerDeadHistory)et).setVillagePlayerId(cti(vl)), "villagePlayerId");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getDay(), (et, vl) -> ((VillagePlayerDeadHistory)et).setDay(cti(vl)), "day");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getIsDead(), (et, vl) -> {
            ((VillagePlayerDeadHistory)et).setIsDead((Boolean)vl);
        }, "isDead");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getDeadReasonCode(), (et, vl) -> {
            CDef.DeadReason cls = (CDef.DeadReason)gcls(et, columnDeadReasonCode(), vl);
            if (cls != null) {
                ((VillagePlayerDeadHistory)et).setDeadReasonCodeAsDeadReason(cls);
            } else {
                ((VillagePlayerDeadHistory)et).mynativeMappingDeadReasonCode((String)vl);
            }
        }, "deadReasonCode");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getRegisterDatetime(), (et, vl) -> ((VillagePlayerDeadHistory)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getRegisterTrace(), (et, vl) -> ((VillagePlayerDeadHistory)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getUpdateDatetime(), (et, vl) -> ((VillagePlayerDeadHistory)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerDeadHistory)et).getUpdateTrace(), (et, vl) -> ((VillagePlayerDeadHistory)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((VillagePlayerDeadHistory)et).getDeadReason(), (et, vl) -> ((VillagePlayerDeadHistory)et).setDeadReason((OptionalEntity<DeadReason>)vl), "deadReason");
        setupEfpg(_efpgMap, et -> ((VillagePlayerDeadHistory)et).getVillagePlayer(), (et, vl) -> ((VillagePlayerDeadHistory)et).setVillagePlayer((OptionalEntity<VillagePlayer>)vl), "villagePlayer");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "village_player_dead_history";
    protected final String _tableDispName = "VILLAGE_PLAYER_DEAD_HISTORY";
    protected final String _tablePropertyName = "villagePlayerDeadHistory";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_PLAYER_DEAD_HISTORY", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillagePlayerDeadHistoryId = cci("VILLAGE_PLAYER_DEAD_HISTORY_ID", "VILLAGE_PLAYER_DEAD_HISTORY_ID", null, null, Integer.class, "villagePlayerDeadHistoryId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillagePlayerId = cci("VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", null, null, Integer.class, "villagePlayerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villagePlayer", null, null, false);
    protected final ColumnInfo _columnDay = cci("DAY", "DAY", null, null, Integer.class, "day", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIsDead = cci("IS_DEAD", "IS_DEAD", null, null, Boolean.class, "isDead", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnDeadReasonCode = cci("DEAD_REASON_CODE", "DEAD_REASON_CODE", null, null, String.class, "deadReasonCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "deadReason", null, CDef.DefMeta.DeadReason, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_PLAYER_DEAD_HISTORY_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerDeadHistoryId() { return _columnVillagePlayerDeadHistoryId; }
    /**
     * VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to village_player}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerId() { return _columnVillagePlayerId; }
    /**
     * DAY: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDay() { return _columnDay; }
    /**
     * IS_DEAD: {NotNull, BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsDead() { return _columnIsDead; }
    /**
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to dead_reason, classification=DeadReason}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDeadReasonCode() { return _columnDeadReasonCode; }
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
        ls.add(columnVillagePlayerDeadHistoryId());
        ls.add(columnVillagePlayerId());
        ls.add(columnDay());
        ls.add(columnIsDead());
        ls.add(columnDeadReasonCode());
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
    protected UniqueInfo cpui() { return hpcpui(columnVillagePlayerDeadHistoryId()); }
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
     * DEAD_REASON by my DEAD_REASON_CODE, named 'deadReason'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignDeadReason() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnDeadReasonCode(), DeadReasonDbm.getInstance().columnDeadReasonCode());
        return cfi("FK_VILLAGE_PLAYER_DEAD_HISTORY_DEAD_REASON", "deadReason", this, DeadReasonDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerDeadHistoryList", false);
    }
    /**
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayer'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillagePlayer() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerDbm.getInstance().columnVillagePlayerId());
        return cfi("FK_VILLAGE_PLAYER_DEAD_HISTORY_VILLAGE_PLAYER", "villagePlayer", this, VillagePlayerDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerDeadHistoryList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillagePlayerDeadHistory"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillagePlayerDeadHistoryCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillagePlayerDeadHistoryBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillagePlayerDeadHistory> getEntityType() { return VillagePlayerDeadHistory.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillagePlayerDeadHistory newEntity() { return new VillagePlayerDeadHistory(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillagePlayerDeadHistory)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillagePlayerDeadHistory)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
