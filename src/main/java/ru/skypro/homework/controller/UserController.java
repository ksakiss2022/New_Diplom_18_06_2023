package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final ImageService imageService;

    private PasswordEncoder passwordEncoder;

    @Operation(
            operationId = "setPassword",
            summary = "Обновление пароля",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = NewPasswordDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * Метод "setPassword" является обработчиком POST-запроса по адресу "/set_password".
     * Этот метод принимает объект типа "NewPasswordDto", содержащий новый пароль,
     * и объект "Authentication", представляющий текущую аутентификацию пользователя.
     */
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPassword,
                                                      Authentication authentication) {
        log.info("Set password: " + newPassword);// Логгирует информацию о новом пароле.
        Optional<UserDto> user = userService.getUser(authentication.getName());
        // Получает пользователя по имени из сервиса userService.
        // Если пользователь не найден, возвращается ответ с кодом 404.
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Проверяет, аутентифицирован ли текущий пользователь.
        // Если неаутентифицирован, возвращается ответ с кодом 401.
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (authService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok(new NewPasswordDto());
        }
        // Если все проверки пройдены успешно, вызывается метод authService.changePassword для смены пароля.
        // Если смена пароля прошла успешно, возвращается ответ с кодом 200 и пустым объектом
        // NewPasswordDto. Если смена пароля не удалась, возвращается ответ с кодом 403.
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


    @Operation(
            operationId = "getUser",
            summary = "Получить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * Метод "getUser" является обработчиком GET-запроса по адресу "/me". Этот метод не принимает входных параметров,
     * но использует объект "Authentication" для получения информации о текущей аутентификации
     * пользователя.
     */
    @GetMapping("/me")
    public ResponseEntity<Optional<UserDto>> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Получает текущую аутентификацию пользователя из контекста безопасности (SecurityContextHolder).
        log.info("User {} authenticated", authentication.getName());
        //Логгирует информацию о том, что пользователь с именем authentication.getName() успешно аутентифицирован.
        Optional<UserDto> user = userService.getUser(authentication.getName());
        //Вызывает метод userService.getUser для получения информации о пользователе по его имени.
        return ResponseEntity.ok(user);
        // Возвращает ответ с кодом 200 и объектом Optional<UserDto> в теле ответа, содержащим информацию о пользователе.

    }

   @Operation(
            operationId = "updateUser",
            summary = "Обновить информацию об авторизованном пользователе",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = UserDto.class))
                    }),
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
   /**
    *
    Метод "updateUser" является обработчиком PATCH-запроса по адресу "/me". Этот метод принимает
    объект типа "RegisterReq", содержащий обновленную информацию о пользователе,
    и объект "Authentication", представляющий текущую аутентификацию пользователя.
    */
   @PatchMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterReq> updateUser(@RequestBody RegisterReq user,Authentication authentication) {
       // Вызывает метод userService.update для обновления информации о пользователе.
       // Передает в метод обновленный объект типа RegisterReq и объект аутентификации.
       RegisterReq updatedUser = userService.update(user, authentication);
        log.info("User {} update", authentication.getName());
        //Логгирует информацию об обновлении пользователя с именем authentication.getName().
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            operationId = "updateUserImage",
            summary = "Обновить аватар авторизованного пользователя",
            tags = {"Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    /**
     * Метод "updateUserImage" является обработчиком PATCH-запроса по адресу "/me/image".
     * Этот метод принимает
     * файл изображения пользователя в формате MultipartFile и обновляет аватар пользователя.
     */
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image, @NotNull Authentication authentication ) throws IOException {
        //Получает текущую аутентификацию пользователя из контекста безопасности (SecurityContextHolder).
        log.info("User {} update avatar", authentication.getName());
        //Логгирует информацию о том, что пользователь с именем authentication.getName() обновляет аватар.
        imageService.saveAvatar(authentication.getName(), image);
        // Вызывает метод imageService.saveAvatar для сохранения нового изображения аватара
        // пользователя. Передает в метод имя пользователя и файл изображения.
        return ResponseEntity.status(200).build();
    }

    /**
     * Метод "getImage" является обработчиком GET-запроса по адресу "/{id}/getImage".
     * Этот метод принимает параметр "id" типа int, представляющий идентификатор пользователя,
     * и возвращает изображение аватара пользователя в виде массива байтов.
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}/getImage")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws Exception{
        log.info("Get avatar from user with id " + id);
        // Логгирует информацию о получении аватара пользователя с идентификатором id.
        return ResponseEntity.ok(imageService.getAvatar(id));
        // Вызывает метод imageService.getAvatar(id) для получения изображения
        // аватара пользователя в виде массива байтов.
    }
}


