package com.example.monthlysnack.service;

import com.example.monthlysnack.exception.CustomerException.CustomerNotFoundException;
import com.example.monthlysnack.exception.CustomerException.CustomerNotRegisterException;
import com.example.monthlysnack.model.customer.Customer;
import com.example.monthlysnack.model.customer.CustomerDto.RegisterCustomer;
import com.example.monthlysnack.model.customer.CustomerDto.UpdateCustomer;
import com.example.monthlysnack.repository.customer.CustomerRepository;
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
class CustomerDefaultServiceTest {
    @InjectMocks
    CustomerRepository customerRepository;

    @Mock
    CustomerDefaultService customerDefaultService;

    RegisterCustomer registerCustomer;
    UpdateCustomer updateCustomer;
    Customer customer;
    List<Customer> customerList = new ArrayList<>();

    @BeforeEach
    void setup() {
        customerRepository = mock(CustomerRepository.class);
        customerDefaultService = new CustomerDefaultService(customerRepository);

        registerCustomer = new RegisterCustomer(
                "창호",
                "devcourser@grepp.com",
                "대륭서초타워",
                "06627"
        );

        var customerId = UUID.randomUUID();
        updateCustomer = new UpdateCustomer(
                customerId,
                "창호",
                "대륭서초타워",
                "06627"
        );

        var now = LocalDateTime.now();
        customer = new Customer(
                customerId,
                "창호",
                "devcourser@grepp.com",
                "대륭서초타워",
                "06627",
                now,
                now
        );
    }

    @Test
    @DisplayName("고객을 등록 할 수 있다.")
    void insert() {
        given(customerRepository.insert(any()))
                .willReturn(Optional.of(customer));

        var insertedCustomer = customerDefaultService.insert(registerCustomer);

        assertThat(insertedCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("중복된 정보를 가진 고객을 등록 할 수 없다.")
    void insertFailWithDuplicateKey() {
        given(customerRepository.insert(any()))
                .willThrow(DuplicateKeyException.class);
        assertThrows(DuplicateKeyException.class,
                () -> customerDefaultService.insert(registerCustomer));
    }

    @Test
    @DisplayName("고객 등록을 할 수 없다.")
    void insertFail() {
        given(customerRepository.insert(any())).willReturn(Optional.empty());
        assertThrows(CustomerNotRegisterException.class,
                () -> customerDefaultService.insert(registerCustomer));
    }

    @Test
    @DisplayName("id로 고객을 찾을 수 있다.")
    void getById() {
        var customerId = customer.getCustomerId();
        given(customerRepository.findById(customerId)).willReturn(Optional.of(customer));

        var selectedCustomer = customerDefaultService.getById(customerId);

        assertThat(selectedCustomer.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    @DisplayName("해당 id를 가진 고객을 찾을 수 없다.")
    void getByIdFindNothing() {
        var customerId = customer.getCustomerId();
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerDefaultService.getById(customerId));
    }

    @Test
    @DisplayName("특정 이름을 가진 고객들을 찾을 수 있다.")
    void getByName() {
        customerList.add(customer);
        var name = customer.getName();
        given(customerRepository.findByName(name)).willReturn(customerList);

        var customers = customerDefaultService.getByName(name);

        assertThat(customers).hasSameSizeAs(customerList);
        for (Customer selectedCustomer : customers) {
            assertThat(selectedCustomer.getName()).isEqualTo(name);
        }
    }

    @Test
    @DisplayName("특정 이름을 가진 고객은 없다.")
    void getByNameNotFound() {
        var name = "test";
        given(customerRepository.findByName(name)).willReturn(List.of());

        var customers = customerDefaultService.getByName(name);

        assertThat(customers).isEmpty();
    }

    @Test
    @DisplayName("고객 정보를 수정 할 수 있다.")
    void update() {
        given(customerRepository.findById(updateCustomer.customerId())).willReturn(Optional.of(customer));
        given(customerRepository.update(any())).willReturn(Optional.of(customer));

        var updatedCustomer = customerDefaultService.update(updateCustomer);

        assertThat(updatedCustomer.getCustomerId()).isEqualTo(customer.getCustomerId());
    }

    @Test
    @DisplayName("존재하지 않는 고객을 수정 할 수 없다.")
    void updateCustomerNotFound() {
        given(customerRepository.findById(updateCustomer.customerId())).willReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerDefaultService.update(updateCustomer));
    }
}