package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;
import ru.skypro.homework.model.Ads;

import java.util.Collection;

/**
 * является интерфейсом с аннотацией `@Mapper(componentModel = "spring")`, что указывает MapStruct использовать
 * Spring для создания экземпляров маппера.
 */
@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "id", target = "pk")
    @Mapping(target = "image", expression = "java(getImage(ads))")
    @Mapping(target = "author", expression = "java(ads.getAuthorId().getId())")
    @Mapping(target = "description", source = "description")
    AdsDto adsToAdsDto(Ads ads);


    @Mapping(target = "authorFirstName", source = "authorId.firstName")
    @Mapping(target = "authorLastName", source = "authorId.lastName")
    @Mapping(target = "email", source = "authorId.email")
    @Mapping(target = "image", expression = "java(getImage(ads))")
    @Mapping(target = "phone", source = "authorId.phone")
    @Mapping(target = "pk", source = "id")
    AdsDtoFull adsToAdsDtoFull(Ads ads);

    default String getImage(Ads ads) {
        if (ads.getImage() == null) {
            return null;
        }
        return "/ads/" + ads.getId() + "/image";
    }

    @InheritInverseConfiguration
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "description", ignore = true)
    Ads adsDtoToAds(AdsDto adsDto);

    Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection);

    @Mapping(target = "image", expression = "java(ads.getImage())")
    void updateAds(AdsDto adsDto, @MappingTarget Ads ads);
}