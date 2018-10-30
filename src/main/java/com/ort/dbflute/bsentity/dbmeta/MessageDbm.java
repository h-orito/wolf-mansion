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
 * The DB meta of message. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class MessageDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final MessageDbm _instance = new MessageDbm();
    private MessageDbm() {}
    public static MessageDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((Message)et).getMessageId(), (et, vl) -> ((Message)et).setMessageId(cti(vl)), "messageId");
        setupEpg(_epgMap, et -> ((Message)et).getVillageId(), (et, vl) -> ((Message)et).setVillageId(cti(vl)), "villageId");
        setupEpg(_epgMap, et -> ((Message)et).getVillagePlayerId(), (et, vl) -> ((Message)et).setVillagePlayerId(cti(vl)), "villagePlayerId");
        setupEpg(_epgMap, et -> ((Message)et).getToVillagePlayerId(), (et, vl) -> ((Message)et).setToVillagePlayerId(cti(vl)), "toVillagePlayerId");
        setupEpg(_epgMap, et -> ((Message)et).getPlayerId(), (et, vl) -> ((Message)et).setPlayerId(cti(vl)), "playerId");
        setupEpg(_epgMap, et -> ((Message)et).getDay(), (et, vl) -> ((Message)et).setDay(cti(vl)), "day");
        setupEpg(_epgMap, et -> ((Message)et).getMessageTypeCode(), (et, vl) -> {
            CDef.MessageType cls = (CDef.MessageType)gcls(et, columnMessageTypeCode(), vl);
            if (cls != null) {
                ((Message)et).setMessageTypeCodeAsMessageType(cls);
            } else {
                ((Message)et).mynativeMappingMessageTypeCode((String)vl);
            }
        }, "messageTypeCode");
        setupEpg(_epgMap, et -> ((Message)et).getMessageNumber(), (et, vl) -> ((Message)et).setMessageNumber(cti(vl)), "messageNumber");
        setupEpg(_epgMap, et -> ((Message)et).getMessageContent(), (et, vl) -> ((Message)et).setMessageContent((String)vl), "messageContent");
        setupEpg(_epgMap, et -> ((Message)et).getMessageDatetime(), (et, vl) -> ((Message)et).setMessageDatetime(ctldt(vl)), "messageDatetime");
        setupEpg(_epgMap, et -> ((Message)et).getIsConvertDisable(), (et, vl) -> {
            ((Message)et).setIsConvertDisable((Boolean)vl);
        }, "isConvertDisable");
        setupEpg(_epgMap, et -> ((Message)et).getFaceTypeCode(), (et, vl) -> {
            CDef.FaceType cls = (CDef.FaceType)gcls(et, columnFaceTypeCode(), vl);
            if (cls != null) {
                ((Message)et).setFaceTypeCodeAsFaceType(cls);
            } else {
                ((Message)et).mynativeMappingFaceTypeCode((String)vl);
            }
        }, "faceTypeCode");
        setupEpg(_epgMap, et -> ((Message)et).getRegisterDatetime(), (et, vl) -> ((Message)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((Message)et).getRegisterTrace(), (et, vl) -> ((Message)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((Message)et).getUpdateDatetime(), (et, vl) -> ((Message)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((Message)et).getUpdateTrace(), (et, vl) -> ((Message)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((Message)et).getFaceType(), (et, vl) -> ((Message)et).setFaceType((OptionalEntity<FaceType>)vl), "faceType");
        setupEfpg(_efpgMap, et -> ((Message)et).getMessageType(), (et, vl) -> ((Message)et).setMessageType((OptionalEntity<MessageType>)vl), "messageType");
        setupEfpg(_efpgMap, et -> ((Message)et).getPlayer(), (et, vl) -> ((Message)et).setPlayer((OptionalEntity<Player>)vl), "player");
        setupEfpg(_efpgMap, et -> ((Message)et).getVillagePlayerByToVillagePlayerId(), (et, vl) -> ((Message)et).setVillagePlayerByToVillagePlayerId((OptionalEntity<VillagePlayer>)vl), "villagePlayerByToVillagePlayerId");
        setupEfpg(_efpgMap, et -> ((Message)et).getVillageDay(), (et, vl) -> ((Message)et).setVillageDay((OptionalEntity<VillageDay>)vl), "villageDay");
        setupEfpg(_efpgMap, et -> ((Message)et).getVillagePlayerByVillagePlayerId(), (et, vl) -> ((Message)et).setVillagePlayerByVillagePlayerId((OptionalEntity<VillagePlayer>)vl), "villagePlayerByVillagePlayerId");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "message";
    protected final String _tableDispName = "MESSAGE";
    protected final String _tablePropertyName = "message";
    protected final TableSqlName _tableSqlName = new TableSqlName("MESSAGE", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnMessageId = cci("MESSAGE_ID", "MESSAGE_ID", null, null, Integer.class, "messageId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villageDay", null, null, false);
    protected final ColumnInfo _columnVillagePlayerId = cci("VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", null, null, Integer.class, "villagePlayerId", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villagePlayerByVillagePlayerId", null, null, false);
    protected final ColumnInfo _columnToVillagePlayerId = cci("TO_VILLAGE_PLAYER_ID", "TO_VILLAGE_PLAYER_ID", null, null, Integer.class, "toVillagePlayerId", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villagePlayerByToVillagePlayerId", null, null, false);
    protected final ColumnInfo _columnPlayerId = cci("PLAYER_ID", "PLAYER_ID", null, null, Integer.class, "playerId", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, "player", null, null, false);
    protected final ColumnInfo _columnDay = cci("DAY", "DAY", null, null, Integer.class, "day", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "villageDay", null, null, false);
    protected final ColumnInfo _columnMessageTypeCode = cci("MESSAGE_TYPE_CODE", "MESSAGE_TYPE_CODE", null, null, String.class, "messageTypeCode", null, false, false, true, "VARCHAR", 20, 0, null, null, false, null, null, "messageType", null, CDef.DefMeta.MessageType, false);
    protected final ColumnInfo _columnMessageNumber = cci("MESSAGE_NUMBER", "MESSAGE_NUMBER", null, null, Integer.class, "messageNumber", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnMessageContent = cci("MESSAGE_CONTENT", "MESSAGE_CONTENT", null, null, String.class, "messageContent", null, false, false, true, "VARCHAR", 10000, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnMessageDatetime = cci("MESSAGE_DATETIME", "MESSAGE_DATETIME", null, null, java.time.LocalDateTime.class, "messageDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIsConvertDisable = cci("IS_CONVERT_DISABLE", "IS_CONVERT_DISABLE", null, null, Boolean.class, "isConvertDisable", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnFaceTypeCode = cci("FACE_TYPE_CODE", "FACE_TYPE_CODE", null, null, String.class, "faceTypeCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "faceType", null, CDef.DefMeta.FaceType, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * MESSAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMessageId() { return _columnMessageId; }
    /**
     * VILLAGE_ID: {UQ+, IX+, NotNull, INT UNSIGNED(10), FK to village_day}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerId() { return _columnVillagePlayerId; }
    /**
     * TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnToVillagePlayerId() { return _columnToVillagePlayerId; }
    /**
     * PLAYER_ID: {IX, INT UNSIGNED(10), FK to player}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPlayerId() { return _columnPlayerId; }
    /**
     * DAY: {NotNull, INT UNSIGNED(10), FK to village_day}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDay() { return _columnDay; }
    /**
     * MESSAGE_TYPE_CODE: {+UQ, IX, NotNull, VARCHAR(20), FK to message_type, classification=MessageType}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMessageTypeCode() { return _columnMessageTypeCode; }
    /**
     * MESSAGE_NUMBER: {+UQ, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMessageNumber() { return _columnMessageNumber; }
    /**
     * MESSAGE_CONTENT: {NotNull, VARCHAR(10000)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMessageContent() { return _columnMessageContent; }
    /**
     * MESSAGE_DATETIME: {NotNull, DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMessageDatetime() { return _columnMessageDatetime; }
    /**
     * IS_CONVERT_DISABLE: {NotNull, BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsConvertDisable() { return _columnIsConvertDisable; }
    /**
     * FACE_TYPE_CODE: {IX, VARCHAR(20), FK to face_type, classification=FaceType}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnFaceTypeCode() { return _columnFaceTypeCode; }
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
        ls.add(columnMessageId());
        ls.add(columnVillageId());
        ls.add(columnVillagePlayerId());
        ls.add(columnToVillagePlayerId());
        ls.add(columnPlayerId());
        ls.add(columnDay());
        ls.add(columnMessageTypeCode());
        ls.add(columnMessageNumber());
        ls.add(columnMessageContent());
        ls.add(columnMessageDatetime());
        ls.add(columnIsConvertDisable());
        ls.add(columnFaceTypeCode());
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
    protected UniqueInfo cpui() { return hpcpui(columnMessageId()); }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return false; }

    // -----------------------------------------------------
    //                                        Unique Element
    //                                        --------------
    public UniqueInfo uniqueOf() {
        List<ColumnInfo> ls = newArrayListSized(4);
        ls.add(columnVillageId());
        ls.add(columnMessageTypeCode());
        ls.add(columnMessageNumber());
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
     * FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignFaceType() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnFaceTypeCode(), FaceTypeDbm.getInstance().columnFaceTypeCode());
        return cfi("FK_MESSAGE_FACE_TYPE", "faceType", this, FaceTypeDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "messageList", false);
    }
    /**
     * MESSAGE_TYPE by my MESSAGE_TYPE_CODE, named 'messageType'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignMessageType() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnMessageTypeCode(), MessageTypeDbm.getInstance().columnMessageTypeCode());
        return cfi("FK_MESSAGE_MESSAGE_TYPE", "messageType", this, MessageTypeDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "messageList", false);
    }
    /**
     * PLAYER by my PLAYER_ID, named 'player'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignPlayer() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnPlayerId(), PlayerDbm.getInstance().columnPlayerId());
        return cfi("FK_MESSAGE_PLAYER", "player", this, PlayerDbm.getInstance(), mp, 2, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "messageList", false);
    }
    /**
     * VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillagePlayerByToVillagePlayerId() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnToVillagePlayerId(), VillagePlayerDbm.getInstance().columnVillagePlayerId());
        return cfi("FK_MESSAGE_VILLAGE_PLAYER_TO", "villagePlayerByToVillagePlayerId", this, VillagePlayerDbm.getInstance(), mp, 3, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "messageByToVillagePlayerIdList", false);
    }
    /**
     * VILLAGE_DAY by my VILLAGE_ID, DAY, named 'villageDay'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillageDay() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMapSized(4);
        mp.put(columnVillageId(), VillageDayDbm.getInstance().columnVillageId());
        mp.put(columnDay(), VillageDayDbm.getInstance().columnDay());
        return cfi("FK_MESSAGE_VILLAGE_DAY", "villageDay", this, VillageDayDbm.getInstance(), mp, 4, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "messageList", false);
    }
    /**
     * VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillagePlayerByVillagePlayerId() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerDbm.getInstance().columnVillagePlayerId());
        return cfi("FK_MESSAGE_VILLAGE_PLAYER", "villagePlayerByVillagePlayerId", this, VillagePlayerDbm.getInstance(), mp, 5, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "messageByVillagePlayerIdList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.Message"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.MessageCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.MessageBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<Message> getEntityType() { return Message.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public Message newEntity() { return new Message(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((Message)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((Message)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
