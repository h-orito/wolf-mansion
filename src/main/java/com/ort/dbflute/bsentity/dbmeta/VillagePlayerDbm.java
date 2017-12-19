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
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getChara(), (et, vl) -> ((VillagePlayer)et).setChara((OptionalEntity<Chara>)vl), "chara");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getPlayer(), (et, vl) -> ((VillagePlayer)et).setPlayer((OptionalEntity<Player>)vl), "player");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getSkill(), (et, vl) -> ((VillagePlayer)et).setSkill((OptionalEntity<Skill>)vl), "skill");
        setupEfpg(_efpgMap, et -> ((VillagePlayer)et).getVillage(), (et, vl) -> ((VillagePlayer)et).setVillage((OptionalEntity<Village>)vl), "village");
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
    protected final ColumnInfo _columnVillagePlayerId = cci("VILLAGE_PLAYER_ID", "VILLAGE_PLAYER_ID", null, null, Integer.class, "villagePlayerId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, "messageByToVillagePlayerIdList,messageByVillagePlayerIdList", null, false);
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "village", null, null, false);
    protected final ColumnInfo _columnPlayerId = cci("PLAYER_ID", "PLAYER_ID", null, null, Integer.class, "playerId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "player", null, null, false);
    protected final ColumnInfo _columnCharaId = cci("CHARA_ID", "CHARA_ID", null, null, Integer.class, "charaId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "chara", null, null, false);
    protected final ColumnInfo _columnSkillCode = cci("SKILL_CODE", "SKILL_CODE", null, null, String.class, "skillCode", null, false, false, false, "VARCHAR", 20, 0, null, null, false, null, null, "skill", null, CDef.DefMeta.Skill, false);

    /**
     * VILLAGE_PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerId() { return _columnVillagePlayerId; }
    /**
     * VILLAGE_ID: {UQ+, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * PLAYER_ID: {+UQ, IX, NotNull, INT UNSIGNED(10), FK to PLAYER}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnPlayerId() { return _columnPlayerId; }
    /**
     * CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaId() { return _columnCharaId; }
    /**
     * SKILL_CODE: {IX, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnSkillCode() { return _columnSkillCode; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnVillagePlayerId());
        ls.add(columnVillageId());
        ls.add(columnPlayerId());
        ls.add(columnCharaId());
        ls.add(columnSkillCode());
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

    // -----------------------------------------------------
    //                                        Unique Element
    //                                        --------------
    public UniqueInfo uniqueOf() {
        List<ColumnInfo> ls = newArrayListSized(4);
        ls.add(columnVillageId());
        ls.add(columnPlayerId());
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
        return cfi("FK_VILLAGE_PLAYER_CHARA", "chara", this, CharaDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerList", false);
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
     * SKILL by my SKILL_CODE, named 'skill'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignSkill() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnSkillCode(), SkillDbm.getInstance().columnSkillCode());
        return cfi("FK_VILLAGE_PLAYER_SKILL", "skill", this, SkillDbm.getInstance(), mp, 2, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerList", false);
    }
    /**
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillage() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDbm.getInstance().columnVilalgeId());
        return cfi("FK_VILLAGE_PLAYER_VILLAGE", "village", this, VillageDbm.getInstance(), mp, 3, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villagePlayerList", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
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

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============
    public boolean hasIdentity() { return true; }

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
