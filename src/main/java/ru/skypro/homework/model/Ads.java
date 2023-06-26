package ru.skypro.homework.model;

import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
//@Getter
//@Setter
//@ToString
//@RequiredArgsConstructor
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal price;
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User authorId;
    @OneToOne(mappedBy = "ads")
    private Image image;

    public Ads() {

    }


    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", authorId=" + authorId +
                ", image=" + image +
                '}';
    }

    public Ads(Integer id, BigDecimal price, String title, User authorId, Image image) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.authorId = authorId;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return id.equals(ads.id) && price.equals(ads.price) && title.equals(ads.title) && authorId.equals(ads.authorId) && image.equals(ads.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, title, authorId, image);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}