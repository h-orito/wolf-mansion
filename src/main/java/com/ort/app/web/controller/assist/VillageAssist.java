package com.ort.app.web.controller.assist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.optional.OptionalThing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.controller.logic.VillageDispLogic;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageLeaveForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.form.VillageVoteForm;
import com.ort.app.web.model.VillageResultContent;
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
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Skill;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;
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
    private VillageDispLogic villageDispLogic;

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

        VillageResultContent content = new VillageResultContent();
        setVillageModelBasicInfo(content, village, day, dayList);
        setVillageModelForm(content, villageId, village, userInfo, day, dayList, optVillagePlayer, sayForm, participateForm,
                changeRequestSkillForm, model);
        setVillageModelCreateUser(content, village, userInfo);
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

    private List<Skill> selectSelectableSkillList(Integer villageId) {
        return skillBhv.selectList(cb -> {
            cb.query().setSkillCode_InScope_AsSkill(Arrays.asList(CDef.Skill.おまかせ, CDef.Skill.人狼, CDef.Skill.共鳴者, CDef.Skill.賢者,
                    CDef.Skill.妖狐, CDef.Skill.狂人, CDef.Skill.狩人, CDef.Skill.導師, CDef.Skill.村人));
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
        } else if (isAvailableNormalSay) {
            form.setMessageType(CDef.MessageType.通常発言.code());
        } else if (isAvailableGraveSay) {
            form.setMessageType(CDef.MessageType.死者の呻き.code());
        } else if (isAvailableSpectateSay) {
            form.setMessageType(CDef.MessageType.見学発言.code());
        } else {
            form.setMessageType(CDef.MessageType.独り言.code());
        }
        model.addAttribute("sayForm", form);
    }

    private void setAbilityTarget(Integer villageId, Village village, List<Chara> abilityTargetList,
            OptionalThing<VillagePlayer> optVillagePlayer, int day, boolean isLatestDay, Model model) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || optVillagePlayer.get().isIsSpectatorTrue()
                || !isLatestDay || !village.isVillageStatusCode進行中()) {
            return;
        }
        CDef.Skill skill = optVillagePlayer.get().getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.占い師 && skill != CDef.Skill.賢者 && skill != CDef.Skill.狩人 && skill != CDef.Skill.狂人
                && skill != CDef.Skill.妖狐 && skill != CDef.Skill.魔神官 && skill != CDef.Skill.C国狂人) {
            return;
        }

        VillageAbilityForm abilityForm = new VillageAbilityForm();
        CDef.AbilityType type = skill == CDef.Skill.人狼 ? CDef.AbilityType.襲撃 //
                : SkillUtil.hasDivineAbility(skill) ? CDef.AbilityType.占い //
                        : skill == CDef.Skill.狩人 ? CDef.AbilityType.護衛 : null;
        OptionalEntity<Ability> optAbility = selectAbility(villageId, day, type);
        if (optAbility.isPresent()) {
            Ability ab = optAbility.get();
            abilityForm.setCharaId(ab.getCharaId());
            model.addAttribute("charaName", getCharacterNameFromCharaId(village, ab.getCharaId()));
            abilityForm.setTargetCharaId(ab.getTargetCharaId());
            model.addAttribute("targetCharaName", getCharacterNameFromCharaId(village, ab.getTargetCharaId()));
        }

        footstepBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setCharaId_Equal(optAbility.isPresent() ? optAbility.get().getCharaId() : optVillagePlayer.get().getCharaId());
        }).ifPresent(fs -> {
            abilityForm.setFootstep(fs.getFootstepRoomNumbers());
            model.addAttribute("footstep", fs.getFootstepRoomNumbers());
        });

        model.addAttribute("abilityForm", abilityForm);
    }

    private void setVoteTarget(Integer villageId, Village village, OptionalThing<VillagePlayer> optVillagePlayer, int day,
            boolean isLatestDay, Model model) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || optVillagePlayer.get().isIsSpectatorTrue()
                || !isLatestDay || !village.isVillageStatusCode進行中()) {
            return;
        }
        voteBhv.selectEntity(cb -> {
            cb.setupSelect_CharaByVoteCharaId();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setCharaId_Equal(optVillagePlayer.get().getCharaId());
            cb.query().setDay_Equal(day);
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
    private void setVillageModelBasicInfo(VillageResultContent content, Village village, int day, ListResultBean<VillageDay> dayList) {
        content.setVillageId(village.getVillageId());
        content.setVillageName(village.getVillageDisplayName());
        content.setMemberList(convertToMemberPart(village.getVillagePlayerList()));
        content.setCharacterList(village.getVillagePlayerList().stream().map(vp -> vp.getChara().get()).collect(Collectors.toList()));
        content.setRoomAssignedRowList(convertToRoomAssignedPart(village, village.getVillagePlayerList(), day));
        content.setRoomWidth(village.getRoomSizeWidth());
        content.setDay(day);
        content.setDayList(dayList.stream().map(VillageDay::getDay).collect(Collectors.toList()));
        content.setEpilogueDay(village.getEpilogueDay());
        content.setVillageSettings(convertToSettings(village.getVillageSettingsAsOne().get(), dayList));
        content.setIsSkillRequestAvailable(village.getVillageSettingsAsOne().get().getIsPossibleSkillRequest());
    }

    // 参加している場合に使う情報
    private void setVillageModelForm(VillageResultContent content, Integer villageId, Village village, UserInfo userInfo, int day,
            ListResultBean<VillageDay> dayList, OptionalThing<VillagePlayer> optVillagePlayer, VillageSayForm sayForm,
            VillageParticipateForm participateForm, VillageChangeRequestSkillForm changeRequestSkillForm, Model model) {
        // 参戦
        boolean isDispParticipateForm = villageDispLogic.isDispParticipateForm(day, userInfo, village);
        setVillageModelParticipateForm(content, villageId, participateForm, model, isDispParticipateForm);
        // 役職希望変更
        boolean isDispChangeRequestSkillForm = villageDispLogic.isDispChangeRequestSkillForm(day, optVillagePlayer, village);
        setVillageModekChangeRequestSkillForm(content, villageId, optVillagePlayer, changeRequestSkillForm, model, isDispParticipateForm,
                isDispChangeRequestSkillForm);
        // 参戦、役職希望変更時に選べる役職
        content.setSelectableSkillList(isDispParticipateForm || isDispChangeRequestSkillForm
                ? selectSelectableSkillList(villageId).stream().map(skill -> convertToSkillPart(skill)).collect(Collectors.toList())
                : null);
        // 入村パスワードを必要とするか
        content.setIsRequiredJoinPassword(StringUtils.isNotEmpty(village.getVillageSettingsAsOne().get().getJoinPassword()));
        // 退村
        content.setIsDispLeaveVillageForm(villageDispLogic.isDispLeaveVillageForm(day, optVillagePlayer, village));
        model.addAttribute("villageLeaveForm", new VillageLeaveForm());
        // 発言
        setVillageModelSayForm(content, villageId, village, userInfo, day, dayList, optVillagePlayer, sayForm, model);
        // 能力行使
        setVillageModelAbilityForm(content, villageId, village, day, dayList, optVillagePlayer, model);
        // 投票
        setVoteTarget(villageId, village, optVillagePlayer, day, villageDispLogic.isLatestDay(day, dayList), model);
        content.setVoteTargetList(
                villageDispLogic.makeVoteTargetList(village, optVillagePlayer, day, villageDispLogic.isLatestDay(day, dayList)));
        // 参加情報
        setVillageModelPlayerInfo(content, optVillagePlayer);
    }

    // 村建て
    private void setVillageModelCreateUser(VillageResultContent content, Village village, UserInfo userInfo) {
        String createPlayerName = village.getCreatePlayerName();
        content.setCreatePlayerName(createPlayerName);
        content.setIsCreatePlayer(userInfo != null && userInfo.getUsername().equals(createPlayerName));
        content.setIsAvailableSettingsUpdate(
                userInfo != null && (userInfo.getUsername().equals(createPlayerName) || "master".equals(userInfo.getUsername()))
                        && village.isVillageStatusCode募集中());
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

    private void setVillageModelAbilityForm(VillageResultContent content, Integer villageId, Village village, int day,
            ListResultBean<VillageDay> dayList, OptionalThing<VillagePlayer> optVillagePlayer, Model model) {
        boolean isLatestDay = villageDispLogic.isLatestDay(day, dayList);
        List<Chara> abilityTargetList = villageDispLogic.makeAbilityTargetList(village, optVillagePlayer, day, isLatestDay);
        content.setAbilityTargetList(abilityTargetList);
        content.setAttackerList(villageDispLogic.makeAttackerList(village, optVillagePlayer, day, isLatestDay));
        setAbilityTarget(villageId, village, abilityTargetList, optVillagePlayer, day, isLatestDay, model);
    }

    private void setVillageModelSayForm(VillageResultContent content, Integer villageId, Village village, UserInfo userInfo, int day,
            ListResultBean<VillageDay> dayList, OptionalThing<VillagePlayer> optVillagePlayer, VillageSayForm sayForm, Model model) {
        boolean isDispSayForm = villageDispLogic.isDispSayForm(villageId, village, userInfo, optVillagePlayer, day, dayList);
        boolean isAllSayAvailable =
                isDispSayForm && userInfo.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")));
        VillagePlayer vPlayer = optVillagePlayer.orElse(null);
        boolean isAvailableNormalSay = isDispSayForm && villageDispLogic.isAvailableNormalSay(village, vPlayer); // 通常発言可能か
        boolean isAvailableWerewolfSay = isDispSayForm && villageDispLogic.isAvailableWerewolfSay(village, vPlayer); // 囁き可能か
        boolean isAvailableMasonSay = isDispSayForm && villageDispLogic.isAvailableMasonSay(village, vPlayer); // 共有者発言可能か
        boolean isAvailableGraveSay = isDispSayForm && villageDispLogic.isAvailableGraveSay(village, vPlayer); // 死者の呻きが発言可能か
        boolean isAvailableSpectateSay = isDispSayForm && villageDispLogic.isAvailableSpectateSay(village, vPlayer); // 見学発言が発言可能か
        boolean isAvailableMonologueSay = isDispSayForm && villageDispLogic.isAvailableMonologueSay(village); // 独り言が発言可能か
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

    private void setVillageModekChangeRequestSkillForm(VillageResultContent content, Integer villageId,
            OptionalThing<VillagePlayer> optVillagePlayer, VillageChangeRequestSkillForm changeRequestSkillForm, Model model,
            boolean isDispParticipateForm, boolean isDispChangeRequestSkillForm) {
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
                villagePlayerList.stream().filter(villagePlayer -> villagePlayer.getDeadDay() == null).collect(Collectors.toList());
        aliveMember.setStatus("生存");
        aliveMember.setStatusMemberList(aliveMemberList.stream().map(mem -> convertToMemberDetailPart(mem)).collect(Collectors.toList()));
        // 吊り
        VillageMemberDto executedMember = new VillageMemberDto();
        List<VillagePlayer> executedMemberList = villagePlayerList.stream()
                .filter(villagePlayer -> villagePlayer.getDeadReason().isPresent()
                        && CDef.DeadReason.処刑 == CDef.DeadReason.codeOf(villagePlayer.getDeadReason().get().getDeadReasonCode()))
                .collect(Collectors.toList());
        executedMember.setStatus("処刑死");
        executedMember
                .setStatusMemberList(executedMemberList.stream().map(mem -> convertToMemberDetailPart(mem)).collect(Collectors.toList()));
        // 襲撃・呪殺
        VillageMemberDto attackedMember = new VillageMemberDto();
        List<VillagePlayer> attackedMemberList = villagePlayerList.stream().filter(villagePlayer -> {
            if (!villagePlayer.getDeadReason().isPresent()) {
                return false;
            }
            CDef.DeadReason reason = CDef.DeadReason.codeOf(villagePlayer.getDeadReason().get().getDeadReasonCode());
            return reason == CDef.DeadReason.襲撃 || reason == CDef.DeadReason.呪殺;
        }).collect(Collectors.toList());
        attackedMember.setStatus("襲撃死・呪殺");
        attackedMember
                .setStatusMemberList(attackedMemberList.stream().map(mem -> convertToMemberDetailPart(mem)).collect(Collectors.toList()));
        // 突然死
        VillageMemberDto suddonlyDeathMember = new VillageMemberDto();
        List<VillagePlayer> suddonlyDeathMemberList = villagePlayerList.stream()
                .filter(villagePlayer -> villagePlayer.getDeadReason().isPresent()
                        && CDef.DeadReason.突然 == CDef.DeadReason.codeOf(villagePlayer.getDeadReason().get().getDeadReasonCode()))
                .collect(Collectors.toList());
        suddonlyDeathMember.setStatus("突然死");
        suddonlyDeathMember.setStatusMemberList(
                suddonlyDeathMemberList.stream().map(mem -> convertToMemberDetailPart(mem)).collect(Collectors.toList()));
        // 見学
        VillageMemberDto spectateMember = new VillageMemberDto();
        List<VillagePlayer> spectateMemberList =
                villagePlayerList.stream().filter(villagePlayer -> villagePlayer.isIsSpectatorTrue()).collect(Collectors.toList());
        spectateMember.setStatus("見学");
        spectateMember
                .setStatusMemberList(spectateMemberList.stream().map(mem -> convertToMemberDetailPart(mem)).collect(Collectors.toList()));

        return Arrays.asList(aliveMember, executedMember, attackedMember, suddonlyDeathMember, spectateMember);
    }

    private List<VillageRoomAssignedRowDto> convertToRoomAssignedPart(Village village, List<VillagePlayer> villagePlayerList, int day) {
        if (villagePlayerList.stream().anyMatch(vp -> vp.isIsSpectatorFalse() && vp.getRoomNumber() == null)) {
            return null; // 部屋がまだ割り当てられていない
        }
        Integer width = village.getRoomSizeWidth();
        Integer height = village.getRoomSizeHeight();
        List<VillageRoomAssignedRowDto> roomAssignedRowList = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            VillageRoomAssignedRowDto row = new VillageRoomAssignedRowDto();
            for (int j = 1; j <= width; j++) {
                VillageRoomAssignedDto room = new VillageRoomAssignedDto();
                final int roomNum = i * width + j;
                room.setRoomNumber(String.format("%02d", roomNum));
                villagePlayerList.stream()
                        .filter(vp -> vp.isIsSpectatorFalse() && vp.getRoomNumber().equals(roomNum))
                        .findFirst()
                        .ifPresent(vp -> {
                            room.setCharaName(vp.getChara().get().getCharaShortName());
                            room.setCharaImgUrl(vp.getChara().get().getCharaImgUrl());
                            //                    room.setIsDead(BooleanUtils.isTrue(vp.getIsDead()));
                            room.setIsDead(vp.getDeadDay() == null ? false : vp.getDeadDay() <= day);
                        });
                row.getRoomAssignedList().add(room);
            }
            roomAssignedRowList.add(row);
        }
        return roomAssignedRowList;
    }

    private VillageMemberDetailDto convertToMemberDetailPart(VillagePlayer mem) {
        VillageMemberDetailDto detail = new VillageMemberDetailDto();
        detail.setCharaName(mem.getChara().get().getCharaName());
        detail.setDeadDay(mem.getDeadDay() != null ? mem.getDeadDay() + "日目" : null);
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

    private VillageSettingsDto convertToSettings(VillageSettings settings, ListResultBean<VillageDay> dayList) {
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
        return part;
    }

    private String makeDayChangeIntervalStr(Integer interval) {
        String hour = interval >= 3600 ? String.format("%02d時間", interval / 3600) : "";
        String minute = interval >= 60 ? String.format("%02d分", (interval % 3600) / 60) : "";
        String second = String.format("%02d秒", interval % 60);
        return hour + minute + second;
    }
}
