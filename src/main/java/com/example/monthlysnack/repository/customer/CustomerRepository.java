package com.example.monthlysnack.repository.customer;

import com.example.monthlysnack.model.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface CustomerRepository {
    Optional<Customer> insert(Customer customer);

    List<Customer> findAll();

    Optional<Customer> findById(UUID customerId);

    List<Customer> findByName(String name);

    Optional<Customer> update(Customer customer);
}
