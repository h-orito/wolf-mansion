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
 * The entity of ORIGINAL_CHARA as TABLE. <br>
 * オリジナルキャラクター
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsOriginalChara extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _originalCharaId;

    /** ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)} */
    protected String _originalCharaName;

    /** ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)} */
    protected String _originalCharaShortName;

    /** ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group} */
    protected Integer _originalCharaGroupId;

    /** DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)} */
    protected Integer _displayWidth;

    /** DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)} */
    protected Integer _displayHeight;

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
        return "original_chara";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_originalCharaId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'. */
    protected OptionalEntity<OriginalCharaGroup> _originalCharaGroup;

    /**
     * [get] ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'originalCharaGroup'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<OriginalCharaGroup> getOriginalCharaGroup() {
        if (_originalCharaGroup == null) { _originalCharaGroup = OptionalEntity.relationEmpty(this, "originalCharaGroup"); }
        return _originalCharaGroup;
    }

    /**
     * [set] ORIGINAL_CHARA_GROUP by my ORIGINAL_CHARA_GROUP_ID, named 'originalCharaGroup'.
     * @param originalCharaGroup The entity of foreign property 'originalCharaGroup'. (NullAllowed)
     */
    public void setOriginalCharaGroup(OptionalEntity<OriginalCharaGroup> originalCharaGroup) {
        _originalCharaGroup = originalCharaGroup;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** ORIGINAL_CHARA_IMAGE by ORIGINAL_CHARA_ID, named 'originalCharaImageList'. */
    protected List<OriginalCharaImage> _originalCharaImageList;

    /**
     * [get] ORIGINAL_CHARA_IMAGE by ORIGINAL_CHARA_ID, named 'originalCharaImageList'.
     * @return The entity list of referrer property 'originalCharaImageList'. (NotNull: even if no loading, returns empty list)
     */
    public List<OriginalCharaImage> getOriginalCharaImageList() {
        if (_originalCharaImageList == null) { _originalCharaImageList = newReferrerList(); }
        return _originalCharaImageList;
    }

    /**
     * [set] ORIGINAL_CHARA_IMAGE by ORIGINAL_CHARA_ID, named 'originalCharaImageList'.
     * @param originalCharaImageList The entity list of referrer property 'originalCharaImageList'. (NullAllowed)
     */
    public void setOriginalCharaImageList(List<OriginalCharaImage> originalCharaImageList) {
        _originalCharaImageList = originalCharaImageList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsOriginalChara) {
            BsOriginalChara other = (BsOriginalChara)obj;
            if (!xSV(_originalCharaId, other._originalCharaId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _originalCharaId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_originalCharaGroup != null && _originalCharaGroup.isPresent())
        { sb.append(li).append(xbRDS(_originalCharaGroup, "originalCharaGroup")); }
        if (_originalCharaImageList != null) { for (OriginalCharaImage et : _originalCharaImageList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "originalCharaImageList")); } } }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_originalCharaId));
        sb.append(dm).append(xfND(_originalCharaName));
        sb.append(dm).append(xfND(_originalCharaShortName));
        sb.append(dm).append(xfND(_originalCharaGroupId));
        sb.append(dm).append(xfND(_displayWidth));
        sb.append(dm).append(xfND(_displayHeight));
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
        if (_originalCharaGroup != null && _originalCharaGroup.isPresent())
        { sb.append(dm).append("originalCharaGroup"); }
        if (_originalCharaImageList != null && !_originalCharaImageList.isEmpty())
        { sb.append(dm).append("originalCharaImageList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public OriginalChara clone() {
        return (OriginalChara)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * オリジナルキャラクターID
     * @return The value of the column 'ORIGINAL_CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getOriginalCharaId() {
        checkSpecifiedProperty("originalCharaId");
        return _originalCharaId;
    }

    /**
     * [set] ORIGINAL_CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * オリジナルキャラクターID
     * @param originalCharaId The value of the column 'ORIGINAL_CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaId(Integer originalCharaId) {
        registerModifiedProperty("originalCharaId");
        _originalCharaId = originalCharaId;
    }

    /**
     * [get] ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * オリジナルキャラクター名
     * @return The value of the column 'ORIGINAL_CHARA_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getOriginalCharaName() {
        checkSpecifiedProperty("originalCharaName");
        return convertEmptyToNull(_originalCharaName);
    }

    /**
     * [set] ORIGINAL_CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * オリジナルキャラクター名
     * @param originalCharaName The value of the column 'ORIGINAL_CHARA_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaName(String originalCharaName) {
        registerModifiedProperty("originalCharaName");
        _originalCharaName = originalCharaName;
    }

    /**
     * [get] ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * オリジナルキャラクター略称
     * @return The value of the column 'ORIGINAL_CHARA_SHORT_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getOriginalCharaShortName() {
        checkSpecifiedProperty("originalCharaShortName");
        return convertEmptyToNull(_originalCharaShortName);
    }

    /**
     * [set] ORIGINAL_CHARA_SHORT_NAME: {NotNull, CHAR(1)} <br>
     * オリジナルキャラクター略称
     * @param originalCharaShortName The value of the column 'ORIGINAL_CHARA_SHORT_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaShortName(String originalCharaShortName) {
        registerModifiedProperty("originalCharaShortName");
        _originalCharaShortName = originalCharaShortName;
    }

    /**
     * [get] ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group} <br>
     * オリジナルキャラクターグループID
     * @return The value of the column 'ORIGINAL_CHARA_GROUP_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getOriginalCharaGroupId() {
        checkSpecifiedProperty("originalCharaGroupId");
        return _originalCharaGroupId;
    }

    /**
     * [set] ORIGINAL_CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara_group} <br>
     * オリジナルキャラクターグループID
     * @param originalCharaGroupId The value of the column 'ORIGINAL_CHARA_GROUP_ID'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaGroupId(Integer originalCharaGroupId) {
        registerModifiedProperty("originalCharaGroupId");
        _originalCharaGroupId = originalCharaGroupId;
    }

    /**
     * [get] DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)} <br>
     * 表示時横幅
     * @return The value of the column 'DISPLAY_WIDTH'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDisplayWidth() {
        checkSpecifiedProperty("displayWidth");
        return _displayWidth;
    }

    /**
     * [set] DISPLAY_WIDTH: {NotNull, INT UNSIGNED(10)} <br>
     * 表示時横幅
     * @param displayWidth The value of the column 'DISPLAY_WIDTH'. (basically NotNull if update: for the constraint)
     */
    public void setDisplayWidth(Integer displayWidth) {
        registerModifiedProperty("displayWidth");
        _displayWidth = displayWidth;
    }

    /**
     * [get] DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)} <br>
     * 表示時縦幅
     * @return The value of the column 'DISPLAY_HEIGHT'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDisplayHeight() {
        checkSpecifiedProperty("displayHeight");
        return _displayHeight;
    }

    /**
     * [set] DISPLAY_HEIGHT: {NotNull, INT UNSIGNED(10)} <br>
     * 表示時縦幅
     * @param displayHeight The value of the column 'DISPLAY_HEIGHT'. (basically NotNull if update: for the constraint)
     */
    public void setDisplayHeight(Integer displayHeight) {
        registerModifiedProperty("displayHeight");
        _displayHeight = displayHeight;
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
