package com.example.monthlysnack.service.order;

import com.example.monthlysnack.exception.OrderException;
import com.example.monthlysnack.exception.OrderException.*;
import com.example.monthlysnack.message.ErrorMessage;
import com.example.monthlysnack.model.order.Order;
import com.example.monthlysnack.model.order.OrderItem;
import com.example.monthlysnack.model.order.OrderStatus;
import com.example.monthlysnack.model.snack.Snack;
import com.example.monthlysnack.repository.customer.CustomerRepository;
import com.example.monthlysnack.repository.order.OrderRepository;
import com.example.monthlysnack.repository.snack.SnackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.monthlysnack.message.ErrorMessage.*;
import static com.example.monthlysnack.model.order.OrderStatus.ACCEPTED;

@Service
public class OrderDefaultService implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final SnackRepository snackRepository;

    public OrderDefaultService(OrderRepository orderRepository,
                               CustomerRepository customerRepository,
                               SnackRepository snackRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.snackRepository = snackRepository;
    }

    @Override
    public Order insertOrder(UUID customerId, List<UUID> snackIds) {
        var customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            throw new OrderNotRegisterException(CUSTOMER_NOT_FOUND);
        }

        var now = LocalDateTime.now();

        var order = new Order(UUID.randomUUID(), customer.get().getCustomerId(),
                ACCEPTED.toString(), now, now);

        var insertedOrder = orderRepository.insertOrder(order);

        if (insertedOrder.isEmpty()) {
            throw new OrderNotRegisterException(ORDER_NOT_REGISTER);
        }

        for (UUID snackId : snackIds) {
            var snack = snackRepository.findById(snackId);

            if (snack.isEmpty()) {
                throw new OrderNotRegisterException(SNACK_NOT_FOUND);
            }

            var orderItem = orderRepository.insertOrderItem(new OrderItem(
                    insertedOrder.get().getSnackOrderId(),
                    snackId, now, now
            ));

            if (orderItem.isEmpty()) {
                throw new OrderNotRegisterException(ORDER_ITEM_NOT_REGISTER);
            }
        }

        return insertedOrder.get();
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAllOrder();
    }

    @Override
    public List<Order> getAllOrderByCustomerId(UUID customerId) {
        return orderRepository.findAllOrderByCustomerId(customerId);
    }

    @Override
    public Order getOrderById(UUID snackOrderId) {
        var order = orderRepository.findOrderById(snackOrderId);

        if (order.isEmpty()) {
            throw new OrderNotFoundException(ORDER_NOT_FOUND);
        }

        return order.get();
    }

    @Override
    public List<OrderItem> getAllOrderItem(UUID snackOrderId) {
        return orderRepository.findAllOrderItem(snackOrderId);
    }

    @Override
    public Order updateOrder(Order order) {
        var updateOrder = orderRepository.updateOrder(order);

        if (updateOrder.isEmpty()) {
            throw new OrderNotUpdateException(ORDER_NOT_UPDATE);
        }

        return order;
    }

    @Override
    public Order deleteOrderById(UUID snackOrderId) {
        var order = orderRepository.findOrderById(snackOrderId);

        if (order.isEmpty()) {
            throw new OrderNotDeleteException(ORDER_NOT_FOUND);
        }

        var deleteOrder = orderRepository.deleteOrderById(snackOrderId);

        if (!deleteOrder) {
            throw new OrderNotDeleteException(ORDER_NOT_DELETE);
        }

        return order.get();
    }

    @Override
    public List<OrderItem> deleteAllOderItemById(UUID snackOrderId) {
        var orderItems =
                orderRepository.findAllOrderItem(snackOrderId);

        var delete = orderRepository.deleteAllOderItemById(snackOrderId);

        if (!delete) {
            throw new OrderItemNotDeleteException(ORDER_ITEM_NOT_DELETE);
        }

        return orderItems;
    }
}
