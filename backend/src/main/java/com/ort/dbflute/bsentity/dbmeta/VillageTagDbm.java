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
 * The DB meta of VILLAGE_TAG. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillageTagDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillageTagDbm _instance = new VillageTagDbm();
    private VillageTagDbm() {}
    public static VillageTagDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillageTag)et).getVillageTagId(), (et, vl) -> ((VillageTag)et).setVillageTagId(cti(vl)), "villageTagId");
        setupEpg(_epgMap, et -> ((VillageTag)et).getVillageId(), (et, vl) -> ((VillageTag)et).setVillageId(cti(vl)), "villageId");
        setupEpg(_epgMap, et -> ((VillageTag)et).getVillageTagItemCode(), (et, vl) -> {
            CDef.VillageTagItem cls = (CDef.VillageTagItem)gcls(et, columnVillageTagItemCode(), vl);
            if (cls != null) {
                ((VillageTag)et).setVillageTagItemCodeAsVillageTagItem(cls);
            } else {
                ((VillageTag)et).mynativeMappingVillageTagItemCode((String)vl);
            }
        }, "villageTagItemCode");
        setupEpg(_epgMap, et -> ((VillageTag)et).getRegisterDatetime(), (et, vl) -> ((VillageTag)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((VillageTag)et).getRegisterTrace(), (et, vl) -> ((VillageTag)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((VillageTag)et).getUpdateDatetime(), (et, vl) -> ((VillageTag)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((VillageTag)et).getUpdateTrace(), (et, vl) -> ((VillageTag)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((VillageTag)et).getVillage(), (et, vl) -> ((VillageTag)et).setVillage((OptionalEntity<Village>)vl), "village");
        setupEfpg(_efpgMap, et -> ((VillageTag)et).getVillageTagItem(), (et, vl) -> ((VillageTag)et).setVillageTagItem((OptionalEntity<VillageTagItem>)vl), "villageTagItem");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VILLAGE_TAG";
    protected final String _tableDispName = "VILLAGE_TAG";
    protected final String _tablePropertyName = "villageTag";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_TAG", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillageTagId = cci("VILLAGE_TAG_ID", "VILLAGE_TAG_ID", null, null, Integer.class, "villageTagId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnVillageId = cci("VILLAGE_ID", "VILLAGE_ID", null, null, Integer.class, "villageId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "village", null, null, false);
    protected final ColumnInfo _columnVillageTagItemCode = cci("VILLAGE_TAG_ITEM_CODE", "VILLAGE_TAG_ITEM_CODE", null, null, String.class, "villageTagItemCode", null, false, false, true, "VARCHAR", 50, 0, null, null, false, null, null, "villageTagItem", null, CDef.DefMeta.VillageTagItem, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * VILLAGE_TAG_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageTagId() { return _columnVillageTagId; }
    /**
     * VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to VILLAGE}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageId() { return _columnVillageId; }
    /**
     * VILLAGE_TAG_ITEM_CODE: {IX, NotNull, VARCHAR(50), FK to VILLAGE_TAG_ITEM, classification=VillageTagItem}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageTagItemCode() { return _columnVillageTagItemCode; }
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
        ls.add(columnVillageTagId());
        ls.add(columnVillageId());
        ls.add(columnVillageTagItemCode());
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
    protected UniqueInfo cpui() { return hpcpui(columnVillageTagId()); }
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
     * VILLAGE by my VILLAGE_ID, named 'village'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillage() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageId(), VillageDbm.getInstance().columnVillageId());
        return cfi("FK_VILLAGE_TAG_VILLAGE", "village", this, VillageDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villageTagList", false);
    }
    /**
     * VILLAGE_TAG_ITEM by my VILLAGE_TAG_ITEM_CODE, named 'villageTagItem'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignVillageTagItem() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageTagItemCode(), VillageTagItemDbm.getInstance().columnVillageTagItemCode());
        return cfi("FK_VILLAGE_TAG_VILLAGE_TAG_ITEM", "villageTagItem", this, VillageTagItemDbm.getInstance(), mp, 1, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "villageTagList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillageTag"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillageTagCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillageTagBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillageTag> getEntityType() { return VillageTag.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillageTag newEntity() { return new VillageTag(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillageTag)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillageTag)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
