package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.ort.app.web.controller.logic.VillageDispLogic;
import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.form.NewVillageSayRestrictDetailDto;
import com.ort.app.web.form.NewVillageSayRestrictDto;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageChangeRequestSkillForm;
import com.ort.app.web.form.VillageCommitForm;
import com.ort.app.web.form.VillageKickForm;
import com.ort.app.web.form.VillageLeaveForm;
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
import com.ort.app.web.model.inner.village.VillageCommitFormDto;
import com.ort.app.web.model.inner.village.VillageFormDto;
import com.ort.app.web.model.inner.village.VillageParticipateDto;
import com.ort.app.web.model.inner.village.VillageParticipateFormDto;
import com.ort.app.web.model.inner.village.VillageParticipateSkillDto;
import com.ort.app.web.model.inner.village.VillageSayFormDto;
import com.ort.app.web.model.inner.village.VillageSettingsDto;
import com.ort.app.web.model.inner.village.VillageVoteFormDto;
import com.ort.app.web.util.CharaUtil;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.CommitBhv;
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.RandomKeywordBhv;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.CharaImage;
import com.ort.dbflute.exentity.Commit;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.MessageRestriction;
import com.ort.dbflute.exentity.RandomKeyword;
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
    //                                                                          Definition
    //                                                                          ==========
    private static Map<CDef.MessageType, CDef.FaceType> EXPECTED_FACE_TYPE_MAP = null;
    static {
        Map<CDef.MessageType, CDef.FaceType> map = new HashMap<>();
        map.put(CDef.MessageType.通常発言, CDef.FaceType.通常);
        map.put(CDef.MessageType.人狼の囁き, CDef.FaceType.囁き);
        map.put(CDef.MessageType.共鳴発言, CDef.FaceType.共鳴);
        map.put(CDef.MessageType.独り言, CDef.FaceType.独り言);
        map.put(CDef.MessageType.死者の呻き, CDef.FaceType.墓下);
        map.put(CDef.MessageType.見学発言, CDef.FaceType.通常);
        EXPECTED_FACE_TYPE_MAP = map;
    }
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
    private RandomKeywordBhv randomKeywordBhv;
    @Autowired
    private VillageDispLogic villageDispLogic;

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
        // 村の初期表示に必要な情報を収集
        VillageInfo villageInfo = selectVillageInfo(villageId, day);
        // 表示用情報
        VillageResultContent content = new VillageResultContent();
        // 参加していなくても見られる情報
        setVillageModelBasicInfo(content, villageInfo);
        // 参加している場合のみ見られる情報
        setVillageModelForm(content, villageInfo, sayForm, participateForm, changeRequestSkillForm, model);
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
        villageBhv.load(village, loader -> {
            loader.loadVillagePlayer(villagePlayerCB -> { // 参加者一覧用
                villagePlayerCB.setupSelect_Chara();
                villagePlayerCB.setupSelect_SkillBySkillCode();
                villagePlayerCB.setupSelect_DeadReason();
                villagePlayerCB.query().setIsGone_Equal_False();
                villagePlayerCB.query().addOrderBy_DeadDay_Asc();
            }).withNestedReferrer(vpLoader -> {
                vpLoader.pulloutChara().loadCharaImage(charaImageCB -> charaImageCB.query().setFaceTypeCode_Equal_通常());
            });
            loader.loadMessageRestriction(mRest -> {});
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
        skillSet.add(CDef.Skill.おまかせ村人陣営);
        skillSet.add(CDef.Skill.おまかせ人狼陣営);
        skillSet.add(CDef.Skill.おまかせ足音職);
        skillSet.add(CDef.Skill.おまかせ役職窓あり);
        skillSet.add(CDef.Skill.おまかせ人外);
        return skillBhv.selectList(cb -> {
            cb.query().setSkillCode_InScope_AsSkill(skillSet);
            cb.query().addOrderBy_DispOrder_Asc();
        });
    }

    public OptionalThing<VillagePlayer> selectVillagePlayer(Integer villageId, UserInfo userInfo, boolean isContainSpectator) {
        if (userInfo == null) {
            return OptionalThing.empty();
        }
        OptionalEntity<VillagePlayer> optVillagePlayer = villagePlayerBhv.selectEntity(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_Player();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.getUsername());
            cb.query().queryVillage().setVillageStatusCode_NotInScope_AsVillageStatus(
                    Arrays.asList(CDef.VillageStatus.廃村, CDef.VillageStatus.終了));
        });
        if (!optVillagePlayer.isPresent()) {
            return OptionalThing.empty();
        }
        villagePlayerBhv.load(optVillagePlayer.get(), loader -> {
            loader.pulloutChara().loadCharaImage(charaImageCB -> {
                charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc();
            });
        });
        return optVillagePlayer;
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
                charaCB.query().setCharaId_NotEqual(villageSettings.getDummyCharaId());
                charaCB.query().setCharaId_NotInScope(alreadyParticipatePlayerIdList);
            }).withNestedReferrer(charaLoader -> {
                charaLoader.loadCharaImage(charaImageCB -> charaImageCB.query().setFaceTypeCode_Equal_通常());
            });
        });
        return villageSettings.getCharaGroup().get().getCharaList();
    }

    private OptionalEntity<Ability> selectAbility(Integer villageId, int day, CDef.AbilityType type, Integer charaId) {
        if (type == null) {
            return OptionalEntity.empty();
        }
        return abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setAbilityTypeCode_Equal_AsAbilityType(type);
            if (type != CDef.AbilityType.襲撃) {
                cb.query().setCharaId_Equal(charaId);
            }
            cb.fetchFirst(1);
        });
    }

    private String selectRandomKeywords() {
        return String.join(",", randomKeywordBhv.selectList(cb -> {}).stream().map(RandomKeyword::getKeyword).collect(Collectors.toList()));
    }

    private ListResultBean<Footstep> selectFootstepList(Integer villageId) {
        return footstepBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private ListResultBean<Vote> selectVoteList(Integer villageId, int day) {
        return voteBhv.selectList(cb -> {
            cb.setupSelect_CharaByVoteCharaId();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_LessThan(day);
            cb.query().addOrderBy_Day_Asc();
        });
    }

    private List<Ability> selectAbilityList(Integer villageId) {
        return abilityBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private VillageInfo selectVillageInfo(Integer villageId, int day) {
        Village village = selectVillage(villageId);
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo(); // ログインしているか
        ListResultBean<VillageDay> dayList = villageDayBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        OptionalThing<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo, true);
        ListResultBean<Vote> voteList = selectVoteList(villageId, day);
        List<Footstep> footStepList = selectFootstepList(villageId);
        List<Ability> abilityList = selectAbilityList(villageId);
        VillageInfo villageInfo = new VillageInfo(village, userInfo, dayList, optVillagePlayer, day, voteList, footStepList, abilityList);
        return villageInfo;
    }

    private CDef.FaceType detectDefaultFaceType(VillageInfo villageInfo, CDef.MessageType defaultMessageType) {
        CDef.FaceType expectFaceType = EXPECTED_FACE_TYPE_MAP.get(defaultMessageType);
        List<CharaImage> faceList = villageInfo.optVillagePlayer.get().getChara().get().getCharaImageList();
        if (faceList.stream().anyMatch(face -> face.getFaceTypeCodeAsFaceType() == expectFaceType)) {
            return expectFaceType;
        } else {
            return CDef.FaceType.通常;
        }
    }

    private void setAbilityTarget(VillageInfo villageInfo, Model model) {
        if (!villageInfo.isParticipate() || villageInfo.isDead() || villageInfo.isSpectator() || !villageInfo.isLatestDay()
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return;
        }
        CDef.Skill skill = villageInfo.optVillagePlayer.get().getSkillCodeAsSkill();
        if (!SkillUtil.SET_AVAILABLE_SKILL_LIST.contains(skill)) {
            return;
        }
        if (SkillUtil.SECOND_DAY_SET_AVAILABLE_SKILL_LIST.contains(skill) && villageInfo.day <= 1) {
            return; // 1日目はセット不可
        }
        VillageAbilityForm abilityForm = new VillageAbilityForm();
        CDef.AbilityType type = SkillUtil.SKILL_ABILITY_TYPE_MAP.get(skill);
        OptionalEntity<Ability> optAbility =
                selectAbility(villageInfo.villageId, villageInfo.day, type, villageInfo.optVillagePlayer.get().getCharaId());
        if (optAbility.isPresent()) {
            Ability ab = optAbility.get();
            abilityForm.setCharaId(ab.getCharaId());
            model.addAttribute("charaName", CharaUtil.makeCharaName(extractVillagePlayerBy(villageInfo, ab.getCharaId())));
            abilityForm.setTargetCharaId(ab.getTargetCharaId());
            if (ab.getTargetCharaId() != null) {
                model.addAttribute("targetCharaName", CharaUtil.makeCharaName(extractVillagePlayerBy(villageInfo, ab.getTargetCharaId())));
            }
            abilityForm.setFootstep(ab.getTargetFootstep());
            model.addAttribute("footstep", ab.getTargetFootstep());
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
                || !villageInfo.village.isVillageStatusCode進行中() || villageInfo.day < 2) {
            return;
        }
        OptionalEntity<Vote> optVote = voteBhv.selectEntity(cb -> {
            cb.setupSelect_CharaByVoteCharaId();
            cb.query().setVillageId_Equal(villageInfo.villageId);
            cb.query().setCharaId_Equal(villageInfo.optVillagePlayer.get().getCharaId());
            cb.query().setDay_Equal(villageInfo.day);
        });

        VillageVoteForm form = new VillageVoteForm();
        form.setTargetCharaId(optVote.map(vote -> vote.getVoteCharaId()).orElse(null));
        model.addAttribute("voteForm", form);
        model.addAttribute("voteTarget", optVote.map(vote -> vote.getCharaByVoteCharaId().get().getCharaName()).orElse(null));
    }

    private boolean isAvailableSecretSay(VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || !villageInfo.isLatestDay() || !villageInfo.village.isVillageStatusCode進行中()) {
            return false;
        }
        CDef.AllowedSecretSay allowedSecretSay = villageInfo.settings.getAllowedSecretSayCodeAsAllowedSecretSay();
        return villageInfo.isAdmin() || allowedSecretSay == CDef.AllowedSecretSay.全員;
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
        content.setSettings(convertToSettings(villageInfo));
        content.setDayChangeDatetime(makeDayChangeDatetime(villageInfo));
        content.setIsDispUnspoiler(villageInfo.village.isVillageStatusCodeエピローグ() || villageInfo.village.isVillageStatusCode終了());
        content.setRandomKeywords(selectRandomKeywords());
        if (villageInfo.isStartedVote()) {
            content.setVote(makeMemberVote(villageInfo));
        }
        if (villageInfo.isStartedFootstepSet()) {
            content.setVillageFootstepList(villageDispLogic.makeFootstepList(villageInfo));
            content.setSituationList(villageDispLogic.makeSituationList(villageInfo));
            content.setIsDispSpoilerContent(villageDispLogic.isDispSpoilerContent(villageInfo));
        }
    }

    private List<OptionDto> extractAndSortCharacterList(VillageInfo villageInfo) {
        return villageInfo.vPlayerList.stream().sorted((vp1, vp2) -> {
            // 参加→見学 それぞれの中ではキャラID順
            if (vp1.isIsSpectatorTrue() && vp2.isIsSpectatorFalse()) {
                return 1;
            } else if (vp1.isIsSpectatorFalse() && vp2.isIsSpectatorTrue()) {
                return -1;
            } else {
                return vp1.getCharaId() - vp2.getCharaId();
            }
        }).map(vp -> {
            OptionDto character = new OptionDto();
            character.setName(CharaUtil.makeCharaName(vp));
            character.setValue(vp.getCharaId().toString());
            return character;
        }).collect(Collectors.toList());
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
        // 各種フォーム絡みの情報
        content.setForm(convertToVillageForm(villageInfo, sayForm, participateForm, changeRequestSkillForm, model));
        // 参加している自分自身の情報
        content.setMyself(convertToMyself(villageInfo));
    }

    // 各種フォーム絡みの情報
    private VillageFormDto convertToVillageForm(VillageInfo villageInfo, VillageSayForm sayForm, VillageParticipateForm participateForm,
            VillageChangeRequestSkillForm changeRequestSkillForm, Model model) {
        VillageFormDto formDto = new VillageFormDto();

        // 参戦
        // 役職希望変更
        // 退村
        formDto.setParticipate(convertToParticipateForm(villageInfo));
        setParticipateForm(model, formDto.getParticipate(), villageInfo.optVillagePlayer, participateForm, changeRequestSkillForm);

        // コミット
        formDto.setCommit(convertToCommitForm(villageInfo));
        model.addAttribute("commitForm", createCommitForm(formDto.getCommit().getIsDispCommitForm(), villageInfo));

        // 発言
        formDto.setSay(convertToSayForm(villageInfo));
        model.addAttribute("sayForm", createSayForm(formDto.getSay(), villageInfo, sayForm));

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
        vote.setVoteTargetList(villageDispLogic.makeVoteTargetList(villageInfo));
        return vote;
    }

    private VillageAbilityFormDto convertToAbilityForm(VillageInfo villageInfo) {
        VillageAbilityFormDto ability = new VillageAbilityFormDto();
        ability.setAbilityTargetList(villageDispLogic.makeAbilityTargetList(villageInfo));
        ability.setAttackerList(villageDispLogic.makeAttackerList(villageInfo));
        ability.setSkillHistoryList(villageDispLogic.makeSkillHistoryList(villageInfo));
        ability.setWerewolfCharaNameList(villageDispLogic.makeWerewolfCharaNameList(villageInfo));
        ability.setcMadmanCharaNameList(villageDispLogic.makeCMadmanCharaNameList(villageInfo));
        ability.setTargetPrefixMessage(villageDispLogic.makeTargetPrefixMessage(villageInfo));
        ability.setTargetSuffixMessage(villageDispLogic.makeTargetSuffixMessage(villageInfo));
        ability.setIsTargetingAndFootstep(villageDispLogic.isTargetingAndFootstep(villageInfo));
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
        if (villageInfo.isAdmin()) {
            form.setMessageType(CDef.MessageType.通常発言.code());
        } else if (villageInfo.village.isVillageStatusCodeエピローグ()) {
            form.setMessageType(CDef.MessageType.通常発言.code());
            if (villageInfo.isSpectator()) {
                form.setMessageType(CDef.MessageType.見学発言.code());
            }
        } else if (sayFormDto.getIsAvailableWerewolfSay()) {
            form.setMessageType(CDef.MessageType.人狼の囁き.code());
        } else if (sayFormDto.getIsAvailableMasonSay()) {
            form.setMessageType(CDef.MessageType.共鳴発言.code());
        } else if (sayFormDto.getIsAvailableMonologueSay()) {
            form.setMessageType(CDef.MessageType.独り言.code());
        } else if (sayFormDto.getIsAvailableNormalSay()) {
            form.setMessageType(CDef.MessageType.通常発言.code());
        } else if (sayFormDto.getIsAvailableGraveSay()) {
            form.setMessageType(CDef.MessageType.死者の呻き.code());
        } else if (sayFormDto.getIsAvailableSpectateSay()) {
            form.setMessageType(CDef.MessageType.見学発言.code());
        }
        form.setFaceType(detectDefaultFaceType(villageInfo, CDef.MessageType.codeOf(form.getMessageType())).code());
        return form;
    }

    // 発言
    private VillageSayFormDto convertToSayForm(VillageInfo villageInfo) {
        VillageSayFormDto say = new VillageSayFormDto();
        boolean isDispSayForm = villageDispLogic.isDispSayForm(villageInfo);
        if (!isDispSayForm) {
            say.setIsDispSayForm(false);
            say.setAllSayTypeAvailable(false);
            return say;
        }
        say.setRestrict(villageDispLogic.makeRestrict(villageInfo));
        say.setFaceTypeList(isDispSayForm ? makeFaceTypeCodeList(villageInfo) : null);
        say.setIsDispSayForm(true);

        // 管理者は全て発言できる
        boolean isAllSayAvailable = isDispSayForm && villageInfo.isAdmin();
        if (isAllSayAvailable) {
            say.setAllSayTypeAvailable(true);
            return say;
        }
        Village village = villageInfo.village;
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        say.setIsAvailableNormalSay(villageDispLogic.isAvailableNormalSay(village, vPlayer)); // 通常発言可能か
        say.setIsAvailableWerewolfSay(villageDispLogic.isAvailableWerewolfSay(village, vPlayer)); // 囁き可能か
        say.setIsAvailableMasonSay(villageDispLogic.isAvailableMasonSay(village, vPlayer)); // 共有者発言可能か
        say.setIsAvailableGraveSay(villageDispLogic.isAvailableGraveSay(village, vPlayer)); // 死者の呻きが発言可能か
        say.setIsAvailableSpectateSay(villageDispLogic.isAvailableSpectateSay(village, vPlayer)); // 見学発言が発言可能か
        say.setIsAvailableMonologueSay(villageDispLogic.isAvailableMonologueSay(village)); // 独り言が発言可能か
        // 秘話
        boolean availableSecretSay = isAvailableSecretSay(villageInfo);
        say.setIsAvailableSecretSay(availableSecretSay);
        say.setSecretSayTargetList(availableSecretSay ? villageDispLogic.makeSecretSayTargetList(villageInfo) : null);
        return say;
    }

    // 参戦
    // 役職希望変更
    // 退村
    private void setParticipateForm(Model model, VillageParticipateFormDto participate, OptionalThing<VillagePlayer> optVillagePlayer,
            VillageParticipateForm participateForm, VillageChangeRequestSkillForm changeRequestSkillForm) {
        if (participate.getIsDispParticipateForm()) {
            model.addAttribute("participateForm", participateForm == null ? new VillageParticipateForm() : participateForm);
            return;
        }
        if (participate.getIsDispChangeRequestSkillForm()) {
            VillageChangeRequestSkillForm requestSkillForm =
                    changeRequestSkillForm == null ? makeChangeRequestSkillForm(optVillagePlayer.get()) : changeRequestSkillForm;
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
        commit.setIsDispCommitForm(villageDispLogic.isDispCommitForm(villageInfo));
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
        boolean isDispParticipateForm = villageDispLogic.isDispParticipateForm(villageInfo);
        participate.setIsDispParticipateForm(isDispParticipateForm);
        participate.setSelectableCharaList(isDispParticipateForm
                ? selectSelectableCharaList(villageInfo.villageId).stream().map(chara -> convertToCharaPart(chara)).collect(
                        Collectors.toList())
                : null); // 入村可能なキャラ
        participate.setIsDispChangeRequestNgMessage(villageDispLogic.isDispChangeRequestSkillNgMessage(villageInfo));
        // 役職希望変更
        boolean isDispChangeRequestSkillForm = villageDispLogic.isDispChangeRequestSkillForm(villageInfo);
        participate.setIsDispChangeRequestSkillForm(isDispChangeRequestSkillForm);

        // 参戦、役職希望変更時に選べる役職
        participate.setSelectableSkillList(isDispParticipateForm || isDispChangeRequestSkillForm
                ? selectSelectableSkillList(villageInfo).stream().map(skill -> convertToSkillPart(skill)).collect(Collectors.toList())
                : null);

        // 退村
        participate.setIsDispLeaveVillageForm(villageDispLogic.isDispLeaveVillageForm(villageInfo));
        return participate;
    }

    private VillageParticipateDto convertToMyself(VillageInfo villageInfo) {
        VillageParticipateDto myself = new VillageParticipateDto();
        OptionalThing<VillagePlayer> optVillagePlayer = villageInfo.optVillagePlayer;
        // 参加情報
        myself.setCharaImageUrl(optVillagePlayer.map(vp -> vp.getChara()
                .get()
                .getCharaImageList()
                .stream()
                .filter(im -> im.isFaceTypeCode通常())
                .findFirst()
                .get()
                .getCharaImgUrl()).orElse(null));
        myself.setCharaName(optVillagePlayer.map(vp -> CharaUtil.makeCharaName(vp)).orElse(null));
        myself.setCharaImageWidth(optVillagePlayer.map(vp -> vp.getChara().get().getDisplayWidth()).orElse(null));
        myself.setCharaImageHeight(optVillagePlayer.map(vp -> vp.getChara().get().getDisplayHeight()).orElse(null));
        myself.setIsDead(optVillagePlayer.map(VillagePlayer::isIsDeadTrue).orElse(null));
        myself.setSkill(optVillagePlayer.map(vp -> convertToParticipateSkill(vp.getSkillCodeAsSkill())).orElse(null));
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
        );
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
        // 襲撃・呪殺・罠死・爆死
        VillageMemberDto attackedMember = new VillageMemberDto();
        List<VillagePlayer> attackedMemberList = villagePlayerList.stream().filter(vp -> {
            if (!vp.getDeadReason().isPresent()) {
                return false;
            }
            CDef.DeadReason reason = CDef.DeadReason.codeOf(vp.getDeadReason().get().getDeadReasonCode());
            return reason == CDef.DeadReason.襲撃 || reason == CDef.DeadReason.呪殺 || reason == CDef.DeadReason.罠死
                    || reason == CDef.DeadReason.爆死;
        }).collect(Collectors.toList());
        attackedMember.setStatus("犠牲");
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
        villageInfo.vPlayerList.stream().filter(vp -> {
            return vp.isIsSpectatorFalse() && vp.getRoomNumber().equals(roomNum);
        }).findFirst().ifPresent(vp -> {
            Chara chara = vp.getChara().get();
            room.setCharaName(chara.getCharaShortName());
            room.setCharaImgUrl(CharaUtil.getNormalCharaImgUrl(chara));
            room.setCharaImgWidth(chara.getDisplayWidth());
            room.setCharaImgHeight(chara.getDisplayHeight());
            room.setIsDummy(chara.getCharaId().equals(villageInfo.settings.getDummyCharaId()));
            if (vp.getDeadDay() != null && vp.getDeadDay() <= villageInfo.day) {
                room.setIsDead(true);
                room.setDeadDay(vp.getDeadDay());
                room.setDeadReason(vp.getDeadReasonCode());
            } else {
                room.setIsDead(false);
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
        detail.setCharaName(CharaUtil.makeCharaName(mem));
        detail.setDeadDay(mem.getDeadDay() != null ? mem.getDeadDay() + "d" : null);
        detail.setLastAccessDatetime(mem.getLastAccessDatetime());
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
        part.setUrl(CharaUtil.getNormalCharaImgUrl(chara));
        part.setWidth(chara.getDisplayWidth());
        part.setHeight(chara.getDisplayHeight());
        return part;
    }

    private VillageSettingsDto convertToSettings(VillageInfo villageInfo) {
        VillageSettings settings = villageInfo.settings;
        List<VillageDay> dayList = villageInfo.dayList;
        VillageSettingsDto part = new VillageSettingsDto();
        part.setStartDatetime(dayList.get(0).getDaychangeDatetime()); // 0日目の切り替え日時
        part.setStartPersonMinNum(settings.getStartPersonMinNum());
        part.setPersonMaxNum(settings.getPersonMaxNum());
        part.setCharaGroupId(settings.getCharacterGroupId());
        part.setCharaGroupName(settings.getCharaGroup().get().getCharaGroupName());
        part.setDummyCharaName(CharaUtil.makeCharaName(
                villageInfo.vPlayerList.stream().filter(vp -> vp.getCharaId().equals(settings.getDummyCharaId())).findFirst().get()));
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
        part.setOrganization(makeDisplayOrganization(settings.getOrganize(), villageInfo));
        part.setAllowedSecretSayCode(settings.getAllowedSecretSayCode());
        part.setSayRestrictList(convertToRestrictList(villageInfo.village.getMessageRestrictionList()));
        part.setIsSkillRequestAvailable(settings.getIsPossibleSkillRequest());
        part.setCreatePlayerName(villageInfo.village.getCreatePlayerName());
        return part;
    }

    // 人数：構成の表示にする
    private String makeDisplayOrganization(String organize, VillageInfo villageInfo) {
        if (villageInfo.day == 0) {
            return String.join("\n", Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).map(org -> {
                return String.format("%02d人: %s", org.length(), org);
            }).collect(Collectors.toList()));
        } else {
            int memberNum = villageInfo.getVPList(false, true, false).size();
            return String.format("%02d人: %s", memberNum,
                    Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).filter(org -> org.length() == memberNum).findFirst().get());
        }
    }

    private String makeDayChangeIntervalStr(Integer interval) {
        String hour = interval >= 3600 ? String.format("%02d時間", interval / 3600) : "";
        String minute = interval >= 60 ? String.format("%02d分", (interval % 3600) / 60) : "";
        String second = String.format("%02d秒", interval % 60);
        return hour + minute + second;
    }

    private List<NewVillageSayRestrictDto> convertToRestrictList(List<MessageRestriction> registeredRestrictList) {
        return SkillUtil.SORTED_SKILL_LIST.stream().map(skill -> {
            NewVillageSayRestrictDto restrict = new NewVillageSayRestrictDto();
            restrict.setSkillName(skill.name());
            restrict.setSkillCode(skill.code());
            restrict.setDetailList(createDetailList(skill, registeredRestrictList));
            return restrict;
        }).collect(Collectors.toList());
    }

    private List<NewVillageSayRestrictDetailDto> createDetailList(CDef.Skill skill, List<MessageRestriction> registeredRestrictList) {
        List<NewVillageSayRestrictDetailDto> detailList = new ArrayList<>();
        detailList.add(createDetail("通常発言", CDef.MessageType.通常発言.code(), skill, registeredRestrictList));
        if (skill.isAvailableWerewolfSay()) {
            detailList.add(createDetail("囁き", CDef.MessageType.人狼の囁き.code(), skill, registeredRestrictList));
        } else if (skill == CDef.Skill.共鳴者) {
            detailList.add(createDetail("共鳴発言", CDef.MessageType.共鳴発言.code(), skill, registeredRestrictList));
        }
        return detailList;
    }

    private NewVillageSayRestrictDetailDto createDetail(String messageTypeName, String messageTypeCode, CDef.Skill skill,
            List<MessageRestriction> registeredRestrictList) {
        NewVillageSayRestrictDetailDto detail = new NewVillageSayRestrictDetailDto();
        detail.setMessageTypeName(messageTypeName);
        detail.setMessageTypeCode(messageTypeCode);
        detail.setIsRestrict(false);
        Optional<MessageRestriction> optDbRes = registeredRestrictList.stream()
                .filter(dbRes -> dbRes.getMessageTypeCode().equals(messageTypeCode) && dbRes.getSkillCode().equals(skill.code()))
                .findFirst();
        if (optDbRes.isPresent()) {
            detail.setIsRestrict(true);
            detail.setCount(optDbRes.get().getMessageMaxNum());
            detail.setLength(optDbRes.get().getMessageMaxLength());
        }
        return detail;
    }

    private VillageVoteDto makeMemberVote(VillageInfo villageInfo) {
        List<Vote> voteList = villageInfo.voteList;
        List<VillageMemberVoteDto> memberVoteDtoList = villageInfo.getVPList(false, true, true).stream().map(vp -> {
            VillageMemberVoteDto voteDto = new VillageMemberVoteDto();
            voteDto.setCharaName(String.format("%02d%s", vp.getRoomNumber(), vp.getChara().get().getCharaShortName()));
            List<String> voteTargetList = voteList.stream().filter(v -> v.getCharaId().equals(vp.getCharaId())).map(v -> {
                VillagePlayer vPlayer = extractVillagePlayerBy(villageInfo, v.getVoteCharaId());
                return String.format("%02d%s", vPlayer.getRoomNumber(), v.getCharaByVoteCharaId().get().getCharaShortName());
            }).collect(Collectors.toList());
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

    private VillagePlayer extractVillagePlayerBy(VillageInfo villageInfo, Integer charaId) {
        return villageInfo.getVPList(false, false, false).stream().filter(vp -> vp.getCharaId().equals(charaId)).findFirst().orElse(null);
    }

    // 開発用機能
    private void setDebugInfo(Boolean debug, Model model) {
        model.addAttribute("isDebugMode", debug);
    }
}
