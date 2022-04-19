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
 * The DB meta of original_chara_image. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class OriginalCharaImageDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final OriginalCharaImageDbm _instance = new OriginalCharaImageDbm();
    private OriginalCharaImageDbm() {}
    public static OriginalCharaImageDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getOriginalCharaImageId(), (et, vl) -> ((OriginalCharaImage)et).setOriginalCharaImageId(cti(vl)), "originalCharaImageId");
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getOriginalCharaId(), (et, vl) -> ((OriginalCharaImage)et).setOriginalCharaId(cti(vl)), "originalCharaId");
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getFaceTypeName(), (et, vl) -> ((OriginalCharaImage)et).setFaceTypeName((String)vl), "faceTypeName");
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getCharaImgUrl(), (et, vl) -> ((OriginalCharaImage)et).setCharaImgUrl((String)vl), "charaImgUrl");
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getRegisterDatetime(), (et, vl) -> ((OriginalCharaImage)et).setRegisterDatetime(ctldt(vl)), "registerDatetime");
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getRegisterTrace(), (et, vl) -> ((OriginalCharaImage)et).setRegisterTrace((String)vl), "registerTrace");
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getUpdateDatetime(), (et, vl) -> ((OriginalCharaImage)et).setUpdateDatetime(ctldt(vl)), "updateDatetime");
        setupEpg(_epgMap, et -> ((OriginalCharaImage)et).getUpdateTrace(), (et, vl) -> ((OriginalCharaImage)et).setUpdateTrace((String)vl), "updateTrace");
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
        setupEfpg(_efpgMap, et -> ((OriginalCharaImage)et).getOriginalChara(), (et, vl) -> ((OriginalCharaImage)et).setOriginalChara((OptionalEntity<OriginalChara>)vl), "originalChara");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "original_chara_image";
    protected final String _tableDispName = "ORIGINAL_CHARA_IMAGE";
    protected final String _tablePropertyName = "originalCharaImage";
    protected final TableSqlName _tableSqlName = new TableSqlName("ORIGINAL_CHARA_IMAGE", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnOriginalCharaImageId = cci("ORIGINAL_CHARA_IMAGE_ID", "ORIGINAL_CHARA_IMAGE_ID", null, null, Integer.class, "originalCharaImageId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnOriginalCharaId = cci("ORIGINAL_CHARA_ID", "ORIGINAL_CHARA_ID", null, null, Integer.class, "originalCharaId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "originalChara", null, null, false);
    protected final ColumnInfo _columnFaceTypeName = cci("FACE_TYPE_NAME", "FACE_TYPE_NAME", null, null, String.class, "faceTypeName", null, false, false, true, "VARCHAR", 20, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnCharaImgUrl = cci("CHARA_IMG_URL", "CHARA_IMG_URL", null, null, String.class, "charaImgUrl", null, false, false, true, "VARCHAR", 100, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterDatetime = cci("REGISTER_DATETIME", "REGISTER_DATETIME", null, null, java.time.LocalDateTime.class, "registerDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnRegisterTrace = cci("REGISTER_TRACE", "REGISTER_TRACE", null, null, String.class, "registerTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateDatetime = cci("UPDATE_DATETIME", "UPDATE_DATETIME", null, null, java.time.LocalDateTime.class, "updateDatetime", null, false, false, true, "DATETIME", 19, 0, null, null, true, null, null, null, null, null, false);
    protected final ColumnInfo _columnUpdateTrace = cci("UPDATE_TRACE", "UPDATE_TRACE", null, null, String.class, "updateTrace", null, false, false, true, "VARCHAR", 64, 0, null, null, true, null, null, null, null, null, false);

    /**
     * ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaImageId() { return _columnOriginalCharaImageId; }
    /**
     * ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnOriginalCharaId() { return _columnOriginalCharaId; }
    /**
     * FACE_TYPE_NAME: {NotNull, VARCHAR(20)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnFaceTypeName() { return _columnFaceTypeName; }
    /**
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaImgUrl() { return _columnCharaImgUrl; }
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
        ls.add(columnOriginalCharaImageId());
        ls.add(columnOriginalCharaId());
        ls.add(columnFaceTypeName());
        ls.add(columnCharaImgUrl());
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
    protected UniqueInfo cpui() { return hpcpui(columnOriginalCharaImageId()); }
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
     * ORIGINAL_CHARA by my ORIGINAL_CHARA_ID, named 'originalChara'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignOriginalChara() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnOriginalCharaId(), OriginalCharaDbm.getInstance().columnOriginalCharaId());
        return cfi("FK_ORIGINAL_CHARA_IMAGE_ORIGINAL_CHARA", "originalChara", this, OriginalCharaDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "originalCharaImageList", false);
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
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.OriginalCharaImage"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.OriginalCharaImageCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.OriginalCharaImageBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<OriginalCharaImage> getEntityType() { return OriginalCharaImage.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public OriginalCharaImage newEntity() { return new OriginalCharaImage(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((OriginalCharaImage)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((OriginalCharaImage)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
