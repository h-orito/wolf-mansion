package com.ort.app.web.controller.assist;

import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.ort.app.web.model.VillageListResultContent;
import com.ort.app.web.model.inner.VillageListVillageDto;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Village;

@Component
public class VillageListAssist {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======

    public String setIndexModel(Model model) {
        ListResultBean<Village> villageList = selectVillageList();
        VillageListResultContent content = mappingToContent(villageList);
        model.addAttribute("content", content);
        return "village-list";
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private VillageListResultContent mappingToContent(ListResultBean<Village> villageList) {
        VillageListResultContent content = new VillageListResultContent();
        content.setVillageList(villageList.stream().map(village -> convertToVillageDto(village)).collect(Collectors.toList()));
        return content;
    }

    private VillageListVillageDto convertToVillageDto(Village village) {
        VillageListVillageDto part = new VillageListVillageDto();
        part.setVillageId(village.getVillageId());
        part.setVillageNumber(String.format("%04d", village.getVillageId()));
        part.setVillageName(village.getVillageDisplayName());
        int participateNum = village.getVillagePlayerList().size();
        Integer maxNum = village.getVillageSettingsAsOne().get().getPersonMaxNum();
        part.setParticipateNum(String.format("%d/%d", participateNum, maxNum));
        part.setStatus(village.getVillageStatusCodeAsVillageStatus().alias());
        return part;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<Village> selectVillageList() {
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {
            cb.setupSelect_Camp();
            cb.setupSelect_VillageSettingsAsOne();
            cb.setupSelect_VillageStatus();
            cb.query().addOrderBy_VillageId_Desc();
        });
        villageBhv.loadVillagePlayer(villageList, cb -> {
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
        });
        return villageList;
    }
}
