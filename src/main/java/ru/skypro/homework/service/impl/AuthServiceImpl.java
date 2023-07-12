package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

/**
 * Класс AuthServiceImpl является реализацией интерфейса AuthService. Он аннотирован аннотацией @Slf4j,
 * что означает использование библиотеки SLF4J для логирования.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserService userService;

    private final UserRepository userRepository;

    @Override
    //Метод `login` выполняет аутентификацию пользователя.
    public boolean login(String userName, String password) {
        //Он сначала проверяет, существует ли в репозитории `userRepository`
        // пользователь с заданным именем `userName`.
        if (userRepository.findUserByUsername(userName) == null) {
            log.info("Пользователь с именем {} не найден", userName);
            // Если пользователь не найден, возвращается `false`.
            return false;
        }
        //Затем метод загружает данные пользователя с использованием `UserDetailsService` `manager` и проверяет
        // соответствие введенного пароля с хешированным паролем пользователя с помощью `PasswordEncoder` `encoder`.
        UserDetails userDetails = manager.loadUserByUsername(userName);
        // Если пароль совпадает, возвращается `true`, иначе - `false`.
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    //Метод `register` выполняет регистрацию нового пользователя.
    public boolean register(RegisterReq registerReq, Role role) {
        // Сначала он проверяет, не существует ли уже пользователь с заданным именем
        // `registerReq.getUsername()` в репозитории `userRepository`.
        if (userRepository.findUserByUsername(registerReq.getUsername()) != null) {
            log.info("Пользователь с именем {} уже существует", registerReq.getUsername());
            //Если пользователь уже существует, возвращается `false`.
            return false;
        }
//Затем метод создает новый экземпляр `RegisterReq` и заполняет его данными из `registerReq`.
        RegisterReq newUser = new RegisterReq();
        newUser.setUsername(registerReq.getUsername());
        newUser.setPassword(registerReq.getPassword());
        newUser.setFirstName(registerReq.getFirstName());
        newUser.setLastName(registerReq.getLastName());
        newUser.setPhone(registerReq.getPhone());
        newUser.setRole(role);
        userService.save(newUser);
        //Затем метод сохраняет нового пользователя с использованием `userService.save(newUser)`.

        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode)
                        .password(registerReq.getPassword())
                        .username(registerReq.getUsername())
                        .roles(role.name())
                        .build());
        //Затем метод создает и сохраняет в `UserDetailsService` `manager` нового пользователя с помощью
        // `PasswordEncoder` `encoder`. Наконец, метод возвращает `true`, чтобы показать успешную регистрацию.
        return true;
    }

    @Override
    // метод `changePassword`, который изменяет пароль пользователя.
    public boolean changePassword(NewPasswordDto newPasswordDto, String userName) {
        if (manager.userExists(userName)) {
            //Сначала метод проверяет, существует ли пользователь с заданным именем `userName`, используя
            // `UserDetailsService` `manager` и метод `userExists`.
            String encodedNewPassword = encoder.encode(newPasswordDto.getNewPassword());
            //Затем метод хеширует новый пароль из объекта `newPasswordDto` с использованием `PasswordEncoder`
            // `encoder` и сохраняет его в переменную `encodedNewPassword`.
            manager.changePassword(userName, encodedNewPassword);
            //Затем метод вызывает метод `changePassword` у `UserDetailsService` `manager`,
            // передавая ему имя пользователя и хешированный новый пароль `encodedNewPassword`.
            return true;
            //Наконец, метод возвращает `true`, чтобы показать успешное изменение пароля пользователя.
        }
        //Если пользователь не найден, метод возвращает `false`.
        log.info("Пользователь с именем {} не найден", userName);
        return false;
    }
}