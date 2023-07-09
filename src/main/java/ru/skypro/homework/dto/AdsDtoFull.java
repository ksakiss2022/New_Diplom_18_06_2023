package ru.skypro.homework.dto;

import lombok.Data;

/**
 * Класс AdsDtoFull представляет собой класс для передачи полной информации об объявлении.
 */
@Data// Аннотация "@Data" является частью проекта Lombok и генерирует стандартные методы,
// такие как геттеры, сеттеры, методы equals и hashCode, а также метод toString.
public class AdsDtoFull {
    private String authorFirstName;// строковое поле, представляющее имя автора объявления.
    private String authorLastName;// строковое поле, представляющее фамилию автора объявления.
    private String description;//строковое поле, представляющее описание объявления.
    private String email;// строковое поле, представляющее адрес электронной почты автора объявления.
    private String image;//строковое поле, представляющее путь к изображению объявления.
    private String phone;//строковое поле, представляющее телефонный номер автора объявления.
    private Integer pk;//поле типа Integer, представляющее уникальный идентификатор объявления.
    private Integer price;//поле типа Integer, представляющее цену объявления.
    private String title;//строковое поле, представляющее заголовок объявления.
}
