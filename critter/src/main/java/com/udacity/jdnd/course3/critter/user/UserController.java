package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.services.CustomerServiceImpl;
import com.udacity.jdnd.course3.critter.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO)  {
         Customer customer = this.customerService.save(modelMapper.map(customerDTO, Customer.class));
         return modelMapper.map(customer, CustomerDTO.class);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return this.customerService.getAll()
                .stream()
                .map(customer -> toDTO(customer))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
         Customer customer = this.customerService.getOwnerByPet(petId);
         return toDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = this.employeeService.save(fromDTO(employeeDTO));
        return toDTO(employee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return toDTO(this.employeeService.getById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.employeeService.setAvailability(employeeId,daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
         return this.employeeService.getAvailability(employeeDTO.getDate(),employeeDTO.getSkills()).stream()
                     .map(e -> toDTO(e))
                     .collect(Collectors.toList());
    }

    //region converter tofrom DTO
    private Employee fromDTO(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        return employee;
    }

    private EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return employeeDTO;
    }

    private Customer fromDTO(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);

        customer.setPets(customerDTO.getPetIds().stream()
                .map(p -> {
                    Pet pet = new Pet();
                    pet.setId(p);
                    return pet;
                }).collect(Collectors.toList()));

        return customer;
    }

    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);

        if(customer.getPets() != null)
            customerDTO.setPetIds(customer.getPets().stream().map(s -> s.getId()).collect(Collectors.toList()));

        return customerDTO;
    }
    //endregion
}
