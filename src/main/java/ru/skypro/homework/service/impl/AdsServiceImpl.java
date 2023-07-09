package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Класс AdsServiceImpl является реализацией интерфейса AdsService. Он имеет поля типа AdsRepository,
 * ImageService, UserRepository и AdsMapper, которые используются для доступа к данным объявлений,
 * работы с изображениями, доступа к данным пользователей и маппинга объектов объявлений.
 */
@Service
@Transactional
@Slf4j
@RequiredArgsConstructor


public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final AdsMapper adsMapper;

    @Override
    //метода getAllAds(String title) позволяет получить коллекцию объявлений.
    public Collection<AdsDto> getAllAds(String title) {
        //Метод начинается с проверки, является ли переданный заголовок (title) пустым. Если он не пустой,
        // выполняется поиск объявлений по подобию (с помощью метода findByTitleLike(title) у adsRepository),
        // который возвращает коллекцию объявлений, чьи заголовки содержат указанную подстроку в title.
        if (!isEmpty(title)) {
            Collection<Ads> ads = adsRepository.findByTitleLike(title);
            log.info("Get ads with title: " + title);
            //Затем запись о получении объявлений с указанным title заносится в журнал с помощью метода log.info().
            // Результат поиска преобразуется из коллекции Ads в коллекцию AdsDto с помощью метода
            // adsMapper.adsCollectionToAdsDto() и возвращается.
            return adsMapper.adsCollectionToAdsDto(ads);
        }
        Collection<Ads> ads = adsRepository.findAll();
        //Если заголовок пустой, то выполняется поиск всех объявлений с помощью метода findAll() у adsRepository,
        // возвращающего коллекцию всех объявлений.
        log.info("Get all ads: " + ads);
        // Запись о получении всех объявлений заносится в журнал, а результат поиска преобразуется в коллекцию AdsDto
        // и возвращается.
        return adsMapper.adsCollectionToAdsDto(ads);
    }

    @Override
    //Метод addAd(AdsDto adsDto, MultipartFile image, Authentication authentication) добавляет новое объявление в базу данных.
    public AdsDto addAd(AdsDto adsDto, MultipartFile image, Authentication authentication) throws IOException {
        //Сначала выполняется преобразование объекта adsDto в объект Ads с помощью метода adsMapper.adsDtoToAds()
        Ads newAds = adsMapper.adsDtoToAds(adsDto);
        //затем устанавливается id автора объявления (полученный по аутентификации пользователя) и сохраняется новое
        // объявление в adsRepository с помощью метода save().
        newAds.setAuthorId(userRepository.findUserByUsername(authentication.getName()));
        adsRepository.save(newAds);
        //Затем выполняется логирование с помощью log.info(), где записывается информация о сохранении объявления newAds.
        log.info("Save ads: " + newAds);
        if (image != null) {
            //Затем выполняется проверка наличия файла изображения (image).
            imageService.saveImage(newAds.getAuthorId().getId(), image);
            // Если файл не равен null, используется сервис imageService для сохранения изображения,
            // связанного с объявлением, с помощью метода saveImage().
            log.info("Photo has been saved");
            //Запись о том, что фотография была сохранена, также заносится в журнал с помощью log.info().
        } else {
            throw new IOException("Photo not found");
            //Если файл image равен null, генерируется исключение IOException с сообщением "Photo not found".
        }
        //В конце метод возвращает объект AdsDto, который получается из объекта newAds с помощью метода adsMapper.adsToAdsDto().
        return adsMapper.adsToAdsDto(newAds);
    }

    @Override
    //Метод `getAds(Integer id)` используется для получения полной информации об объявлении по его ID.
    public AdsDtoFull getAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(
                // Если объявление не найдено, генерируется исключение `IllegalArgumentException`
                // с сообщением "Ads not found".
                () -> new IllegalArgumentException("Ads not found"));
        log.info("Get ads: " + ads);
        //Затем метод выполняет запись в лог с использованием `log.info()`,
        // где указывается информация о получении объявления.
        return adsMapper.adsToAdsDtoFull(ads);
        // Для преобразования объявления в объект `AdsDtoFull` используется метод `adsMapper.adsToAdsDtoFull()`.
        // Результат возвращается.
    }

    //    @Override
