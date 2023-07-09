package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.model.Comment;

import java.util.Collection;

/**
 * Класс `CommentMapper` определяет ряд методов для преобразования объектов класса `Comment` в объекты класса
 * `CommentDto` и обратно.
 */
@Mapper(componentModel = "spring")//Аннотация `@Mapper(componentModel = "spring")` указывает, что данный интерфейс
// является маппером, используемым для преобразования объектов из одного класса в другой.
public interface CommentMapper {
    //Метод `commentToCommentDto` выполняет преобразование объекта `Comment` в объект `CommentDto`.
    @Mapping(target = "pk", source = "id")
// Аннотация `@Mapping` указывает, что поле `id` объекта `Comment` должно
    // быть преобразовано в поле `pk` объекта `CommentDto`.
    CommentDto commentToCommentDto(Comment comment);

    //Метод `commentDtoToComment` выполняет обратное преобразование, преобразуя объект `CommentDto` в объект `Comment`.
    @InheritInverseConfiguration
// Аннотация `@InheritInverseConfiguration` указывает, что обратное преобразование
    // должно быть выполнено с использованием тех же настроек, что и прямое преобразование.
    Comment commentDtoToComment(CommentDto commentDto);

    //Метод `commentCollectionToCommentDto` преобразует коллекцию объектов `Comment` в коллекцию объектов `CommentDto`.
    Collection<CommentDto> commentCollectionToCommentDto(Collection<Comment> commentCollection);
    //Этот метод не требует явного преобразования полей, так как он использует тот же маппер для преобразования отдельных объектов.
}