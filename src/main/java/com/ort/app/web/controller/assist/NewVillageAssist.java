package com.ort.app.web.controller.assist;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.TwitterLogic;
import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.util.SkillUtil;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.NewVillageRpSayRestrictDto;
import com.ort.app.web.form.NewVillageSayRestrictDto;
import com.ort.app.web.form.NewVillageSkillSayRestrictDto;
import com.ort.app.web.model.common.SelectOptionDto;
import com.ort.app.web.model.inner.NewVillageDivertVillageDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exbhv.NormalSayRestrictionBhv;
import com.ort.dbflute.exbhv.SkillSayRestrictionBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.NormalSayRestriction;
import com.ort.dbflute.exentity.SkillSayRestriction;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class NewVillageAssist {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillageSettingsBhv villageSettingsBhv;
    @Autowired
    private NormalSayRestrictionBhv normalSayRestrictionBhv;
    @Autowired
    private SkillSayRestrictionBhv skillSayRestrictionBhv;
    @Autowired
    private VillageDayBhv villageDayBhv;
    @Autowired
    private CharaGroupBhv charaGroupBhv;
    @Autowired
    private CharaBhv charaBhv;
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
        form.initialize();
        model.addAttribute("villageForm", form);

        // ????????????????????????
        List<NewVillageDivertVillageDto> villageList = villageBhv.selectList(cb -> {
            cb.query()
                    .setVillageStatusCode_InScope_AsVillageStatus(
                            Arrays.asList(CDef.VillageStatus.???????????????, CDef.VillageStatus.??????, CDef.VillageStatus.??????));
            cb.query().addOrderBy_VillageId_Asc();
        }).stream().map(v -> new NewVillageDivertVillageDto(v.getVillageId(), v.getVillageDisplayName())).collect(Collectors.toList());
        model.addAttribute("villageList", villageList);

        // ???????????????
        model.addAttribute("skillListStr", SkillUtil.getSkillListStr());

        // ????????????
        LocalDate now = WerewolfMansionDateUtil.currentLocalDate();
        model.addAttribute("nowYear", now.getYear());

        ListResultBean<CharaGroup> charaGroupList = charaGroupBhv.selectList(cb -> cb.setupSelect_Designer());
        List<SelectOptionDto<Integer>> characterSetList = convertToCharacterSetList(charaGroupList);
        model.addAttribute("characterSetList", characterSetList);
    }

    // ????????????
    public void override(NewVillageForm form, Model model, Integer villageId) {
        VillageSettings settings = villageSettingsBhv.selectEntityWithDeletedCheck(cb -> cb.query().setVillageId_Equal(villageId));
        form.setStartPersonMinNum(settings.getStartPersonMinNum());
        form.setPersonMaxNum(settings.getPersonMaxNum());
        form.setDayChangeIntervalHours(settings.getDayChangeIntervalSeconds() / 3600);
        form.setDayChangeIntervalMinutes((settings.getDayChangeIntervalSeconds() % 3600) / 60);
        form.setDayChangeIntervalSeconds(settings.getDayChangeIntervalSeconds() % 60);
        form.setCharacterSetId(settings.getCharacterGroupId());
        form.setDummyCharaId(settings.getDummyCharaId());
        form.setOrganization(settings.getOrganize().trim());
        form.setIsPossibleSkillRequest(settings.getIsPossibleSkillRequest());
        form.setIsAvailableSameWolfAttack(settings.getIsAvailableSameWolfAttack());
        form.setIsAvailableGuardSameTarget(settings.getIsAvailableGuardSameTarget());
        form.setIsAvailableSuddonlyDeath(settings.getIsAvailableSuddonlyDeath());
        form.setIsAvailableCommit(settings.getIsAvailableCommit());
        form.setIsAvailableSpectate(settings.getIsAvailableSpectate());
        form.setIsOpenSkillInGrave(settings.getIsOpenSkillInGrave());
        form.setIsVisibleGraveSpectateMessage(settings.getIsVisibleGraveSpectateMessage());
        form.setAllowedSecretSayCode(settings.getAllowedSecretSayCode());
        form.setIsOpenVote(settings.getIsOpenVote());
        ListResultBean<NormalSayRestriction> normalSayRestrictList =
                normalSayRestrictionBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        form.getSayRestrictList().forEach(formRest -> {
            String skillCode = formRest.getSkillCode();
            Optional<NormalSayRestriction> optRestrict =
                    normalSayRestrictList.stream().filter(rest -> rest.getSkillCode().equals(skillCode)).findFirst();
            if (optRestrict.isPresent()) {
                formRest.setIsRestrict(true);
                formRest.setCount(optRestrict.get().getMessageMaxNum());
                formRest.setLength(optRestrict.get().getMessageMaxLength());
            } else {
                formRest.setIsRestrict(false);
                formRest.setCount(20);
                formRest.setLength(400);
            }
        });
        ListResultBean<SkillSayRestriction> skillSayRestrictList =
                skillSayRestrictionBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        form.getSkillSayRestrictList().forEach(formRest -> {
            String messageTypeCode = formRest.getMessageTypeCode();
            Optional<SkillSayRestriction> optRestrict =
                    skillSayRestrictList.stream().filter(rest -> rest.getMessageTypeCode().equals(messageTypeCode)).findFirst();
            if (optRestrict.isPresent()) {
                formRest.setIsRestrict(true);
                formRest.setCount(optRestrict.get().getMessageMaxNum());
                formRest.setLength(optRestrict.get().getMessageMaxLength());
            } else {
                formRest.setIsRestrict(false);
                formRest.setCount(20);
                formRest.setLength(400);
            }
        });
        model.addAttribute("villageForm", form);
    }

    public boolean isEnoughCharacterNum(NewVillageForm villageForm) {
        int charaNum = charaBhv.selectCount(cb -> cb.query().setCharaGroupId_Equal(villageForm.getCharacterSetId()));
        return villageForm.getPersonMaxNum() <= charaNum;
    }

    // ?????????
    public Village createVillage(NewVillageForm villageForm, String userName) throws WerewolfMansionBusinessException {
        // ???
        Village village = insertVillage(villageForm, userName);
        // ?????????
        VillageSettings settings = insertVillageSettings(villageForm, village);
        // ????????????
        insertMessageRestrict(village.getVillageId(), villageForm);
        // ?????????
        insertVillageDay(village, 0, settings.getStartDatetime());
        // ???????????????????????????
        insertInitialMessage(villageForm, village);
        // ????????????????????????????????????
        joinDummyChara(villageForm, village);
        // tweet
        tweetNewVillage(villageForm, village.getVillageId());

        return village;
    }

    private void tweetNewVillage(NewVillageForm villageForm, Integer villageId) {
        String villageName = villageForm.getVillageName();
        if (StringUtils.isNotEmpty(villageForm.getJoinPassword())) { // ???????????????????????????
            return;
        }
        String startDatetime =
                LocalDateTime
                        .of(villageForm.getStartYear(), villageForm.getStartMonth(), villageForm.getStartDay(), villageForm.getStartHour(),
                                villageForm.getStartMinute(), 0)
                        .format(TIME_FORMAT);
        twitterLogic.tweet(String.format("???????????????????????????????????????\r\n?????????%s\r\n???????????????%s", villageName, startDatetime), villageId);
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    // ???????????????
    private void insertVillageDay(Village village, int day, LocalDateTime startDatetime) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(village.getVillageId());
        villageDay.setDay(day);
        villageDay.setDaychangeDatetime(startDatetime);
        villageDayBhv.insert(villageDay);
    }

    // ????????????????????????????????????
    private void insertInitialMessage(NewVillageForm villageForm, Village village) {
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(village.getVillageId(), 0)
                .content(messageSource.getMessage("newvillage.initial.message", null, Locale.JAPAN))
                .isConvertDisable(false)
                .build());
    }

    private Village insertVillage(NewVillageForm villageForm, String userName) {
        Village village = new Village();
        village.setVillageDisplayName(villageForm.getVillageName());
        village.setVillageStatusCode_?????????();
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
        settings.setIsAvailableAction(villageForm.getIsAvailableAction());
        settings.setOrganize(villageForm.getOrganization().replace("\r\n", "\n"));
        settings.setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay.codeOf(villageForm.getAllowedSecretSayCode()));
        villageSettingsBhv.insert(settings);
        return settings;
    }

    private void insertNormalSayRestriction(Integer villageId, NewVillageSayRestrictDto restrict) {
        NormalSayRestriction entity = new NormalSayRestriction();
        entity.setVillageId(villageId);
        entity.setMessageMaxNum(restrict.getCount());
        entity.setMessageMaxLength(restrict.getLength());
        entity.setMessageTypeCodeAsMessageType(CDef.MessageType.????????????);
        entity.setSkillCodeAsSkill(CDef.Skill.codeOf(restrict.getSkillCode()));
        normalSayRestrictionBhv.insert(entity);
    }

    private void insertSkillSayRestriction(Integer villageId, NewVillageSkillSayRestrictDto restrict) {
        SkillSayRestriction entity = new SkillSayRestriction();
        entity.setVillageId(villageId);
        entity.setMessageMaxNum(restrict.getCount());
        entity.setMessageMaxLength(restrict.getLength());
        entity.setMessageTypeCodeAsMessageType(CDef.MessageType.codeOf(restrict.getMessageTypeCode()));
        skillSayRestrictionBhv.insert(entity);
    }

    private void insertRpSayRestriction(Integer villageId, NewVillageRpSayRestrictDto restrict) {
        SkillSayRestriction entity = new SkillSayRestriction();
        entity.setVillageId(villageId);
        entity.setMessageMaxNum(restrict.getCount());
        entity.setMessageMaxLength(restrict.getLength());
        entity.setMessageTypeCodeAsMessageType(CDef.MessageType.codeOf(restrict.getMessageTypeCode()));
        skillSayRestrictionBhv.insert(entity);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private List<SelectOptionDto<Integer>> convertToCharacterSetList(ListResultBean<CharaGroup> charaGroupList) {
        return charaGroupList.mappingList(charaGroup -> {
            SelectOptionDto<Integer> option = new SelectOptionDto<Integer>();
            option.setName(String.format("%s (%s??????)", charaGroup.getCharaGroupName(), charaGroup.getDesigner().get().getDesignerName()));
            option.setValue(charaGroup.getCharaGroupId());
            return option;
        });
    }

    private void joinDummyChara(NewVillageForm villageForm, Village village) {
        int dummyPlayerId = 1;
        villageLogic.participate(village.getVillageId(), dummyPlayerId, villageForm.getDummyCharaId(), CDef.Skill.??????, CDef.Skill.??????,
                villageForm.getDummyJoinMessage(), false, false);
    }

    private LocalDateTime makeStartDateTime(NewVillageForm form) throws WerewolfMansionBusinessException {
        try {
            return LocalDateTime.of(form.getStartYear(), form.getStartMonth(), form.getStartDay(), form.getStartHour(),
                    form.getStartMinute());
        } catch (DateTimeException e) {
            throw new WerewolfMansionBusinessException("???????????????????????????");
        }
    }

    private void insertMessageRestrict(Integer villageId, NewVillageForm villageForm) {
        villageForm.getSayRestrictList().forEach(restrict -> {
            // ???????????????????????????????????????
            if (BooleanUtils.isFalse(restrict.getIsRestrict())) {
                return;
            }
            insertNormalSayRestriction(villageId, restrict);
        });
        villageForm.getSkillSayRestrictList().forEach(restrict -> {
            // ???????????????????????????????????????
            if (BooleanUtils.isFalse(restrict.getIsRestrict())) {
                return;
            }
            insertSkillSayRestriction(villageId, restrict);
        });
        villageForm.getRpSayRestrictList().forEach(restrict -> {
            // ???????????????????????????????????????
            if (!BooleanUtils.isTrue(restrict.getIsRestrict())) {
                return;
            }
            insertRpSayRestriction(villageId, restrict);
        });
    }
}
