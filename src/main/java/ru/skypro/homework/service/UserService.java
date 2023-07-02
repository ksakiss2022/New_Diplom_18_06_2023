package ru.skypro.homework.service;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.UserDto;

import java.security.Principal;
import java.util.Optional;


import java.util.Optional;

@Service
public interface UserService {

    Optional<UserDto> getUser(String name);

    RegisterReq update(RegisterReq user, Principal principal);

    RegisterReq update(RegisterReq user);

    RegisterReq save(RegisterReq newUser);

}
