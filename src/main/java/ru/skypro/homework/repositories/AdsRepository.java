package ru.skypro.homework.repositories;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 Класс AdsRepository является интерфейсом и представляет собой репозиторий для работы с объявлениями (Ads). Он расширяет
 интерфейс JpaRepository и указывает класс Ads в качестве сущности, а тип данных Integer в качестве идентификатора.
 */
public interface AdsRepository extends JpaRepository<Ads, Integer> {
    @NonNull
    List<Ads> findAll();//возвращает все объявления.

    Optional<Ads> findById(Integer id);//возвращает объявление по заданному идентификатору.

    void deleteById(Integer id);//удаляет объявление по заданному идентификатору.

    Collection<Ads> findAllByAuthorId(User authorId);//возвращает все объявления, в которых указан заданный автор.


    Collection<Ads> findByTitleLike(String title);//возвращает объявления, у которых заголовок содержит заданную строку
    // (поиск с учетом подстроки).

}