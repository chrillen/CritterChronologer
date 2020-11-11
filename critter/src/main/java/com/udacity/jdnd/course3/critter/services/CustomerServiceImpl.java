package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAll() {
        return (List<Customer>)this.customerRepository.findAll();
    }

    public void setOwner(Pet pet, Long customerId) {
        List<Pet> pets = new ArrayList<Pet>();
        pets.add(pet);
        Customer customer = this.customerRepository.findById(customerId).
                orElseThrow(CustomerNotFoundException::new);

        customer.setPets(pets);
        this.customerRepository.save(customer);
    }

    public Customer getById(Long customerId) {
        return this.customerRepository.findById(customerId).
                orElseThrow(CustomerNotFoundException::new);
    }

    public Customer getOwnerByPet(Long petId) {
        return this.customerRepository.findOwnerByPet(petId)
                .orElseThrow(CustomerNotFoundException::new);
    }
}
