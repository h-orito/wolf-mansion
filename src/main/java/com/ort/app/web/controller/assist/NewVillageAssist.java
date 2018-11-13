package com.ort.app.web.controller.assist;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.controller.logic.TwitterLogic;
import com.ort.app.web.controller.logic.VillageParticipateLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.NewVillageSayRestrictDetailDto;
import com.ort.app.web.form.NewVillageSayRestrictDto;
import com.ort.app.web.model.common.SelectOptionDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exbhv.MessageRestrictionBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.MessageRestriction;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class NewVillageAssist {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final String DEFAULT_ORGANIZE = // 
            "村狼狼賢導村村村\n" // 8
                    + "村狼狼賢導村村村村\n" // 9
                    + "村狼狼狂賢導村村村村\n" // 10
                    + "村狼狼狂賢導村村村村村\n" // 11
                    + "村狼狼狼狂賢導狩村村村村\n" // 12
                    + "村狼狼狼狂賢導狩村村村村村\n" // 13
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊\n" // 14
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊霊\n" // 15
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊共共\n" // 16
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊霊共共\n" // 17
                    + "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊共共\n" // 18
                    + "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊共共\n" // 19
                    + "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊霊共共"; // 20
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");
    private static final List<CDef.Skill> SET_AVAILABLE_SKILLS =
            Arrays.asList(CDef.Skill.村人, CDef.Skill.霊能者, CDef.Skill.導師, CDef.Skill.人狼, CDef.Skill.C国狂人, CDef.Skill.占い師, CDef.Skill.賢者,
                    CDef.Skill.狩人, CDef.Skill.共鳴者, CDef.Skill.狂人, CDef.Skill.魔神官, CDef.Skill.狂信者, CDef.Skill.妖狐);
    private static final int DEFAULT_SAY_MAX_COUNT = 20;
    private static final int DEFAULT_SAY_MAX_LENGTH = 400;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillageSettingsBhv villageSettingsBhv;
    @Autowired
    private MessageRestrictionBhv messageRestrictionBhv;
    @Autowired
    private VillageDayBhv villageDayBhv;
    @Autowired
    private CharaGroupBhv charaGroupBhv;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageParticipateLogic villageLogic;
    @Autowired
    private TwitterLogic twitterLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void setIndexModel(NewVillageForm form, Model model) {
        if (form.getStartPersonMinNum() == null) {
            form.setStartPersonMinNum(8);
        }
        if (form.getPersonMaxNum() == null) {
            form.setPersonMaxNum(20);
        }
        if (form.getIsOpenVote() == null) {
            form.setIsOpenVote(true);
        }
        if (form.getIsPossibleSkillRequest() == null) {
            form.setIsPossibleSkillRequest(true);
        }
        if (form.getIsAvailableSpectate() == null) {
            form.setIsAvailableSpectate(false);
        }
        if (form.getIsAvailableSameWolfAttack() == null) {
            form.setIsAvailableSameWolfAttack(true);
        }
        if (form.getIsOpenSkillInGrave() == null) {
            form.setIsOpenSkillInGrave(false);
        }
        if (form.getIsVisibleGraveSpectateMessage() == null) {
            form.setIsVisibleGraveSpectateMessage(false);
        }
        if (form.getIsAvailableSuddonlyDeath() == null) {
            form.setIsAvailableSuddonlyDeath(false);
        }
        if (form.getIsAvailableCommit() == null) {
            form.setIsAvailableCommit(false);
        }
        if (form.getIsAvailableGuardSameTarget() == null) {
            form.setIsAvailableGuardSameTarget(true);
        }
        if (form.getDayChangeIntervalHours() == null) {
            form.setDayChangeIntervalHours(24);
        }
        if (form.getStartYear() == null) {
            // 一週間後にしておく
            LocalDateTime oneWeekAfter = WerewolfMansionDateUtil.currentLocalDateTime().plusDays(7L);
            form.setStartYear(oneWeekAfter.getYear());
            form.setStartMonth(oneWeekAfter.getMonthValue());
            form.setStartDay(oneWeekAfter.getDayOfMonth());
            form.setStartHour(0);
            form.setStartMinute(0);
        }
        if (form.getOrganization() == null) {
            form.setOrganization(DEFAULT_ORGANIZE);
        }
        if (form.getAllowedSecretSayCode() == null) {
            form.setAllowedSecretSayCode(CDef.AllowedSecretSay.なし.code());
        }
        if (CollectionUtils.isEmpty(form.getSayRestrictList())) {
            form.setSayRestrictList(createRestrictList());
        }

        model.addAttribute("villageForm", form);

        // 現在の年
        LocalDate now = WerewolfMansionDateUtil.currentLocalDate();
        model.addAttribute("nowYear", now.getYear());

        ListResultBean<CharaGroup> charaGroupList = charaGroupBhv.selectList(cb -> cb.setupSelect_Designer());
        List<SelectOptionDto<Integer>> characterSetList = convertToCharacterSetList(charaGroupList);
        model.addAttribute("characterSetList", characterSetList);
    }

    // 村作成
    public Village createVillage(NewVillageForm villageForm, String userName) throws WerewolfMansionBusinessException {
        // 村
        Village village = insertVillage(villageForm, userName);
        // 村設定
        VillageSettings settings = insertVillageSettings(villageForm, village);
        // 発言制限
        insertMessageRestrict(village.getVillageId(), villageForm);
        // 村日付
        insertVillageDay(village, 0, settings.getStartDatetime());
        // システムメッセージ
        insertInitialMessage(villageForm, village);
        // ダミーキャラを参加させる
        joinDummyChara(villageForm, village);
        // tweet
        tweetNewVillage(villageForm, village.getVillageId());

        return village;
    }

    private void tweetNewVillage(NewVillageForm villageForm, Integer villageId) {
        String villageName = villageForm.getVillageName();
        if (StringUtils.isNotEmpty(villageForm.getJoinPassword())) { // 身内村は通知しない
            return;
        }
        String startDatetime =
                LocalDateTime
                        .of(villageForm.getStartYear(), villageForm.getStartMonth(), villageForm.getStartDay(), villageForm.getStartHour(),
                                villageForm.getStartMinute(), 0)
                        .format(TIME_FORMAT);
        twitterLogic.tweet(String.format("新しい村が作成されました。\r\n村名：%s\r\n開始予定：%s", villageName, startDatetime), villageId);
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    // 村日付登録
    private void insertVillageDay(Village village, int day, LocalDateTime startDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(village.getVillageId());
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(startDatetime);
        villageDayBhv.insert(villageDay);
    }

    // 村建て初期メッセージ登録
    private void insertInitialMessage(NewVillageForm villageForm, Village village) {
        try {
            messageLogic.insertMessage( // 
                    village.getVillageId(), // 村ID
                    0, // day
                    CDef.MessageType.公開システムメッセージ, // 発言種別
                    messageSource.getMessage("newvillage.initial.message", null, Locale.JAPAN), // メッセージ内容
                    false); // 変換有効 
        } catch (WerewolfMansionBusinessException e) {
            // 被ることは100％ないため何もしない
        }
    }

    private Village insertVillage(NewVillageForm villageForm, String userName) {
        Village village = new Village();
        village.setVillageDisplayName(villageForm.getVillageName());
        village.setVillageStatusCode_募集中();
        village.setCreatePlayerName(userName);
        villageBhv.insert(village);
        return village;
    }

    private VillageSettings insertVillageSettings(NewVillageForm villageForm, Village village) throws WerewolfMansionBusinessException {
        VillageSettings settings = new VillageSettings();
        settings.setVillageId(village.getVillageId());
        settings.setDummyCharaId(villageForm.getDummyCharaId());
        settings.setStartPersonMinNum(villageForm.getStartPersonMinNum());
        settings.setPersonMaxNum(villageForm.getPersonMaxNum());
        settings.setDayChangeIntervalSeconds(villageForm.getDayChangeIntervalHours() * 3600 + villageForm.getDayChangeIntervalMinutes() * 60
                + villageForm.getDayChangeIntervalSeconds());
        settings.setStartDatetime(makeStartDateTime(villageForm));
        settings.setIsOpenVote(villageForm.getIsOpenVote());
        settings.setIsPossibleSkillRequest(villageForm.getIsPossibleSkillRequest());
        settings.setIsAvailableSpectate(villageForm.getIsAvailableSpectate());
        settings.setCharacterGroupId(villageForm.getCharacterSetId());
        settings.setJoinPassword(villageForm.getJoinPassword());
        settings.setIsAvailableSameWolfAttack(villageForm.getIsAvailableSameWolfAttack());
        settings.setIsAvailableGuardSameTarget(villageForm.getIsAvailableGuardSameTarget());
        settings.setIsOpenSkillInGrave(villageForm.getIsOpenSkillInGrave());
        settings.setIsVisibleGraveSpectateMessage(villageForm.getIsVisibleGraveSpectateMessage());
        settings.setIsAvailableSuddonlyDeath(villageForm.getIsAvailableSuddonlyDeath());
        settings.setIsAvailableCommit(villageForm.getIsAvailableCommit());
        settings.setOrganize(villageForm.getOrganization().replace("\r\n", "\n"));
        settings.setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay.codeOf(villageForm.getAllowedSecretSayCode()));
        villageSettingsBhv.insert(settings);
        return settings;
    }

    private void insertMessageRestriction(Integer villageId, String skillCode, NewVillageSayRestrictDetailDto detail) {
        MessageRestriction entity = new MessageRestriction();
        entity.setVillageId(villageId);
        entity.setMessageMaxNum(detail.getCount());
        entity.setMessageMaxLength(detail.getLength());
        entity.setMessageTypeCodeAsMessageType(CDef.MessageType.codeOf(detail.getMessageTypeCode()));
        entity.setSkillCodeAsSkill(CDef.Skill.codeOf(skillCode));
        messageRestrictionBhv.insert(entity);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private List<SelectOptionDto<Integer>> convertToCharacterSetList(ListResultBean<CharaGroup> charaGroupList) {
        return charaGroupList.mappingList(charaGroup -> {
            SelectOptionDto<Integer> option = new SelectOptionDto<Integer>();
            option.setName(String.format("%s (%s様作)", charaGroup.getCharaGroupName(), charaGroup.getDesigner().get().getDesignerName()));
            option.setValue(charaGroup.getCharaGroupId());
            return option;
        });
    }

    private void joinDummyChara(NewVillageForm villageForm, Village village) {
        int dummyPlayerId = 1;
        villageLogic.participate(village.getVillageId(), dummyPlayerId, villageForm.getDummyCharaId(), CDef.Skill.村人, CDef.Skill.村人,
                villageForm.getDummyJoinMessage(), false, false);
    }

    private LocalDateTime makeStartDateTime(NewVillageForm form) throws WerewolfMansionBusinessException {
        try {
            return LocalDateTime.of(form.getStartYear(), form.getStartMonth(), form.getStartDay(), form.getStartHour(),
                    form.getStartMinute());
        } catch (DateTimeException e) {
            throw new WerewolfMansionBusinessException("存在しない日付です");
        }
    }

    private List<NewVillageSayRestrictDto> createRestrictList() {
        return SET_AVAILABLE_SKILLS.stream().map(skill -> {
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
        if (skill == CDef.Skill.人狼 || skill == CDef.Skill.C国狂人) {
            detailList.add(createDetail("囁き", CDef.MessageType.人狼の囁き.code()));
        } else if (skill == CDef.Skill.共鳴者) {
            detailList.add(createDetail("共鳴", CDef.MessageType.共鳴発言.code()));
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

    private void insertMessageRestrict(Integer villageId, NewVillageForm villageForm) {
        villageForm.getSayRestrictList().forEach(restrict -> {
            String skillCode = restrict.getSkillCode();
            restrict.getDetailList().forEach(detail -> {
                // 制限無しの場合は登録しない
                if (BooleanUtils.isFalse(detail.getIsRestrict())) {
                    return;
                }
                insertMessageRestriction(villageId, skillCode, detail);
            });
        });
    }
}
