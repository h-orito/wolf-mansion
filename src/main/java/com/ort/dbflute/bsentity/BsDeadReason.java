package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of DEAD_REASON as TABLE. <br>
 * 死亡理由
 * <pre>
 * [primary-key]
 *     DEAD_REASON_CODE
 *
 * [column]
 *     DEAD_REASON_CODE, DEAD_REASON_NAME
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
 *     VILLAGE_PLAYER
 *
 * [foreign property]
 *     
 *
 * [referrer property]
 *     villagePlayerList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * String deadReasonCode = entity.getDeadReasonCode();
 * String deadReasonName = entity.getDeadReasonName();
 * entity.setDeadReasonCode(deadReasonCode);
 * entity.setDeadReasonName(deadReasonName);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsDeadReason extends AbstractEntity implements DomainEntity {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** DEAD_REASON_CODE: {PK, NotNull, VARCHAR(20)} */
    protected String _deadReasonCode;

    /** DEAD_REASON_NAME: {NotNull, VARCHAR(20)} */
    protected String _deadReasonName;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "DEAD_REASON";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_deadReasonCode == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** VILLAGE_PLAYER by DEAD_REASON_CODE, named 'villagePlayerList'. */
    protected List<VillagePlayer> _villagePlayerList;

    /**
     * [get] VILLAGE_PLAYER by DEAD_REASON_CODE, named 'villagePlayerList'.
     * @return The entity list of referrer property 'villagePlayerList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerList() {
        if (_villagePlayerList == null) { _villagePlayerList = newReferrerList(); }
        return _villagePlayerList;
    }

    /**
     * [set] VILLAGE_PLAYER by DEAD_REASON_CODE, named 'villagePlayerList'.
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
        if (obj instanceof BsDeadReason) {
            BsDeadReason other = (BsDeadReason)obj;
            if (!xSV(_deadReasonCode, other._deadReasonCode)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _deadReasonCode);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_villagePlayerList != null) { for (VillagePlayer et : _villagePlayerList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "villagePlayerList")); } } }
        return sb.toString();
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_deadReasonCode));
        sb.append(dm).append(xfND(_deadReasonName));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_villagePlayerList != null && !_villagePlayerList.isEmpty())
        { sb.append(dm).append("villagePlayerList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public DeadReason clone() {
        return (DeadReason)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] DEAD_REASON_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 死亡理由コード
     * @return The value of the column 'DEAD_REASON_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getDeadReasonCode() {
        checkSpecifiedProperty("deadReasonCode");
        return convertEmptyToNull(_deadReasonCode);
    }

    /**
     * [set] DEAD_REASON_CODE: {PK, NotNull, VARCHAR(20)} <br>
     * 死亡理由コード
     * @param deadReasonCode The value of the column 'DEAD_REASON_CODE'. (basically NotNull if update: for the constraint)
     */
    public void setDeadReasonCode(String deadReasonCode) {
        registerModifiedProperty("deadReasonCode");
        _deadReasonCode = deadReasonCode;
    }

    /**
     * [get] DEAD_REASON_NAME: {NotNull, VARCHAR(20)} <br>
     * 死亡理由
     * @return The value of the column 'DEAD_REASON_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getDeadReasonName() {
        checkSpecifiedProperty("deadReasonName");
        return convertEmptyToNull(_deadReasonName);
    }

    /**
     * [set] DEAD_REASON_NAME: {NotNull, VARCHAR(20)} <br>
     * 死亡理由
     * @param deadReasonName The value of the column 'DEAD_REASON_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setDeadReasonName(String deadReasonName) {
        registerModifiedProperty("deadReasonName");
        _deadReasonName = deadReasonName;
    }
}
