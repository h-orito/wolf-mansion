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
 * The entity of VILLAGE_SETTINGS as TABLE. <br>
 * 村設定
 * <pre>
 * [primary-key]
 *     VILLAGE_ID
 *
 * [column]
 *     VILLAGE_ID, START_PERSON_MIN_NUM, PERSON_MAX_NUM, START_DATETIME, DAY_CHANGE_INTERVAL_SECONDS, IS_OPEN_VOTE, IS_POSSIBLE_SKILL_REQUEST, CHARACTER_GROUP_ID, JOIN_PASSWORD, REGISTER_DATETIME, REGISTER_TRACE, UPDATE_DATETIME, UPDATE_TRACE
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
 *     CHARA_GROUP, VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     charaGroup, village
 *
 * [referrer property]
 *     
 *
 * [get/set template]
 * /= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
 * Integer villageId = entity.getVillageId();
 * Integer startPersonMinNum = entity.getStartPersonMinNum();
 * Integer personMaxNum = entity.getPersonMaxNum();
 * java.time.LocalDateTime startDatetime = entity.getStartDatetime();
 * Integer dayChangeIntervalSeconds = entity.getDayChangeIntervalSeconds();
 * Boolean isOpenVote = entity.getIsOpenVote();
 * Boolean isPossibleSkillRequest = entity.getIsPossibleSkillRequest();
 * Integer characterGroupId = entity.getCharacterGroupId();
 * String joinPassword = entity.getJoinPassword();
 * java.time.LocalDateTime registerDatetime = entity.getRegisterDatetime();
 * String registerTrace = entity.getRegisterTrace();
 * java.time.LocalDateTime updateDatetime = entity.getUpdateDatetime();
 * String updateTrace = entity.getUpdateTrace();
 * entity.setVillageId(villageId);
 * entity.setStartPersonMinNum(startPersonMinNum);
 * entity.setPersonMaxNum(personMaxNum);
 * entity.setStartDatetime(startDatetime);
 * entity.setDayChangeIntervalSeconds(dayChangeIntervalSeconds);
 * entity.setIsOpenVote(isOpenVote);
 * entity.setIsPossibleSkillRequest(isPossibleSkillRequest);
 * entity.setCharacterGroupId(characterGroupId);
 * entity.setJoinPassword(joinPassword);
 * entity.setRegisterDatetime(registerDatetime);
 * entity.setRegisterTrace(registerTrace);
 * entity.setUpdateDatetime(updateDatetime);
 * entity.setUpdateTrace(updateTrace);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillageSettings extends AbstractEntity implements DomainEntity, EntityDefinedCommonColumn {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    /** The serial version UID for object serialization. (Default) */
    private static final long serialVersionUID = 1L;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    /** VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE} */
    protected Integer _villageId;

    /** START_PERSON_MIN_NUM: {INT UNSIGNED(10)} */
    protected Integer _startPersonMinNum;

    /** PERSON_MAX_NUM: {INT UNSIGNED(10)} */
    protected Integer _personMaxNum;

    /** START_DATETIME: {DATETIME(19)} */
    protected java.time.LocalDateTime _startDatetime;

    /** DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)} */
    protected Integer _dayChangeIntervalSeconds;

    /** IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} */
    protected Boolean _isOpenVote;

    /** IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} */
    protected Boolean _isPossibleSkillRequest;

    /** CHARACTER_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA_GROUP} */
    protected Integer _characterGroupId;

    /** JOIN_PASSWORD: {VARCHAR(12)} */
    protected String _joinPassword;

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
        return "VILLAGE_SETTINGS";
    }

    // ===================================================================================
    //                                                                        Key Handling
    //                                                                        ============
    /** {@inheritDoc} */
    public boolean hasPrimaryKeyValue() {
        if (_villageId == null) { return false; }
        return true;
    }

    // ===================================================================================
    //                                                             Classification Property
    //                                                             =======================
    /**
     * Get the value of isOpenVote as the classification of Flg. <br>
     * IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsOpenVoteAsFlg() {
        return CDef.Flg.codeOf(getIsOpenVote());
    }

    /**
     * Set the value of isOpenVote as the classification of Flg. <br>
     * IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsOpenVoteAsFlg(CDef.Flg cdef) {
        setIsOpenVote(cdef != null ? toBoolean(cdef.code()) : null);
    }

    /**
     * Get the value of isPossibleSkillRequest as the classification of Flg. <br>
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * <p>It's treated as case insensitive and if the code value is null, it returns null.</p>
     * @return The instance of classification definition (as ENUM type). (NullAllowed: when the column value is null)
     */
    public CDef.Flg getIsPossibleSkillRequestAsFlg() {
        return CDef.Flg.codeOf(getIsPossibleSkillRequest());
    }

    /**
     * Set the value of isPossibleSkillRequest as the classification of Flg. <br>
     * IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * フラグを示す
     * @param cdef The instance of classification definition (as ENUM type). (NullAllowed: if null, null value is set to the column)
     */
    public void setIsPossibleSkillRequestAsFlg(CDef.Flg cdef) {
        setIsPossibleSkillRequest(cdef != null ? toBoolean(cdef.code()) : null);
    }

    // ===================================================================================
    //                                                              Classification Setting
    //                                                              ======================
    /**
     * Set the value of isOpenVote as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsOpenVote_True() {
        setIsOpenVoteAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isOpenVote as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsOpenVote_False() {
        setIsOpenVoteAsFlg(CDef.Flg.False);
    }

    /**
     * Set the value of isPossibleSkillRequest as True (true). <br>
     * はい: 有効を示す
     */
    public void setIsPossibleSkillRequest_True() {
        setIsPossibleSkillRequestAsFlg(CDef.Flg.True);
    }

    /**
     * Set the value of isPossibleSkillRequest as False (false). <br>
     * いいえ: 無効を示す
     */
    public void setIsPossibleSkillRequest_False() {
        setIsPossibleSkillRequestAsFlg(CDef.Flg.False);
    }

    // ===================================================================================
    //                                                        Classification Determination
    //                                                        ============================
    /**
     * Is the value of isOpenVote True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsOpenVoteTrue() {
        CDef.Flg cdef = getIsOpenVoteAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isOpenVote False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsOpenVoteFalse() {
        CDef.Flg cdef = getIsOpenVoteAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    /**
     * Is the value of isPossibleSkillRequest True? <br>
     * はい: 有効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsPossibleSkillRequestTrue() {
        CDef.Flg cdef = getIsPossibleSkillRequestAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.True) : false;
    }

    /**
     * Is the value of isPossibleSkillRequest False? <br>
     * いいえ: 無効を示す
     * <p>It's treated as case insensitive and if the code value is null, it returns false.</p>
     * @return The determination, true or false.
     */
    public boolean isIsPossibleSkillRequestFalse() {
        CDef.Flg cdef = getIsPossibleSkillRequestAsFlg();
        return cdef != null ? cdef.equals(CDef.Flg.False) : false;
    }

    // ===================================================================================
    //                                                           Classification Name/Alias
    //                                                           =========================
    /**
     * Get the value of the column 'isOpenVote' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsOpenVoteAlias() {
        CDef.Flg cdef = getIsOpenVoteAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    /**
     * Get the value of the column 'isPossibleSkillRequest' as classification alias.
     * @return The string of classification alias. (NullAllowed: when the column value is null)
     */
    public String getIsPossibleSkillRequestAlias() {
        CDef.Flg cdef = getIsPossibleSkillRequestAsFlg();
        return cdef != null ? cdef.alias() : null;
    }

    // ===================================================================================
    //                                                                    Foreign Property
    //                                                                    ================
    /** CHARA_GROUP by my CHARACTER_GROUP_ID, named 'charaGroup'. */
    protected OptionalEntity<CharaGroup> _charaGroup;

    /**
     * [get] CHARA_GROUP by my CHARACTER_GROUP_ID, named 'charaGroup'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'charaGroup'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<CharaGroup> getCharaGroup() {
        if (_charaGroup == null) { _charaGroup = OptionalEntity.relationEmpty(this, "charaGroup"); }
        return _charaGroup;
    }

    /**
     * [set] CHARA_GROUP by my CHARACTER_GROUP_ID, named 'charaGroup'.
     * @param charaGroup The entity of foreign property 'charaGroup'. (NullAllowed)
     */
    public void setCharaGroup(OptionalEntity<CharaGroup> charaGroup) {
        _charaGroup = charaGroup;
    }

    /** VILLAGE by my VILLAGE_ID, named 'village'. */
    protected OptionalEntity<Village> _village;

    /**
     * [get] VILLAGE by my VILLAGE_ID, named 'village'. <br>
     * Optional: alwaysPresent(), ifPresent().orElse(), get(), ...
     * @return The entity of foreign property 'village'. (NotNull, EmptyAllowed: when e.g. null FK column, no setupSelect)
     */
    public OptionalEntity<Village> getVillage() {
        if (_village == null) { _village = OptionalEntity.relationEmpty(this, "village"); }
        return _village;
    }

    /**
     * [set] VILLAGE by my VILLAGE_ID, named 'village'.
     * @param village The entity of foreign property 'village'. (NullAllowed)
     */
    public void setVillage(OptionalEntity<Village> village) {
        _village = village;
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
        if (obj instanceof BsVillageSettings) {
            BsVillageSettings other = (BsVillageSettings)obj;
            if (!xSV(_villageId, other._villageId)) { return false; }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int doHashCode(int initial) {
        int hs = initial;
        hs = xCH(hs, asTableDbName());
        hs = xCH(hs, _villageId);
        return hs;
    }

    @Override
    protected String doBuildStringWithRelation(String li) {
        StringBuilder sb = new StringBuilder();
        if (_charaGroup != null && _charaGroup.isPresent())
        { sb.append(li).append(xbRDS(_charaGroup, "charaGroup")); }
        if (_village != null && _village.isPresent())
        { sb.append(li).append(xbRDS(_village, "village")); }
        return sb.toString();
    }
    protected <ET extends Entity> String xbRDS(org.dbflute.optional.OptionalEntity<ET> et, String name) { // buildRelationDisplayString()
        return et.get().buildDisplayString(name, true, true);
    }

    @Override
    protected String doBuildColumnString(String dm) {
        StringBuilder sb = new StringBuilder();
        sb.append(dm).append(xfND(_villageId));
        sb.append(dm).append(xfND(_startPersonMinNum));
        sb.append(dm).append(xfND(_personMaxNum));
        sb.append(dm).append(xfND(_startDatetime));
        sb.append(dm).append(xfND(_dayChangeIntervalSeconds));
        sb.append(dm).append(xfND(_isOpenVote));
        sb.append(dm).append(xfND(_isPossibleSkillRequest));
        sb.append(dm).append(xfND(_characterGroupId));
        sb.append(dm).append(xfND(_joinPassword));
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
        if (_charaGroup != null && _charaGroup.isPresent())
        { sb.append(dm).append("charaGroup"); }
        if (_village != null && _village.isPresent())
        { sb.append(dm).append("village"); }
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length()).insert(0, "(").append(")");
        }
        return sb.toString();
    }

    @Override
    public VillageSettings clone() {
        return (VillageSettings)super.clone();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * [get] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE} <br>
     * 村ID
     * @return The value of the column 'VILLAGE_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getVillageId() {
        checkSpecifiedProperty("villageId");
        return _villageId;
    }

    /**
     * [set] VILLAGE_ID: {PK, NotNull, INT UNSIGNED(10), FK to VILLAGE} <br>
     * 村ID
     * @param villageId The value of the column 'VILLAGE_ID'. (basically NotNull if update: for the constraint)
     */
    public void setVillageId(Integer villageId) {
        registerModifiedProperty("villageId");
        _villageId = villageId;
    }

    /**
     * [get] START_PERSON_MIN_NUM: {INT UNSIGNED(10)} <br>
     * 最低開始人数
     * @return The value of the column 'START_PERSON_MIN_NUM'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getStartPersonMinNum() {
        checkSpecifiedProperty("startPersonMinNum");
        return _startPersonMinNum;
    }

    /**
     * [set] START_PERSON_MIN_NUM: {INT UNSIGNED(10)} <br>
     * 最低開始人数
     * @param startPersonMinNum The value of the column 'START_PERSON_MIN_NUM'. (NullAllowed: null update allowed for no constraint)
     */
    public void setStartPersonMinNum(Integer startPersonMinNum) {
        registerModifiedProperty("startPersonMinNum");
        _startPersonMinNum = startPersonMinNum;
    }

    /**
     * [get] PERSON_MAX_NUM: {INT UNSIGNED(10)} <br>
     * 定員
     * @return The value of the column 'PERSON_MAX_NUM'. (NullAllowed even if selected: for no constraint)
     */
    public Integer getPersonMaxNum() {
        checkSpecifiedProperty("personMaxNum");
        return _personMaxNum;
    }

    /**
     * [set] PERSON_MAX_NUM: {INT UNSIGNED(10)} <br>
     * 定員
     * @param personMaxNum The value of the column 'PERSON_MAX_NUM'. (NullAllowed: null update allowed for no constraint)
     */
    public void setPersonMaxNum(Integer personMaxNum) {
        registerModifiedProperty("personMaxNum");
        _personMaxNum = personMaxNum;
    }

    /**
     * [get] START_DATETIME: {DATETIME(19)} <br>
     * 開始日時
     * @return The value of the column 'START_DATETIME'. (NullAllowed even if selected: for no constraint)
     */
    public java.time.LocalDateTime getStartDatetime() {
        checkSpecifiedProperty("startDatetime");
        return _startDatetime;
    }

    /**
     * [set] START_DATETIME: {DATETIME(19)} <br>
     * 開始日時
     * @param startDatetime The value of the column 'START_DATETIME'. (NullAllowed: null update allowed for no constraint)
     */
    public void setStartDatetime(java.time.LocalDateTime startDatetime) {
        registerModifiedProperty("startDatetime");
        _startDatetime = startDatetime;
    }

    /**
     * [get] DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)} <br>
     * 更新間隔（秒）
     * @return The value of the column 'DAY_CHANGE_INTERVAL_SECONDS'. (basically NotNull if selected: for the constraint)
     */
    public Integer getDayChangeIntervalSeconds() {
        checkSpecifiedProperty("dayChangeIntervalSeconds");
        return _dayChangeIntervalSeconds;
    }

    /**
     * [set] DAY_CHANGE_INTERVAL_SECONDS: {NotNull, INT UNSIGNED(10)} <br>
     * 更新間隔（秒）
     * @param dayChangeIntervalSeconds The value of the column 'DAY_CHANGE_INTERVAL_SECONDS'. (basically NotNull if update: for the constraint)
     */
    public void setDayChangeIntervalSeconds(Integer dayChangeIntervalSeconds) {
        registerModifiedProperty("dayChangeIntervalSeconds");
        _dayChangeIntervalSeconds = dayChangeIntervalSeconds;
    }

    /**
     * [get] IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * 記名投票か
     * @return The value of the column 'IS_OPEN_VOTE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsOpenVote() {
        checkSpecifiedProperty("isOpenVote");
        return _isOpenVote;
    }

    /**
     * [set] IS_OPEN_VOTE: {NotNull, BIT, classification=Flg} <br>
     * 記名投票か
     * @param isOpenVote The value of the column 'IS_OPEN_VOTE'. (basically NotNull if update: for the constraint)
     */
    public void setIsOpenVote(Boolean isOpenVote) {
        checkClassificationCode("IS_OPEN_VOTE", CDef.DefMeta.Flg, isOpenVote);
        registerModifiedProperty("isOpenVote");
        _isOpenVote = isOpenVote;
    }

    /**
     * [get] IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * 役職希望有効か
     * @return The value of the column 'IS_POSSIBLE_SKILL_REQUEST'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsPossibleSkillRequest() {
        checkSpecifiedProperty("isPossibleSkillRequest");
        return _isPossibleSkillRequest;
    }

    /**
     * [set] IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT, classification=Flg} <br>
     * 役職希望有効か
     * @param isPossibleSkillRequest The value of the column 'IS_POSSIBLE_SKILL_REQUEST'. (basically NotNull if update: for the constraint)
     */
    public void setIsPossibleSkillRequest(Boolean isPossibleSkillRequest) {
        checkClassificationCode("IS_POSSIBLE_SKILL_REQUEST", CDef.DefMeta.Flg, isPossibleSkillRequest);
        registerModifiedProperty("isPossibleSkillRequest");
        _isPossibleSkillRequest = isPossibleSkillRequest;
    }

    /**
     * [get] CHARACTER_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA_GROUP} <br>
     * キャラクターグループID
     * @return The value of the column 'CHARACTER_GROUP_ID'. (basically NotNull if selected: for the constraint)
     */
    public Integer getCharacterGroupId() {
        checkSpecifiedProperty("characterGroupId");
        return _characterGroupId;
    }

    /**
     * [set] CHARACTER_GROUP_ID: {IX, NotNull, INT UNSIGNED(10), FK to CHARA_GROUP} <br>
     * キャラクターグループID
     * @param characterGroupId The value of the column 'CHARACTER_GROUP_ID'. (basically NotNull if update: for the constraint)
     */
    public void setCharacterGroupId(Integer characterGroupId) {
        registerModifiedProperty("characterGroupId");
        _characterGroupId = characterGroupId;
    }

    /**
     * [get] JOIN_PASSWORD: {VARCHAR(12)} <br>
     * 入村パスワード
     * @return The value of the column 'JOIN_PASSWORD'. (NullAllowed even if selected: for no constraint)
     */
    public String getJoinPassword() {
        checkSpecifiedProperty("joinPassword");
        return convertEmptyToNull(_joinPassword);
    }

    /**
     * [set] JOIN_PASSWORD: {VARCHAR(12)} <br>
     * 入村パスワード
     * @param joinPassword The value of the column 'JOIN_PASSWORD'. (NullAllowed: null update allowed for no constraint)
     */
    public void setJoinPassword(String joinPassword) {
        registerModifiedProperty("joinPassword");
        _joinPassword = joinPassword;
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
