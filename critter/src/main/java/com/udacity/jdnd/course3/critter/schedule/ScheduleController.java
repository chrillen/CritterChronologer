package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import com.udacity.jdnd.course3.critter.services.PetService;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.EntityManagerBeanDefinitionRegistrarPostProcessor;
import org.springframework.web.bind.annotation.*;
import com.udacity.jdnd.course3.critter.entities.Schedule;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = this.scheduleService.save(fromDTO(scheduleDTO));
        employeeService.setSchedule(schedule);
        petService.setSchedule(schedule);
        return toDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return this.scheduleService.getAll().stream()
                .map(c -> toDTO(c))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return this.scheduleService.findScheduleForPet(petId).stream()
                .map(c -> toDTO(c))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return this.scheduleService.findScheduleForEmployee(employeeId).stream()
                .map(c -> toDTO(c))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Set<Long> petIds = this.petService.getByOwner(customerId).stream().map(s -> s.getId()).collect(Collectors.toSet());
        return this.scheduleService.findScheduledForPetOwner(petIds).stream().map(s -> toDTO(s)).collect(Collectors.toList());
    }

    //region DTO conversions to/from Entity
    private Schedule fromDTO(ScheduleDTO scheduleDTO) {
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);

        if(scheduleDTO.getPetIds() != null) {
            schedule.setPets(scheduleDTO.getPetIds().stream()
                    .map(s -> {
                        Pet pet = new Pet();
                        pet.setId(s);
                        return pet;
                    }).collect(Collectors.toSet()));
        }


        if(scheduleDTO.getEmployeeIds() != null) {
            schedule.setEmployees(scheduleDTO.getEmployeeIds().stream()
                    .map(s -> {
                        Employee e = new Employee();
                        e.setId(s);
                        return e;
                    }).collect(Collectors.toSet()));
        }

        return schedule;
    }

    private ScheduleDTO toDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = modelMapper.map(schedule, ScheduleDTO.class);

        if(schedule.getEmployees() != null)
            scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(s -> s.getId()).collect(Collectors.toList()));

        if(schedule.getPets() != null)
            scheduleDTO.setPetIds(schedule.getPets().stream().map(s -> s.getId()).collect(Collectors.toList()));

        return scheduleDTO;
    }
    //endregion
}
