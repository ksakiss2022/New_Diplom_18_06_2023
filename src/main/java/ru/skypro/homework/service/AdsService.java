package ru.skypro.homework.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;

import java.io.IOException;
import java.util.Collection;

/**
 * Класс public interface AdsService представляет сервис для работы с объявлениями.
 */
@Service
public interface AdsService {

    //получает список всех объявлений.
    Collection<AdsDto> getAllAds(String title);

    //добавляет новое объявление.
    AdsDto addAd(AdsDto adsDto, MultipartFile image, Authentication authentication) throws IOException;

    //получает объявление по его идентификатору.
    AdsDtoFull getAds(Integer id);

    //Удаляет объявление по его идентификатору.
    boolean removeAd(Integer id);

    //Обновляет объявление по его идентификатору.
    AdsDto updateAds(AdsDto adsDto, Integer id);

    //получает список объявлений, принадлежащих пользователю с указанным email.
    Collection<AdsDto> getMe(String email);

    //обновляет изображение для объявления по его идентификатору.
    byte[] updateImage(Integer id, MultipartFile image) throws IOException;

    @PreAuthorize("isAnonymous()")
    Collection<AdsDto> getAllAdsForAnonymous(String title);
}
