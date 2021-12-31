package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.CustomerDTO;
import org.example.productservice.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerServiceImpl customerService;

    private List<CustomerDTO> listCustomers;
    private CustomerDTO customer;
    private final String url = "/api/v1/customer";
    private final String urlId = "/api/v1/customer/1";

    @BeforeEach
    public void setUp() throws Exception {
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

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(listCustomers);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        log.info("Test - testCreateCustomer");
        Mockito.when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(customer);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetCustomerById() throws Exception{
        log.info("Test - testGetCustomerById");
        Mockito.when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(urlId)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(customer);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        log.info("Test - testUpdateCustomer");
        Mockito.when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(customer);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.put(urlId)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(customer);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteCustomer() throws Exception{
        log.info("Test - testDeleteCustomer");

        mockMvc.perform(MockMvcRequestBuilders.delete(urlId)).andExpect(status().isOk());
    }
}

