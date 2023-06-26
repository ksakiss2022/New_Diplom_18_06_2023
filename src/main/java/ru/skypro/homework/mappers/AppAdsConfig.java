//package ru.skypro.homework.mappers;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import ru.skypro.homework.dto.AdsDto;
//import ru.skypro.homework.model.Ads;
//
//import java.util.Collection;
//
//@Configuration
//public class AppAdsConfig {
//    @Bean
//    public AdsMapper adsMapper() {
//        return new AdsMapper() {
//            @Override
//            public AdsDto adsToAdsDto(Ads ads) {
//                return null;
//            }
//
//            @Override
//            public Ads adsDtoToAds(AdsDto adsDto) {
//                return null;
//            }
//
//            @Override
//            public Collection<AdsDto> adsCollectionToAdsDto(Collection<Ads> adsCollection) {
//                return null;
//            }
//        };
//    }
//}
