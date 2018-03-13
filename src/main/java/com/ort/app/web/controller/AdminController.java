package com.ort.app.web.controller;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ort.app.web.controller.logic.VillageParticipateLogic;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.fw.util.WerewolfMansionDateUtil;

@Controller
public class AdminController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private CharaBhv charaBhv;

    @Autowired
    private VillageDayBhv villageDayBhv;

    @Autowired
    private VillageParticipateLogic villageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // 管理者機能：参戦
    @PostMapping("/admin/village/{villageId}/allparticipate")
    private String allparticipate(@PathVariable Integer villageId, VillageParticipateForm participateForm, Model model) {
        // 参戦していないキャラを人数分探す
        ListResultBean<Chara> charaList = charaBhv.selectList(cb -> {
            cb.query().queryCharaGroup().existsVillageSettings(
                    villageSettingsCB -> villageSettingsCB.query().setVillageId_Equal(villageId));
            cb.query().notExistsVillagePlayer(villagePlayerCB -> villagePlayerCB.query().setVillageId_Equal(villageId));
            cb.fetchFirst(participateForm.getPersonNumber());
        });
        for (int i = 0; i < charaList.size(); i++) {
            int playerId = i + 2; // テストアカウントは2~
            // 希望役職をランダムに取得
            CDef.Skill randomSkill = CDef.Skill.values()[(int) (Math.random() * CDef.Skill.values().length - 1)];
            // 入村
            villageLogic.participate(villageId, playerId, charaList.get(i).getCharaId(), randomSkill, "テストアカウント入村です。playerId：" + playerId);
        }

        return "redirect:/village/" + villageId;
    }

    // 管理者機能：時間を進める
    @PostMapping("/admin/village/{villageId}/dayChange")
    private String daychange(@PathVariable Integer villageId) {
        // 最新の日付の更新日時を今にする
        VillageDay latestDay = villageDayBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        VillageDay villageDay = new VillageDay();
        villageDay.setDaychangeDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        villageDayBhv.queryUpdate(villageDay, cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(latestDay.getDay());
        });

        return "redirect:/village/" + villageId;
    }
}
