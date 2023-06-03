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
 * The DB meta of village_player_notification. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerNotificationDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillagePlayerNotificationDbm _instance = new VillagePlayerNotificationDbm();
    private VillagePlayerNotificationDbm() {}
    public static VillagePlayerNotificationDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getVillagePlayerId(), (et, vl) -> ((VillagePlayerNotification)et).setVillagePlayerId(cti(vl)), "villagePlayerId");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getDiscordWebhookUrl(), (et, vl) -> ((VillagePlayerNotification)et).setDiscordWebhookUrl((String)vl), "discordWebhookUrl");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getVillageStart(), (et, vl) -> ((VillagePlayerNotification)et).setVillageStart((Boolean)vl), "villageStart");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getVillageDaychange(), (et, vl) -> ((VillagePlayerNotification)et).setVillageDaychange((Boolean)vl), "villageDaychange");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getVillageEpilogue(), (et, vl) -> ((VillagePlayerNotification)et).setVillageEpilogue((Boolean)vl), "villageEpilogue");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getReceiveSecretSay(), (et, vl) -> ((VillagePlayerNotification)et).setReceiveSecretSay((Boolean)vl), "receiveSecretSay");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getReceiveAbilitySay(), (et, vl) -> ((VillagePlayerNotification)et).setReceiveAbilitySay((Boolean)vl), "receiveAbilitySay");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getReceiveAnchorSay(), (et, vl) -> ((VillagePlayerNotification)et).setReceiveAnchorSay((Boolean)vl), "receiveAnchorSay");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getKeyword(), (et, vl) -> ((VillagePlayerNotification)et).setKeyword((String)vl), "keyword");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getRegisterDatetime(), (et, vl) -> ((VillagePlayerNotification)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getRegisterTrace(), (et, vl) -> ((VillagePlayerNotification)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getUpdateDatetime(), (et, vl) -> ((VillagePlayerNotification)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayerNotification)et).getUpdateTrace(), (et, vl) -> ((VillagePlayerNotification)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((VillagePlayerNotification)et).getVillagePlayer(), (et, vl) -> ((VillagePlayerNotification)et).setVillagePlayer((OptionalEntity<VillagePlayer>)vl), "villagePlayer");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "village_player_notification";
    protected final String _tableDispName = "VILLAGE_PLAYER_NOTIFICATION";
    protected final String _tablePropertyName = "villagePlayerNotification";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_PLAYER_NOTIFICATION", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillagePlayerId = cci("VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", null, null, Integer.class, "villagePlayerId", null, true, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villagePlayer", null, null, false);
    protected final ColumnInfo _columnDiscordWebhookUrl = cci("DISCORD_WEBHOOK_URL", "DISCORD_WEBHOOK_URL", null, null, String.class, "discordWebhookUrl", null, false, false, true, "VARCHAR", 1000, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillageStart = cci("VILLAGE_START", "VILLAGE_START", null, null, Boolean.class, "villageStart", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillageDaychange = cci("VILLAGE_DAYCHANGE", "VILLAGE_DAYCHANGE", null, null, Boolean.class, "villageDaychange", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillageEpilogue = cci("VILLAGE_EPILOGUE", "VILLAGE_EPILOGUE", null, null, Boolean.class, "villageEpilogue", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnReceiveSecretSay = cci("RECEIVE_SECRET_SAY", "RECEIVE_SECRET_SAY", null, null, Boolean.class, "receiveSecretSay", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnReceiveAbilitySay = cci("RECEIVE_ABILITY_SAY", "RECEIVE_ABILITY_SAY", null, null, Boolean.class, "receiveAbilitySay", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnReceiveAnchorSay = cci("RECEIVE_ANCHOR_SAY", "RECEIVE_ANCHOR_SAY", null, null, Boolean.class, "receiveAnchorSay", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnKeyword = cci("KEYWORD", "KEYWORD", null, null, String.class, "keyword", null, false, false, false, "VARCHAR", 255, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_PLAYER_ID: {PK, NotNull, INT UNSIGNED(10), FK to village_player}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerId() { return _columnVillagePlayerId; }
    /**
     * DISCORD_WEBHOOK_URL: {NotNull, VARCHAR(1000)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDiscordWebhookUrl() { return _columnDiscordWebhookUrl; }
    /**
     * VILLAGE_START: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageStart() { return _columnVillageStart; }
    /**
     * VILLAGE_DAYCHANGE: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageDaychange() { return _columnVillageDaychange; }
    /**
     * VILLAGE_EPILOGUE: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageEpilogue() { return _columnVillageEpilogue; }
    /**
     * RECEIVE_SECRET_SAY: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnReceiveSecretSay() { return _columnReceiveSecretSay; }
    /**
     * RECEIVE_ABILITY_SAY: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnReceiveAbilitySay() { return _columnReceiveAbilitySay; }
    /**
     * RECEIVE_ANCHOR_SAY: {NotNull, BIT}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnReceiveAnchorSay() { return _columnReceiveAnchorSay; }
    /**
     * KEYWORD: {VARCHAR(255)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnKeyword() { return _columnKeyword; }
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
        ls.add(columnVillagePlayerId());
        ls.add(columnDiscordWebhookUrl());
        ls.add(columnVillageStart());
        ls.add(columnVillageDaychange());
        ls.add(columnVillageEpilogue());
        ls.add(columnReceiveSecretSay());
        ls.add(columnReceiveAbilitySay());
        ls.add(columnReceiveAnchorSay());
        ls.add(columnKeyword());
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
    protected UniqueInfo cpui() { return hpcpui(columnVillagePlayerId()); }
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
        return cfi("FK_VILLAGE_PLAYER_NOTIFICATION_VILLAGE_PLAYER", "villagePlayer", this, VillagePlayerDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, true, false, false, false, null, null, false, "villagePlayerNotificationAsOne", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillagePlayerNotification"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillagePlayerNotificationCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillagePlayerNotificationBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillagePlayerNotification> getEntityType() { return VillagePlayerNotification.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillagePlayerNotification newEntity() { return new VillagePlayerNotification(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillagePlayerNotification)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillagePlayerNotification)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
