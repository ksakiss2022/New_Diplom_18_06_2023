package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class AdsDtoFull {
    private String authorFirstName;//имя автора объявления.
    private String authorLastName;//фамилию автора объявления.
    private String description;//описание объявления.
    private String email;//представляет email автора объявления.
    private String image;//представляет изображение объявления.
    private String phone;// представляет телефон автора объявления.
    private Integer pk;//первичный ключ объявления.
    private Integer price;//представляет цену объявления.
    private String title;//представляет заголовок объявления.

}
