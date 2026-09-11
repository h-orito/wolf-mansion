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
 * The DB meta of analyzer_memo. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class AnalyzerMemoDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final AnalyzerMemoDbm _instance = new AnalyzerMemoDbm();
    private AnalyzerMemoDbm() {}
    public static AnalyzerMemoDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getAnalyzerMemoId(), (et, vl) -> ((AnalyzerMemo)et).setAnalyzerMemoId(cti(vl)), "analyzerMemoId");
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getPlayerId(), (et, vl) -> ((AnalyzerMemo)et).setPlayerId(cti(vl)), "playerId");
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getVillageId(), (et, vl) -> ((AnalyzerMemo)et).setVillageId(cti(vl)), "villageId");
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getMemoJson(), (et, vl) -> ((AnalyzerMemo)et).setMemoJson((String)vl), "memoJson");
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getRegisterDatetime(), (et, vl) -> ((AnalyzerMemo)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getRegisterTrace(), (et, vl) -> ((AnalyzerMemo)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getUpdateDatetime(), (et, vl) -> ((AnalyzerMemo)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((AnalyzerMemo)et).getUpdateTrace(), (et, vl) -> ((AnalyzerMemo)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((AnalyzerMemo)et).getPlayer(), (et, vl) -> ((AnalyzerMemo)et).setPlayer((OptionalEntity<Player>)vl), "player");
        setupEfpg(_efpgMap, et -> ((AnalyzerMemo)et).getVillage(), (et, vl) -> ((AnalyzerMemo)et).setVillage((OptionalEntity<Village>)vl), "village");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "analyzer_memo";
    protected final String _tableDispName = "ANALYZER_MEMO";
    protected final String _tablePropertyName = "analyzerMemo";
    protected final TableSqlName _tableSqlName = new TableSqlName("ANALYZER_MEMO", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnAnalyzerMemoId = cci("ANALYZER_MEMO_ID", "ANALYZER_MEMO_ID", null, null, Integer.class, "analyzerMemoId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnPlayerId = cci("PLAYER_ID", "PLAYER_ID", null, null, Integer.class, "playerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "player", null, null, false);
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "village", null, null, false);
    protected final ColumnInfo _columnMemoJson = cci("MEMO_JSON", "MEMO_JSON", null, null, String.class, "memoJson", null, false, false, true, "MEDIUMTEXT", 16777215, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * ANALYZER_MEMO_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnAnalyzerMemoId() { return _columnAnalyzerMemoId; }
    /**
     * PLAYER_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to player}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPlayerId() { return _columnPlayerId; }
    /**
     * VILLAGE_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to village}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * MEMO_JSON: {NotNull, MEDIUMTEXT(16777215)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemoJson() { return _columnMemoJson; }
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
        ls.add(columnAnalyzerMemoId());
        ls.add(columnPlayerId());
        ls.add(columnVillageId());
        ls.add(columnMemoJson());
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
    protected UniqueInfo cpui() { return hpcpui(columnAnalyzerMemoId()); }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // -----------------------------------------------------
    //                                        Unique Element
    //                                        --------------
    public UniqueInfo uniqueOf() {
        List<ColumnInfo> ls = newArrayListSized(4);
        ls.add(columnPlayerId());
        ls.add(columnVillageId());
        return hpcui(ls);
    }

    // ===================================================================================
    //                                                                       Relation Info
    //                                                                       =============
    // cannot cache because it uses related DB meta instance while booting
    // (instead, cached by super's collection)
    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    /**
     * PLAYER by my PLAYER_ID, named 'player'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignPlayer() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnPlayerId(), PlayerDbm.getInstance().columnPlayerId());
        return cfi("FK_ANALYZER_MEMO_PLAYER", "player", this, PlayerDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "analyzerMemoList", false);
    }
    /**
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillage() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDbm.getInstance().columnVillageId());
        return cfi("FK_ANALYZER_MEMO_VILLAGE", "village", this, VillageDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "analyzerMemoList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.AnalyzerMemo"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.AnalyzerMemoCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.AnalyzerMemoBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<AnalyzerMemo> getEntityType() { return AnalyzerMemo.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public AnalyzerMemo newEntity() { return new AnalyzerMemo(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((AnalyzerMemo)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((AnalyzerMemo)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
