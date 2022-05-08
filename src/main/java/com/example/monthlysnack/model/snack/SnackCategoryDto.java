package com.example.monthlysnack.model.snack;

import java.time.LocalDateTime;
import java.util.UUID;

public class SnackCategoryDto {
    private SnackCategoryDto() {

    }

    public record RegisterSnackCategory(String snackCategoryName) {
        public SnackCategory getSnackCategory() {
            var now = LocalDateTime.now();
            return new SnackCategory(UUID.randomUUID(), snackCategoryName, now, now);
        }
    }

    public record UpdateSnackCategory(UUID snackCategoryId, String snackCategoryName) {
    }
}
