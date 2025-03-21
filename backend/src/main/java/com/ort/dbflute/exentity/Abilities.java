package com.ort.dbflute.exentity;

import com.ort.dbflute.allcommon.CDef;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Abilities {

    public List<Ability> list;

    public Abilities(List<Ability> list) {
        this.list = list;
    }

    public Abilities filterBy(Predicate<Ability> predicate) {
        return new Abilities(this.list.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Abilities filterByType(CDef.AbilityType type) {
        return this.filterBy(a -> a.getAbilityTypeCodeAsAbilityType() == type);
    }

    public Abilities filterByDay(int day) {
        return this.filterBy(a -> a.getDay().equals(day));
    }

    public Abilities filterPastDay(int day) {
        return this.filterBy(a -> a.getDay().intValue() < day);
    }

    public Abilities sortedByDay() {
        return new Abilities(this.list.stream().sorted(Comparator.comparingInt(Ability::getDay)).collect(Collectors.toList()));
    }

    public <R> List<R> map(Function<Ability, ? extends R> mapper) {
        return this.list.stream().map(mapper).collect(Collectors.toList());
    }
}
