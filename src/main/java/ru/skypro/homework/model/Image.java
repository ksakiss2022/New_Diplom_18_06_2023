package ru.skypro.homework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 *
 Класс Image представляет изображение, которое имеет уникальный идентификатор (id), путь к файлу (filePath), размер
 файла (fileSize), тип медиа (mediaType), превью изображения (preview), связь один к одному с объявлением (ads) и связь
 один к одному с пользователем (user).
 */
@Entity//Аннотация `@Entity` указывает, что данный класс является сущностью базы данных и будет отображаться в таблице `ads`.
@Getter
//Аннотация `@Getter` и `@Setter` генерирует геттеры и сеттеры для всех полей класса.
@Setter
@ToString//Аннотация `@ToString` генерирует метод `toString()`, который возвращает строковое представление объекта.
@RequiredArgsConstructor//Аннотация `@RequiredArgsConstructor` генерирует конструктор, принимающий все финальные поля класса.
@Table(name = "images")//Аннотация `@Table` указывает имя таблицы, в которой будет храниться сущность.
public class Image {
    @Id//Аннотация `@Id` указывает, что поле `id` является первичным ключом сущности.
    @GeneratedValue(strategy = GenerationType.IDENTITY)//значение поля id будет генерироваться автоматически с
    // помощью стратегии IDENTIFY.
    private Integer id;
    private String filePath;//Поле строка, которая содержит путь к файлу изображения.
    private long fileSize;//числовое значение для указания размера файла изображения.
    private String mediaType;// строка, которая содержит тип медиа изображения (например, "image/jpeg").
    @Lob//Аннотация @Lob указывает, что поле preview является большим объектом (LOB),
    // который может храниться в базе данных.
    private byte[] preview;//представляет собой массив байтов, который содержит превью изображения.
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ads_id", referencedColumnName = "id")
    @ToString.Exclude
    private Ads ads;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Image image = (Image) o;
        return getId() != null && Objects.equals(getId(), image.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}