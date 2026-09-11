package com.ort.dbflute.exentity;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Votes {

    public List<Vote> list;

    public Votes(List<Vote> list) {
        this.list = list;
    }

    public Votes filterBy(Predicate<Vote> predicate) {
        return new Votes(this.list.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Votes filterMylsefNotIn(Collection<VillagePlayer> vPlayerList) {
        return new Votes(this.list.stream().filter(v -> {
            return vPlayerList.stream().noneMatch(vp -> vp.getCharaId().equals(v.getCharaId()));
        }).collect(Collectors.toList()));
    }

    public Votes filterByChara(int charaId) {
        return this.filterBy(v -> v.getCharaId().equals(charaId));
    }

    public Votes filterByTargetChara(int targetCharaId) {
        return this.filterBy(v -> v.getVoteCharaId().equals(targetCharaId));
    }

    public Votes filterByDay(int day) {
        return this.filterBy(v -> v.getDay().equals(day));
    }

    public Votes filterYesterday(int day) {
        return this.filterByDay(day - 1);
    }

    public Votes filterPast(int day) {
        return this.filterBy(v -> v.getDay().intValue() < day);
    }

    public Votes sortedByDay() {
        return new Votes(this.list.stream().sorted(Comparator.comparingInt(Vote::getDay)).collect(Collectors.toList()));
    }

    public Votes sortedByDayDesc() {
        return new Votes(this.list.stream().sorted(Comparator.comparingInt(Vote::getDay).reversed()).collect(Collectors.toList()));
    }

    public <R> List<R> map(Function<Vote, ? extends R> mapper) {
        return this.list.stream().map(mapper).collect(Collectors.toList());
    }

    public Optional<Vote> findTodayVote(int day, Integer charaId) {
        return this.filterByDay(day).filterByChara(charaId).list.stream().findFirst();
    }
}
