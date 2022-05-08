package com.example.monthlysnack.repository.snack;

import com.example.monthlysnack.model.snack.Snack;
import com.example.monthlysnack.model.snack.SnackCategory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.example.monthlysnack.util.JdbcUtil.toLocalDateTime;
import static com.example.monthlysnack.util.JdbcUtil.toUUID;

@Repository
public class SnackJdbcRepository implements SnackRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SnackJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<SnackCategory> insertSnackCategory(SnackCategory snackCategory) {
        var insertedRow = jdbcTemplate.update(
                "INSERT INTO snack_category(snack_category_id, snack_category_name, " +
                        "updated_at, created_at) VALUES(UUID_TO_BIN(:snackCategoryId), " +
                        ":name, :updatedAt, :createdAt)",
                snackCategoryParamMap(snackCategory)
        );

        if (insertedRow == 0) {
            return Optional.empty();
        }

        return Optional.of(snackCategory);
    }

    @Override
    public Optional<Snack> insertSnack(Snack snack) {
        var insertedRow = jdbcTemplate.update(
                "INSERT INTO snack(snack_id, name, price, category_id, description, " +
                        "updated_at, created_at) VALUES (UUID_TO_BIN(:snackId), " +
                        ":name, :price, UUID_TO_BIN(:categoryId), :description, " +
                        ":updatedAt, :createdAt)",
                snackParamMap(snack)
        );

        if (insertedRow == 0) {
            return Optional.empty();
        }

        return Optional.of(snack);
    }

    @Override
    public List<Snack> findAllSnack() {
        return jdbcTemplate.query(
                "SELECT sn.snack_id, sn.name, sn.price, sn.category_id, " +
                        "sn.description, sn.updated_at, sn.created_at " +
                        "FROM snack sn JOIN snack_category sc " +
                        "on sn.category_id = sc.snack_category_id",
                snackRowMapper);
    }

    @Override
    public List<SnackCategory> findAllSnackCategory() {
        return jdbcTemplate.query(
                "SELECT * FROM snack_category",
                categoryRowMapper);
    }

    @Override
    public Optional<Snack> findById(UUID snackId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT sn.snack_id, sn.name, sn.price, sn.category_id, " +
                                    "sn.description, sn.updated_at, sn.created_at " +
                                    "FROM snack sn JOIN snack_category sc " +
                                    "ON sn.category_id = sc.snack_category_id " +
                                    "WHERE sn.snack_id = UUID_TO_BIN(:snackId)",
                            Collections.singletonMap("snackId", snackId.toString().getBytes()),
                            snackRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Snack> findByName(String name) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT sn.snack_id, sn.name, sn.price, sn.category_id, " +
                                    "sn.description, sn.updated_at, sn.created_at " +
                                    "FROM snack sn JOIN snack_category sc " +
                                    "ON sn.category_id = sc.snack_category_id " +
                                    "WHERE sn.name = :name",
                            Collections.singletonMap("name", name),
                            snackRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SnackCategory> findCategoryById(UUID categoryId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            "SELECT sn.snack_id, sn.name, sn.price, sn.category_id, " +
                                    "sn.description, sn.updated_at, sn.created_at " +
                                    "FROM snack sn JOIN snack_category sc " +
                                    "ON sn.category_id = sc.snack_category_id " +
                                    "WHERE sn.snack_id = UUID_TO_BIN(:snackId)",
                            Collections.singletonMap("categoryId", categoryId.toString().getBytes()),
                            categoryRowMapper)
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Snack> updateSnack(Snack snack) {
        var insertedRow = jdbcTemplate.update(
                "UPDATE snack SET name = :name, price = :price, " +
                        "category_id = UUID_TO_BIN(:categoryId), description = :description, " +
                        "updated_at = :updatedAt WHERE snack_id = UUID_TO_BIN(:snackId)",
                snackParamMap(snack)
        );

        if (insertedRow == 0) {
            return Optional.empty();
        }

        return Optional.of(snack);
    }

    @Override
    public Optional<SnackCategory> updateCategory(SnackCategory snackCategory) {
        var insertedRow = jdbcTemplate.update(
                "UPDATE snack_category SET snack_category_name = :name " +
                        "WHERE snack_category_id = UUID_TO_BIN(:snackCategoryId)",
                snackCategoryParamMap(snackCategory)
        );

        if (insertedRow == 0) {
            return Optional.empty();
        }

        return Optional.of(snackCategory);
    }

    @Override
    public Optional<Snack> deleteSnack(Snack snack) {
        var update = jdbcTemplate.update("DELETE FROM snack " +
                        "WHERE snack_id = UUID_TO_BIN(:snackId)",
                snackParamMap(snack));

        if (update == 0) {
            return Optional.empty();
        }

        return Optional.of(snack);
    }

    @Override
    public Optional<SnackCategory> deleteSnackCategory(SnackCategory snackCategory) {
        var update = jdbcTemplate.update("DELETE FROM snack_category " +
                        "WHERE snack_category_id = UUID_TO_BIN(:snackCategoryId)",
                snackCategoryParamMap(snackCategory));

        if (update == 0) {
            return Optional.empty();
        }

        return Optional.of(snackCategory);
    }


    private final RowMapper<Snack> snackRowMapper = (resultSet, rowNum) -> {
        var snackId = toUUID(resultSet.getBytes("snack_id"));
        var name = resultSet.getString("name");
        var email = resultSet.getInt("price");
        var categoryId = toUUID(resultSet.getBytes("category_id"));
        var description = resultSet.getString("description");
        var createdAt = toLocalDateTime(
                resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(
                resultSet.getTimestamp("updated_at"));
        return new Snack(snackId, name, email, categoryId,
                description, createdAt, updatedAt);
    };

    private final RowMapper<SnackCategory> categoryRowMapper = (resultSet, rowNum) -> {
        var categoryId = toUUID(resultSet.getBytes("snack_category_id"));
        var name = resultSet.getString("snack_category_name");
        var createdAt = toLocalDateTime(
                resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(
                resultSet.getTimestamp("updated_at"));
        return new SnackCategory(categoryId, name, createdAt, updatedAt);
    };

    private Map<String, Object> snackParamMap(Snack snack) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("snackId", snack.getSnackId().toString().getBytes());
        hashMap.put("name", snack.getName());
        hashMap.put("price", snack.getPrice());
        hashMap.put("categoryId", snack.getSnackCategoryId().toString().getBytes());
        hashMap.put("description", snack.getDescription());
        hashMap.put("updatedAt", snack.getUpdatedAt());
        hashMap.put("createdAt", snack.getCreatedAt());
        return hashMap;
    }

    private Map<String, Object> snackCategoryParamMap(SnackCategory snackCategory) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("snackCategoryId",
                snackCategory.getSnackCategoryId().toString().getBytes());
        hashMap.put("name", snackCategory.getName());
        hashMap.put("updatedAt", snackCategory.getUpdatedAt());
        hashMap.put("createdAt", snackCategory.getCreatedAt());
        return hashMap;
    }
}
