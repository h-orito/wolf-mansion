package com.ort.app.web.controller.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class FootstepLogic {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // 足音：なし
    private static final String NO_FOOTSTEP = "なし";

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private FootstepBhv footStepBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 足音を上から時計回りに作る
    public String makeClockwiseFootStep(Village village, Integer fromCharaId, Integer toCharaId, List<VillagePlayer> villagePlayerList) {
        FootStep footStep = new FootStep();
        // 始点の部屋番号（1始まりで計算しにくいので、0始まりにする）
        footStep.startRoomNum =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(fromCharaId)).findFirst().get().getRoomNumber() - 1;
        // 終点の部屋番号（1始まりで計算しにくいので、0始まりにする）
        footStep.destRoomNum =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(toCharaId)).findFirst().get().getRoomNumber() - 1;
        // 部屋の横サイズ
        footStep.roomWidth = village.getRoomSizeWidth();

        // 上
        addUpFootStep(footStep);
        // 右
        addRightFootStep(footStep);
        // 下
        addDownFootStep(footStep);
        // 左
        addLeftFootStep(footStep);

        if (footStep.footStepList.size() == 0) {
            return NO_FOOTSTEP;
        }
        // 数字の若い順、カンマ区切りにする
        Collections.sort(footStep.footStepList);
        StringJoiner joiner = new StringJoiner(",");
        footStep.footStepList.stream().forEach(fs -> joiner.add(fs.toString()));
        return joiner.toString();
    }

    // 足音を左から反時計周りに作る
    public String makeCounterclockwiseFootStep(Village village, Integer fromCharaId, Integer toCharaId,
            List<VillagePlayer> villagePlayerList) {
        FootStep footStep = new FootStep();
        // 始点の部屋番号（1始まりで計算しにくいので、0始まりにする）
        footStep.startRoomNum =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(fromCharaId)).findFirst().get().getRoomNumber() - 1;
        // 終点の部屋番号（1始まりで計算しにくいので、0始まりにする）
        footStep.destRoomNum =
                villagePlayerList.stream().filter(vp -> vp.getCharaId().equals(toCharaId)).findFirst().get().getRoomNumber() - 1;
        // 部屋の横サイズ
        footStep.roomWidth = village.getRoomSizeWidth();

        // 左
        addLeftFootStep(footStep);
        // 下
        addDownFootStep(footStep);
        // 右
        addRightFootStep(footStep);
        // 上
        addUpFootStep(footStep);

        if (footStep.footStepList.size() == 0) {
            return NO_FOOTSTEP;
        }
        // 数字の若い順、カンマ区切りにする
        Collections.sort(footStep.footStepList);
        StringJoiner joiner = new StringJoiner(",");
        footStep.footStepList.stream().forEach(fs -> joiner.add(fs.toString()));
        return joiner.toString();
    }

    // 足音候補を取得
    public List<String> getFootstepCandidateList(Integer villageId, VillagePlayer villagePlayer, int day, Integer charaId,
            Integer targetCharaId) {
        CDef.Skill skill = villagePlayer.getSkillCodeAsSkill();
        Village village = selectVillage(villageId);
        List<VillagePlayer> villagePlayerList = selectVillagePlayerList(villageId);

        if (skill == CDef.Skill.人狼) {
            return makeWolfSeerFootstepCandidateList(charaId, targetCharaId, village, villagePlayerList);
        } else if (skill == CDef.Skill.占い師) {
            return makeWolfSeerFootstepCandidateList(villagePlayer.getCharaId(), targetCharaId, village, villagePlayerList);
        }
        return null;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<VillagePlayer> selectVillagePlayerList(Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
        });
    }

    private Village selectVillage(Integer villageId) {
        return villageBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.query().setVillageId_Equal(villageId);
        });
    }

    // ===================================================================================
    //                                                                              Update
    //                                                                              ======
    public void insertFootStep(Integer villageId, int day, Integer charaId, String footStepStr) {
        Footstep footStep = new Footstep();
        footStep.setVillageId(villageId);
        footStep.setDay(day);
        footStep.setCharaId(charaId);
        footStep.setFootstepRoomNumbers(footStepStr);
        footStepBhv.insert(footStep);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============

    // 上
    private void addUpFootStep(FootStep footStep) {
        while (true) {
            int fromy = footStep.startRoomNum / footStep.roomWidth; // 始点のy座標
            int toy = footStep.destRoomNum / footStep.roomWidth; // 終点のy座標
            if (fromy <= toy) {
                break;
            }
            footStep.startRoomNum -= footStep.roomWidth;
            if (footStep.startRoomNum != footStep.destRoomNum) {
                footStep.footStepList.add(footStep.startRoomNum + 1); // 0始まりにするために1減らしていたので1加算
            }
        }
    }

    // 右
    private void addRightFootStep(FootStep footStep) {
        while (true) {
            int fromx = footStep.startRoomNum % footStep.roomWidth; // 始点のx座標
            int tox = footStep.destRoomNum % footStep.roomWidth; // 終点のx座標
            if (tox <= fromx) {
                break;
            }
            footStep.startRoomNum += 1;
            if (footStep.startRoomNum != footStep.destRoomNum) {
                footStep.footStepList.add(footStep.startRoomNum + 1); // 0始まりにするために1減らしていたので1加算
            }
        }
    }

    // 下
    private void addDownFootStep(FootStep footStep) {
        while (true) {
            int fromy = footStep.startRoomNum / footStep.roomWidth; // 始点のy座標
            int toy = footStep.destRoomNum / footStep.roomWidth; // 終点のy座標
            if (toy <= fromy) {
                break;
            }
            footStep.startRoomNum += footStep.roomWidth;
            if (footStep.startRoomNum != footStep.destRoomNum) {
                footStep.footStepList.add(footStep.startRoomNum + 1); // 0始まりにするために1減らしていたので1加算
            }
        }
    }

    // 左
    private void addLeftFootStep(FootStep footStep) {
        while (true) {
            int fromx = footStep.startRoomNum % footStep.roomWidth; // 始点のx座標
            int tox = footStep.destRoomNum % footStep.roomWidth; // 終点のx座標
            if (fromx <= tox) {
                break;
            }
            footStep.startRoomNum -= 1;
            if (footStep.startRoomNum != footStep.destRoomNum) {
                footStep.footStepList.add(footStep.startRoomNum + 1); // 0始まりにするために1減らしていたので1加算
            }
        }
    }

    // 人狼と占いの足音候補リスト
    private List<String> makeWolfSeerFootstepCandidateList(Integer charaId, Integer targetCharaId, Village village,
            List<VillagePlayer> villagePlayerList) {
        Set<String> footstepSet = new HashSet<>();
        if (targetCharaId == null) {
            // 襲撃なし
            footstepSet.add(NO_FOOTSTEP);
            return new ArrayList<>(footstepSet);
        }
        // 右回り左回りの足音を足して重複を削除
        footstepSet.add(this.makeClockwiseFootStep(village, charaId, targetCharaId, villagePlayerList));
        footstepSet.add(this.makeCounterclockwiseFootStep(village, charaId, targetCharaId, villagePlayerList));
        return new ArrayList<>(footstepSet);
    }

    // ===================================================================================
    //                                                                         inner class
    //                                                                            ========
    private static class FootStep {
        private int startRoomNum; // 部屋番号（現在位置）
        private int destRoomNum; // 部屋番号（目的地）
        private int roomWidth; // 部屋の横幅
        private List<Integer> footStepList = new ArrayList<>(); // 足音リスト
    }
}
