package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryService {
    /**
     * Получение категорий.
     *
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     * @return список категорий
     */
    List<CategoryDto> getAll(int from, int size);

    /**
     * Получение информации о категории.
     *
     * @param catId id категории
     * @return данные категории
     */
    CategoryDto get(long catId);

    /**
     * Получение сущности категории по ID.
     *
     * @param catId id категории
     * @return сущность категории
     */
    Category getCategoryById(long catId);

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