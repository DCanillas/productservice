package org.example.productservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CustomerDTO;
import org.example.modelproject.model.Customer;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.security.TestSecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(classes = TestSecurityConfig.class)
public class CustomerServiceImplTest {
    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerServiceImpl customerService;

    private List<CustomerDTO> listCustomersDTO;
    private List<Customer> listCustomers;
    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    public void setUp() throws Exception{
        customerService = new CustomerServiceImpl(customerRepository, modelMapper);
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
    @Transactional
    public void testGetAllCustomers(){
        log.info("Test - testGetAllCustomers");

        Mockito.when(customerRepository.findAll()).thenReturn(listCustomers);

        List<CustomerDTO> actualListCustomersDTO = customerService.getAllCustomers();
        List<CustomerDTO> expectedListCustomersDTO = listCustomers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
        assertTrue(actualListCustomersDTO.equals(expectedListCustomersDTO));
    }

    @Test
    @Transactional
    public void testCreateCustomer(){
        log.info("Test - testCreateCustomer");
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO actualCustomerDTO = customerService.createCustomer(customerDTO);
        CustomerDTO expectedCustomerDTO = modelMapper.map(customer, CustomerDTO.class);
        assertTrue(actualCustomerDTO.equals(expectedCustomerDTO));
    }

    @Test
    @Transactional
    public void testGetCustomerById(){
        log.info("Test - testGetCustomerById");
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));

        CustomerDTO actualCustomerDTO = customerService.getCustomerById(1);
        CustomerDTO expectedCustomerDTO = modelMapper.map(customer, CustomerDTO.class);
        assertTrue(actualCustomerDTO.equals(expectedCustomerDTO));
    }

    @Test
    @Transactional
    public void testUpdateCustomer(){
        log.info("Test - testUpdateCustomer");
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);

        CustomerDTO actualCustomerDTO = customerService.updateCustomer(1, customerDTO);
        CustomerDTO expectedCustomerDTO = modelMapper.map(customer, CustomerDTO.class);
        assertTrue(actualCustomerDTO.equals(expectedCustomerDTO));
    }

    @Test
    @Transactional
    public void testDeleteCustomer(){
        log.info("Test - testDeleteCustomer");
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
        Mockito.doNothing().when(customerRepository).deleteById(anyLong());

        customerService.deleteCustomer(1);

        Mockito.verify(customerRepository, times(1)).deleteById(1L);
    }
}
