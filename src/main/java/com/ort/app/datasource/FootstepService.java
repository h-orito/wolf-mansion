package com.ort.app.datasource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ort.dbflute.exbhv.FootstepBhv;
import com.ort.dbflute.exentity.Footstep;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.VillagePlayers;

@Repository
public class FootstepService {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private FootstepBhv footstepBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Footsteps selectFootsteps(Integer villageId) {
        return new Footsteps(footstepBhv.selectList(cb -> {
            cb.setupSelect_Chara();
            cb.query().setVillageId_Equal(villageId);
        }));
    }

    public void deleteWolfFootstep(//
            Integer villageId, //
            int day, //
            Integer charaId, //
            String footstep, //
            VillagePlayers wolfPlayers //
    ) {
        List<Integer> wolfCharaIdList = wolfPlayers.list.stream().map(vp -> vp.getCharaId()).collect(Collectors.toList());
        footstepBhv.queryDelete(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setCharaId_InScope(wolfCharaIdList);
        });
    }

    public void deleteFootstep(Integer villageId, int day, Integer charaId) {
        footstepBhv.queryDelete(cb -> {
            cb.query().setVillageId_Equal(villageId);
            cb.query().setDay_Equal(day);
            cb.query().setCharaId_Equal(charaId);
        });
    }

    public void insertFootstep(//
            Integer villageId, //
            int day, //
            Integer charaId, //
            String footstep//
    ) {
        Footstep entity = new Footstep();
        entity.setVillageId(villageId);
        entity.setDay(day);
        entity.setCharaId(charaId);
        entity.setFootstepRoomNumbers(footstep);
        footstepBhv.insert(entity);
    }
}
