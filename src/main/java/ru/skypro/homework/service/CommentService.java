package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.model.Comment;
import java.io.IOException;
import java.util.Collection;

/**
 *Этот интерфейс предоставляет контракт для реализации классом, который будет предоставлять конкретную логику работы с комментариями.
 Код "@Service" обозначает, что данный интерфейс представляет сервисный компонент приложения,
 который обеспечивает бизнес-логику взаимодействия с комментариями.
 */
@Service
public interface CommentService {
    Comment findCommentById(Integer id);

    public Iterable<CommentDto> getComments();//возвращает все комментарии в виде объекта Iterable<CommentDto>.

    Collection<CommentDto> getComments(Integer id);//возвращает коллекцию комментариев по заданному идентификатору.

    //Добавляет комментарий к объекту с заданным идентификатором. Принимает объект CommentDto для передачи информации
    // о комментарии, а также объект Authentication для аутентификации пользователя. Метод может выбрасывать исключение IOException.
    CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) throws IOException;


    boolean deleteComment(Integer adId, Integer id);// удаляет комментарий с указанными идентификаторами. Возвращает
    // булевское значение, которое указывает, был ли комментарий успешно удален.

    //обновляет информацию в комментарии с указанными идентификаторами. Принимает объект CommentDto для передачи новой
    // информации о комментарии, а также объект Authentication для аутентификации пользователя.
    // Метод может выбрасывать исключение IOException.
    CommentDto updateComment(Integer adId, CommentDto commentDto, Integer id, Authentication authentication) throws IOException;
}
