package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Класс `ImageService` является сервисом, который предоставляет функционал для работы с изображениями.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    @Value("${Image.dir.path}")
    private String imageDir;

    // сохраняет изображение.
    public byte[] saveImage(Integer id, MultipartFile file) throws IOException {
        log.info("Was invoked method to upload photo to ads with id {}", id);
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ads not found"));
        if (isEmpty(file)) {
            throw new IllegalArgumentException("File is empty");
        }
        if (!adsRepository.existsById(id)) {
            throw new IllegalArgumentException("Ads not found");
        }
        Image imageToSave = imageRepository.findByAds(ads);
        if (imageToSave == null) {
            imageToSave = new Image();
        }
        imageToSave.setAds(ads);
        return saveImageAndGetBytes(file, imageToSave);
    }

    //сохраняет аватар пользователя.
    public byte[] saveAvatar(String email, MultipartFile file) throws IOException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        Image imageToSave = user.getAvatar();
        if (imageToSave == null) {
            imageToSave = new Image();
        } else {
            String filePath = imageToSave.getFilePath();
            File fileToDelete = new File(filePath);
            if (fileToDelete.exists()) {
                fileToDelete.delete();
            }
        }
        imageToSave.setUser(user);
        return saveImageAndGetBytes(file, imageToSave);
    }

    // приватный метод, который сохраняет изображение и возвращает его в виде массива байт.
    private byte[] saveImageAndGetBytes(MultipartFile file, Image imageToSave) throws IOException {
        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        String path = uploadImage(UUID.randomUUID().toString(), file);
        imageToSave.setFilePath(path);
        imageRepository.save(imageToSave);
        return Files.readAllBytes(Paths.get(path));
    }

    //сохраняет файл изображения.
    public String uploadImage(String name, MultipartFile file) {
        log.debug("Was invoked method to upload image");


        String extension = Optional.ofNullable(StringUtils.getFilenameExtension(file.getOriginalFilename())).
                orElse(" ");

        Path filePath = Path.of(imageDir, name + extension);
        try {
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            Files.write(filePath, file.getBytes());
        } catch (IOException ex) {
            log.error("Picture not saved", ex);
            throw new RuntimeException("Возникла проблема при создании или записи файла или директории.");
        }
        return filePath.toString();
    }

    //возвращает аватар пользователя в виде массива байт.
    public byte[] getAvatar(int id) throws IOException {
        log.info("Was invoked method to get avatar from user with id {}", id);
        Optional<User> user = userRepository.findById(id);
        if (isEmpty(user)) {
            throw new IllegalArgumentException("User not found");
        }
        Image filePath = user.get().getAvatar();
        if (filePath == null) {
            return null;
        }
        return Files.readAllBytes(Paths.get(Objects.requireNonNull(user.get().getAvatar().getFilePath())));
    }

    // возвращает изображение объявления в виде массива байт
    public byte[] getImage(int id) throws IOException { //for AdsMapper
        log.info("Was invoked method to get image from ads with id {}", id);
        Optional<Ads> ads = adsRepository.findById(id);
        if (isEmpty(ads)) {
            throw new IllegalArgumentException("Ads not found");
        }
        return Files.readAllBytes(Paths.get(Objects.requireNonNull(ads.get().getImage().getFilePath())));
    }
}
