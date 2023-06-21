package ru.skypro.homework.mappers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.UserService;

import java.util.Optional;

@Configuration
@Primary
public class AppUserMapper implements UserMapper{
    @Bean
    public UserService adsService() {
        return new UserService() {
            @Override
            public UserDto update(UserDto user, String email) {
                return null;
            }

            @Override
            public Optional<UserDto> getUser(String name) {
                return Optional.empty();
            }

            @Override
            public UserDto updateUser(UserDto user, Long id) {
                return null;
            }
        };
    }

    @Override
    public UserDto userToUserDto(User user) {
        return null;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        return null;
    }
}
