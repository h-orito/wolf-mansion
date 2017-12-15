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
 * The DB meta of chara. (Singleton)
 * @author DBFlute(AutoGenerator)
 */
public class CharaDbm extends AbstractDBMeta {

    // ===================================================================================
    //                                                                           Singleton
    //                                                                           =========
    private static final CharaDbm _instance = new CharaDbm();
    private CharaDbm() {}
    public static CharaDbm getInstance() { return _instance; }

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
        setupEpg(_epgMap, et -> ((Chara)et).getCharaId(), (et, vl) -> ((Chara)et).setCharaId(cti(vl)), "charaId");
        setupEpg(_epgMap, et -> ((Chara)et).getCharaName(), (et, vl) -> ((Chara)et).setCharaName((String)vl), "charaName");
        setupEpg(_epgMap, et -> ((Chara)et).getCharaGroupId(), (et, vl) -> ((Chara)et).setCharaGroupId(cti(vl)), "charaGroupId");
        setupEpg(_epgMap, et -> ((Chara)et).getCharaImgUrl(), (et, vl) -> ((Chara)et).setCharaImgUrl((String)vl), "charaImgUrl");
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
        setupEfpg(_efpgMap, et -> ((Chara)et).getCharaGroup(), (et, vl) -> ((Chara)et).setCharaGroup((OptionalEntity<CharaGroup>)vl), "charaGroup");
    }
    public PropertyGateway findForeignPropertyGateway(String prop)
    { return doFindEfpg(_efpgMap, prop); }

    // ===================================================================================
    //                                                                          Table Info
    //                                                                          ==========
    protected final String _tableDbName = "chara";
    protected final String _tableDispName = "CHARA";
    protected final String _tablePropertyName = "chara";
    protected final TableSqlName _tableSqlName = new TableSqlName("CHARA", _tableDbName);
    { _tableSqlName.xacceptFilter(DBFluteConfig.getInstance().getTableSqlNameFilter()); }
    public String getTableDbName() { return _tableDbName; }
    public String getTableDispName() { return _tableDispName; }
    public String getTablePropertyName() { return _tablePropertyName; }
    public TableSqlName getTableSqlName() { return _tableSqlName; }

    // ===================================================================================
    //                                                                         Column Info
    //                                                                         ===========
    protected final ColumnInfo _columnCharaId = cci("CHARA_ID", "CHARA_ID", null, null, Integer.class, "charaId", null, true, true, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, null, "villagePlayerList", null, false);
    protected final ColumnInfo _columnCharaName = cci("CHARA_NAME", "CHARA_NAME", null, null, String.class, "charaName", null, false, false, true, "VARCHAR", 40, 0, null, null, false, null, null, null, null, null, false);
    protected final ColumnInfo _columnCharaGroupId = cci("CHARA_GROUP_ID", "CHARA_GROUP_ID", null, null, Integer.class, "charaGroupId", null, false, false, true, "INT UNSIGNED", 10, 0, null, null, false, null, null, "charaGroup", null, null, false);
    protected final ColumnInfo _columnCharaImgUrl = cci("CHARA_IMG_URL", "CHARA_IMG_URL", null, null, String.class, "charaImgUrl", null, false, false, true, "VARCHAR", 100, 0, null, null, false, null, null, null, null, null, false);

    /**
     * CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaId() { return _columnCharaId; }
    /**
     * CHARA_NAME: {NotNull, VARCHAR(40)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaName() { return _columnCharaName; }
    /**
     * CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaGroupId() { return _columnCharaGroupId; }
    /**
     * CHARA_IMG_URL: {NotNull, VARCHAR(100)}
     * @return The information object of specified column. (NotNull)
     */
    public ColumnInfo columnCharaImgUrl() { return _columnCharaImgUrl; }

    protected List<ColumnInfo> ccil() {
        List<ColumnInfo> ls = newArrayList();
        ls.add(columnCharaId());
        ls.add(columnCharaName());
        ls.add(columnCharaGroupId());
        ls.add(columnCharaImgUrl());
        return ls;
    }

    { initializeInformationResource(); }

    // ===================================================================================
    //                                                                         Unique Info
    //                                                                         ===========
    // -----------------------------------------------------
    //                                       Primary Element
    //                                       ---------------
    protected UniqueInfo cpui() { return hpcpui(columnCharaId()); }
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
     * CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'.
     * @return The information object of foreign property. (NotNull)
     */
    public ForeignInfo foreignCharaGroup() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnCharaGroupId(), CharaGroupDbm.getInstance().columnCharaGroupId());
        return cfi("FK_CHARA_CHARA_GROUP", "charaGroup", this, CharaGroupDbm.getInstance(), mp, 0, org.dbflute.optional.OptionalEntity.class, false, false, false, false, null, null, false, "charaList", false);
    }

    // -----------------------------------------------------
    //                                     Referrer Property
    //                                     -----------------
    /**
     * VILLAGE_PLAYER by CHARA_ID, named 'villagePlayerList'.
     * @return The information object of referrer property. (NotNull)
     */
    public ReferrerInfo referrerVillagePlayerList() {
        Map<ColumnInfo, ColumnInfo> mp = newLinkedHashMap(columnCharaId(), VillagePlayerDbm.getInstance().columnCharaId());
        return cri("FK_VILLAGE_PLAYER_CHARA", "villagePlayerList", this, VillagePlayerDbm.getInstance(), mp, false, "chara");
    }

    // ===================================================================================
    //                                                                        Various Info
    //                                                                        ============
    public boolean hasIdentity() { return true; }

    // ===================================================================================
    //                                                                           Type Name
    //                                                                           =========
    public String getEntityTypeName() { return "com.ort.dbflute.exentity.Chara"; }
    public String getConditionBeanTypeName() { return "com.ort.dbflute.cbean.CharaCB"; }
    public String getBehaviorTypeName() { return "com.ort.dbflute.exbhv.CharaBhv"; }

    // ===================================================================================
    //                                                                         Object Type
    //                                                                         ===========
    public Class<Chara> getEntityType() { return Chara.class; }

    // ===================================================================================
    //                                                                     Object Instance
    //                                                                     ===============
    public Chara newEntity() { return new Chara(); }

    // ===================================================================================
    //                                                                   Map Communication
    //                                                                   =================
    public void acceptPrimaryKeyMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptPrimaryKeyMap((Chara)et, mp); }
    public void acceptAllColumnMap(Entity et, Map<String, ? extends Object> mp)
    { doAcceptAllColumnMap((Chara)et, mp); }
    public Map<String, Object> extractPrimaryKeyMap(Entity et) { return doExtractPrimaryKeyMap(et); }
    public Map<String, Object> extractAllColumnMap(Entity et) { return doExtractAllColumnMap(et); }
}
