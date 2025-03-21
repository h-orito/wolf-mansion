package com.ort.dbflute.bsentity.dbmeta;

import java.util.List;
import java.util.Map;

import org.dbflute.Entity;
import org.dbflute.dbmeta.AbstractDBMeta;
import org.dbflute.dbmeta.info.*;
import org.dbflute.dbmeta.name.*;
import org.dbflute.dbmeta.property.PropertyGateway;
import org.dbflute.dbway.DBDef;
import com.ort.dbflute.allcommon.*;
import com.ort.dbflute.exentity.*;

/**
 * The DB meta of village_tag_item. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillageTagItemDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillageTagItemDbm _instance = new VillageTagItemDbm();
    private VillageTagItemDbm() {}
    public static VillageTagItemDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillageTagItem)et).getVillageTagItemCode(), (et, vl) -> {
            CDef.VillageTagItem cls = (CDef.VillageTagItem)gcls(et, columnVillageTagItemCode(), vl);
            if (cls != null) {
                ((VillageTagItem)et).setVillageTagItemCodeAsVillageTagItem(cls);
            } else {
                ((VillageTagItem)et).mynativeMappingVillageTagItemCode((String)vl);
            }
        }, "villageTagItemCode");
        setupEpg(_epgMap, et -> ((VillageTagItem)et).getVillageTagItemName(), (et, vl) -> ((VillageTagItem)et).setVillageTagItemName((String)vl), "villageTagItemName");
        setupEpg(_epgMap, et -> ((VillageTagItem)et).getDispOrder(), (et, vl) -> ((VillageTagItem)et).setDispOrder(cti(vl)), "dispOrder");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "village_tag_item";
    protected final String _tableDispName = "VILLAGE_TAG_ITEM";
    protected final String _tablePropertyName = "villageTagItem";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_TAG_ITEM", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillageTagItemCode = cci("VILLAGE_TAG_ITEM_CODE", "VILLAGE_TAG_ITEM_CODE", null, null, String.class, "villageTagItemCode", null, true, false, true, "VARCHAR", 50, 0, null, null, false, null, null, null, "villageTagList", CDef.DefMeta.VillageTagItem, false);
    protected final ColumnInfo _columnVillageTagItemName = cci("VILLAGE_TAG_ITEM_NAME", "VILLAGE_TAG_ITEM_NAME", null, null, String.class, "villageTagItemName", null, false, false, true, "VARCHAR", 50, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnDispOrder = cci("DISP_ORDER", "DISP_ORDER", null, null, Integer.class, "dispOrder", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);

    /**
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageTagItemCode() { return _columnVillageTagItemCode; }
    /**
     * VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillageTagItemName() { return _columnVillageTagItemName; }
    /**
     * DISP_ORDER: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDispOrder() { return _columnDispOrder; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnVillageTagItemCode());
        ls.add(columnVillageTagItemName());
        ls.add(columnDispOrder());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnVillageTagItemCode()); }
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

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * VILLAGE_TAG by VILLAGE_TAG_ITEM_CODE, named 'villageTagList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillageTagList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillageTagItemCode(), VillageTagDbm.getInstance().columnVillageTagItemCode());
        return cri("FK_VILLAGE_TAG_VILLAGE_TAG_ITEM", "villageTagList", this, VillageTagDbm.getInstance(), mp, false, "villageTagItem");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillageTagItem"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillageTagItemCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillageTagItemBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillageTagItem> getEntityType() { return VillageTagItem.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillageTagItem newEntity() { return new VillageTagItem(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillageTagItem)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillageTagItem)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
