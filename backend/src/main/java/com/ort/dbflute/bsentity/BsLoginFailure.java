package com.ort.dbflute.bsentity;

import java.util.List;
import java.util.ArrayList;

import org.dbflute.dbmeta.DBMeta;
import org.dbflute.dbmeta.AbstractEntity;
import org.dbflute.dbmeta.accessory.DomainEntity;
import com.ort.dbflute.allcommon.EntityDefinedCommonColumn;
import com.ort.dbflute.allcommon.DBMetaInstanceHandler;
import com.ort.dbflute.exentity.*;

/**
 * The entity of LOGIN_FAILURE as TABLE. <br>
 * ログイン失敗試行
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsLoginFailure extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _loginFailureId;

    /** LOGIN_NAME: {IX+, NotNull, VARCHAR(60)} */
    protected String _loginName;

    /** IP_ADDRESS: {IX+, NotNull, VARCHAR(64)} */
    protected String _ipAddress;

    /** ATTEMPT_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _attemptDatetime;

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
        return "login_failure";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_loginFailureId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    protected <ELEMENT> List<ELEMENT> newReferrerList() { // overriding to import
        return new ArrayList<ELEMENT>();
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    protected boolean doEquals(Object obj) {
        if (obj instanceof BsLoginFailure) {
            BsLoginFailure other = (BsLoginFailure)obj;
            if (!xSV(_loginFailureId, other._loginFailureId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _loginFailureId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        return "";
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_loginFailureId));
        sb.append(dm).append(xfND(_loginName));
        sb.append(dm).append(xfND(_ipAddress));
        sb.append(dm).append(xfND(_attemptDatetime));
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
        return "";
    }

    @Override
    public LoginFailure clone() {
        return (LoginFailure)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * ログイン失敗ID
     * @return The value of the column 'LOGIN_FAILURE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getLoginFailureId() {
        checkSpecifiedProperty("loginFailureId");
        return _loginFailureId;
    }

    /**
     * [set] LOGIN_FAILURE_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * ログイン失敗ID
     * @param loginFailureId The value of the column 'LOGIN_FAILURE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setLoginFailureId(Integer loginFailureId) {
        registerModifiedProperty("loginFailureId");
        _loginFailureId = loginFailureId;
    }

    /**
     * [get] LOGIN_NAME: {IX+, NotNull, VARCHAR(60)} <br>
     * 試行されたログインID(存在しないこともある)
     * @return The value of the column 'LOGIN_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getLoginName() {
        checkSpecifiedProperty("loginName");
        return convertEmptyToNull(_loginName);
    }

    /**
     * [set] LOGIN_NAME: {IX+, NotNull, VARCHAR(60)} <br>
     * 試行されたログインID(存在しないこともある)
     * @param loginName The value of the column 'LOGIN_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setLoginName(String loginName) {
        registerModifiedProperty("loginName");
        _loginName = loginName;
    }

    /**
     * [get] IP_ADDRESS: {IX+, NotNull, VARCHAR(64)} <br>
     * クライアントIP(CF-Connecting-IP優先)
     * @return The value of the column 'IP_ADDRESS'. (basically NotNull if selected: for the constraint)
     */
    public String getIpAddress() {
        checkSpecifiedProperty("ipAddress");
        return convertEmptyToNull(_ipAddress);
    }

    /**
     * [set] IP_ADDRESS: {IX+, NotNull, VARCHAR(64)} <br>
     * クライアントIP(CF-Connecting-IP優先)
     * @param ipAddress The value of the column 'IP_ADDRESS'. (basically NotNull if update: for the constraint)
     */
    public void setIpAddress(String ipAddress) {
        registerModifiedProperty("ipAddress");
        _ipAddress = ipAddress;
    }

    /**
     * [get] ATTEMPT_DATETIME: {NotNull, DATETIME(19)} <br>
     * 失敗試行日時
     * @return The value of the column 'ATTEMPT_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getAttemptDatetime() {
        checkSpecifiedProperty("attemptDatetime");
        return _attemptDatetime;
    }

    /**
     * [set] ATTEMPT_DATETIME: {NotNull, DATETIME(19)} <br>
     * 失敗試行日時
     * @param attemptDatetime The value of the column 'ATTEMPT_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setAttemptDatetime(java.time.LocalDateTime attemptDatetime) {
        registerModifiedProperty("attemptDatetime");
        _attemptDatetime = attemptDatetime;
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
