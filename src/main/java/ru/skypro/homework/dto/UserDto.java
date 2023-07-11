package ru.skypro.homework.dto;

import lombok.Data;

@Data//Аннотация `@Data` автоматически генерирует методы `toString()`,
// `equals()`, `hashCode()` и геттеры/сеттеры для всех полей класса.
public class UserDto {
    private Integer id;////целочисленное значение, представляющее собой идентификатор пользователя.
    private String email; //строковое поле, представляющее собой электронную почту пользователя.
    private String firstName; //строковое поле, представляющее собой имя пользователя.
    private String lastName; //строковое поле, представляющее собой фамилию пользователя
    private String phone;//строковое поле, представляющее собой номер телефона пользователя
    private String image; //строковое поле, представляющее собой путь к изображению объявления.
}