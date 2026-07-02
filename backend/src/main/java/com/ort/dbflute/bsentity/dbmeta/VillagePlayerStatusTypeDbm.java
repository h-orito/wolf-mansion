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
 * The DB meta of VILLAGE_PLAYER_STATUS_TYPE. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class VillagePlayerStatusTypeDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final VillagePlayerStatusTypeDbm _instance = new VillagePlayerStatusTypeDbm();
    private VillagePlayerStatusTypeDbm() {}
    public static VillagePlayerStatusTypeDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((VillagePlayerStatusType)et).getVillagePlayerStatusTypeCode(), (et, vl) -> {
            CDef.VillagePlayerStatusType cls = (CDef.VillagePlayerStatusType)gcls(et, columnVillagePlayerStatusTypeCode(), vl);
            if (cls != null) {
                ((VillagePlayerStatusType)et).setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(cls);
            } else {
                ((VillagePlayerStatusType)et).mynativeMappingVillagePlayerStatusTypeCode((String)vl);
            }
        }, "villagePlayerStatusTypeCode");
        setupEpg(_epgMap, et -> ((VillagePlayerStatusType)et).getVillagePlayerStatusTypeName(), (et, vl) -> ((VillagePlayerStatusType)et).setVillagePlayerStatusTypeName((String)vl), "villagePlayerStatusTypeName");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "VILLAGE_PLAYER_STATUS_TYPE";
    protected final String _tableDispName = "VILLAGE_PLAYER_STATUS_TYPE";
    protected final String _tablePropertyName = "villagePlayerStatusType";
    protected final TableSqlName _tableSqlName = new TableSqlName("VILLAGE_PLAYER_STATUS_TYPE", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnVillagePlayerStatusTypeCode = cci("VILLAGE_PLAYER_STATUS_TYPE_CODE", "VILLAGE_PLAYER_STATUS_TYPE_CODE", null, null, String.class, "villagePlayerStatusTypeCode", null, true, false, true, "VARCHAR", 20, 0, null, null, false, null, null, null, "villagePlayerStatusList", CDef.DefMeta.VillagePlayerStatusType, false);
    protected final ColumnInfo _columnVillagePlayerStatusTypeName = cci("VILLAGE_PLAYER_STATUS_TYPE_NAME", "VILLAGE_PLAYER_STATUS_TYPE_NAME", null, null, String.class, "villagePlayerStatusTypeName", null, false, false, true, "VARCHAR", 20, 0, null, null, false, null, null, null, null, null, false);

    /**
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerStatusTypeCode() { return _columnVillagePlayerStatusTypeCode; }
    /**
     * VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnVillagePlayerStatusTypeName() { return _columnVillagePlayerStatusTypeName; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnVillagePlayerStatusTypeCode());
        ls.add(columnVillagePlayerStatusTypeName());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnVillagePlayerStatusTypeCode()); }
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
     * VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerStatusList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnVillagePlayerStatusTypeCode(), VillagePlayerStatusDbm.getInstance().columnVillagePlayerStatusCode());
        return cri("FK_VILLAGE_PLAYER_STATUS_VILLAGE_PLAYER_STATUS_TYPE", "villagePlayerStatusList", this, VillagePlayerStatusDbm.getInstance(), mp, false, "villagePlayerStatusType");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.VillagePlayerStatusType"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.VillagePlayerStatusTypeCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.VillagePlayerStatusTypeBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<VillagePlayerStatusType> getEntityType() { return VillagePlayerStatusType.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public VillagePlayerStatusType newEntity() { return new VillagePlayerStatusType(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((VillagePlayerStatusType)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((VillagePlayerStatusType)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
