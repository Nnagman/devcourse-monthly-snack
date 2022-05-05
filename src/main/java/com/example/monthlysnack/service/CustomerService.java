package com.example.monthlysnack.service;

import com.example.monthlysnack.exception.CustomerException.CustomerNotRegisterException;
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

import static com.example.monthlysnack.exception.CustomerException.CustomerNotFoundException;
import static com.example.monthlysnack.exception.CustomerException.CustomerNotUpdateException;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer insert(RegisterCustomer registerCustomer) {
        var customer = customerRepository
                .insert(registerCustomer.getCustomer());

        if (customer.isEmpty()) {
            throw new CustomerNotRegisterException(
                    ErrorMessage.CUSTOMER_NOT_REGISTER);
        }

        return customer.get();
    }

    public Optional<Customer> getById(UUID id) {
        var customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(
                    ErrorMessage.CUSTOMER_NOT_FOUND);
        }

        return customerRepository.findById(id);
    }

    public List<Customer> getByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> getByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer update(UpdateCustomer updateCustomer) {
        var customer
                = customerRepository.findById(updateCustomer.customerId());

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(ErrorMessage.CUSTOMER_NOT_FOUND);
        }

        customer.get().changeName(updateCustomer.name());
        customer.get().changeAddress(updateCustomer.address());
        customer.get().changePostcode(updateCustomer.postCode());
        customer.get().changeUpdatedAt(LocalDateTime.now());

        var updatedCustomer
                = customerRepository.update(customer.get());

        if (updatedCustomer.isEmpty()) {
            throw new CustomerNotUpdateException(
                    ErrorMessage.CUSTOMER_NOT_UPDATE);
        }

        return updatedCustomer.get();
    }
}
