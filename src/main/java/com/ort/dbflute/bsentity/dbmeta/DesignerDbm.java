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
 * The DB meta of designer. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class DesignerDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final DesignerDbm _instance = new DesignerDbm();
    private DesignerDbm() {}
    public static DesignerDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((Designer)et).getDesignerId(), (et, vl) -> ((Designer)et).setDesignerId(cti(vl)), "designerId");
        setupEpg(_epgMap, et -> ((Designer)et).getDesignerName(), (et, vl) -> ((Designer)et).setDesignerName((String)vl), "designerName");
    }
    public PropertyGateway findPropertyGateway(String prop)
    { return doFindEpg(_epgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "designer";
    protected final String _tableDispName = "designer";
    protected final String _tablePropertyName = "designer";
    protected final TableSqlName _tableSqlName = new TableSqlName("designer", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnDesignerId = cci("DESIGNER_ID", "DESIGNER_ID", null, null, Integer.class, "designerId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, "charaGroupList", null, false);
    protected final ColumnInfo _columnDesignerName = cci("DESIGNER_NAME", "DESIGNER_NAME", null, null, String.class, "designerName", null, false, false, true, "VARCHAR", 40, 0, null, null, false, null, null, null, null, null, false);

    /**
     * DESIGNER_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDesignerId() { return _columnDesignerId; }
    /**
     * DESIGNER_NAME: {NotNull, VARCHAR(40)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDesignerName() { return _columnDesignerName; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnDesignerId());
        ls.add(columnDesignerName());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnDesignerId()); }
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
     * chara_group by DESIGNER_ID, named 'charaGroupList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerCharaGroupList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnDesignerId(), CharaGroupDbm.getInstance().columnDesignerId());
        return cri("FK_CHARA_GROUP_DESIGNER", "charaGroupList", this, CharaGroupDbm.getInstance(), mp, false, "designer");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============
    public boolean hasIdentity() { return true; }

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.Designer"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.DesignerCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.DesignerBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<Designer> getEntityType() { return Designer.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public Designer newEntity() { return new Designer(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((Designer)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((Designer)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
