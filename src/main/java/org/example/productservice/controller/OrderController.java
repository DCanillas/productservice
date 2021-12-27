package org.example.productservice.controller;

import org.example.modelproject.Customer;
import org.example.modelproject.Order;
import org.example.modelproject.Product;
import org.example.productservice.repository.CustomerRepository;
import org.example.productservice.repository.OrderRepository;
import org.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@CrossOrigin(origins = "http://localhost::3306")
@RestController
@RequestMapping("${api.version}/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // get all orders
    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @PostMapping("")
    // create order
    public Order createOrder(@RequestBody Order order){
        return orderRepository.save(order);
    }

    // get order by id
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") long orderId) throws ResolutionException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        return ResponseEntity.ok().body(order);
    }

    // assign customer to order
    @PutMapping("/{orderId}/customer/{customerId}")
    public Order updateOrder(@PathVariable(value = "orderId") long orderId,
                                             @PathVariable(value = "customerId") long customerId) throws ResolutionException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResolutionException("Customer not found for this id :: " + customerId));
        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    // assign product to order
    @PutMapping("{orderId}/product/{productId}")
    public Order assignProducttoOrder(@PathVariable(value="orderId") long orderId,
                                                          @PathVariable(value="productId") long productId) throws ResolutionException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResolutionException("product not found for this id :: " + productId));
        order.addProduct(product);
        return orderRepository.save(order);
    }

    // delete order by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") long orderId) throws ResolutionException{
        orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        orderRepository.deleteById(orderId);
        return ResponseEntity.ok().build();
    }

}
