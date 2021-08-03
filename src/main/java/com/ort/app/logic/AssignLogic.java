package com.ort.app.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.dbflute.cbean.result.ListResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.message.MessageEntity;
import com.ort.app.util.RoomSize;
import com.ort.app.util.RoomUtil;
import com.ort.app.util.SkillUtil;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.allcommon.CDef.Camp;
import com.ort.dbflute.allcommon.CDef.Skill;
import com.ort.dbflute.exbhv.SkillBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VillagePlayerStatusBhv;
import com.ort.dbflute.exentity.CampAllocation;
import com.ort.dbflute.exentity.SkillAllocation;
import com.ort.dbflute.exentity.Skills;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayerStatus;
import com.ort.dbflute.exentity.VillagePlayers;
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
    private static final Map<Skill, List<Skill>> rangeSkillMap;
    private static final List<CDef.Skill> MADMAN_PRIORITY_LIST =
            Arrays.asList(CDef.Skill.C国狂人, CDef.Skill.狂信者, CDef.Skill.魔神官, CDef.Skill.狂人);
    private static final List<CDef.Skill> WOLF_PRIORITY_LIST = Arrays.asList(CDef.Skill.智狼, CDef.Skill.呪狼, CDef.Skill.絶対人狼, CDef.Skill.人狼);
    private static final List<CDef.Skill> SEER_PRIORITY_LIST = Arrays.asList(CDef.Skill.賢者, CDef.Skill.占星術師, CDef.Skill.占い師);
    private static final Logger logger = LoggerFactory.getLogger(AssignLogic.class);

    static {
        rangeSkillMap = new HashMap<Skill, List<Skill>>();

        List<Skill> jingaiSkillList = CDef.Skill.listAll().stream().filter(s -> {
            if (s.isSomeoneSkill()) {
                return false;
            }
            Camp camp = Camp.codeOf(s.campCode());
            return camp == CDef.Camp.人狼陣営 || camp == CDef.Camp.狐陣営 || camp == CDef.Camp.愉快犯陣営;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ人外, jingaiSkillList);

        List<Skill> wolfCampSkillList = CDef.Skill.listAll().stream().filter(s -> {
            return !s.isSomeoneSkill() && Camp.codeOf(s.campCode()) == CDef.Camp.人狼陣営;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ人狼陣営, wolfCampSkillList);

        List<Skill> foxCampSkillList = CDef.Skill.listAll().stream().filter(s -> {
            return !s.isSomeoneSkill() && Camp.codeOf(s.campCode()) == CDef.Camp.狐陣営;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ妖狐陣営, foxCampSkillList);

        List<Skill> loversCampSkillList = CDef.Skill.listAll().stream().filter(s -> {
            return !s.isSomeoneSkill() && Camp.codeOf(s.campCode()) == CDef.Camp.恋人陣営;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ恋人陣営, loversCampSkillList);

        List<Skill> criminalCampSkillList = CDef.Skill.listAll().stream().filter(s -> {
            return !s.isSomeoneSkill() && Camp.codeOf(s.campCode()) == CDef.Camp.愉快犯陣営;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ愉快犯陣営, criminalCampSkillList);

        List<Skill> sayableSkillList = CDef.Skill.listAll().stream().filter(s -> {
            if (s.isSomeoneSkill())
                return false;
            return s.isAvailableWerewolfSay() || s == CDef.Skill.共鳴者 || s == CDef.Skill.恋人 || s == CDef.Skill.同棲者;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ役職窓あり, sayableSkillList);

        List<Skill> notSayableSkillList = CDef.Skill.listAll().stream().filter(s -> {
            if (s.isSomeoneSkill())
                return false;
            return !s.isAvailableWerewolfSay() && s != CDef.Skill.共鳴者 && s != CDef.Skill.恋人 && s != CDef.Skill.同棲者;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ役職窓なし, notSayableSkillList);

        List<Skill> villagerCampSkillList = CDef.Skill.listAll().stream().filter(s -> {
            return !s.isSomeoneSkill() && Camp.codeOf(s.campCode()) == CDef.Camp.村人陣営;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ村人陣営, villagerCampSkillList);

        List<Skill> footstepSkillList = CDef.Skill.listAll().stream().filter(s -> {
            if (s.isSomeoneSkill())
                return false;
            return s.isHasAttackAbility() || s.isHasDisturbAbility() || s.isHasDivineAbility() || s == CDef.Skill.狩人;
        }).collect(Collectors.toList());
        rangeSkillMap.put(Skill.おまかせ足音職, footstepSkillList);
    }

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;
    @Autowired
    private VillagePlayerStatusBhv villagePlayerStatusBhv;
    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private SkillBhv skillBhv;
    @Autowired
    private VillageService villageService;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 役職を割り当てる
    public void assignSkill(Integer villageId, VillagePlayers vPlayers, VillageSettings settings) {
        Integer dummyCharaId = settings.getDummyCharaId();

        // 村人数に応じた役職人数Map（key: 役職, value: 役職人数）
        Map<CDef.Skill, Integer> skillPersonNumMap = makeSkillPersonNumMap(villageId, vPlayers.list.size());
        // 人数により役職構成が切り替わった場合に役職希望を自動で変更する
        List<VillagePlayer> vpList = updateSkillRequestByOrganization(villageId, vPlayers, skillPersonNumMap, dummyCharaId);
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
        // 恋人がいたら状態付与
        insertVillagePlayerStatusIfNeeded(skillAssignList);
        // 役職割り当てについてメッセージ追加
        insertOrganizationMessage(villageId, skillPersonNumMap, settings.getIsRandomOrganize());
        insertAssignedMessage(villageId);
    }

    // 部屋を割り当てる
    public void assignRoom(Integer villageId, VillagePlayers vPlayers) {
        // 部屋サイズを決めて登録
        Village village = calculateRoomSizeAndUpdate(villageId, vPlayers.list.size());
        List<Integer> roomNumList = new ArrayList<>();
        for (int i = 1; i <= village.getRoomSizeWidth() * village.getRoomSizeHeight(); i++) {
            roomNumList.add(i);
        }
        // 部屋を割り当てて登録
        assignRoomAndUpdate(vPlayers, roomNumList, 1);
        // 部屋割り当てメッセージ登録
        insertRoomAssignMessage(villageId);
    }

    public void reAssignRoom(Village village, VillagePlayers vPlayers) {
        List<Integer> roomNumList = new ArrayList<>();
        for (int i = 1; i <= village.getRoomSizeWidth() * village.getRoomSizeHeight(); i++) {
            roomNumList.add(i);
        }
        // 部屋を割り当てて登録
        assignRoomAndUpdate(vPlayers, roomNumList, village.getVillageDays().latestDay().getDay());
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
    private void insertOrganizationMessage(Integer villageId, Map<Skill, Integer> skillPersonNumMap, boolean isRandomOrganize) {
        StringJoiner joiner = new StringJoiner("、", "この館には、", "いるようだ。");
        skillBhv.selectList(cb -> {
            cb.query().addOrderBy_DispOrder_Asc();
        }).stream().forEach(skill -> {
            Skill s = skill.getSkillCodeAsSkill();
            if (skillPersonNumMap.containsKey(s) && skillPersonNumMap.get(s) > 0) {
                joiner.add(String.format("%sが%d名", s.alias(), skillPersonNumMap.get(s)));
            }
        });
        if (isRandomOrganize) {
            messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(villageId, 1) //
                    .content(joiner.toString())
                    .build());
        } else {
            messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, 1) //
                    .content(joiner.toString())
                    .build());
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
        messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(villageId, 1) //
                .content(message)
                .build());
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
        VillageSettings settings = village.getVillageSettingsAsOne().get();
        if (settings.isIsRandomOrganizeFalse()) {
            String organize = settings.getOrganize();
            String personNumOrg =
                    Stream.of(organize.replaceAll("\r\n", "\n").split("\n")).filter(org -> org.length() == personNum).findFirst().get();

            return SkillUtil.createSkillPersonNum(personNumOrg);
        } else {
            villageBhv.load(village, loader -> {
                loader.loadCampAllocation(cb -> {});
                loader.loadSkillAllocation(cb -> {});
            });
            return createRandomOrganizationSkillPersonNumMap(village, personNum, 0);
        }
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
            joiner.add(String.format("%sの役職は%sになりました。（希望役職：%s/%s）", player.name(), player.getSkillBySkillCode().get().getSkillName(),
                    player.getSkillByRequestSkillCode().get().getSkillName(),
                    player.getSkillBySecondRequestSkillCode().get().getSkillName()));
        }
        return joiner.toString();
    }

    /**
     * 構成に応じて役職希望を自動変更する。
     * 1. 村-霊、占-賢、狂-C-信-魔について、存在する役職に自動変更
     * 2. 第一希望が構成上存在しない役職で第二希望が存在する役職の場合、第二→第一に繰り上げ。第二はおまかせにする
     * 　　第一も第二も存在しない役職の場合、どちらもおまかせにする
     */
    private List<VillagePlayer> updateSkillRequestByOrganization(Integer villageId, VillagePlayers vPlayers,
            Map<Skill, Integer> skillPersonNumMap, Integer dummyCharaId) {
        vPlayers.filterNotDummy(dummyCharaId).list.forEach(vp -> {
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

    private void convertToExistsSkill(SkillRequest request, Map<Skill, Integer> orgMap) {
        if (!existSkillInOrg(CDef.Skill.村人, orgMap) && existSkillInOrg(CDef.Skill.霊能者, orgMap)) {
            convertSkillRequestIfNeeded(request, CDef.Skill.村人, CDef.Skill.霊能者);
        } else if (existSkillInOrg(CDef.Skill.村人, orgMap) && !existSkillInOrg(CDef.Skill.霊能者, orgMap)) {
            convertSkillRequestIfNeeded(request, CDef.Skill.霊能者, CDef.Skill.村人);
        }

        convertSkill(MADMAN_PRIORITY_LIST, request, orgMap);
        convertSkill(WOLF_PRIORITY_LIST, request, orgMap);
        convertSkill(SEER_PRIORITY_LIST, request, orgMap);
    }

    private void convertSkill(List<Skill> skillPriorityList, SkillRequest request, Map<Skill, Integer> orgMap) {
        skillPriorityList.forEach(toSkill -> {
            if (!existSkillInOrg(toSkill, orgMap)) {
                return;
            }
            skillPriorityList.forEach(fromSkill -> {
                if (existSkillInOrg(fromSkill, orgMap)) {
                    return;
                }
                // fromが存在しなく、toが存在するので、入れ替える
                convertSkillRequestIfNeeded(request, fromSkill, toSkill);
            });
        });
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
        if (skill.isSomeoneSkill()) {
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
        String message = String.format("%sの役職希望を自動変更しました。\n%s/%s → %s/%s", vp.name(), before.first.alias(), before.second.alias(),
                after.first.alias(), after.second.alias());
        messageLogic.saveIgnoreError(MessageEntity.privateSystemBuilder(villageId, 1) //
                .content(message)
                .build());
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

    private void insertVillagePlayerStatusIfNeeded(List<SkillAssign> skillAssignList) {
        // 恋人
        List<SkillAssign> loversList =
                skillAssignList.stream().filter(sa -> sa.assignedSkill == CDef.Skill.恋人).collect(Collectors.toList());
        Collections.shuffle(loversList);
        for (int i = 0; i < loversList.size() - 1; i = i + 2) {
            Integer loverPlayerId1 = loversList.get(i).villagePlayerId;
            Integer loverPlayerId2 = loversList.get(i + 1).villagePlayerId;
            insertVillagePlayerStatus(loverPlayerId1, loverPlayerId2, CDef.VillagePlayerStatusType.後追い);
            insertVillagePlayerStatus(loverPlayerId2, loverPlayerId1, CDef.VillagePlayerStatusType.後追い);
        }
        // 同棲者
        List<SkillAssign> cohabitersList =
                skillAssignList.stream().filter(sa -> sa.assignedSkill == CDef.Skill.同棲者).collect(Collectors.toList());
        Collections.shuffle(cohabitersList);
        for (int i = 0; i < cohabitersList.size() - 1; i = i + 2) {
            Integer loverPlayerId1 = cohabitersList.get(i).villagePlayerId;
            Integer loverPlayerId2 = cohabitersList.get(i + 1).villagePlayerId;
            insertVillagePlayerStatus(loverPlayerId1, loverPlayerId2, CDef.VillagePlayerStatusType.後追い);
            insertVillagePlayerStatus(loverPlayerId2, loverPlayerId1, CDef.VillagePlayerStatusType.後追い);
        }
    }

    private void insertVillagePlayerStatus(Integer from, Integer to, CDef.VillagePlayerStatusType type) {
        VillagePlayerStatus status = new VillagePlayerStatus();
        status.setVillagePlayerId(from);
        status.setToVillagePlayerId(to);
        status.setVillagePlayerStatusCodeAsVillagePlayerStatusType(type);
        villagePlayerStatusBhv.insert(status);
    }

    private void assignSpecifySkillRequest(List<SkillAssign> skillAssignList, Map<Skill, Integer> skillPersonNumMap, boolean isFirst) {
        for (Entry<CDef.Skill, Integer> entry : skillPersonNumMap.entrySet()) {
            CDef.Skill skill = entry.getKey();
            int capacity = entry.getValue();
            // この役職を希望していて、まだ役職が割り当たっていない人
            List<SkillAssign> requestPlayerList = skillAssignList.stream()
                    .filter(sa -> sa.getRequest(isFirst) == skill && !sa.alreadyAssigned())
                    .collect(Collectors.toList());
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
        List<SkillAssign> rangeRequestPlayerList = skillAssignList.stream().filter(sa -> {
            Skill requestSkill = sa.getRequest(isFirst);
            return requestSkill != CDef.Skill.おまかせ //
                    && requestSkill.isSomeoneSkill() //
                    && !sa.alreadyAssigned();
        }).collect(Collectors.toList());
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
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(villageId, 1) //
                .content(message)
                .build());
    }

    private String makeRoomAssignedMessage(ListResultBean<VillagePlayer> playerList) {
        StringJoiner joiner = new StringJoiner("\n", "人狼の痕跡を残すため、村人たちはそれぞれ別の部屋で夜を明かすことにした。\n\n", "");
        for (VillagePlayer player : playerList) {
            joiner.add(String.format("[%s] %sには、部屋番号%02dが割り当てられた。", player.getCharaShortName(), player.getCharaName(),
                    player.getRoomNumber()));
        }
        return joiner.toString();
    }

    private void assignRoomAndUpdate(VillagePlayers vPlayers, List<Integer> roomNumList, int day) {
        List<Integer> roomNumberList = new ArrayList<>(RoomUtil.getRoomNumberList(vPlayers.list.size()));
        Collections.shuffle(roomNumberList);
        for (int i = 0; i < vPlayers.list.size(); i++) {
            VillagePlayer player = vPlayers.list.get(i);
            Integer roomNumber = roomNumberList.get(i);
            villageService.assignRoom(player, roomNumber, day);
        }
    }

    // 闇鍋配役を作成
    private Map<Skill, Integer> createRandomOrganizationSkillPersonNumMap(Village village, int personNum, int retryCount) {
        if (retryCount >= 50) {
            throw new IllegalStateException("50回試行しましたが割り振れませんでした。");
        }

        List<CampAllocation> campAllocationList = village.getCampAllocationList();
        List<SkillAllocation> skillAllocationList = village.getSkillAllocationList();

        Map<Skill, Integer> skillPersonNumMap = new HashMap<>();
        Skills.of().filterNotSomeone().list.forEach(skill -> {
            skillPersonNumMap.put(skill, 0);
        });

        // 最低人数が決まっている役職を先に割り当てる
        skillAllocationList.stream().filter(skillAllocation -> skillAllocation.getMinNum() > 0).forEach(skillAllocation -> {
            skillPersonNumMap.put(CDef.Skill.codeOf(skillAllocation.getSkillCode()), skillAllocation.getMinNum());
        });

        // 最低人数が決まっている陣営を先に割り当てる
        campAllocationList.stream().filter(campAllocation -> campAllocation.getMinNum() > 0).forEach(campAllocation -> {
            int campSkillSum = skillPersonNumMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().campCode().equals(campAllocation.getCampCode()))
                    .mapToInt(entry -> entry.getValue())
                    .sum();
            int left = campAllocation.getMinNum() - campSkillSum;
            for (int i = 0; i < left; i++) {
                // 割り振っても良い役職
                List<SkillAllocation> skillList = skillAllocationList.stream().filter(s -> {
                    return s.getSkillCodeAsSkill().campCode().equals(campAllocation.getCampCode())
                            && isAllocatableSkill(skillPersonNumMap, s, campAllocation.getCampCodeAsCamp());
                }).collect(Collectors.toList());
                CDef.Skill skill = gachaSkill(skillList);
                addSkillPerson(skillPersonNumMap, skill);
            }
        });

        // ここまでで人狼カウントがいなかったら先に一人割り当てる
        int wolfCount = skillPersonNumMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isWolfCount())
                .mapToInt(entry -> entry.getValue())
                .sum();
        if (wolfCount <= 0) {
            CDef.Skill wolf = gachaSkill(skillAllocationList.stream().filter(s -> s.isSkillCode_WolfCount()).collect(Collectors.toList()));
            addSkillPerson(skillPersonNumMap, wolf);
        }

        // 人数を満たすまで継続
        while (skillPersonNumMap.values().stream().mapToInt(it -> it).sum() < personNum) {
            CDef.Skill skill = gachaCampAndSkill(campAllocationList, skillAllocationList, skillPersonNumMap);
            addSkillPerson(skillPersonNumMap, skill);
        }

        if (shouldRetry(skillPersonNumMap, personNum)) {
            // やり直し
            skillPersonNumMap.entrySet().stream().forEach(entry -> {
                logger.info(String.format("%s: %d", entry.getKey().alias(), entry.getValue()));
            });
            return createRandomOrganizationSkillPersonNumMap(village, personNum, retryCount + 1);
        }

        return skillPersonNumMap;
    }

    private boolean shouldRetry(Map<Skill, Integer> skillPersonNumMap, int personNum) {
        // 人数オーバー
        int sum = skillPersonNumMap.values().stream().mapToInt(it -> it).sum();
        if (personNum < sum) {
            logger.info("人数オーバーしているのでやり直し");
            return true;
        }

        // 恋人と同棲者は二人ペアでなければいけないので奇数ならやりなおし
        Integer loversCount = skillPersonNumMap.get(CDef.Skill.恋人);
        if (loversCount != null && loversCount % 2 == 1) {
            logger.info("恋人が奇数だったのでやり直し");
            return true;
        }
        Integer cohabitersCount = skillPersonNumMap.get(CDef.Skill.同棲者);
        if (cohabitersCount != null && cohabitersCount % 2 == 1) {
            logger.info("同棲者が奇数だったのでやり直し");
            return true;
        }

        // 勝利できない役職がいたらやり直し（妖狐系なしの背徳者）
        int foxCount = Optional.ofNullable(skillPersonNumMap.get(CDef.Skill.妖狐)).orElse(0);
        int cheaterFoxCount = Optional.ofNullable(skillPersonNumMap.get(CDef.Skill.誑狐)).orElse(0);
        int immoralCount = Optional.ofNullable(skillPersonNumMap.get(CDef.Skill.背徳者)).orElse(0);
        if (foxCount + cheaterFoxCount == 0 && 0 < immoralCount) {
            logger.info("妖狐系がいないのに背徳者がいるのでやり直し");
            return true;
        }

        // PPチェック
        int wolfSum = skillPersonNumMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().isWolfCount())
                .mapToInt(entry -> entry.getValue())
                .sum();
        int villagerSum = skillPersonNumMap.entrySet().stream().filter(entry -> {
            CDef.Skill skill = entry.getKey();
            return !skill.isWolfCount() && !skill.isNoCount();
        }).mapToInt(entry -> entry.getValue()).sum();
        // 2日目に村人が一人減るので村人は人狼よりも二人以上多くなければいけない
        if (villagerSum <= wolfSum + 1) {
            logger.info(String.format("即PPになるのでやり直し villager: %d, wolf: %d", villagerSum, wolfSum));
            return true;
        }

        return false;
    }

    private CDef.Skill gachaCampAndSkill(List<CampAllocation> campAllocationList, List<SkillAllocation> skillAllocationList,
            Map<Skill, Integer> skillPersonNumMap) {
        // 割り振っても良い陣営
        List<CampAllocation> campList = campAllocationList.stream().filter(c -> {
            return isAllocatableCamp(skillAllocationList, skillPersonNumMap, c);
        }).collect(Collectors.toList());
        // 陣営を抽選
        CDef.Camp camp = gachaCamp(campList);
        // 割り振っても良い役職
        List<SkillAllocation> skillList = skillAllocationList.stream().filter(s -> {
            return isAllocatableSkill(skillPersonNumMap, s, camp);
        }).collect(Collectors.toList());
        // 役職を抽選
        CDef.Skill skill = gachaSkill(skillList);

        if (skill == null) {
            throw new IllegalStateException("役職を割り当てられませんでした。");
        }

        return skill;
    }

    private boolean isAllocatableCamp(List<SkillAllocation> skillAllocationList, Map<Skill, Integer> skillPersonNumMap,
            CampAllocation camp) {
        List<SkillAllocation> campSkillAllocationList = skillAllocationList.stream()
                .filter(s -> CDef.Skill.codeOf(s.getSkillCode()).campCode().equals(camp.getCampCode()))
                .collect(Collectors.toList());

        // 配分が0
        if (camp.getAllocation() <= 0) {
            return false;
        }

        // 配分が0より大きい役職がいない
        if (campSkillAllocationList.stream().noneMatch(s -> s.getAllocation() > 0)) {
            return false;
        }

        // 全役職が既に最大人数（maxが設定されていない、割り振られていない役職が1つでもあれば問題なし）
        if (campSkillAllocationList.stream().allMatch(s -> {
            Integer max = s.getMaxNum();
            Integer current = skillPersonNumMap.get(CDef.Skill.codeOf(s.getSkillCode()));
            return max != null && current != null && max <= current;
        })) {
            return false;
        }

        // 陣営の役職の合計数が既に陣営配分のmax人数
        int campSkillSum = skillPersonNumMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().campCode().equals(camp.getCampCode()))
                .mapToInt(entry -> entry.getValue())
                .sum();
        if (camp.getMaxNum() != null && camp.getMaxNum() <= campSkillSum) {
            return false;
        }

        return true;
    }

    private boolean isAllocatableSkill(Map<Skill, Integer> skillPersonNumMap, SkillAllocation skillAllocation, CDef.Camp camp) {
        // 配分が0
        if (skillAllocation.getAllocation() <= 0) {
            return false;
        }
        // 既に最大人数
        Integer max = skillAllocation.getMaxNum();
        Integer current = skillPersonNumMap.get(CDef.Skill.codeOf(skillAllocation.getSkillCode()));
        if (max != null && current != null && max <= current) {
            return false;
        }

        return true;
    }

    private void addSkillPerson(Map<Skill, Integer> skillPersonNumMap, CDef.Skill skill) {
        if (skillPersonNumMap.containsKey(skill)) {
            Integer num = skillPersonNumMap.get(skill);
            skillPersonNumMap.put(skill, num + 1);
        } else {
            skillPersonNumMap.put(skill, 1);
        }
    }

    private CDef.Skill gachaSkill(List<SkillAllocation> skillAllocationList) {
        int sum = skillAllocationList.stream().mapToInt(s -> s.getAllocation()).sum();
        for (SkillAllocation skill : skillAllocationList) {
            Random rand = new Random();
            int random = rand.nextInt(sum);
            int skillAllocation = skill.getAllocation();
            if (random < skillAllocation) {
                return CDef.Skill.codeOf(skill.getSkillCode());
            }
            sum -= skillAllocation;
        }
        return null; // should not reach
    }

    private CDef.Camp gachaCamp(List<CampAllocation> campAllocationList) {
        int sum = campAllocationList.stream().mapToInt(s -> s.getAllocation()).sum();
        for (CampAllocation camp : campAllocationList) {
            Random rand = new Random();
            int random = rand.nextInt(sum);
            int campAllocation = camp.getAllocation();
            if (random < campAllocation) {
                return CDef.Camp.codeOf(camp.getCampCode());
            }
            sum -= campAllocation;
        }
        return null; // should not reach
    }
}
