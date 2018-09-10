package com.ort.app.web.controller;

import java.util.Arrays;

import javax.annotation.Resource;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ort.app.web.form.LoginForm;
import com.ort.app.web.model.IndexResultContent;
import com.ort.app.web.model.inner.IndexVillageDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.PlayerBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Village;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class IndexController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    @Resource
    private PlayerBhv playerBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/")
    private String index(LoginForm form, Model model) {
        setIndexModel(form, model);
        return "index";
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private IndexResultContent mappingToContent(ListResultBean<Village> villageList) {
        IndexResultContent content = new IndexResultContent();
        content.setVillageList(villageList.mappingList(village -> convertToIndexVillage(village)));
        content.setIsParticipate(isParticipate());
        return content;
    }

    private IndexVillageDto convertToIndexVillage(Village village) {
        IndexVillageDto villageDto = new IndexVillageDto();
        villageDto.setVillageId(village.getVillageId());
        villageDto.setVillageNumber(String.format("%04d", village.getVillageId()));
        villageDto.setVillageName(village.getVillageDisplayName());
        int participateNum = village.getVillagePlayerList().size();
        Integer maxNum = village.getVillageSettingsAsOne().get().getPersonMaxNum();
        villageDto.setParticipateNum(String.format("%d/%d人", participateNum, maxNum));
        villageDto.setStatus(village.getVillageStatus().get().getVillageStatusName());
        return villageDto;
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void setIndexModel(LoginForm form, Model model) {
        model.addAttribute("form", form);
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.setupSelect_VillageStatus();
            cb.query().setVillageStatusCode_InScope_AsVillageStatus(
                    Arrays.asList(CDef.VillageStatus.エピローグ, CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
            cb.query().addOrderBy_VillageId_Asc();
        });
        villageBhv.loadVillagePlayer(villageList, cb -> {
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
        });
        IndexResultContent content = mappingToContent(villageList);
        model.addAttribute("content", content);
    }

    private boolean isParticipate() {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        if (userInfo == null) {
            return false;
        }
        if ("master".equals(userInfo.getUsername())) {
            return false; // masterは参加してない扱い
        }
        int participateCount = playerBhv.selectCount(cb -> {
            cb.query().setPlayerName_Equal(userInfo.getUsername());
            // 募集中、開始待ち、進行中の村に参戦している
            cb.query().existsVillagePlayer(villagePlayerCB -> {
                villagePlayerCB.query().queryVillage().setVillageStatusCode_InScope_AsVillageStatus(
                        Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
                villagePlayerCB.query().setIsGone_Equal_False();
            });
        });
        return participateCount > 0;
    }

}
