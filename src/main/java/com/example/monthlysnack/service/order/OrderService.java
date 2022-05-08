package com.example.monthlysnack.service.order;

import com.example.monthlysnack.model.order.Order;
import com.example.monthlysnack.model.order.OrderItem;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order insertOrder(UUID customerId, List<UUID> snackIds);

    List<Order> getAllOrder();

    List<Order> getAllOrderByCustomerId(UUID customerId);

    Order getOrderById(UUID snackOrderId);

    List<OrderItem> getAllOrderItem(UUID snackOrderId);

    Order updateOrder(Order order);

    Order deleteOrderById(UUID snackOrderId);

    List<OrderItem> deleteAllOderItemById(UUID snackOrderId);
}
