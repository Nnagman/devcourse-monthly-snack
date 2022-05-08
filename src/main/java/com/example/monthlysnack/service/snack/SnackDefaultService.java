package com.example.monthlysnack.service.snack;

import com.example.monthlysnack.exception.SnackException;
import com.example.monthlysnack.exception.SnackException.*;
import com.example.monthlysnack.message.ErrorMessage;
import com.example.monthlysnack.model.snack.Snack;
import com.example.monthlysnack.model.snack.SnackCategory;
import com.example.monthlysnack.model.snack.SnackCategoryDto;
import com.example.monthlysnack.model.snack.SnackCategoryDto.RegisterSnackCategory;
import com.example.monthlysnack.model.snack.SnackCategoryDto.UpdateSnackCategory;
import com.example.monthlysnack.model.snack.SnackDto;
import com.example.monthlysnack.model.snack.SnackDto.RegisterSnack;
import com.example.monthlysnack.model.snack.SnackDto.UpdateSnack;
import com.example.monthlysnack.repository.snack.SnackRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.monthlysnack.message.ErrorMessage.*;

@Service
public class SnackDefaultService implements SnackService {
    private final SnackRepository snackRepository;

    public SnackDefaultService(SnackRepository snackRepository) {
        this.snackRepository = snackRepository;
    }

    @Override
    public SnackCategory insertSnackCategory(RegisterSnackCategory registerSnackCategory) {
        var snackCategory =
                snackRepository.insertSnackCategory(registerSnackCategory.getSnackCategory());

        if (snackCategory.isEmpty()) {
            throw new SnackCategoryNotRegisterException(SNACK_CATEGORY_NOT_REGISTER);
        }

        return snackCategory.get();
    }

    @Override
    public Snack insertSnack(RegisterSnack registerSnack) {
        var snack =
                snackRepository.insertSnack(registerSnack.getSnack());

        if (snack.isEmpty()) {
            throw new SnackNotRegisterException(SNACK_NOT_REGISTER);
        }

        return snack.get();
    }

    @Override
    public List<Snack> getAllSnack() {
        return snackRepository.findAllSnack();
    }

    @Override
    public List<SnackCategory> getAllSnackCategory() {
        return snackRepository.findAllSnackCategory();
    }

    @Override
    public Snack getById(UUID snackId) {
        var snack = snackRepository.findById(snackId);

        if (snack.isEmpty()) {
            throw new SnackNotFoundException(SNACK_NOT_FOUND);
        }

        return snack.get();
    }

    @Override
    public Snack getByName(String name) {
        var snack = snackRepository.findByName(name);

        if (snack.isEmpty()) {
            throw new SnackNotFoundException(SNACK_NOT_FOUND);
        }

        return snack.get();
    }

    @Override
    public SnackCategory getCategoryById(UUID categoryId) {
        var snackCategory =
                snackRepository.findCategoryById(categoryId);

        if (snackCategory.isEmpty()) {
            throw new SnackCategoryNotFoundException(SNACK_CATEGORY_NOT_FOUND);
        }

        return snackCategory.get();
    }

    @Override
    public Snack updateSnack(UpdateSnack updateSnack) {
        var snack =
                snackRepository.findById(updateSnack.snackId());

        if (snack.isEmpty()) {
            throw new SnackNotFoundException(SNACK_NOT_FOUND);
        }

        snack.get().changeName(updateSnack.name());
        snack.get().changePrice(updateSnack.price());
        snack.get().changeDescription(updateSnack.description());
        snack.get().changeSnackCategoryId(updateSnack.categoryId());
        snack.get().changeUpdatedAt(LocalDateTime.now());

        var updatedSnack =
                snackRepository.updateSnack(snack.get());

        if (updatedSnack.isEmpty()) {
            throw new SnackNotUpdateException(SNACK_NOT_UPDATE);
        }

        return updatedSnack.get();
    }

    @Override
    public SnackCategory updateCategory(UpdateSnackCategory updateSnackCategory) {
        var snackCategory =
                snackRepository.findCategoryById(updateSnackCategory.snackCategoryId());

        if (snackCategory.isEmpty()) {
            throw new SnackCategoryNotFoundException(SNACK_CATEGORY_NOT_FOUND);
        }

        snackCategory.get().changeName(updateSnackCategory.snackCategoryName());
        snackCategory.get().changeUpdatedAt(LocalDateTime.now());

        var updatedSnack =
                snackRepository.updateCategory(snackCategory.get());

        if (updatedSnack.isEmpty()) {
            throw new SnackCategoryNotFoundException(SNACK_CATEGORY_NOT_FOUND);
        }

        return updatedSnack.get();
    }

    @Override
    public Snack deleteSnack(UUID snackId) {
        var snack = snackRepository.findById(snackId);

        if (snack.isEmpty()) {
            throw new SnackNotFoundException(SNACK_NOT_FOUND);
        }

        var deletedSnack
                = snackRepository.deleteSnack(snack.get());

        if (deletedSnack.isEmpty()) {
            throw new SnackNotDeleteException(SNACK_NOT_DELETE);
        }

        return deletedSnack.get();
    }

    @Override
    public SnackCategory deleteSnackCategory(UUID snackCategoryId) {
        var snackCategory
                = snackRepository.findCategoryById(snackCategoryId);

        if (snackCategory.isEmpty()) {
            throw new SnackCategoryNotFoundException(SNACK_CATEGORY_NOT_FOUND);
        }

        var deletedSnackCategory
                = snackRepository.deleteSnackCategory(snackCategory.get());

        if (deletedSnackCategory.isEmpty()) {
            throw new SnackCategoryNotDeleteException(SNACK_CATEGORY_NOT_DELETE);
        }

        return deletedSnackCategory.get();
    }
}
