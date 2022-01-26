package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CustomerDTO;
import org.example.modelproject.model.Customer;
import org.example.productservice.exception.ResourceNotFoundException;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    // get all customers
    @Override
    public List<CustomerDTO> getAllCustomers(){
        log.info("CustomerServiceImpl - Method getAllCustomers");
        List<CustomerDTO> customersDTO = customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
        log.info("CustomerServiceImpl - Return getAllCustomers: "+customersDTO);
        return customersDTO;
    }

    // create customer
    @Override
    public CustomerDTO createCustomer(CustomerDTO customer){
        log.info("CustomerServiceImpl - Method createCustomer");
        Customer customerCreated = customerRepository.save(modelMapper.map(customer, Customer.class));
        log.info("CustomerServiceImpl - Created createCustomer: "+customerCreated);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    // get customer by id
    @Override
    public CustomerDTO getCustomerById(long customerId) {
        log.info("CustomerServiceImpl - Method getCustomerById");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        log.info("CustomerServiceImpl - Found getCustomerById: "+customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    // update customer
    @Override
    public CustomerDTO updateCustomer(long customerId, CustomerDTO customerDetails) {
        log.info("CustomerServiceImpl - Method updateCustomer");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        log.info("CustomerServiceImpl - Found updateCustomer: "+customer);
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);

    }

    // delete customer by id
    @Override
    public void deleteCustomer(long customerId) {
        log.info("CustomerServiceImpl - Method deleteCustomer");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        log.info("CustomerServiceImpl - Found deleteCustomer: "+customer);
        customerRepository.deleteById(customerId);
    }

}
