package com.example.monthlysnack.controller.customer;

import com.example.monthlysnack.model.Customer;
import com.example.monthlysnack.repository.CustomerRepository;
import com.example.monthlysnack.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerMvcController.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CustomerMvcControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    private final List<Customer> customers = new ArrayList<>();

    @BeforeEach
    void setup() {
        var now = LocalDateTime.now();
        customers.add(new Customer(
                UUID.randomUUID(),
                "창호",
                "devcourser@grepp.com",
                "대륭서초타워",
                "06627",
                now,
                now
        ));
        customers.add(new Customer(
                UUID.randomUUID(),
                "창호우",
                "devcourser@prgrms.com",
                "대륭서초타워",
                "06627",
                now,
                now
        ));
    }

    @Test
    @DisplayName("고객 리스트 페이지를 불러 올 수 있다.")
    public void getCustomersPage() throws Exception {
        given(this.customerService.getAll()).willReturn(customers);
        this.mockMvc.perform(get("/customers")).andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", customers));
    }

    @Test
    @DisplayName("이름이 일치하는 고객 리스트 페이지를 불러 올 수 있다.")
    public void getCustomersPageByName() throws Exception {
        var filteredCustomers = customers.stream()
                .filter(o -> "창호".equals(o.getName()))
                .toList();
        given(this.customerService.getByName("창호")).willReturn(filteredCustomers);
        this.mockMvc.perform(get("/customers/{name}", "창호"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", filteredCustomers));
    }
}