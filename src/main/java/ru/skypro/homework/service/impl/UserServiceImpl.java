package ru.skypro.homework.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;

import java.security.Principal;
import java.util.Optional;
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
        User updateUser = userMapper.updateUserFromRegisterReq(user, optionalUser);
        updateUser.setRole(optionalUser.getRole());
        updateUser.setId(optionalUser.getId());
        updateUser.setEmail(optionalUser.getEmail());
        userRepository.save(updateUser);
        return user;
    }

    @Override
    public RegisterReq save(RegisterReq user) {//Метод `save` сохраняет нового пользователя на основе объекта `RegisterReq`.
        log.info("Save user: " + user);
                User newUser = new User();
        newUser = userMapper.updateUserFromRegisterReq(user, newUser);
        userRepository.save(newUser);
        return user;
    }

}