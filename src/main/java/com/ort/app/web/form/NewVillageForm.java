package com.ort.app.web.form;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.CollectionUtils;

import com.ort.app.logic.AssignLogic;
import com.ort.app.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.fw.util.WerewolfMansionDateUtil;

public class NewVillageForm implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SAY_MAX_COUNT = 20;
    private static final int DEFAULT_SAY_MAX_LENGTH = 400;

    /** 村表示名 */
    @NotNull
    @Length(min = 5, max = 40)
    private String villageName;

    /** 最低開始人数 */
    @NotNull
    @Min(8)
    private Integer startPersonMinNum;

    /** 定員 */
    @NotNull
    @Max(99)
    private Integer personMaxNum;

    /** 更新間隔時間 */
    @Min(0)
    @Max(48)
    private Integer dayChangeIntervalHours;

    /** 更新間隔分 */
    @Min(0)
    @Max(59)
    private Integer dayChangeIntervalMinutes;

    /** 更新間隔秒 */
    @Min(0)
    @Max(59)
    private Integer dayChangeIntervalSeconds;

    /** 開始年 */
    @Min(0)
    private Integer startYear;
    /** 開始月 */
    @Min(1)
    @Max(12)
    private Integer startMonth;
    /** 開始日 */
    @Min(1)
    @Max(31)
    private Integer startDay;
    /** 開始時間 */
    @Min(0)
    @Max(23)
    private Integer startHour;
    /** 開始分 */
    @Min(0)
    @Max(59)
    private Integer startMinute;

    /** 記名投票か */
    @NotNull
    private Boolean isOpenVote;

    /** 役職希望有効か */
    @NotNull
    private Boolean isPossibleSkillRequest;

    /** 連続襲撃ありか */
    @NotNull
    private Boolean isAvailableSameWolfAttack;

    /** 墓下役職公開ありか */
    @NotNull
    private Boolean isOpenSkillInGrave;

    /** 墓下見学発言を地上から見られるか */
    @NotNull
    private Boolean isVisibleGraveSpectateMessage;

    /** キャラセットID */
    @NotNull
    private Integer characterSetId;

    /** ダミーキャラID */
    @NotNull
    private Integer dummyCharaId;

    /** ダミーキャラ入村発言 */
    @NotNull
    @Length(min = 1, max = 400)
    private String dummyJoinMessage;

    /** 入村パスワード */
    private String joinPassword;

    /** 観戦を可能にするか */
    @NotNull
    private Boolean isAvailableSpectate;

    /** 突然死ありか */
    @NotNull
    private Boolean isAvailableSuddonlyDeath;

    /** コミット可能か */
    @NotNull
    private Boolean isAvailableCommit;

    /** 連続ガードありか */
    @NotNull
    private Boolean isAvailableGuardSameTarget;

    /** 構成 */
    @NotNull
    private String organization;

    /** 秘話可能範囲 */
    @NotNull
    private String allowedSecretSayCode;

    /** 発言制限 */
    @NotNull
    private List<NewVillageSayRestrictDto> sayRestrictList;

    public void initialize() {
        if (startPersonMinNum == null) {
            startPersonMinNum = 8;
        }
        if (personMaxNum == null) {
            personMaxNum = 20;
        }
        if (isOpenVote == null) {
            isOpenVote = true;
        }
        if (isPossibleSkillRequest == null) {
            isPossibleSkillRequest = true;
        }
        if (isAvailableSpectate == null) {
            isAvailableSpectate = false;
        }
        if (isAvailableSameWolfAttack == null) {
            isAvailableSameWolfAttack = true;
        }
        if (isOpenSkillInGrave == null) {
            isOpenSkillInGrave = false;
        }
        if (isVisibleGraveSpectateMessage == null) {
            isVisibleGraveSpectateMessage = false;
        }
        if (isAvailableSuddonlyDeath == null) {
            isAvailableSuddonlyDeath = false;
        }
        if (isAvailableCommit == null) {
            isAvailableCommit = false;
        }
        if (isAvailableGuardSameTarget == null) {
            isAvailableGuardSameTarget = true;
        }
        if (dayChangeIntervalHours == null) {
            dayChangeIntervalHours = 24;
        }
        if (startYear == null) {
            // 一週間後にしておく
            LocalDateTime oneWeekAfter = WerewolfMansionDateUtil.currentLocalDateTime().plusDays(7L);
            startYear = oneWeekAfter.getYear();
            startMonth = oneWeekAfter.getMonthValue();
            startDay = oneWeekAfter.getDayOfMonth();
            startHour = 0;
            startMinute = 0;
        }
        if (organization == null) {
            organization = AssignLogic.DEFAULT_ORGANIZE;
        }
        if (allowedSecretSayCode == null) {
            allowedSecretSayCode = CDef.AllowedSecretSay.なし.code();
        }
        if (CollectionUtils.isEmpty(sayRestrictList)) {
            sayRestrictList = createRestrictList();
        }
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Integer getStartPersonMinNum() {
        return startPersonMinNum;
    }

    public void setStartPersonMinNum(Integer startPersonMinNum) {
        this.startPersonMinNum = startPersonMinNum;
    }

    public Integer getPersonMaxNum() {
        return personMaxNum;
    }

    public void setPersonMaxNum(Integer personMaxNum) {
        this.personMaxNum = personMaxNum;
    }

    public Integer getDayChangeIntervalHours() {
        return dayChangeIntervalHours;
    }

    public void setDayChangeIntervalHours(Integer dayChangeIntervalHours) {
        this.dayChangeIntervalHours = dayChangeIntervalHours;
    }

    public Integer getDayChangeIntervalMinutes() {
        return dayChangeIntervalMinutes;
    }

    public void setDayChangeIntervalMinutes(Integer dayChangeIntervalMinutes) {
        this.dayChangeIntervalMinutes = dayChangeIntervalMinutes;
    }

    public Integer getDayChangeIntervalSeconds() {
        return dayChangeIntervalSeconds;
    }

    public void setDayChangeIntervalSeconds(Integer dayChangeIntervalSeconds) {
        this.dayChangeIntervalSeconds = dayChangeIntervalSeconds;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Boolean getIsOpenVote() {
        return isOpenVote;
    }

    public void setIsOpenVote(Boolean isOpenVote) {
        this.isOpenVote = isOpenVote;
    }

    public Boolean getIsPossibleSkillRequest() {
        return isPossibleSkillRequest;
    }

    public void setIsPossibleSkillRequest(Boolean isPossibleSkillRequest) {
        this.isPossibleSkillRequest = isPossibleSkillRequest;
    }

    public Integer getCharacterSetId() {
        return characterSetId;
    }

    public void setCharacterSetId(Integer characterSetId) {
        this.characterSetId = characterSetId;
    }

    public String getDummyJoinMessage() {
        return dummyJoinMessage;
    }

    public void setDummyJoinMessage(String dummyJoinMessage) {
        this.dummyJoinMessage = dummyJoinMessage;
    }

    public String getJoinPassword() {
        return joinPassword;
    }

    public void setJoinPassword(String joinPassword) {
        this.joinPassword = joinPassword;
    }

    public Boolean getIsAvailableSpectate() {
        return isAvailableSpectate;
    }

    public void setIsAvailableSpectate(Boolean isAvailableSpectate) {
        this.isAvailableSpectate = isAvailableSpectate;
    }

    public Boolean getIsAvailableSameWolfAttack() {
        return isAvailableSameWolfAttack;
    }

    public void setIsAvailableSameWolfAttack(Boolean isAvailableSameWolfAttack) {
        this.isAvailableSameWolfAttack = isAvailableSameWolfAttack;
    }

    public Boolean getIsOpenSkillInGrave() {
        return isOpenSkillInGrave;
    }

    public void setIsOpenSkillInGrave(Boolean isOpenSkillInGrave) {
        this.isOpenSkillInGrave = isOpenSkillInGrave;
    }

    public Boolean getIsVisibleGraveSpectateMessage() {
        return isVisibleGraveSpectateMessage;
    }

    public void setIsVisibleGraveSpectateMessage(Boolean isVisibleGraveSpectateMessage) {
        this.isVisibleGraveSpectateMessage = isVisibleGraveSpectateMessage;
    }

    public Boolean getIsAvailableSuddonlyDeath() {
        return isAvailableSuddonlyDeath;
    }

    public void setIsAvailableSuddonlyDeath(Boolean isAvailableSuddonlyDeath) {
        this.isAvailableSuddonlyDeath = isAvailableSuddonlyDeath;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Boolean getIsAvailableCommit() {
        return isAvailableCommit;
    }

    public void setIsAvailableCommit(Boolean isAvailableCommit) {
        this.isAvailableCommit = isAvailableCommit;
    }

    public Boolean getIsAvailableGuardSameTarget() {
        return isAvailableGuardSameTarget;
    }

    public void setIsAvailableGuardSameTarget(Boolean isAvailableGuardSameTarget) {
        this.isAvailableGuardSameTarget = isAvailableGuardSameTarget;
    }

    public String getAllowedSecretSayCode() {
        return allowedSecretSayCode;
    }

    public void setAllowedSecretSayCode(String allowedSecretSayCode) {
        this.allowedSecretSayCode = allowedSecretSayCode;
    }

    public List<NewVillageSayRestrictDto> getSayRestrictList() {
        return sayRestrictList;
    }

    public void setSayRestrictList(List<NewVillageSayRestrictDto> sayRestrictList) {
        this.sayRestrictList = sayRestrictList;
    }

    public Integer getDummyCharaId() {
        return dummyCharaId;
    }

    public void setDummyCharaId(Integer dummyCharaId) {
        this.dummyCharaId = dummyCharaId;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private List<NewVillageSayRestrictDto> createRestrictList() {
        return SkillUtil.SORTED_SKILL_LIST.stream().map(skill -> {
            NewVillageSayRestrictDto restrict = new NewVillageSayRestrictDto();
            restrict.setSkillName(skill.name());
            restrict.setSkillCode(skill.code());
            restrict.setDetailList(createDetailList(skill));
            return restrict;
        }).collect(Collectors.toList());
    }

    private List<NewVillageSayRestrictDetailDto> createDetailList(Skill skill) {
        List<NewVillageSayRestrictDetailDto> detailList = new ArrayList<>();
        detailList.add(createDetail("通常発言", CDef.MessageType.通常発言.code()));
        if (skill.isAvailableWerewolfSay()) {
            detailList.add(createDetail("囁き", CDef.MessageType.人狼の囁き.code()));
        } else if (skill == CDef.Skill.共鳴者) {
            detailList.add(createDetail("共鳴", CDef.MessageType.共鳴発言.code()));
        } else if (skill == CDef.Skill.恋人 || skill == CDef.Skill.同棲者) {
            detailList.add(createDetail("恋人", CDef.MessageType.恋人発言.code()));
        }
        return detailList;
    }

    private NewVillageSayRestrictDetailDto createDetail(String messageTypeName, String messageTypeCode) {
        NewVillageSayRestrictDetailDto detail = new NewVillageSayRestrictDetailDto();
        detail.setMessageTypeName(messageTypeName);
        detail.setMessageTypeCode(messageTypeCode);
        detail.setIsRestrict(false);
        detail.setCount(DEFAULT_SAY_MAX_COUNT);
        detail.setLength(DEFAULT_SAY_MAX_LENGTH);
        return detail;
    }
}
