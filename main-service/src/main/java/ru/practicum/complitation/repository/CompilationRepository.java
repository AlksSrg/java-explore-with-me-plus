package ru.practicum.complitation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.complitation.model.Compilation;

import java.util.Optional;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    Optional<Compilation> findById(Long id);

    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);

    boolean existsByTitle(String title);

    @Query("SELECT c FROM Compilation c WHERE (:pinned IS NULL OR c.pinned = :pinned)")
    Page<Compilation> findAllByPinnedOptional(@Param("pinned") Boolean pinned, Pageable pageable);
}