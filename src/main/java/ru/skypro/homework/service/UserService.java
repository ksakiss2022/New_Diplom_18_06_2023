package ru.skypro.homework.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;

import java.io.IOException;
import java.util.Optional;
@Service
public interface UserService {
    public UserDto update(UserDto user, String email);

    public Optional<UserDto> getUser(String name);

    public UserDto updateUser(UserDto user, Long id);

}
