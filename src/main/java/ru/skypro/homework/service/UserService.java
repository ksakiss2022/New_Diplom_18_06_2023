package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;

import java.security.Principal;
import java.util.Optional;

@Service
public interface UserService {

//Метод `getUser` принимает имя пользователя в качестве параметра и возвращает объект `Optional<UserDto>`,
// содержащий информацию о пользователе с указанным именем.
    Optional<UserDto> getUser(String name);

//Метод `update` обновляет информацию о пользователе на основе переданного объекта `RegisterReq` и текущего пользователя,
// представленного объектом `Principal`. Метод возвращает объект `RegisterReq` с обновленной информацией.
    RegisterReq update(RegisterReq user, Principal principal);

//Второй перегруженный метод `update` обновляет информацию о пользователе на основе переданного объекта `RegisterReq`.
// Метод возвращает объект `RegisterReq` с обновленной информацией.
    RegisterReq update(RegisterReq user);
//Метод `save` сохраняет нового пользователя на основе переданного объекта `RegisterReq` и возвращает объект
// `RegisterReq` с сохраненным пользователем.
    RegisterReq save(RegisterReq newUser);

}
