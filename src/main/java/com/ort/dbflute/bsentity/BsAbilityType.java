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
    /** ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} */
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
        return "ABILITY_TYPE";
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
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of abilityTypeCode as the classification of AbilityType. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.AbilityType getAbilityTypeCodeAsAbilityType() {
        return CDef.AbilityType.codeOf(getAbilityTypeCode());
    }

    /**
     * Set the value of abilityTypeCode as the classification of AbilityType. <br>
     * ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setAbilityTypeCodeAsAbilityType(CDef.AbilityType cdef) {
        setAbilityTypeCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of abilityTypeCode as 襲撃 (ATTACK). <br>
     * 襲撃
     */
    public void setAbilityTypeCode_襲撃() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.襲撃);
    }

    /**
     * Set the value of abilityTypeCode as 爆弾設置 (BOMB). <br>
     * 爆弾設置
     */
    public void setAbilityTypeCode_爆弾設置() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.爆弾設置);
    }

    /**
     * Set the value of abilityTypeCode as 同棲 (COHABIT). <br>
     * 同棲
     */
    public void setAbilityTypeCode_同棲() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.同棲);
    }

    /**
     * Set the value of abilityTypeCode as 指揮 (COMMAND). <br>
     * 指揮
     */
    public void setAbilityTypeCode_指揮() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.指揮);
    }

    /**
     * Set the value of abilityTypeCode as 求愛 (COURT). <br>
     * 求愛
     */
    public void setAbilityTypeCode_求愛() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.求愛);
    }

    /**
     * Set the value of abilityTypeCode as 占い (DIVINE). <br>
     * 占い
     */
    public void setAbilityTypeCode_占い() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.占い);
    }

    /**
     * Set the value of abilityTypeCode as フルーツバスケット (FRUITSBASKET). <br>
     * フルーツバスケット
     */
    public void setAbilityTypeCode_フルーツバスケット() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.フルーツバスケット);
    }

    /**
     * Set the value of abilityTypeCode as 護衛 (GUARD). <br>
     * 護衛
     */
    public void setAbilityTypeCode_護衛() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.護衛);
    }

    /**
     * Set the value of abilityTypeCode as 捜査 (INVESTIGATE). <br>
     * 捜査
     */
    public void setAbilityTypeCode_捜査() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.捜査);
    }

    /**
     * Set the value of abilityTypeCode as ストーキング (STALKING). <br>
     * ストーキング
     */
    public void setAbilityTypeCode_ストーキング() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.ストーキング);
    }

    /**
     * Set the value of abilityTypeCode as 罠設置 (TRAP). <br>
     * 罠設置
     */
    public void setAbilityTypeCode_罠設置() {
        setAbilityTypeCodeAsAbilityType(CDef.AbilityType.罠設置);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of abilityTypeCode 襲撃? <br>
     * 襲撃
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode襲撃() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.襲撃) : false;
    }

    /**
     * Is the value of abilityTypeCode 爆弾設置? <br>
     * 爆弾設置
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode爆弾設置() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.爆弾設置) : false;
    }

    /**
     * Is the value of abilityTypeCode 同棲? <br>
     * 同棲
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode同棲() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.同棲) : false;
    }

    /**
     * Is the value of abilityTypeCode 指揮? <br>
     * 指揮
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode指揮() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.指揮) : false;
    }

    /**
     * Is the value of abilityTypeCode 求愛? <br>
     * 求愛
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode求愛() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.求愛) : false;
    }

    /**
     * Is the value of abilityTypeCode 占い? <br>
     * 占い
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode占い() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.占い) : false;
    }

    /**
     * Is the value of abilityTypeCode フルーツバスケット? <br>
     * フルーツバスケット
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCodeフルーツバスケット() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.フルーツバスケット) : false;
    }

    /**
     * Is the value of abilityTypeCode 護衛? <br>
     * 護衛
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode護衛() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.護衛) : false;
    }

    /**
     * Is the value of abilityTypeCode 捜査? <br>
     * 捜査
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode捜査() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.捜査) : false;
    }

    /**
     * Is the value of abilityTypeCode ストーキング? <br>
     * ストーキング
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCodeストーキング() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.ストーキング) : false;
    }

    /**
     * Is the value of abilityTypeCode 罠設置? <br>
     * 罠設置
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAbilityTypeCode罠設置() {
        CDef.AbilityType cdef = getAbilityTypeCodeAsAbilityType();
        return cdef != null ? cdef.equals(CDef.AbilityType.罠設置) : false;
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
     * [get] ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別コード
     * @return The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getAbilityTypeCode() {
        checkSpecifiedProperty("abilityTypeCode");
        return convertEmptyToNull(_abilityTypeCode);
    }

    /**
     * [set] ABILITY_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=AbilityType} <br>
     * 能力種別コード
     * @param abilityTypeCode The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setAbilityTypeCode(String abilityTypeCode) {
        checkClassificationCode("ABILITY_TYPE_CODE", CDef.DefMeta.AbilityType, abilityTypeCode);
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

    /**
     * For framework so basically DON'T use this method.
     * @param abilityTypeCode The value of the column 'ABILITY_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingAbilityTypeCode(String abilityTypeCode) {
        setAbilityTypeCode(abilityTypeCode);
    }
}
