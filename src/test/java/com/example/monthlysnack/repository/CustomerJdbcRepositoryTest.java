package com.example.monthlysnack.repository;

import com.example.monthlysnack.model.Customer;
import com.example.monthlysnack.model.Email;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
class CustomerJdbcRepositoryTest {
    private final CustomerRepository customerRepository;

    CustomerJdbcRepositoryTest(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    List<Customer> customerList;

    @BeforeAll
    void init() {
        var now = LocalDateTime.now();
        customerList.add(new Customer(UUID.randomUUID(), "창호",
                new Email("test2@gmail.com"), "대구시", "41232", now, now));
        customerList.add(new Customer(UUID.randomUUID(), "창호",
                new Email("test2@gmail.com"), "서울", "41232", now, now));
    }

    @Test
    @Order(1)
    @DisplayName("고객을 등록 할 수 있다.")
    void insert() {
        for (Customer customer : customerList) {
            var insertedCustomer = customerRepository.insert(customer);

            assertThat(customer.getCustomerId(), is(insertedCustomer.getCustomerId()));
        }
    }

    @Test
    @Order(2)
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void findAll() {
        var customers = customerRepository.findAll();

        assertThat(customers.size(), is(customerList.size()));
    }
}