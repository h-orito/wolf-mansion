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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ort.app.web.model.CharaGroupListResultContent;
import com.ort.app.web.model.CharaGroupResultContent;
import com.ort.app.web.model.GroupCharaDto;
import com.ort.app.web.model.inner.CharaGroupCharaDto;
import com.ort.app.web.model.inner.CharaGroupListCharaGroupDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaBhv;
import com.ort.dbflute.exbhv.CharaGroupBhv;
import com.ort.dbflute.exbhv.CharaImageBhv;
import com.ort.dbflute.exbhv.VillagePlayerBhv;
import com.ort.dbflute.exentity.Chara;
import com.ort.dbflute.exentity.CharaGroup;
import com.ort.dbflute.exentity.CharaImage;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.fw.security.UserInfo;
import com.ort.fw.util.WerewolfMansionUserInfoUtil;

@Controller
public class CharaController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private CharaGroupBhv charaGroupBhv;
    @Autowired
    private CharaBhv charaBhv;
    @Autowired
    private CharaImageBhv charaImageBhv;
    @Autowired
    private VillagePlayerBhv villagePlayerBhv;

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

    @GetMapping("/getCharacterList/{charaGroupId}")
    @ResponseBody
    private List<GroupCharaDto> getCharacterList(@PathVariable Integer charaGroupId) {
        ListResultBean<Chara> charaList = charaBhv.selectList(cb -> {
            cb.query().setCharaGroupId_Equal(charaGroupId);
            cb.query().addOrderBy_DefaultJoinMessage_Desc().withNullsLast();
            cb.query().addOrderBy_CharaId_Asc();
        });
        charaBhv.loadCharaImage(charaList, charaImgCB -> {
            charaImgCB.query().setFaceTypeCode_Equal_通常();
        });
        return charaList.stream().map(chara -> {
            GroupCharaDto groupChara = new GroupCharaDto();
            groupChara.setCharaId(chara.getCharaId());
            groupChara.setCharaName(chara.getCharaName());
            groupChara.setCharaImgHeight(chara.getDisplayHeight());
            groupChara.setCharaImgWidth(chara.getDisplayWidth());
            groupChara.setJoinMessage(chara.getDefaultJoinMessage());
            groupChara.setFirstDayMessage(chara.getDefaultFirstdayMessage());
            groupChara.setCharaImgUrl(chara.getCharaImageList().get(0).getCharaImgUrl());
            return groupChara;
        }).collect(Collectors.toList());
    }

    @GetMapping("/getFaceImgUrl/{villageId}/{faceTypeCode}")
    @ResponseBody
    private String getFaceImgUrl(@PathVariable Integer villageId, @PathVariable String faceTypeCode) {
        UserInfo userInfo = WerewolfMansionUserInfoUtil.getUserInfo();
        OptionalEntity<VillagePlayer> optVillagePlayer = selectVillagePlayer(villageId, userInfo);
        if (!optVillagePlayer.isPresent()) {
            return null;
        }
        CDef.FaceType faceType = CDef.FaceType.codeOf(faceTypeCode);
        if (faceType == null) {
            return null;
        }
        OptionalEntity<CharaImage> optImage = charaImageBhv.selectEntity(cb -> {
            cb.query().setCharaId_Equal(optVillagePlayer.get().getCharaId());
            cb.query().setFaceTypeCode_Equal_AsFaceType(faceType);
        });
        return optImage.map(im -> im.getCharaImgUrl()).orElse(null);
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
        part.setDummyImgUrl(charaGroup.getCharaList().get(0).getCharaImageList().get(0).getCharaImgUrl());
        part.setDummyImgWidth(charaGroup.getCharaList().get(0).getDisplayWidth());
        part.setDummyImgHeight(charaGroup.getCharaList().get(0).getDisplayHeight());
        return part;
    }

    private CharaGroupResultContent mappingToDetailContent(CharaGroup charaGroup) {
        CharaGroupResultContent content = new CharaGroupResultContent();
        content.setCharaGroupId(charaGroup.getCharaGroupId());
        content.setCharaGroupName(charaGroup.getCharaGroupName());
        content.setDesignerName(charaGroup.getDesigner().get().getDesignerName());
        content.setDescriptionUrl(charaGroup.getDescriptionUrl());
        content.setCharaList(charaGroup.getCharaList().stream().map(chara -> convertToCharaDto(chara)).collect(Collectors.toList()));
        return content;
    }

    private CharaGroupCharaDto convertToCharaDto(Chara chara) {
        CharaGroupCharaDto part = new CharaGroupCharaDto();
        part.setCharaId(chara.getCharaId());
        part.setCharaName(chara.getCharaName());
        part.setCharaShortName(chara.getCharaShortName());
        part.setCharaImgUrlList(chara.getCharaImageList().stream().map(charaImg -> charaImg.getCharaImgUrl()).collect(Collectors.toList()));
        part.setCharaImgWidth(chara.getDisplayWidth());
        part.setCharaImgHeight(chara.getDisplayHeight());
        return part;
    }

    // ===================================================================================
    //                                                                              Select
    //                                                                              ======
    private ListResultBean<CharaGroup> selectCharaGroupList() {
        ListResultBean<CharaGroup> charaGroupList = charaGroupBhv.selectList(cb -> {
            cb.setupSelect_Designer();
            cb.query().addOrderBy_CharaGroupId_Asc();
        });
        charaGroupBhv.load(charaGroupList, loader -> {
            loader.loadChara(charaCB -> {
                charaCB.query().addOrderBy_DefaultJoinMessage_Asc().withNullsLast();
            }).withNestedReferrer(charaLoader -> {
                charaLoader.loadCharaImage(charaImageCB -> charaImageCB.query().setFaceTypeCode_Equal_通常());
            });
        });
        return charaGroupList;
    }

    private CharaGroup selectCharaGroup(Integer charaGroupId) {
        CharaGroup charaGroup = charaGroupBhv.selectEntityWithDeletedCheck(cb -> {
            cb.setupSelect_Designer();
            cb.query().setCharaGroupId_Equal(charaGroupId);
        });
        charaGroupBhv.load(charaGroup, loader -> {
            loader.loadChara(charaCB -> {
                charaCB.query().addOrderBy_DefaultJoinMessage_Desc();
                charaCB.query().addOrderBy_CharaId_Asc();
            }).withNestedReferrer(charaLoader -> {
                charaLoader.loadCharaImage(charaImageCB -> {
                    charaImageCB.query().queryFaceType().addOrderBy_DispOrder_Asc();
                });
            });
        });
        return charaGroup;
    }

    private OptionalEntity<VillagePlayer> selectVillagePlayer(Integer villageId, UserInfo userInfo) {
        if (userInfo == null) {
            return OptionalEntity.empty();
        }
        return villagePlayerBhv.selectEntity(cb -> {
            cb.setupSelect_Player();
            cb.setupSelect_SkillBySkillCode();
            cb.query().setVillageId_Equal(villageId);
            cb.query().setIsGone_Equal_False();
            cb.query().queryPlayer().setPlayerName_Equal(userInfo.getUsername());
        });
    }
}
