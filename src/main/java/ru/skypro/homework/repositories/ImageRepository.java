package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findByAds(Ads ads);

    Image findByUser(User user);


}