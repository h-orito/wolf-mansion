package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import com.ort.dbflute.allcommon.EntityDefinedCommonColumn;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of VILLAGE_CHARA_GROUP as TABLE. <br>
 * 村キャラクターグループ
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillageCharaGroup extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _villageCharaGroupId;

    /** VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} */
    protected Integer _villageId;

    /** CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group} */
    protected Integer _charaGroupId;

    /** REGISTER_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _registerDatetime;

    /** REGISTER_TRACE: {NotNull, VARCHAR(64)} */
    protected String _registerTrace;

    /** UPDATE_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _updateDatetime;

    /** UPDATE_TRACE: {NotNull, VARCHAR(64)} */
    protected String _updateTrace;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "village_chara_group";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageCharaGroupId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'. */
    protected OptionalEntity<CharaGroup> _charaGroup;

    /**
     * [get] CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'charaGroup'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<CharaGroup> getCharaGroup() {
        if (_charaGroup == null) { _charaGroup = OptionalEntity.relationEmpty(this, "charaGroup"); }
        return _charaGroup;
    }

    /**
     * [set] CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'.
     * @param charaGroup The entity of foreign property 'charaGroup'. (NullAllowed)
     */
    public void setCharaGroup(OptionalEntity<CharaGroup> charaGroup) {
        _charaGroup = charaGroup;
    }

    /** VILLAGE by my VILLAGE_ID, named 'village'. */
    protected OptionalEntity<Village> _village;

    /**
     * [get] VILLAGE by my VILLAGE_ID, named 'village'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'village'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Village> getVillage() {
        if (_village == null) { _village = OptionalEntity.relationEmpty(this, "village"); }
        return _village;
    }

    /**
     * [set] VILLAGE by my VILLAGE_ID, named 'village'.
     * @param village The entity of foreign property 'village'. (NullAllowed)
     */
    public void setVillage(OptionalEntity<Village> village) {
        _village = village;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVillageCharaGroup) {
            BsVillageCharaGroup other = (BsVillageCharaGroup)obj;
            if (!xSV(_villageCharaGroupId, other._villageCharaGroupId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villageCharaGroupId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_charaGroup != null && _charaGroup.isPresent())
        { sb.append(li).append(xbRDS(_charaGroup, "charaGroup")); }
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villageCharaGroupId));
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_charaGroupId));
        sb.append(dm).append(xfND(_registerDatetime));
        sb.append(dm).append(xfND(_registerTrace));
        sb.append(dm).append(xfND(_updateDatetime));
        sb.append(dm).append(xfND(_updateTrace));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_charaGroup != null && _charaGroup.isPresent())
        { sb.append(dm).append("charaGroup"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillageCharaGroup clone() {
        return (VillageCharaGroup)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村キャラクターグループID
     * @return The value of the column 'VILLAGE_CHARA_GROUP_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageCharaGroupId() {
        checkSpecifiedProperty("villageCharaGroupId");
        return _villageCharaGroupId;
    }

    /**
     * [set] VILLAGE_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村キャラクターグループID
     * @param villageCharaGroupId The value of the column 'VILLAGE_CHARA_GROUP_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageCharaGroupId(Integer villageCharaGroupId) {
        registerModifiedProperty("villageCharaGroupId");
        _villageCharaGroupId = villageCharaGroupId;
    }

    /**
     * [get] VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group} <br>
     * キャラクターグループID
     * @return The value of the column 'CHARA_GROUP_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaGroupId() {
        checkSpecifiedProperty("charaGroupId");
        return _charaGroupId;
    }

    /**
     * [set] CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group} <br>
     * キャラクターグループID
     * @param charaGroupId The value of the column 'CHARA_GROUP_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaGroupId(Integer charaGroupId) {
        registerModifiedProperty("charaGroupId");
        _charaGroupId = charaGroupId;
    }

    /**
     * [get] REGISTER_DATETIME: {NotNull, DATETIME(19)} <br>
     * 登録日時
     * @return The value of the column 'REGISTER_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return _registerDatetime;
    }

    /**
     * [set] REGISTER_DATETIME: {NotNull, DATETIME(19)} <br>
     * 登録日時
     * @param registerDatetime The value of the column 'REGISTER_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterDatetime(java.time.LocalDateTime registerDatetime) {
        registerModifiedProperty("registerDatetime");
        _registerDatetime = registerDatetime;
    }

    /**
     * [get] REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * 登録トレース
     * @return The value of the column 'REGISTER_TRACE'. (basically NotNull if selected: for the constraint)
     */
    public String getRegisterTrace() {
        checkSpecifiedProperty("registerTrace");
        return convertEmptyToNull(_registerTrace);
    }

    /**
     * [set] REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * 登録トレース
     * @param registerTrace The value of the column 'REGISTER_TRACE'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterTrace(String registerTrace) {
        registerModifiedProperty("registerTrace");
        _registerTrace = registerTrace;
    }

    /**
     * [get] UPDATE_DATETIME: {NotNull, DATETIME(19)} <br>
     * 更新日時
     * @return The value of the column 'UPDATE_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return _updateDatetime;
    }

    /**
     * [set] UPDATE_DATETIME: {NotNull, DATETIME(19)} <br>
     * 更新日時
     * @param updateDatetime The value of the column 'UPDATE_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateDatetime(java.time.LocalDateTime updateDatetime) {
        registerModifiedProperty("updateDatetime");
        _updateDatetime = updateDatetime;
    }

    /**
     * [get] UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * 更新トレース
     * @return The value of the column 'UPDATE_TRACE'. (basically NotNull if selected: for the constraint)
     */
    public String getUpdateTrace() {
        checkSpecifiedProperty("updateTrace");
        return convertEmptyToNull(_updateTrace);
    }

    /**
     * [set] UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * 更新トレース
     * @param updateTrace The value of the column 'UPDATE_TRACE'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateTrace(String updateTrace) {
        registerModifiedProperty("updateTrace");
        _updateTrace = updateTrace;
    }
}
