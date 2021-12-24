package org.example.productservice.controller;

import org.example.modelproject.Order;
import org.example.productservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@CrossOrigin(origins = "http://localhost::3306")
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    // create get all orders api
    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @PostMapping("/order")
    // create order
    public Order createOrder(@RequestBody Order order){
        return orderRepository.save(order);
    }

    // get order by id
    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") long orderId) throws ResolutionException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        return ResponseEntity.ok().body(order);
    }

    // update order
    @PutMapping("/order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") long orderId,
                                                   @RequestBody Order orderDetails) throws ResolutionException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        order.setCustomerId(orderDetails.getCustomerId());
        orderRepository.save(order);
        return ResponseEntity.ok().body(order);
    }

    // delete order by id
    @DeleteMapping("/order/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") long orderId) throws ResolutionException{
        orderRepository.findById(orderId)
                .orElseThrow(() -> new ResolutionException("Order not found for this id :: " + orderId));
        orderRepository.deleteById(orderId);
        return ResponseEntity.ok().build();
    }

}
