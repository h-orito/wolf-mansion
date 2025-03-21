package com.ort.dbflute.exentity;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VillageDays {

    public List<VillageDay> list;

    public VillageDays(List<VillageDay> list) {
        this.list = list.stream().sorted(Comparator.comparingInt(VillageDay::getDay)).collect(Collectors.toList());
    }

    public VillageDay latestDay() {
        if (this.list.isEmpty()) {
            throw new IllegalStateException("list is empty");
        }
        return this.list.stream().sorted(Comparator.comparingInt(VillageDay::getDay).reversed()).findFirst().get();
    }

    public <R> List<R> map(Function<VillageDay, ? extends R> mapper) {
        return this.list.stream().map(mapper).collect(Collectors.toList());
    }
}
