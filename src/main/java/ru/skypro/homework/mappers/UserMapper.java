package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.SecurityUserDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

/**
 * класс `UserMapper` является интерфейсом с аннотацией `@Mapper(componentModel = "spring", unmappedTargetPolicy =
 * ReportingPolicy.ERROR)`, что указывает MapStruct использовать Spring для создания экземпляров маппера и
 * генерировать ошибку при обнаружении несопоставленных полей целевого класса.
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    @Mapping(target = "image", expression = "java(getImage(user))")
    UserDto userToUserDto(User user);

    default String getImage(User user) {
        if (user.getAvatar() == null) {
            return null;
        }
        return "/users/" + user.getId() + "/image";
    }

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "registerReq.role")
    @Mapping(target = "email", source = "registerReq.username")
    @Mapping(target = "firstName", source = "registerReq.firstName")
    @Mapping(target = "lastName", source = "registerReq.lastName")
    @Mapping(target = "phone", source = "registerReq.phone")
    @Mapping(target = "password", source = "registerReq.password")
    User updateUserFromRegisterReq(RegisterReq registerReq, User user);

    SecurityUserDto toSecurityDto(User user);

}
