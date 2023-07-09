package ru.skypro.homework.dto;

import lombok.Data;
/**
 * Класс RegisterReq представляет собой класс отвечающий за регистрацию пользователя.
 */
@Data//Аннотация `@Data` автоматически генерирует методы `toString()`,
// `equals()`, `hashCode()`, `getter` и `setter` для всех полей класса.
public class RegisterReq {
    private String username;//строковое значение, представляющее собой имя пользователя.
    private String password;//строковое значение, представляющее собой пароль.
    private String firstName;//строковое значение, представляющее собой имя пользователя.
    private String lastName; //строковое значение, представляющее собой фамилию пользователя.
    private String phone; //строковое значение, представляющее собой телефон пользователя.
    private Role role;// типа `Role`, представляющий роль пользователя.
}
