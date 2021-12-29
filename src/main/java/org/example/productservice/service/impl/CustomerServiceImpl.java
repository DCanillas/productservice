package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.Customer;
import org.example.productservice.dto.CustomerDTO;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // get all customers
    @Override
    public List<CustomerDTO> getAllCustomers(){
        log.info("CustomerServiceImpl - Method getAllCustomers");
        List<CustomerDTO> customersDTO = mapListCustomerToDTO(customerRepository.findAll());
        log.info("CustomerServiceImpl - Return getAllCustomers: "+customersDTO);
        return customersDTO;
    }

    // create customer
    @Override
    public CustomerDTO createCustomer(CustomerDTO customer){
        log.info("CustomerServiceImpl - Method createCustomer");
        Customer customerCreated = customerRepository.save(mapDTOToCustomer(customer));
        log.info("CustomerServiceImpl - Created createCustomer: "+customerCreated);
        return mapCustomerToDTO(customerCreated);
    }

    // get customer by id
    @Override
    public CustomerDTO getCustomerById(long customerId) throws ResolutionException {
        log.info("CustomerServiceImpl - Method getCustomerById");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        log.info("CustomerServiceImpl - Found getCustomerById: "+customer);
        return mapCustomerToDTO(customer);
    }

    // update customer
    @Override
    public CustomerDTO updateCustomer(long customerId, CustomerDTO customerDetails) throws ResolutionException{
        log.info("CustomerServiceImpl - Method updateCustomer");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        log.info("CustomerServiceImpl - Found updateCustomer: "+customer);
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customerRepository.save(customer);
        return mapCustomerToDTO(customer);
    }

    // delete customer by id
    @Override
    public void deleteCustomer(long customerId) throws ResolutionException{
        log.info("CustomerServiceImpl - Method deleteCustomer");
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        log.info("CustomerServiceImpl - Found deleteCustomer: "+customer);
        customerRepository.deleteById(customerId);
    }

    static CustomerDTO mapCustomerToDTO (Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setOrders(OrderServiceImpl.mapListOrderToDTO(customer.getOrders()));
        return customerDTO;
    }

    static List<CustomerDTO> mapListCustomerToDTO (List<Customer> customers){
        List<CustomerDTO> customersDTO = new ArrayList<>();
        for (Customer customer : customers) {
            customersDTO.add(mapCustomerToDTO(customer));
        }
        return customersDTO;
    }

    static Customer mapDTOToCustomer (CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        return customer;
    }
}
