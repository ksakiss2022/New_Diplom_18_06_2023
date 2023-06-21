package ru.skypro.homework.service;


import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.UserDto;

import java.util.Optional;
@Service
public interface UserService {
    public UserDto update(UserDto user, String email);

    public Optional<UserDto> getUser(String name);

    public UserDto updateUser(UserDto user, Long id);

}
