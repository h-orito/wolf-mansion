package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.Entity;
import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import org.dbflute.optional.OptionalEntity;
import com.ort.dbflute.allcommon.EntityDefinedCommonColumn;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of VILLAGE as TABLE. <br>
 * 村
 * <pre>
 * [primary-key]
 *     VILALGE_ID
 *
 * [column]
 *     VILALGE_ID, VILLAGE_DISPLAY_NAME, WIN_CAMP_CODE, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     VILALGE_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     VILLAGE_SETTINGS(AsOne)
 *
 * [referrer table]
 *     MESSAGE, VILLAGE_PLAYER, VILLAGE_SETTINGS
 *
 * [foreign property]
 *     villageSettingsAsOne
 *
 * [referrer property]
 *     messageList, villagePlayerList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer vilalgeId = entity.getVilalgeId();
 * String villageDisplayName = entity.getVillageDisplayName();
 * String winCampCode = entity.getWinCampCode();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVilalgeId(vilalgeId);
 * entity.setVillageDisplayName(villageDisplayName);
 * entity.setWinCampCode(winCampCode);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillage extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _vilalgeId;

    /** VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)} */
    protected String _villageDisplayName;

    /** WIN_CAMP_CODE: {VARCHAR(20)} */
    protected String _winCampCode;

    /** REGISTER_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _registerDatetime;

    /** REGISTER_TRACE: {NotNull, VARCHAR(64)} */
    protected String _registerTrace;

    /** UPDATE_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _updateDatetime;

    /** UPDATE_TRACE: {NotNull, VARCHAR(64)} */
    protected String _updateTrace;

    // ===================================================================================
    //                                                                             DB Meta
    //                                                                             =======
    /** {@inheritDoc} */
    public DBMeta asDBMeta() {
        return DBMetaInstanceHandler.findDBMeta(asTableDbName());
    }

    /** {@inheritDoc} */
    public String asTableDbName() {
        return "village";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_vilalgeId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** village_settings by VILLAGE_ID, named 'villageSettingsAsOne'. */
    protected OptionalEntity<VillageSettings> _villageSettingsAsOne;

    /**
     * [get] village_settings by VILLAGE_ID, named 'villageSettingsAsOne'.
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return the entity of foreign property(referrer-as-one) 'villageSettingsAsOne'. (NotNull, EmptyAllowed: when e.g. no data, no setupSelect)
     */
    public OptionalEntity<VillageSettings> getVillageSettingsAsOne() {
        if (_villageSettingsAsOne == null) { _villageSettingsAsOne = OptionalEntity.relationEmpty(this, "villageSettingsAsOne"); }
        return _villageSettingsAsOne;
    }

    /**
     * [set] village_settings by VILLAGE_ID, named 'villageSettingsAsOne'.
     * @param villageSettingsAsOne The entity of foreign property(referrer-as-one) 'villageSettingsAsOne'. (NullAllowed)
     */
    public void setVillageSettingsAsOne(OptionalEntity<VillageSettings> villageSettingsAsOne) {
        _villageSettingsAsOne = villageSettingsAsOne;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** MESSAGE by VILLAGE_ID, named 'messageList'. */
    protected List<Message> _messageList;

    /**
     * [get] MESSAGE by VILLAGE_ID, named 'messageList'.
     * @return The entity list of referrer property 'messageList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageList() {
        if (_messageList == null) { _messageList = newReferrerList(); }
        return _messageList;
    }

    /**
     * [set] MESSAGE by VILLAGE_ID, named 'messageList'.
     * @param messageList The entity list of referrer property 'messageList'. (NullAllowed)
     */
    public void setMessageList(List<Message> messageList) {
        _messageList = messageList;
    }

    /** VILLAGE_PLAYER by VILLAGE_ID, named 'villagePlayerList'. */
    protected List<VillagePlayer> _villagePlayerList;

    /**
     * [get] VILLAGE_PLAYER by VILLAGE_ID, named 'villagePlayerList'.
     * @return The entity list of referrer property 'villagePlayerList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerList() {
        if (_villagePlayerList == null) { _villagePlayerList = newReferrerList(); }
        return _villagePlayerList;
    }

    /**
     * [set] VILLAGE_PLAYER by VILLAGE_ID, named 'villagePlayerList'.
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
        if (obj instanceof BsVillage) {
            BsVillage other = (BsVillage)obj;
            if (!xSV(_vilalgeId, other._vilalgeId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _vilalgeId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_villageSettingsAsOne != null && _villageSettingsAsOne.isPresent())
        { sb.append(li).append(xbRDS(_villageSettingsAsOne, "villageSettingsAsOne")); }
        if (_messageList != null) { for (Message et : _messageList)
        { if (et != null) { sb.append(li).append(xbRDS(et, "messageList")); } } }
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
        sb.append(dm).append(xfND(_vilalgeId));
        sb.append(dm).append(xfND(_villageDisplayName));
        sb.append(dm).append(xfND(_winCampCode));
        sb.append(dm).append(xfND(_registerDatetime));
        sb.append(dm).append(xfND(_registerTrace));
        sb.append(dm).append(xfND(_updateDatetime));
        sb.append(dm).append(xfND(_updateTrace));
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
        if (_villageSettingsAsOne != null && _villageSettingsAsOne.isPresent())
        { sb.append(dm).append("villageSettingsAsOne"); }
        if (_messageList != null && !_messageList.isEmpty())
        { sb.append(dm).append("messageList"); }
        if (_villagePlayerList != null && !_villagePlayerList.isEmpty())
        { sb.append(dm).append("villagePlayerList"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public Village clone() {
        return (Village)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村ID
     * @return The value of the column 'VILALGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVilalgeId() {
        checkSpecifiedProperty("vilalgeId");
        return _vilalgeId;
    }

    /**
     * [set] VILALGE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * 村ID
     * @param vilalgeId The value of the column 'VILALGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVilalgeId(Integer vilalgeId) {
        registerModifiedProperty("vilalgeId");
        _vilalgeId = vilalgeId;
    }

    /**
     * [get] VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)} <br>
     * 村表示名
     * @return The value of the column 'VILLAGE_DISPLAY_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getVillageDisplayName() {
        checkSpecifiedProperty("villageDisplayName");
        return convertEmptyToNull(_villageDisplayName);
    }

    /**
     * [set] VILLAGE_DISPLAY_NAME: {NotNull, VARCHAR(40)} <br>
     * 村表示名
     * @param villageDisplayName The value of the column 'VILLAGE_DISPLAY_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setVillageDisplayName(String villageDisplayName) {
        registerModifiedProperty("villageDisplayName");
        _villageDisplayName = villageDisplayName;
    }

    /**
     * [get] WIN_CAMP_CODE: {VARCHAR(20)} <br>
     * 勝利陣営コード
     * @return The value of the column 'WIN_CAMP_CODE'. (NullAllowed even if selected: for no constraint)
     */
    public String getWinCampCode() {
        checkSpecifiedProperty("winCampCode");
        return convertEmptyToNull(_winCampCode);
    }

    /**
     * [set] WIN_CAMP_CODE: {VARCHAR(20)} <br>
     * 勝利陣営コード
     * @param winCampCode The value of the column 'WIN_CAMP_CODE'. (NullAllowed: null update allowed for no constraint)
     */
    public void setWinCampCode(String winCampCode) {
        registerModifiedProperty("winCampCode");
        _winCampCode = winCampCode;
    }

    /**
     * [get] REGISTER_DATETIME: {NotNull, DATETIME(19)} <br>
     * 登録日時
     * @return The value of the column 'REGISTER_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getRegisterDatetime() {
        checkSpecifiedProperty("registerDatetime");
        return _registerDatetime;
    }

    /**
     * [set] REGISTER_DATETIME: {NotNull, DATETIME(19)} <br>
     * 登録日時
     * @param registerDatetime The value of the column 'REGISTER_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterDatetime(java.time.LocalDateTime registerDatetime) {
        registerModifiedProperty("registerDatetime");
        _registerDatetime = registerDatetime;
    }

    /**
     * [get] REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * 登録トレース
     * @return The value of the column 'REGISTER_TRACE'. (basically NotNull if selected: for the constraint)
     */
    public String getRegisterTrace() {
        checkSpecifiedProperty("registerTrace");
        return convertEmptyToNull(_registerTrace);
    }

    /**
     * [set] REGISTER_TRACE: {NotNull, VARCHAR(64)} <br>
     * 登録トレース
     * @param registerTrace The value of the column 'REGISTER_TRACE'. (basically NotNull if update: for the constraint)
     */
    public void setRegisterTrace(String registerTrace) {
        registerModifiedProperty("registerTrace");
        _registerTrace = registerTrace;
    }

    /**
     * [get] UPDATE_DATETIME: {NotNull, DATETIME(19)} <br>
     * 更新日時
     * @return The value of the column 'UPDATE_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getUpdateDatetime() {
        checkSpecifiedProperty("updateDatetime");
        return _updateDatetime;
    }

    /**
     * [set] UPDATE_DATETIME: {NotNull, DATETIME(19)} <br>
     * 更新日時
     * @param updateDatetime The value of the column 'UPDATE_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateDatetime(java.time.LocalDateTime updateDatetime) {
        registerModifiedProperty("updateDatetime");
        _updateDatetime = updateDatetime;
    }

    /**
     * [get] UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * 更新トレース
     * @return The value of the column 'UPDATE_TRACE'. (basically NotNull if selected: for the constraint)
     */
    public String getUpdateTrace() {
        checkSpecifiedProperty("updateTrace");
        return convertEmptyToNull(_updateTrace);
    }

    /**
     * [set] UPDATE_TRACE: {NotNull, VARCHAR(64)} <br>
     * 更新トレース
     * @param updateTrace The value of the column 'UPDATE_TRACE'. (basically NotNull if update: for the constraint)
     */
    public void setUpdateTrace(String updateTrace) {
        registerModifiedProperty("updateTrace");
        _updateTrace = updateTrace;
    }
}
