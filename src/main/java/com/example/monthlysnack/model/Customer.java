package com.example.monthlysnack.model;

import com.example.monthlysnack.model.customer.Address;
import com.example.monthlysnack.model.customer.Email;
import com.example.monthlysnack.model.customer.Postcode;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private String address;
    private String postcode;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(UUID customerId, String name, String email,
                    String address, String postcode, LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changePostcode(String postcode) {
        this.postcode = postcode;
    }

    public void changeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
