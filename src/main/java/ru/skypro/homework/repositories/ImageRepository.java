package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Image;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Object> findById(Integer id);
}