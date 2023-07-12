package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

/**
 *
 в данном интерфейсе репозитория ImageRepository мы используем JpaRepository для работы с сущностью Image,
 поэтому он расширяет этот интерфейс.
 */
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findImageByAds (Ads ads);
    Image findImageByUser (User user);
    Image findImageByAds_Id(Integer id);//возвращает изображение по заданному идентификатору объявления.

}