package ru.skypro.homework.mappers;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.model.Ads;
import java.util.Collection;
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AdsMapper {

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression = "java(ads.getImage().getPreview().toString())")
    @Mapping(target = "author", expression = "java(ads.getAuthorId().getId())")
    AdsDto adsToAdsDto(Ads ads);

    @InheritInverseConfiguration
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    Ads adsDtoToAds(AdsDto adsDto);
    Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection);
}
