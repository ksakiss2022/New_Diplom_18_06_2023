package ru.skypro.homework.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 *
 Класс User представляет пользователя, который имеет уникальный идентификатор (id), адрес электронной почты (email),
 имя (firstName), фамилию (lastName), телефон (phone), роль (role) и связь один к одному с изображением (avatar).
 */
@Entity//Аннотация `@Entity` указывает, что данный класс является сущностью базы данных и будет отображаться в таблице `ads`.
@Getter
//Аннотация `@Getter` и `@Setter` генерирует геттеры и сеттеры для всех полей класса.
@Setter
@ToString//Аннотация `@ToString` генерирует метод `toString()`, который возвращает строковое представление объекта.
@RequiredArgsConstructor//Аннотация `@RequiredArgsConstructor` генерирует конструктор, принимающий все финальные поля класса.
@Table(name = "users")//Аннотация `@Table` указывает имя таблицы, в которой будет храниться сущность.
public class User {
    @Id//Аннотация `@Id` указывает, что поле `id` является первичным ключом сущности.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//указывает, что значение поля id будет генерироваться
    // автоматически с помощью стратегии IDENTIFY.
    @NotNull
    private Integer id;
    private String email;//представляет собой строку, которая содержит адрес электронной почты пользователя.
    private String firstName;//представляет собой строку, которая содержит имя пользователя.
    private String lastName;// представляет собой строку, которая содержит фамилию пользователя.
    private String phone;//представляет собой строку, которая содержит номер телефона пользователя.
    @Enumerated(EnumType.STRING)//указывает, что поле role является перечислением и его значения должны храниться в виде строк.
    private Role role;//Поле role представляет собой роль пользователя.
    @OneToOne(mappedBy = "user")//Аннотация @OneToOne(mappedBy = "user") указывает, что связь с изображением является
    // владельцем связи (mappedBy = "user"). Это означает, что любые операции, такие как сохранение, обновление или
    // удаление этого пользователя, также применяются к связанному объекту Image.
    @ToString.Exclude//Аннотация @ToString.Exclude указывает, что поле avatar должно быть исключено из метода toString(),
    // чтобы избежать рекурсивного вызова.
    private Image avatar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}