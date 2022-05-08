package com.example.monthlysnack.model.snack;

import java.time.LocalDateTime;
import java.util.UUID;

public class SnackDto {
    private SnackDto() {

    }

    static public class RegisterSnack {
        private final String name;
        private final int price;
        private final UUID categoryId;
        private String description;

        public RegisterSnack(String name, int price, UUID categoryId) {
            this.name = name;
            this.price = price;
            this.categoryId = categoryId;
            this.description = "";
        }

        public RegisterSnack(String name, int price, UUID categoryId,
                             String description) {
            this.name = name;
            this.price = price;
            this.categoryId = categoryId;
            this.description = description;
        }

        public Snack getSnack() {
            var now = LocalDateTime.now();
            return new Snack(UUID.randomUUID(), name, price,
                    categoryId, description, now, now);
        }
    }

    public record UpdateSnack(UUID snackId, String name, int price,
                              UUID categoryId, String description) {
    }
}
