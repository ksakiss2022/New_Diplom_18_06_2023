package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;

import java.security.Principal;
import java.util.Optional;

import static ru.skypro.homework.dto.Role.USER;

/**
 *
 Класс UserServiceImpl является реализацией интерфейса UserService.
 */
@Service//Аннотация @Service обозначает, что класс является сервисным компонентом в архитектуре Spring.
@Transactional//Аннотация @Transactional указывает, что все методы класса должны выполняться в транзакционном контексте.
// Это означает, что если один из методов не выполнится успешно, то изменения, внесенные в базу данных, будут отменены.
@Slf4j//Аннотация @Slf4j используется для генерации кода для логирования с использованием библиотеки SLF4J.
@RequiredArgsConstructor//Аннотация @RequiredArgsConstructor создает конструктор, который принимает все необходимые
// зависимости, помеченные аннотациями @Autowired или @NonNull, и инициализирует поля класса.
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    //Метод `getUser` возвращает информацию о пользователе с указанным email.
    public Optional<UserDto> getUser(String email) {
        log.info("Get user: " + email);
        // Сначала метод ищет пользователя в репозитории `userRepository`
        // с использованием метода `findUserByUsername(email)`.
        User user = userRepository.findUserByUsername(email);
        UserDto userDto = userMapper.userToUserDto(user);
        //Затем метод преобразует найденного пользователя в объект `UserDto`
        // с использованием `userMapper` и возвращает его в виде `Optional<UserDto>`.
        return Optional.ofNullable(userDto);
        //Если пользователь не найден, метод возвращает пустое значение `Optional`.
    }

    @Override
    //Метод `update` обновляет информацию о пользователе с использованием данных из объекта `RegisterReq`
    public RegisterReq update(RegisterReq user, Principal principal) {
        //Сначала метод ищет текущего пользователя в репозитории `userRepository`
        // с использованием имени пользователя из объекта `Principal`.
        log.info("Update user: " + principal);
        User optionalUser = userRepository.findUserByUsername(principal.getName());
        if (optionalUser == null) {
            //Если пользователь не найден, выбрасывается исключение `IllegalArgumentException`.
            throw new IllegalArgumentException("User not found");
        }
        ModelMapper mapper = new ModelMapper();
        //метод создает экземпляр `ModelMapper`, который используется для копирования данных из объекта
        // `user` в найденного пользователя `optionalUser`
        mapper.map(user, optionalUser);
        userMapper.userToUserDto(userRepository.save(optionalUser));
        //Затем метод сохраняет обновленного пользователя в репозитории `userRepository`
        // и возвращает его в виде объекта `RegisterReq`.
        return user;
    }

    @Override
    public RegisterReq update(RegisterReq user) {//Метод `update` обновляет данные пользователя с использованием данных из объекта `RegisterReq`.
        Role role = user.getRole() == null ? USER : user.getRole();

        log.info("Update user: " + user);
        //Сначала метод создает новый экземпляр `ModelMapper`, который используется для копирования данных из объекта
        // `user` в найденного пользователя `optionalUser`.
        User optionalUser = userRepository.findUserByUsername(user.getUsername());
        if (optionalUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        //Затем метод устанавливает роль пользователя из `user.getRole()` или использует значение по умолчанию `USER`
        // в зависимости от того, задана ли роль в объекте `user`.
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, optionalUser);
        optionalUser.setRole(role);
        //метод возвращает объект `RegisterReq`, чтобы показать успешное обновление данных пользователя.
        return user;
    }

    @Override
    public RegisterReq save(RegisterReq user) {//Метод `save` сохраняет нового пользователя на основе объекта `RegisterReq`.
        log.info("Save user: " + user);
        User newUser = new User();
        //Сначала метод создает новый экземпляр `User` и копирует данные из объекта `user` с использованием `ModelMapper`.
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, newUser);
        newUser.setEmail(user.getUsername());
        // Затем метод устанавливает email нового пользователя равным имени пользователя из объекта `user`.
        userRepository.save(newUser);
        //Затем метод сохраняет нового пользователя в репозитории `userRepository`.
        return user;
        // возвращает объект `RegisterReq`, чтобы показать успешное сохранение нового пользователя.
    }

}