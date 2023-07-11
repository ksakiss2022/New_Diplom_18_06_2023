package ru.skypro.homework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 Класс Ads представляет объявление, которое имеет уникальный идентификатор (id), цену (price), заголовок (title),
 описание (description), автора (authorId) и изображение (image).
 */
@Entity//Аннотация `@Entity` указывает, что данный класс является сущностью базы данных и будет отображаться в таблице `ads`.
@Getter
//Аннотация `@Getter` и `@Setter` генерирует геттеры и сеттеры для всех полей класса.
@Setter
@ToString//Аннотация `@ToString` генерирует метод `toString()`, который возвращает строковое представление объекта.
@RequiredArgsConstructor//Аннотация `@RequiredArgsConstructor` генерирует конструктор, принимающий все финальные поля класса.
@Table(name = "ads")//Аннотация `@Table` указывает имя таблицы, в которой будет храниться сущность.
public class Ads {
    @Id//Аннотация `@Id` указывает, что поле `id` является первичным ключом сущ
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Аннотация @GeneratedValue(strategy = GenerationType.IDENTITY)
    // указывает, что значение поля id будет генерироваться автоматически с помощью стратегии IDENTIFY. Это означает,
    // что идентификатор будет автоматически увеличиваться с добавлением новых экземпляров класса Ads.
    private Integer id;
    private BigDecimal price;//Поле price имеет тип BigDecimal и представляет собой числовое значение для указания цены объявления.
    private String title;//Поле title представляет собой строку, которая содержит заголовок объявления.
    private String description;//Поле description представляет собой строку, которая содержит описание объявления.

    @ManyToOne(fetch = FetchType.LAZY)//Аннотация @ManyToOne(fetch = FetchType.LAZY) указывает, что поле authorId
    // является связью многие к одному с классом User. Это означает, что каждое объявление имеет только одного автора.
    @JoinColumn(name = "user_id",
            referencedColumnName = "id", nullable = false)//Аннотация @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    // указывает, что поле authorId будет связано с полем id класса User. Кроме того, оно не может быть пустым (nullable = false).
    private User authorId;

    @ToString.Exclude//Аннотация @ToString.Exclude указывает, что поле image должно быть исключено из метода toString(),
    // чтобы избежать рекурсивного вызова.
    @OneToOne(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;
//Аннотация @OneToOne(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.LAZY) указывает, что поле image
// является связью один к одному с классом Image. Она указывает, что объявление является владельцем связи
// (mappedBy = "ads") и что любые операции, такие как сохранение, обновление или удаление этого объявления,
// также применяются к связанному объекту Image. FetchType.LAZY указывает, что загрузка изображения будет отложена до
// момента обращения к нему.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ads ads = (Ads) o;
        return getId() != null && Objects.equals(getId(), ads.getId());
    }
//Методы equals() и hashCode() переопределены на основе идентификатора объявления (id).
// Это позволяет правильно сравнивать и хешировать объекты класса Ads.
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}