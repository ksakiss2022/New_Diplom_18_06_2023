package ru.skypro.homework.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.model.User;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    @Mapping(target = "image", expression = "java(getImage(user))")//@Mapping` указывает, что поле `image` объекта
        // `UserDto` должно быть вычислено с помощью метода `getImage(user)`.
    UserDto userToUserDto(User user);//`UserMapper` определяет метод `userToUserDto`, который выполняет преобразование
    // объекта `User` в объект `UserDto`.

    //Метод `getImage` является дополнительным методом, используемым в методе `userToUserDto` для вычисления значения
    // поля `image` объекта `UserDto`.
    default String getImage(User user) {
        if (user.getAvatar() == null) {
            return null;//Если поле `avatar` объекта `User` равно `null`, метод возвращает `null`.
        }
        return "/users/" + user.getId() + "/getImage";//В противном случае, он возвращает строку вида "/users/" +
        // user.getId() + "/getImage".
    }

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "role", ignore = true)
    User userDtoToUser(UserDto userDto);//Метод `userDtoToUserIgnoreFields` выполняет обратное преобразование,
    // преобразуя объект `UserDto` в объект `User`. Аннотация `@Mapping` с атрибутом `ignore` указывает, что поля
    // `avatar` и `role` не должны быть преобразованы при обратном преобразовании.

}
