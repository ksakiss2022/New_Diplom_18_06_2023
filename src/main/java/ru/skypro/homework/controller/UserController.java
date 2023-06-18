package ru.skypro.homework.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import ru.skypro.homework.dto.UserDto;
/**
 * Класс UserController является REST-контроллером, который обрабатывает запросы для работы с пользователями.
 * <p>
 * Класс содержит методы для обновления пароля, получения информации об авторизованном пользователе,
 * обновления информации и аватара пользователя.
 */
@RestController//указывает, что это класс REST-контроллера
@RequestMapping("/users")// устанавливает базовый URL-адрес для всех методов, которые обрабатываются этим контроллером.
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")//определяет адреса и порты, с которых будет доступен данный ресурс.

public class UserController {
    private final UserService userService;// внедряется UserService, который содержит бизнес-логику, связанную с пользователями.

    private PasswordEncoder passwordEncoder;
    // public UserController(UserService userService) {
    //   this.userService = userService;
    //}


    //Аннотация @Operation содержит описание каждого метода, включая название,
// описание, ответы и теги для документирования API.
    @Operation(
            //Метод setPassword(@RequestBody NewPasswordDto newPassword,
            // Authentication authentication) обрабатывает POST-запрос для обновления пароля пользователя.
            operationId = "setPassword",
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = User.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden")
            }, tags = "Пользователи")
    @PostMapping("/set_password")
    public ResponseEntity<String> setPassword(@RequestBody NewPasswordDto newPassword, Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(
            // Метод getUser(Authentication authentication) обрабатывает GET-запрос для получения информации об
            // авторизованном пользователе.
            operationId = "getUser",
            summary = "Получить информацию об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
            }, tags = "Пользователи")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(
            //Метод updateUser(@RequestBody UserDto user, Authentication authentication)
            // обрабатывает PATCH-запрос для обновления информации об авторизованном пользователе.
            operationId = "updateUser",
            summary = "Обновить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
            }
    )
//    @PatchMapping("/me")
//    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user, Authentication authentication) {
//        System.out.println("Hello");
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @PatchMapping("/me")
    public UserDto updateUser(@RequestBody UserDto user) {
        System.out.println("Hello");
        return new UserDto();
    }
    @Operation(
            //Метод updateUserImage(@RequestParam("image") MultipartFile image, Authentication authentication)
            // обрабатывает PATCH-запрос для обновления аватара авторизованного пользователя.
            operationId = "updateUserImage",
            summary = "Обновить аватар авторизованного пользователя",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
            }
    )
    @PostMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestPart("image") MultipartFile image, Authentication authentication) {
        return ResponseEntity.status(200).build();
    }
}