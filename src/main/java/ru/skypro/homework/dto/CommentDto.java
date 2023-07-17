package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Integer author;//представляет автора комментария.
    private String authorImage;//представляет изображение автора комментария.
    private String authorFirstName;//представляет имя автора комментария.
    private String authorLastName;//представляет фамилию автора комментария.
    private Long createdAt;//представляет дату и время создания комментар
    private Integer pk;//первичный ключ
    private String text;//ьексь комментария

}