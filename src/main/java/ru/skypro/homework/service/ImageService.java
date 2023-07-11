package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
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

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 *
 Класс `ImageService` представляет собой сервис для работы с изображениями в приложении.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public byte[] saveImage(Integer id, MultipartFile file) throws IOException {
        //Метод `saveImage` сохраняет изображение для указанного объявления. Он принимает идентификатор объявления и
        // объект `MultipartFile`, содержащий изображение, в качестве параметров.
        log.info("Was invoked method to upload photo to ads with id {}", id);
        if (file.isEmpty()) {
            //Метод выбрасывает исключение `IOException`, если происходит ошибка при сохранении изображения.
            throw new IllegalArgumentException("File is empty");
        }
        //Внутри метода создается новый объект `Image`, заполняются его поля (идентификатор, связь с объявлением,
        // предварительное изображение, тип медиа, размер файла и путь к файлу) и сохраняется в репозитории.
        Ads ads = adsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ads not found"));
        Image imageToSave = new Image();
        imageToSave.setId(id);
        imageToSave.setAds(ads);
        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        imageToSave.setFilePath(file.getOriginalFilename());
//        imageToSave.setUser(userRepository.findById(ads.getAuthorId().getId()).get());
        System.out.println(ads);
        imageRepository.save(imageToSave);
        return imageToSave.getPreview();
    }

    //сохраняет аватар пользователя.
    public byte[] saveAvatar(String email, MultipartFile file) throws IOException {
        Integer id = userRepository.findUserByUsername(email).getId();
        log.info("Was invoked method to upload photo to user with id {}", id);
        if (file.isEmpty()) {
            //Затем метод проверяет, является ли загруженный файл `file` пустым, и если да, выбрасывает исключение
            throw new IllegalArgumentException("File is empty");
        }
        if (!userRepository.existsById(id)) {
            //Затем метод проверяет, существует ли пользователь с указанным `id` в репозитории `userRepository`,
            // и если нет, выбрасывает исключение
            throw new IllegalArgumentException("User not found");
        }
        User user = userRepository.findById(id).get();
        //Затем метод создает новый объект `Image`, заполняет его данными и сохраняет его в репозитории `imageRepository`.
        Image imageToSave = new Image();
        //Данные включают `id` пользователя, объект `User`,
        // байтовое представление файла `preview`, тип контента файла `mediaType`,
        // размер файла `fileSize` и путь к файлу `filePath`.
        imageToSave.setId(id);
        imageToSave.setUser(user);
        imageToSave.setPreview(file.getBytes());
        imageToSave.setMediaType(file.getContentType());
        imageToSave.setFileSize(file.getSize());
        imageToSave.setFilePath(file.getOriginalFilename());
        imageRepository.save(imageToSave);
        //Наконец, метод возвращает байтовое представление сохраненного файла изображения с помощью метода
        // `getPreview()` объекта `Image`.
        return imageToSave.getPreview();
    }

    //возвращает аватар пользователя с указанным `id`.
    public byte[] getAvatar(int id) {
        log.info("Was invoked method to get avatar from user with id {}", id);
        ////Сначала метод находит запись об аватаре в репозитории `imageRepository` с использованием метода `findById(id)`.
        Image image = imageRepository.findById(id).get();
        if (isEmpty(image)) {
//Если запись не найдена, выбрасывается исключение `IllegalArgumentException` с сообщением "Avatar not found".
            throw new IllegalArgumentException("Avatar not found");
        }
        // В противном случае, метод возвращает превью изображения (image.getPreview()) в виде массива байтов.
        return imageRepository.findById(id).get().getPreview();
    }

    //Этот код представляет собой метод для получения изображения для объявления с заданным id.
    public byte[] getImage(int id) {
        // В первой строке метода выводится информационное сообщение с использованием логгера,
        // чтобы указать о вызове метода и передать значение id.
        log.info("Was invoked method to get image from ads with id {}", id);
        //Затем выполняется запрос к репозиторию изображений (imageRepository) для поиска объекта Image,
        // связанного с объявлением, у которого id соответствует заданному id.
        Image image = imageRepository.findImageByAds_Id(id);
        if (isEmpty(image)) {
            //Если найденное изображение пустое (null), выбрасывается исключение IllegalArgumentException с
            // сообщением "Image not found" (Изображение не найдено).
            throw new IllegalArgumentException("Image not found");
        }
        // В противном случае, из объекта Image, найденного в предыдущем шаге, извлекается поле preview
        // (предварительный просмотр изображения) и возвращается в виде массива байтов (byte[]).
        return imageRepository.findById(id).get().getPreview();
    }
}
