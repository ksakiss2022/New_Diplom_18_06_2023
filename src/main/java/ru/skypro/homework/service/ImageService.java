package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public ImageService(ImageRepository imageRepository, AdsRepository adsRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    public byte[] saveImage(Integer id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        Optional<Ads> ads = adsRepository.findById(Long.valueOf(id));
        Image imageToSave = new Image();
        imageToSave.setId(id);
        imageToSave.setAds(new Ads());
        imageToSave.setPreview(file.getBytes());
        imageRepository.save(imageToSave);
        return imageToSave.getPreview();
    }

    public byte[] saveAvatar(String email, MultipartFile file) throws IOException {
        Integer id = userRepository.findUserByEmailIs(email).get().getId();
        log.info("Was invoked method to upload photo to user with id {}", id);
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }
        if (!userRepository.existsById(Long.valueOf(id))) {
            throw new IllegalArgumentException("User not found");
        }
        User user = userRepository.findById(id).get();
        Image imageToSave = new Image();
        imageToSave.setId(id);
        imageToSave.setUser(user);
        imageToSave.setPreview(file.getBytes());
        imageRepository.save(imageToSave);
        return imageToSave.getPreview();
    }
}