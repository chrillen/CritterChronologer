package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository=petRepository;
    }

    @Override
    public Pet save(Pet pet) {
        return this.petRepository.save(pet);
    }

    @Override
    public List<Pet> getAll() {
        return (List<Pet>)this.petRepository.findAll();
    }

    @Override
    public Pet getById(Long id) {
        return this.petRepository.findById(id).
                orElseThrow(PetNotFoundException::new);
    }

    @Override
    public List<Pet> getByOwner(Long ownerId) {
        List<Pet> pets = this.petRepository.findPetsByOwner(ownerId);

        if(pets.size() == 0)
            throw new PetNotFoundException();

        return pets;
    }

    @Override
    public void setSchedule(Schedule schedule) {
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(schedule);
        Set<Pet> pets = new HashSet<>();
        schedule.getPets().forEach(p -> {
            Pet pet = this.petRepository.findById(p.getId()).orElseThrow(PetNotFoundException::new);

            if(pet.getSchedules() == null)
                pet.setSchedules(new HashSet<>());

            pet.getSchedules().add(schedule);
            pets.add(pet);
        });
        this.petRepository.saveAll(pets);
    }
}
