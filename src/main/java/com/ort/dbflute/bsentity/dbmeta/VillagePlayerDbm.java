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
 * The DB meta of VILLAGE_PLAYER. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillagePlayerDbm _instance = new VillagePlayerDbm();
    private VillagePlayerDbm() {}
    public static VillagePlayerDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getVillagePlayerId(), (et, vl) -> ((VillagePlayer)et).setVillagePlayerId(cti(vl)), "villagePlayerId");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getVillageId(), (et, vl) -> ((VillagePlayer)et).setVillageId(cti(vl)), "villageId");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getPlayerId(), (et, vl) -> ((VillagePlayer)et).setPlayerId(cti(vl)), "playerId");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getCharaId(), (et, vl) -> ((VillagePlayer)et).setCharaId(cti(vl)), "charaId");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getSkillCode(), (et, vl) -> {
            CDef.Skill cls = (CDef.Skill)gcls(et, columnSkillCode(), vl);
            if (cls != null) {
                ((VillagePlayer)et).setSkillCodeAsSkill(cls);
            } else {
                ((VillagePlayer)et).mynativeMappingSkillCode((String)vl);
            }
        }, "skillCode");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getRequestSkillCode(), (et, vl) -> {
            CDef.Skill cls = (CDef.Skill)gcls(et, columnRequestSkillCode(), vl);
            if (cls != null) {
                ((VillagePlayer)et).setRequestSkillCodeAsSkill(cls);
            } else {
                ((VillagePlayer)et).mynativeMappingRequestSkillCode((String)vl);
            }
        }, "requestSkillCode");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getSecondRequestSkillCode(), (et, vl) -> {
            CDef.Skill cls = (CDef.Skill)gcls(et, columnSecondRequestSkillCode(), vl);
            if (cls != null) {
                ((VillagePlayer)et).setSecondRequestSkillCodeAsSkill(cls);
            } else {
                ((VillagePlayer)et).mynativeMappingSecondRequestSkillCode((String)vl);
            }
        }, "secondRequestSkillCode");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getRoomNumber(), (et, vl) -> ((VillagePlayer)et).setRoomNumber(cti(vl)), "roomNumber");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getIsDead(), (et, vl) -> {
            ((VillagePlayer)et).setIsDead((Boolean)vl);
        }, "isDead");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getIsSpectator(), (et, vl) -> {
            ((VillagePlayer)et).setIsSpectator((Boolean)vl);
        }, "isSpectator");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getDeadReasonCode(), (et, vl) -> {
            CDef.DeadReason cls = (CDef.DeadReason)gcls(et, columnDeadReasonCode(), vl);
            if (cls != null) {
                ((VillagePlayer)et).setDeadReasonCodeAsDeadReason(cls);
            } else {
                ((VillagePlayer)et).mynativeMappingDeadReasonCode((String)vl);
            }
        }, "deadReasonCode");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getDeadDay(), (et, vl) -> ((VillagePlayer)et).setDeadDay(cti(vl)), "deadDay");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getIsGone(), (et, vl) -> {
            ((VillagePlayer)et).setIsGone((Boolean)vl);
        }, "isGone");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getLastAccessDatetime(), (et, vl) -> ((VillagePlayer)et).setLastAccessDatetime(ctldt(vl)), "lastAccessDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getCampCode(), (et, vl) -> ((VillagePlayer)et).setCampCode((String)vl), "campCode");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getIsWin(), (et, vl) -> {
            ((VillagePlayer)et).setIsWin((Boolean)vl);
        }, "isWin");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getCharaName(), (et, vl) -> ((VillagePlayer)et).setCharaName((String)vl), "charaName");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getCharaShortName(), (et, vl) -> ((VillagePlayer)et).setCharaShortName((String)vl), "charaShortName");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getMemo(), (et, vl) -> ((VillagePlayer)et).setMemo((String)vl), "memo");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getRegisterDatetime(), (et, vl) -> ((VillagePlayer)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getRegisterTrace(), (et, vl) -> ((VillagePlayer)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getUpdateDatetime(), (et, vl) -> ((VillagePlayer)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((VillagePlayer)et).getUpdateTrace(), (et, vl) -> ((VillagePlayer)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getDeadReason(), (et, vl) -> ((VillagePlayer)et).setDeadReason((OptionalEntity<DeadReason>)vl), "deadReason");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getPlayer(), (et, vl) -> ((VillagePlayer)et).setPlayer((OptionalEntity<Player>)vl), "player");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getSkillByRequestSkillCode(), (et, vl) -> ((VillagePlayer)et).setSkillByRequestSkillCode((OptionalEntity<Skill>)vl), "skillByRequestSkillCode");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getSkillBySecondRequestSkillCode(), (et, vl) -> ((VillagePlayer)et).setSkillBySecondRequestSkillCode((OptionalEntity<Skill>)vl), "skillBySecondRequestSkillCode");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getSkillBySkillCode(), (et, vl) -> ((VillagePlayer)et).setSkillBySkillCode((OptionalEntity<Skill>)vl), "skillBySkillCode");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getVillage(), (et, vl) -> ((VillagePlayer)et).setVillage((OptionalEntity<Village>)vl), "village");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getVillagePlayerNotificationAsOne(), (et, vl) -> ((VillagePlayer)et).setVillagePlayerNotificationAsOne((OptionalEntity<VillagePlayerNotification>)vl), "villagePlayerNotificationAsOne");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VILLAGE_PLAYER";
    protected final String _tableDispName = "VILLAGE_PLAYER";
    protected final String _tablePropertyName = "villagePlayer";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_PLAYER", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillagePlayerId = cci("VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", null, null, Integer.class, "villagePlayerId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, "commitList,messageByToVillagePlayerIdList,messageByVillagePlayerIdList,messageSendtoList,villagePlayerAccessInfoList,villagePlayerDeadHistoryList,villagePlayerRoomHistoryList,villagePlayerSkillHistoryList,villagePlayerStatusByToVillagePlayerIdList,villagePlayerStatusByVillagePlayerIdList", null, false);
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "village", null, null, false);
    protected final ColumnInfo _columnPlayerId = cci("PLAYER_ID", "PLAYER_ID", null, null, Integer.class, "playerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "player", null, null, false);
    protected final ColumnInfo _columnCharaId = cci("CHARA_ID", "CHARA_ID", null, null, Integer.class, "charaId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnSkillCode = cci("SKILL_CODE", "SKILL_CODE", null, null, String.class, "skillCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "skillBySkillCode", null, CDef.DefMeta.Skill, false);
    protected final ColumnInfo _columnRequestSkillCode = cci("REQUEST_SKILL_CODE", "REQUEST_SKILL_CODE", null, null, String.class, "requestSkillCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "skillByRequestSkillCode", null, CDef.DefMeta.Skill, false);
    protected final ColumnInfo _columnSecondRequestSkillCode = cci("SECOND_REQUEST_SKILL_CODE", "SECOND_REQUEST_SKILL_CODE", null, null, String.class, "secondRequestSkillCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "skillBySecondRequestSkillCode", null, CDef.DefMeta.Skill, false);
    protected final ColumnInfo _columnRoomNumber = cci("ROOM_NUMBER", "ROOM_NUMBER", null, null, Integer.class, "roomNumber", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIsDead = cci("IS_DEAD", "IS_DEAD", null, null, Boolean.class, "isDead", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnIsSpectator = cci("IS_SPECTATOR", "IS_SPECTATOR", null, null, Boolean.class, "isSpectator", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnDeadReasonCode = cci("DEAD_REASON_CODE", "DEAD_REASON_CODE", null, null, String.class, "deadReasonCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "deadReason", null, CDef.DefMeta.DeadReason, false);
    protected final ColumnInfo _columnDeadDay = cci("DEAD_DAY", "DEAD_DAY", null, null, Integer.class, "deadDay", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIsGone = cci("IS_GONE", "IS_GONE", null, null, Boolean.class, "isGone", null, false, false, true, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnLastAccessDatetime = cci("LAST_ACCESS_DATETIME", "LAST_ACCESS_DATETIME", null, null, java.time.LocalDateTime.class, "lastAccessDatetime", null, false, false, false, "DATETIME", 19, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnCampCode = cci("CAMP_CODE", "CAMP_CODE", null, null, String.class, "campCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnIsWin = cci("IS_WIN", "IS_WIN", null, null, Boolean.class, "isWin", null, false, false, false, "BIT", null, null, null, null, false, null, null, null, null, CDef.DefMeta.Flg, false);
    protected final ColumnInfo _columnCharaName = cci("CHARA_NAME", "CHARA_NAME", null, null, String.class, "charaName", null, false, false, true, "VARCHAR", 40, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnCharaShortName = cci("CHARA_SHORT_NAME", "CHARA_SHORT_NAME", null, null, String.class, "charaShortName", null, false, false, true, "CHAR", 1, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnMemo = cci("MEMO", "MEMO", null, null, String.class, "memo", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerId() { return _columnVillagePlayerId; }
    /**
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPlayerId() { return _columnPlayerId; }
    /**
     * CHARA_ID: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaId() { return _columnCharaId; }
    /**
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnSkillCode() { return _columnSkillCode; }
    /**
     * REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRequestSkillCode() { return _columnRequestSkillCode; }
    /**
     * SECOND_REQUEST_SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnSecondRequestSkillCode() { return _columnSecondRequestSkillCode; }
    /**
     * ROOM_NUMBER: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnRoomNumber() { return _columnRoomNumber; }
    /**
     * IS_DEAD: {NotNull, BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsDead() { return _columnIsDead; }
    /**
     * IS_SPECTATOR: {NotNull, BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsSpectator() { return _columnIsSpectator; }
    /**
     * DEAD_REASON_CODE: {IX, VARCHAR(20), FK to DEAD_REASON, classification=DeadReason}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDeadReasonCode() { return _columnDeadReasonCode; }
    /**
     * DEAD_DAY: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDeadDay() { return _columnDeadDay; }
    /**
     * IS_GONE: {NotNull, BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsGone() { return _columnIsGone; }
    /**
     * LAST_ACCESS_DATETIME: {DATETIME(19)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnLastAccessDatetime() { return _columnLastAccessDatetime; }
    /**
     * CAMP_CODE: {VARCHAR(20)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCampCode() { return _columnCampCode; }
    /**
     * IS_WIN: {BIT, classification=Flg}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnIsWin() { return _columnIsWin; }
    /**
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaName() { return _columnCharaName; }
    /**
     * CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaShortName() { return _columnCharaShortName; }
    /**
     * MEMO: {VARCHAR(20)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMemo() { return _columnMemo; }
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
        ls.add(columnVillageId());
        ls.add(columnPlayerId());
        ls.add(columnCharaId());
        ls.add(columnSkillCode());
        ls.add(columnRequestSkillCode());
        ls.add(columnSecondRequestSkillCode());
        ls.add(columnRoomNumber());
        ls.add(columnIsDead());
        ls.add(columnIsSpectator());
        ls.add(columnDeadReasonCode());
        ls.add(columnDeadDay());
        ls.add(columnIsGone());
        ls.add(columnLastAccessDatetime());
        ls.add(columnCampCode());
        ls.add(columnIsWin());
        ls.add(columnCharaName());
        ls.add(columnCharaShortName());
        ls.add(columnMemo());
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
     * DEAD_REASON by my DEAD_REASON_CODE, named 'deadReason'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignDeadReason() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnDeadReasonCode(), DeadReasonDbm.getInstance().columnDeadReasonCode());
        return cfi("FK_VILLAGE_PLAYER_DEAD_REASON", "deadReason", this, DeadReasonDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerList", false);
    }
    /**
     * PLAYER by my PLAYER_ID, named 'player'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignPlayer() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnPlayerId(), PlayerDbm.getInstance().columnPlayerId());
        return cfi("FK_VILLAGE_PLAYER_PLAYER", "player", this, PlayerDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerList", false);
    }
    /**
     * SKILL by my REQUEST_SKILL_CODE, named 'skillByRequestSkillCode'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignSkillByRequestSkillCode() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnRequestSkillCode(), SkillDbm.getInstance().columnSkillCode());
        return cfi("FK_VILLAGE_PLAYER_SKILL_REQUEST", "skillByRequestSkillCode", this, SkillDbm.getInstance(), mp, 2, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerByRequestSkillCodeList", false);
    }
    /**
     * SKILL by my SECOND_REQUEST_SKILL_CODE, named 'skillBySecondRequestSkillCode'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignSkillBySecondRequestSkillCode() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnSecondRequestSkillCode(), SkillDbm.getInstance().columnSkillCode());
        return cfi("FK_VILLAGE_PLAYER_SECOND_SKILL_REQ", "skillBySecondRequestSkillCode", this, SkillDbm.getInstance(), mp, 3, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerBySecondRequestSkillCodeList", false);
    }
    /**
     * SKILL by my SKILL_CODE, named 'skillBySkillCode'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignSkillBySkillCode() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnSkillCode(), SkillDbm.getInstance().columnSkillCode());
        return cfi("FK_VILLAGE_PLAYER_SKILL", "skillBySkillCode", this, SkillDbm.getInstance(), mp, 4, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerBySkillCodeList", false);
    }
    /**
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillage() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDbm.getInstance().columnVillageId());
        return cfi("FK_VILLAGE_PLAYER_VILLAGE", "village", this, VillageDbm.getInstance(), mp, 5, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerList", false);
    }
    /**
     * VILLAGE_PLAYER_NOTIFICATION by VILLAGE_PLAYER_ID, named 'villagePlayerNotificationAsOne'.
     * @return The information object of foreign property(referrer-as-one). (NotNull)
     */
    public ForeignInfo foreignVillagePlayerNotificationAsOne() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerNotificationDbm.getInstance().columnVillagePlayerId());
        return cfi("FK_VILLAGE_PLAYER_NOTIFICATION_VILLAGE_PLAYER", "villagePlayerNotificationAsOne", this, VillagePlayerNotificationDbm.getInstance(), mp, 6, org.dbflute.optional.OptionalEntity.class, true, false, true, false, null, null, false, "villagePlayer", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * COMMIT by VILLAGE_PLAYER_ID, named 'commitList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerCommitList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), CommitDbm.getInstance().columnVillagePlayerId());
        return cri("FK_COMMIT_VILLAGE_PLAYER", "commitList", this, CommitDbm.getInstance(), mp, false, "villagePlayer");
    }
    /**
     * MESSAGE by TO_VILLAGE_PLAYER_ID, named 'messageByToVillagePlayerIdList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerMessageByToVillagePlayerIdList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), MessageDbm.getInstance().columnToVillagePlayerId());
        return cri("FK_MESSAGE_VILLAGE_PLAYER_TO", "messageByToVillagePlayerIdList", this, MessageDbm.getInstance(), mp, false, "villagePlayerByToVillagePlayerId");
    }
    /**
     * MESSAGE by VILLAGE_PLAYER_ID, named 'messageByVillagePlayerIdList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerMessageByVillagePlayerIdList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), MessageDbm.getInstance().columnVillagePlayerId());
        return cri("FK_MESSAGE_VILLAGE_PLAYER", "messageByVillagePlayerIdList", this, MessageDbm.getInstance(), mp, false, "villagePlayerByVillagePlayerId");
    }
    /**
     * MESSAGE_SENDTO by VILLAGE_PLAYER_ID, named 'messageSendtoList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerMessageSendtoList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), MessageSendtoDbm.getInstance().columnVillagePlayerId());
        return cri("FK_MESSAGE_SENDTO_VILLAGE_PLAYER", "messageSendtoList", this, MessageSendtoDbm.getInstance(), mp, false, "villagePlayer");
    }
    /**
     * VILLAGE_PLAYER_ACCESS_INFO by VILLAGE_PLAYER_ID, named 'villagePlayerAccessInfoList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerAccessInfoList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerAccessInfoDbm.getInstance().columnVillagePlayerId());
        return cri("FK_VILLAGE_PLAYER_ACCESS_INFO_VILLAGE_PLAYER", "villagePlayerAccessInfoList", this, VillagePlayerAccessInfoDbm.getInstance(), mp, false, "villagePlayer");
    }
    /**
     * VILLAGE_PLAYER_DEAD_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerDeadHistoryList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerDeadHistoryList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerDeadHistoryDbm.getInstance().columnVillagePlayerId());
        return cri("FK_VILLAGE_PLAYER_DEAD_HISTORY_VILLAGE_PLAYER", "villagePlayerDeadHistoryList", this, VillagePlayerDeadHistoryDbm.getInstance(), mp, false, "villagePlayer");
    }
    /**
     * VILLAGE_PLAYER_ROOM_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerRoomHistoryList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerRoomHistoryList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerRoomHistoryDbm.getInstance().columnVillagePlayerId());
        return cri("FK_VILLAGE_PLAYER_ROOM_HISTORY_VILLAGE_PLAYER", "villagePlayerRoomHistoryList", this, VillagePlayerRoomHistoryDbm.getInstance(), mp, false, "villagePlayer");
    }
    /**
     * VILLAGE_PLAYER_SKILL_HISTORY by VILLAGE_PLAYER_ID, named 'villagePlayerSkillHistoryList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerSkillHistoryList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerSkillHistoryDbm.getInstance().columnVillagePlayerId());
        return cri("FK_VILLAGE_PLAYER_SKILL_HISTORY_VILLAGE_PLAYER", "villagePlayerSkillHistoryList", this, VillagePlayerSkillHistoryDbm.getInstance(), mp, false, "villagePlayer");
    }
    /**
     * VILLAGE_PLAYER_STATUS by TO_VILLAGE_PLAYER_ID, named 'villagePlayerStatusByToVillagePlayerIdList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerStatusByToVillagePlayerIdList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerStatusDbm.getInstance().columnToVillagePlayerId());
        return cri("FK_VILLAGE_PLAYER_STATUS_TO_VILLAGE_PLAYER", "villagePlayerStatusByToVillagePlayerIdList", this, VillagePlayerStatusDbm.getInstance(), mp, false, "villagePlayerByToVillagePlayerId");
    }
    /**
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_ID, named 'villagePlayerStatusByVillagePlayerIdList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerStatusByVillagePlayerIdList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerId(), VillagePlayerStatusDbm.getInstance().columnVillagePlayerId());
        return cri("FK_VILLAGE_PLAYER_STATUS_VILLAGE_PLAYER", "villagePlayerStatusByVillagePlayerIdList", this, VillagePlayerStatusDbm.getInstance(), mp, false, "villagePlayerByVillagePlayerId");
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillagePlayer"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillagePlayerCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillagePlayerBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillagePlayer> getEntityType() { return VillagePlayer.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillagePlayer newEntity() { return new VillagePlayer(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillagePlayer)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillagePlayer)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
