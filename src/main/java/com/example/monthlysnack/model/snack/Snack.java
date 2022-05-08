package com.example.monthlysnack.model.snack;

import java.time.LocalDateTime;
import java.util.UUID;

public class Snack {
    private final UUID snackId;
    private String name;
    private int price;
    private UUID snackCategoryId;
    private String description;
    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt;

    public Snack(UUID snackId, String name, int price, UUID snackCategoryId,
                 LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.snackId = snackId;
        this.name = name;
        this.price = price;
        this.snackCategoryId = snackCategoryId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Snack(UUID snackId, String name, int price, UUID snackCategoryId, String description,
                 LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.snackId = snackId;
        this.name = name;
        this.price = price;
        this.snackCategoryId = snackCategoryId;
        this.description = description;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public UUID getSnackId() {
        return snackId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public UUID getSnackCategoryId() {
        return snackCategoryId;
    }

    public String getDescription() {
        return description;
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

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeSnackCategoryId(UUID snackCategoryId) {
        this.snackCategoryId = snackCategoryId;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
