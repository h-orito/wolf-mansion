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
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.*;

/**
 * The entity of PLAYER as TABLE. <br>
 * プレイヤー
 * <pre>
 * [primary-key]
 *     PLAYER_ID
 *
 * [column]
 *     PLAYER_ID, PLAYER_NAME, PLAYER_PASSWORD, AUTHORITY_CODE, IS_RESTRICTED_PARTICIPATION, SHOULD_CHECK_ACCESS_INFO, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
 *
 * [sequence]
 *     
 *
 * [identity]
 *     PLAYER_ID
 *
 * [version-no]
 *     
 *
 * [foreign table]
 *     AUTHORITY, PLAYER_DETAIL(AsOne)
 *
 * [referrer table]
 *     MESSAGE, VILLAGE_PLAYER, PLAYER_DETAIL
 *
 * [foreign property]
 *     authority, playerDetailAsOne
 *
 * [referrer property]
 *     messageList, villagePlayerList
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer playerId = entity.getPlayerId();
 * String playerName = entity.getPlayerName();
 * String playerPassword = entity.getPlayerPassword();
 * String authorityCode = entity.getAuthorityCode();
 * Boolean isRestrictedParticipation = entity.getIsRestrictedParticipation();
 * Boolean shouldCheckAccessInfo = entity.getShouldCheckAccessInfo();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setPlayerId(playerId);
 * entity.setPlayerName(playerName);
 * entity.setPlayerPassword(playerPassword);
 * entity.setAuthorityCode(authorityCode);
 * entity.setIsRestrictedParticipation(isRestrictedParticipation);
 * entity.setShouldCheckAccessInfo(shouldCheckAccessInfo);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsPlayer extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _playerId;

    /** PLAYER_NAME: {UQ, NotNull, VARCHAR(12)} */
    protected String _playerName;

    /** PLAYER_PASSWORD: {NotNull, CHAR(60)} */
    protected String _playerPassword;

    /** AUTHORITY_CODE: {IX, NotNull, VARCHAR(20), FK to AUTHORITY, classification=Authority} */
    protected String _authorityCode;

    /** IS_RESTRICTED_PARTICIPATION: {NotNull, BIT, classification=Flg} */
    protected Boolean _isRestrictedParticipation;

    /** SHOULD_CHECK_ACCESS_INFO: {NotNull, BIT} */
    protected Boolean _shouldCheckAccessInfo;

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
        return "PLAYER";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_playerId == null) { return false; }
        return true;
    }

    /**
     * To be unique by the unique column. <br>
     * You can update the entity by the key when entity update (NOT batch update).
     * @param playerName : UQ, NotNull, VARCHAR(12). (NotNull)
     */
    public void uniqueBy(String playerName) {
        __uniqueDrivenProperties.clear();
        __uniqueDrivenProperties.addPropertyName("playerName");
        setPlayerName(playerName);
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of authorityCode as the classification of Authority. <br>
     * AUTHORITY_CODE: {IX, NotNull, VARCHAR(20), FK to AUTHORITY, classification=Authority} <br>
     * 権限
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Authority getAuthorityCodeAsAuthority() {
        return CDef.Authority.codeOf(getAuthorityCode());
    }

    /**
     * Set the value of authorityCode as the classification of Authority. <br>
     * AUTHORITY_CODE: {IX, NotNull, VARCHAR(20), FK to AUTHORITY, classification=Authority} <br>
     * 権限
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setAuthorityCodeAsAuthority(CDef.Authority cdef) {
        setAuthorityCode(cdef != null ? cdef.code() : null);
    }

    /**
     * Get the value of isRestrictedParticipation as the classification of Flg. <br>
     * IS_RESTRICTED_PARTICIPATION: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsRestrictedParticipationAsFlg() {
        return CDef.Flg.codeOf(getIsRestrictedParticipation());
    }

    /**
     * Set the value of isRestrictedParticipation as the classification of Flg. <br>
     * IS_RESTRICTED_PARTICIPATION: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsRestrictedParticipationAsFlg(CDef.Flg cdef) {
        setIsRestrictedParticipation(cdef != null ? toBoolean(cdef.code()) : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of authorityCode as 管理者 (ROLE_ADMIN). <br>
     * 管理者
     */
    public void setAuthorityCode_管理者() {
        setAuthorityCodeAsAuthority(CDef.Authority.管理者);
    }

    /**
     * Set the value of authorityCode as プレイヤー (ROLE_PLAYER). <br>
     * プレイヤー
     */
    public void setAuthorityCode_プレイヤー() {
        setAuthorityCodeAsAuthority(CDef.Authority.プレイヤー);
    }

    /**
     * Set the value of isRestrictedParticipation as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsRestrictedParticipation_True() {
        setIsRestrictedParticipationAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isRestrictedParticipation as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsRestrictedParticipation_False() {
        setIsRestrictedParticipationAsFlg(CDef.Flg.False);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of authorityCode 管理者? <br>
     * 管理者
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAuthorityCode管理者() {
        CDef.Authority cdef = getAuthorityCodeAsAuthority();
        return cdef != null ? cdef.equals(CDef.Authority.管理者) : false;
    }

    /**
     * Is the value of authorityCode プレイヤー? <br>
     * プレイヤー
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isAuthorityCodeプレイヤー() {
        CDef.Authority cdef = getAuthorityCodeAsAuthority();
        return cdef != null ? cdef.equals(CDef.Authority.プレイヤー) : false;
    }

    /**
     * Is the value of isRestrictedParticipation True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsRestrictedParticipationTrue() {
        CDef.Flg cdef = getIsRestrictedParticipationAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isRestrictedParticipation False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsRestrictedParticipationFalse() {
        CDef.Flg cdef = getIsRestrictedParticipationAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'isRestrictedParticipation' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsRestrictedParticipationAlias() {
        CDef.Flg cdef = getIsRestrictedParticipationAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** AUTHORITY by my AUTHORITY_CODE, named 'authority'. */
    protected OptionalEntity<Authority> _authority;

    /**
     * [get] AUTHORITY by my AUTHORITY_CODE, named 'authority'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'authority'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Authority> getAuthority() {
        if (_authority == null) { _authority = OptionalEntity.relationEmpty(this, "authority"); }
        return _authority;
    }

    /**
     * [set] AUTHORITY by my AUTHORITY_CODE, named 'authority'.
     * @param authority The entity of foreign property 'authority'. (NullAllowed)
     */
    public void setAuthority(OptionalEntity<Authority> authority) {
        _authority = authority;
    }

    /** PLAYER_DETAIL by PLAYER_ID, named 'playerDetailAsOne'. */
    protected OptionalEntity<PlayerDetail> _playerDetailAsOne;

    /**
     * [get] PLAYER_DETAIL by PLAYER_ID, named 'playerDetailAsOne'.
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return the entity of foreign property(referrer-as-one) 'playerDetailAsOne'. (NotNull, EmptyAllowed: when e.g. no data, no setupSelect)
     */
    public OptionalEntity<PlayerDetail> getPlayerDetailAsOne() {
        if (_playerDetailAsOne == null) { _playerDetailAsOne = OptionalEntity.relationEmpty(this, "playerDetailAsOne"); }
        return _playerDetailAsOne;
    }

    /**
     * [set] PLAYER_DETAIL by PLAYER_ID, named 'playerDetailAsOne'.
     * @param playerDetailAsOne The entity of foreign property(referrer-as-one) 'playerDetailAsOne'. (NullAllowed)
     */
    public void setPlayerDetailAsOne(OptionalEntity<PlayerDetail> playerDetailAsOne) {
        _playerDetailAsOne = playerDetailAsOne;
    }

    // ===================================================================================
    //                                                                   Referrer Property
    //                                                                   =================
    /** MESSAGE by PLAYER_ID, named 'messageList'. */
    protected List<Message> _messageList;

    /**
     * [get] MESSAGE by PLAYER_ID, named 'messageList'.
     * @return The entity list of referrer property 'messageList'. (NotNull: even if no loading, returns empty list)
     */
    public List<Message> getMessageList() {
        if (_messageList == null) { _messageList = newReferrerList(); }
        return _messageList;
    }

    /**
     * [set] MESSAGE by PLAYER_ID, named 'messageList'.
     * @param messageList The entity list of referrer property 'messageList'. (NullAllowed)
     */
    public void setMessageList(List<Message> messageList) {
        _messageList = messageList;
    }

    /** VILLAGE_PLAYER by PLAYER_ID, named 'villagePlayerList'. */
    protected List<VillagePlayer> _villagePlayerList;

    /**
     * [get] VILLAGE_PLAYER by PLAYER_ID, named 'villagePlayerList'.
     * @return The entity list of referrer property 'villagePlayerList'. (NotNull: even if no loading, returns empty list)
     */
    public List<VillagePlayer> getVillagePlayerList() {
        if (_villagePlayerList == null) { _villagePlayerList = newReferrerList(); }
        return _villagePlayerList;
    }

    /**
     * [set] VILLAGE_PLAYER by PLAYER_ID, named 'villagePlayerList'.
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
        if (obj instanceof BsPlayer) {
            BsPlayer other = (BsPlayer)obj;
            if (!xSV(_playerId, other._playerId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _playerId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_authority != null && _authority.isPresent())
        { sb.append(li).append(xbRDS(_authority, "authority")); }
        if (_playerDetailAsOne != null && _playerDetailAsOne.isPresent())
        { sb.append(li).append(xbRDS(_playerDetailAsOne, "playerDetailAsOne")); }
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
        sb.append(dm).append(xfND(_playerId));
        sb.append(dm).append(xfND(_playerName));
        sb.append(dm).append(xfND(_playerPassword));
        sb.append(dm).append(xfND(_authorityCode));
        sb.append(dm).append(xfND(_isRestrictedParticipation));
        sb.append(dm).append(xfND(_shouldCheckAccessInfo));
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
        if (_authority != null && _authority.isPresent())
        { sb.append(dm).append("authority"); }
        if (_playerDetailAsOne != null && _playerDetailAsOne.isPresent())
        { sb.append(dm).append("playerDetailAsOne"); }
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
    public Player clone() {
        return (Player)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * プレイヤーID
     * @return The value of the column 'PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getPlayerId() {
        checkSpecifiedProperty("playerId");
        return _playerId;
    }

    /**
     * [set] PLAYER_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * プレイヤーID
     * @param playerId The value of the column 'PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPlayerId(Integer playerId) {
        registerModifiedProperty("playerId");
        _playerId = playerId;
    }

    /**
     * [get] PLAYER_NAME: {UQ, NotNull, VARCHAR(12)} <br>
     * プレイヤー名
     * @return The value of the column 'PLAYER_NAME'. (basically NotNull if selected: for the constraint)
     */
    public String getPlayerName() {
        checkSpecifiedProperty("playerName");
        return convertEmptyToNull(_playerName);
    }

    /**
     * [set] PLAYER_NAME: {UQ, NotNull, VARCHAR(12)} <br>
     * プレイヤー名
     * @param playerName The value of the column 'PLAYER_NAME'. (basically NotNull if update: for the constraint)
     */
    public void setPlayerName(String playerName) {
        registerModifiedProperty("playerName");
        _playerName = playerName;
    }

    /**
     * [get] PLAYER_PASSWORD: {NotNull, CHAR(60)} <br>
     * プレイヤーパスワード
     * @return The value of the column 'PLAYER_PASSWORD'. (basically NotNull if selected: for the constraint)
     */
    public String getPlayerPassword() {
        checkSpecifiedProperty("playerPassword");
        return convertEmptyToNull(_playerPassword);
    }

    /**
     * [set] PLAYER_PASSWORD: {NotNull, CHAR(60)} <br>
     * プレイヤーパスワード
     * @param playerPassword The value of the column 'PLAYER_PASSWORD'. (basically NotNull if update: for the constraint)
     */
    public void setPlayerPassword(String playerPassword) {
        registerModifiedProperty("playerPassword");
        _playerPassword = playerPassword;
    }

    /**
     * [get] AUTHORITY_CODE: {IX, NotNull, VARCHAR(20), FK to AUTHORITY, classification=Authority} <br>
     * 権限コード
     * @return The value of the column 'AUTHORITY_CODE'. (basically NotNull if selected: for the constraint)
     */
    public String getAuthorityCode() {
        checkSpecifiedProperty("authorityCode");
        return convertEmptyToNull(_authorityCode);
    }

    /**
     * [set] AUTHORITY_CODE: {IX, NotNull, VARCHAR(20), FK to AUTHORITY, classification=Authority} <br>
     * 権限コード
     * @param authorityCode The value of the column 'AUTHORITY_CODE'. (basically NotNull if update: for the constraint)
     */
    protected void setAuthorityCode(String authorityCode) {
        checkClassificationCode("AUTHORITY_CODE", CDef.DefMeta.Authority, authorityCode);
        registerModifiedProperty("authorityCode");
        _authorityCode = authorityCode;
    }

    /**
     * [get] IS_RESTRICTED_PARTICIPATION: {NotNull, BIT, classification=Flg} <br>
     * 入村制限されているか
     * @return The value of the column 'IS_RESTRICTED_PARTICIPATION'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsRestrictedParticipation() {
        checkSpecifiedProperty("isRestrictedParticipation");
        return _isRestrictedParticipation;
    }

    /**
     * [set] IS_RESTRICTED_PARTICIPATION: {NotNull, BIT, classification=Flg} <br>
     * 入村制限されているか
     * @param isRestrictedParticipation The value of the column 'IS_RESTRICTED_PARTICIPATION'. (basically NotNull if update: for the constraint)
     */
    public void setIsRestrictedParticipation(Boolean isRestrictedParticipation) {
        checkClassificationCode("IS_RESTRICTED_PARTICIPATION", CDef.DefMeta.Flg, isRestrictedParticipation);
        registerModifiedProperty("isRestrictedParticipation");
        _isRestrictedParticipation = isRestrictedParticipation;
    }

    /**
     * [get] SHOULD_CHECK_ACCESS_INFO: {NotNull, BIT} <br>
     * アクセス情報を確認するか
     * @return The value of the column 'SHOULD_CHECK_ACCESS_INFO'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getShouldCheckAccessInfo() {
        checkSpecifiedProperty("shouldCheckAccessInfo");
        return _shouldCheckAccessInfo;
    }

    /**
     * [set] SHOULD_CHECK_ACCESS_INFO: {NotNull, BIT} <br>
     * アクセス情報を確認するか
     * @param shouldCheckAccessInfo The value of the column 'SHOULD_CHECK_ACCESS_INFO'. (basically NotNull if update: for the constraint)
     */
    public void setShouldCheckAccessInfo(Boolean shouldCheckAccessInfo) {
        registerModifiedProperty("shouldCheckAccessInfo");
        _shouldCheckAccessInfo = shouldCheckAccessInfo;
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

    /**
     * For framework so basically DON'T use this method.
     * @param authorityCode The value of the column 'AUTHORITY_CODE'. (basically NotNull if update: for the constraint)
     */
    public void mynativeMappingAuthorityCode(String authorityCode) {
        setAuthorityCode(authorityCode);
    }
}
