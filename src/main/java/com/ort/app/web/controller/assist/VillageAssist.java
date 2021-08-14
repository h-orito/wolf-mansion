package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.datasource.AbilityService;
import com.ort.app.datasource.FootstepService;
import com.ort.app.datasource.VillageService;
import com.ort.app.datasource.VoteService;
import com.ort.app.logic.AbilityLogic;
import com.ort.app.logic.CommitLogic;
import com.ort.app.logic.ParticipateLogic;
import com.ort.app.logic.SayLogic;
import com.ort.app.logic.SkillRequestLogic;
import com.ort.app.logic.VillageDispLogic;
import com.ort.app.logic.VoteLogic;
import com.ort.app.util.SkillUtil;
import com.ort.app.web.controller.assist.impl.VillageForms;
import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.form.NewVillageRandomOrgCampDto;
import com.ort.app.web.form.NewVillageRandomOrgSkillDto;
import com.ort.app.web.form.NewVillageRpSayRestrictDto;
import com.ort.app.web.form.NewVillageSayRestrictDto;
import com.ort.app.web.form.NewVillageSkillSayRestrictDto;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageActionForm;
import com.ort.app.web.form.VillageChangeNameForm;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageCommitForm;
import com.ort.app.web.form.VillageKickForm;
import com.ort.app.web.form.VillageLeaveForm;
import com.ort.app.web.form.VillageMemoForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.model.OptionDto;
import com.ort.app.web.model.VillageMemberVoteDto;
import com.ort.app.web.model.VillageResultContent;
import com.ort.app.web.model.VillageVoteDto;
import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageMemberDetailDto;
import com.ort.app.web.model.inner.VillageMemberDto;
import com.ort.app.web.model.inner.VillageRoomAssignedDto;
import com.ort.app.web.model.inner.VillageRoomAssignedRowDto;
import com.ort.app.web.model.inner.VillageSkillDto;
import com.ort.app.web.model.inner.village.VillageAbilityFormDto;
import com.ort.app.web.model.inner.village.VillageActionFormDto;
import com.ort.app.web.model.inner.village.VillageChangeNameFormDto;
import com.ort.app.web.model.inner.village.VillageCommitFormDto;
import com.ort.app.web.model.inner.village.VillageFormDto;
import com.ort.app.web.model.inner.village.VillageMemoFormDto;
import com.ort.app.web.model.inner.village.VillageParticipateDto;
import com.ort.app.web.model.inner.village.VillageParticipateFormDto;
import com.ort.app.web.model.inner.village.VillageParticipateSkillDto;
import com.ort.app.web.model.inner.village.VillagePlayerStatusDto;
import com.ort.app.web.model.inner.village.VillageSayFormDto;
import com.ort.app.web.model.inner.village.VillageSettingsDto;
import com.ort.app.web.model.inner.village.VillageVoteFormDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exbhv.CommitBhv;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.CampAllocation;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.Commit;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.NormalSayRestriction;
import com.ort.dbflute.exentity.RandomKeyword;
import com.ort.dbflute.exentity.SkillAllocation;
import com.ort.dbflute.exentity.SkillSayRestriction;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Vote;
import com.ort.dbflute.exentity.Votes;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WolfMansionUserInfoUtil;

