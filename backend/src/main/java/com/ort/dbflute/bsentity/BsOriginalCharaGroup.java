package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.EntityDefinedCommonColumn;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of ORIGINAL_CHARA_GROUP as TABLE. <br>
 * オリジナルキャラクターグループ
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsOriginalCharaGroup extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _originalCharaGroupId;

    /** ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} */
    protected String _originalCharaGroupName;

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
        return "original_chara_group";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_originalCharaGroupId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** ORIGINAL_CHARA by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaList'. */
    protected List<OriginalChara> _originalCharaList;

    /**
     * [get] ORIGINAL_CHARA by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaList'.
     * @return The entity list of referrer property 'originalCharaList'. (NotNull: even if no loading, returns empty list)
     */
    public List<OriginalChara> getOriginalCharaList() {
        if (_originalCharaList == null) { _originalCharaList = newReferrerList(); }
        return _originalCharaList;
    }

    /**
     * [set] ORIGINAL_CHARA by ORIGINAL_CHARA_GROUP_ID, named 'originalCharaList'.
     * @param originalCharaList The entity list of referrer property 'originalCharaList'. (NullAllowed)
     */
    public void setOriginalCharaList(List<OriginalChara> originalCharaList) {
        _originalCharaList = originalCharaList;
    }

    /** VILLAGE_SETTINGS by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsList'. */
    protected List<VillageSettings> _villageSettingsList;

    /**
     * [get] VILLAGE_SETTINGS by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsList'.
     * @return The entity list of referrer property 'villageSettingsList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillageSettings> getVillageSettingsList() {
        if (_villageSettingsList == null) { _villageSettingsList = newReferrerList(); }
        return _villageSettingsList;
    }

    /**
     * [set] VILLAGE_SETTINGS by ORIGINAL_CHARA_GROUP_ID, named 'villageSettingsList'.
     * @param villageSettingsList The entity list of referrer property 'villageSettingsList'. (NullAllowed)
     */
    public void setVillageSettingsList(List<VillageSettings> villageSettingsList) {
        _villageSettingsList = villageSettingsList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsOriginalCharaGroup) {
            BsOriginalCharaGroup other = (BsOriginalCharaGroup)obj;
            if (!xSV(_originalCharaGroupId, other._originalCharaGroupId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _originalCharaGroupId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_originalCharaList != null) { for (OriginalChara et : _originalCharaList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "originalCharaList")); } } }
        if (_villageSettingsList != null) { for (VillageSettings et : _villageSettingsList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villageSettingsList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_originalCharaGroupId));
        sb.append(dm).append(xfND(_originalCharaGroupName));
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
        if (_originalCharaList != null && !_originalCharaList.isEmpty())
        { sb.append(dm).append("originalCharaList"); }
        if (_villageSettingsList != null && !_villageSettingsList.isEmpty())
        { sb.append(dm).append("villageSettingsList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public OriginalCharaGroup clone() {
        return (OriginalCharaGroup)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * オリジナルキャラクターグループID
     * @return The value of the column 'ORIGINAL_CHARA_GROUP_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getOriginalCharaGroupId() {
        checkSpecifiedProperty("originalCharaGroupId");
        return _originalCharaGroupId;
    }

    /**
     * [set] ORIGINAL_CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * オリジナルキャラクターグループID
     * @param originalCharaGroupId The value of the column 'ORIGINAL_CHARA_GROUP_ID'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaGroupId(Integer originalCharaGroupId) {
        registerModifiedProperty("originalCharaGroupId");
        _originalCharaGroupId = originalCharaGroupId;
    }

    /**
     * [get] ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} <br>
     * オリジナルキャラクターグループ名
     * @return The value of the column 'ORIGINAL_CHARA_GROUP_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getOriginalCharaGroupName() {
        checkSpecifiedProperty("originalCharaGroupName");
        return convertEmptyToNull(_originalCharaGroupName);
    }

    /**
     * [set] ORIGINAL_CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} <br>
     * オリジナルキャラクターグループ名
     * @param originalCharaGroupName The value of the column 'ORIGINAL_CHARA_GROUP_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaGroupName(String originalCharaGroupName) {
        registerModifiedProperty("originalCharaGroupName");
        _originalCharaGroupName = originalCharaGroupName;
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
