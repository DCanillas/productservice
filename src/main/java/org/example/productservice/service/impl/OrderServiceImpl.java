package org.example.productservice.service.impl;

import org.example.modelproject.Customer;
import org.example.modelproject.Order;
import org.example.modelproject.Product;
import org.example.productservice.dto.OrderDTO;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.repository.OrderRepository;
import org.example.productservice.repository.ProductRepository;
import org.example.productservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    // get all orders
    @Override
    public List<OrderDTO> getAllOrders(){
        return mapListOrderToDTO(orderRepository.findAll());
    }

    // create order
    @Override
    public OrderDTO createOrder(){
        return mapOrderToDTO(orderRepository.save(new Order()));
    }

    // get order by id
    @Override
    public OrderDTO getOrderById(long orderId) throws ResolutionException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        return mapOrderToDTO(order);
    }

    // assign customer to order
    @Override
    public OrderDTO updateOrder(long orderId, long customerId) throws ResolutionException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        order.setCustomer(customer);
        orderRepository.save(order);
        return mapOrderToDTO(order);
    }

    // assign product to order
    @Override
    public OrderDTO assignProducttoOrder(long orderId, long productId) throws ResolutionException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("product not found for this id :: " + productId));
        order.addProduct(product);
        return mapOrderToDTO(orderRepository.save(order));
    }

    // delete order by id
    @Override
    public void deleteOrder(long orderId) throws ResolutionException{
        orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        orderRepository.deleteById(orderId);
    }

    static OrderDTO mapOrderToDTO (Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        if (order.getCustomer() != null){
            orderDTO.setCustomerId(order.getCustomer().getId());
        }
        if (order.getProducts() != null){
            orderDTO.setProducts(ProductServiceImpl.mapListProductToDTO(order.getProducts()));
        }
        return orderDTO;
    }

    static List<OrderDTO> mapListOrderToDTO (List<Order> orders){
        List<OrderDTO> ordersDTO = new ArrayList<>();
        for (Order order : orders) {
            ordersDTO.add(mapOrderToDTO(order));
        }
        return ordersDTO;
    }

}
