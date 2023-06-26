package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.mappers.AdsMapper;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@Component

public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;
    private final AdsMapper adsMapper;


    @Override
    public Collection<AdsDto> getAllAds() {
        Collection<Ads> ads = adsRepository.findAll();
        log.info("Get all ads: " + ads);
        return adsMapper.adsCollectionToAdsDto(ads);
    }

    @Override
    public AdsDto addAd(AdsDto adsDto, MultipartFile image) throws IOException {
        Ads newAds = adsMapper.adsDtoToAds(adsDto);
        log.info("Save ads: " + newAds);
        imageService.saveImage(newAds.getId(), image);
        log.info("Photo have been saved");
        return adsMapper.adsToAdsDto(newAds);
    }

    @Override
    public Optional<AdsDto> getAds(Integer id) {
        return Optional.empty();
    }


    @Override
    public Optional<AdsDto> getAds(Long id) {
        Optional<Ads> ads = adsRepository.findById(id);
        log.info("Get ads: " + ads);
        return ads.map(adsMapper::adsToAdsDto);
    }

    @Override
    public boolean removeAd(Long id) {
        log.info("Delete ads: " + id);
        adsRepository.deleteById(id);
        return false;
    }


    @Override
    public AdsDto updateAds(AdsDto adsDto, Long id) {
        Ads ads = adsMapper.adsDtoToAds(adsDto);
        log.info("Update ads: " + ads);
        return adsMapper.adsToAdsDto(adsRepository.save(ads));
    }

    @Override
    public Collection<AdsDto> getMe(String email) {
        log.info("Get ads: " + email);
        Integer authorId = userRepository.findByEmail(email).get().getId();
        Collection<Ads> ads = adsRepository.findAllByAuthorId(Long.valueOf(authorId));
        return ads.isEmpty() ? null : adsMapper.adsCollectionToAdsDto(ads);
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile image) throws IOException {
        log.info("Update image: " + id);
        imageService.saveImage(id, image);
        log.info("Photo have been saved");
        return image.getBytes();
    }

}
