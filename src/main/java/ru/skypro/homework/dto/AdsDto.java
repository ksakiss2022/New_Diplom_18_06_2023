package ru.skypro.homework.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

//@Data

public class AdsDto {

    private Integer author;
    private String image;
    private Long pk;
    private BigDecimal price;
    private String title;

    @Override
    public String toString() {
        return "AdsDto{" +
                "author=" + author +
                ", image='" + image + '\'' +
                ", pk=" + pk +
                ", price=" + price +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdsDto adsDto = (AdsDto) o;
        return author.equals(adsDto.author) && image.equals(adsDto.image) && pk.equals(adsDto.pk) && price.equals(adsDto.price) && title.equals(adsDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, image, pk, price, title);
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
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
}
