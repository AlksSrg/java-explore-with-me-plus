package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.Category;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Поиск категории по имени.
     *
     * @param name наименование категории
     * @return найденная категория
     */
    Optional<Category> findByNameContainingIgnoreCase(String name);

    /**
     * Поиск категории по имени исключая переданные id.
     *
     * @param name наименование категории
     * @param id перечень id для исключения
     * @return найденная категория
     */
    Optional<Category> findByNameContainingIgnoreCaseAndIdNotIn(String name, Collection<Long> id);
}
