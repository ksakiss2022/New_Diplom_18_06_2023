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


    //получает список всех объявлений. Принимает в качестве параметра строку title, которая содержит название объявления.
// Возвращает список объявлений типа AdsDto
    Collection<AdsDto> getAllAds(String title);

    //добавляет новое объявление. Принимает в качестве параметров объект AdsDto, содержащий информацию об объявлении,
    // объект MultipartFile image, содержащий изображение для объявления, и объект Authentication для аутентификации
    // пользователя. Возвращает объект AdsDto, содержащий информацию о добавленном объявлении.
    AdsDto addAd(AdsDto adsDto, MultipartFile image, Authentication authentication) throws IOException;

    //получает объявление по его идентификатору. Принимает в качестве параметра целочисленное значение id, которое
    // является идентификатором объявления. Возвращает объект AdsDtoFull, содержащий информацию о запрошенном объявлении.
    AdsDtoFull getAds(Integer id);

    //Удаляет объявление по его идентификатору. Принимает в качестве параметра целочисленное значение id, которое
    // является идентификатором объявления. Возвращает true, если объявление успешно удалено, иначе возвращает false.
    boolean removeAd(Integer id);

    //Обновляет объявление по его идентификатору. Принимает в качестве параметров объект AdsDto,
    // содержащий информацию об обновленном объявлении, и целочисленное значение id,
    // которое является идентификатором объявления. Возвращает объект AdsDto, содержащий информацию об обновленном объявлении.
    AdsDto updateAds(AdsDto adsDto, Integer id);

    //получает список объявлений, принадлежащих пользователю с указанным email. Принимает в качестве параметра строку email,
    //которая является адресом электронной почты пользователя. Возвращает список объявлений типа AdsDto,
    // принадлежащих указанному пользователю.
    Collection<AdsDto> getMe(String email);

    //обновляет изображение для объявления по его идентификатору. Принимает в качестве параметров целочисленное значение
    // id, являющееся идентификатором объявления, и объект MultipartFile image, содержащий новое изображение для объявления.
    // Возвращает массив байтов с обновленным изображением.
    byte[] updateImage(Integer id, MultipartFile image) throws IOException;

    @PreAuthorize("isAnonymous()")
    Collection<AdsDto> getAllAdsForAnonymous(String title);
}
