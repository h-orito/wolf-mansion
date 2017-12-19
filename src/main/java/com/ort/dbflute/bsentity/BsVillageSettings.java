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
 * The entity of VILLAGE_SETTINGS as TABLE. <br>
 * 村設定
 * <pre>
 * [primary-key]
 *     VILLAGE_ID
 *
 * [column]
 *     VILLAGE_ID, START_PERSON_MIN_NUM, PERSON_MAX_NUM, START_DATETIME, DAY_CHANGE_INTERVAL_SECONDS, IS_OPEN_VOTE, IS_POSSIBLE_SKILL_REQUEST
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
 *     VILLAGE
 *
 * [referrer table]
 *     
 *
 * [foreign property]
 *     village
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
 * entity.setVillageId(villageId);
 * entity.setStartPersonMinNum(startPersonMinNum);
 * entity.setPersonMaxNum(personMaxNum);
 * entity.setStartDatetime(startDatetime);
 * entity.setDayChangeIntervalSeconds(dayChangeIntervalSeconds);
 * entity.setIsOpenVote(isOpenVote);
 * entity.setIsPossibleSkillRequest(isPossibleSkillRequest);
 * = = = = = = = = = =/
 * </pre>
 * @author DBFlute(AutoGenerator)
 */
public abstract class BsVillageSettings extends AbstractEntity implements DomainEntity {

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

    /** IS_OPEN_VOTE: {NotNull, BIT} */
    protected Boolean _isOpenVote;

    /** IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT} */
    protected Boolean _isPossibleSkillRequest;

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
    //                                                                    Foreign Property
    //                                                                    ================
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
        if (sb.length() > dm.length()) {
            sb.delete(0, dm.length());
        }
        sb.insert(0, "{").append("}");
        return sb.toString();
    }

    @Override
    protected String doBuildRelationString(String dm) {
        StringBuilder sb = new StringBuilder();
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
     * [get] IS_OPEN_VOTE: {NotNull, BIT} <br>
     * 記名投票か
     * @return The value of the column 'IS_OPEN_VOTE'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsOpenVote() {
        checkSpecifiedProperty("isOpenVote");
        return _isOpenVote;
    }

    /**
     * [set] IS_OPEN_VOTE: {NotNull, BIT} <br>
     * 記名投票か
     * @param isOpenVote The value of the column 'IS_OPEN_VOTE'. (basically NotNull if update: for the constraint)
     */
    public void setIsOpenVote(Boolean isOpenVote) {
        registerModifiedProperty("isOpenVote");
        _isOpenVote = isOpenVote;
    }

    /**
     * [get] IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT} <br>
     * 役職希望有効か
     * @return The value of the column 'IS_POSSIBLE_SKILL_REQUEST'. (basically NotNull if selected: for the constraint)
     */
    public Boolean getIsPossibleSkillRequest() {
        checkSpecifiedProperty("isPossibleSkillRequest");
        return _isPossibleSkillRequest;
    }

    /**
     * [set] IS_POSSIBLE_SKILL_REQUEST: {NotNull, BIT} <br>
     * 役職希望有効か
     * @param isPossibleSkillRequest The value of the column 'IS_POSSIBLE_SKILL_REQUEST'. (basically NotNull if update: for the constraint)
     */
    public void setIsPossibleSkillRequest(Boolean isPossibleSkillRequest) {
        registerModifiedProperty("isPossibleSkillRequest");
        _isPossibleSkillRequest = isPossibleSkillRequest;
    }
}
