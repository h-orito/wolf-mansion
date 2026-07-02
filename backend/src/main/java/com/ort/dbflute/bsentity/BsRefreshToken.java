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
 * The entity of REFRESH_TOKEN as TABLE. <br>
 * リフレッシュトークン
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsRefreshToken extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** REFRESH_TOKEN_ID: {PK, ID, NotNull, INT UNSIGNED(10)} */
    protected Integer _refreshTokenId;

    /** PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player} */
    protected Integer _playerId;

    /** TOKEN_HASH: {UQ, NotNull, CHAR(64)} */
    protected String _tokenHash;

    /** ISSUED_DATETIME: {NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _issuedDatetime;

    /** EXPIRES_DATETIME: {IX, NotNull, DATETIME(19)} */
    protected java.time.LocalDateTime _expiresDatetime;

    /** USED_DATETIME: {DATETIME(19)} */
    protected java.time.LocalDateTime _usedDatetime;

    /** REVOKED_DATETIME: {DATETIME(19)} */
    protected java.time.LocalDateTime _revokedDatetime;

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
        return "refresh_token";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_refreshTokenId == null) { return false; }
        return true;
    }

    /**
     * To be unique by the unique column. <br>
     * You can update the entity by the key when entity update (NOT batch update).
     * @param tokenHash : UQ, NotNull, CHAR(64). (NotNull)
     */
    public void uniqueBy(String tokenHash) {
        __uniqueDrivenProperties.clear();
        __uniqueDrivenProperties.addPropertyName("tokenHash");
        setTokenHash(tokenHash);
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** PLAYER by my PLAYER_ID, named 'player'. */
    protected OptionalEntity<Player> _player;

    /**
     * [get] PLAYER by my PLAYER_ID, named 'player'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'player'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Player> getPlayer() {
        if (_player == null) { _player = OptionalEntity.relationEmpty(this, "player"); }
        return _player;
    }

    /**
     * [set] PLAYER by my PLAYER_ID, named 'player'.
     * @param player The entity of foreign property 'player'. (NullAllowed)
     */
    public void setPlayer(OptionalEntity<Player> player) {
        _player = player;
    }

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
        if (obj instanceof BsRefreshToken) {
            BsRefreshToken other = (BsRefreshToken)obj;
            if (!xSV(_refreshTokenId, other._refreshTokenId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _refreshTokenId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_player != null && _player.isPresent())
        { sb.append(li).append(xbRDS(_player, "player")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_refreshTokenId));
        sb.append(dm).append(xfND(_playerId));
        sb.append(dm).append(xfND(_tokenHash));
        sb.append(dm).append(xfND(_issuedDatetime));
        sb.append(dm).append(xfND(_expiresDatetime));
        sb.append(dm).append(xfND(_usedDatetime));
        sb.append(dm).append(xfND(_revokedDatetime));
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
        if (_player != null && _player.isPresent())
        { sb.append(dm).append("player"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public RefreshToken clone() {
        return (RefreshToken)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] REFRESH_TOKEN_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * リフレッシュトークンID
     * @return The value of the column 'REFRESH_TOKEN_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getRefreshTokenId() {
        checkSpecifiedProperty("refreshTokenId");
        return _refreshTokenId;
    }

    /**
     * [set] REFRESH_TOKEN_ID: {PK, ID, NotNull, INT UNSIGNED(10)} <br>
     * リフレッシュトークンID
     * @param refreshTokenId The value of the column 'REFRESH_TOKEN_ID'. (basically NotNull if update: for the constraint)
     */
    public void setRefreshTokenId(Integer refreshTokenId) {
        registerModifiedProperty("refreshTokenId");
        _refreshTokenId = refreshTokenId;
    }

    /**
     * [get] PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @return The value of the column 'PLAYER_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getPlayerId() {
        checkSpecifiedProperty("playerId");
        return _playerId;
    }

    /**
     * [set] PLAYER_ID: {IX, NotNull, INT UNSIGNED(10), FK to player} <br>
     * プレイヤーID
     * @param playerId The value of the column 'PLAYER_ID'. (basically NotNull if update: for the constraint)
     */
    public void setPlayerId(Integer playerId) {
        registerModifiedProperty("playerId");
        _playerId = playerId;
    }

    /**
     * [get] TOKEN_HASH: {UQ, NotNull, CHAR(64)} <br>
     * リフレッシュトークンのSHA-256ハッシュ(hex)
     * @return The value of the column 'TOKEN_HASH'. (basically NotNull if selected: for the constraint)
     */
    public String getTokenHash() {
        checkSpecifiedProperty("tokenHash");
        return convertEmptyToNull(_tokenHash);
    }

    /**
     * [set] TOKEN_HASH: {UQ, NotNull, CHAR(64)} <br>
     * リフレッシュトークンのSHA-256ハッシュ(hex)
     * @param tokenHash The value of the column 'TOKEN_HASH'. (basically NotNull if update: for the constraint)
     */
    public void setTokenHash(String tokenHash) {
        registerModifiedProperty("tokenHash");
        _tokenHash = tokenHash;
    }

    /**
     * [get] ISSUED_DATETIME: {NotNull, DATETIME(19)} <br>
     * 発行日時
     * @return The value of the column 'ISSUED_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getIssuedDatetime() {
        checkSpecifiedProperty("issuedDatetime");
        return _issuedDatetime;
    }

    /**
     * [set] ISSUED_DATETIME: {NotNull, DATETIME(19)} <br>
     * 発行日時
     * @param issuedDatetime The value of the column 'ISSUED_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setIssuedDatetime(java.time.LocalDateTime issuedDatetime) {
        registerModifiedProperty("issuedDatetime");
        _issuedDatetime = issuedDatetime;
    }

    /**
     * [get] EXPIRES_DATETIME: {IX, NotNull, DATETIME(19)} <br>
     * 有効期限
     * @return The value of the column 'EXPIRES_DATETIME'. (basically NotNull if selected: for the constraint)
     */
    public java.time.LocalDateTime getExpiresDatetime() {
        checkSpecifiedProperty("expiresDatetime");
        return _expiresDatetime;
    }

    /**
     * [set] EXPIRES_DATETIME: {IX, NotNull, DATETIME(19)} <br>
     * 有効期限
     * @param expiresDatetime The value of the column 'EXPIRES_DATETIME'. (basically NotNull if update: for the constraint)
     */
    public void setExpiresDatetime(java.time.LocalDateTime expiresDatetime) {
        registerModifiedProperty("expiresDatetime");
        _expiresDatetime = expiresDatetime;
    }

    /**
     * [get] USED_DATETIME: {DATETIME(19)} <br>
     * 使用(ローテーション)日時。使用済みは再利用不可
     * @return The value of the column 'USED_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getUsedDatetime() {
        checkSpecifiedProperty("usedDatetime");
        return _usedDatetime;
    }

    /**
     * [set] USED_DATETIME: {DATETIME(19)} <br>
     * 使用(ローテーション)日時。使用済みは再利用不可
     * @param usedDatetime The value of the column 'USED_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setUsedDatetime(java.time.LocalDateTime usedDatetime) {
        registerModifiedProperty("usedDatetime");
        _usedDatetime = usedDatetime;
    }

    /**
     * [get] REVOKED_DATETIME: {DATETIME(19)} <br>
     * 失効日時。ログアウトや漏洩疑い等で失効
     * @return The value of the column 'REVOKED_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getRevokedDatetime() {
        checkSpecifiedProperty("revokedDatetime");
        return _revokedDatetime;
    }

    /**
     * [set] REVOKED_DATETIME: {DATETIME(19)} <br>
     * 失効日時。ログアウトや漏洩疑い等で失効
     * @param revokedDatetime The value of the column 'REVOKED_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setRevokedDatetime(java.time.LocalDateTime revokedDatetime) {
        registerModifiedProperty("revokedDatetime");
        _revokedDatetime = revokedDatetime;
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
