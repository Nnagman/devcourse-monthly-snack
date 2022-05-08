package com.example.monthlysnack.repository;

import com.example.monthlysnack.model.snack.Snack;
import com.example.monthlysnack.model.snack.SnackCategory;
import com.example.monthlysnack.repository.snack.SnackRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SnackJdbcRepositoryTest {
    @Autowired
    SnackRepository snackRepository;

    List<Snack> snackList = new ArrayList<>();
    SnackCategory snackCategory;

    @BeforeAll
    void setup() {
        var now = LocalDateTime.now();
        snackCategory = new SnackCategory(UUID.randomUUID(), "biscuit",
                now, now);
        snackList.add(new Snack(UUID.randomUUID(), "ginger-biscuit", 5000,
                snackCategory.getSnackCategoryId(), snackCategory.getName(), now, now));
        snackList.add(new Snack(UUID.randomUUID(), "chocolate-biscuit", 3000,
                snackCategory.getSnackCategoryId(), snackCategory.getName(), now, now));
    }

    @Test
    @Order(1)
    @DisplayName("과자 카테고리를 등록 할 수 있다.")
    void insertSnackCategory() {
        var insertSnackCategory
                = snackRepository.insertSnackCategory(snackCategory);
        assertThat(insertSnackCategory).isPresent();
        assertThat(insertSnackCategory.get().getSnackCategoryId())
                .isEqualTo(snackCategory.getSnackCategoryId());
    }

    @Test
    @Order(2)
    @DisplayName("같은 이름를 가진 카테고리를 등록 할 수 없다.")
    void insertDuplicateCategoryName() {
        var now = LocalDateTime.now();
        var newSnackCategory = new SnackCategory(UUID.randomUUID(), "biscuit",
                now, now);
        assertThrows(DuplicateKeyException.class,
                () -> snackRepository.insertSnackCategory(newSnackCategory));
    }

    @Test
    @Order(3)
    @DisplayName("같은 ID를 가진 카테고리를 등록 할 수 없다.")
    void insertDuplicateCategoryId() {
        assertThrows(DuplicateKeyException.class,
                () -> snackRepository.insertSnackCategory(snackCategory));
    }

    @Test
    @Order(4)
    @DisplayName("과자를 등록 할 수 있다.")
    void insertSnack() {
        for (Snack snack : snackList) {
            var insertSnack = snackRepository.insertSnack(snack);
            assertThat(insertSnack).isPresent();
            assertThat(snack.getSnackId()).isEqualTo(insertSnack.get().getSnackId());
        }
    }

    @Test
    @Order(5)
    @DisplayName("같은 이름를 가진 과자를 등록 할 수 없다.")
    void insertDuplicateSnackName() {
        var now = LocalDateTime.now();
        var newSnack = new Snack(UUID.randomUUID(), "ginger-biscuit", 5000,
                snackCategory.getSnackCategoryId(), snackCategory.getName(), now, now);
        assertThrows(DuplicateKeyException.class,
                () -> snackRepository.insertSnack(newSnack));
    }

    @Test
    @Order(6)
    @DisplayName("같은 ID를 가진 과자를 등록 할 수 없다.")
    void insertDuplicateSnackId() {
        assertThrows(DuplicateKeyException.class,
                () -> snackRepository.insertSnack(snackList.get(0)));
    }

    @Test
    @Order(7)
    @DisplayName("모든 과자들을 조회 할 수 있다.")
    void findAllSnack() {
        var snacks = snackRepository.findAllSnack();
        assertThat(snacks.size()).isEqualTo(snackList.size());
    }

    @Test
    @Order(8)
    @DisplayName("모든 과자 카테고리들을 조회 할 수 있다.")
    void findAllSnackCategory() {
        var snackCategories = snackRepository.findAllSnackCategory();
        assertThat(snackCategories.size()).isEqualTo(1);
    }

    @Test
    @Order(9)
    @DisplayName("ID로 과자를 검색 할 수 있다.")
    void findById() {
        for (Snack snack : snackList) {
            var selectedSnack = snackRepository.findById(snack.getSnackId());
            assertThat(selectedSnack).isPresent();
            assertThat(snack.getSnackId()).isEqualTo(selectedSnack.get().getSnackId());
        }
    }

    @Test
    @Order(10)
    @DisplayName("ID가 일치하는 과자가 없다.")
    void findByIdFail() {
        var selectedSnack = snackRepository.findById(UUID.randomUUID());
        assertThat(selectedSnack).isEmpty();
    }

    @Test
    @Order(11)
    @DisplayName("과자 정보를 수정 할 수 있다.")
    void update() {
        var editedSnack = new Snack(
                snackList.get(0).getSnackId(),
                "ginger-biscuit", 10000,
                snackCategory.getSnackCategoryId(),
                snackCategory.getName(),
                snackList.get(0).getUpdatedAt(),
                LocalDateTime.now()
        );
        var updatedSnack = snackRepository.updateSnack(editedSnack);
        assertThat(updatedSnack).isPresent();
        assertThat(updatedSnack.get().getPrice()).isEqualTo(10000);
    }

    @Test
    @Order(12)
    @DisplayName("해당 id를 가진 과자 정보를 수정 할 수 없다.")
    void updateFail() {
        var editedSnack = new Snack(
                UUID.randomUUID(),
                "ginger-biscuit", 10000,
                snackCategory.getSnackCategoryId(),
                snackCategory.getName(),
                snackList.get(0).getUpdatedAt(),
                LocalDateTime.now()
        );
        var updatedSnack = snackRepository.updateSnack(editedSnack);
        assertThat(updatedSnack).isEmpty();
    }

    @Test
    @Order(13)
    @DisplayName("해당 id를 가진 과자를 삭제 할 수 있다.")
    void deleteSnack() {
        for (Snack snack : snackList) {
            var deleteSnack = snackRepository.deleteSnack(snack);
            assertThat(deleteSnack).isPresent();
            assertThat(deleteSnack.get().getSnackId()).isEqualTo(snack.getSnackId());
        }
    }

    @Test
    @Order(14)
    @DisplayName("해당 id를 가진 과자 카테고리를 삭제 할 수 있다.")
    void deleteSnackCategory() {
        var deleteSnackCategory
                = snackRepository.deleteSnackCategory(snackCategory);
        assertThat(deleteSnackCategory).isPresent();
        assertThat(deleteSnackCategory.get().getSnackCategoryId())
                .isEqualTo(snackCategory.getSnackCategoryId());
    }
}