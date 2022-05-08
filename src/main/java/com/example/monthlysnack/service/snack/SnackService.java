package com.example.monthlysnack.service.snack;

import com.example.monthlysnack.model.snack.Snack;
import com.example.monthlysnack.model.snack.SnackCategory;
import com.example.monthlysnack.model.snack.SnackCategoryDto.RegisterSnackCategory;
import com.example.monthlysnack.model.snack.SnackCategoryDto.UpdateSnackCategory;
import com.example.monthlysnack.model.snack.SnackDto.RegisterSnack;
import com.example.monthlysnack.model.snack.SnackDto.UpdateSnack;

import java.util.List;
import java.util.UUID;

public interface SnackService {
    SnackCategory insertSnackCategory(RegisterSnackCategory registerSnackCategory);

    Snack insertSnack(RegisterSnack registerSnack);

    List<Snack> getAllSnack();

    List<SnackCategory> getAllSnackCategory();

    Snack getById(UUID snackId);

    Snack getByName(String name);

    SnackCategory getCategoryById(UUID categoryId);

    Snack updateSnack(UpdateSnack updateSnack);

    SnackCategory updateCategory(UpdateSnackCategory updateSnackCategory);

    Snack deleteSnack(UUID snackId);

    SnackCategory deleteSnackCategory(UUID snackCategoryId);
}
