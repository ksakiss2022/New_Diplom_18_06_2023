package ru.skypro.homework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс `Image` является сущностью в базе данных и представляет собой изображение, связанное с объявлением или пользователем
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//идентификатор изображения
    private String filePath;//путь к файлу изображения
    private long fileSize;//размер файла изображения
    private String mediaType;
    @Lob
    private byte[] preview;//превью изображения в виде массива байтов
    @OneToOne(optional = true)
    @JoinColumn(name = "ads_id", referencedColumnName = "id")
    @ToString.Exclude
    private Ads ads;//объявление, к которому относится изображение (связано с сущностью `Ads`)

    @OneToOne(optional = true)
    @JoinColumn(referencedColumnName = "id")
    @ToString.Exclude
    private User user;//пользователь, к которому относится изображение (связан с сущностью `User`)

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