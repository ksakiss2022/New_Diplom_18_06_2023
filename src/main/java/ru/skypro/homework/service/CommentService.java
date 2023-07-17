package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;

import java.io.IOException;
import java.util.Collection;

/**
 * Класс `CommentService` является интерфейсом, который определяет методы для работы с комментариями.
 */
@Service
public interface CommentService {

    //этот метод возвращает все комментарии.
    Iterable<CommentDto> getComments();

    // этот метод возвращает комментарии с указанным идентификатором.
    Collection<CommentDto> getComments(Integer id);

    // этот метод добавляет комментарий к объекту с указанным идентификатором.
    CommentDto addComment(Integer id, CommentDto commentDto, Authentication authentication) throws IOException;

    // этот метод удаляет комментарий с указанными идентификаторами.
    boolean deleteComment(Integer adId, Integer id);

    // этот метод обновляет существующий комментарий.
    CommentDto updateComment(Integer adId, CommentDto commentDto, Integer id, Authentication authentication) throws IOException;
}
