package ru.skypro.homework.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
@Component
public interface UserMapper {


    @Mapping(target = "image", expression = "java(user.getAvatar() != null ? user.getAvatar().getFilePath() : null)")
    UserDto userToUserDto(User user);

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    User userDtoToUser(UserDto userDto);

}