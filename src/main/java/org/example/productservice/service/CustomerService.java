package org.example.productservice.service;

import org.example.modelproject.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO createCustomer(CustomerDTO customer);
    CustomerDTO getCustomerById(long customerId);
    CustomerDTO updateCustomer(long customerId, CustomerDTO customerDetails);
    void deleteCustomer(long customerId);
}
