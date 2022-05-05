package com.example.monthlysnack.service;

import com.example.monthlysnack.exception.CustomerException;
import com.example.monthlysnack.message.ErrorMessage;
import com.example.monthlysnack.model.Customer;
import com.example.monthlysnack.model.CustomerDto.RegisterCustomer;
import com.example.monthlysnack.model.CustomerDto.UpdateCustomer;
import com.example.monthlysnack.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> insert(RegisterCustomer registerCustomer) {
        return customerRepository
                .insert(registerCustomer.getCustomer());
    }

    public Optional<Customer> getById(UUID id) {
        var customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerException
                    .CustomerNotFoundException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }

        return customerRepository.findById(id);
    }

    public List<Customer> getByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> getByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> update(UpdateCustomer updateCustomer) {
        var customer
                = customerRepository.findById(updateCustomer.customerId());

        if (customer.isEmpty()) {
            return customer;
        }

        customer.get().changeName(updateCustomer.name());
        customer.get().changeAddress(updateCustomer.address());
        customer.get().changePostcode(updateCustomer.postCode());
        customer.get().changeUpdatedAt(LocalDateTime.now());

        return customerRepository.update(customer.get());
    }

    public Optional<Customer> delete(UUID customerId) {
        var customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return Optional.empty();
        }

        return customerRepository.deleteById(customer.get());
    }
}
