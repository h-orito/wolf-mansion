package com.ort.app.web.controller.assist;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageSettingsForm;
import com.ort.app.web.model.VillageSettingsResultContent;
import com.ort.app.web.model.inner.VillageSettingsDto;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionDateUtil;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageSettingsAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageSettingsBhv villageSettingsBhv;

    @Autowired
    private VillageDayBhv villageDayBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private VillageAssist villageAssist;

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
        if (startDateTime.isBefore(WerewolfMansionDateUtil.currentLocalDateTime())) {
            throw new WerewolfMansionBusinessException("開始日時を現在より過去にすることはできません");
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

        VillageSettings villageSettings = selectVillageSettings(villageId);
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
        settings.setIsOpenSkillInGrave(form.getIsOpenSkillInGrave());
        settings.setIsVisibleGraveSpectateMessage(form.getIsVisibleGraveSpectateMessage());
        settings.setIsAvailableSpectate(form.getIsAvailableSpectate());
        settings.setOrganize(form.getOrganization());
        villageSettingsBhv.update(settings);
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
    private VillageSettingsResultContent mappingToSettingsResultContent(VillageSettings settings, ListResultBean<VillageDay> dayList) {
        VillageSettingsResultContent content = new VillageSettingsResultContent();
        content.setVillageId(settings.getVillageId());
        content.setVillageName(settings.getVillage().get().getVillageDisplayName());
        content.setVillageSettings(convertToSettings(settings, dayList));
        return content;
    }

    private VillageSettingsDto convertToSettings(VillageSettings settings, ListResultBean<VillageDay> dayList) {
        // 変更不可項目はpartへ、変更可能項目はFormへ
        VillageSettingsDto part = new VillageSettingsDto();
        part.setCharaGroupId(settings.getCharacterGroupId());
        part.setCharaGroupName(settings.getCharaGroup().get().getCharaGroupName());
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
        ListResultBean<VillageDay> dayList = selectVillageDayList(villageId);
        VillageSettingsResultContent content = mappingToSettingsResultContent(settings, dayList);
        model.addAttribute("content", content);
        // 現在の年
        LocalDate now = WerewolfMansionDateUtil.currentLocalDate();
        model.addAttribute("nowYear", now.getYear());
        setVillageSettingsForm(form, settings, dayList, model);
    }

    // 村設定変更
    private void updateVillageSettings(Integer villageId, VillageSettingsForm form, UserInfo userInfo)
            throws WerewolfMansionBusinessException {
        validate(villageId, form, userInfo);
        updateSettings(villageId, form);
        updateVillageDay(villageId, form);
    }

    private void setVillageSettingsForm(VillageSettingsForm form, VillageSettings settings, ListResultBean<VillageDay> dayList,
            Model model) {
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
        settingsForm.setIsOpenSkillInGrave(settings.getIsOpenSkillInGrave());
        settingsForm.setIsVisibleGraveSpectateMessage(settings.getIsVisibleGraveSpectateMessage());
        settingsForm.setIsAvailableSpectate(settings.getIsAvailableSpectate());
        settingsForm.setOrganization(settings.getOrganize());
        model.addAttribute("settingsForm", settingsForm);
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
