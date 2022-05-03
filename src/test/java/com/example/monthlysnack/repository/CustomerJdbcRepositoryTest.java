package com.example.monthlysnack.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerJdbcRepositoryTest {
    private final CustomerRepository customerRepository;

    CustomerJdbcRepositoryTest(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @BeforeEach
    void setup() {
    }

    @Test
    @DisplayName("모든 고객을 불러올 수 있다.")
    void findAll() {
        var customers = customerRepository.findAll();
    }
}