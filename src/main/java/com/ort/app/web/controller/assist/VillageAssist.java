package com.ort.app.web.controller.assist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
import com.ort.app.web.model.inner.VillageSettingsDto;
import com.ort.app.web.model.inner.VillageSkillDto;
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
    private static final List<CDef.Skill> SET_AVAILABLE_SKILLS = Arrays.asList(CDef.Skill.人狼, CDef.Skill.占い師, CDef.Skill.賢者, CDef.Skill.狩人,
            CDef.Skill.狂人, CDef.Skill.妖狐, CDef.Skill.魔神官, CDef.Skill.C国狂人, CDef.Skill.狂信者, CDef.Skill.探偵);
    private static final List<CDef.Skill> RESTRICT_SKILLS =
            Arrays.asList(CDef.Skill.村人, CDef.Skill.霊能者, CDef.Skill.導師, CDef.Skill.人狼, CDef.Skill.C国狂人, CDef.Skill.占い師, CDef.Skill.賢者,
                    CDef.Skill.狩人, CDef.Skill.探偵, CDef.Skill.共鳴者, CDef.Skill.狂人, CDef.Skill.魔神官, CDef.Skill.狂信者, CDef.Skill.妖狐);

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

    // デフォルト発言区分
    private void setDefaultMessageTypeIfNeeded(VillageSayForm sayForm, boolean isDispSayForm, boolean isAvailableNormalSay,
            boolean isAvailableWerewolfSay, boolean isAvailableMasonSay, boolean isAvailableGraveSay, boolean isAvailableMonologueSay,
            boolean isAvailableSpectateSay, VillageInfo villageInfo, Model model) {
        if (sayForm != null || !isDispSayForm) {
            return;
        }
        VillageSayForm form = new VillageSayForm();
        if (villageInfo.village.isVillageStatusCodeエピローグ()) {
            form.setMessageType(CDef.MessageType.通常発言.code());
            if (villageInfo.isSpectator()) {
                form.setMessageType(CDef.MessageType.見学発言.code());
            }
        } else if (isAvailableWerewolfSay) {
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
        form.setFaceType(detectDefaultFaceType(villageInfo, CDef.MessageType.codeOf(form.getMessageType())).code());
        model.addAttribute("sayForm", form);
    }

    private CDef.FaceType detectDefaultFaceType(VillageInfo villageInfo, CDef.MessageType defaultMessageType) {
        List<CharaImage> faceList = villageInfo.optVillagePlayer.get().getChara().get().getCharaImageList();
        CDef.FaceType expectFaceType = null;
        if (defaultMessageType == CDef.MessageType.通常発言) {
            expectFaceType = CDef.FaceType.通常;
        } else if (defaultMessageType == CDef.MessageType.人狼の囁き) {
            expectFaceType = CDef.FaceType.囁き;
        } else if (defaultMessageType == CDef.MessageType.共鳴発言) {
            expectFaceType = CDef.FaceType.共鳴;
        } else if (defaultMessageType == CDef.MessageType.独り言) {
            expectFaceType = CDef.FaceType.独り言;
        } else if (defaultMessageType == CDef.MessageType.死者の呻き) {
            expectFaceType = CDef.FaceType.墓下;
        } else if (defaultMessageType == CDef.MessageType.見学発言) {
            expectFaceType = CDef.FaceType.通常;
        }
        final String expectedCode = expectFaceType.code();
        if (faceList.stream().anyMatch(face -> face.getFaceTypeCodeAsFaceType().code().equals(expectedCode))) {
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
        if (!SET_AVAILABLE_SKILLS.contains(skill)) {
            return;
        }

        VillageAbilityForm abilityForm = new VillageAbilityForm();
        CDef.AbilityType type = skill == CDef.Skill.人狼 ? CDef.AbilityType.襲撃 //
                : skill.isHasDivineAbility() ? CDef.AbilityType.占い //
                        : skill == CDef.Skill.狩人 ? CDef.AbilityType.護衛 // 
                                : skill == CDef.Skill.探偵 ? CDef.AbilityType.捜査 : null;
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

    private void setSecretSayTarget(VillageResultContent content, VillageInfo villageInfo) {
        if (!villageInfo.isParticipate() || !villageInfo.isLatestDay() || !villageInfo.village.isVillageStatusCode進行中()) {
            return;
        }
        CDef.AllowedSecretSay allowedSecretSay = villageInfo.settings.getAllowedSecretSayCodeAsAllowedSecretSay();
        if (villageInfo.isAdmin() || allowedSecretSay == CDef.AllowedSecretSay.全員) {
            content.setIsAvailableSecretSay(true);
            content.setSecretSayTargetList(villageDispLogic.makeSecretSayTargetList(villageInfo));
        } else {
            content.setIsAvailableSecretSay(false);
        }
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
        content.setVillageSettings(convertToSettings(villageInfo));
        content.setSayRestrictList(convertToRestrictList(villageInfo.village.getMessageRestrictionList()));
        content.setIsSkillRequestAvailable(villageInfo.settings.getIsPossibleSkillRequest());
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
        // 参戦
        boolean isDispParticipateForm = villageDispLogic.isDispParticipateForm(villageInfo);
        setVillageModelParticipateForm(content, villageInfo.villageId, participateForm, model, isDispParticipateForm);
        // 役職希望変更
        content.setIsDispChangeRequestNgMessage(villageDispLogic.isDispChangeRequestSkillNgMessage(villageInfo));
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
        // 秘話
        setSecretSayTarget(content, villageInfo);
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
    private void setVillageModelCreateUser(VillageResultContent content, VillageInfo villageInfo, Model model) {
        String createPlayerName = villageInfo.village.getCreatePlayerName();
        content.setCreatePlayerName(createPlayerName);
        UserInfo userInfo = villageInfo.user;
        boolean isCreator = userInfo != null && userInfo.getUsername().equals(createPlayerName);
        content.setIsCreatePlayer(isCreator);
        content.setIsAvailableSettingsUpdate(
                userInfo != null && (userInfo.getUsername().equals(createPlayerName) || "master".equals(userInfo.getUsername()))
                        && villageInfo.village.isVillageStatusCode募集中());
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
            model.addAttribute("secondRequestSkillName", CDef.Skill.codeOf(changeRequestSkillForm.getSecondRequestedSkill()).alias());
            return changeRequestSkillForm;
        }
        VillageChangeRequestSkillForm form = new VillageChangeRequestSkillForm();
        form.setRequestedSkill(optVillagePlayer.get().getRequestSkillCode());
        form.setSecondRequestedSkill(optVillagePlayer.get().getSecondRequestSkillCode());
        model.addAttribute("requestSkillName", CDef.Skill.codeOf(optVillagePlayer.get().getRequestSkillCode()).alias());
        model.addAttribute("secondRequestSkillName", CDef.Skill.codeOf(optVillagePlayer.get().getSecondRequestSkillCode()).alias());
        return form;
    }

    private void setVillageModelPlayerInfo(VillageResultContent content, OptionalThing<VillagePlayer> optVillagePlayer) {
        content.setCharaImageUrl(optVillagePlayer.map(vp -> vp.getChara()
                .get()
                .getCharaImageList()
                .stream()
                .filter(im -> im.isFaceTypeCode通常())
                .findFirst()
                .get()
                .getCharaImgUrl()).orElse(null));
        content.setCharaImageWidth(optVillagePlayer.map(vp -> vp.getChara().get().getDisplayWidth()).orElse(null));
        content.setCharaImageHeight(optVillagePlayer.map(vp -> vp.getChara().get().getDisplayHeight()).orElse(null));
        content.setIsDead(optVillagePlayer.map(VillagePlayer::isIsDeadTrue).orElse(null));
        content.setIsSpectator(optVillagePlayer.map(VillagePlayer::isIsSpectatorTrue).orElse(null));
        content.setSkillName(optVillagePlayer.map(vp -> vp.getSkillCode()).orElse(null));
    }

    private void setVillageModelAbilityForm(VillageResultContent content, VillageInfo villageInfo, Model model) {
        content.setAbilityTargetList(villageDispLogic.makeAbilityTargetList(villageInfo));
        content.setAttackerList(villageDispLogic.makeAttackerList(villageInfo));
        content.setSkillHistoryList(villageDispLogic.makeSkillHistoryList(villageInfo));
        content.setWerewolfCharaNameList(villageDispLogic.makeWerewolfCharaNameList(villageInfo));
        content.setcMadmanCharaNameList(villageDispLogic.makeCMadmanCharaNameList(villageInfo));
        setAbilityTarget(villageInfo, model);
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
        content.setRestrict(villageDispLogic.makeRestrict(villageInfo));
        content.setFaceTypeList(isDispSayForm ? makeFaceTypeCodeList(villageInfo) : null);
        setDefaultMessageTypeIfNeeded(sayForm, isDispSayForm, isAvailableNormalSay, isAvailableWerewolfSay, isAvailableMasonSay,
                isAvailableGraveSay, isAvailableMonologueSay, isAvailableSpectateSay, villageInfo, model); // デフォルト発言区分
    }

    private List<OptionDto> makeFaceTypeCodeList(VillageInfo villageInfo) {
        return villageInfo.optVillagePlayer.get().getChara().get().getCharaImageList().stream().map(img -> {
            OptionDto option = new OptionDto();
            option.setName(img.getFaceTypeCodeAsFaceType().alias());
            option.setValue(img.getFaceTypeCode());
            return option;
        }).collect(Collectors.toList());
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
        return RESTRICT_SKILLS.stream().map(skill -> {
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
        if (skill == CDef.Skill.人狼 || skill == CDef.Skill.C国狂人) {
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
