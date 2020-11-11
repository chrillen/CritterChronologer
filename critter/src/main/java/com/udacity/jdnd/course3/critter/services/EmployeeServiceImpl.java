package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return (List<Employee>)this.employeeRepository.findAll();
    }

    @Override
    public Employee getById(Long id) {
        return this.employeeRepository.findById(id)
                .orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public void setSchedule(Schedule schedule) {
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(schedule);
        Set<Employee> employees = new HashSet<>();
        schedule.getEmployees().forEach(e -> {
            Employee emp = this.employeeRepository.findById(e.getId()).orElseThrow(EmployeeNotFoundException::new);

            if(emp.getSchedules() == null)
                emp.setSchedules(new HashSet<>());

            emp.getSchedules().add(schedule);
            employees.add(emp);
        });
        this.employeeRepository.saveAll(employees);
    }

    @Override
    public void setAvailability(Long id, Set<DayOfWeek> daysAvailable) {
        Employee employee = this.employeeRepository.findById(id).
                orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAvailability(LocalDate date, Set<EmployeeSkill> skillSet) {
        return this.employeeRepository.findByDaysAndEmployeeSkills(date.getDayOfWeek(),skillSet);
    }
}