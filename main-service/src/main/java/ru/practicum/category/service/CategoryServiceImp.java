package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.ConflictResource;
import ru.practicum.exception.NotFoundResource;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

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
