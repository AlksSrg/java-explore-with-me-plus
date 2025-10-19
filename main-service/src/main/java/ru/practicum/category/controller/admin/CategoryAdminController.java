package ru.practicum.category.controller.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;

@Validated
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

    /**
     * Создание категории.
     *
     * @param categoryDto данные категории
     * @return созданная категория
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody @Valid NewCategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    /**
     * Изменение категории.
     *
     * @param categoryDto данные категории
     * @return измененная категория
     */
    @PatchMapping("/{catId}")
    public CategoryDto update(@RequestBody @Valid CategoryDto categoryDto,
                              @PathVariable @Positive Long catId) {
        categoryDto.setId(catId);
        return categoryService.update(categoryDto);
    }

    /**
     * Удаление категории.
     *
     * @param catId id категории
     */
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long catId) {
        categoryService.delete(catId);
    }
}
