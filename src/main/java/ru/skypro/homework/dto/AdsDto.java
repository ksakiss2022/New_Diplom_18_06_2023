package ru.skypro.homework.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AdsDto {

    private Integer author;// которое представляет автора объявления.
    private String image;//изображение объявления
    private Long pk;// первичный ключ объявления.
    private BigDecimal price;//цена объявления.
    private String title;//заголовок объявления.
    private String description;//описание объявления
}