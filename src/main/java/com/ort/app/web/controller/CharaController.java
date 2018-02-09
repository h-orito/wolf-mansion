package com.ort.app.web.controller;

import java.util.stream.Collectors;

import org.dbflute.cbean.result.ListResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ort.app.web.model.CharaGroupListResultContent;
import com.ort.app.web.model.CharaGroupResultContent;
import com.ort.app.web.model.inner.CharaGroupCharaDto;
import com.ort.app.web.model.inner.CharaGroupListCharaGroupDto;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.CharaGroup;

@Controller
public class CharaController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private CharaGroupBhv charaGroupBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/chara-group")
    private String charaIndex(Model model) {
        ListResultBean<CharaGroup> charaGroupList = selectCharaGroupList();
        CharaGroupListResultContent content = mappingToContent(charaGroupList);
        model.addAttribute("content", content);
        return "chara-list";
    }

    @GetMapping("/chara-group/{charaGroupId}")
    private String charaDetailIndex(@PathVariable Integer charaGroupId, Model model) {
        CharaGroup charaGroup = selectCharaGroup(charaGroupId);
        CharaGroupResultContent content = mappingToDetailContent(charaGroup);
        model.addAttribute("content", content);
        return "chara";
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private CharaGroupListResultContent mappingToContent(ListResultBean<CharaGroup> charaGroupList) {
        CharaGroupListResultContent content = new CharaGroupListResultContent();
        content.setCharaGroupList(
                charaGroupList.stream().map(charaGroup -> convertToCharaGroupDto(charaGroup)).collect(Collectors.toList()));
        return content;
    }

    private CharaGroupListCharaGroupDto convertToCharaGroupDto(CharaGroup charaGroup) {
        CharaGroupListCharaGroupDto part = new CharaGroupListCharaGroupDto();
        part.setCharaGroupId(charaGroup.getCharaGroupId());
        part.setCharaGroupName(charaGroup.getCharaGroupName());
        part.setDesignerName(charaGroup.getDesigner().get().getDesignerName());
        part.setCharaNum(charaGroup.getCharaList().size());
        part.setDummyImgUrl(charaGroup.getCharaList().stream().filter(c -> c.isIsDummyTrue()).findFirst().get().getCharaImgUrl());
        return part;
    }

    private CharaGroupResultContent mappingToDetailContent(CharaGroup charaGroup) {
        CharaGroupResultContent content = new CharaGroupResultContent();
        content.setCharaGroupId(charaGroup.getCharaGroupId());
        content.setCharaGroupName(charaGroup.getCharaGroupName());
        content.setDesignerName(charaGroup.getDesigner().get().getDesignerName());
        content.setCharaList(charaGroup.getCharaList().stream().map(chara -> convertToCharaDto(chara)).collect(Collectors.toList()));
        return content;
    }

    private CharaGroupCharaDto convertToCharaDto(Chara chara) {
        CharaGroupCharaDto part = new CharaGroupCharaDto();
        part.setCharaId(chara.getCharaId());
        part.setCharaName(chara.getCharaName());
        part.setCharaShortName(chara.getCharaShortName());
        part.setCharaImgUrl(chara.getCharaImgUrl());
        return part;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<CharaGroup> selectCharaGroupList() {
        ListResultBean<CharaGroup> charaGroupList = charaGroupBhv.selectList(cb -> {
            cb.setupSelect_Designer();
        });
        charaGroupBhv.loadChara(charaGroupList, cb -> {});
        return charaGroupList;
    }

    private CharaGroup selectCharaGroup(Integer charaGroupId) {
        CharaGroup charaGroup = charaGroupBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_Designer();
            cb.query().setCharaGroupId_Equal(charaGroupId);
        });
        charaGroupBhv.loadChara(charaGroup, cb -> {});
        return charaGroup;
    }
}
