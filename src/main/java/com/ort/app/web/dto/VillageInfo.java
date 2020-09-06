package com.ort.app.web.dto;

import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.Footsteps;
import com.ort.dbflute.exentity.Village;
import com.ort.dbflute.exentity.VillageDays;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Votes;
import com.ort.fw.security.UserInfo;

public class VillageInfo {

    public Integer villageId;
    public Village village;
    public VillagePlayers vPlayers;
    public VillageSettings settings;
    public UserInfo user;
    public Optional<VillagePlayer> optVillagePlayer;
    public int day;
    public Votes votes;
    public Footsteps footsteps;
    public Abilities abilities;
    public VillageDays days;

    public VillageInfo() {
    }

    public VillageInfo( //
            Village village, //
            UserInfo userInfo, //
            Optional<VillagePlayer> optVillagePlayer, //
            int day, //
            Votes votes, //
            Footsteps footsteps, //
            Abilities abilities //
    ) {
        this.villageId = village.getVillageId();
        this.village = village;
        this.vPlayers = village.getVillagePlayers();
        this.settings = village.getVillageSettingsAsOne().get();
        this.user = userInfo;
        this.optVillagePlayer = optVillagePlayer;
        this.day = day;
        this.votes = votes;
        this.footsteps = footsteps;
        this.abilities = abilities;
        this.days = village.getVillageDays();
    }

    // 投票が始まっているか
    public boolean isStartedVote() {
        return days.latestDay().getDay() >= 2;
    }

    // 足音セットが始まっているか
    public boolean isStartedFootstepSet() {
        return days.latestDay().getDay() >= 1;
    }

    // この村に参加しているか
    public boolean isParticipate() {
        return optVillagePlayer.isPresent();
    }

    // 最新日か
    public boolean isLatestDay() {
        return this.days.latestDay().getDay().equals(this.day);
    }

    // 管理者か
    public boolean isAdmin() {
        return user != null && user.getAuthorities().stream().anyMatch(a -> a.equals(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    // 村建てか
    public boolean isCreator() {
        String createPlayerName = village.getCreatePlayerName();
        return user != null && user.getUsername().equals(createPlayerName);
    }

    // 死亡しているか
    public boolean isDead() {
        return optVillagePlayer.isPresent() && optVillagePlayer.get().isIsDeadTrue();
    }

    // 観戦者か
    public boolean isSpectator() {
        return optVillagePlayer.isPresent() && optVillagePlayer.get().isIsSpectatorTrue();
    }
}
