package com.ngdb.entities;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.*;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

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

    public Collection<String> employees() {
        return transform(participations, new Function<Participation, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Participation participation) {
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

    public Collection<String> roles() {
        Set<String> roles = new TreeSet<String>();
        for (Participation participation : participations) {
            roles.add(participation.getRole());
        }
        return roles;
    }

    public Collection<String> employeesOfRole(final String role) {
        return transform(filter(participations, new Predicate<Participation>() {
            @Override
            public boolean apply(Participation participation) {
                return participation.hasRole(role);
            }
        }), new Function<Participation, String>() {
            @Nullable
            @Override
            public String apply(Participation input) {
                return input.getEmployee();
            }
        });
    }

}
