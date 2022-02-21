package org.example.productservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.OrderDTO;
import org.example.productservice.security.TestSecurityConfig;
import org.example.productservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestSecurityConfig.class)
public class OrderControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;

    private OrderController orderController;

    private List<OrderDTO> listOrders;
    private OrderDTO order;

    @BeforeEach
    public void setUp() throws Exception {
        orderController = new OrderController(orderService);
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

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(listOrders);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testCreateOrder() throws Exception {
        log.info("Test - testCreateOrder");
        Mockito.when(orderService.createOrder(any(OrderDTO.class))).thenReturn(order);

        ResponseEntity<OrderDTO> response = orderController.createOrder(order);

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testGetOrderById() throws Exception{
        log.info("Test - testGetOrderById");
        Mockito.when(orderService.getOrderById(anyLong())).thenReturn(order);

        ResponseEntity<OrderDTO> response = orderController.getOrderById(order.getId());

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testUpdateOrder() throws Exception {
        log.info("Test - testUpdateOrder");
        Mockito.when(orderService.updateOrder(anyLong(), anyLong())).thenReturn(order);

        ResponseEntity<OrderDTO> response = orderController.updateOrder(order.getId(), order.getId());

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testAssignProductToOrder() throws Exception {
        log.info("Test - testAssignProductToOrder");
        Mockito.when(orderService.assignProductToOrder(anyLong(), anyLong())).thenReturn(order);

        ResponseEntity<OrderDTO> response = orderController.assignProductToOrder(order.getId(), order.getId());

        String actualJsonResponse = objectMapper.writeValueAsString(response.getBody());
        String expectedJsonResponse = objectMapper.writeValueAsString(order);
        assertThat(actualJsonResponse).isEqualTo(expectedJsonResponse);
    }

    @Test
    public void testDeleteOrder() throws Exception{
        log.info("Test - testDeleteOrder");

        ResponseEntity<OrderDTO> response = orderController.deleteOrder(order.getId());
        assertThat(response.getStatusCode()).isEqualTo(ResponseEntity.ok().build().getStatusCode());
    }
}
