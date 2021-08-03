package com.ort.app.logic.daychange.ability;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.datasource.VillageService;
import com.ort.app.logic.MessageLogic;
import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.app.logic.message.MessageEntity;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Skills;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class RevivalLogic {

    private static final List<CDef.Skill> notRevivalableSkillList = Arrays.asList(CDef.Skill.同棲者, CDef.Skill.恋人);

    @Autowired
    private MessageLogic messageLogic;
    @Autowired
    private VillageService villageService;

    public void revival(DayChangeVillage dayChangeVillage) {
        revivalAbsoluteWolf(dayChangeVillage);
        revivalReincarnation(dayChangeVillage);
        revivalHeavenChild(dayChangeVillage);
    }

    private void revivalAbsoluteWolf(DayChangeVillage dayChangeVillage) {
        // 絶対人狼以外の人狼系役職が生存しているか
        boolean isAliveWolfWithoutAbsoluteWolf = dayChangeVillage.vPlayers.filterBy(vp -> {
            return vp.getSkillCodeAsSkill().isHasAttackAbility() && vp.getSkillCodeAsSkill() != CDef.Skill.絶対人狼;
        }).list.stream().anyMatch(vp -> dayChangeVillage.isAlive(vp));
        if (!isAliveWolfWithoutAbsoluteWolf) {
            return;
        }
        dayChangeVillage.deadPlayers.getList().stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.絶対人狼).forEach(vp -> {
            villageService.revive(vp, dayChangeVillage.day);
            dayChangeVillage.deadPlayers.remove(vp);
            saveRevivalMessage(dayChangeVillage, vp);
        });
    }

    private void revivalReincarnation(DayChangeVillage dayChangeVillage) {
        dayChangeVillage.deadPlayers.getList().stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.転生者).forEach(vp -> {
            villageService.revive(vp, dayChangeVillage.day);
            dayChangeVillage.deadPlayers.remove(vp);
            // ランダム役職で蘇生
            CDef.Skill skill = Skills.of().filterNotSomeone().filterBy(s -> !notRevivalableSkillList.contains(s)).getRandom();
            villageService.updateSkill(vp, skill);
            saveRevivalMessage(dayChangeVillage, vp);
            // 後続処理のためここもset
            vp.setSkillCodeAsSkill(skill);
            if (!vp.hasLover() && !vp.isFoxPossessioned()) {
                vp.setCampCode(skill.campCode());
            }
        });
    }

    private void revivalHeavenChild(DayChangeVillage dayChangeVillage) {
        dayChangeVillage.deadPlayers.getList().stream().filter(vp -> vp.getSkillCodeAsSkill() == CDef.Skill.申し子).forEach(vp -> {
            villageService.revive(vp, dayChangeVillage.day);
            dayChangeVillage.deadPlayers.remove(vp);
            // 村陣営のランダム役職で蘇生
            CDef.Skill skill = Skills.of()
                    .filterNotSomeone()
                    .filterByCamp(CDef.Camp.村人陣営)
                    .filterBy(s -> !notRevivalableSkillList.contains(s))
                    .getRandom();
            villageService.updateSkill(vp, skill);
            saveRevivalMessage(dayChangeVillage, vp);
            // 後続処理のためここもset
            vp.setSkillCodeAsSkill(skill);
            if (!vp.hasLover() && !vp.isFoxPossessioned()) {
                vp.setCampCode(skill.campCode());
            }
        });
    }

    private void saveRevivalMessage(DayChangeVillage dayChangeVillage, VillagePlayer vp) {
        messageLogic.saveIgnoreError(MessageEntity.publicSystemBuilder(dayChangeVillage.villageId, dayChangeVillage.day)
                .content(String.format("不思議なことに、%sが生き返った。", vp.name()))
                .build());
    }
}
