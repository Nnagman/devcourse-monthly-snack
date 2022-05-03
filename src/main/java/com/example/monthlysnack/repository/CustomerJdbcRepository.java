package com.example.monthlysnack.repository;

import com.example.monthlysnack.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.monthlysnack.util.JdbcUtil.*;

@Repository
public class CustomerJdbcRepository implements CustomerRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer insert(Customer customer) {
        var update = jdbcTemplate.update(
                "INSERT INTO customer(customer_id, name, email, address, " +
                        "postcode, updated_at, created_at) VALUES(UUID_TO_BIN(:customerId), " +
                        ":name, :email, :address, :postcode, :updatedAt, :createdAt)",
                toParamMap(customer));

        if (update == 0) {
            return null;
        }

        return customer;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", rowMapper);
    }

    @Override
    public Customer findById(UUID customerId) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM customer WHERE customer_id = :customerId",
                Collections.singletonMap("customerId", customerId),
                rowMapper);
    }

    @Override
    public List<Customer> findByName(String name) {
        return jdbcTemplate.query(
                "SELECT * FROM customer WHERE name = :name",
                Collections.singletonMap("name", name),
                rowMapper);
    }

    @Override
    public Customer findByEmail(String email) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM customer WHERE email = :email",
                Collections.singletonMap("email", email),
                rowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        var update = jdbcTemplate.update(
                "UPDATE customer SET name = :name, email = :email, " +
                        "address = :address, postcode = :postcode, " +
                        "updated_at = :updatedAt, created_at = :createdAt " +
                        "WHERE customer_id = UUID_TO_BIN(:customerId)",
                toParamMap(customer));

        if (update == 0) {
            return null;
        }

        return customer;
    }

    @Override
    public Customer deleteById(Customer customer) {
        var update = jdbcTemplate.update("DELETE FROM customer WHERE customer_id = :customerId",
                toParamMap(customer));

        if (update == 0) {
            return null;
        }

        return customer;
    }

    private final RowMapper<Customer> rowMapper = (resultSet, rowNum) -> {
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var name = resultSet.getString("name");
        var email = resultSet.getString("email");
        var address = resultSet.getString("address");
        var postcode = resultSet.getString("postcode");
        var createdAt = toLocalDateTime(
                resultSet.getTimestamp("createdAt"));
        var updatedAt = toLocalDateTime(
                resultSet.getTimestamp("updatedAt"));
        return new Customer(customerId, name, email, address, postcode, createdAt, updatedAt);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("customerId", customer.getCustomerId().toString().getBytes());
        hashMap.put("name", customer.getName());
        hashMap.put("address", customer.getAddress());
        hashMap.put("postcode", customer.getPostcode());
        hashMap.put("updatedAt", customer.getUpdatedAt());
        hashMap.put("createdAt", customer.getCreatedAt());
        return hashMap;
    }
}
