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
 * The DB meta of skill. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class SkillDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final SkillDbm _instance = new SkillDbm();
    private SkillDbm() {}
    public static SkillDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((Skill)et).getSkillCode(), (et, vl) -> ((Skill)et).setSkillCode((String)vl), "skillCode");
        setupEpg(_epgMap, et -> ((Skill)et).getSkillName(), (et, vl) -> ((Skill)et).setSkillName((String)vl), "skillName");
        setupEpg(_epgMap, et -> ((Skill)et).getCampCode(), (et, vl) -> ((Skill)et).setCampCode((String)vl), "campCode");
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
        setupEfpg(_efpgMap, et -> ((Skill)et).getCamp(), (et, vl) -> ((Skill)et).setCamp((OptionalEntity<Camp>)vl), "camp");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "skill";
    protected final String _tableDispName = "SKILL";
    protected final String _tablePropertyName = "skill";
    protected final TableSqlName _tableSqlName = new TableSqlName("SKILL", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnSkillCode = cci("SKILL_CODE", "SKILL_CODE", null, null, String.class, "skillCode", null, true, false, true, "VARCHAR", 20, 0, null, null, false, null, null, null, "villagePlayerList", null, false);
    protected final ColumnInfo _columnSkillName = cci("SKILL_NAME", "SKILL_NAME", null, null, String.class, "skillName", null, false, false, true, "VARCHAR", 20, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnCampCode = cci("CAMP_CODE", "CAMP_CODE", null, null, String.class, "campCode", null, false, false, true, "VARCHAR", 20, 0, null, null, false, null, null, "camp", null, null, false);

    /**
     * SKILL_CODE: {PK, NotNull, VARCHAR(20)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnSkillCode() { return _columnSkillCode; }
    /**
     * SKILL_NAME: {NotNull, VARCHAR(20)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnSkillName() { return _columnSkillName; }
    /**
     * CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCampCode() { return _columnCampCode; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnSkillCode());
        ls.add(columnSkillName());
        ls.add(columnCampCode());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnSkillCode()); }
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
     * CAMP by my CAMP_CODE, named 'camp'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignCamp() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnCampCode(), CampDbm.getInstance().columnCampCode());
        return cfi("FK_SKILL_CAMP", "camp", this, CampDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "skillList", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnSkillCode(), VillagePlayerDbm.getInstance().columnSkillCode());
        return cri("FK_VILLAGE_PLAYER_SKILL", "villagePlayerList", this, VillagePlayerDbm.getInstance(), mp, false, "skill");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.Skill"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.SkillCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.SkillBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<Skill> getEntityType() { return Skill.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public Skill newEntity() { return new Skill(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((Skill)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((Skill)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
