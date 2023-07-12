package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

import java.util.Collection;
import java.util.Optional;

/**
 *
 В данном интерфейсе UserRepository мы используем JpaRepository для работы с сущностью User, поэтому он
 расширяет этот интерфейс.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);//возвращает пользователя по заданному идентификатору.
//Аннотация @Query указывает, что в данном методе будет выполнен запрос на языке SQL. Запрос выбирает всех пользователей,
// у которых значение поля email совпадает с заданным.
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    User findUserByUsername(String email);

}