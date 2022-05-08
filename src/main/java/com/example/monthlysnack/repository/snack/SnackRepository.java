package com.example.monthlysnack.repository.snack;

import com.example.monthlysnack.model.snack.Snack;
import com.example.monthlysnack.model.snack.SnackCategory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public interface SnackRepository {
    Optional<SnackCategory> insertSnackCategory(SnackCategory snackCategory);

    Optional<Snack> insertSnack(Snack snack);

    List<Snack> findAllSnack();

    List<SnackCategory> findAllSnackCategory();

    Optional<Snack> findById(UUID snackId);

    Optional<Snack> findByName(String name);

    Optional<SnackCategory> findCategoryById(UUID categoryId);

    Optional<Snack> updateSnack(Snack snack);

    Optional<SnackCategory> updateCategory(SnackCategory snackCategory);

    Optional<Snack> deleteSnack(Snack snack);

    Optional<SnackCategory> deleteSnackCategory(SnackCategory snackCategory);
}
