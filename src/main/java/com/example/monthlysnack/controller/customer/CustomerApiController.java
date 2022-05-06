package com.example.monthlysnack.controller.customer;

import com.example.monthlysnack.model.customer.Customer;
import com.example.monthlysnack.model.customer.CustomerDto.UpdateCustomer;
import com.example.monthlysnack.model.customer.Name;
import com.example.monthlysnack.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CustomerApiController {
    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/api/v1/customers")
    public List<Customer> getCustomerList(@RequestParam Optional<Name> name) {
        if (name.isPresent()) {
            return customerService.getByName(name.get().getName());
        }
        return customerService.getAll();
    }

    @GetMapping("/api/v1/customers/{id}")
    public Customer getCustomer(@PathVariable UUID id) {
        return customerService.getById(id);
    }

    @PostMapping("/api/v1/customers/update")
    public Customer updateCustomer(@RequestBody UpdateCustomer updateCustomer) {
        return customerService.update(updateCustomer);
    }
}
