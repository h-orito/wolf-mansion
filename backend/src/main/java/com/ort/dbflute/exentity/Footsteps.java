package com.ort.dbflute.exentity;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Footsteps {

    public List<Footstep> list;

    public Footsteps(List<Footstep> list) {
        this.list = list;
    }

    public Footsteps filterBy(Predicate<Footstep> predicate) {
        return new Footsteps(this.list.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Footsteps filterByChara(int charaId) {
        return this.filterBy(f -> f.getCharaId().equals(charaId));
    }

    public Footsteps filterInCharaIdList(List<Integer> charaIdList) {
        return this.filterBy(f -> charaIdList.contains(f.getCharaId()));
    }

    public Footsteps filterByDay(int day) {
        return this.filterBy(f -> f.getDay().equals(day));
    }

    public Footsteps filterToday(int day) {
        return this.filterByDay(day);
    }

    public Footsteps filterYesteday(int day) {
        return this.filterByDay(day - 1);
    }

    public Footsteps filterPastDay(int day) {
        return this.filterBy(f -> f.getDay().intValue() < day);
    }

    public Footsteps sortedByDay() {
        return new Footsteps(this.list.stream().sorted(Comparator.comparingInt(Footstep::getDay)).collect(Collectors.toList()));
    }

    public Footsteps sortedByDayDesc() {
        return new Footsteps(this.list.stream().sorted(Comparator.comparingInt(Footstep::getDay).reversed()).collect(Collectors.toList()));
    }
}
