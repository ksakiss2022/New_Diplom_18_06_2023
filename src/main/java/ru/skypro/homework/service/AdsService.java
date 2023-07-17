package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;

import java.io.IOException;
import java.util.Collection;

/**
 * Интерфейс AdsService определяет методы для работы с объявлениями.
 */
@Service
public interface AdsService {
    //метод для получения всех объявлений
    Collection<AdsDto> getAllAds(String title);

    // метод для добавления объявления.
    AdsDto addAd(AdsDto adsDto, MultipartFile image, Authentication authentication) throws IOException;

    // метод для получения объявления по идентификатору.
    AdsDtoFull getAds(Integer id);

    // метод для удаления объявления.
    boolean removeAd(Integer id);

    // метод для обновления объявления.
    AdsDto updateAds(AdsDto adsDto, Integer id);

    // метод для получения всех объявлений пользователя по электронной почте.
    Collection<AdsDto> getMe(String email);

    //метод для обновления изображения объявления.
    byte[] updateImage(Integer id, MultipartFile image) throws IOException;


}
