package com.example.monthlysnack.service;

import com.example.monthlysnack.model.customer.Customer;
import com.example.monthlysnack.model.customer.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAll();
    Customer insert(CustomerDto.RegisterCustomer registerCustomer);

    Customer getById(UUID id);

    List<Customer> getByName(String name);

    Customer update(CustomerDto.UpdateCustomer updateCustomer);
}
