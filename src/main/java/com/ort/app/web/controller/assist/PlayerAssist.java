package com.ort.app.web.controller.assist;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.model.PlayerResultContent;
import com.ort.app.web.model.inner.PlayerCampStatsDto;
import com.ort.app.web.model.inner.PlayerParticipateVillageDto;
import com.ort.app.web.model.inner.PlayerSkillStatsDto;
import com.ort.app.web.model.inner.PlayerStatsDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.Player;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class PlayerAssist {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private final static List<CDef.Skill> STATS_SKILL_LIST = CDef.Skill.listAll()
            .stream()
            .filter(s -> !s.alias().startsWith("おまかせ"))
            .sorted((s1, s2) -> Integer.parseInt(s1.order()) - Integer.parseInt(s2.order()))
            .collect(Collectors.toList());
    private final static CDef.Camp[] STATS_CAMPS = { CDef.Camp.村人陣営, CDef.Camp.人狼陣営, CDef.Camp.狐陣営, CDef.Camp.愉快犯陣営 };

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private PlayerBhv playerBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public void setPlayerIndexModel(String userName, Model model) {
        model.addAttribute("userName", userName);

        OptionalEntity<Player> optPlayer = playerBhv.selectEntity(cb -> {
            cb.query().setPlayerName_Equal(userName);
        });

        if (!optPlayer.isPresent()) {
            return;
        }
        Player player = optPlayer.get();
        playerBhv.load(player, loader -> {
            loader.loadVillagePlayer(vPlayerCB -> {
                vPlayerCB.setupSelect_Chara();
                vPlayerCB.setupSelect_DeadReason();
                vPlayerCB.setupSelect_Village();
                vPlayerCB.setupSelect_SkillBySkillCode().withCamp();
                vPlayerCB.query().addOrderBy_VillageId_Desc();
            }).withNestedReferrer(vPlayerLoader -> {
                vPlayerLoader.pulloutChara().loadCharaImage(charaImageCB -> {
                    charaImageCB.query().setFaceTypeCode_Equal_通常();
                });
            });
        });
        PlayerResultContent content = mappingToContent(player);
        model.addAttribute("content", content);
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private PlayerResultContent mappingToContent(Player player) {
        PlayerResultContent content = new PlayerResultContent();
        content.setWholeStats(convertToWholeStats(player));
        content.setCampStatsList(convertToCampStats(player));
        content.setSkillStatsList(convertToSkillStats(player));
        content.setParticipateVillageList(convertToParticipateVillage(player));
        content.setSpectateVillageList(convertToSpectateVillage(player));
        return content;
    }

    private PlayerStatsDto convertToWholeStats(Player player) {
        PlayerStatsDto part = new PlayerStatsDto();
        int participateNum = (int) player.getVillagePlayerList().stream().filter(vp -> {
            // 退村してない、見学でない、エピローグを迎えている村数
            return vp.isIsGoneFalse() && vp.isIsSpectatorFalse() && vp.getVillage().get().getEpilogueDay() != null;
        }).count();
        int winNum = (int) player.getVillagePlayerList().stream().filter(vp -> {
            // 退村してない、見学でない、エピローグを迎えている、勝利した村数
            if (vp.isIsGoneTrue() || vp.isIsSpectatorTrue() || vp.getVillage().get().getEpilogueDay() == null) {
                return false;
            }
            return vp.isIsWinTrue();
        }).count();
        float winRate = participateNum == 0 ? 0 : (float) winNum / (float) participateNum;
        part.setParticipateNum(participateNum);
        part.setWinNum(winNum);
        part.setWinRate(winRate);
        return part;
    }

    private List<PlayerCampStatsDto> convertToCampStats(Player player) {
        return Stream.of(STATS_CAMPS).map(camp -> {
            PlayerCampStatsDto campStatsDto = new PlayerCampStatsDto();
            int participateNum = (int) player.getVillagePlayerList().stream().filter(vp -> {
                // 退村してない、見学でない、エピローグを迎えている、陣営が一致
                return vp.isIsGoneFalse() && vp.isIsSpectatorFalse() && vp.getVillage().get().getEpilogueDay() != null
                        && camp.code().equals(vp.getCampCode());
            }).count();
            int winNum = (int) player.getVillagePlayerList().stream().filter(vp -> {
                // 退村してない、見学でない、エピローグを迎えている、陣営が一致、勝利した村数
                if (vp.isIsGoneTrue() || vp.isIsSpectatorTrue() || vp.getVillage().get().getEpilogueDay() == null
                        || !camp.code().equals(vp.getCampCode())) {
                    return false;
                }
                return vp.isIsWinTrue();
            }).count();
            float winRate = participateNum == 0 ? 0 : (float) winNum / (float) participateNum;
            campStatsDto.setCampName(camp.alias());
            campStatsDto.setParticipateNum(participateNum);
            campStatsDto.setWinNum(winNum);
            campStatsDto.setWinRate(winRate);
            return campStatsDto;
        }).collect(Collectors.toList());
    }

    private List<PlayerSkillStatsDto> convertToSkillStats(Player player) {
        return STATS_SKILL_LIST.stream().map(skill -> {
            PlayerSkillStatsDto skillStatsDto = new PlayerSkillStatsDto();
            int participateNum = (int) player.getVillagePlayerList().stream().filter(vp -> {
                // 退村してない、見学でない、エピローグを迎えている、役職が一致
                return vp.isIsGoneFalse() && vp.isIsSpectatorFalse() && vp.getVillage().get().getEpilogueDay() != null
                        && vp.getSkillCodeAsSkill() == skill;
            }).count();
            int winNum = (int) player.getVillagePlayerList().stream().filter(vp -> {
                // 退村してない、見学でない、エピローグを迎えている、役職が一致、勝利した村数
                if (vp.isIsGoneTrue() || vp.isIsSpectatorTrue() || vp.getVillage().get().getEpilogueDay() == null
                        || vp.getSkillCodeAsSkill() != skill) {
                    return false;
                }
                return vp.isIsWinTrue();
            }).count();
            float winRate = participateNum == 0 ? 0 : (float) winNum / (float) participateNum;
            skillStatsDto.setSkillName(skill.alias());
            skillStatsDto.setParticipateNum(participateNum);
            skillStatsDto.setWinNum(winNum);
            skillStatsDto.setWinRate(winRate);
            return skillStatsDto;
        }).collect(Collectors.toList());
    }

    private List<PlayerParticipateVillageDto> convertToParticipateVillage(Player player) {
        return player.getVillagePlayerList().stream().filter(vp -> {
            return vp.isIsGoneFalse() && vp.isIsSpectatorFalse() && vp.getVillage().get().getEpilogueDay() != null;
        }).map(vp -> {
            PlayerParticipateVillageDto participateVillageDto = new PlayerParticipateVillageDto();
            Village village = vp.getVillage().get();
            Chara chara = vp.getChara().get();
            participateVillageDto.setVillageId(vp.getVillageId());
            participateVillageDto.setVillageName(village.getVillageDisplayName());
            participateVillageDto.setCharacterName(chara.getCharaName());
            participateVillageDto.setCharacterImgUrl(chara.getNormalCharaImgUrl());
            participateVillageDto.setCharacterImgWidth(chara.getDisplayWidth());
            participateVillageDto.setCharacterImgHeight(chara.getDisplayHeight());
            participateVillageDto.setSkillName(vp.getSkillCodeAsSkill().alias());
            participateVillageDto.setCampName(CDef.Camp.codeOf(vp.getCampCode()).alias());
            participateVillageDto.setLiveStatus(createLiveStatusMessage(vp));
            participateVillageDto.setWinStatus(vp.isDeadReasonCode突然() ? "-" : vp.isIsWinTrue() ? "勝利" : "敗北");
            return participateVillageDto;
        }).collect(Collectors.toList());
    }

    private List<PlayerParticipateVillageDto> convertToSpectateVillage(Player player) {
        return player.getVillagePlayerList().stream().filter(vp -> {
            return vp.isIsGoneFalse() && vp.isIsSpectatorTrue() && vp.getVillage().get().getEpilogueDay() != null;
        }).map(vp -> {
            PlayerParticipateVillageDto participateVillageDto = new PlayerParticipateVillageDto();
            Village village = vp.getVillage().get();
            Chara chara = vp.getChara().get();
            participateVillageDto.setVillageId(vp.getVillageId());
            participateVillageDto.setVillageName(village.getVillageDisplayName());
            participateVillageDto.setCharacterName(chara.getCharaName());
            participateVillageDto.setCharacterImgUrl(chara.getNormalCharaImgUrl());
            participateVillageDto.setCharacterImgWidth(chara.getDisplayWidth());
            participateVillageDto.setCharacterImgHeight(chara.getDisplayHeight());
            return participateVillageDto;
        }).collect(Collectors.toList());
    }

    private String createLiveStatusMessage(VillagePlayer vp) {
        if (vp.isIsDeadFalse()) {
            return "生存";
        }
        Integer deadDay = vp.getDeadDay();
        if (vp.isDeadReasonCode処刑()) {
            return deadDay + "d 処刑死";
        } else if (vp.isDeadReasonCode呪殺()) {
            return deadDay + "d 呪殺";
        } else if (vp.isDeadReasonCode突然()) {
            return deadDay + "d 突然死";
        } else if (vp.isDeadReasonCode襲撃()) {
            return deadDay + "d 襲撃死";
        } else if (vp.isDeadReasonCode罠死()) {
            return deadDay + "d 罠死";
        } else if (vp.isDeadReasonCode爆死()) {
            return deadDay + "d 爆死";
        }
        return "";
    }
}
