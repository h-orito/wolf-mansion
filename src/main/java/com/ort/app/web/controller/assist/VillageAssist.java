package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.optional.OptionalThing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.controller.logic.FootstepLogic;
import com.ort.app.web.controller.logic.VillageDispLogic;
import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageCommitForm;
import com.ort.app.web.form.VillageKickForm;
import com.ort.app.web.form.VillageLeaveForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.model.VillageFootstepDto;
import com.ort.app.web.model.VillageMemberVoteDto;
import com.ort.app.web.model.VillageResultContent;
import com.ort.app.web.model.VillageVoteDto;
import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageMemberDetailDto;
import com.ort.app.web.model.inner.VillageMemberDto;
import com.ort.app.web.model.inner.VillageRoomAssignedDto;
import com.ort.app.web.model.inner.VillageRoomAssignedRowDto;
import com.ort.app.web.model.inner.VillageSettingsDto;
import com.ort.app.web.model.inner.VillageSkillDto;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.CommitBhv;
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Commit;
import com.ort.dbflute.exentity.Skill;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Vote;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Component
public class VillageAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillageSettingsBhv villageSettingsBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private VillageDayBhv villageDayBhv;

    @Autowired
    private SkillBhv skillBhv;

    @Autowired
    private AbilityBhv abilityBhv;

    @Autowired
    private VoteBhv voteBhv;

    @Autowired
    private FootstepBhv footstepBhv;

    @Autowired
    private CommitBhv commitBhv;

    @Autowired
    private VillageDispLogic villageDispLogic;

    @Autowired
    private FootstepLogic footstepLogic;

    @Value("${isDebugMode}")
    private Boolean debug;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public String setIndexModelAndReturnView(Integer villageId, VillageSayForm sayForm, VillageParticipateForm participateForm,
            VillageChangeRequestSkillForm changeRequestSkillForm, Model model) {
        // 最新の日付取得
        int day = selectLatestDay(villageId);
        return setIndexModel(villageId, day, sayForm, participateForm, changeRequestSkillForm, model);
    }

    public String setIndexModel(Integer villageId, int day, VillageSayForm sayForm, VillageParticipateForm participateForm,
            VillageChangeRequestSkillForm changeRequestSkillForm, Model model) {
        Village village = selectVillage(villageId);
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo(); // ログインしているか
        ListResultBean<VillageDay> dayList = villageDayBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        OptionalThing<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo, true);
        VillageInfo villageInfo = new VillageInfo(village, userInfo, dayList, optVillagePlayer, day);

        VillageResultContent content = new VillageResultContent();
        setVillageModelBasicInfo(content, villageInfo);
        setVillageModelForm(content, villageInfo, sayForm, participateForm, changeRequestSkillForm, model);
        setVillageModelCreateUser(content, village, userInfo, model);
        setDebugInfo(debug, model);
        model.addAttribute("content", content);
        return "village";
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    public int selectLatestDay(Integer villageId) {
        return villageDayBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnDay();
            cb.query().setVillageId_Equal(villageId);
        }).get();
    }

    private Village selectVillage(Integer villageId) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne().withCharaGroup();
            cb.query().setVillageId_Equal(villageId);
        });
        villageBhv.loadVillagePlayer(village, villagePlayerCB -> { // 参加者一覧用
            villagePlayerCB.setupSelect_Chara();
            villagePlayerCB.setupSelect_SkillBySkillCode();
            villagePlayerCB.setupSelect_DeadReason();
            villagePlayerCB.query().setIsGone_Equal_False();
            villagePlayerCB.query().addOrderBy_DeadDay_Asc();
        });
        return village;
    }

    private List<Skill> selectSelectableSkillList(VillageInfo villageInfo) {
        String organize = villageInfo.settings.getOrganize();
        Set<CDef.Skill> skillSet = new HashSet<>();
        Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).forEach(org -> {
            Stream.of(org.split("")).forEach(skillStr -> {
                skillSet.add(SkillUtil.SKILL_SHORTNAME_MAP.get(skillStr));
            });
        });
        skillSet.add(CDef.Skill.おまかせ);
        return skillBhv.selectList(cb -> {
            cb.query().setSkillCode_InScope_AsSkill(skillSet);
            cb.query().addOrderBy_DispOrder_Asc();
        });
    }

    public OptionalThing<VillagePlayer> selectVillagePlayer(Integer villageId, UserInfo userInfo, boolean isContainSpectator) {
        if (userInfo == null) {
            return OptionalThing.empty();
        }
        return villagePlayerBhv.selectEntity(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.getUsername());
            cb.orScopeQuery(orCB -> {
                orCB.query().setDeadReasonCode_IsNull();
                orCB.query().setDeadReasonCode_NotEqual_突然();
            });
            cb.query().queryVillage().setVillageStatusCode_NotInScope_AsVillageStatus(
                    Arrays.asList(CDef.VillageStatus.廃村, CDef.VillageStatus.終了));
        });
    }

    // 参戦キャラとして選択可能なキャラを取得
    private List<Chara> selectSelectableCharaList(Integer villageId) {
        // 既に参加しているキャラ
        List<Integer> alreadyParticipatePlayerIdList = villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
        }).stream().map(VillagePlayer::getCharaId).collect(Collectors.toList());

        // 村作成時に選択したキャラクターグループ内で、まだ参戦していないキャラ
        // ダミーも除外
        VillageSettings villageSettings = villageSettingsBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_CharaGroup();
            cb.query().setVillageId_Equal(villageId);
        });
        villageSettingsBhv.load(villageSettings, loader -> {
            loader.pulloutCharaGroup().loadChara(charaCB -> {
                charaCB.query().setIsDummy_Equal_False();
                charaCB.query().setCharaId_NotInScope(alreadyParticipatePlayerIdList);
            });
        });
        return villageSettings.getCharaGroup().get().getCharaList();
    }

    private OptionalEntity<Ability> selectAbility(Integer villageId, int day, CDef.AbilityType type) {
        if (type == null) {
            return OptionalEntity.empty();
        }
        return abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setAbilityTypeCode_Equal_AsAbilityType(type);
        });
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    // デフォルト発言区分
    private void setDefaultMessageTypeIfNeeded(VillageSayForm sayForm, boolean isDispSayForm, boolean isAvailableNormalSay,
            boolean isAvailableWerewolfSay, boolean isAvailableMasonSay, boolean isAvailableGraveSay, boolean isAvailableMonologueSay,
            boolean isAvailableSpectateSay, Model model) {
        if (sayForm != null || !isDispSayForm) {
            return;
        }
        VillageSayForm form = new VillageSayForm();
        if (isAvailableWerewolfSay) {
            form.setMessageType(CDef.MessageType.人狼の囁き.code());
        } else if (isAvailableMasonSay) {
            form.setMessageType(CDef.MessageType.共鳴発言.code());
        } else if (isAvailableMonologueSay) {
            form.setMessageType(CDef.MessageType.独り言.code());
        } else if (isAvailableNormalSay) {
            form.setMessageType(CDef.MessageType.通常発言.code());
        } else if (isAvailableGraveSay) {
            form.setMessageType(CDef.MessageType.死者の呻き.code());
        } else if (isAvailableSpectateSay) {
            form.setMessageType(CDef.MessageType.見学発言.code());
        }
        model.addAttribute("sayForm", form);
    }

    private void setAbilityTarget(VillageInfo villageInfo, List<Chara> abilityTargetList, Model model) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || villageInfo.isSpectator() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return;
        }
        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.占い師 && skill != CDef.Skill.賢者 && skill != CDef.Skill.狩人 && skill != CDef.Skill.狂人
                && skill != CDef.Skill.妖狐 && skill != CDef.Skill.魔神官 && skill != CDef.Skill.C国狂人) {
            return;
        }

        VillageAbilityForm abilityForm = new VillageAbilityForm();
        CDef.AbilityType type = skill == CDef.Skill.人狼 ? CDef.AbilityType.襲撃 //
                : SkillUtil.hasDivineAbility(skill) ? CDef.AbilityType.占い //
                        : skill == CDef.Skill.狩人 ? CDef.AbilityType.護衛 : null;
        OptionalEntity<Ability> optAbility = selectAbility(villageInfo.villageId, villageInfo.day, type);
        if (optAbility.isPresent()) {
            Ability ab = optAbility.get();
            abilityForm.setCharaId(ab.getCharaId());
            model.addAttribute("charaName", getCharacterNameFromCharaId(villageInfo.village, ab.getCharaId()));
            abilityForm.setTargetCharaId(ab.getTargetCharaId());
            model.addAttribute("targetCharaName", getCharacterNameFromCharaId(villageInfo.village, ab.getTargetCharaId()));
        }

        footstepBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_Equal(villageInfo.day);
            cb.query().setCharaId_Equal(
                    optAbility.isPresent() ? optAbility.get().getCharaId() : villageInfo.optVillagePlayer.get().getCharaId());
        }).ifPresent(fs -> {
            abilityForm.setFootstep(fs.getFootstepRoomNumbers());
            model.addAttribute("footstep", fs.getFootstepRoomNumbers());
        });

        model.addAttribute("abilityForm", abilityForm);
    }

    private void setVoteTarget(VillageInfo villageInfo, Model model) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || villageInfo.isSpectator() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return;
        }
        voteBhv.selectEntity(cb -> {
            cb.setupSelect_CharaByVoteCharaId();
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setCharaId_Equal(villageInfo.optVillagePlayer.get().getCharaId());
            cb.query().setDay_Equal(villageInfo.day);
        }).ifPresent(vote -> {
            VillageVoteForm form = new VillageVoteForm();
            form.setTargetCharaId(vote.getVoteCharaId());
            model.addAttribute("voteForm", form);
            model.addAttribute("voteTarget", vote.getCharaByVoteCharaId().get().getCharaName());
        });
    }

    private String getCharacterNameFromCharaId(Village village, Integer charaId) {
        return village.getVillagePlayerList()
                .stream()
                .filter(vp -> vp.getCharaId().equals(charaId))
                .findFirst()
                .get()
                .getChara()
                .get()
                .getCharaName();
    }

    // 基本的な情報、参加有無に関わらない情報
    private void setVillageModelBasicInfo(VillageResultContent content, VillageInfo villageInfo) {
        content.setVillageId(villageInfo.village.getVillageId());
        content.setVillageNumber(String.format("%04d", villageInfo.village.getVillageId()));
        content.setVillageName(villageInfo.village.getVillageDisplayName());
        content.setMemberList(convertToMemberPart(villageInfo.vPlayerList));
        content.setCharacterList(extractAndSortCharacterList(villageInfo));
        content.setRoomAssignedRowList(convertToRoomAssignedPart(villageInfo));
        content.setRoomWidth(villageInfo.village.getRoomSizeWidth());
        content.setDay(villageInfo.day);
        content.setDayList(villageInfo.getDayList());
        content.setEpilogueDay(villageInfo.village.getEpilogueDay());
        content.setVillageSettings(convertToSettings(villageInfo.settings, villageInfo.dayList));
        content.setIsSkillRequestAvailable(villageInfo.settings.getIsPossibleSkillRequest());
        content.setDayChangeDatetime(makeDayChangeDatetime(villageInfo));
        if (villageInfo.isStartedVote()) {
            content.setVote(makeMemberVote(villageInfo));
        }
        if (villageInfo.isStartedFootstepSet()) {
            content.setVillageFootstepList(makeFootstepList(villageInfo));
        }
    }

    private List<Chara> extractAndSortCharacterList(VillageInfo villageInfo) {
        return villageInfo.vPlayerList.stream().sorted((vp1, vp2) -> {
            // 参加→見学 それぞれの中ではキャラID順
            if (vp1.isIsSpectatorTrue() && vp2.isIsSpectatorFalse()) {
                return 1;
            } else if (vp1.isIsSpectatorFalse() && vp2.isIsSpectatorTrue()) {
                return -1;
            } else {
                return vp1.getCharaId() - vp2.getCharaId();
            }
        }).map(vp -> vp.getChara().get()).collect(Collectors.toList());
    }

    // 更新時刻
    private LocalDateTime makeDayChangeDatetime(VillageInfo villageInfo) {
        if (villageInfo.village.isVillageStatusCode廃村() || villageInfo.village.isVillageStatusCode終了()) {
            return null;
        }
        return villageInfo.dayList.get(villageInfo.dayList.size() - 1).getDaychangeDatetime();
    }

    // 参加している場合に使う情報
    private void setVillageModelForm(VillageResultContent content, VillageInfo villageInfo, VillageSayForm sayForm,
            VillageParticipateForm participateForm, VillageChangeRequestSkillForm changeRequestSkillForm, Model model) {
        // 参戦
        boolean isDispParticipateForm = villageDispLogic.isDispParticipateForm(villageInfo);
        setVillageModelParticipateForm(content, villageInfo.villageId, participateForm, model, isDispParticipateForm);
        // 役職希望変更
        boolean isDispChangeRequestSkillForm = villageDispLogic.isDispChangeRequestSkillForm(villageInfo);
        setVillageModekChangeRequestSkillForm(content, villageInfo.optVillagePlayer, changeRequestSkillForm, model, isDispParticipateForm,
                isDispChangeRequestSkillForm);
        // 参戦、役職希望変更時に選べる役職
        content.setSelectableSkillList(isDispParticipateForm || isDispChangeRequestSkillForm
                ? selectSelectableSkillList(villageInfo).stream().map(skill -> convertToSkillPart(skill)).collect(Collectors.toList())
                : null);
        // 入村パスワードを必要とするか
        content.setIsRequiredJoinPassword(StringUtils.isNotEmpty(villageInfo.settings.getJoinPassword()));
        // 退村
        content.setIsDispLeaveVillageForm(villageDispLogic.isDispLeaveVillageForm(villageInfo));
        model.addAttribute("villageLeaveForm", new VillageLeaveForm());
        // コミット
        setCommitForm(content, villageInfo, model);
        // 発言
        setVillageModelSayForm(content, villageInfo, sayForm, model);
        // 能力行使
        setVillageModelAbilityForm(content, villageInfo, model);
        // 投票
        setVoteTarget(villageInfo, model);
        content.setVoteTargetList(villageDispLogic.makeVoteTargetList(villageInfo));
        // 参加情報
        setVillageModelPlayerInfo(content, villageInfo.optVillagePlayer);
        // 自分のキャラID
        content.setCharaId(villageInfo.isParticipate() ? villageInfo.optVillagePlayer.get().getCharaId() : null);
    }

    // コミット
    private void setCommitForm(VillageResultContent content, VillageInfo villageInfo, Model model) {
        Boolean isDispCommitForm = villageDispLogic.isDispCommitForm(villageInfo);
        content.setIsDispCommitForm(isDispCommitForm);
        if (!isDispCommitForm) {
            return;
        }
        VillageCommitForm form = new VillageCommitForm();
        OptionalEntity<Commit> optCommit = commitBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_Equal(villageInfo.day);
            cb.query().setVillagePlayerId_Equal(villageInfo.optVillagePlayer.get().getVillagePlayerId());
        });
        form.setCommit(!optCommit.isPresent()); // すでに存在する＝取り消すを表示、存在しない＝コミットするを表示
        model.addAttribute("commitForm", form);
    }

    // 村建て
    private void setVillageModelCreateUser(VillageResultContent content, Village village, UserInfo userInfo, Model model) {
        String createPlayerName = village.getCreatePlayerName();
        content.setCreatePlayerName(createPlayerName);
        boolean isCreator = userInfo != null && userInfo.getUsername().equals(createPlayerName);
        content.setIsCreatePlayer(isCreator);
        content.setIsAvailableSettingsUpdate(
                userInfo != null && (userInfo.getUsername().equals(createPlayerName) || "master".equals(userInfo.getUsername()))
                        && village.isVillageStatusCode募集中());
        if (isCreator) {
            model.addAttribute("kickForm", new VillageKickForm());
            model.addAttribute("creatorSayForm", new VillageSayForm());
        }
    }

    private VillageChangeRequestSkillForm makeChangeRequestSkillForm(boolean isDispChangeRequestSkillForm,
            VillageChangeRequestSkillForm changeRequestSkillForm, OptionalThing<VillagePlayer> optVillagePlayer, Model model) {
        if (!isDispChangeRequestSkillForm) {
            return null;
        }
        if (changeRequestSkillForm != null) {
            model.addAttribute("requestSkillName", CDef.Skill.codeOf(changeRequestSkillForm.getRequestedSkill()).alias());
            return changeRequestSkillForm;
        }
        VillageChangeRequestSkillForm form = new VillageChangeRequestSkillForm();
        form.setRequestedSkill(optVillagePlayer.get().getRequestSkillCode());
        model.addAttribute("requestSkillName", CDef.Skill.codeOf(optVillagePlayer.get().getRequestSkillCode()).alias());
        return form;
    }

    private void setVillageModelPlayerInfo(VillageResultContent content, OptionalThing<VillagePlayer> optVillagePlayer) {
        content.setCharaImageUrl(optVillagePlayer.map(vp -> vp.getChara().get().getCharaImgUrl()).orElse(null));
        content.setIsDead(optVillagePlayer.map(VillagePlayer::isIsDeadTrue).orElse(null));
        content.setIsSpectator(optVillagePlayer.map(VillagePlayer::isIsSpectatorTrue).orElse(null));
        content.setSkillName(optVillagePlayer.map(vp -> vp.getSkillCode()).orElse(null));
    }

    private void setVillageModelAbilityForm(VillageResultContent content, VillageInfo villageInfo, Model model) {
        List<Chara> abilityTargetList = villageDispLogic.makeAbilityTargetList(villageInfo);
        content.setAbilityTargetList(abilityTargetList);
        content.setAttackerList(villageDispLogic.makeAttackerList(villageInfo));
        setAbilityTarget(villageInfo, abilityTargetList, model);
    }

    private void setVillageModelSayForm(VillageResultContent content, VillageInfo villageInfo, VillageSayForm sayForm, Model model) {
        boolean isDispSayForm = villageDispLogic.isDispSayForm(villageInfo);
        boolean isAllSayAvailable = isDispSayForm && villageInfo.isAdmin();
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.orElse(null);
        boolean isAvailableNormalSay = isDispSayForm && villageDispLogic.isAvailableNormalSay(villageInfo.village, vPlayer); // 通常発言可能か
        boolean isAvailableWerewolfSay = isDispSayForm && villageDispLogic.isAvailableWerewolfSay(villageInfo.village, vPlayer); // 囁き可能か
        boolean isAvailableMasonSay = isDispSayForm && villageDispLogic.isAvailableMasonSay(villageInfo.village, vPlayer); // 共有者発言可能か
        boolean isAvailableGraveSay = isDispSayForm && villageDispLogic.isAvailableGraveSay(villageInfo.village, vPlayer); // 死者の呻きが発言可能か
        boolean isAvailableSpectateSay = isDispSayForm && villageDispLogic.isAvailableSpectateSay(villageInfo.village, vPlayer); // 見学発言が発言可能か
        boolean isAvailableMonologueSay = isDispSayForm && villageDispLogic.isAvailableMonologueSay(villageInfo.village); // 独り言が発言可能か
        content.setIsDispSayForm(isAllSayAvailable || isDispSayForm);
        content.setIsAvailableNormalSay(isAllSayAvailable || isAvailableNormalSay); // 通常発言可能か
        content.setIsAvailableWerewolfSay(isAllSayAvailable || isAvailableWerewolfSay); // 囁き可能か
        content.setIsAvailableMasonSay(isAllSayAvailable || isAvailableMasonSay); // 共有者発言可能か
        content.setIsAvailableGraveSay(isAllSayAvailable || isAvailableGraveSay); // 死者の呻きが発言可能か
        content.setIsAvailableSpectateSay(isAllSayAvailable || isAvailableSpectateSay); // 見学発言が発言可能か
        content.setIsAvailableMonologueSay(isAllSayAvailable || isAvailableMonologueSay); // 独り言が発言可能か
        setDefaultMessageTypeIfNeeded(sayForm, isDispSayForm, isAvailableNormalSay, isAvailableWerewolfSay, isAvailableMasonSay,
                isAvailableGraveSay, isAvailableMonologueSay, isAvailableSpectateSay, model); // デフォルト発言区分
    }

    private void setVillageModekChangeRequestSkillForm(VillageResultContent content, OptionalThing<VillagePlayer> optVillagePlayer,
            VillageChangeRequestSkillForm changeRequestSkillForm, Model model, boolean isDispParticipateForm,
            boolean isDispChangeRequestSkillForm) {
        content.setIsDispChangeRequestSkillForm(isDispChangeRequestSkillForm);
        model.addAttribute("changeRequestSkillForm",
                makeChangeRequestSkillForm(isDispChangeRequestSkillForm, changeRequestSkillForm, optVillagePlayer, model));
    }

    private void setVillageModelParticipateForm(VillageResultContent content, Integer villageId, VillageParticipateForm participateForm,
            Model model, boolean isDispParticipateForm) {
        content.setIsDispParticipateForm(isDispParticipateForm);
        content.setSelectableCharaList(isDispParticipateForm
                ? selectSelectableCharaList(villageId).stream().map(chara -> convertToCharaPart(chara)).collect(Collectors.toList())
                : null); // 入村可能なキャラ
        model.addAttribute("participateForm", participateForm == null ? new VillageParticipateForm() : participateForm);
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private List<VillageMemberDto> convertToMemberPart(List<VillagePlayer> villagePlayerList) {
        // 生存
        VillageMemberDto aliveMember = new VillageMemberDto();
        List<VillagePlayer> aliveMemberList =
                villagePlayerList.stream().filter(vp -> vp.getDeadDay() == null && vp.isIsSpectatorFalse()).collect(Collectors.toList());
        aliveMember.setStatus("生存");
        aliveMember.setStatusMemberList(
                aliveMemberList.stream().sorted(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)).collect(
                        Collectors.toList()));
        // 吊り
        VillageMemberDto executedMember = new VillageMemberDto();
        List<VillagePlayer> executedMemberList = villagePlayerList.stream()
                .filter(villagePlayer -> villagePlayer.getDeadReason().isPresent()
                        && CDef.DeadReason.処刑 == CDef.DeadReason.codeOf(villagePlayer.getDeadReason().get().getDeadReasonCode()))
                .collect(Collectors.toList());
        executedMember.setStatus("処刑死");
        executedMember.setStatusMemberList(
                executedMemberList.stream().sorted(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)).collect(
                        Collectors.toList()));
        // 襲撃・呪殺
        VillageMemberDto attackedMember = new VillageMemberDto();
        List<VillagePlayer> attackedMemberList = villagePlayerList.stream().filter(vp -> {
            if (!vp.getDeadReason().isPresent()) {
                return false;
            }
            CDef.DeadReason reason = CDef.DeadReason.codeOf(vp.getDeadReason().get().getDeadReasonCode());
            return reason == CDef.DeadReason.襲撃 || reason == CDef.DeadReason.呪殺;
        }).collect(Collectors.toList());
        attackedMember.setStatus("襲撃死・呪殺");
        attackedMember.setStatusMemberList(
                attackedMemberList.stream().sorted(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)).collect(
                        Collectors.toList()));
        // 突然死
        VillageMemberDto suddonlyDeathMember = new VillageMemberDto();
        List<VillagePlayer> suddonlyDeathMemberList = villagePlayerList.stream()
                .filter(vp -> vp.getDeadReason().isPresent()
                        && CDef.DeadReason.突然 == CDef.DeadReason.codeOf(vp.getDeadReason().get().getDeadReasonCode()))
                .collect(Collectors.toList());
        suddonlyDeathMember.setStatus("突然死");
        suddonlyDeathMember.setStatusMemberList(
                suddonlyDeathMemberList.stream().sorted(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)).collect(
                        Collectors.toList()));
        // 見学
        VillageMemberDto spectateMember = new VillageMemberDto();
        List<VillagePlayer> spectateMemberList =
                villagePlayerList.stream().filter(vp -> vp.isIsSpectatorTrue()).collect(Collectors.toList());
        spectateMember.setStatus("見学");
        spectateMember.setStatusMemberList(
                spectateMemberList.stream().sorted(compareForMemberList()).map(mem -> convertToMemberDetailPart(mem)).collect(
                        Collectors.toList()));

        return Arrays.asList(aliveMember, executedMember, attackedMember, suddonlyDeathMember, spectateMember);
    }

    // 死亡日時の若い順→キャラIDの若い順
    private Comparator<? super VillagePlayer> compareForMemberList() {
        return (vp1, vp2) -> {
            Integer vp1deadDay = vp1.getDeadDay();
            Integer vp2deadDay = vp2.getDeadDay();
            if (vp1deadDay != null && vp2deadDay != null) {
                if (vp1deadDay == vp2deadDay) {
                    return vp1.getCharaId() - vp2.getCharaId();
                } else {
                    return vp1deadDay - vp2deadDay;
                }
            }
            return vp1.getCharaId() - vp2.getCharaId();
        };
    }

    private List<VillageRoomAssignedRowDto> convertToRoomAssignedPart(VillageInfo villageInfo) {
        if (villageInfo.vPlayerList.size() == 0
                || villageInfo.vPlayerList.stream().anyMatch(vp -> vp.isIsSpectatorFalse() && vp.getRoomNumber() == null)) {
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
        villageInfo.vPlayerList.stream().filter(vp -> vp.isIsSpectatorFalse() && vp.getRoomNumber().equals(roomNum)).findFirst().ifPresent(
                vp -> {
                    room.setCharaName(vp.getChara().get().getCharaShortName());
                    room.setCharaImgUrl(vp.getChara().get().getCharaImgUrl());
                    room.setIsDead(vp.getDeadDay() == null ? false : vp.getDeadDay() <= villageInfo.day);
                    if (isAvailableSeeMemberSkill(villageInfo)) {
                        room.setSkillName(vp.getSkillCodeAsSkill().alias());
                    }
                });
        return room;
    }

    private boolean isAvailableSeeMemberSkill(VillageInfo villageInfo) {
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
        Chara chara = mem.getChara().get();
        detail.setCharaName(String.format("[%s] %s", chara.getCharaShortName(), chara.getCharaName()));
        detail.setDeadDay(mem.getDeadDay() != null ? mem.getDeadDay() + "d" : null);
        return detail;
    }

    private VillageSkillDto convertToSkillPart(Skill skill) {
        VillageSkillDto part = new VillageSkillDto();
        part.setCode(skill.getSkillCode());
        part.setName(skill.getSkillName());
        return part;
    }

    private VillageCharaDto convertToCharaPart(Chara chara) {
        VillageCharaDto part = new VillageCharaDto();
        part.setId(chara.getCharaId());
        part.setName(chara.getCharaName());
        part.setUrl(chara.getCharaImgUrl());
        return part;
    }

    private VillageSettingsDto convertToSettings(VillageSettings settings, List<VillageDay> dayList) {
        VillageSettingsDto part = new VillageSettingsDto();
        part.setStartDatetime(dayList.get(0).getDaychangeDatetime()); // 0日目の切り替え日時
        part.setStartPersonMinNum(settings.getStartPersonMinNum());
        part.setPersonMaxNum(settings.getPersonMaxNum());
        part.setCharaGroupId(settings.getCharacterGroupId());
        part.setCharaGroupName(settings.getCharaGroup().get().getCharaGroupName());
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
        part.setOrganization(makeDisplayOrganization(settings.getOrganize()));
        return part;
    }

    // 人数：構成の表示にする
    private String makeDisplayOrganization(String organize) {
        return String.join("\n", Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).map(org -> {
            return String.format("%02d人: %s", org.length(), org);
        }).collect(Collectors.toList()));
    }

    private String makeDayChangeIntervalStr(Integer interval) {
        String hour = interval >= 3600 ? String.format("%02d時間", interval / 3600) : "";
        String minute = interval >= 60 ? String.format("%02d分", (interval % 3600) / 60) : "";
        String second = String.format("%02d秒", interval % 60);
        return hour + minute + second;
    }

    private VillageVoteDto makeMemberVote(VillageInfo villageInfo) {
        ListResultBean<Vote> voteList = voteBhv.selectList(cb -> {
            cb.setupSelect_CharaByVoteCharaId();
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setDay_LessThan(villageInfo.dayList.size() - 1);
            cb.query().addOrderBy_Day_Asc();
        });
        List<VillageMemberVoteDto> memberVoteDtoList = villageInfo.getVPList(false, true, true).stream().map(vp -> {
            VillageMemberVoteDto voteDto = new VillageMemberVoteDto();
            voteDto.setCharaName(vp.getChara().get().getCharaShortName());
            List<String> voteTargetList = voteList.stream()
                    .filter(v -> v.getCharaId().equals(vp.getCharaId()))
                    .map(v -> v.getCharaByVoteCharaId().get().getCharaShortName())
                    .collect(Collectors.toList());
            voteDto.setVoteTargetList(voteTargetList);
            return voteDto;
        }).collect(Collectors.toList());
        // 投票した回数が多い順
        memberVoteDtoList =
                memberVoteDtoList.stream().sorted((m1, m2) -> m2.getVoteTargetList().size() - m1.getVoteTargetList().size()).collect(
                        Collectors.toList());
        VillageVoteDto voteDto = new VillageVoteDto();
        voteDto.setVoteList(memberVoteDtoList);
        voteDto.setMaxVoteCount(memberVoteDtoList.stream().map(v -> v.getVoteTargetList().size()).max(Integer::max).get());
        return voteDto;
    }

    private List<VillageFootstepDto> makeFootstepList(VillageInfo villageInfo) {
        List<VillageFootstepDto> footstepList = new ArrayList<>();
        for (int i = 2; i < villageInfo.dayList.size(); i++) {
            int day = i;
            List<Integer> livingPlayerRoomNumList = villageInfo.vPlayerList.stream()
                    .filter(vp -> vp.isIsSpectatorFalse() && (vp.isIsDeadFalse() || day < vp.getDeadDay()))
                    .map(VillagePlayer::getRoomNumber)
                    .collect(Collectors.toList());
            String message;
            if (isDispSkillNameInFootstep(villageInfo)) {
                message = footstepLogic.makeFootstepMessageOpenSkill(villageInfo.villageId, day - 1, livingPlayerRoomNumList,
                        villageInfo.getVPList(false, true, true));
            } else {
                message = footstepLogic.makeFootstepMessageWithoutHeader(villageInfo.villageId, day - 1, livingPlayerRoomNumList);
            }
            VillageFootstepDto footstep = new VillageFootstepDto();
            footstep.setDay(day);
            footstep.setFootstep(message);
            footstepList.add(footstep);
        }
        return footstepList;
    }

    // 足音に役職名を表示するか
    private boolean isDispSkillNameInFootstep(VillageInfo villageInfo) {
        // エピっていたら表示
        if (villageInfo.village.isVillageStatusCodeエピローグ() || villageInfo.village.isVillageStatusCode終了()) {
            return true;
        }
        // 墓下役職公開で墓下か見物だったら表示
        if (villageInfo.settings.isIsOpenSkillInGraveTrue() && (villageInfo.isDead() || villageInfo.isSpectator())) {
            return true;
        }

        return false;
    }

    // 開発用機能
    private void setDebugInfo(Boolean debug, Model model) {
        model.addAttribute("isDebugMode", debug);
    }
}
