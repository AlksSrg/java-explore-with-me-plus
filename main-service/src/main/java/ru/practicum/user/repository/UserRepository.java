package ru.practicum.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Ищет пользователя по email.
     *
     * @param email email
     * @return пользователь
     */
    Optional<User> findByEmailContainingIgnoreCase(String email);

    /**
     * Ищет пользователей по id.
     *
     * @param ids перечень id пользователей
     * @return список пользователей
     */
    List<User> findAllByIdIn(List<Long> ids);
}
