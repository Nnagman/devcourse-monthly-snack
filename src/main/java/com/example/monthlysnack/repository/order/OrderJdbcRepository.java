package com.example.monthlysnack.repository.order;

import com.example.monthlysnack.model.order.Order;
import com.example.monthlysnack.model.order.OrderItem;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.monthlysnack.util.JdbcUtil.toLocalDateTime;
import static com.example.monthlysnack.util.JdbcUtil.toUUID;

@Repository
public class OrderJdbcRepository implements OrderRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Order> insertOrder(Order order) {
        var insertedRow = jdbcTemplate.update(
                "INSERT INTO snack_order(snack_order_id, customer_id, order_status, " +
                        "updated_at, created_at) VALUES(UUID_TO_BIN(:snackOrderId), " +
                        "UUID_TO_BIN(:customerId), :orderStatus, :updatedAt, :createdAt)",
                orderParamMap(order)
        );

        if (insertedRow == 0) {
            return Optional.empty();
        }

        return Optional.of(order);
    }

    @Override
    public Optional<OrderItem> insertOrderItem(OrderItem orderItem) {
        var insertedRow = jdbcTemplate.update(
                "INSERT INTO snack_order_item(snack_order_id, snack_id, updated_at, created_at)" +
                        " VALUES(UUID_TO_BIN(:snackOrderId), UUID_TO_BIN(:snackId), " +
                        ":updatedAt, :createdAt)",
                orderItemParamMap(orderItem)
        );

        if (insertedRow == 0) {
            return Optional.empty();
        }

        return Optional.of(orderItem);
    }

    @Override
    public List<Order> findAllOrder() {
        return jdbcTemplate.query(
                "SELECT * FROM snack_order",
                orderRowMapper);
    }

    @Override
    public List<Order> findAllOrderByCustomerId(UUID customerId) {
        return jdbcTemplate.query(
                "SELECT * FROM snack_order WHERE customer_id = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId", customerId),
                orderRowMapper);
    }

    @Override
    public Optional<Order> findOrderById(UUID snackOrderId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM snack_order WHERE snack_order_id = UUID_TO_BIN(:snackOrderId)",
                            Collections.singletonMap("snackOrderId", snackOrderId),
                            orderRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<OrderItem> findAllOrderItem(UUID snackOrderId) {
        return jdbcTemplate.query(
                "SELECT * FROM snack_order_item WHERE snack_order_id = UUID_TO_BIN(:snackOrderId)",
                Collections.singletonMap("snackOrderId", snackOrderId),
                orderItemRowMapper);
    }

    @Override
    public Optional<OrderItem> findOrderItemById(UUID orderItemId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT * FROM snack_order_item " +
                                    "WHERE snack_order_id = UUID_TO_BIN(:orderItemId)",
                            Collections.singletonMap("orderItemId", orderItemId),
                            orderItemRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Order> updateOrder(Order order) {
        var update = jdbcTemplate.update(
                "UPDATE  snack_order SET order_status = :orderStatus, updated_at = :updatedAt " +
                        "WHERE snack_order_id = UUID_TO_BIN(:snackOrderId) AND " +
                        "customer_id = UUID_TO_BIN(:customerId)",
                orderParamMap(order)
        );

        if (update == 0) {
            return Optional.empty();
        }

        return Optional.of(order);
    }

    @Override
    public boolean deleteOrderById(UUID snackOrderId) {
        var update = jdbcTemplate.update(
                "DELETE FROM snack_order WHERE snack_order_id = UUID_TO_BIN(:snackOrderId)",
                Collections.singletonMap("snackOrderId", snackOrderId)
        );

        return update != 0;
    }

    @Override
    public boolean deleteAllOderItemById(UUID snackOrderId) {
        var update = jdbcTemplate.update(
                "DELETE FROM snack_order_item " +
                        "WHERE snack_order_id = UUID_TO_BIN(:snackOrderId)",
                Collections.singletonMap("snackOrderId", snackOrderId)
        );

        return update != 0;
    }

    private final RowMapper<Order> orderRowMapper = (resultSet, rowNum) -> {
        var snackOrderId =
                toUUID(resultSet.getBytes("snack_order_id"));
        var customerId = toUUID(resultSet.getBytes("customer_id"));
        var orderStatus = resultSet.getString("order_status");
        var updatedAt =
                toLocalDateTime(resultSet.getTimestamp("updated_at"));
        var createdAt =
                toLocalDateTime(resultSet.getTimestamp("created_at"));

        return new Order(snackOrderId, customerId, orderStatus, updatedAt, createdAt);
    };

    private final RowMapper<OrderItem> orderItemRowMapper = (resultSet, rowNum) -> {
        var snackOrderId =
                toUUID(resultSet.getBytes("snack_order_id"));
        var snackId =
                toUUID(resultSet.getBytes("snack_id"));
        var updatedAt =
                toLocalDateTime(resultSet.getTimestamp("updated_at"));
        var createdAt =
                toLocalDateTime(resultSet.getTimestamp("created_at"));

        return new OrderItem(snackOrderId, snackId, updatedAt, createdAt);
    };

    private Map<String, Object> orderParamMap(Order order) {
        Map<String, Object> hs = new HashMap<>();
        hs.put("snackOrderId", order.getSnackOrderId().toString().getBytes());
        hs.put("customerId", order.getCustomerId().toString().getBytes());
        hs.put("orderStatus", order.getOrderStatus());
        hs.put("updatedAt", order.getUpdatedAt());
        hs.put("createdAt", order.getCreatedAt());
        return hs;
    }

    private Map<String, Object> orderItemParamMap(OrderItem orderItem) {
        Map<String, Object> hs = new HashMap<>();
        hs.put("snackOrderId", orderItem.getSnackOrderId().toString().getBytes());
        hs.put("snackId", orderItem.getSnackId().toString().getBytes());
        hs.put("updatedAt", orderItem.getUpdatedAt());
        hs.put("createdAt", orderItem.getCreatedAt());
        return hs;
    }

    private Map<String, Object> deleteOrderItemParamMap(UUID snackOrderId, UUID snackId) {
        Map<String, Object> hs = new HashMap<>();
        hs.put("snackOrderId", snackOrderId.toString().getBytes());
        hs.put("snackId", snackId.toString().getBytes());
        return hs;
    }
}
