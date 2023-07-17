package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
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
import java.util.Collection;
import java.util.Optional;

/**
 * Класс AdsServiceImpl представляет собой сервис для работы с объявлениями.
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


    @Override// получает все объявления из репозитория
    public Collection<AdsDto> getAllAds(String title) {
        if (StringUtils.isNotEmpty(title)) {
            Collection<Ads> ads = adsRepository.findByTitleLike(title);
            log.info("Get ads with title: " + title);
            return adsMapper.adsCollectionToAdsDto(ads);
        }
        Collection<Ads> ads = adsRepository.findAll();
        log.info("Get all ads: " + ads);
        return adsMapper.adsCollectionToAdsDto(ads);
    }

    @Override// добавляет новое объявление в репозиторий.
    public AdsDto addAd(AdsDto adsDto, MultipartFile image, Authentication authentication) throws IOException {
        Ads newAds = adsMapper.adsDtoToAds(adsDto);
        newAds.setAuthorId(userRepository.findUserByUsername(authentication.getName()));
        newAds.setDescription(adsDto.getDescription()); // Установка значения description
        adsRepository.save(newAds);
        log.info("Save ads: " + newAds);
        if (image != null) {
            imageService.saveImage(newAds.getId(), image);
            log.info("Photo has been saved");
        } else {
            throw new IOException("Photo not found");
        }
        return adsMapper.adsToAdsDto(newAds);
    }

    @Override
// получает объявление по его идентификатору из репозитория, и, если оно существует, преобразует его в объект AdsDtoFul
    public AdsDtoFull getAds(Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Ads not found"));
        log.info("Get ads: " + ads);
        return adsMapper.adsToAdsDtoFull(ads);
    }

    @Override//удаляет объявление по его идентификатору из репозитория
    public boolean removeAd(Integer id) {
        Optional<Ads> adOptional = adsRepository.findById(id);
        log.info("Delete ads: " + adOptional);
        if (adOptional.isEmpty()) {
            log.info("Ad not found");
            return false;
        }

        Ads adsToDelete = adsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Ads not found"));
        String filePath = adsToDelete.getImage().getFilePath();
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }

        adsRepository.deleteById(id);
        return true;
    }

    @Override//обновляет информацию об объявлении по его идентификатору.
    public AdsDto updateAds(AdsDto adsDto, Integer id) {
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ads not found"));
        log.info("Update ads: " + ads);
        adsMapper.updateAds(adsDto, ads);
        return adsMapper.adsToAdsDto(adsRepository.save(ads));
    }

    @Override//получает все объявления, созданные пользователем с заданным email, из репозитория.
    public Collection<AdsDto> getMe(String email) {
        log.info("Get ads: " + email);
        User author = userRepository.findUserByUsername(email);
        Collection<Ads> ads = adsRepository.findAllByAuthorId(author);
        Collection<AdsDto> adsDto = adsMapper.adsCollectionToAdsDto(ads);
        log.info("Found ads: " + adsDto);
        return adsDto;
    }

    @Override//обновляет изображение для объявления с заданным идентификатором.
    public byte[] updateImage(Integer id, MultipartFile image) throws IOException {
        log.info("Update image: " + id);
        imageService.saveImage(id, image);
        log.info("Photo have been saved");
        return image.getBytes();
    }
}