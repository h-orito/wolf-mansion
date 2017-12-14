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
 * The entity of chara_group as TABLE. <br>
 * キャラクターグループ
 * <pre>
 * [primary-key]
 *     CHARA_GROUP_ID
 *
 * [column]
 *     CHARA_GROUP_ID, CHARA_NAME, DESIGNER_ID
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
 *     designer
 *
 * [referrer table]
 *     chara
 *
 * [foreign property]
 *     designer
 *
 * [referrer property]
 *     charaList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer charaGroupId = entity.getCharaGroupId();
 * String charaName = entity.getCharaName();
 * Integer designerId = entity.getDesignerId();
 * entity.setCharaGroupId(charaGroupId);
 * entity.setCharaName(charaName);
 * entity.setDesignerId(designerId);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsCharaGroup extends AbstractEntity implements DomainEntity {

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

    /** CHARA_NAME: {NotNull, VARCHAR(40)} */
    protected String _charaName;

    /** DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer} */
    protected Integer _designerId;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "chara_group";
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
    //                                                                    Foreign Property
    //                                                                    ================
    /** designer by my DESIGNER_ID, named 'designer'. */
    protected OptionalEntity<Designer> _designer;

    /**
     * [get] designer by my DESIGNER_ID, named 'designer'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'designer'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Designer> getDesigner() {
        if (_designer == null) { _designer = OptionalEntity.relationEmpty(this, "designer"); }
        return _designer;
    }

    /**
     * [set] designer by my DESIGNER_ID, named 'designer'.
     * @param designer The entity of foreign property 'designer'. (NullAllowed)
     */
    public void setDesigner(OptionalEntity<Designer> designer) {
        _designer = designer;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** chara by CHARA_GROUP_ID, named 'charaList'. */
    protected List<Chara> _charaList;

    /**
     * [get] chara by CHARA_GROUP_ID, named 'charaList'.
     * @return The entity list of referrer property 'charaList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Chara> getCharaList() {
        if (_charaList == null) { _charaList = newReferrerList(); }
        return _charaList;
    }

    /**
     * [set] chara by CHARA_GROUP_ID, named 'charaList'.
     * @param charaList The entity list of referrer property 'charaList'. (NullAllowed)
     */
    public void setCharaList(List<Chara> charaList) {
        _charaList = charaList;
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
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_charaGroupId));
        sb.append(dm).append(xfND(_charaName));
        sb.append(dm).append(xfND(_designerId));
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
     * [get] CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクターグループ名
     * @return The value of the column 'CHARA_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getCharaName() {
        checkSpecifiedProperty("charaName");
        return _charaName;
    }

    /**
     * [set] CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクターグループ名
     * @param charaName The value of the column 'CHARA_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setCharaName(String charaName) {
        registerModifiedProperty("charaName");
        _charaName = charaName;
    }

    /**
     * [get] DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer} <br>
     * デザイナーID
     * @return The value of the column 'DESIGNER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDesignerId() {
        checkSpecifiedProperty("designerId");
        return _designerId;
    }

    /**
     * [set] DESIGNER_ID: {IX, NotNull, INT UNSIGNED(10), FK to designer} <br>
     * デザイナーID
     * @param designerId The value of the column 'DESIGNER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setDesignerId(Integer designerId) {
        registerModifiedProperty("designerId");
        _designerId = designerId;
    }
}
