package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;

import java.io.IOException;
import java.util.Collection;

@Service

public interface AdsService {

    Iterable<AdsDto> getAllAds(String title);

    AdsDto addAd(AdsDto adsDto, MultipartFile image, Authentication authentication) throws IOException;

    AdsDtoFull getAds(Integer id);

    boolean removeAd(Integer id);

    AdsDto updateAds(AdsDto adsDto, Integer id);

    Collection<AdsDto> getMe(String email);

    byte[] updateImage(Integer id, MultipartFile image) throws IOException;

}
