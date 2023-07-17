package ru.skypro.homework.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Класс `User` является сущностью в базе данных и представляет собой пользователя системы.
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;//идентификатор пользователя
    private String email;// электронная почта пользователя
    private String firstName;//имя пользователя
    private String lastName;// фамилия пользователя
    private String phone;//телефон пользователя
    private String password;//пароль пользователя

    @Enumerated(EnumType.STRING)
    private Role role;//роль пользователя (связан с перечислением `Role`)
    @OneToOne(mappedBy = "user")
    @ToString.Exclude
    private Image avatar;// аватар пользователя (связан с сущностью `Image`)

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