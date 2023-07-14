package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findImageByAds_Id(Integer id);

}