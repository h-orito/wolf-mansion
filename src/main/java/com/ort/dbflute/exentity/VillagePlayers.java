package com.ort.dbflute.exentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ort.dbflute.allcommon.CDef;

public class VillagePlayers {

    public List<VillagePlayer> list;

    public VillagePlayers(List<VillagePlayer> list) {
        this.list = list;
    }

    public VillagePlayers filterBy(Predicate<VillagePlayer> predicate) {
        return new VillagePlayers(this.list.stream().filter(predicate).collect(Collectors.toList()));
    }

    public VillagePlayers filterAlive() {
        return this.filterBy(vp -> vp.isIsDeadFalse());
    }

    public VillagePlayers filterExecuted() {
        return this.filterBy(vp -> vp.isDeadReasonCode処刑());
    }

    public VillagePlayers filterMiserable() {
        return this.filterBy(vp -> vp.isIsDeadTrue() && vp.getDeadReasonCodeAsDeadReason().isMiserable());
    }

    public VillagePlayers filterSuddenly() {
        return this.filterBy(vp -> vp.isDeadReasonCode突然());
    }

    public VillagePlayers filterSuicide() {
        return this.filterBy(vp -> vp.isDeadReasonCode後追());
    }

    public VillagePlayers filterNotDummy(VillageSettings villageSettings) {
        return this.filterNotDummy(villageSettings.getDummyCharaId());
    }

    public VillagePlayers filterNotDummy(int dummyCharaId) {
        return this.filterBy(vp -> !vp.getCharaId().equals(dummyCharaId));
    }

    public VillagePlayers filterSpectate() {
        return this.filterBy(vp -> vp.isIsSpectatorTrue());
    }

    public VillagePlayers filterNotSpecatate() {
        return this.filterBy(vp -> vp.isIsSpectatorFalse());
    }

    public VillagePlayers filterNotGone() {
        return this.filterBy(vp -> vp.isIsGoneFalse());
    }

    public VillagePlayers filterBySkill(CDef.Skill skill) {
        return this.filterBy(vp -> vp.getSkillCodeAsSkill() == skill);
    }

    public VillagePlayers filterNot(VillagePlayer villagePlayer) {
        return this.filterBy(vp -> !vp.getVillagePlayerId().equals(villagePlayer.getVillagePlayerId()));
    }

    public VillagePlayers sortedByRoomNumber() {
        return this.sortedBy(Comparator.comparingInt(VillagePlayer::getRoomNumber));
    }

    public VillagePlayers sortedBy(Comparator<VillagePlayer> comparator) {
        return new VillagePlayers(this.list.stream().sorted(comparator).collect(Collectors.toList()));
    }

    public VillagePlayers shuffled() {
        if (this.list.isEmpty()) {
            return this;
        }
        List<VillagePlayer> newList = new ArrayList<>(this.list);
        Collections.shuffle(newList);
        return new VillagePlayers(newList);
    }

    public VillagePlayer findByCharaId(int charaId) {
        return this.filterBy(vp -> vp.getCharaId().equals(charaId)).list.stream().findFirst().orElseThrow(
                () -> new IllegalStateException("no found chara. charaId: " + charaId));
    }

    public Optional<VillagePlayer> findByRoomNumber(int roomNum, int day) {
        return this.filterBy(vp -> {
            Integer roomNumber = vp.getRoomNumberWhen(day);
            return roomNumber != null && roomNumber.equals(roomNum);
        }).list.stream().findFirst();
    }

    public VillagePlayer getRandom() {
        if (this.list.isEmpty())
            throw new IllegalStateException("village_player_list is empty.");
        List<VillagePlayer> newList = new ArrayList<>(this.list);
        Collections.shuffle(newList);
        return newList.get(0);
    }

    public <R> List<R> map(Function<VillagePlayer, ? extends R> mapper) {
        return this.list.stream().map(mapper).collect(Collectors.toList());
    }
}
