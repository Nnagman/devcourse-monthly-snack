package com.example.monthlysnack.controller.customer;

import com.example.monthlysnack.model.customer.CustomerDto.UpdateCustomer;
import com.example.monthlysnack.service.customer.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
public class CustomerMvcController {
    private final CustomerService customerService;

    public CustomerMvcController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String getCustomersPage(Model model) {
        var customers = customerService.getAll();
        model.addAttribute("customers", customers);
        return "customer/customer-list";
    }

    @GetMapping("/customers/{name}")
    public String getCustomersPage(Model model, @PathVariable("name") String name) {
        var customers = customerService.getByName(name);
        model.addAttribute("customers", customers);
        return "customer/customer-list";
    }

    @GetMapping("/customer/{id}")
    public String getCustomerDetailPage(Model model, @PathVariable("id") UUID id) {
        var customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "customer/customer-detail";
    }

    @GetMapping("/customer/update/{id}")
    public String getCustomerUpdatePage(Model model, @PathVariable("id") UUID id) {
        var customer = customerService.getById(id);
        model.addAttribute("customer", customer);
        return "customer/customer-update";
    }

    @PostMapping("/customer/update")
    public String updateCustomer(@RequestBody UpdateCustomer updateCustomer) {
        var customer = customerService.update(updateCustomer);
        return "redirect:/customer/" + customer.getCustomerId().toString();
    }
}
