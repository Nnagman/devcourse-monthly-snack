package com.example.monthlysnack.service;

import com.example.monthlysnack.exception.CustomerException.CustomerNotRegisterException;
import com.example.monthlysnack.message.ErrorMessage;
import com.example.monthlysnack.model.customer.Customer;
import com.example.monthlysnack.model.customer.CustomerDto.RegisterCustomer;
import com.example.monthlysnack.model.customer.CustomerDto.UpdateCustomer;
import com.example.monthlysnack.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.monthlysnack.exception.CustomerException.CustomerNotFoundException;
import static com.example.monthlysnack.exception.CustomerException.CustomerNotUpdateException;

@Service
public class CustomerDefaultService implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDefaultService(CustomerRepository customerRepository) {
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

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(UUID id) {
        var customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(
                    ErrorMessage.CUSTOMER_NOT_FOUND);
        }

        return customer.get();
    }

    @Override
    public List<Customer> getByName(String name) {
        return customerRepository.findByName(name);
    }

    @Override
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
