package com.example.monthlysnack.service;

import com.example.monthlysnack.model.Customer;
import com.example.monthlysnack.model.CustomerDto;
import com.example.monthlysnack.model.Email;
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

    public Optional<Customer> insert(CustomerDto customerDto) {
        var email = new Email(customerDto.emailAddress());
        var now = LocalDateTime.now();
        Customer customer = new Customer(UUID.randomUUID(), customerDto.name(),
                email, customerDto.address(), customerDto.postCode(), now, now);
        return customerRepository.insert(customer);
    }

    public Optional<Customer> getById(UUID id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> getByEmail(String emailAddress) {
        Email email = new Email(emailAddress);
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> update(CustomerDto customerDto, UUID customerId) {
        var customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return Optional.empty();
        }

        var email = new Email(customerDto.address());

        return customerRepository.update(
                new Customer(customer.get().getCustomerId(), customerDto.name(), email,
                        customerDto.emailAddress(), customerDto.postCode(),
                        customer.get().getCreatedAt(),
                        LocalDateTime.now())
        );
    }

    public Optional<Customer> delete(UUID customerId) {
        var customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return Optional.empty();
        }

        return customerRepository.deleteById(customer.get());
    }
}
