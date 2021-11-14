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
 * The entity of CHARA_GROUP as TABLE. <br>
 * キャラクターグループ
 * <pre>
 * [primary-key]
 *     CHARA_GROUP_ID
 *
 * [column]
 *     CHARA_GROUP_ID, CHARA_GROUP_NAME, DESIGNER_ID, DESCRIPTION_URL, IS_AVAILABLE_CHANGE_NAME, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     CHARA_GROUP_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     DESIGNER
 *
 * [referrer table]
 *     CHARA, VILLAGE_CHARA_GROUP
 *
 * [foreign property]
 *     designer
 *
 * [referrer property]
 *     charaList, villageCharaGroupList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer charaGroupId = entity.getCharaGroupId();
 * String charaGroupName = entity.getCharaGroupName();
 * Integer designerId = entity.getDesignerId();
 * String descriptionUrl = entity.getDescriptionUrl();
 * Boolean isAvailableChangeName = entity.getIsAvailableChangeName();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setCharaGroupId(charaGroupId);
 * entity.setCharaGroupName(charaGroupName);
 * entity.setDesignerId(designerId);
 * entity.setDescriptionUrl(descriptionUrl);
 * entity.setIsAvailableChangeName(isAvailableChangeName);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsCharaGroup extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _charaGroupId;

    /** CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} */
    protected String _charaGroupName;

    /** DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to DESIGNER} */
    protected Integer _designerId;

    /** DESCRIPTION_URL: {TEXT(65535)} */
    protected String _descriptionUrl;

    /** IS_AVAILABLE_CHANGE_NAME: {NotNull, BIT, classification=Flg} */
    protected Boolean _isAvailableChangeName;

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
        return "CHARA_GROUP";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_charaGroupId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of isAvailableChangeName as the classification of Flg. <br>
     * IS_AVAILABLE_CHANGE_NAME: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsAvailableChangeNameAsFlg() {
        return CDef.Flg.codeOf(getIsAvailableChangeName());
    }

    /**
     * Set the value of isAvailableChangeName as the classification of Flg. <br>
     * IS_AVAILABLE_CHANGE_NAME: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsAvailableChangeNameAsFlg(CDef.Flg cdef) {
        setIsAvailableChangeName(cdef != null ? toBoolean(cdef.code()) : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of isAvailableChangeName as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsAvailableChangeName_True() {
        setIsAvailableChangeNameAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isAvailableChangeName as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsAvailableChangeName_False() {
        setIsAvailableChangeNameAsFlg(CDef.Flg.False);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of isAvailableChangeName True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableChangeNameTrue() {
        CDef.Flg cdef = getIsAvailableChangeNameAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isAvailableChangeName False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsAvailableChangeNameFalse() {
        CDef.Flg cdef = getIsAvailableChangeNameAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'isAvailableChangeName' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsAvailableChangeNameAlias() {
        CDef.Flg cdef = getIsAvailableChangeNameAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** DESIGNER by my DESIGNER_ID, named 'designer'. */
    protected OptionalEntity<Designer> _designer;

    /**
     * [get] DESIGNER by my DESIGNER_ID, named 'designer'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'designer'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Designer> getDesigner() {
        if (_designer == null) { _designer = OptionalEntity.relationEmpty(this, "designer"); }
        return _designer;
    }

    /**
     * [set] DESIGNER by my DESIGNER_ID, named 'designer'.
     * @param designer The entity of foreign property 'designer'. (NullAllowed)
     */
    public void setDesigner(OptionalEntity<Designer> designer) {
        _designer = designer;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** CHARA by CHARA_GROUP_ID, named 'charaList'. */
    protected List<Chara> _charaList;

    /**
     * [get] CHARA by CHARA_GROUP_ID, named 'charaList'.
     * @return The entity list of referrer property 'charaList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Chara> getCharaList() {
        if (_charaList == null) { _charaList = newReferrerList(); }
        return _charaList;
    }

    /**
     * [set] CHARA by CHARA_GROUP_ID, named 'charaList'.
     * @param charaList The entity list of referrer property 'charaList'. (NullAllowed)
     */
    public void setCharaList(List<Chara> charaList) {
        _charaList = charaList;
    }

    /** VILLAGE_CHARA_GROUP by CHARA_GROUP_ID, named 'villageCharaGroupList'. */
    protected List<VillageCharaGroup> _villageCharaGroupList;

    /**
     * [get] VILLAGE_CHARA_GROUP by CHARA_GROUP_ID, named 'villageCharaGroupList'.
     * @return The entity list of referrer property 'villageCharaGroupList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillageCharaGroup> getVillageCharaGroupList() {
        if (_villageCharaGroupList == null) { _villageCharaGroupList = newReferrerList(); }
        return _villageCharaGroupList;
    }

    /**
     * [set] VILLAGE_CHARA_GROUP by CHARA_GROUP_ID, named 'villageCharaGroupList'.
     * @param villageCharaGroupList The entity list of referrer property 'villageCharaGroupList'. (NullAllowed)
     */
    public void setVillageCharaGroupList(List<VillageCharaGroup> villageCharaGroupList) {
        _villageCharaGroupList = villageCharaGroupList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsCharaGroup) {
            BsCharaGroup other = (BsCharaGroup)obj;
            if (!xSV(_charaGroupId, other._charaGroupId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _charaGroupId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_designer != null && _designer.isPresent())
        { sb.append(li).append(xbRDS(_designer, "designer")); }
        if (_charaList != null) { for (Chara et : _charaList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "charaList")); } } }
        if (_villageCharaGroupList != null) { for (VillageCharaGroup et : _villageCharaGroupList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villageCharaGroupList")); } } }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_charaGroupId));
        sb.append(dm).append(xfND(_charaGroupName));
        sb.append(dm).append(xfND(_designerId));
        sb.append(dm).append(xfND(_descriptionUrl));
        sb.append(dm).append(xfND(_isAvailableChangeName));
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
        if (_designer != null && _designer.isPresent())
        { sb.append(dm).append("designer"); }
        if (_charaList != null && !_charaList.isEmpty())
        { sb.append(dm).append("charaList"); }
        if (_villageCharaGroupList != null && !_villageCharaGroupList.isEmpty())
        { sb.append(dm).append("villageCharaGroupList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public CharaGroup clone() {
        return (CharaGroup)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * キャラクターグループID
     * @return The value of the column 'CHARA_GROUP_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaGroupId() {
        checkSpecifiedProperty("charaGroupId");
        return _charaGroupId;
    }

    /**
     * [set] CHARA_GROUP_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * キャラクターグループID
     * @param charaGroupId The value of the column 'CHARA_GROUP_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaGroupId(Integer charaGroupId) {
        registerModifiedProperty("charaGroupId");
        _charaGroupId = charaGroupId;
    }

    /**
     * [get] CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクターグループ名
     * @return The value of the column 'CHARA_GROUP_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getCharaGroupName() {
        checkSpecifiedProperty("charaGroupName");
        return convertEmptyToNull(_charaGroupName);
    }

    /**
     * [set] CHARA_GROUP_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクターグループ名
     * @param charaGroupName The value of the column 'CHARA_GROUP_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setCharaGroupName(String charaGroupName) {
        registerModifiedProperty("charaGroupName");
        _charaGroupName = charaGroupName;
    }

    /**
     * [get] DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to DESIGNER} <br>
     * デザイナーID
     * @return The value of the column 'DESIGNER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDesignerId() {
        checkSpecifiedProperty("designerId");
        return _designerId;
    }

    /**
     * [set] DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to DESIGNER} <br>
     * デザイナーID
     * @param designerId The value of the column 'DESIGNER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setDesignerId(Integer designerId) {
        registerModifiedProperty("designerId");
        _designerId = designerId;
    }

    /**
     * [get] DESCRIPTION_URL: {TEXT(65535)} <br>
     * キャラチップURL : キャラチップの利用規約や配布サイトのURL
     * @return The value of the column 'DESCRIPTION_URL'. (NullAllowed even if selected: for no constraint)
     */
    public String getDescriptionUrl() {
        checkSpecifiedProperty("descriptionUrl");
        return convertEmptyToNull(_descriptionUrl);
    }

    /**
     * [set] DESCRIPTION_URL: {TEXT(65535)} <br>
     * キャラチップURL : キャラチップの利用規約や配布サイトのURL
     * @param descriptionUrl The value of the column 'DESCRIPTION_URL'. (NullAllowed: null update allowed for no constraint)
     */
    public void setDescriptionUrl(String descriptionUrl) {
        registerModifiedProperty("descriptionUrl");
        _descriptionUrl = descriptionUrl;
    }

    /**
     * [get] IS_AVAILABLE_CHANGE_NAME: {NotNull, BIT, classification=Flg} <br>
     * 名称変更可能か
     * @return The value of the column 'IS_AVAILABLE_CHANGE_NAME'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsAvailableChangeName() {
        checkSpecifiedProperty("isAvailableChangeName");
        return _isAvailableChangeName;
    }

    /**
     * [set] IS_AVAILABLE_CHANGE_NAME: {NotNull, BIT, classification=Flg} <br>
     * 名称変更可能か
     * @param isAvailableChangeName The value of the column 'IS_AVAILABLE_CHANGE_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setIsAvailableChangeName(Boolean isAvailableChangeName) {
        checkClassificationCode("IS_AVAILABLE_CHANGE_NAME", CDef.DefMeta.Flg, isAvailableChangeName);
        registerModifiedProperty("isAvailableChangeName");
        _isAvailableChangeName = isAvailableChangeName;
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
