package com.example.monthlysnack.repository;

import com.example.monthlysnack.model.customer.Customer;
import com.example.monthlysnack.repository.customer.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerJdbcRepositoryTest {
    @Autowired
    CustomerRepository customerRepository;

    List<Customer> customerList = new ArrayList<>();

    @BeforeAll
    void setup() {
        var now = LocalDateTime.now();
        customerList.add(new Customer(UUID.randomUUID(), "창호",
                "test1@gmail.com", "대구시", "41232", now, now));
        customerList.add(new Customer(UUID.randomUUID(), "창호우",
                "test1@gmail.com", "서울", "41232", now, now));
    }

    @Test
    @Order(1)
    @DisplayName("고객을 등록 할 수 있다.")
    void insert() {
        for (Customer customer : customerList) {
            var insertedCustomer = customerRepository.insert(customer);
            assertThat(insertedCustomer).isPresent();
            assertThat(customer.getCustomerId()).isEqualTo(insertedCustomer.get().getCustomerId());
        }
    }

    @Test
    @Order(2)
    @DisplayName("같은 ID를 가진 고객을 등록 할 수 없다.")
    void insertDuplicateId() {
        for (Customer customer : customerList) {
            assertThrows(DuplicateKeyException.class,
                    () -> customerRepository.insert(customer));
        }
    }

    @Test
    @Order(3)
    @DisplayName("같은 Email을 가진 고객을 등록 할 수 없다.")
    void insertDuplicateEmail() {
        for (Customer customer : customerList) {
            var newCustomer = new Customer(UUID.randomUUID(), customer.getName(),
                    customer.getEmail(), customer.getAddress(), customer.getPostcode(),
                    customer.getCreatedAt(), customer.getUpdatedAt());
            assertThrows(DuplicateKeyException.class, () -> customerRepository.insert(newCustomer));
        }
    }

    @Test
    @Order(4)
    @DisplayName("전체 고객을 조회 할 수 있다.")
    void findAll() {
        var customers = customerRepository.findAll();

        assertThat(customers).hasSameSizeAs(customerList);
    }

    @Test
    @Order(5)
    @DisplayName("ID로 고객을 조회 할 수 있다.")
    void findById() {
        for (Customer customer : customerList) {
            var selectedCustomer = customerRepository.findById(customer.getCustomerId());
            assertThat(selectedCustomer).isPresent();
            assertThat(customer.getCustomerId()).isEqualTo(selectedCustomer.get().getCustomerId());
        }
    }

    @Test
    @Order(6)
    @DisplayName("이름으로 고객들을 조회 할 수 있다.")
    void findByName() {
        for (Customer customer : customerList) {
            var customers
                    = customerRepository.findByName(customer.getName());
            for (Customer selectedCustomer : customers) {
                assertThat(customer.getName()).isEqualTo(selectedCustomer.getName());
            }
        }
    }

    @Test
    @Order(7)
    @DisplayName("주어진 정보를 가진 고객을 찾을 수 없다.")
    void findEmpty() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> customerRepository.findById(UUID.randomUUID()));
    }

    @Test
    @Order(8)
    @DisplayName("고객 정보를 변경 할 수 있다.")
    void update() {
        var customer = customerList.get(0);
        customer = new Customer(
                customer.getCustomerId(), "test", customer.getEmail(), customer.getAddress(),
                customer.getPostcode(), customer.getCreatedAt(), customer.getUpdatedAt()
        );

        var updatedCustomer = customerRepository.update(customer);
        assertThat(updatedCustomer).isPresent();
        assertThat(updatedCustomer.get().getName()).isEqualTo("test");
        customerRepository.update(customerList.get(0));
    }

    @Test
    @Order(9)
    @DisplayName("중복된 Email로 변경 할 수 없다.")
    void updateDuplicateEmail() {
        var customer = new Customer(
                customerList.get(0).getCustomerId(), customerList.get(0).getName(),
                customerList.get(1).getEmail(), customerList.get(0).getAddress(),
                customerList.get(0).getPostcode(), customerList.get(0).getCreatedAt(),
                customerList.get(0).getUpdatedAt()
        );

        assertThrows(DuplicateKeyException.class, () -> customerRepository.update(customer));
        customerRepository.update(customerList.get(0));
    }
}