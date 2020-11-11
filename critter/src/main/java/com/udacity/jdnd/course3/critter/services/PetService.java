package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;

import java.util.List;

public interface PetService {
    Pet save(Pet pet);
    List<Pet> getAll();
    Pet getById(Long id);
    List<Pet> getByOwner(Long ownerId);
    void setSchedule(Schedule schedule);
}
