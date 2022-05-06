package com.example.monthlysnack.service;

import com.example.monthlysnack.model.Customer;
import com.example.monthlysnack.model.CustomerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAll();
    Customer insert(CustomerDto.RegisterCustomer registerCustomer);

    Customer getById(UUID id);

    List<Customer> getByName(String name);

    Optional<Customer> getByEmail(String email);

    Customer update(CustomerDto.UpdateCustomer updateCustomer);
}
