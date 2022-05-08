package com.example.monthlysnack.controller.customer;

import com.example.monthlysnack.model.customer.Customer;
import com.example.monthlysnack.service.customer.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.monthlysnack.model.customer.CustomerDto.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerMvcController.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class SnackMvcControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

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
    void getCustomersPage() throws Exception {
        given(this.customerService.getAll()).willReturn(customers);
        this.mockMvc.perform(get("/customers")).andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", customers));
    }

    @Test
    @DisplayName("이름이 일치하는 고객 리스트 페이지를 불러 올 수 있다.")
    void getCustomersPageByName() throws Exception {
        var filteredCustomers = customers.stream()
                .filter(o -> "창호".equals(o.getName()))
                .toList();
        given(this.customerService.getByName("창호")).willReturn(filteredCustomers);
        this.mockMvc.perform(get("/customers/{name}", "창호"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", filteredCustomers));
    }

    @Test
    @DisplayName("아이디가 일치하는 고객 상세 페이지를 불러 올 수 있다.")
    void getCustomerDetailPageById() throws Exception {
        var customer = customers.get(0);
        given(this.customerService.getById(customer.getCustomerId())).willReturn(customer);
        this.mockMvc.perform(get("/customer/{id}", customer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", customer));
    }

    @Test
    @DisplayName("고객의 정보 수정 페이지를 불러 올 수 있다.")
    void getCustomerUpdatePage() throws Exception {
        var customer = customers.get(0);
        given(this.customerService.getById(customer.getCustomerId())).willReturn(customer);
        this.mockMvc.perform(get("/customer/update/{id}", customer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", customer));
    }

    @Test
    @DisplayName("고객의 정보 수정을 하고 확인 할 수 있다.")
    void getUpdateCustomer() throws Exception {
        var customer = customers.get(0);
        var updateCustomer = new UpdateCustomer(
                customer.getCustomerId(),
                customer.getName(),
                customer.getAddress(),
                customer.getPostcode()
        );
        given(this.customerService.update(updateCustomer)).willReturn(customer);
        this.mockMvc.perform(post("/customer/update")
                        .content(objectMapper.writeValueAsString(updateCustomer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customer/" + customer.getCustomerId().toString()));
    }
}