package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.model.common.SelectOptionDto;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
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
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private CharaGroupBhv charaGroupBhv;

    @Autowired
    private MessageBhv messageBhv;

    @Autowired
    private MessageSource messageSource;

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

        ListResultBean<CharaGroup> charaGroupList = charaGroupBhv.selectList(cb -> cb.setupSelect_Designer());
        List<SelectOptionDto<Integer>> characterSetList = convertToCharacterSetList(charaGroupList);
        model.addAttribute("characterSetList", characterSetList);
    }

    // 村作成
    public Village createVillage(NewVillageForm villageForm, Locale locale) {
        // 村
        Village village = insertVillage(villageForm);
        // 村設定
        insertVillageSettings(villageForm, village);
        // 村日付
        insertVillageDay(village, 0);
        // システムメッセージ
        insertInitialMessage(villageForm, village, locale);
        // ダミーキャラを参加させる
        joinDummyChara(villageForm, village);

        return village;
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    // 村日付登録
    private void insertVillageDay(Village village, int day) {
        VillageDay villageDay = new VillageDay();
        villageDay.setVillageId(village.getVillageId());
        villageDay.setDay(day);
        villageDayBhv.insert(villageDay);
    }

    // 村建て初期メッセージ登録
    private void insertInitialMessage(NewVillageForm villageForm, Village village, Locale locale) {
        Message message = new Message();
        message.setVillageId(village.getVillageId());
        message.setDay(0);
        message.setMessageTypeCode_公開システムメッセージ();
        message.setMessageNumber(0); // TODO h-orito 発番ロジック (2017/12/22)
        message.setMessageContent(messageSource.getMessage("newvillage.initial.message", null, locale));
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        messageBhv.insert(message);
    }

    private Village insertVillage(NewVillageForm villageForm) {
        Village village = new Village();
        village.setVillageDisplayName(villageForm.getVillageName());
        village.setVillageStatusCode_募集中();
        villageBhv.insert(village);
        return village;
    }

    private void insertVillageSettings(NewVillageForm villageForm, Village village) {
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
    }

    private void insertDummyCharaMessage(NewVillageForm villageForm, Village village, VillagePlayer dummyVillagePlayer) {
        Message message = new Message();
        message.setVillageId(village.getVillageId());
        message.setVillagePlayerId(dummyVillagePlayer.getVillagePlayerId());
        message.setDay(0);
        message.setMessageTypeCode_通常発言();
        message.setMessageNumber(0); // TODO h-orito 発番ロジック (2017/12/22)
        message.setMessageContent(villageForm.getDummyJoinMessage());
        message.setMessageDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        messageBhv.insert(message);
    }

    private VillagePlayer insertDummyVillagePlayer(NewVillageForm villageForm, Village village) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillageId(village.getVillageId());
        villagePlayer.setPlayerId(1); // ダミーキャラのプレイヤーID
        Integer dummyCharaId = charaBhv.selectEntityWithDeletedCheck(cb -> {
            cb.query().setCharaGroupId_Equal(villageForm.getCharacterSetId());
            cb.query().setIsDummy_Equal_True();
        }).getCharaId();
        villagePlayer.setCharaId(dummyCharaId);
        villagePlayer.setIsDead_False();
        villagePlayerBhv.insert(villagePlayer);
        return villagePlayer;
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
        // 参加させる
        VillagePlayer dummyVillagePlayer = insertDummyVillagePlayer(villageForm, village);
        // 参加発言
        insertDummyCharaMessage(villageForm, village, dummyVillagePlayer);
    }
}
