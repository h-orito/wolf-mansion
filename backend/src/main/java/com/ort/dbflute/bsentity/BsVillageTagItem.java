package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.*;

/**
 * The entity of VILLAGE_TAG_ITEM as TABLE. <br>
 * 村タグ種別
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillageTagItem extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} */
    protected String _villageTagItemCode;

    /** VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)} */
    protected String _villageTagItemName;

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
        return "village_tag_item";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageTagItemCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of villageTagItemCode as the classification of VillageTagItem. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.VillageTagItem getVillageTagItemCodeAsVillageTagItem() {
        return CDef.VillageTagItem.of(getVillageTagItemCode()).orElse(null);
    }

    /**
     * Set the value of villageTagItemCode as the classification of VillageTagItem. <br>
     * VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem cdef) {
        setVillageTagItemCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of villageTagItemCode as 誰歓 (ANYONE_WELCOME). <br>
     * 誰歓
     */
    public void setVillageTagItemCode_誰歓() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.誰歓);
    }

    /**
     * Set the value of villageTagItemCode as R15 (R15). <br>
     * R15
     */
    public void setVillageTagItemCode_R15() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.R15);
    }

    /**
     * Set the value of villageTagItemCode as R18 (R18). <br>
     * R18
     */
    public void setVillageTagItemCode_R18() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.R18);
    }

    /**
     * Set the value of villageTagItemCode as 身内 (RELATIVES_ONLY). <br>
     * 身内
     */
    public void setVillageTagItemCode_身内() {
        setVillageTagItemCodeAsVillageTagItem(CDef.VillageTagItem.身内);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of villageTagItemCode 誰歓? <br>
     * 誰歓
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCode誰歓() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.誰歓) : false;
    }

    /**
     * Is the value of villageTagItemCode R15? <br>
     * R15
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCodeR15() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.R15) : false;
    }

    /**
     * Is the value of villageTagItemCode R18? <br>
     * R18
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCodeR18() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.R18) : false;
    }

    /**
     * Is the value of villageTagItemCode 身内? <br>
     * 身内
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillageTagItemCode身内() {
        CDef.VillageTagItem cdef = getVillageTagItemCodeAsVillageTagItem();
        return cdef != null ? cdef.equals(CDef.VillageTagItem.身内) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** VILLAGE_TAG by VILLAGE_TAG_ITEM_CODE, named 'villageTagList'. */
    protected List<VillageTag> _villageTagList;

    /**
     * [get] VILLAGE_TAG by VILLAGE_TAG_ITEM_CODE, named 'villageTagList'.
     * @return The entity list of referrer property 'villageTagList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillageTag> getVillageTagList() {
        if (_villageTagList == null) { _villageTagList = newReferrerList(); }
        return _villageTagList;
    }

    /**
     * [set] VILLAGE_TAG by VILLAGE_TAG_ITEM_CODE, named 'villageTagList'.
     * @param villageTagList The entity list of referrer property 'villageTagList'. (NullAllowed)
     */
    public void setVillageTagList(List<VillageTag> villageTagList) {
        _villageTagList = villageTagList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVillageTagItem) {
            BsVillageTagItem other = (BsVillageTagItem)obj;
            if (!xSV(_villageTagItemCode, other._villageTagItemCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villageTagItemCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_villageTagList != null) { for (VillageTag et : _villageTagList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villageTagList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villageTagItemCode));
        sb.append(dm).append(xfND(_villageTagItemName));
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
        if (_villageTagList != null && !_villageTagList.isEmpty())
        { sb.append(dm).append("villageTagList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillageTagItem clone() {
        return (VillageTagItem)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別コード
     * @return The value of the column 'VILLAGE_TAG_ITEM_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getVillageTagItemCode() {
        checkSpecifiedProperty("villageTagItemCode");
        return convertEmptyToNull(_villageTagItemCode);
    }

    /**
     * [set] VILLAGE_TAG_ITEM_CODE: {PK, NotNull, VARCHAR(50), classification=VillageTagItem} <br>
     * 村タグ種別コード
     * @param villageTagItemCode The value of the column 'VILLAGE_TAG_ITEM_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setVillageTagItemCode(String villageTagItemCode) {
        checkClassificationCode("VILLAGE_TAG_ITEM_CODE", CDef.DefMeta.VillageTagItem, villageTagItemCode);
        registerModifiedProperty("villageTagItemCode");
        _villageTagItemCode = villageTagItemCode;
    }

    /**
     * [get] VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)} <br>
     * 村タグ種別名
     * @return The value of the column 'VILLAGE_TAG_ITEM_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getVillageTagItemName() {
        checkSpecifiedProperty("villageTagItemName");
        return convertEmptyToNull(_villageTagItemName);
    }

    /**
     * [set] VILLAGE_TAG_ITEM_NAME: {NotNull, VARCHAR(50)} <br>
     * 村タグ種別名
     * @param villageTagItemName The value of the column 'VILLAGE_TAG_ITEM_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setVillageTagItemName(String villageTagItemName) {
        registerModifiedProperty("villageTagItemName");
        _villageTagItemName = villageTagItemName;
    }

    /**
     * [get] DISP_ORDER: {NotNull, INT UNSIGNED(10)} <br>
     * 表示順
     * @return The value of the column 'DISP_ORDER'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDispOrder() {
        checkSpecifiedProperty("dispOrder");
        return _dispOrder;
    }

    /**
     * [set] DISP_ORDER: {NotNull, INT UNSIGNED(10)} <br>
     * 表示順
     * @param dispOrder The value of the column 'DISP_ORDER'. (basically NotNull if update: for the constraint)
     */
    public void setDispOrder(Integer dispOrder) {
        registerModifiedProperty("dispOrder");
        _dispOrder = dispOrder;
    }

    /**
     * For framework so basically DON'T use this method.
     * @param villageTagItemCode The value of the column 'VILLAGE_TAG_ITEM_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingVillageTagItemCode(String villageTagItemCode) {
        setVillageTagItemCode(villageTagItemCode);
    }
}
