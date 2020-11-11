package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> getAll();
    Employee getById(Long id);
    void setSchedule(Schedule schedule);
    void setAvailability(Long id, Set<DayOfWeek> daysAvailable);
    List<Employee> getAvailability(LocalDate date, Set<EmployeeSkill> skillSet);
}
