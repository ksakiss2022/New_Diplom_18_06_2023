package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;

/**
 * Интерфейс AuthService определяет методы для работы с аутентификацией и авторизацией пользователей.
 */
public interface AuthService {
    // метод для аутентификации пользователя.
    boolean login(String userName, String password);

    // метод для регистрации нового пользователя.
    boolean register(RegisterReq registerReq, Role role);

    // метод для изменения пароля пользователя.
    boolean changePassword(NewPasswordDto newPasswordDto, String userName);
}


