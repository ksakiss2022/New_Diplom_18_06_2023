package ru.skypro.homework.mappers;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.AdsDtoFull;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repositories.ImageRepository;

import java.util.Collection;
import java.util.List;

/**
 * `AdsMapper` определяет ряд методов для преобразования объектов класса `Ads` в объекты класса `AdsDto` и обратно.
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
//Аннотация `@Mapper` указывает, что данный интерфейс является маппером, используемым для преобразования объектов
// из одного класса в другой. Она также определяет компонентную модель маппера, указывая, что создание и инжектирование
// экземпляров маппера будет управляться фреймворком Spring.
public interface AdsMapper {

    @Mapping(source = "id", target = "pk")//Аннотация `@Mapping` указывает, что поле `id` объекта `Ads`
    // должно быть преобразовано в поле `pk` объекта `AdsDto`.
    @Mapping(target = "image", expression = "java(getImage(ads))")//Аннотация `@Mapping` с атрибутом `expression`
    // указывает, что поле `image` объекта `AdsDto` должно быть вычислено с помощью метода `getImage(ads)`.
    @Mapping(target = "author", expression = "java(ads.getAuthorId().getId())")//Аннотация `@Mapping` с атрибутом
        // `expression` указывает, что поле `author` объекта `AdsDto` должно быть вычислено с помощью вызова метода
        // `ads.getAuthorId().getId()`.
    AdsDto adsToAdsDto(Ads ads); //Метод `adsToAdsDto` выполняет преобразование объекта `Ads` в объект `AdsDto`.

    //В данном случае используется несколько аннотаций `@Mapping`, чтобы указать соответствие между полями `Ads`
    // и `AdsDtoFull`. Особенность этого метода состоит в том, что он также вычисляет значения для полей
    // `authorFirstName`, `authorLastName`, `email` и `phone` объекта `AdsDtoFull`.
    @Mapping(target = "authorFirstName", source = "authorId.firstName")
    @Mapping(target = "authorLastName", source = "authorId.lastName")
    @Mapping(target = "email", source = "authorId.email")
    @Mapping(target = "image", expression="java(getImage(ads))")
    @Mapping(target = "phone", source = "authorId.phone")
    @Mapping(target = "pk", source = "id")
    AdsDtoFull adsToAdsDtoFull(Ads ads);//Метод `adsToAdsDtoFull` также выполняет преобразование объекта `Ads` в объект `AdsDtoFull`.

    //Метод `getImage` является дополнительным методом, используемым в методах `adsToAdsDto` и `adsToAdsDtoFull`
    // для вычисления значения поля `image` объектов `AdsDto` и `AdsDtoFull`.
    default String getImage(Ads ads) {
        if (ads.getImage() == null) {
            return null;//Если поле `image` объекта `Ads` равно `null`, метод возвращает `null`.
        }
        return "/ads/" + ads.getId() + "/getImage";
        //противном случае, он возвращает строку вида "/ads/" + ads.getId() + "/getImage".
    }

    //Метод `adsDtoToAds` выполняет обратное преобразование, преобразуя объект `AdsDto` в объект `Ads`.
    @InheritInverseConfiguration //В данном случае, аннотация `@InheritInverseConfiguration` указывает,
    // что обратное преобразование должно быть выполнено с использованием тех же настроек, что и прямое преобразование.
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "authorId", ignore = true)
    @Mapping(target = "description", ignore = true)
    //Аннотация `@Mapping` с атрибутом `ignore` указывает, что определенные поля (`image`, `authorId` и `description`)
    // не должны быть преобразованы при обратном преобразовании.
    Ads adsDtoToAds(AdsDto adsDto);

    //Метод `adsCollectionToAdsDto` преобразует коллекцию объектов `Ads`в коллекцию объектов `AdsDto`. Этот метод не
    // требует явного преобразования полей, так как он использует тот же маппер для преобразования отдельных объектов.
    Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection);
}