//    //Метод `removeAd(Integer id)` используется для удаления объявления по его ID.
//    public boolean removeAd(Integer id) {
//        Optional<Ads> adOptional = adsRepository.findById(id);
//        log.info("Delete ads: " + adOptional);
//        if (adOptional.isEmpty()) {
//            log.info("Ad not found");
//            return false;
//        }
//
//        Ads adsToDelete = adsRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("Ads not found"));
//        String filePath = adsToDelete.getImage().getFilePath();
//        File fileToDelete = new File(filePath);
//        if (fileToDelete.exists()) {
//            fileToDelete.delete();
//        }
//
//        adsRepository.deleteById(id);
//        return true;
//    }
    @Override
    // Метод removeAd(Integer id) используется для удаления объявления по его ID.
    public boolean removeAd(Integer id) {
        // Проверка существования объявления по ID
        Optional<Ads> adOptional = adsRepository.findById(id);
        if (adOptional.isEmpty()) {
            // Если объявление не существует, возвращаем false
            return false;
        }

        // Получение объявления из базы данных
        Ads ad = adOptional.get();

        // Проверка, является ли текущий авторизованный пользователь владельцем объявления
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentAuthorId = authentication.getName();
        if (!ad.getAuthorId().equals(currentAuthorId)) {
            // Если текущий пользователь не является владельцем объявления, возвращаем false
            return false;
        }

        // Удаление файла изображения
        String filePath = ad.getImage().getFilePath();
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
        // Удаление объявления из базы данных
        adsRepository.deleteById(id);

        return true;
    }

    @Override
    //Этот код обновляет объявление (Ads) по заданному id с использованием данных из объекта adsDto.
    public AdsDto updateAds(AdsDto adsDto, Integer id) {
        //Сначала он преобразует adsDto в объект Ads с помощью adsMapper.
        Ads ads = adsMapper.adsDtoToAds(adsDto);
        log.info("Update ads: " + ads);
        //Затем он сохраняет объект Ads в репозитории adsRepository и возвращает результат обратно в виде объекта AdsDto.
        return adsMapper.adsToAdsDto(adsRepository.save(ads));
    }

    @Override
    //Кроме того, этот метод получает список объявлений (Ads) для заданного email.
    public Collection<AdsDto> getMe(String email) {
        log.info("Get ads: " + email);
        //Сначала он находит пользователя (Author) в репозитории userRepository по заданному email.
        User author = userRepository.findUserByUsername(email);
        //Затем он находит все объявления, которые принадлежат этому пользователю,
        // с использованием adsRepository.findAllByAuthorId(author).
        Collection<Ads> ads = adsRepository.findAllByAuthorId(author);
        //Затем он преобразует список объявлений в список AdsDto с помощью adsMapper и возвращает результат.
        Collection<AdsDto> adsDto = adsMapper.adsCollectionToAdsDto(ads);
        log.info("Found ads: " + adsDto);
        return adsDto;
    }

    @Override
    //Этот метод обновляет изображение объявления (Ads) с заданным id.
    public byte[] updateImage(Integer id, MultipartFile image) throws IOException {
        // Он получает файл изображения в виде параметра MultipartFile и вызывает
        // метод saveImage в imageService, передавая ему id объявления и файл изображения.
        log.info("Update image: " + id);
        imageService.saveImage(id, image);
        log.info("Photo have been saved");
        //Затем он возвращает байтовое представление сохраненного файла изображения с помощью метода getBytes().
        return image.getBytes();
    }
    @Override
    @PreAuthorize("isAnonymous()")
    public Collection<AdsDto> getAllAdsForAnonymous(String title) {
        if (!StringUtils.isEmpty(title)) {
            Collection<Ads> ads = adsRepository.findByTitleLike(title);
            log.info("Get ads with title: " + title);
            return adsMapper.adsCollectionToAdsDto(ads);
        }
        Collection<Ads> ads = adsRepository.findAll();
        log.info("Get all ads: " + ads);
        return adsMapper.adsCollectionToAdsDto(new ArrayList<>(ads));
    }
}
