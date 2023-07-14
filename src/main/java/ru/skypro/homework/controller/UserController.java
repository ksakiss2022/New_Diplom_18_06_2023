package ru.skypro.homework.controller;

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
    //Используется для установки нового пароля пользователю.
    @PostMapping("/set_password")
    public ResponseEntity<NewPasswordDto> setPassword(@RequestBody NewPasswordDto newPassword,
                                                      Authentication authentication) {
        log.info("Set password: " + newPassword);
        Optional<UserDto> user = userService.getUser(authentication.getName());
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (authService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok(new NewPasswordDto());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    //Возвращает информацию о текущем аутентифицированном пользователе.
    @GetMapping("/me")
    public ResponseEntity<Optional<UserDto>> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} authenticated", authentication.getName());
        Optional<UserDto> user = userService.getUser(authentication.getName());
        return ResponseEntity.ok(user);
    }
    //Этот метод для обновления данных пользователя и возвращает обновленный объект пользователя в ответе.
    @PatchMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterReq> updateUser(@RequestBody RegisterReq user, Authentication authentication) {
        RegisterReq updatedUser = userService.update(user, authentication);
        log.info("User {} update", authentication.getName());
        return ResponseEntity.ok(updatedUser);
    }

    //Этот метод, который содержит логику по сохранению аватаров пользователей.
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} update avatar", authentication.getName());
        imageService.saveAvatar(authentication.getName(), image);
        return ResponseEntity.status(200).build();
    }
    //Метод, который возвращает изображение (аватар).
    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") int id) throws IOException {
        log.info("Get avatar from user with id " + id);
        return ResponseEntity.ok(imageService.getAvatar(id));
    }
}