package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.service.AdsService;

import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Класс AdsController представляет собой Rest-контроллер, который содержит операции для работы с объявлениями.
 */
@RestController
// означает, что данный класс является контроллером, который обрабатывает REST-запросы и возвращает JSON-ответы
@RequestMapping("/ads")//указывает, что все операции контроллера будут обрабатываться по пути /ads.
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")//включает поддержку CORS на уровне контроллера, что позволяет принимать
// запросы из указанного источника (по адресу http://localhost:3000).
public class AdsController {
    private final AdsService adsService;

    //операция "getAllAds" предназначена для получения списка всех объявлений. Для этого используется GET-запрос на путь
    // /ads. В ответ сервер возвращает массив объектов AdsDto в формате JSON.
    @Operation(
            operationId = "getAllAds",
            summary = "Получить все объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdsDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }, tags = "Объявления")
    @GetMapping
    public ResponseEntity<List<AdsDto>> getAllAds() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //операция "addAd" предназначена для добавления нового объявления. Для этого используется POST-запрос на путь /ads
    // с объектом типа Ads в теле запроса. В ответ сервер возвращает объект AdsDto созданного объявления.
    @Operation(
            operationId = "addAd",
            summary = "Добавить объявление",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdsDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }, tags = "Объявления")

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(@RequestPart("properties") AdsDto ads, @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adsService.addAd(ads, image));
    }

    //операция "getAds" предназначена для получения информации об определенном объявлении. Для этого нужно передать ID
// объявления в параметре URL. В ответ сервер возвращает объект AdsDto запрашиваемого объявления в формате JSON.
    @Operation(
            operationId = "getAds",
            summary = "Получить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdsDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            }, tags = "Объявления")
    @GetMapping("/{id}")
    public ResponseEntity<AdsDto> getAds(@Parameter(description = "Id объявления") @PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //операция "removeAd" предназначена для удаления определенного объявления. Для этого нужно передать ID объявления
    // в параметре URL. В ответ сервер вернет статус 204 No Content в случае успешного удаления.
    @Operation(
            operationId = "removeAd",
            summary = "Удалить объявление",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
            }, tags = "Объявления")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@Parameter(description = "Id объявления") @PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // операция "updateAds" предназначена для обновления определенного объявления. Для этого нужно передать в теле
    // запроса объект Ads с обновленными полями и ID объявления в параметрах URL. В ответ сервер вернет объект
    // AdsDto обновленного объявления.
    @Operation(
            operationId = "updateAds",
            summary = "Обновить информацию об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdsDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }, tags = "Объявления")
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(@RequestBody AdsDto ads, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(adsService.updateAds(ads, id));
    }

    @Operation(
            operationId = "getAdsMe",
            summary = "Получить объявления авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdsDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
            }, tags = "Объявления")
    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            operationId = "updateImage",
            summary = "Обновить картинку объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = AdsDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }, tags = "Объявления")

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(
            @PathVariable @Positive(message = "ID объявления должен быть положительным числом") Integer id,
            @NotNull(message = "Файл изображения не может быть пустым") @RequestParam("image") MultipartFile image)
            throws IOException {
        // Логика обработки запроса на обновление картинки объявления
        // с использованием id и MultipartFile image
        // Возвращаем ответ с обновленным изображением в виде массива байтов
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(image.getBytes());
    }
}
