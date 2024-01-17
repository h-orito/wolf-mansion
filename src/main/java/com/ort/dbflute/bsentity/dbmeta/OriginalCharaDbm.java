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
 * The DB meta of ORIGINAL_CHARA. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class OriginalCharaDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final OriginalCharaDbm _instance = new OriginalCharaDbm();
    private OriginalCharaDbm() {}
    public static OriginalCharaDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((OriginalChara)et).getOriginalCharaId(), (et, vl) -> ((OriginalChara)et).setOriginalCharaId(cti(vl)), "originalCharaId");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getOriginalCharaName(), (et, vl) -> ((OriginalChara)et).setOriginalCharaName((String)vl), "originalCharaName");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getOriginalCharaShortName(), (et, vl) -> ((OriginalChara)et).setOriginalCharaShortName((String)vl), "originalCharaShortName");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getOriginalCharaGroupId(), (et, vl) -> ((OriginalChara)et).setOriginalCharaGroupId(cti(vl)), "originalCharaGroupId");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getDisplayWidth(), (et, vl) -> ((OriginalChara)et).setDisplayWidth(cti(vl)), "displayWidth");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getDisplayHeight(), (et, vl) -> ((OriginalChara)et).setDisplayHeight(cti(vl)), "displayHeight");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getRegisterDatetime(), (et, vl) -> ((OriginalChara)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getRegisterTrace(), (et, vl) -> ((OriginalChara)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getUpdateDatetime(), (et, vl) -> ((OriginalChara)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((OriginalChara)et).getUpdateTrace(), (et, vl) -> ((OriginalChara)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((OriginalChara)et).getOriginalCharaGroup(), (et, vl) -> ((OriginalChara)et).setOriginalCharaGroup((OptionalEntity<OriginalCharaGroup>)vl), "originalCharaGroup");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "ORIGINAL_CHARA";
    protected final String _tableDispName = "ORIGINAL_CHARA";
    protected final String _tablePropertyName = "originalChara";
    protected final TableSqlName _tableSqlName = new TableSqlName("ORIGINAL_CHARA", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnOriginalCharaId = cci("ORIGINAL_CHARA_ID", "ORIGINAL_CHARA_ID", null, null, Integer.class, "originalCharaId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, "originalCharaImageList", null, false);
    protected final ColumnInfo _columnOriginalCharaName = cci("ORIGINAL_CHARA_NAME", "ORIGINAL_CHARA_NAME", null, null, String.class, "originalCharaName", null, false, false, true, "VARCHAR", 40, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnOriginalCharaShortName = cci("ORIGINAL_CHARA_SHORT_NAME", "ORIGINAL_CHARA_SHORT_NAME", null, null, String.class, "originalCharaShortName", null, false, false, true, "CHAR", 1, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnOriginalCharaGroupId = cci("ORIGINAL_CHARA_GROUP_ID", "ORIGINAL_CHARA_GROUP_ID", null, null, Integer.class, "originalCharaGroupId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "originalCharaGroup", null, null, false);
    protected final ColumnInfo _columnDisplayWidth = cci("DISPLAY_WIDTH", "DISPLAY_WIDTH", null, null, Integer.class, "displayWidth", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnDisplayHeight = cci("DISPLAY_HEIGHT", "DISPLAY_HEIGHT", null, null, Integer.class, "displayHeight", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaId() { return _columnOriginalCharaId; }
    /**
     * ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaName() { return _columnOriginalCharaName; }
    /**
     * ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaShortName() { return _columnOriginalCharaShortName; }
    /**
     * ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to ORIGINAL_CHARA_GROUP}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaGroupId() { return _columnOriginalCharaGroupId; }
    /**
     * DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDisplayWidth() { return _columnDisplayWidth; }
    /**
     * DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnDisplayHeight() { return _columnDisplayHeight; }
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
        ls.add(columnOriginalCharaId());
        ls.add(columnOriginalCharaName());
        ls.add(columnOriginalCharaShortName());
        ls.add(columnOriginalCharaGroupId());
        ls.add(columnDisplayWidth());
        ls.add(columnDisplayHeight());
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
    protected UniqueInfo cpui() { return hpcpui(columnOriginalCharaId()); }
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
     * ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignOriginalCharaGroup() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnOriginalCharaGroupId(), OriginalCharaGroupDbm.getInstance().columnOriginalCharaGroupId());
        return cfi("FK_ORIGINAL_CHARA_ORIGINAL_CHARA_GROUP", "originalCharaGroup", this, OriginalCharaGroupDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "originalCharaList", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * ORIGINAL_CHARA_IMAGE by ORIGINAL_CHARA_ID, named 'originalCharaImageList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerOriginalCharaImageList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnOriginalCharaId(), OriginalCharaImageDbm.getInstance().columnOriginalCharaId());
        return cri("FK_ORIGINAL_CHARA_IMAGE_ORIGINAL_CHARA", "originalCharaImageList", this, OriginalCharaImageDbm.getInstance(), mp, false, "originalChara");
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.OriginalChara"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.OriginalCharaCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.OriginalCharaBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<OriginalChara> getEntityType() { return OriginalChara.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public OriginalChara newEntity() { return new OriginalChara(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((OriginalChara)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((OriginalChara)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