@Component
public class VillageAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageSituationAssist villageSituationAssist;
    @Autowired
    private CommitBhv commitBhv;
    @Autowired
    private RandomKeywordBhv randomKeywordBhv;
    @Autowired
    private VillageDispLogic villageDispLogic;
    @Autowired
    private SayLogic sayLogic;
    @Autowired
    private AbilityLogic abilityLogic;
    @Autowired
    private VoteLogic voteLogic;
    @Autowired
    private ParticipateLogic participateLogic;
    @Autowired
    private SkillRequestLogic skillRequestLogic;
    @Autowired
    private CommitLogic commitLogic;
    @Autowired
    private VillageService villageService;
    @Autowired
    private FootstepService footstepService;
    @Autowired
    private AbilityService abilityService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private CharaGroupBhv charaGroupBhv;

    @Value("${app.debug}")
    private Boolean debug;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String setIndexModelAndReturnView( //
            Integer villageId, //
            VillageForms forms, //
            Model model //
    ) {
        // 最新の日付取得
        int day = villageService.selectLatestDay(villageId);
        return setIndexModel(villageId, day, forms, model);
    }

    public String setIndexModel(//
            Integer villageId, //
            int day, //
            VillageForms forms, //
            Model model //
    ) {
        // 村の初期表示に必要な情報を収集
        VillageInfo villageInfo = selectVillageInfo(villageId, day);
        // 表示用情報
        VillageResultContent content = new VillageResultContent();
        // 参加していなくても見られる情報
        setVillageModelBasicInfo(content, villageInfo);
        // 参加している場合のみ見られる情報
        setVillageModelForm(content, villageInfo, forms, model);
        // 村建てのみ見られる情報
        setVillageModelCreateUser(content, villageInfo, model);
        // デバッグ用
        setDebugInfo(debug, model);

        model.addAttribute("content", content);
        return "village";
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private String selectRandomKeywords() {
        return String.join(",", randomKeywordBhv.selectList(cb -> {}).stream().map(RandomKeyword::getKeyword).collect(Collectors.toList()));
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private VillageInfo selectVillageInfo(Integer villageId, int day) {
        Village village = villageService.selectVillage(villageId, false, true);
        UserInfo userInfo = WolfMansionUserInfoUtil.getUserInfo(); // ログインしているか
        Optional<VillagePlayer> optVillagePlayer = villageService.selectVillagePlayer(villageId, userInfo, true);
        Votes votes = voteService.selectVotes(villageId);
        Footsteps footsteps = footstepService.selectFootsteps(villageId);
        Abilities abilities = abilityService.selectAbilities(villageId);
        VillageInfo villageInfo = new VillageInfo(village, userInfo, optVillagePlayer, day, votes, footsteps, abilities);
        return villageInfo;
    }

    private void setAbilityTarget(VillageInfo villageInfo, Model model) {
        if (!abilityLogic.isAbilityUsable(villageInfo.village, villageInfo.optVillagePlayer, villageInfo.day)) {
            return;
        }
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        CDef.AbilityType type = SkillUtil.SKILL_ABILITY_TYPE_MAP.get(villagePlayer.getSkillCodeAsSkill());
        Optional<Ability> optAbility = villageInfo.abilities.findTodayAbility(villageInfo.day, type, villagePlayer.getCharaId());
        VillageAbilityForm abilityForm = new VillageAbilityForm();
        if (optAbility.isPresent()) {
            Ability ab = optAbility.get();
            abilityForm.setCharaId(ab.getCharaId());
            model.addAttribute("charaName", villageInfo.vPlayers.findByCharaId(ab.getCharaId()).name());
            abilityForm.setTargetCharaId(ab.getTargetCharaId());
            if (ab.getTargetCharaId() != null) {
                model.addAttribute("targetCharaName", villageInfo.vPlayers.findByCharaId(ab.getTargetCharaId()).name());
            }
            abilityForm.setFootstep(ab.getTargetFootstep());
            model.addAttribute("footstep", ab.getTargetFootstep());
        }

        villageInfo.footsteps.filterToday(villageInfo.day)
                .filterByChara(optAbility.map(a -> a.getCharaId()).orElse(villagePlayer.getCharaId())) //
                        .list.stream().findFirst().ifPresent(fs -> {
                            abilityForm.setFootstep(fs.getFootstepRoomNumbers());
                            model.addAttribute("footstep", fs.getFootstepRoomNumbers());
                        });

        model.addAttribute("abilityForm", abilityForm);
    }

    private void setVoteTarget(VillageInfo villageInfo, Model model) {
        int day = villageInfo.day;
        Optional<VillagePlayer> optVillagePlayer = villageInfo.optVillagePlayer;
        if (!voteLogic.isAvailableVote(villageInfo.village, optVillagePlayer, day)) {
            return;
        }

        Optional<Vote> optVote = villageInfo.votes.findTodayVote(day, optVillagePlayer.get().getCharaId());
        VillageVoteForm form = new VillageVoteForm();
        form.setTargetCharaId(optVote.map(vote -> vote.getVoteCharaId()).orElse(null));
        model.addAttribute("voteForm", form);
        model.addAttribute("voteTarget",
                optVote.map(vote -> villageInfo.vPlayers.findByCharaId(vote.getVoteCharaId()).name()).orElse(null));
    }

    // 基本的な情報、参加有無に関わらない情報
    private void setVillageModelBasicInfo(VillageResultContent content, VillageInfo villageInfo) {
        content.setVillageId(villageInfo.village.getVillageId());
        content.setVillageNumber(villageInfo.village.getVillageNumber());
        content.setVillageName(villageInfo.village.getVillageDisplayName());
        content.setVillageStatusCode(villageInfo.village.getVillageStatusCode());
        content.setMemberList(convertToMemberPart(villageInfo.vPlayers));
        content.setCharacterList(extractAndSortCharacterList(villageInfo));
        content.setRoomAssignedRowList(convertToRoomAssignedPart(villageInfo));
        content.setRoomWidth(villageInfo.village.getRoomSizeWidth());
        content.setDay(villageInfo.day);
        content.setDayList(villageInfo.days.map(VillageDay::getDay));
        content.setEpilogueDay(villageInfo.village.getEpilogueDay());
        content.setSettings(convertToSettings(villageInfo));
        content.setDayChangeDatetime(makeDayChangeDatetime(villageInfo));
        content.setIsDispUnspoiler(villageInfo.village.isVillageStatusCodeエピローグ() || villageInfo.village.isVillageStatusCode終了());
        content.setRandomKeywords(selectRandomKeywords());
        if (villageInfo.day > 2) {
            content.setVote(makeMemberVote(villageInfo));
        }
        if (villageInfo.isStartedFootstepSet()) {
            content.setVillageFootstepList(villageSituationAssist.makeFootstepList(villageInfo));
            content.setSituationList(villageSituationAssist.makeSituationList(villageInfo));
            content.setIsDispSpoilerContent(villageDispLogic.isDispSpoilerContent(villageInfo));
        }
    }

    private List<OptionDto> extractAndSortCharacterList(VillageInfo villageInfo) {
        // 参加→見学 それぞれの中ではキャラID順
        return villageInfo.vPlayers.list.stream()
                .sorted(Comparator.comparing(VillagePlayer::isIsSpectatorTrue) //
                        .thenComparingInt(VillagePlayer::getCharaId))
                .map(vp -> new OptionDto(vp))
                .collect(Collectors.toList());
    }

    // 更新時刻
    private LocalDateTime makeDayChangeDatetime(VillageInfo villageInfo) {
        if (villageInfo.village.isVillageStatusCode廃村() || villageInfo.village.isVillageStatusCode終了()) {
            return null;
        }
        return villageInfo.days.latestDay().getDaychangeDatetime();
    }

    // 参加している場合に使う情報
    private void setVillageModelForm(//
            VillageResultContent content, //
            VillageInfo villageInfo, //
            VillageForms forms, //
            Model model //
    ) {
        // 各種フォーム絡みの情報
        content.setForm(convertToVillageForm(villageInfo, forms, model));
        // 参加している自分自身の情報
        content.setMyself(convertToMyself(villageInfo));
    }

    // 各種フォーム絡みの情報
    private VillageFormDto convertToVillageForm(//
            VillageInfo villageInfo, //
            VillageForms forms, //
            Model model //
    ) {
        VillageFormDto formDto = new VillageFormDto();

        // 参戦
        // 役職希望変更
        // 退村
        formDto.setParticipate(convertToParticipateForm(villageInfo));
        setParticipateForm(model, formDto.getParticipate(), villageInfo.optVillagePlayer, forms);

        // コミット
        formDto.setCommit(convertToCommitForm(villageInfo));
        model.addAttribute("commitForm", createCommitForm(formDto.getCommit().getIsDispCommitForm(), villageInfo));

        // 発言
        formDto.setSay(convertToSayForm(villageInfo));
        model.addAttribute("sayForm", createSayForm(formDto.getSay(), villageInfo, forms.sayForm));

        // アクション
        formDto.setAction(convertToActionForm(villageInfo));
        model.addAttribute("actionForm", createActionForm(formDto.getAction(), villageInfo, forms.actionForm));

        // 名前変更
        formDto.setChangeName(convertToChangeNameForm(villageInfo));
        model.addAttribute("changeNameForm", createChangeNameForm(formDto.getChangeName(), villageInfo, forms.changeNameForm));

        // 簡易メモ変更
        formDto.setMemo(convertToMemoForm(villageInfo));
        model.addAttribute("memoForm", createMemoForm(formDto.getMemo(), villageInfo, forms.memoForm));

        // 能力行使
        formDto.setAbility(convertToAbilityForm(villageInfo));
        setAbilityTarget(villageInfo, model);

        // 投票
        formDto.setVote(convertToVoteForm(villageInfo));
        setVoteTarget(villageInfo, model);

        return formDto;
    }

    // 投票
    private VillageVoteFormDto convertToVoteForm(VillageInfo villageInfo) {
        VillageVoteFormDto vote = new VillageVoteFormDto();
        vote.setVoteTargetList(voteLogic.getSelectableVoteTargetList(villageInfo));
        return vote;
    }

    private VillageAbilityFormDto convertToAbilityForm(VillageInfo villageInfo) {
        VillageAbilityFormDto ability = new VillageAbilityFormDto();
        ability.setAbilityTargetList(abilityLogic.getSelectableTargetList(villageInfo));
        ability.setAttackerList(abilityLogic.getSelectableAttackerList(villageInfo));
        ability.setSkillHistoryList(abilityLogic.makeSkillHistoryList(villageInfo));
        ability.setWerewolfCharaNameList(abilityLogic.createWerewolfCharaNameList(villageInfo));
        ability.setcMadmanCharaNameList(abilityLogic.createCMadmanCharaNameList(villageInfo));
        ability.setFoxCharaNameList(abilityLogic.createFoxCharaNameList(villageInfo));
        ability.setLoversCharaNameList(abilityLogic.createLoversCharaNameList(villageInfo));
        ability.setTargetPrefixMessage(abilityLogic.makeTargetPrefixMessage(villageInfo));
        ability.setTargetSuffixMessage(abilityLogic.makeTargetSuffixMessage(villageInfo));
        ability.setIsTargetingAndFootstep(abilityLogic.isTargetingAndFootstep(villageInfo));
        ability.setStatusList(createStatusList(villageInfo));
        return ability;
    }

    // 発言
    private VillageSayForm createSayForm(VillageSayFormDto sayFormDto, VillageInfo villageInfo, VillageSayForm sayForm) {
        if (sayForm != null) {
            return sayForm;
        }
        if (!sayFormDto.getIsDispSayForm()) {
            return null;
        }
        VillageSayForm form = new VillageSayForm();
        Optional<CDef.MessageType> defaultMessageType = sayLogic.detectDefaultMessageType(villageInfo);
        defaultMessageType.ifPresent(messageType -> {
            form.setMessageType(messageType.code());
            form.setFaceType(villageInfo.optVillagePlayer.get().detectDefaultFaceType(messageType).code());
        });
        return form;
    }

    // アクション
    private VillageActionForm createActionForm(VillageActionFormDto actionFormDto, VillageInfo villageInfo, VillageActionForm actionForm) {
        if (actionForm != null) {
            return actionForm;
        }
        if (!actionFormDto.getIsDispActionForm()) {
            return null;
        }
        VillageActionForm form = new VillageActionForm();
        form.setMyself(villageInfo.optVillagePlayer.get().name() + "は、");
        return form;
    }

    // 名前変更
    private VillageChangeNameForm createChangeNameForm(VillageChangeNameFormDto changeNameFormDto, VillageInfo villageInfo,
            VillageChangeNameForm changeNameForm) {
        if (changeNameForm != null) {
            return changeNameForm;
        }
        if (!changeNameFormDto.getIsDispChangeNameForm()) {
            return null;
        }
        VillageChangeNameForm form = new VillageChangeNameForm();
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        form.setName(villagePlayer.getCharaName());
        form.setShortName(villagePlayer.getCharaShortName());
        return form;
    }

    // 簡易メモ
    private VillageMemoForm createMemoForm(VillageMemoFormDto memoFormDto, VillageInfo villageInfo, VillageMemoForm memoForm) {
        if (memoForm != null) {
            return memoForm;
        }
        if (!memoFormDto.getIsDispMemoForm()) {
            return null;
        }
        VillageMemoForm form = new VillageMemoForm();
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        form.setMemo(villagePlayer.getMemo());
        return form;
    }

    // 発言
    private VillageSayFormDto convertToSayForm(VillageInfo villageInfo) {
        VillageSayFormDto say = new VillageSayFormDto();
        boolean isDispSayForm = sayLogic.isAvailableSay(villageInfo);
        if (!isDispSayForm) {
            say.setIsDispSayForm(false);
            say.setAllSayTypeAvailable(false);
            return say;
        }
        say.setRestrict(sayLogic.makeRestrict(villageInfo));
        say.setFaceTypeList(isDispSayForm ? makeFaceTypeCodeList(villageInfo) : null);
        say.setIsDispSayForm(true);

        // 管理者は全て発言できる
        if (isDispSayForm && villageInfo.isAdmin()) {
            say.setAllSayTypeAvailable(true);
            say.setSecretSayTargetList(sayLogic.getSelectableSecretSayTarget(villageInfo));
            return say;
        }
        say.setIsAvailableNormalSay(sayLogic.isAvailableNormalSay(villageInfo)); // 通常発言可能か
        say.setIsAvailableWerewolfSay(sayLogic.isAvailableWerewolfSay(villageInfo)); // 囁き可能か
        say.setIsAvailableMasonSay(sayLogic.isAvailableMasonSay(villageInfo)); // 共鳴者発言可能か
        say.setIsAvailableLoversSay(sayLogic.isAvailableLoversSay(villageInfo)); // 恋人発言可能か
        say.setIsAvailableGraveSay(sayLogic.isAvailableGraveSay(villageInfo)); // 死者の呻きが発言可能か
        say.setIsAvailableSpectateSay(sayLogic.isAvailableSpectateSay(villageInfo)); // 見学発言が発言可能か
        say.setIsAvailableMonologueSay(sayLogic.isAvailableMonologueSay(villageInfo)); // 独り言が発言可能か
        say.setIsAvailableAction(sayLogic.isAvailableAction(villageInfo)); // アクションが発言可能か
        // 秘話
        say.setIsAvailableSecretSay(sayLogic.isAvailableSecretSay(villageInfo));
        say.setSecretSayTargetList(sayLogic.getSelectableSecretSayTarget(villageInfo));
        return say;
    }

    // アクション
    private VillageActionFormDto convertToActionForm(VillageInfo villageInfo) {
        VillageActionFormDto action = new VillageActionFormDto();
        boolean isDispActionForm = sayLogic.isAvailableAction(villageInfo);
        action.setIsDispActionForm(isDispActionForm);
        if (isDispActionForm) {
            List<OptionDto> targetList = villageInfo.vPlayers.list.stream()
                    .filter(vp -> !vp.getVillagePlayerId().equals(villageInfo.optVillagePlayer.get().getVillagePlayerId()))
                    .sorted(Comparator.comparing(VillagePlayer::isIsSpectatorTrue) //
                            .thenComparingInt(vp -> vp.getRoomNumber() != null ? vp.getRoomNumber() : vp.getCharaId())
                            .thenComparingInt(VillagePlayer::getCharaId))
                    .map(vp -> new OptionDto(vp.name(), vp.name()))
                    .collect(Collectors.toList());
            action.setTargetList(targetList);
        }
        action.setRestrict(sayLogic.makeActionRestrict(villageInfo));
        return action;
    }

    // 名前変更
    private VillageChangeNameFormDto convertToChangeNameForm(VillageInfo villageInfo) {
        VillageChangeNameFormDto changeName = new VillageChangeNameFormDto();
        CharaGroup charaGroup = charaGroupBhv
                .selectEntityWithDeletedCheck(cb -> cb.query().setCharaGroupId_Equal(villageInfo.settings.getCharacterGroupId()));
        boolean isDispChangeNameForm = sayLogic.isAvailableSay(villageInfo) && charaGroup.isIsAvailableChangeNameTrue();
        changeName.setIsDispChangeNameForm(isDispChangeNameForm);
        return changeName;
    }

    // 簡易メモ
    private VillageMemoFormDto convertToMemoForm(VillageInfo villageInfo) {
        VillageMemoFormDto memo = new VillageMemoFormDto();
        boolean isDispMemoForm = sayLogic.isAvailableSay(villageInfo);
        memo.setIsDispMemoForm(isDispMemoForm);
        return memo;
    }

    // 参戦
    // 役職希望変更
    // 退村
    private void setParticipateForm( //
            Model model, //
            VillageParticipateFormDto participate, //
            Optional<VillagePlayer> optVillagePlayer, //
            VillageForms forms //
    ) {
        if (participate.getIsDispParticipateForm()) {
            model.addAttribute("participateForm", Optional.ofNullable(forms.participateForm).orElse(new VillageParticipateForm()));
            return;
        }
        if (participate.getIsDispChangeRequestSkillForm()) {
            VillageChangeRequestSkillForm requestSkillForm =
                    Optional.ofNullable(forms.changeRequestSkillForm).orElse(makeChangeRequestSkillForm(optVillagePlayer.get()));
            model.addAttribute("changeRequestSkillForm", requestSkillForm);
            model.addAttribute("requestSkillName", CDef.Skill.codeOf(requestSkillForm.getRequestedSkill()).alias());
            model.addAttribute("secondRequestSkillName", CDef.Skill.codeOf(requestSkillForm.getSecondRequestedSkill()).alias());
        }
        if (participate.getIsDispLeaveVillageForm()) {
            model.addAttribute("villageLeaveForm", new VillageLeaveForm());
        }
    }

    // コミット
    private VillageCommitFormDto convertToCommitForm(VillageInfo villageInfo) {
        VillageCommitFormDto commit = new VillageCommitFormDto();
        commit.setIsDispCommitForm(commitLogic.isAvailableCommit(villageInfo));
        return commit;
    }

    private VillageCommitForm createCommitForm(boolean isDispCommitForm, VillageInfo villageInfo) {
        if (!isDispCommitForm) {
            return null;
        }
        VillageCommitForm form = new VillageCommitForm();
        OptionalEntity<Commit> optCommit = commitBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_Equal(villageInfo.day);
            cb.query().setVillagePlayerId_Equal(villageInfo.optVillagePlayer.get().getVillagePlayerId());
        });
        form.setCommit(!optCommit.isPresent()); // すでに存在する＝取り消すを表示、存在しない＝コミットするを表示
        return form;
    }

    // 参戦
    // 役職希望変更
    // 退村
    private VillageParticipateFormDto convertToParticipateForm(VillageInfo villageInfo) {
        VillageParticipateFormDto participate = new VillageParticipateFormDto();
        // 参戦
        boolean isDispParticipateForm = participateLogic.isAvailableParticipate(villageInfo);
        participate.setIsDispParticipateForm(isDispParticipateForm);
        if (isDispParticipateForm) {
            participate.setSelectableCharaList(participateLogic.selectSelectableCharaList(villageInfo)
                    .stream()
                    .map(chara -> convertToCharaPart(chara))
                    .collect(Collectors.toList()));
        }
        participate.setIsDispChangeRequestNgMessage(villageDispLogic.isDispChangeRequestSkillNgMessage(villageInfo));
        // 役職希望変更
        boolean isDispChangeRequestSkillForm = skillRequestLogic.isAvailableChangeSkillRequest(villageInfo);
        participate.setIsDispChangeRequestSkillForm(isDispChangeRequestSkillForm);

        // 参戦、役職希望変更時に選べる役職
        if (isDispParticipateForm || isDispChangeRequestSkillForm) {
            participate.setSelectableSkillList(SkillUtil.getSelectableSkillList(villageInfo.settings.getOrganize())
                    .stream()
                    .map(skill -> convertToSkillPart(skill))
                    .collect(Collectors.toList()));
        }

        // 退村
        participate.setIsDispLeaveVillageForm(participateLogic.isAvailableLeave(villageInfo));
        return participate;
    }

    private VillageParticipateDto convertToMyself(VillageInfo villageInfo) {
        VillageParticipateDto myself = new VillageParticipateDto();
        // 参加情報
        villageInfo.optVillagePlayer.ifPresent(villagePlayer -> {
            myself.setCharaImageUrl(villagePlayer.getChara().get().getNormalCharaImgUrl());
            myself.setCharaName(villagePlayer.name());
            myself.setCharaImageWidth(villagePlayer.getChara().get().getDisplayWidth());
            myself.setCharaImageHeight(villagePlayer.getChara().get().getDisplayHeight());
            myself.setIsDead(villagePlayer.isIsDeadTrue());
            myself.setSkill(convertToParticipateSkill(villagePlayer.getSkillCodeAsSkill()));
        });
        return myself;
    }

    private VillageParticipateSkillDto convertToParticipateSkill(CDef.Skill skill) {
        if (skill == null) {
            return null;
        }
        VillageParticipateSkillDto participateSkill = new VillageParticipateSkillDto();
        participateSkill.setCode(skill.code());
        participateSkill.setHasAttackAbility(skill.isHasAttackAbility());
        participateSkill.setHasDivineAbility(skill.isHasDivineAbility());
        participateSkill.setHasGuardAbility(skill == CDef.Skill.狩人);
        participateSkill.setHasDisturbAbility(skill.isHasDisturbAbility());
        participateSkill.setHasFootstepAbility(//
                skill.isHasAttackAbility() //
                        || skill.isHasDivineAbility() //
                        || skill == CDef.Skill.狩人 //
                        || skill.isHasDisturbAbility() //
                        || skill == CDef.Skill.一匹狼);
        participateSkill.setHasCohabitAbility(skill == CDef.Skill.同棲者);
        return participateSkill;
    }

    // 村建て
    private void setVillageModelCreateUser(VillageResultContent content, VillageInfo villageInfo, Model model) {
        content.setIsCreatePlayer(villageInfo.isCreator());
        content.setIsAvailableSettingsUpdate(isAvailableSettingsUpdate(villageInfo));
        if (villageInfo.isCreator()) {
            model.addAttribute("kickForm", new VillageKickForm());
            model.addAttribute("creatorSayForm", new VillageSayForm());
        }
    }

    private boolean isAvailableSettingsUpdate(VillageInfo villageInfo) {
        return (villageInfo.isAdmin() || villageInfo.isCreator()) && villageInfo.village.isVillageStatusCode募集中();
    }

    private VillageChangeRequestSkillForm makeChangeRequestSkillForm(VillagePlayer villagePlayer) {
        VillageChangeRequestSkillForm form = new VillageChangeRequestSkillForm();
        form.setRequestedSkill(villagePlayer.getRequestSkillCode());
        form.setSecondRequestedSkill(villagePlayer.getSecondRequestSkillCode());
        return form;
    }

    private List<OptionDto> makeFaceTypeCodeList(VillageInfo villageInfo) {
        return villageInfo.optVillagePlayer.get().getChara().get().getCharaImageList().stream().map(img -> {
            OptionDto option = new OptionDto();
            option.setName(img.getFaceTypeCodeAsFaceType().alias());
            option.setValue(img.getFaceTypeCode());
            return option;
        }).collect(Collectors.toList());
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private List<VillageMemberDto> convertToMemberPart(VillagePlayers villagePlayers) {
        // 生存
        VillageMemberDto aliveMember = new VillageMemberDto();
        aliveMember.setStatus("生存");
        aliveMember.setStatusMemberList(villagePlayers.filterAlive()
                .filterNotSpecatate()
                .sortedBy(compareForMemberList())
                .map(mem -> convertToMemberDetailPart(mem)));
        // 処刑
        VillageMemberDto executedMember = new VillageMemberDto();
        executedMember.setStatus("処刑死");
        executedMember.setStatusMemberList(
                villagePlayers.filterExecuted().sortedBy(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)));
        // 無惨
        VillageMemberDto misrableMember = new VillageMemberDto();
        misrableMember.setStatus("無惨");
        misrableMember.setStatusMemberList(
                villagePlayers.filterMiserable().sortedBy(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)));
        // 後追
        VillageMemberDto suicideMember = new VillageMemberDto();
        suicideMember.setStatus("後追");
        suicideMember.setStatusMemberList(
                villagePlayers.filterSuicide().sortedBy(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)));
        // 突然死
        VillageMemberDto suddenlyDeathMember = new VillageMemberDto();
        suddenlyDeathMember.setStatus("突然死");
        suddenlyDeathMember.setStatusMemberList(
                villagePlayers.filterSuddenly().sortedBy(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)));
        // 見学
        VillageMemberDto spectateMember = new VillageMemberDto();
        spectateMember.setStatus("見学");
        spectateMember.setStatusMemberList(
                villagePlayers.filterSpectate().sortedBy(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)));

        return Arrays.asList(aliveMember, executedMember, misrableMember, suicideMember, suddenlyDeathMember, spectateMember);
    }

    // 死亡日時の若い順→キャラIDの若い順
    private Comparator<VillagePlayer> compareForMemberList() {
        return Comparator.comparing(VillagePlayer::isIsDeadFalse) //
                .thenComparing(vp -> Optional.ofNullable(vp.getDeadDay()).orElse(0)) //
                .thenComparing(VillagePlayer::getCharaId);
    }

    private List<VillageRoomAssignedRowDto> convertToRoomAssignedPart(VillageInfo villageInfo) {
        if (villageInfo.vPlayers.list.isEmpty()
                || villageInfo.vPlayers.filterNotSpecatate().list.stream().anyMatch(vp -> vp.getRoomNumber() == null)) {
            return null; // 部屋がまだ割り当てられていない
        }
        Integer width = villageInfo.village.getRoomSizeWidth();
        Integer height = villageInfo.village.getRoomSizeHeight();
        List<VillageRoomAssignedRowDto> roomAssignedRowList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            VillageRoomAssignedRowDto row = new VillageRoomAssignedRowDto();
            for (int j = 1; j <= width; j++) {
                final int roomNum = i * width + j;
                row.getRoomAssignedList().add(createRoomInfo(villageInfo, width, roomNum));
            }
            roomAssignedRowList.add(row);
        }
        return roomAssignedRowList;
    }

    private VillageRoomAssignedDto createRoomInfo(VillageInfo villageInfo, Integer width, int roomNum) {
        VillageRoomAssignedDto room = new VillageRoomAssignedDto();
        room.setRoomNumber(String.format("%02d", roomNum));
        villageInfo.vPlayers.filterNotSpecatate().findByRoomNumber(roomNum, villageInfo.day).ifPresent(vp -> {
            Chara chara = vp.getChara().get();
            room.setCharaName(vp.getCharaName());
            room.setCharaShortName(vp.getCharaShortName());
            room.setCharaImgUrl(chara.getNormalCharaImgUrl());
            room.setCharaImgWidth(chara.getDisplayWidth());
            room.setCharaImgHeight(chara.getDisplayHeight());
            room.setIsDummy(chara.getCharaId().equals(villageInfo.settings.getDummyCharaId()));
            if (vp.isAliveWhen(villageInfo.day)) {
                room.setIsDead(false);
            } else {
                room.setIsDead(true);
                room.setDeadDay(vp.getDeadDay());
                room.setDeadReason(vp.getDeadReasonCode());
            }
            if (isAvailableSeeMemberSkill(villageInfo)) {
                room.setSkillName(vp.getSkillCodeAsSkill().alias());
            }
        });
        return room;
    }

    private boolean isAvailableSeeMemberSkill(VillageInfo villageInfo) {
        if (villageInfo.isAdmin()) {
            return true;
        }
        if (villageInfo.village.isVillageStatusCodeエピローグ() || villageInfo.village.isVillageStatusCode終了()) {
            return true;
        }
        if (villageInfo.settings.getIsOpenSkillInGrave() && (villageInfo.isDead() || villageInfo.isSpectator())) {
            // 墓下役職公開ありで、墓下か見学である
            return true;
        }
        return false;
    }

    private VillageMemberDetailDto convertToMemberDetailPart(VillagePlayer mem) {
        VillageMemberDetailDto detail = new VillageMemberDetailDto();
        detail.setCharaName(mem.name());
        detail.setDeadDay(mem.getDeadDay() != null ? mem.getDeadDay() + "d" : null);
        detail.setLastAccessDatetime(mem.getLastAccessDatetime());
        detail.setMemo(mem.getMemo());
        return detail;
    }

    private VillageSkillDto convertToSkillPart(CDef.Skill skill) {
        VillageSkillDto part = new VillageSkillDto();
        part.setCode(skill.code());
        part.setName(skill.alias());
        return part;
    }

    private VillageCharaDto convertToCharaPart(Chara chara) {
        VillageCharaDto part = new VillageCharaDto();
        part.setId(chara.getCharaId());
        part.setName(chara.getCharaName());
        part.setUrl(chara.getNormalCharaImgUrl());
        part.setWidth(chara.getDisplayWidth());
        part.setHeight(chara.getDisplayHeight());
        return part;
    }

    private VillageSettingsDto convertToSettings(VillageInfo villageInfo) {
        VillageSettings settings = villageInfo.settings;
        List<VillageDay> dayList = villageInfo.days.list;
        VillageSettingsDto part = new VillageSettingsDto();
        part.setStartDatetime(dayList.get(0).getDaychangeDatetime()); // 0日目の切り替え日時
        part.setStartPersonMinNum(settings.getStartPersonMinNum());
        part.setPersonMaxNum(settings.getPersonMaxNum());
        part.setCharaGroupId(settings.getCharacterGroupId());
        part.setCharaGroupName(settings.getCharaGroup().get().getCharaGroupName());
        part.setDummyCharaName(villageInfo.vPlayers.findByCharaId(settings.getDummyCharaId()).name());
        part.setDayChangeInterval(makeDayChangeIntervalStr(settings.getDayChangeIntervalSeconds()));
        part.setSkillRequestType(BooleanUtils.isTrue(settings.getIsPossibleSkillRequest()) ? "有効" : "無効");
        part.setVoteType(BooleanUtils.isTrue(settings.getIsOpenVote()) ? "記名投票" : "無記名投票");
        part.setIsRequiredJoinPassword(StringUtils.isNotEmpty(settings.getJoinPassword()));
        part.setIsAvailableSpectate(settings.getIsAvailableSpectate());
        part.setIsAvailableSameWolfAttack(settings.getIsAvailableSameWolfAttack());
        part.setIsAvailableGuardSameTarget(settings.getIsAvailableGuardSameTarget());
        part.setIsOpenSkillInGrave(settings.getIsOpenSkillInGrave());
        part.setIsVisibleGraveSpectateMessage(settings.getIsVisibleGraveSpectateMessage());
        part.setIsAvailableSuddonlyDeath(settings.getIsAvailableSuddonlyDeath());
        part.setIsAvailableCommit(settings.getIsAvailableCommit());
        part.setIsAvailableAction(settings.getIsAvailableAction());
        part.setOrganization(makeDisplayOrganization(villageInfo));
        part.setIsRandomOrganization(settings.getIsRandomOrganize());
        part.setCampAllocationList(
                makeCampAllocationList(villageInfo.village.getCampAllocationList(), villageInfo.village.getSkillAllocationList()));
        part.setAllowedSecretSayCode(settings.getAllowedSecretSayCode());
        part.setSayRestrictList(convertToRestrictList(villageInfo.village.getNormalSayRestrictionList()));
        part.setSkillSayRestrictList(convertToSkillRestrictList(villageInfo.village.getSkillSayRestrictionList()));
        part.setRpSayRestrictList(convertToRpRestrictList(villageInfo.village.getSkillSayRestrictionList()));
        part.setIsSkillRequestAvailable(settings.getIsPossibleSkillRequest());
        part.setCreatePlayerName(villageInfo.village.getCreatePlayerName());
        return part;
    }

    // 人数：構成の表示にする
    private String makeDisplayOrganization(VillageInfo villageInfo) {
        String organize = villageInfo.settings.getOrganize();
        if (villageInfo.village.isVillageStatusCode募集中() || villageInfo.village.isVillageStatusCode廃村()) {
            return String.join("\n", Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).map(org -> {
                return String.format("%02d人: %s", org.length(), org);
            }).collect(Collectors.toList()));
        } else {
            int memberNum = villageInfo.vPlayers.filterNotSpecatate().list.size();
            return String.format("%02d人: %s", memberNum,
                    Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).filter(org -> org.length() == memberNum).findFirst().get());
        }
    }

    private List<NewVillageRandomOrgCampDto> makeCampAllocationList(List<CampAllocation> campAllocationList,
            List<SkillAllocation> skillAllocationList) {
        return campAllocationList.stream().sorted((c1, c2) -> getCampOrder(c1) - getCampOrder(c2)).map(campAllocation -> {
            NewVillageRandomOrgCampDto campDto = new NewVillageRandomOrgCampDto();
            campDto.setCampCode(campAllocation.getCampCode());
            campDto.setCampName(campAllocation.getCampCodeAsCamp().alias());
            campDto.setMinNum(campAllocation.getMinNum());
            campDto.setMaxNum(campAllocation.getMaxNum());
            campDto.setAllocation(campAllocation.getAllocation());
            campDto.setSkillAllocation(makeSkillAllocationList(campAllocation.getCampCodeAsCamp(), skillAllocationList));
            return campDto;
        }).collect(Collectors.toList());
    }

    private int getCampOrder(CampAllocation c) {
        CDef.Camp camp = c.getCampCodeAsCamp();
        switch (camp) {
        case 村人陣営:
            return 1;
        case 人狼陣営:
            return 2;
        case 狐陣営:
            return 3;
        case 恋人陣営:
            return 4;
        case 愉快犯陣営:
            return 5;
        }
        return 9;
    }

    private List<NewVillageRandomOrgSkillDto> makeSkillAllocationList(CDef.Camp camp, List<SkillAllocation> skillAllocationList) {
        return skillAllocationList.stream()
                .filter(skill -> skill.getSkillCodeAsSkill().campCode().equals(camp.code()))
                .sorted((s1, s2) -> Integer.parseInt(s1.getSkillCodeAsSkill().order()) - Integer.parseInt(s2.getSkillCodeAsSkill().order()))
                .map(skillAllocation -> {
                    NewVillageRandomOrgSkillDto skillDto = new NewVillageRandomOrgSkillDto();
                    skillDto.setSkillCode(skillAllocation.getSkillCode());
                    skillDto.setSkillName(skillAllocation.getSkillCodeAsSkill().alias());
                    skillDto.setMinNum(skillAllocation.getMinNum());
                    skillDto.setMaxNum(skillAllocation.getMaxNum());
                    skillDto.setAllocation(skillAllocation.getAllocation());
                    return skillDto;
                })
                .collect(Collectors.toList());
    }

    private String makeDayChangeIntervalStr(Integer interval) {
        String hour = interval >= 3600 ? String.format("%02d時間", interval / 3600) : "";
        String minute = interval >= 60 ? String.format("%02d分", (interval % 3600) / 60) : "";
        String second = String.format("%02d秒", interval % 60);
        return hour + minute + second;
    }

    private List<NewVillageSayRestrictDto> convertToRestrictList(List<NormalSayRestriction> restrictList) {
        return SkillUtil.SORTED_SKILL_LIST.stream().map(skill -> {
            NewVillageSayRestrictDto restrict = new NewVillageSayRestrictDto();
            restrict.setSkillName(skill.alias());
            restrict.setSkillCode(skill.code());
            restrict.setIsRestrict(false);
            restrictList.stream().filter(r -> r.getSkillCode().equals(skill.code())).findFirst().ifPresent(res -> {
                restrict.setIsRestrict(true);
                restrict.setCount(res.getMessageMaxNum());
                restrict.setLength(res.getMessageMaxLength());
            });
            return restrict;
        }).collect(Collectors.toList());
    }

    private List<NewVillageSkillSayRestrictDto> convertToSkillRestrictList(List<SkillSayRestriction> restrictList) {
        return Arrays.asList(CDef.MessageType.人狼の囁き, CDef.MessageType.共鳴発言, CDef.MessageType.恋人発言).stream().map(type -> {
            NewVillageSkillSayRestrictDto restrict = new NewVillageSkillSayRestrictDto();
            restrict.setMessageTypeName(type.alias());
            restrict.setMessageTypeCode(type.code());
            restrict.setIsRestrict(false);
            restrictList.stream().filter(r -> r.getMessageTypeCodeAsMessageType() == type).findFirst().ifPresent(res -> {
                restrict.setIsRestrict(true);
                restrict.setCount(res.getMessageMaxNum());
                restrict.setLength(res.getMessageMaxLength());

            });
            return restrict;
        }).collect(Collectors.toList());
    }

    private List<NewVillageRpSayRestrictDto> convertToRpRestrictList(List<SkillSayRestriction> restrictList) {
        return Arrays.asList(CDef.MessageType.アクション).stream().map(type -> {
            NewVillageRpSayRestrictDto restrict = new NewVillageRpSayRestrictDto();
            restrict.setMessageTypeName(type.alias());
            restrict.setMessageTypeCode(type.code());
            restrict.setIsRestrict(false);
            restrictList.stream().filter(r -> r.getMessageTypeCodeAsMessageType() == type).findFirst().ifPresent(res -> {
                restrict.setIsRestrict(true);
                restrict.setCount(res.getMessageMaxNum());
                restrict.setLength(res.getMessageMaxLength());

            });
            return restrict;
        }).collect(Collectors.toList());
    }

    private VillageVoteDto makeMemberVote(VillageInfo villageInfo) {
        List<VillageMemberVoteDto> memberVoteDtoList = villageInfo.vPlayers //
                .filterNotSpecatate() //
                .filterNotDummy(villageInfo.settings.getDummyCharaId())
                .map(vp -> {
                    VillageMemberVoteDto voteDto = new VillageMemberVoteDto();
                    voteDto.setCharaName(vp.shortName());
                    List<String> voteTargetList = villageInfo.votes.filterByChara(vp.getCharaId()) //
                            .filterBy(v -> v.getDay() < villageInfo.day) //
                            .sortedByDay()
                            .map(v -> {
                                return villageInfo.vPlayers.findByCharaId(v.getVoteCharaId()).shortName(v.getDay());
                            });
                    voteDto.setVoteTargetList(voteTargetList);
                    return voteDto;
                });
        // 投票した回数が多い順
        memberVoteDtoList = memberVoteDtoList.stream() //
                .sorted((m1, m2) -> m2.getVoteTargetList().size() - m1.getVoteTargetList().size())
                .collect(Collectors.toList());

        VillageVoteDto voteDto = new VillageVoteDto();
        voteDto.setVoteList(memberVoteDtoList);
        voteDto.setMaxVoteCount(memberVoteDtoList.stream().map(v -> v.getVoteTargetList().size()).max(Integer::max).get());
        return voteDto;
    }

    // 開発用機能
    private void setDebugInfo(Boolean debug, Model model) {
        model.addAttribute("isDebugMode", debug);
    }

    private List<VillagePlayerStatusDto> createStatusList(VillageInfo villageInfo) {
        if (!villageInfo.optVillagePlayer.isPresent()) {
            return null;
        }
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        if (villagePlayer.isIsDeadTrue() || villagePlayer.isIsSpectatorTrue()) {
            return null;
        }
        if (!villageInfo.village.isVillageStatusCode進行中()) {
            return null;
        }
        if (!villageInfo.village.getVillageDays().latestDay().getDay().equals(villageInfo.day)) {
            return null;
        }
        // 恋絆
        List<VillagePlayerStatusDto> loversStatusList = villagePlayer.getVillagePlayerStatusByVillagePlayerIdList()
                .stream()
                .filter(st -> st.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.後追い)
                .map(status -> {
                    VillagePlayerStatusDto statusDto = new VillagePlayerStatusDto();
                    statusDto.setStatusCode(status.getVillagePlayerStatusCode());
                    statusDto.setMessage(String.format("あなたは%sに恋しています。", status.getVillagePlayerByToVillagePlayerId().get().name()));
                    return statusDto;
                })
                .collect(Collectors.toList());
        // 狐憑き
        List<VillagePlayerStatusDto> foxPosessionStatusList = villagePlayer.getVillagePlayerStatusByToVillagePlayerIdList()
                .stream()
                .filter(st -> st.getVillagePlayerStatusCodeAsVillagePlayerStatusType() == CDef.VillagePlayerStatusType.狐憑き)
                .map(status -> {
                    VillagePlayerStatusDto statusDto = new VillagePlayerStatusDto();
                    statusDto.setStatusCode(status.getVillagePlayerStatusCode());
                    statusDto.setMessage("あなたは妖狐に与するものとなりました。");
                    return statusDto;
                })
                .collect(Collectors.toList());

        List<VillagePlayerStatusDto> list = new ArrayList<>();
        list.addAll(loversStatusList);
        list.addAll(foxPosessionStatusList);
        return list;
    }
}
