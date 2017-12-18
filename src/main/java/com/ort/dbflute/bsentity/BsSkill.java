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
 * The entity of SKILL as TABLE. <br>
 * 役職
 * <pre>
 * [primary-key]
 *     SKILL_CODE
 *
 * [column]
 *     SKILL_CODE, SKILL_NAME, CAMP_CODE
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
 *     CAMP
 *
 * [referrer table]
 *     VILLAGE_PLAYER
 *
 * [foreign property]
 *     camp
 *
 * [referrer property]
 *     villagePlayerList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String skillCode = entity.getSkillCode();
 * String skillName = entity.getSkillName();
 * String campCode = entity.getCampCode();
 * entity.setSkillCode(skillCode);
 * entity.setSkillName(skillName);
 * entity.setCampCode(campCode);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsSkill extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** SKILL_CODE: {PK, NotNull, VARCHAR(20)} */
    protected String _skillCode;

    /** SKILL_NAME: {NotNull, VARCHAR(20)} */
    protected String _skillName;

    /** CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp} */
    protected String _campCode;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "skill";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_skillCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CAMP by my CAMP_CODE, named 'camp'. */
    protected OptionalEntity<Camp> _camp;

    /**
     * [get] CAMP by my CAMP_CODE, named 'camp'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'camp'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Camp> getCamp() {
        if (_camp == null) { _camp = OptionalEntity.relationEmpty(this, "camp"); }
        return _camp;
    }

    /**
     * [set] CAMP by my CAMP_CODE, named 'camp'.
     * @param camp The entity of foreign property 'camp'. (NullAllowed)
     */
    public void setCamp(OptionalEntity<Camp> camp) {
        _camp = camp;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerList'. */
    protected List<VillagePlayer> _villagePlayerList;

    /**
     * [get] VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerList'.
     * @return The entity list of referrer property 'villagePlayerList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerList() {
        if (_villagePlayerList == null) { _villagePlayerList = newReferrerList(); }
        return _villagePlayerList;
    }

    /**
     * [set] VILLAGE_PLAYER by SKILL_CODE, named 'villagePlayerList'.
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
        if (obj instanceof BsSkill) {
            BsSkill other = (BsSkill)obj;
            if (!xSV(_skillCode, other._skillCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _skillCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_camp != null && _camp.isPresent())
        { sb.append(li).append(xbRDS(_camp, "camp")); }
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
        sb.append(dm).append(xfND(_skillCode));
        sb.append(dm).append(xfND(_skillName));
        sb.append(dm).append(xfND(_campCode));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_camp != null && _camp.isPresent())
        { sb.append(dm).append("camp"); }
        if (_villagePlayerList != null && !_villagePlayerList.isEmpty())
        { sb.append(dm).append("villagePlayerList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Skill clone() {
        return (Skill)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] SKILL_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 役職コード
     * @return The value of the column 'SKILL_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getSkillCode() {
        checkSpecifiedProperty("skillCode");
        return convertEmptyToNull(_skillCode);
    }

    /**
     * [set] SKILL_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 役職コード
     * @param skillCode The value of the column 'SKILL_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setSkillCode(String skillCode) {
        registerModifiedProperty("skillCode");
        _skillCode = skillCode;
    }

    /**
     * [get] SKILL_NAME: {NotNull, VARCHAR(20)} <br>
     * 役職名
     * @return The value of the column 'SKILL_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getSkillName() {
        checkSpecifiedProperty("skillName");
        return convertEmptyToNull(_skillName);
    }

    /**
     * [set] SKILL_NAME: {NotNull, VARCHAR(20)} <br>
     * 役職名
     * @param skillName The value of the column 'SKILL_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setSkillName(String skillName) {
        registerModifiedProperty("skillName");
        _skillName = skillName;
    }

    /**
     * [get] CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp} <br>
     * 陣営コード
     * @return The value of the column 'CAMP_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getCampCode() {
        checkSpecifiedProperty("campCode");
        return convertEmptyToNull(_campCode);
    }

    /**
     * [set] CAMP_CODE: {IX, NotNull, VARCHAR(20), FK to camp} <br>
     * 陣営コード
     * @param campCode The value of the column 'CAMP_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setCampCode(String campCode) {
        registerModifiedProperty("campCode");
        _campCode = campCode;
    }
}
