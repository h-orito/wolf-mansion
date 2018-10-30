package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of FACE_TYPE as TABLE. <br>
 * 表情種別
 * <pre>
 * [primary-key]
 *     FACE_TYPE_CODE
 *
 * [column]
 *     FACE_TYPE_CODE, FACE_TYPE_NAME, DISP_ORDER
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
 *     
 *
 * [referrer table]
 *     CHARA_IMAGE, MESSAGE
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     charaImageList, messageList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String faceTypeCode = entity.getFaceTypeCode();
 * String faceTypeName = entity.getFaceTypeName();
 * Integer dispOrder = entity.getDispOrder();
 * entity.setFaceTypeCode(faceTypeCode);
 * entity.setFaceTypeName(faceTypeName);
 * entity.setDispOrder(dispOrder);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsFaceType extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** FACE_TYPE_CODE: {PK, NotNull, VARCHAR(20)} */
    protected String _faceTypeCode;

    /** FACE_TYPE_NAME: {NotNull, VARCHAR(20)} */
    protected String _faceTypeName;

    /** DISP_ORDER: {NotNull, INT UNSIGNED(10)} */
    protected Integer _dispOrder;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "face_type";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_faceTypeCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** CHARA_IMAGE by FACE_TYPE_CODE, named 'charaImageList'. */
    protected List<CharaImage> _charaImageList;

    /**
     * [get] CHARA_IMAGE by FACE_TYPE_CODE, named 'charaImageList'.
     * @return The entity list of referrer property 'charaImageList'. (NotNull: even if no loading, returns empty list)
     */
    public List<CharaImage> getCharaImageList() {
        if (_charaImageList == null) { _charaImageList = newReferrerList(); }
        return _charaImageList;
    }

    /**
     * [set] CHARA_IMAGE by FACE_TYPE_CODE, named 'charaImageList'.
     * @param charaImageList The entity list of referrer property 'charaImageList'. (NullAllowed)
     */
    public void setCharaImageList(List<CharaImage> charaImageList) {
        _charaImageList = charaImageList;
    }

    /** MESSAGE by FACE_TYPE_CODE, named 'messageList'. */
    protected List<Message> _messageList;

    /**
     * [get] MESSAGE by FACE_TYPE_CODE, named 'messageList'.
     * @return The entity list of referrer property 'messageList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageList() {
        if (_messageList == null) { _messageList = newReferrerList(); }
        return _messageList;
    }

    /**
     * [set] MESSAGE by FACE_TYPE_CODE, named 'messageList'.
     * @param messageList The entity list of referrer property 'messageList'. (NullAllowed)
     */
    public void setMessageList(List<Message> messageList) {
        _messageList = messageList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsFaceType) {
            BsFaceType other = (BsFaceType)obj;
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
        hs = xCH(hs, _faceTypeCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_charaImageList != null) { for (CharaImage et : _charaImageList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "charaImageList")); } } }
        if (_messageList != null) { for (Message et : _messageList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_faceTypeCode));
        sb.append(dm).append(xfND(_faceTypeName));
        sb.append(dm).append(xfND(_dispOrder));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_charaImageList != null && !_charaImageList.isEmpty())
        { sb.append(dm).append("charaImageList"); }
        if (_messageList != null && !_messageList.isEmpty())
        { sb.append(dm).append("messageList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public FaceType clone() {
        return (FaceType)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] FACE_TYPE_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 表情種別コード
     * @return The value of the column 'FACE_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getFaceTypeCode() {
        checkSpecifiedProperty("faceTypeCode");
        return convertEmptyToNull(_faceTypeCode);
    }

    /**
     * [set] FACE_TYPE_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 表情種別コード
     * @param faceTypeCode The value of the column 'FACE_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setFaceTypeCode(String faceTypeCode) {
        registerModifiedProperty("faceTypeCode");
        _faceTypeCode = faceTypeCode;
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
     * [get] DISP_ORDER: {NotNull, INT UNSIGNED(10)} <br>
     * 並び順
     * @return The value of the column 'DISP_ORDER'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDispOrder() {
        checkSpecifiedProperty("dispOrder");
        return _dispOrder;
    }

    /**
     * [set] DISP_ORDER: {NotNull, INT UNSIGNED(10)} <br>
     * 並び順
     * @param dispOrder The value of the column 'DISP_ORDER'. (basically NotNull if update: for the constraint)
     */
    public void setDispOrder(Integer dispOrder) {
        registerModifiedProperty("dispOrder");
        _dispOrder = dispOrder;
    }
}
