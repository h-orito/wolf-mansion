package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of CAMP as TABLE. <br>
 * 陣営
 * <pre>
 * [primary-key]
 *     CAMP_CODE
 *
 * [column]
 *     CAMP_CODE, CAMP_NAME
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
 *     SKILL
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     skillList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String campCode = entity.getCampCode();
 * String campName = entity.getCampName();
 * entity.setCampCode(campCode);
 * entity.setCampName(campName);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsCamp extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** CAMP_CODE: {PK, NotNull, VARCHAR(20)} */
    protected String _campCode;

    /** CAMP_NAME: {NotNull, VARCHAR(20)} */
    protected String _campName;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "camp";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_campCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** SKILL by CAMP_CODE, named 'skillList'. */
    protected List<Skill> _skillList;

    /**
     * [get] SKILL by CAMP_CODE, named 'skillList'.
     * @return The entity list of referrer property 'skillList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Skill> getSkillList() {
        if (_skillList == null) { _skillList = newReferrerList(); }
        return _skillList;
    }

    /**
     * [set] SKILL by CAMP_CODE, named 'skillList'.
     * @param skillList The entity list of referrer property 'skillList'. (NullAllowed)
     */
    public void setSkillList(List<Skill> skillList) {
        _skillList = skillList;
    }

    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsCamp) {
            BsCamp other = (BsCamp)obj;
            if (!xSV(_campCode, other._campCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _campCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_skillList != null) { for (Skill et : _skillList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "skillList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_campCode));
        sb.append(dm).append(xfND(_campName));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_skillList != null && !_skillList.isEmpty())
        { sb.append(dm).append("skillList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Camp clone() {
        return (Camp)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] CAMP_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 陣営コード
     * @return The value of the column 'CAMP_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getCampCode() {
        checkSpecifiedProperty("campCode");
        return convertEmptyToNull(_campCode);
    }

    /**
     * [set] CAMP_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 陣営コード
     * @param campCode The value of the column 'CAMP_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setCampCode(String campCode) {
        registerModifiedProperty("campCode");
        _campCode = campCode;
    }

    /**
     * [get] CAMP_NAME: {NotNull, VARCHAR(20)} <br>
     * 陣営名
     * @return The value of the column 'CAMP_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getCampName() {
        checkSpecifiedProperty("campName");
        return convertEmptyToNull(_campName);
    }

    /**
     * [set] CAMP_NAME: {NotNull, VARCHAR(20)} <br>
     * 陣営名
     * @param campName The value of the column 'CAMP_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setCampName(String campName) {
        registerModifiedProperty("campName");
        _campName = campName;
    }
}
