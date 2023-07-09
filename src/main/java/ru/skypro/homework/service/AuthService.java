package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;

/**
 *
 Класс public interface AuthService представляет сервис аутентификации и авторизации пользователей.
 */
public interface AuthService {

    boolean login(String userName, String password);//осуществляет процесс входа пользователя в систему.

    boolean register(RegisterReq registerReq, Role role);//осуществляет регистрацию нового пользователя в системе.

    boolean changePassword(NewPasswordDto newPasswordDto, String userName);//осуществляет смену пароля пользователя.
}

