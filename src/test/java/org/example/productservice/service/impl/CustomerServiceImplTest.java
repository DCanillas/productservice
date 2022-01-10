package org.example.productservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CategoryDTO;
import org.example.modelproject.dto.CustomerDTO;
import org.example.modelproject.model.Customer;
import org.example.productservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@Slf4j
@SpringBootTest
public class CustomerServiceImplTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private CustomerRepository customerRepository;
    
    @InjectMocks
    private CustomerServiceImpl customerService;

    private List<CustomerDTO> listCustomersDTO;
    private List<Customer> listCustomers;
    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    public void setUp() throws Exception{
        listCustomersDTO = new ObjectMapper().readValue(
                new File("src/test/resource/ListCustomersDTO.json"),
                new TypeReference<List<CustomerDTO>>() {
                });
        customerDTO = listCustomersDTO.get(0);

        listCustomers = new ObjectMapper().readValue(
                new File("src/test/resource/ListCustomers.json"),
                new TypeReference<List<Customer>>() {
                });
        customer = listCustomers.get(0);
    }

    @Test
    public void testGetAllCustomers(){
        log.info("Test - testGetAllCustomers");

        Mockito.when(customerRepository.findAll()).thenReturn(listCustomers);

        List<CustomerDTO> actualListCustomersDTO = customerService.getAllCustomers();
        List<CustomerDTO> expectedListCustomersDTO = listCustomers.stream()
                .map(customer -> new ModelMapper().map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
        assertTrue(actualListCustomersDTO.equals(expectedListCustomersDTO));
    }

    @Test
    public void testCreateCustomer(){
        log.info("Test - testCreateCustomer");
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO actualCustomerDTO = customerService.createCustomer(customerDTO);
        CustomerDTO expectedCustomerDTO = new ModelMapper().map(customer, CustomerDTO.class);
        assertTrue(actualCustomerDTO.equals(expectedCustomerDTO));
    }

    @Test
    public void testGetCustomerById(){
        log.info("Test - testGetCustomerById");
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));

        CustomerDTO actualCustomerDTO = customerService.getCustomerById(1);
        CustomerDTO expectedCustomerDTO = new ModelMapper().map(customer, CustomerDTO.class);
        assertTrue(actualCustomerDTO.equals(expectedCustomerDTO));
    }

    @Test
    public void testUpdateCustomer(){
        log.info("Test - testUpdateCustomer");
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO actualCustomerDTO = customerService.updateCustomer(1, customerDTO);
        CustomerDTO expectedCustomerDTO = new ModelMapper().map(customer, CustomerDTO.class);
        assertTrue(actualCustomerDTO.equals(expectedCustomerDTO));
    }

    @Test
    public void testDeleteCustomer(){
        log.info("Test - testDeleteCustomer");
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
        Mockito.doNothing().when(customerRepository).deleteById(anyLong());

        customerService.deleteCustomer(1);

        Mockito.verify(customerRepository, times(1)).deleteById(1L);
    }
}
