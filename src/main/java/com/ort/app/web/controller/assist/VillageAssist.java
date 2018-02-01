package com.ort.app.web.controller.assist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.dbflute.optional.OptionalThing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;

import com.ort.app.web.controller.logic.VillageLogic;
import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.form.VillageAbilityForm;
import com.ort.app.web.form.VillageGetMessageListForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.form.VillageSayForm;
import com.ort.app.web.model.VillageMessageListResultContent;
import com.ort.app.web.model.VillageResultContent;
import com.ort.app.web.model.inner.VillageCharaDto;
import com.ort.app.web.model.inner.VillageMemberDetailDto;
import com.ort.app.web.model.inner.VillageMemberDto;
import com.ort.app.web.model.inner.VillageMessageDto;
import com.ort.app.web.model.inner.VillageRoomAssignedDto;
import com.ort.app.web.model.inner.VillageRoomAssignedRowDto;
import com.ort.app.web.model.inner.VillageSkillDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.MessageBhv;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillageSettingsBhv;
import com.ort.dbflute.exentity.Ability;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Message;
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
    private PlayerBhv playerBhv;

    @Autowired
    private SkillBhv skillBhv;

    @Autowired
    private AbilityBhv abilityBhv;

    @Autowired
    private FootstepBhv footstepBhv;

    @Autowired
    private MessageBhv messageBhv;

    @Autowired
    private VillageLogic villageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void setIndexModel(Integer villageId, int day, VillageSayForm sayForm, Model model) {
        Village village = selectVillage(villageId);
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo(); // ログインしているか
        boolean isDispParticipateForm = isDispParticipateForm(day, userInfo); // 参戦フォームを表示するか
        List<Chara> selectableCharaList = isDispParticipateForm ? selectSelectableCharaList(villageId) : null; // 参戦可能なキャラ
        List<Skill> selectableSkillList = isDispParticipateForm ? selectSelectableSkillList(villageId) : null; // 希望役職に選べる役職
        model.addAttribute("participateForm", new VillageParticipateForm());
        ListResultBean<VillageDay> dayList = villageDayBhv.selectList(cb -> cb.query().setVillageId_Equal(villageId));
        OptionalThing<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo);
        boolean isDispSayForm = isDispSayForm(villageId, userInfo, optVillagePlayer, day, dayList); // 発言フォームを表示するか
        boolean isAvailableNormalSay = isDispSayForm && isAvailableNormalSay(village, optVillagePlayer.get()); // 通常発言可能か
        boolean isAvailableWerewolfSay = isDispSayForm && isAvailableWerewolfSay(village, optVillagePlayer.get()); // 囁き可能か
        boolean isAvailableMasonSay = isDispSayForm && isAvailableMasonSay(village, optVillagePlayer.get()); // 共有者発言可能か
        boolean isAvailableGraveSay = isDispSayForm && isAvailableGraveSay(village, optVillagePlayer.get()); // 死者の呻きが発言可能か
        boolean isAvailableMonologueSay = isDispSayForm && isAvailableMonologueSay(village); // 独り言が発言可能か
        setDefaultMessageTypeIfNeeded(sayForm, isDispSayForm, isAvailableNormalSay, isAvailableWerewolfSay, isAvailableMasonSay,
                isAvailableGraveSay, isAvailableMonologueSay, model); // デフォルト発言区分
        List<Chara> abilityTargetList = makeAbilityTargetList(village, optVillagePlayer, day, isLatestDay(day, dayList));
        List<Chara> attackerList = makeAttackerList(village, optVillagePlayer, day, isLatestDay(day, dayList));
        setAbilityTarget(villageId, village, abilityTargetList, optVillagePlayer, day, model);
        List<Chara> voteTargetList = makeVoteTargetList(village, optVillagePlayer, day, isLatestDay(day, dayList));

        VillageResultContent content = mappingToContent(village, isDispParticipateForm, selectableCharaList, selectableSkillList,
                isDispSayForm, day, dayList, isAvailableNormalSay, isAvailableWerewolfSay, isAvailableMasonSay, isAvailableGraveSay,
                isAvailableMonologueSay, optVillagePlayer, abilityTargetList, attackerList, voteTargetList);
        model.addAttribute("content", content);
    }

    public void setConfirmModel(Integer villageId, Model model) {
        Village village = selectVillage(villageId);
        model.addAttribute("villageName", village.getVillageDisplayName());

    }

    public VillageMessageListResultContent getMessageList(VillageGetMessageListForm form) {
        Integer villageId = form.getVillageId();
        int day = form.getDay() != null ? form.getDay() : selectLatestDay(villageId);
        ListResultBean<Message> messageList = selectMessageList(form.getVillageId(), day);
        VillageMessageListResultContent content = mappingToMessageListContent(messageList);
        return content;
    }

    public void assertAlreadyParticipateChara(Integer villageId, VillageParticipateForm participateForm)
            throws WerewolfMansionBusinessException {
        int participateCount = villagePlayerBhv.selectCount(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setCharaId_Equal(participateForm.getCharaId());
        });
        if (participateCount > 0) {
            throw new WerewolfMansionBusinessException("既に参加されているキャラクターです。別のキャラクターを選択してください。");
        }
    }

    // 参戦する
    public void participate(Integer villageId, VillageParticipateForm participateForm, UserInfo userInfo) {
        Integer playerId =
                playerBhv.selectEntityWithDeletedCheck(cb -> cb.query().setPlayerName_Equal(userInfo.getUsername())).getPlayerId();
        villageLogic.participate(villageId, playerId, participateForm.getCharaId(), CDef.Skill.codeOf(participateForm.getRequestedSkill()),
                participateForm.getJoinMessage());
    }

    // 発言可能かチェック
    public boolean isAvailableSay(Integer villageId, UserInfo userInfo, VillageSayForm sayForm) {
        CDef.MessageType type = CDef.MessageType.codeOf(sayForm.getMessageType());
        if (type == null) {
            throw new IllegalArgumentException("発言種別が改ざんされている");
        }
        Village village = selectVillage(villageId);
        VillagePlayer villagePlayer = selectVillagePlayer(villageId, userInfo).orElseThrow(() -> {
            return new IllegalArgumentException("セッション切れ？");
        });
        switch (type) {
        case 人狼の囁き:
            return isAvailableWerewolfSay(village, villagePlayer);
        case 通常発言:
            return isAvailableNormalSay(village, villagePlayer);
        case 共鳴発言:
            return isAvailableMasonSay(village, villagePlayer);
        case 死者の呻き:
            return isAvailableGraveSay(village, villagePlayer);
        case 独り言:
            return isAvailableMonologueSay(village);
        default:
            throw new IllegalArgumentException("発言種別が改ざんされている type: " + type);
        }

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
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        villageBhv.loadVillagePlayer(village, villagePlayerCB -> { // 参加者一覧用
            villagePlayerCB.setupSelect_Chara();
            villagePlayerCB.setupSelect_SkillBySkillCode();
            villagePlayerCB.setupSelect_DeadReason();
            villagePlayerCB.query().addOrderBy_DeadDay_Asc();
        });
        return village;
    }

    private ListResultBean<Message> selectMessageList(Integer villageId, Integer day) {
        return messageBhv.selectList(cb -> {
            cb.setupSelect_VillagePlayer().withPlayer();
            cb.setupSelect_VillagePlayer().withChara();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().addOrderBy_MessageDatetime_Asc();
            cb.query().addOrderBy_MessageId_Asc();
        });
    }

    private List<Skill> selectSelectableSkillList(Integer villageId) {
        // TODO h-orito いったん全役職 (2017/12/25)
        return skillBhv.selectList(cb -> {});
    }

    public OptionalThing<VillagePlayer> selectVillagePlayer(Integer villageId, UserInfo userInfo) {
        if (userInfo == null) {
            return OptionalThing.empty();
        }
        return villagePlayerBhv.selectEntity(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.getUsername());
            cb.orScopeQuery(orCB -> {
                orCB.query().setDeadReasonCode_IsNull();
                orCB.query().setDeadReasonCode_NotEqual_突然();
            });
            cb.query().queryVillage().setVillageStatusCode_NotInScope_AsVillageStatus(
                    Arrays.asList(CDef.VillageStatus.廃村, CDef.VillageStatus.終了));
        });
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageResultContent mappingToContent(Village village, boolean isDispParticipateForm, List<Chara> selectableCharaList,
            List<Skill> selectableSkillList, boolean isDispSayForm, int day, ListResultBean<VillageDay> dayList,
            boolean isAvailableNormalSay, boolean isAvailableWerewolfSay, boolean isAvailableMasonSay, boolean isAvailableGraveSay,
            boolean isAvailableMonologueSay, OptionalThing<VillagePlayer> optVillagePlayer, List<Chara> abilityTargetList,
            List<Chara> attackerList, List<Chara> voteTargetList) {
        VillageResultContent content = new VillageResultContent();
        content.setVillageId(village.getVillageId());
        content.setVillageName(village.getVillageDisplayName());
        content.setDay(day);
        content.setDayList(dayList.stream().map(VillageDay::getDay).collect(Collectors.toList()));
        content.setEpilogueDay(village.getEpilogueDay());
        content.setIsDispParticipateForm(isDispParticipateForm);
        content.setSelectableCharaList(selectableCharaList == null ? null
                : selectableCharaList.stream().map(chara -> convertToCharaPart(chara)).collect(Collectors.toList()));
        content.setSelectableSkillList(selectableSkillList == null ? null
                : selectableSkillList.stream().map(skill -> convertToSkillPart(skill)).collect(Collectors.toList()));
        content.setMemberList(convertToMemberPart(village.getVillagePlayerList()));
        content.setRoomAssignedRowList(convertToRoomAssignedPart(village, village.getVillagePlayerList()));
        content.setRoomWidth(village.getRoomSizeWidth());
        content.setIsDispSayForm(isDispSayForm);
        content.setIsAvailableNormalSay(isAvailableNormalSay);
        content.setIsAvailableWerewolfSay(isAvailableWerewolfSay);
        content.setIsAvailableMasonSay(isAvailableMasonSay);
        content.setIsAvailableGraveSay(isAvailableGraveSay);
        content.setIsAvailableMonologueSay(isAvailableMonologueSay);
        content.setCharaImageUrl(optVillagePlayer.map(vp -> vp.getChara().get().getCharaImgUrl()).orElse(null));
        content.setAbilityTargetList(abilityTargetList);
        content.setAttackerList(attackerList);
        content.setVoteTargetList(voteTargetList);
        content.setIsDead(optVillagePlayer.map(VillagePlayer::isIsDeadTrue).orElse(null));
        content.setSkillName(optVillagePlayer.map(vp -> vp.getSkillCode()).orElse(null));

        return content;
    }

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
        return Arrays.asList(aliveMember, executedMember, attackedMember, suddonlyDeathMember);
    }

    private List<VillageRoomAssignedRowDto> convertToRoomAssignedPart(Village village, List<VillagePlayer> villagePlayerList) {
        if (villagePlayerList.stream().anyMatch(vp -> vp.getRoomNumber() == null)) {
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
                villagePlayerList.stream().filter(vp -> vp.getRoomNumber().equals(roomNum)).findFirst().ifPresent(vp -> {
                    room.setCharaName(vp.getChara().get().getCharaShortName());
                    room.setCharaImgUrl(vp.getChara().get().getCharaImgUrl());
                    room.setIsDead(BooleanUtils.isTrue(vp.getIsDead()));
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

    private VillageMessageListResultContent mappingToMessageListContent(ListResultBean<Message> messageList) {
        VillageMessageListResultContent content = new VillageMessageListResultContent();
        content.setMessageList(convertToMessageList(messageList));
        return content;
    }

    private List<VillageMessageDto> convertToMessageList(List<Message> messageList) {
        return messageList.stream().map(message -> {
            VillageMessageDto messageDto = new VillageMessageDto();
            messageDto.setCharacterName(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaName()).orElse(null));
            messageDto.setCharacterImageUrl(
                    message.getVillagePlayer().map(villagePlayer -> villagePlayer.getChara().get().getCharaImgUrl()).orElse(null));
            // エピ入ってないと表示しちゃだめ
            // messageDto.setPlayerName(
            //         message.getVillagePlayer().map(villagePlayer -> villagePlayer.getPlayer().get().getPlayerName()).orElse(null));
            messageDto.setMessageContent(message.getMessageContent());
            messageDto.setMessageDatetime(message.getMessageDatetime());
            messageDto.setMessageNumber(message.getMessageNumber());
            messageDto.setMessageType(message.getMessageTypeCodeAsMessageType().code());
            return messageDto;
        }).collect(Collectors.toList());
    }

    // 参戦フォームを表示するか
    private boolean isDispParticipateForm(int day, UserInfo userInfo) {
        // ログインしていない場合は表示しない
        if (userInfo == null) {
            return false;
        }
        // 0日目でない場合表示しない
        if (day != 0) {
            return false;
        }
        // 決着のついていない村に参戦している場合表示しない
        int participateCount = playerBhv.selectCount(cb -> {
            cb.query().setPlayerName_Equal(userInfo.getUsername());
            // 募集中、開始待ち、進行中の村に参戦している
            cb.query().existsVillagePlayer(villagePlayerCB -> {
                villagePlayerCB.query().queryVillage().setVillageStatusCode_InScope_AsVillageStatus(
                        Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
            });
        });
        if (participateCount > 0) {
            return false;
        }
        return true;
    }

    // 発言フォームを表示するか
    private boolean isDispSayForm(Integer villageId, UserInfo userInfo, OptionalThing<VillagePlayer> optVillagePlayer, int day,
            ListResultBean<VillageDay> dayList) {
        // ログインしていない場合は表示しない
        if (userInfo == null) {
            return false;
        }
        // この村に参戦していない場合は表示しない
        if (!optVillagePlayer.isPresent()) {
            return false;
        }

        // 最新日以外は表示しない
        if (!isLatestDay(day, dayList)) {
            return false;
        }
        return true;
    }

    // 最新日か
    private boolean isLatestDay(int day, ListResultBean<VillageDay> dayList) {
        return dayList.get(dayList.size() - 1).getDay().equals(day);
    }

    private boolean isAvailableNormalSay(Village village, VillagePlayer villagePlayer) {
        // エピローグ以外で死亡している場合は不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead()) && !village.isVillageStatusCodeエピローグ()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableWerewolfSay(Village village, VillagePlayer villagePlayer) {
        // 狼とC狂以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼 && skill != CDef.Skill.C国狂人) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMasonSay(Village village, VillagePlayer villagePlayer) {
        // 共有以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.共有者) {
            return false;
        }
        // 死亡していたら不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableGraveSay(Village village, VillagePlayer villagePlayer) {
        // 死亡していなかったら不可
        if (!BooleanUtils.isTrue(villagePlayer.getIsDead())) {
            return false;
        }
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    private boolean isAvailableMonologueSay(Village village) {
        // 進行中以外は不可
        if (!village.isVillageStatusCode進行中()) {
            return false;
        }
        return true;
    }

    // 参戦キャラとして選択可能なキャラを取得
    private List<Chara> selectSelectableCharaList(Integer villageId) {
        // 既に参加しているキャラ
        List<Integer> alreadyParticipatePlayerIdList = villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
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

    // デフォルト発言区分
    private void setDefaultMessageTypeIfNeeded(VillageSayForm sayForm, boolean isDispSayForm, boolean isAvailableNormalSay,
            boolean isAvailableWerewolfSay, boolean isAvailableMasonSay, boolean isAvailableGraveSay, boolean isAvailableMonologueSay,
            Model model) {
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
        } else {
            form.setMessageType(CDef.MessageType.独り言.code());
        }
        model.addAttribute("sayForm", form);
    }

    private void setAbilityTarget(Integer villageId, Village village, List<Chara> abilityTargetList,
            OptionalThing<VillagePlayer> optVillagePlayer, int day, Model model) {
        if (CollectionUtils.isEmpty(abilityTargetList)) {
            return;
        }
        CDef.Skill skill = optVillagePlayer.get().getSkillCodeAsSkill();
        CDef.AbilityType type =
                skill == CDef.Skill.人狼 ? CDef.AbilityType.襲撃 : skill == CDef.Skill.占い師 ? CDef.AbilityType.占い : CDef.AbilityType.護衛;
        OptionalEntity<Ability> optAbility = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setAbilityTypeCode_Equal_AsAbilityType(type);
        });

        VillageAbilityForm abilityForm = new VillageAbilityForm();
        optAbility.ifPresent(ab -> {
            abilityForm.setCharaId(ab.getCharaId());
            model.addAttribute("charaName", getCharacterNameFromCharaId(village, ab.getCharaId()));
            abilityForm.setTargetCharaId(ab.getTargetCharaId());
            model.addAttribute("targetCharaName", getCharacterNameFromCharaId(village, ab.getTargetCharaId()));
            OptionalEntity<Footstep> optFootstep = footstepBhv.selectEntity(cb -> {
                cb.query().setVillageId_Equal(villageId);
                cb.query().setDay_Equal(day);
                cb.query().setCharaId_Equal(ab.getCharaId());
            });
            optFootstep.ifPresent(fs -> {
                abilityForm.setFootstep(fs.getFootstepRoomNumbers());
                model.addAttribute("footstep", fs.getFootstepRoomNumbers());
            });
        });
        model.addAttribute("abilityForm", abilityForm);
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

    private List<Chara> makeVoteTargetList(Village village, OptionalThing<VillagePlayer> optVillagePlayer, int day, boolean isLatestDay) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || !isLatestDay || !village.isVillageStatusCode進行中()
                || day == 1) {
            return null;
        }
        return village.getVillagePlayerList().stream().filter(vp -> vp.isIsDeadFalse()).map(vp -> vp.getChara().get()).collect(
                Collectors.toList());
    }

    private List<Chara> makeAbilityTargetList(Village village, OptionalThing<VillagePlayer> optVillagePlayer, int day,
            boolean isLatestDay) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || !isLatestDay || !village.isVillageStatusCode進行中()) {
            return null;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (skill == CDef.Skill.人狼) {
            if (day == 1) {
                // ダミーキャラ固定
                return village.getVillagePlayerList()
                        .stream()
                        .filter(vp -> vp.getChara().get().isIsDummyTrue())
                        .map(vp -> vp.getChara().get())
                        .collect(Collectors.toList());
            } else {
                // 狼以外の生存しているプレイヤー全員
                return village.getVillagePlayerList()
                        .stream()
                        .filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() != CDef.Skill.人狼)
                        .map(vp -> vp.getChara().get())
                        .collect(Collectors.toList());
            }
        } else if (skill == CDef.Skill.占い師 || (skill == CDef.Skill.狩人 && day > 1)) {
            // 自分以外の生存しているプレイヤー全員
            return village.getVillagePlayerList()
                    .stream()
                    .filter(vp -> vp.isIsDeadFalse() && !vp.getVillagePlayerId().equals(vPlayer.getVillagePlayerId()))
                    .map(vp -> vp.getChara().get())
                    .collect(Collectors.toList());
        }
        return null;
    }

    private List<Chara> makeAttackerList(Village village, OptionalThing<VillagePlayer> optVillagePlayer, int day, boolean isLatestDay) {
        if (!optVillagePlayer.isPresent() || optVillagePlayer.get().isIsDeadTrue() || !isLatestDay || !village.isVillageStatusCode進行中()) {
            return null;
        }
        VillagePlayer vPlayer = optVillagePlayer.get();
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.人狼) {
            return null;
        }
        // 昨日襲撃した狼
        Integer yesterdayAttackerId = abilityBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(village.getVillageId());
            cb.query().setDay_Equal(day - 1);
            cb.query().setAbilityTypeCode_Equal_襲撃();
        }).map(ab -> ab.getCharaId()).orElse(null);
        // 生存している狼リスト
        List<Chara> liveAttackerList = village.getVillagePlayerList()
                .stream()
                .filter(vp -> vp.isIsDeadFalse() && vp.getSkillCodeAsSkill() == CDef.Skill.人狼)
                .map(vp -> vp.getChara().get())
                .collect(Collectors.toList());
        // 生存している狼が1名ではない場合は、昨日襲撃した狼は襲撃できない
        if (liveAttackerList.size() > 1) {
            return liveAttackerList.stream().filter(attacker -> !attacker.getCharaId().equals(yesterdayAttackerId)).collect(
                    Collectors.toList());
        } else {
            return liveAttackerList;
        }
    }
}
