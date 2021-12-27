package org.example.productservice.controller;

import org.example.modelproject.Customer;
import org.example.productservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("${api.version}/customer")
public class CustomerController {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // get all customers
    @GetMapping("")
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    // create customer
    @PostMapping("")
    public Customer createCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }

    // get customer by id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") long customerId) throws ResolutionException{
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        return ResponseEntity.ok().body(customer);
    }

    // update customer
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") long customerId) throws ResolutionException{
        customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        customerRepository.deleteById(customerId);
        return ResponseEntity.ok().build();
    }

}
