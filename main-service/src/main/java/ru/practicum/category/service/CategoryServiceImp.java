package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.ConflictResource;
import ru.practicum.exception.NotFoundResource;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository; // Добавляем для проверки связанных событий

    /**
     * Получение категорий.
     *
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     * @return список категорий
     */
    @Override
    public List<CategoryDto> getAll(int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable).stream()
                .map(CategoryDto::mapFromCategory)
                .toList();
    }

    /**
     * Получение информации о категории.
     *
     * @param catId id категории
     * @return данные категории
     */
    @Override
    public CategoryDto get(long catId) {
        Category category = getCategoryById(catId);
        return CategoryDto.mapFromCategory(category);
    }

    /**
     * Получение сущности категории по ID.
     *
     * @param catId id категории
     * @return сущность категории
     */
    @Override
    public Category getCategoryById(long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundResource("Категория с id=" + catId + " не найдена"));
    }

    /**
     * Создание категории.
     *
     * @param categoryDto данные категории
     * @return созданная категория
     */
    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto categoryDto) {
        // Проверка на уникальность имени категории
        categoryRepository.findByNameContainingIgnoreCase(categoryDto.getName())
                .ifPresent(category -> {
                    throw new ConflictResource("Категория '" + categoryDto.getName() + "' уже существует");
                });

        Category category = categoryDto.mapToCategory();
        Category savedCategory = categoryRepository.save(category);

        return CategoryDto.mapFromCategory(savedCategory);
    }

    /**
     * Изменение категории.
     *
     * @param categoryDto данные категории
     * @return измененная категория
     */
    @Override
    @Transactional
    public CategoryDto update(CategoryDto categoryDto) {
        // Проверка существования категории
        Category existingCategory = getCategoryById(categoryDto.getId());

        // Проверка на уникальность имени (исключая текущую категорию)
        categoryRepository.findByNameContainingIgnoreCaseAndIdNotIn(categoryDto.getName(),
                        List.of(categoryDto.getId()))
                .ifPresent(category -> {
                    throw new ConflictResource("Категория '" + categoryDto.getName() + "' уже существует");
                });

        existingCategory.setName(categoryDto.getName());
        Category updatedCategory = categoryRepository.save(existingCategory);

        return CategoryDto.mapFromCategory(updatedCategory);
    }

    /**
     * Удаление категории.
     *
     * @param catId id категории
     */
    @Override
    @Transactional
    public void delete(long catId) {
        // Проверка существования категории
        Category category = getCategoryById(catId);

        // Проверка: существуют ли события, связанные с категорией
        boolean hasEvents = eventRepository.existsByCategoryId(catId);
        if (hasEvents) {
            throw new ConflictResource("Нельзя удалить категорию: существуют события, связанные с этой категорией");
        }

        categoryRepository.deleteById(catId);
    }
}