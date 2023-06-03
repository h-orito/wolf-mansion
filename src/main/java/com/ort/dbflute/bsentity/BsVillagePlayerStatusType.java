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
 * The entity of VILLAGE_PLAYER_STATUS_TYPE as TABLE. <br>
 * 村参加者ステータス種別
 * <pre>
 * [primary-key]
 *     VILLAGE_PLAYER_STATUS_TYPE_CODE
 *
 * [column]
 *     VILLAGE_PLAYER_STATUS_TYPE_CODE, VILLAGE_PLAYER_STATUS_TYPE_NAME
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
 *     VILLAGE_PLAYER_STATUS
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     villagePlayerStatusList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String villagePlayerStatusTypeCode = entity.getVillagePlayerStatusTypeCode();
 * String villagePlayerStatusTypeName = entity.getVillagePlayerStatusTypeName();
 * entity.setVillagePlayerStatusTypeCode(villagePlayerStatusTypeCode);
 * entity.setVillagePlayerStatusTypeName(villagePlayerStatusTypeName);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillagePlayerStatusType extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} */
    protected String _villagePlayerStatusTypeCode;

    /** VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)} */
    protected String _villagePlayerStatusTypeName;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "village_player_status_type";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villagePlayerStatusTypeCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of villagePlayerStatusTypeCode as the classification of VillagePlayerStatusType. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.VillagePlayerStatusType getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType() {
        return CDef.VillagePlayerStatusType.codeOf(getVillagePlayerStatusTypeCode());
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as the classification of VillagePlayerStatusType. <br>
     * VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType cdef) {
        setVillagePlayerStatusTypeCode(cdef != null ? cdef.code() : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of villagePlayerStatusTypeCode as 信念 (BELIEF). <br>
     * 信念
     */
    public void setVillagePlayerStatusTypeCode_信念() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.信念);
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as 反呪符 (COUNTERCURSEMARK). <br>
     * 反呪符
     */
    public void setVillagePlayerStatusTypeCode_反呪符() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.反呪符);
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as 呪縛符 (CURSEMARK). <br>
     * 呪縛符
     */
    public void setVillagePlayerStatusTypeCode_呪縛符() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.呪縛符);
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as 不敬 (DISRESPECTFUL). <br>
     * 不敬
     */
    public void setVillagePlayerStatusTypeCode_不敬() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.不敬);
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as 後追い (FOLLOWING_SUICIDE). <br>
     * 後追い
     */
    public void setVillagePlayerStatusTypeCode_後追い() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.後追い);
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as 狐憑き (FOX_POSSESSION). <br>
     * 狐憑き
     */
    public void setVillagePlayerStatusTypeCode_狐憑き() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.狐憑き);
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as 狂気 (INSANITY). <br>
     * 狂気
     */
    public void setVillagePlayerStatusTypeCode_狂気() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.狂気);
    }

    /**
     * Set the value of villagePlayerStatusTypeCode as 保険 (INSURANCE). <br>
     * 保険
     */
    public void setVillagePlayerStatusTypeCode_保険() {
        setVillagePlayerStatusTypeCodeAsVillagePlayerStatusType(CDef.VillagePlayerStatusType.保険);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of villagePlayerStatusTypeCode 信念? <br>
     * 信念
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode信念() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.信念) : false;
    }

    /**
     * Is the value of villagePlayerStatusTypeCode 反呪符? <br>
     * 反呪符
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode反呪符() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.反呪符) : false;
    }

    /**
     * Is the value of villagePlayerStatusTypeCode 呪縛符? <br>
     * 呪縛符
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode呪縛符() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.呪縛符) : false;
    }

    /**
     * Is the value of villagePlayerStatusTypeCode 不敬? <br>
     * 不敬
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode不敬() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.不敬) : false;
    }

    /**
     * Is the value of villagePlayerStatusTypeCode 後追い? <br>
     * 後追い
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode後追い() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.後追い) : false;
    }

    /**
     * Is the value of villagePlayerStatusTypeCode 狐憑き? <br>
     * 狐憑き
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode狐憑き() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.狐憑き) : false;
    }

    /**
     * Is the value of villagePlayerStatusTypeCode 狂気? <br>
     * 狂気
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode狂気() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.狂気) : false;
    }

    /**
     * Is the value of villagePlayerStatusTypeCode 保険? <br>
     * 保険
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isVillagePlayerStatusTypeCode保険() {
        CDef.VillagePlayerStatusType cdef = getVillagePlayerStatusTypeCodeAsVillagePlayerStatusType();
        return cdef != null ? cdef.equals(CDef.VillagePlayerStatusType.保険) : false;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusList'. */
    protected List<VillagePlayerStatus> _villagePlayerStatusList;

    /**
     * [get] VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusList'.
     * @return The entity list of referrer property 'villagePlayerStatusList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayerStatus> getVillagePlayerStatusList() {
        if (_villagePlayerStatusList == null) { _villagePlayerStatusList = newReferrerList(); }
        return _villagePlayerStatusList;
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS by VILLAGE_PLAYER_STATUS_CODE, named 'villagePlayerStatusList'.
     * @param villagePlayerStatusList The entity list of referrer property 'villagePlayerStatusList'. (NullAllowed)
     */
    public void setVillagePlayerStatusList(List<VillagePlayerStatus> villagePlayerStatusList) {
        _villagePlayerStatusList = villagePlayerStatusList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsVillagePlayerStatusType) {
            BsVillagePlayerStatusType other = (BsVillagePlayerStatusType)obj;
            if (!xSV(_villagePlayerStatusTypeCode, other._villagePlayerStatusTypeCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villagePlayerStatusTypeCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_villagePlayerStatusList != null) { for (VillagePlayerStatus et : _villagePlayerStatusList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerStatusList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villagePlayerStatusTypeCode));
        sb.append(dm).append(xfND(_villagePlayerStatusTypeName));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_villagePlayerStatusList != null && !_villagePlayerStatusList.isEmpty())
        { sb.append(dm).append("villagePlayerStatusList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillagePlayerStatusType clone() {
        return (VillagePlayerStatusType)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別コード
     * @return The value of the column 'VILLAGE_PLAYER_STATUS_TYPE_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getVillagePlayerStatusTypeCode() {
        checkSpecifiedProperty("villagePlayerStatusTypeCode");
        return convertEmptyToNull(_villagePlayerStatusTypeCode);
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS_TYPE_CODE: {PK, NotNull, VARCHAR(20), classification=VillagePlayerStatusType} <br>
     * 村参加者ステータス種別コード
     * @param villagePlayerStatusTypeCode The value of the column 'VILLAGE_PLAYER_STATUS_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setVillagePlayerStatusTypeCode(String villagePlayerStatusTypeCode) {
        checkClassificationCode("VILLAGE_PLAYER_STATUS_TYPE_CODE", CDef.DefMeta.VillagePlayerStatusType, villagePlayerStatusTypeCode);
        registerModifiedProperty("villagePlayerStatusTypeCode");
        _villagePlayerStatusTypeCode = villagePlayerStatusTypeCode;
    }

    /**
     * [get] VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * 村参加者ステータス種別名
     * @return The value of the column 'VILLAGE_PLAYER_STATUS_TYPE_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getVillagePlayerStatusTypeName() {
        checkSpecifiedProperty("villagePlayerStatusTypeName");
        return convertEmptyToNull(_villagePlayerStatusTypeName);
    }

    /**
     * [set] VILLAGE_PLAYER_STATUS_TYPE_NAME: {NotNull, VARCHAR(20)} <br>
     * 村参加者ステータス種別名
     * @param villagePlayerStatusTypeName The value of the column 'VILLAGE_PLAYER_STATUS_TYPE_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setVillagePlayerStatusTypeName(String villagePlayerStatusTypeName) {
        registerModifiedProperty("villagePlayerStatusTypeName");
        _villagePlayerStatusTypeName = villagePlayerStatusTypeName;
    }

    /**
     * For framework so basically DON'T use this method.
     * @param villagePlayerStatusTypeCode The value of the column 'VILLAGE_PLAYER_STATUS_TYPE_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingVillagePlayerStatusTypeCode(String villagePlayerStatusTypeCode) {
        setVillagePlayerStatusTypeCode(villagePlayerStatusTypeCode);
    }
}
