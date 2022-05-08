package com.example.monthlysnack.model.order;

import java.time.LocalDateTime;
import java.util.UUID;

public class Order {
    private final UUID snackOrderId;
    private final UUID customerId;
    private String orderStatus;
    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt;

    public Order(UUID snackOrderId, UUID customerId, String orderStatus,
                 LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.snackOrderId = snackOrderId;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public UUID getSnackOrderId() {
        return snackOrderId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void changeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
