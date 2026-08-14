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
 * The DB meta of player_favorite_chara. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class PlayerFavoriteCharaDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final PlayerFavoriteCharaDbm _instance = new PlayerFavoriteCharaDbm();
    private PlayerFavoriteCharaDbm() {}
    public static PlayerFavoriteCharaDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((PlayerFavoriteChara)et).getPlayerFavoriteCharaId(), (et, vl) -> ((PlayerFavoriteChara)et).setPlayerFavoriteCharaId(cti(vl)), "playerFavoriteCharaId");
        setupEpg(_epgMap, et -> ((PlayerFavoriteChara)et).getPlayerId(), (et, vl) -> ((PlayerFavoriteChara)et).setPlayerId(cti(vl)), "playerId");
        setupEpg(_epgMap, et -> ((PlayerFavoriteChara)et).getCharaId(), (et, vl) -> ((PlayerFavoriteChara)et).setCharaId(cti(vl)), "charaId");
        setupEpg(_epgMap, et -> ((PlayerFavoriteChara)et).getRegisterDatetime(), (et, vl) -> ((PlayerFavoriteChara)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((PlayerFavoriteChara)et).getRegisterTrace(), (et, vl) -> ((PlayerFavoriteChara)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((PlayerFavoriteChara)et).getUpdateDatetime(), (et, vl) -> ((PlayerFavoriteChara)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((PlayerFavoriteChara)et).getUpdateTrace(), (et, vl) -> ((PlayerFavoriteChara)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((PlayerFavoriteChara)et).getChara(), (et, vl) -> ((PlayerFavoriteChara)et).setChara((OptionalEntity<Chara>)vl), "chara");
        setupEfpg(_efpgMap, et -> ((PlayerFavoriteChara)et).getPlayer(), (et, vl) -> ((PlayerFavoriteChara)et).setPlayer((OptionalEntity<Player>)vl), "player");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "player_favorite_chara";
    protected final String _tableDispName = "PLAYER_FAVORITE_CHARA";
    protected final String _tablePropertyName = "playerFavoriteChara";
    protected final TableSqlName _tableSqlName = new TableSqlName("PLAYER_FAVORITE_CHARA", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnPlayerFavoriteCharaId = cci("PLAYER_FAVORITE_CHARA_ID", "PLAYER_FAVORITE_CHARA_ID", null, null, Integer.class, "playerFavoriteCharaId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnPlayerId = cci("PLAYER_ID", "PLAYER_ID", null, null, Integer.class, "playerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "player", null, null, false);
    protected final ColumnInfo _columnCharaId = cci("CHARA_ID", "CHARA_ID", null, null, Integer.class, "charaId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "chara", null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * PLAYER_FAVORITE_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPlayerFavoriteCharaId() { return _columnPlayerFavoriteCharaId; }
    /**
     * PLAYER_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to player}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPlayerId() { return _columnPlayerId; }
    /**
     * CHARA_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to chara}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaId() { return _columnCharaId; }
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
        ls.add(columnPlayerFavoriteCharaId());
        ls.add(columnPlayerId());
        ls.add(columnCharaId());
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
    protected UniqueInfo cpui() { return hpcpui(columnPlayerFavoriteCharaId()); }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // -----------------------------------------------------
    //                                        Unique Element
    //                                        --------------
    public UniqueInfo uniqueOf() {
        List<ColumnInfo> ls = newArrayListSized(4);
        ls.add(columnPlayerId());
        ls.add(columnCharaId());
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
     * CHARA by my CHARA_ID, named 'chara'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignChara() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnCharaId(), CharaDbm.getInstance().columnCharaId());
        return cfi("FK_PLAYER_FAVORITE_CHARA_CHARA", "chara", this, CharaDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "playerFavoriteCharaList", false);
    }
    /**
     * PLAYER by my PLAYER_ID, named 'player'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignPlayer() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnPlayerId(), PlayerDbm.getInstance().columnPlayerId());
        return cfi("FK_PLAYER_FAVORITE_CHARA_PLAYER", "player", this, PlayerDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "playerFavoriteCharaList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.PlayerFavoriteChara"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.PlayerFavoriteCharaCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.PlayerFavoriteCharaBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<PlayerFavoriteChara> getEntityType() { return PlayerFavoriteChara.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public PlayerFavoriteChara newEntity() { return new PlayerFavoriteChara(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((PlayerFavoriteChara)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((PlayerFavoriteChara)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
