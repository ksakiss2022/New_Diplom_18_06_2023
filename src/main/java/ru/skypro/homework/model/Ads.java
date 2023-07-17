package ru.skypro.homework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Класс `Ads` является сущностью в базе данных и представляет собой объявление о товаре или услуге.
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//идентификатор объявления
    private BigDecimal price;//цена товара или услуги, предлагаемого в объявлении
    private String title;//заголовок объявления
    private String description;//описание объявления

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            referencedColumnName = "id", nullable = false)
    private User authorId;//автор объявления (связан с сущностью `User`)

    @ToString.Exclude
    @OneToOne(mappedBy = "ads", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Image image;// изображение, связанное с объявлением (связан с сущностью `Image`)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ads ads = (Ads) o;
        return getId() != null && Objects.equals(getId(), ads.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}