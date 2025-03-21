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
 * The entity of CAMP_ALLOCATION as TABLE. <br>
 * 陣営配分
 * <pre>
 * [primary-key]
 *     VILLAGE_ID, CAMP_CODE
 *
 * [column]
 *     VILLAGE_ID, CAMP_CODE, MIN_NUM, MAX_NUM, ALLOCATION, REINCARNATION_ALLOCATION, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     CAMP, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     camp, village
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villageId = entity.getVillageId();
 * String campCode = entity.getCampCode();
 * Integer minNum = entity.getMinNum();
 * Integer maxNum = entity.getMaxNum();
 * Integer allocation = entity.getAllocation();
 * Integer reincarnationAllocation = entity.getReincarnationAllocation();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillageId(villageId);
 * entity.setCampCode(campCode);
 * entity.setMinNum(minNum);
 * entity.setMaxNum(maxNum);
 * entity.setAllocation(allocation);
 * entity.setReincarnationAllocation(reincarnationAllocation);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsCampAllocation extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village} */
    protected Integer _villageId;

    /** CAMP_CODE: {PK, IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} */
    protected String _campCode;

    /** MIN_NUM: {NotNull, INT UNSIGNED(10)} */
    protected Integer _minNum;

    /** MAX_NUM: {INT UNSIGNED(10)} */
    protected Integer _maxNum;

    /** ALLOCATION: {NotNull, INT UNSIGNED(10)} */
    protected Integer _allocation;

    /** REINCARNATION_ALLOCATION: {NotNull, INT UNSIGNED(10)} */
    protected Integer _reincarnationAllocation;

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
        return "camp_allocation";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageId == null) { return false; }
        if (_campCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of campCode as the classification of Camp. <br>
     * CAMP_CODE: {PK, IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Camp getCampCodeAsCamp() {
        return CDef.Camp.codeOf(getCampCode());
    }

    /**
     * Set the value of campCode as the classification of Camp. <br>
     * CAMP_CODE: {PK, IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setCampCodeAsCamp(CDef.Camp cdef) {
        setCampCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of campCode as 愉快犯陣営 (CRIMINAL). <br>
     * 愉快犯陣営
     */
    public void setCampCode_愉快犯陣営() {
        setCampCodeAsCamp(CDef.Camp.愉快犯陣営);
    }

    /**
     * Set the value of campCode as 狐陣営 (FOX). <br>
     * 狐陣営
     */
    public void setCampCode_狐陣営() {
        setCampCodeAsCamp(CDef.Camp.狐陣営);
    }

    /**
     * Set the value of campCode as 恋人陣営 (LOVERS). <br>
     * 恋人陣営
     */
    public void setCampCode_恋人陣営() {
        setCampCodeAsCamp(CDef.Camp.恋人陣営);
    }

    /**
     * Set the value of campCode as 村人陣営 (VILLAGER). <br>
     * 村人陣営
     */
    public void setCampCode_村人陣営() {
        setCampCodeAsCamp(CDef.Camp.村人陣営);
    }

    /**
     * Set the value of campCode as 人狼陣営 (WEREWOLF). <br>
     * 人狼陣営
     */
    public void setCampCode_人狼陣営() {
        setCampCodeAsCamp(CDef.Camp.人狼陣営);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of campCode 愉快犯陣営? <br>
     * 愉快犯陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode愉快犯陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.愉快犯陣営) : false;
    }

    /**
     * Is the value of campCode 狐陣営? <br>
     * 狐陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode狐陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.狐陣営) : false;
    }

    /**
     * Is the value of campCode 恋人陣営? <br>
     * 恋人陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode恋人陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.恋人陣営) : false;
    }

    /**
     * Is the value of campCode 村人陣営? <br>
     * 村人陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode村人陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.村人陣営) : false;
    }

    /**
     * Is the value of campCode 人狼陣営? <br>
     * 人狼陣営
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isCampCode人狼陣営() {
        CDef.Camp cdef = getCampCodeAsCamp();
        return cdef != null ? cdef.equals(CDef.Camp.人狼陣営) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CAMP by my CAMP_CODE, named 'camp'. */
    protected OptionalEntity<Camp> _camp;

    /**
     * [get] CAMP by my CAMP_CODE, named 'camp'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'camp'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Camp> getCamp() {
        if (_camp == null) { _camp = OptionalEntity.relationEmpty(this, "camp"); }
        return _camp;
    }

    /**
     * [set] CAMP by my CAMP_CODE, named 'camp'.
     * @param camp The entity of foreign property 'camp'. (NullAllowed)
     */
    public void setCamp(OptionalEntity<Camp> camp) {
        _camp = camp;
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
        if (obj instanceof BsCampAllocation) {
            BsCampAllocation other = (BsCampAllocation)obj;
            if (!xSV(_villageId, other._villageId)) { return false; }
            if (!xSV(_campCode, other._campCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villageId);
        hs = xCH(hs, _campCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_camp != null && _camp.isPresent())
        { sb.append(li).append(xbRDS(_camp, "camp")); }
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
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_campCode));
        sb.append(dm).append(xfND(_minNum));
        sb.append(dm).append(xfND(_maxNum));
        sb.append(dm).append(xfND(_allocation));
        sb.append(dm).append(xfND(_reincarnationAllocation));
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
        if (_camp != null && _camp.isPresent())
        { sb.append(dm).append("camp"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public CampAllocation clone() {
        return (CampAllocation)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to village} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] CAMP_CODE: {PK, IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営コード
     * @return The value of the column 'CAMP_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getCampCode() {
        checkSpecifiedProperty("campCode");
        return convertEmptyToNull(_campCode);
    }

    /**
     * [set] CAMP_CODE: {PK, IX, NotNull, VARCHAR(20), FK to camp, classification=Camp} <br>
     * 陣営コード
     * @param campCode The value of the column 'CAMP_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setCampCode(String campCode) {
        checkClassificationCode("CAMP_CODE", CDef.DefMeta.Camp, campCode);
        registerModifiedProperty("campCode");
        _campCode = campCode;
    }

    /**
     * [get] MIN_NUM: {NotNull, INT UNSIGNED(10)} <br>
     * 最小人数
     * @return The value of the column 'MIN_NUM'. (basically NotNull if selected: for the constraint)
     */
    public Integer getMinNum() {
        checkSpecifiedProperty("minNum");
        return _minNum;
    }

    /**
     * [set] MIN_NUM: {NotNull, INT UNSIGNED(10)} <br>
     * 最小人数
     * @param minNum The value of the column 'MIN_NUM'. (basically NotNull if update: for the constraint)
     */
    public void setMinNum(Integer minNum) {
        registerModifiedProperty("minNum");
        _minNum = minNum;
    }

    /**
     * [get] MAX_NUM: {INT UNSIGNED(10)} <br>
     * 最大人数
     * @return The value of the column 'MAX_NUM'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getMaxNum() {
        checkSpecifiedProperty("maxNum");
        return _maxNum;
    }

    /**
     * [set] MAX_NUM: {INT UNSIGNED(10)} <br>
     * 最大人数
     * @param maxNum The value of the column 'MAX_NUM'. (NullAllowed: null update allowed for no constraint)
     */
    public void setMaxNum(Integer maxNum) {
        registerModifiedProperty("maxNum");
        _maxNum = maxNum;
    }

    /**
     * [get] ALLOCATION: {NotNull, INT UNSIGNED(10)} <br>
     * 配分
     * @return The value of the column 'ALLOCATION'. (basically NotNull if selected: for the constraint)
     */
    public Integer getAllocation() {
        checkSpecifiedProperty("allocation");
        return _allocation;
    }

    /**
     * [set] ALLOCATION: {NotNull, INT UNSIGNED(10)} <br>
     * 配分
     * @param allocation The value of the column 'ALLOCATION'. (basically NotNull if update: for the constraint)
     */
    public void setAllocation(Integer allocation) {
        registerModifiedProperty("allocation");
        _allocation = allocation;
    }

    /**
     * [get] REINCARNATION_ALLOCATION: {NotNull, INT UNSIGNED(10)} <br>
     * 転生配分
     * @return The value of the column 'REINCARNATION_ALLOCATION'. (basically NotNull if selected: for the constraint)
     */
    public Integer getReincarnationAllocation() {
        checkSpecifiedProperty("reincarnationAllocation");
        return _reincarnationAllocation;
    }

    /**
     * [set] REINCARNATION_ALLOCATION: {NotNull, INT UNSIGNED(10)} <br>
     * 転生配分
     * @param reincarnationAllocation The value of the column 'REINCARNATION_ALLOCATION'. (basically NotNull if update: for the constraint)
     */
    public void setReincarnationAllocation(Integer reincarnationAllocation) {
        registerModifiedProperty("reincarnationAllocation");
        _reincarnationAllocation = reincarnationAllocation;
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
     * @param campCode The value of the column 'CAMP_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingCampCode(String campCode) {
        setCampCode(campCode);
    }
}
