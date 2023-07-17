package ru.skypro.homework.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Класс `AdsRepository` представляет собой интерфейс репозитория для работы с сущностью `Ads` в базе данных.
 * Он расширяет интерфейс `JpaRepository`,
 */
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    //возвращает список всех объявлений.
    @NonNull
    List<Ads> findAll();

    //: возвращает объявление по заданному идентификатору
    Optional<Ads> findById(Integer id);

    //удаляет объявление по заданному идентификатору.
    void deleteById(Integer id);

    // возвращает список всех объявлений, принадлежащих определенному автору.
    Collection<Ads> findAllByAuthorId(User authorId);

    //возвращает список объявлений, у которых заголовок содержит заданную подстроку.
    Collection<Ads> findByTitleLike(String title);
}