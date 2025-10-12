package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

public interface CategoryService {
    /**
     * Создание категории.
     *
     * @param categoryDto данные категории
     * @return созданная категория
     */
    CategoryDto create(NewCategoryDto categoryDto);

    /**
     * Изменение категории.
     *
     * @param categoryDto данные категории
     * @return измененная категория
     */
    CategoryDto update(CategoryDto categoryDto);

    /**
     * Удаление категории.
     *
     * @param catId id категории
     */
    void delete(long catId);
}
