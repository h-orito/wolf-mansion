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
 * The entity of CHARA as TABLE. <br>
 * キャラクター
 * <pre>
 * [primary-key]
 *     CHARA_ID
 *
 * [column]
 *     CHARA_ID, CHARA_NAME, CHARA_GROUP_ID, CHARA_IMG_URL
 *
 * [sequence]
 *     
 *
 * [identity]
 *     CHARA_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     CHARA_GROUP
 *
 * [referrer table]
 *     VILLAGE_PLAYER
 *
 * [foreign property]
 *     charaGroup
 *
 * [referrer property]
 *     villagePlayerList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer charaId = entity.getCharaId();
 * String charaName = entity.getCharaName();
 * Integer charaGroupId = entity.getCharaGroupId();
 * String charaImgUrl = entity.getCharaImgUrl();
 * entity.setCharaId(charaId);
 * entity.setCharaName(charaName);
 * entity.setCharaGroupId(charaGroupId);
 * entity.setCharaImgUrl(charaImgUrl);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsChara extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _charaId;

    /** CHARA_NAME: {NotNull, VARCHAR(40)} */
    protected String _charaName;

    /** CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group} */
    protected Integer _charaGroupId;

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
        return "chara";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_charaId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'. */
    protected OptionalEntity<CharaGroup> _charaGroup;

    /**
     * [get] CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'charaGroup'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<CharaGroup> getCharaGroup() {
        if (_charaGroup == null) { _charaGroup = OptionalEntity.relationEmpty(this, "charaGroup"); }
        return _charaGroup;
    }

    /**
     * [set] CHARA_GROUP by my CHARA_GROUP_ID, named 'charaGroup'.
     * @param charaGroup The entity of foreign property 'charaGroup'. (NullAllowed)
     */
    public void setCharaGroup(OptionalEntity<CharaGroup> charaGroup) {
        _charaGroup = charaGroup;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** VILLAGE_PLAYER by CHARA_ID, named 'villagePlayerList'. */
    protected List<VillagePlayer> _villagePlayerList;

    /**
     * [get] VILLAGE_PLAYER by CHARA_ID, named 'villagePlayerList'.
     * @return The entity list of referrer property 'villagePlayerList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerList() {
        if (_villagePlayerList == null) { _villagePlayerList = newReferrerList(); }
        return _villagePlayerList;
    }

    /**
     * [set] VILLAGE_PLAYER by CHARA_ID, named 'villagePlayerList'.
     * @param villagePlayerList The entity list of referrer property 'villagePlayerList'. (NullAllowed)
     */
    public void setVillagePlayerList(List<VillagePlayer> villagePlayerList) {
        _villagePlayerList = villagePlayerList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsChara) {
            BsChara other = (BsChara)obj;
            if (!xSV(_charaId, other._charaId)) { return false; }
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
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_charaGroup != null && _charaGroup.isPresent())
        { sb.append(li).append(xbRDS(_charaGroup, "charaGroup")); }
        if (_villagePlayerList != null) { for (VillagePlayer et : _villagePlayerList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerList")); } } }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_charaId));
        sb.append(dm).append(xfND(_charaName));
        sb.append(dm).append(xfND(_charaGroupId));
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
        if (_charaGroup != null && _charaGroup.isPresent())
        { sb.append(dm).append("charaGroup"); }
        if (_villagePlayerList != null && !_villagePlayerList.isEmpty())
        { sb.append(dm).append("villagePlayerList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Chara clone() {
        return (Chara)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * キャラクターID
     * @return The value of the column 'CHARA_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaId() {
        checkSpecifiedProperty("charaId");
        return _charaId;
    }

    /**
     * [set] CHARA_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * キャラクターID
     * @param charaId The value of the column 'CHARA_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaId(Integer charaId) {
        registerModifiedProperty("charaId");
        _charaId = charaId;
    }

    /**
     * [get] CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクター名
     * @return The value of the column 'CHARA_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getCharaName() {
        checkSpecifiedProperty("charaName");
        return convertEmptyToNull(_charaName);
    }

    /**
     * [set] CHARA_NAME: {NotNull, VARCHAR(40)} <br>
     * キャラクター名
     * @param charaName The value of the column 'CHARA_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setCharaName(String charaName) {
        registerModifiedProperty("charaName");
        _charaName = charaName;
    }

    /**
     * [get] CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group} <br>
     * キャラクターグループID
     * @return The value of the column 'CHARA_GROUP_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharaGroupId() {
        checkSpecifiedProperty("charaGroupId");
        return _charaGroupId;
    }

    /**
     * [set] CHARA_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to chara_group} <br>
     * キャラクターグループID
     * @param charaGroupId The value of the column 'CHARA_GROUP_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharaGroupId(Integer charaGroupId) {
        registerModifiedProperty("charaGroupId");
        _charaGroupId = charaGroupId;
    }

    /**
     * [get] CHARA_IMG_URL: {NotNull, VARCHAR(100)} <br>
     * キャラクター画像URL : コンテキストパス以降
     * @return The value of the column 'CHARA_IMG_URL'. (basically NotNull if selected: for the constraint)
     */
    public String getCharaImgUrl() {
        checkSpecifiedProperty("charaImgUrl");
        return convertEmptyToNull(_charaImgUrl);
    }

    /**
     * [set] CHARA_IMG_URL: {NotNull, VARCHAR(100)} <br>
     * キャラクター画像URL : コンテキストパス以降
     * @param charaImgUrl The value of the column 'CHARA_IMG_URL'. (basically NotNull if update: for the constraint)
     */
    public void setCharaImgUrl(String charaImgUrl) {
        registerModifiedProperty("charaImgUrl");
        _charaImgUrl = charaImgUrl;
    }
}
