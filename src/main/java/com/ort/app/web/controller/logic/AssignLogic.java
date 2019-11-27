package com.ort.app.web.controller.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import com.ort.app.web.util.RoomSize;
import com.ort.app.web.util.RoomUtil;
import com.ort.app.web.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;

@Component
public class AssignLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    public static final String DEFAULT_ORGANIZE = // 
            "村狼狼賢導村村村\n" // 8
                    + "村狼狼賢導村村村村\n" // 9
                    + "村狼狼狂賢導村村村村\n" // 10
                    + "村狼狼狂賢導村村村村村\n" // 11
                    + "村狼狼狼狂賢導狩村村村村\n" // 12
                    + "村狼狼狼狂賢導狩村村村村村\n" // 13
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊\n" // 14
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊霊\n" // 15
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊共共\n" // 16
                    + "村狼狼狼魔狐賢導狩霊霊霊霊霊霊共共\n" // 17
                    + "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊共共\n" // 18
                    + "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊共共\n" // 19
                    + "村狼狼狼狼魔狐賢導狩霊霊霊霊霊霊霊霊共共"; // 20
    private static final List<Skill> rangeSkillList =
            Arrays.asList(Skill.おまかせ人外, Skill.おまかせ人狼陣営, Skill.おまかせ役職窓あり, Skill.おまかせ村人陣営, Skill.おまかせ足音職);
    private static final Map<Skill, List<Skill>> rangeSkillMap;

    static {
        rangeSkillMap = new HashMap<Skill, List<Skill>>();
        rangeSkillMap.put(Skill.おまかせ人外,
                Arrays.asList(Skill.C国狂人, Skill.人狼, CDef.Skill.呪狼, CDef.Skill.智狼, Skill.妖狐, Skill.狂人, Skill.狂信者, Skill.魔神官, Skill.爆弾魔));
        rangeSkillMap.put(Skill.おまかせ人狼陣営,
                Arrays.asList(Skill.C国狂人, Skill.人狼, CDef.Skill.呪狼, CDef.Skill.智狼, Skill.狂人, Skill.狂信者, Skill.魔神官));
        rangeSkillMap.put(Skill.おまかせ役職窓あり, Arrays.asList(Skill.人狼, CDef.Skill.呪狼, CDef.Skill.智狼, Skill.C国狂人, Skill.共鳴者));
        rangeSkillMap.put(Skill.おまかせ村人陣営,
                Arrays.asList(Skill.共鳴者, Skill.占い師, Skill.導師, Skill.村人, Skill.狩人, Skill.賢者, Skill.霊能者, Skill.探偵, Skill.罠師));
        rangeSkillMap.put(Skill.おまかせ足音職, Arrays.asList(Skill.C国狂人, Skill.人狼, CDef.Skill.呪狼, CDef.Skill.智狼, Skill.占い師, Skill.妖狐, Skill.狂人,
                Skill.狂信者, Skill.狩人, Skill.賢者, Skill.魔神官));
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
    public void assignSkill(Integer villageId, List<VillagePlayer> vPlayerList, VillageSettings settings) {
        Integer dummyCharaId = settings.getDummyCharaId();

        // 村人数に応じた役職人数Map（key: 役職, value: 役職人数）
        Map<CDef.Skill, Integer> skillPersonNumMap = makeSkillPersonNumMap(villageId, vPlayerList.size());
        // 人数により役職構成が切り替わった場合に役職希望を自動で変更する
        List<VillagePlayer> vpList = updateSkillRequestByOrganization(villageId, vPlayerList, skillPersonNumMap, dummyCharaId);
        // 管理用オブジェクトへの詰め替え、ダミー配役
        List<SkillAssign> skillAssignList = createSkillAssignList(vpList, dummyCharaId);
        // 第1希望で役職指定で希望した人に割り当て
        assignSpecifySkillRequest(skillAssignList, skillPersonNumMap, true);
        // 第1希望で範囲指定で希望した人に割り当て
        assignRangeSkillRequest(skillAssignList, skillPersonNumMap, true);
        // 第2希望で役職指定で希望した人に割り当て
        assignSpecifySkillRequest(skillAssignList, skillPersonNumMap, false);
        // 第2希望で範囲指定で希望した人に割り当て
        assignRangeSkillRequest(skillAssignList, skillPersonNumMap, false);
        // ここまでで割当たらなかった人に割り当て
        assignOther(skillAssignList, skillPersonNumMap);
        // update
        updateVillagePlayer(skillAssignList);
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
            cb.setupSelect_SkillBySecondRequestSkillCode();
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

    private void updateSkillRequest(Integer villagePlayerId, Skill first, Skill second) {
        VillagePlayer vp = new VillagePlayer();
        vp.setVillagePlayerId(villagePlayerId);
        vp.setRequestSkillCodeAsSkill(first);
        vp.setSecondRequestSkillCodeAsSkill(second);
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

    private void assignNotPopularSkill(SkillAssign assign, Map<Skill, Integer> skillPersonNumMap, List<SkillAssign> skillAssignList) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            long assignedPlayerNum = skillAssignList.stream().filter(sa -> sa.assignedSkill == skill).count();
            if (assignedPlayerNum < capacity) {
                assign.assignedSkill = skill;
            }
        }
    }

    private String makeAssignedMessage(ListResultBean<VillagePlayer> playerList) {
        StringJoiner joiner = new StringJoiner("\n");
        for (VillagePlayer player : playerList) {
            joiner.add(String.format("%sの役職は%sになりました。（希望役職：%s/%s）", CharaUtil.makeCharaName(player),
                    player.getSkillBySkillCode().get().getSkillName(), player.getSkillByRequestSkillCode().get().getSkillName(),
                    player.getSkillBySecondRequestSkillCode().get().getSkillName()));
        }
        return joiner.toString();
    }

    /**
     * 構成に応じて役職希望を自動変更する。
     * 1. 村-霊、占-賢、狂-C-信-魔について、存在する役職に自動変更
     * 2. 第一希望が構成上存在しない役職で第二希望が存在する役職の場合、第二→第一に繰り上げ。第二はおまかせにする
     * 　　第一も第二も存在しない役職の場合、どちらもおまかせにする
     * 
     * @param villageId
     * @param vPlayerList 
     * @param skillPersonNumMap
     * @return
     */
    private List<VillagePlayer> updateSkillRequestByOrganization(Integer villageId, List<VillagePlayer> vPlayerList,
            Map<Skill, Integer> skillPersonNumMap, Integer dummyCharaId) {
        vPlayerList.stream().filter(vp -> vp.getCharaId().intValue() != dummyCharaId).forEach(vp -> {
            SkillRequest before = new SkillRequest(vp.getRequestSkillCodeAsSkill(), vp.getSecondRequestSkillCodeAsSkill());
            SkillRequest after = new SkillRequest(vp.getRequestSkillCodeAsSkill(), vp.getSecondRequestSkillCodeAsSkill());
            // 存在する役職に変更
            convertToExistsSkill(after, skillPersonNumMap);
            // 第一への繰り上げ、存在しない場合おまかせに変更
            upgradeToFirstRequest(after, skillPersonNumMap);
            // 変更があれば更新
            if (before.isChanged(after)) {
                updateSkillRequestFromTo(villageId, vp, before, after);
            }
        });

        return selectVillagePlayerList(villageId);
    }

    private void convertToExistsSkill(SkillRequest after, Map<Skill, Integer> orgMap) {
        if (!existSkillInOrg(CDef.Skill.村人, orgMap) && existSkillInOrg(CDef.Skill.霊能者, orgMap)) {
            convertSkillRequestIfNeeded(after, CDef.Skill.村人, CDef.Skill.霊能者);
        } else if (existSkillInOrg(CDef.Skill.村人, orgMap) && !existSkillInOrg(CDef.Skill.霊能者, orgMap)) {
            convertSkillRequestIfNeeded(after, CDef.Skill.霊能者, CDef.Skill.村人);
        }
        if (existSkillInOrg(CDef.Skill.C国狂人, orgMap)) {
            if (!existSkillInOrg(CDef.Skill.狂人, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.狂人, CDef.Skill.C国狂人);
            }
            if (!existSkillInOrg(CDef.Skill.狂信者, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.狂信者, CDef.Skill.C国狂人);
            }
            if (!existSkillInOrg(CDef.Skill.魔神官, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.魔神官, CDef.Skill.C国狂人);
            }
        }
        if (existSkillInOrg(CDef.Skill.狂信者, orgMap)) {
            if (!existSkillInOrg(CDef.Skill.C国狂人, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.C国狂人, CDef.Skill.狂信者);
            }
            if (!existSkillInOrg(CDef.Skill.狂人, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.狂人, CDef.Skill.狂信者);
            }
            if (!existSkillInOrg(CDef.Skill.魔神官, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.魔神官, CDef.Skill.狂信者);
            }
        }
        if (existSkillInOrg(CDef.Skill.魔神官, orgMap)) {
            if (!existSkillInOrg(CDef.Skill.C国狂人, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.C国狂人, CDef.Skill.魔神官);
            }
            if (!existSkillInOrg(CDef.Skill.狂人, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.狂人, CDef.Skill.魔神官);
            }
            if (!existSkillInOrg(CDef.Skill.狂信者, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.狂信者, CDef.Skill.魔神官);
            }
        }
        if (existSkillInOrg(CDef.Skill.狂人, orgMap)) {
            if (!existSkillInOrg(CDef.Skill.C国狂人, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.C国狂人, CDef.Skill.狂人);
            }
            if (!existSkillInOrg(CDef.Skill.魔神官, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.魔神官, CDef.Skill.狂人);
            }
            if (!existSkillInOrg(CDef.Skill.狂信者, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.狂信者, CDef.Skill.狂人);
            }
        }
        if (existSkillInOrg(CDef.Skill.占い師, orgMap) && !existSkillInOrg(CDef.Skill.賢者, orgMap)) {
            convertSkillRequestIfNeeded(after, CDef.Skill.賢者, CDef.Skill.占い師);
        } else if (existSkillInOrg(CDef.Skill.賢者, orgMap) && !existSkillInOrg(CDef.Skill.占い師, orgMap)) {
            convertSkillRequestIfNeeded(after, CDef.Skill.占い師, CDef.Skill.賢者);
        }
        if (existSkillInOrg(CDef.Skill.人狼, orgMap)) {
            if (!existSkillInOrg(CDef.Skill.呪狼, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.呪狼, CDef.Skill.人狼);
            }
            if (!existSkillInOrg(CDef.Skill.智狼, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.智狼, CDef.Skill.人狼);
            }
        }
        if (existSkillInOrg(CDef.Skill.呪狼, orgMap)) {
            if (!existSkillInOrg(CDef.Skill.人狼, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.人狼, CDef.Skill.呪狼);
            }
            if (!existSkillInOrg(CDef.Skill.智狼, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.智狼, CDef.Skill.呪狼);
            }
        }
        if (existSkillInOrg(CDef.Skill.智狼, orgMap)) {
            if (!existSkillInOrg(CDef.Skill.人狼, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.人狼, CDef.Skill.智狼);
            }
            if (!existSkillInOrg(CDef.Skill.呪狼, orgMap)) {
                convertSkillRequestIfNeeded(after, CDef.Skill.呪狼, CDef.Skill.智狼);
            }
        }
    }

    private void convertSkillRequestIfNeeded(SkillRequest request, Skill before, Skill after) {
        if (request.first == before) {
            request.first = after;
        }
        if (request.second == before) {
            request.second = after;
        }
    }

    private void upgradeToFirstRequest(SkillRequest after, Map<Skill, Integer> orgMap) {
        if (!existSkillInOrg(after.first, orgMap) && !existSkillInOrg(after.second, orgMap)) {
            // どちらも存在しない
            after.first = CDef.Skill.おまかせ;
            after.second = CDef.Skill.おまかせ;
            return;
        } else if (!existSkillInOrg(after.first, orgMap) && existSkillInOrg(after.second, orgMap)) {
            // 第二は存在
            after.first = after.second;
            after.second = CDef.Skill.おまかせ;
            return;
        } else if (existSkillInOrg(after.first, orgMap) && !existSkillInOrg(after.second, orgMap)) {
            // 第一は存在
            after.second = CDef.Skill.おまかせ;
            return;
        }
    }

    private boolean existSkillInOrg(Skill skill, Map<Skill, Integer> skillPersonNumMap) {
        if (skill == CDef.Skill.おまかせ || rangeSkillList.contains(skill)) {
            return true;
        }
        if (skill == CDef.Skill.村人) {
            return skillPersonNumMap.get(skill) > 1;
        } else {
            return skillPersonNumMap.get(skill) > 0;
        }
    }

    private void updateSkillRequestFromTo(Integer villageId, VillagePlayer vp, SkillRequest before, SkillRequest after) {
        updateSkillRequest(vp.getVillagePlayerId(), after.first, after.second);
        String message = String.format("%sの役職希望を自動変更しました。\n%s/%s → %s/%s", CharaUtil.makeCharaName(vp), before.first.alias(),
                before.second.alias(), after.first.alias(), after.second.alias());
        try {
            messageLogic.insertMessage(villageId, 1, CDef.MessageType.非公開システムメッセージ, message, true);
        } catch (WerewolfMansionBusinessException e) {
            // ここでは何回も被らないので何もしない
        }
    }

    private List<SkillAssign> createSkillAssignList(List<VillagePlayer> vPlayerList, Integer dummyCharaId) {
        List<SkillAssign> skillAssignList = vPlayerList.stream().filter(vp -> vp.getCharaId().intValue() != dummyCharaId).map(vp -> {
            return new SkillAssign(vp.getVillagePlayerId(), vp.getRequestSkillCodeAsSkill(), vp.getSecondRequestSkillCodeAsSkill());
        }).collect(Collectors.toList());
        VillagePlayer dummy = vPlayerList.stream().filter(vp -> vp.getCharaId().intValue() == dummyCharaId).findFirst().get();
        SkillAssign assign =
                new SkillAssign(dummy.getVillagePlayerId(), dummy.getRequestSkillCodeAsSkill(), dummy.getSecondRequestSkillCodeAsSkill());
        assign.assignedSkill = CDef.Skill.村人; // 村人固定
        skillAssignList.add(assign);
        return skillAssignList;
    }

    private void updateVillagePlayer(List<SkillAssign> skillAssignList) {
        skillAssignList.forEach(assign -> {
            VillagePlayer vp = new VillagePlayer();
            vp.setVillagePlayerId(assign.villagePlayerId);
            vp.setSkillCodeAsSkill(assign.assignedSkill);
            vp.setCampCode(assign.assignedSkill.campCode());
            villagePlayerBhv.update(vp);
        });
    }

    private void assignSpecifySkillRequest(List<SkillAssign> skillAssignList, Map<Skill, Integer> skillPersonNumMap, boolean isFirst) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            // この役職を希望していて、まだ役職が割り当たっていない人
            List<SkillAssign> requestPlayerList =
                    skillAssignList.stream().filter(sa -> sa.getRequest(isFirst) == skill && !sa.alreadyAssigned()).collect(
                            Collectors.toList());
            if (requestPlayerList.size() == 0) {
                continue;
            }
            // この役職について空いている枠数
            int left = capacity - (int) skillAssignList.stream().filter(sa -> sa.assignedSkill == skill).count();
            if (left <= 0) {
                continue;
            }
            // 空いている人数まで割り当てる
            Collections.shuffle(requestPlayerList); // シャッフル
            int count = 0;
            for (SkillAssign requestedPlayer : requestPlayerList) {
                if (count >= left) {
                    break;
                }
                requestedPlayer.assignedSkill = skill;
                count++;
            }
        }
    }

    private void assignRangeSkillRequest(List<SkillAssign> skillAssignList, Map<Skill, Integer> skillPersonNumMap, boolean isFirst) {
        // 範囲指定している人単位でランダム順に割り当てていく
        List<SkillAssign> rangeRequestPlayerList =
                skillAssignList.stream().filter(sa -> rangeSkillList.contains(sa.getRequest(isFirst)) && !sa.alreadyAssigned()).collect(
                        Collectors.toList());
        if (rangeRequestPlayerList.size() == 0) {
            return;
        }
        Collections.shuffle(rangeRequestPlayerList);
        rangeRequestPlayerList.forEach(assign -> {
            // 範囲指定の中でまだ枠がある役職をランダムに取得して割り当て（割当たらないこともある）
            extractAvailableSkill(assign, skillAssignList, skillPersonNumMap, isFirst);
        });
    }

    private void extractAvailableSkill(SkillAssign assign, List<SkillAssign> skillAssignList, Map<Skill, Integer> skillPersonNumMap,
            boolean isFirst) {
        List<Skill> candidateSkillList = rangeSkillMap.get(assign.getRequest(isFirst));
        Collections.shuffle(candidateSkillList);
        for (Skill skill : candidateSkillList) {
            // まだ空きがあったら割り当てる
            int capacity = skillPersonNumMap.get(skill);
            int left = capacity - (int) skillAssignList.stream().filter(sa -> sa.assignedSkill == skill).count();
            if (left <= 0) {
                continue;
            }
            assign.assignedSkill = skill;
            return;
        }
    }

    private void assignOther(List<SkillAssign> skillAssignList, Map<Skill, Integer> skillPersonNumMap) {
        List<SkillAssign> noAssignedPlayer = skillAssignList.stream().filter(sa -> !sa.alreadyAssigned()).collect(Collectors.toList());
        Collections.shuffle(noAssignedPlayer); // シャッフル
        noAssignedPlayer.forEach(sa -> {
            // 枠が余っている役職を割り当てる
            assignNotPopularSkill(sa, skillPersonNumMap, skillAssignList);
        });
    }

    // ===================================================================================
    //                                                                         inner class
    //                                                                             =======
    private static class SkillRequest {
        private Skill first;
        private Skill second;

        private SkillRequest(Skill first, Skill second) {
            this.first = first;
            this.second = second;
        }

        private boolean isChanged(SkillRequest after) {
            return this.first != after.first || this.second != after.second;
        }
    }

    private static class SkillAssign {
        private Integer villagePlayerId;
        private Skill firstRequestSkill;
        private Skill secondRequestSkill;
        private Skill assignedSkill;

        private SkillAssign(Integer vpId, Skill firstRequestSkill, Skill secondRequestSkill) {
            this.villagePlayerId = vpId;
            this.firstRequestSkill = firstRequestSkill;
            this.secondRequestSkill = secondRequestSkill;
        }

        private boolean alreadyAssigned() {
            return assignedSkill != null;
        }

        private Skill getRequest(boolean isFirst) {
            return isFirst ? firstRequestSkill : secondRequestSkill;
        }
    }

    // ===================================================================================
    //                                                          Assist Logic (assign room)
    //                                                                        ============
    private Village calculateRoomSizeAndUpdate(Integer villageId, int personNum) {
        RoomSize roomSize = RoomUtil.getRoomSize(personNum);
        Village village = new Village();
        village.setRoomSizeWidth(roomSize.width);
        village.setRoomSizeHeight(roomSize.height);
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
        List<Integer> roomNumberList = new ArrayList<>(RoomUtil.getRoomNumberList(playerList.size()));
        Collections.shuffle(roomNumberList);
        for (int i = 0; i < playerList.size(); i++) {
            VillagePlayer entity = new VillagePlayer();
            entity.setRoomNumber(roomNumberList.get(i));
            VillagePlayer player = playerList.get(i);
            villagePlayerBhv.queryUpdate(entity, cb -> cb.query().setVillagePlayerId_Equal(player.getVillagePlayerId()));
        }
    }
}
