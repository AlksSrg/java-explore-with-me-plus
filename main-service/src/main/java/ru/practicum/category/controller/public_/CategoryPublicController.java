package ru.practicum.category.controller.public_;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPublicController {
    private final CategoryService categoryService;

    /**
     * Получение категорий.
     *
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     * @return список категорий
     */
    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                    @RequestParam(defaultValue = "10") @Positive int size) {
       return categoryService.getAll(from, size);
    }

    /**
     * Получение информации о категории.
     *
     * @param catId id категории
     * @return данные категории
     */
    @GetMapping("/{catId}")
    public CategoryDto get(@PathVariable @Positive long catId) {
       return categoryService.get(catId);
    }
}
