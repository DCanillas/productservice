package org.example.productservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.modelproject.dto.OrderDTO;
import org.example.modelproject.model.Customer;
import org.example.modelproject.model.Order;
import org.example.modelproject.model.Product;
import org.example.productservice.exception.ResourceNotFoundException;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.repository.OrderRepository;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    // get all orders
    @Override
    public List<OrderDTO> getAllOrders(){
        log.info("OrderServiceImpl - Method getAllOrders");
        List<OrderDTO> ordersDTO = orderRepository.findAll().stream()
                        .map(order -> modelMapper.map(order, OrderDTO.class))
                        .collect(Collectors.toList());
        log.info("OrderServiceImpl - Return getAllOrders: "+ordersDTO);
        return ordersDTO;
    }

    // create order
    @Override
    public OrderDTO createOrder(OrderDTO order){
        log.info("OrderServiceImpl - Method createOrder: "+order);
        Order orderCreated = orderRepository.save(new Order());
        log.info("OrderServiceImpl - Created createOrder: "+orderCreated);
        if (order != null){
            if (Long.valueOf(order.getCustomerId()) != null && order.getCustomerId() != 0){
                this.updateOrder(orderCreated.getId(), order.getCustomerId());
            }
            if (order.getProducts() != null){
                order.getProducts().stream().forEach(product -> assignProductToOrder(orderCreated.getId(),product.getId()));
            }
        }
        return modelMapper.map(orderCreated, OrderDTO.class);
    }

    // get order by id
    @Override
    public OrderDTO getOrderById(long orderId) {
        log.info("OrderServiceImpl - Method getOrderById: "+orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        log.info("OrderServiceImpl - Found getOrderById: "+order);
        return modelMapper.map(order, OrderDTO.class);
    }

    // assign customer to order
    @Override
    public OrderDTO updateOrder(long orderId, long customerId) {
        log.info("OrderServiceImpl - Method updateOrder: "+orderId+"; "+customerId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        log.info("OrderServiceImpl - Order Found updateOrder: "+order);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerId));
        log.info("OrderServiceImpl - Customer Found updateOrder: "+customer);
        order.setCustomer(customer);
        orderRepository.save(order);
        return modelMapper.map(order, OrderDTO.class);
    }

    // assign product to order
    @Override
    public OrderDTO assignProductToOrder(long orderId, long productId) {
        log.info("OrderServiceImpl - Method assignProductToOrder: "+orderId+"; "+productId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        log.info("OrderServiceImpl - Order Found assignProductToOrder: "+order);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product not found for this id :: " + productId));
        log.info("OrderServiceImpl - Product Found assignProductToOrder: "+product);
        order.addProduct(product);
        return modelMapper.map(orderRepository.save(order), OrderDTO.class);
    }

    // delete order by id
    @Override
    public void deleteOrder(long orderId) {
        log.info("OrderServiceImpl - Method deleteOrder");
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderId));
        log.info("OrderServiceImpl - Found deleteOrder: "+order);
        orderRepository.deleteById(orderId);
    }

}
