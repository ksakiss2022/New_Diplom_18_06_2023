package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;

    @Override// метод для получения пользователя по электронной почте.
    public Optional<UserDto> getUser(String email) {
        log.info("Get user: " + email);
        User user = userRepository.findUserByUsername(email);
        UserDto userDto = userMapper.userToUserDto(user);
        return Optional.ofNullable(userDto);
    }

    @Override//: метод для обновления пользователя.
    public RegisterReq update(RegisterReq user, Principal principal) {
        log.info("Update user: " + principal);
        User optionalUser = userRepository.findUserByUsername(principal.getName());
        if (optionalUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        User updateUser = userMapper.updateUserFromRegisterReq(user, optionalUser);
        updateUser.setRole(optionalUser.getRole());
        updateUser.setId(optionalUser.getId());
        updateUser.setEmail(optionalUser.getEmail());
        updateUser.setPassword(optionalUser.getPassword());
        userRepository.save(updateUser);
        return user;
    }

    @Override
// метод для обновления пользователя. Он получает текущего пользователя по имени пользователя из объекта RegisterReq
    public RegisterReq update(RegisterReq user) {
        Role role = user.getRole() == null ? USER : user.getRole();

        log.info("Update user: " + user);
        User optionalUser = userRepository.findUserByUsername(user.getUsername());
        if (optionalUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, optionalUser);
        optionalUser.setRole(role);
        return user;
    }

    @Override//метод для сохранения нового пользователя.
    public RegisterReq save(RegisterReq user) {
        log.info("Save user: " + user);
        User newUser = new User();
        ModelMapper mapper = new ModelMapper();
        mapper.map(user, newUser);
        newUser.setEmail(user.getUsername());
        userRepository.save(newUser);
        return user;
    }
}