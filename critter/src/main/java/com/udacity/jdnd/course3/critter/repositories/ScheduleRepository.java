package com.udacity.jdnd.course3.critter.repositories;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule,Long> {

    @Query("SELECT s FROM Schedule s INNER JOIN s.pets p WHERE p.id = :petId")
    List<Schedule> findScheduleForPet(Long petId);

    @Query("SELECT s FROM Schedule s INNER JOIN s.employees e WHERE e.id = :employeeId")
    List<Schedule> findScheduleByEmployee(Long employeeId);

    @Query("SELECT s FROM Schedule s INNER JOIN s.pets p where p.id IN (:petIds)")
    List<Schedule> findAllScheduleByPets(Set<Long> petIds);
}
