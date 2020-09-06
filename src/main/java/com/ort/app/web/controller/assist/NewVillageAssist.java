package com.ort.app.web.controller.assist;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
import com.ort.app.util.SkillUtil;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.NewVillageForm;
import com.ort.app.web.form.NewVillageSayRestrictDetailDto;
import com.ort.app.web.model.common.SelectOptionDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
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
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");

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

        // 役職リスト
        model.addAttribute("skillListStr", SkillUtil.getSkillListStr());

        // 現在の年
        LocalDate now = WerewolfMansionDateUtil.currentLocalDate();
        model.addAttribute("nowYear", now.getYear());

        ListResultBean<CharaGroup> charaGroupList = charaGroupBhv.selectList(cb -> cb.setupSelect_Designer());
        List<SelectOptionDto<Integer>> characterSetList = convertToCharacterSetList(charaGroupList);
        model.addAttribute("characterSetList", characterSetList);
    }

    public boolean isEnoughCharacterNum(NewVillageForm villageForm) {
        int charaNum = charaBhv.selectCount(cb -> cb.query().setCharaGroupId_Equal(villageForm.getCharacterSetId()));
        return villageForm.getPersonMaxNum() <= charaNum;
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
