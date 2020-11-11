package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Query("SELECT distinct e FROM Employee e inner join e.employeeSkills es inner join e.daysAvailable ed WHERE es IN :employeeSkills AND ed IN :dayOfWeek")
    List<Employee> findByDaysAndEmployeeSkills(DayOfWeek dayOfWeek,Set<EmployeeSkill> employeeSkills);
}
