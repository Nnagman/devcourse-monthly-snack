package com.example.monthlysnack.model.order;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderItem {
    private final UUID snackOrderId;
    private final UUID snackId;
    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt;

    public OrderItem(UUID snackOrderId, UUID snackId, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.snackOrderId = snackOrderId;
        this.snackId = snackId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public UUID getSnackOrderId() {
        return snackOrderId;
    }

    public UUID getSnackId() {
        return snackId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
