package com.ngdb.entities;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.ngdb.entities.article.Game;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.OrderBy;
import java.util.*;

@Embeddable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Staff implements Iterable<Participation> {

    @OrderBy("role")
    @ElementCollection
    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<Participation>();

    public void add(Participation participation) {
        participations.add(participation);
    }

    @Override
    public Iterator<Participation> iterator() {
        return participations.iterator();
    }

    public Collection<Employee> employees() {
        return Collections2.transform(participations, new Function<Participation, Employee>() {
            @Nullable
            @Override
            public Employee apply(@Nullable Participation participation) {
                return participation.getEmployee();
            }
        });
    }

    public boolean isEmpty() {
        return participations.isEmpty();
    }

    public void clear() {
        participations.clear();
    }
}
