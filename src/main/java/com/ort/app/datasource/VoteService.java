package com.ort.app.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ort.dbflute.exbhv.VoteBhv;
import com.ort.dbflute.exentity.Votes;

@Repository
public class VoteService {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Autowired
    private VoteBhv voteBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Votes selectVotes(Integer villageId) {
        return new Votes(voteBhv.selectList(cb -> {
            cb.setupSelect_CharaByCharaId();
            cb.setupSelect_CharaByVoteCharaId();
            cb.query().setVillageId_Equal(villageId);
        }));
    }
}
