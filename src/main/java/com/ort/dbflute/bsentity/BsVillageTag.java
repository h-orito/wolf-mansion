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
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.*;

/**
 * The entity of VILLAGE_TAG as TABLE. <br>
 * 村タグ
 * <pre>
 * [primary-key]
 *     VILLAGE_TAG_ID
 *
 * [column]
 *     VILLAGE_TAG_ID, VILLAGE_ID, VILLAGE_TAG_ITEM_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_TAG_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VILLAGE, VILLAGE_TAG_ITEM
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     village, villageTagItem
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villageTagId = entity.getVillageTagId();
 * Integer villageId = entity.getVillageId();
 * String villageTagItemCode = entity.getVillageTagItemCode();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillageTagId(villageTagId);
 * entity.setVillageId(villageId);
 * entity.setVillageTagItemCode(villageTagItemCode);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillageTag extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_TAG_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _villageTagId;

    /** VILLAGE_ID: {IX, NotNull, INT UNSIGNED(10), FK to village} */
    protected Integer _villageId;

    /** VILLAGE_TAG_ITEM_CODE: {IX, NotNull, VARCHAR(50), FK to village_tag_item, classification=VillageTagItem} */
    protected String _villageTagItemCode;

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
        return "village_tag";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageTagId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of villageTagItemCode as the classification of VillageTagItem. <br>
     * VILLAGE_TAG_ITEM_CODE: {IX, NotNull, VARCHAR(50), FK to village_tag_item, classification=VillageTagItem} <br>
     * 村タグ種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.VillageTagItem getVillageTagItemCodeAsVillageTagItem() {
        return CDef.VillageTagItem.codeOf(getVillageTagItemCode());
    }

    /**
     * Set the value of villageTagItemCode as the classification of VillageTagItem. <br>
     * VILLAGE_TAG_ITEM_CODE: {IX, NotNull, VARCHAR(50), FK to village_tag_item, classification=VillageTagItem} <br>
     * 村タグ種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem cdef) {
        setVillageTagItemCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of villageTagItemCode as 誰歓 (ANYONE_WELCOME). <br>
     * 誰歓
     */
    public void setVillageTagItemCode_誰歓() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.誰歓);
    }

    /**
     * Set the value of villageTagItemCode as R15 (R15). <br>
     * R15
     */
    public void setVillageTagItemCode_R15() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.R15);
    }

    /**
     * Set the value of villageTagItemCode as R18 (R18). <br>
     * R18
     */
    public void setVillageTagItemCode_R18() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.R18);
    }

    /**
     * Set the value of villageTagItemCode as 身内 (RELATIVES_ONLY). <br>
     * 身内
     */
    public void setVillageTagItemCode_身内() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.身内);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of villageTagItemCode 誰歓? <br>
     * 誰歓
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCode誰歓() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.誰歓) : false;
    }

    /**
     * Is the value of villageTagItemCode R15? <br>
     * R15
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCodeR15() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.R15) : false;
    }

    /**
     * Is the value of villageTagItemCode R18? <br>
     * R18
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCodeR18() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.R18) : false;
    }

    /**
     * Is the value of villageTagItemCode 身内? <br>
     * 身内
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCode身内() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.身内) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
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

    /** VILLAGE_TAG_ITEM by my VILLAGE_TAG_ITEM_CODE, named 'villageTagItem'. */
    protected OptionalEntity<VillageTagItem> _villageTagItem;

    /**
     * [get] VILLAGE_TAG_ITEM by my VILLAGE_TAG_ITEM_CODE, named 'villageTagItem'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villageTagItem'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillageTagItem> getVillageTagItem() {
        if (_villageTagItem == null) { _villageTagItem = OptionalEntity.relationEmpty(this, "villageTagItem"); }
        return _villageTagItem;
    }

    /**
     * [set] VILLAGE_TAG_ITEM by my VILLAGE_TAG_ITEM_CODE, named 'villageTagItem'.
     * @param villageTagItem The entity of foreign property 'villageTagItem'. (NullAllowed)
     */
    public void setVillageTagItem(OptionalEntity<VillageTagItem> villageTagItem) {
        _villageTagItem = villageTagItem;
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
        if (obj instanceof BsVillageTag) {
            BsVillageTag other = (BsVillageTag)obj;
            if (!xSV(_villageTagId, other._villageTagId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villageTagId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
        if (_villageTagItem != null && _villageTagItem.isPresent())
        { sb.append(li).append(xbRDS(_villageTagItem, "villageTagItem")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villageTagId));
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_villageTagItemCode));
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
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (_villageTagItem != null && _villageTagItem.isPresent())
        { sb.append(dm).append("villageTagItem"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillageTag clone() {
        return (VillageTag)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_TAG_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村タグID
     * @return The value of the column 'VILLAGE_TAG_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageTagId() {
        checkSpecifiedProperty("villageTagId");
        return _villageTagId;
    }

    /**
     * [set] VILLAGE_TAG_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村タグID
     * @param villageTagId The value of the column 'VILLAGE_TAG_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageTagId(Integer villageTagId) {
        registerModifiedProperty("villageTagId");
        _villageTagId = villageTagId;
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
     * [get] VILLAGE_TAG_ITEM_CODE: {IX, NotNull, VARCHAR(50), FK to village_tag_item, classification=VillageTagItem} <br>
     * 村タグ種別コード
     * @return The value of the column 'VILLAGE_TAG_ITEM_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getVillageTagItemCode() {
        checkSpecifiedProperty("villageTagItemCode");
        return convertEmptyToNull(_villageTagItemCode);
    }

    /**
     * [set] VILLAGE_TAG_ITEM_CODE: {IX, NotNull, VARCHAR(50), FK to village_tag_item, classification=VillageTagItem} <br>
     * 村タグ種別コード
     * @param villageTagItemCode The value of the column 'VILLAGE_TAG_ITEM_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setVillageTagItemCode(String villageTagItemCode) {
        checkClassificationCode("VILLAGE_TAG_ITEM_CODE", CDef.DefMeta.VillageTagItem, villageTagItemCode);
        registerModifiedProperty("villageTagItemCode");
        _villageTagItemCode = villageTagItemCode;
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

    /**
     * For framework so basically DON'T use this method.
     * @param villageTagItemCode The value of the column 'VILLAGE_TAG_ITEM_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingVillageTagItemCode(String villageTagItemCode) {
        setVillageTagItemCode(villageTagItemCode);
    }
}
