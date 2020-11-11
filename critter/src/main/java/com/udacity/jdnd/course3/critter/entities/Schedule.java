package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    public Schedule() {
    }

    public Schedule(Long id, LocalDate date) {
        this.id = id;
        this.date = date;
    }

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "schedules")
    private Set<Pet> pets;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST,mappedBy = "schedules")
    private Set<Employee> employees;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "schedule_activity", joinColumns = @JoinColumn(name = "schedule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_name")
    private Set<EmployeeSkill> activities;

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if(employees != null)
            this.employees = new HashSet<>(employees);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        if(pets != null)
            this.pets = new HashSet<>(pets);
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
