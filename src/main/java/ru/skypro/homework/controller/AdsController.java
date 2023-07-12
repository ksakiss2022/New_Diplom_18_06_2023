package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;
import ru.skypro.homework.dto.ResponseWrapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collection;

/*
 * Класс AdsController является контроллером, который отвечает за обработку HTTP-запросов,
 *  связанных с объявлениями. Он содержит различные методы, которые позволяют получать,
 * добавлять, обновлять и удалять объявления. Класс также использует AdsService и
 * ImageService для выполнения операций с объявлениями и изображениями соответственно.
 * */
@Slf4j
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class AdsController {

    private final AdsService adsService;
    private final ImageService imageService;

    @Operation(
            operationId = "getAllAds",
            summary = "Получить все объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    /**
     *- getAllAds: Метод для получения всех объявлений. Принимает необязательный параметр "title",
     * который используется для фильтрации объявлений по заголовку. Возвращает ResponseEntity,
     * содержащий список объявлений (AdsDto).
     */

//    @GetMapping("/ads")
//    public ResponseEntity<Iterable<AdsDto>> getAllAds(@RequestParam(required = false) String title) {
//        return ResponseEntity.ok(adsService.getAllAds(title));
//    }
    @GetMapping("/ads")
    public ResponseEntity<Collection<AdsDto>> getAllAds(@RequestParam(required = false) String title) {
        Collection<AdsDto> ads = adsService.getAllAdsForAnonymous(title);
        return ResponseEntity.ok(ads);
    }

    @Operation(
            operationId = "addAd",
            summary = "Добавить объявление",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }
    )
    /**
     * - addAd: Метод для добавления нового объявления. Принимает параметры аутентификации (authentication),
     *изображение (image) и свойства объявления (properties). Возвращает ResponseEntity, содержащий
     *  добавленное объявление (AdsDto).
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(Authentication authentication,
                                        @RequestPart("image") MultipartFile image,
                                        @RequestPart("properties") AdsDto properties) throws IOException {
        log.info("Add ad: " + properties);
        return ResponseEntity.ok(adsService.addAd(properties, image, authentication));
    }

    @Operation(
            operationId = "getAds",
            summary = "Получить информацию об объявлении",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * getAds: Метод для получения информации об объявлении по его ID. Принимает ID объявления (id) в качестве пути.
     * Возвращает ResponseEntity, содержащий информацию об объявлении (AdsDtoFull).
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdsDtoFull> getAds(@Parameter(description = "Id объявления") @PathVariable Integer id) {
        log.info("Get ads: " + id);
        return ResponseEntity.ok(adsService.getAds(id));
    }

    @Operation(
            operationId = "removeAd",
            summary = "Удалить объявление",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * - removeAd: Метод для удаления объявления по его ID. Принимает ID объявления (id) в качестве пути.
     * Возвращает ResponseEntity типа Void. Если объявление успешно удалено, возвращается статус NO_CONTENT.
     * Если объявление не найдено, возвращается статус NOT_FOUND.
     */

    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAds(#id).email == authentication.principal.username")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Integer id) {
        boolean result = adsService.removeAd(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            operationId = "updateAds",
            summary = "Обновить информацию об объявлении",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * - updateAds: Метод для обновления информации об объявлении. Принимает ID объявления (id) в качестве пути и
     * обновленные свойства объявления (AdsDto). Возвращает ResponseEntity, содержащий обновленную информацию об
     * объявлении (AdsDto).
     */
    @PreAuthorize("hasRole('ADMIN') or @adsServiceImpl.getAds(#id).email == authentication.principal.username")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@RequestBody AdsDto ads, @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateAds(ads, id));
    }

    @Operation(
            operationId = "getAdsMe",
            summary = "Получить объявления авторизованного пользователя",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     *
     Метод getMe:

     Этот метод позволяет получить объявления авторизованного пользователя. Принимает параметр аутентификации
     (authentication), содержащий информацию о текущем пользователе. Метод использует AdsService для получения
     объявлений пользователя и возвращает ResponseEntity, содержащий результат в виде ResponseWrapper<AdsDto>.
     ResponseWrapper используется для обертки данных и передачи дополнительной информации, такой как статус запроса.
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<AdsDto>> getMe(@NotNull Authentication authentication) {
        log.info("Get me: " + authentication.getName());
        ResponseWrapper<AdsDto> ads = new ResponseWrapper<>(adsService.getMe(authentication.getName()));
        return ResponseEntity.ok(ads);
    }

    @Operation(
            operationId = "updateImage",
            summary = "Обновить картинку объявления",
            tags = {"Объявления"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * updateImage:
     *
     * Этот метод позволяет обновить картинку объявления. Принимает ID объявления (id) в качестве пути и новое
     * изображение (image) в виде MultipartFile. Метод использует AdsService для обновления изображения объявления
     * и возвращает ResponseEntity типа byte[], содержащий обновленное изображение.
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Integer id,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateImage(id, image));
    }

    /**
     * getImage:
     * <p>
     * Этот метод позволяет получить изображение объявления по его ID. Принимает ID объявления (id) в качестве пути.
     * Метод использует ImageService для получения изображения и возвращает ResponseEntity типа byte[],
     * содержащий изображение.
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/getImage")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) {
        log.info("Get image from ads with id " + id);
        return ResponseEntity.ok(imageService.getImage(id));
    }

//    @GetMapping("/ads")
//    public ResponseEntity<Collection<AdsDto>> getAllAds1(@RequestParam(required = false) String title) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
//            Collection<AdsDto> ads = adsService.getAllAdsForAnonymous(title);
//            return ResponseEntity.ok(ads);
//        } else {
//            Collection<AdsDto> ads = adsService.getAllAds(title);
//            return ResponseEntity.ok(ads);
//        }
//    }
}
