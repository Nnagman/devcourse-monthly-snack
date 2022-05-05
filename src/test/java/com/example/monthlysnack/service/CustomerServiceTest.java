package com.example.monthlysnack.service;

import com.example.monthlysnack.exception.CustomerException;
import com.example.monthlysnack.model.Customer;
import com.example.monthlysnack.model.CustomerDto;
import com.example.monthlysnack.model.CustomerDto.RegisterCustomer;
import com.example.monthlysnack.model.CustomerDto.UpdateCustomer;
import com.example.monthlysnack.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CustomerServiceTest {
    @InjectMocks
    CustomerRepository customerRepository;

    @Mock
    CustomerService customerService;

    RegisterCustomer registerCustomer;
    UpdateCustomer updateCustomer;
    Customer customer;
    List<Customer> customerList = new ArrayList<>();

    @BeforeEach
    void setup() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);

        registerCustomer = new RegisterCustomer(
                "창호",
                "devcourser@grepp.com",
                "대륭서초타워",
                "06627"
        );

        updateCustomer = new UpdateCustomer(
                UUID.randomUUID(),
                "창호",
                "대륭서초타워",
                "06627"
        );
    }

    @Test
    @DisplayName("고객을 등록 할 수 있다.")
    void insert() {
        given(customerRepository.insert(any()))
                .willReturn(Optional.of(customer));

        var insertedCustomer = customerService.insert(registerCustomer);

        assertThat(insertedCustomer.isPresent()).isTrue();
        assertThat(insertedCustomer.get().getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("중복된 정보를 가진 고객을 등록 할 수 없다.")
    void insertFailWithDuplicateKey() {
        given(customerRepository.insert(any()))
                .willThrow(DuplicateKeyException.class);
        assertThrows(DuplicateKeyException.class,
                () -> customerService.insert(registerCustomer));
    }

    @Test
    @DisplayName("id로 고객을 찾을 수 있다.")
    void getById() {
        var customerId = customer.getCustomerId();
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        var selectedCustomer = customerService.getById(customerId);

        assertThat(selectedCustomer.isPresent()).isTrue();
        assertThat(selectedCustomer.get().getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("해당 id를 가진 고객을 찾을 수 없다.")
    void getByIdFindNothing() {
        var customerId = customer.getCustomerId();
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());
        assertThrows(CustomerException.CustomerNotFoundException.class, () -> customerService.getById(customerId));
    }

    @Test
    @DisplayName("특정 이름을 가진 고객들을 찾을 수 있다.")
    void getByName() {
        customerList.add(registerCustomer.getCustomer());
        var name = "창호";
        given(customerRepository.findByName(name)).willReturn(customerList);

        var customers = customerService.getByName(name);

        assertThat(customers.size()).isEqualTo(customerList.size());
        for (Customer selectedCustomer : customers) {
            assertThat(selectedCustomer.getName()).isEqualTo(name);
        }
    }

    @Test
    @DisplayName("특정 이름을 가진 고객은 없다.")
    void getByNameNotFound() {
        var name = "test";
        given(customerRepository.findByName(name)).willReturn(List.of());

        var customers = customerService.getByName(name);

        assertThat(customers.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("고객 정보를 수정 할 수 있다.")
    void update() {
        var customerId = customer.getCustomerId();
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        var selectedCustomer = customerService.getById(customerId);

        assertThat(selectedCustomer.isPresent()).isTrue();
        assertThat(selectedCustomer.get().getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("고객 정보를 업데이트 할 수 없다.")
    void updateFail() {
        var customerId = customer.getCustomerId();
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());
        assertThrows(CustomerException.CustomerNotFoundException.class, () -> customerService.getById(customerId));
    }
}