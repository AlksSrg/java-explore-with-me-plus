package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.ConflictResource;
import ru.practicum.exception.NotFoundResource;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Получение категорий.
     *
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора
     * @param size количество категорий в наборе
     * @return список категорий
     */
    @Override
    public List<CategoryDto> getAll(int from, int size) {
        return categoryRepository.findAll().stream()
                .sorted(Comparator.comparing(Category::getId))
                .skip(from == 0 ? 0 : from - 1)
                .limit(size)
                .map(CategoryDto::mapFromCategory)
                .toList();
    }

    /**
     * Получение инфорации о категории.
     *
     * @param catId id категории
     * @return данные категории
     */
    @Override
    public CategoryDto get(long catId) {
        Optional<Category> optionalCategory = categoryRepository.findById(catId);

        if (optionalCategory.isEmpty())
            throw new NotFoundResource("Категория %d не найдена".formatted(catId));

        return CategoryDto.mapFromCategory(optionalCategory.get());
    }

    /**
     * Создание категории.
     *
     * @param categoryDto данные категории
     * @return созданная категория
     */
    @Override
    public CategoryDto create(NewCategoryDto categoryDto) {
        if (categoryRepository.findByNameContainingIgnoreCase(categoryDto.getName()).isPresent())
            throw new ConflictResource("Категория %s уже существует".formatted(categoryDto.getName()));

        return CategoryDto.mapFromCategory(categoryRepository.save(categoryDto.mapToCategory()));
    }

    /**
     * Изменение категории.
     *
     * @param categoryDto данные категории
     * @return измененная категория
     */
    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryRepository.findById(categoryDto.getId()).isEmpty())
            throw new NotFoundResource("Категория %d не найдена".formatted(categoryDto.getId()));

        if (categoryRepository.findByNameContainingIgnoreCaseAndIdNotIn(categoryDto.getName(),
                List.of(categoryDto.getId())).isPresent())
            throw new ConflictResource("Категория %s уже существует".formatted(categoryDto.getName()));

        return CategoryDto.mapFromCategory(categoryRepository.save(categoryDto.mapToCategory()));
    }

    /**
     * Удаление категории.
     *
     * @param catId id категории
     */
    @Override
    public void delete(long catId) {
        if (categoryRepository.findById(catId).isEmpty())
            throw new NotFoundResource("Категория %d не найдена".formatted(catId));

        // TODO : реализовать проверку: 409 Существуют события, связанные с категорией(пока нет)

        categoryRepository.deleteById(catId);
    }
}
