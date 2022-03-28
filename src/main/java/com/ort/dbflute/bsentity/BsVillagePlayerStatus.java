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
 * The entity of VILLAGE_PLAYER_STATUS as TABLE. <br>
 * 村参加者ステータス
 * <pre>
 * [primary-key]
 *     VILLAGE_PLAYER_STATUS_ID
 *
 * [column]
 *     VILLAGE_PLAYER_STATUS_ID, VILLAGE_PLAYER_ID, TO_VILLAGE_PLAYER_ID, VILLAGE_PLAYER_STATUS_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILLAGE_PLAYER_STATUS_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VILLAGE_PLAYER, VILLAGE_PLAYER_STATUS_TYPE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     villagePlayerByToVillagePlayerId, villagePlayerByVillagePlayerId, villagePlayerStatusType
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villagePlayerStatusId = entity.getVillagePlayerStatusId();
 * Integer villagePlayerId = entity.getVillagePlayerId();
 * Integer toVillagePlayerId = entity.getToVillagePlayerId();
 * String villagePlayerStatusCode = entity.getVillagePlayerStatusCode();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillagePlayerStatusId(villagePlayerStatusId);
 * entity.setVillagePlayerId(villagePlayerId);
 * entity.setToVillagePlayerId(toVillagePlayerId);
 * entity.setVillagePlayerStatusCode(villagePlayerStatusCode);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillagePlayerStatus extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_PLAYER_STATUS_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _villagePlayerStatusId;

    /** VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to village_player} */
    protected Integer _villagePlayerId;

    /** TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} */
    protected Integer _toVillagePlayerId;

    /** VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to village_player_status_type, classification=VillagePlayerStatusType} */
    protected String _villagePlayerStatusCode;

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
        return "village_player_status";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villagePlayerStatusId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of villagePlayerStatusCode as the classification of VillagePlayerStatusType. <br>
     * VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to village_player_status_type, classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.VillagePlayerStatusType getVillagePlayerStatusCodeAsVillagePlayerStatusType() {
        return CDef.VillagePlayerStatusType.codeOf(getVillagePlayerStatusCode());
    }

    /**
     * Set the value of villagePlayerStatusCode as the classification of VillagePlayerStatusType. <br>
     * VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to village_player_status_type, classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setVillagePlayerStatusCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType cdef) {
        setVillagePlayerStatusCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of villagePlayerStatusCode as 後追い (FOLLOWING_SUICIDE). <br>
     * 後追い
     */
    public void setVillagePlayerStatusCode_後追い() {
        setVillagePlayerStatusCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.後追い);
    }

    /**
     * Set the value of villagePlayerStatusCode as 狐憑き (FOX_POSSESSION). <br>
     * 狐憑き
     */
    public void setVillagePlayerStatusCode_狐憑き() {
        setVillagePlayerStatusCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.狐憑き);
    }

    /**
     * Set the value of villagePlayerStatusCode as 保険 (INSURANCE). <br>
     * 保険
     */
    public void setVillagePlayerStatusCode_保険() {
        setVillagePlayerStatusCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.保険);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of villagePlayerStatusCode 後追い? <br>
     * 後追い
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusCode後追い() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.後追い) : false;
    }

    /**
     * Is the value of villagePlayerStatusCode 狐憑き? <br>
     * 狐憑き
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusCode狐憑き() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.狐憑き) : false;
    }

    /**
     * Is the value of villagePlayerStatusCode 保険? <br>
     * 保険
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusCode保険() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.保険) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'. */
    protected OptionalEntity<VillagePlayer> _villagePlayerByToVillagePlayerId;

    /**
     * [get] VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villagePlayerByToVillagePlayerId'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillagePlayer> getVillagePlayerByToVillagePlayerId() {
        if (_villagePlayerByToVillagePlayerId == null) { _villagePlayerByToVillagePlayerId = OptionalEntity.relationEmpty(this, "villagePlayerByToVillagePlayerId"); }
        return _villagePlayerByToVillagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER by my TO_VILLAGE_PLAYER_ID, named 'villagePlayerByToVillagePlayerId'.
     * @param villagePlayerByToVillagePlayerId The entity of foreign property 'villagePlayerByToVillagePlayerId'. (NullAllowed)
     */
    public void setVillagePlayerByToVillagePlayerId(OptionalEntity<VillagePlayer> villagePlayerByToVillagePlayerId) {
        _villagePlayerByToVillagePlayerId = villagePlayerByToVillagePlayerId;
    }

    /** VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'. */
    protected OptionalEntity<VillagePlayer> _villagePlayerByVillagePlayerId;

    /**
     * [get] VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villagePlayerByVillagePlayerId'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillagePlayer> getVillagePlayerByVillagePlayerId() {
        if (_villagePlayerByVillagePlayerId == null) { _villagePlayerByVillagePlayerId = OptionalEntity.relationEmpty(this, "villagePlayerByVillagePlayerId"); }
        return _villagePlayerByVillagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER by my VILLAGE_PLAYER_ID, named 'villagePlayerByVillagePlayerId'.
     * @param villagePlayerByVillagePlayerId The entity of foreign property 'villagePlayerByVillagePlayerId'. (NullAllowed)
     */
    public void setVillagePlayerByVillagePlayerId(OptionalEntity<VillagePlayer> villagePlayerByVillagePlayerId) {
        _villagePlayerByVillagePlayerId = villagePlayerByVillagePlayerId;
    }

    /** VILLAGE_PLAYER_STATUS_TYPE by my VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusType'. */
    protected OptionalEntity<VillagePlayerStatusType> _villagePlayerStatusType;

    /**
     * [get] VILLAGE_PLAYER_STATUS_TYPE by my VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusType'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'villagePlayerStatusType'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<VillagePlayerStatusType> getVillagePlayerStatusType() {
        if (_villagePlayerStatusType == null) { _villagePlayerStatusType = OptionalEntity.relationEmpty(this, "villagePlayerStatusType"); }
        return _villagePlayerStatusType;
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS_TYPE by my VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusType'.
     * @param villagePlayerStatusType The entity of foreign property 'villagePlayerStatusType'. (NullAllowed)
     */
    public void setVillagePlayerStatusType(OptionalEntity<VillagePlayerStatusType> villagePlayerStatusType) {
        _villagePlayerStatusType = villagePlayerStatusType;
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
        if (obj instanceof BsVillagePlayerStatus) {
            BsVillagePlayerStatus other = (BsVillagePlayerStatus)obj;
            if (!xSV(_villagePlayerStatusId, other._villagePlayerStatusId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villagePlayerStatusId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_villagePlayerByToVillagePlayerId != null && _villagePlayerByToVillagePlayerId.isPresent())
        { sb.append(li).append(xbRDS(_villagePlayerByToVillagePlayerId, "villagePlayerByToVillagePlayerId")); }
        if (_villagePlayerByVillagePlayerId != null && _villagePlayerByVillagePlayerId.isPresent())
        { sb.append(li).append(xbRDS(_villagePlayerByVillagePlayerId, "villagePlayerByVillagePlayerId")); }
        if (_villagePlayerStatusType != null && _villagePlayerStatusType.isPresent())
        { sb.append(li).append(xbRDS(_villagePlayerStatusType, "villagePlayerStatusType")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villagePlayerStatusId));
        sb.append(dm).append(xfND(_villagePlayerId));
        sb.append(dm).append(xfND(_toVillagePlayerId));
        sb.append(dm).append(xfND(_villagePlayerStatusCode));
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
        if (_villagePlayerByToVillagePlayerId != null && _villagePlayerByToVillagePlayerId.isPresent())
        { sb.append(dm).append("villagePlayerByToVillagePlayerId"); }
        if (_villagePlayerByVillagePlayerId != null && _villagePlayerByVillagePlayerId.isPresent())
        { sb.append(dm).append("villagePlayerByVillagePlayerId"); }
        if (_villagePlayerStatusType != null && _villagePlayerStatusType.isPresent())
        { sb.append(dm).append("villagePlayerStatusType"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillagePlayerStatus clone() {
        return (VillagePlayerStatus)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_PLAYER_STATUS_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者ステータスID
     * @return The value of the column 'VILLAGE_PLAYER_STATUS_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillagePlayerStatusId() {
        checkSpecifiedProperty("villagePlayerStatusId");
        return _villagePlayerStatusId;
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村参加者ステータスID
     * @param villagePlayerStatusId The value of the column 'VILLAGE_PLAYER_STATUS_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerStatusId(Integer villagePlayerStatusId) {
        registerModifiedProperty("villagePlayerStatusId");
        _villagePlayerStatusId = villagePlayerStatusId;
    }

    /**
     * [get] VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to village_player} <br>
     * 村参加者ID
     * @return The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillagePlayerId() {
        checkSpecifiedProperty("villagePlayerId");
        return _villagePlayerId;
    }

    /**
     * [set] VILLAGE_PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to village_player} <br>
     * 村参加者ID
     * @param villagePlayerId The value of the column 'VILLAGE_PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerId(Integer villagePlayerId) {
        registerModifiedProperty("villagePlayerId");
        _villagePlayerId = villagePlayerId;
    }

    /**
     * [get] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * 対象の村参加者ID
     * @return The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getToVillagePlayerId() {
        checkSpecifiedProperty("toVillagePlayerId");
        return _toVillagePlayerId;
    }

    /**
     * [set] TO_VILLAGE_PLAYER_ID: {IX, INT UNSIGNED(10), FK to village_player} <br>
     * 対象の村参加者ID
     * @param toVillagePlayerId The value of the column 'TO_VILLAGE_PLAYER_ID'. (NullAllowed: null update allowed for no constraint)
     */
    public void setToVillagePlayerId(Integer toVillagePlayerId) {
        registerModifiedProperty("toVillagePlayerId");
        _toVillagePlayerId = toVillagePlayerId;
    }

    /**
     * [get] VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to village_player_status_type, classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別コード
     * @return The value of the column 'VILLAGE_PLAYER_STATUS_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getVillagePlayerStatusCode() {
        checkSpecifiedProperty("villagePlayerStatusCode");
        return convertEmptyToNull(_villagePlayerStatusCode);
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS_CODE: {IX, NotNull, VARCHAR(20), FK to village_player_status_type, classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別コード
     * @param villagePlayerStatusCode The value of the column 'VILLAGE_PLAYER_STATUS_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setVillagePlayerStatusCode(String villagePlayerStatusCode) {
        checkClassificationCode("VILLAGE_PLAYER_STATUS_CODE", CDef.DefMeta.VillagePlayerStatusType, villagePlayerStatusCode);
        registerModifiedProperty("villagePlayerStatusCode");
        _villagePlayerStatusCode = villagePlayerStatusCode;
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
     * @param villagePlayerStatusCode The value of the column 'VILLAGE_PLAYER_STATUS_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingVillagePlayerStatusCode(String villagePlayerStatusCode) {
        setVillagePlayerStatusCode(villagePlayerStatusCode);
    }
}
