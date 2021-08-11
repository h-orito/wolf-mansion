package com.ort.app.web.controller;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.optional.OptionalScalar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ort.app.logic.VillageParticipateLogic;
import com.ort.app.web.form.LoginForm;
import com.ort.app.web.form.VillageRecordListForm;
import com.ort.app.web.model.IndexResultContent;
import com.ort.app.web.model.RecruitingResultContent;
import com.ort.app.web.model.SkillListResultContent;
import com.ort.app.web.model.VillageRecordLatestVidResultContent;
import com.ort.app.web.model.VillageRecordListResultContent;
import com.ort.app.web.model.inner.CampSkillDto;
import com.ort.app.web.model.inner.IndexVillageDto;
import com.ort.app.web.model.inner.RecruitingVillageDto;
import com.ort.app.web.model.inner.VillageParticipantRecordDto;
import com.ort.app.web.model.inner.VillageRecordDto;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CampBhv;
import com.ort.dbflute.exbhv.VillageBhv;
import com.ort.dbflute.exentity.Camp;
import com.ort.dbflute.exentity.Skill;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillageSettings;

@Controller
public class IndexController {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VillageBhv villageBhv;
    @Autowired
    private CampBhv campBhv;
    @Autowired
    private VillageParticipateLogic participateLogic;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @GetMapping("/")
    private String index(LoginForm form, Model model) {
        setIndexModel(form, model);
        return "index";
    }

