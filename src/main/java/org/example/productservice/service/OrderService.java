package org.example.productservice.service;

import org.example.productservice.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO createOrder();
    OrderDTO getOrderById(long orderId);
    OrderDTO updateOrder(long orderId, long customerId);
    OrderDTO assignProducttoOrder(long orderId, long productId);
    void deleteOrder(long orderId);
}
