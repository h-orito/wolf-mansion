package com.ort.app.logic.daychange;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ort.dbflute.allcommon.CDef;
import com.ort.dbflute.exentity.Abilities;
import com.ort.dbflute.exentity.VillagePlayer;
import com.ort.dbflute.exentity.VillagePlayers;
import com.ort.dbflute.exentity.VillageSettings;
import com.ort.dbflute.exentity.Votes;

public class DayChangeVillage {

    public Integer villageId;
    public int day;
    public VillagePlayers vPlayers;
    public Abilities abilities;
    public Votes votes;
    public VillageSettings settings;
    public DeadPlayers deadPlayers;
    public GuardedPlayers guardedPlayers;

    public DayChangeVillage(//
            Integer villageId, //
            int day, //
            VillagePlayers vPlayers, //
            Abilities abilities, //
            Votes votes, //
            VillageSettings settings//
    ) {
        this.villageId = villageId;
        this.day = day;
        this.vPlayers = vPlayers.filterNotSpecatate(); // 見学は登場しないのでここで弾く
        this.abilities = abilities;
        this.votes = votes;
        this.settings = settings;
        this.deadPlayers = new DeadPlayers();
        this.guardedPlayers = new GuardedPlayers();
    }

    public boolean isAlive(VillagePlayer villagePlayer) {
        return villagePlayer.isIsDeadFalse() && this.deadPlayers.isAlive(villagePlayer);
    }

    public VillagePlayers alivePlayers() {
        return new VillagePlayers(this.vPlayers.list.stream().filter(vp -> isAlive(vp)).collect(Collectors.toList()));
    }

    // ===================================================================================
    //                                                                         inner class
    //                                                                        ============
    public static class DeadPlayers {

        public Set<DeadPlayer> list = new HashSet<>();

        public void add(VillagePlayer villagePlayer, CDef.DeadReason reason) {
            this.list.add(new DeadPlayer(villagePlayer, reason));
        }

        public void addAll(List<VillagePlayer> list, CDef.DeadReason reason) {
            if (list.isEmpty()) {
                return;
            }
            list.forEach(vp -> this.add(vp, reason));
        }

        public void remove(VillagePlayer vp) {
            this.list.removeIf(dp -> dp.villagePlayer.getVillagePlayerId().equals(vp.getVillagePlayerId()));
        }

        public DeadPlayers() {
        }

        public DeadPlayers(List<DeadPlayer> list) {
            this.list = new HashSet<>(list);
        }

        public DeadPlayers filterSuddenly() {
            return new DeadPlayers(this.list.stream() //
                    .filter(DeadPlayer::isSuddenly) //
                    .map(dp -> new DeadPlayer(dp.villagePlayer, dp.reason)) //
                    .collect(Collectors.toList()));
        }

        public DeadPlayers filterAttacked() {
            return new DeadPlayers(this.list.stream() //
                    .filter(DeadPlayer::isAttacked) //
                    .map(dp -> new DeadPlayer(dp.villagePlayer, dp.reason)) //
                    .collect(Collectors.toList()));
        }

        public DeadPlayers filterMiserable() {
            return new DeadPlayers(this.list.stream() //
                    .filter(DeadPlayer::isMiserable) //
                    .map(dp -> new DeadPlayer(dp.villagePlayer, dp.reason)) //
                    .collect(Collectors.toList()));
        }

        public DeadPlayers filterPsychicable() {
            return new DeadPlayers(this.list.stream() //
                    .filter(DeadPlayer::isPsychicable) //
                    .map(dp -> new DeadPlayer(dp.villagePlayer, dp.reason)) //
                    .collect(Collectors.toList()));
        }

        public List<VillagePlayer> getList() {
            return this.list.stream().map(dp -> dp.villagePlayer).collect(Collectors.toList());
        }

        public boolean isAlive(VillagePlayer villagePlayer) {
            return this.list.stream().noneMatch(dp -> dp.villagePlayer.getVillagePlayerId().equals(villagePlayer.getVillagePlayerId()));
        }

        public static class DeadPlayer {

            public VillagePlayer villagePlayer;
            public CDef.DeadReason reason;

            public DeadPlayer(VillagePlayer villagePlayer, CDef.DeadReason reason) {
                this.villagePlayer = villagePlayer;
                this.reason = reason;
            }

            public boolean isSuddenly() {
                return reason == CDef.DeadReason.突然;
            }

            public boolean isAttacked() {
                return reason == CDef.DeadReason.襲撃;
            }

            public boolean isMiserable() {
                return reason.isMiserable();
            }

            public boolean isPsychicable() {
                return Arrays.asList(CDef.DeadReason.処刑, CDef.DeadReason.突然).contains(this.reason);
            }
        }
    }

    public static class GuardedPlayers {

        public Set<VillagePlayer> list = new HashSet<>();

        public void add(VillagePlayer villagePlayer) {
            this.list.add(villagePlayer);
        }
    }
}
