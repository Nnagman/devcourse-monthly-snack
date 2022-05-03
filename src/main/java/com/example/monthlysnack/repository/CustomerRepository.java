package com.example.monthlysnack.repository;

import com.example.monthlysnack.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {
    Customer insert(Customer customer);

    List<Customer> findAll();

    Customer findById(UUID customerId);

    List<Customer> findByName(String name);

    Customer findByEmail(String email);

    Customer update(Customer customer);

    Customer deleteById(Customer customer);
}
