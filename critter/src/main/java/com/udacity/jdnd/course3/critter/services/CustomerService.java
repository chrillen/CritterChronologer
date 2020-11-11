package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;

import java.util.List;

/*
Interface defining or CustomerService
 */
public interface CustomerService {
    List<Customer> getAll();
    void setOwner(Pet pet, Long customerId);
    Customer getById(Long customerId);
    Customer getOwnerByPet(Long petId);
}
