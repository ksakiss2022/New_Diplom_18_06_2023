package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.model.User;

import java.util.Optional;

/**
 * Класс `UserRepository` является интерфейсом репозитория для работы с сущностью `User` в базе данных.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);//возвращает пользователя по заданному адресу электронной почты.

    Optional<User> findById(Integer id);//возвращает пользователя по заданному идентификатору.

    // возвращает пользователя по заданному адресу электронной почты с использованием запроса на языке SQL.
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findUserByUsername(String email);

}