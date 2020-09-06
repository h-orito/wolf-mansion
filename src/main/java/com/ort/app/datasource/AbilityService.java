package com.ort.app.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exbhv.AbilityBhv;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Ability;

@Repository
public class AbilityService {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private AbilityBhv abilityBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Abilities selectAbilities(Integer villageId) {
        return new Abilities(abilityBhv.selectList(cb -> {
            cb.setupSelect_CharaByCharaId();
            cb.setupSelect_CharaByTargetCharaId();
            cb.query().setVillageId_Equal(villageId);
        }));
    }

    public void insertAbility(//
            Integer villageId, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            String footstep, //
            CDef.AbilityType abilityType//
    ) {
        Ability ability = new Ability();
        ability.setVillageId(villageId);
        ability.setDay(day);
        ability.setCharaId(charaId);
        ability.setTargetCharaId(targetCharaId);
        ability.setTargetFootstep(footstep);
        ability.setAbilityTypeCodeAsAbilityType(abilityType);
        abilityBhv.insert(ability);
    }

    public void updateAbility(//
            Integer villageId, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            String footstep, //
            CDef.AbilityType type //
    ) {
        // delete insertする
        deleteAbility(villageId, day, charaId, type);
        insertAbility(villageId, day, charaId, targetCharaId, footstep, type);
    }

    public void updateAbility(//
            Integer villageId, //
            int day, //
            Integer charaId, //
            Integer targetCharaId, //
            CDef.AbilityType type //
    ) {
        this.updateAbility(villageId, day, charaId, targetCharaId, null, type);
    }

    public void updateAbility(//
            Integer villageId, //
            int day, //
            Integer charaId, //
            String footstep, //
            CDef.AbilityType type //
    ) {
        this.updateAbility(villageId, day, charaId, null, footstep, type);
    }

    public void deleteAbility(//
            Integer villageId, //
            int day, //
            Integer charaId, //
            CDef.AbilityType type//
    ) {
        abilityBhv.queryDelete(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            if (type != CDef.AbilityType.襲撃) {
                cb.query().setCharaId_Equal(charaId);
            }
            cb.query().setAbilityTypeCode_Equal_AsAbilityType(type);
        });
    }
}
