package com.ort.app.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.model.inner.SayRestrictDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Message;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class SayLogic {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private MessageLogic messageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 秘話対象に選択できる相手
    public List<Chara> getSelectableSecretSayTarget(VillageInfo villageInfo) {
        if (!isAvailableSecretSay(villageInfo)) {
            return new ArrayList<>();
        }
        // 自分以外
        return villageInfo.vPlayers.filterNot(villageInfo.optVillagePlayer.get()).map(vp -> vp.getChara().get());
    }

    // 発言可能か（表示用）
    public boolean isAvailableSay(VillageInfo villageInfo) {
        // ログインしていない場合はNG
        if (villageInfo.user == null) {
            return false;
        }
        // この村に参戦していない場合はNG
        if (!villageInfo.isParticipate()) {
            return false;
        }
        // 最新日以外はNG
        if (!villageInfo.isLatestDay()) {
            return false;
        }
        // 突然死した場合はエピローグ以外NG
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        if (villagePlayer.getDeadReasonCodeAsDeadReason() == CDef.DeadReason.突然 && !villageInfo.village.isVillageStatusCodeエピローグ()) {
            return false;
        }
        return true;
    }

    // 通常発言可能か
    public boolean isAvailableNormalSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        Village village = villageInfo.village;
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        // エピローグ以外で死亡している場合は不可
        if (BooleanUtils.isTrue(villagePlayer.getIsDead()) && !village.isVillageStatusCodeエピローグ()) {
            return false;
        }
        // 見学は不可
        if (villagePlayer.isIsSpectatorTrue()) {
            return false;
        }
        return true;
    }

    // 囁き可能か
    public boolean isAvailableWerewolfSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        Village village = villageInfo.village;
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        // 狼とC狂以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill == null || !skill.isAvailableWerewolfSay()) {
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

    // 共鳴発言可能か
    public boolean isAvailableMasonSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        Village village = villageInfo.village;
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        // 共鳴以外は不可
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        if (skill != CDef.Skill.共鳴者) {
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

    // 恋人発言可能か
    public boolean isAvailableLoversSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        Village village = villageInfo.village;
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        // 恋絆を結ばれている人のみ可
        if (!villagePlayer.hasLover()) {
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

    // 墓下発言可能か
    public boolean isAvailableGraveSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        Village village = villageInfo.village;
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
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

    // 見学発言可能か
    public boolean isAvailableSpectateSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        VillagePlayer villagePlayer = villageInfo.optVillagePlayer.get();
        if (BooleanUtils.isTrue(villagePlayer.isIsSpectatorFalse())) {
            return false;
        }
        return true;
    }

    // 独り言可能か
    public boolean isAvailableMonologueSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        // いつでも可能
        return true;
    }

    // 秘話可能か
    public boolean isAvailableSecretSay(VillageInfo villageInfo) {
        if (!isAvailableSay(villageInfo)) {
            return false;
        }
        CDef.AllowedSecretSay allowedSecretSay = villageInfo.settings.getAllowedSecretSayCodeAsAllowedSecretSay();
        return villageInfo.isAdmin() || allowedSecretSay == CDef.AllowedSecretSay.全員;
    }

    public SayRestrictDto makeRestrict(VillageInfo villageInfo) {
        SayRestrictDto restrict = new SayRestrictDto();
        if (!villageInfo.isParticipate() //
                || villageInfo.isSpectator() //
                || villageInfo.isDead() //
                || !villageInfo.isLatestDay() //
                || !villageInfo.village.isVillageStatusCode進行中()) {
            return restrict;
        }
        VillagePlayer vPlayer = villageInfo.optVillagePlayer.get();
        if ("master".equals(vPlayer.getPlayer().get().getPlayerName())) {
            return restrict; // masterは制限なし
        }
        CDef.Skill skill = vPlayer.getSkillCodeAsSkill();
        // 制限
        villageInfo.village.getNormalSayRestrictionList().stream().filter(r -> {
            return r.getSkillCodeAsSkill() == skill;
        }).findFirst().ifPresent(res -> {
            restrict.setNormalCount(res.getMessageMaxNum());
            restrict.setNormalLength(res.getMessageMaxLength());
        });
        villageInfo.village.getSkillSayRestrictionList().stream().forEach(res -> {
            if (res.isMessageTypeCode人狼の囁き()) {
                restrict.setWhisperCount(res.getMessageMaxNum());
                restrict.setWhisperLength(res.getMessageMaxLength());
            } else if (res.isMessageTypeCode共鳴発言()) {
                restrict.setMasonCount(res.getMessageMaxNum());
                restrict.setMasonLength(res.getMessageMaxLength());
            } else if (res.isMessageTypeCode恋人発言()) {
                restrict.setLoversCount(res.getMessageMaxNum());
                restrict.setLoversLength(res.getMessageMaxLength());
            }
        });
        // 現在の発言数から残り発言数を計算
        Integer normalCount = restrict.getNormalCount();
        Integer whisperCount = restrict.getWhisperCount();
        Integer masonCount = restrict.getMasonCount();
        Integer loversCount = restrict.getLoversCount();
        if (normalCount != null || whisperCount != null || masonCount != null || loversCount != null) {
            List<Message> messageList =
                    messageLogic.selectDayPersonMessage(villageInfo.villageId, villageInfo.day, vPlayer.getVillagePlayerId());
            if (normalCount != null) {
                int sayCount = (int) messageList.stream().filter(m -> m.isMessageTypeCode通常発言()).count();
                restrict.setNormalLeftCount(normalCount - sayCount);
            }
            if (whisperCount != null) {
                int sayCount = (int) messageList.stream().filter(m -> m.isMessageTypeCode人狼の囁き()).count();
                restrict.setWhisperLeftCount(whisperCount - sayCount);
            }
            if (masonCount != null) {
                int sayCount = (int) messageList.stream().filter(m -> m.isMessageTypeCode共鳴発言()).count();
                restrict.setMasonLeftCount(masonCount - sayCount);
            }
            if (loversCount != null) {
                int sayCount = (int) messageList.stream().filter(m -> m.isMessageTypeCode恋人発言()).count();
                restrict.setLoversLeftCount(loversCount - sayCount);
            }
        }
        return restrict;
    }

    public Optional<CDef.MessageType> detectDefaultMessageType(VillageInfo villageInfo) {
        CDef.MessageType type = null;
        if (!isAvailableSay(villageInfo)) {
            return Optional.empty();
        }
        if (villageInfo.isAdmin()) {
            type = CDef.MessageType.通常発言;
        } else if (villageInfo.village.isVillageStatusCodeエピローグ()) {
            if (!villageInfo.isSpectator()) {
                type = CDef.MessageType.通常発言;
            } else {
                type = CDef.MessageType.見学発言;
            }
        } else if (isAvailableWerewolfSay(villageInfo)) {
            type = CDef.MessageType.人狼の囁き;
        } else if (isAvailableMasonSay(villageInfo)) {
            type = CDef.MessageType.共鳴発言;
        } else if (isAvailableLoversSay(villageInfo)) {
            type = CDef.MessageType.恋人発言;
        } else if (isAvailableMonologueSay(villageInfo)) {
            type = CDef.MessageType.独り言;
        } else if (isAvailableNormalSay(villageInfo)) {
            type = CDef.MessageType.通常発言;
        } else if (isAvailableGraveSay(villageInfo)) {
            type = CDef.MessageType.死者の呻き;
        } else if (isAvailableSpectateSay(villageInfo)) {
            type = CDef.MessageType.見学発言;
        }
        return Optional.of(type);
    }
}
