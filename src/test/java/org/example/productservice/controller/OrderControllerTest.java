package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.OrderDTO;
import org.example.productservice.service.impl.OrderServiceImpl;
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
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;

    private List<OrderDTO> listOrders;
    private OrderDTO order;
    private final String url = "/api/v1/order";
    private final String urlId = "/api/v1/order/1";
    private final String urlIdC = "/api/v1/order/1/customer/1";
    private final String urlIdP = "/api/v1/order/1/product/1";

    @BeforeEach
    public void setUp() throws Exception {
        listOrders = new ObjectMapper().readValue(
                new File("src/test/resource/ListOrdersDTO.json"),
                new TypeReference<List<OrderDTO>>() {
                });
        order = listOrders.get(0);
    }

    @Test
    public void testGetAllOrders() throws Exception{
        log.info("Test - testGetAllOrders");
        Mockito.when(orderService.getAllOrders()).thenReturn(listOrders);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(listOrders);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateOrder() throws Exception {
        log.info("Test - testCreateOrder");
        Mockito.when(orderService.createOrder(any(OrderDTO.class))).thenReturn(order);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetOrderById() throws Exception{
        log.info("Test - testGetOrderById");
        Mockito.when(orderService.getOrderById(anyLong())).thenReturn(order);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(urlId)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateOrder() throws Exception {
        log.info("Test - testUpdateOrder");
        Mockito.when(orderService.updateOrder(anyLong(), anyLong())).thenReturn(order);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(urlIdC)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testAssignProductToOrder() throws Exception {
        log.info("Test - testAssignProductToOrder");
        Mockito.when(orderService.assignProductToOrder(anyLong(), anyLong())).thenReturn(order);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(urlIdP)).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteOrder() throws Exception{
        log.info("Test - testDeleteOrder");

        mockMvc.perform(MockMvcRequestBuilders.delete(urlId)).andExpect(status().isOk());
    }
}
