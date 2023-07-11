package ru.skypro.homework.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс AdsDto представляет собой класс для передачи информации об объявлении.
 */
@Data//Аннотация "@Data" является частью проекта Lombok и генерирует стандартные методы,
// такие как геттеры, сеттеры, методы equals и hashCode, а также метод toString.
public class AdsDto {

    private Integer author;//целочисленное значение, представляющее идентификатор автора объявления.
    private String image;//строковое поле, представляющее путь к изображению объявления.
    private Long pk; //поле типа Long, представляющее уникальный идентификатор объявления.
    private BigDecimal price;//поле типа BigDecimal, представляющее цену объявления.
    private String title;//строковое поле, представляющее заголовок объявления.

}