package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.services.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet newPet = fromDTO(petDTO);
        Pet savedPet = this.petService.save(newPet);
        this.customerService.setOwner(savedPet,petDTO.getOwnerId());
        return toDTO(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = this.petService.getById(petId);
        return toDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.petService.getAll()
                .stream()
                .map(pet -> toDTO(pet))
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.petService.getByOwner(ownerId)
                .stream()
                .map(pet -> toDTO(pet))
                .collect(Collectors.toList());
    }

    private Pet fromDTO(PetDTO petDTO) {
        Pet pet = modelMapper.map(petDTO, Pet.class);
        Customer customer = new Customer();
        customer.setId(petDTO.getOwnerId());
        pet.setCustomer(customer);
        return pet;
    }

    private PetDTO toDTO(Pet pet) {
        PetDTO petDTO = modelMapper.map(pet, PetDTO.class);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}
