package com.ort.app.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.web.form.VillageLeaveForm;
import com.ort.app.web.form.VillageParticipateForm;
import com.ort.app.web.model.VillageCharaPlayerResultContent;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.VillageDayBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.VillageDay;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.Vote;
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
    private VillagePlayerBhv villagePlayerBhv;

    @Autowired
    private VoteBhv voteBhv;

    @Autowired
    private VillageParticipateLogic villageLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    // ????????????????????????
    @PostMapping("/admin/village/{villageId}/allparticipate")
    private String allparticipate(@PathVariable Integer villageId, VillageParticipateForm participateForm, Model model) {
        // ????????????????????????????????????????????????
        ListResultBean<Chara> charaList = charaBhv.selectList(cb -> {
            cb.query().queryCharaGroup().existsVillageSettings(
                    villageSettingsCB -> villageSettingsCB.query().setVillageId_Equal(villageId));
            cb.query().notExistsVillagePlayer(villagePlayerCB -> {
                villagePlayerCB.query().setVillageId_Equal(villageId);
                villagePlayerCB.query().setIsGone_Equal_False();
            });
            cb.fetchFirst(participateForm.getPersonNumber());
        });
        for (int i = 0; i < charaList.size(); i++) {
            int playerId = i + 2;
            // ????????????????????????????????????
            CDef.Skill randomSkill = CDef.Skill.values()[(int) (Math.random() * CDef.Skill.values().length - 1)];
            CDef.Skill randomSkill2 = CDef.Skill.values()[(int) (Math.random() * CDef.Skill.values().length - 1)];
            // ??????
            villageLogic.participate(villageId, playerId, charaList.get(i).getCharaId(), randomSkill, randomSkill2,
                    "???????????????????????????????????????playerId???" + playerId, false, true);
        }

        return "redirect:/village/" + villageId;
    }

    // ????????????????????????????????????
    @PostMapping("/admin/village/{villageId}/dayChange")
    private String daychange(@PathVariable Integer villageId) {
        // ?????????????????????????????????????????????
        VillageDay latestDay = villageDayBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get();
        VillageDay villageDay = new VillageDay();
        villageDay.setDaychangeDatetime(WerewolfMansionDateUtil.currentLocalDateTime().minusSeconds(1L));
        villageDayBhv.queryUpdate(villageDay, cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(latestDay.getDay());
        });

        return "redirect:/village/" + villageId;
    }

    // ??????????????????????????????
    @PostMapping("/admin/village/{villageId}/leave")
    private String leave(@PathVariable Integer villageId, VillageLeaveForm leaveForm) {
        if (leaveForm.getVillagePlayerId() == null) {
            return "redirect:/village/" + villageId;
        }
        OptionalEntity<VillagePlayer> optVPlayer = villagePlayerBhv.selectByPK(leaveForm.getVillagePlayerId());
        if (!optVPlayer.isPresent()) {
            return "redirect:/village/" + villageId;
        }
        // ???????????????
        villageLogic.leave(optVPlayer.get());

        return "redirect:/village/" + villageId;
    }

    // ????????????????????????????????????
    @PostMapping("/admin/village/{villageId}/access")
    private String leave(@PathVariable Integer villageId) {
        VillagePlayer vp = new VillagePlayer();
        vp.setLastAccessDatetime(WerewolfMansionDateUtil.currentLocalDateTime());
        villagePlayerBhv.queryUpdate(vp, cb -> {
            cb.query().setVillageId_Equal(villageId);
        });
        return "redirect:/village/" + villageId;
    }

    // ????????????????????????????????????
    @PostMapping("/admin/village/{villageId}/vote")
    private String vote(@PathVariable Integer villageId) {
        Integer latestDay = villageDayBhv.selectEntity(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().addOrderBy_Day_Desc();
            cb.fetchFirst(1);
        }).get().getDay();
        List<Integer> voteCharaIdList = voteBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(latestDay);
        }).stream().map(Vote::getCharaId).collect(Collectors.toList());
        villagePlayerBhv.selectList(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
            cb.query().setIsDead_Equal_False();
            cb.query().setCharaId_NotInScope(voteCharaIdList);
        }).forEach(vp -> {
            Vote vote = new Vote();
            vote.setVillageId(villageId);
            vote.setDay(latestDay);
            vote.setCharaId(vp.getCharaId());
            vote.setVoteCharaId(vp.getCharaId());
            voteBhv.insert(vote);
        });
        return "redirect:/village/" + villageId;
    }

    // ???????????????????????????????????????
    @GetMapping("/admin/village/{villageId}/player")
    @ResponseBody
    private List<VillageCharaPlayerResultContent> player(@PathVariable Integer villageId) {
        return villagePlayerBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.setupSelect_Player();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().addOrderBy_VillagePlayerId_Asc();
        }).stream().map(vp -> {
            VillageCharaPlayerResultContent charaPlayer = new VillageCharaPlayerResultContent();
            charaPlayer.setCharaName(vp.getChara().get().getCharaName());
            charaPlayer.setPlayerName(vp.getPlayer().get().getPlayerName());
            return charaPlayer;
        }).collect(Collectors.toList());
    }
}
