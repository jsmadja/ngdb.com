package com.ngdb.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @OrderBy("role")
    @IndexedEmbedded
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Participation> participations = new HashSet<Participation>();

    private String employeeName;

    Employee() {

    }

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public boolean hasName(String name) {
        return employeeName.equalsIgnoreCase(name);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void addParticipation(Participation participation) {
        participations.add(participation);
    }
}
