package com.example.monthlysnack.repository.order;

import com.example.monthlysnack.model.order.Order;
import com.example.monthlysnack.model.order.OrderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    Optional<Order> insertOrder(Order order);

    Optional<OrderItem> insertOrderItem(OrderItem orderItem);

    List<Order> findAllOrder();

    List<Order> findAllOrderByCustomerId(UUID customerId);

    Optional<Order> findOrderById(UUID snackOrderId);

    List<OrderItem> findAllOrderItem(UUID snackOrderId);

    Optional<OrderItem> findOrderItemById(UUID orderItemId);

    Optional<Order> updateOrder(Order order);

    boolean deleteOrderById(UUID snackOrderId);

    boolean deleteAllOderItemById(UUID snackOrderId);
}
