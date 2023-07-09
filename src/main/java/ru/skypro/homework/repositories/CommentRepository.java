package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Comment;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * Класс CommentRepository является интерфейсом и представляет собой репозиторий для работы с комментариями (Comment).
 * Он расширяет интерфейс JpaRepository и указывает класс Comment в качестве сущности, а тип данных Integer в качестве
 * идентификатора.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @NotNull
    List<Comment> findAll();//возвращает все комментарии.

    Collection<Comment> findCommentsByAds_Id(Integer id);//возвращает комментарии по заданному идентификатору объявления.

    void deleteById(Integer id);// удаляет комментарий по заданному идентификатору.

}