package ru.skypro.homework.model;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] preview;
    @OneToOne(optional = true)
    @JoinColumn(name = "ads_id", referencedColumnName = "id")
    private Ads ads;

    @OneToOne(optional = true)
    @JoinColumn(referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return fileSize == image.fileSize && id.equals(image.id) && filePath.equals(image.filePath) && mediaType.equals(image.mediaType) && Arrays.equals(preview, image.preview) && ads.equals(image.ads) && user.equals(image.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, ads, user);
        result = 31 * result + Arrays.hashCode(preview);
        return result;
    }
}
