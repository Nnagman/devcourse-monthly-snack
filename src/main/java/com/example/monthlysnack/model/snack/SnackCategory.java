package com.example.monthlysnack.model.snack;

import java.time.LocalDateTime;
import java.util.UUID;

public class SnackCategory {
    private final UUID snackCategoryId;
    private String name;
    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt;

    public SnackCategory(UUID snackCategoryId, String name,
                         LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.snackCategoryId = snackCategoryId;
        this.name = name;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public UUID getSnackCategoryId() {
        return snackCategoryId;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
