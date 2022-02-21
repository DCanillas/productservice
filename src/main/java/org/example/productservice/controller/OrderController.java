package org.example.productservice.controller;

import org.example.modelproject.dto.OrderDTO;
import org.example.productservice.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    // create order
    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order){
        return ResponseEntity.ok().body(orderService.createOrder(order));
    }

    // get order by id
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> assignProductToOrder(@PathVariable(value="orderId") long orderId,
                                                          @PathVariable(value="productId") long productId) throws ResolutionException{
        return ResponseEntity.ok().body(orderService.assignProductToOrder(orderId, productId));
    }

    // delete order by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable(value = "id") long orderId) throws ResolutionException{
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

}
