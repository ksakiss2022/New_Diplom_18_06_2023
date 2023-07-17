package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

/**
 * Этот интерфейс является репозиторием для работы с сущностью `Image` в базе данных.
 */
public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByAds(Ads ads);//возвращает изображение, связанное с заданным объявлением.

    Image findByUser(User user);// возвращает изображение, связанное с заданным пользователем.

}