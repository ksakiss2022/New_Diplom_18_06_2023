package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Класс `AdsController` является контроллером веб-приложения и отвечает за обработку HTTP-запросов,
 * связанных с рекламными объявлениями.
 */
@Slf4j
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    private final AdsService adsService;
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<ResponseWrapper<AdsDto>> getAllAds(@RequestParam(required = false) String title) {
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getAllAds(title));
        return ResponseEntity.ok(ads);
    }

    // Метод `addAd` предназначен для добавления рекламного объявления.
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(Authentication authentication,
                                        @RequestPart("image") MultipartFile image,
                                        @RequestPart("properties") AdsDto properties) throws IOException {
        log.info("Add ad: " + properties);
        return ResponseEntity.ok(adsService.addAd(properties, image, authentication));
    }

    //для получения полных данных об объявлении по его идентификатору.
    @GetMapping("/{id}")
    public ResponseEntity<AdsDtoFull> getAds(@Parameter(description = "Id объявления") @PathVariable Integer id) {
        log.info("Get ads: " + id);
        return ResponseEntity.ok(adsService.getAds(id));
    }

    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAds(#id).email == authentication.principal.username")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@Parameter @PathVariable Integer id) {
        boolean result = adsService.removeAd(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Метод выполняет PATCH-запрос на обновление данных объявления.
    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAds(#id).email == authentication.principal.username")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@RequestBody AdsDto ads, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateAds(ads, id));
    }

    //Этот метод выполняет GET-запрос для получения списка всех объявлений авторизованного пользователя.
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<AdsDto>> getMe(@NotNull Authentication authentication) {
        log.info("Get me: " + authentication.getName());
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getMe(authentication.getName()));
        return ResponseEntity.ok(ads);
    }

    //Этот метод выполняет PATCH-запрос на обновление изображения объявления с указанным идентификатором.
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateImage(id, image));
    }

    //Этот метод выполняет GET-запрос для получения изображения объявления с указанным идентификатором.
    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws IOException {
        log.info("Get image from ads with id " + id);
        return ResponseEntity.ok(imageService.getImage(id));
    }
}