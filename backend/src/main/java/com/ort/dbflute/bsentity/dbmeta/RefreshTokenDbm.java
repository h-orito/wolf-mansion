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
 * The DB meta of REFRESH_TOKEN. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class RefreshTokenDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final RefreshTokenDbm _instance = new RefreshTokenDbm();
    private RefreshTokenDbm() {}
    public static RefreshTokenDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((RefreshToken)et).getRefreshTokenId(), (et, vl) -> ((RefreshToken)et).setRefreshTokenId(cti(vl)), "refreshTokenId");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getPlayerId(), (et, vl) -> ((RefreshToken)et).setPlayerId(cti(vl)), "playerId");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getTokenHash(), (et, vl) -> ((RefreshToken)et).setTokenHash((String)vl), "tokenHash");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getIssuedDatetime(), (et, vl) -> ((RefreshToken)et).setIssuedDatetime(ctldt(vl)), "issuedDatetime");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getExpiresDatetime(), (et, vl) -> ((RefreshToken)et).setExpiresDatetime(ctldt(vl)), "expiresDatetime");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getUsedDatetime(), (et, vl) -> ((RefreshToken)et).setUsedDatetime(ctldt(vl)), "usedDatetime");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getRevokedDatetime(), (et, vl) -> ((RefreshToken)et).setRevokedDatetime(ctldt(vl)), "revokedDatetime");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getRegisterDatetime(), (et, vl) -> ((RefreshToken)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getRegisterTrace(), (et, vl) -> ((RefreshToken)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getUpdateDatetime(), (et, vl) -> ((RefreshToken)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((RefreshToken)et).getUpdateTrace(), (et, vl) -> ((RefreshToken)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((RefreshToken)et).getPlayer(), (et, vl) -> ((RefreshToken)et).setPlayer((OptionalEntity<Player>)vl), "player");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "REFRESH_TOKEN";
    protected final String _tableDispName = "REFRESH_TOKEN";
    protected final String _tablePropertyName = "refreshToken";
    protected final TableSqlName _tableSqlName = new TableSqlName("REFRESH_TOKEN", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnRefreshTokenId = cci("REFRESH_TOKEN_ID", "REFRESH_TOKEN_ID", null, null, Integer.class, "refreshTokenId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnPlayerId = cci("PLAYER_ID", "PLAYER_ID", null, null, Integer.class, "playerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "player", null, null, false);
    protected final ColumnInfo _columnTokenHash = cci("TOKEN_HASH", "TOKEN_HASH", null, null, String.class, "tokenHash", null, false, false, true, "CHAR", 64, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIssuedDatetime = cci("ISSUED_DATETIME", "ISSUED_DATETIME", null, null, java.time.LocalDateTime.class, "issuedDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnExpiresDatetime = cci("EXPIRES_DATETIME", "EXPIRES_DATETIME", null, null, java.time.LocalDateTime.class, "expiresDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnUsedDatetime = cci("USED_DATETIME", "USED_DATETIME", null, null, java.time.LocalDateTime.class, "usedDatetime", null, false, false, false, "DATETIME", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRevokedDatetime = cci("REVOKED_DATETIME", "REVOKED_DATETIME", null, null, java.time.LocalDateTime.class, "revokedDatetime", null, false, false, false, "DATETIME", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * REFRESH_TOKEN_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRefreshTokenId() { return _columnRefreshTokenId; }
    /**
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPlayerId() { return _columnPlayerId; }
    /**
     * TOKEN_HASH: {UQ, NotNull, CHAR(64)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnTokenHash() { return _columnTokenHash; }
    /**
     * ISSUED_DATETIME: {NotNull, DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIssuedDatetime() { return _columnIssuedDatetime; }
    /**
     * EXPIRES_DATETIME: {IX, NotNull, DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnExpiresDatetime() { return _columnExpiresDatetime; }
    /**
     * USED_DATETIME: {DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnUsedDatetime() { return _columnUsedDatetime; }
    /**
     * REVOKED_DATETIME: {DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRevokedDatetime() { return _columnRevokedDatetime; }
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
        ls.add(columnRefreshTokenId());
        ls.add(columnPlayerId());
        ls.add(columnTokenHash());
        ls.add(columnIssuedDatetime());
        ls.add(columnExpiresDatetime());
        ls.add(columnUsedDatetime());
        ls.add(columnRevokedDatetime());
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
    protected UniqueInfo cpui() { return hpcpui(columnRefreshTokenId()); }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // -----------------------------------------------------
    //                                        Unique Element
    //                                        --------------
    public UniqueInfo uniqueOf() { return hpcui(columnTokenHash()); }

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
        return cfi("FK_REFRESH_TOKEN_PLAYER", "player", this, PlayerDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "refreshTokenList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.RefreshToken"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.RefreshTokenCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.RefreshTokenBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<RefreshToken> getEntityType() { return RefreshToken.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public RefreshToken newEntity() { return new RefreshToken(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((RefreshToken)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((RefreshToken)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
