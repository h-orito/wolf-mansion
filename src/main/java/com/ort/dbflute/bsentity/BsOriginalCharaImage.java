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
 * The entity of ORIGINAL_CHARA_IMAGE as TABLE. <br>
 * オリジナルキャラクター画像
 * <pre>
 * [primary-key]
 *     ORIGINAL_CHARA_IMAGE_ID
 *
 * [column]
 *     ORIGINAL_CHARA_IMAGE_ID, ORIGINAL_CHARA_ID, FACE_TYPE_NAME, CHARA_IMG_URL, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     ORIGINAL_CHARA_IMAGE_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     ORIGINAL_CHARA
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     originalChara
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer originalCharaImageId = entity.getOriginalCharaImageId();
 * Integer originalCharaId = entity.getOriginalCharaId();
 * String faceTypeName = entity.getFaceTypeName();
 * String charaImgUrl = entity.getCharaImgUrl();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setOriginalCharaImageId(originalCharaImageId);
 * entity.setOriginalCharaId(originalCharaId);
 * entity.setFaceTypeName(faceTypeName);
 * entity.setCharaImgUrl(charaImgUrl);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsOriginalCharaImage extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _originalCharaImageId;

    /** ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara} */
    protected Integer _originalCharaId;

    /** FACE_TYPE_NAME: {NotNull, VARCHAR(20)} */
    protected String _faceTypeName;

    /** CHARA_IMG_URL: {NotNull, VARCHAR(100)} */
    protected String _charaImgUrl;

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
        return "original_chara_image";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_originalCharaImageId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** ORIGINAL_CHARA by my ORIGINAL_CHARA_ID, named 'originalChara'. */
    protected OptionalEntity<OriginalChara> _originalChara;

    /**
     * [get] ORIGINAL_CHARA by my ORIGINAL_CHARA_ID, named 'originalChara'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'originalChara'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<OriginalChara> getOriginalChara() {
        if (_originalChara == null) { _originalChara = OptionalEntity.relationEmpty(this, "originalChara"); }
        return _originalChara;
    }

    /**
     * [set] ORIGINAL_CHARA by my ORIGINAL_CHARA_ID, named 'originalChara'.
     * @param originalChara The entity of foreign property 'originalChara'. (NullAllowed)
     */
    public void setOriginalChara(OptionalEntity<OriginalChara> originalChara) {
        _originalChara = originalChara;
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
        if (obj instanceof BsOriginalCharaImage) {
            BsOriginalCharaImage other = (BsOriginalCharaImage)obj;
            if (!xSV(_originalCharaImageId, other._originalCharaImageId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _originalCharaImageId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_originalChara != null && _originalChara.isPresent())
        { sb.append(li).append(xbRDS(_originalChara, "originalChara")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_originalCharaImageId));
        sb.append(dm).append(xfND(_originalCharaId));
        sb.append(dm).append(xfND(_faceTypeName));
        sb.append(dm).append(xfND(_charaImgUrl));
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
        if (_originalChara != null && _originalChara.isPresent())
        { sb.append(dm).append("originalChara"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public OriginalCharaImage clone() {
        return (OriginalCharaImage)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * オリジナルキャラクター画像ID
     * @return The value of the column 'ORIGINAL_CHARA_IMAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getOriginalCharaImageId() {
        checkSpecifiedProperty("originalCharaImageId");
        return _originalCharaImageId;
    }

    /**
     * [set] ORIGINAL_CHARA_IMAGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * オリジナルキャラクター画像ID
     * @param originalCharaImageId The value of the column 'ORIGINAL_CHARA_IMAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaImageId(Integer originalCharaImageId) {
        registerModifiedProperty("originalCharaImageId");
        _originalCharaImageId = originalCharaImageId;
    }

    /**
     * [get] ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara} <br>
     * オリジナルキャラクターID
     * @return The value of the column 'ORIGINAL_CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getOriginalCharaId() {
        checkSpecifiedProperty("originalCharaId");
        return _originalCharaId;
    }

    /**
     * [set] ORIGINAL_CHARA_ID: {IX, NotNull, INT UNSIGNED(10), FK to original_chara} <br>
     * オリジナルキャラクターID
     * @param originalCharaId The value of the column 'ORIGINAL_CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setOriginalCharaId(Integer originalCharaId) {
        registerModifiedProperty("originalCharaId");
        _originalCharaId = originalCharaId;
    }

    /**
     * [get] FACE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * 表情種別名
     * @return The value of the column 'FACE_TYPE_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getFaceTypeName() {
        checkSpecifiedProperty("faceTypeName");
        return convertEmptyToNull(_faceTypeName);
    }

    /**
     * [set] FACE_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * 表情種別名
     * @param faceTypeName The value of the column 'FACE_TYPE_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setFaceTypeName(String faceTypeName) {
        registerModifiedProperty("faceTypeName");
        _faceTypeName = faceTypeName;
    }

    /**
     * [get] CHARA_IMG_URL: {NotNull, VARCHAR(100)} <br>
     * キャラクター画像URL
     * @return The value of the column 'CHARA_IMG_URL'. (basically NotNull if selected: for the constraint)
     */
    public String getCharaImgUrl() {
        checkSpecifiedProperty("charaImgUrl");
        return convertEmptyToNull(_charaImgUrl);
    }

    /**
     * [set] CHARA_IMG_URL: {NotNull, VARCHAR(100)} <br>
     * キャラクター画像URL
     * @param charaImgUrl The value of the column 'CHARA_IMG_URL'. (basically NotNull if update: for the constraint)
     */
    public void setCharaImgUrl(String charaImgUrl) {
        registerModifiedProperty("charaImgUrl");
        _charaImgUrl = charaImgUrl;
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
