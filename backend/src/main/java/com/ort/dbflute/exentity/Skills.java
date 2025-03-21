package com.ort.dbflute.exentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ort.dbflute.allcommon.CDef;

public class Skills {

    public List<CDef.Skill> list;

    public Skills(List<CDef.Skill> list) {
        this.list = list;
    }

    public static Skills of() {
        return new Skills(CDef.Skill.listAll());
    }

    public Skills filterBy(Predicate<CDef.Skill> predicate) {
        return new Skills(this.list.stream().filter(predicate).collect(Collectors.toList()));
    }

    public Skills filterByCamp(CDef.Camp camp) {
        return this.filterBy(s -> s.campCode().equals(camp.code()));
    }

    public Skills filterNotSomeone() {
        return this.filterBy(s -> !s.isSomeoneSkill());
    }

    public Skills sortedBy(Comparator<CDef.Skill> comparator) {
        return new Skills(this.list.stream().sorted(comparator).collect(Collectors.toList()));
    }

    public Skills sortedByDispOrder() {
        return this.sortedBy((s1, s2) -> Integer.parseInt(s1.order()) - Integer.parseInt(s2.order()));
    }

    public CDef.Skill getRandom() {
        if (this.list.isEmpty())
            throw new IllegalStateException("skill_list is empty.");
        List<CDef.Skill> newList = new ArrayList<>(this.list);
        Collections.shuffle(newList);
        return newList.get(0);
    }
}
