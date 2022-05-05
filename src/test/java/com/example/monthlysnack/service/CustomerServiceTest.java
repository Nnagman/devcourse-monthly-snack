package com.example.monthlysnack.service;

import com.example.monthlysnack.model.Customer;
import com.example.monthlysnack.model.CustomerDto;
import com.example.monthlysnack.model.Email;
import com.example.monthlysnack.repository.CustomerJdbcRepository;
import com.example.monthlysnack.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {
    @InjectMocks
    CustomerRepository customerRepository;

    @Mock
    CustomerService customerService;

    @BeforeAll
    void setup() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    @DisplayName("고객을 등록 할 수 있다.")
    void insert() {
        CustomerDto customerDto = new CustomerDto("창호",
                "nnagman@gmail.com", "대구", "51232");

        var customer = new Customer(
                UUID.randomUUID(), customerDto.name(),
                new Email(customerDto.emailAddress()), customerDto.address(),
                customerDto.postCode(), LocalDateTime.now(), LocalDateTime.now()
        );

        given(customerRepository.insert(any())).willReturn(Optional.of(customer));

        var insertedCustomer = customerService.insert(customerDto);

        assertThat(insertedCustomer.isPresent()).isTrue();
        assertThat(insertedCustomer.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }
}