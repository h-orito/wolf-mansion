package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of designer as TABLE. <br>
 * デザイナー
 * <pre>
 * [primary-key]
 *     DESIGNER_ID
 *
 * [column]
 *     DESIGNER_ID, DESIGNER_NAME
 *
 * [sequence]
 *     
 *
 * [identity]
 *     DESIGNER_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     
 *
 * [referrer table]
 *     chara_group
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     charaGroupList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer designerId = entity.getDesignerId();
 * String designerName = entity.getDesignerName();
 * entity.setDesignerId(designerId);
 * entity.setDesignerName(designerName);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsDesigner extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** DESIGNER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _designerId;

    /** DESIGNER_NAME: {NotNull, VARCHAR(40)} */
    protected String _designerName;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "designer";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_designerId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** chara_group by DESIGNER_ID, named 'charaGroupList'. */
    protected List<CharaGroup> _charaGroupList;

    /**
     * [get] chara_group by DESIGNER_ID, named 'charaGroupList'.
     * @return The entity list of referrer property 'charaGroupList'. (NotNull: even if no loading, returns empty list)
     */
    public List<CharaGroup> getCharaGroupList() {
        if (_charaGroupList == null) { _charaGroupList = newReferrerList(); }
        return _charaGroupList;
    }

    /**
     * [set] chara_group by DESIGNER_ID, named 'charaGroupList'.
     * @param charaGroupList The entity list of referrer property 'charaGroupList'. (NullAllowed)
     */
    public void setCharaGroupList(List<CharaGroup> charaGroupList) {
        _charaGroupList = charaGroupList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsDesigner) {
            BsDesigner other = (BsDesigner)obj;
            if (!xSV(_designerId, other._designerId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _designerId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_charaGroupList != null) { for (CharaGroup et : _charaGroupList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "charaGroupList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_designerId));
        sb.append(dm).append(xfND(_designerName));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_charaGroupList != null && !_charaGroupList.isEmpty())
        { sb.append(dm).append("charaGroupList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Designer clone() {
        return (Designer)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] DESIGNER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * デザイナーID
     * @return The value of the column 'DESIGNER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDesignerId() {
        checkSpecifiedProperty("designerId");
        return _designerId;
    }

    /**
     * [set] DESIGNER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * デザイナーID
     * @param designerId The value of the column 'DESIGNER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setDesignerId(Integer designerId) {
        registerModifiedProperty("designerId");
        _designerId = designerId;
    }

    /**
     * [get] DESIGNER_NAME: {NotNull, VARCHAR(40)} <br>
     * デザイナー名
     * @return The value of the column 'DESIGNER_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getDesignerName() {
        checkSpecifiedProperty("designerName");
        return _designerName;
    }

    /**
     * [set] DESIGNER_NAME: {NotNull, VARCHAR(40)} <br>
     * デザイナー名
     * @param designerName The value of the column 'DESIGNER_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setDesignerName(String designerName) {
        registerModifiedProperty("designerName");
        _designerName = designerName;
    }
}
