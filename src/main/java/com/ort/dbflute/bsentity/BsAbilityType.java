package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of ABILITY_TYPE as TABLE. <br>
 * 能力種別
 * <pre>
 * [primary-key]
 *     ABILITY_TYPE_CODE
 *
 * [column]
 *     ABILITY_TYPE_CODE, ABILITY_TYPE_NAME
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
 *     ABILITY
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     abilityList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String abilityTypeCode = entity.getAbilityTypeCode();
 * String abilityTypeName = entity.getAbilityTypeName();
 * entity.setAbilityTypeCode(abilityTypeCode);
 * entity.setAbilityTypeName(abilityTypeName);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsAbilityType extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20)} */
    protected String _abilityTypeCode;

    /** ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)} */
    protected String _abilityTypeName;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "ability_type";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_abilityTypeCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** ABILITY by ABILITY_TYPE_CODE, named 'abilityList'. */
    protected List<Ability> _abilityList;

    /**
     * [get] ABILITY by ABILITY_TYPE_CODE, named 'abilityList'.
     * @return The entity list of referrer property 'abilityList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Ability> getAbilityList() {
        if (_abilityList == null) { _abilityList = newReferrerList(); }
        return _abilityList;
    }

    /**
     * [set] ABILITY by ABILITY_TYPE_CODE, named 'abilityList'.
     * @param abilityList The entity list of referrer property 'abilityList'. (NullAllowed)
     */
    public void setAbilityList(List<Ability> abilityList) {
        _abilityList = abilityList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsAbilityType) {
            BsAbilityType other = (BsAbilityType)obj;
            if (!xSV(_abilityTypeCode, other._abilityTypeCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _abilityTypeCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_abilityList != null) { for (Ability et : _abilityList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "abilityList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_abilityTypeCode));
        sb.append(dm).append(xfND(_abilityTypeName));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_abilityList != null && !_abilityList.isEmpty())
        { sb.append(dm).append("abilityList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public AbilityType clone() {
        return (AbilityType)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 能力種別コード
     * @return The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getAbilityTypeCode() {
        checkSpecifiedProperty("abilityTypeCode");
        return convertEmptyToNull(_abilityTypeCode);
    }

    /**
     * [set] ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 能力種別コード
     * @param abilityTypeCode The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setAbilityTypeCode(String abilityTypeCode) {
        registerModifiedProperty("abilityTypeCode");
        _abilityTypeCode = abilityTypeCode;
    }

    /**
     * [get] ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * 能力種別
     * @return The value of the column 'ABILITY_TYPE_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getAbilityTypeName() {
        checkSpecifiedProperty("abilityTypeName");
        return convertEmptyToNull(_abilityTypeName);
    }

    /**
     * [set] ABILITY_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * 能力種別
     * @param abilityTypeName The value of the column 'ABILITY_TYPE_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setAbilityTypeName(String abilityTypeName) {
        registerModifiedProperty("abilityTypeName");
        _abilityTypeName = abilityTypeName;
    }
}
