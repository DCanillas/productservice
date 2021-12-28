package org.example.productservice.controller;

import org.example.productservice.dto.OrderDTO;
import org.example.productservice.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.module.ResolutionException;
import java.util.List;

@RestController
@RequestMapping("${api.version}/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    // get all orders
    @GetMapping("")
    public List<OrderDTO> getAllOrders(){
        return orderService.getAllOrders();
    }

    // create order
    @PostMapping("")
    public OrderDTO createOrder(){
        return orderService.createOrder();
    }

    // get order by id
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable(value = "id") long orderId) throws ResolutionException{
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }

    // assign customer to order
    @PutMapping("/{orderId}/customer/{customerId}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable(value = "orderId") long orderId,
                                             @PathVariable(value = "customerId") long customerId) throws ResolutionException{
        return ResponseEntity.ok().body(orderService.updateOrder(orderId, customerId));
    }

    // assign product to order
    @PutMapping("{orderId}/product/{productId}")
    public OrderDTO assignProducttoOrder(@PathVariable(value="orderId") long orderId,
                                                          @PathVariable(value="productId") long productId) throws ResolutionException{
        return orderService.assignProducttoOrder(orderId, productId);
    }

    // delete order by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "id") long orderId) throws ResolutionException{
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
