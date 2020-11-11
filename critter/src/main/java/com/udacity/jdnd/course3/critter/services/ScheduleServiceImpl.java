package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule save(Schedule schedule) {
         return this.scheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> getAll() {
        return (List<Schedule>) this.scheduleRepository.findAll();
    }

    @Override
    public  List<Schedule> findScheduleForPet(Long petId) {
        return this.scheduleRepository.findScheduleForPet(petId);
    }

    @Override
    public  List<Schedule> findScheduleForEmployee(Long employeeId) {
        return this.scheduleRepository.findScheduleByEmployee(employeeId);
    }

    @Override
    public List<Schedule> findScheduledForPetOwner(Set<Long> petIds) {
        return this.scheduleRepository.findAllScheduleByPets(petIds);
    }
}
