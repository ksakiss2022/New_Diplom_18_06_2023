package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
public interface AdsService {

    public Collection<AdsDto> getAllAds();

    public AdsDto addAd(AdsDto adsDto, MultipartFile image) throws IOException;

    public Optional<AdsDto> getAds(Integer id);

    Optional<AdsDto> getAds(Long id);

    public boolean removeAd(Long id);

    public AdsDto updateAds(AdsDto adsDto, Long id);

    public Collection<AdsDto> getMe(String email);

    public byte[] updateImage(Integer id, MultipartFile image) throws IOException;
}
