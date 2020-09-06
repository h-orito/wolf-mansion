package com.ort.app.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.ort.app.web.dto.VillageInfo;
import com.ort.app.web.model.OptionDto;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillagePlayer;

@Component
public class VoteLogic {

    // 投票対象リスト作成
    public List<OptionDto> getSelectableVoteTargetList(VillageInfo villageInfo) {
        Village village = villageInfo.village;
        int day = villageInfo.day;
        if (!isAvailableVote(village, villageInfo.optVillagePlayer, day)) {
            return new ArrayList<>();
        }
        return villageInfo.vPlayers //
                .filterAlive() //
                .filterNotSpecatate() //
                .sortedByRoomNumber() //
                .map(vp -> new OptionDto(vp));
    }

    // 投票可能か
    public boolean isAvailableVote(Village village, Optional<VillagePlayer> optVillagePlayer, int day) {
        if (!village.isVillageStatusCode進行中()) {
            return false; // 進行中でない
        }
        if (!village.getVillageDays().latestDay().getDay().equals(day)) {
            return false; // 最新日でない
        }
        if (day < 2) {
            return false; // 投票が始まってない
        }
        if (!optVillagePlayer.isPresent()) {
            return false; // 参加していない
        }
        VillagePlayer villagePlayer = optVillagePlayer.get();
        if (villagePlayer.isIsDeadTrue()) {
            return false; // 死亡している
        }
        if (villagePlayer.isIsSpectatorTrue()) {
            return false; // 見学
        }
        return true;
    }
}
