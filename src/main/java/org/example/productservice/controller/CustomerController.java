package org.example.productservice.controller;

import org.example.modelproject.dto.CustomerDTO;
import org.example.productservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("${api.version}/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // get all customers
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    // create customer
    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customer){
        return ResponseEntity.ok().body(customerService.createCustomer(customer));
    }

    // get customer by id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable(value = "id") long customerId) throws ResolutionException{
        return ResponseEntity.ok().body(customerService.getCustomerById(customerId));
    }

    // update customer
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable(value = "id") long customerId,
                                                   @RequestBody CustomerDTO customerDetails) throws ResolutionException{
        return ResponseEntity.ok().body(customerService.updateCustomer(customerId, customerDetails));
    }

    // delete customer by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable(value = "id") long customerId) throws ResolutionException{
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

}
