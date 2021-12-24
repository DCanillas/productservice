package org.example.productservice.controller;

import org.example.productservice.repository.CustomerRepository;
import org.example.modelproject.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@CrossOrigin(origins = "http://localhost::3306")
@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    // create get all customers api
    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @PostMapping("/customer")
    // create customer
    public Customer createCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    // get customer by id
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") long customerId) throws ResolutionException{
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        return ResponseEntity.ok().body(customer);
    }

    // update customer
    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") long customerId,
                                                   @RequestBody Customer customerDetails) throws ResolutionException{
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customerRepository.save(customer);
        return ResponseEntity.ok().body(customer);
    }

    // delete customer by id
    @DeleteMapping("/customer/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") long customerId) throws ResolutionException{
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        customerRepository.deleteById(customerId);
        return ResponseEntity.ok().build();
    }

}
