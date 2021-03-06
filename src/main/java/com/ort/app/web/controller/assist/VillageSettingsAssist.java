package com.ort.app.web.controller.assist;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.util.SkillUtil;
import com.ort.app.web.controller.assist.impl.VillageForms;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.NewVillageRpSayRestrictDto;
import com.ort.app.web.form.NewVillageSayRestrictDto;
import com.ort.app.web.form.NewVillageSkillSayRestrictDto;
import com.ort.app.web.form.VillageSettingsForm;
import com.ort.app.web.model.VillageSettingsResultContent;
import com.ort.app.web.model.inner.VillageSettingsDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.NormalSayRestrictionBhv;
import com.ort.dbflute.exbhv.SkillSayRestrictionBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.NormalSayRestriction;
import com.ort.dbflute.exentity.SkillSayRestriction;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionDateUtil;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageSettingsAssist {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int DEFAULT_SAY_MAX_COUNT = 20;
    private static final int DEFAULT_SAY_MAX_LENGTH = 400;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageSettingsBhv villageSettingsBhv;
    @Autowired
    private NormalSayRestrictionBhv normalSayRestrictionBhv;
    @Autowired
    private SkillSayRestrictionBhv skillSayRestrictionBhv;
    @Autowired
    private VillageDayBhv villageDayBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private CharaBhv charaBhv;
    @Autowired
    private VillageAssist villageAssist;
    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // ????????????
    public String index(Integer villageId, Model model) {
        // ????????????????????????????????????NG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            // ????????????????????????
            return villageAssist.setIndexModelAndReturnView(villageId, VillageForms.empty(), model);
        }
        setVillageSettingsIndexModel(villageId, null, model);
        return "village-settings";
    }

    // ????????????
    public String updateSettings(Integer villageId, VillageSettingsForm form, BindingResult bindingResult, Model model) {
        // ????????????????????????????????????NG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            model.addAttribute("errorMessage", "???????????????????????????????????????");
            setVillageSettingsIndexModel(villageId, form, model);
            return "village-settings";
        }
        if (bindingResult.hasErrors()) {
            setVillageSettingsIndexModel(villageId, form, model);
            return "village-settings";
        }
        try {
            updateVillageSettings(villageId, form, userInfo);
            messageLogic.save(MessageEntity.publicSystemBuilder(villageId, 0).content("???????????????????????????????????????").build());
        } catch (WerewolfMansionBusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            setVillageSettingsIndexModel(villageId, form, model);
            return "village-settings";
        }
        return "redirect:/village/" + villageId + "#bottom";
    }

    // ===================================================================================
    //                                                                          Validation
    //                                                                          ==========
    private void validate(Integer villageId, VillageSettingsForm form, UserInfo userInfo) throws WerewolfMansionBusinessException {
        ListResultBean<VillageDay> dayList = selectVillageDayList(villageId);
        if (dayList.size() > 1) {
            // ?????????1????????????????????????
            throw new WerewolfMansionBusinessException("?????????????????????????????????????????????????????????????????????");
        }
        LocalDateTime startDateTime = makeStartDateTime(form);
        VillageSettings villageSettings = selectVillageSettings(villageId);
        if (startDateTime.isBefore(WerewolfMansionDateUtil.currentLocalDateTime())) {
            throw new WerewolfMansionBusinessException("??????????????????????????????????????????????????????????????????");
        }
        if (startDateTime.isAfter(villageSettings.getRegisterDatetime().plusDays(14L))) {
            throw new WerewolfMansionBusinessException("??????????????????????????????????????????14?????????????????????????????????");
        }
        ListResultBean<VillagePlayer> vPlayerList = villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
        });
        long participateCount = vPlayerList.stream().filter(vp -> vp.isIsSpectatorFalse()).count();
        if (form.getPersonMaxNum() < participateCount) {
            throw new WerewolfMansionBusinessException("???????????????????????????????????????????????????????????????????????????");
        }
        boolean existsSpectator = vPlayerList.stream().anyMatch(vp -> vp.isIsSpectatorTrue());
        if (existsSpectator && BooleanUtils.isFalse(form.getIsAvailableSpectate())) {
            throw new WerewolfMansionBusinessException("???????????????????????????????????????????????????????????????????????????????????????");
        }

        if (!"master".equals(userInfo.getUsername())
                && !userInfo.getUsername().equals(villageSettings.getVillage().get().getCreatePlayerName())) {
            throw new WerewolfMansionBusinessException("????????????????????????????????????????????????");
        }
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private VillageSettings selectVillageSettings(Integer villageId) {
        return villageSettingsBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_Village();
            cb.setupSelect_CharaGroup();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private ListResultBean<VillageDay> selectVillageDayList(Integer villageId) {
        return villageDayBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void updateSettings(Integer villageId, VillageSettingsForm form) throws WerewolfMansionBusinessException {
        VillageSettings settings = new VillageSettings();
        settings.setVillageId(villageId);
        settings.setStartPersonMinNum(form.getStartPersonMinNum());
        settings.setPersonMaxNum(form.getPersonMaxNum());
        settings.setDayChangeIntervalSeconds(
                form.getDayChangeIntervalHours() * 3600 + form.getDayChangeIntervalMinutes() * 60 + form.getDayChangeIntervalSeconds());
        settings.setStartDatetime(makeStartDateTime(form)); // ????????????????????????????????????????????????????????????????????????
        settings.setIsOpenVote(form.getIsOpenVote());
        settings.setIsAvailableSameWolfAttack(form.getIsAvailableSameWolfAttack());
        settings.setIsAvailableGuardSameTarget(form.getIsAvailableGuardSameTarget());
        settings.setIsAvailableCommit(form.getIsAvailableCommit());
        settings.setIsOpenSkillInGrave(form.getIsOpenSkillInGrave());
        settings.setIsVisibleGraveSpectateMessage(form.getIsVisibleGraveSpectateMessage());
        settings.setIsAvailableSpectate(form.getIsAvailableSpectate());
        settings.setIsAvailableSuddonlyDeath(form.getIsAvailableSuddonlyDeath());
        settings.setIsAvailableAction(form.getIsAvailableAction());
        settings.setOrganize(form.getOrganization());
        settings.setJoinPassword(form.getJoinPassword());
        settings.setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay.codeOf(form.getAllowedSecretSayCode()));
        villageSettingsBhv.update(settings);
    }

    private void updateMessageRestrictions(Integer villageId, VillageSettingsForm form) {
        normalSayRestrictionBhv.queryDelete(cb -> cb.query().setVillageId_Equal(villageId));
        skillSayRestrictionBhv.queryDelete(cb -> cb.query().setVillageId_Equal(villageId));
        form.getSayRestrictList().forEach(restrict -> {
            // ???????????????????????????????????????
            if (BooleanUtils.isFalse(restrict.getIsRestrict())) {
                return;
            }
            insertNormalSayRestriction(villageId, restrict);
        });
        form.getSkillSayRestrictList().forEach(restrict -> {
            // ???????????????????????????????????????
            if (BooleanUtils.isFalse(restrict.getIsRestrict())) {
                return;
            }
            insertSkillSayRestriction(villageId, restrict);
        });
        form.getRpSayRestrictList().forEach(restrict -> {
            // ???????????????????????????????????????
            if (BooleanUtils.isFalse(restrict.getIsRestrict())) {
                return;
            }
            insertRpSayRestriction(villageId, restrict);
        });

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

    private void updateVillageDay(Integer villageId, VillageSettingsForm form) throws WerewolfMansionBusinessException {
        VillageDay day = new VillageDay();
        day.setVillageId(villageId);
        day.setDay(0);
        day.setDaychangeDatetime(makeStartDateTime(form));
        villageDayBhv.update(day);
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageSettingsResultContent mappingToSettingsResultContent(VillageSettings settings, ListResultBean<VillageDay> dayList,
            Chara dummyChara) {
        VillageSettingsResultContent content = new VillageSettingsResultContent();
        content.setVillageId(settings.getVillageId());
        content.setVillageName(settings.getVillage().get().getVillageDisplayName());
        content.setVillageSettings(convertToSettings(settings, dayList, dummyChara));
        return content;
    }

    private VillageSettingsDto convertToSettings(VillageSettings settings, ListResultBean<VillageDay> dayList, Chara dummyChara) {
        // ?????????????????????part???????????????????????????Form???
        VillageSettingsDto part = new VillageSettingsDto();
        part.setCharaGroupId(settings.getCharacterGroupId());
        part.setCharaGroupName(settings.getCharaGroup().get().getCharaGroupName());
        part.setDummyCharaName(dummyChara.getCharaName());
        part.setSkillRequestType(BooleanUtils.isTrue(settings.getIsPossibleSkillRequest()) ? "??????" : "??????");
        part.setIsAvailableSpectate(settings.getIsAvailableSpectate());
        return part;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // ???????????????????????????
    private void setVillageSettingsIndexModel(Integer villageId, VillageSettingsForm form, Model model) {
        VillageSettings settings = selectVillageSettings(villageId);
        List<NormalSayRestriction> normalSayRestrictList =
                normalSayRestrictionBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        List<SkillSayRestriction> skillSayRestrictList = skillSayRestrictionBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        ListResultBean<VillageDay> dayList = selectVillageDayList(villageId);
        OptionalEntity<Chara> dummyChara = charaBhv.selectByPK(settings.getDummyCharaId());
        VillageSettingsResultContent content = mappingToSettingsResultContent(settings, dayList, dummyChara.get());
        model.addAttribute("content", content);
        // ???????????????
        model.addAttribute("skillListStr", SkillUtil.getSkillListStr());
        // ????????????
        LocalDate now = WerewolfMansionDateUtil.currentLocalDate();
        model.addAttribute("nowYear", now.getYear());
        setVillageSettingsForm(form, settings, normalSayRestrictList, skillSayRestrictList, dayList, model);
    }

    // ???????????????
    private void updateVillageSettings(Integer villageId, VillageSettingsForm form, UserInfo userInfo)
            throws WerewolfMansionBusinessException {
        validate(villageId, form, userInfo);
        updateSettings(villageId, form);
        updateMessageRestrictions(villageId, form);
        updateVillageDay(villageId, form);
    }

    private void setVillageSettingsForm(VillageSettingsForm form, VillageSettings settings,
            List<NormalSayRestriction> normalSayRestrictList, List<SkillSayRestriction> skillSayRestrictList,
            ListResultBean<VillageDay> dayList, Model model) {
        if (form != null) {
            model.addAttribute("settingsForm", form);
            return;
        }
        VillageSettingsForm settingsForm = new VillageSettingsForm();
        LocalDateTime daychangeDatetime = dayList.get(0).getDaychangeDatetime();
        settingsForm.setStartYear(daychangeDatetime.getYear());
        settingsForm.setStartMonth(daychangeDatetime.getMonthValue());
        settingsForm.setStartDay(daychangeDatetime.getDayOfMonth());
        settingsForm.setStartHour(daychangeDatetime.getHour());
        settingsForm.setStartMinute(daychangeDatetime.getMinute());
        settingsForm.setStartPersonMinNum(settings.getStartPersonMinNum());
        settingsForm.setPersonMaxNum(settings.getPersonMaxNum());
        Integer intervalSec = settings.getDayChangeIntervalSeconds();
        settingsForm.setDayChangeIntervalHours(intervalSec / 3600);
        settingsForm.setDayChangeIntervalMinutes((intervalSec % 3600) / 60);
        settingsForm.setDayChangeIntervalSeconds(intervalSec % 60);
        settingsForm.setIsOpenVote(settings.getIsOpenVote());
        settingsForm.setIsAvailableSameWolfAttack(settings.getIsAvailableSameWolfAttack());
        settingsForm.setIsAvailableGuardSameTarget(settings.getIsAvailableGuardSameTarget());
        settingsForm.setIsOpenSkillInGrave(settings.getIsOpenSkillInGrave());
        settingsForm.setIsVisibleGraveSpectateMessage(settings.getIsVisibleGraveSpectateMessage());
        settingsForm.setIsAvailableSuddonlyDeath(settings.getIsAvailableSuddonlyDeath());
        settingsForm.setIsAvailableCommit(settings.getIsAvailableCommit());
        settingsForm.setIsAvailableSpectate(settings.getIsAvailableSpectate());
        settingsForm.setIsAvailableAction(settings.getIsAvailableAction());
        settingsForm.setOrganization(settings.getOrganize());
        settingsForm.setJoinPassword(settings.getJoinPassword());
        settingsForm.setAllowedSecretSayCode(settings.getAllowedSecretSayCode());
        settingsForm.setSayRestrictList(createRestrictList(normalSayRestrictList));
        settingsForm.setSkillSayRestrictList(createSkillRestrictList(skillSayRestrictList));
        settingsForm.setRpSayRestrictList(createRpRestrictList(skillSayRestrictList));
        model.addAttribute("settingsForm", settingsForm);
    }

    private List<NewVillageSayRestrictDto> createRestrictList(List<NormalSayRestriction> restrictList) {
        return SkillUtil.SORTED_SKILL_LIST.stream().map(skill -> {
            NewVillageSayRestrictDto restrict = new NewVillageSayRestrictDto();
            restrict.setSkillName(skill.alias());
            restrict.setSkillCode(skill.code());
            Optional<NormalSayRestriction> optRes =
                    restrictList.stream().filter(res -> res.getSkillCode().equals(skill.code())).findFirst();
            if (optRes.isPresent()) {
                restrict.setIsRestrict(true);
                restrict.setCount(optRes.get().getMessageMaxNum());
                restrict.setLength(optRes.get().getMessageMaxLength());
            } else {
                restrict.setIsRestrict(false);
                restrict.setCount(DEFAULT_SAY_MAX_COUNT);
                restrict.setLength(DEFAULT_SAY_MAX_LENGTH);
            }
            return restrict;
        }).collect(Collectors.toList());
    }

    private List<NewVillageSkillSayRestrictDto> createSkillRestrictList(List<SkillSayRestriction> restrictList) {
        return Arrays.asList(CDef.MessageType.???????????????, CDef.MessageType.????????????, CDef.MessageType.????????????).stream().map(type -> {
            NewVillageSkillSayRestrictDto restrict = new NewVillageSkillSayRestrictDto();
            restrict.setMessageTypeName(type.alias());
            restrict.setMessageTypeCode(type.code());
            Optional<SkillSayRestriction> optRes =
                    restrictList.stream().filter(res -> res.getMessageTypeCode().equals(type.code())).findFirst();
            if (optRes.isPresent()) {
                restrict.setIsRestrict(true);
                restrict.setCount(optRes.get().getMessageMaxNum());
                restrict.setLength(optRes.get().getMessageMaxLength());
            } else {
                restrict.setIsRestrict(false);
                restrict.setCount(DEFAULT_SAY_MAX_COUNT);
                restrict.setLength(DEFAULT_SAY_MAX_LENGTH);
            }
            return restrict;
        }).collect(Collectors.toList());
    }

    private List<NewVillageRpSayRestrictDto> createRpRestrictList(List<SkillSayRestriction> restrictList) {
        return Arrays.asList(CDef.MessageType.???????????????).stream().map(type -> {
            NewVillageRpSayRestrictDto restrict = new NewVillageRpSayRestrictDto();
            restrict.setMessageTypeName(type.alias());
            restrict.setMessageTypeCode(type.code());
            Optional<SkillSayRestriction> optRes =
                    restrictList.stream().filter(res -> res.getMessageTypeCode().equals(type.code())).findFirst();
            if (optRes.isPresent()) {
                restrict.setIsRestrict(true);
                restrict.setCount(optRes.get().getMessageMaxNum());
                restrict.setLength(optRes.get().getMessageMaxLength());
            } else {
                restrict.setIsRestrict(false);
                restrict.setCount(DEFAULT_SAY_MAX_COUNT);
                restrict.setLength(DEFAULT_SAY_MAX_LENGTH);
            }
            return restrict;
        }).collect(Collectors.toList());
    }

    private LocalDateTime makeStartDateTime(VillageSettingsForm form) throws WerewolfMansionBusinessException {
        try {
            return LocalDateTime.of(form.getStartYear(), form.getStartMonth(), form.getStartDay(), form.getStartHour(),
                    form.getStartMinute());
        } catch (DateTimeException e) {
            throw new WerewolfMansionBusinessException("???????????????????????????");
        }
    }
}
