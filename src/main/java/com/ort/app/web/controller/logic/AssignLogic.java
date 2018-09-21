package com.ort.app.web.controller.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.web.exception.WerewolfMansionBusinessException;
import com.ort.app.web.util.CharaUtil;
import com.ort.app.web.util.RoomUtil;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class AssignLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static Map<Integer, List<Integer>> roomNumberAssignMap;

    static {
        roomNumberAssignMap = RoomUtil.createRoomAssignMap();
    }

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private MessageLogic messageLogic;

    @Autowired
    private SkillBhv skillBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 役職を割り当てる
    public void assignSkill(Integer villageId, List<VillagePlayer> vPlayerList) {
        // 村人数に応じた役職人数Map（key: 役職, value: 役職人数）
        Map<CDef.Skill, Integer> skillPersonNumMap = makeSkillPersonNumMap(villageId, vPlayerList.size());
        // 人数により役職構成が切り替わった場合に役職希望を自動で変更する
        List<VillagePlayer> vpList = updateSkillRequestByOrganization(villageId, vPlayerList, skillPersonNumMap);
        // 更新用プレイヤーリスト
        List<VillagePlayer> updatePlayerList = new ArrayList<>();
        // 役職希望した人について、役職ごとの最大人数まで割り当てて更新用リストに追加
        addRequestToUpdatePlayerList(vpList, skillPersonNumMap, updatePlayerList);
        // 希望していないもしくはまだ割り当てられていないプレイヤーを更新用リストに追加
        addNotRequestToUpdatePlayerList(vpList, skillPersonNumMap, updatePlayerList);
        // update
        updatePlayerList.stream().forEach(player -> {
            villagePlayerBhv.update(player);
        });
        // 役職割り当てについてメッセージ追加
        insertOrganizationMessage(villageId, skillPersonNumMap);
        insertAssignedMessage(villageId);
    }

    // 部屋を割り当てる
    public void assignRoom(Integer villageId, List<VillagePlayer> vPlayerList) {
        // 部屋サイズを決めて登録
        Village village = calculateRoomSizeAndUpdate(villageId, vPlayerList.size());
        List<Integer> roomNumList = new ArrayList<>();
        for (int i = 1; i <= village.getRoomSizeWidth() * village.getRoomSizeHeight(); i++) {
            roomNumList.add(i);
        }
        // 部屋を割り当てて登録
        assignRoomAndUpdate(vPlayerList, roomNumList);
        // 部屋割り当てメッセージ登録
        insertRoomAssignMessage(villageId);
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_Player();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
        });
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    private void insertOrganizationMessage(Integer villageId, Map<Skill, Integer> skillPersonNumMap) {
        StringJoiner joiner = new StringJoiner("、", "この館には、", "いるようだ。");
        skillBhv.selectList(cb -> {
            cb.query().addOrderBy_DispOrder_Asc();
        }).stream().forEach(skill -> {
            Skill s = skill.getSkillCodeAsSkill();
            if (skillPersonNumMap.containsKey(s) && skillPersonNumMap.get(s) > 0) {
                joiner.add(String.format("%sが%d名", s.alias(), skillPersonNumMap.get(s)));
            }
        });
        try {
            messageLogic.insertMessage(villageId, 1, CDef.MessageType.公開システムメッセージ, joiner.toString(), true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは何回も被らないので何もしない
        }
    }

    private void insertAssignedMessage(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_SkillByRequestSkillCode();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setVillageId_Equal(villageId);
        });
        String message = makeAssignedMessage(playerList);
        try {
            messageLogic.insertMessage(villageId, 1, CDef.MessageType.非公開システムメッセージ, message, true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは何回も被らないので何もしない
        }
    }

    private void updateSkillRequest(Integer villagePlayerId, Skill toSkill) {
        VillagePlayer vp = new VillagePlayer();
        vp.setVillagePlayerId(villagePlayerId);
        vp.setRequestSkillCodeAsSkill(toSkill);
        villagePlayerBhv.update(vp);
    }

    // ===================================================================================
    //                                                         Assist Logic (assign skill)
    //                                                                        ============
    // 村人数に応じた役職人数
    private Map<Skill, Integer> makeSkillPersonNumMap(Integer villageId, int personNum) {
        Village village = villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
        String organize = village.getVillageSettingsAsOne().get().getOrganize();
        String personNumOrg =
                Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).filter(org -> org.length() == personNum).findFirst().get();

        return SkillUtil.createSkillPersonNum(personNumOrg);
    }

    private void addRequestToUpdatePlayerList(List<VillagePlayer> vPlayerList, Map<CDef.Skill, Integer> skillPersonNumMap,
            List<VillagePlayer> updatePlayerList) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            // この役職を希望している人
            List<VillagePlayer> requestPlayerList =
                    vPlayerList.stream().filter(player -> skill == player.getRequestSkillCodeAsSkill()).collect(Collectors.toList());
            Collections.shuffle(requestPlayerList); // シャッフル
            int count = 0;
            if (skill == CDef.Skill.村人) {
                // ダミーは強制村人
                Integer dummyVillagePlayerId =
                        vPlayerList.stream().filter(p -> p.getChara().get().isIsDummyTrue()).findFirst().get().getVillagePlayerId();
                updatePlayerList.add(makeUpdateVillagePlayerBean(dummyVillagePlayerId, CDef.Skill.村人));
                count++;
            }
            for (VillagePlayer requestedPlayer : requestPlayerList) {
                if (requestedPlayer.getChara().get().isIsDummyTrue()) {
                    continue;
                }
                if (count >= capacity) {
                    break;
                }
                updatePlayerList.add(makeUpdateVillagePlayerBean(requestedPlayer.getVillagePlayerId(), skill));
                count++;
            }
        }
    }

    private void addNotRequestToUpdatePlayerList(List<VillagePlayer> playerList, Map<CDef.Skill, Integer> skillPersonNumMap,
            List<VillagePlayer> updatePlayerList) {
        List<VillagePlayer> noAssignedPlayer = playerList.stream()
                .filter(player -> updatePlayerList.stream()
                        .noneMatch(updPlayer -> updPlayer.getVillagePlayerId().equals(player.getVillagePlayerId())))
                .collect(Collectors.toList());
        Collections.shuffle(noAssignedPlayer); // シャッフル
        noAssignedPlayer.forEach(player -> {
            // 枠が余っている役職を探す
            CDef.Skill skill = findNotPopularSkill(skillPersonNumMap, updatePlayerList);
            updatePlayerList.add(makeUpdateVillagePlayerBean(player.getVillagePlayerId(), skill));
        });
    }

    private Skill findNotPopularSkill(Map<Skill, Integer> skillPersonNumMap, List<VillagePlayer> updatePlayerList) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            long assignedPlayerNum = updatePlayerList.stream().filter(player -> player.getSkillCodeAsSkill() == skill).count();
            if (assignedPlayerNum < capacity) {
                return skill;
            }
        }
        return null;
    }

    private VillagePlayer makeUpdateVillagePlayerBean(Integer villagePlayerId, CDef.Skill skill) {
        VillagePlayer villagePlayer = new VillagePlayer();
        villagePlayer.setVillagePlayerId(villagePlayerId);
        villagePlayer.setSkillCodeAsSkill(skill);
        return villagePlayer;
    }

    private String makeAssignedMessage(ListResultBean<VillagePlayer> playerList) {
        StringJoiner joiner = new StringJoiner("\n");
        for (VillagePlayer player : playerList) {
            joiner.add(String.format("%sの役職は%sになりました。（希望役職：%s）", CharaUtil.makeCharaName(player),
                    player.getSkillBySkillCode().get().getSkillName(), player.getSkillByRequestSkillCode().get().getSkillName()));
        }
        return joiner.toString();
    }

    private List<VillagePlayer> updateSkillRequestByOrganization(Integer villageId, List<VillagePlayer> vPlayerList,
            Map<Skill, Integer> skillPersonNumMap) {
        if (skillPersonNumMap.get(CDef.Skill.村人) == 1 && skillPersonNumMap.get(CDef.Skill.霊能者) > 0) {
            // 村人→霊能者
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.村人, CDef.Skill.霊能者);
        } else if (skillPersonNumMap.get(CDef.Skill.村人) > 1 && skillPersonNumMap.get(CDef.Skill.霊能者) == 0) {
            // 霊能者→村人
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.霊能者, CDef.Skill.村人);
        }
        if (skillPersonNumMap.get(CDef.Skill.狂人) > 0) {
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.C国狂人, CDef.Skill.狂人);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.魔神官, CDef.Skill.狂人);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.狂信者, CDef.Skill.狂人);
        } else if (skillPersonNumMap.get(CDef.Skill.C国狂人) > 0) {
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.狂人, CDef.Skill.C国狂人);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.魔神官, CDef.Skill.C国狂人);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.狂信者, CDef.Skill.C国狂人);
        } else if (skillPersonNumMap.get(CDef.Skill.魔神官) > 0) {
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.狂人, CDef.Skill.魔神官);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.C国狂人, CDef.Skill.魔神官);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.狂信者, CDef.Skill.魔神官);
        } else if (skillPersonNumMap.get(CDef.Skill.狂信者) > 0) {
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.狂人, CDef.Skill.狂信者);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.魔神官, CDef.Skill.狂信者);
            updateSkillRequestFromTo(villageId, vPlayerList, CDef.Skill.C国狂人, CDef.Skill.狂信者);
        }

        return selectVillagePlayerList(villageId);
    }

    private void updateSkillRequestFromTo(Integer villageId, List<VillagePlayer> vPlayerList, Skill fromSkill, Skill toSkill) {
        vPlayerList.stream().filter(vp -> vp.getRequestSkillCodeAsSkill() == fromSkill && vp.getChara().get().isIsDummyFalse()).forEach(
                vp -> {
                    updateSkillRequest(vp.getVillagePlayerId(), toSkill);
                    String message =
                            String.format("%sの役職希望を%sから%sに自動変更しました。", CharaUtil.makeCharaName(vp), fromSkill.alias(), toSkill.alias());
                    try {
                        messageLogic.insertMessage(villageId, 1, CDef.MessageType.非公開システムメッセージ, message, true);
                    } catch (WerewolfMansionBusinessException e) {
                        // ここでは何回も被らないので何もしない
                    }
                });
    }

    // ===================================================================================
    //                                                          Assist Logic (assign room)
    //                                                                        ============
    private Village calculateRoomSizeAndUpdate(Integer villageId, int personNum) {
        // 8-10 4x3 11 5x3 12-13 4x4 14-17 5x4 18-20 6x4
        // 8    9    10   11   
        // _xx_ _xx_ xxx_ _xxx_
        // xxxx xxxx xxxx xxxxx
        // _xx_ _xxx _xxx _xxx_
        // 
        // 12   13   14    15    16    17   
        // _xx_ _xx_ __xx_ __xx_ _xxx_ _xxx_
        // xxxx xxxx xxxxx xxxxx xxxxx xxxxx
        // xxxx xxxx xxxxx xxxxx xxxxx xxxxx
        // _xx_ _xxx _xx__ _xxx_ _xxx_ _xxxx
        //
        // 18     19     20
        // __xxx_ _xxxx_ _xxxx_
        // xxxxxx xxxxxx xxxxxx
        // xxxxxx xxxxxx xxxxxx
        // _xxx__ _xxx__ _xxxx_

        int width = personNum >= 18 ? 6 : personNum >= 14 ? 5 : personNum >= 12 ? 4 : personNum == 11 ? 5 : 4;
        int height = personNum >= 12 ? 4 : 3;

        Village village = new Village();
        village.setRoomSizeWidth(width);
        village.setRoomSizeHeight(height);
        villageBhv.queryUpdate(village, cb -> cb.query().setVillageId_Equal(villageId));
        return village;
    }

    private void insertRoomAssignMessage(Integer villageId) {
        ListResultBean<VillagePlayer> playerList = villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setVillageId_Equal(villageId);
        });
        String message = makeRoomAssignedMessage(playerList);
        try {
            messageLogic.insertMessage(villageId, 1, CDef.MessageType.公開システムメッセージ, message, true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは何回も被らないので何もしない
        }
    }

    private String makeRoomAssignedMessage(ListResultBean<VillagePlayer> playerList) {
        StringJoiner joiner = new StringJoiner("\n", "人狼の痕跡を残すため、村人たちはそれぞれ別の部屋で夜を明かすことにした。\n\n", "");
        for (VillagePlayer player : playerList) {
            joiner.add(String.format("[%s] %sには、部屋番号%02dが割り当てられた。", player.getChara().get().getCharaShortName(),
                    player.getChara().get().getCharaName(), player.getRoomNumber()));
        }
        return joiner.toString();
    }

    private void assignRoomAndUpdate(List<VillagePlayer> playerList, List<Integer> roomNumList) {
        List<Integer> roomNumberList = new ArrayList<>(roomNumberAssignMap.get(playerList.size()));
        Collections.shuffle(roomNumberList);
        for (int i = 0; i < playerList.size(); i++) {
            VillagePlayer entity = new VillagePlayer();
            entity.setRoomNumber(roomNumberList.get(i));
            VillagePlayer player = playerList.get(i);
            villagePlayerBhv.queryUpdate(entity, cb -> cb.query().setVillagePlayerId_Equal(player.getVillagePlayerId()));
        }
    }
}
