package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of CHARA_IMAGE as TABLE. <br>
 * キャラクター画像
 * <pre>
 * [primary-key]
 *     CHARA_ID, FACE_TYPE_CODE
 *
 * [column]
 *     CHARA_ID, FACE_TYPE_CODE, CHARA_IMG_URL
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
 *     CHARA, FACE_TYPE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     chara, faceType
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer charaId = entity.getCharaId();
 * String faceTypeCode = entity.getFaceTypeCode();
 * String charaImgUrl = entity.getCharaImgUrl();
 * entity.setCharaId(charaId);
 * entity.setFaceTypeCode(faceTypeCode);
 * entity.setCharaImgUrl(charaImgUrl);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsCharaImage extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** CHARA_ID: {PK, NotNull, INT UNSIGNED(10), FK to chara} */
    protected Integer _charaId;

    /** FACE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to face_type} */
    protected String _faceTypeCode;

    /** CHARA_IMG_URL: {NotNull, VARCHAR(100)} */
    protected String _charaImgUrl;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "chara_image";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_charaId == null) { return false; }
        if (_faceTypeCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CHARA by my CHARA_ID, named 'chara'. */
    protected OptionalEntity<Chara> _chara;

    /**
     * [get] CHARA by my CHARA_ID, named 'chara'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'chara'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Chara> getChara() {
        if (_chara == null) { _chara = OptionalEntity.relationEmpty(this, "chara"); }
        return _chara;
    }

    /**
     * [set] CHARA by my CHARA_ID, named 'chara'.
     * @param chara The entity of foreign property 'chara'. (NullAllowed)
     */
    public void setChara(OptionalEntity<Chara> chara) {
        _chara = chara;
    }

    /** FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'. */
    protected OptionalEntity<FaceType> _faceType;

    /**
     * [get] FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'faceType'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<FaceType> getFaceType() {
        if (_faceType == null) { _faceType = OptionalEntity.relationEmpty(this, "faceType"); }
        return _faceType;
    }

    /**
     * [set] FACE_TYPE by my FACE_TYPE_CODE, named 'faceType'.
     * @param faceType The entity of foreign property 'faceType'. (NullAllowed)
     */
    public void setFaceType(OptionalEntity<FaceType> faceType) {
        _faceType = faceType;
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
        if (obj instanceof BsCharaImage) {
            BsCharaImage other = (BsCharaImage)obj;
            if (!xSV(_charaId, other._charaId)) { return false; }
            if (!xSV(_faceTypeCode, other._faceTypeCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _charaId);
        hs = xCH(hs, _faceTypeCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_chara != null && _chara.isPresent())
        { sb.append(li).append(xbRDS(_chara, "chara")); }
        if (_faceType != null && _faceType.isPresent())
        { sb.append(li).append(xbRDS(_faceType, "faceType")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_charaId));
        sb.append(dm).append(xfND(_faceTypeCode));
        sb.append(dm).append(xfND(_charaImgUrl));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_chara != null && _chara.isPresent())
        { sb.append(dm).append("chara"); }
        if (_faceType != null && _faceType.isPresent())
        { sb.append(dm).append("faceType"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public CharaImage clone() {
        return (CharaImage)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] CHARA_ID: {PK, NotNull, INT UNSIGNED(10), FK to chara} <br>
     * キャラクターID
     * @return The value of the column 'CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaId() {
        checkSpecifiedProperty("charaId");
        return _charaId;
    }

    /**
     * [set] CHARA_ID: {PK, NotNull, INT UNSIGNED(10), FK to chara} <br>
     * キャラクターID
     * @param charaId The value of the column 'CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaId(Integer charaId) {
        registerModifiedProperty("charaId");
        _charaId = charaId;
    }

    /**
     * [get] FACE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to face_type} <br>
     * 表情種別コード
     * @return The value of the column 'FACE_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getFaceTypeCode() {
        checkSpecifiedProperty("faceTypeCode");
        return convertEmptyToNull(_faceTypeCode);
    }

    /**
     * [set] FACE_TYPE_CODE: {PK, IX, NotNull, VARCHAR(20), FK to face_type} <br>
     * 表情種別コード
     * @param faceTypeCode The value of the column 'FACE_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setFaceTypeCode(String faceTypeCode) {
        registerModifiedProperty("faceTypeCode");
        _faceTypeCode = faceTypeCode;
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
}
