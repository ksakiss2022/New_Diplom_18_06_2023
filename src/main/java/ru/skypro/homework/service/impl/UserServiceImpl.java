package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.mappers.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.UserRepository;
import ru.skypro.homework.service.UserService;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public UserDto update(UserDto user, String email) {
        log.info("Update user: " + user);
        User user1 = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        User updatedUser = userMapper.userDtoToUser(user);
        updatedUser.setId(user1.getId());
        return userMapper.userToUserDto(userRepository.save(updatedUser));
    }
    @Override
    public Optional<UserDto> getUser(String email) {
        log.info("Get user: " + email);
        return userRepository.findUserByEmailIs(email).map(userMapper::userToUserDto);
    }

    @Override
    public UserDto updateUser(UserDto user, Long id) {
        log.info("Update user: " + user);
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        return userMapper.userToUserDto(
                userRepository.save(userMapper.userDtoToUser(user))
        );
    }
}