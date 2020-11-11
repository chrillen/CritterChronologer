package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends User {

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_employeeskill", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "employeeskill_name")
    private Set<EmployeeSkill> employeeSkills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "employee_daysAvailable", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "dayofweek_name")
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Schedule> schedules;

    public Set<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
            this.daysAvailable = daysAvailable;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
            this.schedules = new HashSet<>(schedules);
    }
}
