package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Schedule;

import java.util.List;
import java.util.Set;

public interface ScheduleService {
    Schedule save(Schedule schedule);
    List<Schedule> getAll();
    List<Schedule> findScheduleForPet(Long petId);
    List<Schedule> findScheduleForEmployee(Long employeeId);
    List<Schedule> findScheduledForPetOwner(Set<Long> petIds);
}
