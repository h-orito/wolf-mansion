package com.ort.app.web.controller.assist;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.controller.logic.MessageLogic;
import com.ort.app.web.controller.logic.VillageParticipateLogic;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.model.common.SelectOptionDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Component
public class NewVillageAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageSettingsBhv villageSettingsBhv;

    @Autowired
    private VillageDayBhv villageDayBhv;

    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private CharaGroupBhv charaGroupBhv;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private MessageLogic messageLogic;

    @Autowired
    private VillageParticipateLogic villageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void setIndexModel(NewVillageForm form, Model model) {
        if (form.getIsOpenVote() == null) {
            form.setIsOpenVote(true);
        }
        if (form.getIsPossibleSkillRequest() == null) {
            form.setIsPossibleSkillRequest(true);
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
        model.addAttribute("villageForm", form);

        // 現在の年
        LocalDate now = WerewolfMansionDateUtil.currentLocalDate();
        model.addAttribute("nowYear", now.getYear());

        ListResultBean<CharaGroup> charaGroupList = charaGroupBhv.selectList(cb -> cb.setupSelect_Designer());
        List<SelectOptionDto<Integer>> characterSetList = convertToCharacterSetList(charaGroupList);
        model.addAttribute("characterSetList", characterSetList);
    }

    // 村作成
    public Village createVillage(NewVillageForm villageForm, String userName) {
        // 村
        Village village = insertVillage(villageForm, userName);
        // 村設定
        VillageSettings settings = insertVillageSettings(villageForm, village);
        // 村日付
        insertVillageDay(village, 0, settings.getStartDatetime());
        // システムメッセージ
        insertInitialMessage(villageForm, village);
        // ダミーキャラを参加させる
        joinDummyChara(villageForm, village);

        return village;
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
        messageLogic.insertMessage( // 
                village.getVillageId(), // 村ID
                0, // day
                CDef.MessageType.公開システムメッセージ, // 発言種別
                messageSource.getMessage("newvillage.initial.message", null, Locale.JAPAN)); // メッセージ内容
    }

    private Village insertVillage(NewVillageForm villageForm, String userName) {
        Village village = new Village();
        village.setVillageDisplayName(villageForm.getVillageName());
        village.setVillageStatusCode_募集中();
        village.setCreatePlayerName(userName);
        villageBhv.insert(village);
        return village;
    }

    private VillageSettings insertVillageSettings(NewVillageForm villageForm, Village village) {
        VillageSettings settings = new VillageSettings();
        settings.setVillageId(village.getVillageId());
        settings.setStartPersonMinNum(villageForm.getStartPersonMinNum());
        settings.setPersonMaxNum(villageForm.getPersonMaxNum());
        settings.setDayChangeIntervalSeconds(villageForm.getDayChangeIntervalHours() * 3600 + villageForm.getDayChangeIntervalMinutes() * 60
                + villageForm.getDayChangeIntervalSeconds());
        settings.setStartDatetime(LocalDateTime.of(villageForm.getStartYear(), villageForm.getStartMonth(), villageForm.getStartDay(),
                villageForm.getStartHour(), villageForm.getStartMinute()));
        settings.setIsOpenVote(villageForm.getIsOpenVote());
        settings.setIsPossibleSkillRequest(villageForm.getIsPossibleSkillRequest());
        settings.setCharacterGroupId(villageForm.getCharacterSetId());
        villageSettingsBhv.insert(settings);
        return settings;
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
        Integer dummyCharaId = charaBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setCharaGroupId_Equal(villageForm.getCharacterSetId());
            cb.query().setIsDummy_Equal_True();
        }).getCharaId();
        villageLogic.participate(village.getVillageId(), dummyPlayerId, dummyCharaId, CDef.Skill.村人, villageForm.getDummyJoinMessage());
    }
}
