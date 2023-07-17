package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;

import java.security.Principal;
import java.util.Optional;

/**
 * Класс `UserService` является интерфейсом для работы с пользователями.
 */
@Service
public interface UserService {
    // этот метод возвращает информацию о пользователе с указанным именем.
    Optional<UserDto> getUser(String name);

    //этот метод обновляет информацию о пользователе.
    RegisterReq update(RegisterReq user, Principal principal);

    // метод для обновления пользователя. Он получает текущего пользователя по имени пользователя из объекта RegisterReq
    RegisterReq update(RegisterReq user);

    //этот метод сохраняет нового пользователя.
    RegisterReq save(RegisterReq newUser);

}
