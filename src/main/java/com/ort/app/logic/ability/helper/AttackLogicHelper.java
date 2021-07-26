/**
 * 
 */
package com.ort.app.logic.ability.helper;

import org.dbflute.optional.OptionalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ort.app.logic.daychange.DayChangeVillage;
import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.CharaImageBhv;
import com.ort.dbflute.exentity.CharaImage;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class AttackLogicHelper {

    @Autowired
    private CharaImageBhv charaImageBhv;

    public boolean isAttackSuccess(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        if (!dayChangeVillage.isAlive(targetPlayer)) {
            return false; // すでに死亡している
        }
        if (isGuarded(dayChangeVillage, targetPlayer)) {
            return false; // 護衛されている
        }
        if (targetPlayer.getSkillCodeAsSkill().isNoDeadByAttack()) {
            return false; // 襲撃されても死なない役職
        }
        if (isAbsence(dayChangeVillage, targetPlayer)) {
            return false; // 同棲先に移動していて不在
        }
        return true;
    }

    public boolean isCohabitting(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.同棲) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByTargetChara(targetPlayer.getCharaId()).list.isEmpty();
    }

    public boolean hasWerewolfFace(VillagePlayer attackerPlayer) {
        OptionalEntity<CharaImage> optAttackFace = charaImageBhv.selectEntity(cb -> {
            cb.query().setCharaId_Equal(attackerPlayer.getCharaId());
            cb.query().setFaceTypeCode_Equal_囁き();
        });
        return optAttackFace.isPresent();
    }

    private boolean isGuarded(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.護衛) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                        .list.stream().anyMatch(a -> a.getTargetCharaId().equals(targetPlayer.getCharaId()));
    }

    private boolean isAbsence(DayChangeVillage dayChangeVillage, VillagePlayer targetPlayer) {
        return !dayChangeVillage.abilities //
                .filterYesterday(dayChangeVillage.day) //
                .filterByType(CDef.AbilityType.同棲) //
                .filterMylsefNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterTargetNotIn(dayChangeVillage.deadPlayers.getList()) //
                .filterByChara(targetPlayer.getCharaId()).list.isEmpty();
    }
}
