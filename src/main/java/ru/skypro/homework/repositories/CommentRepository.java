package ru.skypro.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.homework.model.Comment;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * Класс `CommentRepository` является интерфейсом репозитория для работы с сущностью `Comment` в базе данных.
 * Он расширяет интерфейс `JpaRepository`,
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @NotNull
    List<Comment> findAll();//возвращает список всех комментариев.

    Collection<Comment> findCommentsByAds_Id(Integer id);//возвращает список комментариев, относящихся к определенному объявлению.

    void deleteById(Integer id);//удаляет комментарий по заданному идентификатору.

}