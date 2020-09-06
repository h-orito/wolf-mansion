package com.ort.dbflute.exentity;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ort.dbflute.allcommon.CDef;

public class Abilities {

    public List<Ability> list;

    public Abilities(List<Ability> list) {
        this.list = list;
    }

    public Abilities filterBy(Predicate<Ability> predicate) {
        return new Abilities(this.list.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Abilities filterMylsefNotIn(Collection<VillagePlayer> vPlayerList) {
        return new Abilities(this.list.stream().filter(a -> {
            return vPlayerList.stream().noneMatch(vp -> vp.getCharaId().equals(a.getCharaId()));
        }).collect(Collectors.toList()));
    }

    public Abilities filterTargetNotIn(Collection<VillagePlayer> vPlayerList) {
        return new Abilities(this.list.stream().filter(a -> {
            return vPlayerList.stream().noneMatch(vp -> vp.getCharaId().equals(a.getTargetCharaId()));
        }).collect(Collectors.toList()));
    }

    public Abilities filterByChara(int charaId) {
        return this.filterBy(a -> a.getCharaId().equals(charaId));
    }

    public Abilities filterByTargetChara(int targetCharaId) {
        return this.filterBy(a -> a.getTargetCharaId().equals(targetCharaId));
    }

    public Abilities filterByType(CDef.AbilityType type) {
        return this.filterBy(a -> a.getAbilityTypeCodeAsAbilityType() == type);
    }

    public Abilities filterByFootstep(String footstep) {
        return this.filterBy(a -> a.getTargetFootstep().equals(footstep));
    }

    public Abilities filterByDay(int day) {
        return this.filterBy(a -> a.getDay().equals(day));
    }

    public Abilities filterYesterday(int day) {
        return this.filterByDay(day - 1);
    }

    public Abilities filterToday(int day) {
        return this.filterByDay(day);
    }

    public Abilities filterPastDay(int day) {
        return this.filterBy(a -> a.getDay().intValue() < day);
    }

    public Abilities sortedByDay() {
        return new Abilities(this.list.stream().sorted(Comparator.comparingInt(Ability::getDay)).collect(Collectors.toList()));
    }

    public Abilities sortedByDayDesc() {
        return new Abilities(this.list.stream().sorted(Comparator.comparingInt(Ability::getDay).reversed()).collect(Collectors.toList()));
    }

    public <R> List<R> map(Function<Ability, ? extends R> mapper) {
        return this.list.stream().map(mapper).collect(Collectors.toList());
    }

    public Optional<Ability> findTodayAbility(int day, CDef.AbilityType type, Integer charaId) {
        if (type == null) {
            return Optional.empty();
        }
        return this.filterByDay(day) //
                .filterByType(type) //
                .filterBy(a -> {
                    if (type == CDef.AbilityType.襲撃) {
                        return true;
                    } else if (type == CDef.AbilityType.同棲) {
                        return a.getCharaId().equals(charaId) || a.getTargetCharaId().equals(charaId);
                    } else {
                        return a.getCharaId().equals(charaId);
                    }
                }).list.stream().findFirst();
    }
}