    @GetMapping("/recruiting")
    @ResponseBody
    private RecruitingResultContent recruiting() {
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {
            cb.setupSelect_VillageSettingsAsOne().withCharaGroup();
            cb.setupSelect_VillageStatus();
            cb.query()
                    .setVillageStatusCode_InScope_AsVillageStatus(
                            Arrays.asList(CDef.VillageStatus.募集中, CDef.VillageStatus.エピローグ, CDef.VillageStatus.進行中));
            cb.query().setVillageDisplayName_NotEqual("【サンプル】インターフェース確認用");
            cb.query().addOrderBy_VillageId_Asc();
        });
        villageBhv.load(villageList, loader -> {
            loader.loadVillagePlayer(vpCB -> {
                vpCB.query().setIsGone_Equal_False();
            });
            loader.loadVillageDay(vdCB -> {
                vdCB.query().addOrderBy_DaychangeDatetime_Desc();
            });
            loader.loadNormalSayRestriction(restCB -> {});
            loader.loadSkillSayRestriction(restCB -> {});
        });
        return mappingToRecruitingContent(villageList);
    }

    @GetMapping("/village-record/list")
    @ResponseBody
    private VillageRecordListResultContent villageRecordList( //
            VillageRecordListForm form //
    ) {
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {
            cb.setupSelect_VillageSettingsAsOne().withCharaGroup();
            cb.query()
                    .setVillageStatusCode_InScope_AsVillageStatus(//
                            Arrays.asList(//
                                    CDef.VillageStatus.エピローグ, //
                                    CDef.VillageStatus.終了, //
                                    CDef.VillageStatus.廃村)//
            );
            if (CollectionUtils.isNotEmpty(form.getVid())) {
                cb.query().setVillageId_InScope(form.getVid());
            }
            cb.query().addOrderBy_VillageId_Desc();
        });
        if (villageList.isEmpty()) {
            return new VillageRecordListResultContent();
        }
        villageBhv.load(villageList, loader -> {
            loader.loadVillagePlayer(vpCB -> {
                vpCB.setupSelect_Chara();
                vpCB.setupSelect_Player();
                vpCB.query().setIsGone_Equal_False();
            });
            loader.loadVillageDay(vdCB -> {
                vdCB.query().addOrderBy_DaychangeDatetime_Desc();
            });
        });
        return mappingToVillageRecordListContent(villageList);
    }

    @GetMapping("/village-record/latest-vid")
    @ResponseBody
    private VillageRecordLatestVidResultContent latestVillageRecordVid() {
        OptionalScalar<Integer> optVid = villageBhv.selectScalar(Integer.class).max(cb -> {
            cb.specify().columnVillageId();
            cb.query()
                    .setVillageStatusCode_InScope_AsVillageStatus(//
                            Arrays.asList(//
                                    CDef.VillageStatus.エピローグ, //
                                    CDef.VillageStatus.終了, //
                                    CDef.VillageStatus.廃村)//
            );
        });
        return optVid.map(vid -> new VillageRecordLatestVidResultContent(vid)).orElse(null);
    }

    @GetMapping("/skill/list")
    @ResponseBody
    private SkillListResultContent skillList() {
        ListResultBean<Camp> campList = campBhv.selectList(cb -> {
            cb.query().addOrderBy_CampCode_Asc();
        });
        campBhv.loadSkill(campList, skillCB -> {
            skillCB.query().addOrderBy_DispOrder_Asc();
            skillCB.query().setSkillCode_NotInScope_AsSkill(CDef.Skill.listOfSomeoneSkill());
        });
        SkillListResultContent content = new SkillListResultContent();
        content.setList(campList.stream().map(camp -> {
            CampSkillDto campSkills = new CampSkillDto();
            campSkills.setCampName(camp.getCampName());
            campSkills.setSkillList(camp.getSkillList().stream().map(Skill::getSkillName).collect(Collectors.toList()));
            return campSkills;
        }).collect(Collectors.toList()));
        return content;
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private IndexResultContent mappingToContent(ListResultBean<Village> villageList) {
        IndexResultContent content = new IndexResultContent();
        content.setVillageList(villageList.mappingList(village -> convertToIndexVillage(village)));
        content.setIsParticipate(participateLogic.isParticipatingOrCreatingVillage());
        return content;
    }

    private IndexVillageDto convertToIndexVillage(Village village) {
        IndexVillageDto villageDto = new IndexVillageDto();
        villageDto.setVillageId(village.getVillageId());
        villageDto.setVillageNumber(village.getVillageNumber());
        villageDto.setVillageName(village.getVillageDisplayName());
        int participateNum = village.getVillagePlayerList().size();
        Integer maxNum = village.getVillageSettingsAsOne().get().getPersonMaxNum();
        villageDto.setParticipateNum(String.format("%d/%d人", participateNum, maxNum));
        villageDto.setStatus(village.getVillageStatus().get().getVillageStatusName());
        return villageDto;
    }

    private RecruitingResultContent mappingToRecruitingContent(ListResultBean<Village> villageList) {
        RecruitingResultContent content = new RecruitingResultContent();
        content.setVillageList(villageList.mappingList(village -> convertToRecruitingVillage(village)));
        return content;
    }

    private RecruitingVillageDto convertToRecruitingVillage(Village village) {
        RecruitingVillageDto villageDto = new RecruitingVillageDto();
        villageDto.setVillageId(village.getVillageId());
        villageDto.setVillageNumber(village.getVillageNumber());
        villageDto.setVillageName(village.getVillageDisplayName());
        int participateNum = (int) village.getVillagePlayerList().stream().filter(vp -> vp.isIsSpectatorFalse()).count();
        VillageSettings settings = village.getVillageSettingsAsOne().get();
        Integer maxNum = settings.getPersonMaxNum();
        villageDto.setParticipateNum(String.format("%d/%d", participateNum, maxNum));
        int spectateNum = (int) village.getVillagePlayerList().stream().filter(vp -> vp.isIsSpectatorTrue()).count();
        villageDto.setSpectateNum(String.valueOf(spectateNum));
        villageDto.setDaychangeDatetime(
                village.getVillageDayList().get(0).getDaychangeDatetime().format(DateTimeFormatter.ofPattern("hh:mm")));
        villageDto.setDaychangeInterval(makeIntervalStr(settings));
        villageDto.setCharaset(settings.getCharaGroup().get().getCharaGroupName());
        villageDto.setRestrict(CollectionUtils.isNotEmpty(village.getNormalSayRestrictionList())
                || CollectionUtils.isNotEmpty(village.getSkillSayRestrictionList()) ? "あり" : "なし");
        villageDto.setStatus(village.getVillageStatus().get().getVillageStatusName());
        villageDto.setUrl("https://wolfort.net/wolf-mansion/village/" + village.getVillageId());
        return villageDto;
    }

    private VillageRecordListResultContent mappingToVillageRecordListContent(ListResultBean<Village> villageList) {
        VillageRecordListResultContent content = new VillageRecordListResultContent();
        content.setList(villageList.stream().map(village -> convertToVillageRecord(village)).collect(Collectors.toList()));
        return content;
    }

    private VillageRecordDto convertToVillageRecord(Village village) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm");
        VillageRecordDto villageRecord = new VillageRecordDto();
        villageRecord.setId(village.getVillageId());
        villageRecord.setName(village.getVillageDisplayName());
        villageRecord.setStatus(village.getVillageStatusCodeAsVillageStatus().alias());
        villageRecord.setOrganization(extractOrganization(village));
        villageRecord.setIntervalSeconds(village.getVillageSettingsAsOne().get().getDayChangeIntervalSeconds());
        villageRecord.setStartDatetime(
                village.isVillageStatusCode廃村() ? null : village.getVillageSettingsAsOne().get().getStartDatetime().format(formatter));
        villageRecord.setPrologueDatetime(extractPrologueDatetime(village, formatter));
        villageRecord.setEpilogueDatetime(village.isVillageStatusCode廃村() ? null : extractEpilogueDatetime(village, formatter));
        villageRecord.setEpilogueDay(village.getEpilogueDay());
        villageRecord.setUrl("https://wolfort.net/wolf-mansion/village/" + village.getVillageId());
        villageRecord.setWinCampName(village.isVillageStatusCode廃村() ? null : village.getWinCampCodeAsCamp().alias());
        villageRecord.setParticipantList(village.getVillagePlayerList()
                .stream()
                .map(vp -> convertToVillageParticipantRecord(village, vp))
                .collect(Collectors.toList()));
        return villageRecord;
    }

    private VillageParticipantRecordDto convertToVillageParticipantRecord(Village village, VillagePlayer vp) {
        VillageParticipantRecordDto record = new VillageParticipantRecordDto();
        record.setUserId(vp.getPlayer().get().getPlayerName());
        record.setCharacterName(vp.getCharaName());
        record.setSkillName(vp.getSkillCodeAsSkill() == null ? null : vp.getSkillCodeAsSkill().alias());
        record.setIsSpectator(vp.getIsSpectator());
        record.setIsWin(vp.getIsWin());
        record.setIsDead(vp.getIsDead());
        record.setDeadDay(vp.getDeadDay());
        record.setDeadReason(extractDeadReason(vp));
        return record;
    }

    private String extractDeadReason(VillagePlayer vp) {
        if (!BooleanUtils.isTrue(vp.getIsDead())) {
            return null;
        }
        String reason = vp.getDeadReasonCodeAsDeadReason().alias();
        if (reason.endsWith("死")) {
            return reason;
        } else {
            return reason + "死";
        }
    }

    private String extractOrganization(Village village) {
        // 人数
        if (village.isVillageStatusCode廃村()) {
            return Stream.of(village.getVillageSettingsAsOne().get().getOrganize().replaceAll("\r\n", "\n").split("\n"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("no org. vid=" + village.getVillageId()));
        }
        long count = village.getVillagePlayerList().stream().filter(vp -> vp.isIsSpectatorFalse()).count();
        return Stream.of(village.getVillageSettingsAsOne().get().getOrganize().replaceAll("\r\n", "\n").split("\n"))
                .filter(org -> org.length() == count)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no org. vid=" + village.getVillageId()));
    }

    private String makeIntervalStr(VillageSettings settings) {
        Integer seconds = settings.getDayChangeIntervalSeconds();
        if (seconds >= 3600) {
            return seconds / 3600 + "h";
        }
        return seconds / 60 + "m";
    }

    private String extractPrologueDatetime(Village village, DateTimeFormatter formatter) {
        return village.getRegisterDatetime().format(formatter);
    }

    private String extractEpilogueDatetime(Village village, DateTimeFormatter formatter) {
        return village.getVillageDayList()
                .stream()
                .filter(day -> day.getDay().equals(village.getEpilogueDay() - 1))
                .findFirst()
                .map(vd -> vd.getDaychangeDatetime().format(formatter))
                .orElse(null);
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private void setIndexModel(LoginForm form, Model model) {
        model.addAttribute("form", form);
        ListResultBean<Village> villageList = villageBhv.selectList(cb -> {
            cb.setupSelect_VillageSettingsAsOne();
            cb.setupSelect_VillageStatus();
            cb.query()
                    .setVillageStatusCode_InScope_AsVillageStatus(Arrays.asList(CDef.VillageStatus.エピローグ, CDef.VillageStatus.募集中,
                            CDef.VillageStatus.進行中, CDef.VillageStatus.開始待ち));
            cb.query().addOrderBy_VillageId_Asc();
        });
        villageBhv.loadVillagePlayer(villageList, cb -> {
            cb.query().setIsGone_Equal_False();
            cb.query().setIsSpectator_Equal_False();
        });
        IndexResultContent content = mappingToContent(villageList);
        model.addAttribute("content", content);
    }
}