package ru.skypro.homework.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс Comment представляет комментарий, который имеет уникальный идентификатор (id),
 * дату и время создания (createdAt) и текст комментария (text).
 */
@Entity//Аннотация `@Entity` указывает, что данный класс является сущностью базы данных и будет отображаться в таблице `ads`.
@Getter
//Аннотация `@Getter` и `@Setter` генерирует геттеры и сеттеры для всех полей класса.
@Setter
@ToString//Аннотация `@ToString` генерирует метод `toString()`, который возвращает строковое представление объекта.
@RequiredArgsConstructor//Аннотация `@RequiredArgsConstructor` генерирует конструктор, принимающий все финальные поля класса.
@Table(name = "comments")//Аннотация `@Table` указывает имя таблицы, в которой будет храниться сущность.
public class Comment {
    @Id//Аннотация `@Id` указывает, что поле `id` является первичным ключом сущности.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//указывает, что значение поля id будет генерироваться
    // автоматически с помощью стратегии IDENTIFY.
    private Integer id;
    private String createdAt;//представляет собой строку, которая содержит дату и время создания комментария.
    private String text;//представляет собой строку, которая содержит текст комментария.

    @ManyToOne(fetch = FetchType.LAZY)//Указывает, что поля ads и authorId являются связями многие к одному с классами
    // Ads и User соответственно. Это означает, что каждый комментарий относится только к одному объявлению и только к
    // одному пользователю.
    @JoinColumn(name = "ads_id", nullable = false)//указывает, что поле ads будет связано с полем id класса Ads.
    // Кроме того, оно не может быть пустым.
    @ToString.Exclude//поля ads и authorId должны быть исключены из метода toString(), чтобы избежать рекурсивного вызова.
    private Ads ads;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)//указывает, что поле authorId будет связано с полем id класса User.
    // Кроме того, оно не может быть пустым.
    @ToString.Exclude//поля ads и authorId должны быть исключены из метода toString(), чтобы избежать рекурсивного вызова.
    private User authorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return getId() != null && Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}