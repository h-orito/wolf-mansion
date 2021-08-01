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
 * The DB meta of SKILL_ALLOCATION. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class SkillAllocationDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final SkillAllocationDbm _instance = new SkillAllocationDbm();
    private SkillAllocationDbm() {}
    public static SkillAllocationDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getVillageId(), (et, vl) -> ((SkillAllocation)et).setVillageId(cti(vl)), "villageId");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getSkillCode(), (et, vl) -> {
            CDef.Skill cls = (CDef.Skill)gcls(et, columnSkillCode(), vl);
            if (cls != null) {
                ((SkillAllocation)et).setSkillCodeAsSkill(cls);
            } else {
                ((SkillAllocation)et).mynativeMappingSkillCode((String)vl);
            }
        }, "skillCode");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getMinNum(), (et, vl) -> ((SkillAllocation)et).setMinNum(cti(vl)), "minNum");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getMaxNum(), (et, vl) -> ((SkillAllocation)et).setMaxNum(cti(vl)), "maxNum");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getAllocation(), (et, vl) -> ((SkillAllocation)et).setAllocation(cti(vl)), "allocation");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getRegisterDatetime(), (et, vl) -> ((SkillAllocation)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getRegisterTrace(), (et, vl) -> ((SkillAllocation)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getUpdateDatetime(), (et, vl) -> ((SkillAllocation)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((SkillAllocation)et).getUpdateTrace(), (et, vl) -> ((SkillAllocation)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((SkillAllocation)et).getSkill(), (et, vl) -> ((SkillAllocation)et).setSkill((OptionalEntity<Skill>)vl), "skill");
        setupEfpg(_efpgMap, et -> ((SkillAllocation)et).getVillage(), (et, vl) -> ((SkillAllocation)et).setVillage((OptionalEntity<Village>)vl), "village");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "SKILL_ALLOCATION";
    protected final String _tableDispName = "SKILL_ALLOCATION";
    protected final String _tablePropertyName = "skillAllocation";
    protected final TableSqlName _tableSqlName = new TableSqlName("SKILL_ALLOCATION", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, true, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "village", null, null, false);
    protected final ColumnInfo _columnSkillCode = cci("SKILL_CODE", "SKILL_CODE", null, null, String.class, "skillCode", null, true, false, true, "VARCHAR", 20, 0, null, null, false, null, null, "skill", null, CDef.DefMeta.Skill, false);
    protected final ColumnInfo _columnMinNum = cci("MIN_NUM", "MIN_NUM", null, null, Integer.class, "minNum", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnMaxNum = cci("MAX_NUM", "MAX_NUM", null, null, Integer.class, "maxNum", null, false, false, false, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnAllocation = cci("ALLOCATION", "ALLOCATION", null, null, Integer.class, "allocation", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * SKILL_CODE: {PK, IX, NotNull, VARCHAR(20), FK to SKILL, classification=Skill}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnSkillCode() { return _columnSkillCode; }
    /**
     * MIN_NUM: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMinNum() { return _columnMinNum; }
    /**
     * MAX_NUM: {INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnMaxNum() { return _columnMaxNum; }
    /**
     * ALLOCATION: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnAllocation() { return _columnAllocation; }
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
        ls.add(columnVillageId());
        ls.add(columnSkillCode());
        ls.add(columnMinNum());
        ls.add(columnMaxNum());
        ls.add(columnAllocation());
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
    protected UniqueInfo cpui() {
        List<ColumnInfo> ls = newArrayListSized(4);
        ls.add(columnVillageId());
        ls.add(columnSkillCode());
        return hpcpui(ls);
    }
    public boolean hasPrimaryKey() { return true; }
    public boolean hasCompoundPrimaryKey() { return true; }

    // ===================================================================================
    //                                                                       Relation Info
    //                                                                       =============
    // cannot cache because it uses related DB meta instance while booting
    // (instead, cached by super's collection)
    // -----------------------------------------------------
    //                                      Foreign Property
    //                                      ----------------
    /**
     * SKILL by my SKILL_CODE, named 'skill'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignSkill() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnSkillCode(), SkillDbm.getInstance().columnSkillCode());
        return cfi("FK_SKILL_ALLOCATION_SKILL", "skill", this, SkillDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "skillAllocationList", false);
    }
    /**
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillage() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDbm.getInstance().columnVillageId());
        return cfi("FK_SKILL_ALLOCATION_VILLAGE", "village", this, VillageDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "skillAllocationList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.SkillAllocation"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.SkillAllocationCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.SkillAllocationBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<SkillAllocation> getEntityType() { return SkillAllocation.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public SkillAllocation newEntity() { return new SkillAllocation(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((SkillAllocation)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((SkillAllocation)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
