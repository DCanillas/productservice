package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.CustomerDTO;
import org.example.productservice.security.TestSecurityConfig;
import org.example.productservice.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestSecurityConfig.class)
public class CustomerControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerServiceImpl customerService;

    private CustomerController customerController;

    private List<CustomerDTO> listCustomers;
    private CustomerDTO customer;

    @BeforeEach
    public void setUp() throws Exception {
        customerController = new CustomerController(customerService);
        listCustomers = new ObjectMapper().readValue(
                new File("src/test/resource/ListCustomersDTO.json"),
                new TypeReference<List<CustomerDTO>>() {
                });
        customer = listCustomers.get(0);
    }

    @Test
    public void testGetAllCustomers() throws Exception{
        log.info("Test - testGetAllCustomers");
        Mockito.when(customerService.getAllCustomers()).thenReturn(listCustomers);

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(listCustomers);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        log.info("Test - testCreateCustomer");
        Mockito.when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customer);

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(customer);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetCustomerById() throws Exception{
        log.info("Test - testGetCustomerById");
        Mockito.when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(customer.getId());

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(customer);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        log.info("Test - testUpdateCustomer");
        Mockito.when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customer);

        ResponseEntity<CustomerDTO> response = customerController.updateCustomer(customer.getId(), customer);

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(customer);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteCustomer() throws Exception{
        log.info("Test - testDeleteCustomer");

        ResponseEntity<CustomerDTO> response = customerController.deleteCustomer(customer.getId());
        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok().build().getStatusCode());
    }
}

