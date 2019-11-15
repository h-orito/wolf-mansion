package com.ort.app.web.controller.assist;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.NewVillageSayRestrictDetailDto;
import com.ort.app.web.form.NewVillageSayRestrictDto;
import com.ort.app.web.form.VillageSettingsForm;
import com.ort.app.web.model.VillageSettingsResultContent;
import com.ort.app.web.model.inner.VillageSettingsDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.MessageRestrictionBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.MessageRestriction;
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
    private static final List<CDef.Skill> RESTRICT_SKILLS = CDef.Skill.listAll().stream().filter(skill -> {
        return !skill.alias().contains("おまかせ");
    }).sorted((s1, s2) -> Integer.parseInt(s1.order()) - Integer.parseInt(s2.order())).collect(Collectors.toList());
    private static final int DEFAULT_SAY_MAX_COUNT = 20;
    private static final int DEFAULT_SAY_MAX_LENGTH = 400;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageSettingsBhv villageSettingsBhv;
    @Autowired
    private MessageRestrictionBhv messageRestrictionBhv;
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
    // 初期表示
    public String index(Integer villageId, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            // 最新の日付を表示
            return villageAssist.setIndexModelAndReturnView(villageId, null, null, null, model);
        }
        setVillageSettingsIndexModel(villageId, null, model);
        return "village-settings";
    }

    // 設定変更
    public String updateSettings(Integer villageId, VillageSettingsForm form, BindingResult bindingResult, Model model) {
        // ログインしていなかったらNG
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            model.addAttribute("errorMessage", "ログインし直してください。");
            setVillageSettingsIndexModel(villageId, form, model);
            return "village-settings";
        }
        if (bindingResult.hasErrors()) {
            setVillageSettingsIndexModel(villageId, form, model);
            return "village-settings";
        }
        try {
            updateVillageSettings(villageId, form, userInfo);
            messageLogic.insertMessage(villageId, 0, CDef.MessageType.公開システムメッセージ, "村の設定が変更されました。", true);
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
            // すでに1日目に入っている
            throw new WerewolfMansionBusinessException("既にプロローグが終了しているため変更できません");
        }
        LocalDateTime startDateTime = makeStartDateTime(form);
        VillageSettings villageSettings = selectVillageSettings(villageId);
        if (startDateTime.isBefore(WerewolfMansionDateUtil.currentLocalDateTime())) {
            throw new WerewolfMansionBusinessException("開始日時を現在より過去にすることはできません");
        }
        if (startDateTime.isAfter(villageSettings.getRegisterDatetime().plusDays(14L))) {
            throw new WerewolfMansionBusinessException("開始日時は最初に建てた日時の14日後以降にはできません");
        }
        ListResultBean<VillagePlayer> vPlayerList = villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
        });
        long participateCount = vPlayerList.stream().filter(vp -> vp.isIsSpectatorFalse()).count();
        if (form.getPersonMaxNum() < participateCount) {
            throw new WerewolfMansionBusinessException("定員は既に入村済みの人数未満にすることはできません");
        }
        boolean existsSpectator = vPlayerList.stream().anyMatch(vp -> vp.isIsSpectatorTrue());
        if (existsSpectator && BooleanUtils.isFalse(form.getIsAvailableSpectate())) {
            throw new WerewolfMansionBusinessException("見学者が既にいるため、見学入村を不可にすることはできません");
        }

        if (!"master".equals(userInfo.getUsername())
                && !userInfo.getUsername().equals(villageSettings.getVillage().get().getCreatePlayerName())) {
            throw new WerewolfMansionBusinessException("村作成者しか使用できない機能です");
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

    private List<MessageRestriction> selectMessageRestrictList(Integer villageId) {
        return messageRestrictionBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
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
        settings.setStartDatetime(makeStartDateTime(form)); // この項目は更新しても意味がないが一応更新しておく
        settings.setIsOpenVote(form.getIsOpenVote());
        settings.setIsAvailableSameWolfAttack(form.getIsAvailableSameWolfAttack());
        settings.setIsAvailableGuardSameTarget(form.getIsAvailableGuardSameTarget());
        settings.setIsAvailableCommit(form.getIsAvailableCommit());
        settings.setIsOpenSkillInGrave(form.getIsOpenSkillInGrave());
        settings.setIsVisibleGraveSpectateMessage(form.getIsVisibleGraveSpectateMessage());
        settings.setIsAvailableSpectate(form.getIsAvailableSpectate());
        settings.setIsAvailableSuddonlyDeath(form.getIsAvailableSuddonlyDeath());
        settings.setOrganize(form.getOrganization());
        settings.setJoinPassword(form.getJoinPassword());
        settings.setAllowedSecretSayCodeAsAllowedSecretSay(CDef.AllowedSecretSay.codeOf(form.getAllowedSecretSayCode()));
        villageSettingsBhv.update(settings);
    }

    private void updateMessageRestrictions(Integer villageId, VillageSettingsForm form) {
        messageRestrictionBhv.queryDelete(cb -> cb.query().setVillageId_Equal(villageId));
        form.getSayRestrictList().forEach(restrict -> {
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

    private void insertMessageRestriction(Integer villageId, String skillCode, NewVillageSayRestrictDetailDto detail) {
        MessageRestriction entity = new MessageRestriction();
        entity.setVillageId(villageId);
        entity.setMessageMaxNum(detail.getCount());
        entity.setMessageMaxLength(detail.getLength());
        entity.setMessageTypeCodeAsMessageType(CDef.MessageType.codeOf(detail.getMessageTypeCode()));
        entity.setSkillCodeAsSkill(CDef.Skill.codeOf(skillCode));
        messageRestrictionBhv.insert(entity);
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
        // 変更不可項目はpartへ、変更可能項目はFormへ
        VillageSettingsDto part = new VillageSettingsDto();
        part.setCharaGroupId(settings.getCharacterGroupId());
        part.setCharaGroupName(settings.getCharaGroup().get().getCharaGroupName());
        part.setDummyCharaName(dummyChara.getCharaName());
        part.setSkillRequestType(BooleanUtils.isTrue(settings.getIsPossibleSkillRequest()) ? "有効" : "無効");
        part.setIsAvailableSpectate(settings.getIsAvailableSpectate());
        return part;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // 村設定変更画面作成
    private void setVillageSettingsIndexModel(Integer villageId, VillageSettingsForm form, Model model) {
        VillageSettings settings = selectVillageSettings(villageId);
        List<MessageRestriction> restrictList = selectMessageRestrictList(villageId);
        ListResultBean<VillageDay> dayList = selectVillageDayList(villageId);
        OptionalEntity<Chara> dummyChara = charaBhv.selectByPK(settings.getDummyCharaId());
        VillageSettingsResultContent content = mappingToSettingsResultContent(settings, dayList, dummyChara.get());
        model.addAttribute("content", content);
        // 現在の年
        LocalDate now = WerewolfMansionDateUtil.currentLocalDate();
        model.addAttribute("nowYear", now.getYear());
        setVillageSettingsForm(form, settings, restrictList, dayList, model);
    }

    // 村設定変更
    private void updateVillageSettings(Integer villageId, VillageSettingsForm form, UserInfo userInfo)
            throws WerewolfMansionBusinessException {
        validate(villageId, form, userInfo);
        updateSettings(villageId, form);
        updateMessageRestrictions(villageId, form);
        updateVillageDay(villageId, form);
    }

    private void setVillageSettingsForm(VillageSettingsForm form, VillageSettings settings, List<MessageRestriction> restrictList,
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
        settingsForm.setOrganization(settings.getOrganize());
        settingsForm.setJoinPassword(settings.getJoinPassword());
        settingsForm.setAllowedSecretSayCode(settings.getAllowedSecretSayCode());
        settingsForm.setSayRestrictList(createRestrictList(restrictList));
        model.addAttribute("settingsForm", settingsForm);
    }

    private List<NewVillageSayRestrictDto> createRestrictList(List<MessageRestriction> restrictList) {
        return RESTRICT_SKILLS.stream().map(skill -> {
            NewVillageSayRestrictDto restrict = new NewVillageSayRestrictDto();
            restrict.setSkillName(skill.name());
            restrict.setSkillCode(skill.code());
            restrict.setDetailList(createDetailList(skill, restrictList));
            return restrict;
        }).collect(Collectors.toList());
    }

    private List<NewVillageSayRestrictDetailDto> createDetailList(Skill skill, List<MessageRestriction> restrictList) {
        List<NewVillageSayRestrictDetailDto> detailList = new ArrayList<>();
        detailList.add(createDetail("通常発言", CDef.MessageType.通常発言.code(), skill, restrictList));
        if (skill.isAvailableWerewolfSay()) {
            detailList.add(createDetail("囁き", CDef.MessageType.人狼の囁き.code(), skill, restrictList));
        } else if (skill == CDef.Skill.共鳴者) {
            detailList.add(createDetail("共鳴", CDef.MessageType.共鳴発言.code(), skill, restrictList));
        }
        return detailList;
    }

    private NewVillageSayRestrictDetailDto createDetail(String messageTypeName, String messageTypeCode, Skill skill,
            List<MessageRestriction> restrictList) {
        NewVillageSayRestrictDetailDto detail = new NewVillageSayRestrictDetailDto();
        detail.setMessageTypeName(messageTypeName);
        detail.setMessageTypeCode(messageTypeCode);
        Optional<MessageRestriction> optDbRes = restrictList.stream()
                .filter(dbRes -> dbRes.getMessageTypeCode().equals(messageTypeCode) && dbRes.getSkillCode().equals(skill.code()))
                .findFirst();
        if (optDbRes.isPresent()) {
            detail.setIsRestrict(true);
            detail.setCount(optDbRes.get().getMessageMaxNum());
            detail.setLength(optDbRes.get().getMessageMaxLength());
        } else {
            detail.setIsRestrict(false);
            detail.setCount(DEFAULT_SAY_MAX_COUNT);
            detail.setLength(DEFAULT_SAY_MAX_LENGTH);
        }
        return detail;
    }

    private LocalDateTime makeStartDateTime(VillageSettingsForm form) throws WerewolfMansionBusinessException {
        try {
            return LocalDateTime.of(form.getStartYear(), form.getStartMonth(), form.getStartDay(), form.getStartHour(),
                    form.getStartMinute());
        } catch (DateTimeException e) {
            throw new WerewolfMansionBusinessException("存在しない日付です");
        }
    }
}
