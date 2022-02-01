package org.example.productservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.OrderDTO;
import org.example.modelproject.model.Customer;
import org.example.modelproject.model.Order;
import org.example.modelproject.model.Product;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.repository.OrderRepository;
import org.example.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

@Slf4j
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private ModelMapper modelMapper;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    private OrderServiceImpl orderService;

    private List<OrderDTO> listOrdersDTO;
    private List<Order> listOrders;
    private OrderDTO orderDTO;
    private Order order;
    private Customer customer;
    private Product product;

    @BeforeEach
    public void setUp() throws Exception{
        orderService = new OrderServiceImpl(orderRepository,
                customerRepository, productRepository, modelMapper);
        listOrdersDTO = new ObjectMapper().readValue(
                new File("src/test/resource/ListOrdersDTO.json"),
                new TypeReference<List<OrderDTO>>() {
                });
        orderDTO = listOrdersDTO.get(0);

        listOrders = new ObjectMapper().readValue(
                new File("src/test/resource/ListOrders.json"),
                new TypeReference<List<Order>>() {
                });
        order = listOrders.get(0);

        product = (new ObjectMapper().readValue(
                new File("src/test/resource/ListProducts.json"),
                new TypeReference<List<Product>>() {
                })).get(0);

        customer = (new ObjectMapper().readValue(
                new File("src/test/resource/ListCustomers.json"),
                new TypeReference<List<Customer>>() {
                })).get(0);
    }

    @Test
    public void testGetAllOrders(){
        log.info("Test - testGetAllOrders");

        Mockito.when(orderRepository.findAll()).thenReturn(listOrders);

        List<OrderDTO> actualListOrdersDTO = orderService.getAllOrders();
        List<OrderDTO> expectedListOrdersDTO = listOrders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
        assertTrue(actualListOrdersDTO.equals(expectedListOrdersDTO));
    }

    @Test
    public void testCreateOrder(){
        log.info("Test - testCreateOrder");
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO actualOrderDTO = orderService.createOrder(new OrderDTO());
        OrderDTO expectedOrderDTO = modelMapper.map(order, OrderDTO.class);
        assertTrue(actualOrderDTO.equals(expectedOrderDTO));
    }

    @Test
    public void testGetOrderById(){
        log.info("Test - testGetOrderById");
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));

        OrderDTO actualOrderDTO = orderService.getOrderById(1);
        OrderDTO expectedOrderDTO = modelMapper.map(order, OrderDTO.class);
        assertTrue(actualOrderDTO.equals(expectedOrderDTO));
    }

    @Test
    public void testUpdateOrder(){
        log.info("Test - testUpdateOrder");
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        Mockito.when(customerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        OrderDTO actualOrderDTO = orderService.updateOrder(1, 1);
        OrderDTO expectedOrderDTO = modelMapper.map(order, OrderDTO.class);
        assertTrue(actualOrderDTO.equals(expectedOrderDTO));
    }

    @Test
    public void testDeleteOrder(){
        log.info("Test - testDeleteOrder");
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        Mockito.doNothing().when(orderRepository).deleteById(anyLong());

        orderService.deleteOrder(1);

        Mockito.verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testAssignProductToOrder(){
        log.info("Test - testAssignProductToOrder");
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(product));
        Mockito.when(orderRepository.save(order)).thenReturn(order);

        OrderDTO actualOrderDTO = orderService.assignProductToOrder(1, 1);
        OrderDTO expectedOrderDTO = modelMapper.map(order, OrderDTO.class);
        assertTrue(actualOrderDTO.equals(expectedOrderDTO));
    }
}
